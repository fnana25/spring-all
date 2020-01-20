package com.example.ratelimiterwaiterservice.controller;

import com.example.ratelimiterwaiterservice.controller.request.NewCoffeeRequest;
import com.example.ratelimiterwaiterservice.model.Coffee;
import com.example.ratelimiterwaiterservice.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest request){
        return coffeeService.saveCoffee(request.getName(),request.getPrice());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest request){
        return coffeeService.saveCoffee(request.getName(),request.getPrice());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Coffee> batchAddCoffee(@RequestParam("file")MultipartFile file){
        List<Coffee> list = new ArrayList<>();
        if(!file.isEmpty()){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str = null;
                while((str = reader.readLine())!=null){
                    String[] arr = str.split(" ");
                    list.add(coffeeService.saveCoffee(arr[0], Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(arr[1]))));
                }
            }catch (IOException e){
                log.error("exception {}",e);
            }finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return list;
    }

    @GetMapping(path = "/",params = "!name")
    public List<Coffee> getAll(){
        return coffeeService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Coffee getById(@PathVariable Long id){
        return coffeeService.getById(id);
    }

    @GetMapping(path = "/",params = "name")
    public Coffee getByName(@RequestParam("name") String name){
        return coffeeService.getByName(name);
    }
}