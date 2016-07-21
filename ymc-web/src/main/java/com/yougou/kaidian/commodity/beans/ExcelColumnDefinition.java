package com.yougou.kaidian.commodity.beans;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字段定义
 * 
 * @author yang.mq
 *
 */
public class ExcelColumnDefinition {
	private String identifier;
	private String readFieldName;
	private String writFieldName;
	private Pattern validator;
	private boolean constField = false;
	private boolean required = true;
	private List<ExcelColumnDefinition> dependencies = Collections.<ExcelColumnDefinition>emptyList();

	private ExcelColumnDefinition() {
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public String getReadFieldName() {
		return readFieldName;
	}

	public String getWritFieldName() {
		return writFieldName;
	}

	public boolean isConstField() {
		return constField;
	}

	public boolean isRequired() {
		return required;
	}

	public Pattern getValidator() {
		return validator;
	}

	public List<ExcelColumnDefinition> getDependencies() {
		return dependencies;
	}
	
	public static class ColumnDefinitionBuilder {

		private ExcelColumnDefinition columnDefinition;
		
		public ColumnDefinitionBuilder() {
			columnDefinition = new ExcelColumnDefinition();
		}
		
		public static ColumnDefinitionBuilder start() {
			return new ColumnDefinitionBuilder();
		}
		
		public ColumnDefinitionBuilder identifier(String identifier) {
			this.columnDefinition.identifier = identifier;
			return this;
		}

		public ColumnDefinitionBuilder readFieldName(String readFieldName) {
			this.columnDefinition.readFieldName = readFieldName;
			return this;
		}
		
		public ColumnDefinitionBuilder writFieldName(String writFieldName) {
			this.columnDefinition.writFieldName = writFieldName;
			return this;
		}
		
		public ColumnDefinitionBuilder validator(Pattern validator) {
			this.columnDefinition.validator = validator;
			return this;
		}

		public ColumnDefinitionBuilder constField(boolean constField) {
			this.columnDefinition.constField = constField;
			return this;
		}
		
		public ColumnDefinitionBuilder required(boolean required) {
			this.columnDefinition.required = required;
			return this;
		}
		
		public ColumnDefinitionBuilder dependencies(List<ExcelColumnDefinition> dependencies) {
			this.columnDefinition.dependencies = dependencies;
			return this;
		}
		
		public ExcelColumnDefinition get() {
			return columnDefinition;
		}
	}
}
