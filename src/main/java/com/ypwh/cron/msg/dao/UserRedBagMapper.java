package com.ypwh.cron.msg.dao;

import com.ypwh.cron.msg.bean.UserDetailEntity;
import com.ypwh.cron.msg.bean.UserEntity;
import com.ypwh.cron.msg.bean.UserRedBagEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserRedBagMapper {



	/**
	 * 获取用户当前可用的红包
	 * @param userId
	 * @return
	 */
	public List<UserRedBagEntity> listUserRedBag(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("id") long id);

	public List<UserRedBagEntity> listUserRedBagGreaterDay(@Param("startTime") String startTime, @Param("endTime") String endTime ,@Param("id") long id);

	public List<UserRedBagEntity> listUserRedBagLessDay(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("id") long id);

	public List<UserEntity> listUser(@Param("userIds") List<Long> userIds);

	public List<UserDetailEntity> listUserDetail(@Param("userIds") List<Long> userIds);


}
