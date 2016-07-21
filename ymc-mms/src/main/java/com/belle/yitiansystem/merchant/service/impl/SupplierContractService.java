package com.belle.yitiansystem.merchant.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.finance.biz.dubbo.IMerchantsRefundDubboService;
import com.belle.finance.biz.dubbo.IMerchantsSettledDubboService;
import com.belle.finance.income.alreadyincome.model.vo.MerchantsRefundCashbillVo;
import com.belle.finance.income.alreadyincome.model.vo.MerchantsSettledCashbillVo;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.UUIDGenerator;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantSupplierExpandMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierContractMapper;
import com.belle.yitiansystem.merchant.enums.ContractStatusEnum;
import com.belle.yitiansystem.merchant.exception.MerchantSystemException;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractAttachment;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractTrademark;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractTrademarkSub;
import com.belle.yitiansystem.merchant.model.pojo.SupplierSp4MyBatis;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact4MyBatis;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.yougou.api.model.pojo.ApiKeyMetadata;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.service.IApiService;
import com.yougou.component.area.api.IAreaApi;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.ordercenter.common.DateUtil;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;
@Service
public class SupplierContractService implements ISupplierContractService {
	
	private static Logger logger = Logger.getLogger(SupplierContractService.class);

	@Resource
	private SupplierContractMapper supplierContractMapper;
	
	@Resource
	private IAreaApi areaApi;
	
	@Resource
	private IPurchaseApiService purchaseApiService;
	
	@Resource
	private IMerchantServiceNew merchantServiceNew;
	
	@Resource
	private IMerchantsSettledDubboService financeSettleApiService;
	
	@Resource
	private IMerchantsRefundDubboService financeRefundApiService;
	
	@Resource
	private IApiKeyService apiKeyService;
	
	@Resource
	private IApiService apiService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void saveSupplierContract(SupplierContract contract,String curUser,
			String[] contract_attachments, String[] trademarks,
			String[] authorizers, String[] types, String[] registeredTrademarks,
			String[] registeredStartDates, String[] registeredEndDates,
			String[] beAuthorizers, String[] authorizStartdates,
			String[] authorizEnddates) throws BusinessException {
		try{
			//保存主表
			String contractId =contract.getId();
			//初始化合同到期剩余天数 Add by LQ on 20150401
			Integer contractRemainingDays = null;
			contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate() );
			if (null != contractRemainingDays ){
				contract.setContractRemainingDays(contractRemainingDays);
			}
			//校验合同号是否存在
			if(StringUtils.isBlank(contractId)){
				if( null!=contract.getContractNo() && !"".equals(contract.getContractNo().trim()) ){
					if( null!=supplierContractMapper.selectSupplierContractByContractNo(contract.getContractNo())){
						throw new BusinessException("合同编号已经存在!");
					}
				}
				contractId = UUIDGenerator.getUUID();
				contract.setId(contractId);
				String date = DateUtil.getDateTime(new Date());
				contract.setUpdateTime(date);
				contract.setCreateTime(date);
				contract.setUpdateUser(curUser);
				contract.setCreateUser(curUser);
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
				supplierContractMapper.insertSupplierContract(contract);
			}else{
				contract.setUpdateUser(curUser);
				contract.setUpdateTime(DateUtil.getDateTime(new Date()));				
				supplierContractMapper.updateSupplierContract(contract);
			}
			//保存附件
			//删除原来的附件信息
			supplierContractMapper.deleteSupplierContractAttachmentByContractId(contractId);
			List<SupplierContractAttachment> attachmentList = new ArrayList<SupplierContractAttachment>();
			if(contract_attachments!=null){
				for(String attachment:contract_attachments){
					String[] attachmentArray = attachment.split(";");
					if(null!=attachmentArray&&attachmentArray.length==3){
						SupplierContractAttachment att = new SupplierContractAttachment();
						att.setId(UUIDGenerator.getUUID());
						att.setContractId(contractId);
						att.setAttachmentType(attachmentArray[0]);
						att.setAttachmentName(attachmentArray[1]);
						att.setAttachmentRealName(attachmentArray[2]);
						att.setSupplierId(contract.getSupplierId());
						attachmentList.add(att);
					}
				}
			}
			if(!attachmentList.isEmpty()){
				supplierContractMapper.insertSupplierContractAttachmentBatch(attachmentList);
			}
			//保存商标
			List<SupplierContractTrademark> markList = new ArrayList<SupplierContractTrademark>();
			List<SupplierContractTrademarkSub> markSubList= new ArrayList<SupplierContractTrademarkSub>();
			//初始化两个到期剩余天数 Add by LQ on 20150401
			Integer markRemainingDays = null;
			Date latestEndDay = null;
			if(trademarks!=null){
				for(int i=0,_len =trademarks.length;i<_len;i++){
					int subCount = 6;
					String trademarkId =  UUIDGenerator.getUUID();
					SupplierContractTrademark mark = new SupplierContractTrademark();
					mark.setId(trademarkId);
					mark.setContractId(contractId);
					mark.setTrademark(trademarks[i]);
					mark.setAuthorizer(authorizers[i]);
					mark.setType(types[i]);
					mark.setRegisteredTrademark(registeredTrademarks[i]);
					mark.setRegisteredStartDate(StringUtils.isBlank(registeredStartDates[i])?null:registeredStartDates[i]);
					mark.setRegisteredEndDate(StringUtils.isBlank(registeredEndDates[i])?null:registeredEndDates[i]);
					markList.add(mark);
					//商标明细
					subCount = subCount*i;
					String beAuthorizer = "";
					String authorizStartdate = "";
					String authorizEnddate = "";
					for(int j=0;j<6;j++){
						int index = j+subCount;
						beAuthorizer = beAuthorizers[index];
						authorizStartdate = StringUtils.isBlank(authorizStartdates[index])?null:authorizStartdates[index];
						authorizEnddate = StringUtils.isBlank(authorizEnddates[index])?null:authorizEnddates[index];
						if(StringUtils.isBlank(beAuthorizer)&&StringUtils.isBlank(authorizStartdate)&&StringUtils.isBlank(authorizEnddate)){
							continue;
						}
						// 取最近的商标到期日
						if( !StringUtils.isBlank(authorizEnddate)  ){
							Date endDate = null;
							endDate = DateUtil.getdate(authorizEnddate);
							if( null==latestEndDay || DateUtil.diffDate(endDate,latestEndDay)<0 ){
								latestEndDay = endDate;
							}
							
						}
						
						SupplierContractTrademarkSub sub = new SupplierContractTrademarkSub();
						sub.setId(UUIDGenerator.getUUID());
						sub.setContractId(contractId);
						sub.setTrademarkId(trademarkId);
						sub.setBeAuthorizer(beAuthorizer);
						sub.setAuthorizStartdate(authorizStartdate);
						sub.setAuthorizEnddate(authorizEnddate);
						sub.setLevel(j+1);
						markSubList.add(sub);
					}
				}
			}
						
			supplierContractMapper.deleteSupplierContractTrademarkByContractId(contractId);
			if(!markList.isEmpty()){
				supplierContractMapper.insertSupplierContractTrademarkBatch(markList);
			}
			supplierContractMapper.deleteSupplierContractTrademarkSubByContractIdId(contractId);
			if(!markSubList.isEmpty()){
				supplierContractMapper.insertSupplierContractTrademarkSub(markSubList);
				
				//更新商标到期日
				if( null!=latestEndDay ){
					markRemainingDays =  DateUtil2.getDiffDateFromToday(latestEndDay );
					contract.setMarkRemainingDays( markRemainingDays );
					supplierContractMapper.updateSupplierContract(contract);
				}
			}
		}catch(Exception e){
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public PageFinder<SupplierContract> findSupplierContractList(
			Map<String, Object> param, Query query) {
		List<String> merchantCodeList = null;
		//求合同的创建者的集合
		List<SupplierContract> list = supplierContractMapper.selectSupplierContractList(param,merchantCodeList,null,query);
		for(SupplierContract contract:list){
			contract.setIsNewContract(MerchantConstant.OLD_MERCHANT_FLAG);
			if(StringUtils.isBlank(contract.getParentContractId())){
				contract.setIsNewContract(MerchantConstant.NEW_MERCHANT_FLAG);
			}
			contract.setTrademarkList(supplierContractMapper.selectSupplierContractTrademark(contract.getId()));
			contract.setStatusName(ContractStatusEnum.getValue(contract.getStatus()));
		}
		int count = supplierContractMapper.selectSupplierContractCount(param,merchantCodeList,null);
		PageFinder<SupplierContract> pageFinder = new PageFinder<SupplierContract>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}
	
	
	public SupplierContract getSupplierContract(String id){
		SupplierContract contract = supplierContractMapper.selectSupplierContractById(id);
		List<SupplierContractAttachment> attachmentList = supplierContractMapper.selectSupplierContractAttachmentByContractId(id);
		List<SupplierContractTrademark> trademarkList = supplierContractMapper.selectSupplierContractTrademark(id);
		for(SupplierContractTrademark trademark:trademarkList){
			List<SupplierContractTrademarkSub> trademarkSubList = supplierContractMapper.selectSupplierContractTrademarkSubByTrademarkId(trademark.getId());
			trademark.setTrademarkSubList(trademarkSubList);
			
			if(!StringUtils.isBlank(trademark.getBrandNo())){				
				com.yougou.pc.model.brand.Brand brand = null;
				try {
					brand = commodityBaseApiService.getBrandByNo(trademark.getBrandNo());
					trademark.setBrandName(brand.getBrandName());
				} catch (Exception e) {
					logger.error(MessageFormat.format(
							"commodityBaseApiSercie接口获取品牌[{0}]失败", trademark.getBrandNo()), e);
				}
			}
		}
		if(null!=contract){
			contract.setAttachmentList(attachmentList);
			contract.setTrademarkList(trademarkList);
		}
		return contract;
	}
	
	/**
	 * 查询供应商列表
	 */
	public PageFinder<SupplierSp4MyBatis> selectSupplier4Contact(Map<String,Object> param, Query query){
		List<SupplierSp4MyBatis> list = supplierContractMapper.selectSupplier4ContractList(param, query);
		int count = supplierContractMapper.selectSupplier4ContractCount(param);
		PageFinder<SupplierSp4MyBatis> pageFinder = new PageFinder<SupplierSp4MyBatis>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}

	public List<SupplierSp4MyBatis> selectSupplierSpByYgContact(String curUser,String supplierCode, String supplierType){
		List<String> resultList = getMerchantCodeByCurUser(curUser);
		if(!StringUtils.isBlank(supplierCode)){
			resultList.add(supplierCode);
		}
		if(!resultList.isEmpty()){
			return supplierContractMapper.selectSupplierSpByYgContact(resultList, supplierType);
		}else{
			return new ArrayList<SupplierSp4MyBatis>();
		}
		
	}
	
	public List<String> getUsernameByCurUser(String curUser){
		List<String> resultList = new ArrayList<String>();
		if(StringUtils.isBlank(curUser)){
			return resultList;
		}
		//货品负责人
		List<SupplierYgContact4MyBatis> list = supplierContractMapper.selectYgContact(curUser);
		Iterator<SupplierYgContact4MyBatis> iterator = list.iterator();
		while(iterator.hasNext()){
			SupplierYgContact4MyBatis contact = iterator.next();
			if(curUser.equals(contact.getUserName())){
				resultList.add(contact.getUserName());
			}else{
				//查询领导下属负责的商家
				String leads =contact.getLeads();
				if(!StringUtils.isBlank(leads)){
					String[] leadArray = leads.split(",");
					for(String lead:leadArray){
						if(curUser.equals(lead)){
							resultList.add(contact.getUserName());
						}
					}
				}
			}
		}
		//包含当前登录者
		if(!(resultList.contains(curUser))){
			resultList.add(curUser);
		}
		return resultList;
	}
	
	@Override
	public List<String> getMerchantCodeByCurUser(String curUser) {
		List<String> resultList = new ArrayList<String>();
		if(StringUtils.isBlank(curUser)){
			return resultList;
		}
		List<SupplierYgContact4MyBatis> list = supplierContractMapper.selectYgContact(curUser);
		Iterator<SupplierYgContact4MyBatis> iterator = list.iterator();
		while(iterator.hasNext()){
			SupplierYgContact4MyBatis contact = iterator.next();
			if(curUser.equals(contact.getUserName())){
				resultList.add(contact.getUserId());
			}else{
				//查询领导下属负责的商家
				String leads =contact.getLeads();
				if(!StringUtils.isBlank(leads)){
					String[] leadArray = leads.split(",");
					for(String lead:leadArray){
						if(curUser.equals(lead)){
							resultList.add(contact.getUserId());
						}
					}
				}
			}
		}
		//查询货品负责人负责的商家
		if(!resultList.isEmpty()){
			return supplierContractMapper.selectMerchantCodeByYgContact(resultList);
		}else{
			return resultList;
		}
		
	}

	@Override
	public void bindContractIfExited(Supplier supplier) {
		SupplierContract supplierContract = new SupplierContract();
		supplierContract.setSupplier(supplier.getSupplier());
		supplierContract.setSupplierId(supplier.getId());
		supplierContractMapper.updateSupplierContractBindStatus(supplierContract);
		
	}
	
	@Override
	public List<SupplierContract> findSupplierContractListForExport(
			Map<String, Object> param) {
//		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<String> merchantCodeList = null;
		List<String> userNameList= null;
		//求合同的创建者的集合
		List<SupplierContract> list = supplierContractMapper.selectSupplierContractListForExport(param,merchantCodeList,userNameList);
		for(SupplierContract contract:list){
			contract.setTrademarkList(supplierContractMapper.selectSupplierContractTrademark(contract.getId()));
		}
		return list;
	}

	public void updateDateInfo(){
		supplierContractMapper.updateSupplierIdTOExpand();
		supplierContractMapper.updateExpandContract();
		supplierContractMapper.markLeftDays();
		supplierContractMapper.contractLeftDays();
		supplierContractMapper.businessRemainDays();
		supplierContractMapper.institutionalDays();
		//add by lsm 2015-8-21 notes:更新商家表的同时更新合同表
		supplierContractMapper.updateContractOverdays();
		supplierContractMapper.updateContractMarkOverdays();
		//续签合同且审核通过，合同剩余有效天数更新到扩展表
		supplierContractMapper.contractLeftDaysByRenew();
		
	}
	
	
	// 更新商标到期剩余天数和合同到期剩余天数
	public void updateRemainingDaysForContract() {
		//  Auto更新合同到期剩余天数：到期日那天，剩余天数为0
		supplierContractMapper.autoUpdateContractRemainingDays1();
		supplierContractMapper.autoUpdateContractRemainingDays2();
		
		// Auto更新商标到期剩余天数：取商标到期日期中最小数
		List <Map<String,String>> contractList = supplierContractMapper.computeAndSelectTrademarkRemainingDays();
		if( null!=contractList && 0<contractList.size() ){
			List<SupplierContract> supplierContractList = new ArrayList<SupplierContract>();
			
			for(int i=0;i<contractList.size();i++){
				Map<String,String> map = contractList.get(i);
				SupplierContract sc = new SupplierContract();
				sc.setContractNo((String)map.get("id"));
				sc.setMarkRemainingDays(Integer.parseInt(map.get("num")));
				supplierContractList.add(sc);
			}
			supplierContractMapper.autoUpdateMarkRemainingDays(supplierContractList);
		}
		
	}
	
	/**
	 * 根据供应商ID查询合同
	 * @param supplierId
	 * @return 供应商合同列表
	 */
	public List<SupplierContract> selectSupplierContractListBySupplierId(String supplierId){
		return supplierContractMapper.selectSupplierContractListBySupplierId(supplierId);
	}

	@Override
	public SupplierContract getSupplierContractBySupplierId(String supplierId) {
		
		SupplierContract contract = supplierContractMapper.getCurrentContractBySupplierId(supplierId); 
		if(contract == null) return null;
		return getSupplierContract(contract.getId());
	}
	
	/**
	 * 商家管理创建商家调用的 合同创建
	 * @author luo.q1
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void saveContractForSupplier(Map<String,Object> params) throws Exception {
		Supplier supplier = (Supplier)params.get("supplier");
		String supplierId = (String)params.get("supplierId");
		String curUser = supplier.getCreator();
		SupplierContract contract = (SupplierContract)params.get("supplierContract");
		AttachmentFormVo attachmentFormVo = (AttachmentFormVo)params.get("attachmentFormVo");

		//校验合同号是否存在
		if( null!=contract.getContractNo() && !"".equals(contract.getContractNo().trim()) ){
			if( null!=supplierContractMapper.selectSupplierContractByContractNo(contract.getContractNo())){
				throw new BusinessException("合同编号已经存在!");
			}
		}
		
		//初始化合同到期剩余天数 
		Integer contractRemainingDays = null;
		contractRemainingDays =   DateUtil2.getDiffDateFromToday(contract.getFailureDate() );
		if ( null!=contractRemainingDays ){
			contract.setContractRemainingDays(contractRemainingDays);
		}
		
		//保存主表
		String contractId = UUIDGenerator.getUUID();
		contract.setId(contractId);
		String date = DateUtil.getDateTime(new Date());
		contract.setUpdateTime(date);
		contract.setCreateTime(date);
		contract.setUpdateUser(curUser);
		contract.setCreateUser(curUser);
		contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT);
		supplierContractMapper.insertSupplierContract(contract);
		
		//保存附件
		attachmentFormVo.setContractId(contractId);
		attachmentFormVo.setSupplierId(supplierId);
		saveOrUpdateByAttachmentFormVo(attachmentFormVo);
		contract.setMarkRemainingDays(attachmentFormVo.getMarkRemainingDays());//供后面更新到商家扩张表
	}
	
	/**
	 * 商家管理编辑商家调用的-- 合同修改
	 * @author luo.q1
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void updateContractForSupplier(Map<String,Object> params) throws BusinessException {
		Supplier supplier = (Supplier)params.get("supplier");
		String supplierId = (String)params.get("supplierId");
		String curUser = supplier.getCreator();
		SupplierContract contract = (SupplierContract)params.get("supplierContract");
		AttachmentFormVo attachmentFormVo = (AttachmentFormVo)params.get("attachmentFormVo");
		try{
			//保存主表
			//初始化合同到期剩余天数 
			Integer contractRemainingDays = null;
			contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate() );
			if ( null!=contractRemainingDays ){
				contract.setContractRemainingDays( contractRemainingDays);
			}
				
			String date = DateUtil.getDateTime(new Date());
			contract.setUpdateUser(curUser);
			contract.setUpdateTime(date);	
			if(contract.getDeposit().compareTo(BigDecimal.ZERO) <= 0){
				contract.setDepositStatus("");
			}
			if(contract.getUseFee().compareTo(BigDecimal.ZERO) <= 0){
				contract.setUseFeeStatus("");
			}
			supplierContractMapper.updateSupplierContract(contract);
			
			//保存附件、保存商标、初始化合同两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
			attachmentFormVo.setContractId(contract.getId());
			attachmentFormVo.setSupplierId(supplierId);
			saveOrUpdateByAttachmentFormVo(attachmentFormVo);
			contract.setMarkRemainingDays(attachmentFormVo.getMarkRemainingDays());//供后面更新到商家扩张表
			
		}catch(Exception e){
			throw new BusinessException("修改合同、附件和商标信息保存失败！"+e.getMessage());
		}
	}
	
	/**
	 * 保存招商供应商合同
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void saveSupplierContractSimple(SupplierContract contract, Supplier supplier, String curUser,
			 AttachmentFormVo attachmentFormVo) throws BusinessException {
		try{
			//保存主表
			String contractId =contract.getId();
			int contractRemainingDays = 0;
			String date = DateUtil2.getCurrentDateTimeToStr2();
			MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId( supplier.getId() );
			if( contract.getFailureDate()!= null && contract.getFailureDate()!="" ){
				contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate() );
				contract.setContractRemainingDays(contractRemainingDays);
				supplierExpand.setContractRemainingDays(contractRemainingDays);
			}
			
			//校验合同号是否存在
			if(StringUtils.isBlank(contractId)){
				if( null!=contract.getContractNo() && !"".equals(contract.getContractNo().trim()) ){
					if( null!=supplierContractMapper.selectSupplierContractByContractNo(contract.getContractNo())){
						throw new BusinessException("合同编号已经存在!");
					}
				}
				contractId = UUIDGenerator.getUUID();
				contract.setId(contractId);
				contract.setUpdateTime(date);
				contract.setCreateTime(date);
				contract.setUpdateUser(curUser);
				contract.setCreateUser(curUser);
				contract.setSupplierId(supplier.getId());
				contract.setSupplier(supplier.getSupplier());
				contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT);
				
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
				supplierContractMapper.insertSupplierContract(contract);
			}else{
				contract.setUpdateUser(curUser);
				contract.setUpdateTime(date);
				supplierContractMapper.updateSupplierContract(contract);
			}
			
			//保存附件、保存商标、初始化合同两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
			// 若为新增合同，从前台拿到的contractId是空的，必须保存合同后拿到contractId设置attachmentFormVo
			attachmentFormVo.setContractId(contractId);
			saveOrUpdateByAttachmentFormVo(attachmentFormVo);
			
			// 更新商家扩展表的两个剩余天数
    		if (null != attachmentFormVo.getMarkRemainingDays()) {
    			supplierExpand.setMarkRemainingDays(attachmentFormVo
    					.getMarkRemainingDays());
    		}
    		if( supplierExpand.getMarkRemainingDays()!= null  || supplierExpand.getContractRemainingDays()!= null ){
	    		supplierExpand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
	    		merchantSupplierExpandMapper
	    				.updateByPrimaryKeySelective(supplierExpand);
    		}
		}catch(Exception e){
			logger.error("保存合同信息发生异常：",e);
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * 保存招商供应商合同并提交审核
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void saveSupplierContractMerchant(SupplierContract contract, Supplier supplier, String curUser,
			boolean isSubmit, AttachmentFormVo attachmentFormVo) throws BusinessException {
		try{
			//保存主表
			String contractId =contract.getId();
			String date = DateUtil2.getCurrentDateTimeToStr2();
			int contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate() );
			contract.setContractRemainingDays(contractRemainingDays);
			boolean depositResult = false;
			boolean useFeeResult = false;
			//校验合同号是否存在
			if(StringUtils.isBlank(contractId)){
				if( null!=contract.getContractNo() && !"".equals(contract.getContractNo().trim()) ){
					if( null!=supplierContractMapper.selectSupplierContractByContractNo(contract.getContractNo())){
						throw new BusinessException("合同编号已经存在!");
					}
				}
				contractId = UUIDGenerator.getUUID();
				contract.setId(contractId);
				contract.setUpdateTime(date);
				contract.setCreateTime(date);
				contract.setUpdateUser(curUser);
				contract.setCreateUser(curUser);
				contract.setSupplierId(supplier.getId());
				contract.setSupplier(supplier.getSupplier());
				contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT);
				if(isSubmit){
					//提交审核的时候才创建收款单
					//创建平台使用费收款单
					useFeeResult = createFinanceUseFeeCashBill(contract,curUser);
					//创建保证金收款单
					depositResult = createFinanceDepositCashBill(contract,curUser,null);
					if(!depositResult || !useFeeResult){
						logger.error("调用财务接口创建收款单失败");
						throw new Exception("调用财务接口创建收款单失败");
					}
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED);
				}else{
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
				}
				supplierContractMapper.insertSupplierContract(contract);
			}else{
				contract.setUpdateUser(curUser);
				contract.setUpdateTime(date);
				contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT);
				if(isSubmit){
					SupplierContract currentContract = supplierContractMapper.selectSupplierContractById(contractId);
					if(StringUtils.isBlank(currentContract.getDepositStatus())){
						depositResult = createFinanceDepositCashBill(contract,curUser,null);
					}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(currentContract.getDepositStatus())){
						//修改保证金
						depositResult = updateFinanceDepositCashBill(contract,curUser,currentContract,null);
					}
					
					if(StringUtils.isBlank(currentContract.getUseFeeStatus())){
						useFeeResult = createFinanceUseFeeCashBill(contract,curUser);
					}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(currentContract.getUseFeeStatus())){
						//修改平台使用费
						useFeeResult = updateFinanceUseFeeCashBill(contract,curUser,currentContract);
					}
					if(!depositResult || !useFeeResult){
						logger.error("调用财务接口处理收款单失败");
						throw new Exception("调用财务接口处理收款单失败");
					}
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED);
				}else if(contractRemainingDays > 0 && MerchantConstant.CONTRACT_STATUS_EXPIRED.equals(contract.getStatus())){
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_EFFECTIVE);
				}
				supplierContractMapper.updateSupplierContract(contract);
			}
			
			//保存附件、保存商标、初始化合同两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
			// 若为新增合同，从前台拿到的contractId是空的，必须保存合同后拿到contractId设置attachmentFormVo
			attachmentFormVo.setContractId(contractId);
			saveOrUpdateByAttachmentFormVo(attachmentFormVo);
			
			// 更新商家扩展表的两个剩余天数
    		MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId( supplier.getId() );
    		supplierExpand.setContractRemainingDays(contractRemainingDays);
    		if (null != attachmentFormVo.getMarkRemainingDays()) {
    			supplierExpand.setMarkRemainingDays(attachmentFormVo
    					.getMarkRemainingDays());
    		}
    		supplierExpand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
    		merchantSupplierExpandMapper
    				.updateByPrimaryKeySelective(supplierExpand);
			
		}catch(Exception e){
			logger.error("保存合同信息发生异常：",e);
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * 保存供应商合同
	 * @param contract
	 */
	public void updateSupplierContract(SupplierContract contract){
		supplierContractMapper.updateSupplierContract(contract);
	}
	
	/**
	 * //保存附件、保存商标、初始化合同的两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
	 * @throws Exception 
	 */
	public void saveOrUpdateByAttachmentFormVo(AttachmentFormVo attachmentFormVo) throws Exception{
		//保存附件
		//删除原来的附件信息
		String contractId = attachmentFormVo.getContractId();
		String supplierId = attachmentFormVo.getSupplierId();
//		supplierContractMapper.delAttachmentExceptContract(supplierId,contractId);
		List<SupplierContractAttachment> attachmentList = new ArrayList<SupplierContractAttachment>();
		if( attachmentFormVo.getContract_attachment()!=null ){
			for(String attachment:attachmentFormVo.getContract_attachment()){
				String[] attachmentArray = attachment.split(";");
				if(null!=attachmentArray&&attachmentArray.length>=3){
					SupplierContractAttachment att = new SupplierContractAttachment();
					att.setContractId(contractId);////
					att.setAttachmentType(attachmentArray[0]);
					att.setAttachmentName(attachmentArray[1]);
					att.setAttachmentRealName(attachmentArray[2]);
					att.setSupplierId(supplierId);////
					
					if(attachmentArray.length>3){
						String flagUnit = attachmentArray[3];
						if( flagUnit.indexOf("-1")!=-1 ){//删除
							// 删除之前必须查询是否在数据库已存在（可能是新上传的删除）
							att.setDeleteFlag("-1");
						}
					}
					
					attachmentList.add(att);
				}
				
			}
		}
		
		boolean isSuccess = saveOrDeleteAttachment(attachmentList) ;// 处理附件
		
		if( isSuccess ){
				//保存商标
				List<SupplierContractTrademark> markList = new ArrayList<SupplierContractTrademark>();
				List<SupplierContractTrademarkSub> markSubList= new ArrayList<SupplierContractTrademarkSub>();
				//初始化两个到期剩余天数 Add by LQ on 20150401
				Integer markRemainingDays = null;
				Date latestEndDay = null;
				String[] trademarks = attachmentFormVo.getTrademark();
				String[] brandNos = attachmentFormVo.getBrandNo();
				String[] deductionPoints = attachmentFormVo.getDeductionPoint();
				if(attachmentFormVo.getTrademark()!=null){
					for(int i=0,_len =trademarks.length;i<_len;i++){
						int subCount = 6;
						String trademarkId =  UUIDGenerator.getUUID();
						SupplierContractTrademark mark = new SupplierContractTrademark();
						mark.setId(trademarkId);
						mark.setContractId(contractId);
						mark.setTrademark(trademarks[i]);
						mark.setBrandNo(brandNos[i]);
						String deductVal = deductionPoints[i];
						if( deductVal!= null && StringUtils.isNotEmpty(deductVal) && NumberUtils.isDigits(deductVal.trim()) ){
							mark.setDeductionPoint( Integer.valueOf(deductVal.trim()));
						}
						mark.setAuthorizer((attachmentFormVo.getAuthorizer())[i]);
						mark.setType((attachmentFormVo.getType())[i]);
						mark.setRegisteredTrademark((attachmentFormVo.getRegisteredTrademark())[i]);
						mark.setRegisteredStartDate(StringUtils.isBlank((attachmentFormVo.getRegisteredStartDate())[i])?null:(attachmentFormVo.getRegisteredStartDate())[i]);
						mark.setRegisteredEndDate(StringUtils.isBlank((attachmentFormVo.getRegisteredEndDate())[i])?null:(attachmentFormVo.getRegisteredEndDate())[i]);
						markList.add(mark);
						//商标明细
						subCount = subCount*i;
						String beAuthorizer = "";
						String authorizStartdate = "";
						String authorizEnddate = "";
						for(int j=0;j<6;j++){
							int index = j+subCount;
							beAuthorizer = (attachmentFormVo.getBeAuthorizer())[index];
							authorizStartdate = StringUtils.isBlank((attachmentFormVo.getAuthorizStartdate())[index])?null:(attachmentFormVo.getAuthorizStartdate())[index];
							authorizEnddate = StringUtils.isBlank((attachmentFormVo.getAuthorizEnddate())[index])?null:(attachmentFormVo.getAuthorizEnddate())[index];
							if(StringUtils.isBlank(beAuthorizer)&&StringUtils.isBlank(authorizStartdate)&&StringUtils.isBlank(authorizEnddate)){
								continue;
							}
							// 取最近的商标到期日
							if( !StringUtils.isBlank(authorizEnddate)  ){
								Date endDate = null;
								endDate = DateUtil.getdate(authorizEnddate);
								if( null==latestEndDay || DateUtil.diffDate(endDate,latestEndDay)<0 ){
									latestEndDay = endDate;
								}
								
							}
							
							SupplierContractTrademarkSub sub = new SupplierContractTrademarkSub();
							sub.setId(UUIDGenerator.getUUID());
							sub.setContractId(contractId);
							sub.setTrademarkId(trademarkId);
							sub.setBeAuthorizer(beAuthorizer);
							sub.setAuthorizStartdate(authorizStartdate);
							sub.setAuthorizEnddate(authorizEnddate);
							sub.setLevel(j+1);
							markSubList.add(sub);
						}
					}
				}
							
				supplierContractMapper.deleteSupplierContractTrademarkByContractId(contractId);
				if(!markList.isEmpty()){
					supplierContractMapper.insertSupplierContractTrademarkBatch(markList);
				}
				supplierContractMapper.deleteSupplierContractTrademarkSubByContractIdId(contractId);
				SupplierContract contract  = new SupplierContract();
				contract.setId(contractId);
				contract.setUpdateTime(DateUtil2.getCurrentDateTimeToStr2());
				if(null!=attachmentFormVo.getIsNeedRenew()){// 更新资质页面 独有
					contract.setIsNeedRenew(attachmentFormVo.getIsNeedRenew());
				}
				if(!markSubList.isEmpty()){
					supplierContractMapper.insertSupplierContractTrademarkSub(markSubList);
					
					//更新商标到期日
					if( null!=latestEndDay ){
						markRemainingDays = DateUtil2.getDiffDateFromToday(latestEndDay);////
						contract.setMarkRemainingDays( markRemainingDays );
						attachmentFormVo.setMarkRemainingDays(markRemainingDays);//
					}
				}
				supplierContractMapper.updateSupplierContract(contract);
		}
	}
	
	/** 商家管理--获取商家的合同和合同附件、合同商标信息合集 */
	public SupplierContract buildSupplierContractSet(String supplierId){
		SupplierContract contract = null;
		List<SupplierContractAttachment> firstAttachmentList = supplierContractMapper.getSupplierAttachmentBySupplierId(supplierId);
		contract = supplierContractMapper.getCurrentContractBySupplierId(supplierId); 
		if( null!=contract){
			List<SupplierContractAttachment> attachmentList = supplierContractMapper.getContractAttachmentByContractId(contract.getId());
			List<SupplierContractTrademark> trademarkList = supplierContractMapper.selectSupplierContractTrademark(contract.getId());
			for(SupplierContractTrademark trademark:trademarkList){
				List<SupplierContractTrademarkSub> trademarkSubList = supplierContractMapper.selectSupplierContractTrademarkSubByTrademarkId(trademark.getId());
				trademark.setTrademarkSubList(trademarkSubList);
				
				if(!StringUtils.isBlank(trademark.getBrandNo())){				
					com.yougou.pc.model.brand.Brand brand = null;
					try {
						brand = commodityBaseApiService.getBrandByNo(trademark.getBrandNo());
						trademark.setBrandName(brand.getBrandName());
					} catch (Exception e) {
						logger.error(MessageFormat.format(
								"commodityBaseApiSercie接口获取品牌[{0}]失败", trademark.getBrandNo()), e);
					}
				}
			}
			// 合并附件
			if( null!=attachmentList&&attachmentList.size()>0 ){
				attachmentList.addAll(firstAttachmentList);
			}else{
				attachmentList = firstAttachmentList;
			}
			if(null!=contract){
				contract.setAttachmentList(attachmentList);
				contract.setTrademarkList(trademarkList);
			}
			
		}
		return contract;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void renewSupplierContract(SupplierContract contract, Supplier supplier, String curUser,
			boolean isSubmit, Map<String, Object> params) throws BusinessException {
		try{
			SupplierContract currentSupplierContract = (SupplierContract)params.get("currentSupplierContract");
			//保存主表
			String contractId =contract.getId();
			Integer contractRemainingDays = null;
			contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate());
			if ( null!=contractRemainingDays ){
				contract.setContractRemainingDays( contractRemainingDays );
			}
			
			if( null!=contract.getContractNo() && !"".equals(contract.getContractNo().trim()) ){
				if( null!=supplierContractMapper.selectSupplierContractByContractNo(contract.getContractNo())){
					throw new BusinessException("合同编号已经存在!");
				}
			}
			contractId = UUIDGenerator.getUUID();
			contract.setId(contractId);
			String date = DateUtil.getDateTime(new Date());
			contract.setUpdateTime(date);
			contract.setCreateTime(date);
			contract.setUpdateUser(curUser);
			contract.setCreateUser(curUser);
			contract.setSupplierId(supplier.getId());
			contract.setSupplier(supplier.getSupplier());
			contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_FUTURE);
			contract.setParentContractId(currentSupplierContract.getId());
			if(isSubmit){
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED);
				currentSupplierContract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT_WITH_FUTURE);//更新上期合同
				// step2:更新上期合同的保证金状态---------- 针对续签合同有上期保证金转入的情形----------
				String isTransferDeposit = contract.getIsTransferDeposit() ;
				if( MerchantConstant.CONTRACT_TRANSFER_FLAG.equals(isTransferDeposit) && 
						contract.getPreDeposit().compareTo(BigDecimal.ZERO)>0 ){
					currentSupplierContract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_TRANSPORT);// Add on 2015-08-21
				}
				
			}else{
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
			}
			supplierContractMapper.insertSupplierContract(contract);
			
//			handleAttachmentAndTradeMarks( contract,params );// mark by LQ.
			//保存附件、保存商标、初始化合同两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
			// 若为新增合同，从前台拿到的contractId是空的，必须保存合同后拿到contractId设置attachmentFormVo
			AttachmentFormVo attachmentFormVo = (AttachmentFormVo)params.get("attachmentFormVo");
			attachmentFormVo.setContractId(contractId);
			saveOrUpdateByAttachmentFormVo(attachmentFormVo);// mark by LQ.
			
			 //保存品类授权
			saveOrUpdateBrandAndCats(supplier.getId(),attachmentFormVo.getBankNoHidden(),attachmentFormVo.getCatNameHidden());
			
			// 旧合同改变续签标志位renew_flag（和保证金状态）
			if( isSubmit ){
		  		currentSupplierContract.setUpdateUser(curUser);
		  		currentSupplierContract.setUpdateTime( DateUtil2.getCurrentDateTimeToStr2() );
				supplierContractMapper.updateSupplierContract(currentSupplierContract);
				// 添加供应商当前合同续签标志的修改日志
				try {
					MerchantOperationLog log = new MerchantOperationLog();
					log.setType(MerchantConstant.LOG_FOR_CONTRACT);
					log.setOperator(curUser);
					log.setContractNo(currentSupplierContract.getContractNo());
					log.setOperationNotes("续签合同提交审核时将当前合同续签标志更新为'当前合同有续签'。");
					log.setOperationType(OperationType.UPDATE_CONTRACT.getDescription());
					log.setMerchantCode(currentSupplierContract.getSupplierCode());
					merchantServiceNew.insertMerchantLog(log);
				} catch (Exception e) {
					logger.error("续签合同提交审核时，给当前合同(" + currentSupplierContract.getContractNo() + ")增加操作日志异常.");
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public SupplierContract getSupplierCurrentContract(String supplierId) {
		return supplierContractMapper.getCurrentContractBySupplierId(supplierId); 
	}
	
	@Override
	public SupplierContract getSupplierRenewContract(String supplierId) {
		return supplierContractMapper.getRenewContractBySupplierId(supplierId); 
	}
	
	/** 获取续签合同和合同的附件 */
	@Override
	public SupplierContract getSupplierRenewContractAndItsAttachments(String supplierId) {
		SupplierContract contract = supplierContractMapper.getRenewContractBySupplierId(supplierId); 
		if( null!=contract ){
			List<SupplierContractAttachment>  attachmentList = supplierContractMapper.getContractAttachmentByContractId(contract.getId());
			if( null!=attachmentList && 0 < attachmentList.size()){
				contract.setAttachmentList(attachmentList);		
			}
		}
		return contract;
	}

	@Override
	public void updateRenewSupplierContract(SupplierContract contract,
			Supplier supplier, String curUser, boolean isSubmit,
			Map<String, Object> params) throws BusinessException {
		try{
			SupplierContract currentSupplierContract = (SupplierContract)params.get("currentSupplierContract");
			Boolean updateCurrentContractFlag = (Boolean)params.get("updateCurrentContractFlag");
			//保存主表
			Integer contractRemainingDays = null;
			contractRemainingDays =  DateUtil2.getDiffDateFromToday(contract.getFailureDate());
			if ( null!=contractRemainingDays ){
				contract.setContractRemainingDays( contractRemainingDays);
			}
			String date = DateUtil2.getCurrentDateTimeToStr2();
			contract.setUpdateTime(date);
			contract.setUpdateUser(curUser);
			
			if(isSubmit){
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED);
			}else{
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
			}
			supplierContractMapper.updateSupplierContract(contract);
			
			//保存附件、保存商标、初始化合同两个到期剩余天数保存到合同表（注意：商家扩展表的两个天数未更新，请后面另行更新）
			// 若为新增合同，从前台拿到的contractId是空的，必须保存合同后拿到contractId设置attachmentFormVo
			AttachmentFormVo attachmentFormVo = (AttachmentFormVo)params.get("attachmentFormVo");
			attachmentFormVo.setContractId(contract.getId());
			saveOrUpdateByAttachmentFormVo(attachmentFormVo);// mark by LQ.
			
			 //保存品类授权
			saveOrUpdateBrandAndCats(contract.getSupplierId(),attachmentFormVo.getBankNoHidden(),attachmentFormVo.getCatNameHidden());
			
			// step2:更新上期合同的保证金状态---------- 针对续签合同有上期保证金转入的情形----------
			if( null!=updateCurrentContractFlag && updateCurrentContractFlag){
				currentSupplierContract.setUpdateUser(curUser);
				currentSupplierContract.setUpdateTime( DateUtil2.getCurrentDateTimeToStr2());
				currentSupplierContract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_TRANSPORT);
				supplierContractMapper.updateSupplierContract(currentSupplierContract);
				
				// 添加供应商当前合同的修改日志
				try {
					MerchantOperationLog log = new MerchantOperationLog();
					log.setType(MerchantConstant.LOG_FOR_CONTRACT);
					log.setOperator(curUser);
					log.setContractNo(currentSupplierContract.getContractNo());
					log.setOperationNotes("续签合同提交审核时修改上期保证金转入字段。");
					log.setOperationType(OperationType.UPDATE_CONTRACT.getDescription());
					log.setMerchantCode(currentSupplierContract.getSupplierCode());
					merchantServiceNew.insertMerchantLog(log);
				} catch (Exception e) {
					logger.error("续签合同提交审核时，给当前合同(" + currentSupplierContract.getContractNo() + ")增加操作日志异常.");
				}
			}else if(MerchantConstant.FEE_STATUS_WAIT_TRANSPORT.equals(currentSupplierContract.getDepositStatus())){
				currentSupplierContract.setDepositStatus(MerchantConstant.FEE_STATUS_PAYED);
				currentSupplierContract.setUpdateUser(curUser);
				currentSupplierContract.setUpdateTime( DateUtil2.getCurrentDateTimeToStr2());
				supplierContractMapper.updateSupplierContract(currentSupplierContract);
			}
		}catch(Exception e){
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * 根据合同ID查询合同
	 * @param id
	 * @return
	 */
	@Override
	public SupplierContract getSupplierContractById(String id){
		return supplierContractMapper.selectSupplierContractById(id);
	}
	
	/**
	 * 创建使用费收款单
	 * @param contract
	 * @param userName
	 * @throws Exception
	 */
	@Override
	public boolean createFinanceUseFeeCashBill(SupplierContract contract, String userName){
		//----------平台使用费-----
		if(null != contract.getUseFee() && contract.getUseFee().compareTo(BigDecimal.ZERO) > 0){
			MerchantsSettledCashbillVo useFee = new MerchantsSettledCashbillVo();
			useFee.setContractNumber(contract.getContractNo());
			//费用类型(1:平台使用费 2:保证金) 
			useFee.setCostType(1);
			useFee.setSupplierCode(contract.getSupplierCode());
			useFee.setSupplierName(contract.getSupplier());
			//汇款金额 
			useFee.setRemitAmount(contract.getUseFee().doubleValue());
			//支付方式（1:银行转账 2:上期转入） 
			useFee.setPayStyle(1);
			//登记人
			useFee.setRegisterPerson(userName);
			useFee.setContractStartDate(contract.getEffectiveDate());
			useFee.setContractEndDate(contract.getFailureDate());
			boolean useFeeResult = financeSettleApiService.insertMerchantsSettledCashbill(useFee);
			if(!useFeeResult){
				return false;
			}else{
				contract.setUseFeeStatus(MerchantConstant.FEE_STATUS_WAIT_PAY);
			}
		}
		
		return true;
	}
	
	/**
	 * 更新平台使用费
	 * @param contract
	 * @param userName
	 * @param currentContract
	 * @throws Exception
	 */
	@Override
	public boolean updateFinanceUseFeeCashBill(SupplierContract contract, String userName, SupplierContract currentContract){
		//----------平台使用费-----
		if(null != contract.getUseFee() && contract.getUseFee().compareTo(BigDecimal.ZERO) > 0){
			if(currentContract.getUseFee().compareTo(contract.getUseFee()) != 0){
				MerchantsSettledCashbillVo useFee = new MerchantsSettledCashbillVo();
				useFee.setContractNumber(contract.getContractNo());
				//费用类型(1:平台使用费 2:保证金) 
				useFee.setCostType(1);
				//汇款金额 
				useFee.setRemitAmount(contract.getUseFee().doubleValue());
				//支付方式（1:银行转账 2:上期转入） 
				useFee.setPayStyle(1);
				useFee.setSupplierCode(contract.getSupplierCode());
				useFee.setContractStartDate(contract.getEffectiveDate());
				useFee.setContractEndDate(contract.getFailureDate());
				boolean useFeeResult = financeSettleApiService.updateMerchantsSettledCashbillRemitamount(useFee);
				if(!useFeeResult){
					return false;
				}
			}
		}else{
			contract.setUseFeeStatus("");
			//收款单怎么处理？线下沟通财务，作废收款单
			
		}
		return true;
	}
	
	// 创建保证金收款单       param： preContract 为续签商家的当前合同，即续签合同的上期合同
	@Override
	public boolean createFinanceDepositCashBill(SupplierContract contract, String userName, SupplierContract preContract){
		boolean depositResult = true;
		//----------保证金-----
		if(null != contract.getDeposit() && contract.getDeposit().compareTo(BigDecimal.ZERO) > 0){
			MerchantsSettledCashbillVo deposit = new MerchantsSettledCashbillVo();
			
			deposit.setContractNumber(contract.getContractNo());
			//费用类型(1:平台使用费 2:保证金) 
			deposit.setCostType(2);
			deposit.setSupplierCode(contract.getSupplierCode());
			deposit.setSupplierName(contract.getSupplier());
			//汇款金额 
			deposit.setRemitAmount(contract.getDeposit().doubleValue());
			//支付方式（1:银行转账 2:上期转入） 
			deposit.setPayStyle(1);
			//登记人
			deposit.setRegisterPerson(userName);
			deposit.setContractStartDate(contract.getEffectiveDate());
			deposit.setContractEndDate(contract.getFailureDate());
			depositResult = financeSettleApiService.insertMerchantsSettledCashbill(deposit);
			contract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_PAY);
		}
		//---------- 针对续签合同有上期保证金转入的情形----------
		boolean depositResult2 = true;
		MerchantsSettledCashbillVo depositFromPre = null;
		String isTransferDeposit = contract.getIsTransferDeposit() ;
		if( MerchantConstant.CONTRACT_TRANSFER_FLAG.equals(isTransferDeposit) && 
				contract.getPreDeposit().compareTo(BigDecimal.ZERO)>0 ){
			// 新增一个本期收款单（支付类型：上期转入）
			depositFromPre = new MerchantsSettledCashbillVo();
			depositFromPre.setContractNumber(contract.getContractNo());
			//费用类型(1:平台使用费 2:保证金) 
			depositFromPre.setCostType(2);
			depositFromPre.setSupplierCode(contract.getSupplierCode());
			depositFromPre.setSupplierName(contract.getSupplier());
			//汇款金额 
			depositFromPre.setRemitAmount(contract.getPreDeposit().doubleValue());
			//支付方式（1:银行转账 2:上期转入） 
			depositFromPre.setPayStyle(2);
			depositFromPre.setPriorContractNumber(preContract.getContractNo());
			//登记人
			depositFromPre.setRegisterPerson(userName);
			depositFromPre.setContractStartDate(contract.getEffectiveDate());
			depositFromPre.setContractEndDate(contract.getFailureDate());
			
			if( null!=depositFromPre ){
				depositResult2 = financeSettleApiService.insertMerchantsSettledCashbill(depositFromPre);
			}
		}
		if( !depositResult||!depositResult2 ){
			return false;
		}else{
			if( null!=depositFromPre ){// 有上期转入的情形
				preContract.setDepositStatus( MerchantConstant.FEE_STATUS_WAIT_TRANSPORT );
				contract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_PAY);
			}
		}
		return true;
	}
	
	/**
	 * 修改保证金
	 * @param contract
	 * @param userName
	 * @param currentContract 未修改之前的合同(非当前合同)
	 * @param preContract 当前合同
	 * @throws Exception
	 */
	@Override
	public boolean updateFinanceDepositCashBill(SupplierContract contract, String userName, 
			SupplierContract currentContract,SupplierContract preContract) {
		//----------保证金-----
		boolean depositResult = true;
		if(null != contract.getDeposit() && contract.getDeposit().compareTo(BigDecimal.ZERO) > 0){
			if(currentContract.getDeposit().compareTo(contract.getDeposit()) != 0){
				MerchantsSettledCashbillVo deposit = new MerchantsSettledCashbillVo();
				
				deposit.setContractNumber(contract.getContractNo());
				//费用类型(1:平台使用费 2:保证金) 
				deposit.setCostType(2);
				deposit.setSupplierCode(contract.getSupplierCode());
				//汇款金额 
				deposit.setRemitAmount(contract.getDeposit().doubleValue());
				//支付方式（1:银行转账 2:上期转入） 
				deposit.setPayStyle(1);
				deposit.setContractStartDate(contract.getEffectiveDate());
				deposit.setContractEndDate(contract.getFailureDate());
				depositResult = financeSettleApiService.updateMerchantsSettledCashbillRemitamount(deposit);
				contract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_PAY);
			}
		}else{
			contract.setDepositStatus("");
			//收款单怎么处理？线下沟通财务，作废收款单
		}
		//---------- 针对续签合同有上期保证金转入的情形(修改之前 ，未做上期转入)----------
		MerchantsSettledCashbillVo depositFromPre = null;
		String isTransferDeposit = contract.getIsTransferDeposit() ;
		if( MerchantConstant.CONTRACT_TRANSFER_FLAG.equals(isTransferDeposit) && 
				contract.getPreDeposit().compareTo(BigDecimal.ZERO)>0 &&
				!MerchantConstant.CONTRACT_TRANSFER_FLAG.equals( currentContract.getIsTransferDeposit() )
				){
			// 新增一个本期收款单（支付类型：上期转入）
			depositFromPre = new MerchantsSettledCashbillVo();
			depositFromPre.setContractNumber(contract.getContractNo());
			//费用类型(1:平台使用费 2:保证金) 
			depositFromPre.setCostType(1);
			depositFromPre.setSupplierCode(contract.getSupplierCode());
			depositFromPre.setSupplierName(contract.getSupplier());
			//汇款金额 
			depositFromPre.setRemitAmount(contract.getPreDeposit().doubleValue());
			//支付方式（1:银行转账 2:上期转入） 
			depositFromPre.setPayStyle(2);
			//登记人
			depositFromPre.setRegisterPerson(userName);
			//上期合同编号
			depositFromPre.setPriorContractNumber(preContract.getContractNo());
			depositFromPre.setContractStartDate(contract.getEffectiveDate());
			depositFromPre.setContractEndDate(contract.getFailureDate());
		}
		
		boolean depositResult2 = true;
		if( null!=depositFromPre ){
			depositResult2 = financeSettleApiService.insertMerchantsSettledCashbill(depositFromPre);
		}
		if( !depositResult||!depositResult2 ){
			return false;
		}else{
			if( null!=depositFromPre ){// 有上期转入的情形
				preContract.setDepositStatus( MerchantConstant.FEE_STATUS_WAIT_TRANSPORT );
				contract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_PAY);
			}
		}
		return true;
	}
	
	/**
	 * 修改保证金
	 * @param contract
	 * @param userName
	 * @throws Exception
	 */
	@Override
	public boolean createFinanceRefundBill(SupplierContract contract, String userName,String type){
		MerchantsRefundCashbillVo refund = new MerchantsRefundCashbillVo();
		refund.setContractNumber(contract.getContractNo());
		refund.setSupplierCode(contract.getSupplierCode());
		refund.setSupplierName(contract.getSupplier());
		BigDecimal totalRefund = BigDecimal.ZERO;
		/**退款类型(1:平台使用费 2:保证金)**/
		if("deposit".equals(type)){
			refund.setRefundType(2);
			if(String.valueOf(MerchantConstant.YES).equals(contract.getIsTransferDeposit())){
				totalRefund = totalRefund.add(contract.getPreDeposit());
			}
			totalRefund = totalRefund.add(contract.getDeposit());
			refund.setRefundAmount(totalRefund.doubleValue());
		}else{
			refund.setRefundType(1);
			refund.setRefundAmount(contract.getUseFee().doubleValue());
		}
		refund.setApplyPerson(userName);
		refund.setContractStartDate(contract.getEffectiveDate());
		refund.setContractEndDate(contract.getFailureDate());
		boolean refundResult = financeRefundApiService.insertMerchantsRefundCashbill(refund);
		if(refundResult){
			SupplierContract supplierContract = new SupplierContract();
			supplierContract.setId(contract.getId());
			if("deposit".equals(type)){
				supplierContract.setDepositStatus(MerchantConstant.FEE_STATUS_WAIT_REFUND);
			}else{
				supplierContract.setUseFeeStatus(MerchantConstant.FEE_STATUS_WAIT_REFUND);
			}
			supplierContract.setUpdateTime(DateUtil2.getCurrentDateTimeToStr2());
			supplierContract.setUpdateUser(userName);
			this.updateSupplierContract(supplierContract);
			
			// 添加供应商合同修改日志
			try {
				MerchantOperationLog log = new MerchantOperationLog();
				log.setType(MerchantConstant.LOG_FOR_CONTRACT);
				log.setOperator(userName);
				log.setContractNo(contract.getContractNo());
				if("deposit".equals(type)){
					log.setOperationNotes("申请保证金退款");
				}else{
					log.setOperationNotes("申请平台使用费退款");
				}
				log.setOperationType(OperationType.UPDATE_CONTRACT.getDescription());
				log.setMerchantCode(contract.getSupplierCode());
				merchantServiceNew.insertMerchantLog(log);

			} catch (Exception e) {
				logger.error("供应商合同(" + contract.getContractNo() + ")增加修改日志异常.");
			}
			
			return true;
		}else{
			return false;
		}
	}
	
	public void recoverApiLicenceBySupplierId(String supplierId){
		// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(supplierId);
		SupplierContract contract = supplierContractMapper.getCurrentContractBySupplierId(supplierId); 
		if(null == supplierExpand || contract == null){
			logger.error("恢复处理API库存更新和查询授权出现异常-找不到扩展表或合同表数据");
			return;
		}
		Integer contractRemainingDays = contract.getContractRemainingDays();
		Integer institutionalRemainingDays = supplierExpand.getInstitutionalRemainingDays();
		Integer businessRemainingDays = supplierExpand.getBusinessRemainingDays();
		if(contractRemainingDays != null && contractRemainingDays >0 && institutionalRemainingDays != null && institutionalRemainingDays >0
				&& businessRemainingDays != null && businessRemainingDays >0){
			ApiKeyMetadata apiKeyMetadata;
			try {
				apiKeyMetadata = apiKeyService.queryApiKeyByMetadataVal(supplierExpand.getMerchantCode());
				if(null != apiKeyMetadata){					  
					apiKeyService.recoverApiLicence(apiKeyMetadata.getId());
					//刷新缓存
					apiService.refreshApiCache();
				}
			} catch (Exception e) {
				logger.error("恢复处理API库存更新和查询授权出现异常"+e);
			}
		}
	}
	
	/**
	 * 保存草稿到redis之前，处理表单数据。
	 * @param attachmentFormVo
	 * @param contract
	 */
	@Override
	public void updateSupplierContractAccordingFormVo(AttachmentFormVo attachmentFormVo,SupplierContract contract ){
		String[] contract_attachment = attachmentFormVo.getContract_attachment();
		String[] trademark = attachmentFormVo.getTrademark();
		String[] brandNo = attachmentFormVo.getBrandNo();
		String[] deductionPoint = attachmentFormVo.getDeductionPoint();
		String[] authorizer = attachmentFormVo.getAuthorizer();
		String[] type = attachmentFormVo.getType();
		String[] registeredTrademark = attachmentFormVo.getRegisteredTrademark();
		String[] registeredStartDate = attachmentFormVo.getRegisteredStartDate();
		String[] registeredEndDate = attachmentFormVo.getRegisteredEndDate();
		String[] beAuthorizers = attachmentFormVo.getBeAuthorizer();
		String[] authorizStartdates = attachmentFormVo.getAuthorizStartdate();
		String[] authorizEnddates = attachmentFormVo.getAuthorizEnddate();
		//保存附件
		List<SupplierContractAttachment> attachmentList = new ArrayList<SupplierContractAttachment>();
		if(contract_attachment!=null){
			for(String attachment:contract_attachment){
				String[] attachmentArray = attachment.split(";");
				if(null!=attachmentArray&&attachmentArray.length==3){
					SupplierContractAttachment att = new SupplierContractAttachment();
					att.setId(UUIDGenerator.getUUID());
					att.setAttachmentType(attachmentArray[0]);
					att.setAttachmentName(attachmentArray[1]);
					att.setAttachmentRealName(attachmentArray[2]);
					attachmentList.add(att);
				}
			}
		}
		if(!attachmentList.isEmpty()){
			contract.setAttachmentList(attachmentList);
		}
		
		//保存商标
		List<SupplierContractTrademark> markList = new ArrayList<SupplierContractTrademark>();
		
		
		
		if(trademark!=null){
			for(int i=0,_len =trademark.length;i<_len;i++){
				int subCount = 6;
				String trademarkId =  UUIDGenerator.getUUID();
				SupplierContractTrademark mark = new SupplierContractTrademark();
				mark.setId(trademarkId);
				mark.setTrademark(trademark[i]);
				mark.setBrandNo(brandNo[i]);
				mark.setDeductionPoint(NumberUtils.isDigits(deductionPoint[i])?Integer.valueOf(deductionPoint[i]): null);
				mark.setAuthorizer(authorizer[i]);
				mark.setType(type[i]);
				mark.setRegisteredTrademark(registeredTrademark[i]);
				mark.setRegisteredStartDate(StringUtils.isBlank(registeredStartDate[i])?null:registeredStartDate[i]);
				mark.setRegisteredEndDate(StringUtils.isBlank(registeredEndDate[i])?null:registeredEndDate[i]);
				
				if(!StringUtils.isBlank(mark.getBrandNo())){				
					com.yougou.pc.model.brand.Brand brand = null;
					try {
						brand = commodityBaseApiService.getBrandByNo(mark.getBrandNo());
						mark.setBrandName(brand.getBrandName());
					} catch (Exception e) {
						logger.error(MessageFormat.format(
								"commodityBaseApiSercie接口获取品牌[{0}]失败", mark.getBrandNo()), e);
					}	
				}
				
				//商标明细
				List<SupplierContractTrademarkSub> markSubList= new ArrayList<SupplierContractTrademarkSub>();
				subCount = subCount*i;
				String beAuthorizer = "";
				String authorizStartdate = "";
				String authorizEnddate = "";
				for(int j=0;j<6;j++){
					int index = j+subCount;
					beAuthorizer = beAuthorizers[index];
					authorizStartdate = StringUtils.isBlank(authorizStartdates[index])?null:authorizStartdates[index];
					authorizEnddate = StringUtils.isBlank(authorizEnddates[index])?null:authorizEnddates[index];
					if(StringUtils.isBlank(beAuthorizer)&&StringUtils.isBlank(authorizStartdate)&&StringUtils.isBlank(authorizEnddate)){
						continue;
					}
					
					SupplierContractTrademarkSub sub = new SupplierContractTrademarkSub();
					sub.setId(UUIDGenerator.getUUID());
					sub.setTrademarkId(trademarkId);
					sub.setBeAuthorizer(beAuthorizer);
					sub.setAuthorizStartdate(authorizStartdate);
					sub.setAuthorizEnddate(authorizEnddate);
					sub.setLevel(j+1);
					markSubList.add(sub);
				}
				mark.setTrademarkSubList(markSubList);
				markList.add(mark);
			}
		}
					
		if(!markList.isEmpty()){
			contract.setTrademarkList(markList);
		}
	}
	
	/** 资质的处理 */
	public boolean saveOrDeleteAttachment(List<SupplierContractAttachment> attachmentList) throws Exception{
		if(attachmentList.isEmpty()){
//			throw new Exception("商家资质附件不允许为空，请检查！");
			return true;
		}else{
			for(SupplierContractAttachment attach :attachmentList){
				String deleteFlag = attach.getDeleteFlag();
				// 先查询
				List<SupplierContractAttachment> queryResult = supplierContractMapper.queryAttachmentExistByVO(attach);
				if( queryResult!= null && queryResult.size() >0 ){// 后处理
					// 已存在
					if( null!=deleteFlag && deleteFlag.equals("-1") ){
						if (queryResult.size()>1){// 查到多条  删除不执行 以免误删
							logger.error("@saveOrDeleteAttachment-操作商家资质时，发现页面传给后台的附件在数据库中存在多条记录，vo ： "+attach.toString());
						}else{// 查到1条，删除该条
							supplierContractMapper.deleteAttachmentByVO( queryResult.get(0) );
						}
					}
				}else{
					// 不存在
					if( null==deleteFlag || !deleteFlag.equals("-1") ){// 新增一条
						attach.setId(UUIDGenerator.getUUID());
						supplierContractMapper.insertSupplierContractAttachment(attach);
					}
				}
			}
			return true;
		}
	}
	
	//保存品类授权
	private void saveOrUpdateBrandAndCats(String supplierId,String bankNoHidden,String catNameHidden){
		SupplierVo supplierVo = new SupplierVo();
		supplierVo.setId(supplierId);
		merchantServiceNew.updateMerchantsBankAndCat(supplierVo,bankNoHidden, catNameHidden);
	}
}
