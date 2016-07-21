package com.yougou.kaidian.user.model.pojo;



/**
 * TblAreaInfo entity. @author MyEclipse Persistence Tools
 */

public class Area implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private int sort; //排序
	private String no;  //树结构
	private String isleaf;
	private int child;
	private int level;
	private String post;// 邮编
	private String code;//区号
	//时间戳
	private Long timestamp = System.currentTimeMillis();

	private String nameStr;
	
	public Area() {
		super();
	}

	public Area(String id, String name, String no, String isleaf,String post,String code,Long timestamp) {
		super();
		this.id = id;
		this.name = name;
		this.no = no;
		this.isleaf = isleaf;
		this.post = post;
		this.code=code;
		this.timestamp=timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	
}