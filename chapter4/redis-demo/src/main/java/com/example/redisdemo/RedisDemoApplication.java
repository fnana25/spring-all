package com.example.redisdemo;

import com.example.redisdemo.model.Coffee;
import com.example.redisdemo.service.CoffeeOrderService;
import com.example.redisdemo.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class RedisDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Coffee> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
        log.info("Coffee {}", c);

        for (int i = 0; i < 5; i++) {
            c = coffeeService.findOneCoffee("mocha");
        }

        log.info("Value from Redis: {}", c);
    }
}
