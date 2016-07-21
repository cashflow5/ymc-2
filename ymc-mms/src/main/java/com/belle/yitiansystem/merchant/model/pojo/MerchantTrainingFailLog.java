/*
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.belle.yitiansystem.merchant.model.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MerchantTrainingFailLog {
    private String id;

    private String trainingId;

    private String filePath;

    private String desc;

    private String operator;

    private String operated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId == null ? null : trainingId.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperated() {
        return operated;
    }

    public void setOperated(String operated) {
        this.operated = operated == null ? null : operated.trim();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}