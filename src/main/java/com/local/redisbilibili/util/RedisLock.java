package com.local.redisbilibili.util;

import com.local.redisbilibili.utils.UtilObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Nullable;
import java.util.UUID;

@Component
public class RedisLock implements Lock {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "lock_";

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1] == ARGV[1]");
        sb.append("then");
        sb.append("     return redis.call(\"del\",KEYS[1])");
        sb.append("else");
        sb.append("     return 0");
        sb.append("end");
        UNLOCK_LUA = sb.toString();
    }

    // ThreadLocal用于保存某个县城共享变量，对于同一个 static ThreadLocal，不同线程只能从中 get, set, remove 自己的变量，而不会影响其他线程的变量
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();


    @Override
    public void lock(String key) {
        boolean b = tryLock(key);
        if(b) {
            return;
        }
        try {
            Thread.sleep(50);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        lock(key);
    }

    @Override
    public boolean tryLock(String key) {
        String uuid = UUID.randomUUID().toString();
        RedisCallback<String> callback = (redisConnection -> {
            JedisCommands commands = (JedisCommands)redisConnection.getNativeConnection();
            return commands.set(KEY_PREFIX + key, uuid, "NT", "PX", 60000);
        });
        Object execute = redisTemplate.execute(callback);
        if(UtilObject.isNotEmpty(execute)) {
            threadLocal.set(uuid);
            return true;
        }
        return false;
    }

    @Override
    public void unlock(String key){
        RedisCallback redisScript = new RedisCallback() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Object eval = redisConnection.eval(UNLOCK_LUA.getBytes(), ReturnType.fromJavaType(Long.class), 1, (KEY_PREFIX + key).getBytes(), threadLocal.get().getBytes());
                return eval;
            }
        };
        redisTemplate.execute(redisScript);
    }
}
