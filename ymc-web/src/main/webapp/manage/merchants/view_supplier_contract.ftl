<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/merchant/merchans_center.css"/>
    
  <title>优购商城--商家后台</title>
  </head>
 
<script>
var basePath = '${BasePath}';

$(function(){
	$(".download").live("click",function(){	 	
		var fileName = $(this).attr("fileName");
		var realName = $(this).attr("realName");
	 
		$("#fileName").val(fileName);
		$("#realName").val(realName);
		$("#downloadForm").submit();
	});
});
</script>  
<body>
     <div id="newmain" class="main_bd fr">
     <form id="downloadForm" action="${BasePath}/merchants/login/downLoadContractAttachment.sc" method="post"> 
	 			<input type="hidden" name="fileName" id="fileName">
	 			<input type="hidden" name="realName" id="realName">
	</form>
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 设置 &gt; 合同信息</p>
                    <div class="tab_panel relative">
                        <ul class="tab">
                           
                         <li  onclick="location.href='${BasePath}/merchants/login/to_MerchantsUser.sc'" ><span>基本信息</span></li>
                         <li onclick="location.href='${BasePath}/merchants/login/to_merchant_shop.sc'"><span>店铺信息</span></li>
                         <li  class="curr"><span>合同信息</span></li>
                        </ul>
                        <div class="tab_content">
                            <div class="info-box">
                                <h3>合同信息</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">商家名称：</span>${(supplierContract.supplier)!""}</li>
                                    <li><span class="stitle">合同编号：</span>${(supplierContract.contractNo)!""}</li>
                                    <li><span class="stitle">合作模式：</span>
                                    <span>
                                    <#if (supplier.isInputYougouWarehouse)??>
                                    <#if supplier.isInputYougouWarehouse ==0>
                                    		不入优购仓库，商家发货
                                    <#elseif supplier.isInputYougouWarehouse == 1>
                                   			入优购仓库，优购发货
                                    <#elseif supplier.isInputYougouWarehouse == 2>
                                   			 不入优购仓库，优购发货
                                    </#if>
                                    </#if>
                                    </span>
                                         
                                    </li>
                                    <li><span class="stitle">合同起止时间：</span>
                                    	<#if supplierContract??>  
										<#if (supplierContract.effectiveDate)??>${supplierContract.effectiveDate}</#if> 
										至 <#if (supplierContract.failureDate)??>${supplierContract.failureDate}</#if>
										<#if supplierExpand??&&supplierExpand.contractRemainingDays??>
										<#if supplierExpand??&& (supplierExpand.contractRemainingDays<=90) && (supplierExpand.contractRemainingDays>0)>
										(<i class="Orange">即将过期，请及时更新</i>)
										<#elseif supplierExpand??&&supplierExpand.contractRemainingDays<=0>
										 (<i class="Red">已过期，请立即更新</i>)
										</#if>
										<#if supplierExpand??&& supplierExpand.contractRemainingDays != supplierContract.contractRemainingDays>
										(<i style="color:Green">已续签</i>)
										</#if>
										</#if>
										</#if>
                                    </li>
                                    <li><span class="stitle">成本帐套名称：</span>
                                    <#if costSetofBooksList??>
					                	<#list costSetofBooksList as item>
					                   	<#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode><span>${(item.setOfBooksName)!''}</span></#if>
					                 	</#list>
				                   	</#if>
                                    </li>
                                    <li><span class="stitle">合同电子版：</span>
			 						<#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
					 					<#list supplierContract.attachmentList as item >
					 						<#if item['attachmentType']=='1'>
					 							[<a href="javascript:void(0)" class="download blue" fileName="${(item['attachmentName'])!''}" realName="${(item['attachmentRealName'])!''}">查看</a>]
					 						</#if>
					 					</#list>
				 					</#if>
                                    </li>
                                    <li><span class="stitle">结算方式：</span>
                                        <#if supplierContract??&&supplierContract.clearingForm='1'><span>底价结算</span></#if>
								        <#if supplierContract??&&supplierContract.clearingForm='2'><span>扣点结算</span></#if>
								        <#if supplierContract??&&supplierContract.clearingForm='3'><span>配折结算</span></#if>
								        <#if supplierContract??&&supplierContract.clearingForm='4'><span>促销结算</span></#if>
                                    </li>
                                     <li><span class="stitle"></span><li>
                                    <#-- 2016.4.6 隐藏是否需要续签 敏感信息，added by zhangfeng
                                    <li><span class="stitle">是否需续签：</span>
	                                     <#if supplierContract?? && supplierContract.isNeedRenew??>			
										<#if supplierContract.isNeedRenew == '1'>
										<span>是</span>
										<#elseif supplierContract.isNeedRenew == '0'>
										<span>否</span>
										<#else>
										<span>--</span>
										</#if>
										</#if>
                                    </li>
                                    -->
                                    <li><span class="stitle">验收差异处理方式：</span>
                                      <#if supplier?? && supplier.couponsAllocationProportion??>
			                            <span>${(supplier.couponsAllocationProportion)?string("#.##")}%</span>
			                            </#if>
                                    </li>
                                    <li><span class="stitle">验销售发票开具方：</span>
	                                     <#if supplier??><#if supplier.isInvoice == 0><span>优购</span></#if></#if>
	                           			<#if supplier??><#if supplier.isInvoice == 1><span>商家</span></#if></#if>
                                    </li>
                                     <li><span class="stitle">使用优购WMS：</span>
                                     	 <#if supplier??><#if supplier.isUseYougouWms == 0><span>否</span></#if></#if>
                           				 <#if supplier??><#if supplier.isUseYougouWms == 1><span>是</span></#if></#if>
                                     </li>
                                      <li><span class="stitle">使用ERP系统对接：</span>
                                      	 <#if supplierContract?? && supplierContract.isUseERP??><#if supplierContract.isUseERP == '1'>是</#if></#if>
                						 <#if supplierContract?? && supplierContract.isUseERP?? && supplierContract.isUseERP == '0'>否</#if>
                                      </li>
                                    <li><span class="stitle">是否需缴纳保证金：</span>
	                                    <#if supplierContract?? && supplierContract.isNeedDeposit??>			
											<#if supplierContract.isNeedDeposit == '1'>
											<span>是</span>
											<#else>
											<span>否</span>
											</#if>
										<#else>
										<span>否</span>
										</#if>
                                    </li>
                                    <li><span class="stitle">是否需缴纳平台使用费：</span>
	                                    <#if supplierContract?? && supplierContract.isNeedUseFee??>			
											<#if supplierContract.isNeedUseFee == '1'>
											<span>是</span>
											<#else>
											<span>否</span>
											</#if>
										<#else>
										<span>否</span>
										</#if>
                                    </li>
                                    <li><span class="stitle">保证金金额：</span>
                                       ${(supplierContract.deposit)?default(0)?string("#.##")}元
                                       
                                      
                                       
                                       <#if (supplierContract.depositStatus)??>
		                            	<#if (supplierContract.depositStatus) == '1'>(<i class="red">未缴纳</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '2'>(<i class="green">已缴纳</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '3'>(<i class="blue">退款中</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '4'>(<i class="red">已退款</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '5'>(<i class="red">已转入下期</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '6'>(<i class="red">转入下期中</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '7'>(<i class="green">上期已转入</i>)</#if>
		                            	<#if (supplierContract.depositStatus) == '8'>(<i class="green">本期已收款</i>)</#if>
		                            </#if>
                                       </span>
                                    </li>
                                    <li><span class="stitle">平台使用费金额：</span>
	                                   <span>
	                                    ${(supplierContract.useFee)?default(0)?string("#.##")}元
	                                    <#if (supplierContract.useFeeStatus)??>
			                            	<#if (supplierContract.useFeeStatus) == '1'>(<i class="red">未缴纳</i>)</#if>
			                            	<#if (supplierContract.useFeeStatus) == '2'>(<i class="green">已缴纳</i>)</#if>
			                            	<#if (supplierContract.useFeeStatus) == '3'>(<i class="blue">退款中</i>)</#if>
			                            	<#if (supplierContract.useFeeStatus) == '4'>(<i class="red">已退款</i>)</#if>
			                            </#if>
	                                    </span> 
	                                    <span>
                                    </li>
                                    <li><span class="stitle">卡券最高分摊：</span><#if supplierExpand?? && (supplierExpand.maxCouponAmount)??>${supplierExpand.maxCouponAmount!0}</#if></li>
                                    <#--<li><span class="stitle">优惠券分摊比例：</span>${(supplier.couponsAllocationProportion)?string("#.##")}%</li> -->
                                    <li><span class="stitle">申报人：</span>${(supplierContract.declarant)!""}</li>
                                </ul>
                            </div>                            
                            <div class="info-box">
                                <h3>授权资质</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">授权书：</span>
                                        <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
						 					<#list supplierContract.attachmentList as item >
						 						<#if item['attachmentType']=='3'>
						 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
						 						</#if>
						 					</#list>
					 					</#if>
                                    </li>
                                    <li><span class="stitle">商标注册证：</span>
                                       <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
						 					<#list supplierContract.attachmentList as item >
						 						<#if item['attachmentType']=='4'>
						 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
						 						</#if>
						 					</#list>
					 					</#if>
                                    </li>
                                    <li><span class="stitle">商标授权：</span>
                                    	<#if (supplierContract.markRemainingDays)??>
                                    		<#if ((supplierContract.markRemainingDays?int)>0)>
                                    		<span style="color:green">有效</span></li>
                                    		<#else>
                                    		<span style="color:red">过期</span></li>
                                    		</#if>
                                    	</#if>
                                    </li>
                                </ul>
                                <table class="authorised-list">
                                    <thead>
                                        <tr>
                                            <th>商标</th>
                                            <th>商标专利授权人</th>
                                            <th>类别</th>
                                            <th>注册商标号</th>
                                            <th>注册开始日期</th>
                                            <th>注册截止日期</th>
                                            <th>授权级别</th>
                                            <th>被授权人</th>
                                            <th>授权期起</th>
                                            <th>授权期止</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <#assign size=1>
						 			<#if (supplierContract.trademarkList)??&&(supplierContract.trademarkList?size>0)>
					 					<#list supplierContract.trademarkList as item >
					 						<#if (item.trademarkSubList?size=0)>
					 							<#assign size=1>
					 						<#else>
					 							<#assign size=(item.trademarkSubList?size)>
					 						</#if>
					 						<tr>
					 							<td class="ft-cl-r" rowspan="${size}">${item.trademark!''}</td>
					 							<td class="ft-cl-r" rowspan="${size}">${item.authorizer!''}</td>
					 							<td class="ft-cl-r" rowspan="${size}">${item.type!''}</td>
					 							<td class="ft-cl-r" rowspan="${size}">${item.registeredTrademark!''}</td>
					 							<td class="ft-cl-r" rowspan="${size}">${item.registeredStartDate!''}</td>
					 							<td class="ft-cl-r" rowspan="${size}">${item.registeredEndDate!''}</td>
					 							<#if (item.trademarkSubList?size>0)>
					 								<#list item.trademarkSubList as sub >
					 									<#if sub_index==0>
					 										<td class="ft-cl-r">${sub.levelStr!''}级授权</td>
					 										<td class="ft-cl-r">${sub.beAuthorizer!''}</td>
					 										<td class="ft-cl-r">${sub.authorizStartdate!''}</td>
					 										<td class="ft-cl-r">${sub.authorizEnddate!''}</td></tr>
					 										<#else>
					 										<tr>
					 											<td class="ft-cl-r">${sub.levelStr!''}级授权</td>
					 											<td class="ft-cl-r">${sub.beAuthorizer!''}</td>
					 											<td class="ft-cl-r">${sub.authorizStartdate!''}</td>
					 											<td class="ft-cl-r">${sub.authorizEnddate!''}</td>
					 										</tr>
					 									</#if>
					 								</#list>
					 							<#else>
					 								<td class="ft-cl-r"></td>
					 								<td class="ft-cl-r"></td>
					 								<td class="ft-cl-r"></td>
					 								<td class="ft-cl-r"></td></tr>
					 							</#if>
					 					</#list>
						 			</#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
