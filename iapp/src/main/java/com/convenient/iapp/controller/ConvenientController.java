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

   /* @ApiOperation("根据归属地查看邮政编码")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getZipCodeByArea", method = RequestMethod.POST)
    public String getZipCodeByArea(@RequestBody JSONObject json){
        return convenientService.getZipCodeByArea(json.toJSONString());
    }*/

   /* @ApiOperation("获取城市树结构")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/buildCityTree", method = RequestMethod.POST)
    public String buildCityTree(){
        return convenientService.buildCityTree();
    }*/

    @ApiOperation("根据星座查运势")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getFortuneByConstellation", method = RequestMethod.POST)
    public String getFortuneByConstellation(@RequestBody JSONObject json){
        return convenientService.getFortuneByConstellation(json.toJSONString());
    }

    @ApiOperation("查询ip")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getLocationByIp", method = RequestMethod.POST)
    public String getLocationByIp(@RequestBody JSONObject json){
        return convenientService.getLocationByIp(json.toJSONString());
    }

    @ApiOperation("成语释义")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/chengyu", method = RequestMethod.POST)
    public String getChengyu(@RequestBody JSONObject json){
        return convenientService.getChengyu(json.toJSONString());
    }

    @ApiOperation("近义词反义词")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/tyfy", method = RequestMethod.POST)
    public String getTyfy(@RequestBody JSONObject json){
        return convenientService.getTyfy(json.toJSONString());
    }

    @ApiOperation("新华字典")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/xhzd", method = RequestMethod.POST)
    public String getXhzd(@RequestBody JSONObject json){
        return convenientService.getXhzd(json.toJSONString());
    }

    @ApiOperation("汇率查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getExchange", method = RequestMethod.POST)
    public String getExchange(@RequestBody JSONObject json){
        return convenientService.getExchange(json.toJSONString());
    }

    @ApiOperation("彩票查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getLottery", method = RequestMethod.POST)
    public String getLottery(@RequestBody JSONObject json){
        return convenientService.getLottery(json.toJSONString());
    }


    @ApiOperation("获取彩虹屁")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getRainbowWord", method = RequestMethod.POST)
    public String getRainbowWord(){
        return convenientService.getRainbowWord();
    }

    @ApiOperation("获取朋友圈文案")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getPyqwa", method = RequestMethod.POST)
    public String getPyqwa(){
        return convenientService.getPyqWa();
    }

    @ApiOperation("垃圾分类")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getGarbageList", method = RequestMethod.POST)
    public String getGarbageList(@RequestBody JSONObject json){
        return convenientService.getGarbageList(json.toJSONString());
    }

  @ApiOperation("卡路里查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
//    })
    @RequestMapping(path = "/getCalorie", method = RequestMethod.POST)
    public String getCalorie(@RequestBody JSONObject json){
        return convenientService.getCalorie(json.toJSONString());
    }

//    @ApiOperation("翻译")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "requestData", value = "json", defaultValue = "")
////    })
//    @RequestMapping(path = "/getTranslate", method = RequestMethod.POST)
//    public String getTranslate(@RequestBody JSONObject json){
//        return convenientService.getTranslate(json.toJSONString());
//    }
}
