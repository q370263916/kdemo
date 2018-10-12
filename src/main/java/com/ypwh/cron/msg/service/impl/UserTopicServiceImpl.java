package com.ypwh.cron.msg.service.impl;

import com.xinshang.api.user.service.entity.res.MicroUser;
import com.ypwh.common.constant.CacheKey;
import com.ypwh.cron.msg.bean.UserEntity;
import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.cron.msg.util.RedisUtil;
import com.ypwh.cron.user.dao.UserMapper;
import com.ypwh.cron.user.entity.UserLoginLogEntity;
import com.ypwh.framework.common.util.StringUtil;
import com.ypwh.framework.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "userTopic")
public class UserTopicServiceImpl implements ComsumerService {
    private static final Logger logger = LoggerFactory.getLogger(AbonTopicServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    @Override
    public void comsumerMsg(String topic, String str, String key) {
        logger.info(" 更新用户登录时间--------------comsumerMsg=topic={},key={},value={}",topic,key,str);
         if(StringUtil.isEmpty(str)){
             return;
         }
         this.updateUserLastLoginTime(str);

    }

    public void updateUserLastLoginTime(String str) {
        UserEntity entity=userMapper.findById(Long.valueOf(str));
        if(entity != null) {
            Date lastLogin = entity.getLastLogin();
            if(entity.getLastLogin() == null || TimeUtil.getDate(TimeUtil.getTime(lastLogin, TimeUtil.DATE_FORMAT)).before(TimeUtil.getDate(TimeUtil.getTime(new Date(), TimeUtil.DATE_FORMAT)))) {
                this.updateLastLogin(Long.valueOf(str),0);
                UserLoginLogEntity log = new UserLoginLogEntity();
                log.setUserId(Long.valueOf(str));
                userMapper.recordUserLoginTime(log);            }
        }
    }

    public int updateLastLogin(long userId, int onlyRegister) {
        String key = CacheKey.generateKey("mocrouser", "lastlogin", userId);
        RedisUtil.set(key, System.currentTimeMillis() + "");
        return userMapper.updateLastLogin(userId, onlyRegister);
    }
}
