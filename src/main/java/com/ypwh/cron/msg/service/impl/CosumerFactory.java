package com.ypwh.cron.msg.service.impl;

import com.ypwh.cron.msg.service.ComsumerService;

public class CosumerFactory implements ComsumerService {

    private ComsumerService comsumerService;



    public ComsumerService getComsumerService() {
        return comsumerService;
    }


    public void comsumerMsg(String topic, String str,String key) {
      this.comsumerService.comsumerMsg(topic,str,key);
    }

    public CosumerFactory(ComsumerService comsumerService) {
        this.comsumerService = comsumerService;
    }

}
