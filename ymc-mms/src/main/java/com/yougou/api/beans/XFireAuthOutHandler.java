package com.yougou.api.beans;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;
import org.jdom.Namespace;

public class XFireAuthOutHandler extends AbstractHandler {

	private static final Namespace NAMESPACE = Namespace.getNamespace("yougou", "http://api.yougou.com/ws");
	private String identifier;
	private String token;

	public XFireAuthOutHandler(String token, String identifier) {
		this.token = token;
		this.identifier = identifier;
	}

	public void invoke(MessageContext context) throws Exception {
		Element header = new Element("header", NAMESPACE);
		Element authToken = new Element("AuthToken", NAMESPACE);
		Element token1 = new Element("Token", NAMESPACE);
		token1.addContent(this.token);
		Element token2 = new Element("WarehouseId", NAMESPACE);
		token2.addContent(this.identifier);
		authToken.addContent(token1);
		authToken.addContent(token2);
		header.addContent(authToken);
		context.getCurrentMessage().setHeader(header);
	}

}
