package com.yougou.kaidian.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.belle.finance.biz.dubbo.ICostSetOfBooksDubboService;
import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.dao.MerchantSupplierExpandMapper;
import com.yougou.kaidian.user.dao.SupplierContractMapper;
import com.yougou.kaidian.user.model.pojo.SupplierContract;
import com.yougou.kaidian.user.model.pojo.SupplierContractAttachment;
import com.yougou.kaidian.user.model.pojo.SupplierContractTrademark;
import com.yougou.kaidian.user.model.pojo.SupplierContractTrademarkSub;
import com.yougou.kaidian.user.service.IMerchantInfo;
import com.yougou.merchant.api.supplier.vo.MerchantUser;

@Service
public class MerchantInfoImpl implements IMerchantInfo {

	@Resource
	private  SupplierContractMapper  supplierContractMapper;
	@Override
	public SupplierContract getSupplierContractBySupplierId(String supplierId) {
		SupplierContract contract = supplierContractMapper.getCurrentContractBySupplierId(supplierId); 
		if(contract==null){
			return null;
		}
		return getSupplierContract(contract.getId());
	}
	public SupplierContract getSupplierContract(String id){
		SupplierContract contract = supplierContractMapper.selectSupplierContractById(id);
		List<SupplierContractAttachment> attachmentList = supplierContractMapper.selectSupplierContractAttachmentByContractId(id);
		List<SupplierContractTrademark> trademarkList = supplierContractMapper.selectSupplierContractTrademark(id);
		for(SupplierContractTrademark trademark:trademarkList){
			List<SupplierContractTrademarkSub> trademarkSubList = supplierContractMapper.selectSupplierContractTrademarkSubByTrademarkId(trademark.getId());
			trademark.setTrademarkSubList(trademarkSubList);
		}
		if(null!=contract){
			contract.setAttachmentList(attachmentList);
			contract.setTrademarkList(trademarkList);
		}
		return contract;
	}
	@Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	/**
	 * 根据商家编号查询商家登录信息
	 */
	@Override
	public MerchantUser getMerchantsBySuppliceCode(String supplierCode) {
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setMerchantCode(supplierCode);
		merchantUser.setDeleteFlag(UserConstant.NOT_DELETED);
		merchantUser.setIsAdministrator( UserConstant.YES );
		List<MerchantUser> merchantUserList = merchantSupplierExpandMapper.queryMerchantUserList(merchantUser);
		if (merchantUserList != null && merchantUserList.size() > 0) {
			merchantUser = merchantUserList.get(0);
		}
		return merchantUser;
	}
	
	@Resource
	private ICostSetOfBooksDubboService costSetofBookApi; 
	
	@Override
	public List<CostSetofBooks> getCostSetofBooksList() throws Exception {
		return costSetofBookApi.queryAllCostSetOfBooks();
	}
	
	 
}
