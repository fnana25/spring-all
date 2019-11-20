package com.example.contexthierarchydemo.foo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
@Slf4j
@Aspect
public class FooAspect {

    @AfterReturning("bean(testBean*)")
    public void afterReturning(){
        log.info("After hello()");
    }
}