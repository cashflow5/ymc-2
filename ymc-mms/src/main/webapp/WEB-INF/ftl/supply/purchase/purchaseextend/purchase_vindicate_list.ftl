<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="">
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">采购单维护列表</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/supply/vindicate/extend/to_queryPurchaseTypeList.sc" class="btn-onselc">采购类型维护列表</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/supply/vindicate/extend/to_queryPosPurchaseList.sc" class="btn-onselc">运动的pos采购单同步</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/supply/vindicate/extend/to_queryPurchaseVindicateList.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>采购单号：</label>
                     <input type="text" name="purchaseCode" id="purchaseCode" value="<#if purchaseVo??&&purchaseVo.purchaseCode??>${purchaseVo.purchaseCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>pos采购单号：</label>
                     <input type="text" name="posPurchaseCode" id="posPurchaseCode" value="<#if purchaseVo??&&purchaseVo.posPurchaseCode??>${purchaseVo.posPurchaseCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryPurchaseVindicateList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>采购单号</th>
                        <th>采购类型</th>
                        <th>供应商</th>
                        <th>采购员</th>
                        <th>总数量</th>
                        <th>总金额</th>
                        <th>采购日期</th>
                        <th>仓库</th>
                        <th>状态</th>
                        <th>pos采购单</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['purchase_code']!""}</td>
		                         <td><#if item['type_name']??&&item['type_name']==102>自购固定价结算
		                        <#elseif item['type_name']??&&item['type_name']==103>自购配折结算
		                        <#elseif item['type_name']??&&item['type_name']==106>招商底价代销
		                        <#elseif item['type_name']??&&item['type_name']==107>招商扣点代销
		                        <#elseif item['type_name']??&&item['type_name']==108>招商配折结算</#if></td>
		                        <td>${item['supplier']!""}</td>
		                        <td>${item['purchaser']!""}</td>
		                        <td>${item['amount']!""}</td>
		                        <td>${item['total_price']!""}</td>
		                        <td>${item['purchase_date']!""}</td>
		                        <td>${item['warehouse_name']!""}</td>       
		                        <td><#if item['status']??&&item['status']==1>已审核
		                        <#elseif item['status']??&&item['status']==2>新建
		                        <#elseif item['status']??&&item['status']==3>已关闭
		                        <#elseif item['status']??&&item['status']==-1>已作废</#if></td>
		                        <td>${item['pos_purchase_no']!""}</td>
		                        <td>
		                            <a href="#" onclick="updateParchaseVindicate('${item['id']!''}')">修改</a>
		                       </td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
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
//查询采购单维护数据
function queryPurchaseVindicateList(){
  document.queryForm.submit();
}

//跳转到修改采购单数据页面
function updateParchaseVindicate(id){
   openwindow("${BasePath}/supply/vindicate/extend/to_updatePurchaseVindicate.sc?id="+id,1200,700,"修改采购单数据");
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>