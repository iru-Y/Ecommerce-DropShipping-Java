package com.delivery.trizi.trizi.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Component
@AllArgsConstructor
@NoArgsConstructor
public class IpAddressUtil {
    private int serverPort;

    @EventListener
    void onWebInit(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
    private String getIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public String getServerUrl(String url) throws UnknownHostException {
        String ipAddress = getIpAddress();
        return "http://" + ipAddress + ":" + serverPort + "/" + url;
    }
}
