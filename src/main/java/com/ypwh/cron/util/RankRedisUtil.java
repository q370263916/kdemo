package com.ypwh.cron.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ypwh.framework.common.util.Resources;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

/**
 * 
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年6月21日 下午4:05:16 
* 类说明
 */
public final class RankRedisUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(RankRedisUtil.class);   
    
    //Redis服务器IP
    private static String ADDR = Resources.getString("index.redis.ip");
    
    //Redis的端口号
    private static int PORT = Resources.getConfigAsInt("index.redis.port");
    
    //Redis服务器IP
    private static String PASSWORD = Resources.getString("index.redis.pswd");
    
    private static int TIMEOUT = Resources.getConfigAsInt("redis.timeout");
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = Boolean.valueOf(Resources.getString("redis.testOnBorrow"));
    
    private static int maxIdle = Resources.getConfigAsInt("redis.maxIdle");
    
    private static int maxTotal = Resources.getConfigAsInt("redis.maxActive");
    
    private static int maxWaitMillis = Resources.getConfigAsInt("redis.maxWait");
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setMaxWaitMillis(maxWaitMillis);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, PASSWORD);
        } catch (Exception e) {
        	logger.error("connect redis error ADDR="+ADDR+",PORT="+PORT, e);
        }
    }
    
    public static void putObjectForJson(String key, String jsonStr) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, jsonStr);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static void putObject(String key, Object obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, getValueMap(obj));
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static Long hlen(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hlen(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return "";
    }
    
    public static Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hkeys(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static List<String> hvals(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hvals(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static void hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, hash);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }


    public static Map<String, String> getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    public static void removeKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }


    public static void set(String key, String obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, obj);
            logger.info("*****************************************jedis执行了: "+jedis.get(key)+"*********************************");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static void setAndExpire(String key, String obj, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key, time, obj);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }


    public static void incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.incr(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static void incrby(String key, Long num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.incrBy(key, num);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static void decrby(String key, Long num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.decrBy(key, num);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static void decr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.decr(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }


    public static Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static String getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
            return "";
        } finally {
            returnResource(jedis);
        }
    }


    public static void push(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static void lrem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lrem(key, 1, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    public static List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.lrange(key, 0, -1);
            return list;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.lrange(key, start, end);
            return list;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    public static void add(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static void srem(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> set = jedis.smembers(key);
            return set;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static void zadd(String key, double score, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static void zmadd(String key, Map<String, Double> values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.zadd(key, values);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static Long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static Set<String> zcard(String key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    
    public static void zincrby(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
    }
    
    public static Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }
    

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("jedis 异常", e);
            return null;
        }
    }


    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }


    public static Map<String, String> getValueMap(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o.toString());
                }
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                logger.error("参数不合法", ex);
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                logger.error("不合法访问", ex);
                ex.printStackTrace();
            }
        }
        return map;
    }
    
    public static void setExpireTime(String key, int time){
    	 Jedis jedis = null;
         try {
             jedis = getJedis();
             jedis.expire(key, time);
         } catch (Exception e) {
             logger.error("jedis 异常", e);
         } finally {
             returnResource(jedis);
         }
    }
    
    public static long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            returnResource(jedis);
        }
        return 0;
    }
}