<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">异常售后审核</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/aftersale/abnormal_after_sale.sc?str=1" name="queryForm" id="queryForm" method="post">        	
              	<div class="add_detail_box">
					<p>
						<span>
						  <label>订单号：</label>
						  <input style="width:135px;" type="text" id="orderNo" name="orderNo" value="<#if vo??&&vo.orderNo??>${vo.orderNo}</#if>">
						</span>
						
						<span>
						<label>售后申请单号：</label>
						<input style="width:135px;" type="text" id="applyNo" name="applyNo" value="<#if vo??&&vo.applyNo??>${vo.applyNo}</#if>">
						</span>
						
						<span>
						<label>申请时间：</label>
						<input style="width:115px;" id="createTimeStart" name="createTimeStart" value="<#if vo??&&vo.createTimeStart??>${vo.createTimeStart}</#if>" />
						-
						<input style="width:115px;" id="createTimeEnd" name="createTimeEnd" value="<#if vo??&&vo.createTimeEnd??>${vo.createTimeEnd}</#if>"  />
						</span>
						
					</p>
					<p>
						<span>
						<label>商家编号：</label>
						<input style="width:135px;" type="text" id="merchantCode" name="merchantCode" value="<#if vo??&&vo.merchantCode??>${vo.merchantCode}</#if>">
					    </span>
					    
					    <span>
						<label>商家名称：</label>
						<input style="width:135px;" type="text" id="merchantName" name="merchantName" value="<#if vo??&&vo.merchantName??>${vo.merchantName}</#if>">
					    </span>
					    
					    <span>
						<label>品牌：</label>
						<input style="width:135px;" type="text" id="brandName" name="brandName" value="<#if vo??&&vo.brandName??>${vo.brandName}</#if>">
					    </span>
	                </p>
	                <p>
	                    <span><label>登记类型：</label>
						<select id="exceptionType" name="exceptionType" style="width:138px;">
						  <option <#if vo.exceptionType??&&vo.exceptionType==''>selected</#if> value="">全部</option>
						  <option <#if vo.exceptionType??&&vo.exceptionType=='LOST_GOODS'>selected</#if> value="LOST_GOODS">丢件</option>
						  <option <#if vo.exceptionType??&&vo.exceptionType=='DRAIN_GOODS'>selected</#if> value="DRAIN_GOODS">漏发</option>
						  <option <#if vo.exceptionType??&&vo.exceptionType=='ERROR_GOODS'>selected</#if> value="ERROR_GOODS">错发</option>
						  <option <#if vo.exceptionType??&&vo.exceptionType=='QUALITY_GOODS'>selected</#if> value="QUALITY_GOODS">质量问题投诉</option>
						  <option <#if vo.exceptionType??&&vo.exceptionType=='REJECT_GOODS'>selected</#if> value="REJECT_GOODS">拒收未质检</option>
						</select>
						</span>
	
						<span><label>售后申请单状态：</label>
						<select id="status" name="status" style="width:138px;">
									<option value="">请选择</option>
									<option <#if vo.status??&&vo.status=='SALE_APPLY'>selected</#if> value="SALE_APPLY">未审核</option>
									<option <#if vo.status??&&vo.status=='SALE_COMFIRM'>selected</#if> value="SALE_COMFIRM">已审核</option>
									<option <#if vo.status??&&vo.status=='SALE_CANCEL'>selected</#if> value="SALE_CANCEL">已作废</option>
						</select>
						</span>
						<label>货品负责人：</label>
                     	<input type="text" name="supplierYgContacts" id="supplierYgContacts" value="<#if vo??&&vo.supplierYgContacts??>${vo.supplierYgContacts!""}</#if>"/>&nbsp;&nbsp;&nbsp;
						<span><input type="button" value="搜索" onclick="queryMerchantsDialogList();" class="yt-seach-btn" /></span>
					</p>
					 
				</div>         	
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th style="text-align: center;">商家名称</th>
                        <th style="text-align: center;">商家编码</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">售后申请单号</th>
                        <th style="text-align: center;">订单号</th>
                        <th style="text-align: center;">登记类型</th>
                        <th style="text-align: center;">售后申请状态</th>
                        <th style="text-align: center;">登记时间</th>
                        <th style="text-align: center;">登记人</th>
                        <th style="text-align: center;">未处理时长</th>
                        <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                		  <td class="ft-cl-r">${item['merchant_name']?default('')}</td>
                		  <td style="text-align: center;">${item['merchant_code']?default('')}</td>
                		  <td style="text-align: center;width:40px">
                		  	<#if item.brand_name??>
		                        <a href="javascript:;" data-attr="{title: '${(item.merchant_code)!''}', reason:'${(item.brand_name)!''}'}" class="f_blue brand_no">品牌</a>
		                    </#if>
                		  </td>
                		  <td style="text-align: center;">${item['apply_no']?default('')}</td>
                		  <td style="text-align: center;"><a target="_blank" href="${omsHost!''}/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?orderNo=${item['order_no']}" >${item['order_no']?default('')}</a></td>
                		  <td style="text-align: center;">${item['exceptionTypeName']?default('')}</td>  
                		  <td style="text-align: center;">${item['statusName']?default('')}</td>
                		  <td style="text-align: center;">${item['create_time']?string('yyyy-MM-dd HH:mm:ss')}</td>
                		  <td style="text-align: center;">${item['createor']!''}</td>  
                		  <td style="text-align: center;">${item['HourNumRemain']!''}</td>
                		  <td class="ft-cl-r" style="text-align: center;"><a href="${BasePath}/yitiansystem/merchants/aftersale/abnormal_after_sale_detail.sc?orderNo=${item['order_no']!''}&applyNo=${item['apply_no']?default('')}" target="_blank"><#if item.status=='SALE_APPLY'>审核<#else>查看</#if></a></td>
                		 </tr>
                	     </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="11">
                        	     抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
$("#createTimeStart").calendar({maxDate:'#createTimeEnd',format:'yyyy-MM-dd'});
$("#createTimeEnd").calendar({minDate:'#createTimeStart',format:'yyyy-MM-dd'});
});
//根据条件查询
function queryMerchantsDialogList(){
  document.queryForm.submit();
}
$(".brand_no").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		title : data.title,
		content : '<p class="picDetail">' + data.reason + '</p>',
		id : 'detailBox',
		left : $(this).offset().left - 200,
		top : _top,
		width : 100,
		closable : false
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
