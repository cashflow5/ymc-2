package com.belle.yitiansystem.systemmgmt.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.yitiansystem.systemmgmt.dao.ISystemConfigDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;

/**
 * 类AuthorityResourcesDaoImpl.java的实现描述：TODO 系统配置
 * @author yinhongbiao 2011-4-21 下午02:19:47
 */
@Repository
public class SystemConfigDaoImpl extends HibernateEntityDao<SystemConfig> implements ISystemConfigDao {

	@Override
	public SystemConfig findSystemConfigByKey(String key) {
		Session session = getHibernateSession();
		DataSourceSwitcher.setDataSourceSys();
		String hql =  "select sc from SystemConfig sc where sc.deleteFlag = ? and sc.key = ?";
		Query query = session.createQuery(hql).setParameter(0, "1").setParameter(1, key);
		List<SystemConfig> scs = null;
		try {
			scs = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseSession(session);
			DataSourceSwitcher.clearDataSource();
		}
		if(scs!=null && scs.size()>0){
			return scs.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据key模糊查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemConfig> findAllSystemConfigByKey(String value) {
		CriteriaAdapter criteriaAdapter = this.createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		criteria.add(Restrictions.like("key", "%"+value+"%"));
		List<SystemConfig> list = criteria.list();
		releaseHibernateSession(criteriaAdapter.getSession());
		return list;
	}

	/**
	 * 更新系统配置
	 */
	public boolean updateSystemConfig(SystemConfig systemConfig) {
		String hql="update SystemConfig  b  set  b.value=?  where  b.key=?";
		try {
			Session session = getHibernateSession();
			org.hibernate.Query query = session.createQuery(hql);
			query.setParameter(0, systemConfig.getValue());
			query.setParameter(1, systemConfig.getKey());
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}