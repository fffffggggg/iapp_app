package com.convenient.iapp.dao;

import java.util.List;
import java.util.Map;

public interface AscTaskDao {

    void insertGarbageBatch(List<Map<String, String>> paramList);

    int checkGarbageNameIfExist(String name);

    int checkCalorieNameIfExist(String name);

    void insertCalorieBatch(List<Map<String, String>> insertMap);
}
