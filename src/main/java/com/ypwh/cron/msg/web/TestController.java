package com.ypwh.cron.msg.web;



import com.ypwh.cron.msg.bean.Dessert;
import com.ypwh.cron.msg.bean.DessertKafkaObject;
import com.ypwh.cron.msg.bean.DessertLog;
import com.ypwh.cron.msg.bean.TaskActivityInvite;
import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.cron.msg.service.InviteEarnService;
import com.ypwh.cron.msg.service.UserRedBagService;
import com.ypwh.cron.msg.service.impl.AbonTopicServiceImpl;
import com.ypwh.cron.msg.util.KafkaProductServer;
import com.ypwh.framework.common.util.JsonUtil;
import com.ypwh.framework.common.util.SpringContextUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inviteEarn")
public class TestController {
	private Logger logger = Logger.getLogger(TestController.class);

	@Autowired
	private KafkaProductServer kafkaProducerServer;

	@Autowired
	private InviteEarnService inviteEarnService;

	@Autowired
	private UserRedBagService userRedBagService;
	//
	@RequestMapping(value = "/updateInviteTask", method = { RequestMethod.GET })
	@ResponseBody
	public String updateInviteTask(HttpServletRequest request) {
		String str="success";
	 try {
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:config/kafkaProducer.xml"});
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
		// Map<String,Object> res = ((KafkaProductServer)(context.getBean("kafkaProductServer"))).sndMesForTemplate("demoTopic", String.valueOf(System.currentTimeMillis()), JSONObject.fromObject(topicMap).toString(), "0", 3);
		 Map<String,Object> res = kafkaProducerServer.sndMesForTemplate("demoTopic", String.valueOf(System.currentTimeMillis()), JSONObject.fromObject(topicMap).toString(), "0", 3);
		 System.out.println("测试结果如下：===============");
		 String message = (String)res.get("message");
		 String code = (String)res.get("code");

		 System.out.println("code:"+code);
		 System.out.println("message:"+message);	 }catch (Exception e){
		 str="error";
		 logger.error("---------------updateInviteTask------error"+e.getMessage());
	 }
	 return str;
	}

	@RequestMapping(value = "/testDao", method = { RequestMethod.GET })
	@ResponseBody
	public String testDao(HttpServletRequest request){
		logger.info("-----------info--------");
		logger.debug("-------debug------------");
		logger.error("--------error-----------");
		Map m=new HashMap();
		//m.put("lotteryDrawId","8");
		m.put("userId","20518113");
		Map<String,Object> res = kafkaProducerServer.sndMesForTemplate("userTopic", String.valueOf(System.currentTimeMillis()), JsonUtil.toJson(20518113), "0", 3);

		//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		//wac.getBean("getFriTopic", ComsumerService.class).comsumerMsg("getFriTopic","{\"lotteryDrawId\":\"18\",\"userId\":\"21100770\"}",String.valueOf(System.currentTimeMillis()));
		String str="success";
//		TaskActivityInvite param=new TaskActivityInvite();
//		List<TaskActivityInvite> list= inviteEarnService.listInvite(param);
//		List<Long> l=new ArrayList<>();
//		l.add(10l);
//		l.add(9l);
//		List list2=userRedBagService.listUser(l);
//		JSONArray jsonArray=JSONArray.fromObject(list);
//		str=jsonArray.toString();
		return str;
	}

	

}
