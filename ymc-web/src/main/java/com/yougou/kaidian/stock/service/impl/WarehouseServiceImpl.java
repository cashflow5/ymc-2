/**
 * 
 */
package com.yougou.kaidian.stock.service.impl;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.stock.dao.StockMapper;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

/**
 * @author huang.tao
 *
 */
@Service
public class WarehouseServiceImpl implements IWarehouseService {
	
	private final static Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
	
	@Resource
	private IWarehouseCacheService warehouseCacheService;

    @Resource
    private StockMapper stockMapper;
	   
	@Override
	public String queryWarehouseCodeByMerchantCode(String merchantCode) throws Exception {
		String wareHouseCode = null;
		
		//通过商家编号获取仓库
		Map<String, String> wareHouseMap = warehouseCacheService.getWarehouseByMerchantCode(merchantCode);
		if (MapUtils.isNotEmpty(wareHouseMap)) {
			for (String wareHouseCodeTmp : wareHouseMap.keySet()) {
				wareHouseCode = wareHouseCodeTmp;
				break;
			}						
		}else{
			wareHouseCode = stockMapper.queryWarehouseCodeByMerchantCode(merchantCode);// 从本地表查询仓库编码
		}
		
		if (StringUtils.isBlank(wareHouseCode)) {
			logger.error("商家：{}| 还未设置仓库编码.", merchantCode);
			throw new Exception(MessageFormat.format("商家：{0}| 还未设置仓库编码.", merchantCode));
		}
		
		return wareHouseCode;
	}

}
