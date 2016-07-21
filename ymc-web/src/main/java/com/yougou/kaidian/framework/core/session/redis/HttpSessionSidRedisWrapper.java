package com.yougou.kaidian.framework.core.session.redis;

import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;

import com.yougou.kaidian.framework.core.session.alisoft.Enumerator;
import com.yougou.tools.common.utils.SpringContextHolder;

public class HttpSessionSidRedisWrapper extends HttpSessionWrapper {

	private String sid = "";

	private RedisTemplate<String, Object> redisTemplate;
	
	public String getSid(){
		return sid;
	}

	public HttpSessionSidRedisWrapper(String sid, HttpSession session) {
		super(session);
		this.sid = sid;
		this.redisTemplate = SpringContextHolder.getBean("redisTemplate");
	}

	public Object getAttribute(String name) {
		return this.redisTemplate.opsForHash().get(this.sid, name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return (new Enumerator(this.redisTemplate.opsForHash().keys(this.sid), true));
	}

	public void invalidate() {
		this.redisTemplate.delete(this.sid);
	}

	public void removeAttribute(String name) {
		this.redisTemplate.opsForHash().delete(this.sid, name);
	}

	public void setAttribute(String name, Object value) {
		this.redisTemplate.opsForHash().put(this.sid, name, value);
		this.redisTemplate.expire(this.sid, 120, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
	}

}
