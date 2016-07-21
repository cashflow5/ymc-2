package com.belle.infrastructure.tree;

import java.util.List;

public class MyTreeNode {
	private BizNode bizNode;
	
	private MyTreeNode fatherNode;
	
	private List<MyTreeNode> children;

	public BizNode getBizNode() {
		return this.bizNode;
	}

	public void setBizNode(BizNode bizNode) {
		this.bizNode = bizNode;
	}

	public MyTreeNode getFatherNode() {
		return this.fatherNode;
	}

	public void setFatherNode(MyTreeNode fatherNode) {
		this.fatherNode = fatherNode;
	}

	public List<MyTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<MyTreeNode> children) {
		this.children = children;
	}

}
