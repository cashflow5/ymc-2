package com.belle.infrastructure.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FileFtpUtil {

	private FtpClient ftpclient;
	private String ipAddress;
	private int ipPort;
	private String userName;
	private String PassWord;

	/**
	 * 构造函数
	 * @param ip	String 机器IP
	 * @param port	String 机器FTP端口号
	 * @param username	String FTP用户名
	 * @param password	String FTP密码
	 * @throws Exception
	 */
	public FileFtpUtil(String ip, int port, String username, String password) throws Exception {
		ipAddress = new String(ip);
		ipPort = port;
		ftpclient = new FtpClient(ipAddress, ipPort);
		// ftpclient = new FtpClient(ipAddress);
		userName = new String(username);
		PassWord = new String(password);
	}

	/**
	 * 构造函数
	 * @param ip	String 机器IP，默认端口为21
	 * @param username	String FTP用户名
	 * @param password	String FTP密码
	 * @throws Exception
	 */
	public FileFtpUtil(String ip, String username, String password) throws Exception {
		ipAddress = new String(ip);
		ipPort = 21;
		ftpclient = new FtpClient(ipAddress, ipPort);
		// ftpclient = new FtpClient(ipAddress);
		userName = new String(username);
		PassWord = new String(password);
	}

	/**
	 * 登录FTP服务器
	 * @throws Exception
	 */
	public void login() throws Exception {
		ftpclient.login(userName, PassWord);
	}

	/**
	 * 退出FTP服务器
	 * @throws Exception
	 */
	public void logout() throws Exception {
		// 用ftpclient.closeServer()断开FTP出错时用下更语句退出
		ftpclient.sendServer("QUIT\r\n");
		int reply = ftpclient.readServerResponse(); // 取得服务器的返回信息

	}

	/**
	 * 在FTP服务器上建立指定的目录,当目录已经存在的情下不会影响目录下的文件,这样用以判断FTP
	 * 上传文件时保证目录的存在目录格式必须以"/"根目录开头
	 * @param pathList	String
	 * @throws Exception
	 */

	public void buildList(String pathList) throws Exception {
		ftpclient.ascii();
		StringTokenizer s = new StringTokenizer(pathList, "/"); // sign
		int count = s.countTokens();
		String pathName = "";
		while (s.hasMoreElements()) {
			pathName = pathName + "/" + (String) s.nextElement();
			try {
				ftpclient.sendServer("XMKD " + pathName + "\r\n");
			} catch (Exception ex) {
				ex = null;
			}
			int reply = ftpclient.readServerResponse();
		}
		ftpclient.binary();
	}

	/**
	 * 取得指定目录下的所有文件名，不包括目录名称 分析nameList得到的输入流中的数，得到指定目录下的所有文件名
	 * @param fullPath	String
	 * @return ArrayList
	 * @throws Exception
	 */

	public ArrayList<String> fileNames(String fullPath) throws Exception {
		ftpclient.ascii(); // 注意，使用字符模式
		TelnetInputStream list = ftpclient.nameList(fullPath);
		byte[] names = new byte[2048];
		int bufsize = 0;
		bufsize = list.read(names, 0, names.length); // 从流中读取
		list.close();
		ArrayList<String> namesList = new ArrayList<String>();
		int i = 0;
		int j = 0;
		while (i < bufsize) {
			if (names[i] == 10) { // 字符模式为10，二进制模式为13
				// 文件名在数据中开始下标为j,i-j为文件名的长度,文件名在数据中的结束下标为i-1
				String tempName = new String(names, j, i - j);
				namesList.add(tempName);
				j = i + 1; // 上一次位置字符模式
			}
			i = i + 1;
		}
		return namesList;

	}

	/**
	 * 上传文件到FTP服务器,destination路径以FTP服务器的"/"开始，带文件名、 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖
	 * @param source	String
	 * @param destination	String
	 * @throws Exception
	 */
	public void uploadFile(String source, String destination) throws Exception {
		buildList(destination.substring(0, destination.lastIndexOf("/")));
		ftpclient.binary(); // 此行代码必须放在buildList之后
		TelnetOutputStream ftpOut = ftpclient.put(destination);
		TelnetInputStream ftpIn = new TelnetInputStream(new FileInputStream(
		source), true);
		byte[] buf = new byte[204800];
		int bufsize = 0;
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			ftpOut.write(buf, 0, bufsize);
		}
		ftpIn.close();
		ftpOut.close();
	}

	/**
	 * JSP中的流上传到FTP服务器, 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖 字节数组做为文件的输入流,此方法适用于JSP中通过
	 * request输入流来直接上传文件在RequestUpload类中调用了此方法，destination路径以FTP服务器的"/"开始，带文件名
	 * @param sourceData	byte[]
	 * @param destination	String
	 * @throws Exception
	 */
	public void uploadFile(byte[] sourceData, String destination) throws Exception {
		buildList(destination.substring(0, destination.lastIndexOf("/")));
		ftpclient.binary(); // 此行代码必须放在buildList之后
		TelnetOutputStream ftpOut = ftpclient.put(destination);
		ftpOut.write(sourceData, 0, sourceData.length);
		// ftpOut.flush();
		ftpOut.close();
	}

	/**
	 * 从FTP文件服务器上下载文件SourceFileName，到本地destinationFileName 所有的文件名中都要求包括完整的路径名在内
	 * @param SourceFileName	String
	 * @param destinationFileName	String
	 * @throws Exception
	 */
	public void downFile(String SourceFileName, String destinationFileName)	throws Exception {
		ftpclient.binary(); // 一定要使用二进制模式
		TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
		byte[] buf = new byte[204800];
		int bufsize = 0;
		FileOutputStream ftpOut = new FileOutputStream(destinationFileName);
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			ftpOut.write(buf, 0, bufsize);
		}
		ftpOut.close();
		ftpIn.close();
	}

	/**
	 * 从FTP文件服务器上下载文件，输出到字节数组中
	 * @param SourceFileName	String
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] downFile(String SourceFileName) throws Exception {
		ftpclient.binary(); // 一定要使用二进制模式
		TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		byte[] buf = new byte[204800];
		int bufsize = 0;
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			byteOut.write(buf, 0, bufsize);
		}
		byte[] return_arraybyte = byteOut.toByteArray();
		byteOut.close();
		ftpIn.close();
		return return_arraybyte;
	}
	
	/**
	 * 从FTP文件服务器上下载文件，输出到字节数组中
	 * @param SourceFileName	String
	 * @return byte[]
	 * @throws Exception
	 */
	public void downRemoteFile(String SourceFile,OutputStream outputStream) throws Exception {
		ftpclient.binary(); // 一定要使用二进制模式
		TelnetInputStream ftpIn = ftpclient.get(SourceFile);
		byte[] buf = new byte[204800];
		int bufsize = 0;
		//FileOutputStream ftpOut = new FileOutputStream(destinationFileName);
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			outputStream.write(buf, 0, bufsize);
		}
		
		ftpIn.close();
	}

	/**
	 * @param args	String[]
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FileFtpUtil fUp = new FileFtpUtil("183.62.107.241", 9999, "mobilepic", "CsWaDCw!#$()D");
		fUp.login();

//		FileInputStream fin = new FileInputStream("d:\\my.txt");
//		byte[] data = new byte[20480000];
//		fin.read(data, 0, data.length);
//		fUp.upFile(data, "/contract/my.txt");
//		fUp.logout();
		fUp.downFile("/contract/my.txt", "d://my2.txt");
		fUp.logout();
		System.out.println("程序运行完成！");

		/*
		 * 在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n
		 * ftpclient.sendServer("XMKD /test/bb\r\n"); //执行服务器上的FTP命令
		 * ftpclient.readServerResponse一定要在sendServer后调用
		 * nameList("/test")获取指目录下的文件列表XMKD建立目录，当目录存在的情况下再次创建目录时报错XRMD删除目录
		 * DELE删除文件
		 */

	}

}
