package com.belle.memcached.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @Author
 * @Create 2011-5-1
 * @desc 缓存类
 * @Modify
 * @Version 1.0
 */
public class Cache {

	protected static final Log log = LogFactory.getLog(Cache.class);

	/**
	 * 缓存客户端是否可用标识
	 */
	private static final String CACHE_CLIENT_AVAILABLE_FLAG = "CACHE_CLIENT_AVAILABLE_FLAG";

	private static ICacheManager<IMemcachedCache> manager;

	private static int num = 0;

	/**
	 *
	 * @author
	 * @description 获得缓存管理
	 * @return ICacheManager 缓存管理对象
	 * @modified
	 */
	@SuppressWarnings("unchecked")
	public static ICacheManager getManager() {
		try {
			// 初始化缓存管理
			if (manager == null) {
				manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
				
				String filePath ="file:/etc/yougouhtconf"+ File.separator +"memcached_cluster.xml";
				
				File configDir = new File("/etc/yougouhtconf");
				if (!configDir.exists()) {
					log.error("配置文件目录不存在！/etc/yougouhtconf ");
					filePath = "file:/c:/yougouhtconf"+ File.separator +"memcached_cluster.xml";;
				}
				
				
				manager.setConfigFile(filePath);
				manager.start();
			}
			return manager;
		} catch (Exception ex) {
			try{
				// 初始化缓存管理    
				manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
				String filePath ="file:"+Cache.class.getResource("/").getFile().toString()+ File.separator + "config"
				+ File.separator +"memcached_cluster.xml";
				manager.setConfigFile(filePath);
				manager.start();
				return manager;
			}catch(Exception e){
				log.error("获取缓存ICacheManager对象时发生异常", ex);
				return null;
			}
		}
	}

	@SuppressWarnings("static-access")
	public void setManager(ICacheManager<IMemcachedCache> manager) {
		this.manager = manager;
	}

	/**
	 *
	 * @author
	 * @description 获得cache实例
	 * @return IMemcachedCache
	 * @modified
	 */
	@SuppressWarnings("unchecked")
	public static IMemcachedCache getCacheInstatce() {

		if (manager == null) {
			getManager();
		}

		List lst = new ArrayList();
		lst.add("mclient1");
		lst.add("mclient2");
		lst.add("mclient3");
		lst.add("mclient4");

		HashFunction hf = new HashFunction();
		ConsistentHash ch = new ConsistentHash(hf, 1, lst);
		int keynum = num % lst.size();
		String cachename = (String) ch.get(lst.get(keynum).toString() + "0");
		// System.out.println("缓存节点:" + cachename);
		IMemcachedCache memcache = manager.getCache(cachename);

		if (num == 10000) {
			num = 0;
		} else {
			num++;
		}
		return memcache;
	}

	/**
	 *
	 * @author
	 * @description 获得公共缓存块cache实例
	 * @return IMemcachedCache
	 * @modified
	 */
	@SuppressWarnings("unchecked")
	public static IMemcachedCache getCommonCacheInstatce() {
        /*
		if (manager == null) {
			getManager();
		}

		List lst = new ArrayList();
		lst.add("commonclient1");
		lst.add("commonclient2");

		HashFunction hf = new HashFunction();
		ConsistentHash ch = new ConsistentHash(hf, 1, lst);
		int keynum = num % lst.size();
		String cachename = (String) ch.get(lst.get(keynum).toString() + "0");
		// System.out.println("缓存节点:" + cachename);
		IMemcachedCache memcache = manager.getCache(cachename);

		if (num == 10000) {
			num = 0;
		} else {
			num++;
		}
		*/
		return null;
	}

	// 获取特定的cacheClient
	public static IMemcachedCache getSingleCacheClient(String cacheClient) {

		if (manager == null) {
			getManager();
		}

		String client = null;
		if (cacheClient != null && !"".equals(cacheClient)) {
			client = cacheClient;
		} else {
			client = "mclient1";
		}

		return manager.getCache(client);
	}

	/**
	 * 检查缓存服务器是否可用
	 *
	 * @param cacheClient
	 * @return
	 */
	public static boolean checkCacheClientAvailable(CacheClientEnum cacheClientName) {
		IMemcachedCache cacheClient = manager.getCache(cacheClientName.name());
		Object availableFlag = cacheClient.get(CACHE_CLIENT_AVAILABLE_FLAG);

		// 避免验证标识被清除
		if (availableFlag == null) {
			try {
				cacheClient.put(CACHE_CLIENT_AVAILABLE_FLAG, true);
				availableFlag = cacheClient.get(CACHE_CLIENT_AVAILABLE_FLAG);
			} catch (Exception ex) {
				log.error("缓存器" + cacheClientName.name() + "出现故障,请赶紧处理");
			}
		}

		if (availableFlag != null && (Boolean) availableFlag) {
			return true;
		}

		return false;
	}

}
