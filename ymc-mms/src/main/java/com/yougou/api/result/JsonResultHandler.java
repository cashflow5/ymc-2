package com.yougou.api.result;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.util.JsonBeanUtils;
import com.yougou.dto.output.OutputDto;
import com.yougou.dto.output.PageableOutputDto;

/**
 * JSON 类型返回结果处理类
 * 
 * @author 杨梦清
 * 
 */
public class JsonResultHandler extends AbstractResultHandler {

	public JsonResultHandler(HttpServletResponse response, String rootName) {
		super(response, rootName);
	}

	@Override
	protected String getNullResult() {
		JSONObject root = new JSONObject();
		root.accumulate(getRootName(), null);
		return root.toString();
	}
	
	@Override
	protected String handleResult(Collection<OutputDto> collection) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("total_count", collection.size());
		branch.accumulate("items", JSONArray.fromObject(collection, JsonBeanUtils.getDateTimeJsonValueProcessorConfig()));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(PageableOutputDto outputDto) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("page_index", outputDto.getPage_index());
		branch.accumulate("page_size", outputDto.getPage_size());
		branch.accumulate("total_count", outputDto.getTotal_count());
		branch.accumulate("items", JSONArray.fromObject(outputDto.getItems(), JsonBeanUtils.getDateTimeJsonValueProcessorConfig()));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(OutputDto outputDto) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("item", JSONObject.fromObject(outputDto, JsonBeanUtils.getDateTimeJsonValueProcessorConfig()));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(Exception exception) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
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
		
		branch.accumulate("code", outCode);
		branch.accumulate("message", outMessage);
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(Boolean bool) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", bool ? YOPBusinessCode.SUCCESS : YOPBusinessCode.ERROR);
		branch.accumulate("message", bool);
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(Date date) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("item", new JSONObject().accumulate("date", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss")));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	/**
	 * 仅用于订单
	 */
	@Override
	protected String handleResult(com.yougou.ordercenter.vo.merchant.output.OutputDto outputDto) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("item", JSONObject.fromObject(outputDto, JsonBeanUtils.getDateTimeJsonValueProcessorConfig()));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}

	@Override
	protected String handleResult(com.yougou.ordercenter.vo.merchant.output.PageableOutputDto outputDto) throws Exception {
		JSONObject root = new JSONObject();
		JSONObject branch = new JSONObject();
		branch.accumulate("code", YOPBusinessCode.SUCCESS);
		branch.accumulate("message", "");
		branch.accumulate("page_index", outputDto.getPage_index());
		branch.accumulate("page_size", outputDto.getPage_size());
		branch.accumulate("total_count", outputDto.getTotal_count());
		branch.accumulate("items", JSONArray.fromObject(outputDto.getItems(), JsonBeanUtils.getDateTimeJsonValueProcessorConfig()));
		root.accumulate(getRootName(), branch);
		return root.toString();
	}
}
