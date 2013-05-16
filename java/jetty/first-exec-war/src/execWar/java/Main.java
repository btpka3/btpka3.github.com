import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.NetworkTrafficSelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        URL url = Main.class.getResource("index.html");
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " + url);
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " +  url.getFile());
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " +  new File(url.getFile()).exists());
        System.out.println("[[[[[[[[[[[[[[[[[ index.html = " +  url.toExternalForm());

        NetworkTrafficSelectChannelConnector connector = new NetworkTrafficSelectChannelConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        System.out.println("[[[[[[[[[[[[[[[[[ Main.class.getProtectionDomain() = " +  location);
        System.out.println("[[[[[[[[[[[[[[[[[ Main.class.getProtectionDomain() = " +  location.toExternalForm());


        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);
        //webapp.setDefaultsDescriptor("/path/to/webdefault.xml");

        server.start();
        server.join();

    }
}
