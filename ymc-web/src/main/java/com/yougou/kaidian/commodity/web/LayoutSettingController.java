package com.yougou.kaidian.commodity.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.commodity.model.vo.LayoutTemplate;
import com.yougou.kaidian.commodity.service.ILayoutSettingService;
import com.yougou.kaidian.commodity.thrift.LayoutSettingThriftService;
import com.yougou.kaidian.commodity.util.FSSFilePublisher;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.picture.Picture;


/**
 * 商家中心关联版式设置
 * @author zhang.f1
 *
 */
@Controller
@RequestMapping("/layout_setting")
public class LayoutSettingController {
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private ILayoutSettingService layoutSettingService;
	
	@Value("#{commodityPicsSettings['image.cdn.server']}")
	public String imageFtpServer;	
	
	@Resource
	private FSSFilePublisher fssFilePublisher;
	
	 /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory
            .getLogger(LayoutSettingController.class);
	
	/**
	 * 去到版式设置页面
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/go_layout_setting")
	public String goLayoutSetting(HttpServletRequest request,String type,ModelMap modelMap) {
		
		if(!StringUtil.isBlank(type) && Constant.LAYOUT_SET_TEMPLATE_TYPE_FIXED.equals(type)){
			return "/manage/layout_setting/commodity_layout_setting2";
		}
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<LayoutTemplate> tempList = layoutSettingService.queryLayoutTemplate(merchantCode);
		modelMap.put("type", type);
		modelMap.put("tempList", tempList);
		return "/manage/layout_setting/commodity_layout_setting";
	}
	
	/**
	 * 新增版式
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addLayoutTemplate")
	public String addLayoutTemplate(HttpServletRequest request,LayoutTemplate template) {
		
		String id = UUIDGenerator.getUUID();
		template.setId(id);
		template.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		layoutSettingService.addLayoutTemplate(template);
		JSONObject json = new JSONObject();
		json.put("msg", "success");
		return json.toString();
	}
	
	
	/**
	 * 更新版式
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateLayoutTemplate")
	public String updateLayoutTemplate(HttpServletRequest request,LayoutTemplate template) {
		
		layoutSettingService.updateLayoutTemplate(template);
		JSONObject json = new JSONObject();
		json.put("msg", "success");
		return json.toString();
	}
	
	/**
	 * 删除版式
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteLayoutTemplate")
	public String deleteLayoutTemplate(HttpServletRequest request,LayoutTemplate template) {
		LayoutTemplate temp = layoutSettingService.queryLayoutTemplateById(template.getId());
		//将模板静态页内容清除
		if(temp!=null){			
			fssFilePublisher.setStaticHtml(null, temp.getHtmlFilePath());			
		}
		//删除版式及
		layoutSettingService.deleteLayoutTemplate(template.getId());
		JSONObject json = new JSONObject();
		json.put("msg", "success");
		return json.toString();
	}
	
	/**
	 * 查询自定义版式
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryLayoutTemplate")
	public String queryLayoutTemplate(HttpServletRequest request,LayoutTemplate template) {
		
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();		
		List<LayoutTemplate> tempList = layoutSettingService.queryLayoutTemplate(merchantCode);
		JSONObject json = new JSONObject();
		json.put("tempList", tempList);
		return json.toString();
	}
	
	/**
	 * 查询自定义版式 by id
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryLayoutTemplateById")
	public String queryLayoutTemplateById(HttpServletRequest request,LayoutTemplate template) {	
		LayoutTemplate temp = layoutSettingService.queryLayoutTemplateById(template.getId());
		JSONObject json = new JSONObject();
		json.put("temp", temp);
		return json.toString();
	}
	
	
	/**
	 * 下载选择商品的excel模板
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downLoadLayoutSettingExcel")
	public String downLoadExcelTemplate(HttpServletRequest request, HttpServletResponse response){
		int rowIndex = 0, cellIndex = 0;
        XSSFWorkbook excel = new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet("商品编码");
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFRow row = sheet.createRow(rowIndex++);       
       // ExcelToDataModelConverter.createCell(row, cellIndex++, "商品编码");
        ExcelToDataModelConverter.createCell(row, cellIndex++, "商品编码",null,drawing,"输入正确的商品编码");
     
        OutputStream os = null;
        try {
            // 下载生成的模板
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", MessageFormat.format(
                    "attachment; filename=laout_setting_commodityNos_{0}.xlsx",
                    DateFormatUtils.format(new Date(), "yyyyMMdd")));
     
			os = response.getOutputStream();
	        excel.write(os);
        }catch (IOException e) {							
			logger.error("LayoutSettingController版式设置，商家中心版式设置生成指定商品的Excel 模板失败",e);
			e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
        
        return null;
	      
	}
	
	
	/**
	 * 导入的excel模板方式版式设置：部分商品
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/importLayoutSettingExcel")
	public String importLayoutSettingExcel(HttpServletRequest request,LayoutTemplate template,
			@RequestParam(value = "settingFile") MultipartFile multipartFile,ModelMap modelMap){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		template.setMerchantCode(merchantCode);
		//解析excel 导入的商品编码 
		Map<String,Object> resultMap = parseExcel(multipartFile);
		String errorCommodityNos = null;
		ArrayList<String> commodityNos = null;
		if(resultMap!=null && resultMap.size()>0){
			commodityNos = (ArrayList<String>) resultMap.get("commodityNos");
			errorCommodityNos = (String) resultMap.get("errorCommodityNos");		
			modelMap.put("errorCommodityNos", errorCommodityNos);
			// 保存版式设置
			layoutSettingService.layoutSettingForSomeCommoditys(template, commodityNos);
			
		}	
		String type = String.valueOf(template.getType());
		if(!StringUtil.isBlank(type) && Constant.LAYOUT_SET_TEMPLATE_TYPE_FIXED.equals(type)){
			return "/manage/layout_setting/commodity_layout_setting2";
		}

		List<LayoutTemplate> tempList = layoutSettingService.queryLayoutTemplate(merchantCode);
		modelMap.put("tempList", tempList);
		
		return "/manage/layout_setting/commodity_layout_setting";
	}
	
	/**
	 * 输出导入Excel 失败的商品编码excel 文件
	 * @param response
	 * @param errorCommodityNos
	 */
	@RequestMapping("/downLoadErrorCommodityNos")
	public void downLoadErrorCommodityNos( HttpServletResponse response,HttpServletRequest request,@RequestParam(value="errorCommodityNos")String errorCommodityNos) {

		String[] errorCommodityNoArr = errorCommodityNos.split(","); 
		int rowIndex = 0, cellIndex = 0;
	    XSSFWorkbook excel = new XSSFWorkbook();
	    XSSFSheet sheet = excel.createSheet("商品编码");
	    XSSFDrawing drawing = sheet.createDrawingPatriarch();
	    XSSFRow row = sheet.createRow(rowIndex++);       
	    ExcelToDataModelConverter.createCell(row, cellIndex++, "导入失败的商品编码",null,drawing,"导入的商品编码不存在或者不是你的商品");
	    //每行的列创建完后，指标回到起点0
	    cellIndex=0;
		for(String commodityNo : errorCommodityNoArr){
			  XSSFRow row_ = sheet.createRow(rowIndex++);       
		      ExcelToDataModelConverter.createCell(row_, cellIndex++, commodityNo);		
		      cellIndex = 0;
		}
		OutputStream os = null;
	    try {
	        // 下载生成的模板
	        response.reset();
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition","attachment; filename=layout_set_errorCommodityNos.xlsx");
	 
			os = response.getOutputStream();
	        excel.write(os);
	    }catch (IOException e) {
							
			logger.error("LayoutSettingController版式设置，商家中心版式设置，生成错误的商品编码Excel 模板失败");
			e.printStackTrace();
	    } finally {
	        IOUtils.closeQuietly(os);
	    }
			
		return;
		
	}
	
	/**
	 * 解析导入的商品编码Excel 文件
	 * @param multipartFile
	 */
	private Map<String,Object> parseExcel(MultipartFile multipartFile) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		logger.warn("LayoutSettingController Excel导入校验设置版式的商品，商家编码：{}",merchantCode);
		XSSFWorkbook workBook = null;
		 // 定义集合对象存放解析Excel获取的商品编码
        ArrayList<String> commodityNos =new ArrayList<String>(); 
        // 定义集合对象存放解析Excel获取的有问题的商品编码
        //ArrayList<String> errorCommodityNos =new ArrayList<String>(); 
        StringBuffer sbf = new StringBuffer();
        String errorCommodityNos = new String();
		try {		
			if(multipartFile!=null && multipartFile.getSize()>0){
				workBook = new XSSFWorkbook(multipartFile.getInputStream());
			}
		} catch (IOException e) {
			logger.error("LayoutSettingController版式设置，商家中心读取上传选择商品的excel文件失败!",e);
			e.printStackTrace();
		}
        if(workBook!=null){
            Sheet sheet = workBook.getSheetAt(0);           
            if (sheet != null) {  		           
	            // 循环行Row  
	            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {  
	                Row row = sheet.getRow(rowNum);  
	                if (row == null) {  
	                    continue;  
	                }  
	                  
	                // 循环列Cell  		               
	                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {  
	                    Cell cell = row.getCell(cellNum);  
	                    if (cell == null) {  
	                        continue;  
	                    }  
	                    cell.setCellType(Cell.CELL_TYPE_STRING);
	                    String commodityNo = cell.getStringCellValue();
	                    if(StringUtils.isNotBlank(commodityNo)){
	                    	commodityNo = commodityNo.trim();
	                    	logger.warn("LayoutSettingController Excel导入校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口，商品编码：{}",commodityNo);
		                    Commodity commodity = commodityBaseApiService.getCommodityByNo(commodityNo);
		    				// 根据商品编码未获取到信息或者获取到的商品不属于当前商家
		    				if(null == commodity || !commodity.getMerchantCode().equals(merchantCode)){
		    					//errorCommodityNos.add(commodityNo);
		    					sbf.append(commodityNo).append(",");
		    					logger.error("LayoutSettingController Excel导入校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口未获取到商品信息获取商家编码不匹配当前商家");
		    					continue;
		    				}
		    				
		    				if(null != commodity){
								logger.warn("LayoutSettingController Excel导入校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口，获取到的商家编码：{}",commodity.getMerchantCode());
							}
										
		                    commodityNos.add(commodityNo);  
	                    }else{
	                    	logger.error("LayoutSettingController Excel导入方式进行版式设置第  {} 行；第 {} 列商品编码为空",rowNum,(cellNum+1));
	                    }
	                }  
	            }  
            }  	        
        }
        if(sbf.length()>0){
        	errorCommodityNos = sbf.substring(0, sbf.length()-1);
        }
        resultMap.put("commodityNos", commodityNos);
        resultMap.put("errorCommodityNos", errorCommodityNos);
        return resultMap;
	}
	
	/**
	 * 普通方式版式设置：部分商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("/layoutSettingForSomeCommoditys")
	public String layoutSettingForSomeCommoditys(HttpServletRequest request,LayoutTemplate template,ModelMap modelMap){
		String commodityNo = request.getParameter("commodityNos");
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		template.setMerchantCode(merchantCode);
		if(!StringUtil.isBlank(commodityNo)){
			commodityNo = commodityNo.trim();
			String[] commodityNos = commodityNo.split(","); 
			ArrayList<String> commodityNoList = new ArrayList<String>();
			Collections.addAll( commodityNoList,commodityNos);
			layoutSettingService.layoutSettingForSomeCommoditys(template, commodityNoList);
		}
		
		String type = String.valueOf(template.getType());
		if(!StringUtil.isBlank(type) && Constant.LAYOUT_SET_TEMPLATE_TYPE_FIXED.equals(type)){
			return "/manage/layout_setting/commodity_layout_setting2";
		}
		List<LayoutTemplate> tempList = layoutSettingService.queryLayoutTemplate(merchantCode);
		modelMap.put("tempList", tempList);
		return "/manage/layout_setting/commodity_layout_setting";
	      
	}
	
	/**
	 * 针对所有商品的版式设置
	 * @param request
	 * @param response
	 */
	@RequestMapping("/layoutSettingForAllCommoditys")
	public String layoutSettingForAllCommoditys(HttpServletRequest request,LayoutTemplate template,ModelMap modelMap){
	
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		layoutSettingService.layoutSettingForAllCommoditys(template, merchantCode);
		
		String type = String.valueOf(template.getType());
		if(!StringUtil.isBlank(type) && "0".equals(type)){
			return "/manage/layout_setting/commodity_layout_setting2";
		}
		List<LayoutTemplate> tempList = layoutSettingService.queryLayoutTemplate(merchantCode);
		modelMap.put("tempList", tempList);
		return "/manage/layout_setting/commodity_layout_setting";
	      
	}
	
	
	/**
	 * 预览版式设置
	 * @param request
	 * @param response
	 */
	@RequestMapping("/previewLayoutSetting")
	public String previewLayoutSetting(HttpServletRequest request,ModelMap modelMap){
		String layoutHtml = request.getParameter("layoutHtml");
		modelMap.put("layoutHtml", layoutHtml);
		return "layoutSettingPreview";
	      
	}
	
	/**
	 * 校验输入的商品编码是否存在且为当前商家的商品
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCommodityNo")
	public String checkCommodityNo(HttpServletRequest request){
		String commodityNos = request.getParameter("commodityNos");
		// 记录有问题的商品编码
		String errorMsg = "";
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		logger.warn("LayoutSettingController校验设置版式的商品，商家编码：{}" , merchantCode);
		if(!StringUtil.isBlank(commodityNos)){
			String[] commArr = commodityNos.split(",");
			for(String commodityNo : commArr){
				if(StringUtils.isNotBlank(commodityNo)){
					commodityNo = commodityNo.trim();
					logger.warn("LayoutSettingController校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口，商品编码：{}",commodityNo);
					Commodity commodity = commodityBaseApiService.getCommodityByNo(commodityNo);
					// 根据商品编码未获取到信息或者获取到的商品不属于当前商家
					if(null == commodity || !commodity.getMerchantCode().equals(merchantCode)){
						errorMsg += commodityNo+",";
						logger.error("LayoutSettingController校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口未获取到商品信息获取商家编码不匹配当前商家");
					}
					if(null != commodity){
						logger.warn("LayoutSettingController校验设置版式的商品，调用货品commodityBaseApiService.getCommodityByNo接口，获取到的商家编码：{}",commodity.getMerchantCode());
					}
				}else{
					errorMsg += commodityNo+",";
				}
			}
		}
		if(errorMsg.length()>0){
			errorMsg = errorMsg.substring(0, errorMsg.length()-1);
		}
		
		JSONObject json =  new JSONObject();
		json.put("errorMsg", errorMsg);
		
		return json.toString();
	      
	}
	

	/**
	 * 固定版式获取版式内8个商品的信息，填充版式模板
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fixedLayoutSetting")
	public String fixedLayoutSetting(HttpServletRequest request,ModelMap modelMap) {
		String commodityNos = request.getParameter("commodityList");
		JSONObject json = new JSONObject();
		if(!StringUtil.isBlank(commodityNos)){
			commodityNos = commodityNos.trim();
			String[] commodityNoArr = commodityNos.split(","); 
			ArrayList<String> list = new ArrayList<String>();
			Collections.addAll(list,commodityNoArr );
			
			// 批量查询商品单品页链接
			Map<String,String> pageMap = commodityBaseApiService.batchFullCommodityPageUrlWithExtension(list);
			// 封装页面展示的固定模板所需数据：优购价，商品图片，单品页链接
			List<Map<String,Object>> commList = new ArrayList<Map<String,Object>>();
			//封装异常信息
			String errorMsg = "";
			for(String commodityNo : commodityNoArr){
				
				Commodity commodity = commodityBaseApiService.getCommodityByNo(commodityNo, false, true, false);				
				if(commodity!=null){		
					Map<String,Object> commMap = new HashMap<String,Object>();
					commMap.put("commodityNo", commodityNo);
					commMap.put("yougouPrice", commodity.getSellPrice());
					commMap.put("pageUrl", pageMap.get(commodityNo));
					List<Picture> pictures = commodity.getPictures();
					for(Picture pic : pictures){
						String picType = pic.getPicType();
						if(picType.equals("ms")){
							String firstImg = pic.getPicPath();
							String firstImgName = pic.getPicName();
							String imgUrl = this.imageFtpServer+"/pics"+firstImg+firstImgName;
							if(imgUrl.indexOf("http")==-1){
								imgUrl = "http://"+imgUrl;
							}
							commMap.put("imgUrl", imgUrl);
							break;
						}						
					}		
					commList.add(commMap);
				}else{
					errorMsg += commodityNo+",";
					logger.error("商家中心关联版式设置，固定版式获取商品图片时商品编码{}调用commodityBaseApiService.getCommodityByNo 接口未获取到数据", commodityNo);
				}				
			}
			if(errorMsg.length()>0){
				errorMsg = errorMsg.substring(0,errorMsg.length()-1);
			}
			json.put("commList", commList);
			json.put("errorMsg", errorMsg);			
		}else{
			json.put("errorMsg", "商品编码为空");
		}
		
		return json.toString();
	}
	
	@RequestMapping("/initAllUsingLayoutTemplate")
	@ResponseBody
	public String initAllUsingLayoutTemplate() throws IOException{
		layoutSettingService.initAllUsingLayoutTemplate();
		return "success";
	}
	
	
	
	/*@RequestMapping("/staticHtml")
	@ResponseBody
	public String staticHtml() throws IOException{
		String dateString = "2015/12/11";
		String shopId = "test";
		String filePath = Constant.FILE_NAME_URL_PATH + dateString + "/" + shopId + ".shtml";
		List<String> ips = fssFilePublisher.parseIPs(this.STATIC_SERVER_IPS);
		String htmlContent = "<a>商家中心关联版式设置优化test</a>";
		byte[] bytes = htmlContent.getBytes("UTF-8"); 
		String newhtmlContent = new String(bytes);
		fssFilePublisher.publishSingleFileByJson(filePath, newhtmlContent, ips);
		return "success";
	}
	*/
	

	public static void main(String[] args) throws TException {
	//	String hostStr = "10.10.20.19";
    //	int port = 20883;
		/*String hostStr = "192.168.212.40";
	    int port = 9090;
        System.out.println("thrift client connext server at "+port+" port ");  
        TTransport transport = new TSocket(hostStr,port);
        transport.open();    
        TProtocol protocol = new TBinaryProtocol(transport);    
        CmsApi.Client client = new CmsApi.Client(protocol);    
        Map<String,String> params = new HashMap<String,String>();
        params.put("commodityNos", "100268680");
        //params.put("merchantCode", "SP20150427640144");
        int result = client.buildYmcPagesByParams(params);    
        System.out.println("result ==========="+result);
        transport.close();    
        System.out.println("thrift client close connextion");  */
        
        
        
        TTransport transport;    
        try {    
     
        	/*String hostStr = "10.10.20.91";
        	int port = 7788;*/
        	String hostStr = "10.0.20.142";
        	int port = 7788;
            System.out.println("thrift client connext server at "+port+" port ");  
            transport = new TSocket(hostStr,port);
            transport.open();    
            TProtocol protocol = new TBinaryProtocol(transport);    
            LayoutSettingThriftService.Client client = new LayoutSettingThriftService.Client(protocol);    
            
            System.out.println("htmlFilePath................"+client.newGetLayoutSetting("100255557"));    
            System.out.println("layoutHtml................"+client.getLayoutSetting("100255557"));   
            transport.close();    
            System.out.println("thrift client close connextion");  
           
        } catch (TTransportException e) {    
            e.printStackTrace();    
          
        } catch (TException e) {    
            e.printStackTrace();    
          
        }    
			
		
	}


	
	
}
