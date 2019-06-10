package com.taotao.content.jedis;

import redis.clients.jedis.Jedis;

public class RedisTest {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.137.10",6379);
		
		String string = jedis.ping();
		System.out.println(string);
		
		jedis.set("hello", "hellovalue");
		
		System.out.println(jedis.get("hello"));
		
		jedis.close();
	}

}
