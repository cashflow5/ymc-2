package com.belle.yitiansystem.systemmgmt.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_organiz_struct")
public class OrganizStruct implements java.io.Serializable {

	// Fields

	private String id;
	private String orgName;
	private String struct;
	private String yitianInd;
	private String remark;
	private String no;
	
	private String isleaf;
    private int child;
    private int level;

	// Constructors

	/** default constructor */
	public OrganizStruct() {
	}

	/** full constructor */
	public OrganizStruct(String orgName, String struct, String yitianInd,
			String remark, String no) {
		this.orgName = orgName;
		this.struct = struct;
		this.yitianInd = yitianInd;
		this.remark = remark;
		this.no = no;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "org_name", length = 150)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "struct", length = 20)
	public String getStruct() {
		return this.struct;
	}

	public void setStruct(String struct) {
		this.struct = struct;
	}

	@Column(name = "yitian_ind", length = 2)
	public String getYitianInd() {
		return this.yitianInd;
	}

	public void setYitianInd(String yitianInd) {
		this.yitianInd = yitianInd;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return this.no;
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
}