package com.yougou.api.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import com.yougou.api.model.vo.ApiAllCount;
import com.yougou.api.model.vo.ApiAnalyzeDetailVo;
import com.yougou.api.model.vo.ApiCount;
import com.yougou.api.model.vo.ApiMetadata;
import com.yougou.api.model.vo.AppKeyCount;
import com.yougou.api.model.vo.AppKeyMetadata;
import com.yougou.merchant.api.monitor.vo.ApiMonitorParameterVo;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyVo;
import com.yougou.merchant.api.monitor.vo.MonitorRateWarnDetail;

/**
 * api 监控接口
 * @author he.wc
 *
 */
public interface IApiAnalyzeService {

	/**
	 * 根据apiId查询api其绑定的appkey调用的次数表表
	 * @param apiId
	 * @return
	 */
	public List<AppKeyCount> findAppKeyCountByApiId(String apiId) throws Exception;
	
	/**
	 * 根据appKeyOwner查询其绑定的api调用次数列表
	 * 注：暂时无法根据分销商来查，所以appKeyOwner = appKey
	 * @param appKeyOwner
	 * @return
	 */
	public List<ApiCount> findApiCountByAppKeyOwner(String appKeyOwner);
	
	/**
	 * API 系统调用情况
	 * @return
	 */
	public ApiAllCount findApiAllCount();
	
	/**
	 * 根据api方法名查询api_id
	 * @param apiMethod
	 * @return
	 */
	public String findApiIdByName(String apiMethod);
	
	/**
	 * 得到API某天调用信息
	 * @param apiId
	 * @param timeQuantum
	 * @return
	 */
	public Map<String, Object> getApiCountSum(String apiId,String timeQuantum);
	
	/**
	 * 得到appKey某天调用信息
	 * @param apiId
	 * @param timeQuantum
	 * @return
	 */
	public Map<String, Object> getAppKeyCountSum(String appKey,String timeQuantum);
	
	/**
	 * 得到appkey与appkey用户关系
	 * @return
	 */
	public List<Map<String, Object>> getApiKeyMetadata();
	
	/**
	 * 生成appkey与appkey用户缓存
	 */
	public void createAppKeyUserCache() throws Exception ;
	
	/**
	 * 通过appkey得到appkey用户
	 * @param appKey
	 */
	public String getKeyUserByAppkey(String appKey) throws Exception ;
	
	/**
	 * 通过appkeyUser模糊查询appkey信息
	 * @param appKeyUser
	 * @return
	 */
	public List<AppKeyMetadata> getAppkeyByUser(String appKeyUser);
	
	
	/**
	 * 通过apiName模糊查询AppKeyMetadata信息
	 * @param appKeyUser
	 * @return
	 */
	public List<ApiMetadata> getApiMetadataByApiName(String apiName);
	
	/**
	 * 查询时间段API调用情况
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ApiAnalyzeDetailVo> getApiAnalyzeDetailVoList(Date startTime,Date endTime);
	
	/**
	 * 通过appKey得到商家编码
	 * @param appKey
	 * @return
	 */
	public String getMerchantCodeByappKey(String appKey);
	
	
	public Integer getAppkeyCallCount(Date startDate, Date endDate, String appKey);

	public Date getLastCallDate(Date startDate, Date endDate, String appKey);
	
	/**
	 * 通过apiId获取接口的method （yougou.time.get）
	 * 
	 * @param apiId
	 * @return
	 */
	public String getApiMethodById(String apiId);
	
	/**
	 * 获取appKey的频率预警明细
	 * @param appKey
	 * @return
	 */
	public List<MonitorRateWarnDetail> getMonitorRateWarnByAppKey(String appKey);
	
    /**
     * 获取全局的API监控参数设置
     * 
     * @param ApiMonitorParameterVo
     * @return
     */
    public ApiMonitorParameterVo getApiMonitorConfig();
	   
    /**
     * 按照appkey查询该appkey全局的配置参数和每个接口的配置明细
     * @param MonitorAppKeyVo
     * @return
     */
    MonitorAppKeyVo queryMonitorAppKeyVo(String appkey) throws Exception;
    
    /**
     * 更新redis缓存里的每个appkey对应的模板配置
     * @return
     */
    List<String> updateRedisCasheForMonitorTemplate();
    
	/**
	 * 查询每天调用的appKey
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findAppKeyByDate(Date startDate, Date endDate);
	
	/**
	 * 查询每天appkey调用次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer findCallCountGroupByKey(Date startDate, Date endDate,String appKey);
	
	/**
	 * 查询每天api调用次数，成功次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findCallCountGroupByApi(Date startDate, Date endDate,String appKey);
	
	/**
	 * 查询每天api调用次数,按api,appkey分组
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findCallCountGroupByApiAppKey(Date startDate, Date endDate);
	
	/**
	 * 在中间表根据时间和appkey查询该appkey的调用次数
	 * @param startDate
	 * @param endDate
	 * @param appKey
	 * @return
	 */
	public Integer findCallCountByDateAndAppKey(Date startDate, Date endDate,String appKey);
	
	/**
	 * 在中间表根据时间查询appkey
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> findAppKeyForCountByDate(Date startDate, Date endDate);
	/**
	 * findCountGroupByAppkey: 根据开始时间，结束时间查询每个appkey的调用次数
	 * @author li.n1 
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @since JDK 1.6 
	 * @date 2016-1-14 上午10:36:47
	 */
	public List<Map<String,Object>> findCountGroupByAppkey(Date startDate, Date endDate);
}
