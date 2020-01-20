package com.example.bulkheadcustomerservice.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    private final long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return Arrays.asList(httpResponse.getHeaders(HTTP.CONN_KEEP_ALIVE))
                .stream()
                .filter(h -> StringUtils.equalsAnyIgnoreCase(h.getName(), "timeout") && StringUtils.isNumeric(h.getValue()))
                .findFirst()
                .map(h -> NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS))
                .orElse(DEFAULT_SECONDS) * 1000;

    }
}