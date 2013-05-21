import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.ConnectorStatistics;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ClassList classList = ClassList.setServerDefault(server);
        classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        webapp.setWar(location.toExternalForm());
        webapp.setDefaultsDescriptor("META-INF/jetty/webdefault.xml");
        server.setHandler(webapp);
        server.start();
        server.join();
    }

    // http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
    public static void main1(String[] args) throws Exception {

        // String jettyHome = null;
        // String jetty_home = System.getProperty("jetty.home",
        // "../../jetty-distribution/target/distribution");
        // System.setProperty("jetty.home", jetty_home);

        // ---------------------------------- jetty.xml
        int minThreads = Integer.valueOf(System.getProperty("threads.min", "10"));
        int maxThreads = Integer.valueOf(System.getProperty("threads.max", "200"));
        int idleTimeout = Integer.valueOf(System.getProperty("threads.timeout", "60000"));
        QueuedThreadPool threadPool = new QueuedThreadPool(minThreads, maxThreads, idleTimeout);
        threadPool.setDetailedDump(false);

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(Integer.valueOf(System.getProperty("jetty.secure.port", "8443")));
        httpConfig.setOutputBufferSize(32768);
        httpConfig.setRequestHeaderSize(8192);
        httpConfig.setResponseHeaderSize(8192);
        httpConfig.setSendServerVersion(true);
        httpConfig.setSendDateHeader(false);
        httpConfig.setHeaderCacheSize(512);

        Server server = new Server(threadPool);
        server.addBean(new ScheduledExecutorScheduler());

        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        handlers.setHandlers(new Handler[]{contexts, new DefaultHandler()});
        server.setHandler(handlers);

        server.setStopAtShutdown(true);
        server.setStopTimeout(5000);
        server.setDumpAfterStart(Boolean.valueOf(System.getProperty("jetty.dump.start", "false")));
        server.setDumpBeforeStop(Boolean.valueOf(System.getProperty("jetty.dump.stop", "false")));

        // ---------------------------------- jetty-jmx.xml
        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // server.addBean(new MBeanContainer(mBeanServer));
        // server.addBean(new Log());

        // ---------------------------------- jetty-http.xml
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        http.setHost(System.getProperty("jetty.host"));
        http.setPort(Integer.valueOf(System.getProperty("jetty.port", "8080")));
        http.setIdleTimeout(Integer.valueOf(System.getProperty("http.timeout", "30000")));
        server.addConnector(http);

        // ---------------------------------- jetty-deploy.xml
        // DeploymentManager deployer = new DeploymentManager();
        // deployer.setContexts(handlers);
        // deployer.setContextAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
        // ".*/servlet-api-[^/]*\\.jar$");
        // WebAppProvider webappProvider = new WebAppProvider();
        // webappProvider.setMonitoredDirName(jettyHome + "/webapps");
        // webappProvider.setDefaultsDescriptor(jettyHome +
        // "/etc/webdefault.xml");
        // webappProvider.setScanInterval(1);
        // webappProvider.setExtractWars(true);
        // webappProvider.setConfigurationManager(new
        // PropertiesConfigurationManager());
        // deployer.addAppProvider(webappProvider);
        // server.addBean(deployer);

        // ---------------------------------- jetty-rewrite.xml
        RewriteHandler rewrite = new RewriteHandler();
        rewrite.setHandler(server.getHandler());
        rewrite.setRewriteRequestURI(Boolean.valueOf(System.getProperty("rewrite.rewriteRequestURI", "true")));
        rewrite.setRewritePathInfo(Boolean.valueOf(System.getProperty("rewrite.rewritePathInfo", "false")));
        rewrite.setOriginalPathAttribute(System.getProperty("rewrite.originalPathAttribute", "requestedPath"));

        // ---------------------------------- jetty-demo.xml
        // ---------------------------------- test-realm.xml
        // HashLoginService login = new HashLoginService();
        // login.setName("Test Realm");
        // login.setConfig(jettyHome + "/etc/realm.properties");
        // login.setRefreshInterval(0);
        // server.addBean(login);

        // ---------------------------------- jetty-jaas.xml

        // ---------------------------------- jetty-plus.xml
        ClassList classList = ClassList.setServerDefault(server);
        classList.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
                "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                "org.eclipse.jetty.plus.webapp.PlusConfiguration");

        // jetty-annotations.xml
        classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        // jetty-stats.xml
        StatisticsHandler stats = new StatisticsHandler();
        stats.setHandler(server.getHandler());
        server.setHandler(stats);
        ConnectorStatistics.addToAllConnectors(server);

        // ---------------------------------- jetty-requestlog.xml
        // AsyncNCSARequestLog requestLogImpl = new AsyncNCSARequestLog();
        //
        // requestLogImpl.setFilename(jettyHome+System.getProperty("jetty.logs","/logs")+"/yyyy_mm_dd.request.log");
        // requestLogImpl.setFilenameDateFormat("yyyy_MM_dd");
        // requestLogImpl.setRetainDays(Integer.valueOf(System.getProperty("requestlog.retain","90")));
        // requestLogImpl.setAppend(Boolean.valueOf(System.getProperty("requestlog.append","true")));
        // requestLogImpl.setExtended(Boolean.valueOf(System.getProperty("requestlog.extended","true")));
        // requestLogImpl.setLogCookies(false);
        // requestLogImpl.setLogTimeZone("GMT");
        //
        // RequestLogHandler requestLogHandler = new RequestLogHandler();
        // requestLogHandler.setRequestLog(requestLogImpl);
        // handlers.addHandler(requestLogHandler);

        // ---------------------------------- jetty-lowresources.xml
        // LowResourceMonitor lowResourcesMonitor = new
        // LowResourceMonitor(server);
        // lowResourcesMonitor.setPeriod(Integer.valueOf(System.getProperty("lowresources.period","1000")));
        // lowResourcesMonitor.setLowResourcesIdleTimeout(Integer.valueOf(System.getProperty("lowresources.lowResourcesIdleTimeout","200")));
        // lowResourcesMonitor.setMonitorThreads(Boolean.valueOf(System.getProperty("lowresources.monitorThreads","true")));
        // lowResourcesMonitor.setMaxConnections(Integer.valueOf(System.getProperty("lowresources.maxConnections","0")));
        // lowResourcesMonitor.setMaxMemory(Integer.valueOf(System.getProperty("lowresources.maxMemory","0")));
        // lowResourcesMonitor.setMaxLowResourcesTime(Integer.valueOf(System.getProperty("lowresources.maxLowResourcesTime","0")));
        // server.addBean(lowResourcesMonitor);

        // ---------------------------------- jetty-ssl.xml
        // SslContextFactory sslContextFactory = new SslContextFactory();
        // sslContextFactory.setKeyStorePath(jettyHome+System.getProperty("jetty.keystore","/etc/keystore"));
        // sslContextFactory.setKeyStorePassword(System.getProperty("jetty.keystore.password","OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4"));
        // sslContextFactory.setKeyManagerPassword(System.getProperty("jetty.keymanager.password","OBF:1u2u1wml1z7s1z7a1wnl1u2g"));
        // sslContextFactory.setTrustStorePath(jettyHome+System.getProperty("jetty.truststore","/etc/keystore"));
        // sslContextFactory.setTrustStorePassword(System.getProperty("jetty.truststore.password","OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4"));
        // sslContextFactory.setEndpointIdentificationAlgorithm(null);
        // sslContextFactory.setExcludeCipherSuites(
        // "SSL_RSA_WITH_DES_CBC_SHA",
        // "SSL_DHE_RSA_WITH_DES_CBC_SHA",
        // "SSL_DHE_DSS_WITH_DES_CBC_SHA",
        // "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
        // "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
        // "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
        // "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
        // HttpConfiguration sslHttpConfig = new HttpConfiguration(httpConfig);
        // sslHttpConfig.addCustomizer(new SecureRequestCustomizer());

        // ---------------------------------- jetty-https.xml
        // ServerConnector httpsConnector = new ServerConnector(server,
        // new SslConnectionFactory(sslContextFactory, "http/1.1"),
        // new HttpConnectionFactory(sslHttpConfig));
        // httpsConnector.setHost(System.getProperty("jetty.host"));
        // httpsConnector.setPort(Integer.valueOf(System.getProperty("jetty.https.port","8443")));
        // httpsConnector.setIdleTimeout(30000);
        // server.addConnector(httpsConnector);

        URL url = Main.class.getResource("index.html");
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " + url);
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " + url.getFile());
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " + new File(url.getFile()).exists());
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " + url.toExternalForm());

        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        System.out.println("[[[[[[[[[[[[[[[[[ Main.class.getProtectionDomain() = " + location);
        System.out.println("[[[[[[[[[[[[[[[[[ Main.class.getProtectionDomain() = " + location.toExternalForm());

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        // webapp.setConfigurations(new Configuration[] { new
        // AnnotationConfiguration() });

        // Resource d =
        // Resource.newSystemResource(webapp.getDefaultsDescriptor());
        // System.out.println("^^^^^^^^^^^^^^^^ defaultsDescriptor = " +
        // webapp.getDefaultsDescriptor());
        // if (d != null) {
        // System.out
        // .println("^^^^^^^^^^^^^^^^ defaultsDescriptor newSystemResource EXISTS and url = "
        // + d.toString());
        // } else {
        // d = webapp.newResource(webapp.getDefaultsDescriptor());
        // if (d != null) {
        // System.out.println("^^^^^^^^^^^^^^^^ defaultsDescriptor newResource EXISTS and url = "
        // + d.toString());
        // } else {
        // System.out.println("^^^^^^^^^^^^^^^^ defaultsDescriptor = " +
        // webapp.getDefaultsDescriptor()
        // + " NOT exists");
        // }
        // }

        webapp.setDefaultsDescriptor("META-INF/jetty/webdefault.xml");

        // System.out.println("================= defaultsDescriptor = " +
        // webapp.getDefaultsDescriptor());
        // if (d != null) {
        // System.out
        // .println("================= defaultsDescriptor newSystemResource EXISTS and url = "
        // + d.toString());
        // } else {
        // d = webapp.newResource(webapp.getDefaultsDescriptor());
        // if (d != null) {
        // System.out.println("================= defaultsDescriptor newResource EXISTS and url = "
        // + d.toString());
        // } else {
        // System.out.println("================= defaultsDescriptor = " +
        // webapp.getDefaultsDescriptor()
        // + " NOT exists");
        // }
        // }

        // webapp.setHandler(server.getHandler());
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
