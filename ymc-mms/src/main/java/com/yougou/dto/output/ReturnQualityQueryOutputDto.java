package com.yougou.dto.output;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ReturnQualityQueryOutputDto extends PageableOutputDto {

	private static final long serialVersionUID = 4093477387725751250L;
	
	/** 页数据 **/
	private List<Item> items = Collections.emptyList();
	
	public ReturnQualityQueryOutputDto() {
		super();
	}

	public ReturnQualityQueryOutputDto(int page_index, int page_size, int total_count) {
		super(page_index, page_size, total_count);
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	@Override
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * <p>退换货质检信息</p>
	 * 
	 * @author huang.tao
	 *
	 */
	public static class Item implements Serializable {
		
		private static final long serialVersionUID = -6836905521368246771L;
		
		/** 退换货质检Id */
		private String return_id;
		
		/** 优购订单号 */
		private String order_sub_no;
		
		/** 外部订单号 */
		private String out_order_id;
		
		/** 发货快递公司 */
		private String logistics_name;
		
		/** 快递单号 */
		private String express_code;
		
		/** 发货仓库 */
		private String warehouse_name;
		
		private String return_logistics_name;
		private String return_express_code;
		private Double express_fee;
		
		private Date qa_date;
		private String qa_person;
		private String qa_remark;
		
		private List<ReturnQADetail> return_details = Collections.emptyList();

		public String getReturn_id() {
			return return_id;
		}

		public void setReturn_id(String return_id) {
			this.return_id = return_id;
		}

		public String getOrder_sub_no() {
			return order_sub_no;
		}

		public void setOrder_sub_no(String order_sub_no) {
			this.order_sub_no = order_sub_no;
		}

		public String getOut_order_id() {
			return out_order_id;
		}

		public void setOut_order_id(String out_order_id) {
			this.out_order_id = out_order_id;
		}

		public String getLogistics_name() {
			return logistics_name;
		}

		public void setLogistics_name(String logistics_name) {
			this.logistics_name = logistics_name;
		}

		public String getExpress_code() {
			return express_code;
		}

		public void setExpress_code(String express_code) {
			this.express_code = express_code;
		}
		
		public String getWarehouse_name() {
			return warehouse_name;
		}

		public void setWarehouse_name(String warehouse_name) {
			this.warehouse_name = warehouse_name;
		}

		public String getReturn_logistics_name() {
			return return_logistics_name;
		}

		public void setReturn_logistics_name(String return_logistics_name) {
			this.return_logistics_name = return_logistics_name;
		}

		public String getReturn_express_code() {
			return return_express_code;
		}

		public void setReturn_express_code(String return_express_code) {
			this.return_express_code = return_express_code;
		}

		public Double getExpress_fee() {
			return express_fee;
		}

		public void setExpress_fee(Double express_fee) {
			this.express_fee = express_fee;
		}
		public Date getQa_date() {
			return qa_date;
		}
		public void setQa_date(Date qa_date) {
			this.qa_date = qa_date;
		}
		public String getQa_person() {
			return qa_person;
		}
		public void setQa_person(String qa_person) {
			this.qa_person = qa_person;
		}
		public String getQa_remark() {
			return qa_remark;
		}
		public void setQa_remark(String qa_remark) {
			this.qa_remark = qa_remark;
		}
		public List<ReturnQADetail> getReturn_details() {
			return return_details;
		}

		public void setReturn_details(List<ReturnQADetail> return_details) {
			this.return_details = return_details;
		}

	}
	
	/**
	 * <p>退换货质检明细</p>
	 * 
	 * @author huang.tao
	 *
	 */
	public static class ReturnQADetail implements Serializable {
		
		private static final long serialVersionUID = -7673053263721901163L;
		
		//==========================申请单=======================================
		
		/** 申请单号 */
		private String apply_no;
		
		/** 申请单状态 */
		private String apply_status;
		
		/** 附件 */
		private String attachment;
		
		/** 申请人 */
		private String applyer;
		
		/** 申请时间 */
		private Date apply_time;
		
		/** 售后类型（REFUND_GOODS-拒收,QUIT_GOODS-退货,TRADE_GOODS-换货,
		 * REPAIR_GOODS-返修,OTHER-其他 ） */
		private String aftersale_type;
		
		/** 售后原因（顾客填写） */
		private String aftersale_reason;
		
		/** 售后说明（客服填写） */
		private String sale_remark;
		
		//===========================销售商品====================================
		
		/** 供应商款色编码 **/
		private String supplier_code;
		/** 货品编码 */
		private String prod_no;
		/** 货品名称 */
		private String prod_name;
		/** 商品单价 */
		private Double sale_price;
		/**货品规格**/
		private String commodity_specification_str;
		
		//===========================质检商品====================================
		
		private String qa_supplier_code;
		private String qa_prod_no;
		private String qa_prod_name;
		private String qa_commodity_specification_str;
		private Integer qa_quantity;
		
		private String question_type;
		private String question_description;
		
		private String storage_type;
		
		private String qa_description;
		
		public String getApply_no() {
			return apply_no;
		}
		public void setApply_no(String apply_no) {
			this.apply_no = apply_no;
		}
		public String getApply_status() {
			return apply_status;
		}
		
		public String getApply_status_name() {
			if ("SALE_APPLY".equals(this.apply_status)) return "未审核";
			else if ("SALE_COMFIRM".equals(this.apply_status)) return "已审核";
			else if ("SALE_REFUSE".equals(this.apply_status)) return "拒绝申请";
			else if ("SALE_NOT_GOODS".equals(this.apply_status)) return "未收到货";
			else if ("SALE_RECEIVE_GOODS".equals(this.apply_status)) return "收到退货";
			else if ("SALE_CALL_BACK".equals(this.apply_status)) return "打回";
			else if ("PART_SALE_QC".equals(this.apply_status)) return "部分质检";
			else if ("SALE_QC".equals(this.apply_status)) return "已质检";
			else if ("SALE_SEND_GOODS".equals(this.apply_status)) return "已发货";
			else if ("SALE_REFUND_APPLY".equals(this.apply_status)) return "退款申请中";
			else if ("SALE_REFUND_COMFIRM".equals(this.apply_status)) return "退款审核通过";
			
			else if ("SALE_REFUND_REFUSE".equals(this.apply_status)) return "退款拒绝";
			else if ("SALE_REFUND_YES".equals(this.apply_status)) return "已退款";
			else if ("SALE_SUPPLY_YES".equals(this.apply_status)) return "已补款";
			else if ("SALE_SUPPLY_FAIL".equals(this.apply_status)) return "补款拒绝";
			
			else if ("SALE_SUCCESS".equals(this.apply_status)) return "已完成";
			else if ("SALE_CANCEL".equals(this.apply_status)) return "作废";
			
			return "";
		}
		
		public void setApply_status(String apply_status) {
			this.apply_status = apply_status;
		}
		public String getAttachment() {
			return attachment;
		}
		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}
		public String getApplyer() {
			return applyer;
		}
		public void setApplyer(String applyer) {
			this.applyer = applyer;
		}
		public Date getApply_time() {
			return apply_time;
		}
		public void setApply_time(Date apply_time) {
			this.apply_time = apply_time;
		}
		public String getAftersale_type() {
			return aftersale_type;
		}
		
		//REFUND_GOODS-拒收,QUIT_GOODS-退货,TRADE_GOODS-换货,REPAIR_GOODS-返修,OTHER-其他 
		public String getAftersale_type_name() {
			if ("REFUND_GOODS".equals(this.aftersale_type)) return "拒收";
			else if ("QUIT_GOODS".equals(this.aftersale_type)) return "退货";
			else if ("TRADE_GOODS".equals(this.aftersale_type)) return "换货";
			else if ("REPAIR_GOODS".equals(this.aftersale_type)) return "返修";
			
			return "其他";
		}
		
		public void setAftersale_type(String aftersale_type) {
			this.aftersale_type = aftersale_type;
		}
		public String getAftersale_reason() {
			return aftersale_reason;
		}
		public void setAftersale_reason(String aftersale_reason) {
			this.aftersale_reason = aftersale_reason;
		}
		
		public String getSale_remark() {
			return sale_remark;
		}
		public void setSale_remark(String sale_remark) {
			this.sale_remark = sale_remark;
		}
		public String getSupplier_code() {
			return supplier_code;
		}
		public void setSupplier_code(String supplier_code) {
			this.supplier_code = supplier_code;
		}
		public String getProd_no() {
			return prod_no;
		}
		public void setProd_no(String prod_no) {
			this.prod_no = prod_no;
		}
		public String getProd_name() {
			return prod_name;
		}
		public void setProd_name(String prod_name) {
			this.prod_name = prod_name;
		}

		public Double getSale_price() {
			return sale_price;
		}
		public void setSale_price(Double sale_price) {
			this.sale_price = sale_price;
		}
		public String getCommodity_specification_str() {
			return commodity_specification_str;
		}
		public void setCommodity_specification_str(String commodity_specification_str) {
			this.commodity_specification_str = commodity_specification_str;
		}
		public String getQa_supplier_code() {
			return qa_supplier_code;
		}
		public void setQa_supplier_code(String qa_supplier_code) {
			this.qa_supplier_code = qa_supplier_code;
		}
		public String getQa_prod_no() {
			return qa_prod_no;
		}
		public void setQa_prod_no(String qa_prod_no) {
			this.qa_prod_no = qa_prod_no;
		}
		public String getQa_prod_name() {
			return qa_prod_name;
		}
		public void setQa_prod_name(String qa_prod_name) {
			this.qa_prod_name = qa_prod_name;
		}
		public String getQa_commodity_specification_str() {
			return qa_commodity_specification_str;
		}
		public void setQa_commodity_specification_str(String qa_commodity_specification_str) {
			this.qa_commodity_specification_str = qa_commodity_specification_str;
		}
		public String getQuestion_type() {
			return question_type;
		}
		
		public String getQuestion_type_name() {
			if ("BAD".equals(this.question_type)) {
				return "质量问题";
			}
			
			return "非质量问题";
		}
		
		public void setQuestion_type(String question_type) {
			this.question_type = question_type;
		}
		public String getQuestion_description() {
			return question_description;
		}
		public void setQuestion_description(String question_description) {
			this.question_description = question_description;
		}
		
		public String getStorage_type() {
			return storage_type;
		}
		public String getStorage_type_name() {
			if ("BAD".equals(this.storage_type)) {
				return "残次品";
			} else if ("MAINTENANCE".equals(this.storage_type)) {
				return "维修品";
			} 
			
			return "正品";
		}
		
		public void setStorage_type(String storage_type) {
			this.storage_type = storage_type;
		}
		public String getQa_description() {
			return qa_description;
		}
		public void setQa_description(String qa_description) {
			this.qa_description = qa_description;
		}
		public Integer getQa_quantity() {
			return qa_quantity;
		}
		public void setQa_quantity(Integer qa_quantity) {
			this.qa_quantity = qa_quantity;
		}
		
	}
}
