package com.yougou.kaidian.commodity.model.vo;

import java.util.List;

public class RedisFilePublishVo {

	/** 内容标记，说明 */
	public static final String FLAG_CONTENT = "html";

	/** 停止服务标记 */
	public static final String FLAG_SERVICE_STOP = "service_stop";

	/** 开启服务标记 */
	public static final String FLAG_SERVICE_START = "service_start";

	public List<String> ips; // 同步服务器ip

	private String flag;
	// 全地址，包括文件名，如: /topics/abc.shtml，但对于接收方来说，这可能是个相对地址
	private String filePath;
	private String content;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

}
