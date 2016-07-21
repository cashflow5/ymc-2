package com.belle.other.model.vo;

/**
 * 
 * 类描述：人员
 * @author xiongjingang
 * @date 2011-6-8 下午05:56:32
 * @email xjg-280@163.com
 */
public class StaffWmsVo {
	/** 人员ID */
	private String id;
	/** 人员编号 */
	private String staffCode;
	/** 名字 */
	private String staffName;
	/** 性别 */
	private Integer sex;
	/** 电话 */
	private String telPhone;
	/** 手机 */
	private String machinePhone;
	/** 邮箱 */
	private String email;
	/** QQ */
	private String qq;
	/** MSN */
	private String msn;
	/** 是否在职 */
	private Integer isWork;
	/** 是否有效 */
	private Integer status;
	/** 更新时间戳　 */
	private Long updateTimestamp;

	public StaffWmsVo() {
		super();
	}

	public StaffWmsVo(String id, String staffCode, String staffName, Integer sex, String telPhone, String machinePhone, String email, String qq, String msn, Integer isWork, Integer status,
			Long updateTimestamp) {
		super();
		this.id = id;
		this.staffCode = staffCode;
		this.staffName = staffName;
		this.sex = sex;
		this.telPhone = telPhone;
		this.machinePhone = machinePhone;
		this.email = email;
		this.qq = qq;
		this.msn = msn;
		this.isWork = isWork;
		this.status = status;
		this.updateTimestamp = updateTimestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMachinePhone() {
		return machinePhone;
	}

	public void setMachinePhone(String machinePhone) {
		this.machinePhone = machinePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public Integer getIsWork() {
		return isWork;
	}

	public void setIsWork(Integer isWork) {
		this.isWork = isWork;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

}
