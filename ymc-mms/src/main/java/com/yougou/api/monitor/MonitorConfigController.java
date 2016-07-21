package com.yougou.api.monitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.merchant.api.common.Constant;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.vo.ApiMonitorParameterVo;
import com.yougou.merchant.api.monitor.vo.MonitorConfig;

/**
 * API监控参数设置
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/merchant/api/monitor")
public class MonitorConfigController {

    private final String BASE_PATH = "yitiansystem/merchant/apimonitor/";

    private static final Logger logger = Logger.getLogger(MonitorConfigController.class);

    @Resource
    private IApiMonitorService apiMonitorService;

    @Resource
    private IApiAnalyzeService apiAnalyzeService;
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    public static final String API_CONFIG_REDIS_KEY = "api_config_redis_key";
    
    public static final String API_APPKEY_FLOW_MAX_NUM = "api:appkey.flow:max";
    
    public static final String API_APPKEY_FLOW_WARN_NUM = "api:appkey.flow:warn";

    /**
     * API监控参数获取
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_monitor_config")
    public String apiMonitorConfig(ModelMap model, ApiMonitorParameterVo vo, HttpServletRequest request) {
        vo = apiAnalyzeService.getApiMonitorConfig();
        model.addAttribute("vo", vo);
        model.addAttribute("status", "");
        model.addAttribute("appKeyFlowMax",valueOperations.get(API_APPKEY_FLOW_MAX_NUM) );
        model.addAttribute("appKeyFlowWarn",valueOperations.get(API_APPKEY_FLOW_WARN_NUM));
        return BASE_PATH + "api_monitor_config";
    }

    /**
     * API监控参数设置
     * 
     * @param modelMap
     * @param ApiMonitorParameterVo
     * @return
     */
    @RequestMapping("api_monitor_config_modify")
    public String apiMonitorConfigModify(ModelMap model, ApiMonitorParameterVo vo,String appKeyFlowMax,String appKeyFlowWarn) {
        List<MonitorConfig> configList = new ArrayList<MonitorConfig>();
        valueOperations.set(API_APPKEY_FLOW_MAX_NUM, appKeyFlowMax);
        valueOperations.set(API_APPKEY_FLOW_WARN_NUM,appKeyFlowWarn);

        // 单接口日调用超限次数
        MonitorConfig config_DataFlowRate = new MonitorConfig();
        config_DataFlowRate.setConfigName(Constant.DATA_FLOW_RATE);
        config_DataFlowRate.setConfigKey(Constant.KEY_DATA_FLOW_RATE);
        config_DataFlowRate.setConfigValue(vo.getDataFlowRate().toString());
        config_DataFlowRate.setDeleteFlag("1");
        config_DataFlowRate.setRemark("单接口日调用超限次数");
        config_DataFlowRate.setUpdateTime(new Date());
        configList.add(config_DataFlowRate);
        // 单接口超限频率
        MonitorConfig config_FrequencyRate = new MonitorConfig();
        config_FrequencyRate.setConfigName(Constant.FREQUENCY_RATE);
        config_FrequencyRate.setConfigKey(Constant.KEY_FREQUENCY_RATE);
        config_FrequencyRate.setConfigValue(vo.getFrequencyRate().toString());
        config_FrequencyRate.setDeleteFlag("1");
        config_FrequencyRate.setRemark("单接口超限频率");
        config_FrequencyRate.setUpdateTime(new Date());
        configList.add(config_FrequencyRate);

        // 锁定接口小时数
        MonitorConfig config_FrequencyOutLockTime = new MonitorConfig();
        config_FrequencyOutLockTime.setConfigName(Constant.FREQUENCY_OUTLOCK_TIME);
        config_FrequencyOutLockTime.setConfigKey(Constant.KEY_FREQUENCY_OUTLOCK_TIME);
        config_FrequencyOutLockTime.setConfigValue(vo.getFrequencyOutLockTime().toString());
        config_FrequencyOutLockTime.setDeleteFlag("1");
        config_FrequencyOutLockTime.setRemark("锁定接口小时数");
        config_FrequencyOutLockTime.setUpdateTime(new Date());
        configList.add(config_FrequencyOutLockTime);

        // 单接口日调用次数
        MonitorConfig config_SimpleImplOneDayRate = new MonitorConfig();
        config_SimpleImplOneDayRate.setConfigName(Constant.SIMPLEIMPL_ONEDAY_RATE);
        config_SimpleImplOneDayRate.setConfigKey(Constant.KEY_SIMPLEIMPL_ONEDAY_RATE);
        config_SimpleImplOneDayRate.setConfigValue(vo.getSimpleImplOneDayRate().toString());
        config_SimpleImplOneDayRate.setDeleteFlag("1");
        config_SimpleImplOneDayRate.setRemark("单接口日调用次数");
        config_SimpleImplOneDayRate.setUpdateTime(new Date());
        configList.add(config_SimpleImplOneDayRate);

        // 单接口频率预警
        MonitorConfig config_SimpleImplFrequencyRate = new MonitorConfig();
        config_SimpleImplFrequencyRate.setConfigName(Constant.SIMPLEIMPL_FREQUENCY_RATE);
        config_SimpleImplFrequencyRate.setConfigKey(Constant.KEY_SIMPLEIMPL_FREQUENCY_RATE);
        config_SimpleImplFrequencyRate.setConfigValue(vo.getSimpleImplFrequencyRate().toString());
        config_SimpleImplFrequencyRate.setDeleteFlag("1");
        config_SimpleImplFrequencyRate.setRemark("单接口频率预警");
        config_SimpleImplFrequencyRate.setUpdateTime(new Date());
        configList.add(config_SimpleImplFrequencyRate);

        // 调用成功率预警
        MonitorConfig config_SuccessRate = new MonitorConfig();
        config_SuccessRate.setConfigName(Constant.SUCCESS_RATE);
        config_SuccessRate.setConfigKey(Constant.KEY_SUCCESS_RATE);
        config_SuccessRate.setConfigValue(vo.getSuccessRate().toString());
        config_SuccessRate.setDeleteFlag("1");
        config_SuccessRate.setRemark("调用成功率预警");
        config_SuccessRate.setUpdateTime(new Date());
        configList.add(config_SuccessRate);

        // AppKey日调用次数预警
        MonitorConfig config_AppKeyCallFrequencyRate = new MonitorConfig();
        config_AppKeyCallFrequencyRate.setConfigName(Constant.APPKEY_CALLFREQUENCY_RATE);
        config_AppKeyCallFrequencyRate.setConfigKey(Constant.KEY_APPKEY_CALLFREQUENCY_RATE);
        config_AppKeyCallFrequencyRate.setConfigValue(vo.getAppKeyCallFrequencyRate().toString());
        config_AppKeyCallFrequencyRate.setDeleteFlag("1");
        config_AppKeyCallFrequencyRate.setRemark("AppKey日调用次数预警");
        config_AppKeyCallFrequencyRate.setUpdateTime(new Date());
        configList.add(config_AppKeyCallFrequencyRate);

        // 无效AppKey发送次数，则该IP被封
        MonitorConfig config_InvalidAppKeyRequest = new MonitorConfig();
        config_InvalidAppKeyRequest.setConfigName(Constant.INVALID_APPKEY_REQUEST);
        config_InvalidAppKeyRequest.setConfigKey(Constant.KEY_INVALID_APPKEY_REQUEST);
        config_InvalidAppKeyRequest.setConfigValue(vo.getInvalidAppKeyRequest().toString());
        config_InvalidAppKeyRequest.setDeleteFlag("1");
        config_InvalidAppKeyRequest.setRemark("无效AppKey发送次数，则该IP被封");
        config_InvalidAppKeyRequest.setUpdateTime(new Date());
        configList.add(config_InvalidAppKeyRequest);

        boolean success = false;
        Map<String, String> config = new HashMap<String, String>();
        config.put(Constant.DATA_FLOW_RATE, vo.getDataFlowRate().toString());
        config.put(Constant.FREQUENCY_RATE, vo.getFrequencyRate().toString());
        config.put(Constant.FREQUENCY_OUTLOCK_TIME, vo.getFrequencyOutLockTime().toString());
        config.put(Constant.SIMPLEIMPL_ONEDAY_RATE, vo.getSimpleImplOneDayRate().toString());
        config.put(Constant.SIMPLEIMPL_FREQUENCY_RATE, vo.getSimpleImplFrequencyRate().toString());
        config.put(Constant.SUCCESS_RATE, vo.getSuccessRate().toString());
        config.put(Constant.APPKEY_CALLFREQUENCY_RATE, vo.getAppKeyCallFrequencyRate().toString());
        config.put(Constant.INVALID_APPKEY_REQUEST, vo.getInvalidAppKeyRequest().toString());
        try {
            success = apiMonitorService.updateMonitorConfig(configList);
            redisTemplate.opsForValue().set(API_CONFIG_REDIS_KEY, config);
        } catch (Exception e) {
            logger.error("更新API监控参数时发生异常！");
        }
        model.addAttribute("vo", vo);
        model.addAttribute("status", String.valueOf(success));
        model.addAttribute("appKeyFlowMax", appKeyFlowMax);
        model.addAttribute("appKeyFlowWarn", appKeyFlowWarn);
        return BASE_PATH + "api_monitor_config";
    }
}
