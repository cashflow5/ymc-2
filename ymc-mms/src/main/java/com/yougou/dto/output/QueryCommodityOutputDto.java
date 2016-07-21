package com.yougou.dto.output;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 杨梦清
 * 
 */
public class QueryCommodityOutputDto extends PageableOutputDto {

	private static final long serialVersionUID = -6329297313801514092L;

	/** 页数据 **/
	private List<Item> items = Collections.emptyList();

	
	public QueryCommodityOutputDto() {
		super();
	}

	public QueryCommodityOutputDto(int page_index, int page_size, int total_count) {
		super(page_index, page_size, total_count);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static class Item {
		private String brand_name;
		private String cat_name;
		private String commodity_name;
		private String commodity_no;
		private Integer commodity_status;
		private String commodity_status_name;
		private Date create_date;
		private String id;
		private Double public_price;
		private Double sale_price;
		private String spec_name;
		private String stock;
		private String style_no;
		private String supplier_code;
		private Date update_date;

		public String getBrand_name() {
			return brand_name;
		}

		public void setBrand_name(String brand_name) {
			this.brand_name = brand_name;
		}

		public String getCat_name() {
			return cat_name;
		}

		public void setCat_name(String cat_name) {
			this.cat_name = cat_name;
		}

		public String getCommodity_name() {
			return commodity_name;
		}

		public void setCommodity_name(String commodity_name) {
			this.commodity_name = commodity_name;
		}

		public String getCommodity_no() {
			return commodity_no;
		}

		public void setCommodity_no(String commodity_no) {
			this.commodity_no = commodity_no;
		}

		public Integer getCommodity_status() {
			return commodity_status;
		}

		public void setCommodity_status(Integer commodity_status) {
			this.commodity_status = commodity_status;
		}
		
		public String getCommodity_status_name() {
			return commodity_status_name;
		}

		public void setCommodity_status_name(String commodity_status_name) {
			this.commodity_status_name = commodity_status_name;
		}

		public Date getCreate_date() {
			return create_date;
		}

		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Double getPublic_price() {
			return public_price;
		}

		public void setPublic_price(Double public_price) {
			this.public_price = public_price;
		}

		public Double getSale_price() {
			return sale_price;
		}

		public void setSale_price(Double sale_price) {
			this.sale_price = sale_price;
		}

		public String getSpec_name() {
			return spec_name;
		}

		public void setSpec_name(String spec_name) {
			this.spec_name = spec_name;
		}

		public String getStock() {
			return stock;
		}

		public void setStock(String stock) {
			this.stock = stock;
		}

		public String getStyle_no() {
			return style_no;
		}

		public void setStyle_no(String style_no) {
			this.style_no = style_no;
		}

		public String getSupplier_code() {
			return supplier_code;
		}

		public void setSupplier_code(String supplier_code) {
			this.supplier_code = supplier_code;
		}

		public Date getUpdate_date() {
			return update_date;
		}

		public void setUpdate_date(Date update_date) {
			this.update_date = update_date;
		}
	}
}
