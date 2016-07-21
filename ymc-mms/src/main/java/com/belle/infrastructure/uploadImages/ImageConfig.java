package com.belle.infrastructure.uploadImages;


/**
 * Bean类 - 图片配置
 */

public class ImageConfig {

    // 水印位置（无、左上、右上、居中、左下、右下）
    public enum WatermarkPosition {
        no, topLeft, topRight, center, bottomLeft, bottomRight
    }

    public static final String LOGO_UPLOAD_NAME                          = "logo";                            // Logo图片文件名称(不包含扩张名)
    public static final String DEFAULT_BIG_PRODUCT_IMAGE_FILE_NAME       = "default_big_product_image";       // 默认商品图片（大）文件名称(不包含扩展名)
    public static final String DEFAULT_SMALL_PRODUCT_IMAGE_FILE_NAME     = "default_small_product_image";     // 默认商品图片（小）文件名称(不包含扩展名)
    public static final String DEFAULT_THUMBNAIL_PRODUCT_IMAGE_FILE_NAME = "default_thumbnail_product_image"; // 商品缩略图文件名称(不包含扩展名)
    public static final String WATERMARK_IMAGE_FILE_NAME                 = "watermark";                       // 水印图片文件名称(不包含扩展名)
    public static final String UPLOAD_IMAGE_DIR                          = "/upload/image/";                  // 图片文件上传目录
    public static final String UPLOAD_MEDIA_DIR                          = "/upload/media/";                  // 媒体文件上传目录
    public static final String UPLOAD_FILE_DIR                           = "/upload/file/";                   // 其它文件上传目录

    private Integer            uploadLimit;                                                                   // 文件上传最大值,0表示无限制,单位KB

    private String             watermarkImagePath;                                                           // 水印图片路径
    private WatermarkPosition  watermarkPosition;                                                            // 水印位置
    private Integer            watermarkAlpha;                                                                // 水印透明度

    private Integer            bigProductImageWidth;                                                          // 商品图片（大）宽度
    private Integer            bigProductImageHeight;                                                         // 商品图片（大）高度
    private Integer            smallProductImageWidth;                                                        // 商品图片（小）宽度
    private Integer            smallProductImageHeight;                                                       // 商品图片（小）高度
    private Integer            thumbnailProductImageWidth;                                                    // 商品缩略图宽度
    private Integer            thumbnailProductImageHeight;                                                   // 商品缩略图高度

    private String             defaultBigProductImagePath;                                                    // 默认商品图片（大）
    private String             defaultSmallProductImagePath;                                                  // 默认商品图片（小）
    private String             defaultThumbnailProductImagePath;                                              // 默认缩略图

    private String             allowedUploadImageExtension;                                                   // 允许上传的图片文件扩展名（为空表示不允许上传图片文件）
    private String             allowedUploadMediaExtension;                                                   // 允许上传的媒体文件扩展名（为空表示不允许上传媒体文件）
    private String             allowedUploadFileExtension;                                                    // 允许上传的文件扩展名（为空表示不允许上传文件）

    public Integer getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(Integer uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

    public String getWatermarkImagePath() {
        return watermarkImagePath;
    }

    public void setWatermarkImagePath(String watermarkImagePath) {
        this.watermarkImagePath = watermarkImagePath;
    }

    public WatermarkPosition getWatermarkPosition() {
        return watermarkPosition;
    }

    public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
        this.watermarkPosition = watermarkPosition;
    }

    public Integer getWatermarkAlpha() {
        return watermarkAlpha;
    }

    public void setWatermarkAlpha(Integer watermarkAlpha) {
        this.watermarkAlpha = watermarkAlpha;
    }

    public Integer getBigProductImageWidth() {
        return bigProductImageWidth;
    }

    public void setBigProductImageWidth(Integer bigProductImageWidth) {
        this.bigProductImageWidth = bigProductImageWidth;
    }

    public Integer getBigProductImageHeight() {
        return bigProductImageHeight;
    }

    public void setBigProductImageHeight(Integer bigProductImageHeight) {
        this.bigProductImageHeight = bigProductImageHeight;
    }

    public Integer getSmallProductImageWidth() {
        return smallProductImageWidth;
    }

    public void setSmallProductImageWidth(Integer smallProductImageWidth) {
        this.smallProductImageWidth = smallProductImageWidth;
    }

    public Integer getSmallProductImageHeight() {
        return smallProductImageHeight;
    }

    public void setSmallProductImageHeight(Integer smallProductImageHeight) {
        this.smallProductImageHeight = smallProductImageHeight;
    }

    public Integer getThumbnailProductImageWidth() {
        return thumbnailProductImageWidth;
    }

    public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
        this.thumbnailProductImageWidth = thumbnailProductImageWidth;
    }

    public Integer getThumbnailProductImageHeight() {
        return thumbnailProductImageHeight;
    }

    public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
        this.thumbnailProductImageHeight = thumbnailProductImageHeight;
    }

    public String getDefaultBigProductImagePath() {
        return defaultBigProductImagePath;
    }

    public void setDefaultBigProductImagePath(String defaultBigProductImagePath) {
        this.defaultBigProductImagePath = defaultBigProductImagePath;
    }

    public String getDefaultSmallProductImagePath() {
        return defaultSmallProductImagePath;
    }

    public void setDefaultSmallProductImagePath(String defaultSmallProductImagePath) {
        this.defaultSmallProductImagePath = defaultSmallProductImagePath;
    }

    public String getDefaultThumbnailProductImagePath() {
        return defaultThumbnailProductImagePath;
    }

    public void setDefaultThumbnailProductImagePath(String defaultThumbnailProductImagePath) {
        this.defaultThumbnailProductImagePath = defaultThumbnailProductImagePath;
    }

    public String getAllowedUploadImageExtension() {
        return allowedUploadImageExtension;
    }

    public void setAllowedUploadImageExtension(String allowedUploadImageExtension) {
        this.allowedUploadImageExtension = allowedUploadImageExtension;
    }

    public String getAllowedUploadMediaExtension() {
        return allowedUploadMediaExtension;
    }

    public void setAllowedUploadMediaExtension(String allowedUploadMediaExtension) {
        this.allowedUploadMediaExtension = allowedUploadMediaExtension;
    }

    public String getAllowedUploadFileExtension() {
        return allowedUploadFileExtension;
    }

    public void setAllowedUploadFileExtension(String allowedUploadFileExtension) {
        this.allowedUploadFileExtension = allowedUploadFileExtension;
    }

}
