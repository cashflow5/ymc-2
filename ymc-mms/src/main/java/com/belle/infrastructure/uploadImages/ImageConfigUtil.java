package com.belle.infrastructure.uploadImages;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 工具类 - 图片配置
 */

public class ImageConfigUtil {

    public static final String CONFIG_FILE_NAME = "ImageConfig.xml"; // 系统配置文件名称
    public static ImageConfig  imageConfig      = null;

    /**
     * 获取图片配置信息
     * 
     * @return ImageConfig对象
     */
    public static ImageConfig getImageConfig() {

        File configFile = null;
        Document document = null;
        try {
            String configFilePath = Thread.currentThread().getContextClassLoader().getResource("com/belle/infrastructure/uploadImages/"+CONFIG_FILE_NAME).toURI().getPath();
            configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node uploadLimitNode = document.selectSingleNode("/belle/ImageConfig/uploadLimit");
        Node watermarkImagePathNode = document.selectSingleNode("/belle/ImageConfig/watermarkImagePath");
        Node watermarkPositionNode = document.selectSingleNode("/belle/ImageConfig/watermarkPosition");
        Node watermarkAlphaNode = document.selectSingleNode("/belle/ImageConfig/watermarkAlpha");
        Node bigProductImageWidthNode = document.selectSingleNode("/belle/ImageConfig/bigProductImageWidth");
        Node bigProductImageHeightNode = document.selectSingleNode("/belle/ImageConfig/bigProductImageHeight");
        Node smallProductImageWidthNode = document.selectSingleNode("/belle/ImageConfig/smallProductImageWidth");
        Node smallProductImageHeightNode = document.selectSingleNode("/belle/ImageConfig/smallProductImageHeight");
        Node thumbnailProductImageWidthNode = document.selectSingleNode("/belle/ImageConfig/thumbnailProductImageWidth");
        Node thumbnailProductImageHeightNode = document.selectSingleNode("/belle/ImageConfig/thumbnailProductImageHeight");
        Node defaultBigProductImagePathNode = document.selectSingleNode("/belle/ImageConfig/defaultBigProductImagePath");
        Node defaultSmallProductImagePathNode = document.selectSingleNode("/belle/ImageConfig/defaultSmallProductImagePath");
        Node defaultThumbnailProductImagePathNode = document.selectSingleNode("/belle/ImageConfig/defaultThumbnailProductImagePath");
        Node allowedUploadImageExtensionNode = document.selectSingleNode("/belle/ImageConfig/allowedUploadImageExtension");
        Node allowedUploadMediaExtensionNode = document.selectSingleNode("/belle/ImageConfig/allowedUploadMediaExtension");
        Node allowedUploadFileExtensionNode = document.selectSingleNode("/belle/ImageConfig/allowedUploadFileExtension");

        imageConfig = new ImageConfig();
        imageConfig.setUploadLimit(Integer.valueOf(uploadLimitNode.getText()));
        imageConfig.setWatermarkImagePath(watermarkImagePathNode.getText());
        imageConfig.setWatermarkPosition(ImageConfig.WatermarkPosition.valueOf(watermarkPositionNode.getText()));
        imageConfig.setWatermarkAlpha(Integer.valueOf(watermarkAlphaNode.getText()));
        imageConfig.setBigProductImageWidth(Integer.valueOf(bigProductImageWidthNode.getText()));
        imageConfig.setBigProductImageHeight(Integer.valueOf(bigProductImageHeightNode.getText()));
        imageConfig.setSmallProductImageWidth(Integer.valueOf(smallProductImageWidthNode.getText()));
        imageConfig.setSmallProductImageHeight(Integer.valueOf(smallProductImageHeightNode.getText()));
        imageConfig.setThumbnailProductImageWidth(Integer.valueOf(thumbnailProductImageWidthNode.getText()));
        imageConfig.setThumbnailProductImageHeight(Integer.valueOf(thumbnailProductImageHeightNode.getText()));
        imageConfig.setDefaultBigProductImagePath(defaultBigProductImagePathNode.getText());
        imageConfig.setDefaultSmallProductImagePath(defaultSmallProductImagePathNode.getText());
        imageConfig.setDefaultThumbnailProductImagePath(defaultThumbnailProductImagePathNode.getText());
        imageConfig.setAllowedUploadImageExtension(allowedUploadImageExtensionNode.getText());
        imageConfig.setAllowedUploadMediaExtension(allowedUploadMediaExtensionNode.getText());
        imageConfig.setAllowedUploadFileExtension(allowedUploadFileExtensionNode.getText());
        return imageConfig;
    }

}
