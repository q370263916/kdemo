package com.ypwh.cron.msg.util;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

/**
 * kafkaProducer监听器，在producer配置文件中开启
 * @author wangb
 *
 */
@SuppressWarnings("rawtypes")
public class KafkaProducerListener implements ProducerListener{
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerListener.class);
    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
            Object value, RecordMetadata recordMetadata) {
    	SMSRedisUtil.incrby("kafkaProducerNum", 1l);
        LOG.info("kafka发送数据成功:topic={},partition={},key={},value={},RecordMetadata={},num={}",topic,partition,key,value,recordMetadata,SMSRedisUtil.get("kafkaProducerNum"));
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key,
            Object value, Exception exception) {
    	LOG.info("kafka发送数据错误:topic={},partition={},key={},value={},exception={}",topic,partition,key,value,exception);
        exception.printStackTrace();
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        LOG.info("///kafkaProducer监听器启动///");
        return true;
    }

}