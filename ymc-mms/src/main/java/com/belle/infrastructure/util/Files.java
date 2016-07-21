package com.belle.infrastructure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class Files {

	/**
	 * 向客户端下载文件,弹出下载框.
	 * 
	 * @param response
	 *            (HttpServletResponse)
	 * @param file
	 *            (需要下载的文件)
	 * @param isDel
	 *            (下载完成后是否删除该文件)
	 * @throws IOException
	 */
	public void exportFile(HttpServletResponse response, File file,
			boolean isDel) throws IOException {
		OutputStream out = null;
		InputStream in = null;
		try {
			// 获得文件名
			String filename = URLEncoder.encode(file.getName(), "UTF-8");
			// 定义输出类型(下载)
			response.setContentType("application/force-download");
			response.setHeader("Location", filename);
			// 定义输出文件头
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			out = response.getOutputStream();
			in = new FileInputStream(file.getPath());
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
		}catch(Exception e) {
			if(in != null){
				in.close();
			if(out!=null)
				out.close();
			}
		}finally {
			if(in != null){
				in.close();
			if(out!=null)
				out.close();
			}
		}
		if (isDel) {
			// 删除文件,删除前关闭所有的Stream.
			file.delete();
		}

	}
}
