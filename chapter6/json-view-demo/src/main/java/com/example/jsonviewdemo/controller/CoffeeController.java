package com.example.jsonviewdemo.controller;

import com.example.jsonviewdemo.controller.request.NewCoffeeReq;
import com.example.jsonviewdemo.model.Coffee;
import com.example.jsonviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * @create 2019/11/21
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/{id}")
    public Coffee findById(@PathVariable Long id) {
        return coffeeService.findById(id);
    }

    @GetMapping(path = "", params = "name")
    public Coffee findByName(@RequestParam String name) {
        return coffeeService.getByName(name);
    }

    @GetMapping(path = "", params = "!name")
    public List<Coffee> findAll() {
        return coffeeService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee AddCoffee(@Valid NewCoffeeReq req) {
        log.info("Coffee Add...");
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Coffee addJsonCoffee(@RequestBody @Valid NewCoffeeReq req) {
        log.info("Coffee Add...");
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam MultipartFile file) {

        log.info("Batch Add Start...");
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
                log.error("Exception:{}", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }
}