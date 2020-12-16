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

    /*@ApiOperation("根据汉字联想成语")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getIdiomBySingleWord", method = RequestMethod.POST)
    public String getIdiomBySingleWord(@RequestParam("requestData")String requestData){
        return convenientService.getIdiomBySingleWord(requestData);
    }*/

    /*@ApiOperation("数字转化为大写汉字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getBigNumByNumber", method = RequestMethod.POST)
    public String getBigNumByNumber(@RequestParam("requestData")String requestData){
        return convenientService.getBigNumByNumber(requestData);
    }*/

//    @RequestMapping(path = "/getLocationByExpress", method = RequestMethod.POST)
   /* public String getLocationByExpress(@RequestParam("requestData")String requestData){
        return convenientService.getLocationByExpress(null, null);
    }*/

    @ApiOperation("根据身份证查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getLocationByIdCard", method = RequestMethod.POST)
    public String getLocationByIdCard(@RequestParam("requestData")String requestData){
        return convenientService.getLocationByIdCard(requestData);
    }

    @ApiOperation("根据手机号查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getLocationByMobile", method = RequestMethod.POST)
    public String getLocationByMobile(@RequestParam("requestData")String requestData){
        return convenientService.getLocationByMobile(requestData);
    }

    @ApiOperation("根据邮政编码查找归属地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getLocationByZipCode", method = RequestMethod.POST)
    public String getLocationByZipCode(@RequestParam("requestData")String requestData){
        return convenientService.getLocationByZipCode(requestData);
    }

    @ApiOperation("根据星座查运势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
    })
    @RequestMapping(path = "/getFortuneByConstellation", method = RequestMethod.POST)
    public String getFortuneByConstellation(@RequestParam("requestData")String requestData){
        return convenientService.getFortuneByConstellation(requestData);
    }
}
