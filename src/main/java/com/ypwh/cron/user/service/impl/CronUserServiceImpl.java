package com.ypwh.cron.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ypwh.cron.msg.util.SMSRedisUtil;
import com.ypwh.cron.user.dao.UserMapper;
import com.ypwh.cron.user.entity.UserDetailEntity;
import com.ypwh.cron.user.service.CronUserService;
import com.ypwh.framework.common.util.StringUtil;

@Service
public class CronUserServiceImpl  implements  CronUserService{
	@Resource(name = "userMapper")
	private UserMapper userMapper;
	public static final int rows=10000;
	
	public static final String USER_ALL_JPUSHS="task:user:jpushid";
	public  void initUser(){
		long startUserId=0;
		List<UserDetailEntity>   detailList=null;
		while(true){
			detailList=userMapper.listUserDetail(startUserId, rows);
			if(detailList!=null&&detailList.size()>0){
				setRedis(detailList);
				startUserId=detailList.get(detailList.size()-1).getUserId();
			}
			if(detailList==null||detailList.size()<rows){
				break;
			}
		}
	}
	public void  setRedis(List<UserDetailEntity>   detailList){
		if(detailList!=null&&detailList.size()>0){
			Map<String,String> map=new HashMap<String,String>(detailList.size());
			for(UserDetailEntity tmp:detailList){
				if(StringUtil.isNotEmpty(tmp.getJpushId())){
					map.put(tmp.getUserId()+"", tmp.getJpushId());
				}
			}
			if(map!=null&&map.size()>0){
				SMSRedisUtil.hmset(USER_ALL_JPUSHS, map);
			}
			map=null;
		}
	}
	public String getJpushId(String userId){
		return SMSRedisUtil.hget(USER_ALL_JPUSHS, userId);
	}
}
