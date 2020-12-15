package com.convenient.iapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.dao.MessageDao;
import com.convenient.iapp.entity.Message;
import com.convenient.iapp.service.MessageService;
import com.convenient.iapp.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public boolean save(String data) {
       Message message = JSONObject.parseObject(data, Message.class);
       if(StringUtils.isEmpty(message.getOpenId()) || StringUtils.isEmpty(message.getUserName()) ||
       StringUtils.isEmpty(message.getMessageTitle()) || StringUtils.isEmpty(message.getMessage())){
           return false;
       }
       message.setCreateTime(DateUtil.getCurrentDate(new Date()));
       return messageDao.save(message);
    }

    @Override
    public String getMessageList(String data) {
        Message message = JSONObject.parseObject(data, Message.class);
        if(StringUtils.isEmpty(message.getEndTime()))
            message.setEndTime(DateUtil.getCurrentDate(new Date()));
        int pageNo = 1;
        int pageSize = 10;
        if(!StringUtils.isEmpty(message.getPageNo()))
            pageNo = message.getPageNo();
        if(!StringUtils.isEmpty(message.getPageSize()))
            pageSize =  message.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        List<Message> list = messageDao.getMessageList(message);
        return JSON.toJSONString(list);
    }

    @Override
    public String getMessageSingle(String data) {
        Message message = JSONObject.parseObject(data, Message.class);
        message = messageDao.getMessageSingle(message);
        return JSON.toJSONString(message);
    }
}
