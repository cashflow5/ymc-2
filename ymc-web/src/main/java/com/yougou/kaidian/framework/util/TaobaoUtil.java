package com.yougou.kaidian.framework.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class TaobaoUtil {

/*	*//**
	 * 
	 * 签名运算
	 * 
	 * @param parameter
	 * 
	 * @param secret
	 * 
	 * @return
	 * 
	 * @throws EncryptException
	 * 
	 *
	 *//*

	public static String sign(String parameter, String secret)
			throws EncryptException {

		// 对参数+密钥做MD5运算

		MessageDigest md = null;

		try {

			md = MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {

			log.error(e.getMessage(), e);

			throw new EncryptException(e);

		}

		byte[] digest = md.digest((parameter + secret).getBytes());

		// 对运算结果做BASE64运算并加密

		BASE64Encoder encode = new BASE64Encoder();

		return encode.encode(digest);

	}

	/**
	 * 
	 * 验证签名
	 * 
	 * @param sign
	 * 
	 * @param parameter
	 * 
	 * @param secret
	 * 
	 * @return
	 * 
	 * @throws EncryptException
	 

	public static boolean validateSign(String sign, String parameter, String secret) throws EncryptException {
		return sign != null && parameter != null && secret != null && sign.equals(sign(parameter, secret));
		// 注意，这个parameter并不就是上面的top_paramater，而是指的待签名验证的参数，即上面的top_appkey+top_parameter+top_session
	}
*/

	// 验证TOP回调地址的签名是否合法
/*
	*//**
	 * 
	 * 验证TOP回调地址的签名是否合法。要求所有参数均为已URL反编码的。
	 * 
	 * @param topParams
	 *            TOP私有参数（未经BASE64解密）
	 * 
	 * @param topSession
	 *            TOP私有会话码
	 * 
	 * @param topSign
	 *            TOP回调签名
	 * 
	 * @param appKey
	 *            应用公钥
	 * 
	 * @param appSecret
	 *            应用密钥
	 * 
	 * @param appSecret
	 *            应用密钥
	 * 
	 * @return 验证成功返回true，否则返回false
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 * @throws IOException
	 *//*

	public static boolean verifyTopResponse(String topParams, String topSession, String topSign, String appKey, String appSecret) throws NoSuchAlgorithmException, IOException {

		StringBuilder result = new StringBuilder();

		MessageDigest md5 = MessageDigest.getInstance("MD5");

		result.append(appKey).append(topParams).append(topSession).append(appSecret);

		byte[] bytes = md5.digest(result.toString().getBytes("UTF-8"));

		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(bytes).equals(topSign);

	}
*/
	// 解析top_parameters

	public static Map<String, String> getParametersMap(String topParameters, String charset) {

		Map<String, String> mapTopParameters = convertBase64StringtoMap(topParameters, charset);
		
//		String nick = null;
//		Iterator<String> keyValuePairs = mapParametersValue.keySet().iterator();
//		while(keyValuePairs.hasNext()) {
//			String k = keyValuePairs.next();
//			if(k.equals("visitor_nick")){
//				nick = mapParametersValue.get(k);
//			}
//		}

		return mapTopParameters;
	}
	
	public static Map<String, String> getParametersMap(String topParameters) {
		return getParametersMap(topParameters, null);
	}

	/**
	 * 
	 * 把经过BASE64编码的字符串转换为Map对象
	 * 
	 * 
	 * 
	 * @param str
	 *            callback
	 *            url上top_parameters的值，request.getParameter("top_parameter"
	 *            )方法获得参数已经做了url
	 * 
	 *            decode。这里需要的就是decode后的参数
	 * 
	 * @param encode
	 *            callback url上encode的值，如果不存在此参数请传null
	 * 
	 * @return
	 */

	private static Map<String, String> convertBase64StringtoMap(String str, String charset) {
		if (str == null)
			return null;
		if (charset == null)
			charset = "GBK";
		
		String keyvalues = null;
		try {
			keyvalues = new String(Base64.decodeBase64(str.getBytes(charset)), charset);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		String[] keyvalueArray = keyvalues.split("\\&");
		Map<String, String> map = new HashMap<String, String>();
		for (String keyvalue : keyvalueArray) {
			String[] s = keyvalue.split("\\=");
			if (s == null || s.length != 2)
				return null;
			map.put(s[0], s[1]);
		}
		return map;
	}
}
