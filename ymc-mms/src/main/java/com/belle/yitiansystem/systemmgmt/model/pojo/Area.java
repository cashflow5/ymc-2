package com.belle.yitiansystem.systemmgmt.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * TblAreaInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_systemmg_area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "isleaf", length = 1)
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	@Column(name = "post", length = 40)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "child")
    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }


    @Column(name = "level")
    public int getLevel() {
        return level;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "timestamp")
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
    
}