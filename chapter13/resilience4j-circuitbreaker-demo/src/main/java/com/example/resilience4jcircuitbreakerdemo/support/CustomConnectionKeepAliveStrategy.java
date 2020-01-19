package com.example.resilience4jcircuitbreakerdemo.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2020/1/19
 */
public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    private final long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return Arrays.asList(httpResponse.getHeaders(HTTP.CONN_KEEP_ALIVE))
                .stream()
                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout") && StringUtils.isNumeric(h.getValue()))
                .findFirst()
                .map(e -> NumberUtils.toLong(e.getValue(), DEFAULT_SECONDS))
                .orElse(DEFAULT_SECONDS) * 1000;

    }
}