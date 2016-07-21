/**
 * 
 */
package com.yougou.kaidian.asm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.asm.vo.AsmQcDetailVo;
import com.yougou.kaidian.asm.vo.AsmQcVo;
import com.yougou.kaidian.asm.vo.QualityNotPassQueryVo;
import com.yougou.kaidian.asm.vo.QualityNotPassResultVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;



/**
 * 质检重构  查询
 * 
 * @author huang.tao
 *
 */
public interface QualityMapper {
	/**
	 * 通过订单号查询质检明细(退换货)
	 * 
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> queryQualityDetailFromReturn(QualityQueryVo vo);
	
	/**
	 * 通过订单号查询质检明细(拒收)
	 * 
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> queryQualityDetailFromInspection(QualityQueryVo vo);
	
	//================================  质检tamll风格   =====================================//
	
	/**
	 * 新质检查询
	 * 
	 * @param vo
	 * @param rowBounds
	 * @return
	 */
	List<AsmQcVo> queryAsmQcListByVo(QualityQueryVo vo, RowBounds rowBounds);
	
	List<AsmQcVo> queryAsmQcListByTime(QualityQueryVo vo);
	
	int queryAsmQcListByVoCount(QualityQueryVo vo);
	
	/**
	 * 获取订单质检明细信息
	 * 
	 * @param orderNo
	 * @return
	 */
	List<AsmQcDetailVo> queryAsmQcDetailsByOrderNo(String orderNo);
	
    /**
     * 查询质检不通过列表
     * 
     * @param vo
     * @param rowBounds
     * @return
     */
    List<QualityNotPassResultVo> queryQualityNotPassListByVo(QualityNotPassQueryVo vo, RowBounds rowBounds);

    int queryQualityNotPassListByVoCount(QualityNotPassQueryVo vo);
    
    /**
     * 根据老的订单号，查询是否存在换货单号
     * 
     * @param vo
     * @param orderSubNo
     * @return
     */
    String queryChangeOrderSubNoByOldOrderSubNo(String orderSubNo);
}
