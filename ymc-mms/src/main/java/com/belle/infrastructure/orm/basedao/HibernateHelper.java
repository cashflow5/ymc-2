package com.belle.infrastructure.orm.basedao;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateHelper {
	
	private Session session;
	private Transaction transaction;
	
	public HibernateHelper(SessionFactory sessionFactory) {
		session = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
	}
	
	/**
	 * 开起事务
	 */
	public void beginTransaction(){
		transaction = session.beginTransaction();
//		transaction.begin();
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public Connection getConnection(){
		return session.connection();
	}
	
	/**
	 * 提交事物
	 * @throws Exception
	 */
	public void commit() throws Exception{
		TransactionSynchronizationManager.unbindResource(session.getSessionFactory());
		transaction.commit();
		session.close();
	}
	
	public void rollback() throws Exception{
		Object obj = TransactionSynchronizationManager.getResource(session.getSessionFactory());
		if(obj != null){
			TransactionSynchronizationManager.unbindResource(session.getSessionFactory());
		}
		
		transaction.rollback();
		close();
	}
	
	public void close(){
		Object obj = TransactionSynchronizationManager.getResource(session.getSessionFactory());
		if(obj != null){
			TransactionSynchronizationManager.unbindResource(session.getSessionFactory());
		}
		if(session.isOpen()){
			session.close();
		}
	}
	
	
	
	

}
