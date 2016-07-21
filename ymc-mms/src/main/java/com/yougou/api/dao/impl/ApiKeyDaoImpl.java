package com.yougou.api.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.HibernateEntityDao;
import com.yougou.api.beans.AppType;
import com.yougou.api.dao.IApiKeyDao;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiKeyMetadata;

@Service
public class ApiKeyDaoImpl extends HibernateEntityDao<ApiKey> implements IApiKeyDao {

	private static final String GET_API_KEY_POTENTIAL_CUSTOMERS_SQL;
	
	private static final String GET_MERCHANT_API_KEY_SQL;
	
	static {
		// 初始化 GET_API_KEY_POTENTIAL_CUSTOMERS_SQL
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select 'MERCHANTS' as metadata_key, t1.supplier_code as metadata_val, t1.supplier as metadata_tag from tbl_sp_supplier t1 where t1.is_valid in (1,-1) ");
		/*
		sqlBuilder.append(" union all ");
		sqlBuilder.append(" select 'CHAIN' as metadata_key, t2.id as metadata_val, t2.seller_name as metadata_tag from tbl_chain_seller_login_account t2 where t2.enabled = 'Y' ");
		*/
		GET_API_KEY_POTENTIAL_CUSTOMERS_SQL = sqlBuilder.toString();
		// 初始化 GET_MERCHANT_API_KEY_SQL
		sqlBuilder.setLength(0);
		sqlBuilder.append(" select ");
		sqlBuilder.append(" t2.id, t2.key_id, t2.metadata_key, t2.metadata_val ");
		sqlBuilder.append(" from ");
		sqlBuilder.append(" tbl_merchant_api_key t1 ");
		sqlBuilder.append(" inner join ");
		sqlBuilder.append(" tbl_merchant_api_key_metadata t2 ");
		sqlBuilder.append(" on(t1.id = t2.key_id) ");
		sqlBuilder.append(" where ");
		sqlBuilder.append(" t1.app_key = ? ");
		sqlBuilder.append(" and ");
		sqlBuilder.append(" t2.metadata_key = ? ");
		GET_MERCHANT_API_KEY_SQL = sqlBuilder.toString();
	}

	@Override
	public String getApiKeyPotentialCustomersSqlStatement() {
		return GET_API_KEY_POTENTIAL_CUSTOMERS_SQL;
	}

	@Override
	public ApiKeyMetadata getApiKeyMetadata(final String appKey, final AppType appType) {
		return getHibernateTemplate().execute(new HibernateCallback<ApiKeyMetadata>() {
			@Override
			public ApiKeyMetadata doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(GET_MERCHANT_API_KEY_SQL);
				sqlQuery.setString(0, appKey);
				sqlQuery.setString(1, appType.name());
				sqlQuery.addEntity(ApiKeyMetadata.class);
				return (ApiKeyMetadata) sqlQuery.uniqueResult();
			}
		});
	}
	
	
	
	 
}
