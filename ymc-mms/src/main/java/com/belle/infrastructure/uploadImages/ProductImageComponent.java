package com.belle.infrastructure.uploadImages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @descript：图片上传组件
 * @author ：方勇
 * @email ：fangyong@broadengate.com
 * @time ： 2011-4-21 上午07:49:47
 */
@Component
public class ProductImageComponent implements IProductImageComponent {

    /**
     * 随机获取UUID字符串(无中划线)
     * 
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
               + uuid.substring(24);
    }

    /**
     * 保存大中小图片
     */
    public ProductImage buildProductImage(File uploadProductImageFile) {
        ImageConfig systemConfig = ImageConfigUtil.getImageConfig();
        String sourceProductImageFormatName = ImageUtil.getImageFormatName(uploadProductImageFile);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String dateString = simpleDateFormat.format(new Date());
        String uuid = getUUID();

        String sourceProductImagePath = "/upload/image/" + dateString + "/" + uuid + "." + sourceProductImageFormatName;
        String bigProductImagePath = "/upload/image/" + dateString + "/" + uuid + "_big" + "." + "jpg";
        String smallProductImagePath = "/upload/image/" + dateString + "/" + uuid + "_small" + "." + "jpg";
        String thumbnailProductImagePath = "/upload/image/" + dateString + "/" + uuid + "_thumbnail" + "." + "jpg";

        File sourceProductImageFile = new File(SpringUtil.getServletContext().getRealPath(sourceProductImagePath));
        File bigProductImageFile = new File(SpringUtil.getServletContext().getRealPath(bigProductImagePath));
        File smallProductImageFile = new File(SpringUtil.getServletContext().getRealPath(smallProductImagePath));
        File thumbnailProductImageFile = new File(SpringUtil.getServletContext().getRealPath(thumbnailProductImagePath));
        File watermarkImageFile = new File(
                                           SpringUtil.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

        File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
        File bigProductImageParentFile = bigProductImageFile.getParentFile();
        File smallProductImageParentFile = smallProductImageFile.getParentFile();
        File thumbnailProductImageParentFile = thumbnailProductImageFile.getParentFile();

        if (!(sourceProductImageParentFile.exists())) sourceProductImageParentFile.mkdirs();

        if (!(bigProductImageParentFile.exists())) bigProductImageParentFile.mkdirs();

        if (!(smallProductImageParentFile.exists())) smallProductImageParentFile.mkdirs();

        if (!(thumbnailProductImageParentFile.exists())) thumbnailProductImageParentFile.mkdirs();

        try {
            BufferedImage srcBufferedImage = ImageIO.read(uploadProductImageFile);

            FileUtils.copyFile(uploadProductImageFile, sourceProductImageFile);

            ImageUtil.zoomAndWatermark(srcBufferedImage, bigProductImageFile,
                                       systemConfig.getBigProductImageHeight().intValue(),
                                       systemConfig.getBigProductImageWidth().intValue(), watermarkImageFile,
                                       systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());

            ImageUtil.zoomAndWatermark(srcBufferedImage, smallProductImageFile,
                                       systemConfig.getSmallProductImageHeight().intValue(),
                                       systemConfig.getSmallProductImageWidth().intValue(), watermarkImageFile,
                                       systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());

            ImageUtil.zoom(srcBufferedImage, thumbnailProductImageFile,
                           systemConfig.getThumbnailProductImageHeight().intValue(),
                           systemConfig.getThumbnailProductImageWidth().intValue());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProductImage productImage = new ProductImage();
        productImage.setId(uuid);
        productImage.setSourceProductImagePath(sourceProductImagePath);
        productImage.setBigProductImagePath(bigProductImagePath);
        productImage.setSmallProductImagePath(smallProductImagePath);
        productImage.setThumbnailProductImagePath(thumbnailProductImagePath);
        return productImage;
    }

    /**
     * 删除图片
     */
    public void deleteFile(ProductImage productImage) {
        File sourceProductImageFile = new File(
                                               SpringUtil.getServletContext().getRealPath(productImage.getSourceProductImagePath()));
        if (sourceProductImageFile.exists()) sourceProductImageFile.delete();

        File bigProductImageFile = new File(
                                            SpringUtil.getServletContext().getRealPath(productImage.getBigProductImagePath()));
        if (bigProductImageFile.exists()) bigProductImageFile.delete();

        File smallProductImageFile = new File(
                                              SpringUtil.getServletContext().getRealPath(productImage.getSmallProductImagePath()));
        if (smallProductImageFile.exists()) smallProductImageFile.delete();

        File thumbnailProductImageFile = new File(
                                                  SpringUtil.getServletContext().getRealPath(productImage.getThumbnailProductImagePath()));
        if (thumbnailProductImageFile.exists()) thumbnailProductImageFile.delete();
    }

    /**
     * 批量生成商品图片（包括原图、大图、小图、缩略图）
     * 
     * @param uploadProductImageFile 上传图片文件
     * @throws IOException 
     */
    public List<ProductImage> batchBuildProductImage(List<MultipartFile> imageList) throws IOException {
        List<ProductImage> productImageList = null;
        if(null!=imageList){          
            productImageList = new ArrayList<ProductImage>();
            // 图片组
            for (int i = 0; i < imageList.size(); i++) {
                MultipartFile multipartFile = imageList.get(i);
                if (multipartFile.isEmpty()) continue;
                byte[] bytes = multipartFile.getBytes();
                File file = new File(multipartFile.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
                
                ProductImage destProductImage = buildProductImage(file);
                productImageList.add(destProductImage);
            }
        }
        return productImageList;
    }

}
