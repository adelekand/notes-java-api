package com.adelekand.speer.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class HttpRequestUtil implements Serializable {

    public String getIpAddress(HttpServletRequest request) {
        String[] headersToCheck = {
                "x-forwarded-for",
                "Proxy-Client-IP",
                "x-real-ip",
                "x-forwarded-server",
                "x-forwarded-host",
                "cf-connecting-ip",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")) {
                return ip;
            }
        }

        // If no valid IP found in headers, fallback to remote address
        return request.getRemoteAddr();
    }
}
