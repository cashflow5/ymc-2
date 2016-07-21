<#macro trademarkView pageSrc=''>
              
                <h1 class="supplier-title">品牌品类&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="close" id="click_show" onClick="clickShow(this);">[点此展开]</a></h1> 
                  <div id="content_list">            
			 		<table id="target_for_click_show" cellpadding="0" cellspacing="0" class="list_table table_gray hidden" width="100%">
			 			<thead>
			 			<tr>
				            <th width="2%">品牌</th>
				            <th width="5%">一级品类</th>
		                    <th width="8%">二级品类</th>
			 			</tr>
						</thead>
			 			<#if catList??&&(catList?size>0)>
		 					<#list catList as item >
		 						<tr>
		 							<td class="ft-cl-r" >${item.no!''}</td>
		 							<td class="ft-cl-r" >${item.catName!''}</td>
		 							<td class="ft-cl-r" >${item.structName!''}</td>
		 					</#list>
			 			</#if>
			 		</table>
                
                </div>
                <div id="content_list"><br/><br/><br/> </div>
                 <h1 class="supplier-title">授权资质</h1>
                 <div class="form-box">
                     <div class="supplier-query-wrap clearfix">
                         <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">授权书：</label>
                            <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='3'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                         </div>
                         <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">商标注册证：</label>
                            <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='4'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                         </div>
                     </div>
                     <div class="supplier-query-wrap clearfix">
                         <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">商标授权：</label>
                            <#if (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>90) >
	                        <span style="color:green">有效</span></li>
							<#elseif (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>0) >
							<span class="Orange">即将过期，请及时更新</span></li>
							<#elseif (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)<1) >
	                        <span class="Red">已过期，请立即更新</span></li>
	                        <#else>
							</#if>
                         </div>
                         <div class="supplier-query-right">
                         	<#if pageSrc=='natural'>
                            <label for="" class="supplier-query-title">补充协议：</label>
                            <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='2'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
							</#if>
                         </div>
                     </div>
                 </div>
                  <div id="content_list">             
	 		<table  cellpadding="0" cellspacing="0" class="list_table table_gray" width="100%">
	 			<thead>
	 			<tr>
		            <th width="5%">商标</th>
		            <th width="5%">绑定品牌</th>
                    <th width="2%">扣点</th>
		            <th width="5%">商标专利授权人</th>
		            <th width="2%">类别</th>
		            <th width="5%">注册商标号</th>
		            <th width="5%">注册开始日期</th>
		            <th width="5%">注册截止日期</th>
		            <th width="5%">授权级别</th>
		            <th width="5%">被授权人</th>
		            <th width="5%">授权期起</th>
		            <th width="5%">授权期止</th>
	 			</tr>
				</thead>
	 			<#assign size=1>
	 			<#if supplierContract??&&(supplierContract.trademarkList)??&&(supplierContract.trademarkList?size>0)>
 					<#list supplierContract.trademarkList as item >
 						<#if (item.trademarkSubList?size=0)>
 							<#assign size=1>
 						<#else>
 							<#assign size=(item.trademarkSubList?size)>
 						</#if>
 						<tr>
 							<td class="ft-cl-r" rowspan="${size}">${item.trademark!''}</td>
 							<td class="ft-cl-r" rowspan="${size}">${item.brandName!''}</td>
 							<td class="ft-cl-r" rowspan="${size}">${item.deductionPoint!''}</td>
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
	 		</table>
		 </div>
			
</#macro>
