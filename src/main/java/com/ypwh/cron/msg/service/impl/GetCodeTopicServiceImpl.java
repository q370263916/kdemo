package com.ypwh.cron.msg.service.impl;


import com.ypwh.common.constant.AppProtocal;
import com.ypwh.cron.msg.bean.KaflkaEnum;
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
import net.sf.json.JSONObject;
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


@Service("getCodeRedTopic")
public class GetCodeTopicServiceImpl implements ComsumerService {
	private static final Logger logger = LoggerFactory.getLogger(GetCodeTopicServiceImpl.class);
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



	@Override
	public void comsumerMsg(String topic, String str,String key) {
		logger.info("kafkaConsumer 数据处理=topic={},key={},value={}",topic,key,str);
		if(null==str ||"".equals(str)){
			return;
		}
        if(str.indexOf("userId")>0 && str.indexOf("lotteryDrawId")>0){
            String tkey="getCodeRedTopic:key";
            String userIdsArr= (String) SMSRedisUtil.get(tkey);
            if(key.equals(userIdsArr)){
                return;
            }
            JSONObject obj=JSONObject.fromObject(str);
            String userId=obj.getString("userId");
            String lotteryDrawId=obj.getString("lotteryDrawId");
            String content="有好友注册了，您参加的0元抽奖活动获得了一个抽奖码，快来助力好友，让好友获得更多抽奖机会，还会奖励您红包呦！";
            List<Long> list=new ArrayList<Long>();
            list.add(Long.valueOf(userId));
            List<UserDetailEntity> ulist=userRedBagService.listUserDetail(list);
            List<String> pushId =ulist.stream().map(x -> x.getJpushId()).distinct().collect(Collectors.toList());
            this.sentOnePush(pushId,content,8,lotteryDrawId);
            this.sendTopic(Long.valueOf(userId),content,8,lotteryDrawId);
            SMSRedisUtil.setAndExpire(tkey,key,7*24 * 60 * 60);
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
	    logger.info("------------sendTopic------------入参userId"+userId+"--type"+type+"--content"+content);
	    try {
            String title = "";
            String targetUrl = "";

            SystemMessage message = new SystemMessage();
            message.setBizId(userId);
            if (8 == type) {
                title = "获得抽奖码";
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=webViewClient&url=https://m.91xinshang.com/activity/newUserLottery/friendList.html?lotteryDrawId=" + lotteryDrawId+"&title=%e6%88%91%e7%9a%84%e5%a5%bd%e5%8f%8b");
                message.setBizType(1124);
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
//		PushMessage pushMessage=new PushMessage();
//		pushMessage.setContent(content);
//		pushMessage.setTargetUrl(targetUrl);
//		userMessage.setPushMessage(pushMessage);
            systemMessageService.sendUserMessage(userMessage);
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

            if (1 == type) {
                targetUrl = StringUtil.formatTemplate(AppProtocal.USER_BONUS_PAGE);
                pushMessage.setBizCode("task:batch:arrive");
                pushMessage.setBizKey("task:batch:arrive");

            }
            if (2 == type) {
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=myRedPackage&isNeedLogin=true");
                pushMessage.setBizCode("task:batch:arrive");
                pushMessage.setBizKey("task:batch:arrive");

            }
            if (3 == type) {
                targetUrl = StringUtil.formatTemplate(AppProtocal.USER_BONUS_PAGE);
                pushMessage.setBizCode("task:batch:expire");
                pushMessage.setBizKey("task:batch:expire");
            }
            if (4 == type) {
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=myRedPackage&isNeedLogin=true");
                pushMessage.setBizCode("task:batch:expire");
                pushMessage.setBizKey("task:batch:expire");
            }
            pushMessage.setContent(content);
            pushMessage.setTargetUrl(targetUrl);
            pushMessage.setRegistrationIds(jpushId);
            logger.info("---------------push--------" + Resources.getString("push.test"));
            if ("1".equals(Resources.getString("push.test"))) {
                pushMessage.setTargetEnvironment(PushEnvironment.TEST);
            }
//		List<String> registrationIds = Lists.newArrayList();
//		registrationIds.add("1a0018970a864b2446d");
//		registrationIds.add("1111111ewa864b2446d");
//		pushMessage.setRegistrationIds(registrationIds);
            appPushMessageService.pushMessage(pushMessage);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("------sentPush()-----"+e.getMessage());

        }
    }

    /**
     *
     * @param jpushId
     * @param content
     * @param type  5 开奖 6 中奖 7激活 8邀请码 9助力抽奖码
     */
    public void sentOnePush(List<String> jpushId,  String content, int type,String lotteryDrawId  ){
        try {
            String title = "";
            JpushMessage pushMessage = new JpushMessage();
            String targetUrl = "";
            if (8 == type) {
                targetUrl = StringUtil.formatTemplate("sph://xinShangApp/startApp?target=webViewClient&url=https://m.91xinshang.com/activity/newUserLottery/friendList.html?lotteryDrawId=" + lotteryDrawId+"&title=%e6%88%91%e7%9a%84%e5%a5%bd%e5%8f%8b");
                pushMessage.setBizCode("task:zccj:yqcode");
                pushMessage.setBizKey("task:zccj:yqcode");
            }
            pushMessage.setContent(content);
            pushMessage.setTargetUrl(targetUrl);
            pushMessage.setRegistrationIds(jpushId);
            logger.info("---------------push--------" + Resources.getString("push.test"));
            if ("1".equals(Resources.getString("push.test"))) {
                pushMessage.setTargetEnvironment(PushEnvironment.TEST);
            }
//		List<String> registrationIds = Lists.newArrayList();
//		registrationIds.add("1a0018970a864b2446d");
//		registrationIds.add("1111111ewa864b2446d");
//		pushMessage.setRegistrationIds(registrationIds);
            appPushMessageService.pushMessage(pushMessage);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("------------------------sentOnePush-"+e.getMessage());
        }
    }


    /**
	 * 发短信
	 * @param userId
	 * @param content
	 * @param type 1 优惠券到期  2 红包到期
	 */
	public void sentMessage(String content,int type,List<String> mobs){
		if(null==mobs ||mobs.size()==0 ){ //每次发送1000个
			return;
		}
        int pointsDataLimit = 1000;
		List<String> newList=new ArrayList<String>();
		for (int i=0;i<mobs.size();i++){
            newList.add(mobs.get(i));
            if(pointsDataLimit==newList.size() || i==mobs.size()-1){
               this.sentMsg(content,type,newList);
               newList.clear();
            }
        }

	}

    /**
     *
     * @param content
     * @param type
     * @param mobs
     */
    public void bathPush(String content,int type,List<String> pushIds){
        logger.info("------bathPush---------"+pushIds);
        if(null==pushIds ||pushIds.size()==0 ){ //每次发送1000个
            return;
        }
        int pointsDataLimit = 1000;
        List<String> newList=new ArrayList<String>();
        for (int i=0;i<pushIds.size();i++){
            newList.add(pushIds.get(i));
            if(pointsDataLimit==newList.size() || i==pushIds.size()-1){
                this.sentPush(newList,content,type);
                newList.clear();
            }
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
