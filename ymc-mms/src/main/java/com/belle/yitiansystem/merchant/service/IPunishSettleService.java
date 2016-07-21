package com.belle.yitiansystem.merchant.service;  

import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;
import com.belle.yitiansystem.merchant.model.vo.PunishSettleVo;

public interface IPunishSettleService {

	/** 
	 * getPunishSettle:违规结算分页列表 
	 * @author li.n1 
	 * @param query
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 */  
	PageFinder<PunishSettle> getPunishSettle(Query query,
			PunishSettleVo vo);
	
	/**
	 * addPunishSettle:添加违规结算
	 * @author li.n1 
	 * @param settle 
	 * @since JDK 1.6
	 */
	public void addPunishSettle(PunishSettle settle) throws Exception ;
	
	/** 
	 * cancelPunishSettle:取消违规结算，状态改为4申请修改的状态
	 * @author li.n1 
	 * @param id
	 * @return 
	 * @since JDK 1.6 
	 */  
	boolean cancelPunishSettle(String id);
	/**
	 * getPunishSettle:根据id获取punishSettle对象 
	 * @author li.n1 
	 * @param id
	 * @return 
	 * @since JDK 1.6
	 */
	public PunishSettle getPunishSettle(String id);

	/** 
	 * submitPunishSettle:提交财务的违规结算 
	 * @author li.n1 
	 * @param settle
	 * @return 
	 * @since JDK 1.6 
	 */  
	boolean submitPunishSettle(PunishSettle settle);
	/**
	 * getPunishIdBySettleId：根据结算Id查询违规订单的Id主键
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getPunishIdBySettleId(String id);
	/**
	 * updatePunishOrderSubmitStatus:更新提交到结算的状态，还未提交的财务，只是提交到结算列表
	 * @param id
	 * @return
	 */
	public boolean updatePunishOrderSubmitStatus(String id);
	/**
	 * vaildPunishMQ:审核的违规订单发送到MQ
	 * @author li.n1 
	 * @param settle 
	 * @since JDK 1.6
	 */
	void vaildPunishMQ(PunishSettle settle);
}
