/**
 * 
 */
package com.yougou.kaidian.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yougou.kaidian.order.model.OrderSubExpand;
import com.yougou.ordercenter.model.order.OrderDetail4sub;

/**
 * 订单相关逻辑处理(接口类)
 * 
 * @author huang.tao
 *
 */
public interface IOrderService {
	
	/**
	 * <p>查询店铺提醒相关信息：
	 * 包括待发货订单数、缺货订单数、超时未发货订单数、库存提示(少于5)：待发货订单</p>
	 * <p>
	 * &ltwaitSends, Set&ltString>>      待发货订单<br />
	 * &ltstockOuts, Set&ltString>>      缺货订单<br />
	 * &lttimeOutOrders, Set&ltString>>  超时未发货订单<br />
	 * </p>
	 * @param merchantCode
	 * @return
	 */
	Map<String, Integer> queryShopRemindList(String merchantCode) throws Exception;
	
	/**
	 * 分析统计商家近一周的销售情况（指标有 下单量、下单金额）
	 * <p>
	 * _list.put("date", dates);<br />
	 * _list.put("orderNums", orderNums);<br />
	 * _list.put("orderMoneys", orderMoneys);<br />
	 * </p>
	 * @param merchantCode
	 * @return
	 */
	Map<String, List<?>> analyzeSellByNeerWeek(String merchantCode);
	
	/**
	 * 通过子订单Id获取详细列表
	 * 
	 * @param orderSubId
	 * @return
	 */
	List<OrderDetail4sub> getOrderDetail4sub(String orderSubId, String merchantCode);
	
	/**
	 * 导出订单
	 * 
	 * @param list
	 * @param templatePath
	 * @param fileName
	 * @param response
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	boolean doExportOrder(List<Object[]> list, String templatePath, String fileName,
			HttpServletResponse response, String sheetName) throws Exception;
	
	/**
     * 置为备货
     */
    public void updateStocking(String[] orderSubIds,Map<String,Object> unionUser) throws Exception;

	/**
	 * 根据订单号获取订单信息  收货人名字 手机 发货时间  快递公司    快递单号
	 * [售后接口]
	 * @return
	 */
	public OrderSubExpand getOrderSubExpandByOrderSubNo(String orderSubNo,String merchantCode) throws Exception;
}
