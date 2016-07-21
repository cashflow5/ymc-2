package com.yougou.kaidian.taobao.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoCommodityImportInfo;
import com.yougou.kaidian.taobao.model.TaobaoItem;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;
import com.yougou.kaidian.taobao.model.TaobaoItemProp;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValue;
import com.yougou.kaidian.taobao.vo.ErrorVo;
import com.yougou.pc.model.commodityinfo.Commodity;



public interface ITaobaoItemService {
	
	List<TaobaoCommodityImportInfo> importTaobaoItemDataToYougou(String merchantCode, String extend_idArry[],String loginName,String isAudit);
	
	/**
	 * 检查导入优购是否含有敏感词
	 * @param extend_idArry
	 * @return
	 */
	Map<String,String> checkTaobaoImportSensitiveWords(String extend_idArry[]);
	
	PageFinder<TaobaoItemExtendDto> getTaobaoItem(Map<String,Object> params, Query query);
	
	TaobaoItem getTaobaoItemByNumIid(long num_iid);

	void deleteTaobaoItemExtend(String idStr, String merchantCode)
			throws BusinessException;
	
	void updateTaobaoItemExtend(String idStr, String merchantCode, String deleteOrReduct)
			throws BusinessException;
	
	/**
	 * 彻底删除淘宝商品   物理删除
	 * @param idStr
	 * @param merchantCode
	 */
	void deleteTaobaoItemForever(String[] idStr,String merchantCode) throws BusinessException;
	
	Map<String, Object> getTaobaoItem4Update(String merchantCode,
			long numIid, String extendId) throws BusinessException;

	void checkBindInfo(long numIid, String merchantCode)
			throws BusinessException;
	
	void updateByPrimaryKeySelective(TaobaoItem taobaoItem);

	List<ErrorVo> updateTaobaoItem(Map<String, Object> params,
			String merchantCode, HttpServletRequest request) throws BusinessException;
	
	XSSFWorkbook getItemBook(String itemIds,String merchantCode) throws BusinessException;
	
	Map<String, Object> exportExcel(File file, String merchantCode) throws BusinessException;
	
	TaobaoItemProp getTaobaoItemProp(long pid)throws BusinessException;
	
	TaobaoItemPropValue getTaobaoItemPropValue(long vid)throws BusinessException;
	
	void delTaoBaoItemByCommodityNo(String merchantCode,String commodityNo) throws BusinessException;
	
	Commodity getExtendFormatCommodity(String extendId,long numIid)  throws Exception;
}
