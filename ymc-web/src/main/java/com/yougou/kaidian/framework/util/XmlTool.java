package com.yougou.kaidian.framework.util;  

import java.io.File;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XmlTool {
	//获取根节点
	public static Element getRootElement(Document doc){
		if(doc!=null){
			return doc.getRootElement();
		}
		return null;
	}
	
	//获取节点属性值
	public static String getAttributeVal(Element element,String attr){
		Attribute attribute = element.attribute(attr);
		return attribute.getText();
	}

	public static Document createDocument(String path) throws Exception {
		SAXReader reader = new SAXReader();
 		return reader.read(new File(path));
	}
	
}
