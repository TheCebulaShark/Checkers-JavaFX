package com.example.checkerajavafx;

import java.util.Random;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerUtils {
    public static String generateRandomPort() {
        Random random = new Random();
        StringBuilder port = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            port.append(digit);
        }

        return port.toString();
    }

    public static String getServerIPAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
