package com.yougou.kaidian.taobao.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品上传后的状态。onsale出售中，instock库中
	 */
	private String approveStatus;

	/**
	 * 商品级别的条形码
	 */
	private String barcode;

	/**
	 * 商品所属的叶子类目 id
	 */
	private Long cid;

	/**
	 * Item的发布时间，目前仅供taobao.item.add和taobao.item.get可用
	 */
	private Date created;

	/**
	 * 下架时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date delistTime;

	/**
	 * 商品描述, 字数要大于5个字符，小于25000个字符
	 */
	private String description;

	/**
	 * 角度图str
	 */
	private String anglePic;
	
	/**
	 * 商品url
	 */
	private String detailUrl;



	/**
	 * 用户自行输入的类目属性ID串。结构："pid1,pid2,pid3"，如："20000"（表示品牌） 注：通常一个类目下用户可输入的关键属性不超过1个。
	 */
	private String inputPids;

	/**
	 * 用户自行输入的子属性名和属性值，结构:"父属性值;一级子属性名;一级子属性值;二级子属性名;自定义输入值,....",如：“耐克;耐克系列;科比系列;科比系列;2K5”，input_str需要与input_pids一一对应，注：通常一个类目下用户可输入的关键属性不超过1个。所有属性别名加起来不能超过 3999字节。
	 */
	private String inputStr;

	/**
	 * 非分销商品：0，代销：1，经销：2
	 */
	private Long isFenxiao;

	/**
	 * 是否在淘宝显示
	 */
	private Boolean isTaobao;

	/**
	 * 是否定时上架商品
	 */
	private Boolean isTiming;

	/**
	 * 标示商品是否为新品。
值含义：true-是，false-否。
	 */
	private Boolean isXinpin;

	/**
	 * 表示商品的体积，用于按体积计费的运费模板。该值的单位为立方米（m3）。
该值支持两种格式的设置：格式1：bulk:3,单位为立方米(m3),表示直接设置为商品的体积。格式2：weight:10;breadth:10;height:10，单位为米（m）
	 */
	private String itemSize;

	/**
	 * 商品的重量，用于按重量计费的运费模板。注意：单位为kg
	 */
	private String itemWeight;

	/**
	 * 上架时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date listTime;


	/**
	 * 商品修改时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private Date modified;

	/**
	 * 卖家昵称
	 */
	private String nick;

	/**
	 * 商品数字id
	 */
	private Long numIid;

	/**
	 * 商家外部编码(可与商家外部系统对接)。需要授权才能获取。
	 */
	private String outerId;

	/**
	 * 商品主图片地址
	 */
	private String picUrl;

	/**
	 * 商品价格，格式：5.00；单位：元；精确到：分
	 */
	private String price;

	/**
	 * 商品属性 格式：pid:vid;pid:vid
	 */
	private String props;

	/**
	 * 商品属性名称。标识着props内容里面的pid和vid所对应的名称。格式为：pid1:vid1:pid_name1:vid_name1;pid2:vid2:pid_name2:vid_name2……(<strong>注：</strong><font color="red">属性名称中的冒号":"被转换为："#cln#";  
分号";"被转换为："#scln#"
</font>)
	 */
	private String propsName;

	/**
	 * 商品卖点信息，天猫商家使用字段，最长150个字符。
	 */
	private String sellPoint;

	/**
	 * 商品标题,不能超过60字节
	 */
	private String title;

	/**
	 * 商品是否违规，违规：true , 不违规：false
	 */
	private Boolean violation;

	/**
	 * 无线的宝贝描述
	 */
	private String wirelessDesc;
	
	/**
	 * 淘宝店铺主账号昵称ID
	 */
	private Long nickId;
	
	/**
	 * 优购商家编码
	 */
	private String merchantCode;
	
	/**
	 * 操作员
	 */
	private String operater;

	/**
	 * 操作时间
	 */
	private String operated;
	/**
	 * 年份
	 */
	private String yougouYears;
	
	private String yougouBrandNo;
	/**
	 * 优购商品编码
	 */
	private String yougouBrandName;
	
	private String yougouCateNo;

	/**
	 * 款号
	 */
	private String yougouStyleNo;

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDelistTime() {
		return delistTime;
	}

	public void setDelistTime(Date delistTime) {
		this.delistTime = delistTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getInputPids() {
		return inputPids;
	}

	public void setInputPids(String inputPids) {
		this.inputPids = inputPids;
	}

	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}

	public Long getIsFenxiao() {
		return isFenxiao;
	}

	public void setIsFenxiao(Long isFenxiao) {
		this.isFenxiao = isFenxiao;
	}

	public Boolean getIsTaobao() {
		return isTaobao;
	}

	public void setIsTaobao(Boolean isTaobao) {
		this.isTaobao = isTaobao;
	}

	public Boolean getIsTiming() {
		return isTiming;
	}

	public void setIsTiming(Boolean isTiming) {
		this.isTiming = isTiming;
	}

	public Boolean getIsXinpin() {
		return isXinpin;
	}

	public void setIsXinpin(Boolean isXinpin) {
		this.isXinpin = isXinpin;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}

	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public String getPropsName() {
		return propsName;
	}

	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getViolation() {
		return violation;
	}

	public void setViolation(Boolean violation) {
		this.violation = violation;
	}

	public String getWirelessDesc() {
		return wirelessDesc;
	}

	public void setWirelessDesc(String wirelessDesc) {
		this.wirelessDesc = wirelessDesc;
	}

	public Long getNickId() {
		return nickId;
	}

	public void setNickId(Long nickId) {
		this.nickId = nickId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperated() {
		return operated;
	}

	public void setOperated(String operated) {
		this.operated = operated;
	}

	public String getYougouYears() {
		return yougouYears;
	}

	public void setYougouYears(String yougouYears) {
		this.yougouYears = yougouYears;
	}

	public String getYougouBrandNo() {
		return yougouBrandNo;
	}

	public void setYougouBrandNo(String yougouBrandNo) {
		this.yougouBrandNo = yougouBrandNo;
	}

	public String getYougouBrandName() {
		return yougouBrandName;
	}

	public void setYougouBrandName(String yougouBrandName) {
		this.yougouBrandName = yougouBrandName;
	}

	public String getYougouCateNo() {
		return yougouCateNo;
	}

	public void setYougouCateNo(String yougouCateNo) {
		this.yougouCateNo = yougouCateNo;
	}

	public String getYougouStyleNo() {
		return yougouStyleNo;
	}

	public void setYougouStyleNo(String yougouStyleNo) {
		this.yougouStyleNo = yougouStyleNo;
	}

	public String getAnglePic() {
		return anglePic;
	}

	public void setAnglePic(String anglePic) {
		this.anglePic = anglePic;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
