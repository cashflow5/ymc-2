package com.yougou.kaidian.commodity.beans;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 大小调整定义
 * 
 * @author yang.mq
 *
 */
public abstract class ResizeDefinition {
	
	/**
	 * 无定义
	 */
	public static final ResizeDefinition NONE_RESIZE = new ResizeDefinition() {

		@Override
		public Set<ResizeEntry> getResizeEntrySet() {
			return Collections.emptySet();
		}

		@Override
		public Set<ResizeEntry> getDynamicResizeEntrySet() {
			return Collections.emptySet();
		}
	};
	
	/**
	 * 预定义
	 */
	public static final ResizeDefinition SCHEDULED_RESIZE = new ResizeDefinition() {
		
		private Set<ResizeEntry> resizeEntrySet = new HashSet<ResizeEntry>();
		
		private Set<ResizeEntry> dynamicResizeEntrySet = new HashSet<ResizeEntry>();
		
		{
			// 7张详情页左侧图
			resizeEntrySet.add(new ResizeEntry("_01_l.jpg", "_01_m.jpg", "480x480!"));
			resizeEntrySet.add(new ResizeEntry("_02_l.jpg", "_02_m.jpg", "480x480!"));
			resizeEntrySet.add(new ResizeEntry("_03_l.jpg", "_03_m.jpg", "480x480!"));
			resizeEntrySet.add(new ResizeEntry("_04_l.jpg", "_04_m.jpg", "480x480!"));
			resizeEntrySet.add(new ResizeEntry("_05_l.jpg", "_05_m.jpg", "480x480!"));
			//resizeEntrySet.add(new ResizeEntry("_06_l.jpg", "_06_m.jpg", "480x480!"));
			//resizeEntrySet.add(new ResizeEntry("_07_l.jpg", "_07_m.jpg", "480x480!"));
			// 7张缩略图
			resizeEntrySet.add(new ResizeEntry("_01_l.jpg", "_01_t.jpg", "60x60!"));
			resizeEntrySet.add(new ResizeEntry("_02_l.jpg", "_02_t.jpg", "60x60!"));
			resizeEntrySet.add(new ResizeEntry("_03_l.jpg", "_03_t.jpg", "60x60!"));
			resizeEntrySet.add(new ResizeEntry("_04_l.jpg", "_04_t.jpg", "60x60!"));
			resizeEntrySet.add(new ResizeEntry("_05_l.jpg", "_05_t.jpg", "60x60!"));
			//resizeEntrySet.add(new ResizeEntry("_06_l.jpg", "_06_t.jpg", "60x60!"));
			//resizeEntrySet.add(new ResizeEntry("_07_l.jpg", "_07_t.jpg", "60x60!"));
			// 7张手机图
			resizeEntrySet.add(new ResizeEntry("_01_l.jpg", "_01_mb.jpg", "240x240!"));
			resizeEntrySet.add(new ResizeEntry("_02_l.jpg", "_02_mb.jpg", "240x240!"));
			resizeEntrySet.add(new ResizeEntry("_03_l.jpg", "_03_mb.jpg", "240x240!"));
			resizeEntrySet.add(new ResizeEntry("_04_l.jpg", "_04_mb.jpg", "240x240!"));
			resizeEntrySet.add(new ResizeEntry("_05_l.jpg", "_05_mb.jpg", "240x240!"));
			//resizeEntrySet.add(new ResizeEntry("_06_l.jpg", "_06_mb.jpg", "240x240!"));
			//resizeEntrySet.add(new ResizeEntry("_07_l.jpg", "_07_mb.jpg", "240x240!"));
			// 7张手机图
			resizeEntrySet.add(new ResizeEntry("_01_l.jpg", "_01_ms.jpg", "160x160!"));
			resizeEntrySet.add(new ResizeEntry("_02_l.jpg", "_02_ms.jpg", "160x160!"));
			resizeEntrySet.add(new ResizeEntry("_03_l.jpg", "_03_ms.jpg", "160x160!"));
			resizeEntrySet.add(new ResizeEntry("_04_l.jpg", "_04_ms.jpg", "160x160!"));
			resizeEntrySet.add(new ResizeEntry("_05_l.jpg", "_05_ms.jpg", "160x160!"));
			//resizeEntrySet.add(new ResizeEntry("_06_l.jpg", "_06_ms.jpg", "160x160!"));
			//resizeEntrySet.add(new ResizeEntry("_07_l.jpg", "_07_ms.jpg", "160x160!"));
			// 1张颜色图
			dynamicResizeEntrySet.add(new ResizeEntry("_01_c.jpg", "40x40!"));
			// 1张列表图
			dynamicResizeEntrySet.add(new ResizeEntry("_01_s.jpg", "160x160!"));
			// 1张后台程序图
			dynamicResizeEntrySet.add(new ResizeEntry("_01_u.jpg", "100x100!"));
		}

		@Override
		public Set<ResizeEntry> getResizeEntrySet() {
			return Collections.unmodifiableSet(resizeEntrySet);
		}

		@Override
		public Set<ResizeEntry> getDynamicResizeEntrySet() {
			return Collections.unmodifiableSet(dynamicResizeEntrySet);
		}
	};

	/**
	 * 获取调整条目集
	 * 
	 * @return Set
	 */
	public abstract Set<ResizeEntry> getResizeEntrySet();
	
	/**
	 * 获取扩展调整条目集
	 * 
	 * @return Set
	 */
	public abstract Set<ResizeEntry> getDynamicResizeEntrySet();
	
	/**
	 * 调整条目
	 * 
	 * @author yang.mq
	 *
	 */
	public class ResizeEntry {

		private String sourceSuffix;
		private String targetSuffix;
		private String dimension;
		
		public ResizeEntry(String targetSuffix, String dimension) {
			this(null, targetSuffix, dimension);
		}

		public ResizeEntry(String sourceSuffix, String targetSuffix, String dimension) {
			this.sourceSuffix = sourceSuffix;
			this.targetSuffix = targetSuffix;
			this.dimension = dimension;
		}

		public String getSourceSuffix() {
			return sourceSuffix;
		}

		public String getTargetSuffix() {
			return targetSuffix;
		}

		public String getDimension() {
			return dimension;
		}

		@Override
		public String toString() {
			return "ResizeEntry [sourceSuffix=" + sourceSuffix + ", targetSuffix=" + targetSuffix + ", dimension=" + dimension + "]";
		}
	}
}
