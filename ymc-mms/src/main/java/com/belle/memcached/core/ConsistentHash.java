package com.belle.memcached.core;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * 
 * <p>Title:</p>
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
 * @desc 根据HASH算法操作缓存服务器
 * @Modify 
 * @Version 1.0
 */
public class ConsistentHash<T> {
	 private final HashFunction hashFunction;
	 private final int numberOfReplicas;
	 private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

	 public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
	     Collection<T> nodes) {
	   this.hashFunction = hashFunction;
	   this.numberOfReplicas = numberOfReplicas;

	   for (T node : nodes) {
	     add(node);
	   }
	 }

	 /**
	  * 
	  * @author 
	  * @description 根据HASH算法添加缓存服务器名称
	  * @param node 缓存服务器名称
	  * @modified
	  */
	 public void add(T node) {
	   for (int i = 0; i < numberOfReplicas; i++) {
	     circle.put(hashFunction.hash(node.toString() + i), node);
	   }
	 }

	 /**
	  * 
	  * @author 
	  * @description 根据HASH算法删除缓存服务器名称
	  * @param node 缓存服务器名称
	  * @modified
	  */
	 public void remove(T node) {
	   for (int i = 0; i < numberOfReplicas; i++) {
	     circle.remove(hashFunction.hash(node.toString() + i));
	   }
	 }

	 /**
	  * 
	  * @author 
	  * @description 根据HASH算法找到缓存服务器名称
	  * @param key
	  * @return 服务器名称
	  * @modified
	  */
	 public T get(Object key) {
	   if (circle.isEmpty()) {
	     return null;
	   }
	   int hash = hashFunction.hash(key.toString());
	   if (!circle.containsKey(hash)) {
	     SortedMap<Integer, T> tailMap = circle.tailMap(hash);
	     hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
	   }
	   return circle.get(hash);
	 }


}
