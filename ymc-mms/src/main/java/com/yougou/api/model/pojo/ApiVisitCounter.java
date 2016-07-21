package com.yougou.api.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_visit_counter")
public class ApiVisitCounter {

	private String id;
	private String visitor;// 访问者(商家)
	private String visitorIp;// 访问者IP
	private Integer visitCount;// 访问计算
	private Long visitTimestamp;// 访问时间戳

	public ApiVisitCounter() {
		super();
	}
	
	public ApiVisitCounter(String id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "visitor", nullable = false, length = 32)
	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	@Column(name = "visitor_ip", nullable = true, length = 32)
	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	@Column(name = "visit_count", nullable = false)
	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	@Column(name = "visit_timestamp", nullable = false)
	public Long getVisitTimestamp() {
		return visitTimestamp;
	}

	public void setVisitTimestamp(Long visitTimestamp) {
		this.visitTimestamp = visitTimestamp;
	}
}
