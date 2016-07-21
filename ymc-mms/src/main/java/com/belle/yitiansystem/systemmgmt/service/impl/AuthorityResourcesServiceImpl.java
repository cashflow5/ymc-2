package com.belle.yitiansystem.systemmgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.tree.BizNode;
import com.belle.infrastructure.tree.impl.TreeTools;
import com.belle.yitiansystem.systemmgmt.common.SystemgmtConstant;
import com.belle.yitiansystem.systemmgmt.component.IAuthorityResourcesComponent;
import com.belle.yitiansystem.systemmgmt.dao.IAuthorityResourcesDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;
import com.belle.yitiansystem.systemmgmt.model.vo.TreeNodeVo;
import com.belle.yitiansystem.systemmgmt.service.IAuthorityResourcesService;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午02:45:14
 */
@Service
public class AuthorityResourcesServiceImpl extends TreeTools implements IAuthorityResourcesService {

	@Resource
	private IAuthorityResourcesDao authorityResourcesDao;
	
	@Resource
	private IAuthorityResourcesComponent authorityResourcesComponent;
	
	@Transactional
	public String addAuthorityResources(AuthorityResources authorityResources)throws Exception {
		return (String)authorityResourcesDao.save(authorityResources);
	}
	
	@Transactional
	public BizNode addAuthorityResources(String parentid,AuthorityResources resource)throws Exception {
		AuthorityResources resources = authorityResourcesDao.getById(parentid);
		String parentStruc = resources.getStructure();
		return authorityResourcesComponent.addTreeNode(parentStruc, resource);
	}
	
	
	public AuthorityResources getAuthorityResourcesById(String id){
		return authorityResourcesDao.getById(id);
	}

	
	public List<AuthorityResources> getAllAuthorityResources() {
		return authorityResourcesDao.getAll("sort",true);
	}
	
	public List<AuthorityResources> getAuthorityResourcesByType(String type){
		CritMap critMap = new CritMap();
		critMap.addEqual("type", type);
		critMap.addAsc("sort");
		return authorityResourcesDao.findByCritMap(critMap,true);
	}
	
	public String getResourceTreeByType(HttpServletRequest request,String type) {
		
		//设置最高根节点结构
		TreeNodeVo rootVo = new TreeNodeVo();
		rootVo.setNodeStruc(SystemgmtConstant.MENU_ROOT_STRUC); 
		
		CritMap critMap = new CritMap();
		
		if(!type.equals(SystemgmtConstant.AUTH_TYPE_ALL)){
			critMap.addEqual("type", type);
		}
		
		critMap.addAsc("sort");
		critMap.addLeftLike("structure", rootVo.getNodeStruc());
		List<AuthorityResources> authList = authorityResourcesDao.findByCritMap(critMap,true);
		
		return authorityResourcesComponent.getTreeJsonDate(request,rootVo, authList,true);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public BizNode updateAuthorityResources(AuthorityResources authorityResources)throws Exception {
		
		AuthorityResources resource = getAuthorityResourcesById(authorityResources.getId());
		resource.setMenuName(authorityResources.getMenuName());
		resource.setMemuUrl(authorityResources.getMemuUrl());
		resource.setRemark(authorityResources.getRemark());
		resource.setSort(authorityResources.getSort());
		resource.setType(authorityResources.getType());
		resource.setFlag(authorityResources.getFlag());
		authorityResourcesDao.merge(resource);
		
		//如果当前目录修改为功能点  则下面所有子菜单都更新为功能点
		if(resource.getType().equals(SystemgmtConstant.AUTH_TYPE_FUNCTION) 
				&&   resource.getIsleaf().equals(SystemgmtConstant.NOT_TREE_LEAF)){
			CriteriaAdapter criteriaAdapter = authorityResourcesDao.createCriteriaAdapter();
			Criteria criteria = criteriaAdapter.getCriteria();
			criteria.add(Restrictions.like("structure", resource.getStructure()+"-",MatchMode.START));
			List<AuthorityResources> resLIst =  criteria.list();
			authorityResourcesDao.releaseHibernateSession(criteriaAdapter.getSession());
			
			for (AuthorityResources res : resLIst) {
				res.setType(SystemgmtConstant.AUTH_TYPE_FUNCTION);
				authorityResourcesDao.merge(res);
			}
		}
		
		authorityResourcesComponent.updateLeaf(resource.getStructure(),"");
		
		TreeNodeVo node = new TreeNodeVo();
		node.setNodeId(resource.getId());
		node.setNodeName(resource.getMenuName());
		return node;
	}

	@Transactional
	public String removeAuthorityResources(String resourceid)throws Exception {
		CritMap critMap = new CritMap();
		//获取要删除的节点
		critMap.addEqual("id", resourceid);
		critMap.addFech("authorityRoles");
		AuthorityResources removeNode = authorityResourcesDao.getObjectByCritMap(critMap,true);
		return authorityResourcesComponent.delChirldTree(removeNode);
	}
	
    public List<AuthorityResources> getChildAuthorityResources(String rootStruc){
        List<AuthorityResources> resultList = new ArrayList<AuthorityResources>(); 
        
        CritMap critMap = new CritMap();
        critMap.addEqual("type",SystemgmtConstant.AUTH_TYPE_MENU);
        critMap.addAsc("sort");
        critMap.addLeftLike("structure", rootStruc+"-");
        //查询所有的子节点与 子子节点
        List<AuthorityResources> authList = authorityResourcesDao.findByCritMap(critMap,true);
        
        //过滤掉所有的子子节点
        int rootLevel = rootStruc.split("-").length+1;
        for (AuthorityResources res : authList) {
            if(res.getStructure().split("-").length == rootLevel){
                resultList.add(res);
            }
        }
        
        return resultList;
    }
	

	
}
