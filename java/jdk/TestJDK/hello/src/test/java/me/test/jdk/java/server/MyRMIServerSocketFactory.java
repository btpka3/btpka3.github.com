package me.test.jdk.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

// http://www.rmiproxy.com/doc/ejp/Internet.pdf
// http://stackoverflow.com/questions/2675362/how-to-find-an-available-port
public class MyRMIServerSocketFactory implements RMIServerSocketFactory {

    private static final int RANDOM_PORT = 0;

    private static final int MIN_PORT = 1;

    private static final int MAX_PORT = 65535;

    private int startPort = 0;

    private int endPort = 0;

    private int socketTimeout = 0;

    public static void main(String[] args) {
        MyRMIServerSocketFactory mySF = new MyRMIServerSocketFactory();
        mySF.setStartPort(10000);
        mySF.setEndPort(10002);

        for (int i = 0; i < 4; i++) {
            try {
                ServerSocket s = mySF.createServerSocket(0);
                System.out.println("listening on port: " + s.getLocalPort());
            } catch (IOException ex) {
                System.err.println("no available ports");
            }
        }
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket s = create(startPort, endPort);
        s.setSoTimeout(socketTimeout);
        return s;
    }

    public ServerSocket create(int startPort, int endPort) throws IOException {
        if (startPort < 0 || startPort > MAX_PORT) {
            throw new IOException("Invalid port number : " + startPort);
        }
        if (endPort < 0 || endPort > MAX_PORT) {
            throw new IOException("Invalid port number : " + endPort);
        }
        if (startPort == RANDOM_PORT && endPort == RANDOM_PORT) {
            return new ServerSocket(RANDOM_PORT);
        }

        if (startPort == RANDOM_PORT) {
            startPort = MIN_PORT;
        }
        if (endPort == RANDOM_PORT) {
            endPort = MAX_PORT;
        }
        int[] ports = new int[endPort - startPort + 1];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = startPort + i;
        }

        return create(ports);
    }

    public ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue;
            }
        }
        throw new IOException("no free port found");
    }

    public int getStartPort() {
        return startPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
