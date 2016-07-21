package com.belle.infrastructure.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.belle.yitiansystem.systemmgmt.model.pojo.PermissionDataObject;

/**
 * 权限处理工具类
 * 
 * @author zhubin date：2011-12-23 下午1:34:58
 */
public class PermissionHandelUtil {

	private static PermissionHandelUtil instance = null;

	// 获得权限处理工具类实例
	public static synchronized PermissionHandelUtil getInstance() {

		if (instance == null) {
			instance = new PermissionHandelUtil();
		}

		return instance;
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public static HashMap<String, Object> getSession() {
		/*String sessionId = MemcachedSessionFilter.getSesstionId();
		IMemcachedCache cache = Cache.getSingleCacheClient("commonclient2");
		HashMap<String, Object> sessionMap = null;
		if (cache != null) {
			sessionMap = (HashMap<String, Object>) cache.get(sessionId);
			if (sessionMap != null) {
				// 如果品牌和分类构造数据session为kong就调用初始化构造方法
				if (sessionMap.get("commodityCatb2cPermission") == null
						&& sessionMap.get("commodityBrandPermission") == null) {
					// 构造
					getInstance().getCommodityPermissionList(sessionMap);
				}
			}
		}*/
		
		return new HashMap<String,Object>();
		
	}

	
	/**
	 * 从session中获取数据权限
	 * @param req
	 * @return
	 */
	public static List<PermissionDataObject> getOrderPermissionList(HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<PermissionDataObject> orderPermissionList = (List<PermissionDataObject>) req.getSession().getAttribute(
				"orderPermission");
		return orderPermissionList;
	}
	
//	/**
//	 * 获得订单权限列表
//	 * 
//	 * @return
//	 */
//	public static List<PermissionDataObject> getOrderPermissionList() {
//
//		List<PermissionDataObject> orderPermissionList = null;
//		HashMap<String, Object> session = getSession();
//		if (session != null) {
//			if (session.get("orderPermission") != null) {
//				orderPermissionList = (List<PermissionDataObject>) getSession()
//						.get("orderPermission");
//
//			}
//		}
//
//		return orderPermissionList;
//	}

	/**
	 * 获得商品权限列表
	 * 
	 * @return
	 */
	public List<PermissionDataObject> getCommodityPermissionList(
			Map<String, Object> sessionMap) {
		@SuppressWarnings("unchecked")
		List<PermissionDataObject> commodityPermissionList = (List<PermissionDataObject>) sessionMap
				.get("commodityPermission");

		// 构造object[]
		Object[] object = null;
		if (commodityPermissionList != null
				&& commodityPermissionList.size() > 0) {
			object = new Object[commodityPermissionList.size()];
			int temp = 0;
			for (Iterator<PermissionDataObject> iterator = commodityPermissionList
					.iterator(); iterator.hasNext();) {
				PermissionDataObject permissionDataObject = iterator.next();
				object[temp] = permissionDataObject.getDataName();
				temp++;
			}
		}
		sessionMap.put("commodityCatb2cPermission", object);

		List<Object> brandList = new ArrayList<Object>();
		Object[] brandObj = null;
		if (commodityPermissionList != null
				&& commodityPermissionList.size() > 0) {
			for (Iterator<PermissionDataObject> iterator = commodityPermissionList
					.iterator(); iterator.hasNext();) {
				PermissionDataObject permissionDataObject = iterator.next();
				String[] brandStr = permissionDataObject.getDataValue().trim()
						.split(",");
				for (int i = 0; i < brandStr.length; i++) {
					brandList.add(brandStr[i]);
				}
			}
			if (brandList.size() > 0) {
				brandObj = new Object[brandList.size()];
				int tempSize = brandList.size();
				for (int i = 0; i < tempSize; i++) {
					brandObj[i] = brandList.get(i);
				}
			}
		}
		sessionMap.put("commodityBrandPermission", brandObj);

		return commodityPermissionList;
	}

	/**
	 * 获得商品分类权限列表
	 * 
	 * @return
	 */
	public static Object[] getCommodityCatb2cPermissionList() {
		Object[] catb2cObj = (Object[]) getSession().get(
				"commodityCatb2cPermission");
		return catb2cObj;
	}

	/**
	 * 获得商品品牌权限列表
	 * 
	 * @return
	 */
	public static Object[] getCommodityBrandPermissionList() {
		Object[] brandObj = (Object[]) getSession().get(
				"commodityBrandPermission");
		return brandObj;

	}

	/**
	 * 获得商品分类权限列表
	 * 
	 * @return
	 */
	public static Object[] getCatb2cStructNamePermissionList() {
		Object[] catb2cObj = (Object[]) getSession().get(
				"commodityCatb2cPermission");

		return catb2cObj;
	}

	/**
	 * 根据品牌组装like语句
	 * 
	 * @author li.sk
	 * @param property
	 *            属性字段
	 * @return Disjunction
	 */
	public static String getBrandNoSql(String property) {
		StringBuffer sbBrandNo = new StringBuffer(1024);
		if (PermissionHandelUtil.getCommodityBrandPermissionList() != null) {
			Object[] brandObj = (Object[]) PermissionHandelUtil.getSession()
					.get("commodityBrandPermission");
			int catlength = brandObj.length;
			if (brandObj != null && catlength > 0) {
				sbBrandNo.append(" AND " + property + " IN(");
				for (int i = 0; i < catlength; i++) {
					if (i == 0) {
						sbBrandNo.append("'" + brandObj[i].toString() + "'");
					} else {
						sbBrandNo.append(",'" + brandObj[i].toString() + "'");
					}
				}
				sbBrandNo.append(")");
			}
		}
		return sbBrandNo.toString();
	}

	/**
	 * 根据一级分类组装like语句
	 * 
	 * @author li.sk
	 * @param property
	 *            属性字段
	 * @return Disjunction
	 */
	public static Disjunction getStructNameDisjunction(String property) {
		Map<String, String> catMap = getCatStructName();
		Disjunction disjunction = Restrictions.disjunction();
		if (PermissionHandelUtil.getCommodityBrandPermissionList() != null) {
			Object[] catb2cObj = (Object[]) PermissionHandelUtil.getSession()
					.get("commodityCatb2cPermission");
			int catlength = catb2cObj.length;
			if (catb2cObj != null && catlength > 0) {
				for (int i = 0; i < catlength; i++) {
					Criterion name = Restrictions.like(property,
							catMap.get(catb2cObj[i]) + "%");
					disjunction.add(name);
				}
			}
		}
		return disjunction;
	}

	/**
	 * 根据一级分类组装like语句
	 * 
	 * @author li.sk
	 * @param property
	 *            属性字段
	 * @return String
	 */
	public static String getStructNameSql(String property) {
		StringBuffer sbStructName = new StringBuffer(1024);
		Map<String, String> catMap = getCatStructName();
		if (PermissionHandelUtil.getCommodityBrandPermissionList() != null) {
			Object[] catb2cObj = (Object[]) PermissionHandelUtil.getSession()
					.get("commodityCatb2cPermission");
			int catlength = catb2cObj.length;
			if (catb2cObj != null && catlength > 0) {
				sbStructName.append(" AND (");
				for (int i = 0; i < catlength; i++) {
					if (i == 0) {
						sbStructName.append(property + " LIKE '"
								+ catMap.get(catb2cObj[i]) + "%'");
					} else {
						sbStructName.append(" OR " + property + " LIKE '"
								+ catMap.get(catb2cObj[i]) + "%'");
					}
				}
				sbStructName.append(")");
			}
		}
		return sbStructName.toString();
	}

	/**
	 * 一级分类名称和struct_name对应
	 * 
	 */
	public static Map<String, String> getCatStructName() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("女鞋", "10");
		map.put("运动", "11");
		map.put("男鞋", "12");
		map.put("童鞋", "13");
		map.put("包", "14");
		map.put("户外休闲", "15");
		map.put("童装", "16");
		map.put("女装", "17");
		map.put("男装", "18");
		return map;
	}
}
