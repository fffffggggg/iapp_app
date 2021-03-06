package com.convenient.iapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.service.MessageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/message")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation("保存留言")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "请求信息", defaultValue = "json")
//    })
    @RequestMapping(path = "/saveMsg", method = RequestMethod.POST)
    public boolean saveMsg(@RequestBody JSONObject json){
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("openId", "123");
//        map.put("userName", "jac");
//        map.put("messageTitle", "zzzz");
//        map.put("message", "bbb");
//        data = JSON.toJSONString(map);
        return messageService.save(json.toJSONString());
    }

    @ApiOperation("查询列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "请求信息", defaultValue = "json")
//    })
    @RequestMapping(path = "/getMsgList", method = RequestMethod.POST)
    public String getMsgList(@RequestBody JSONObject json){
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("openId", "123");
//        map.put("userName", "jac");
//        map.put("pageNo", 1);
//        map.put("pageSize", 5);
//        data = JSON.toJSONString(map);
        return messageService.getMessageList(json.toJSONString());
    }

    @ApiOperation("根据id查询单条")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "requestData", value = "请求信息", defaultValue = "json")
//    })
    @RequestMapping(path = "/getMsg", method = RequestMethod.POST)
    public String getMsg(@RequestBody JSONObject json){
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "0");
//        data = JSON.toJSONString(map);
        return messageService.getMessageSingle(json.toJSONString());
    }
}
