package com.belle.yitiansystem.merchant.thread;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.MessageChannel;

import com.belle.infrastructure.constant.Constant;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.service.IMerchantTrainingService;
import com.belle.yitiansystem.merchant.util.DocConverter;
import com.belle.yitiansystem.merchant.util.SpringFTPUtil;
import com.yougou.tools.common.utils.ServiceLocator;

public class ProcessCallable implements Callable<ProcessResult>{
		private String trainingFtpPath;
		private String relativePath;
		private String localTempPath;
		private String type;
		private MerchantTraining merchantTraining;
		private RedisTemplate<String, Object> redisTemplate;
		private MessageChannel ftpChannel;
		private Logger logger = Logger.getLogger(ProcessCallable.class);
		
		public ProcessCallable( MerchantTraining merchantTraining, String localTempPath,String trainingFtpPath,
								String relativePath, String type, RedisTemplate<String, Object> redisTemplate,
								MessageChannel ftpChannel ) {
			this.merchantTraining = merchantTraining;
			this.trainingFtpPath = trainingFtpPath;
			this.relativePath =relativePath;
			this.localTempPath = localTempPath;
			this.type = type;
			
			this.redisTemplate = redisTemplate;
			this.ftpChannel = ftpChannel;
		}

		/** 处理方法主体 */
		public ProcessResult call() throws Exception {
			String ftpFilePath = trainingFtpPath+relativePath;
			logger.info("文件转换:> "+merchantTraining.getFileUrl()+" , "+
					localTempPath+" , "+ ftpFilePath +" , "+type		);
			ProcessResult processResult = null;
			String message = "";// 异常信息
			// step1: 写入redis缓存 处理中的状态
			Map<String,String> map = new HashMap<String,String>();
			if( null!= this.redisTemplate.opsForValue().get(Constant.PROCESS_POOL_REDIS_KEY) ){
				map = (Map<String,String>)this.redisTemplate.opsForValue().get(Constant.PROCESS_POOL_REDIS_KEY);
			}
			map.put(merchantTraining.getId(), "true");
			this.redisTemplate.opsForValue().set(Constant.PROCESS_POOL_REDIS_KEY, map);
			
			// step2 do locate + transfer + upload + deleteFile + updateDB
			String originalFileName = merchantTraining.getFileName();
			String fileFtpUrl = merchantTraining.getFileUrl();
			String type = originalFileName.substring(originalFileName.lastIndexOf("."));
			type = type.toLowerCase();//为了判断转小写
			
			localTempPath = localTempPath.replaceAll("\\\\", "/");
			String tempFileName	= fileFtpUrl.substring(fileFtpUrl.lastIndexOf("/")+1);//必须对
			String filePath =  localTempPath+ tempFileName;
			String afterFilePath = "";//格式转换后临时文件swf的路径
			String previewFileName = "";//swf文件的名称
			previewFileName = tempFileName.substring(0,tempFileName.lastIndexOf("."))+".swf";
			afterFilePath = localTempPath+ previewFileName;
			File afterFile = null;//格式转换后临时文件swf
			File file = null;
			
			IMerchantTrainingService service = null;
			try {
				service = (IMerchantTrainingService) ServiceLocator.getInstance().getBeanFactory().getBean(IMerchantTrainingService.class);
			} catch (Exception e) {
				logger.error("获取IMerchantTrainingService接口异常：",e);
				message = "获取IMerchantTrainingService接口异常："+e;
		    	processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,message);
		    	return processResult;
			}
			
			try{
				
				File dir  = new File(localTempPath);
				if( !dir.exists() ){
					// 异常写入日志文件：服务器临时文件保存目录未创建
				 	processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,"服务器临时文件保存目录未创建");
				 	return	processResult;
				}
				file = new File( filePath );
				if( !file.exists() ){
					// 异常写入日志文件：该课程临时文件定位失败，请检查
					processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,"该课程临时文件定位失败，请检查");
					return	processResult;
				}
				
				// step 2-1:transfer
				this.transferFileForView(filePath,type);//若有异常，抛出的是BusinessException
				
				afterFile = new File(afterFilePath);//格式转换后临时文件swf
				if( afterFile.exists() ){
					
					// step 2-2:ftp
					Map uploadFileMap = new HashMap<String,File>();
					uploadFileMap.put( previewFileName, afterFile );
					uploadFileMap.put( tempFileName, file );
					Map<String,File> failFile = SpringFTPUtil.ftpUpload(ftpChannel, uploadFileMap, ftpFilePath );
					if(null!=failFile && 0<failFile.size() ){
						message = " 文件转换完成后，"+failFile.size()+"个文件ftp上传失败，请查看日志查明原因：";
				    	processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,message);
				    	return	processResult;
					}
						
					// 已成功 处理和上传
				}else{
					message = " 目标文件未成功生成，不能ftp上传，请查看日志查明原因。";
			    	processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,message);
			    	return	processResult;
				}
				
				
				// step2-3  更新数据库
				MerchantTraining merchantTrainingForUpdate = new MerchantTraining ();
				merchantTrainingForUpdate.setId( merchantTraining.getId() );
				merchantTrainingForUpdate.setPreviewUrl( relativePath+previewFileName);
				merchantTrainingForUpdate.setIsPublish(merchantTraining.getIsPublish());
//				merchantTraining.setFileUrl(relativePath+tempFileName);
				service.updateByPrimaryKeySelective(merchantTrainingForUpdate);
				
			}catch(Exception e ){
		    	message = getStackTrace(e);
		    	processResult = new ProcessResult(merchantTraining, filePath,ftpFilePath, type, false,message);
		    	return	processResult;
			}finally{
				
				 if(null!=file){
					 file.delete();
				 }
				 if(null!=afterFile){
					 afterFile.delete();
				 }
				 
				 try {
					// step 00 失败则更新previewUrl=0
					if( null!=processResult && !processResult.isSuccess() ){
						MerchantTraining merchantTrainingForUpdate = new MerchantTraining ();
						merchantTrainingForUpdate.setId( merchantTraining.getId() );
						merchantTrainingForUpdate.setPreviewUrl( Constant.FAIL_TAG );
						
						service.updateByPrimaryKeySelective(merchantTrainingForUpdate);
						// step4  失败则写入日志；
						service.insertFailLog( processResult.getFailLog() );
					}
				 } catch (Exception e) {
						e.printStackTrace();
						message = "文件转换失败后写入数据库，发生异常，请确认该课程是否已被删除！异常信息： "+e ;
				    	processResult = new ProcessResult(merchantTraining, filePath ,ftpFilePath, type, false,message);
				 }finally{
					  // step0  删除redis缓存记录
						Map<String,String> getMap = 
								(Map<String,String>)this.redisTemplate.opsForValue().get(Constant.PROCESS_POOL_REDIS_KEY);
						if( null!=getMap ){
							getMap.remove(merchantTraining.getId());
							this.redisTemplate.opsForValue().set(Constant.PROCESS_POOL_REDIS_KEY, getMap);
						}
				 }
				
			}
			
			// step4  sucess over!
			return processResult;
		}
		
		/**
		 * 获取异常的堆栈信息
		 * 
		 * @param t
		 * @return
		 */
		private static String getStackTrace(Throwable t)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			try
			{
				t.printStackTrace(pw);
				return sw.toString();
			}
			finally
			{
				pw.close();
			}
		}

		
		public boolean transferFileForView(String filePath, String suffix) throws Exception {
			   if( suffix.endsWith("pdf")){
				   return transferPdfToSwf(filePath);
			   }else{
				   return transferToSwf(filePath);
			   }
				
		}
		
		private boolean transferPdfToSwf(String filePath) throws Exception {
			
			DocConverter d = new DocConverter( filePath );
			
			return d.converPdfToSwf();
		}


		private boolean transferToSwf(String filePath ) throws Exception {
			
			DocConverter d = new DocConverter( filePath );
			//调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;
			return d.conver();
		}


		
//		public static void main(String [] args ){
//			String fileFtpUrl= "http://wew.9090/uni/vvv/123.ppt";
//			System.out.println(fileFtpUrl.substring(fileFtpUrl.lastIndexOf("/")+1));;
//			
//		}
	}

