package com.yougou.kaidian.commodity.thrift;

import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.server.TThreadPoolServer.Args;
import com.yougou.tools.common.utils.SpringContextHolder;

public class LayoutSettingThriftServer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SETTING_PORT = "7788";
	
	
	@Override
	public void init() throws ServletException {
		new Thread(new Runnable(){
			@Override
			public void run() {
				thrift();
			}
		}).start();
		super.init();
	}


	private void thrift() {
		try {
			Properties prop = SpringContextHolder.getBean("configProperties");
			String portStr = prop.getProperty("layoutSetting.thrift.port");
			//System.out.println("-----port:"+portStr);
			int port = Integer.valueOf( portStr == null ? SETTING_PORT:portStr );
			//使用堵塞式I/O进行传输，也是最常见的模式
			TServerSocket serverTransport = new TServerSocket(port);
			TProcessor processor = new LayoutSettingThriftService.Processor<LayoutSettingThriftService.Iface>(new LayoutSettingThriftServiceImpl());
			//二进制编码格式进行数据传输
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			System.out.println("server open ....."+port);
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(protFactory);
			// 多线程服务器端使用标准的堵塞式I/O
			TServer server = new TThreadPoolServer(args);
			server.serve();  
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
