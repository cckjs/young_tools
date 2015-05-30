package com.young.tools.common.util.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.test.spring.SpringUtils;
import com.young.tools.common.util.redis.listener.OperatorListener;

public class ReidsTool {

	RedisTools redis = SpringUtils.getBean(RedisTools.class);
	@Test
	public void testPut(){
		for(int i=0;i<1500;i++){
			redis.put(i, "__"+i+"",1,TimeUnit.MINUTES);
		}
	}
	
	@Test
	public void testGet(){
		for(int i=0;i<1500;i++){
			System.out.println(redis.get(i));
		}
	}
	@Test
	public void testSub(){
	    redis.getTemplate(RedisTools.Type.w).execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.subscribe(new OperatorListener(), "test".getBytes());
				return null;
			}
		});
	}
	
	@Test
	public void testPub(){
		redis.getTemplate(RedisTools.Type.w).execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.publish("test".getBytes(), "哈哈 i am here".getBytes());
				return null;
			}
		});
	}
	
	@Test
	public void testClientList(){
		//查看目前连接的client端
		System.out.println(redis.getTemplate(RedisTools.Type.w).getClientList());
		
	}
	@Test
	public void testTransation(){
		redis.getTemplate(RedisTools.Type.w).execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.multi();
				connection.set("1".getBytes(), "1".getBytes());
				connection.set("2".getBytes(), "2".getBytes());
				//事务还没有执行,所以get为空
				System.out.println(connection.get("1".getBytes()));
				System.out.println(connection.exec());
				//事务已经提交,所以可以获取到值
				System.out.println(new String(connection.get("1".getBytes())));
				return null;
			}
		});
	}
	
	
	public static void main(String[] args) {
		ReidsTool tool = new ReidsTool();
		tool.testTransation();
	}
}
