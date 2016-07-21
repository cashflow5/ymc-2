package com.belle.yitiansystem.merchant.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.belle.yitiansystem.merchant.dao.mapper.ContractAttachmentMapper;
import com.belle.yitiansystem.merchant.dao.mapper.ContractTradeMarkMapper;
import com.belle.yitiansystem.merchant.dao.mapper.ContractTradeMarkSubMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantContractUpdateHistoryMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierContractMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierVoMapper;
import com.belle.yitiansystem.merchant.model.entity.ContractAttachment;
import com.belle.yitiansystem.merchant.model.entity.ContractAttachmentQuery;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMark;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkQuery;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkSub;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkSubQuery;
import com.belle.yitiansystem.merchant.model.entity.SupplierVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.vo.SupplierInfo;
import com.belle.yitiansystem.merchant.service.IContractNewApi;
import com.yougou.merchant.api.common.Constant;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.YmcResult;

 
/**
 * 合同接口实现类
 * @author le.sm
 *
 */
@Service("contractNewApi")
public class ContractNewApiImpl implements IContractNewApi {
	
	private static final Logger logger = Logger.getLogger(ContractNewApiImpl.class);
//	@Resource
//	private MerchantMapper supplierMapper;
	@Resource
	private SupplierVoMapper supplierVoMapper;
	@Resource
	private SupplierContractMapper supplierContractMapper;
	@Resource
	private ContractAttachmentMapper contractAttachmentMapper;
	@Resource
	private ContractTradeMarkMapper  contractTradeMarkMapper;
	@Resource
	private ContractTradeMarkSubMapper contractTradeMarkSubMapper;
	@Resource
	private MerchantContractUpdateHistoryMapper merchantContractUpdateHistoryMapper;
	 
	/***
	 * status  传递的状态是需要改变的状态 ，如： 审核通过就传审核通过的状态，被拒就传被拒的状态。
	 */
	@Override
	
	public YmcResult auditContract(String merchantCode, String contractId, String status,String operatorName) {
		YmcResult result = new YmcResult();
		if(StringUtils.isEmpty(merchantCode)){
			result = new YmcResult(YmcResult.ERROR_CODE, "merchantCode 商家编码为空");
			return result;
		} 
		if(StringUtils.isEmpty(contractId)){
			result = new YmcResult(YmcResult.ERROR_CODE, "contractNo 合同编号为空");
			return result;
		} 
		if(StringUtils.isEmpty(status)){
			result = new YmcResult(YmcResult.ERROR_CODE, "status 状态属性为空");
			return result;
		} 
 
		SupplierVo supplier =  supplierVoMapper.getSupplierByMerchantCode(merchantCode);
		if(supplier == null){
			result = new YmcResult(YmcResult.ERROR_CODE, "商家不存在");
			return result;
		}
		
		String statusWords = "";
		try {
			statusWords =  Constant.getStatus(status.toCharArray()[0]);
			
		} catch (Exception e) {
			 logger.error(e);
		}
		if(StringUtils.isEmpty(statusWords)){
			result = new YmcResult(YmcResult.ERROR_CODE, "status 属性只能为  合同状态（1新建 2待审核 3业务审核通过 4业务审核不通过 5财务审核通过 6财务审核不通过 7生效 8已过期）");
			return result;
		}
//		SupplierContractQuery  sq = new SupplierContractQuery();  
//		com.yougou.merchant.api.supplier.vo.SupplierContractQuery.Criteria c = 
//				sq.createCriteria();
//		//c.andContractNoEqualTo(contractNo);
//		c.andIdEqualTo(contractId);
//		c.andSupplierIdEqualTo(supplier.getId());
		
		SupplierContract constract = supplierContractMapper.selectSupplierContractById(contractId);
		
//		SupplierContract constract = CollectionUtils.isEmpty(supplierContractMapper.selectByQuery(sq) )?
//				null:supplierContractMapper.selectByQuery(sq) .get(0);
		if(constract==null){
			result = new YmcResult(YmcResult.ERROR_CODE, "合同不存在");
			return result;
		}
		//SupplierContract constractUpdate = new SupplierContract();
		constract.setId(constract.getId());
		constract.setStatus(status);
		supplierContractMapper.updateSupplierContract(constract);//updateByPrimaryKeySelective(constractUpdate);
		
		//记录日志
		//审核日志
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(supplier.getSupplierCode());
		log.setOperator(operatorName);
		log.setOperated(new Date());
		log.setOperationType(OperationType.CONTRACT);
		log.setOperationNotes("商家  "+supplier.getSimpleName()+" 合同编号  "
				+constract.getContractNo() +"审核 " +statusWords +" 操作人 ：" +operatorName);
		supplierVoMapper.insertMerchantLog(log);

		
		return result;
	}
	
	
	/**
	 * 提供给商品系统查询供应商合同，合同附件，授权资质等数据的接口
	 * return SupplierInfo
	 */
	public YmcResult   getMerchantInfo(String merchantCode){
		YmcResult result = new YmcResult();
		if(StringUtils.isEmpty(merchantCode)){
			result = new YmcResult(YmcResult.ERROR_CODE, "supplierId 商家编码为空");
			return result;
		} 
		SupplierVo supplier =  supplierVoMapper.getSupplierByMerchantCode(merchantCode);
		if(supplier == null){
			result = new YmcResult(YmcResult.ERROR_CODE, "商家不存在");
			return result;
		}
		try {
			//合同信息
//			SupplierContractQuery  sq = new SupplierContractQuery();  
//			com.yougou.merchant.api.supplier.vo.SupplierContractQuery.Criteria c = 
//					sq.createCriteria();
//			c.andSupplierIdEqualTo(supplier.getId());
			
			List<SupplierContract>  contractList = 
					supplierContractMapper.selectSupplierContractListBySupplierId(supplier.getId());// supplierContractMapper.selectByQuery(sq)  ;
			
			//授权资质信息
			ContractAttachmentQuery  attach = null;//new ContractAttachmentQuery();
			com.belle.yitiansystem.merchant.model.entity.ContractAttachmentQuery.Criteria attachCri = null;// attach.createCriteria();
			List<ContractAttachment> contractAttachmentList = null;
			//商标  
			ContractTradeMarkQuery markQuery =  null;// = new ContractTradeMarkQuery();
			com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkQuery.Criteria markCri = null;// markQuery.createCriteria();
			List<ContractTradeMark>  contractTradeMarkList = null;
			//子商标
			ContractTradeMarkSubQuery markSubQuery = null;// new ContractTradeMarkSubQuery();
			com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkSubQuery.Criteria markSubCri = null;//  markSubQuery.createCriteria();
			List<ContractTradeMarkSub>  contractTradeMarkSubList = null;
			
			MerchantContractUpdateHistoryQuery historyQuery = null;
			com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery.Criteria historyCri = null;
			List<MerchantContractUpdateHistory> historyList = null;
			

			for(SupplierContract contract: contractList){
				attach = new ContractAttachmentQuery();
				attachCri = attach.createCriteria();
				
				markQuery = new ContractTradeMarkQuery();
				markCri = markQuery.createCriteria();
				
				attachCri.andContractIdEqualTo(contract.getId());
				contractAttachmentList = contractAttachmentMapper.selectByQuery(attach);
				if(CollectionUtils.isNotEmpty(contractAttachmentList)){
					contract.setContractAttachmentList(contractAttachmentList);
				}
				markCri.andContractIdEqualTo(contract.getId());
				contractTradeMarkList  = contractTradeMarkMapper.selectByQuery(markQuery);
				
				for(ContractTradeMark mark: contractTradeMarkList){
					
					markSubQuery =  new ContractTradeMarkSubQuery();
					markSubCri = markSubQuery.createCriteria();
					
					markSubCri.andTrademarkIdEqualTo(mark.getId());
					contractTradeMarkSubList  =  this.contractTradeMarkSubMapper.selectByQuery(markSubQuery);
				
					if(CollectionUtils.isNotEmpty(contractTradeMarkSubList)){
						mark.setContractTradeMarkSubList(contractTradeMarkSubList);
					}
				
				}
				contract.setContractTradeMarkList(contractTradeMarkList);
				
				// history contractUpdateHistoryList
				historyQuery = new MerchantContractUpdateHistoryQuery();
				historyCri = historyQuery.createCriteria();
				historyCri.andContractNoEqualTo(contract.getContractNo());
				historyList = merchantContractUpdateHistoryMapper.selectByQuery(historyQuery);
				contract.setContractUpdateHistoryList(historyList);
			}
			//supplier.setSupplierContractList(contractList);
			
			List<MerchantOperationLog>  logList = supplierVoMapper.queryMerchantOperLog(merchantCode);
			
			supplier.setLogList(logList);
			SupplierInfo info = new SupplierInfo();
			info.setSupplierVO(supplier);
			info.setContractList(contractList);
			result.setData(info);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
	
		return result;
	}
	public YmcResult updateDateInfo(){
		YmcResult result = new YmcResult();
		
		supplierContractMapper.updateSupplierIdTOExpand();
		supplierContractMapper.updateExpandContract();
		supplierContractMapper.markLeftDays();
		supplierContractMapper.contractLeftDays();
		supplierContractMapper.businessRemainDays();
		supplierContractMapper.institutionalDays();
		
		//查询出 renew_flag =1 的所有
		return result;
	}
}
