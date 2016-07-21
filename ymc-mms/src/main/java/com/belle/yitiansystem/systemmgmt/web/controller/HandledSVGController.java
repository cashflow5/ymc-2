/**
 * 
 */
package com.belle.yitiansystem.systemmgmt.web.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 处理SVG
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/yitiansystem/svg")
public class HandledSVGController extends HttpServlet {

	private static final long serialVersionUID = 831261921694334620L;
	
	private static final String SVG_DOCTYPE = "<?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
	
	@RequestMapping(method = RequestMethod.POST)
	public void exporter(
		@RequestParam(value = "svg", required = false) String svg,
		@RequestParam(value = "type", required = false) String type,
		@RequestParam(value = "filename", required = false) String filename,
		@RequestParam(value = "width", required = false) String width,
		@RequestParam(value = "scale", required = false) String scale,
		@RequestParam(value = "options", required = false) String options,
		@RequestParam(value = "constr", required = false) String constructor,
		@RequestParam(value = "callback", required = false) String callback,
		HttpServletRequest request,
		HttpServletResponse response,
		HttpSession session) throws Exception {
		request.setCharacterEncoding("utf-8");//设置编码，解决乱码问题
		ServletOutputStream out = response.getOutputStream();
//        if (null != type && null != svg) {
//            svg = svg.replaceAll(":rect", "rect");
//            String ext = "";
//            Transcoder t = null;
//            if (type.equals("image/png")) {
//                ext = "png";  
//                t = new PNGTranscoder();  
//            } else if (type.equals("image/jpeg")) {  
//                ext = "jpg";
//                t = new JPEGTranscoder();  
//            } else if (type.equals("application/pdf")) {  
//                ext = "pdf"; 
//                t =(Transcoder) new PDFTranscoder();
//            } else if(type.equals("image/svg+xml"))   
//                ext = "svg";
//            response.addHeader("Content-Disposition", "attachment; filename="+ filename + "."+ext);  
//            response.addHeader("Content-Type", type);  
//            
//            if (null != t) { 
//                TranscoderInput input = new TranscoderInput(new StringReader(svg));  
//                TranscoderOutput output = new TranscoderOutput(out);  
//                
//                try {
//                    t.transcode(input, output);
//                } catch (TranscoderException e) {
//                    out.print("Problem transcoding stream. See the web logs for more details.");  
//                    e.printStackTrace();
//                }
//            } else if (ext.equals("svg")) {
//                OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
//                writer.append(SVG_DOCTYPE);
//                writer.append(svg);  
//                writer.close();  
//            } else   
//                out.print("Invalid type: " + type);  
//        } else {  
//            response.addHeader("Content-Type", "text/html");  
//            out.println("Usage:\n\tParameter [svg]: The DOM Element to be converted." +  
//                    "\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");  
//        }  
        out.flush();  
        out.close();  
	}
}
