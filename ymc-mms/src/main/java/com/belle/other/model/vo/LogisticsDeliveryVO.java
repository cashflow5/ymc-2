package com.belle.other.model.vo;


/**
 * 类描述：TODO 物流配送实体ＶＯ 
 * @author 段思远
 * @date May 6, 2011 12:01:54 PM
 * @email duan.sy@belle.com.cn
 */
public class LogisticsDeliveryVO {

//  快件单号;
    private String expressCode;

//  始发网点;
    private String beginPoint;

//  目的地;
    private String target;

//   件数;
    private String pakages;

//  发件时间;
    private String startDate;
    
    private LogisticsStepVO[] step;

    
    public String getExpressCode() {
        return expressCode;
    }

    
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    
    public String getBeginPoint() {
        return beginPoint;
    }

    
    public void setBeginPoint(String beginPoint) {
        this.beginPoint = beginPoint;
    }

    
    public String getTarget() {
        return target;
    }

    
    public void setTarget(String target) {
        this.target = target;
    }

    
    public String getPakages() {
        return pakages;
    }

    
    public void setPakages(String pakages) {
        this.pakages = pakages;
    }

    
    public String getStartDate() {
        return startDate;
    }

    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    
    public LogisticsStepVO[] getStep() {
        return step;
    }

    
    public void setStep(LogisticsStepVO[] step) {
        this.step = step;
    }
}
