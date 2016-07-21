<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css" />
    
    
    
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/active/css/iconfont.css"/>
	<script type="text/javascript" src="${BasePath}/js/wms/jquery-1.3.2.min.js"></script>
    <script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
    <title>优购商城--商家后台</title>
     <!-- 日期 -->
  <script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>

<body>
    <div class="container">
        <div class="toolbar">
            <div class="t-content">
                <div class="btn">
                    <span class="btn_l"></span>
                    <b class="ico_btn back"></b>
                    <span class="btn_txt"><a href="${BasePath}/active/web/merchantOfficialActiveController/qeuryMerchantOfficialActive.sc">返回</a> </span>
                    <span class="btn_r"></span>
                </div>
                    <div class="mt15 fl"> > 查看商家</div>
            </div>
        </div>
        <!--工具栏start-->
        <div class="list_content">
            <div class="modify">
                <form action="${BasePath}/active/web/merchantOfficialActiveController/queryMerchant.sc" method="post" id="queryVoform">
                    <ul class="searchs w-auto clearfix">
                     <input type="hidden"   value="${(params.activeId)!''}"  name='activeId' class="w120" />
                        <li>
                            <label for="">商家名称：</label>
                            <input type="text"   value="${(params.merchantName)!''}"  name='merchantName' class="w120" />
                        </li>
                         <li>
                            <label for="">商家编码：</label>
                            <input type="text"   value="${(params.merchantCode)!''}" name="merchantCode"  class="w120" />
                        </li>
                        <li>
                            <label for="">报名状态：</label>
                            <select class="w120" name="status">
                                	<option   value="">请选择</option>
                                 	<option  <#if params.status??&&(params.status == '1')>selected</#if>  value="1">新建</option>
                                  	<option <#if params.status??&&(params.status == '2')>selected</#if> value="2">等待审核</option>
                                  	<option <#if params.status??&&(params.status == '3')>selected</#if> value="3">审核通过</option>
                                    <option <#if params.status??&&(params.status == '4')>selected</#if> value="4">审核不通过</option>
                                    <option <#if params.status??&&(params.status == '5')>selected</#if> value="5">报名已结束</option>
                            </select>
                        </li>
                        <li>
                             <button   class="ml10">  搜 索</button>
                        </li>
                    </ul>
                </form>
                <div id="content_list">
                    <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                        <thead>
                            <tr>
                                <th>商家编号</th>
                                <th>商家名称</th>
                                <th>活动名称</th>
                                <th>报名状态</th>
                                <th>审核意见</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder?? && (pageFinder.data)?? > 
        					<#list pageFinder.data as item >
                            <tr class="odd even">
                                <td>${item.merchant_code!"—"}</td>
                                <td>${item.merchant_name!"—"}</td>
                                <td>${item.active_name!"—"}</td>
                                <td>
                                	 <#if item.status?number == 1>新建</#if>
										<#if item.status?number == 2>等待审核</#if>
										<#if item.status?number == 3>审核通过</#if>  
										<#if item.status?number == 4>审核不通过</#if>  
										<#if item.status?number == 5>报名已结束</#if>
                                </td>
                                <td>${item.audit_remark!"—"}</td>
                                <td> <a href="${BasePath}/active/web/merchantOfficialActiveController/queryCommodity.sc?activeId=${item.active_id}&merchantCode=${item.merchant_code}">查看商品</a></td>
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
    <script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
    <script type="text/javascript" src="static/js/common.min.js"></script>
 <script type="text/javascript">

	function mysubmit(){
		
		$("#queryVoform").submit();
	}
	
    </script>
</body>

</html>
