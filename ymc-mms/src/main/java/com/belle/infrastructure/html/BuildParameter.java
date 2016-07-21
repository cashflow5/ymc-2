package com.belle.infrastructure.html;

import java.util.Date;

/**
 * 
 * @descript：构建静态页面参数
 * @author  ：方勇
 * @email   ：fangyong@broadengate.com
 * @time    ： 2011-6-11 下午02:29:16
 */
public class BuildParameter implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 更新类型
    private String            buildType;
    // 更新内容
    private String            buildContent;
    // 每次更新数
    private int               maxResults       = 50;
    // 更新文章起始结果数
    private Integer           firstResult      = 0;
    // 开始日期
    private Date              beginDate;
    // 结束日期
    private Date              endDate;

    public BuildParameter(String buildType, String buildContent, int maxResults, Integer firstResult, Date beginDate,
                          Date endDate) {
        super();
        this.buildType = buildType;
        this.buildContent = buildContent;
        this.maxResults = maxResults;
        this.firstResult = firstResult;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getBuildContent() {
        return buildContent;
    }

    public void setBuildContent(String buildContent) {
        this.buildContent = buildContent;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
