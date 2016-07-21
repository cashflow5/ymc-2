package com.yougou.api.cfg;

public class FilterMapping {
	private String identifier;
	private Class<?> filterClass;
	private String filterRule;

	protected FilterMapping(Class<?> filterClass, String filterRule) {
		this.filterClass = filterClass;
		this.filterRule = filterRule;
	}

	protected FilterMapping(FilterMapping another) {
		this.identifier = another.identifier;
		this.filterClass = another.filterClass;
		this.filterRule = another.filterRule;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Class<?> getFilterClass() {
		return filterClass;
	}

	public String getFilterRule() {
		return filterRule;
	}
	
	public static class FilterMappingBuilder {
		
		private FilterMapping mapping;

		public FilterMappingBuilder(Class<?> filterClass, String filterRule) {
			this.mapping = new FilterMapping(filterClass, filterRule);
		}
		
		public FilterMappingBuilder identifier(String identifier) {
			this.mapping.identifier = identifier;
			return this;
		}
		
		public FilterMapping build() {
			return new FilterMapping(mapping);
		}
	}
}
