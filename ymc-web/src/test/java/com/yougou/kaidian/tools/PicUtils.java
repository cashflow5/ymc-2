package com.yougou.kaidian.tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PicUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String source="E:\\99823149_03_b.jpg";
		Image src=null;
		FileInputStream fileInputStream=null;
		try {
			fileInputStream=new FileInputStream(source);
			src=Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(fileInputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        //Image src=Toolkit.getDefaultToolkit().getImage(source);
        BufferedImage image=null;
        if (image instanceof BufferedImage) {
        	image= (BufferedImage)image;
        }else{
            src = new ImageIcon(src).getImage();
            image = new BufferedImage(src.getWidth(null), src.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, null);
            g.dispose();
        }
        
        int w=image.getWidth(null);
        int h=image.getHeight(null);

        //计算缩略图
        int nw=128;
        int nh=128;
        if(w>h){
        	nh=(nw*h)/w;
        }else{
        	nw=(nh*w)/h;
        }                   
        BufferedImage tag=new BufferedImage(nw,nh,BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image.getScaledInstance(nw, nh, Image.SCALE_SMOOTH), 0, 0, null);
        File thumbnailFile=new File(source.replaceAll("\\.jpg$", ".png"));
        try {
            FileOutputStream out=new FileOutputStream(thumbnailFile);
            JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
	        tag.flush();
	        image.flush();
	        src.flush();
		}
	}

}
