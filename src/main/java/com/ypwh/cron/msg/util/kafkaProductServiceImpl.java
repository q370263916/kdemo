package com.ypwh.cron.msg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 
 * @author lipeng
 */
@Service
public class kafkaProductServiceImpl implements kafkaProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(kafkaProductServiceImpl.class);
	
	@Autowired
    private KafkaProductServer kafkaProducerServer;


	private static final String SNSTOPIC ="demoTopic";

	@Override
	public Map<String, Object> sndMesForTemplate(String valueString) {
		// TODO Auto-generated method stub
		return kafkaProducerServer.sndMesForTemplate(SNSTOPIC, String.valueOf(System.currentTimeMillis()), valueString, "0", 3);
	}
	
}
