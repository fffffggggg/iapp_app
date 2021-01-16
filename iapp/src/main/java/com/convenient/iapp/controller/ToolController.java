package com.convenient.iapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.service.ToolService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tool")
@Slf4j
public class ToolController {

    @Autowired
    private ToolService toolService;

    @ApiOperation("数字转化为大写")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/numConvert", method = RequestMethod.POST)
    public String getNumConvert(@RequestBody JSONObject json){
        return toolService.getNumConvert(json.toJSONString());
    }

    @ApiOperation("MD5签名")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/md5Sign", method = RequestMethod.POST)
    public String getMd5Sign(@RequestBody JSONObject json){
        return toolService.getMd5Sign(json.toJSONString());
    }

    @ApiOperation("摩尔斯密码加解密")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/morseCode", method = RequestMethod.POST)
    public String getMorseCode(@RequestBody JSONObject json){
        return toolService.getMorseCode(json.toJSONString());
    }

    @ApiOperation("获取随机数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/randomNumber", method = RequestMethod.POST)
    public String getRandomNumber(@RequestBody JSONObject json){
        return toolService.getRandomNumber(json.toJSONString());
    }

    @ApiOperation("RC4加解密")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getRc4", method = RequestMethod.POST)
    public String getRc4(@RequestBody JSONObject json){
        return toolService.getRc4(json.toJSONString());
    }

    @ApiOperation("汉字转拼音")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getPy", method = RequestMethod.POST)
    public String getPy(@RequestBody JSONObject json){
        return toolService.getPy(json.toJSONString());
    }

    @ApiOperation("字符串base64加解密")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getBase64", method = RequestMethod.POST)
    public String getBase64(@RequestBody JSONObject json){
        return toolService.getBase64(json.toJSONString());
    }

    @ApiOperation("公式计算 根据数学表示式进行计算并返回字符串")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getResultByExp", method = RequestMethod.POST)
    public String getResultByExp(@RequestBody JSONObject json){
        return toolService.getResultByExp(json.toJSONString());
    }

    @ApiOperation("获取二维码")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getQrCode", method = RequestMethod.POST)
    public String getQrCode(@RequestBody JSONObject json){
        return toolService.getQrCode(json.toJSONString());
    }

}
