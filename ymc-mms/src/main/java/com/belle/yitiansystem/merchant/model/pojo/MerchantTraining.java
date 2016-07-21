
package com.belle.yitiansystem.merchant.model.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MerchantTraining {
    private String id;

    private String title;

    private Short deleteFlag;

    private Short isPublish;

    private String createTime;

    private String updateTime;

    private String catName;

    private Short fileType;

    private Integer personNum;

    private String creator;

    private String operator;

    private Short onTop;

    private String ontopTime;

    private String description;

    private String versionId;

    private String picUrl;

    private String fileName;

    private String fileUrl;

    private String previewUrl;

    private String totalClick;
    
    private String createTimeStart;
    
    private String createTimeEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Short getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Short isPublish) {
        this.isPublish = isPublish;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public Short getFileType() {
        return fileType;
    }

    public void setFileType(Short fileType) {
        this.fileType = fileType;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Short getOnTop() {
        return onTop;
    }

    public void setOnTop(Short onTop) {
        this.onTop = onTop;
    }

    public String getOntopTime() {
        return ontopTime;
    }

    public void setOntopTime(String ontopTime) {
        this.ontopTime = ontopTime == null ? null : ontopTime.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId == null ? null : versionId.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl == null ? null : previewUrl.trim();
    }

    public String getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(String totalClick) {
        this.totalClick = totalClick == null ? null : totalClick.trim();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}