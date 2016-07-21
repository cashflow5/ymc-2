package com.belle.infrastructure.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;



/**
 * 
 * 一句话功能简述：〈文件上传工具类〉 功能详细描述：〈功能详细描述〉
 * 
 * @author fangyong
 * @version [版本号, Oct 12, 200910:57:35 AM]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileUploadUtil {

	private static FileUploadUtil fileUploadUtil;

	// 图片的自动编号
	private int picNo = 0;

	// 单例
	public static synchronized FileUploadUtil getlnstance() {
		if (null == fileUploadUtil) {
			fileUploadUtil = new FileUploadUtil();
		}

		return fileUploadUtil;
	}

	/**
	 * 
	 * @Title: getNewFileName
	 * @Description: TODO(上传后的文件名)
	 * @param @param fileName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getNewFileName(String fileName) {
		StringBuffer buff = new StringBuffer();
		String str = DateUtil.getCurrentDateTimeToStr();
		long m = Long.parseLong((str)) + picNo;
		picNo++;
		buff.append(m).append(
				FileUploadUtil.getlnstance().getExtention(fileName));
		return buff.toString();
	}

	/**
	 * 
	 * 〈获取文件格式eg:0001.jpg--->jpg〉
	 * 
	 * @param [@param fileName
	 * @param [@return] [参数1说明]&#13;
	 * @param [@param fileName
	 * @param [@return] [参数2说明]&#13;
	 * @return String
	 * @Modify [方勇]
	 */
	public String getExtention(String fileName) {
		String uploadName = "";
		if (!"".equals(fileName) && null != fileName) {
			int pos = fileName.lastIndexOf(".");
			uploadName = fileName.substring(pos);
		}
		return uploadName;
	}
	
	
//	public static final String DEFAULT_FILE_UPLOAD_DIR = "fileupload/files";

	
//	/**
//	 * 
//	 * @功能模块：
//	 * @方法说明(MethodsIntro)： 保存上传的文件到指定的目录。并给文件随机生成一个新文件名，并返回这个文件保存成功之后的相对路径。
//	 * @param mFile
//	 * @param relativeDir
//	 *            文件要保存的路径。相对于webroot
//	 * @return
//	 * 
//	 */
//	public static String saveFileUpload(MultipartFile mFile, String relativeDir) {
//		return saveFileUpload(mFile, relativeDir, UUID.randomUUID().toString());
//	}
//
	/**
	 * springmvc专用, 保存上传的文件。
	 * 
	 * @param mFile
	 * @param dir
	 *            文件要保存的路径。相对于webroot
	 * @param newfileName
	 *            新文件名,可以为空。定义保存后，承现的文件名。如果不带有后缀名，将使用上传源文件的后缀名。 返回文件的相对路径
	 */
	public String saveFileUpload(MultipartFile mFile, String relativeDir, String newfileName) {
	    String url = "";
		try {
		   
			if (newfileName != null) {
				if (newfileName != "") {
					newfileName = newfileName.concat(getExtention(mFile.getOriginalFilename()));
				}
			} else {
				newfileName = mFile.getOriginalFilename();
			}
			
			 String realPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
			url= relativeDir+"/"+newfileName;
			File file = new File(realPath+"/"+url);
			if(!file.exists()){
				file.mkdirs();
			}
			mFile.transferTo(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

}
