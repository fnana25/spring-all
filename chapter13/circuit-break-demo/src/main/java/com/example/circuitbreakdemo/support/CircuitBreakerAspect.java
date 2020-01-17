package com.example.circuitbreakdemo.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @uthor : fengna
 * @create 2020/1/17
 * <description>：TODO
 */
@Slf4j
@Aspect
@Component
public class CircuitBreakerAspect {

    private static final Integer THRESHOLD = 3;
    private Map<String, AtomicInteger> counter = new HashMap<>();
    private Map<String, AtomicInteger> breakCounter = new HashMap<>();

    @Around("execution(* com.example.circuitbreakdemo.integration..*(..))")
    public Object doWithCircuitBreaker(ProceedingJoinPoint joinPoint) throws Throwable {

        String signature = joinPoint.getSignature().toLongString();
        log.info("Invoke {}", signature);
        Object returnVal;
        try {
            if (counter.containsKey(signature)) {
                if (counter.get(signature).get() > THRESHOLD &&
                        breakCounter.get(signature).get() < THRESHOLD) {
                    log.warn("Circuit breaker return null, break {} times.",
                            breakCounter.get(signature).incrementAndGet());
                    return null;
                }
            } else {
                counter.put(signature, new AtomicInteger(0));
                breakCounter.put(signature, new AtomicInteger(0));
            }
            returnVal = joinPoint.proceed();
            counter.get(signature).set(0);
            breakCounter.get(signature).set(0);
        } catch (Throwable t) {
            log.warn("Circuit breaker counter: {}, Throwable {}",
                    counter.get(signature).incrementAndGet(), t.getMessage());
            breakCounter.get(signature).set(0);
            throw t;
        }
        return returnVal;
    }
}