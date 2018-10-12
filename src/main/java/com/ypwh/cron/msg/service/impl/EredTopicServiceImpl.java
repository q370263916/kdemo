package com.ypwh.cron.msg.service.impl;


import com.ypwh.common.constant.AppProtocal;
import com.ypwh.cron.chuda.service.ChuDaService;
import com.ypwh.cron.msg.bean.KaflkaEnum;
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

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service("exppRedTopicNew")
public class EredTopicServiceImpl extends BaseServiceImpl implements ComsumerService {
	private static final Logger logger = LoggerFactory.getLogger(EredTopicServiceImpl.class);

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
		logger.info("kafkaConsumer 消费过期红包   数据处理=topic={},key={},value={}",topic,key,str);

		if(null==str ||"".equals(str)){
			return;
		}
      
        List<String> userIds =this.getUserIds(str);
        if(null==userIds || 0==userIds.size()){
            return;
        }
        if(!chuDaService.judgeWorkTime(userIds,2)){
			return;
		}
        String jpush=null;

        List<String> pushIds=new ArrayList<String>(userIds.size());
        long result=0;
        long resultId=0;
        String  content=MessageEnum.getKaflkaEnumByCode(this.getRandom(10,12)).getDesc();

        for (String userId:userIds){
        	/*resultId=chuDaService.haveSendSys(userId);
        	if(resultId>0){
                this.sendTopic(StringUtil.convertLong(userId, 0),content,4,null);
        	}*/
            jpush=cronUserService.getJpushId(userId);
			 if(StringUtil.isNotEmpty(jpush)){
				 
				 result=chuDaService.haveSend(userId, jpush);
				 if(result>0){
					 pushIds.add(jpush);
				 }
			 }
        }
        if(pushIds!=null&&pushIds.size()>0){
           this.sentPush(pushIds,content,4);
        }
	}

	/**
	 * type 1 优惠券送达 2 红包送达 3.优惠券到期 4. 红包到期 5.注册抽奖开奖 6，中奖 7，激活未注册 8 获奖码 9，助力获奖码
	 * @param userId
	 * @param content
	 * @param type
	 */

	public void sendTopic(long userId,  String content, int type,String lotteryDrawId  )
	{
		if(userId<=0){
			return;
		}
	    try {
            String title = "";
            String targetUrl = "";
             if(userId<=0){
            	 return;
             }
            SystemMessage message = new SystemMessage();
            message.setBizId(userId);
            if (4 == type) {
                title = "您的红包即将到期";
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=myRedPackage&isNeedLogin=true");
                message.setBizType(9010);
            }
            message.setTitle(title);
            message.setContent(content);
            message.setUserId(userId);
            message.setCreateTime(new Date());
            message.setMsgType(MessageClassifyType.SYS.getCode());
            message.setThumbnail("");
            message.setLinkUrl(targetUrl);
            UserMessage userMessage = new UserMessage();
            userMessage.setTargetUserId(userId);
            userMessage.setSysMessage(message);
            //logger.info("触达项目   过期红包   推送的用户系统消息   content={} type={}  userId={}",content,type,userId);
            //systemMessageService.sendUserMessage(userMessage);
        }catch (Exception e){
	        e.printStackTrace();
	        logger.error("-------sendTopic-------"+e.getMessage());
        }
	}

    public void sendMsgBatch(List<String> list,  String content )
    {
        if(null==list || list.size()<=0){
            return;
        }
        try {
            List<SystemMessage> newList=new ArrayList<SystemMessage>();
            for (String userId: list) {
            String title = "";
            String targetUrl = "";
            SystemMessage message = new SystemMessage();
            message.setBizId(Long.valueOf(userId));
            title = "您的红包即将到期";
            targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=myRedPackage&isNeedLogin=true");
            message.setBizType(9010);
            message.setTitle(title);
            message.setContent(content);
            message.setUserId(Long.valueOf(userId));
            message.setCreateTime(new Date());
            message.setMsgType(MessageClassifyType.SYS.getCode());
            message.setThumbnail("");
            message.setLinkUrl(targetUrl);
            newList.add(message);
            }
            //logger.info("触达项目   过期红包   推送的用户系统消息   content={} type={}  userId={}",content,type,userId);
            //systemMessageService.sendUserMessage(userMessage);
            systemMessageService.sendBatchSysMessage(newList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("-------sendTopic-------"+e.getMessage());
        }
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
            if (4 == type) {
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=myRedPackage&isNeedLogin=true");
                pushMessage.setBizCode("task:batch:expire");
                pushMessage.setBizKey("task:batch:expire");
            }
            pushMessage.setContent(content);
            pushMessage.setTargetUrl(targetUrl);
            pushMessage.setRegistrationIds(jpushId);
            if ("1".equals(Resources.getString("push.test"))) {
                pushMessage.setTargetEnvironment(PushEnvironment.TEST);
            }
            logger.info("触达项目   过期红包   推送的用户jpush content={} type={}  jpushId={}",content,type,jpushId);
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
     * @param type 1 优惠券到期  2 红包到期
     * @param mobs
     */
	public void sentMsg(String content,int type,List<String> mobs){
        if(null==mobs ||mobs.size()==0 ){
            return;
        }
        StringBuffer sb=new StringBuffer();
        for (String str:mobs){
            sb.append(str);
            sb.append(",");
        }
        String s=sb.toString();
        String ms=s.substring(0,s.length()-1);
        SmsMessage msMessage = new SmsMessage();
        msMessage.setMobiles(ms);
        msMessage.setIs_template(false);
        if(1==type) {
            msMessage.setBiz_key("task:bounsDQ:");
            msMessage.setBiz_code("task:bounsDQ");
        }else if(2==type){
            msMessage.setBiz_key("task:redbagDQ");
            msMessage.setBiz_code("task:redbagDQ");
        }
        msMessage.setSource(99);//批量发短信
        msMessage.setContent(content);
        smsService.sendTxtMessage(msMessage);
    }

	public  int getRandom(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;

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
