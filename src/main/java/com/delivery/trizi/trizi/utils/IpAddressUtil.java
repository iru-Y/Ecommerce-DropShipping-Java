package com.delivery.trizi.trizi.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class IpAddressUtil {

    private int serverPort;
    private String getIpAddress() throws UnknownHostException, SocketException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        if (inetAddress.isLoopbackAddress()) {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getHostAddress().contains(".")) {
                        return address.getHostAddress();
                    }
                }
            }
        }
        return inetAddress.getHostAddress();
    }

    public String getServerUrl(String url) throws UnknownHostException, SocketException {
        String ipAddress = getIpAddress();
        return "http://" + ipAddress + ":" + serverPort + "/" + url;
    }

    @EventListener
    public void onWebInit(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}
