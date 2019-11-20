package com.example.contexthierarchydemo.foo;

import com.example.contexthierarchydemo.context.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
@Configuration
@EnableAspectJAutoProxy
public class FooConfig {

    @Bean
    public TestBean testBeanX(){
        return new TestBean("foo");
    }

    @Bean
    public TestBean testBeanY(){
        return new TestBean("foo");
    }

    @Bean
    public FooAspect fooAspect(){
        return new FooAspect();
    }
}