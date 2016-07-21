package com.belle.other.model.vo;

/**
 * 操作返回类
 * 
 * @author CYJ
 * 
 */
public class ResultMsg {

    // 成功标识
    private boolean success;
    // 返回代码
    private String reCode;
    // 结果ID
    private String idCode;
    // 返回信息
    private String msg;
    // 返回实体
    private Object reObj;

    public ResultMsg() {
    }

    public ResultMsg(boolean success, String reCode, String idCode, String msg, Object reObj) {
        this.success = success;
        this.reCode = reCode;
        this.idCode = idCode;
        this.msg = msg;
        this.reObj = reObj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getReObj() {
        return reObj;
    }

    public void setReObj(Object reObj) {
        this.reObj = reObj;
    }

}
