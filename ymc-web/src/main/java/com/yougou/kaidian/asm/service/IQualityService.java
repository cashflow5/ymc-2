package com.yougou.kaidian.asm.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.asm.model.OrderProductQuantityVo;
import com.yougou.kaidian.asm.vo.QualityNotPassQueryVo;
import com.yougou.kaidian.asm.vo.QualityNotPassResultVo;
import com.yougou.kaidian.asm.vo.QualitySaveVo;
import com.yougou.kaidian.asm.vo.QualityVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;

/**
 * 质检重构后使用该 qualityService
 * 
 * @author huang.tao
 *
 */
public interface IQualityService {
	
	PageFinder<Map<String,Object>> queryQualityListByVo(QualityQueryVo vo, Query query) ;
	
	/**
	 * queryQualityAllListByVo:查询指定条件下的质检 
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6
	 */
	List<Map<String,Object>> queryQualityAllListByVo(QualityQueryVo vo);
	/**
	 * queryQualityAllListByVo:分批查询 
	 * @author li.n1 
	 * @param vo
	 * @param size
	 * @return 
	 * @since JDK 1.6
	 */
	List<Map<String,Object>> batchQuery(QualityQueryVo vo,int size);
	
	/**
	 * 通过订单号查询质检明细(退换货和拒收)
	 * 
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> queryQualityDetail(QualityQueryVo vo);
	
	/**
	 * 保存质检以及质检明细
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	boolean saveQualityInfo(QualitySaveVo vo) throws Exception;
	
	/**
	 * 保存质检以及质检明细(退货)
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	Map<String,String> saveReturnQualityInfo(QualitySaveVo vo) throws Exception;
	
	/**
	 * 获取物流公司列表
	 */
	public List<Map<String, Object>> getExpressInfo();
	
    /**
     * 获取物流公司id和名称对应map
     */
    public Map<String, String> getExpressInfoMap();
	
	   /**
     * 判断是否存在发货信息
     * @param logisticsCode
     * @param lstExpressCode
     * @return
     */
    public boolean isHadExistsOutStoreRecord(String logisticsCode, List<String> lstExpressCode);
    
	   /**
     * 判断是否存在发货信息
     * @param logisticsCode
     * @return
     */
    public boolean isHadExistsOutStoreRecord(List<String> lstExpressCode);
    
    
    /**
     * 查询质检不通过的后续流程
     * 
     * @param QualityNotPassQueryVo
     * @param Query
     * @return
     */
    PageFinder<QualityNotPassResultVo> queryQualityNotPassListByVo(QualityNotPassQueryVo vo, Query query);
    
    List<OrderProductQuantityVo> queryOrderAsmInfo(QualityVo qualityVo);
    
	/**
	 * 校验订单是否已经质检
	 * 
	 * @param orderSub
	 * @return
	 */
	public boolean checkHasQAByOrderNo(OrderSub orderSub);
}
