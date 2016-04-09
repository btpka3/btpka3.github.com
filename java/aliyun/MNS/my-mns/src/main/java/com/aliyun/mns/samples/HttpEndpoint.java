/**
 * HttpEndpoint类可以作为一个完整的MNS的notification的Endpint实现使用。
 * 实现功能：
 * 1：在本起启动一个http服务
 * 2：接收发到/notifications的请求
 * 3：解析并验证发送到/notifications的请求
 *
 * HttpEndpoint类不依赖MNS的JAVA SDK, 但依赖apache的httpcomponents。如果你的项目用maven管理，
 * 请在pom中添加以下依赖：
 * <dependency>
 *   <groupId>org.apache.httpcomponents</groupId>
 *   <artifactId>httpasyncclient</artifactId>
 *   <version>4.0.1</version>
 * </dependency>
 */


package com.aliyun.mns.samples;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.protocol.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLServerSocketFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * HTTP/1.1 file server,处理发送到/notifications的请求
 */
public class HttpEndpoint {
    public static Logger logger = Logger.getLogger(HttpEndpoint.class);
    public static Thread t;
    private int port;

    /**
     * 静态方法，使用本机地址用于生成一个endpoint地址
     * @return http endpoint
     */
    public static String GenEndpointLocal() {
        return HttpEndpoint.GenEndpointLocal(80);
    }

    /**
     * 静态方法，使用本机地址用于生成一个endpoint地址
     * @param port, http server port
     * @return http endpoint
     */
    public static String GenEndpointLocal(int port) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString();
            return "http://" + ip + ":" + port;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.warn("get local host fail," + e.getMessage());
            return "http://127.0.0.1:" + port;
        }

    }

    /**
     * 构造函数，用指定端口构造HttpEndpoint对象
     * @param port， http server port
     */
    public HttpEndpoint(int port) {
        init(port);
    }

    /**
     * 构造函数，构造HttpEndpoint对象,默认80端口
     *
     */
    public HttpEndpoint() {
        init(80);
    }

    private void init(int port){
        this.port = port;
        t = null;
    }

    /**
     * start http server
     * @throws Exception
     */
    public void start() throws Exception {
        //check port if used
        try {
            new Socket(InetAddress.getLocalHost(), this.port);
            System.out.println("port is used!");
            logger.error("port already in use, http server start failed");
            throw new BindException("port already in use");
        } catch (IOException e) {
            //e.printStackTrace();

        }


        // Set up the HTTP protocol processor
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new ResponseDate())
                .add(new ResponseServer("MNS-Endpoint/1.1"))
                .add(new ResponseContent())
                .add(new ResponseConnControl()).build();

        // Set up request handlers, listen /notifications request whit NSHandler class
        UriHttpRequestHandlerMapper reqistry = new UriHttpRequestHandlerMapper();
        reqistry.register("/notifications", new NSHandler());
        reqistry.register("/simplified", new SimplifiedNSHandler());

        // Set up the HTTP service
        HttpService httpService = new HttpService(httpproc, reqistry);

        //start thread for http server
        t = new RequestListenerThread(port, httpService, null);
        t.setDaemon(false);
        t.start();
    }

    /**
     * stop http endpoint
     */
    public void stop() {
        if (t != null) {
            t.interrupt();
            try {
                t.join(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("endpoint stop");
    }
    
    /**
     * check if this request comes from MNS Server
     * @param method, http method
     * @param uri, http uri
     * @param headers, http headers
     * @param cert, cert url
     * @return true if verify pass
     */
    private Boolean authenticate(String method, String uri, Map<String, String> headers, String cert) {
        String str2sign = getSignStr(method, uri, headers);
        //System.out.println(str2sign);
        String signature = headers.get("Authorization");
        byte[] decodedSign = Base64.decodeBase64(signature);
        //get cert, and verify this request with this cert
        try {
            //String cert = "http://mnstest.oss-cn-hangzhou.aliyuncs.com/x509_public_certificate.pem";
            URL url = new URL(cert);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(conn.getInputStream());
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            Certificate c = cf.generateCertificate(in);
            PublicKey pk = c.getPublicKey();

            java.security.Signature signetcheck = java.security.Signature.getInstance("SHA1withRSA");
            signetcheck.initVerify(pk);
            signetcheck.update(str2sign.getBytes());
            Boolean res = signetcheck.verify(decodedSign);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("authenticate fail, " + e.getMessage());
            return false;
        }
    }
    
    /**
     * build string for sign
     * @param method, http method
     * @param uri, http uri
     * @param headers, http headers
     * @return String fro sign
     */
    private String getSignStr(String method, String uri, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Content-md5"));
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Content-Type"));
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Date"));
        sb.append("\n");

        List<String> tmp = new ArrayList<String>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getKey().startsWith("x-mns-"))
                tmp.add(entry.getKey() + ":" + entry.getValue());
        }
        Collections.sort(tmp);

        for (String kv : tmp) {
            sb.append(kv);
            sb.append("\n");
        }

        sb.append(uri);
        return sb.toString();
    }
    
    private String safeGetHeader(Map<String, String> headers, String name) {
        if (headers.containsKey(name))
            return headers.get(name);
        else
            return "";
    }
    
    public class SimplifiedNSHandler implements HttpRequestHandler {
        /**
         * process method for NSHandler
         * @param request, http request
         * @param response, http responst
         * @param context, http context
         * @throws HttpException
         * @throws IOException
         */
        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {

            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);

            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported");
            }

            Header[] headers = request.getAllHeaders();
            Map<String, String> hm = new HashMap<String, String>();
            for (Header h : headers) {
                System.out.println(h.getName() + ":" + h.getValue());
                hm.put(h.getName(), h.getValue());
            }

            String target = request.getRequestLine().getUri();
            System.out.println(target);


            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

                
              //verify request
                Header certHeader = request.getFirstHeader("x-mns-signing-cert-url");
                if (certHeader == null) {
                    System.out.println("SigningCerURL Header not found");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                
                String cert = certHeader.getValue();
                if (cert.isEmpty()) {
                    System.out.println("SigningCertURL empty");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                cert = new String(Base64.decodeBase64(cert));
                System.out.println("SigningCertURL:\t" + cert);
                logger.debug("SigningCertURL:\t" + cert);
                
                
                if (!authenticate(method, target, hm, cert)) {
                    System.out.println("authenticate fail");
                    logger.warn("authenticate fail");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                
                //parser content of simplified notification
                InputStream is = entity.getContent();                
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String content = buffer.toString();
                
                System.out.println("Simplified Notification: \n" + content);
            }

            response.setStatusCode(HttpStatus.SC_NO_CONTENT);
        }
    }

    /**
     * core class for processing /notifications request
     */
    public class NSHandler implements HttpRequestHandler {
        public Logger logger = Logger.getLogger(HttpRequestHandler.class);

        public NSHandler() {
            super();
        }

        private String safeGetElementContent(Element element, String tag) {
            NodeList nl = element.getElementsByTagName(tag);
            if (nl != null && nl.getLength() > 0) {
                return nl.item(0).getTextContent();
            } else {
                logger.warn("get " + tag + " from xml fail");
                return "";
            }
        }

        /**
         * parser /notifications message content
         * @param notify, xml element
         */
        private void paserContent(Element notify) {
            try {
                String topicOwner = safeGetElementContent(notify, "TopicOwner");
                System.out.println("TopicOwner:\t" + topicOwner);
                logger.debug("TopicOwner:\t" + topicOwner);

                String topicName = safeGetElementContent(notify, "TopicName");
                System.out.println("TopicName:\t" + topicName);
                logger.debug("TopicName:\t" + topicName);

                String subscriber = safeGetElementContent(notify, "Subscriber");
                System.out.println("Subscriber:\t" + subscriber);
                logger.debug("Subscriber:\t" + subscriber);

                String subscriptionName = safeGetElementContent(notify, "SubscriptionName");
                System.out.println("SubscriptionName:\t" + subscriptionName);
                logger.debug("SubscriptionName:\t" + subscriptionName);

                String msgid = safeGetElementContent(notify, "MessageId");
                System.out.println("MessageId:\t" + msgid);
                logger.debug("MessageId:\t" + msgid);

                // if PublishMessage with base64 message
                String msg = safeGetElementContent(notify, "Message");
                System.out.println("Message:\t" + new String(Base64.decodeBase64(msg)));
                logger.debug("Message:\t" + new String(Base64.decodeBase64(msg)));

                //if PublishMessage with string message
                //String msg = safeGetElementContent(notify, "Message");
                //System.out.println("Message:\t" + msg);
                //logger.debug("Message:\t" + msg);

                String msgMD5 = safeGetElementContent(notify, "MessageMD5");
                System.out.println("MessageMD5:\t" + msgMD5);
                logger.debug("MessageMD5:\t" + msgMD5);

                String msgPublishTime = safeGetElementContent(notify, "PublishTime");
                Date d = new Date(Long.parseLong(msgPublishTime));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strdate = sdf.format(d);
                System.out.println("PublishTime:\t" + strdate);
                logger.debug("MessagePublishTime:\t" + strdate);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                logger.warn(e.getMessage());
            }


        }


        /**
         * process method for NSHandler
         * @param request, http request
         * @param response, http responst
         * @param context, http context
         * @throws HttpException
         * @throws IOException
         */
        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {

            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);

            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported");
            }

            Header[] headers = request.getAllHeaders();
            Map<String, String> hm = new HashMap<String, String>();
            for (Header h : headers) {
                System.out.println(h.getName() + ":" + h.getValue());
                hm.put(h.getName(), h.getValue());
            }

            String target = request.getRequestLine().getUri();
            System.out.println(target);


            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

                //parser xml content
                InputStream content = entity.getContent();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                Element notify = null;
                try {
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = db.parse(content);
                    NodeList nl = document.getElementsByTagName("Notification");
                    if (nl == null || nl.getLength() == 0) {
                        System.out.println("xml tag error");
                        logger.warn("xml tag error");
                        response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                        return;
                    }
                    notify = (Element) nl.item(0);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    logger.warn("xml parser fail! " + e.getMessage());
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                } catch (SAXException e) {
                    e.printStackTrace();
                    logger.warn("xml parser fail! " + e.getMessage());
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }

                //verify request
                Header certHeader = request.getFirstHeader("x-mns-signing-cert-url");
                if (certHeader == null) {
                    System.out.println("SigningCerURL Header not found");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                
                String cert = certHeader.getValue();
                if (cert.isEmpty()) {
                    System.out.println("SigningCertURL empty");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                cert = new String(Base64.decodeBase64(cert));
                System.out.println("SigningCertURL:\t" + cert);
                logger.debug("SigningCertURL:\t" + cert);
                
                
                if (!authenticate(method, target, hm, cert)) {
                    System.out.println("authenticate fail");
                    logger.warn("authenticate fail");
                    response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                paserContent(notify);

            }

            response.setStatusCode(HttpStatus.SC_NO_CONTENT);
        }

    }

    /**
     * http listen work thread
     */
    public class RequestListenerThread extends Thread {

        private final HttpConnectionFactory<DefaultBHttpServerConnection> connFactory;
        private final ServerSocket serversocket;
        private final HttpService httpService;

        public RequestListenerThread(
                final int port,
                final HttpService httpService,
                final SSLServerSocketFactory sf) throws IOException {
            this.connFactory = DefaultBHttpServerConnectionFactory.INSTANCE;
            this.serversocket = sf != null ? sf.createServerSocket(port) : new ServerSocket(port);
            this.httpService = httpService;
        }

        @Override
        public void run() {
            System.out.println("Listening on port " + this.serversocket.getLocalPort());
            Thread t = null;
            while (!Thread.interrupted()) {
                try {
                    // Set up HTTP connection
                    Socket socket = this.serversocket.accept();
                    System.out.println("Incoming connection from " + socket.getInetAddress());
                    HttpServerConnection conn = this.connFactory.createConnection(socket);

                    // Start worker thread
                    t = new WorkerThread(this.httpService, conn);
                    t.setDaemon(true);
                    t.start();
                } catch (IOException e) {
                    System.err.println("Endpoint http server stop or IO error: "
                            + e.getMessage());
                    try {
                        if (t != null)
                            t.join(5*1000);
                    } catch (InterruptedException e1) {
                        //e1.printStackTrace();
                    }
                    break;
                }
            }
        }

        @Override
        public void interrupt() {
            super.interrupt();
            try {
                this.serversocket.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * http work thread, it will dispatch /notifications to NSHandler
     */
    public class WorkerThread extends Thread {

        private final HttpService httpservice;
        private final HttpServerConnection conn;

        public WorkerThread(
                final HttpService httpservice,
                final HttpServerConnection conn) {
            super();
            this.httpservice = httpservice;
            this.conn = conn;
        }

        @Override
        public void run() {
            System.out.println("New connection thread");
            HttpContext context = new BasicHttpContext(null);
            try {
                while (!Thread.interrupted() && this.conn.isOpen()) {
                    this.httpservice.handleRequest(this.conn, context);
                }
            } catch (ConnectionClosedException ex) {
                System.err.println("Client closed connection");
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex.getMessage());
            } catch (HttpException ex) {
                System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
            } finally {
                try {
                    this.conn.shutdown();
                } catch (IOException ignore) {
                }
            }
        }

    }

    /**
     * 简单的使用， main函数demo
     */
    public static void main(String[] args) {
        int port = 8080;
        HttpEndpoint httpEndpoint = null;
        try {
            httpEndpoint = new HttpEndpoint(port);
            httpEndpoint.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpEndpoint.stop();
        }
    }

}

