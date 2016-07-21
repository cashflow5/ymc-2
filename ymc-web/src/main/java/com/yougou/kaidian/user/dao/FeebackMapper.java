package com.yougou.kaidian.user.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.user.model.pojo.Feeback;
import com.yougou.kaidian.user.model.pojo.HelpMenu;



/**
 * 意见反馈
 * @author he.wc
 *
 */
public interface FeebackMapper {


	public int addFeeback(Feeback feeback);
	
	public List<Feeback> queryFeebackList(String merchantCode,RowBounds rowBounds);
	
	public List<String> queryFeebackReplyList(String feebackId);
	
	public int queryFeebackCount(String merchantCode);
	
	/**
	 * 查询商家帮助中心菜单
	 * 
	 * @param obj
	 * @return 菜单集合
	 */
	public List<HelpMenu> queryHelpMenuListByObj(HelpMenu obj);
}
