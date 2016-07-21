package com.belle.infrastructure.tree.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.belle.infrastructure.tree.BizNode;
import com.belle.infrastructure.tree.ITreeTools;
import com.belle.infrastructure.tree.MyTreeNode;
import com.belle.infrastructure.util.ReflectionUtils;

@Component
public class TreeTools implements ITreeTools {

	@Override
	public String genChildTree(BizNode treeNode) {

		return genChildTree(getChirldNodeList(treeNode), treeNode);
	}

	@Override
	public String genChildTree(List<BizNode> treeList, BizNode bizNode) {

		if (null == treeList || 0 == treeList.size()
				|| !treeList.remove(bizNode))
			return null;

		List<List<BizNode>> levelList = getLevelList(treeList, bizNode);

		MyTreeNode root = new MyTreeNode();

		root.setBizNode(bizNode);

		MyTreeNode rootNode = buildTree(root, levelList);

		return new StringBuffer("[").append(genJasonData(rootNode)).append("]")
				.toString();
	}

	protected MyTreeNode buildTree(MyTreeNode node,
			List<List<BizNode>> levelList) {
		int level = getLevel(node.getBizNode());
		int levelSize = levelList.size();

		if (level > levelSize || null == levelList.get(level - 1)
				|| 0 == levelList.get(level - 1).size()){
		    return node;
		    
		}

		List<BizNode> levelNodeList = levelList.get(level - 1);
		node.setChildren(new ArrayList<MyTreeNode>());
		for (int i = 0; i < levelNodeList.size(); i++) {
			BizNode o = levelNodeList.get(i);

			if (o.getNodeStruc().startsWith(
					node.getBizNode().getNodeStruc() + "-")) {

				MyTreeNode newNode = new MyTreeNode();

				newNode.setBizNode(o);
				node.getChildren().add(buildTree(newNode, levelList));

			}
		}
		return node;
	}
	
	/**
	 * 2011.6.1 yinhongbiao
	 * @param node
	 * @param levelList
	 * @param rootLevel
	 * @return
	 */
	protected MyTreeNode buildTree(MyTreeNode node,
           List<List<BizNode>> levelList,int rootLevel) {
       int level = getLevel(node.getBizNode())- rootLevel;
       int levelSize = levelList.size();

       if (level > levelSize || null == levelList.get(level - 1)
               || 0 == levelList.get(level - 1).size()){
           return node;
           
       }

       List<BizNode> levelNodeList = levelList.get(level - 1);
       node.setChildren(new ArrayList<MyTreeNode>());
       for (int i = 0; i < levelNodeList.size(); i++) {
           BizNode o = levelNodeList.get(i);

           if (o.getNodeStruc().startsWith(
                   node.getBizNode().getNodeStruc() + "-")) {

               MyTreeNode newNode = new MyTreeNode();

               newNode.setBizNode(o);
               node.getChildren().add(buildTree(newNode, levelList,rootLevel));

           }
       }
       return node;
   }

	
	protected StringBuffer genJasonData(MyTreeNode rootNode) {

		StringBuffer strb = new StringBuffer().append("{ ");

		strb.append(genBizNodeJason(rootNode.getBizNode()));

		List<MyTreeNode> childList = rootNode.getChildren();
		if (null != childList && childList.size() > 0) {
			strb.append(", \"children\":[");
			int i = 0;
			for (MyTreeNode o : childList) {
				if (i > 0)
					strb.append(", ");
				strb.append(genJasonData(o));
				i++;
			}
			strb.append("]");
		}

		return strb.append("}");
	}

	/**
	 * 可根据具体BizNode类重载此方法，不依赖反射以提高性能
	 * 
	 * @param o
	 * @return
	 */
	protected StringBuffer genBizNodeJason(Object o) {
		StringBuffer sbf = new StringBuffer();
		if (null == o)
			return sbf;

		Class<?> classType = o.getClass();
		Field[] fields = classType.getDeclaredFields();

		int length = fields.length;

		for (int i = 0; i < length; i++) {

			String fieldName = fields[i].getName();
			Class<?> clazzType = fields[i].getType();
			Package package1 = clazzType.getPackage();
			Object fo = ReflectionUtils.getFieldValue(o, fieldName);

			if (!(fo instanceof Collection)
					&& (clazzType.isPrimitive() || null == package1
							|| package1.getName().equals("java.lang") || package1
							.getName().equals("java.util"))) {
				sbf.append("\"").append(fieldName).append("\":\"").append(fo)
						.append("\"");
			} else if (!(fo instanceof Collection)) {
				sbf.append("\"").append(fieldName).append("\":").append(
						genBizNodeJason(fo));
			}

			if (i < length - 1)
				sbf.append(", ");

		}

		return sbf;
	}

	protected List<List<BizNode>> getLevelList(List<BizNode> treeList,
			BizNode bizNode) {

		List<List<BizNode>> list = new ArrayList<List<BizNode>>();
		int m = bizNode.getNodeStruc().split("-").length;

		Map<Integer, List<BizNode>> map = new HashMap<Integer, List<BizNode>>();

		for (BizNode o : treeList) {
			int k = o.getNodeStruc().split("-").length - m;

			siglton(map, k).add(o);
		}

		int i = 0;
		while (map.get(i) != null) {
			list.add(map.get(i));
			i++;
		}

		return list;
	}

	protected List<BizNode> siglton(Map<Integer, List<BizNode>> map, int k) {

		if (null != map.get(k - 1)) {
			return map.get(k - 1);
		} else {
			map.put(k - 1, new ArrayList<BizNode>());
			return map.get(k - 1);
		}

	}

	@Override
	public String genTreeWithFather(BizNode treeNode) {

		return genChildTree(getChirldNodeListWithFather(treeNode), treeNode);
	}

	@Override
	public BizNode addTreeNode(BizNode fatherNode, BizNode treeNode) {

		String[] brotherNodesStrucStr = getBrotherNodes(fatherNode);

		treeNode.setNodeStruc(genNewNodeStrucStr(brotherNodesStrucStr));

		return treeNode;

	}

	protected String genNewNodeStrucStr(String[] brotherNodesStrucStr) {

		return null;
	}

	public String[] getBrotherNodes(BizNode fatherNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delChirldTree(BizNode treeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	protected class Temp4TreeCreate {
		MyTreeNode rootNode;

		int bottomLevel;

		public Temp4TreeCreate(MyTreeNode treeNode, int level) {
			rootNode = treeNode;

			bottomLevel = level;

		}
	}

	@Override
	public List<BizNode> getChirldNodeList(BizNode treeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BizNode> getChirldNodeListWithFather(BizNode treeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	protected int getLevel(BizNode node) {
		return node.getNodeStruc().split("-").length;

	}

	@Override
	public String genChildTreesWithoutFatherNode(List<BizNode> treeList,
			BizNode bizNode) {

		if (null == treeList || 0 == treeList.size()
				|| !treeList.remove(bizNode))
			return null;

		List<List<BizNode>> levelList = getLevelList(treeList, bizNode);

		MyTreeNode root = new MyTreeNode();

		root.setBizNode(bizNode);

		//2011.06.01 yinhongbiao 当前根节点相对 root 节点等级数   修改 不能获取  root-1 单子树bug 
		int level = getLevel(root.getBizNode());
		MyTreeNode rootNode = buildTree(root, levelList,level-1);
		

		List<MyTreeNode> childrenNodes = rootNode.getChildren();

		StringBuffer sbf = new StringBuffer("[");

		boolean flag = false;
		for (MyTreeNode o : childrenNodes) {
			if (flag) {
				sbf.append(",");
			} else {
				flag = true;
			}
			sbf.append(genJasonData(o));
		}

		return sbf.append("]").toString();

	}
	

	@Override
	public String genTreeWithRoot(List<BizNode> nodes, String str) {
		List<String[]> strsList = new ArrayList<String[]>();
		
		for (BizNode o : nodes) {
			strsList.add(o.getNodeStruc().split(str));
		}

		int len0 = strsList.get(0).length;
		List<List<String>> strsList0 = new ArrayList<List<String>>();
		for (int i = 0; i < len0; i++) {
			List<String> colList = new ArrayList<String>();
			strsList0.add(colList);
		}

		List<String[]> strsList1 = new ArrayList<String[]>();
		for (String[] strs : strsList) {
			int len = strs.length;
			String[] strs1 = new String[len];
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < i; j++) {
					if (len - 1 == i) {
						strs1[i] += strs[i];
					} else {
						strs1[i] += strs[i] + str;
					}
				}
			}
			strsList1.add(strs1);
		}

		for (String[] strs1 : strsList1) {
			int len = strs1.length;
			for (int i = 0; i < len; i++) {
				strsList0.get(i).add(strs1[i]);
			}
		}

		for (int i = 0; i < len0; i++) {
			List<String> list = strsList0.get(i);
			
		}
	
		return null;
	}


	private Map<String, List<String>> combineChildStr(String str, List<String> list) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		
		for(String str1 : list) {
			if(str1.startsWith(str) && !list1.contains(str1)) {
				list1.add(str1);
			}
		}
		map.put(str, list1);
		
		return map;
	}

}
