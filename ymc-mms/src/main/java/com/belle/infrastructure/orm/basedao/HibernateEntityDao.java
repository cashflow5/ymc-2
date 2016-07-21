package com.belle.infrastructure.orm.basedao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.belle.infrastructure.orm.basedao.CritMap.MatchType;
import com.belle.infrastructure.util.ReflectionUtils;


/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * 
 * @see HibernateDaoSupport
 * @see GenericsUtils
 */

@SuppressWarnings("unchecked")
public abstract class HibernateEntityDao<T> extends HibernateDaoSupport  implements IHibernateEntityDao<T>  {

	/**
	 * DAO实体代理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public HibernateEntityDao() {
		entityClass = ReflectionUtils.getSuperClassGenricType(getClass(),0);
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果�?
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 注入SessionFactory
	 */
	@Resource(name="sessionFactory")
	public void setMySessionFactory(SessionFactory sessionFactory){
	  super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 保存对象.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public Serializable save(T o) throws Exception {
		getHibernateTemplate().saveOrUpdate(o);
		return getId(o); //返回对象主键值
	}
	
	public T saveObject(T o) throws Exception {
		getHibernateTemplate().saveOrUpdate(o);
		return o;
	}
	
	
	
	
	
	
	/**
	 * 删除对象.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public Serializable remove(T o) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		// 根据对象的注解判断此对象是否需要建立索引
//		if (o.getClass().getAnnotation(Indexed.class) != null) {
//			// 使用封装全文搜索引擎后的session进行数据库操作
//			FullTextSession fullTextSession = getFullTextSession();
//			fullTextSession.delete(o);
//		} else {
			getHibernateTemplate().delete(o);
//		}
		return getId(o);
	}

	/**
	 * 根据ID移除对象.
	 * @throws Exception 
	 */
	public Serializable removeById(Serializable id) throws Exception {
		return remove(getById(id));
	}

	
	
	
	
	
	
	public T merge(T o){
		try {
			return getHibernateTemplate().merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 * 
	 * 不带级联查询
	 */
	public T getById(Serializable id) {
		T  obj = (T) getHibernateTemplate().get(entityClass, id);
		return obj;
	}
	
	/**
	 * 根据 critMap 条件获取对象
	 * @param critMap  查询条件集合
	 * @return 
	 */
	public T getObjectByCritMap(final CritMap critMap,final boolean ... useCache){
		
		return (T)super.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) {
				Criteria criteria = buildPropertyFilterCriteria(session.createCriteria(entityClass),critMap);

				if(useCache != null && useCache.length > 0 && useCache[0]){
					criteria.setCacheable(true);
				}
				
				return (T)criteria.uniqueResult();
			}
			
		});
		
	}

	/**
	 * 获取全部对象
	 */
	public List<T> getAll() {
		getHibernateTemplate().setCacheQueries(true);
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 获取全部对象,带排序字段与升降序参数
	 * 
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc(orderBy)));
	}


	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @return 符合条件的对象集合
	 * @see HibernateGenericDao#findBy(Class,String,Object)
	 */
	public List<T> findBy(final String propertyName,final Object value,final boolean ... useCache) {
		Assert.hasText(propertyName);
		List<T> list=null;
		
		try{
			
			list = (List<T>)super.getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Criteria criteria = session.createCriteria(entityClass);
					criteria.add(Restrictions.eq(propertyName, value));
					
					if(useCache != null && useCache.length > 0 && useCache[0]){
						criteria.setCacheable(true);
					}
					
					return	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
				}
				
			});
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据critMap 条件集合查询
	 * @param critMap
	 * @return
	 */
	public List<T> findByCritMap(final CritMap critMap,final boolean ... useCache){
		Assert.notNull(critMap);
		
		List<T>  list = null;
		
		list = (List<T> )super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(entityClass);
				buildPropertyFilterCriteria(criteria,critMap);
				criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
				
				if(useCache != null && useCache.length > 0 && useCache[0]){
					criteria.setCacheable(true);
				}
				return criteria.list();
			}
		});
		
		return list;
	}

	
	
	
	/**
	 * 取得Entity的Criteria. 可变的Restrictions条件列表
	 * {@link #createQuery(String,Object...)}
	 * @param criterions
	 * @return
	 */
//	public Criteria createCriteria(Criterion... criterions) {
//		Criteria criteria = getSession().createCriteria(entityClass);
//		for (Criterion c : criterions) {
//			criteria.add(c);
//		}
//	
//		return criteria;
//		
//	}
	
	
	public CriteriaAdapter createCriteriaAdapter(Criterion... criterions) {
		CriteriaAdapter criteria = new CriteriaAdapter(getSession(), entityClass, criterions);
		return criteria;
	}
	

	/**
	 * 不带属性查询对象,带排序参数和数据条数
	 * 
	 * @return 符合条件的对象集合
	 */
	public List<T> findBy(final String orderBy,final boolean isAsc,final int limit,final boolean ... useCache) {
		Assert.hasText(orderBy);
		List<T>  list = null;
		list = (List<T> )super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(entityClass);
				
				if(useCache != null && useCache.length > 0 && useCache[0]){
					criteria.setCacheable(true);
				}

				if (isAsc)
					criteria.addOrder(Order.asc(orderBy));
				else
					criteria.addOrder(Order.desc(orderBy));
				
				return criteria.setMaxResults(limit).setCacheable(true).list();
			}
		});
		return list;
		
		
//		return createCriteria(orderBy, isAsc).setMaxResults(limit).setCacheable(true).list();
	}

	/**
	 * 根据属性名和属性值查询单个对象
	 * 
	 * @return 符合条件的唯数据 or null
	 */
	public T findUniqueBy(final String propertyName,final Object value,final boolean ... useCache) {
		Assert.hasText(propertyName);
		
		T t = (T)super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(entityClass);
				criteria.add(Restrictions.eq(propertyName, value));
				
				if(useCache != null && useCache.length > 0 && useCache[0]){
					criteria.setCacheable(true);
				}
				
				return	criteria.uniqueResult();
			}
		});
		
		return t;
		
	}

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数,不推荐使用
	 * 
	 * @param values
	 *            可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List<T> find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 根据外置命名查询
	 * 
	 * @param queryName
	 * @param values
	 *            参数值列
	 * @return
	 */
	public List<T> findByNameQuery(String queryName, Object... values) {
		return findByNameQuery(true, queryName, values);
	}
	
	private List<T> findByNameQuery( boolean isCache,String queryName, Object... values) {
		Assert.hasText(queryName);
		getHibernateTemplate().setCacheQueries(isCache);
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	/**
	 * 创建Query对象.
	 * 对于max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下:
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if (null != values && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	
	public T queryObjectByCriteria(Criteria criteria) {
		return (T)criteria.uniqueResult();
	}

	/**
	 * 按hibernate标准查询器进行分页查询
	 * @param pagination
	 * @return
	 */
	public PageFinder<T> pagedByCriteria(Criteria criteria, int pageNo, int pageSize) {
		Integer totalRows = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		if (totalRows.intValue() < 1) {
			return new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
		} else {
			PageFinder<T> finder = new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
			List<T> list = criteria.setFirstResult(finder.getStartOfPage()).setMaxResults(
					finder.getPageSize()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			finder.setData(list);
			return finder;
		}
	}
	
	/**
	 * 分页查询
	 * 
	 */
	public PageFinder<T> pagedByCritMap(final CritMap critMap,final int pageNo,final int pageSize) {
		PageFinder<T> pageFinder;
		
		pageFinder = (PageFinder<T>)super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(entityClass);
				
				buildPropertyFilterCriteria(criteria,critMap);
				
				Integer totalRows = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
				criteria.setProjection(null);
				if (totalRows.intValue() < 1) {
					return new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
				} else {
					PageFinder<T> finder = new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
					List<T> list = criteria.setFirstResult(finder.getStartOfPage()).setMaxResults(
							finder.getPageSize()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
					finder.setData(list);
					return finder;
				}
			}
		});
		
		return pageFinder;
		
//		Criteria criteria = buildPropertyFilterCriteria(critMap);
//		
//		Integer totalRows = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
//		criteria.setProjection(null);
//		if (totalRows.intValue() < 1) {
//			return new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
//		} else {
//			PageFinder<T> finder = new PageFinder<T>(pageNo, pageSize, totalRows.intValue());
//			List<T> list = criteria.setFirstResult(finder.getStartOfPage()).setMaxResults(
//					finder.getPageSize()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//			finder.setData(list);
//			return finder;
//		}
	}
	

	/**
	 * 按HQL方式进行分页查询
	 * 
	 * @param toPage
	 *            跳转页号
	 * @param pageSize
	 *            每页数量
	 * @param hql
	 *            查询语句
	 * @param values
	 *            参数
	 * @return
	 */
	public PageFinder<T> pagedByHQL(String hql, int toPage, int pageSize, Object... values) {
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List<T> countlist = getHibernateTemplate().find(countQueryString, values);
		if(countlist.size()==0){
			return null;
		}
		Long totalCount = (Long) countlist.get(0);
		if (totalCount.intValue() < 1) {
			return new PageFinder<T>(toPage, pageSize, totalCount.intValue());
		} else {
			PageFinder<T> finder = new PageFinder<T>(toPage, pageSize, totalCount.intValue());
			Query query = createQuery(hql, values);
			List<T> list = query.setFirstResult(finder.getStartOfPage()).setMaxResults(finder.getPageSize())
					.list();
			finder.setData(list);
			return finder;
		}

	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	private Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName());
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	private String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, entityClass.getSimpleName() + " has no identifier property define.");
		return idName;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况用于pagedQuery.
	 * 
	 * @param hql
	 * @return
	 */
	private String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @param hql
	 * @return
	 */
	private  String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	private Criteria buildPropertyFilterCriteria(Criteria criteria,CritMap critMap) {

		try {
			
			Map<String, Object> propertyMap = null;
			
			//带别名级联
			propertyMap = critMap.getFieldMap(MatchType.ALIASFECH.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					//key 为别名 value 为属性名
					String value = propertyMap.get(key).toString();
					criteria.createAlias(value, key);
					criteria.setFetchMode(key, FetchMode.JOIN);
				}
			}
			
			//级联
			propertyMap = critMap.getFieldMap(MatchType.FECH.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					//key 为别名 value 为属性名
					String value = propertyMap.get(key).toString();
					criteria.setFetchMode(value, FetchMode.JOIN);
				}
			}
			
			//判断是NULL
			propertyMap = critMap.getFieldMap(MatchType.IS_NULL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.isNull(value.toString()));
				}
			}
			
			//判断是not NULL
			propertyMap = critMap.getFieldMap(MatchType.IS_NONULL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.isNotNull(value.toString()));
				}
			}
			
			//相等
			propertyMap = critMap.getFieldMap(MatchType.EQUAL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.eq(key, value));
				}
			}
			
			//不相等
			propertyMap = critMap.getFieldMap(MatchType.NOT_EQUAL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.ne(key, value));
				}
			}
			
			//左模糊查询
			propertyMap = critMap.getFieldMap(MatchType.L_LIKE.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.like(key,value+"%"));
				}
			}
			
			//右模糊查询
			propertyMap = critMap.getFieldMap(MatchType.R_LIKE.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.like(key,"%"+value));
				}
			}
			
			//模糊查询
			propertyMap = critMap.getFieldMap(MatchType.LIKE.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.like(key,"%"+value+"%"));
				}
			}
			
			//大于
			propertyMap = critMap.getFieldMap(MatchType.GREATER.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.gt(key,value));
				}
			}
			
			//大于等于
			propertyMap = critMap.getFieldMap(MatchType.GREATER_EQUAL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.ge(key,value));
				}
			}
			
			//小于
			propertyMap = critMap.getFieldMap(MatchType.LESS.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.lt(key,value));
				}
			}
			
			//小于等于
			propertyMap = critMap.getFieldMap(MatchType.LESS_EQUAL.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object value = propertyMap.get(key);
					criteria.add(Restrictions.le(key,value));
				}
			}
			//IN
			//增加IN类型查询，用于同一属性多值查询　by dsy 20110421_1639
			propertyMap = critMap.getFieldMap(MatchType.IN.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					Object[] values = (Object[]) propertyMap.get(key);
					criteria.add(Restrictions.in(key, values));
				}
			}
			
			//升序
			propertyMap = critMap.getFieldMap(MatchType.ORDER_ASC.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					criteria.addOrder(Order.asc(key));
				}
			}
			
			//降序
			propertyMap = critMap.getFieldMap(MatchType.ORDER_DESC.name());
			if(propertyMap != null){
				for (String key : propertyMap.keySet()) {
					criteria.addOrder(Order.desc(key));
				}
			}
			
			
			//获取指定数据数
			int maxsize = critMap.getMaxSize();
			if(maxsize > 0){
				criteria.setMaxResults(maxsize);
			}
			
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		//System.out.println(this.getCriteriaSql(criteria));
		return criteria;
		
	}

	/*public static String getCriteriaSql(Criteria criteria) {  
	    CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;//转型  
	    SessionImplementor session = criteriaImpl.getSession();//获取SESSION  
	    SessionFactoryImplementor factory = session.getFactory();//获取FACTORY  
	    CriteriaQueryTranslator translator = new CriteriaQueryTranslator(factory, criteriaImpl, criteriaImpl  
	        .getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);  
	    String[] implementors = factory.getImplementors(criteriaImpl.getEntityOrClassName());  
	    CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable) factory  
	        .getEntityPersister(implementors[0]), translator, factory, criteriaImpl, criteriaImpl  
	        .getEntityOrClassName(), session.getEnabledFilters());  
	    return walker.getSQLString();  
	}  */
	
	/***
	 * 获得总记录条数
	 */
	public int getRowCount(Criteria criteria){
		Integer totalRows = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return totalRows;
	}
	
	
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	
	public void evict(String collection, Serializable id) {
		getSessionFactory().evictCollection(collection, id);
	}

	
	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	public Session getHibernateSession() {
		return getSession();
	}
	
	
	public void releaseHibernateSession(Session session){
		releaseSession(session);
	}
	
	public HibernateHelper getHibernateHelper(){
		HibernateHelper hibernateHelper = new HibernateHelper(getSessionFactory());
		return hibernateHelper;
	}

//	/**
//	 * 按游离hibernate标准查询器进行分页查询
//	 * @deprecated
//	 * @param pagination
//	 * @return
//	 */
//	public PageFinder<T> pagedByDetachedCriteria(DetachedCriteria detachedCriteria, int pageNo, int pageSize) {
//		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
//		return pagedByCriteria(criteria, pageNo, pageSize);
//	}
	
//	/**
//	 * 分页查询
//	 *  @deprecated
//	 * @param criteria
//	 * @param offset
//	 * @param pageSize
//	 * @return
//	 */
//	public List<T> findByPage(Criteria criteria, int offset, int pageSize) {
//		return criteria.setFirstResult(offset).setMaxResults(pageSize).setResultTransformer(
//				Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
	
//	/**
//	 * 分页查询
//	 * 
//	 */
//	public List<T> findPageByCritMap(final CritMap critMap,final int offset,final int pageSize) {
//		
//		List<T> list = null;
//		
//		list = (List<T>)super.getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//				Criteria criteria = session.createCriteria(entityClass);
//				
//				buildPropertyFilterCriteria(criteria,critMap);
//				
//				return criteria.setMaxResults(pageSize).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).list();
//			}
//		});
//		return list;
//		
//		
////		Criteria criteria = buildPropertyFilterCriteria(critMap);
////		return criteria.setFirstResult(offset).setMaxResults(pageSize).setResultTransformer(
////				Criteria.DISTINCT_ROOT_ENTITY).list();
//	}


//	/**
//	 * 使用搜索引擎进行分页查询
//	 * 
//	 * @param criteria
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 */
//	public PageFinder<T> pagedBySearcher(org.apache.lucene.search.Query luceneQuery, int pageNo, int pageSize) {
//		FullTextSession s = getFullTextSession();
//		FullTextQuery query = s.createFullTextQuery(luceneQuery, entityClass);
//		int totalRows = query.getResultSize();
//		if (totalRows < 1) {
//			return new PageFinder(pageNo, pageSize, totalRows);
//		} else {
//			PageFinder finder = new PageFinder(pageNo, pageSize, totalRows);
//			query.setMaxResults(pageSize);
//			query.setFirstResult(finder.getStartOfPage());
//			List<T> list = query.list();
//			finder.setData(list);
//			return finder;
//		}
//	}

//	/**
//	 * 分页实体,按分页对象类型调用相应的分页实现
//	 * 
//	 * @param pagination
//	 * @return
//	 */
//	public PageFinder<T> pagedEntity(final int toPage,final int pageSize) {
//		
//		PageFinder<T> pageFinder;
//		
//		pageFinder = (PageFinder<T>)super.getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//				
//				Criteria criteria = session.createCriteria(entityClass);
//				return pagedByCriteria(criteria, toPage, pageSize);
//			}
//		});
//		return pageFinder;
//		
////		Criteria criteria = this.getSession().createCriteria(entityClass);
////		return pagedByCriteria(criteria, toPage, pageSize);
//	}
	
//	/**
//	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
//	 * @deprecated
//	 * @param pageNo
//	 *            页号,每页条数
//	 * @return 含分页记录数和当前页数据的Page对象.
//	 */
//	public PageFinder<T> pagedFinder(int pageNo, int pageSize, Criterion... criterions) {
//		
//		Criteria criteria = createCriteria(criterions);
//		return pagedByCriteria(criteria, pageNo, pageSize);
//	}

//	/**
//	 * 分页查询函数，根据entityClass和查询条件参数排序参数创建默认<code>Criteria</code>.
//	 * @deprecated
//	 * @param pageNo
//	 *            页号,每页条数
//	 * @return 含分页记录数和当前页数据的Page对象.
//	 */
//	public PageFinder<T> pagedQuery(int pageNo, int pageSize, String orderBy, boolean isAsc,
//			Criterion... criterions) {
//		
//		Criteria criteria = createCriteria(orderBy, isAsc, criterions);
//		return pagedByCriteria(criteria, pageNo, pageSize);
//	}
	
//	/**
//	 * 分页查询
//	 * @param pageNo
//	 * @param pageSize
//	 * @param orderBy
//	 * @param isAsc
//	 * @param critMap
//	 * @return
//	 */
//	public PageFinder<T> pagedQueryByCritMap(int pageNo, int pageSize, String orderBy, boolean isAsc,
//			CritMap critMap) {
//		return pagedByCritMap(critMap, pageNo, pageSize) ;
//	}
	
//	/**
//	 * 获得全文搜索引擎的查询会话
//	 * 
//	 * @return
//	 */
//	protected FullTextSession getFullTextSession() {
//		return Search.getFullTextSession(getSession());
//	}
	
//	public void setCacheQueries(boolean isCache) {
//	getHibernateTemplate().setCacheQueries(isCache);
//}

///**
// * 根据外置命名查询
// * 
// * @param queryName
// * @param limit
// *            记录查询条数
// * @param values
// * @return
// */
//public List<T> findByNameQuery(int limit, String queryName, Object... values) {
//	return findByNameQuery(limit, true, queryName, values);
//}

//private List<T> findByNameQuery(int limit, boolean isCache, String queryName, Object... values) {
//	Query queryObject = getSession().getNamedQuery(queryName).setMaxResults(limit).setCacheable(isCache);
//	if (values != null) {
//		for (int i = 0; i < values.length; i++) {
//			queryObject.setParameter(i, values[i]);
//		}
//	}
//	return queryObject.list();
//}

///**
// * 判断对象某些属性的值在数据库中唯一
// * 
// * @param uniquePropertyNames
// *            在POJO里不能重复的属性列表,以逗号分如 : username,loginid,password"
// * @see HibernateGenericDao#isUnique(Class,Object,String)
// */
//public boolean isUnique(final Object entity,final String uniquePropertyNames) {
//	Assert.hasText(uniquePropertyNames);
//	
//	boolean isUnique;
//	
//	isUnique = (Boolean)super.getHibernateTemplate().execute(new HibernateCallback() {
//		public Object doInHibernate(Session session) {
//			
//			Criteria criteria = session.createCriteria(entityClass);
//			criteria.setProjection(Projections.rowCount());
//			String[] nameList = uniquePropertyNames.split(",");
//			
//			try {
//				// 循环加入唯一属性名称
//				for (String name : nameList) {
//					criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
//				}
//				// 以下代码为了如果是update的情况排除entity自身.
//				String idName = getIdName();
//				// 取得entity的主键值
//				Serializable id = getId(entity);
//
//				// 如果id!=null,说明对象已存�?该操作为update,加入排除自身的判�?
//				if (id != null)
//					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
//			} catch (Exception e) {
//				org.springframework.util.ReflectionUtils.handleReflectionException(e);
//			}
//			return (Integer) criteria.uniqueResult() == 0;
//			
//		}
//	});
//	
//	return isUnique;
////	
////	Criteria criteria = createCriteria().setProjection(Projections.rowCount());
////	String[] nameList = uniquePropertyNames.split(",");
////	try {
////		// 循环加入唯一属性名称
////		for (String name : nameList) {
////			criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
////		}
////		// 以下代码为了如果是update的情况排除entity自身.
////		String idName = getIdName();
////		// 取得entity的主键值
////		Serializable id = getId(entity);
////
////		// 如果id!=null,说明对象已存�?该操作为update,加入排除自身的判�?
////		if (id != null)
////			criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
////	} catch (Exception e) {
////		org.springframework.util.ReflectionUtils.handleReflectionException(e);
////	}
////	return (Integer) criteria.uniqueResult() == 0;
//}

///**
// * 取得Entity的Criteria对象，带排序字段与升降序字段.
// * 
// * @param orderBy
// * @param isAsc
// * @param criterions
// * @return
// */
//public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
//	Assert.hasText(orderBy);
//	
//	Criteria criteria = createCriteria(criterions);
//	if (isAsc)
//		criteria.addOrder(Order.asc(orderBy));
//	else
//		criteria.addOrder(Order.desc(orderBy));
//
//	return criteria;
//}


///**
// * 根据属性名和属性值查询对象带排序参数
// * 
// * @return 符合条件的对象集合
// */
//public List<T> findBy(final String propertyName,final  Object value,final  String orderBy,final boolean isAsc) {
//	Assert.hasText(propertyName);
//	Assert.hasText(orderBy);
//	
//	List<T>  list = null;
//	
//	list = (List<T> )super.getHibernateTemplate().execute(new HibernateCallback() {
//		public Object doInHibernate(Session session) {
//			Criteria criteria = session.createCriteria(entityClass);
//			
//			criteria.add(Restrictions.eq(propertyName, value));
//			
//			if (isAsc)
//				criteria.addOrder(Order.asc(orderBy));
//			else
//				criteria.addOrder(Order.desc(orderBy));
//			
//			return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//		}
//	});
//	
//	return list;
//	
////	return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).setResultTransformer(
////			Criteria.DISTINCT_ROOT_ENTITY).list();
//}

///**
// * 根据属性名和属性值查询对象带排序参数
// * @param propertyName
// * @param value
// * @param orderBy
// * @param isAsc
// * @param limit
// * @return
// */
//public List<T> findBy(final String propertyName,final Object value,final String orderBy,final boolean isAsc,final int limit) {
//	Assert.hasText(propertyName);
//	Assert.hasText(orderBy);
//	
//	List<T>  list = null;
//	
//	list = (List<T> )super.getHibernateTemplate().execute(new HibernateCallback() {
//		public Object doInHibernate(Session session) {
//			Criteria criteria = session.createCriteria(entityClass);
//			
//			criteria.add( Restrictions.eq(propertyName, value));
//			
//			if (isAsc)
//				criteria.addOrder(Order.asc(orderBy));
//			else
//				criteria.addOrder(Order.desc(orderBy));
//			
//			return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setMaxResults(limit).list();
//		}
//	});
//	
//	return list;
//	
////	return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).setResultTransformer(
////			Criteria.DISTINCT_ROOT_ENTITY).setMaxResults(limit).list();
//}
}
