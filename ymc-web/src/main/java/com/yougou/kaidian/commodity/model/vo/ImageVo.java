package com.yougou.kaidian.commodity.model.vo;

import java.io.Serializable;
import java.util.Date;


public class ImageVo implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String name;
    private String thumbnailFilename;
    private String newFilename;
    private String contentType;
    private Long size;
    private Date dateCreated;
    private Date lastUpdated;
    private String url;
    private String thumbnail_url;
    private String delete_url;
    private String delete_type;
    
    public ImageVo() {}

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the thumbnailFilename
     */
    public String getThumbnailFilename() {
        return thumbnailFilename;
    }

    /**
     * @param thumbnailFilename the thumbnailFilename to set
     */
    public void setThumbnailFilename(String thumbnailFilename) {
        this.thumbnailFilename = thumbnailFilename;
    }

    /**
     * @return the newFilename
     */
    public String getNewFilename() {
        return newFilename;
    }

    /**
     * @param newFilename the newFilename to set
     */
    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the lastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the thumbnail_url
     */
    public String getThumbnail_url() {
        return thumbnail_url;
    }

    /**
     * @param thumbnail_url the thumbnail_url to set
     */
    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    /**
     * @return the delete_url
     */
    public String getDelete_url() {
        return delete_url;
    }

    /**
     * @param delete_url the delete_url to set
     */
    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }

    /**
     * @return the delete_type
     */
    public String getDelete_type() {
        return delete_type;
    }

    /**
     * @param delete_type the delete_type to set
     */
    public void setDelete_type(String delete_type) {
        this.delete_type = delete_type;
    }

}
