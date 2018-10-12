package com.ypwh.cron.msg.service;



import com.ypwh.cron.msg.bean.UserBonusEntity;
import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;

import java.util.List;

public interface UserBonusService {

     /**
      * 过去24小时 到账的优惠券
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserBonusEntity> listUserBonus(UserBonusEntity entity);

     /**
      * 总有效期大于3天，剩余有效期大于24小时，且小于48小时
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserBonusEntity> listUserBonusGreaterDay(UserBonusEntity entity);

     /**
      * 总有效期小于等于3天，剩余有效期小于24小时
      * @param entity
      * @return
      */
     @DataSource(DBContextHolder.SITE_READ)
     List<UserBonusEntity> listUserBonusLessDay(UserBonusEntity entity);

     /**
      * 处理送达
      */
     void doArrive();


     /**
      * 处理到期
      */
     void doExpire();

}