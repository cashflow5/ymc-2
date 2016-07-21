package com.yougou.kaidian.commodity.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.model.vo.MessageBean;

public interface IImageService {

	public MessageBean checkImage(File imageFile,int imgtype,MessageBean message) throws Exception;
	public boolean ftpUpload(File imgfile, String ftpServerPath) throws Exception;
	public boolean ftpUpload(File imgfile,String ftpfilename, String ftpServerPath) throws Exception;
	
	public boolean ftpUpload(InputStream is, String ftpfilename, String ftpServerPath) throws Exception;
	public boolean uploadCommodityDescPic2TemporarySpace(MultipartFile multipartFile, String merchantcode,String catalogId,String shopId) throws Exception;
	
	public List<ErrorVo> verifyCommodityProdDesc(CommoditySubmitVo submitVo,List<ErrorVo> errorList) throws Exception;
}
