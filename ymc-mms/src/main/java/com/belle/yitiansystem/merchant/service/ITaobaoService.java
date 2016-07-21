package com.belle.yitiansystem.merchant.service;

import java.util.List;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAccessTrackVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAppkeyVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAuthorizeVo;
import com.yougou.merchant.api.common.Query;

/**
 * 淘宝商品导入service接口
 */
public interface ITaobaoService {
	/**
	 * 淘宝商家授权管理
	 */
	public PageFinder<TaobaoAuthorizeVo> getTaobaoAuthorizeList(TaobaoAuthorizeVo vo, Query query);

	public List<TaobaoAuthorizeVo> getTaobaoAuthorizeList(TaobaoAuthorizeVo vo);
	/**
	 * 根据id检索
	 */
	public TaobaoAuthorizeVo getTaobaoAuthorizeById(TaobaoAuthorizeVo vo);
	/**
	 * 根据id删除
	 */
	public int deleteTaobaoAuthorizeById(TaobaoAuthorizeVo vo);
	//获取已审核或者已授权的list
	public List<TaobaoAuthorizeVo> getTaobaoAuthorizedList(TaobaoAuthorizeVo vo);

	/**
	 * 淘宝商家授权更新
	 */
	public Boolean updateTaobaoAuthorize(TaobaoAuthorizeVo vo);

	/**
	 * 淘宝api调用轨迹查询
	 */
	public PageFinder<TaobaoAccessTrackVo> getTaobaoAccessTrackList(TaobaoAccessTrackVo vo, Query query);

	/**
	 * 淘宝topAppkey管理
	 */
	public PageFinder<TaobaoAppkeyVo> getTaobaoAppkeyList(TaobaoAppkeyVo vo, Query query);
	/**
	 * 获取单个淘宝topAppkey
	 */
	public TaobaoAppkeyVo getTaobaoAppkeyByAppkey(TaobaoAppkeyVo vo);
	/**
	 * 查询可用的topAppkey
	 */
	public List<TaobaoAppkeyVo> getTaobaoAppkeyList(TaobaoAppkeyVo vo);

	/**
	 * 淘宝topAppkey更新
	 */
	public Boolean updateTaobaoAppkey(TaobaoAppkeyVo vo);
	
	/**
	 * 淘宝topAppkey插入
	 */
	public Boolean addTaobaoAppkey(TaobaoAppkeyVo vo);
}
