/**
 * 
 */
package com.belle.yitiansystem.merchant.service;

import java.util.List;

import com.belle.yitiansystem.merchant.model.pojo.HelpCenterImg;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterMenu;

/**
 * 商家帮助中心服务接口类
 * 
 * @author huang.tao
 *
 */
public interface IMerchantHelpService {
	/**
	 * 获取商家中心所有帮助类目菜单
	 * 
	 * @return
	 */
	List<HelpCenterMenu> getHelpCenterMenuList();
	
	/**
	 * 更新菜单内容
	 * @param menuArr
	 * @param operator
	 * @throws Exception
	 */
	void updateMenuData(HelpCenterMenu[] menuArr,String operator) throws Exception;
	
	/**
	 * 根据菜单编号查询帮助内容
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	String getContentByMenuId(String menuId) throws Exception;
	
	/**
	 * 保存或编辑帮助文档内容
	 * @param helpMenuId
	 * @param helpMenuContent
	 * @param operator
	 * @throws Exception
	 */
	void saveOrUpdateContent(String helpMenuId, String helpMenuContent, String operator) throws Exception;
	
	/**
	 * 通过菜单节点Id获取菜单Id
	 * 
	 * @param subId
	 * @return
	 * @throws Exception
	 */
	String getMenuIdBySubId(String subId) throws Exception;
	
	/**
	 * 通过id获取菜单对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	HelpCenterMenu getMenuById(String id) throws Exception;
	
	/**
	 * 增加帮助图片记录
	 * 
	 * @param filename 图片名称
	 * @param isExist 图片是否存在
	 * @throws Exception
	 */
	void addImageName(String filename) throws Exception;
	
	/**
	 * 获取图片列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<HelpCenterImg> getHelpImageNameList(String fileName) throws Exception;
}
