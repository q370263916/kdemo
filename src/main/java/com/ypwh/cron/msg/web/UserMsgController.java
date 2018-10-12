package com.ypwh.cron.msg.web;



import com.ypwh.cron.msg.bean.UserBonusEntity;
import com.ypwh.cron.msg.bean.UserRedBagEntity;
import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.cron.msg.service.UserBonusService;
import com.ypwh.cron.msg.service.UserRedBagService;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/userBonus")
public class UserMsgController {
	private Logger logger = Logger.getLogger(UserMsgController.class);


	@Autowired
	private UserBonusService userBonusService;

	@Autowired
	private UserRedBagService userRedBagService;



	@RequestMapping(value = "/doArrive", method = { RequestMethod.GET })
	@ResponseBody
	public String doArrive(HttpServletRequest request) {
		String str="success";
		UserBonusEntity param=new UserBonusEntity();
		UserRedBagEntity ur=new UserRedBagEntity();
		try {
			logger.info("----kafkaProductServer----userBonus/doArrive----1111111-----");
			userBonusService.doArrive();
			userBonusService.doExpire();
		}catch (Exception e){
			e.printStackTrace();
			str=e.getMessage();
			logger.error(e.getMessage());
		}
		//List<UserBonusEntity> list=	userBonusService.listUserBonus(param);
		//List<UserRedBagEntity> list2=	userRedBagService.listUserRedBag(ur);
		//JSONArray jsonArray=JSONArray.fromObject(list);
		//JSONArray jsonArray2=JSONArray.fromObject(list2);

		//str=jsonArray.toString();
	//	logger.info("--------"+jsonArray2.toString()+"---stt-"+str);
		return str;
	}

	@RequestMapping(value = "/doExpire", method = { RequestMethod.GET })
	@ResponseBody
	public String doExpire(HttpServletRequest request) {
		String str="success";
		try{
			//userBonusService.doExpire();
		}catch (Exception e){
			e.printStackTrace();
			str=e.getMessage();
			logger.error(e.getMessage());
		}
		return str;
	}


	

}
