package com.ypwh.cron.msg.util;

import com.ypwh.cron.msg.bean.Dessert;
import com.ypwh.cron.msg.bean.DessertKafkaObject;
import com.ypwh.cron.msg.bean.DessertLog;
import net.sf.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Map;

public class KafkaProducerTest {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:kafkaProducer.xml"}); 
		context.start();
        //System.in.read();// 为保证服务一直开着，利用输入流的阻塞来模拟 
		Dessert de1 = new Dessert();
    	de1.setId(23252);
    	de1.setUserId(20517611);
    	de1.setScore(new BigDecimal(10));
    	de1.setBalance(new BigDecimal(10));
    	de1.setBizType(1);
    	de1.setIsUse(1);
        DessertLog log1 = new DessertLog();
        log1.setUserId(20517611);
        log1.setContent("123");
        log1.setRevenueType(1);//收支类型 : 1: 收入; 2: 支出 
    	log1.setIncome(new BigDecimal(10));
        log1.setBizType(1);//类型1 点心 2 积分 
        log1.setTradeId(Long.parseLong("99435844922"));
        log1.setScore(new BigDecimal(10));
        log1.setConfigId(1);
        log1.setOperateType(0);
        log1.setDessertConfigName("test");
        log1.setCurrentScoreTotal(new BigDecimal(10));
        //TODO   扔到MQ
        DessertKafkaObject topicMap = new DessertKafkaObject();
        topicMap.setDessert(de1);
        topicMap.setDessertLog(log1);
        //ProduceUtil.connectionKafka(KaflkaEnum.getKaflkaEnumByCode(vo.getCode()).getTopic(), UUID.randomUUID().toString(), JsonUtil.toJson(topicMap));
        Map<String,Object> res = ((KafkaProductServer)(context.getBean("kafkaProductServer"))).sndMesForTemplate("demoTopic", String.valueOf(System.currentTimeMillis()), JSONObject.fromObject(topicMap).toString(), "0", 1);
        System.out.println("测试结果如下：===============");
        String message = (String)res.get("message");
        String code = (String)res.get("code");
        
        System.out.println("code:"+code);
        System.out.println("message:"+message);
    }
}