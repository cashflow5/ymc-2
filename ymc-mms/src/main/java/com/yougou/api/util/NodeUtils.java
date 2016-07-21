package com.yougou.api.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * 
 * @author 杨梦清
 * 
 */
public class NodeUtils {

	/**
	 * 将 XML 节点转换成 XML 字符串
	 * 
	 * @param node
	 * @return String
	 * @throws Exception
	 */
	public static String toXML(Node node) throws Exception {
		StringWriter stringWriter = new StringWriter();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.transform(new DOMSource(node), new StreamResult(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 将 XML 字符串转换成 XML 节点
	 * 
	 * @param xml
	 * @return Node
	 * @throws Exception
	 */
	public static Node toNode(String xml) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.parse(is);
	}
}
