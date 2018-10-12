package com.ypwh.cron.msg.service;



import com.ypwh.cron.msg.bean.UserDetailEntity;
import com.ypwh.cron.msg.bean.UserEntity;
import com.ypwh.cron.msg.bean.UserRedBagEntity;
import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;

import java.util.List;
import java.util.Map;

public interface UserRedBagService {

     /**
      * 过去24小时 到账的紅包
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserRedBagEntity> listUserRedBag(UserRedBagEntity entity);

     /**
      * 总有效期大于3天，剩余有效期大于24小时，且小于48小时
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserRedBagEntity> listUserRedBagGreaterDay(UserRedBagEntity entity);

     /**
      * 总有效期小于等于3天，剩余有效期小于24小时
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserRedBagEntity> listUserRedBagLessDay(UserRedBagEntity entity);

     @DataSource(DBContextHolder.SITE_READ)
     List<UserEntity> listUser(List<Long> userIds);

     @DataSource(DBContextHolder.SITE_READ)
     List<UserDetailEntity> listUserDetail(List<Long> userIds);

}