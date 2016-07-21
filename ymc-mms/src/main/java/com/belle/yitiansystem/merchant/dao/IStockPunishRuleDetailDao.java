package com.belle.yitiansystem.merchant.dao;  

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.yitiansystem.merchant.model.pojo.StockPunishRuleDetail;

public interface IStockPunishRuleDetailDao extends IHibernateEntityDao<StockPunishRuleDetail>{
	/**
	 * 获取HibernateTemplate
	 * @return
	 */
	HibernateTemplate getTemplate();

	/** 
	 * @author li.n1 
	 * @param ruleId
	 * @return 
	 * @since JDK 1.6 
	 */  
	List<StockPunishRuleDetail> getStockPunishRuleDetail(String ruleId);

	/** 
	 * savePunishRuleDetail:
	 * @author li.n1 
	 * @param detailList 
	 * @since JDK 1.6 
	 */  
	void savePunishRuleDetail(String ruleId,List<StockPunishRuleDetail> detailList);
	
}
