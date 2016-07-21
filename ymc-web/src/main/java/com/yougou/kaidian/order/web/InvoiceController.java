package com.yougou.kaidian.order.web;

import java.awt.Color;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yougou.component.area.api.IAreaApi;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.ordercenter.api.invoice.IInvoiceNewApi;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.common.Query;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderInvoiceDetailNew;
import com.yougou.ordercenter.model.order.OrderInvoiceNew;
import com.yougou.ordercenter.vo.order.InvoiceNewDeliveryVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.product.Product;

/**
 * 发票查询控制类
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private final static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Resource
    private IInvoiceNewApi iInvoiceNewApi;
    @Resource
    private IQualityService iQualityService;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    @Resource
    private IAreaApi areaApi;
    @Resource
    private IMerchantsApi iMerchantsApi;
    @Resource
    private IOrderApi OrderApi;

    /**
     * 发票列表查询 creator mei.jf
     * 
     * @param model
     * @param vo
     * @param Query
     * @param request
     * @return
     */
    @RequestMapping("/query")
    public String qualityList(ModelMap model, OrderInvoiceNew vo, Query query, HttpServletRequest request) {

        query.setPageSize(30);
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderSubNo())) {
            vo.setOrderSubNo(StringUtils.deleteWhitespace(vo.getOrderSubNo()));
        }

        // 去掉OrderMainNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderMainNo())) {
            vo.setOrderMainNo(StringUtils.deleteWhitespace(vo.getOrderMainNo()));
        }
        // 去掉InvoiceNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getInvoiceNo())) {
            vo.setInvoiceNo(StringUtils.deleteWhitespace(vo.getInvoiceNo()));
        }
        boolean all_flag = false;
        if (null == vo.getInvoiceStatus()) {
            vo.setInvoiceStatus(2);
        } else if (-1 == vo.getInvoiceStatus()) {
            vo.setInvoiceStatus(null);
            all_flag = true;
        }

        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        PageFinder<OrderInvoiceNew> pageFinder = null;
        try {
            // 默认设置查询起止时间
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isEmpty(vo.getInvoiceCreateTimeStart()) && StringUtils.isEmpty(vo.getInvoiceCreateTimeEnd())) {
                vo.setInvoiceCreateTimeStart(DateUtil2.format1(DateUtils.addDays(new Date(), -30)));
                vo.setInvoiceCreateTimeEnd(DateUtil2.format1(new Date()));
            } else if (StringUtils.isEmpty(vo.getInvoiceCreateTimeStart())) {
                Date invoiceCreateTimeEnd = format.parse(vo.getInvoiceCreateTimeEnd());
                vo.setInvoiceCreateTimeStart(DateUtil2.format1(DateUtils.addDays(invoiceCreateTimeEnd, -30)));
            } else if (StringUtils.isEmpty(vo.getInvoiceCreateTimeEnd())) {
                Date invoiceCreateTimeStart = format.parse(vo.getInvoiceCreateTimeStart());
                vo.setInvoiceCreateTimeEnd(DateUtil2.format1(DateUtils.addDays(invoiceCreateTimeStart, 30)));
            }
            logger.info("发票列表查询-输入参数 query页码:{}-输入参数orderInvoiceNewVo:{}", query.getPage(),vo);
            pageFinder = iInvoiceNewApi.queryMerchantInvoiceByPageFinder(query, vo);
            String orderMainNo_old = "";
            if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
            	logger.info("发票列表查询-返回结果 共{}条", pageFinder.getData().size());
                for (OrderInvoiceNew orderInvoiceNew : pageFinder.getData()) {
                    if (orderMainNo_old.equals(orderInvoiceNew.getOrderMainNo())) {
                        orderMainNo_old = orderInvoiceNew.getOrderMainNo();
                        orderInvoiceNew.setOrderMainNo("");
                    } else {
                        orderMainNo_old = orderInvoiceNew.getOrderMainNo();
                    }

                    // 计算未处理时长
                    if (orderInvoiceNew.getInvoiceStatus() == 2) {
                    	Double hourNumRemain = iMerchantsApi.getShipmentCountdownHour(orderInvoiceNew.getCreateTime(), new Date());
                    	logger.info("计算出当前时间到某个时间发货有效时间-{}->{},结果：{} ",
                    			new Object[]{orderInvoiceNew.getCreateTime(),new Date(),hourNumRemain} );
                    	String hourNumRemainString = shipmentCountdownHour(hourNumRemain);
                        orderInvoiceNew.setHourNumRemain(hourNumRemainString);
                    } else {
                        orderInvoiceNew.setHourNumRemain("-");
                    }
                }
            }

        } catch (Exception e) {
            logger.error("查询商家发票时产生异常:", e);
        }
        if (all_flag) {
            vo.setInvoiceStatus(-1);
        }
        model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return "/manage/order/invoice_list";
    }

    private String shipmentCountdownHour(Double hourNumRemain) {
        int hour = (int) Math.floor(Math.abs(hourNumRemain) % 24);
        return (hourNumRemain > 0 ? "" : "超时") + (int) Math.floor(Math.abs(hourNumRemain) / 24) + "天" + hour + "小时";
    }
   
    
    
 	@ResponseBody
    @RequestMapping("/queryInvoiceCount")
    public String queryInvoiceCount(ModelMap model,HttpServletRequest request){
 		
 		
 		Query query=new  Query();
 		JSONObject object = new JSONObject();
 		Map<String,Integer> map =new HashMap<String, Integer>();
 		OrderInvoiceNew vo=new OrderInvoiceNew();
 		vo.setInvoiceStatus(2);
 		vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
 		// 默认设置查询起止时间
       
        vo.setInvoiceCreateTimeStart(DateUtil2.format1(DateUtils.addDays(new Date(), -30)));
         vo.setInvoiceCreateTimeEnd(DateUtil2.format1(new Date()));
         logger.info("queryInvoiceCount-输入参数orderInvoiceNewVo:{}",vo);
 		try {
			PageFinder<OrderInvoiceNew>   pageFinder = iInvoiceNewApi.queryMerchantInvoiceByPageFinder(query, vo);
			if(pageFinder!=null){
				logger.info("queryInvoiceCount-返回结果 共{}条", pageFinder.getData().size());
				map.put("invoiceCount", pageFinder.getRowCount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 		object.putAll(map);
 		return  object.toString();
 		
 	}
    /**
     * 配送信息保存
     * 
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    public String save(ModelMap model, Query query, InvoiceNewDeliveryVo vo, HttpServletRequest request) throws Exception {
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        String merchantName =YmcThreadLocalHolder.getMerchantName();
        if (StringUtils.isBlank(merchantCode)) {
            return "请先登录！商家编号不能为空！";
        }
        if (StringUtils.isBlank(merchantName)) {
            return "商家名称不能为空！";
        }
       
        try {
            OrderInvoiceNew invoiceDetail = iInvoiceNewApi.getOrderInvoiceNewById(vo.getInvoiceId());
            logger.info("根据发票id查询发票信息,输入参数- {},结果 - {} ", vo.getInvoiceId(),invoiceDetail );
            if (11 == invoiceDetail.getInvoiceStatus()) {
                return "该发票已经被优购客服拦截，请不要配送该发票！";
            } else if (2 != invoiceDetail.getInvoiceStatus()) {
                return "该发票不是处于待商家处理状态，暂时不能配送该发票！";
            } else {
                vo.setSendTime(new Date());
                vo.setPostageOnDelivery(0);
                Date curDate = new Date();
                logger.info("wms回写发票配送信息:{} -{} -{}",
                			new Object[]{vo,merchantName,curDate});
                iInvoiceNewApi.updateInvoiceDeliveryInfo(vo, merchantName, curDate);
            }

        } catch (Exception e) {
            logger.error("配送信息保存时产生异常:", e);
            return "配送信息保存时产生异常！";
        }
        return "true";
    }

    /**
     * 发票详情查询 creator meijunfeng create time 2014-04-25 上午10:08:32
     * 
     * @param model
     * @param vo
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail")
    public String detail(ModelMap model, OrderInvoiceNew vo, HttpServletRequest request) throws Exception {
        // 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("商家编号不能为空！");
        }
        OrderInvoiceNew invoiceDetail = iInvoiceNewApi.getOrderInvoiceNewById(vo.getId());
        logger.info("根据发票id查询发票信息,输入参数- {}, 结果- {} ", vo.getInvoiceId(),invoiceDetail );
        List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
        Product product = null;
        OrderDetail4sub orderDetail4sub = null;
        for (OrderInvoiceDetailNew detail : invoiceDetail.getOrderInvoiceDetailNews()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("prodNo", detail.getProdNo());
            map.put("prodName", detail.getProdName());
            map.put("prodType", detail.getProdType());
            map.put("units", detail.getUnits());
            map.put("brandSpec", detail.getBrandSpec());
            map.put("commodityNum", detail.getCommodityNum());
            map.put("invoiceAmount", detail.getInvoiceAmount());
            map.put("postageCost", detail.getPostageCost());
            map.put("unitPrice", detail.getUnitPrice());
            product = commodityBaseApiService.getProductByNo(detail.getProdNo(), true);
            String staticUrl = "";
            String picUrl = "";
            if( null!=product && null!=product.getCommodity() ){
	            Commodity commodity =  product.getCommodity();
	            picUrl = commodity.getPicSmall();
	            logger.info("根据产品编号得到产品信息及商品信息,输入参数- {}-TRUE, 查到的商品- {} ",detail.getProdNo(),commodity );
	            if(  null!=commodity.getCommodityNo() ){
	            	String commodityNo = commodity.getCommodityNo();
	            	staticUrl = commodityBaseApiService.getFullCommodityPageUrl(commodityNo);
	            	logger.info("获取单品页全链接,输入参数- {}, 结果- {} ",commodityNo,staticUrl );
	            }
            }
            map.put("static_url", staticUrl);
            map.put("picUrl", picUrl);

            orderDetail4sub = OrderApi.getOrderDetail4subById(detail.getOrderSubDetailId());
            logger.info("通过id得到订单明细对象,输入参数- {}, 结果- {} ",detail.getOrderSubDetailId(),orderDetail4sub );
            map.put("bugNum", orderDetail4sub.getCommodityNum());
            // 成交价/数量
            map.put("price", orderDetail4sub.getProdTotalAmt() / orderDetail4sub.getCommodityNum());
            detailList.add(map);
        }

        invoiceDetail.setProvince(areaApi.getAreaByNo(invoiceDetail.getProvince()).getName());
        invoiceDetail.setCity(areaApi.getAreaByNo(invoiceDetail.getCity()).getName());
        invoiceDetail.setArea(areaApi.getAreaByNo(invoiceDetail.getArea()).getName());
        model.addAttribute("orderInvoiceDetailNews", detailList);
        model.addAttribute("orderInvoiceExtNew", invoiceDetail.getOrderInvoiceExtNew());
        model.addAttribute("invoiceDetail", invoiceDetail);
        return "/manage/order/invoice_detail";
    }

    /**
     * 导出发票明细
     * 
     * @param OrderInvoiceNew
     * @param request
     */
    @RequestMapping("doExportInvoice")
    public void doExportInvoice(OrderInvoiceNew vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if( StringUtils.isEmpty(merchantCode) ){
        	throw new Exception("请先登录！");
        }
        // 默认设置查询起止时间
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(vo.getInvoiceCreateTimeStart()) && StringUtils.isEmpty(vo.getInvoiceCreateTimeEnd())) {
            vo.setInvoiceCreateTimeStart(DateFormat.getDateInstance(DateFormat.MEDIUM).format(DateUtils.addDays(new Date(), -30)));
            vo.setInvoiceCreateTimeEnd(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
        } else if (StringUtils.isEmpty(vo.getInvoiceCreateTimeStart())) {
            Date invoiceCreateTimeEnd = format.parse(vo.getInvoiceCreateTimeEnd());
            vo.setInvoiceCreateTimeStart(DateFormat.getDateInstance(DateFormat.MEDIUM).format(DateUtils.addDays(invoiceCreateTimeEnd, -30)));
        } else if (StringUtils.isEmpty(vo.getInvoiceCreateTimeEnd())) {
            Date invoiceCreateTimeStart = format.parse(vo.getInvoiceCreateTimeStart());
            vo.setInvoiceCreateTimeEnd(DateFormat.getDateInstance(DateFormat.MEDIUM).format(DateUtils.addDays(invoiceCreateTimeStart, 30)));
        }
        boolean all_flag = false;
        if (null == vo.getInvoiceStatus()) {
            vo.setInvoiceStatus(2);
        } else if (-1 == vo.getInvoiceStatus()) {
            vo.setInvoiceStatus(null);
            all_flag = true;
        }
        List<Map<String, Object>> list = iInvoiceNewApi.queryMerchantInvoiceList(vo);
        if (all_flag) {
            vo.setInvoiceStatus(-1);
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new Exception("没有数据可导出");
        }

        List<Object[]> rowDataList = new ArrayList<Object[]>();
        for (Map<String, Object> map : list) {
            int index = 0;
            Object[] rowData = new Object[2 == vo.getInvoiceStatus() ? 8 : 11];
            rowData[index++] = map.get("order_main_no");
            rowData[index++] = map.get("create_time");
            if (2 != vo.getInvoiceStatus()) {
                rowData[index++] = map.get("invoice_no");
            }
            if ("1".equals(map.get("invoice_type").toString())) {
                rowData[index++] = "普通发票";
            } else {
                rowData[index++] = "增值税发票";
            }
            rowData[index++] = map.get("invoice_title");
            rowData[index++] = map.get("invoice_amount");
            rowData[index++] = map.get("acreate_time");
            if ("2".equals(map.get("invoice_status").toString())) {
                rowData[index++] = "客服已审核";
            } else if ("7".equals(map.get("invoice_status").toString())) {
                rowData[index++] = "已配送";
            } else if ("10".equals(map.get("invoice_status").toString())) {
                rowData[index++] = "已作废";
            } else if ("11".equals(map.get("invoice_status").toString())) {
                rowData[index++] = "发票拦截";
            } else {
                rowData[index++] = "";
            }
            if (2 != vo.getInvoiceStatus()) {
                if ("1".equals(map.get("ship_method").toString())) {
                    rowData[index++] = "邮政挂号信";
                } else if ("2".equals(map.get("ship_method").toString())) {
                    rowData[index++] = "快递";
                } else {
                    rowData[index++] = "";
                }
                rowData[index++] = map.get("express_code");
            }
            rowData[index++] = map.get("consignee_name");
            rowDataList.add(rowData);
        }

        int rowIndex = 0, cellIndex = 0, index = 0;
        XSSFWorkbook excel = new XSSFWorkbook();
        // 创建样式
        XSSFCellStyle cellStyle = excel.createCellStyle();
        XSSFFont font = excel.createFont();
        font.setBold(true);
        font.setColor(new XSSFColor(Color.RED));
        cellStyle.setFont(font);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);

        XSSFCellStyle bgCellStyle = excel.createCellStyle();
        XSSFFont bgfont = excel.createFont();
        bgfont.setBold(true);
        bgCellStyle.setFont(bgfont);
        bgCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        bgCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);

        XSSFSheet sheet = excel.createSheet();
        XSSFRow row = sheet.createRow(rowIndex++);
        // XSSFDrawing drawing = sheet.createDrawingPatriarch();
        ExcelToDataModelConverter.createCell(row, cellIndex++, "优购订单号", bgCellStyle);
        ExcelToDataModelConverter.createCell(row, cellIndex++, "下单日期", bgCellStyle);
        if (2 != vo.getInvoiceStatus()) {
            ExcelToDataModelConverter.createCell(row, cellIndex++, "发票号", bgCellStyle);
        }

        ExcelToDataModelConverter.createCell(row, cellIndex++, "发票类型", bgCellStyle);
        ExcelToDataModelConverter.createCell(row, cellIndex++, "发票抬头", bgCellStyle);
        ExcelToDataModelConverter.createCell(row, cellIndex++, "开票金额", bgCellStyle);
        ExcelToDataModelConverter.createCell(row, cellIndex++, "发票创建时间", bgCellStyle);
        ExcelToDataModelConverter.createCell(row, cellIndex++, "发票状态", bgCellStyle);
        if (2 != vo.getInvoiceStatus()) {
            ExcelToDataModelConverter.createCell(row, cellIndex++, "配送方式", bgCellStyle);
            ExcelToDataModelConverter.createCell(row, cellIndex++, "配送单号", bgCellStyle);
        }
        ExcelToDataModelConverter.createCell(row, cellIndex++, "收件人", bgCellStyle);

        for (Object[] rowData : rowDataList) {
            cellIndex = 0;
            index = 0;
            row = sheet.createRow(rowIndex++);
            // CellStyle cellStyle
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            if (2 != vo.getInvoiceStatus()) {
                ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            }
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            if (2 != vo.getInvoiceStatus()) {
                ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
                ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));
            }
            ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(rowData[index++]));

        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        if (2 != vo.getInvoiceStatus()) {
            sheet.autoSizeColumn(2);
        }
        sheet.setColumnWidth(3, 6 * 2 * 256);
        sheet.setColumnWidth(4, 6 * 2 * 256);
        sheet.setColumnWidth(5, 5 * 2 * 256);
        sheet.autoSizeColumn(6);
        sheet.setColumnWidth(7, 6 * 2 * 256);
        if (2 != vo.getInvoiceStatus()) {
            sheet.setColumnWidth(8, 6 * 2 * 256);
            sheet.autoSizeColumn(9);
        }
        sheet.setColumnWidth(10, 6 * 2 * 256);

        OutputStream os = null;
        try {
            // 下载生成的模板
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename=order_detail_{0}.xlsx", DateFormatUtils.format(new Date(), "yyyyMMdd")));
            os = response.getOutputStream();
            excel.write(os);
        } finally {
            IOUtils.closeQuietly(os);
        }
        logger.info(MessageFormat.format("商家[{0}]导出发票明细数量：{1} .", new Object[] { merchantCode, rowDataList.size() }));
    }
}
