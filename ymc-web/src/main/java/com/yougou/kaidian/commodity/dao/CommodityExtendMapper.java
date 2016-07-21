package com.yougou.kaidian.commodity.dao;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.commodity.pojo.CommodityExtend;

public interface CommodityExtendMapper {
	
	/**
	 * 根据商品编号查询商品扩展表记录
	 * @param commodityNo 商品编号
	 * @return 商品扩展表
	 */
	CommodityExtend getCommodityExtendByCommodityNo(@Param("commodityNo") String commodityNo) ;
	
	/**
	 * 插入商品扩展表数据
	 * @param commodityExtend
	 */
	void insertCommodityExtend(CommodityExtend commodityExtend);
	
	/**
	 * 修改商品扩展表数据
	 * @param commodityExtend
	 */
	void updateCommodityExtend(CommodityExtend commodityExtend);
	
	/**
	 * getCommodityExtendCountByCommodityNo:根据商品编码获取商品扩展信息记录条数 
	 * @author li.n1 
	 * @param commodityNo
	 * @return int 记录条数
	 * @since JDK 1.6 
	 * @date 2015-9-17 上午10:32:20
	 */
	int getCommodityExtendCountByCommodityNo(String commodityNo);
	
}
