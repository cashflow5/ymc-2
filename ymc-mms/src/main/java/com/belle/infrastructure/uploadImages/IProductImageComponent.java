package com.belle.infrastructure.uploadImages;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service接口 - 商品图片
 */

public interface IProductImageComponent {

    /**
     * 生成商品图片（包括原图、大图、小图、缩略图）
     * 
     * @param uploadProductImageFile 上传图片文件
     */
    public ProductImage buildProductImage(File uploadProductImageFile);

    /**
     * 根据ProductImage对象删除图片文件
     * 
     * @param productImage ProductImage
     */
    public void deleteFile(ProductImage productImage);

    /**
     * 批量生成商品图片（包括原图、大图、小图、缩略图）
     * 
     * @param uploadProductImageFile 上传图片文件
     * @throws IOException 
     */
    public List<ProductImage> batchBuildProductImage(List<MultipartFile> imageList) throws IOException;
}
