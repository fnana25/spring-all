package com.example.contexthierarchydemo.context;

import com.example.contexthierarchydemo.foo.FooConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
@SpringBootApplication
public class ContextHierarchyDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContextHierarchyDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ApplicationContext fooContext = new AnnotationConfigApplicationContext(FooConfig.class);
		ClassPathXmlApplicationContext barContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"},fooContext);
		TestBean bean = fooContext.getBean("testBeanX",TestBean.class);
		bean.hello();

		log.info("==========");

		TestBean testBeanX = barContext.getBean("testBeanX",TestBean.class);
		testBeanX.hello();
		TestBean testBeanY = barContext.getBean("testBeanY",TestBean.class);
		testBeanY.hello();
	}
}
