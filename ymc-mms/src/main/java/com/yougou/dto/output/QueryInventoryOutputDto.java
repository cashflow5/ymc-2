package com.yougou.dto.output;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 查询库存输出DTO
 * 
 * @author 杨梦清
 *
 */
public class QueryInventoryOutputDto extends PageableOutputDto {

	private static final long serialVersionUID = -158596958077873617L;

	private List<Item> items = Collections.emptyList();
	
	public QueryInventoryOutputDto() {
		super();
	}

	public QueryInventoryOutputDto(int page_index, int page_size, int total_count) {
		super(page_index, page_size, total_count);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public static class Item implements Serializable {

		private static final long serialVersionUID = -1966072234216872431L;
		
		/** 商品编码 **/
		private String commodity_no;
		
		/** 供应商款色款码(实际为商家商品编码) **/
		private String supplier_code;
		
		/** 商品名称 **/
		private String commodity_name;

		/** 商品状态 **/
		private Integer commodity_status;
		
		/** 优购货品编码 **/
		private String product_no;
		
		/** 商家货品条码 **/
		private String third_party_code;
		
		/** 总库存 **/
		private Integer total_stock_quantity;
		
		/** 最后修改时间 **/
		private Date modified;
		
		/** 商品默认图片 **/
		private String commodity_image;
		
		/** 商品单品页 **/
		private String commodity_items_page;
		
		/** 市场价 **/
		private BigDecimal sale_price;
		
		/** 优购价 **/
		private BigDecimal public_price;
		
		/** 商品款号 **/
		private String style_no;
		
		public String getCommodity_no() {
			return commodity_no;
		}

		public void setCommodity_no(String commodity_no) {
			this.commodity_no = commodity_no;
		}

		public String getSupplier_code() {
			return supplier_code;
		}

		public void setSupplier_code(String supplier_code) {
			this.supplier_code = supplier_code;
		}

		public String getCommodity_name() {
			return commodity_name;
		}

		public void setCommodity_name(String commodity_name) {
			this.commodity_name = commodity_name;
		}

		public Integer getCommodity_status() {
			return commodity_status;
		}

		public void setCommodity_status(Integer commodity_status) {
			this.commodity_status = commodity_status;
		}
		
		public String getCommodity_status_name() {
			if (commodity_status == null) {
				return null;
			}
			switch (commodity_status) {
			case 1:
				return "停售";
			case 2:
				return "在售";
			case 3:
				return "停用";
			case 4:
				return "待进货";
			case 5:
				return "待售";
			case 6:
				return "预售";
			default:
				return commodity_status.toString();
			}
		}

		public String getProduct_no() {
			return product_no;
		}

		public void setProduct_no(String product_no) {
			this.product_no = product_no;
		}

		public String getThird_party_code() {
			return third_party_code;
		}

		public void setThird_party_code(String third_party_code) {
			this.third_party_code = third_party_code;
		}

		public Integer getTotal_stock_quantity() {
			return total_stock_quantity;
		}

		public void setTotal_stock_quantity(Integer total_stock_quantity) {
			this.total_stock_quantity = total_stock_quantity;
		}

		public Date getModified() {
			return modified;
		}

		public void setModified(Date modified) {
			this.modified = modified;
		}

		public String getCommodity_image() {
			return commodity_image;
		}

		public void setCommodity_image(String commodity_image) {
			this.commodity_image = commodity_image;
		}

		public String getCommodity_items_page() {
			return commodity_items_page;
		}

		public void setCommodity_items_page(String commodity_items_page) {
			this.commodity_items_page = commodity_items_page;
		}

		public BigDecimal getSale_price() {
			return sale_price;
		}

		public void setSale_price(BigDecimal sale_price) {
			this.sale_price = sale_price;
		}

		public BigDecimal getPublic_price() {
			return public_price;
		}

		public void setPublic_price(BigDecimal public_price) {
			this.public_price = public_price;
		}

		public String getStyle_no() {
			return style_no;
		}

		public void setStyle_no(String style_no) {
			this.style_no = style_no;
		}
	}

}
