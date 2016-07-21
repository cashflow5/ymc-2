package com.yougou.api.cfg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yougou.api.beans.AppType;

public class ImplementorMapping {

	// API 信息
	private String apiId;
	private Method apiMethod;
	private String apiVersionNo;
	private AppType ownership;
	private Long apiWeight;
	
	// API 输入输出参数信息
	private Map<String, String> inputParams;
	private Set<String> outputParams;
	
	// API 实现者信息
	private String identifier;
	private Class<?> implementorClass;
	private String isSpringManagedInstance;

	// 校验器信息,拦截器信息
	private List<ValidatorMapping> validators;
	private List<InterceptorMapping> interceptors;

	protected ImplementorMapping(String apiId, Method apiMethod, String apiVersionNo, AppType ownership, Long apiWeight) {
		this.apiId = apiId;
		this.apiMethod = apiMethod;
		this.apiVersionNo = apiVersionNo;
		this.ownership = ownership;
		this.apiWeight = apiWeight;
		this.inputParams = new HashMap<String, String>();
		this.outputParams = new HashSet<String>();
		this.validators = new ArrayList<ValidatorMapping>();
		this.interceptors = new ArrayList<InterceptorMapping>();
	}

	protected ImplementorMapping(ImplementorMapping another) {
		this.apiId = another.apiId;
		this.apiMethod = another.apiMethod;
		this.apiVersionNo = another.apiVersionNo;
		this.ownership = another.ownership;
		this.apiWeight = another.apiWeight;
		this.identifier = another.identifier;
		this.implementorClass = another.implementorClass;
		this.isSpringManagedInstance = another.isSpringManagedInstance;
		this.inputParams = new HashMap<String, String>(another.inputParams);
		this.outputParams = new HashSet<String>(another.outputParams);
		this.validators = new ArrayList<ValidatorMapping>(another.validators);
		this.interceptors = new ArrayList<InterceptorMapping>(another.interceptors);
	}
	
	public String getApiId() {
		return apiId;
	}

	public Method getApiMethod() {
		return apiMethod;
	}
	
	public String getApiVersionNo() {
		return apiVersionNo;
	}

	public AppType getOwnership() {
		return ownership;
	}

	public String getIsSpringManagedInstance() {
		return isSpringManagedInstance;
	}

	public Long getApiWeight() {
		return apiWeight;
	}

	public Map<String, String> getInputParams() {
		return inputParams;
	}

	public Set<String> getOutputParams() {
		return outputParams;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Class<?> getImplementorClass() {
		return implementorClass;
	}

	public boolean isSpringManagedInstance() {
		return "Y".equals(isSpringManagedInstance);
	}

	public List<ValidatorMapping> getValidators() {
		return validators;
	}

	public List<InterceptorMapping> getInterceptors() {
		return interceptors;
	}

	public static class ValidatorMapping {

		private String identifier;
		private Class<?> validatorClass;
		private String fieldName;
		private String fieldDataType;
	    private String messagePattern;
	    private String messageKey;
		private String expression;
		private String caseSensitive;
		private String trim;
		private String minValue;
		private String maxValue;
		private Integer minLength;
		private Integer maxLength;

		protected ValidatorMapping(String identifier, Class<?> validatorClass, String fieldName, String fieldDataType, String messagePattern) {
			this.identifier = identifier;
			this.validatorClass = validatorClass;
			this.fieldName = fieldName;
			this.fieldDataType = fieldDataType;
			this.messagePattern = messagePattern;
		}

		protected ValidatorMapping(ValidatorMapping another) {
			this.identifier = another.identifier;
			this.validatorClass = another.validatorClass;
			this.fieldName = another.fieldName;
			this.fieldDataType = another.fieldDataType;
			this.messagePattern = another.messagePattern;
			this.messageKey = another.messageKey;
			this.expression = another.expression;
			this.caseSensitive = another.caseSensitive;
			this.trim = another.trim;
			this.minValue = another.minValue;
			this.maxValue = another.maxValue;
			this.minLength = another.minLength;
			this.maxLength = another.maxLength;
		}

		public String getIdentifier() {
			return identifier;
		}

		public Class<?> getValidatorClass() {
			return validatorClass;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getFieldDataType() {
			return fieldDataType;
		}

		public String getMessagePattern() {
			return messagePattern;
		}

		public void setMessagePattern(String messagePattern) {
			this.messagePattern = messagePattern;
		}

		public String getMessageKey() {
			return messageKey;
		}

		public void setMessageKey(String messageKey) {
			this.messageKey = messageKey;
		}

		public String getExpression() {
			return expression;
		}

		public boolean isCaseSensitive() {
			return "Y".equals(caseSensitive);
		}

		public boolean isTrim() {
			return "Y".equals(trim);
		}

		public String getMinValue() {
			return minValue;
		}

		public String getMaxValue() {
			return maxValue;
		}

		public Integer getMinLength() {
			return minLength;
		}

		public Integer getMaxLength() {
			return maxLength;
		}
	}

	public static class InterceptorMapping {

		private String identifier;
		private Class<?> interceptorClass;
		
		protected InterceptorMapping(String identifier, Class<?> interceptorClass) {
			this.identifier = identifier;
			this.interceptorClass = interceptorClass;
		}
		
		protected InterceptorMapping(InterceptorMapping another) {
			this.identifier = another.identifier;
			this.interceptorClass = another.interceptorClass;
		}

		public String getIdentifier() {
			return identifier;
		}

		public Class<?> getInterceptorClass() {
			return interceptorClass;
		}
	}

	public static class ImplementorMappingBuilder {

		private ImplementorMapping mapping;

		public ImplementorMappingBuilder(String apiId, Method apiMethod, String apiVersionNo, AppType ownership, Long apiWeight) {
			this.mapping = new ImplementorMapping(apiId, apiMethod, apiVersionNo, ownership, apiWeight);
		}
		
		public ImplementorMappingBuilder identifier(String identifier) {
			this.mapping.identifier = identifier;
			return this;
		}

		public ImplementorMappingBuilder implementorClass(Class<?> implementorClass) {
			this.mapping.implementorClass = implementorClass;
			return this;
		}

		public ImplementorMappingBuilder isSpringManagedInstance(String isSpringManagedInstance) {
			this.mapping.isSpringManagedInstance = isSpringManagedInstance;
			return this;
		}
		
		public ImplementorMappingBuilder addInputParam(String inputParam, String defaultValue) {
			this.mapping.inputParams.put(inputParam, defaultValue);
			return this;
		}
		
		public ImplementorMappingBuilder addInputParam(Map<String, String> inputParams) {
			this.mapping.inputParams.putAll(inputParams);
			return this;
		}
		
		public ImplementorMappingBuilder addOutputParam(String outputParam) {
			this.mapping.outputParams.add(outputParam);
			return this;
		}
		
		public ImplementorMappingBuilder addOutputParam(Collection<String> outputParams) {
			this.mapping.outputParams.addAll(outputParams);
			return this;
		}
		
		public ImplementorMappingBuilder addValidatorMapping(ValidatorMapping mapping) {
			this.mapping.validators.add(mapping);
			return this;
		}
		
		public ImplementorMappingBuilder addValidatorMapping(Collection<ValidatorMapping> mappings) {
			this.mapping.validators.addAll(mappings);
			return this;
		}
		
		public ImplementorMappingBuilder addInterceptorMapping(InterceptorMapping mapping) {
			this.mapping.interceptors.add(mapping);
			return this;
		}
		
		public ImplementorMappingBuilder addInterceptorMapping(Collection<InterceptorMapping> mappings) {
			this.mapping.interceptors.addAll(mappings);
			return this;
		}

		public ImplementorMapping build() {
			mapping.validators = Collections.unmodifiableList(mapping.validators);
			mapping.interceptors = Collections.unmodifiableList(mapping.interceptors);
			return new ImplementorMapping(mapping);
		}
	}
	
	public static class ValidatorMappingBuilder {
		
		private ValidatorMapping mapping;

		public ValidatorMappingBuilder(String identifier, Class<?> validatorClass, String fieldName, String fieldDataType, String messagePattern) {
			this.mapping = new ValidatorMapping(identifier, validatorClass, fieldName, fieldDataType, messagePattern);
		}
		
		public ValidatorMappingBuilder messageKey(String messageKey) {
			this.mapping.messageKey = messageKey;
			return this;
		}
		
		public ValidatorMappingBuilder expression(String expression) {
			this.mapping.expression = expression;
			return this;
		}

		public ValidatorMappingBuilder caseSensitive(String caseSensitive) {
			this.mapping.caseSensitive = caseSensitive;
			return this;
		}

		public ValidatorMappingBuilder trim(String trim) {
			this.mapping.trim = trim;
			return this;
		}

		public ValidatorMappingBuilder minValue(String minValue) {
			this.mapping.minValue = minValue;
			return this;
		}

		public ValidatorMappingBuilder maxValue(String maxValue) {
			this.mapping.maxValue = maxValue;
			return this;
		}

		public ValidatorMappingBuilder minLength(Integer minLength) {
			this.mapping.minLength = minLength;
			return this;
		}

		public ValidatorMappingBuilder maxLength(Integer maxLength) {
			this.mapping.maxLength = maxLength;
			return this;
		}
		
		public ValidatorMapping build() {
			return new ValidatorMapping(mapping);
		}
	}
	
	public static class InterceptorMappingBuilder {
		
		private InterceptorMapping mapping;

		public InterceptorMappingBuilder(String identifier, Class<?> interceptorClass) {
			this.mapping = new InterceptorMapping(identifier, interceptorClass);
		}
		
		public InterceptorMapping build() {
			return new InterceptorMapping(mapping);
		}
	}
}
