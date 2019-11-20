package com.example.contexthierarchydemo.context;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
@Slf4j
@AllArgsConstructor
public class TestBean {

    private String context;

    public void hello() {
        log.info("hello {}" + context);
    }
}