package com.ypwh.cron.msg.service.impl;


import com.ypwh.common.constant.AppProtocal;
import com.ypwh.cron.msg.bean.KaflkaEnum;
import com.ypwh.cron.msg.bean.MessageEnum;
import com.ypwh.cron.msg.bean.UserDetailEntity;
import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.cron.msg.service.UserRedBagService;
import com.ypwh.cron.msg.util.SMSRedisUtil;
import com.ypwh.framework.common.util.Resources;
import com.ypwh.framework.common.util.SpringContextUtils;
import com.ypwh.framework.common.util.StringUtil;
import com.ypwh.goods.service.api.GoodsService;
import com.ypwh.goods.service.entity.resp.GoodsList;
import com.ypwh.push.api.PushMessageService;
import com.ypwh.push.entity.PushEnvironment;
import com.ypwh.push.entity.jpush.JpushMessage;
import com.ypwh.sms.service.api.SmsService;
import com.ypwh.sms.service.entity.SmsMessage;
import com.ypwh.user.service.api.SystemMessageService;
import com.ypwh.user.service.entity.common.MessageClassifyType;
import com.ypwh.user.service.entity.req.UserMessage;
import com.ypwh.user.service.entity.res.SystemMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service(value = "comsumerService")
public class ComsumerServiceImpl implements ComsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ComsumerServiceImpl.class);



	@Override
	public void comsumerMsg(String topic, String str,String key) {
        logger.info("kafkaConsumer 数据处理=topic={},key={},value={}", topic, key, str);
        if (null == str || "".equals(str)) {
            return;
        }
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        wac.getBean(topic, ComsumerService.class).comsumerMsg(topic,str,key);
    }
    public static void main(String[] args) {
		int a=1;
		int b=0;
		//String s="{\"bizCode\":\"task:batch\",\"bizKey\":\"task:batch\",\"targetPlatform\":\"ALL\",\"targetEnvironment\":\"PRODUCT\",\"content\":\"亲，您的特权专享大额优惠券就要过期了，千万不要忘记哦~\",\"targetUrl\":\"sph://xinShangApp/startApp?target\\u003dmyCoupon\\u0026isNeedLogin\\u003dtrue\",\"registrationIds\":[\"1a0018970a864b2446d\",\"1104a89792a0b83ec5a\",\"120c83f76027c7ce26d\",\"100d8559096730b4254\",\"18171adc035608ff886\",\"13065ffa4e04fa3c256\"]}";
        int pointsDataLimit = 2;
        List mobs=new ArrayList();
        mobs.add(1);
        mobs.add(2);
        mobs.add(3);
        mobs.add(4);
        mobs.add(5);
        mobs.add(6);
        List newList=new ArrayList();
        for (int i=0;i<mobs.size();i++){
            newList.add(mobs.get(i));
            if(pointsDataLimit==newList.size() || i==mobs.size()-1){
                for(Object o : newList){
                    System.out.print(o + " -- ");
                }
                newList.clear();
            }
        }
    }
}
