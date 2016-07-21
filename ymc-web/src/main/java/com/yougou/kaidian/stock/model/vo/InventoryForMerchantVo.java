package com.yougou.kaidian.stock.model.vo;

import java.util.Date;

public class InventoryForMerchantVo {

    private String id;
    /*
     * 货品编号
     */
    private String productNo;
    /*
     * 库存数量
     */
    private Integer safeStockQuantity;
    /*
     * 商家编号
     */
    private String merchantCode;
    /*
     * 仓库编号
     */
    private String warehouseCode;
    /*
     * 创建时间
     */
    private Date createDate;
    /*
     * 修改日期
     */
    private Date modityDate;

    public InventoryForMerchantVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getSafeStockQuantity() {
        return safeStockQuantity;
    }

    public void setSafeStockQuantity(Integer safeStockQuantity) {
        this.safeStockQuantity = safeStockQuantity;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModityDate() {
        return modityDate;
    }

    public void setModityDate(Date modityDate) {
        this.modityDate = modityDate;
    }
    
}
