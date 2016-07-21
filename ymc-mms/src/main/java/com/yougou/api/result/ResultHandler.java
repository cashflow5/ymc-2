package com.yougou.api.result;


public interface ResultHandler {
	
	/**
	 * 获取输出响应类型
	 * 
	 * @return String
	 */
	String getContentType();
	
	/**
	 * 输出响应结果
	 * 
	 * @param object
	 * @return String
	 * @throws Exception
	 */
	void writeResult(Object object);
}
