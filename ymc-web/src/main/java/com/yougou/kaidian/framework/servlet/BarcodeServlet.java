package com.yougou.kaidian.framework.servlet;

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
		String s = "";//base64后图片流字符文件
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