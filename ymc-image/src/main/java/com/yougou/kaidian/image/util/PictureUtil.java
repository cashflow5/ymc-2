package com.yougou.kaidian.image.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.yougou.kaidian.image.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.image.beans.CommodityPicEditor;
import com.yougou.kaidian.image.beans.CommodityPicEditor.CommodityPicDescriptor;
import com.yougou.kaidian.image.beans.MessageBean;
import com.yougou.kaidian.image.beans.ResizeDefinition;
import com.yougou.kaidian.image.beans.ResizeDefinition.ResizeEntry;

/**
 * 商品图片工具类
 * 
 * @author huang.tao
 *
 */
public class PictureUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(PictureUtil.class);
	
	// 定义图片后缀 【 详情页左侧图、缩略图、手机图、手机图】
	private final static String[] SUFFIX = new String[] { "_l.jpg", "_m.jpg", "_t.jpg", "_mb.jpg", "_ms.jpg" };
	// 颜色图、列表图、后台程序图
	private final static String[] SUFFIX_F = new String[] { "_c.jpg", "_s.jpg", "_u.jpg" };
	
	public static Map<String, String> createImageNameList(String id, String serialNo, String commodityNo) {
		Map<String, String> imageNames = new HashMap<String, String>();
		for (String suffix : SUFFIX) {
			imageNames.put(id + "_" + serialNo + suffix, commodityNo + "_" + serialNo + suffix);
		}
		
		return imageNames;
	}
	
	/**
	 * 创建颜色小图列表
	 * 
	 * @param id
	 * @param serialNo
	 * @param commodityNo
	 * @return
	 */
	public static Map<String, String> createImageNameList_F(String id, String commodityNo) {
		Map<String, String> imageNames = new HashMap<String, String>();
		for (String suffix : SUFFIX_F) {
			imageNames.put(id + "_01" + suffix, commodityNo + "_01" + suffix);
		}
		
		return imageNames;
	}
	
	/**
	 * 在图片目录创建文件名
	 * 
	 * @param 文件名
	 * @param merchantCode 商家编码
	 * @return String
	 */
	public static String createTemporaryPicname(String picdir,String filename, String merchantCode) {
		File picDir=new File(picdir+File.separator+merchantCode+File.separator);
		if(!picDir.exists()){
			picDir.mkdirs();
		}
		return (new File(picDir,ObjectUtils.defaultIfNull(filename, new java.rmi.server.UID().toString().replaceAll("(:|-)", "_") + ".jpg").toString())).getAbsolutePath();
	}
	
	/**
	 * 在指定目录创建文件名
	 * 
	 * @param 文件名
	 * @return String
	 */
	public static String createTemporaryPicname(String picdir,String filename) {
		return new StringBuilder()
		.append(picdir)
		.append(File.separator)
		.append(ObjectUtils.defaultIfNull(filename, new java.rmi.server.UID().toString().replaceAll("(:|-)", "_") + ".jpg"))
		.toString();
	}
	
	/**
	 * 替换图片名称 uuid_01_l.jpg ==> xx_01_l.jpg
	 * 
	 * @param str
	 * @return
	 */
	public static String imageReplaceNameByStr(String fileName, String str) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		
		String[] arrays = fileName.split("_"); 
		if (arrays.length != 3) {
			return fileName;
		}
		
		return new StringBuilder().append(str).append("_").append(arrays[1]).append("_").append(arrays[2]).toString();
	}
	
	/**
	 * 标准化图片URL
	 * 
	 * @param imageUrl
	 * @return String
	 */
	public static String normalizeImageUrl(String imageUrl) {
		return imageUrl == null ? imageUrl : imageUrl.replaceAll("(?<!http:)/{2,}", "/");
	}
	
	/**
	 * 从图片URL中提取名称
	 * 
	 * @return String
	 */
	public static String extractNameFromImageUrl(String imageUrl) {
		if (imageUrl == null) {
			return imageUrl;
		}
		
		int index = imageUrl.lastIndexOf("/");
		if (index == -1) {
			return imageUrl;
		} else {
			imageUrl = imageUrl.substring(index + 1);
		}
		
		index = imageUrl.indexOf("?");
		return index == -1 ? imageUrl : imageUrl.substring(0, index);
	}
		
	public static File createThumbnailFile_bt(String source) throws Exception {
		Image src = Toolkit.getDefaultToolkit().getImage(source);
		BufferedImage image = null;
		if (src instanceof BufferedImage) {
			image = (BufferedImage) src;
		} else {
			src = new ImageIcon(src).getImage();
			image = new BufferedImage(src.getWidth(null), src.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
		}

		int w = image.getWidth(null);
		int h = image.getHeight(null);

		// 计算缩略图
		int nw = 128;
		int nh = 128;
		if (w > h) { nh = (nw * h) / w;} else {nw = (nh * w) / h; }

		BufferedImage tag = null;
		File thumbnailFile;
		try {
			if (nw == 0 || nh == 0) 
				logger.info("图片: {} | 原图 width:{}, height:{} | 计算后缩略图 width:{}, height:{}",
						new Object[] { source, w, h, nw, nh });
			
			// 处理缩略图长宽为0的情况，给默认值
			if (nw == 0) { nw = 128; }
			if (nh == 0) { nh = 128; }
			tag = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(nw, nh, Image.SCALE_SMOOTH), 0, 0,
					null);
			thumbnailFile = new File(source.replaceAll("\\_b.jpg$", "_bt.jpg"));

			FileOutputStream out = new FileOutputStream(thumbnailFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
		} catch (Exception e) {
			throw e;
		} finally {
			tag.flush();
			image.flush();
			src.flush();
		}
		return thumbnailFile;
	}
	
	/**
	 * 在临时目录创建图片缩略图
	 * 
	 * @param 文件名
	 * @return String
	 * @throws IOException 
	 */
	public static File createThumbnailFile(String source) throws IOException {
        Image src=Toolkit.getDefaultToolkit().getImage(source);
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
			throw e;
		}finally{
	        tag.flush();
	        image.flush();
	        src.flush();
		}
		return thumbnailFile;
	}
	
	/**
	 * 切商品图
	 * @param source
	 * @param resizeDefinition
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static MessageBean createViewPics(List<File> sources,ResizeDefinition resizeDefinition,int number,String perlScriptsPath,File perlHome,String imageMagickHomeStr,String imageMagickSharpen) throws Exception{
    	MessageBean mesBean=new MessageBean();
    	File imageMagickHome = new File(imageMagickHomeStr);
    	
		String[] processCommands;
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process p=null;
		if (File.separatorChar == '/') {// UNIX
			processCommands = new String[] { "bash", "-c", null};
		} else {// WINDOWS
			processCommands = new String[] { "cmd.exe", "/c", null };
		}
		for(File source:sources){
			//perl 切图
			try {
				File perlScriptsFile=new File(perlScriptsPath);
				if(perlScriptsFile.exists()){
					perlScriptsPath=perlScriptsFile.getAbsolutePath();
				}else{
					perlScriptsPath = new ClassPathResource(perlScriptsPath).getFile().getAbsolutePath();
				}
				processCommands[2] = MessageFormat.format("{0} {1} {2}", perlScriptsPath, source, Integer.toString(number));
				logger.info("start process perl command:" + Arrays.toString(processCommands));
				p=processBuilder.directory(perlHome).command(processCommands).start();
				p.waitFor();
				mesBean.getScuessFile().add(source);
			} catch (RuntimeException e) {
				logger.warn("使用perl脚本切角度图异常，切换到ImageMagick切图", e);
				//假如产生异常运行时异常，切换到ImageMagick切图
				CommodityPicEditor editor = new CommodityPicEditor();
				editor.setAsText(source.getName());
				CommodityPicDescriptor descriptor = (CommodityPicDescriptor) editor.getValue();

				// 其它调整尺寸图片
				Object propertyValue = ((CommodityPicDescriptor) ObjectUtils.defaultIfNull(descriptor, CommodityPicDescriptor.NONE)).getSuffix();
				Collection<ResizeEntry> resizeEntrySet = CollectionUtils.select(resizeDefinition.getResizeEntrySet(), new BeanPropertyEqualsPredicate("sourceSuffix", propertyValue));
				if(descriptor != null&&CollectionUtils.isNotEmpty(resizeEntrySet)){
					// 追加3张扩展小图
					resizeEntrySet.addAll(resizeDefinition.getDynamicResizeEntrySet());
					File target=null;
					for (ResizeEntry resizeEntry : resizeEntrySet) {
						target = new File(source.getParent(),descriptor.getPrefix()+resizeEntry.getTargetSuffix());
						processCommands[2] = MessageFormat.format("convert -sharpen {0} {1} -resize {2} {3}", imageMagickSharpen, source, resizeEntry.getDimension(), target);
						logger.info("start process image magick command:" + Arrays.toString(processCommands));
						Process process = processBuilder.directory(imageMagickHome).command(processCommands).start();
						if (process.waitFor() != 0) {
							throw new RuntimeException(IOUtils.toString(process.getErrorStream(), "UTF-8"));
						}
					}
					mesBean.getScuessFile().add(source);
				}else{
					mesBean.getFailFile().add(source);
				}
			}
		}
		return mesBean;
    }
	
	/**
	 * 切商品图(单张)
	 * @param sources
	 * @param resizeDefinition
	 * @param number
	 * @param perlScriptsPath
	 * @param perlHome
	 * @param imageMagickHomeStr
	 * @param imageMagickSharpen
	 * @return
	 * @throws Exception
	 */
	public static MessageBean createViewPics(File source,ResizeDefinition resizeDefinition,int number,String perlScriptsPath,File perlHome,String imageMagickHomeStr,String imageMagickSharpen) throws Exception{
    	MessageBean mesBean=new MessageBean();
    	File imageMagickHome = new File(imageMagickHomeStr);
    	
		String[] processCommands;
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process p=null;
		if (File.separatorChar == '/') {// UNIX
			processCommands = new String[] { "bash", "-c", null};
		} else {// WINDOWS
			processCommands = new String[] { "cmd.exe", "/c", null };
		}
		//perl 切图
		try {
			File perlScriptsFile=new File(perlScriptsPath);
			if(perlScriptsFile.exists()){
				perlScriptsPath=perlScriptsFile.getAbsolutePath();
			}else{
				perlScriptsPath = new ClassPathResource(perlScriptsPath).getFile().getAbsolutePath();
			}
			processCommands[2] = MessageFormat.format("{0} {1} {2}", perlScriptsPath, source, Integer.toString(number));
			logger.info("start process perl command:" + Arrays.toString(processCommands));
			p=processBuilder.directory(perlHome).command(processCommands).start();
			p.waitFor();
			mesBean.getScuessFile().add(source);
		} catch (RuntimeException e) {
			logger.warn("使用perl脚本切角度图异常,切换到ImageMagick切图,File("+source.getName()+")", e);
			//假如产生异常运行时异常，切换到ImageMagick切图
			CommodityPicEditor editor = new CommodityPicEditor();
			editor.setAsText(source.getName());
			CommodityPicDescriptor descriptor = (CommodityPicDescriptor) editor.getValue();

			// 其它调整尺寸图片
			Object propertyValue = ((CommodityPicDescriptor) ObjectUtils.defaultIfNull(descriptor, CommodityPicDescriptor.NONE)).getSuffix();
			Collection<ResizeEntry> resizeEntrySet = CollectionUtils.select(resizeDefinition.getResizeEntrySet(), new BeanPropertyEqualsPredicate("sourceSuffix", propertyValue));
			if(descriptor != null&&CollectionUtils.isNotEmpty(resizeEntrySet)){
				// 追加3张扩展小图
				resizeEntrySet.addAll(resizeDefinition.getDynamicResizeEntrySet());
				File target=null;
				for (ResizeEntry resizeEntry : resizeEntrySet) {
					target = new File(source.getParent(),descriptor.getPrefix()+resizeEntry.getTargetSuffix());
					processCommands[2] = MessageFormat.format("convert -sharpen {0} {1} -resize {2} {3}", imageMagickSharpen, source, resizeEntry.getDimension(), target);
					logger.info("start process image magick command:" + Arrays.toString(processCommands));
					Process process = processBuilder.directory(imageMagickHome).command(processCommands).start();
					if (process.waitFor() != 0) {
						throw new RuntimeException(IOUtils.toString(process.getErrorStream(), "UTF-8"));
					}
				}
				mesBean.getScuessFile().add(source);
			}else{
				mesBean.getFailFile().add(source);
			}
		}
		return mesBean;
    }
}
