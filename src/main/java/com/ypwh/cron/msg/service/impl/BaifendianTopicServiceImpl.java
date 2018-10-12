package com.ypwh.cron.msg.service.impl;


import com.ypwh.cron.msg.service.ComsumerService;
import com.ypwh.framework.common.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service(value = "baifendianTopic")
public class BaifendianTopicServiceImpl extends BaseServiceImpl implements ComsumerService {
	private static final Logger logger = LoggerFactory.getLogger(BaifendianTopicServiceImpl.class);


	@Override
	public void comsumerMsg(String topic, String str,String key) {
		logger.info("-------------------come in----------"+topic+str);
        StringBuilder url = new StringBuilder();
        url.append("http://task.91sph.com/taskGoods/baifendianUpload?goodsId=").append(str+"");
        HttpClientUtil.get(url.toString(), null, 2);
        
	}


    
}
