package com.ypwh.cron.msg.service.impl;


import com.ypwh.common.constant.AppProtocal;
import com.ypwh.cron.chuda.service.ChuDaService;
import com.ypwh.cron.msg.bean.MessageEnum;
import com.ypwh.cron.msg.bean.UserDetailEntity;
import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.cron.msg.service.UserRedBagService;
import com.ypwh.cron.msg.util.SMSRedisUtil;
import com.ypwh.cron.user.service.CronUserService;
import com.ypwh.framework.common.util.Resources;
import com.ypwh.framework.common.util.SpringContextUtils;
import com.ypwh.framework.common.util.StringUtil;
import com.ypwh.goods.service.api.GoodsService;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service(value = "arriveBonTopicNew")
public class AbonTopicServiceImpl extends BaseServiceImpl implements ComsumerService {
	private static final Logger logger = LoggerFactory.getLogger(AbonTopicServiceImpl.class);
    @Resource(name = "appSystemMessageService")
    private SystemMessageService systemMessageService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "pushMessageService")
    private com.ypwh.push.api.PushMessageService  appPushMessageService;

    @Resource(name="goodsService")
    private GoodsService goodsService;

    @Resource(name="userRedBagService")
    private UserRedBagService userRedBagService;
    
    @Autowired
	CronUserService cronUserService;
    
    @Resource(name="chuDaService")
	ChuDaService chuDaService;

	@Override
	public void comsumerMsg(String topic, String str,String key) {
		logger.info("kafkaConsumer 消费触达优惠券  数据处理=topic={},key={},value={}",topic,key,str);
		
        String  content= MessageEnum.getKaflkaEnumByCode(this.getRandom(1,3)).getDesc();
        List<String> userIds =this.getUserIds(str);
        if(null==userIds || 0==userIds.size()){
            return;
        }
        if(!chuDaService.judgeWorkTime(userIds,1)){
			return;
		}
        this.bathPush(content,1,userIds);
        
	}
    /**
     *
     * @param jpushId
     * @param content
     * @param type 1 优惠券送达 2 红包送达 3.优惠券到期 4. 红包到期
     */
    public void sentPush(List<String> jpushId,  String content, int type  ){
        try {
            String title = "";
            JpushMessage pushMessage = new JpushMessage();
            String targetUrl = "";

            if (1 == type) {
                targetUrl = StringUtil.formatTemplate(AppProtocal.USER_BONUS_PAGE);
                pushMessage.setBizCode("task:batch:arrive");
                pushMessage.setBizKey("task:batch:arrive");

            }
            pushMessage.setContent(content);
            pushMessage.setTargetUrl(targetUrl);
            pushMessage.setRegistrationIds(jpushId);
            if ("1".equals(Resources.getString("push.test"))) {
                pushMessage.setTargetEnvironment(PushEnvironment.TEST);
            }
            logger.info("触达项目   触达优惠券   推送的用户jpush content={} type={}  jpushId={}",content,type,jpushId);
            if(chuDaService.isSend()){
                appPushMessageService.pushMessage(pushMessage);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    /**
     *
     * @param content
     * @param type
     * @param mobs
     */
    public void bathPush(String content,int type,List<String> userIds){
        if(null==userIds ||userIds.size()==0 ){ //每次发送1000个
            return;
        }
        String jpush=null;
        List<String> pushIds=new ArrayList<String>(userIds.size());
        long result=0;
        for(String tmp:userIds){
        	
        	jpush=cronUserService.getJpushId(tmp);
			 if(StringUtil.isNotEmpty(jpush)){
				 result=chuDaService.haveSend(tmp, jpush);
				 if(result>0){
					 pushIds.add(jpush);
				 }
			 }
        }
        if(pushIds!=null&&pushIds.size()>0){
        this.sentPush(pushIds,content,type);
        }

    }

	

    
}
