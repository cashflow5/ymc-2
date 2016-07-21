package com.belle.yitiansystem.merchant.dao.impl;  

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.yitiansystem.merchant.dao.IStockPunishRuleDetailDao;
import com.belle.yitiansystem.merchant.model.pojo.StockPunishRuleDetail;

@Repository
public class StockPunishRuleDetailDaoImpl 
	extends HibernateEntityDao<StockPunishRuleDetail> 
	implements IStockPunishRuleDetailDao {

	/** 
	 * @see com.belle.yitiansystem.merchant.dao.IStockPunishRuleDetailDao#getTemplate() 
	 */
	@Override
	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

	/** 
	 * @see com.belle.yitiansystem.merchant.dao.IStockPunishRuleDetailDao#getStockPunishRuleDetail(java.lang.String) 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockPunishRuleDetail> getStockPunishRuleDetail(String ruleId) {
		String hql = "select new StockPunishRuleDetail(id,punishRateBegin,punishRateEnd,punishRule)" +
				" from StockPunishRuleDetail where punishRuleId = ? order by punishRateBegin";
		return this.getTemplate().find(hql,ruleId);
	}

	/** 
	 * @see com.belle.yitiansystem.merchant.dao.IStockPunishRuleDetailDao#savePunishRuleDetail(java.util.List) 
	 */
	@Override
	public void savePunishRuleDetail(String ruleId, List<StockPunishRuleDetail> detailList) {
		Session session = null;
		String sql = "delete from tbl_sp_supplier_punish_rule_detail where punish_rule_id = ?";
		try{
			session = getHibernateSession();
			session.createSQLQuery(sql).setString(0, ruleId).executeUpdate();
			for(StockPunishRuleDetail detail : detailList){
				detail.setPunishRuleId(ruleId);
				session.save(detail);
			}
			session.flush();
		}finally {
			releaseHibernateSession(session);
		}
	}

}
