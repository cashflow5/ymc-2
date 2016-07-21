package com.yougou.api.model.pojo;

import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tbl_merchant_api_interceptor_mapper")
@DiscriminatorColumn(name = "ref_type", discriminatorType = DiscriminatorType.STRING)
public abstract class InterceptorMapper implements java.io.Serializable {

	private static final long serialVersionUID = -4417207991617201796L;
	private String id;
	private Integer orderNo;
	
	private ApiInterceptor apiInterceptor;

	public InterceptorMapper() {
	}
	
	public InterceptorMapper(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "order_no")
	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interceptor_id")
	public ApiInterceptor getApiInterceptor() {
		return this.apiInterceptor;
	}

	public void setApiInterceptor(ApiInterceptor apiInterceptor) {
		this.apiInterceptor = apiInterceptor;
	}
	
	@Transient
	public Object getRefer() {
		try {
			Method[] methods = this.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				JoinColumn annotation = methods[i].getAnnotation(JoinColumn.class);
				if (annotation != null && "ref_id".equalsIgnoreCase(annotation.name())) {
					return methods[i].invoke(this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	@Transient
	@SuppressWarnings("unchecked")
	public <T> T getReferAs(Class<T> clazz) {
		return (T) getRefer();
	}

	@Transient
	public String getReferType() {
		return this.getClass().getAnnotation(DiscriminatorValue.class).value();
	}
}
