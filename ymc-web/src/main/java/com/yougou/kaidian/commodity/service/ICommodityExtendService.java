package com.yougou.kaidian.commodity.service;

import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.common.commodity.pojo.CommodityExtend;

public interface ICommodityExtendService {
	
	/**
	 * 根据商品编号查询商品扩展表记录
	 * @param commodityNo 商品编号
	 * @return 商品扩展表
	 */
	CommodityExtend getCommodityExtendByCommodityNo(String commodityNo) ;
	
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
	 * insertCommodityExtendAndLog: 插入商品扩展表数据，并且记录检测敏感词的日志
	 * @author li.n1 
	 * @param submitVo 商品提交信息
	 * @param operater 操作人
	 * @param comment 记录日志的备注
	 * @throws Exception 
	 * @return true 插入成功  false 插入失败 
	 * @since JDK 1.6 
	 * @date 2015-9-16 下午12:13:16
	 */
	boolean insertCommodityExtendAndLog(CommoditySubmitNewVo submitVo, 
			String operater,String comment) throws Exception;
	
	/**
	 * insertCommodityExtendAndLog: 插入商品扩展表数据，并且记录检测敏感词的日志
	 * @author li.n1 
	 * @param submitVo 商品提交信息
	 * @param operater 操作人
	 * @param comment 记录日志的备注
	 * @param sensitiveWord 敏感词
	 * @param isCheck 
	 * true 只检测sensitiveWord参数，不调用接口检测  
	 * false 实际调用insertCommodityExtendAndLog(CommoditySubmitNewVo submitVo, 
			String operater,String comment)
	 * @throws Exception 
	 * @return true 插入成功  false 插入失败 
	 * @since JDK 1.6 
	 * @date 2015-9-16 下午12:13:16
	 */
	boolean insertCommodityExtendAndLog(CommoditySubmitNewVo submitVo, 
			String operater,String comment, String sensitiveWord, boolean isCheck) throws Exception;
	/**
	 * getCommodityExtendCountByCommodityNo: 根据商品编码获取商品扩展信息记录条数
	 * @author li.n1 
	 * @param commodityNo 商品编码
	 * @return int 记录条数
	 * @since JDK 1.6 
	 * @date 2015-9-17 上午10:34:44
	 */
	int getCommodityExtendCountByCommodityNo(String commodityNo);

}
