package com.ypwh.cron.user.dao;

import java.util.List;

import com.ypwh.cron.msg.bean.UserEntity;
import com.ypwh.cron.user.entity.UserLoginLogEntity;
import org.apache.ibatis.annotations.Param;

import com.ypwh.cron.user.entity.UserDetailEntity;

public interface UserMapper {
	
	public List<UserDetailEntity> listUserDetail(@Param("userId") long userId,@Param("rows") int rows);
	/**
	 * 更新上次登录时间
	 * @param userId
	 * @return
	 */
	public int updateLastLogin(@Param("userId") long userId,@Param("onlyRegister") int onlyRegister);

	/**
	 * 记录登录时间
	 * @param userId
	 */
	public void recordUserLoginTime(UserLoginLogEntity log);


	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 */
	public UserEntity findById(@Param("userId") long id);

}
