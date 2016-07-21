package com.yougou.api.beans;

public class XWSDefinition {

	public XWSDefinition() {
	}

	private String serviceName;
	private String servicePort;
	private String serviceTimeout;
	private String serviceLocation;
	private String serviceMethod;
	private String serviceNamespace;
	private String serviceIdentifier;
	private String serviceToken;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePort() {
		return servicePort;
	}

	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}

	public String getServiceTimeout() {
		return serviceTimeout;
	}

	public void setServiceTimeout(String serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
	}

	public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getServiceNamespace() {
		return serviceNamespace;
	}

	public void setServiceNamespace(String serviceNamespace) {
		this.serviceNamespace = serviceNamespace;
	}

	public String getServiceIdentifier() {
		return serviceIdentifier;
	}

	public void setServiceIdentifier(String serviceIdentifier) {
		this.serviceIdentifier = serviceIdentifier;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

}
