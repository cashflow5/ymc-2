package com.belle.yitiansystem.systemmgmt.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * IP地址工具，目前只支持IPv4
 * @author zwx @ 2011-11-21
 *
 */
public class IpUtil {
	
	 /**
     * 将字符串形式的ip地址转换为BigInteger
     *
     * @param ipInString
     *            字符串形式的ip地址
     * @return 整数形式的ip地址
     */
    public static BigInteger StringToBigInt(String ipInString) {
        ipInString = ipInString.replace(" ", "");
        byte[] bytes;
        if (ipInString.contains(":"))
            bytes = ipv6ToBytes(ipInString);
        else
            bytes = ipv4ToBytes(ipInString);
        return new BigInteger(bytes);
    }
 
    /**
     * 将整数形式的ip地址转换为字符串形式
     *
     * @param ipInBigInt
     *            整数形式的ip地址
     * @return 字符串形式的ip地址
     */
    public static String BigIntToString(BigInteger ipInBigInt) {
        byte[] bytes = ipInBigInt.toByteArray();
        byte[] unsignedBytes = bytes;
 
        // 去除符号位
        try {
            String ip = InetAddress.getByAddress(unsignedBytes).toString();
            return ip.substring(ip.indexOf('/') + 1).trim();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * ipv6地址转有符号byte[17]
     * @param   ipv6 字符串形式的IP地址
     * @return big integer number
     */
    private static byte[] ipv6ToBytes(String ipv6) {
        byte[] ret = new byte[17];
        ret[0] = 0;
        int ib = 16;
        boolean comFlag = false;// ipv4混合模式标记
        if (ipv6.startsWith(":"))// 去掉开头的冒号
            ipv6 = ipv6.substring(1);
        String groups[] = ipv6.split(":");
        for (int ig = groups.length - 1; ig > -1; ig--) {// 反向扫描
            if (groups[ig].contains(".")) {
                // 出现ipv4混合模式
                byte[] temp = ipv4ToBytes(groups[ig]);
                ret[ib--] = temp[4];
                ret[ib--] = temp[3];
                ret[ib--] = temp[2];
                ret[ib--] = temp[1];
                comFlag = true;
            } else if ("".equals(groups[ig])) {
                // 出现零长度压缩,计算缺少的组数
                int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
                while (zlg-- > 0) {// 将这些组置0
                    ret[ib--] = 0;
                    ret[ib--] = 0;
                }
            } else {
                int temp = Integer.parseInt(groups[ig], 16);
                ret[ib--] = (byte) temp;
                ret[ib--] = (byte) (temp >> 8);
            }
        }
        return ret;
    }
 
    /**
     * ipv4地址转有符号byte[5]
     * @param ipv4 字符串的IPV4地址
     * @return big integer number
     */
    private static byte[] ipv4ToBytes(String ipv4) {
        byte[] ret = new byte[5];
        ret[0] = 0;
        // 先找到IP地址字符串中.的位置
        int position1 = ipv4.indexOf(".");
        int position2 = ipv4.indexOf(".", position1 + 1);
        int position3 = ipv4.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ret[1] = (byte) Integer.parseInt(ipv4.substring(0, position1));
        ret[2] = (byte) Integer.parseInt(ipv4.substring(position1 + 1,
                position2));
        ret[3] = (byte) Integer.parseInt(ipv4.substring(position2 + 1,
                position3));
        ret[4] = (byte) Integer.parseInt(ipv4.substring(position3 + 1));
        return ret;
    }
    /**
     * 判断是否为IPv4地址
     * @param tip   要限制的Ip 包括Ipv6
     * @return  Boolean true通过
     *      false 受限制
     */
    public static boolean IsIp4(String ip){
		Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );
		return pattern.matcher(ip).matches();
    }
    
	public static long ipv4ToInt(String ipv4) {
		long iip = 0L;
		if (IsIp4(ipv4)){
			String[] parts = ipv4.split("\\.");
			iip += Long.parseLong(parts[0])*256*256*256;
			iip += Long.parseLong(parts[1])*256*256;
			iip += Long.parseLong(parts[2])*256;
			iip += Long.parseLong(parts[3]);
		}
		return iip;
	}

 }
