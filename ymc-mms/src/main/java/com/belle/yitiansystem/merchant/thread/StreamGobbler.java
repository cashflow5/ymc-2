package com.belle.yitiansystem.merchant.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.belle.yitiansystem.merchant.util.DocConverter;

/**
 * 用于处理Runtime.getRuntime().exec产生的错误流及输出流
 * @author luoqian
 *
 */
public class StreamGobbler extends Thread {
	InputStream is;
	String type;
	OutputStream os;
	private static Logger logger = Logger.getLogger(StreamGobbler.class);    
	public StreamGobbler(InputStream is, String type) {
		this(is, type, null);
	}

    StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }
    
    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            if (os != null)
                pw = new PrintWriter(os);
                
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null) {
                if (pw != null)
                    pw.println(line);
                logger.info(type + ">" + line);    
            }
            
            if (pw != null)
                pw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();  
        } finally{
        	
        	if(null!=pw){
        		pw.close();
        		pw = null;
        	}
        	try {
	        	if(null!=br){
					br.close();
	        		br = null;
	        	}
	        	if(null!=isr){
	        		isr.close();
	        		isr = null;
	        	}
        	} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
} 
