package com.yougou.kaidian.framework.servlet;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.patchca.color.GradientColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

public class ImageValidate extends HttpServlet {

	private static final long serialVersionUID = 2198678289097775859L;
    private static ConfigurableCaptchaService ccs = null;  
    private static RandomFontFactory ff = null;  
    private static RandomWordFactory rwf = null;   

	public ImageValidate() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	
	public void init() throws ServletException {
        super.init();  
        ccs = new ConfigurableCaptchaService();   
		ccs.setColorFactory(new GradientColorFactory());
	    ccs.setFilterFactory(new CurvesRippleFilterFactory(ccs.getColorFactory()));
        ff = new RandomFontFactory(); 
        ff.setRandomStyle(false);  
        ff.setMaxSize(45);  
        ff.setMinSize(30); 
        
        rwf = new RandomWordFactory();  
        rwf.setMaxLength(4);  
        rwf.setMinLength(4);  
        rwf.setCharacters("2345678abcdefghjkmnprstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ");   
        ccs.setTextRenderer(new BestFitTextRenderer());  
        ccs.setFontFactory(ff);  
        ccs.setWordFactory(rwf);  
        ccs.setWidth(120);  
        ccs.setHeight(45);  
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
        
		OutputStream outputStream = response.getOutputStream();
        HttpSession session = request.getSession(true);    
        String captcha = EncoderHelper.getChallangeAndWriteImage(ccs, "png", outputStream);  
		// 将认证码存入SESSION
		session.setAttribute("login_validate_image", captcha);
		outputStream.flush();
		outputStream.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
