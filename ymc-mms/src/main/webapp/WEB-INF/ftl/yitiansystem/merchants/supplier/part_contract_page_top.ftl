<#macro contractPageTop>
              
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">商家名称：</label>
                            <span>${(supplierContract.supplier)!""}</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同编号：</label>
                            <span>${(supplierContract.contractNo)!""}</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">合作模式：</label>
                            <#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
			                   	<#if supplier??&&supplier.isInputYougouWarehouse??&&supplier.isInputYougouWarehouse==item.ordinal()><span>${item.description!''}</span></#if>
			                </#list>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同起止时间：</label>
                            <span>  
                        	<#if supplierContract??>  
							<#if (supplierContract.effectiveDate)??>${supplierContract.effectiveDate}</#if> 
							至 <#if supplierContract.failureDate??>${supplierContract.failureDate}</#if>
							<#if supplierContract.contractRemainingDays??>
							<#if (supplierContract.contractRemainingDays<=90) && (supplierContract.contractRemainingDays>0)>
							(<i class="Orange">即将过期，请及时更新</i>)
							<#elseif supplierContract.contractRemainingDays<=0>
							 (<i class="Red">已过期，请立即更新</i>)
							</#if>
							</#if>
							</#if>
                        	</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">成本账套名称：</label>
                            <#if costSetofBooksList??>
			                	<#list costSetofBooksList as item>
			                   	<#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode><span>${item.setOfBooksName!''}</span></#if>
			                 	</#list>
		                   	</#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同电子版：</label>
                            <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='1' || item['attachmentType']=='2'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                    	<div class="supplier-query-left">
                            <label for="" class="supplier-query-title">销售发票开具方：</label>
                            <#if (supplier.isInvoice)??><#if supplier.isInvoice == 0><span>优购</span></#if></#if>
                            <#if (supplier.isInvoice)??><#if supplier.isInvoice == 1><span>商家</span></#if></#if>
                        </div>
                        
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">是否需续签：</label>
                            <#if (supplierContract.isNeedRenew)??>			
							<#if supplierContract.isNeedRenew == '1'>
							<span>是</span>
							<#else>
							<span>否</span>
							</#if>
							</#if>
                        </div>
                    </div>
                    
                    <div class="supplier-query-wrap clearfix">
                    	<div class="supplier-query-left">
                            <label for="" class="supplier-query-title">发货类型：</label>
                            <#if (supplier.deliveryType)?? && (supplier.deliveryType?number) == 2>
                                <span>海外直发</span>
                            <#else>
                                <span>国内发货</span>
                            </#if>
                        </div>
                        
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">&nbsp;</label>
                        </div>
                    </div>
                    
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">使用优购WMS：</label>
                            <#if (supplier.isUseYougouWms)??><#if supplier.isUseYougouWms == 0><span>否</span></#if></#if>
                            <#if (supplier.isUseYougouWms)??><#if supplier.isUseYougouWms == 1><span>是</span></#if></#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">使用ERP系统对接：</label>
                            <#if (supplierContract.isUseERP)??><#if supplierContract.isUseERP == '1'>是</#if></#if>
                			<#if (supplierContract.isUseERP)?? && supplierContract.isUseERP == '0'>否</#if>
                        </div>
                    </div>
					<div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <#--<label for="" class="supplier-query-title">优惠券分摊比例：</label>
                            <span>${(supplier.couponsAllocationProportion)?string("#.##")}%</span> -->
                            <label for="" class="supplier-query-title">卡券最高分摊：</label>
                            <span><#if (supplierExpand.maxCouponAmount)??>${supplierExpand.maxCouponAmount!0}元</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">结算方式：</label>
						        <#if (supplierContract.clearingForm)??&&supplierContract.clearingForm='1'><span>底价结算</span></#if>
						        <#if (supplierContract.clearingForm)??&&supplierContract.clearingForm='2'><span>扣点结算</span></#if>
						        <#if (supplierContract.clearingForm)??&&supplierContract.clearingForm='3'><span>配折结算</span></#if>
						        <#if (supplierContract.clearingForm)??&&supplierContract.clearingForm='4'><span>促销结算</span></#if>
                        </div>
                    </div>
                    
                    <#if (supplierContract.renewFlag)?? && supplierContract.renewFlag=='3'>  
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">上期保证金金额：</label>
                            <span><#if (supplierContract.preDeposit)??>${(supplierContract.preDeposit)?string("#.##")}元
							</#if></span><span style="color:red"> <#if (supplierContract.isTransferDeposit)?? &&supplierContract.isTransferDeposit=='1'> 转入本期
							</#if> </span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">上期平台使用费金额：</label>
                            <span><#if (supplierContract.preUsefee)??>${(supplierContract.preUsefee)?string("#.##")}元
						</#if></span>
                        </div>
                    </div>  
                    </#if>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">是否需缴纳保证金：</label>
                            <#if (supplierContract.isNeedDeposit)??>			
							<#if (supplierContract.isNeedDeposit) == '1'>
							<span>是</span>
							<#else>
							<span>否</span>
							</#if>
							</#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">是否需缴纳平台使用费：</label>
                            <#if (supplierContract.isNeedUseFee)??>			
							<#if supplierContract.isNeedUseFee == '1'>
							<span>是</span>
							<#else>
							<span>否</span>
							</#if>
							</#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">保证金金额：</label>
                            <span><#if (supplierContract.isTransferDeposit)?? &&supplierContract.isTransferDeposit=='1'>
							<#if (supplierContract.preDeposit)??>${(supplierContract.preDeposit)?string("#.##")} + 
							</#if></#if>
							<#if (supplierContract.deposit)??>${(supplierContract.deposit)?string("#.##")}元
                            <#if (supplierContract.depositStatus)??>
                            	<#if supplierContract.depositStatus == '1'>(<i class="Red">未缴纳</i>)</#if>
                            	<#if supplierContract.depositStatus == '2'>(<i class="Green">已缴纳</i>)
								<#if listKind??&&listKind=="MANAGE_CONTRACT">
								<a class="Blue ml10" href="javascript:requestRefund('${(supplierContract.id)}','${(totalRefundDeposit)?string("#.##")}','deposit')">申请退款</a>
								</#if>
								</#if>
                            	<#if supplierContract.depositStatus == '3'>(<i class="Blue">退款中</i>)</#if>
                            	<#if supplierContract.depositStatus == '4'>(<i class="Red">已退款</i>)</#if>
                            	<#if supplierContract.depositStatus == '5'>(<i class="Red">已转入下期</i>)</#if>
								<#if supplierContract.depositStatus == '6'>(<i class="Red">转入下期中</i>)</#if>
                            	<#if supplierContract.depositStatus == '7'>(<i class="Red">上期已转入</i>)</#if>
                            	<#if supplierContract.depositStatus == '8'>(<i class="Red">本期已收款</i>)</#if>
                            </#if>
							</span></#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">平台使用费金额：</label>
                            <span><#if (supplierContract.useFee)??>${(supplierContract.useFee)?string("#.##")}元
							<#if (supplierContract.useFeeStatus)??>
                            	<#if supplierContract.useFeeStatus == '1'>(<i class="Red">未缴纳</i>)</#if>
                            	<#if supplierContract.useFeeStatus == '2'>(<i class="Green">已缴纳</i>)
								<#if listKind??&&listKind=="MANAGE_CONTRACT">
								<a class="Blue ml10" href="javascript:requestRefund('${(supplierContract.id)}','${(supplierContract.useFee)?string("#.##")}','useFee')">申请退款</a>
								</#if>
								</#if>
                            	<#if supplierContract.useFeeStatus == '3'>(<i class="Blue">退款中</i>)</#if>
                            	<#if supplierContract.useFeeStatus == '4'>(<i class="Red">已退款</i>)</#if>
                            </#if>
						</span></#if>
                        </div>
                    </div>  
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">申报人：</label>
                            <span><#if (supplierContract.declarant)??>${(supplierContract.declarant)!""}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-main">
                            <label for="" class="supplier-query-title">备注：</label>
                            <span><#if (supplierContract.remark)??>${supplierContract.remark}</#if></span>
                        </div>
                    </div>            
                </div>     
			
</#macro>
