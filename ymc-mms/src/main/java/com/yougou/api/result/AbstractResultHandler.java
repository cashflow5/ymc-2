package com.yougou.api.result;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.dto.output.OutputDto;
import com.yougou.dto.output.PageableOutputDto;

public abstract class AbstractResultHandler implements ResultHandler {

	private static final String CONTEXT_TYPE_JSON = "application/json; charset=UTF-8";
	private static final String CONTEXT_TYPE_XML = "application/xml; charset=UTF-8";

	private String rootName;
	private HttpServletResponse response;

	public AbstractResultHandler(HttpServletResponse response, String rootName) {
		this.rootName = initRootName(rootName);
		this.response = response;
	}

	private String initRootName(String rootName) {
		if (StringUtils.isBlank(rootName) || NumberUtils.isNumber(rootName)) {
			rootName = "error_response";
		} else {
			rootName = rootName.replace('.', '_') + "_response";
		}
		return rootName;
	}

	public String getRootName() {
		return rootName;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeResult(Object obj) {
		try {
			if (InputStream.class.isInstance(obj)) {
				response.getOutputStream().write(IOUtils.toByteArray((InputStream) obj));
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} else {
				String str = null;
				// 处理响应内容
				if (obj == null) {
					str = getNullResult();
				} else if (Collection.class.isInstance(obj)) {
					str = handleResult((Collection) obj);
				} else if (Exception.class.isInstance(obj)) {
					str = handleResult((Exception) obj);
				} else if (PageableOutputDto.class.isInstance(obj)) {
					str = handleResult((PageableOutputDto) obj);
				} else if (OutputDto.class.isInstance(obj)) {
					str = handleResult((OutputDto) obj);
				} 
				// 兼容订单接口返回
				else if (com.yougou.ordercenter.vo.merchant.output.PageableOutputDto.class.isInstance(obj)) {
					str = handleResult((com.yougou.ordercenter.vo.merchant.output.PageableOutputDto) obj);
				}
				else if (com.yougou.ordercenter.vo.merchant.output.OutputDto.class.isInstance(obj)) {
					str = handleResult((com.yougou.ordercenter.vo.merchant.output.OutputDto) obj);
				} 
				// order end
				else if (Boolean.class.isInstance(obj)) {
					str = handleResult((Boolean) obj);
				} else if (Date.class.isInstance(obj)) {
					str = handleResult((Date) obj);
				} else {
					str = handleResult((Exception) null);
				}
				// 设置响应类型
				response.setContentType(getContentType());
				// 输出响应内容
				response.getWriter().print(str);
				response.getWriter().flush();
			}
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.ERROR, ex.getMessage(), ex);
		}
	}

	@Override
	public String getContentType() {
		return JsonResultHandler.class.isInstance(this) ? CONTEXT_TYPE_JSON : CONTEXT_TYPE_XML;
	}

	/**
	 * 判断是否为基础数据类型
	 * 
	 * @param o
	 * @return boolean
	 */
	protected boolean isPrimitiveInstance(Object o) {
		return (o == null) || (o instanceof String) || (o instanceof Number) || (o instanceof Boolean) || (o instanceof Date) || (o instanceof Character) || (o instanceof Enum);
	}
	
	/**
	 * 空结果
	 * 
	 * @return String
	 */
	protected abstract String getNullResult();

	protected abstract String handleResult(Collection<OutputDto> collection) throws Exception;

	protected abstract String handleResult(PageableOutputDto pageFinder) throws Exception;

	protected abstract String handleResult(OutputDto result) throws Exception;

	protected abstract String handleResult(Exception exception) throws Exception;

	protected abstract String handleResult(Boolean bool) throws Exception;

	protected abstract String handleResult(Date date) throws Exception;
	
	//仅用于订单接口返回解析
	protected abstract String handleResult(com.yougou.ordercenter.vo.merchant.output.PageableOutputDto pageFinder) throws Exception;
	protected abstract String handleResult(com.yougou.ordercenter.vo.merchant.output.OutputDto result) throws Exception;
}
