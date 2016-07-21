package com.belle.infrastructure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {

	private FTPClient ftpClient;
	
	/**   OK
	 * 			连接服务器
	 * @param url 			服务器url
	 * @param port			端口号 
	 * @param user  		用户名
	 * @param password 		密码
	 * @param path			服务器上的路径
	 */
	public void connectServer(String url, int port, String username,
			String password, String path) {

		try {
			ftpClient = new FTPClient();
			ftpClient.connect(url, port);
			ftpClient.login(username, password); 
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setConnectTimeout(10000);
			
			// 看返回的值是不是230，如果是，表示登陆成功
			int reply = ftpClient.getReplyCode();
			
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				closeConnect();
				System.out.println("FTP server refused connection.");
			} else{
				System.out.println("Connected to " + url + ": success!");
			}
			
			if (path.length() != 0)
				ftpClient.changeWorkingDirectory(path);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 关闭服务器  OK
	 * @throws IOException 
	 */
	public void closeConnect(){
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
				System.out.println("连接关闭 !");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取ftp远程文件流行    OK 
	 * @param remoteFile	远程文件名称  (test2.txt";)
	 * @return
	 * @throws IOException
	 */
	public InputStream getFtpInputStream(String remoteFile) throws IOException{
		ftpClient.enterLocalPassiveMode();
		InputStream inputStream =  ftpClient.retrieveFileStream(remoteFile);
		return inputStream;
	}
	
	/**
	 * ftp 远程上传
	 * @param localFile   	本地文件路径 ("D://1.txt";)
	 * @param remoteFile	远程文件名称  (test2.txt";)
	 * @throws IOException
	 */
	public boolean upload(String localFile, String remoteFile) throws IOException {
		
		boolean uploadRes = false;
		FileInputStream in = new FileInputStream(localFile);
		uploadRes = ftpClient.storeFile(remoteFile, in);
		
		//关闭文件流
		if(null != in){
			in.close();
			in = null;
		}
		System.out.println("上传成功 !");
		
		return uploadRes;
	}
	
	public boolean upload(File localFile, String remoteFile) throws IOException {
		
		boolean uploadRes = false;
		FileInputStream in = new FileInputStream(localFile.getName());
		uploadRes = ftpClient.storeFile(remoteFile, in);
		System.out.println("上传成功 !");
		return uploadRes;
	}
	
	public boolean upload(InputStream in, String remoteFile) throws IOException {
		boolean uploadRes = false;
		ftpClient.enterLocalPassiveMode();
		uploadRes = ftpClient.storeFile(remoteFile, in);
		System.out.println("上传成功 !");
		return uploadRes;
	}
	
	public boolean uploadContract(InputStream in, String remoteFile) throws IOException {
		boolean uploadRes = false;
		ftpClient.enterLocalPassiveMode();
		ftpClient.setControlEncoding("UTF-8");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);     
		conf.setServerLanguageCode("zh");   
		ftpClient.configure(conf);  
		uploadRes = ftpClient.storeFile(remoteFile, in);
		System.out.println("上传成功 !");
		return uploadRes;
	}
	
	public boolean uploadToDir(String localFile, String remoteDir) throws IOException {
		
		boolean uploadRes = false;
		File file = new File(localFile);
		FileInputStream in = new FileInputStream(file);
		uploadRes = ftpClient.storeFile(remoteDir+File.separator+file.getName(), in);
		System.out.println("上传成功 !");
		return uploadRes;
	}
	
	
	public void uploadFiles(String localDir, String remoteDir ) throws IOException {
		File file = new File(localDir);
		if (file.exists()) {
			if (file.isFile()) {
				uploadToDir(file.getAbsolutePath(), remoteDir);
			} else if (file.isDirectory()) {
				String currentRemoteDir = remoteDir + File.separator + file.getName();
				createDir(currentRemoteDir);
				File[] files = file.listFiles();
				for (File f : files) {
					uploadFiles(f.getAbsolutePath(), currentRemoteDir);
				}
			}
		} else {
			System.out.println("文件路径不存在!");
		}
	}
	
	
	public void createDir(String dirPath) {
		StringTokenizer token = new StringTokenizer(dirPath, "/");
		token.countTokens();
		String pathName = "";
		while (token.hasMoreElements()) {
			String dirName = (String) token.nextElement();
			pathName = pathName + "/" + dirName;
			try {
				ftpClient.mkd(pathName);// 创建文件夹
				ftpClient.changeWorkingDirectory(pathName);// 移动命令指针
			} catch (Exception e) {
				System.out.println("createDir ---" + dirPath + "---" + e);
			}
		}
	}

//	/**
//	 * 下载ftp远程文件	
//	 * @param remotePath			远程文件目录
//	 * @param remoteFile			远程文件名称  (test2.txt";)
//	 * @param localFile				本地文件路径 ("D://1.txt";)
//	 * @throws IOException	
//	 */
//	public void download(String remotePath, String remoteFile, String localFile) throws IOException {
//
//		if (remotePath.length() != 0)
//			ftpClient.changeWorkingDirectory(remoteFile);
//		
//		InputStream is = getFtpInputStream(remoteFile);
//		java.io.File file_in = new java.io.File(localFile);
//		FileOutputStream os = new FileOutputStream(file_in);
//		byte[] bytes = new byte[1024];
//		int c;
//		while ((c = is.read(bytes)) != -1) {
//			os.write(bytes, 0, c);
//		}
//
//		os.close();
//		is.close();
//	}

	
	/**
	 * 下载 到本地文件    OK 
	 * @param remoteFile			远程文件名称  (test2.txt";)
	 * @param localFile				本地文件路径 ("D://1.txt";)
	 * @throws IOException
	 */
	public void download(String remoteFile, String localFile)throws IOException {
		java.io.File file_in = new java.io.File(localFile);
		FileOutputStream os = new FileOutputStream(file_in);
		ftpClient.appendFileStream(remoteFile);
		os.close();
	}
	
	/**
	 * 返回文件流
	 * @param remoteFile			远程文件名称  (test2.txt";)
	 * @throws IOException
	 */
	public void download(String remoteFile,OutputStream out)throws IOException {
		ftpClient.retrieveFile("/" + remoteFile, out);
	}
	
	/** OK
	 * 读取远程目录下的文件目录
	 * @param remoteDir
	 * @return
	 * @throws IOException
	 */
	public List<String> nameList(String remoteDir)throws IOException {

		List<String> fileNameList = new ArrayList<String>();
		
		FTPFile [] files =  ftpClient.listFiles(remoteDir);
		for (FTPFile ftpFile : files) {
			String fileName = ftpFile.getName();
			if(!".".equals(fileName) && !"..".equals(fileName))
				fileNameList.add(ftpFile.getName());
		}
		
		return fileNameList;
	}
	
//	public boolean delete(String remoteFileName) throws IOException{
//		
//		String command = "DELE " + remoteFileName;
//		ftpClient.sendServer(command); // 删除服务器上的文件
//		int i = ftpClient.readServerResponse();
//		System.out.println(i);
//		return true;
//	}
	
	

	
    public List<String> getnames(String path) throws IOException{
    	return nameList(path);
	}
    
    
    public boolean downloadFile(String infilepath,String outfilepath,List<String> filename) throws IOException{
        boolean bool=true;
        try {
            InputStream is=null;
            FileOutputStream out=null;
            if(null!=filename&&filename.size()>0){
                File outf=new File(outfilepath);
                if(!outf.exists()){
                    outf.mkdirs();
                }
                for(String name :filename){
//                    System.out.println(name);
//                    is=getFtpInputStream(infilepath+File.separator+name);
                    download(infilepath+File.separator+name,outfilepath+File.separator+name);
//                    if(null!=is){
//                        File outfile=new File(outfilepath+File.separator+name);
//                        out=new FileOutputStream(outfile);
//                        byte [] bytes=new  byte[1024];
//                        int c=-1;
//                        while((c=is.read(bytes))!=-1){
//                            out.write(bytes,0,c);
//                        }
//                    }
//                    outfileclose(out);
//                    infileclose(is);
                    
                }
            }
        } catch (Exception e) {
        	bool=false;
            e.printStackTrace();
        }
        return bool;
    }
    
    public void outfileclose(FileOutputStream out) throws IOException{
    	if(null!=out){
    		out.flush();
    		out.close();
    		
    	}
    }
    public void infileclose(InputStream in) throws IOException{
    	if(null!=in){
    		in.close();
    	}
    }
	public static void main(String agrs[]) throws Exception {
		FtpUtils ftpUtil = new FtpUtils();
		//ftpUtil.connectServer("117.121.50.55", 9999, "merchant", "ScU{{vPRc)X<", "/help");
		ftpUtil.connectServer("10.0.30.193", 21, "shop", "shop", "/merchantpics/notice/201409");
		List<String> names = ftpUtil.nameList("");
		System.out.println(names);
		//ftpUtil.createDir("help");
		
		ftpUtil.upload("D:"+File.separator+"123.txt", "123.txt");
	}
	    
	  //FTP远程命令列表<br>
	  //USER    PORT    RETR    ALLO    DELE    SITE    XMKD    CDUP    FEAT<br>
	  //PASS    PASV    STOR    REST    CWD     STAT    RMD     XCUP    OPTS<br>
	  //ACCT    TYPE    APPE    RNFR    XCWD    HELP    XRMD    STOU    AUTH<br>
	  //REIN    STRU    SMNT    RNTO    LIST    NOOP    PWD     SIZE    PBSZ<br>
	  //QUIT    MODE    SYST    ABOR    NLST    MKD     XPWD    MDTM    PROT<br>
	       
//		在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上 <br>
//    ftpclient.sendServer("XMKD /test/bb "); //执行服务器上的FTP命令<br>
//    ftpclient.readServerResponse一定要在sendServer后调用<br>
//    nameList("/test")获取指目录下的文件列表<br>
//    XMKD建立目录，当目录存在的情况下再次创建目录时报错<br>
//    XRMD删除目录<br>
//    DELE删除文件<br>
}
