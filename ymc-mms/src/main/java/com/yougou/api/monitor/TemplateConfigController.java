package com.yougou.api.monitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.api.model.pojo.Api;
import com.yougou.api.service.IApiService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyTemplate;
import com.yougou.merchant.api.monitor.vo.MonitorTemplate;
import com.yougou.merchant.api.monitor.vo.MonitorTemplateDetail;

/**
 * API监控参数设置
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/merchant/api/template")
public class TemplateConfigController {

    private final String BASE_PATH = "yitiansystem/merchant/apimonitor/";

    private static final Logger logger = Logger.getLogger(TemplateConfigController.class);

    @Resource
    private IApiMonitorService apiMonitorService;

    @Resource
    private IApiService apiService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String API_CONFIG_REDIS_KEY = "api_config_redis_key";

    /**
     * API监控参数获取
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_template_config")
    public String api_template_config(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        if(null==template.getTemplateName()){
            template.setTemplateName("");
        }
        PageFinder<MonitorTemplate> pageFinder = apiMonitorService.queryMonitorTemplateList(template, query);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("template", template);
        // 返回当前页
        return BASE_PATH + "api_template_config";
    }

    /**
     * API监控参数查看
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_template_config_view")
    public String api_template_config_view(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        // 根据TemplateNo获取模板信息，包括模板的详细信息
        MonitorTemplate monitorTemplate = apiMonitorService.getMonitorTemplateByNo(template.getTemplateNo());
        PageFinder<MonitorTemplateDetail> pageFinder = apiMonitorService.queryTemplateDetailList(template.getTemplateNo(), query);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("template", monitorTemplate);
        // 返回当前页
        return BASE_PATH + "api_template_config_view";
    }

    /**
     * 添加API监控模板
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_template_config_add")
    public String api_template_config_add(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        model.addAttribute("template", template);
        // 返回当前页
        return BASE_PATH + "api_template_config_add";
    }

    /**
     * 进入保存API监控模板
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_save")
    public String api_template_config_save(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        List<MonitorTemplateDetail> templateDetails = new ArrayList<MonitorTemplateDetail>();
        String[] details = template.getId().split(";");
        String[] detail;
        for (int i = 0; i < details.length; i++) {
            MonitorTemplateDetail templateDetail = new MonitorTemplateDetail();
            detail = details[i].split(",");
            templateDetail.setTemplateNo(template.getTemplateNo());
            templateDetail.setApiId(detail[0]);
            templateDetail.setFrequency(Integer.valueOf(detail[3]));
            templateDetail.setFrequencyUnit(Integer.valueOf(detail[4]));
            templateDetail.setCallNum(Integer.valueOf(detail[5]));
            templateDetail.setIsFrequency(detail[6].trim().equals("开启") ? 1 : 0);
            templateDetail.setIsCallNum(detail[7].trim().equals("开启") ? 1 : 0);
            templateDetail.setCreateTime(new Date());
            templateDetail.setUpdateTime(new Date());
            templateDetails.add(templateDetail);
        }
        template.setId(null);
        template.setReferenceType(1);
        template.setIsDefault(0);
        template.setTemplateDetails(templateDetails);
        template.setUpdateTime(new Date());
        template.setCreateTime(new Date());
        model.addAttribute("template", template);
        return apiMonitorService.saveMonitorTemplate(template) ? "success" : "failed";
    }

    /**
     * 跳转到到API列表页面
     * 
     * @throws Exception
     */
    @RequestMapping("/add_api_parameter")
    public String add_api_parameter(ModelMap model, Query query) throws Exception {
        List<Api> apiList = apiService.getAllApi();
        model.put("apiList", apiList);
        return BASE_PATH + "api_template_config_add_parameter";
    }

    /**
     * API监控模板修改
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_template_config_modify")
    public String api_template_config_modify(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        MonitorTemplate monitorTemplate = apiMonitorService.getMonitorTemplateByNo(template.getTemplateNo());
        PageFinder<MonitorTemplateDetail> pageFinder = apiMonitorService.queryTemplateDetailList(template.getTemplateNo(), query);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("template", monitorTemplate);
        return BASE_PATH + "api_template_config_modify";
    }

    /**
     * API监控模板修改
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_modify_save")
    public String api_template_config_modify_save(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        List<MonitorTemplateDetail> templateDetails = new ArrayList<MonitorTemplateDetail>();

        String[] details = template.getId().split(";");
        String[] detail;
        for (int i = 1; i < details.length; i++) {
            MonitorTemplateDetail templateDetail = new MonitorTemplateDetail();
            detail = details[i].split(",");
            templateDetail.setTemplateNo(template.getTemplateNo());
            templateDetail.setApiId(detail[0]);
            templateDetail.setId(detail[1].equals("null") ? null : detail[1]);
            templateDetail.setFrequency(Integer.valueOf(detail[4]));
            templateDetail.setFrequencyUnit(Integer.valueOf(detail[5]));
            templateDetail.setCallNum(Integer.valueOf(detail[6]));
            templateDetail.setIsFrequency(detail[7].trim().equals("开启") ? 1 : 0);
            templateDetail.setIsCallNum(detail[8].trim().equals("开启") ? 1 : 0);
            templateDetail.setCreateTime(new Date());
            templateDetail.setUpdateTime(new Date());
            templateDetails.add(templateDetail);
        }

        template.setId(details[0]);
        template.setReferenceType(1);
        //template.setIsDefault(0);
        template.setTemplateDetails(templateDetails);
        template.setUpdateTime(new Date());
        model.addAttribute("template", template);
        // 返回执行状态
        return apiMonitorService.saveMonitorTemplate(template) ? "success" : "failed";
    }

    /**
     * API监控模板删除
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_delete")
    public String api_template_config_delete(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        Boolean success = false;
        success = apiMonitorService.deleteMonitorTemplateByTemplateNo(template.getTemplateNo())
                && apiMonitorService.deleteMonitorTemplateDetailByTemplateNo(template.getTemplateNo());
        // 返回执行状态
        return success ? "success" : "failed";
    }

    /**
     * API监控模板明细按Id删除
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_delete_detail_byId")
    public String api_template_config_delete_detail_byId(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        Boolean success = false;
        success = apiMonitorService.deleteMonitorTemplateDetailById(template.getId());
        // 返回执行状态
        return success ? "success" : "failed";
    }

    // APPkEY自定义模板相关
    /**
     * 获取API监控模板列表
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_appkey_detail")
    public String api_template_config_appkey_detail(ModelMap model, MonitorTemplate template,  Query query) throws Exception {

        MonitorTemplate monitorTemplate = apiMonitorService.getMonitorTemplateByNo(template.getTemplateNo());
        // 返回选择的模板数据
        return JSONArray.fromObject(monitorTemplate!=null?monitorTemplate.getTemplateDetails():null).toString();
    }

    /**
     * 保存APPKEY与模板之间的关系
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config_appkey_save")
    public String api_template_config_appkey_save(ModelMap model, MonitorAppKeyTemplate template, Query query) throws Exception {
        Boolean success = false;
        success = apiMonitorService.updateMonitorAppKeyTemplateByAppkeyId(template);
        // 返回执行状态
        return success ? "success" : "failed";
    }
    
    /**
     * APPKEY的API模板修改
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @ResponseBody
    @RequestMapping("api_template_config__appkey_modify")
    public String api_template_config__appkey_modify(ModelMap model, MonitorTemplate template, Query query) throws Exception {
        List<MonitorTemplateDetail> templateDetails = new ArrayList<MonitorTemplateDetail>();

        String[] details = template.getId().split(";");
        String[] detail;
        for (int i = 1; i < details.length; i++) {
            MonitorTemplateDetail templateDetail = new MonitorTemplateDetail();
            detail = details[i].split(",");
            templateDetail.setTemplateNo(template.getTemplateNo());
            templateDetail.setApiId(detail[0]);
            templateDetail.setId(detail[1].equals("null")?null:detail[1]);
            templateDetail.setFrequency(Integer.valueOf(detail[4]));
            templateDetail.setFrequencyUnit(Integer.valueOf(detail[5]));
            templateDetail.setCallNum(Integer.valueOf(detail[6]));
            templateDetail.setIsFrequency(Integer.valueOf(detail[7]));
            templateDetail.setIsCallNum(Integer.valueOf(detail[8]));
            templateDetail.setCreateTime(new Date());
            templateDetail.setUpdateTime(new Date());
            templateDetails.add(templateDetail);
        }

        template.setId(details[0]);
        template.setReferenceType(2);
        template.setIsDefault(0);
        template.setUpdateTime(new Date());
        template.setTemplateDetails(templateDetails);
        model.addAttribute("template", template);
        //关联改APPKEYid和自定义的API模板
        MonitorAppKeyTemplate appTemplate = new MonitorAppKeyTemplate();
        appTemplate.setAppkeyId(template.getTemplateNo());
        appTemplate.setTemplateNo(template.getTemplateNo());
        // 返回执行状态
        return apiMonitorService.saveAppKeyTemplate(template)&&apiMonitorService.updateMonitorAppKeyTemplateByAppkeyId(appTemplate) ? "success" : "failed";
    }
}
