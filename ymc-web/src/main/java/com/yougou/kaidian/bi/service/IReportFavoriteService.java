package com.yougou.kaidian.bi.service;

import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.common.base.Query;

/**
 * 数据报表-收藏夹操作服务接口
 * @author zhang.f1
 *
 */
public interface IReportFavoriteService {
	/**
	 * 新增收藏归类
	 * @param params{id:xx,classify_name:xx,merchant_code:xx,
	 * 				login_name:huang.t1}
	 * @throws Exception
	 */
	public void addFavoriteClassify(Map params) throws Exception;
	
	/**
	 * 修改收藏归类
	 * @param params{id:xx,classify_name:xx}
	 * @throws Exception
	 */
	public void updateFavoriteClassify(Map params) throws Exception;
	
	/**
	 * 删除收藏归类
	 * @param params
	 * @throws Exception
	 */
	public void deleteFavoriteClassify(String classifyId) throws Exception;
	
	/**
	 * 查询收藏归类列表
	 * @param params：{merchant_code:111,login_name:huang.t1}
	 * @throws Exception
	 */
	public List<Map> queryFavoriteClassify(Map params) throws Exception;
	
	/**
	 * 查询收藏归类排序数值，主要用于新增归类时order_by 字段
	 * @param params：{merchant_code:111,login_name:huang.t1}
	 * @throws Exception
	 */
	public int queryClassifyOrderBy(Map params) throws Exception;
	
	
	/**
	 * 新增收藏商品
	 * @param params：{id:xx,commodity_no:xx,merchant_code:111,login_name:huang.t1}
	 * @throws Exception
	 */
	public void addFavoriteCommodity(Map params) throws Exception;
	
	/**
	 * 取消收藏商品
	 * @param params：{commodity_no:xx,merchant_code:111,login_name:huang.t1}
	 * @throws Exception
	 */
	public void deleteFavoriteCommodity(Map params) throws Exception;
	
	/**
	 * 根据收藏记录ID取消收藏商品
	 * @param params：{id:xx}
	 * @throws Exception
	 */
	public void deleteFavoriteCommodityById(String id) throws Exception;
	
	/**
	 * 查询收藏商品，(已收藏，未归类，已归类)
	 * @param params：{merchant_code:111,login_name:huang.t1,classify_status:N,classify_id:xxx}
	 * classify_status,classify_id 为空查已收藏，classify_status=N，查未归类，classify_id 非空查此归类
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryFavoriteCommodity(Map params) throws Exception;
	
	
	/**
	 * 查询收藏商品数量，用于判断某商品是否被收藏
	 * @param params：{merchant_code:111,login_name:huang.t1,commodity_no:xxx}
	 * @return
	 * @throws Exception
	 */
	public Integer queryFavoriteCommodityCount(Map params) throws Exception;
	
	/**
	 * 新增收藏商品所属归类(单个商品)
	 * @param params：{fvr_commodity_id：xxx,classify_ids:[{id:aaa,classify_id：xxx1},{},....]}
	 * @throws Exception
	 */
	public void addFavoriteCommodityClassify(Map params) throws Exception;
	
	/**
	 * 新增收藏商品所属归类(批量商品归类)
	 * @param params：{comClaList:[{id:xxx,fvr_commodity_id:xxxx,classify_id:xxx},{},....],
	 * 						fvr_commodity_ids:[fvr_commodity_id1,fvr_commodity_id2,...]}
	 * @throws Exception
	 */
	public void batchAddFavoriteCommodityClassify(Map params) throws Exception;
	
	/**
	 * 删除收藏商品所属归类(单个商品)
	 * @param 
	 * @throws Exception
	 */
	public void deleteFavoriteCommodityClassify(String fvrCommodityId) throws Exception;
	
	/**
	 * 删除收藏商品所属归类(批量商品)
	 * @param params：{fvr_commodity_ids:[fvr_commodity_id1,fvr_commodity_id2,...]}
	 * @throws Exception
	 */
	public void batchDeleteFavoriteCommodityClassify(Map params) throws Exception;
	
	
	/**
	 * 查询收藏夹商品信息
	 * @param query
	 * @param map
	 * @return
	 */
	public PageFinder<Map<String, Object>> queryFavoriteClassifyInfo(
			Query query, Map<String,Object> map)throws Exception;
	/**
	 * 查询导出excel
	 * @param query
	 * @param map
	 * @return
	 */
	public  List<Map<String, Object>>  queryExportFavoriteInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 删除收藏商品所属归类(批量商品)
	 * @param params：{fvr_commodity_ids:[fvr_commodity_id1,fvr_commodity_id2,...]}
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryFavoriteCommodityInfo(Map params) throws Exception;
	
	/**
	 * 收藏入口查询信息
	 * @param params：{fvr_commodity_ids:[fvr_commodity_id1,fvr_commodity_id2,...]}
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryFavoriteInfo(Map params) throws Exception;
	
	
	
	
	
	
}
