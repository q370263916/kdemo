package com.ypwh.cron.msg.service;


import com.ypwh.cron.msg.bean.TaskActivityInvite;
import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;


import java.util.List;

public interface InviteEarnService {
     @DataSource(DBContextHolder.SITE_READ)
     List<TaskActivityInvite> listInvite(TaskActivityInvite taskActivityInvite);

}