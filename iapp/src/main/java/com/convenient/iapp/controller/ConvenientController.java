package com.convenient.iapp.controller;

import com.convenient.iapp.service.ConvenientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/query")
@Slf4j
public class ConvenientController {

    @Autowired
    private ConvenientService convenientService;

    @RequestMapping(path = "/getIdiomBySingleWord", method = RequestMethod.GET)
    public String getIdiomBySingleWord(@RequestParam("word")String word){
        return convenientService.getIdiomBySingleWord(word);
    }

    @RequestMapping(path = "/getBigNumByNumber", method = RequestMethod.GET)
    public String getBigNumByNumber(@RequestParam("number")String number){
        return convenientService.getBigNumByNumber(number);
    }

    @RequestMapping(path = "/getLocationByExpress", method = RequestMethod.GET)
    public String getLocationByExpress(@RequestParam("expressCom")String expressCom,@RequestParam("expressNum")String expressNum){
        return convenientService.getLocationByExpress(expressCom, expressNum);
    }

    @RequestMapping(path = "/getLocationByIdCard", method = RequestMethod.GET)
    public String getLocationByIdCard(@RequestParam("idCard")String idCard){
        return convenientService.getLocationByIdCard(idCard);
    }

    @RequestMapping(path = "/getLocationByMobile", method = RequestMethod.GET)
    public String getLocationByMobile(@RequestParam("mobile")String mobile){
        return convenientService.getLocationByMobile(mobile);
    }

    @RequestMapping(path = "/getLocationByZipCode", method = RequestMethod.GET)
    public String getLocationByZipCode(@RequestParam("zipCode")String zipCode){
        return convenientService.getLocationByZipCode(zipCode);
    }
}
