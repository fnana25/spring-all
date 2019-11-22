package com.example.thymeleafviewdemo.controller;

import com.example.thymeleafviewdemo.controller.request.NewCoffeeRequest;
import com.example.thymeleafviewdemo.model.Coffee;
import com.example.thymeleafviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/22
 * <description>：TODO
 */
@Slf4j
@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @ResponseBody
    @GetMapping(path = "/{id}")
    public Coffee findCoffee(@PathVariable Long id) {
        return coffeeService.getCoffee(id);
    }

    @ResponseBody
    @GetMapping(path = "", params = "name")
    public Coffee findByName(@RequestParam String name) {
        return coffeeService.getCoffee(name);
    }

    @ResponseBody
    @GetMapping(path = "", params = "!name")
    public List<Coffee> findAll() {
        return coffeeService.getAll();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee addCoffee(@Valid NewCoffeeRequest req) {
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Coffee addJsonCoffee(@RequestBody @Valid NewCoffeeRequest req) {
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Coffee> addBatchCoffee(@RequestParam("file") MultipartFile file) {

        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    coffees.add(coffeeService.saveCoffee(arr[0], Money.of(CurrencyUnit.of("CNY"),
                            NumberUtils.createBigDecimal(arr[1]))));
                }

            } catch (IOException e) {
                log.error("Exception :{}", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }
}