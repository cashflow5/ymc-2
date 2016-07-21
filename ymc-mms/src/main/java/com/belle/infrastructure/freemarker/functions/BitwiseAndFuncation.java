package com.belle.infrastructure.freemarker.functions;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 位与运算函数 bitwise_and
 * 
 * @author 杨梦清 
 * @date Apr 16, 2012 7:50:16 PM
 */
public class BitwiseAndFuncation implements TemplateMethodModel {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments == null || arguments.size() != 2) {
			throw new TemplateModelException("invalid arguments of bitwise and operator");
		}
		
		return Long.parseLong(arguments.get(0).toString()) & Long.parseLong(arguments.get(1).toString());
	}

}
