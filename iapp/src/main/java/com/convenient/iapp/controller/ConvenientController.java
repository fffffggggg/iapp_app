package com.convenient.iapp.controller;

import com.convenient.iapp.service.ConvenientService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("根据汉字联想成语")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "汉字", defaultValue = "一")
    })
    @RequestMapping(path = "/getIdiomBySingleWord", method = RequestMethod.GET)
    public String getIdiomBySingleWord(@RequestParam("word")String word){
        return convenientService.getIdiomBySingleWord(word);
    }

    @ApiOperation("数字转化为大写汉字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "数字", defaultValue = "123")
    })
    @RequestMapping(path = "/getBigNumByNumber", method = RequestMethod.GET)
    public String getBigNumByNumber(@RequestParam("number")String number){
        return convenientService.getBigNumByNumber(number);
    }

    @RequestMapping(path = "/getLocationByExpress", method = RequestMethod.GET)
    public String getLocationByExpress(@RequestParam("expressCom")String expressCom,@RequestParam("expressNum")String expressNum){
        return convenientService.getLocationByExpress(expressCom, expressNum);
    }

    @ApiOperation("根据身份证查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", value = "身份证号码", defaultValue = "100101197301012011")
    })
    @RequestMapping(path = "/getLocationByIdCard", method = RequestMethod.GET)
    public String getLocationByIdCard(@RequestParam("idCard")String idCard){
        return convenientService.getLocationByIdCard(idCard);
    }

    @ApiOperation("根据手机号查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", defaultValue = "13011223344")
    })
    @RequestMapping(path = "/getLocationByMobile", method = RequestMethod.GET)
    public String getLocationByMobile(@RequestParam("mobile")String mobile){
        return convenientService.getLocationByMobile(mobile);
    }

    @ApiOperation("根据邮政编码查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zipCode", value = "邮政编码", defaultValue = "100000")
    })
    @RequestMapping(path = "/getLocationByZipCode", method = RequestMethod.GET)
    public String getLocationByZipCode(@RequestParam("zipCode")String zipCode){
        return convenientService.getLocationByZipCode(zipCode);
    }
}
