package com.yougou.kaidian.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import com.yougou.kaidian.order.model.AsmProduct;
import com.yougou.kaidian.order.model.MerchantOrderExpand;
import com.yougou.kaidian.order.model.MerchantOrderPrintInputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderPrintOutputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderStockOutputDto;
import com.yougou.kaidian.order.model.NotMerchantOrderBean;
import com.yougou.ordercenter.model.order.OrderSearchTemp;
import com.yougou.ordercenter.vo.merchant.input.QueryStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPickOutputDto;
import com.yougou.ordercenter.vo.order.OrderSubInfoVo;

/**
 * 商家订单
 * 
 * @author zhuang.rb
 * 
 */
public interface MerchantOrderMapper {

    /**
     * 查询非商家订单总数
     * 
     * @return
     */
    public int queryNotMerchantOrderCount();

    /**
     * 查询非商家订单
     * 
     * @return
     */
    public List<NotMerchantOrderBean> queryNotMerchantOrderList(RowBounds rowBounds);

    /**
     * 删除取消的货品信息
     * 
     * @param order_sub_id
     */
    public void deleteOrderCancelGoods(String order_sub_id);

    /**
     * 删除订单申请退款
     * 
     * @param order_sub_no
     */
    public void deleteOrderApplyRefund(String order_sub_no);

    /**
     * 删除订单收货人信息
     */
    public void deleteOrderConsignee(String consignee_id);

    /**
     * 删除订单详细
     * 
     * @param order_sub_id
     */
    public void deleteOrderDetail4sub(String order_sub_id);

    /**
     * 删除订单退换货入库记录
     * 
     * @param order_sub_id
     */
    public void deleteOrderSaleApplyBill(String order_sub_id);

    /**
     * 删除子订单信息
     * 
     * @param id
     */
    public void deleteOrderSub(String id);

    /**
     * 删除子订单扩展信息
     * 
     * @param order_sub_id
     */
    public void deleteOrderSubExpand(String order_sub_id);
    
    /**
     * 删除订单购买人信息
     * 
     * @param order_sub_id
     */
    public void deleteOrderBuyInfo(String order_main_no);

    /**
     * @Comments: 查询订单打印清单
     * @param: dto query传入对象
     * @return PageFinder
     * @Author cao.jz
     * @Create Date： 2013-07-05
     * @throws Exception
     */
    public List<MerchantQueryOrderPrintOutputDto> queryDataOrderPrintList(MerchantOrderPrintInputDto dto) throws Exception;

    public List<Map<String, String>> queryCountOrderPrintList(MerchantOrderPrintInputDto dto) throws Exception;

	public int queryCountOrderPrintNum(MerchantOrderPrintInputDto dto) throws Exception;
    /**
     * @Comments: 查询备货清单
     * @param: dto query传入对象
     * @return PageFinder
     * @Author cao.jz
     * @Create Date： 2013-07-05
     * @throws Exception
     */
    public List<MerchantQueryOrderStockOutputDto> getDataOrderStock(QueryStockInputDto dto, RowBounds rowBounds) throws Exception;
    
    public int getCountOrderStock(QueryStockInputDto dto) throws Exception;

    /**
     * @Comments: 查询拣货清单
     * @param: merchantCode 商家编码
     * @param warehouseCode
     *            商家仓库编码
     * @param orderSubNos
     *            子订单编码
     * @return List
     * @Author cao.jz
     * @Create Date： 2013-07-08
     * @throws Exception
     */
    public List<QueryOrderPickOutputDto> queryOrderPickingList(String merchantCode, String warehouseCode, List<String> orderSubNos) throws Exception;

    /**
     * 根据换货订单号查询订单的基本信息
     * 
     * @author cao.jz
     * @Date 2013-08-06
     * @param newOrderNo
     *            换货订单号
     * @return OrderSubInfoVo
     */
    public OrderSubInfoVo getOrderSubInfoByNewOrderNo(String newOrderNo) throws Exception;
    
    /**
     * 批量添加子订单号到临时表中
     * @author cao.jz
     * @Date 2013-10-24
     * @param orderSubTempList 订单号集合
     * @return int
     */
    public int insertOrderSearchTemp(List<OrderSearchTemp> orderSearchTempList) throws Exception;
    
    /**
     * 根据标识删除子订单号信息
     * @author cao.jz
     * @Date 2013-10-28
     * @param tempKeyWord 标识字段
     * @return 
     */
    public void deleteOrderSearchTemp(String tempKeyWord) throws Exception;
    
    public List<QueryOrderPickOutputDto> queryPickingOrderList(Map<String, Object> parameterMap) throws Exception;

	/**
	 * 根据订单状态查询订单数量
	 */
	public int selectOrderCountByStatus(
			@Param("merchantCode") String merchantCode,
			@Param("orderStatus") String orderStatus);
	
	public AsmProduct selectProductByLevelCode(@Param("order_sub_no") String order_sub_no,@Param("level_code") String level_code);
	
	/** 商家备注功能*/
	List<MerchantOrderExpand> selectByOrderSubNo(@Param("orderSubId")String orderSubId);//
	
	int insertSelective(MerchantOrderExpand record);//
	
	int updateByPrimaryKeySelective(MerchantOrderExpand record);//
	
	List<MerchantOrderExpand> selectByOrders(@Param("orders")List<String> orders);//

	public void updateExportStatus(@Param("merchantCode") String merchantCode,
			@Param("orderNos") List<String> lstOrderSubNo,@Param("now") Date now);

	public void updatePrintExpressSize(@Param("merchantCode") String merchantCode,@Param("orderNo") String orderSubNo);

	public void updatePrintShopSize(@Param("merchantCode") String merchantCode,@Param("orderNos") List<String> _orderNos);

	public void updateOutStoreStatus(@Param("merchantCode") String merchantCode,@Param("orderNo") String orderSubNo,
			@Param("outShopDate") Date outShopDate);

}
