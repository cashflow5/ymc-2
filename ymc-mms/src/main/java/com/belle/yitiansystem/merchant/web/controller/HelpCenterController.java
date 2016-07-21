/**
 * 
 */
package com.belle.yitiansystem.merchant.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterImg;
import com.belle.yitiansystem.merchant.model.pojo.HelpCenterMenu;
import com.belle.yitiansystem.merchant.model.vo.HelpCenterMenuArray;
import com.belle.yitiansystem.merchant.service.IMerchantHelpService;
import com.belle.yitiansystem.merchant.util.FtpTools;

/**
 * 帮助中心Controller
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/yitiansystem/merchants/help")
public class HelpCenterController {
	
	private Logger logger = Logger.getLogger(HelpCenterController.class);
	
	@Resource
	private FtpTools ftp;
	
	@Resource
	private IMerchantHelpService helpService;
	
	private final static String TO_PUBLISH_URL = "yitiansystem/help/to_publish";
	
	private final static String PREVIEW_URL = "yitiansystem/help/preview";
	
	private final static String IMG_SELECTOR_URL = "yitiansystem/help/img_selector";
	
	private final static String IMG_SWFUPLOAD_URL = "yitiansystem/help/img_swfupload";
	
	/**
	 * 进入商家帮助发布后台
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_publish")
	public ModelAndView toPublish(ModelMap modelMap,HttpServletRequest request) throws Exception {
        List<HelpCenterMenu> resultList = helpService.getHelpCenterMenuList();
        modelMap.addAttribute("treeModes", resultList);
		return new ModelAndView(TO_PUBLISH_URL, modelMap);
	}
	
	/**
	 * 保存菜单数据
	 * @param param
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMenuData")
	public ModelAndView updateMenuData(HelpCenterMenuArray param, ModelMap modelMap, HttpServletRequest request) throws Exception {
        if (param!=null && param.getMenuArr()!=null) {
        	helpService.updateMenuData(param.getMenuArr(),GetSessionUtil.getSystemUser(request).getUsername());
		}
		List<HelpCenterMenu> resultList = helpService.getHelpCenterMenuList();
        modelMap.addAttribute("treeModes", resultList);
		return new ModelAndView(TO_PUBLISH_URL, modelMap);
	}
	
	/**
	 * 根据菜单编号查询帮助内容
	 * @param menuId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getContentByMenuId")
	public String getContentByMenuId(String subId, String menuId, HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(menuId) || "undefined".equals(menuId)) {
			if (StringUtils.isBlank(subId))
				return "";
			menuId = helpService.getMenuIdBySubId(subId);
		}
		return helpService.getContentByMenuId(menuId);
	}
	
	/**
	 * 保存帮助文档前预览
	 * @param helpMenuContent
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("preview")
	public ModelAndView preview(String helpMenuContent, String menuId, String menuName, ModelMap modelMap, HttpServletRequest request) throws Exception {
        modelMap.addAttribute("helpMenuContent", helpMenuContent);
		HelpCenterMenu curr_menu = helpService.getMenuById(menuId);
		modelMap.addAttribute("menu", curr_menu);
		//如果用户未保存菜单，则将节点名称传递到预览页面
		modelMap.addAttribute("menuName", curr_menu == null ? menuName : null);
		
		//帮助菜单导航
		List<HelpCenterMenu> menuList = helpService.getHelpCenterMenuList();
		Map<String, String> firstMap = new TreeMap<String, String>();
		Map<String, Map<String, String>> menuMap = new TreeMap<String, Map<String, String>>();
		Map<String, String> temp = null;
		for (HelpCenterMenu menu : menuList) {
			if (0 == menu.getLevel()) {
				firstMap.put(menu.getId(), menu.getMenuName());
				temp = new TreeMap<String, String>();
				for (HelpCenterMenu _menu : menuList) {
					if (menu.getSubId().equals(_menu.getParentId())) {
						temp.put(_menu.getId(), _menu.getMenuName());
					}
				}
				menuMap.put(menu.getId(), temp);
			}
		}
		modelMap.addAttribute("firstMap", firstMap);
		modelMap.addAttribute("menuMap", menuMap);
		
        return new ModelAndView(PREVIEW_URL, modelMap);
	}
	
	/**
	 * 保存帮助内容页
	 * @param param
	 * @param menuSubId 菜单节点Id
	 * @param menuId    帮助菜单Id
	 * @param helpMenuContent 帮助内容
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("save_content")
	public String saveContent(HelpCenterMenuArray param, String menuSubId, String menuId, String helpMenuContent, HttpServletRequest request) throws Exception {
		try {
			if (StringUtils.isBlank(menuSubId)) {
				return "请先选择叶子节点菜单!";
			}
			if (StringUtils.isBlank(helpMenuContent)) {
				return "请先输入帮助文档内容!";
			}
			if (param != null && param.getMenuArr() != null) {
				helpService.updateMenuData(param.getMenuArr(), GetSessionUtil.getSystemUser(request).getUsername());
			}
			if (StringUtils.isBlank(menuId) || "undefined".equals(menuId)) {
				menuId = helpService.getMenuIdBySubId(menuSubId);
			}
			
			helpService.saveOrUpdateContent(menuId, helpMenuContent, GetSessionUtil.getSystemUser(request).getUsername());
		} catch (Exception e) {
			logger.error("保存出错", e);
			return "保存出错:";
		}
		return "成功保存";
	}
	
	/**
	 * 发布帮助静态页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("publish")
	public String publish(HttpServletRequest request) throws Exception {
		logger.info("开始发布帮助中心静态页.");
		
		logger.info("发布帮助中心静态页完成.");
		return "success";
	}
	
	/**
	 * 图片选择器
	 * 
	 * @param fileName
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/img_selector")
	public ModelAndView selectPicTiles(String fileName, HttpServletRequest request) throws Exception {
		List<String> urls = new ArrayList<String>();
		List<HelpCenterImg> images = helpService.getHelpImageNameList(fileName);
		for (HelpCenterImg helpCenterImg : images) {
			urls.add(ftp.getDomainName() + "help/" + helpCenterImg.getPicName());
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("urls", urls);
		model.put("fileName", fileName);
		return new ModelAndView(IMG_SELECTOR_URL, model);
	}
	
	/**
	 * 加载图片上传控件
	 * 
	 * @param replacement 被替换图片名称
	 * @param editFilename 是否验证图片名称
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/upload/ready")
	public ModelAndView readyUploadCommodityPic(String replacement, String editFilename, ModelMap modelMap, HttpServletRequest request) {
		modelMap.addAttribute("replacement", replacement);
		modelMap.addAttribute("editFilename", editFilename);
		modelMap.addAttribute("jsessionId", request.getSession().getId());
		return new ModelAndView(IMG_SWFUPLOAD_URL, modelMap);
	}
}
