package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.TaobaoBrand;
import com.yougou.kaidian.taobao.model.TaobaoYougouBrand;

public interface TaobaoBrandMapper {

	/**
	 * 批量插入淘宝品牌属性信息
	 * @param lstTaobaoBrand
	 */
	public void insertTaobaoBrandList(List<TaobaoBrand> lstTaobaoBrand);
	
	/**
	 * 根据淘宝pid、vid获取淘宝品牌属性信息
	 * @param pid
	 * @param vid
	 * @return
	 */
	public TaobaoBrand getTaobaoBrandByPidVid(@Param("pid")Long pid, @Param("vid")Long vid);
	
	/**
	 * 根据淘宝品牌bid查询优购品牌对应关系
	 * @param taobao_bid
	 * @return
	 */
	public TaobaoYougouBrand getBrandMapperByBid(@Param("taobao_bid")String taobao_bid);
	
	/**
	 * 获取所有淘宝品牌属性信息
	 * @return
	 */
	public List<String> getTaobaoBrandPV();
	
	/**
	 * 批量插入淘宝优购中间品牌绑定信息
	 * @param lstTaobaoYougouBrand
	 */
	public void insertTaobaoYougouBrandList(List<TaobaoYougouBrand> lstTaobaoYougouBrand);
	
	/**
	 * 插入淘宝优购中间品牌绑定信息
	 * @param taobaoYougouBrand
	 */
	public void insertTaobaoYougouBrand(TaobaoYougouBrand taobaoYougouBrand);
	
	/**
	 * 根据商家编码、淘宝主账号nick_id和bid（中间绑定表id）获取淘宝优购品牌绑定信息
	 * @param merchantCode
	 * @param nickId
	 * @param bid
	 * @return
	 */
	public TaobaoYougouBrand getTaobaoYougouBrandByBid(@Param("merchantCode")String merchantCode, @Param("nickId")Long nickId, @Param("bid")String bid);

	public int selectTaobaoYougouBrandCount(@Param("params") Map<String, Object> map);
	/**
	 * 查询品牌绑定列表
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<TaobaoYougouBrand> selectTaobaoYougouBrandList(
			@Param("params") Map<String, Object> map, RowBounds rowBounds);

	/**
	 * 批量绑定品牌
	 * 
	 * @param lstTaobaoBrand
	 */
	public void updateTaobaoYougouBrand(TaobaoYougouBrand brand);
	
	/**
	 * 根据商家编码、淘宝店铺主账号ID、淘宝pid、vid判定是否存在有优购绑定品牌
	 * @param merchantCode
	 * @param nickId
	 * @param pid
	 * @param vid
	 * @return
	 */
	public String getYougouBrandNoByTaobaoNickIdAndPidVid(@Param("merchantCode")String merchantCode, @Param("nickId")Long nickId, @Param("pid")Long pid, @Param("vid")Long vid);

	
	public void updateTaobaoYougouBrandByTaobaoBid(TaobaoYougouBrand brand);
}
