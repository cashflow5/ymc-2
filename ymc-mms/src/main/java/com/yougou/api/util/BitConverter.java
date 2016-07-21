package com.yougou.api.util;

public class BitConverter {

	public static final String HEX_STRING = "0123456789abcdef";

	/**
	 * 二进制转换为十六进制
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String byteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			char high = HEX_STRING.charAt((bytes[i] & 0xf0) >> 4);
			char low = HEX_STRING.charAt((bytes[i] & 0x0f));
			sb.append(high).append(low);
		}
		return sb.toString();
	}

	/**
	 * 十六进制转换为二进制
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hexToByteArray(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			byte high = (byte) (HEX_STRING.indexOf(hex.charAt(2 * i)) << 4);
			byte low = (byte) HEX_STRING.indexOf(hex.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);
		}
		return bytes;
	}
}
