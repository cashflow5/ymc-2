package com.belle.infrastructure.orm.hibernate;

import java.sql.Types;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.QueryException;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * 该类主要处理hibernate 长文本类型导致的问题（inner join）
 * 
 * @author Administrator
 * 
 */
public class HibernateTypeDialect extends MySQLDialect {
	
	public HibernateTypeDialect() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
		registerFunction("bitwise_and", new BitwiseAndSQLFunction("bitwise_and", Hibernate.LONG));
	}
	
	/**
	 * 位与函数
	 * 
	 * @author 杨梦清
	 *
	 */
	class BitwiseAndSQLFunction extends StandardSQLFunction {

		public BitwiseAndSQLFunction(String name, Type type) {
			super(name, type);
		}

		@Override
		public String render(List list, SessionFactoryImplementor implementor) {
			if (list.size() != 2) {
				throw new QueryException("bitwise_and function must be specified 2 arguments");
			}
			return list.get(0) + " & " + list.get(1);
		}
	}
}
