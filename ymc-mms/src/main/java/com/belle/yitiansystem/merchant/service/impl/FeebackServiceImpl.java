package com.belle.yitiansystem.merchant.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.merchant.dao.FeebackDao;
import com.belle.yitiansystem.merchant.dao.FeebackReplyDao;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;
import com.belle.yitiansystem.merchant.model.pojo.FeebackReply;
import com.belle.yitiansystem.merchant.model.vo.FeebackVo;
import com.belle.yitiansystem.merchant.service.FeebackService;

/**
 * 意见反馈
 * 
 * @author he.wc
 * 
 */
@Service
public class FeebackServiceImpl implements FeebackService {


	@Resource
	private FeebackDao feebackDao;
	
	@Resource
	private FeebackReplyDao feebackReplyDao;
	
	public PageFinder<Feeback> queryFeebackList(FeebackVo feebackVo, Query query) throws Exception  {
		CritMap critMap = new CritMap();
		if(StringUtils.isNotBlank(feebackVo.getEmail())){
			critMap.addEqual("email", feebackVo.getEmail());
		}
		if(StringUtils.isNotBlank(feebackVo.getMerchantCode())){
			critMap.addEqual("merchantCode", feebackVo.getMerchantCode());
		}
		if(StringUtils.isNotBlank(feebackVo.getMerchantName())){
			critMap.addLike("merchantName", feebackVo.getMerchantName());
		}
		if(StringUtils.isNotBlank(feebackVo.getFirstCate())){
			critMap.addEqual("firstCate", feebackVo.getFirstCate());
		}
		if(StringUtils.isNotBlank(feebackVo.getSecondCate())){
			critMap.addEqual("secondCate", feebackVo.getSecondCate());
		}
		if(StringUtils.isNotBlank(feebackVo.getPhone())){
			critMap.addEqual("phone", feebackVo.getPhone());
		}
		if(feebackVo.getStartCreateTime()!=null){
			critMap.addGreatAndEq("createTime", feebackVo.getStartCreateTime());
		}
		if(feebackVo.getEndCreateTime()!=null){
			critMap.addLessAndEq("createTime", feebackVo.getEndCreateTime());
		}
		if(StringUtils.isNotBlank(feebackVo.getEmail())){
			critMap.addEqual("email", feebackVo.getEmail());
		}
		if(StringUtils.isNotBlank(feebackVo.getIsRead())){
			critMap.addEqual("isRead", feebackVo.getIsRead());
		}
		if(StringUtils.isNotBlank(feebackVo.getIsReply())){
			critMap.addEqual("isReply", feebackVo.getIsReply());
		}
		
		critMap.addDesc("createTime");
		
		return feebackDao.pagedByCritMap(critMap, query.getPage(), query.getPageSize());
	}

	
	public Feeback getFeebackById(String id) {
		return feebackDao.getById(id);
	}


	@Transactional
	public void updateFeedbackIsRead(String id) {
		Feeback feeback = feebackDao.getById(id);
		feeback.setIsRead("1");
		feebackDao.getTemplate().update(feeback);
	}


	public List<FeebackReply> getFeebackReplyList(String feebackId) {
		
		CritMap critMap = new CritMap();
	    critMap.addEqual("feebackId", feebackId);
	    critMap.addEqual("isDeleted", "0");
	    critMap.addDesc("createTime");
		return  feebackReplyDao.findByCritMap(critMap, false);
	}

	@Transactional
	public void saveFeebackReply(FeebackReply feebackReply) {
		Feeback feeback = feebackDao.getById(feebackReply.getFeebackId());
		feeback.setIsReply("1");
		feebackDao.getTemplate().update(feeback);
		feebackReplyDao.getTemplate().save(feebackReply);
		
	}


	@Transactional
	public void delFeebackReply(String id) {
		 FeebackReply feebackReply =  feebackReplyDao.getById(id);
		 feebackReply.setIsDeleted("1");
		 feebackReplyDao.getTemplate().update(feebackReply);
		 
	}
	
	
	
	
	
	

	

}