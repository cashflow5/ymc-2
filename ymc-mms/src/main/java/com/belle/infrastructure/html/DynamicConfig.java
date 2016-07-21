package com.belle.infrastructure.html;
/**
 * 
 * @descript：Bean类 - 动态模板配置 
 * @author  ：方勇
 * @email   ：fangyong@broadengate.com
 * @time    ： 2011-5-18 上午06:12:13
 */
public class DynamicConfig {
	
	private String name;// 配置名称
	private String description;// 描述
	private String templateFilePath;// Freemarker模板文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

}
