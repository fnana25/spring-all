package com.example.exceptiondemo.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @uthor : fengna
 * @create 2019/11/27
 * <description>：TODO
 */
@Getter
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormValidationException extends RuntimeException{

    private final BindingResult result;
}