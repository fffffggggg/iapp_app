package com.convenient.iapp.threadpool;

import com.convenient.iapp.dao.AscTaskDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class AscTask implements Runnable{


    private AscTaskDao ascTaskDao;

    private String method;
    private List<Map<String, String>> paramList;

    public AscTask(){}

    public AscTask(String method, List<Map<String, String>> paramList, AscTaskDao ascTaskDao){
        this.method = method;
        this.paramList = paramList;
        this.ascTaskDao = ascTaskDao;
    }

    @Override
    public void run() {
        if("garbage".equals(method)){
            doInsertGarbage(paramList);
        }else if("calorie".equals(method)){
            doInsertCalorie(paramList);
        }
    }

    private void doInsertCalorie(List<Map<String, String>> paramList) {
        log.info("enter asc task");

        List<Map<String, String>> insertMap = new ArrayList<>();
        for(Map<String, String> map : paramList){
            String name = map.get("name");
            if(StringUtils.isEmpty(name))
                continue;
            int i = ascTaskDao.checkCalorieNameIfExist(name);
            if(i == 0){
                insertMap.add(map);
            }
        }
        try {
            log.info("do insert");
            ascTaskDao.insertCalorieBatch(insertMap);
        } catch (Exception e) {
            log.error("" + e);
        }
    }

    public void doInsertGarbage(List<Map<String, String>> paramList){
        log.info("enter asc task");

        List<Map<String, String>> insertMap = new ArrayList<>();
        for(Map<String, String> map : paramList){
            String name = map.get("name");
            if(StringUtils.isEmpty(name))
                continue;
            int i = ascTaskDao.checkGarbageNameIfExist(name);
            if(i == 0){
                insertMap.add(map);
            }
        }
        try {
            log.info("do insert");
            ascTaskDao.insertGarbageBatch(insertMap);
        } catch (Exception e) {
            log.error("" + e);
        }
    }


}
