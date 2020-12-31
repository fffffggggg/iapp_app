package com.convenient.iapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.service.ConvenientService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/query")
@Slf4j
public class ConvenientController {

    @Autowired
    private ConvenientService convenientService;


    @ApiOperation("根据身份证查找归属地")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getLocationByIdCard", method = RequestMethod.POST)
    public String getLocationByIdCard(@RequestBody JSONObject json){
        return convenientService.getLocationByIdCard(json.toJSONString());
    }

    @ApiOperation("根据手机号查找归属地")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getLocationByMobile", method = RequestMethod.POST)
    public String getLocationByMobile(@RequestBody JSONObject json){
        return convenientService.getLocationByMobile(json.toJSONString());
    }

    @ApiOperation("根据邮政编码查找归属地")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getLocationByZipCode", method = RequestMethod.POST)
    public String getLocationByZipCode(@RequestBody JSONObject json){
        return convenientService.getLocationByZipCode(json.toJSONString());
    }

    @ApiOperation("根据星座查运势")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getFortuneByConstellation", method = RequestMethod.POST)
    public String getFortuneByConstellation(@RequestBody JSONObject json){
        return convenientService.getFortuneByConstellation(json.toJSONString());
    }
}
