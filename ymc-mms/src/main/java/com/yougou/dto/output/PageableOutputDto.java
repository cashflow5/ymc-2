package com.yougou.dto.output;

import java.util.List;


/**
 * 
 * @author 杨梦清
 * 
 */
public abstract class PageableOutputDto extends OutputDto {

	private static final long serialVersionUID = 6479981698117256176L;

	/** 总记录数 **/
	private int total_count;

	/** 页码 **/
	private int page_index;

	/** 页大小 **/
	private int page_size;

	public PageableOutputDto() {
	}

	public PageableOutputDto(int page_index, int page_size, int total_count) {
		this.page_index = page_index;
		this.page_size = page_size;
		this.total_count = total_count;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	
	@SuppressWarnings("rawtypes")
	abstract public List getItems();
}
