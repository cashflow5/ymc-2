package com.yougou.kaidian.user.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.user.model.pojo.SupplierLinkMan;
import com.yougou.kaidian.user.model.vo.SupplierlinkManVo;


/**
 * 商家信息dao�?
 * @author wang.m
 *
 */
public interface SupplierLinkManMapper {

	/**
	 * 根据商家登录名称查询商家联系人信息列�?
	 * @author wang.m
	 * @Date 2012-03-12
	 */
	List<SupplierLinkMan> getSupplierLinkManList(String supplierID,RowBounds rowBounds);
	
	/**
	 * 添加商家联系人信�?
	 * @author wang.m
	 * @param request
	 * @return
	 */
	int add_linkMan(SupplierLinkMan supplierLinkMan);
		
	
	/**
	 * 修改商家联系人信�?
	 * @author wang.m
	 * @param request
	 * @return
	 */
	int update_linkMan(SupplierLinkMan supplierLinkMan);
	
	/**
	 * 根据联系人主键id查询联系人对象信�?
	 * @author wang.m
	 * @param request 
	 * @return
	 */
	 SupplierLinkMan getSupplierLinkManById(String id);
	 
	 /**
	  * 
	  * 获取总记录数
	  */
	 int getLinkManCounts(String id);
	 
	 
		
		/**
		 * 添加联系人时候  判断手机是否存在
		 * @author wang.m
		 * @Date 2012-03-15
		 * 
		 */
		int exitsTelePhone(SupplierlinkManVo vo) ;
		
		/**
		 * 添加联系人时候  判断email是否存在
		 * @author wang.m
		 * @Date 2012-03-15
		 * 
		 */
		int exitsEmail(String email);
		
		/**
		 * 修改联系人时候  判断手机是否存在
		 * @author wang.m
		 * @Date 2012-05-28
		 * 
		 */
		int exitsNewPhone(SupplierlinkManVo vo);
}
