package com.convenient.iapp.service;

public interface MessageService {

    boolean save(String data);

    String getMessageList(String data);

    String getMessageSingle(String data);
}
