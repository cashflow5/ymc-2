package com.belle.yitiansystem.merchant.service.impl;  

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.finance.merchants.violationdeduction.model.vo.ViolationDeductionVo;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.merchant.dao.IPunishSettleDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;
import com.belle.yitiansystem.merchant.model.vo.PunishSettleVo;
import com.belle.yitiansystem.merchant.service.IPunishSettleService;

@Service
public class PunishSettleServiceImpl implements IPunishSettleService {
	@Resource
	private IPunishSettleDao punishSettleDao;
	@Resource
	private ISqlService sqlService;
	@Resource
	private RabbitTemplate rabbitMerchantsTemplate;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PunishSettleServiceImpl.class);
	/** 
	 * @see com.belle.yitiansystem.merchant.service.IPunishSettleService#getPunishSettle(com.belle.infrastructure.orm.basedao.Query, com.belle.yitiansystem.merchant.model.vo.PunishSettleVo) 
	 */
	@Override
	public PageFinder<PunishSettle> getPunishSettle(Query query,
			PunishSettleVo vo) {
		StringBuffer sb = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		//StringBuffer paramSb = new StringBuffer();
		String hql = "from PunishSettle where 1=1 ";
		if(StringUtils.isNotBlank(vo.getAudit())){
			sb.append("and audit = ? ");
			parms.add(vo.getAudit());
		}
		if(StringUtils.isNotBlank(vo.getDeductType())){
			sb.append("and deductType = ? ");
			parms.add(vo.getDeductType());
		}
		if(StringUtils.isNotBlank(vo.getRegistNum())){
			sb.append("and registNum = ? ");
			parms.add(vo.getRegistNum());
		}
		if(StringUtils.isNotBlank(vo.getRegistrant())){
			sb.append("and registrant = ? ");
			parms.add(vo.getRegistrant());
		}
		if(StringUtils.isNotBlank(vo.getSettleNo())){
			sb.append("and settleNo = ? ");
			parms.add(vo.getSettleNo());
		}
		if(StringUtils.isNotBlank(vo.getSupplier())){
			sb.append("and supplier = ? ");
			parms.add(vo.getSupplier());
		}
		if(StringUtils.isNotBlank(vo.getSupplierCode())){
			sb.append("and supplierCode = ? ");
			parms.add(vo.getSupplierCode());
		}
		if(StringUtils.isNotBlank(vo.getStatus())){
			sb.append("and status = ? ");
			parms.add(vo.getStatus());
		}
		if(vo.getRegistTimeStart()!=null && vo.getRegistTimeEnd()!=null){
			sb.append("and registTime between ? and ? ");
			parms.add(vo.getRegistTimeStart());
			parms.add(vo.getRegistTimeEnd());
		}else if(vo.getRegistTimeStart()==null && vo.getRegistTimeEnd()!=null){
			sb.append("and registTime <=  ?");
			parms.add(vo.getRegistTimeEnd());
		}else if(vo.getRegistTimeStart()!=null && vo.getRegistTimeEnd()==null){
			sb.append("and registTime between ? and ? ");
			parms.add(vo.getRegistTimeStart());
			parms.add(new Date());
		}
		sb.append(" and deleteFlag = 0 ");
		PageFinder<PunishSettle> pageFinder = 
				punishSettleDao.pagedByHQL(hql+sb.toString()+" order by registTime desc", query.getPage(), 
						query.getPageSize(), parms.toArray());
		return pageFinder;
	}

	/** 
	 * @see com.belle.yitiansystem.merchant.service.IPunishSettleService#addPunishSettle(com.belle.yitiansystem.merchant.model.pojo.PunishSettle) 
	 */
	@Override
	@Transactional
	public void addPunishSettle(PunishSettle settle) 
			throws Exception {
		punishSettleDao.save(settle);
	}

	@Override
	@Transactional
	public boolean submitPunishSettle(PunishSettle settle) {
		boolean flag = false;
		try{
			punishSettleDao.getTemplate().update(settle);
			flag = true;
		}catch(Exception e){
			logger.error("提交违规结算订单到财务，服务器发生错误：",e);
		}
		return flag;
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.IPunishSettleService#cancelPunishSettle(java.lang.String) 
	 */
	@Override
	@Transactional
	public boolean cancelPunishSettle(String id) {
		boolean flag = false;
		PunishSettle settle = punishSettleDao.getTemplate().load(PunishSettle.class, id);
		try{
			//取消之后修改状态为已取消的状态
			settle.setStatus("4");
			punishSettleDao.getTemplate().update(settle);
			flag = true;
		}catch(Exception e){
			logger.error("取消违规订单，服务器发生错误：",e);
		}
		return flag;
	}
	
	@Override
	@Transactional
	public boolean updatePunishOrderSubmitStatus(String id){
		try{
			String sql = "update tbl_sp_supplier_punish_order set is_submit='0',settle_id = null where settle_id = ?";
			return sqlService.updateObject(sql, new Object[]{id});
		}catch(Exception e){
			logger.error("更新违规订单提交到结算的状态，服务器发生错误：",e);
		}
		return false;
	}
	
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.IPunishSettleService#getPunishSettle(java.lang.String) 
	 */
	@Override
	public PunishSettle getPunishSettle(String id) {
		return punishSettleDao.getTemplate().load(PunishSettle.class, id);
	}

	@Override
	public List<Map<String, Object>> getPunishIdBySettleId(String id) {
		List<Object> param = new ArrayList<Object>();
		String sql = "select id as punish_id from tbl_sp_supplier_punish_order where settle_id = ?";
		param.add(id);
		return sqlService.getDatasBySql(sql, null, param);
	}

	@Override
	public void vaildPunishMQ(PunishSettle settle) {
		ViolationDeductionVo vo = new ViolationDeductionVo();
		vo.setRegisterCode(settle.getRegistNum());
		//如果为空，设置为1新建的状态
		if(StringUtils.isBlank(settle.getStatus())){
			settle.setStatus("1");
		}
		vo.setRegisterStatus(Integer.parseInt(settle.getStatus()));
		vo.setSupplierCode(settle.getSupplierCode());
		vo.setSupplierName(settle.getSupplier());
		//如果扣款类型为空，设置为超时效类型
		if(StringUtils.isBlank(settle.getDeductType())){
			settle.setDeductType("1");
		}
		vo.setDeductType(settle.getDeductType());
		vo.setStartViolationDate(settle.getSettleStart());
		vo.setEndViolationDate(settle.getSettleEnd());
		vo.setViolationQuantity(settle.getSettleOrderNum());
		vo.setDeductAmount(settle.getDeductMoney());
		vo.setRegisterant(settle.getRegistrant());
		vo.setRegisterDate(settle.getRegistTime());
		vo.setAuditor(settle.getAudit());
		vo.setAuditDate(settle.getAuditTime());
		
		//其余信息等财务返回
		rabbitMerchantsTemplate.convertAndSend(vo);
		logger.info("审核违规订单后发送MQ,发送内容：" + vo);
	}
}
