<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/supplierCommodity.js"></script>
<script>
	var path="${BasePath}";
</script>
<title>无标题文档</title>
</head>
<body>
<div class="contentMain">

        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>商品管理 &gt; 商品库存
            </div>
            
            <div class="content">
            
                <div class="mb-btn-fd-bd">
                    <div class="mb-btn-fd relative">
                    <span class="btn-extrange absolute">
                        <ul class="onselect">
                            <li class="pl-btn-lfbg"></li>
                            <li class="pl-btn-ctbg"><a  class="btn-onselc">商品库存列表</a></li>
                            <li class="pl-btn-rtbg"></li>
                        </ul>
                    </span>
                  </div>
                </div>
           
         		<div class="yt-c-top">
            	  <form action="suppliercommotityList.sc" name="supplierCommodity" id="supplierCommodity" method="post" >
                     <label for="t1">日期范围：</label>
                     <#if comVo??&&comVo.createDate??>
                     	<input type="text" name="createDate" id="createDate" value="${comVo.createDate}" />
                     <#else>
                     	<input type="text" name="createDate" id="createDate" />
                     </#if>
                     -
                     <#if comVo??&&comVo.updateDate??>
                     	<input type="text" name="updateDate" id="updateDate" value="${comVo.updateDate}" />
                     <#else>
                     	<input type="text" name="updateDate" id="updateDate" />
                     </#if>
                     <div style="padding-top:5px;"></div>
                     <label for="t2">所有分类：</label>
                     <!--onchange="changeBrand(this)"-->
                     <select name="catNo" onchange="changeBrand(this)" style="width:151px;">
                     	<option value="" selected>----请选择----</option>
                     	<#if catList??>
                     		<#list catList as cat>
                     			<#if cat.catName??>
                     				<#if comVo??&&comVo.catNo??&&comVo.catNo==cat.id>
                     					<option selected value="${cat.id}">${cat.catName}</option>
                     				<#else>
                     					<option value="${cat.id}">${cat.catName}</option>
                     				</#if>
                     				
                     			</#if>
                     		</#list>
                     	</#if>
                     </select>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <label for="t3">所有品牌： </label>
                     <input type="hidden" id="brandNoIds" name="brandName" />
                      <select name="brandNo" id="brandNo" style="width:90px;">
                          <option value="" selected>----请选择----</option>
                          <#if comVo??&&comVo.brandName??>
                          	<option value="${comVo.brandNo}" selected>${comVo.brandName}</option>
                          </#if>
                     </select>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <label for="t4">商品状态：</label>
                      <select name="showind">
                     	<option value="" selected>请选择</option>
                     	<option <#if comVo??&&comVo.showind??&&comVo.showind=="1">selected</#if> value="1">上架</option>
                     	<option <#if comVo??&&comVo.showind??&&comVo.showind=="0">selected</#if> value="0">下架</option>
                     </select>
                     <label for="t5">自定义：</label>
                      <select name="selectValue" onchange="clearText(this)">
                     	<option value="" selected>请选择</option>
                     	<option <#if comVo??&&comVo.selectValue??&&comVo.selectValue=="commodityName">selected</#if> value="commodityName">商品名称</option>
                     	<option <#if comVo??&&comVo.selectValue??&&comVo.selectValue=="no">selected</#if> value="no">商品编号</option>
                     </select>
                     <#if  comVo??&&comVo.textValue??>
                    	<input name="textValue" value="${comVo.textValue?default("")}" type="text" id="textValueId" size="12" />
                     <#else>
                     	<input name="textValue" value="${comVo.textValue?default("")}" type="text" id="textValueId" size="12" />
                     </#if>
                    <input type="button" value="搜&nbsp;索" class="yt-seach-btn" onclick="seacher()" />
                   
              	<!--批量修改，删除参数-->
              	<div>
              		<input type="hidden" id="hiddenid" name="val" />
              	</div>
           </form>
         </div>
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>
                    <th style="width:5%;font-weight:bold;"><input type="checkbox" ></th>
                    <th style="width:25%;font-weight:bold;">商品名称</th>
                    <th style="width:11%;font-weight:bold;">商品编号  </th>
                    <th style="width:11%;font-weight:bold;">分类名</th>
                    <th style="width:11%;font-weight:bold;">市场价</th>
                    <th style="width:11%;font-weight:bold;">库存</th>
                    <th style="width:5%;font-weight:bold;">状态</th>
                    <th style="width:11%;font-weight:bold;">添加时间</th>
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as com>
	                   		 <tr>
				                 <td>&nbsp;<input type="checkbox" value="${com.id?default("&nbsp;")}"></td>
				                 <td>${com.commodityName?default("&nbsp;")}</td>
				                 <td>${com.no?default("&nbsp;")}</td>
				                 <td>${com.catName?default("&nbsp;")}</td>
				                 <td>${com.publicPrice?default("&nbsp;")}</td>
				                 <td>${com.inventoryQuantity?default("&nbsp;")}</td>
				                 <td>
				                   <#if com.showind==1>上架</#if>
				                   <#if com.showind==0>下架</#if>
				                 </td>
				                 <td class="td0">${com.createDate?default("&nbsp;")}</td>
				             </tr>
                   		</#list>
                   	<#else>
                   		<tr class="div-pl-list">
							<td style="text-align:center;" colspan="8">
								<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据</font>
							</td>
						</tr>
                    </#if>
                    </tbody>
                    </table> 
                    </div>
                    <div class="blank10"></div> 
         <div class="yt-pl-div">
			&nbsp;&nbsp;<input type="checkbox" name="" id="chooseAllid" onclick="chooseAll(this)" />全选&nbsp;
			<a href="#" onclick="delAll()">批量删除</a>
			<a href="#" onclick="batchAudit()">批量审核</a>
		</div>
		<div class="div-pl-bt">
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="supplierCommodity" />
	    </div>
     </div>
  </div>
</body>
</html>
