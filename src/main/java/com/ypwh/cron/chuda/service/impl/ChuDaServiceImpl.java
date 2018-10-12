package com.ypwh.cron.chuda.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ypwh.cron.chuda.service.ChuDaService;
import com.ypwh.cron.msg.bean.KaflkaEnum;
import com.ypwh.cron.msg.util.KafkaProductServer;
import com.ypwh.cron.msg.util.SMSRedisUtil;
import com.ypwh.cron.reduction.dao.BonusMapper;
import com.ypwh.cron.reduction.dao.RedbagMapper;
import com.ypwh.cron.reduction.entity.UserBonusEntity;
import com.ypwh.cron.reduction.entity.UserRedBagEntity;
import com.ypwh.cron.user.service.CronUserService;
import com.ypwh.framework.common.util.JsonUtil;
import com.ypwh.framework.common.util.Resources;
import com.ypwh.framework.common.util.StringUtil;

public class ChuDaServiceImpl implements ChuDaService{
	private static final Logger logger = LoggerFactory.getLogger(ChuDaServiceImpl.class);
	@Resource(name = "redbagMapper")
	private RedbagMapper redbagMapper;
	
	@Resource(name = "bonusMapper")
	private BonusMapper bonusMapper;
	

	public static final  String weekExpirenumKey="task:chuda:weekExpirenum";
	public static final  String weekArrivenumKey="task:chuda:weekArrivenum";
	
	public static final  String usersendsKey="task:chuda:usersends";
	
	public static final  String usersendsDateKey="task:chuda:date";
	
	public static final  String usersendsStartDateKey="task:chuda:startdate";
	
	public static final  String userHavesendsKey="task:chuda:userhavesends";
	
	public static final  String userHavesendsIdKey="task:chuda:userhavesendsid";

	
	
	@Autowired
	CronUserService cronUserService;

	public int rows=50000;
	
	private  ExecutorService executorService = Executors.newCachedThreadPool();
	
	
	
	@Autowired
	private KafkaProductServer kafkaProductServer;
	
	private static boolean isSend="1".equals(Resources.getString("reduction.arriveandexpire.issend"));
	
	@Override
	public void  run(int typeId,int sendType) {//1红包  2优惠券    sendType 1触达 2过期 
		logger.info("typeId={}，sendType={}  isSend={}",typeId,sendType,isSend);
		// TODO Auto-generated method stub
		
		
		
		long maxId=this.getMaxId(typeId);
		
		long startId=this.getStartId(typeId,sendType)-1;
		logger.info("maxId={},startId={}",maxId,startId);
		if(maxId<=startId){
			return;
		}
		//查询红包list
		//获取缓存数据     1 今天发过的用户  2 一周内触达次数    3 一周内发送的次数
		 long dataNum=maxId-startId;
		 int threadNum=getThreadNum(dataNum);
		 
		 long dataNumPer=dataNum/threadNum;
		 
		 long threadStartId=startId;
		 long threadEndId=threadStartId+dataNumPer;
		 
		 logger.info("threadNum={}",threadNum);
		 CountDownLatch downEnd = new CountDownLatch(threadNum);
		 for (int i = 0; i < threadNum; i++) {
			 final  long threadStartIdV=threadStartId;
			 final long threadEndIdV=threadEndId;
			 Runnable runnable = () -> {
				  logger.info("thread  startId={} endId={}  sendType={}  typeId={} ",threadStartIdV,threadEndIdV,sendType,typeId);
					 if(typeId==1){
						 doRedBag(threadStartIdV,threadEndIdV,sendType,typeId);     // 创建一个新的线程  myThread1  此线程进入新建状态  
					}else if(typeId==2){
						doBonus(threadStartIdV,threadEndIdV,sendType,typeId);     // 创建一个新的线程  myThread1  此线程进入新建状态  
					}
					 downEnd.countDown();
			 };
			 
			 executorService.execute(runnable);
			 threadStartId=threadEndId;
			 threadEndId=threadStartId+dataNumPer;
			 if(threadEndId>=maxId){
				 threadEndId=maxId;
			 }           
	    }
		
		 try {
			  // Thread.sleep(5000);
			 downEnd.await();
	            // 当线程池中的所有任务执行完毕时,就会关闭线程池
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		 
	}
	public int getThreadNum(long dataNum){
		 int threadNum=1;
		 String threadNumStr=Resources.getString("reduction.arriveandexpire.threadnum");
		 if(StringUtil.isNotEmpty(threadNumStr)){
			 threadNum=StringUtil.convertInt(threadNumStr, 1);
		 }
		 if(dataNum<= 150000l){
			 threadNum=1;
		 }
		 return threadNum;
	}
	
	public long getMaxId(int typeId){
		if(typeId==1){
			return redbagMapper.getMaxId(ChudaUtil.getArriveEndTime());
		}else if(typeId==2){
			return bonusMapper.getMaxId(ChudaUtil.getArriveEndTime());
		}
		return  0;
		
	}
	public long getStartId(int typeId,int sendType){
		long startId=0;
		if(sendType==1){
			String format = ChudaUtil.getArriveTime();	
			if(typeId==1){
				startId=redbagMapper.getMinId(format);
			}else if(typeId==2){
				startId=bonusMapper.getMinId(format);
			}
		}else if(sendType==2){
			String format = ChudaUtil.getExpireTime();
			if(typeId==1){
				startId=redbagMapper.getMinId(format);
			}else if(typeId==2){
				startId=bonusMapper.getMinId(format);
			}
		}
		return startId;
	}

	
	
	public void doRedBag(final long startId,final long endId,int sendType,int typeId) {
		// TODO Auto-generated method stub
		long minId=startId;
		long maxId=endId;
		List<UserRedBagEntity>  list=null;
		while(maxId>minId){
			logger.info("doRedBag  minId={}  rows={}",minId,rows);
			list=redbagMapper.listByStartId(minId, rows);
			minId=minId+rows;
			if(list!=null&&list.size()>0){
				boolean checkResult=false;
				for(UserRedBagEntity tmp:list){
					if(sendType==1){
						checkResult=checkArrvie(tmp.getGetPlatform(),tmp.getState(),tmp.getEndTime(),typeId);
					}else{
						checkResult=checkExpire(tmp.getState(),tmp.getStartTime(),tmp.getEndTime(),typeId);
					}
					if(checkResult&&isPush(tmp.getUserId(),sendType)){
						addRedis(tmp.getUserId(),sendType,typeId);
					}
				}
			}
		}
	
	}
	
	public void doBonus(final long startId,final long endId,int sendType,int typeId) {
		// TODO Auto-generated method stub
		System.out.println("startId="+startId+"endId="+endId);
		long minId=startId;
		long maxId=endId;
		List<UserBonusEntity>  list=null;
		while(maxId>minId){
			list=bonusMapper.listByStartId(minId, rows);
			minId=minId+rows;
			if(list!=null&&list.size()>0){
				boolean checkResult=false;
				for(UserBonusEntity tmp:list){
					try{
						if(sendType==1){
							checkResult=checkArrvie(tmp.getGetPlatform(),tmp.getState(),tmp.getEndTime(),typeId);
						}else{
							checkResult=checkExpire(tmp.getState(),tmp.getStartTime(),tmp.getEndTime(),typeId);
						}
						
						if(checkResult&&isPush(tmp.getUserId(),sendType)){
							addRedis(tmp.getUserId(),sendType,typeId);
						}
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}
		}
	
	}
	public void addRedis(long userId,int sendType,int typeId){//当天用户数据  用户触达数    1触达  2过期
		long result=SMSRedisUtil.sadd(usersendsKey, userId+"");
		if(result>0){
			SMSRedisUtil.sadd(usersendsKey+":"+sendType+":"+typeId, userId+"");
			String key=weekArrivenumKey;
			if(sendType==2){
				key=weekExpirenumKey;
			}
			String userIdV=userId+"";
			int weekNum=0;
			if(SMSRedisUtil.hexists(key,userIdV)){

				String weekNumStr=SMSRedisUtil.hget(key,userIdV);//一周推送触达的次数
				
				weekNum=StringUtil.convertInt(weekNumStr, 0);
			}
			weekNum++;
			SMSRedisUtil.hset(key, userIdV,weekNum+"");
		}
		
		
	}
	public boolean  isPush(long userId,int sendType){//1 触达 2过期
           
            boolean isExist=SMSRedisUtil.sismembers(usersendsKey, userId+"");//当天是否发送
			if(!isExist){
				 String key=weekArrivenumKey;
		            if(sendType==2){
		            	 key=weekExpirenumKey;
		            }
				String weekArriveNumStr=SMSRedisUtil.hget(key,userId+"");//一周推送触达的次数
				int weekArriveNum=StringUtil.convertInt(weekArriveNumStr, 0);
				if(weekArriveNum<2){
					return true;
				}
			}
            
		return false;
		
	}
    public boolean  checkArrvie(int platForm,int state,Date endTime,int typeId){//1红包 2优惠券
    	//后台推送    未使用    未过有效期
    	try {
			if(5==platForm&&
					((typeId==1&&state==0)||(typeId==2&&state==1))&&
							endTime.getTime()>System.currentTimeMillis()
					){
				 return true;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    public boolean  checkExpire(int state,Date startTime,Date endTime,int typeId){
    	//后台推送    未使用    未过有效期
    	try {
    		if(!((typeId==1&&state==0)||(typeId==2&&state==1))){//不是未使用  返回 false
    			return false;
    		}
    		long startTimeV=startTime.getTime();
    		long endTimeV=endTime.getTime();
    		long nowV=System.currentTimeMillis();
    		long leftTime=endTimeV-nowV;
    		if(leftTime<=0) return false;//已过期  
    		
			if((endTimeV-startTimeV)>3*24*3600000){//大于3天
    			if(leftTime<=48*3600000&&leftTime>24*3600000){
        			//过期脱song
    				return true;
        		}
    		}else{//小于等于3天
    			if(leftTime<=24*3600000){
        			//过期脱song
    				return true;
        		}
    		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
  
    
    
    
    
    /*public void  productKafKa(int typeId,int sendType){
    	Set<String>  result= SMSRedisUtil.smembers(usersendsKey+":"+sendType+":"+typeId);
    	logger.info("   从redis取数据    key={}  value={} ",usersendsKey+":"+sendType+":"+typeId,result);
    	
    	 List<String> sendList=new ArrayList<String>(1000);; 
    	 int i=0;
    	 String topicName=KaflkaEnum.AREDTOPIC.getTopic();
    	 if(sendType==1){
    		 if(typeId==2){
    			 topicName=KaflkaEnum.ABONTOPIC.getTopic();
    		 }
    	 }else {
    		 if(typeId==1){
    			 topicName=KaflkaEnum.EREDTOPIC.getTopic();
    		 }else 	 if(typeId==2){
    			 topicName=KaflkaEnum.EBONTOPIC.getTopic();
    		 }
    	 }
    	 logger.info("  生产  topic={}  typeId={}  sendType={} ",topicName,typeId,sendType);
    	 String jpush=null;
    	 for(String tmp:result){
    		 i++;
    		 if(i>=1000){
    			 logger.info("    to kafka  value={}",JsonUtil.toJson(sendList));
    			 kafkaProductServer.sndMesForTemplate(
    					 topicName,
    						String.valueOf(System.currentTimeMillis()),
    						JsonUtil.toJson(sendList), "0", 1);
    			 sendList=null;
    			 sendList=new ArrayList<String>(1000);
    			 logger.info("   add to 1000  list  reset");
    			 
    		 }else{
    			 logger.info("触达项目需发送的用户={}",tmp);
    			 sendList.add(tmp);
    			 SMSRedisUtil.srem(usersendsKey+":"+sendType+":"+typeId,tmp);
    			 
    		 }
    		 
    	 }
    	 
    	 if(sendList!=null&&sendList.size()>0){
    		 kafkaProductServer.sndMesForTemplate(
    				 topicName,
						String.valueOf(System.currentTimeMillis()),
						JsonUtil.toJson(sendList), "0", 1);
    		 logger.info("    to kafka  value={}",JsonUtil.toJson(sendList));
			 sendList=null;
    	 }
    	
    }*/
    
    
    public void  productKafKa(int typeId,int sendType){    	 
    	 String topicName=KaflkaEnum.AREDTOPIC.getTopic();
    	 if(sendType==1){
    		 if(typeId==2){
    			 topicName=KaflkaEnum.ABONTOPIC.getTopic();
    		 }
    	 }else {
    		 if(typeId==1){
    			 topicName=KaflkaEnum.EREDTOPIC.getTopic();
    		 }else 	 if(typeId==2){
    			 topicName=KaflkaEnum.EBONTOPIC.getTopic();
    		 }
    	 }
    	 final String  redisKey=usersendsKey+":"+sendType+":"+typeId;
    	 final String  topicNameNew=topicName;
    	 long datanum=SMSRedisUtil.scard(redisKey);
    	 int threadNum=1;
    	 if(datanum>100000){
    		 threadNum=20;
    	 }else if(datanum>50000){
    		 threadNum=10;
    	 }else if(datanum>10000){
    		 threadNum=5;
    	 }
		 for (int i = 0; i < threadNum; i++) {
			 Runnable runnable = () -> {
				 pop(topicNameNew,redisKey);
			 }; 
			 executorService.execute(runnable);
	     }	
    }
    
    public  void  pop(String topicName,String redisKey){
    	 List<String>  sendList=null;
    	 String value=null;
    	 boolean flag=true;
    	 while(flag){
    		 sendList=new ArrayList<String>(1000);
	    	for(long i=0;i<=1000;i++){
	    		value=SMSRedisUtil.spop(redisKey);
	    		logger.info("pop value={}",value);
	    		if(StringUtil.isEmpty(value)){
	    			logger.info("break value={}",value);
	    			flag=false;
	    			break;
	    		}
	    		if(StringUtil.isNotEmpty(value)){
	    			sendList.add(value);
	    		}
	    	
	    		
	    	}
	    	if(sendList!=null&&sendList.size()>0){
	   		      kafkaProductServer.sndMesForTemplate(
	   				 topicName,
							String.valueOf(System.currentTimeMillis()),
							JsonUtil.toJson(sendList), "0", 1);
	   		 logger.info("    to kafka  value={}",JsonUtil.toJson(sendList));
				 sendList=null;
	   	   }
    	 }
    }
    
	@Override
	public void delRedis() {
		// TODO Auto-generated method stub
		String today=ChudaUtil.getNowDay();
		
		String redisDate=(String) SMSRedisUtil.get(usersendsDateKey);
		
		String redisStartDate=(String) SMSRedisUtil.get(usersendsStartDateKey);

		
		logger.info("当前时间={}，redis时间={}  redis开始时间={}",today,redisDate,redisStartDate);
		if(redisDate==null||!today.equals(redisDate)){//和缓存当前时间不同  按天
			SMSRedisUtil.del(usersendsKey);
			SMSRedisUtil.del(usersendsKey+":1:1");
			SMSRedisUtil.del(usersendsKey+":1:2");
			SMSRedisUtil.del(usersendsKey+":2:1");
			SMSRedisUtil.del(usersendsKey+":2:2");
			SMSRedisUtil.del(userHavesendsKey);
			SMSRedisUtil.del(userHavesendsIdKey);

			SMSRedisUtil.set(usersendsDateKey, today);
			
			
			logger.info("删除  rediskey  天 ");
			
			logger.info(" set  rediskey= {}, value={} ",usersendsDateKey,today);
		}
		
		if(redisDate==null||redisStartDate==null||ChudaUtil.getDays(redisStartDate, today)>=7){//按周
			SMSRedisUtil.del(weekExpirenumKey);
			SMSRedisUtil.del(weekArrivenumKey);
			SMSRedisUtil.set(usersendsStartDateKey, today);
			
			logger.info("删除  rediskey {},{} ",weekExpirenumKey,weekArrivenumKey);
			
			logger.info(" set  rediskey= {}, value={} ",usersendsStartDateKey,today);
		}
		
	}
	
	public long  haveSend(String userId,String pushId){//push
		 return SMSRedisUtil.sadd(userHavesendsKey, pushId);
	}
	public long  haveSendSys(String userId){
		 return SMSRedisUtil.sadd(userHavesendsIdKey, userId);
	}
	public boolean  judgeWorkTime(List<String> userIds,int sendType){
		 LocalDateTime nowTime = LocalDateTime.now();
	        int hour=nowTime.getHour();
	        String endHourStr=Resources.getString("reduction.arriveandexpire.endTime");
	        int endHour=StringUtil.convertInt(endHourStr, 0);
	       
	        if(hour<9||hour>=endHour){
	        	 logger.info(" kafka 非推送时间  ");
	            //**问题 剩余得明天也不发送
	            return false;
	        }
	        logger.info(" kafka 推送时间  ");
	        return true;
	}
	@Override
	public boolean isSend() {
		// TODO Auto-generated method stub
		
		return isSend;
	}
	
	
}
