package com.ypwh.cron.msg.dao;

import com.ypwh.cron.msg.bean.UserBonusEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserBonusMapper {
	//查询用户所有优惠券                        #############
	//过去24小时到的优惠券
    List<UserBonusEntity> listUserBonus(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("id") long id);

	List<UserBonusEntity> listUserBonusGreaterDay(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("id") long id);

	List<UserBonusEntity> listUserBonusLessDay( @Param("startTime") String startTime,@Param("endTime") String endTime,@Param("id") long id);



	List<UserBonusEntity> getExpiredBonusByUserId(@Param("userId") long userId, @Param("limit") long limit);
}