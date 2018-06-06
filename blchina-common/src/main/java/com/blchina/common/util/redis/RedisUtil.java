package com.blchina.common.util.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtil {
    private Logger log = Logger.getLogger(RedisUtil.class);
    private String ip;
    private String password;
    private int port;
    private JedisPoolConfig jedisPoolConfig;
    private JedisPool jedisPool;

    public void init() {
        if (jedisPoolConfig == null || ip == null || "".equals(ip.trim()) || port == 0) {
            throw new IllegalStateException("need jedisPoolConfig, ip, port");
        }
        if(password != null&& !"".equals(password)){
           jedisPool = new JedisPool(jedisPoolConfig, ip, port,
        		 Protocol.DEFAULT_TIMEOUT,  password);
        }else{
           jedisPool = new JedisPool(jedisPoolConfig, ip, port);
        }
        
        if (jedisPool == null) throw new RuntimeException("redis初始化失败......");
        log.info("jedis init. ******ip=" + ip + ":port=" + port);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.keys(pattern);
            }
        } catch (Exception e) {
            log.error("redis keys 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public long expire(String key, int seconds) {
        Jedis jedis = null;
        long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            log.error("redis expire 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long del(String... keys) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.del(keys);
            }
        } catch (Exception e) {
            log.error("redis lpop 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 从头取
     */
    public String lpop(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.lpop(key);
            }
        } catch (Exception e) {
            log.error("redis lpop 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 插入头
     */
    public boolean lpush(String key, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = false;
            } else {
                jedis.lpush(key, value);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis lpush 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 从尾取
     */
    public String rpop(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.rpop(key);
            }
        } catch (Exception e) {
            log.error("redis rpop 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 插入尾
     */
    public boolean rpush(String key, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = false;
            } else {
                jedis.rpush(key, value);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis rpush 异常..key=" + key + ",value=" + value);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long llen(String key) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.llen(key);
            }
        } catch (Exception e) {
            log.error("redis llen 异常....");
            result = -1L;
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.get(key);
            }
        } catch (Exception e) {
            log.error("redis get 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public boolean set(String key, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = false;
            } else {
                jedis.set(key, value);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis set 异常...." + e.getMessage());
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            return getJedis().setex(key, seconds, value);
        } catch (Exception e) {
            log.error("redis set 异常...." + e.getMessage());
            e.printStackTrace();
        } finally {
            returnJedis(jedis);
        }
        return null;
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hget(key, field);
            }
        } catch (Exception e) {
            log.error("redis hget 异常...." + e.getMessage());
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = -1L;
            } else {
                result = jedis.hset(key, field, value);
            }
        } catch (Exception e) {
            log.error("redis hset 异常...." + e.getMessage());
            result = -1L;
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long hdel(String key, String field) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = -1L;
            } else {
                result = jedis.hdel(key, field);
            }
        } catch (Exception e) {
            log.error("redis hset 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.exists(key);
            }
        } catch (Exception e) {
            log.error("redis exists 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hexists(key, field);
            }
        } catch (Exception e) {
            log.error("redis hset 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long hlen(String key) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hlen(key);
            }
        } catch (Exception e) {
            log.error("redis hlen 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hmset(key, map);
            }
        } catch (Exception e) {
            log.error("redis hlen 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map<String, String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hgetAll(key);
            }
        } catch (Exception e) {
            log.error("redis hgetall 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result == null ? new HashMap<String, String>() : result;
    }

    public long hincrBy(String key, String field, long value) {
        Jedis jedis = null;
        long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hincrBy(key, field, value);
            }
        } catch (Exception e) {
            log.error("redis hincrBy 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public List<String> hvals(String key) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.hvals(key);
            }
        } catch (Exception e) {
            log.error("redis hvals 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long incr(String key) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.incr(key);
            }
        } catch (Exception e) {
            log.error("redis incr 异常....");
            result = -1L;
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long incrBy(String key, long integer) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.incrBy(key, integer);
            }
        } catch (Exception e) {
            log.error("redis incr 异常....");
            result = -1L;
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public Long ttl(String key) {
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.ttl(key);
            }
        } catch (Exception e) {
            log.error("redis ttl 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public boolean decr(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = false;
            } else {
                jedis.decr(key);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis decr 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public boolean decrBy(String key, Long number) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis == null) {
                result = false;
            } else {
                jedis.decrBy(key, number);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis decrBy 异常....");
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public void test() {
        Jedis jedis = null;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    
    public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.100.195", 6379);
//        Set<String> set = jedis.keys("*");
//        for (String key : set) {
//            jedis.del(key);
//        }
        jedis.set("fd","sddddddddddddddddddddddddfdfff");
        System.out.print(jedis.get("fd"));
    }
}
