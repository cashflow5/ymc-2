package com.yougou.kaidian.commodity.component;

import java.util.ArrayList;
import java.util.List;
/**
 * 商品状态
 * @author li.m1
 *
 */
public enum CommodityStatus {
	NEW("待提交", 11), AUDIT_SUBMIT("待审核", 12), AUDIT_REJECT("审核拒绝", 13), SHELVE("上架", 2), SHELVE_OFF("下架", 1), STOCK_READY("待上架(无库存)", 4),SALE_READY("待上架", 5);
	
    private String statusName;  
    private int index;  
    private CommodityStatus(String statusName, int index) {  
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
        for (CommodityStatus c : CommodityStatus.values()) {  
            if (c.getIndex() == index) {  
                return c.statusName;  
            }  
        }
        return "异常状态";  
    }
    public static int getStatusCode(String statusName) {  
        for (CommodityStatus c : CommodityStatus.values()) {  
            if (c.getStatusName().equals( statusName)) {  
                return c.index;  
            }  
        }  
        return -1;  
    }
    public static List<CommodityStatus> getWaitSaleStatus() {  
    	List<CommodityStatus> WaitSaleStatusList=new ArrayList<CommodityStatus>();
        for (CommodityStatus c : CommodityStatus.values()) {  
            if (c.getIndex() != 2) {  
            	WaitSaleStatusList.add(c);
            }  
        }  
        return WaitSaleStatusList;  
    }
    public static List<CommodityStatus> getWaitSaleStatusList() {  
    	List<CommodityStatus> WaitSaleStatusList=new ArrayList<CommodityStatus>();
        for (CommodityStatus c : CommodityStatus.values()) {  
            	WaitSaleStatusList.add(c);
        }  
        return WaitSaleStatusList;  
    }
}
