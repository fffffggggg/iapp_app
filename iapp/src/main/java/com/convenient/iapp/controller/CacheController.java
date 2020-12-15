package com.convenient.iapp.controller;

import com.convenient.iapp.util.RedisUtil;
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
@RequestMapping(path = "/cache")
@Slf4j
public class CacheController {

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(path = "/set", method = RequestMethod.GET)
    public boolean setValue(@RequestParam("key")String key, @RequestParam("value")String value){
        return redisUtil.set(key, value);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public Object getValue(@RequestParam("key")String key){
        return redisUtil.get(key);
    }
}
