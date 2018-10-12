package com.ypwh.cron.reduction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ypwh.cron.reduction.entity.UserBonusEntity;

public interface BonusMapper {
	public long getMaxId(@Param("getTime") String getTime);
	public long getMinId(@Param("getTime") String getTime);
	
	public List<UserBonusEntity>  listByStartId(@Param("startId") long startId,@Param("rows") int rows);

}
