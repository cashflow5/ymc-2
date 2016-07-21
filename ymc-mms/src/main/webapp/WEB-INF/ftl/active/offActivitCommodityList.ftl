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
   <!-- 小图标库的样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />
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
                    <span class="btn_txt"><a href="${BasePath}/active/web/merchantOfficialActiveController/queryMerchant.sc?activeId=${params.activeId}">返回 </a></span>
                    <span class="btn_r"></span>
                </div>
                    <div class="mt15 fl"> > 官方活动 > 查看商家</div>
            </div>
        </div>
        <!--工具栏start-->
        <div class="list_content">
            <div class="modify">
                <form action="${BasePath}/active/web/merchantOfficialActiveController/queryCommodity.sc"   method="post"  id="queryForm">
                    <ul class="searchs w-auto clearfix">
                    <input type="hidden"   value="${(params.activeId)!''}"  name='activeId' class="w120" />
                    <input type="hidden"   value="${(params.merchantCode)!''}"  name='merchantCode' class="w120" />
                    
                        <li>
                            <label for="">活动名称：</label>
                          	${params.activeName!"—"}
                        </li>
                        <li>
                            <label for="">商家名称：</label>
                            	${params.merchantName!"—"}
                        </li>
                        <li>
                            <label for="">商品编号：</label>
                            <input type="text"  value="${(params.commodityNo)!''}"   name="commodityNo" class="w120" />
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
                                <th>商品编码</th>
                                <th>商品名称</th>
                                <th>款色</th>
                                <th>品牌</th>
                                <th>市场价</th>
                                <th>优购价</th>
                                <th>活动价</th>
                                <th>最高可承担卡卷金额</th>
                                <th>用卷限制</th>
                            </tr>
                        </thead>
                        <tbody>
                          <#if pageFinder?? && (pageFinder.data)?? > 
        					<#list pageFinder.data as item >
                            <tr class="odd even">
                               <td>${item.no!"—"}</td>
                                <td>${item.commodity_name!"—"}</td>
                                <td>${item.spec_name!"—"}</td>
                                <td>${item.brand_name!"—"}</td>
                                <td>${item.public_price!"—"}</td>
                                <td>${item.sale_price!"—"}</td> 
                                <td>${item.active_price!"—"}</td>
                                <td>${item.coupon_amount!"—"}</td>
                                <td>
                                <#if item.coupons== true>允许</#if>
								<#if item.coupons== false>不允许</#if>  
                                </td>
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
								<@page.queryForm formId="queryForm"/>
							</#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
   <script type="text/javascript">

		
	
    </script>
</body>

</html>
