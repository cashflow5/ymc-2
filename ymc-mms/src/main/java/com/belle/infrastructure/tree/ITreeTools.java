package com.belle.infrastructure.tree;

import java.util.List;

public interface ITreeTools {
	/**
	 * 生成某个节点的子树
	 * @param treeNode
	 * @return
	 */
	public String genChildTree(BizNode treeNode);
	
	/**
	 * 生成某个节点的子树, 不包含父节点, 用于菜单显示
	 * @param treeNode
	 * @return
	 */
	public String genChildTreesWithoutFatherNode(List<BizNode> bizNodeList, BizNode treeNode);
	
	public List<BizNode> getChirldNodeList(BizNode treeNode);
	
	public List<BizNode> getChirldNodeListWithFather(BizNode treeNode);
	
	public String genChildTree(List<BizNode> treeList, BizNode treeNode);
		
	public String genTreeWithFather(BizNode treeNode);

	public BizNode addTreeNode(BizNode fatherNode, BizNode treeNode);
	
	public String[] getBrotherNodes(BizNode fatherNode);
	
	public String delChirldTree(BizNode treeNode);
	
	/**
	 * 取出一层树任意节点列表，展示包含其父节点的树
	 * @param nodes
	 * @param str : 树结构连接字符串
	 * @return
	 */

	public String genTreeWithRoot(List<BizNode> nodes, String str);
	
}
