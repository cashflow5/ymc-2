package com.belle.infrastructure.util;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTool {
	
	public static Object projection(Object o) throws Exception{
  
        Class<?> classType=o.getClass();

        Object obj=classType.getConstructor(new Class[]{}).newInstance(new Object[]{});

        Field[] fields=classType.getDeclaredFields();


        for(int i=0;i<fields.length;i++){
            String fieldName=fields[i].getName();

            String getMethodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
 
            String setMethodName="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

            Method getMethod=classType.getMethod(getMethodName, new Class[]{});

            Method setMethod=classType.getMethod(setMethodName, new Class[]{fields[i].getType()});

            Object value=getMethod.invoke(o, new Object[]{});

            setMethod.invoke(obj, new Object[]{value});
        }
        
//        Method privateMethod=classType.getDeclaredMethod("print", new Class[]{});
//        // 调用private方法的关键一句话
//        privateMethod.setAccessible(true);
//        // 调用私有方法
//        privateMethod.invoke(o, new Object[]{});
  
        return obj;
	}
}
