package com.ypwh.cron.msg.bean;


import java.io.Serializable;

public class DessertKafkaObject implements Serializable{
    private static final long serialVersionUID = 1L;

    private Dessert dessert;//点心对象

    /**
     * 点心操作日志对象
     */
    private DessertLog dessertLog;

    public Dessert getDessert() {
        return dessert;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }

    public DessertLog getDessertLog() {
        return dessertLog;
    }

    public void setDessertLog(DessertLog dessertLog) {
        this.dessertLog = dessertLog;
    }


}
