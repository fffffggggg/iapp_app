package com.convenient.iapp.dao;

import com.convenient.iapp.entity.Message;

import java.util.List;

public interface MessageDao {

    boolean save(Message message);

    List<Message> getMessageList(Message message);

    Message getMessageSingle(Message message);
}
