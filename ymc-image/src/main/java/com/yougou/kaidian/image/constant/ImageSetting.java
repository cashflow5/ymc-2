/**
 * 
 */
package com.yougou.kaidian.image.constant;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.yougou.kaidian.image.util.CustomizedPropertyConfigurer;

/**
 * 图片相关常量
 * 
 * @author li.m
 *
 */
@Component
public class ImageSetting implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(ImageSetting.class);
	
	public String imageMagickHome;
	public String imageMagickSharpen;
	public String perlHome;
	public String perlScripts;
	public String commodityTemporaryPicdir;
	public String imageIpDownDomain;
	public String imagePreviewDomain;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (File.separatorChar == '/') {// Unix
			this.imageMagickHome=CustomizedPropertyConfigurer.getContextProperty("unix.image.magick.home");
			this.perlHome=CustomizedPropertyConfigurer.getContextProperty("unix.perl.home");
			this.perlScripts=CustomizedPropertyConfigurer.getContextProperty("unix.perl.scripts");
			this.commodityTemporaryPicdir=CustomizedPropertyConfigurer.getContextProperty("unix.commodity.temporary.picdir");
		} else {// Windows
			this.imageMagickHome=CustomizedPropertyConfigurer.getContextProperty("windows.image.magick.home");
			this.perlHome=CustomizedPropertyConfigurer.getContextProperty("windows.perl.home");
			this.perlScripts=CustomizedPropertyConfigurer.getContextProperty("windows.perl.scripts");
			this.commodityTemporaryPicdir=CustomizedPropertyConfigurer.getContextProperty("windows.commodity.temporary.picdir");
		}
		this.imageMagickSharpen=CustomizedPropertyConfigurer.getContextProperty("image.magick.sharpen");
		this.imageIpDownDomain=CustomizedPropertyConfigurer.getContextProperty("image.down.ip.domain");
		this.imagePreviewDomain=CustomizedPropertyConfigurer.getContextProperty("image.preview.domain");
		logger.info(
				"ImageSetting--->imageMagickHome:[{}] imageMagickSharpen:[{}] perlHome:[{}] perlScripts:[{}] commodityTemporaryPicdir:[{}] commodityPreviewDomain:[{}]",new Object[]{
				this.imageMagickHome, this.imageMagickSharpen, this.perlHome,
				this.perlScripts, this.commodityTemporaryPicdir,this.imagePreviewDomain});
		File picDir = new File(this.commodityTemporaryPicdir);
		if (!picDir.exists()) {
			boolean temp = picDir.mkdirs();
			logger.info("mkdir commodity.temporary.picdir:{} is {}.",
					new Object[]{this.commodityTemporaryPicdir,temp});
		}
	}
}
