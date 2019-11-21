package com.example.morecomplexcontrollerdemo.controller;

import com.example.morecomplexcontrollerdemo.controller.request.NewCoffeeRequest;
import com.example.morecomplexcontrollerdemo.model.Coffee;
import com.example.morecomplexcontrollerdemo.service.CoffeeService;
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
import org.springframework.validation.BindingResult;
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
 * @create 2019/11/20
 * <description>：TODO
 */
@Slf4j
@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee addCoffee(@Valid NewCoffeeRequest req) {
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee saveCoffee(@Valid NewCoffeeRequest req, BindingResult result) {
        if (result.hasErrors()) {
            log.info("Add error:{}", result);
            return null;
        }
        return coffeeService.saveCoffee(req.getName(), req.getPrice());
    }

    @ResponseBody
    @GetMapping(path = "",params = "!name",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Coffee> getAll() {
        return coffeeService.findAllCoffee();
    }

    @ResponseBody
    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Coffee getById(@PathVariable Long id) {
        return coffeeService.getById(id);
    }

    @ResponseBody
    @GetMapping(path = "", params = "name")
    public Coffee getByName(@RequestParam String name) {
        return coffeeService.getByName(name);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        coffees.add(coffeeService.saveCoffee(arr[0],
                                Money.of(CurrencyUnit.of("CNY"),
                                        NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("Error : ", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }
}