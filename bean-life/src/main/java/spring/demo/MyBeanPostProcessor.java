package spring.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @uthor : fengna
 * @create 2019/12/24
 * <description>：TODO
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor(){
        System.out.println("【BeanPostProcessor接口】构造方法");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【BeanPostProcessor接口】postProcessBeforeInitialization方法，可对"+beanName+"的属性更改。");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【BeanPostProcessor接口】postProcessAfterInitialization方法，可对"+beanName+"的属性更改。");
        return bean;
    }
}