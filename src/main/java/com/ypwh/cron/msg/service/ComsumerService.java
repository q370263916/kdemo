package com.ypwh.cron.msg.service;



import java.util.List;

public interface ComsumerService {

     void comsumerMsg(String topic, String str,String key);

}