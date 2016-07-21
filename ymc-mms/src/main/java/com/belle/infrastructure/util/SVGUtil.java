/**
 * 
 */
package com.belle.infrastructure.util;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换svg格式的图片
 * 
 * @author huang.tao
 *
 */
public class SVGUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SVGUtil.class);
	
	public static byte[] convert2ByteBySVG(String svg) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
//		Transcoder trans = new PNGTranscoder();
//		TranscoderInput input = new TranscoderInput(new StringReader(svg));  
//        TranscoderOutput output = new TranscoderOutput(out);
        
        try {
//            trans.transcode(input, output);
        } catch (Exception e) {
        	logger.error("Problem transcoding stream. See the web logs for more details.", e);
		}
		return out.toByteArray();
	}
}
