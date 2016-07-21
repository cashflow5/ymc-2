package com.yougou.api.constant;


/**
 * 位权重常量运算类
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 4:48:43 PM
 */
public enum BitWeight {
	
	AUTHORIZING(0x1L, "需要授权"),
	AUTOWIRING(0x2L, "自动装配属性"),
	PAGING(0x4L, "自动计算分页"),
	POWER_2_3(0x8L, null),
	POWER_2_4(0x10L, null),
	POWER_2_5(0x20L, null),
	POWER_2_6(0x40L, null),
	POWER_2_7(0x80L, null),
	POWER_2_8(0x100L, null),
	POWER_2_9(0x200L, null),
	POWER_2_10(0x400L, null),
	POWER_2_11(0x800L, null),
	POWER_2_12(0x1000L, null),
	POWER_2_13(0x2000L, null),
	POWER_2_14(0x4000L, null),
	POWER_2_15(0x8000L, null),
	POWER_2_16(0x10000L, null),
	POWER_2_17(0x20000L, null),
	POWER_2_18(0x40000L, null),
	POWER_2_19(0x80000L, null),
	POWER_2_20(0x100000L, null),
	POWER_2_21(0x200000L, null),
	POWER_2_22(0x400000L, null),
	POWER_2_23(0x800000L, null),
	POWER_2_24(0x1000000L, null),
	POWER_2_25(0x2000000L, null),
	POWER_2_26(0x4000000L, null),
	POWER_2_27(0x8000000L, null),
	POWER_2_28(0x10000000L, null),
	POWER_2_29(0x20000000L, null),
	POWER_2_30(0x40000000L, null),
	POWER_2_31(0x80000000L, null),
	POWER_2_32(0x100000000L, null),
	POWER_2_33(0x200000000L, null),
	POWER_2_34(0x400000000L, null),
	POWER_2_35(0x800000000L, null),
	POWER_2_36(0x1000000000L, null),
	POWER_2_37(0x2000000000L, null),
	POWER_2_38(0x4000000000L, null),
	POWER_2_39(0x8000000000L, null),
	POWER_2_40(0x10000000000L, null),
	POWER_2_41(0x20000000000L, null),
	POWER_2_42(0x40000000000L, null),
	POWER_2_43(0x80000000000L, null),
	POWER_2_44(0x100000000000L, null),
	POWER_2_45(0x200000000000L, null),
	POWER_2_46(0x400000000000L, null),
	POWER_2_47(0x800000000000L, null),
	POWER_2_48(0x1000000000000L, null),
	POWER_2_49(0x2000000000000L, null),
	POWER_2_50(0x4000000000000L, null),
	POWER_2_51(0x8000000000000L, null),
	POWER_2_52(0x10000000000000L, null),
	POWER_2_53(0x20000000000000L, null),
	POWER_2_54(0x40000000000000L, null),
	POWER_2_55(0x80000000000000L, null),
	POWER_2_56(0x100000000000000L, null),
	POWER_2_57(0x200000000000000L, null),
	POWER_2_58(0x400000000000000L, null),
	POWER_2_59(0x800000000000000L, null),
	POWER_2_60(0x1000000000000000L, null),
	POWER_2_61(0x2000000000000000L, null),
	POWER_2_62(0x4000000000000000L, null);
	
	private long value;
	private String label;
	
	private BitWeight(long value, String label) {
		this.value = value;
		this.label = label;
	}

	public long getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}

	/**
	 * 当前枚举位权是否被种子位权包含
	 * 
	 * @param seedWeight
	 * @return boolean
	 */
	public boolean in(long seedWeight) {
		return (seedWeight & value) == value;
	}
	
	/**
	 * 当前枚举位权是否被种子位权包含
	 * 
	 * @param seedWeight
	 * @return boolean
	 */
	public boolean in(BitWeight seedWeight) {
		return (seedWeight.value & value) == value;
	}
	
	/**
	 * 加权运算
	 * 
	 * @param seedWeight
	 * @param weight
	 * @return long
	 */
	public static long add(long seedWeight, BitWeight weight) {
		if (weight.in(seedWeight)) {
			throw new UnsupportedOperationException("seed weight '" + seedWeight + "' already contains BitWeight '" + weight.value + "'");
		}
		return seedWeight |= weight.value;
	}
	
	/**
	 * 减权运算
	 * 
	 * @param seedWeight
	 * @param weight
	 * @return long
	 */
	public static long sub(long seedWeight, BitWeight weight) {
		if (!weight.in(seedWeight)) {
			throw new UnsupportedOperationException("seed weight '" + seedWeight + "' not contains BitWeight '" + weight.value + "'");
		}
		return seedWeight ^= weight.value;
	}
}
