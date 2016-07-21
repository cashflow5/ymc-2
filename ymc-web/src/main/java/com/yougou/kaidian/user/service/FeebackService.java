package com.yougou.kaidian.user.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.user.model.pojo.Feeback;
import com.yougou.kaidian.user.model.pojo.HelpMenu;
import com.yougou.kaidian.user.model.vo.FeebackVo;



/**
 * 意见反馈
 * @author he.wc
 *
 */
public interface FeebackService {
	
	/**
	 * 新建意见反馈信息
	 * @param feeback
	 * @return
	 */
	public int addFeeback(Feeback feeback);
	
	/**
	 * 查询意见反馈信息
	 * @param merchantCode
	 * @param query
	 * @return
	 */
	public PageFinder<FeebackVo> queryFeeback(String merchantCode, Query query); 
	
	/**
	 * 查询帮助中心菜单列表
	 * 
	 * @return
	 */
	public Map<String,List<HelpMenu>> queryHelpMenuListByObj();
}
