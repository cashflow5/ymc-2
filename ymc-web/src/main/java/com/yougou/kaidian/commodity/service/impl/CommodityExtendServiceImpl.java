package com.yougou.kaidian.commodity.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.dao.CommodityExtendMapper;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.service.ICommodityExtendService;
import com.yougou.kaidian.common.commodity.pojo.CommodityExtend;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.pc.model.sensitive.SensitiveCheckLog;

@Service
public class CommodityExtendServiceImpl implements ICommodityExtendService {

	private static final Logger logger = LoggerFactory.getLogger(CommodityExtendServiceImpl.class);

	@Resource
	private CommodityExtendMapper commodityExtendMapper;
	@Resource
	private CommodityComponent commodityComponent;
	
	@Override
	public CommodityExtend getCommodityExtendByCommodityNo(String commodityNo) {
		return commodityExtendMapper.getCommodityExtendByCommodityNo(commodityNo);
	}
	
	@Override
	public void insertCommodityExtend(CommodityExtend commodityExtend) {
		commodityExtendMapper.insertCommodityExtend(commodityExtend);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertCommodityExtendAndLog(
			CommoditySubmitNewVo submitVo, String operater,String comment) 
			throws Exception {
		boolean flag = false;
		StringBuilder sb = new StringBuilder(100);
		try{
			if(StringUtils.isNotBlank(submitVo.getCommodityName())){
				sb.append(submitVo.getCommodityName());
			}
			if(StringUtils.isNotBlank(submitVo.getSellingPoint())){
				sb.append(";"+submitVo.getSellingPoint());
			}
			if(StringUtils.isNotBlank(submitVo.getProdDesc())){
				sb.append(";"+submitVo.getProdDesc());
			}
			String content = sb.toString();
			String sensitiveWord = commodityComponent.checkSensitiveWord(null, content);
			//添加商品一定是插入扩展信息，修改商品信息就未必了
			if(StringUtils.isNotBlank(sensitiveWord)&&(StringUtils.isBlank(submitVo.getCommodityId())
					|| this.getCommodityExtendCountByCommodityNo(submitVo.getCommodityNo())<=0)){
				CommodityExtend commodityExtend = new CommodityExtend();
				commodityExtend.setCommodityNo(submitVo.getCommodityNo());
				commodityExtend.setSensitiveWord(sensitiveWord);
				commodityExtend.setId(UUIDGenerator.getUUID());
				commodityExtendMapper.insertCommodityExtend(commodityExtend);
			}else{
				//修改扩展信息表的敏感词
				commodityExtendMapper.updateCommodityExtend(new CommodityExtend(submitVo.getCommodityNo(),sensitiveWord));
			}
			//不管有没检测出敏感词，都调用接口记录日志
			SensitiveCheckLog log = new SensitiveCheckLog();
			log.setCommodityNo(submitVo.getCommodityNo());
			log.setContent(content);
			log.setFollowOperate(submitVo.getFollowOperate());
			log.setOperatorPerson(operater);
			/**
			 * 操作类型注意：（可以根据商品的id判断）
			 * 0-添加商品,1-修改商品
			 * 保存商品、商品导入成功操作类型为：添加商品;
			 * 修改和重新编辑商品后操作类型为：修改商品;
			 * 商品提交审核操作类型为：添加商品，操作备注为：商品提交审核;
			 */
			if(CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.
					equals(submitVo.getIsSaveSubmit())){
				//如果是保存并提交审核
				log.setOperateType(SensitiveCheckLog.OperateType.ADD.getValue());
				log.setComment("商品提交审核");
			}else{
				//只提交保存
				log.setOperateType((short) (StringUtils.isNotBlank(submitVo.getCommodityId())? 
						SensitiveCheckLog.OperateType.UPDATE.getValue():SensitiveCheckLog.OperateType.ADD.getValue()));
				log.setComment(comment);
			}
			log.setSensitive(StringUtils.isNotBlank(sensitiveWord)?true:false);
			log.setSensitiveWord(sensitiveWord);
			log.setStyleNo(submitVo.getStyleNo());
			log.setSupplierCode(submitVo.getSupplierCode());
			commodityComponent.insertSensitiveWordCheckLogByOne(log);
			flag = true;
		}catch(Exception e){
			logger.error("插入商品扩展表数据与日志报错，错误信息：",e);
			throw new Exception();
		}
		return flag;
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertCommodityExtendAndLog(CommoditySubmitNewVo submitVo,
			String operater, String comment, String sensitiveWord,
			boolean isCheck) throws Exception {
		if(isCheck){
			boolean flag  = false;
			try{
				String content = submitVo.getCommodityName()+";"+submitVo.getSellingPoint()+";"+submitVo.getProdDesc();
				if(StringUtils.isNotBlank(sensitiveWord)){
					//添加商品一定是插入扩展信息，修改商品信息就未必了
					if(StringUtils.isBlank(submitVo.getCommodityId())
							|| this.getCommodityExtendCountByCommodityNo(submitVo.getCommodityNo())<=0){
						CommodityExtend commodityExtend = new CommodityExtend();
						commodityExtend.setCommodityNo(submitVo.getCommodityNo());
						commodityExtend.setSensitiveWord(sensitiveWord);
						commodityExtend.setId(UUIDGenerator.getUUID());
						commodityExtendMapper.insertCommodityExtend(commodityExtend);
					}else{
						//修改扩展信息表的敏感词
						commodityExtendMapper.updateCommodityExtend(new CommodityExtend(submitVo.getCommodityNo(),sensitiveWord));
					}
				}
				//不管有没检测出敏感词，都调用接口记录日志
				SensitiveCheckLog log = new SensitiveCheckLog();
				log.setCommodityNo(submitVo.getCommodityNo());
				log.setContent(content);
				log.setFollowOperate(submitVo.getFollowOperate());
				log.setOperatorPerson(operater);
				/**
				 * 操作类型注意：（可以根据商品的id判断）
				 * 0-添加商品,1-修改商品
				 * 保存商品、商品导入成功操作类型为：添加商品;
				 * 修改和重新编辑商品后操作类型为：修改商品;
				 * 商品提交审核操作类型为：添加商品，操作备注为：商品提交审核;
				 */
				if(CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.
						equals(submitVo.getIsSaveSubmit())){
					//如果是保存并提交审核
					log.setOperateType(SensitiveCheckLog.OperateType.ADD.getValue());
					log.setComment("商品提交审核");
				}else{
					//只提交保存
					log.setOperateType((short) (StringUtils.isNotBlank(submitVo.getCommodityId())? 
							SensitiveCheckLog.OperateType.UPDATE.getValue():SensitiveCheckLog.OperateType.ADD.getValue()));
					log.setComment(comment);
				}
				log.setSensitive(StringUtils.isNotBlank(sensitiveWord)?true:false);
				log.setSensitiveWord(sensitiveWord);
				log.setStyleNo(submitVo.getStyleNo());
				log.setSupplierCode(submitVo.getSupplierCode());
				commodityComponent.insertSensitiveWordCheckLogByOne(log);
				flag = true;
			}catch(Exception e){
				logger.error("插入商品扩展表数据与日志报错，错误信息：",e);
				throw new Exception();
			}
			return flag;
		}else{
			return insertCommodityExtendAndLog(submitVo, operater,comment);
		}
	}
	
	
	@Override
	public void updateCommodityExtend(CommodityExtend commodityExtend) {
		commodityExtendMapper.updateCommodityExtend(commodityExtend);
	}
	
	@Override
	public int getCommodityExtendCountByCommodityNo(String commodityNo) {
		return commodityExtendMapper.getCommodityExtendCountByCommodityNo(commodityNo);
	}

}
