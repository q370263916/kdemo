package com.ypwh.cron.msg.util;

import com.ypwh.cron.msg.service.ComsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

import javax.annotation.Resource;

public class KafkaConsumerServer implements AcknowledgingMessageListener<String, String> {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerServer.class);

	@Resource(name = "comsumerService")
	private ComsumerService comsumerService;



//	/**
//	 * 监听器自动执行该方法
//	 *     消费消息
//	 *     自动提交offset
//	 *     执行业务代码
//	 *     （high level api 不提供offset管理，不能指定offset进行消费）
//	 */
//	@Override
//	public void onMessage(ConsumerRecord<String, String> record) {
//		LOG.info("=============kafkaConsumer开始消费=============");
//		String topic = record.topic();
//		String key = record.key();
//		String value = record.value();
//		long offset = record.offset();
//		int partition = record.partition();
//		LOG.info("-------------topic:"+topic);
//		LOG.info("-------------value:"+value);
//		LOG.info("-------------key:"+key);
//		LOG.info("-------------offset:"+offset);
//		LOG.info("-------------partition:"+partition);
//		comsumerService.ComsumerMsg(topic,value,key);
//		System.out.println(value);
//		LOG.info("~~~~~~~~~~~~~kafkaConsumer消费结束~~~~~~~~~~~~~");
//	}

	@Override
	public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
		
		
		String topic = record.topic();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		int partition = record.partition();
		
		SMSRedisUtil.incrby("kafkaConsumerNum", 1l);
		//LOG.info("kafkaConsumer开始消费  topic={},partition={},key={},value={},offset={},num={}",topic,partition,key,value,offset,SMSRedisUtil.get("kafkaConsumerNum"));

		comsumerService.comsumerMsg(topic,value,key);
		
		try {

			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acknowledgment.acknowledge();// 提交offset
		LOG.info("kafkaConsumer消费结束  topic={},partition={},key={},value={},offset={},num={}",topic,partition,key,value,offset,SMSRedisUtil.get("kafkaConsumerNum"));
	}
}