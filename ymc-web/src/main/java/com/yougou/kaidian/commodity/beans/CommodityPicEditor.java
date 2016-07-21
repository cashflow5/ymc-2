package com.yougou.kaidian.commodity.beans;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商品图片编辑器
 * 
 * @author yang.mq
 *
 */
public class CommodityPicEditor extends PropertyEditorSupport {
	
	// 1KB
	public static final long KB = 1024L;
	
	// 尺寸分离器
	public static final String DIMENSION_SEPARATOR = "*";
	
	/** 列表页图片（1个角度） **/
	public static final Pattern _XX_S = Pattern.compile("_01_s\\.jpg$", Pattern.MULTILINE);
	
	/** 商品详细页左边大图（7个角度） **/
	public static final Pattern _XX_M = Pattern.compile("_0[1-7]_m\\.jpg$", Pattern.MULTILINE);
	
	/** 商品缩略小图（7个角度） **/
	public static final Pattern _XX_T = Pattern.compile("_0[1-7]_t\\.jpg$", Pattern.MULTILINE);
	
	/** 商品颜色选择小图（1个角度） **/
	public static final Pattern _XX_C = Pattern.compile("_01_c\\.jpg$", Pattern.MULTILINE);
	
	/** 放大镜 大图（7个角度） **/
	public static final Pattern _XX_L = Pattern.compile("_0[1-7]_l\\.jpg$", Pattern.MULTILINE);
	
	/** 商品描述图 **/
	public static final Pattern _XX_B = Pattern.compile("_(0[1-9]|[1-9][0-9])_b\\.jpg$", Pattern.MULTILINE);
	
	/** 手机版小图（7个角度） **/
	public static final Pattern _XX_MB = Pattern.compile("_0[1-7]_mb\\.jpg$", Pattern.MULTILINE);
	
	/** 手机版小图（7个角度） **/
	public static final Pattern _XX_MS = Pattern.compile("_0[1-7]_ms\\.jpg$", Pattern.MULTILINE);
	
	/** 后台程序（1个角度） **/
	public static final Pattern _XX_U = Pattern.compile("_01_u\\.jpg$", Pattern.MULTILINE);
	
	
	/** _L 角度图 **/
	public static final Pattern XX_L = Pattern.compile("_0[1-7]_l\\.jpg", Pattern.MULTILINE);
	
	/** _B 描述图 **/
	public static final Pattern XX_B = Pattern.compile("_(0[1-9]|[1-9][0-9])_b\\.jpg", Pattern.MULTILINE);
	
	/** _0x 临时原图  */
	public static final Pattern _0X_L = Pattern.compile("_0[1-7]_l$", Pattern.MULTILINE);
	
	// 图片名称正则表达式
	public static final Pattern[] COMMODITY_PIC_NAME_PATTERNS = { _XX_S, _XX_M, _XX_T, _XX_C, _XX_L, _XX_B, _XX_MB, _XX_MS, _XX_U };
	
	// 商品类型正则表达式
	public static final Pattern COMMODITY_PIC_TYPE_PATTERN = Pattern.compile("[A-Za-z]+(?! \\.jpg)", Pattern.MULTILINE);
	
	// 图片尺寸正则表达式
	public static final Pattern[] COMMODITY_PIC_DIMENSIONS = {
		Pattern.compile("^160\\" + DIMENSION_SEPARATOR + "160$", Pattern.MULTILINE),
		Pattern.compile("^480\\" + DIMENSION_SEPARATOR + "480$", Pattern.MULTILINE),
		Pattern.compile("^60\\" + DIMENSION_SEPARATOR + "60$", Pattern.MULTILINE),
		Pattern.compile("^40\\" + DIMENSION_SEPARATOR + "40$", Pattern.MULTILINE),
		Pattern.compile("^([89][0-9][0-9]|1000)\\" + DIMENSION_SEPARATOR + "([89][0-9][0-9]|1000)$", Pattern.MULTILINE),
		Pattern.compile("^7([4-8][0-9]|90)\\" + DIMENSION_SEPARATOR + "[1-9]([0-9]|[0-9][0-9]|[0-9][0-9][0-9])$", Pattern.MULTILINE),
		Pattern.compile("^240\\" + DIMENSION_SEPARATOR + "240$", Pattern.MULTILINE),
		Pattern.compile("^160\\" + DIMENSION_SEPARATOR + "160$", Pattern.MULTILINE),
		Pattern.compile("^100\\" + DIMENSION_SEPARATOR + "100$", Pattern.MULTILINE)
	};
	
	// 图片大小
	public static final long[] COMMODITY_PIC_SIZES = { 
		KB + KB * 10L, 
		KB + KB * 60L, 
		KB + KB * 10L, 
		KB + KB * 10L, 
		KB + KB * 500L, 
		KB + KB * 500L,
		KB + KB * 25L, 
		KB + KB * 10L, 
		KB + KB * 10L
	};
	
	// 开始索引
	private static final int START_INDEX = 4;
	
	// 结束索引
	private static final int END_INDEX = 6;
	
	private int startIndex;
	
	private int endIndex;
	
	public CommodityPicEditor() {
		this(START_INDEX, END_INDEX);
	}

	public CommodityPicEditor(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			for (int i = startIndex; i < endIndex; i++) {
				Matcher matcher = COMMODITY_PIC_NAME_PATTERNS[i].matcher(text);
				if (matcher.find()) {
					CommodityPicDescriptor descriptor = new CommodityPicDescriptor();
					descriptor.suffix = matcher.group();
					descriptor.prefix = matcher.replaceAll("");
					matcher = COMMODITY_PIC_TYPE_PATTERN.matcher(text);
					descriptor.type = matcher.find() ? matcher.group() : null;
					descriptor.maxSize = COMMODITY_PIC_SIZES[i];
					descriptor.dimensionPattern = COMMODITY_PIC_DIMENSIONS[i];
					setValue(descriptor);
					break;
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public String getAsText() {
		return super.getAsText();
	}
	
	/**
	 * 商品图片描述
	 * 
	 * @author yang.mq
	 *
	 */
	public static class CommodityPicDescriptor {
		
		// 空描述
		public static final CommodityPicDescriptor NONE = new CommodityPicDescriptor();
		
		private String prefix;
		private String suffix;
		private String type;
		private Long maxSize;
		private Pattern dimensionPattern;
		
		public String getPrefix() {
			return prefix;
		}
		
		public String getSuffix() {
			return suffix;
		}

		public String getType() {
			return type;
		}

		public Long getMaxSize() {
			return maxSize;
		}

		public Pattern getDimensionPattern() {
			return dimensionPattern;
		}

		@Override
		public String toString() {
			return "CommodityPicDescriptor [prefix=" + prefix + ", suffix=" + suffix + ", type=" + type + ", maxSize=" + maxSize + ", dimensionPattern=" + dimensionPattern + "]";
		}
	}
}
