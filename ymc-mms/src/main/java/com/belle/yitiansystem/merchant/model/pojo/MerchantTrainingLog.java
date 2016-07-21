
package com.belle.yitiansystem.merchant.model.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MerchantTrainingLog {
    private String id;

    private String trainingId;

    private String operation;

    private String operationDesc;

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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc == null ? null : operationDesc.trim();
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