package com.example.ratelimiterwaiterservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
//preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
//postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView （这个博主就基本不怎么用了）；
//afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；
@Slf4j
public class PerformanceInteceptor implements HandlerInterceptor {

    private ThreadLocal<StopWatch> stopWatch = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch sw = new StopWatch();
        stopWatch.set(sw);
        sw.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        stopWatch.get().stop();
        stopWatch.get().start();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch sw = stopWatch.get();
        sw.stop();
        String method = handler.getClass().getSimpleName();
        if (handler instanceof HandlerMethod) {
            String beanType = ((HandlerMethod) handler).getBeanType().getName();
            String methodName = ((HandlerMethod) handler).getMethod().getName();
            method = beanType + "." + methodName;
        }
        log.info("{};{};{};{};{}ms;{}ms;{}ms", request.getRequestURI(), method,
                response.getStatus(), ex == null ? "-" : ex.getClass().getSimpleName(),
                sw.getTotalTimeMillis(), sw.getTotalTimeMillis() - sw.getLastTaskTimeMillis(),
                sw.getLastTaskTimeMillis());
        stopWatch.remove();
    }
}