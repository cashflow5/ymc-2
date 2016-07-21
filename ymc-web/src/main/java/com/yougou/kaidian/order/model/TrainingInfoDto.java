package com.yougou.kaidian.order.model;

import java.io.Serializable;

/**
 * 商家中心培训中心课程信息DTO
 * @author zhang.f1
 *
 */
public class TrainingInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4297092086102455969L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 删除标志(0表示已删除 1表示未删除)
	 */
	private int delete_flag;
	
	/**
	 * 是否发布，也可以标识流媒体格式是否已生成(1发布,0未发布)
	 */
	private int is_publish;
	
	/**
	 * 创建时间
	 */
	private String create_time;
	
	/**
	 * 更新时间
	 */
	private String update_time ;
	
	/**
	 * 分类(枚举：新手报到,商品管理,商家管理,促销引流,客户服务,规则解读)
	 */
	private String cat_name ;
	
	/**
	 * 文件类型(1 视频,0 ppt)
	 */
	private int file_type;
	
	/**
	 * 学习人数
	 */
	private int person_num;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 置顶
	 */
	private int on_top;
	
	/**
	 * 置顶时间
	 */
	private String ontop_time;
	
	/**
	 * 简介
	 */
	private String description;
	
	/**
	 * 课程版本ID
	 */
	private String version_id;
	
	/**
	 * 主图全路径
	 */
	private String pic_url;
	
	/**
	 * 文件名称
	 */
	private String file_name;
	
	/**
	 * 原文件路径
	 */
	private String file_url;
	
	/**
	 * 浏览全路径
	 */
	private String preview_url;
	
	/**
	 * 课程浏览数
	 */
	private String total_click;
	
	/**
	 * 页码
	 */
    private int pageNo = 0;
    
    /**
	 * 每页记录数
	 */
    private int pageSize = 20;

    /**
	 * 开始索引数
	 */
    private int offset = 0;
    
    /**
     * 是否查询置顶课程
     */
    private String isQueryTop;
    
    /**
     * 是否查询所有课程
     */
    private String isQueryAll;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}

	public int getIs_publish() {
		return is_publish;
	}

	public void setIs_publish(int is_publish) {
		this.is_publish = is_publish;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public int getFile_type() {
		return file_type;
	}

	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	public int getPerson_num() {
		return person_num;
	}

	public void setPerson_num(int person_num) {
		this.person_num = person_num;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getOn_top() {
		return on_top;
	}

	public void setOn_top(int on_top) {
		this.on_top = on_top;
	}

	public String getOntop_time() {
		return ontop_time;
	}

	public void setOntop_time(String ontop_time) {
		this.ontop_time = ontop_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion_id() {
		return version_id;
	}

	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getPreview_url() {
		return preview_url;
	}

	public void setPreview_url(String preview_url) {
		this.preview_url = preview_url;
	}

	public String getTotal_click() {
		return total_click;
	}

	public void setTotal_click(String total_click) {
		this.total_click = total_click;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	 
    public int getOffset() {
		int page = this.pageNo == 0 ? 1 : this.pageNo;
		offset = (page - 1 )* this.pageSize;
		return offset;
	}

	public String getIsQueryTop() {
		return isQueryTop;
	}

	public void setIsQueryTop(String isQueryTop) {
		this.isQueryTop = isQueryTop;
	}

	public String getIsQueryAll() {
		return isQueryAll;
	}

	public void setIsQueryAll(String isQueryAll) {
		this.isQueryAll = isQueryAll;
	}

}
