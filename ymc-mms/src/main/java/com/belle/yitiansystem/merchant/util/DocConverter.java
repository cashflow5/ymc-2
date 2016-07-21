package com.belle.yitiansystem.merchant.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.belle.yitiansystem.merchant.thread.StreamGobbler;
import com.belle.yitiansystem.merchant.util.PropertiesUtilForConverter;
import com.yougou.merchant.api.taobao.exception.BusinessException;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
/**
 * 培训中心使用的文档类型转换工具类
 */
public class DocConverter {
	
	private static Logger logger = Logger.getLogger(DocConverter.class);
	private static int environment = 1;// 
	private String fileString;//  
	private String outputPath = "";// 
	private String fileName;
	private File pdfFile; // 
	private File swfFile; // 
	private File docFile; // 
	private static String SWFToolsDir; // 
	private static String openOfficeIp; // 
	private static String openOfficePort;
	
	static{
		logger.info("*********start to init system in Util: DocConverter.java**************");
		initSystem();
	}
	public DocConverter(String fileString) {
		initName(fileString);
	}

	public void setFile(String fileString) {
		initName(fileString);
	}

	private void initName(String fileString) {
		
		this.fileString = fileString;
		fileName = fileString.substring(0, fileString.lastIndexOf("."));
		docFile = new File(fileString);
		pdfFile = new File(fileName + ".pdf");
		swfFile = new File(fileName + ".swf");
	}
	
	private static void initSystem(){
		if (File.separatorChar == '/') {// Unix、Linux
			environment = 2;
		}else{
			environment = 1;
		}
		SWFToolsDir = PropertiesUtilForConverter.getValue("officeview.SWFTools");
		openOfficeIp = PropertiesUtilForConverter.getValue("officeview.oppenOffice.ip");
		openOfficePort = PropertiesUtilForConverter.getValue("officeview.oppenOffice.port");
	}
	
	private void doc2pdf() throws BusinessException {
		String message = "";
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(openOfficeIp,Integer.parseInt(openOfficePort));
				try {
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
					converter.convert(docFile, pdfFile);
					// close the connection
					connection.disconnect();
					logger.info("****pdf file path : " + pdfFile.getPath()+ "****");
				} catch (java.net.ConnectException e) {
					message = "执行doc2pdf：ConnectException happens when do with openOffice tool";
					logger.error(message);
					throw new BusinessException(message);
				} catch (OpenOfficeException e) {
					message = "执行doc2pdf：OpenOfficeException happens, openOffice转换文件时发生异常 ";
					logger.error(message);
					throw new BusinessException(message);
				} catch (Exception e) {
					message = "执行doc2pdf：Exception happens ： "+e;
					logger.error(message);
					throw new BusinessException(message);
				}
			} else {
				message = "执行doc2pdf：pdf File can not find.please check.";
				logger.error(message);
				throw new BusinessException(message);
			}
		} else {
			
			message = "执行doc2pdf：swf File has already exist.please check.";
			logger.error(message);
			throw new BusinessException(message);
		}
	}
	
	@SuppressWarnings("unused")
	private void pdf2swf() throws BusinessException {
		String message = "";
		String cmd = "";
		if (!swfFile.exists()) {
			if (pdfFile.exists()) {
				if (environment == 1) {// windows 
					try {
						cmd = SWFToolsDir+" -z -t -T 9 "+ pdfFile.getPath() + " -o "+ swfFile.getPath();
						this.executeCMD(cmd);
						
						if (!swfFile.exists()){
							this.executeCMD(cmd+" -s poly2bitmap ");
						}

					} catch (IOException e) {
						message = "执行pdf2swf：命令执行发生异常IOException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					}catch(InterruptedException e){
						message = "执行pdf2swf：命令执行发生异常InterruptedException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (Exception e) {
						message = "执行pdf转swf：发生异常 - "+e;
						logger.error(message);
						throw new BusinessException(message);
					}finally{
						if (pdfFile.exists()) {
							pdfFile.delete();
						}
					}
				} else if (environment == 2) {// linux
					try {
						cmd ="pdf2swf -z -t -T 9 " + pdfFile.getPath()+ " -o " + swfFile.getPath() ;
						executeCMD(cmd);
						if (!swfFile.exists()){
							this.executeCMD(cmd+" -s poly2bitmap ");
						}

					} catch (IOException e) {
						message = "执行pdf2swf：命令执行发生异常IOException，请检查命令语句如下："+cmd;
						logger.error(message+e);
						throw new BusinessException(message);
					}catch(InterruptedException e){
						message = "执行pdf2swf：命令执行发生异常InterruptedException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (Exception e) {
						message = "执行pdf转swf：发生异常 - "+e;
						logger.error(message);
						throw new BusinessException(message);
					}finally{
						if (pdfFile.exists()) {
							pdfFile.delete();
						}
					}
				}
				
			} else {
				message = "执行pdf2swf：pdf File can not find.please check.";
				logger.error(message);
				throw new BusinessException(message);
			}
		} else {
			message = "执行pdf2swf：swf File has already exist.please check.";
			logger.error(message);
			throw new BusinessException(message);
		}
	}
	
	private void executeCMD(String cmd) throws IOException, InterruptedException{
		
		Process p = Runtime.getRuntime().exec(cmd);
		
		StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");            
        errorGobbler.start();
        StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
        outGobbler.start(); 
		
		p.waitFor();//等待process线程执行完毕返回这里
	}

	/** pdf 类型转换，单独调，临时文件不删除 */
	@SuppressWarnings("unused")
	private void pdf2swfForPDF() throws BusinessException {
		String message = "";
		String cmd = "";
		if (!swfFile.exists()) {
			if (pdfFile.exists()) {
				if (environment == 1) {// windows 
					try {
						cmd = SWFToolsDir+" -z -t -T 9 "+ pdfFile.getPath() + " -o "+ swfFile.getPath();
						this.executeCMD(cmd);
						if (!swfFile.exists()){
							this.executeCMD(cmd+" -s poly2bitmap ");
						}
						
					} catch (IOException e) {
						message = "执行pdf2swf：命令执行发生异常IOException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (InterruptedException e) {
						message = "执行pdf2swf：命令执行发生异常InterruptedException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (Exception e) {
						message = "执行pdf转swf：发生异常 - "+e;
						logger.error(message);
						throw new BusinessException(message);
					}
				} else if (environment == 2) {// linux
					try {
						cmd ="pdf2swf -z -t -T 9 " + pdfFile.getPath()+ " -o " + swfFile.getPath() ;
						this.executeCMD(cmd);
						if (!swfFile.exists()){
							this.executeCMD(cmd+" -s poly2bitmap ");
						}
					} catch (IOException e) {
						message = "执行pdf2swf：命令执行发生异常IOException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (InterruptedException e) {
						message = "执行pdf2swf：命令执行发生异常InterruptedException，请检查命令语句如下："+cmd;
						logger.error(message);
						throw new BusinessException(message);
					} catch (Exception e) {
						message = "执行pdf转swf：发生异常 - "+e;
						logger.error(message);
						throw new BusinessException(message);
					} 
				}
			} else {
				message = "执行pdf2swf：pdf File can not find.please check.";
				logger.error(message);
				throw new BusinessException(message);
			}
		} else {
			message = "执行pdf2swf：swf File has already exist.please check.";
			logger.error(message);
			throw new BusinessException(message);
		}
	}
	
	static String loadStream(InputStream in) throws IOException {

		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();

		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}

		return buffer.toString();
	}
	
	/**
	 * 方法主体
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public boolean conver() throws Exception {

		if (swfFile.exists()) {
			logger.info("****swfFile was created and existed in local file system,please check and clear . ***");
			return true;
		}

		if (environment == 1) {
			logger.info("****windows****");
		} else {
			logger.info("****linux****");
		}
	
		doc2pdf();
		pdf2swf();

		if (swfFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean converPdfToSwf() throws Exception {

		if (environment == 1) {
			logger.info("****windows****");
		} else {
			logger.info("****linux****");
		}
	
		pdf2swfForPDF();
		
		if (swfFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	
	public String getswfPath() {
		if (swfFile.exists()) {
			String tempString = swfFile.getPath();
			tempString = tempString.replaceAll("\\\\", "/");
			return tempString;
		} else {
			return "";
		}

	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
		if (!outputPath.equals("")) {
			String realName = fileName.substring(fileName.lastIndexOf("/"),
					fileName.lastIndexOf("."));
			if (outputPath.charAt(outputPath.length()) == '/') {
				swfFile = new File(outputPath + realName + ".swf");
			} else {
				swfFile = new File(outputPath + realName + ".swf");
			}
		}
	}

//	public static void main(String[] args) {
//		DocConverter d = new DocConverter("E:/违规处罚.pptx");
//		try {
//			d.conver();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
