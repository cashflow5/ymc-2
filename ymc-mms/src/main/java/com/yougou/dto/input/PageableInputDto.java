package com.yougou.dto.input;

/**
 * 
 * @author 杨梦清
 * 
 */
public class PageableInputDto extends InputDto {

	private static final long serialVersionUID = -5743330058263137613L;

	/** 页码 **/
	private Integer page_index;

	/** 页大小 **/
	private Integer page_size;

	public Integer getPage_index() {
		return page_index;
	}

	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
}
