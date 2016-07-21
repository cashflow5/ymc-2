package com.yougou.kaidian.framework.settings;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommoditySettings implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommoditySettings.class);
	
	/**
	 * 商品放大镜图片默认数量(单位：张)
	 */
	public static final int COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS = 5;
	
	/**
	 * 商品放大镜图片派生最低数量(单位：张)
	 */
	public static final int COMMODITY_MAGNIFIER_IMAGE_DERIVE_LEAST_NUMBERS = 4;
	
	/**
	 * 商品描述图片最低数量(单位：张)
	 */
	public static final int COMMODITY_DESCRIPTION_IMAGE_LEAST_NUMBERS = 1;
	
	
	@Value("#{commodityPicsSettings['image.ftp.server']}")
	public String imageFtpServer;
	
	@Value("#{commodityPicsSettings['image.ftp.port']}")
	public Integer imageFtpPort;
	
	@Value("#{commodityPicsSettings['image.ftp.username']}")
	public String imageFtpUsername;
	
	@Value("#{commodityPicsSettings['image.ftp.password']}")
	public String imageFtpPassword;
	
	@Value("#{commodityPicsSettings['image.ftp.connect.timeout']}")
	public Integer imageFtpConnectTimeout;
	
	@Value("#{commodityPicsSettings['image.ftp.temporary.space']}")
	public String imageFtpTemporarySpace;
	
	@Value("#{commodityPicsSettings['merchants.image.regex']}")
	public Pattern merchantsImagePattern;
	
	@Value("#{commodityPicsSettings['yougou.image.regex']}")
	public Pattern yougouImagePattern;
	
	@Value("#{commodityPicsSettings['yougou.valid.image.regex']}")
	public String yougouValidImageRegex;
	
	@Value("#{commodityPreviewSettings['commodity.preview.domain']}")
	private String commodityPreviewDomain;
	
	@Value("#{commodityPreviewSettings['commodity.preview.memory.url']}")
	public String commodityPreviewMemoryUrl;
	
	@Value("#{commodityPreviewSettings['commodity.preview.persistent.url']}")
	public String commodityPreviewPersistentUrl;
	
	@Value("#{commodityPicsSettings['unix.commodity.temporary.picdir']}")
	public String picDir;
	
	@Value("#{commodityPicsSettings['windows.commodity.temporary.picdir']}")
	public String winpicDir;
	
	@Value("#{commodityPicsSettings['image.ftp.pre.temp.space']}")
	public String imageFtpPreTempSpace;
	
	@Value("#{commodityPicsSettings['image.cdn.server']}")
	public String imageLayoutFtpServer;

	@Value("#{commodityPicsSettings['compensate.image.ftp.space']}")
	public String compensateImageFtpSpace;
	
	@Value("#{commodityPreviewSettings['compensate.preview.domain']}")
	public String compensatePreviewDomain;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (File.separatorChar == '/') {// Unix
			LOGGER.info("OS is unix, Ignore overriding [ImageMagick, Perl] settings.");
		} else {// Windows
			LOGGER.info("OS is windows, Prepare overriding [ImageMagick, Perl] settings.");
			LOGGER.info("Overriding picDir " + this.picDir + " to " + this.winpicDir);
			this.picDir=this.winpicDir;
		}
		File picDir=new File(this.picDir);
		if(!picDir.exists()){
			boolean temp=picDir.mkdirs();
			LOGGER.info("mkdir commodity.temporary.picdir:" + this.picDir + " is " + temp);
		}
		// trace settings
		LOGGER.info(ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).replaceFirst(this.getClass().getName(), "Describer"));
	}

	public String getCommodityPreviewDomain() {
		return commodityPreviewDomain.replaceAll("/+$", "");
	}

	public String getImageFtpTemporarySpace() {
		imageFtpTemporarySpace = imageFtpPreTempSpace.replaceAll("temp",
				"tempdesc");
		return imageFtpTemporarySpace;
	}
	
}
