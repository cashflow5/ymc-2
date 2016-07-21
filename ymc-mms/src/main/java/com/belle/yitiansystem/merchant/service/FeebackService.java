package com.belle.yitiansystem.merchant.service;

import java.util.List;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;
import com.belle.yitiansystem.merchant.model.pojo.FeebackReply;
import com.belle.yitiansystem.merchant.model.vo.FeebackVo;

/**
 * 意见反馈
 * @author he.wc
 *
 */
public interface FeebackService {

	/**
	 * 意见反馈列表
	 * @param punishOrderVo
	 * @param query
	 * @return
	 */
	public PageFinder<Feeback> queryFeebackList(FeebackVo feebackVo, Query query)  throws Exception;
	
	/**
	 * 通过id得到意见反馈信息
	 * @param id
	 * @return
	 */
	public Feeback getFeebackById(String id);
	
	/**
	 * 更新反馈信息为已读
	 * @param id
	 */
	public void updateFeedbackIsRead(String id);
	
	/**
	 * 得到该意见反馈的回复列表
	 * @param feebackid
	 * @return
	 */
	public List<FeebackReply> getFeebackReplyList(String feebackid);
	
	/**
	 * 新建回复信息
	 * @param feebackReply
	 */
	public void saveFeebackReply(FeebackReply feebackReply);
	
	/**
	 * 标志删除回复信息
	 */
	public void delFeebackReply(String id);
	

}