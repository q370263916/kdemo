package com.ypwh.cron.msg.service.impl;


import com.ypwh.cron.msg.bean.TaskActivityInvite;
import com.ypwh.cron.msg.dao.TaskActivityInviteMapper;
import com.ypwh.cron.msg.service.InviteEarnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteEarnServiceServiceImpl implements InviteEarnService {
	private static final Logger logger = LoggerFactory.getLogger(InviteEarnServiceServiceImpl.class);
	@Autowired
	private TaskActivityInviteMapper taskActivityInviteMapper;






	@Override
	public List<TaskActivityInvite> listInvite(TaskActivityInvite taskActivityInvite) {
		return taskActivityInviteMapper.listInvite(taskActivityInvite);
	}
}
