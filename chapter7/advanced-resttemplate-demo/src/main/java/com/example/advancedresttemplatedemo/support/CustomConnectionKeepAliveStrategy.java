package com.example.advancedresttemplatedemo.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/12/23
 * <description>：TODO
 */
public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    private final long DEFAULT_TIMEOUT = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return Arrays.asList(httpResponse.getHeaders(HTTP.CONN_KEEP_ALIVE))
                .stream()
                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout") && StringUtils.isNumeric(h.getValue()))
                .findFirst()
                .map(e -> NumberUtils.toLong(e.getValue(), DEFAULT_TIMEOUT))
                .orElse(DEFAULT_TIMEOUT) * 1000;
    }
}