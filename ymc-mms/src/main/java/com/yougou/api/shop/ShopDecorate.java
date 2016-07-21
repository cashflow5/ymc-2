package com.yougou.api.shop;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.model.vo.ResultMsg;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.fss.api.vo.PageFinder;
import com.yougou.fss.api.vo.ShopRuleVO;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;

/**
 * 店铺装修
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/merchant/shop/decorate/bak")
public class ShopDecorate {

    private final String BASE_PATH = "yitiansystem/merchant/shop/";

    private static final Logger logger = Logger.getLogger(ShopDecorate.class);
    @Resource
    private IFSSYmcApiService iFSSYmcApiService;

    @Resource
    private ISupplierService iSupplierService;
    @Resource
    private ICommodityBaseApiService iCommodityBaseApiService;

    /**
     * 店铺列表查询
     * 
     * @param modelMap
     * @param vo
     * @param query
     * @return
     */
    @RequestMapping("/shoplist")
    public String shopList(ModelMap model, ShopVO vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) {
        FSSResult result = iFSSYmcApiService.getShopList(vo, query.getPage(), query.getPageSize(), null, null);
        PageFinder pageFinder = (PageFinder) result.getData();
        for (ShopVO shopVO : (List<ShopVO>) pageFinder.getData()) {
            if (StringUtils.isNotEmpty(shopVO.getBrandIds())) {
            	Brand brand = iCommodityBaseApiService.getBrandById(shopVO.getBrandIds());
            	if(brand != null) {
            		shopVO.setBrandNames(brand.getBrandName());
            	}
            }
        }
        List<SupplierVo> supplierList = iSupplierService.getAllSupplier();
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("supplierList", supplierList);
        return BASE_PATH + "shop_list";
    }

    /**
     * 店铺审核列表查询
     * 
     * @param modelMap
     * @param vo
     * @param query
     * @return
     */
    @RequestMapping("/shop_audit")
    public String shopAudit(ModelMap model, ShopVO vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) {
        FSSResult result = iFSSYmcApiService.getShopList(vo, query.getPage(), query.getPageSize(), null, null);
        PageFinder pageFinder = (PageFinder) result.getData();
        for (ShopVO shopVO : (List<ShopVO>) pageFinder.getData()) {
            if (StringUtils.isNotEmpty(shopVO.getBrandIds())) {
            	Brand brand = iCommodityBaseApiService.getBrandById(shopVO.getBrandIds());
            	if(brand != null) {
            		shopVO.setBrandNames(brand.getBrandName());
            	}
            }
        }
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return BASE_PATH + "shop_audit_list";
    }

    /*
     * 创建或者更新店铺
     */
    @ResponseBody
    @RequestMapping("/create_shop")
    public String createShop(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        String structs = vo.getBrandNames();
        if (StringUtils.isNotBlank(structs)) {
            structs = StringUtils.substring(structs, 1);
        }
        for (String struct : structs.split(",")) {
            ShopRuleVO shopRuleVO = new ShopRuleVO();
            shopRuleVO.setBrandNo(struct.split(";")[0]);
            shopRuleVO.setCateNo(struct.split(";")[1]);
            vo.getShopRuleList().add(shopRuleVO);
        }
        vo.setBrandNames(null);
        try {
            vo.setShopStatus(0);
            vo.setAuditStatus(-1);
            vo.setLastUpdateUserId(operator);
            vo.setCreateDateTime(new Date());
            FSSResult result = iFSSYmcApiService.addOrUpdateShop(vo);
            resultMsg.setSuccess(true);
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /*
     * 更新店铺状态
     */
    @ResponseBody
    @RequestMapping("/update_shop_status")
    public String updateShopStatus(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        try {
            vo.setLastUpdateUserId(operator);
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.updateShopStatus(vo);
        } catch (Exception e) {
            logger.error("ShopId：" + vo.getShopId() + "更新店铺状态时发生异常！", e);
            return "fail";
        }
        return "success";
    }

    /*
     * 审核店铺状态
     */
    @ResponseBody
    @RequestMapping("/audit_shop")
    public String auditShop(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        try {
            vo.setLastUpdateUserId(operator);
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.auditShop(vo);
        } catch (Exception e) {
            logger.error("ShopId：" + vo.getShopId() + "审核店铺状态时发生异常！", e);
            return "fail";
        }
        return "success";
    }

    /*
     * 更新店铺状态
     */
    @ResponseBody
    @RequestMapping("/delete_shop")
    public String deleteShop(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        try {
            vo.setLastUpdateUserId(operator);
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.deleteShop(vo.getShopId(), null);
        } catch (Exception e) {
            logger.error("ShopId：" + vo.getShopId() + "删除店铺时发生异常！", e);
            return "fail";
        }
        return "success";
    }
}
