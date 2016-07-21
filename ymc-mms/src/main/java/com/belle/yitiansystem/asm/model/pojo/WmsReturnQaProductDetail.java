package com.belle.yitiansystem.asm.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_wms_return_qa_product_detail")
public class WmsReturnQaProductDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@Column(name = "return_qa_product_id")
	private String return_qa_product_id;
	
	@Column(name = "order_sub_no")
	private String order_sub_no;
	
	@Column(name = "apply_no")
	private String apply_no;
	
	@Column(name = "apply_date")
	private Date apply_date;
	
	@Column(name = "product_id")
	private String product_id;
	
	@Column(name = "product_no")
	private String product_no;
	
	@Column(name = "inside_code")
	private String  inside_code;
	
	@Column(name = "third_party_code")
	private String  third_party_code;
	
	@Column(name = "goods_name")
	private String  goods_name;
	
	@Column(name = "quantity")
	private int  quantity;
	
	@Column(name = "consignee")
	private String  consignee;
	
	@Column(name = "consignee_address")
	private String  consignee_address;
	
	@Column(name = "consignee_phone")
	private String  consignee_phone;
	
	@Column(name = "consignee_tel")
	private String  consignee_tel;
	
	@Column(name = "consignee_postcode")
	private String  consignee_postcode;
	
	@Column(name = "question_description")
	private String  question_description;
	
	@Column(name = "question_type")
	private String  question_type;
	
	@Column(name = "question_cause")
	private String  question_cause;
	
	@Column(name = "question_classification")
	private String  question_classification;
	
	@Column(name = "storage_type")
	private String  storage_type;
	
	@Column(name = "qa_description")
	private String  qa_description;
	
	@Column(name = "member")
	private String  member;
	
	@Column(name = "qa_apply_date")
	private Date  qa_apply_date;
	
	@Column(name = "qa_product_id")
	private String  qa_product_id;
	
	@Column(name = "qa_product_no")
	private String  qa_product_no;
	
	@Column(name = "qa_inside_code")
	private String  qa_inside_code;
	
	@Column(name = "qa_third_party_code")
	private String  qa_third_party_code;
	
	@Column(name = "qa_goods_name")
	private String  qa_goods_name;
	
	@Column(name = "qa_quantity")
	private int  qa_quantity;
	
	@Column(name = "apply_detail_id")
	private String  apply_detail_id;
	
	@Column(name = "qa_supplier_code")
	private String  qa_supplier_code;
	
	@Column(name = "defective_type")
	private int  defective_type;
	
	@Column(name = "out_inventory_type")
	private String  out_inventory_type;
	
	@Column(name = "in_inventory_type")
	private int  in_inventory_type;
	
	@Column(name = "is_passed")
	private int  is_passed;
	
	@Column(name = "is_second_check")
	private int  is_second_check;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReturn_qa_product_id() {
		return return_qa_product_id;
	}

	public void setReturn_qa_product_id(String return_qa_product_id) {
		this.return_qa_product_id = return_qa_product_id;
	}

	public String getOrder_sub_no() {
		return order_sub_no;
	}

	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}

	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_no() {
		return product_no;
	}

	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getInside_code() {
		return inside_code;
	}

	public void setInside_code(String inside_code) {
		this.inside_code = inside_code;
	}

	public String getThird_party_code() {
		return third_party_code;
	}

	public void setThird_party_code(String third_party_code) {
		this.third_party_code = third_party_code;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsignee_address() {
		return consignee_address;
	}

	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
	}

	public String getConsignee_phone() {
		return consignee_phone;
	}

	public void setConsignee_phone(String consignee_phone) {
		this.consignee_phone = consignee_phone;
	}

	public String getConsignee_tel() {
		return consignee_tel;
	}

	public void setConsignee_tel(String consignee_tel) {
		this.consignee_tel = consignee_tel;
	}

	public String getConsignee_postcode() {
		return consignee_postcode;
	}

	public void setConsignee_postcode(String consignee_postcode) {
		this.consignee_postcode = consignee_postcode;
	}

	public String getQuestion_description() {
		return question_description;
	}

	public void setQuestion_description(String question_description) {
		this.question_description = question_description;
	}

	public String getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}

	public String getQuestion_cause() {
		return question_cause;
	}

	public void setQuestion_cause(String question_cause) {
		this.question_cause = question_cause;
	}

	public String getQuestion_classification() {
		return question_classification;
	}

	public void setQuestion_classification(String question_classification) {
		this.question_classification = question_classification;
	}

	public String getStorage_type() {
		return storage_type;
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

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public Date getQa_apply_date() {
		return qa_apply_date;
	}

	public void setQa_apply_date(Date qa_apply_date) {
		this.qa_apply_date = qa_apply_date;
	}

	public String getQa_product_id() {
		return qa_product_id;
	}

	public void setQa_product_id(String qa_product_id) {
		this.qa_product_id = qa_product_id;
	}

	public String getQa_product_no() {
		return qa_product_no;
	}

	public void setQa_product_no(String qa_product_no) {
		this.qa_product_no = qa_product_no;
	}

	public String getQa_inside_code() {
		return qa_inside_code;
	}

	public void setQa_inside_code(String qa_inside_code) {
		this.qa_inside_code = qa_inside_code;
	}

	public String getQa_third_party_code() {
		return qa_third_party_code;
	}

	public void setQa_third_party_code(String qa_third_party_code) {
		this.qa_third_party_code = qa_third_party_code;
	}

	public String getQa_goods_name() {
		return qa_goods_name;
	}

	public void setQa_goods_name(String qa_goods_name) {
		this.qa_goods_name = qa_goods_name;
	}

	public int getQa_quantity() {
		return qa_quantity;
	}

	public void setQa_quantity(int qa_quantity) {
		this.qa_quantity = qa_quantity;
	}

	public String getApply_detail_id() {
		return apply_detail_id;
	}

	public void setApply_detail_id(String apply_detail_id) {
		this.apply_detail_id = apply_detail_id;
	}

	public String getQa_supplier_code() {
		return qa_supplier_code;
	}

	public void setQa_supplier_code(String qa_supplier_code) {
		this.qa_supplier_code = qa_supplier_code;
	}

	public int getDefective_type() {
		return defective_type;
	}

	public void setDefective_type(int defective_type) {
		this.defective_type = defective_type;
	}

	public String getOut_inventory_type() {
		return out_inventory_type;
	}

	public void setOut_inventory_type(String out_inventory_type) {
		this.out_inventory_type = out_inventory_type;
	}

	public int getIn_inventory_type() {
		return in_inventory_type;
	}

	public void setIn_inventory_type(int in_inventory_type) {
		this.in_inventory_type = in_inventory_type;
	}

	public int getIs_passed() {
		return is_passed;
	}

	public void setIs_passed(int is_passed) {
		this.is_passed = is_passed;
	}

	public int getIs_second_check() {
		return is_second_check;
	}

	public void setIs_second_check(int is_second_check) {
		this.is_second_check = is_second_check;
	}
}
