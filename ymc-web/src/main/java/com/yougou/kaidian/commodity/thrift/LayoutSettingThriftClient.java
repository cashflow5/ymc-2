package com.yougou.kaidian.commodity.thrift;

import java.io.IOException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class LayoutSettingThriftClient {

	public void startClient() throws IOException {    
        TTransport transport;    
        try {    
        	/*Properties prop = SpringContextHolder.getBean("configProperties");
        	System.out.println("======================");
			String portStr = prop.getProperty("layoutSetting.thrift.port");
			String hostStr = prop.getProperty("layoutSetting.thrift.host");
			int port = Integer.valueOf( portStr == null ? "7788":portStr );
			hostStr = (hostStr == null ? "10.0.20.142" : hostStr);*/
        	String hostStr = "10.0.30.164";
        	int port = 7788;
            System.out.println("thrift client connext server at "+port+" port ");  
            transport = new TSocket(hostStr,port);
            transport.open();    
            TProtocol protocol = new TBinaryProtocol(transport);    
            LayoutSettingThriftService.Client client = new LayoutSettingThriftService.Client(protocol);    
            
            System.out.println("layoutHtml................"+client.getLayoutSetting("99881613"));    
            transport.close();    
            System.out.println("thrift client close connextion");  
        } catch (TTransportException e) {    
            e.printStackTrace();    
        } catch (TException e) {    
            e.printStackTrace();    
        }    
    }    
    
    public static void main(String[] args) throws IOException {    
        System.out.println("thrift client init ");  
        LayoutSettingThriftClient client = new LayoutSettingThriftClient();    
        System.out.println("thrift client start ");  
        client.startClient();
        System.out.println("thrift client end ");  
    }    
}
