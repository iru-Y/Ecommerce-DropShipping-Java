package com.delivery.trizi.trizi.utils;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class IpAddressUtil {
    private int serverPort;
    @EventListener
    void onWebInit(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
    private String getIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public String getServerUrl() throws UnknownHostException {
        String ipAddress = getIpAddress();
        return "http://" + ipAddress + ":" + serverPort;
    }
}
