package com.belle.infrastructure.web.servlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.commons.codec.binary.Base64;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.eps.EPSCanvasProvider;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.w3c.dom.DocumentFragment;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 按照字符获取条形码
 *
 * @author zhuangruibo
 * @create 2012-6-9 上午11:10:40 
 * @history
 */
public class BarcodeServlet extends HttpServlet {
	private static final long serialVersionUID = -1612710758060435089L;
	private transient Logger log;

	public BarcodeServlet() {
		this.log = new ConsoleLogger(1);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//图片类型
			String format = determineFormat(request);
			//输出方式
			String stream = request.getParameter("stream");
			int orientation = 0;
			Configuration cfg = buildCfg(request);

			String code = request.getParameter("code");
			if (code == null) {
				code = "0123456789";
			}
			
			BarcodeUtil util = BarcodeUtil.getInstance();
			BarcodeGenerator gen = util.createBarcodeGenerator(cfg);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
			BufferedImage image = null;	
			try {
				if (format.equals("image/svg+xml")) {
					SVGCanvasProvider svg = new SVGCanvasProvider(false, orientation);
					gen.generateBarcode(svg, code);
					DocumentFragment frag = svg.getDOMFragment();

					TransformerFactory factory = TransformerFactory.newInstance();
					Transformer trans = factory.newTransformer();
					Source src = new DOMSource(frag);
					Result res = new StreamResult(bout);
					trans.transform(src, res);
				} else if (format.equals("image/x-eps")) {
					EPSCanvasProvider eps = new EPSCanvasProvider(bout, orientation);
					gen.generateBarcode(eps, code);
					eps.finish();
				} else {
					String resText = request.getParameter("res");
					int resolution = 1000;
					if (resText != null) {
						resolution = Integer.parseInt(resText);
					}
					if (resolution > 2400) {
						throw new IllegalArgumentException("Resolutions above 2400dpi are not allowed");
					}

					if (resolution < 10) {
						throw new IllegalArgumentException("Minimum resolution must be 10dpi");
					}

					String gray = request.getParameter("gray");
					BitmapCanvasProvider bitmap = ("true".equalsIgnoreCase(gray)) ? new BitmapCanvasProvider(bout, format, resolution, 10, true, orientation) : new BitmapCanvasProvider(bout, format, resolution, 12, false, orientation);
					gen.generateBarcode(bitmap, code);
					if ("base64".equalsIgnoreCase(stream)) {
						image = bitmap.getBufferedImage();
					}
					bitmap.finish();
				}
			} finally {
				bout.close();
			}


			if ("base64".equalsIgnoreCase(stream)) {
				BufferedImage tag = new BufferedImage(260, 45, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(image.getScaledInstance(260, 45, Image.SCALE_SMOOTH), 0, 0, null);
				bout = new ByteArrayOutputStream(4096);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bout);
                encoder.encode(tag);
//                response.getOutputStream().flush(); // 刷新到页面生成图片                    
//                response.getOutputStream().close(); // 关闭writer
                
                byte[] byteArray = bout.toByteArray();
				response.getWriter().println(new String(Base64.encodeBase64(byteArray)));
				response.getWriter().flush();
			} else {
				byte[] byteArray = bout.toByteArray();
				response.setContentType(format);
				response.setContentLength(bout.size());
				response.getOutputStream().write(byteArray);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (Exception e) {
			this.log.error("Error while generating barcode", e);
			throw new ServletException(e);
		} catch (Throwable t) {
			this.log.error("Error while generating barcode", t);
			throw new ServletException(t);
		}
	}

	/**
	 * 获取图片格式
	 * @param request
	 * @return
	 */
	protected String determineFormat(HttpServletRequest request) {
		String format = request.getParameter("ft");
		format = MimeTypes.expandFormat(format);
		if (format == null) {
			format = "image/jpeg";
		}
		return format;
	}

	/**
	 * 条形码图片属性设置
	 * @param request
	 * @return
	 */
	protected Configuration buildCfg(HttpServletRequest request) {
		DefaultConfiguration cfg = new DefaultConfiguration("barcode");

		String type = request.getParameter("type");
		if (type == null) {
			type = "code128";
		}
		DefaultConfiguration barcodeType = new DefaultConfiguration(type);
		cfg.addChild(barcodeType);
		String height = request.getParameter("height");//"10mm"
		if (height != null) {
			addChild(barcodeType, "height", height);
		}
		String moduleWidth = request.getParameter("mw");
		if (moduleWidth != null) {
			addChild(barcodeType, "module-width", moduleWidth);
		}
		String wideFactor = request.getParameter("wf");
		if (wideFactor != null) {
			addChild(barcodeType, "wide-factor", wideFactor);
		}
		String quietZone = request.getParameter("qz");
		if (quietZone == null) {
			quietZone = "0";
		}
		if (quietZone != null) {
			DefaultConfiguration attr = new DefaultConfiguration("quiet-zone");
			if (quietZone.startsWith("disable"))
				attr.setAttribute("enabled", "false");
			else {
				attr.setValue(quietZone);
			}
			barcodeType.addChild(attr);
		}

		String humanReadablePosition = request.getParameter("hrp");
		String pattern = request.getParameter("hrpattern");
		String humanReadableSize = request.getParameter("hrsize");
		String humanReadableFont = request.getParameter("hrfont");

		if (humanReadableSize == null) {
			humanReadableSize = "0mm";
		}
		if (humanReadablePosition != null || pattern != null || humanReadableSize != null || humanReadableFont != null) {
			DefaultConfiguration humanReadable = new DefaultConfiguration("human-readable");
			if (pattern != null) {
				addChild(humanReadable, "pattern", pattern);
			}
			if (humanReadableSize != null) {
				addChild(humanReadable, "font-size", humanReadableSize);
			}
			if (humanReadableFont != null) {
				addChild(humanReadable, "font-name", humanReadableFont);
			}
			if (humanReadablePosition != null) {
				addChild(humanReadable, "placement", humanReadablePosition);
			}
			barcodeType.addChild(humanReadable);
		}

		return cfg;
	}

	/**
	 * 设置子级属性
	 * @param parent
	 * @param attrKey
	 * @param attrValue
	 */
	private void addChild(DefaultConfiguration parent, String attrKey, String attrValue) {
		DefaultConfiguration attr = new DefaultConfiguration(attrKey);
		if (attrValue instanceof String) {
			attr.setValue(attrValue);
		} else {
			attr.setValue((String)attrValue);
		}
		parent.addChild(attr);
	}

	public static void main(String[] args) {
		String s = "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";//base64后图片流字符文件
		byte[] b = Base64.decodeBase64(s.getBytes());
		File f = new File("d:/456.jpg");
		try {
			FileOutputStream out = new FileOutputStream(f);
			out.write(b);
			out.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}