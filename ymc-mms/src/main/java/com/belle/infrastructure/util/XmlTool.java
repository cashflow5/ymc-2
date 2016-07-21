package com.belle.infrastructure.util;  

import java.io.File;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;


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
	
	public static Node findElementByProperty(Document doc, String property,String proVal){
		return doc.selectSingleNode("/menus/menu[@"+property+"='"+proVal+"']");
	}
	
	public static void main(String[] args) throws Exception {
		Document document = XmlTool.createDocument(XmlTool.class
 				.getClassLoader().getResource("authority.xml").getPath());
		Element ele = getRootElement(document);
		System.out.println(ele.elements().size());
		DefaultElement node = (DefaultElement)findElementByProperty(document,"url","picture/list.sc");
		System.out.println(node.elements().size());
	}
}
