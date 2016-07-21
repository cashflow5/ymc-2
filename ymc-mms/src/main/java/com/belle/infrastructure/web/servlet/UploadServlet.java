package com.belle.infrastructure.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.belle.infrastructure.util.FileUploadUtil;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String path;

	/**
	 * Default constructor.
	 */
	public UploadServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 生成存放文件的路径
		String savePath = path + "/";

		File f1 = new File(savePath);

		if (!f1.exists()) {
			f1.mkdirs();
		}

		DiskFileItemFactory fac = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(fac);

		upload.setHeaderEncoding("utf-8");

		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";

		String extName = "";

		while (it.hasNext()) {

			FileItem item = it.next();

			if (!item.isFormField()) {

				name = item.getName();

				// 新的文件名
				String newUploadFileName = FileUploadUtil.getlnstance()
						.getNewFileName(name);

				// // 扩展名格式：
				//
				// if (name.lastIndexOf(".") >= 0) {
				//
				// extName = name.substring(name.lastIndexOf("."));
				//
				// }

				File saveFile = new File(savePath + newUploadFileName);
				try {

					item.write(saveFile);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		path = config.getServletContext().getRealPath("/uploadImages");
	}

}
