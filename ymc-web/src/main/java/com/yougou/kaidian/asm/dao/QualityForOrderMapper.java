/**
 * 
 */
package com.yougou.kaidian.asm.dao;

import java.util.List;

import com.yougou.kaidian.asm.model.OrderProductQuantityVo;
import com.yougou.kaidian.asm.vo.QualityVo;



/**
 * 质检重构  查询
 * 
 * @author huang.tao
 *
 */
public interface QualityForOrderMapper {
	
	List<OrderProductQuantityVo> queryOrderAsmInfo(QualityVo qualityVo);
}
