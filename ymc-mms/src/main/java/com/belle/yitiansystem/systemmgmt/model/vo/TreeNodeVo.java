package com.belle.yitiansystem.systemmgmt.model.vo;

import com.belle.infrastructure.tree.BizNode;

public class TreeNodeVo implements BizNode {
	
	private String id;  
	private String text;		
	private boolean checked = false;		//是否选中
	private String state;		//是否展开 (默认关闭)
	private String iconCls ;		//图标
	
	private String struc;		//节点结构标识
	private int order;			//顺序
	private String remark;		//节点备注
	private String url;
	private String type;		//资源类型
	
	private String attributes;  //杼节点自定义属性
	
	private String post;//邮编
	private String code;//区号
	
	private String flag;//管理后台菜单项分离标识
	
//	private AuthorityResources resources;  //权限资源
//	
//	public AuthorityResources getResources() {
//		return resources;
//	}
//
//	public void setResources(AuthorityResources resources) {
//		this.resources = resources;
//	}
	
	
	public String getAttributes() {
		return attributes;
	}


	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getNodeStruc() {
		return struc;
	}

	
	public boolean getChecked() {
		return checked;
	}


	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getIconCls() {
		return iconCls;
	}


	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	public int getNodeOrder() {
		return order;
	}

	
	public String getNodeId() {
		return id;
	}
	
	
	public String getNodeName() {
		return text;
	}

	
	public String getNodeInfo() {
		return remark;
	}

	
	public String getNodeUrl() {
		return url;
	}

	
	public void setNodeStruc(String str) {
		this.struc = str;
	}

	
	public void setNodeOrder(int str) {
		this.order = str;
	}

	
	public void setNodeName(String str) {
		this.text = str;
	}

	
	public void setNodeInfo(String str) {
		this.remark = str;
	}

	
	public void setNodeUrl(String str) {
		this.url = str;
	}
	
	public void setNodeId(String id) {
		this.id = id;
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
	

}
