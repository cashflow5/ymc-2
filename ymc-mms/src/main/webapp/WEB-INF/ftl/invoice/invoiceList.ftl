<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
     <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css" />
    
    <!-- 排序样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/active/css/sortfilter.css"/>
    <!-- 小图标库的样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />
	<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
	<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/supplier-contracts.js"></script>
	
	<!-- 日期 -->
	<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
    <title>优购商城--商家后台</title>
</head>

<body>
    <div class="container">
        <div class="toolbar">
            <div class="t-content"></div>
        </div>
        <!--工具栏start-->
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span>发票查看</span>
                    </li>
                </ul>
            </div>
            <div class="modify">
                <input type="hidden" id="pageSize" value="20">
                 <form action="${BasePath}/invoice/web/InvoiceController/queryInvoice.sc" id="queryVoform" name="queryVoform" method="post">
                   
          			<input type="hidden" name="supplierSpId" id="supplierSpId" value="${(supplierSpId)!'' }">  
          			<input type="hidden" name="bindStatus" value="${(supplierContract.bindStatus)!'' }" id="bindStatus"/>
                   <ul class="searchs w-auto clearfix">
                         <li>
                            <label for="">下单时间：</label>
                           <input type="text"   value="${(vo.orderCreateTimeStart)!''}" name="orderCreateTimeStart"   class="calendar timeStart w120" id="orderCreateTimeStart" /> 至
                            <input type="text" value="${(vo.orderCreateTimeEnd)!''}"  name="orderCreateTimeEnd"    class="calendar timeEnd" id="orderCreateTimeEnd" />
                        </li>
                         <li>
                            <label for="">登记时间：</label>
                            <input type="text" name="invoiceCreateTimeStart"  value="${(vo.invoiceCreateTimeStart)!''}"  class="calendar timeStart w120" id="invoiceCreateTimeStart" />
                                                                                                 至 <input type="text" name="invoiceCreateTimeEnd" class="calendar timeEnd"    value="${(vo.invoiceCreateTimeEnd)!''}"  id="invoiceCreateTimeEnd" />
                        </li>
                       
                      <li>
							    <span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderMainNo" name="orderMainNo" value="${vo.orderMainNo!''}"/></span>
						</li>		
								
						<li>	
								<span><label>发票号：</label>
									<input type="text" class="inputtxt" id="invoiceNo" name="invoiceNo" value="${vo.invoiceNo!''}"/></span>
								
						</li>
	                    <li> 
	                      <span><label>发票类型：</label>
									<select id="invoiceType" name="invoiceType" style="width:126px;">
										<option <#if vo.invoiceType??&&vo.invoiceType==0>selected</#if> value="">全部</option>
										<option <#if vo.invoiceType??&&vo.invoiceType==1>selected</#if> value="1">普通发票</option>
										<option <#if vo.invoiceType??&&vo.invoiceType==2>selected</#if> value="2">增值税发票</option>
									</select>
							</span>
						 </li>
						 <li>
						 	<span class="ml30">商家名称</span><input class="supplier-query-text" readonly="readonly" style="width:210px" type="text" name="supplier" id="supplier" value="<#if supplier??>${(supplier)!'' }</#if>">
							<a class="yg-btn-gray-2" href="javascript:tosupper();">选择</a>
						 
						 
						 </li>	
					 <li> 	
								<span><label>发票状态：</label>
								<select id="invoiceStatus" name="invoiceStatus" style="width:126px;">
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==-1>selected</#if> value="-1">全部</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==2>selected</#if> value="2">客服已审核</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==7>selected</#if> value="7">已配送</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==9>selected</#if> value="9">已取消</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==11>selected</#if> value="11">发票拦截</option>
								</select>  
                     </li>  
                    <li>
                           
                              <button class="ml10">搜 索</button>
                        </li>
                    </ul>
                </form>
                <div id="content_list">
                    <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                        <thead>
                            <tr>
                                <th>商家名称</th>
                                <th>发票类型</th>
                                <th>登记时间</th>
                                <th>发票抬头</th>
                                <th>开票金额</th>
                                <th>发票登记状态</th>
                                <th>未处理时长</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder?? && (pageFinder.data)?? > 
        					<#list pageFinder.data as item >
	                            <tr class="even">
	                            </tr>
	                             <#if item.orderMainNo!=''>
		                            <tr class="">
		                              <td colspan="8">
		                                  <span class="ml5 fl" style="width: 200px;">优购订单号：${item.orderMainNo?default('')}</span>
		                                  <span class="ml20 fl" style="width: 180px;">下单时间：${item.orderCreateTime?string("yyyy-MM-dd HH:mm:ss")}</span>
		                             </td>
		                            </tr>
		                            </#if>
									<tr>
									    <td>${item.merchantCode?default('')}</td>
					                    <td>${item.invoiceTypeName?default('')}</td>
					                    <td>${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
					                    <td>${item.invoiceTitle?default('')}</td>
					                    <td>${item.invoiceAmount?default('')}</td>
					                    <td>${item.invoiceStatusName?default('')}</td>
					                     <td>${item.hourNumRemain?default('')}</td>
					                    <td><a  href="javascript:void(0);" onclick="openInvoice('${(item.id!'')}')" >详情</a></td>
		                            </tr>
                             </#list>
	                   	 <#else>
								<tr>
									<td colspan="12" class="td-no">没有相关数据！</td>
								</tr>
						</#if>
                        </tbody>
                    </table>
                    <div class="bottom clearfix">
							<#if pageFinder??>
								<#import "../../../common.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

    //发票详情
    function openInvoice(id){
	    var a="${omsHost}";
	   	dialog_obj=$.dialog.open('${omsHost}/yitiansystem/invoice/invoiceController/invoiceDetail.sc?invoiceid='+id,{
            title:"发票详情",
            max:false,
            min:false,
            width: '1130px',
            height: '870px',
            lock:true,
            cancel:function(){

            }
        });
       
    }
	//选择供应商
	function tosupper(){
	  openwindow('${BasePath}/invoice/web/invoiceSupplierController/to_invoiceSupplier.sc?supplierType=1&invoiceFlag=1',800,500,'选择商家');
	}
    //关闭窗口
    function dialog_close(){
        dialog_obj.close();
    }
	function mysubmit(){
		$("#queryVoform").submit();
	}
	function initContractData(supplierId){
	
		
	}
    </script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
    
    
    
    
</body>

</html>
