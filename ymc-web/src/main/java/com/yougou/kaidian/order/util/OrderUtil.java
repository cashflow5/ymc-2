package com.yougou.kaidian.order.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.order.constant.OrderConstant;
import com.yougou.kaidian.order.constant.OrderEnum.LogType;
import com.yougou.kaidian.order.constant.OrderEnum.MethodCode;
import com.yougou.kaidian.order.model.OrderLog;
import com.yougou.kaidian.order.service.impl.OrderForMerchantServiceImpl;
import com.yougou.ordercenter.common.OrderStatusEnum;

/**
 * @directions:订单工具
 * @author： daixiaowei
 * @create： 2012-3-9 下午01:13:38
 * @history：
 * @version:
 */
public class OrderUtil {
	/**
	 * 转换订单基本状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getStatusDisplay(int status) {
		String statusDisplay = null;
		switch (status) {
		case OrderConstant.BASE_STAY_CHECK:
			statusDisplay = OrderConstant.BASE_STAY_CHECK_DISPLAY;
			break;
		case OrderConstant.BASE_CONFIRM:
			statusDisplay = OrderConstant.BASE_CONFIRM_DISPLAY;
			break;
		case OrderConstant.BASE_SUSPEND:
			statusDisplay = OrderConstant.BASE_SUSPEND_DISPLAY;
			break;
		case OrderConstant.BASE_FINISH:
			statusDisplay = OrderConstant.BASE_FINISH_DISPLAY;
			break;
		case OrderConstant.BASE_CANCEL:
			statusDisplay = OrderConstant.BASE_CANCEL_DISPLAY;
			break;
		case OrderConstant.BASE_SPECIAL_CANCEL:
			statusDisplay = OrderConstant.BASE_SPECIAL_CANCEL_DISPLAY;
			break;
		case OrderConstant.BASE_PACK:
			statusDisplay = OrderConstant.BASE_PACK_DISPLAY;
			break;
		case OrderConstant.BASE_UPDATE:
			statusDisplay = OrderConstant.BASE_CONFIRM_DISPLAY;
			break;
//		case OrderConstant.PAY_STAY_PAID:
//			statusDisplay = OrderConstant.PAY_STAY_PAID_DISPLAY;
//			break;
		case OrderConstant.PAY_PART:
			statusDisplay = OrderConstant.PAY_PART_DISPLAY;
			break;
		case OrderConstant.PAY_PAID:
			statusDisplay = OrderConstant.PAY_PAID_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_APPLY:
			statusDisplay = OrderConstant.PAY_REFUND_APPLY_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_PART:
			statusDisplay = OrderConstant.PAY_REFUND_PART_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_ALL:
			statusDisplay = OrderConstant.PAY_REFUND_ALL_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_FAIL:
			statusDisplay = OrderConstant.PAY_REFUND_FAIL_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_APPLY_CUS:
			statusDisplay = OrderConstant.PAY_REFUND_APPLY_CUS_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_AGREE:
			statusDisplay = OrderConstant.PAY_REFUND_AGREE_DISPLAY;
			break;
		case OrderConstant.PAY_REFUND_REFUSE:
			statusDisplay = OrderConstant.PAY_REFUND_REFUSE_DISPLAY;
			break;
		// 已当做新建使用
		case OrderConstant.DELIVERY_PREPARE:
			statusDisplay = OrderConstant.DELIVERY_PREPARE_DISPLAY;
			break;
		case OrderConstant.DELIVERY_PREPARE_REAl:
			statusDisplay = OrderConstant.DELIVERY_PREPARE_REAl_DISPLAY;
			break;
		case OrderConstant.DELIVERY_PART:
			statusDisplay = OrderConstant.DELIVERY_PART_DISPLAY;
			break;
		case OrderConstant.DELIVERY_SEND:
			statusDisplay = OrderConstant.DELIVERY_SEND_DISPLAY;
			break;
		case OrderConstant.DELIVERY_RECEIVE:
			statusDisplay = OrderConstant.DELIVERY_RECEIVE_DISPLAY;
			break;
		case OrderConstant.DELIVERY_REFUSE:
			statusDisplay = OrderConstant.DELIVERY_REFUSE_DISPLAY;
			break;
		case OrderConstant.DELIVERY_PART_RETURN:
			statusDisplay = OrderConstant.DELIVERY_PART_RETURN_DISPLAY;
			break;
		case OrderConstant.DELIVERY_ALL_RETURN:
			statusDisplay = OrderConstant.DELIVERY_ALL_RETURN_DISPLAY;
			break;
		case OrderConstant.DELIVERY_STOP_SEND:
			statusDisplay = OrderConstant.DELIVERY_STOP_SEND_DISPLAY;
			break;
		default:
			statusDisplay = OrderConstant.BASE_STAY_CHECK_DISPLAY;
			break;
		}
		return statusDisplay;
	}

	/**
	 * 转换excel导出数据 导出订单
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getOrderObject(ResultSet rs) throws SQLException {
		List<Object[]> objList = new ArrayList<Object[]>();
		while (rs.next()) {
			Object[] obj = new Object[13];
			obj[0] = rs.getObject(1);
			obj[1] = rs.getObject(2) != null ? DateUtil2.format(new Date(((Timestamp) rs.getObject(2)).getTime()), "yyyy-MM-dd HH:mm:ss") : "";
			obj[2] = rs.getObject(3);
			obj[3] = rs.getObject(4);
			obj[4] = rs.getObject(5);
			obj[5] = rs.getObject(6);
			obj[6] = rs.getObject(7);
			obj[7] = rs.getObject(8);
			obj[8] = rs.getObject(9);
			obj[9] = rs.getObject(10);
			obj[10] = rs.getObject(11);
			obj[11] = rs.getObject(12);
			// 导出日期
			obj[12] = rs.getObject(13) != null ? DateUtil2.format(new Date(((Timestamp) rs.getObject(13)).getTime()), "yyyy-MM-dd HH:mm:ss") : DateUtil2.format(
					new Date(), "yyyy-MM-dd HH:mm:ss");
			objList.add(obj);
		}
		return objList;
	}

	/**
	 * 转换excel导出数据 导出单据打印中的拣货清单
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getPrintOrderObject(ResultSet rs, final boolean isNewExport) throws SQLException {
		List<Object[]> objList = new ArrayList<Object[]>();
		Object temp = null;
		while (rs.next()) {
			if(isNewExport) {
				Object[] obj = new Object[20];
				if (temp == null || !temp.equals(rs.getObject(4))) {
					temp = rs.getObject(4);
					obj[0] = DateUtil2.format(new Date(), "yyyy-MM-dd HH:mm:ss");
					obj[1] = rs.getObject(2) != null && (Integer.valueOf(rs.getObject(2).toString()) == 1) ? "已拣货" : "未拣货";
					obj[2] = rs.getObject(3) != null ? DateUtil2.format(new Date(((Timestamp) rs.getObject(3)).getTime()), "yyyy-MM-dd HH:mm:ss") : "";
					obj[3] = rs.getObject(4);
					obj[4] = rs.getObject(5);
					obj[5] = rs.getObject(6);
					obj[6] = rs.getObject(7);
					obj[7] = rs.getObject(8);
					obj[8] = rs.getObject(9);
					obj[9] = rs.getObject(10);
					obj[10] = rs.getObject(11);
					obj[11] = rs.getObject(12);
					obj[12] = rs.getObject(13);
				}
				obj[13] = rs.getObject(14);
				obj[14] = rs.getObject(15);
				obj[15] = rs.getObject(16);
				obj[16] = rs.getObject(17);
				obj[17] = rs.getObject(18);
				obj[18] = rs.getObject(19);
				obj[19] = rs.getObject(20);
				objList.add(obj);
			}
			else {
				Object[] obj = new Object[13];
				if(temp == null || !temp.equals(rs.getObject(4))) {
					temp = rs.getObject(4);
					obj[0] = DateUtil2.format(new Date(), "yyyy-MM-dd HH:mm:ss");
					obj[1] = rs.getObject(2) != null && (Integer.valueOf(rs.getObject(2).toString())==1) ? "已拣货" : "未拣货";
					obj[2] = rs.getObject(3) != null ? DateUtil2.format(new Date(((Timestamp) rs.getObject(3)).getTime()), "yyyy-MM-dd HH:mm:ss") : "";
					obj[3] = rs.getObject(4);
					obj[4] = rs.getObject(5);
					obj[5] = rs.getObject(6);
				}
				obj[6] = rs.getObject(7);
				obj[7] = rs.getObject(8);
				obj[8] = rs.getObject(9);
				obj[9] = rs.getObject(10);
				obj[10] = rs.getObject(11);
				obj[11] = rs.getObject(12);
				obj[12] = rs.getObject(13);
				objList.add(obj);
			}
		}
		return objList;
	}

	/**
	 * 转换excel导出数据 导出销售列表
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getSalesDetailObject(List<Object[]> list) throws SQLException {
		List<Object[]> objList = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = new Object[11];
			obj[0] = list.get(i)[1] == null ? null : list.get(i)[1].toString();
			obj[1] = list.get(i)[2] == null ? null : list.get(i)[2].toString();
			obj[2] = list.get(i)[3] == null ? null : list.get(i)[3].toString();
			obj[3] = list.get(i)[4] == null ? null : list.get(i)[4].toString();
			obj[4] = list.get(i)[5] == null ? null : list.get(i)[5].toString();
			obj[5] = list.get(i)[6] == null ? null : list.get(i)[6].toString();
			obj[6] = list.get(i)[7] == null ? null : list.get(i)[7].toString();
			obj[7] = list.get(i)[8] == null ? null : list.get(i)[8].toString();
			obj[8] = list.get(i)[9] == null ? null : Integer.valueOf(list.get(i)[9].toString());
			obj[9] = list.get(i)[10] == null ? null : Integer.valueOf(list.get(i)[10].toString());
			obj[10] = list.get(i)[11] == null ? null : Integer.valueOf(list.get(i)[11].toString());
			objList.add(obj);
		}
		return objList;
	}

	/**
	 * 返回一个订单日志对象
	 * 
	 * @param orderNo
	 * @param methodCode
	 * @param logType
	 * @param operateUser
	 * @param remark
	 * @param flag
	 * @return
	 */
	public static com.yougou.kaidian.order.model.OrderLog getOrderLog(String orderNo, MethodCode methodCode, LogType logType, String operateUser, String remark,
			String applyId, boolean flag) {
		OrderLog log = new OrderLog();
		log.setLogType(logType.getValue());
		log.setCreateTime(new Date());
		log.setOperateResult(flag ? OrderConstant.OPERATE_SUCCESS : OrderConstant.OPERATE_FAIL);
		log.setBehavioutDescribe(methodCode.getName());
		log.setOrderNo(orderNo);
		log.setApplyId(applyId);
		log.setOperateUser(operateUser);
		log.setRemark(remark);
		return log;
	}

	/**
	 * 获得出库单号
	 * 
	 * @param type
	 * @return
	 */
	public static String getCode(String type) {
		if (type == null || type.isEmpty()) {
			type = "PT"; // 非交易类型
		}
		StringBuffer result = new StringBuffer().append(type);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");// 精确到微秒
		String dd = format.format(Calendar.getInstance().getTime()).substring(2, 8);
		result.append(dd);
		Random rand = new Random();
		for (int j = 0; j < 6; j++) {
			result.append(rand.nextInt(10));
		}
		return result.toString();
	}

	/**
	 * 通过当前日期获取相差 day 天前的时间 时分秒00:00:00
	 * 
	 * @param time
	 * @param day
	 * @return
	 */
	public static String getStartTime3(Date time, int day) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time);
		long millSeconds1 = calendar1.getTimeInMillis();
		long tmpDays = Long.valueOf(String.valueOf(day)) * (24 * 60 * 60 * 1000);
		return DateUtil2.format(new Date(millSeconds1 - tmpDays), "yyyy-MM-dd");
	}
	
	/**
	 * 通过url得到返回的信息
	 * creator liuwenjun
	 * create time 2012-2-3 下午05:29:51
	 * @param url
	 * @param encodeCode 得到流的转码方式
	 * @return
	 * @throws Exception
	 */
	public static String getMessageByUrl(String url,String encodeCode) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();  
        HttpGet get = new HttpGet(url);  
        HttpResponse response = httpClient.execute(get);  
        HttpEntity entity = response.getEntity();  
        InputStream inSm = entity.getContent();  
        String tempbf;
        StringBuffer res= new StringBuffer("");
        if(inSm!=null) {
	        BufferedReader br = new BufferedReader(new InputStreamReader(inSm,encodeCode));
	        while ((tempbf = br.readLine()) != null) {
	        	res.append(tempbf);
	        }
        }
        return res.toString();
	}
	
	public static Map<String, String> getNewOrderStatus() throws Exception {
		Map<String, String> orderStatusMap = new HashMap<String, String>();
/*		orderStatusMap.put(Integer.toString(OrderConstant.BASE_STAY_CHECK), OrderConstant.BASE_STAY_CHECK_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_CONFIRM), OrderConstant.BASE_CONFIRM_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_SUSPEND), OrderConstant.BASE_SUSPEND_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_FINISH), OrderConstant.BASE_FINISH_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_CANCEL), OrderConstant.BASE_CANCEL_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_SPECIAL_CANCEL), OrderConstant.BASE_SPECIAL_CANCEL_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_UPDATE), OrderConstant.BASE_UPDATE_DISPLAY);
		orderStatusMap.put(Integer.toString(OrderConstant.BASE_PACK), OrderConstant.BASE_PACK_DISPLAY);*/
		//新订单状态
		orderStatusMap.put(Integer.toString(OrderStatusEnum.SERVICE_NOTICED.getValue()), OrderStatusEnum.SERVICE_NOTICED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.CONFIRMED.getValue()), OrderStatusEnum.CONFIRMED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.MODIFY_APPLIED.getValue()), OrderStatusEnum.MODIFY_APPLIED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.CANCEL_APPLIED.getValue()), OrderStatusEnum.CANCEL_APPLIED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.CANCELLED.getValue()), OrderStatusEnum.CANCELLED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REFUND_APPLIED.getValue()), OrderStatusEnum.REFUND_APPLIED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REFUNDED.getValue()), OrderStatusEnum.REFUNDED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.WAREHOUSE_NOTICED.getValue()), OrderStatusEnum.WAREHOUSE_NOTICED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.DELIVERED.getValue()), OrderStatusEnum.DELIVERED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REJECT_QA.getValue()), OrderStatusEnum.REJECT_QA.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REJECT_REFUND_APPLIED.getValue()), OrderStatusEnum.REJECT_REFUND_APPLIED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REJECT_REFUND_REFUSED.getValue()), OrderStatusEnum.REJECT_REFUND_REFUSED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REJECT_REFUNDED.getValue()), OrderStatusEnum.REJECT_REFUNDED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.REJECT_TRADED.getValue()), OrderStatusEnum.REJECT_TRADED.getName());
		orderStatusMap.put(Integer.toString(OrderStatusEnum.FILED.getValue()), OrderStatusEnum.FILED.getName());
		
		return orderStatusMap;
	}
	//加密移动电话和详细地址,已取消+或超过90天
	/**
	 * status 可能为空，int类型报空指针，Integer类型可以
	 */
	public static String[] encryptMobileAndAddressByDateAndStatus(String address, String mobile, Integer status, Date createTime) {
		int days = 0;
		if(null != createTime){
			days = OrderForMerchantServiceImpl.daysBetween(createTime, new Date(System.currentTimeMillis()));
		}
		if(OrderStatusEnum.CANCELLED.getValue() == status || days > 90){			
			if(StringUtils.isNotBlank(address)){
				address = address.replaceAll(".", "*");        			
			}
			if(StringUtils.isNotBlank(mobile) && mobile.length() > 4){
				mobile = StringUtils.substring(mobile, 0 ,4) + "****" + StringUtils.substring(mobile, 7);
			}
		}
		return new String[]{address,mobile};
	}
}
