package com.belle.infrastructure.util;

import java.lang.reflect.Field;
import java.util.Collection;

public class ObjectEncodeUtil {

	public static Collection<?> convertCollection(Collection<?> cs) throws Exception {

		Object[] ls = cs.toArray();
		int size = ls.length;

		if (0 == size)
			return cs;
		for (int k = 0; k < size; k++) {
			Object o = ls[k];

			convertObject(o);
		}

		return cs;
	}

	public static Object convertObject(Object o) throws Exception {

		if (null == o)
			return o;

		Class<?> classType = o.getClass();
		Field[] fields = classType.getDeclaredFields();

		int length = fields.length;

		for (int i = 0; i < length; i++) {

			String fieldName = fields[i].getName();
			Class<?> clazzType = fields[i].getType();
			Object object = ReflectionUtils.getFieldValue(o, fieldName);
			
			if (!(object instanceof Collection) && clazzType.getName().equals("java.lang.String")){
				if(object == null) continue;
				
				String str =  new String(((String)object).getBytes("iso-8859-1"),"utf-8");
				ReflectionUtils.setFieldValueByFieldType(o, fieldName,str);
				
			}else if(!(object instanceof Collection) && clazzType.getName().startsWith("java.lang")){
				convertObject(object);
			}
			
			if (object instanceof Collection) {
				convertCollection((Collection<?>) object);
			}

		}
		return o;
	}
	
}
