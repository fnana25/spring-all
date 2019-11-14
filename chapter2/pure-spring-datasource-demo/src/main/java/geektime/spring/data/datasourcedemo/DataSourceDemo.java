package geektime.spring.data.datasourcedemo;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
public class DataSourceDemo {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
        showBeans(context);
        showDataSourceDemo(context);
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driverClassName","org.h2.Driver");
        properties.setProperty("url","jdbc:h2:mem:testdb");
        properties.setProperty("username","sa");
        return BasicDataSourceFactory.createDataSource(properties);

    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    private static void showBeans(ApplicationContext context){
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

    private static void showDataSourceDemo(ApplicationContext context) throws SQLException {
        DataSourceDemo demo = context.getBean("dataSourceDemo",DataSourceDemo.class);
        demo.showDataSource();
    }

    public void showDataSource() throws SQLException {
        System.out.println(dataSource.toString());
        Connection con = dataSource.getConnection();
        System.out.println(con.toString());
        con.close();
    }
}
