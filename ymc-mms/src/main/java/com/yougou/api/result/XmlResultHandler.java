package com.yougou.api.result;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.dto.output.OutputDto;
import com.yougou.dto.output.PageableOutputDto;

/**
 * XML 类型返回结果处理类
 * 
 * @author 杨梦清
 * 
 */
public class XmlResultHandler extends AbstractResultHandler {

	public XmlResultHandler(HttpServletResponse response, String rootName) {
		super(response, rootName);
	}

	@Override
	protected String getNullResult() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append("/>\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(Collection<OutputDto> collection) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message></message>\n");
		sb.append("<total_count>").append(collection.size()).append("</total_count>\n");
		sb.append("<items>\n");

		for (Iterator<OutputDto> it = collection.iterator(); it.hasNext();) {
			sb.append("<item>\n");
			sb.append(resolveResult(getPropertyMap(it.next())));
			sb.append("</item>\n");
		}

		sb.append("</items>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(PageableOutputDto outputDto) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message></message>\n");
		sb.append("<page_index>").append(outputDto.getPage_index()).append("</page_index>\n");
		sb.append("<page_size>").append(outputDto.getPage_size()).append("</page_size>\n");
		sb.append("<total_count>").append(outputDto.getTotal_count()).append("</total_count>\n");
		sb.append("<items>\n");

		for (Object item : outputDto.getItems()) {
			sb.append("<item>\n");
			sb.append(resolveResult(getPropertyMap(item)));
			sb.append("</item>\n");
		}

		sb.append("</items>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(OutputDto result) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message></message>\n");
		sb.append("<item>\n");
		sb.append(resolveResult(getPropertyMap(result)));
		sb.append("</item>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(Exception exception) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		String outCode = YOPBusinessCode.ERROR;
		String outMessage = "调用接口异常，请联系优购技术支持！";

		for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
			if (YOPRuntimeException.class.isInstance(cause)) {
				YOPRuntimeException mse = (YOPRuntimeException) cause;
				outCode = mse.getErrorCode();
				outMessage = mse.getMessage();
				break;
			}
		}

		sb.append("<code>").append(outCode).append("</code>\n");
		sb.append("<message>").append(outMessage).append("</message>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(Boolean bool) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(bool ? YOPBusinessCode.SUCCESS : YOPBusinessCode.ERROR).append("</code>\n");
		sb.append("<message>").append(bool).append("</message>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(Date date) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message>").append("</message>\n");
		sb.append("<item>\n");
		sb.append("<date>").append(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss")).append("</date>\n");
		sb.append("</item>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	/**
	 * 将 MAP 序列化成 XML 字符串
	 * 
	 * @param itemMap
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String resolveResult(Map propertyMap) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Iterator outer = propertyMap.keySet().iterator(); outer.hasNext();) {
			Object key = outer.next();
			Object value = propertyMap.get(key);
			sb.append("<").append(key).append(">");
			if (value instanceof Date) {
				sb.append(DateFormatUtils.format((Date) value, "yyyy-MM-dd HH:mm:ss"));
			} else if (isPrimitiveInstance(value)) {
				sb.append(escape(ObjectUtils.toString(value)));
			} else if (value instanceof Collection) {
				for (Object item : (Collection) value) {
					sb.append("<item>\n");
					sb.append(resolveResult(getPropertyMap(item)));
					sb.append("</item>\n");
				}
			} else if (value.getClass().isArray()) {
				if (value.toString().lastIndexOf("[") > 0) {
					throw new UnsupportedOperationException("The system only supports one-dimensional array, but currently array is multidimensional.");
				}

				for (Object item : (Object[]) value) {
					sb.append("<item>\n");
					sb.append(resolveResult(getPropertyMap(item)));
					sb.append("</item>\n");
				}
			} else {
				sb.append(resolveResult(getPropertyMap(value)));
			}
			sb.append("</").append(key).append(">\n");
		}
		return sb.toString();
	}

	/**
	 * 将对象属性值转换成 MAP
	 * 
	 * @param result
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getPropertyMap(Object o) throws Exception {
		if (o == null) {
			return Collections.EMPTY_MAP;
		} else if (Map.class.isInstance(o)) {
			return Map.class.cast(o);
		} else {
			Map propertyMap = new HashMap();
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(o);
			for (int i = 0; i < descriptors.length; i++) {
				String propertyName = descriptors[i].getName();
				if ("class".equals(propertyName)) {
					continue;
				}
				Object propertyValue = PropertyUtils.getProperty(o, propertyName);
				propertyMap.put(propertyName, propertyValue);
			}
			return propertyMap;
		}
	}
	
	/**
	 * 处理 XML 特殊字符
	 * 
	 * @param str
	 * @return String
	 */
	private String escape(String str) {
		if (str != null) {
			str = str.replaceAll("&", "&amp;");
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\"", "&quot;");
			str = str.replaceAll("'", "&apos;");
		}
		return str;
	}

	@Override
	protected String handleResult(com.yougou.ordercenter.vo.merchant.output.OutputDto result) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message></message>\n");
		sb.append("<item>\n");
		sb.append(resolveResult(getPropertyMap(result)));
		sb.append("</item>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}

	@Override
	protected String handleResult(com.yougou.ordercenter.vo.merchant.output.PageableOutputDto outputDto) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(getRootName()).append(">\n");
		sb.append("<code>").append(YOPBusinessCode.SUCCESS).append("</code>\n");
		sb.append("<message></message>\n");
		sb.append("<page_index>").append(outputDto.getPage_index()).append("</page_index>\n");
		sb.append("<page_size>").append(outputDto.getPage_size()).append("</page_size>\n");
		sb.append("<total_count>").append(outputDto.getTotal_count()).append("</total_count>\n");
		sb.append("<items>\n");

		for (Object item : outputDto.getItems()) {
			sb.append("<item>\n");
			sb.append(resolveResult(getPropertyMap(item)));
			sb.append("</item>\n");
		}

		sb.append("</items>\n");
		sb.append("</").append(getRootName()).append(">\n");
		return sb.toString();
	}
}
