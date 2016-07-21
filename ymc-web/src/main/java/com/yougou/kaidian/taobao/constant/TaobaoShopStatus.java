package com.yougou.kaidian.taobao.constant;

/**
 * 商品状态
 * @author li.m1
 *
 */
public enum TaobaoShopStatus {
	NEW("待提交", 0), AUDIT_SUBMIT("待审核", 1), AUDITED("已审核", 2), AUTHORIZED("已授权", 3), DISABLE("禁用", -1);
	
    private String statusName;  
    private int index;  
    private TaobaoShopStatus(String statusName, int index) {  
        this.statusName = statusName;  
        this.index = index;  
    }
    public String getStatusName() {  
        return statusName;  
    }  
    public void setStatusName(String statusName) {  
        this.statusName = statusName;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }
    public static String getStatusName(int index) {  
        for (TaobaoShopStatus c : TaobaoShopStatus.values()) {  
            if (c.getIndex() == index) {  
                return c.statusName;  
            }  
        }  
        return "异常状态";  
    }
}
