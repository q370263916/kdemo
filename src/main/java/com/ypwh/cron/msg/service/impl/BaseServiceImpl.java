package com.ypwh.cron.msg.service.impl;

import java.util.List;
import java.util.Random;

import net.sf.json.JSONArray;

public class BaseServiceImpl {
   public List<String> getUserIds(String str){
		if(null==str ||"".equals(str)){
			return  null;
		}  

       JSONArray jsonArray = JSONArray.fromObject(str);
       List<String> userIds=null;
	try {
		userIds = JSONArray.toList(jsonArray, String.class);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       return userIds;
   }
   public  int getRandom(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;

	}
   
   
}
