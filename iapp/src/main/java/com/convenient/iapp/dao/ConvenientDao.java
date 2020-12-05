package com.convenient.iapp.dao;

import java.util.List;
import java.util.Map;

public interface ConvenientDao {
    List<String> getIdCardInfo(Map<String, String> queryMap);
}
