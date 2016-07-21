package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.belle.yitiansystem.merchant.model.vo.TaobaoAccessTrackVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAppkeyVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAuthorizeVo;

public interface TaobaoMapper {

	/**
	 * 查找淘宝授权数据
	 * 
	 * @param TaobaoAuthorizeVo
	 * @param rowBounds
	 * @return
	 */
	public List<TaobaoAuthorizeVo> queryTaobaoAuthorizeList(TaobaoAuthorizeVo vo, RowBounds rowBounds);

	public List<TaobaoAuthorizeVo> queryTaobaoAuthorizeList(TaobaoAuthorizeVo vo);

	// 获取已审核或者已授权的list
	public List<TaobaoAuthorizeVo> queryTaobaoAuthorizedList(TaobaoAuthorizeVo vo);
	
	/**
	 * 根据id检索
	 */
	public TaobaoAuthorizeVo queryTaobaoAuthorizeById(TaobaoAuthorizeVo vo);
	/**
	 * 淘宝商家授权删除
	 * 
	 * @param TaobaoAuthorizeVo
	 * @return int
	 */
	public int deleteTaobaoAuthorizeById(TaobaoAuthorizeVo vo);
	/**
	 * 查找淘宝授权数据
	 * 
	 * @param TaobaoAuthorizeVo
	 * @return
	 */
	public int queryTaobaoAuthorizeListCount(TaobaoAuthorizeVo vo);

	/**
	 * 淘宝商家授权更新
	 * 
	 * @param TaobaoAuthorizeVo
	 * @return Boolean
	 */
	public int updateTaobaoAuthorize(TaobaoAuthorizeVo vo);

	/**
	 * 查找淘宝api调用轨迹数据
	 * 
	 * @param TaobaoAccessTrackVo
	 * @param rowBounds
	 * @return
	 */
	public List<TaobaoAccessTrackVo> queryTaobaoAccessTrackList(TaobaoAccessTrackVo vo, RowBounds rowBounds);

	/**
	 * 查找淘宝api调用轨迹数据
	 * 
	 * @param TaobaoAccessTrackVo
	 * @return
	 */
	public int queryTaobaoAccessTrackListCount(TaobaoAccessTrackVo vo);

	/**
	 * 查找淘宝topAppkey数据
	 * 
	 * @param TaobaoAppkeyVo
	 * @param rowBounds
	 * @return
	 */
	public List<TaobaoAppkeyVo> queryTaobaoAppkeyList(TaobaoAppkeyVo vo, RowBounds rowBounds);

	// 根据appkey检索
	public TaobaoAppkeyVo queryTaobaoAppkeyByAppkey(TaobaoAppkeyVo vo);

	/**
	 * 查询可用的topAppkey
	 * 
	 * @param TaobaoAppkeyVo
	 * @return
	 */
	public List<TaobaoAppkeyVo> queryTaobaoAppkeyList(TaobaoAppkeyVo vo);

	/**
	 * 查找淘宝Appkey数据
	 * 
	 * @param TaobaoAppkeyVo
	 * @return 查询件数
	 */
	public int queryTaobaoAppkeyListCount(TaobaoAppkeyVo vo);

	/**
	 * 淘宝Appkey更新
	 * 
	 * @param TaobaoAppkeyVo
	 * @return 更新件数
	 */
	public int updateTaobaoAppkey(TaobaoAppkeyVo vo);

	/**
	 * 插入淘宝Appkey数据
	 * 
	 * @param TaobaoAppkeyVo
	 * @return 插入件数
	 */
	public int insertTaobaoAppkey(TaobaoAppkeyVo vo);
}
