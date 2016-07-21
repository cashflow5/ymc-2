/**
 * 
 */
package com.belle.yitiansystem.merchant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.yitiansystem.merchant.dao.IHelpCenterContentDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterImgDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterLogDao;
import com.belle.yitiansystem.merchant.dao.IHelpCenterMenuDao;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterContent;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterImg;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterLog;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterMenu;
import com.belle.yitiansystem.merchant.service.IMerchantHelpService;

/**
 * 商家帮助中心服务实现类
 * 
 * @author huang.tao
 *
 */
@Service
public class MerchantHelpService implements IMerchantHelpService {
	
	@Resource
	private IHelpCenterMenuDao helpCenterMenuDao;
	@Resource
	private IHelpCenterContentDao helpCenterContentDao;
	@Resource
	private IHelpCenterLogDao helpCenterLogDao;
	@Resource
	private IHelpCenterImgDao helpCenterImgDao;
	
	@Override
	public List<HelpCenterMenu> getHelpCenterMenuList() {
		final String hql = "from HelpCenterMenu m order by m.level asc, m.orderNo asc";
		List<HelpCenterMenu> resultList = helpCenterMenuDao.find(hql, new Object[]{});
		return resultList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateMenuData(HelpCenterMenu[] menuArr,String operator) throws Exception {
		//操作日志流水
		Date logDate = new Date();
		List<HelpCenterLog> centerLogList = new ArrayList<HelpCenterLog>();
		Map<String, HelpCenterMenu> tempMap = new HashMap<String, HelpCenterMenu>();
		List<HelpCenterMenu> tempList = helpCenterMenuDao.getAll("orderNo", true);
		if (tempList!=null) {
			for (HelpCenterMenu helpCenterMenu : tempList) {
				tempMap.put(helpCenterMenu.getId(), helpCenterMenu);
			}
		}
		if (menuArr!=null) {
			for (HelpCenterMenu helpCenterMenu : menuArr) {
				if (helpCenterMenu.getId()!=null) {
					if (!helpCenterMenu.equals(tempMap.get(helpCenterMenu.getId()))) {
						helpCenterMenuDao.merge(helpCenterMenu);
						centerLogList.add(new HelpCenterLog(helpCenterMenu.getSubId(), "更新菜单:"+helpCenterMenu.getMenuName(), logDate, operator));
					}
					tempMap.remove(helpCenterMenu.getId());
				}else {
					helpCenterMenuDao.save(helpCenterMenu);
					centerLogList.add(new HelpCenterLog(helpCenterMenu.getSubId(), "新增菜单:"+helpCenterMenu.getMenuName(), logDate, operator));
				}
				
			}
		}
		
		for (Map.Entry<String, HelpCenterMenu> entry:tempMap.entrySet()) {
			//删除菜单和菜单所关联的文章
			helpCenterMenuDao.remove(entry.getValue());
			centerLogList.add(new HelpCenterLog(entry.getValue().getSubId(), "删除菜单:"+entry.getValue().getMenuName(), logDate, operator));
			List<HelpCenterContent> contentList = helpCenterContentDao.findBy("menuId", entry.getValue().getSubId(), false);
			if (contentList!=null) {
				for (HelpCenterContent helpCenterContent : contentList) {
					helpCenterContentDao.remove(helpCenterContent);
					centerLogList.add(new HelpCenterLog(entry.getValue().getSubId(), "删除菜单级联删除相关帮助内容:"+entry.getValue().getMenuName(), logDate, operator));
				}
			}
		}
		for (HelpCenterLog helpCenterLog : centerLogList) {
			helpCenterLogDao.save(helpCenterLog);
		}
	}

	@Override
	public String getContentByMenuId(String menuId) throws Exception {
		List<HelpCenterContent> contentList = null;
		//HelpCenterMenu tempMenu = helpCenterMenuDao.getById(menuId);
		if (StringUtils.isNotBlank(menuId)) {
			contentList = helpCenterContentDao.findBy("menuId", menuId, false);
		}
		return CollectionUtils.isNotEmpty(contentList) ? contentList.get(0).getContent() : "";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveOrUpdateContent(String helpMenuId, String helpMenuContent,
			String operator) throws Exception {
		HelpCenterContent tempContent = null;
		HelpCenterLog tempLog = null;
		List<HelpCenterContent> contentList = helpCenterContentDao.findBy("menuId", helpMenuId, false);
		if (contentList!=null && contentList.size()>0) {
			tempContent = contentList.get(0);
			tempContent.setContent(helpMenuContent);
			tempLog = new HelpCenterLog(helpMenuId, "修改帮助文档内容", new Date(), operator);
		}else {
			tempContent = new HelpCenterContent();
			tempContent.setMenuId(helpMenuId);
			tempContent.setContent(helpMenuContent);
			tempLog = new HelpCenterLog(helpMenuId, "新增帮助文档内容", new Date(), operator);
		}
		helpCenterContentDao.saveObject(tempContent);
		helpCenterLogDao.save(tempLog);
	}

	@Override
	public String getMenuIdBySubId(String subId) throws Exception {
		List<HelpCenterMenu> list = null;
		if (StringUtils.isNotBlank(subId)) {
			list = helpCenterMenuDao.findBy("subId", subId, false);
		}
		return CollectionUtils.isNotEmpty(list) ? list.get(0).getId() : "";
	}

	@Override
	public HelpCenterMenu getMenuById(String id) throws Exception {
		HelpCenterMenu menu = null;
		if (StringUtils.isNotBlank(id)) {
			menu = helpCenterMenuDao.getById(id);
		}
		return menu;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addImageName(String filename) throws Exception {
		if (isExistImage(filename)) 
			return;
		
		HelpCenterImg img = new HelpCenterImg(filename, new Date(), new Date());
		if(filename.length()>32){
			filename= filename.substring(0, 32);
		}
		helpCenterImgDao.save(img);
	}
	
	private boolean isExistImage(String filename) {
		List<HelpCenterImg> list = helpCenterImgDao.findBy("picName", filename, false);
		return CollectionUtils.isNotEmpty(list) ? true : false; 
	}
 
	
	@Override
	public List<HelpCenterImg> getHelpImageNameList(String fileName) throws Exception {
		  String hql = "from HelpCenterImg m  ";
		if(StringUtils.isNotEmpty(fileName)){
			hql += " where  picName like '%"+fileName.trim()+"%'";
		}
		hql += "  order by m.created desc";
				
				
		List<HelpCenterImg> list = helpCenterImgDao.find(hql, new Object[]{});
		return list;
	}
}
