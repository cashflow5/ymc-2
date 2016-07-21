/**
 * 
 */
package com.belle.infrastructure.orm.basedao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 * 
 */
public class Query {

	/**
	 * 自动排序属性
	 */
	private String order;

	/**
	 * 排序方式
	 */
	private boolean isAsc;

	/**
	 * 跳转
	 */
	private int page=1;

	/**
	 * 每页显示记录数
	 */
	private int pageSize = 20;
	
	private boolean showCount = false;

	public Query() {

	}

	public Query(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 按字段排序，如果排序字段为空，则按默认方式排序
	 * 
	 * @param criteria
	 * @param defaultOrderName
	 */
	public void makeOrder(DetachedCriteria criteria, String defaultOrderName, boolean isDefaultAsc) {
		if (StringUtils.isNotEmpty(order)) {
			if (isAsc) {
				criteria.addOrder(Order.asc(order));
			} else {
				criteria.addOrder(Order.desc(order));
			}
		} else {
			if (isDefaultAsc) {
				criteria.addOrder(Order.asc(defaultOrderName));
			} else {
				criteria.addOrder(Order.desc(defaultOrderName));
			}
		}
	}

	/**
	 * 按字段排序，如果排序字段为空，则按默认方式排序
	 * 
	 * @param criteria
	 * @param defaultOrderName
	 */
	public void makeOrder(Criteria criteria, String defaultOrderName, boolean isDefaultAsc) {
		if (StringUtils.isNotEmpty(order)) {
			if (isAsc) {
				criteria.addOrder(Order.asc(order));
			} else {
				criteria.addOrder(Order.desc(order));
			}
		} else {
			if (isDefaultAsc) {
				criteria.addOrder(Order.asc(defaultOrderName));
			} else {
				criteria.addOrder(Order.desc(defaultOrderName));
			}
		}
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowCount() {
		return showCount;
	}

	public void setShowCount(boolean showCount) {
		this.showCount = showCount;
	}

	


}
