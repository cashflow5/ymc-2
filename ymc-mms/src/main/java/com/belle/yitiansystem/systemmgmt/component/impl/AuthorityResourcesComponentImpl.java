package com.belle.yitiansystem.systemmgmt.component.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.log.factory.AppLogFactory;
import com.belle.infrastructure.log.model.vo.AppLog;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.tree.BizNode;
import com.belle.infrastructure.tree.impl.TreeTools;
import com.belle.other.util.JsonUtil;
import com.belle.yitiansystem.systemmgmt.common.SystemgmtConstant;
import com.belle.yitiansystem.systemmgmt.component.IAuthorityResourcesComponent;
import com.belle.yitiansystem.systemmgmt.dao.IAuthorityResourcesDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.vo.TreeNodeVo;


@Component
public class AuthorityResourcesComponentImpl extends TreeTools implements IAuthorityResourcesComponent{
	
	AppLog log = AppLogFactory.getLog(AuthorityResourcesComponentImpl.class);
	
	@Resource
	private IAuthorityResourcesDao authorityResourcesDao;
	
	@Autowired
	private Properties sysconfigproperties;
	 
	/**
	 * 增加子节点
	 * @param parentStruc  父节点的结构 a-b-c
	 * @param resource	当前结构基本信息
	 * @return
	 */
	@Transactional
	public BizNode addTreeNode(String parentStruc, AuthorityResources resource) throws Exception {
		
		TreeNodeVo node = new TreeNodeVo();
		node.setNodeStruc(parentStruc);
		
		Long[] brotherNodesStrucStr = getBrotherNodesLevels(node);
		
		long level = (brotherNodesStrucStr != null && brotherNodesStrucStr.length > 0) ? 
				(brotherNodesStrucStr[brotherNodesStrucStr.length-1]+ 1) : 1;
		
		resource.setStructure(parentStruc+"-"+level);
		resource.setIsleaf(SystemgmtConstant.IS_TREE_LEAF);
		authorityResourcesDao.save(resource);

		//如果是增加功能点 则当前节点继序为子节点
		if(resource.getType() != null && resource.getType().equals(SystemgmtConstant.AUTH_TYPE_MENU)){
			updateLeaf(parentStruc,"add"); //修改父节点结构 
		}
		
		node.setNodeId(resource.getId());
		node.setNodeName(resource.getMenuName());
		node.setNodeStruc(resource.getStructure());
		node.setNodeUrl(resource.getMemuUrl());
		node.setNodeOrder(resource.getSort());
		
		return node;
	}
	
	public String getTreeJsonDate(HttpServletRequest request,TreeNodeVo rootVo,List<AuthorityResources> authList,boolean isShowRoot) {
		
		List<BizNode> bizNodeList = new ArrayList<BizNode>();
		BizNode bizNode = null;
		TreeNodeVo resVo = null;
		for (AuthorityResources res : authList) {
			resVo = new TreeNodeVo();
			
			resVo.setNodeId(res.getId());
			resVo.setNodeName(res.getMenuName());
			resVo.setNodeStruc(res.getStructure());
			resVo.setNodeUrl(res.getMemuUrl());
			resVo.setNodeOrder(res.getSort());
			 
			//只对包含哑参的URL进行处理
			if(res.getMemuUrl() != null){
				try{
					if(res.getMemuUrl().contains("$")){
						String hostUrl = null;
						String url = res.getMemuUrl();
						HttpSession session = request.getSession();
						Map<String,String> urlMap = (Map<String,String>)JsonUtil.getMapFromJson(String.valueOf(JsonUtil.getJSONString(sysconfigproperties)));
						if(session.getAttribute("accessMethod").equals(Constant.VPN_Y)){
							hostUrl = urlMap.get(url.substring(url.indexOf("$")+1,url.lastIndexOf("$"))+".vpn");
						}else{
							hostUrl = urlMap.get(url.substring(url.indexOf("$")+1,url.lastIndexOf("$")));
						}
						//组合新的URL
						resVo.setNodeUrl(hostUrl+url.substring(url.lastIndexOf("$")+1));
					}
				}catch(Exception e){
					log.info("左侧菜单替换哑参失败： ",e);
				}
			}
			
			//设置jquery树展示自定义的属性
			StringBuffer attr  = new StringBuffer();
			attr.append("{");
			attr.append("\"url\":\"").append(resVo.getNodeUrl()).append("\",");
			attr.append("\"struc\":\"").append(res.getStructure()).append("\"");
			attr.append("}");
			resVo.setAttributes(attr.toString());
			
			if(res.getIsleaf().equals(SystemgmtConstant.NOT_TREE_LEAF)){
				resVo.setState(SystemgmtConstant.CLOSED_TREE);
			}
			
			bizNodeList.add(resVo);
			
			if(resVo.getNodeStruc().equals(rootVo.getNodeStruc())){
				bizNode = resVo;
			}
		}
		String data =  "";
		if(isShowRoot){
			data =  genChildTree(bizNodeList,bizNode);
		}else{
			 data = genChildTreesWithoutFatherNode(bizNodeList,bizNode);
		}
		
		if(data!= null && data.length() > 4){
		    data = data.replaceAll("\"\\{", "\\{");
	        data = data.replaceAll("\\}\"", "\\}");
		}else{
		    data = "[]";
		}
		
		return data;
	}
	
	/**
	 * 获取兄弟节点
	 */
	@Transactional
	public Long[] getBrotherNodesLevels(BizNode fatherNode) {
		
		CriteriaAdapter criteriaAdapter = authorityResourcesDao.createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
//		Criteria criteria = authorityResourcesDao.createCriteria();
		criteria.setCacheable(true);
		
		ProjectionList proList = Projections.projectionList();//设置投影集合 
		proList.add(Projections.groupProperty("structure"));
		criteria.setProjection(proList);
		
		criteria.add(Restrictions.like("structure", fatherNode.getNodeStruc()+"-%"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<String> structList = criteria.list();
		
		
		if(structList == null || structList.size() == 0)
			return null;
		
		int myLevel = fatherNode.getNodeStruc().split("-").length;
		Long[] levels = getBrotherNodesStruc(myLevel,structList);
		
		authorityResourcesDao.releaseHibernateSession(criteriaAdapter.getSession());
		return levels;
	}
	
	/**
	 * 获取兄弟节点结构
	 * @param node
	 * @param structList
	 * @return
	 */
	public Long[] getBrotherNodesStruc(int levels,List<String> structList){
		
		List<Long> strucList = new ArrayList<Long>();
		
		for (String str : structList) {
			String [] eles = str.split("-");
			if((eles.length -1) == levels){
				strucList.add(Long.parseLong(eles[levels]));
			}
		}
		
		Long[] bros = new  Long[strucList.size()];
		
		bros = strucList.toArray(bros);
		Arrays.sort(bros);
		return bros;
	}
	
	/**
	 * 删除当前节点，及子节点
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String delChirldTree(AuthorityResources removeNode) throws Exception{
		String nodeStruc = removeNode.getStructure();
		//获取当前节点下的所有子节点
		CritMap critListMap = new CritMap();
		critListMap.addLeftLike("structure", nodeStruc+"-%");
		critListMap.addFech("authorityRoles");
		List<AuthorityResources> authList = authorityResourcesDao.findByCritMap(critListMap,true);
		authList.add(removeNode);
		for (AuthorityResources authorityResources : authList) {
			//根目录不能删除
			if(authorityResources.getAuthorityRoles().size() > 0){
				return Constant.FAIL;
			}
			if(!authorityResources.getStructure().equals(SystemgmtConstant.MENU_ROOT_STRUC)){
			     authorityResourcesDao.remove(authorityResources);
            }
		}
		if(!removeNode.getType().equals(SystemgmtConstant.AUTH_TYPE_FUNCTION)){
			String parentStruc = nodeStruc.substring(0, nodeStruc.lastIndexOf("-"));
			updateLeaf(parentStruc,nodeStruc); //修改父节点结构 
		}
		return Constant.SUCCESS;
	}
	
	/**
	 * 判断是否还有子 ， 没有 则自己变为叶子*******************解决之前删除子节点后 父节点没有变成叶子节点的 bug
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateLeaf(String struc,String strucUp) throws Exception{
		CritMap critMap = new CritMap();
		critMap.addLeftLike("structure", struc+"-");
		critMap.addEqual("type", SystemgmtConstant.AUTH_TYPE_MENU);
		List<AuthorityResources> list = authorityResourcesDao.findByCritMap(critMap,true);
		AuthorityResources res = authorityResourcesDao.findUniqueBy("structure", struc,true);
		if(list.size() == 0){//如果没子 则自己变为子
			//由于事务的原因 前面的添加的节点 并还没有及时添加 这里做特殊处理
			if(strucUp.equals("add")){
				res.setIsleaf(SystemgmtConstant.NOT_TREE_LEAF);
			}else{
				res.setIsleaf(SystemgmtConstant.IS_TREE_LEAF);
			}
		}else if(list.size() > 0){
			boolean flag=true;
			for (AuthorityResources authorityResources : list) {
				//由于事务的原因 前面的删除节点 并还没有及时删除 这里做特殊处理
				if(authorityResources.getStructure().equals(strucUp)&&list.size()==1){
					res.setIsleaf(SystemgmtConstant.IS_TREE_LEAF);
					flag=false;
				}
			}
			if(flag){
				res.setIsleaf(SystemgmtConstant.NOT_TREE_LEAF);
			}
		}
		authorityResourcesDao.merge(res);
		return true;
	}
	
	/**
	 * 获取所有父节点结构
	 */
	public String [] getParentStruts(String root_struc,String struc){
		int index = struc.lastIndexOf("-");
		if(index == -1)
			return null;
			
		String rootStr = struc.substring(0,index);
		String [] nodes = rootStr.split("-");
		
		if(nodes.length == 0) return null;
		
		String [] strucs = new String[nodes.length];
		strucs[0] = nodes[0];
		for (int i = 1; i < nodes.length; i++) {
			strucs[i] = strucs[i-1]+"-" + nodes[i];
		}
		
		
		List<String> resultStrucList = new ArrayList<String>(); 
		int root_struc_index = root_struc.lastIndexOf("-");
		for (int i = 0; i < strucs.length; i++) {
            if(strucs[i].lastIndexOf("-")>root_struc_index){
                resultStrucList.add(strucs[i]);
            }
        }
		
		resultStrucList.add(root_struc);
		
		String[] result = new String[resultStrucList.size()];
		
		return resultStrucList.toArray(result);
	}
	
	
}
