<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.apache.thrift.transport.*"%>
<%@ page import="org.apache.thrift.protocol.*"%>
<%@ page import="org.apache.thrift.protocol.*"%>
<%@ page import="com.yougou.fss.api.*"%>
<%@ page import="com.yougou.fss.api.vo.*"%>
<%
TTransport transport = null;
try {
	String hostStr = "10.10.20.38";
        	int port = 7788;
            System.out.println("thrift client connext server at "+port+" port ");  
            transport = new TSocket(hostStr,port);
            transport.open();    
            TProtocol protocol = new TBinaryProtocol(transport);    
            LayoutSettingThriftService.Client client = new LayoutSettingThriftService.Client(protocol);    
            
            out.println("layoutHtml................"+client.getLayoutSetting("99881613"));    
  
            out..println("thrift client close connextion");
} catch (Exception e) {
	e.printStackTrace();
	out.println("失败");
} finally {
	if (transport != null) {
		transport.close();
	}
}
%>