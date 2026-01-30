package me.test.jdk.java.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

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

    @Test
    public void test4() throws UnknownHostException {
        //String ip = "192.168.1.1";
        byte[] bytes = new byte[4];
        for (byte b0 = 0; b0 <= 255; b0++) {
            for (byte b1 = 0; b1 <= 255; b1++) {
                for (byte b2 = 0; b2 <= 255; b2++) {
                    for (byte b3 = 0; b3 <= 255; b3++) {
                        bytes[0] = b0;
                        bytes[1] = b1;
                        bytes[2] = b2;
                        bytes[3] = b3;

                        int int1 = ipV4ToInt1(bytes);
                        int int2 = ipV4ToInt2(bytes);
                        int int3 = ipV4ToInt3(bytes);

                        String msg = "not equal : " +
                                "b0=" + b0 + ", " +
                                "b1=" + b1 + ", " +
                                "b2=" + b2 + ", " +
                                "b2=" + b2 + ", " +
                                "int1=" + int1 + ", " +
                                "int2=" + int2 + ", " +
                                "int3=" + int3;
                        System.out.println(msg);

//                        Assertions.assertEquals(msg, int1, int2);
                        //Assertions.assertEquals(msg, int1, int3);
                    }
                }
            }
        }
    }


    @SneakyThrows
    public static byte[] ip2bytes(String ip) {
        InetAddress addr = InetAddress.getByName(ip);
        return addr.getAddress();
    }


    public static int ipV4ToInt1(byte[] bytes) {
        if (bytes.length != 4) {
            throw new RuntimeException("invalid ipv4 address : " + bytes.length);
        }
        byte a1 = bytes[0];
        byte a2 = bytes[1];
        byte a3 = bytes[2];
        byte a4 = bytes[3];
        return (a1 << 24) ^ (a2 << 16) ^ (a3 << 8) ^ a4;
    }

    @SneakyThrows
    public static int ipV4ToInt2(byte[] bytes) {
        if (bytes.length != 4) {
            throw new RuntimeException("invalid ipv4 address : " + bytes.length);
        }
        int result = 0;
        for (byte b : bytes) {
            result = result << 8 | (b & 0xFF);
        }
        return result;
    }


    public static int ipV4ToInt3(byte[] bytes) {
        if (bytes.length != 4) {
            throw new RuntimeException("invalid ipv4 address : " + bytes.length);
        }

        long result = 0;
        for (int i = 0; i < 4; i++) {
            result += bytes[i] << (24 - (8 * i));
        }
        return ((Long) result).intValue();
    }
}
