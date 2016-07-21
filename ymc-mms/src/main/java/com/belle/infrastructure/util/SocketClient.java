package com.belle.infrastructure.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public final class SocketClient {
	final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Logger log=Logger.getLogger(this.getClass());
	public SocketClient(List<String> hostList, int port, File transFile,boolean delFlag,String user,String pwd) {
		this.hostList = new ArrayList<String>(hostList);
		this.port = port;
		this.transFile = transFile;
		this.delFlag=delFlag;
		this.user=user;
		this.pwd=pwd;
	}

	public void start() {
		Thread publisher = new Thread() {
			public void run() {
				System.out.println(getDate(sdf)+" start publish task,host size:"+hostList.size()+",details:"+hostList);
				int count=0;
				int total=hostList.size();
		
				for (String host : hostList) {
					count++;
					log.info("publish start!host="+host);
					System.out.println(getDate(sdf)+" publish start!host="+host);

					Socket client = null;
					BufferedInputStream bis = null;
					DataOutputStream output = null;
					try {
						client = new Socket(host, port);
						output = new DataOutputStream(client.getOutputStream());
						
						/**
						 * 校验规则
						 * 读取第一个整数a，
						 * 读取第二个整数b,b的位取反,必须a=b
						 * 取接下来的一个字节,该字节的值代表接下来要忽略掉的字节数
						 * 忽略完字节后，读取字符串password
						 * 取接下来的一个字节,该字节的值代表接下来要忽略掉的字节数
						 * 忽略完字节后，读取字符串user
						 */
						Random  rand=new Random(System.currentTimeMillis());
						int a= rand.nextInt(0x7fffffff);
						int b=a^0xffffffff;
						
						output.writeInt(a);
						output.writeInt(b);
						
						byte c=(byte)(rand.nextInt(0x6f)+1);
						output.write(c);
						for(int i=0;i<c;i++)
						{
							byte dc=(byte)rand.nextInt(0x7f);
							output.write(dc);
						}
						byte[] d=pwd.getBytes("utf8");
						output.write((byte)d.length);
						for(byte bd:d)
						{
							output.write(bd);
						}
						
						
						c=(byte)(rand.nextInt(0x6f)+1);
						output.write(c);
						for(int i=0;i<c;i++)
						{
							byte dc=(byte)rand.nextInt(0x7f);
							output.write(dc);
						}
						
						d=user.getBytes("utf8");
						output.write((byte)d.length);
						for(byte bd:d)
						{
							output.write(bd);
						}
						
						bis = new BufferedInputStream(new FileInputStream(transFile));
						byte[] data = new byte[1024];
						int len = 0;
						while ((len = bis.read(data)) > 0) {
							output.write(data, 0, len);
						}
						log.info("publish finished!host="+host+",result:successfully");

					} catch (Exception e) {
						log.error("publish finished!host="+host+",result:failed,error="+e.getMessage());
						e.printStackTrace();
					} finally {
						if (null != client) {
							try {
								client.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						if (null != bis) {
							try {
								bis.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						if(null!=output){
							try {
								output.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}			
				}
				
				if(delFlag)
				{
					transFile.delete();
				}
			}
		};
		publisher.start();
	}
	private String getDate(SimpleDateFormat sdf)
	{
		return sdf.format(new Date());
	}
	public int getPort() {
		return port;
	}

	public List<String> getHostList() {
		return hostList;
	}

	public File getTransFile() {
		return transFile;
	}
	

	public boolean isDelFlag() {
		return delFlag;
	}


	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}


	private int port;
	private List<String> hostList;
	private File transFile;
	private boolean delFlag;
	private String user;
	private String pwd;
}
