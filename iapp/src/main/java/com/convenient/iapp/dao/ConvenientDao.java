package com.convenient.iapp.dao;

import java.util.List;
import java.util.Map;

public interface ConvenientDao {
    List<String> getIdCardInfo(Map<String, String> queryMap);

    void insertRainbow(Map<String, String> map);

    void insertPyqwn(Map<String, String> insertMap);

    int checkPyqwaIfexist(String content);

    int checkRainbowIfexist(String content);

    String getZipcodeByArea(Map<String, String> queryMap);

    List<Map<String, Object>> getCityList();

}
