package me.test.jdk.java.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class InetAddressTest {


    @Test
    public void test1() throws Exception {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }

    @Test
    public void test2() throws SocketException, UnknownHostException {
        System.out.println(getPublicIPv4());
    }

    @Test
    public void test3() {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface nif = netInterfaces.nextElement();
                Enumeration<InetAddress> InetAddress = nif.getInetAddresses();
                while (InetAddress.hasMoreElements()) {
                    InetAddress ipAddr = InetAddress.nextElement();
                    System.out.println(

                            "getAddress=" + ipAddr.getAddress()
//                                    + ", hostname=" + ipAddr.getCanonicalHostName()
//                                    + ", getHostName=" + ipAddr.getHostName()
                                    + ", getHostAddress=" + ipAddr.getHostAddress()
//                            + ", isAnyLocalAddress=" + ipAddr.isAnyLocalAddress()
//                            + ", isLinkLocalAddress=" + ipAddr.isLinkLocalAddress()
//                            + ", isSiteLocalAddress=" + ipAddr.isSiteLocalAddress()
//                            + ", isMCGlobal=" + ipAddr.isMCGlobal()
//                            + ", isMCNodeLocal=" + ipAddr.isMCNodeLocal()
//                            + ", isMCSiteLocal=" + ipAddr.isMCSiteLocal()
//                            + ", isMCOrgLocal=" + ipAddr.isMCOrgLocal()

                    );
                }
            }
        } catch (SocketException e) {
        }
    }


    static String getPublicIPv4() throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        String ipToReturn = null;
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                String currentAddress = i.getHostAddress();
                if (!i.isSiteLocalAddress() && !i.isLoopbackAddress()
                    //&& validate(currentAddress)
                ) {
                    ipToReturn = currentAddress;
                } else {
                    System.out.println("Address not validated as public IPv4");
                }
            }
        }

        return ipToReturn;
    }

    private static final Pattern IPv4RegexPattern = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validate(final String ip) {
        return IPv4RegexPattern.matcher(ip).matches();
    }
}
