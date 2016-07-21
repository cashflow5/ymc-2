package com.belle.infrastructure.json;

import java.util.Collection;

public interface IJsonTool {

	public StringBuffer convertObj2jason(Collection<?> cs);
	
	public StringBuffer convertObj2jason(Object o);
}
