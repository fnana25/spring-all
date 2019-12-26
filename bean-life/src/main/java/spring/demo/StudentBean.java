package spring.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * @uthor : fengna
 * @create 2019/12/24
 * <description>：TODO
 */
public class StudentBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware {

    private String name;

    private int age;

    private String beanName;

    private BeanFactory beanFactory;

    @Override
    public String toString() {
        return "StudentBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【set注入】注入学生的name属性");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("【set注入】注入学生的age属性");
        this.age = age;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("【BeanFactoryAware接口】setBeanFactory方法");
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
        System.out.println("【BeanNameAware接口】setBeanName方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【DisposableBean接口】destroy方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】afterPropertiesSet方法");
    }

    public void myInit(){
        System.out.println("【init-method】");
    }

    public void myDestroy(){
        System.out.printf("【destroy-method】");
    }


}