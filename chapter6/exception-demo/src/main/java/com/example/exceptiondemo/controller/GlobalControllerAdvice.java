package com.example.exceptiondemo.controller;

import com.example.exceptiondemo.controller.exception.FormValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @uthor : fengna
 * @create 2019/11/27
 * <description>：TODO
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FormValidationException.class)
    public Map<String,String> validationExceptionHandler(ValidationException exception){
        Map<String,String> map = new HashMap<>(1);
        map.put("message",exception.getMessage());
        return map;
    }
}