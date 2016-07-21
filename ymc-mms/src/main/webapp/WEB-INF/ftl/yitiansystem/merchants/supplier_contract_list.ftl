<table cellpadding="0" cellspacing="0" class="list_table" width="100%">
	<thead>
            <tr>
            <th class="min-w50">创建时间</th>
            <th>商家名称</th>
            <th class="min-w50">商标</th>
            <th><span id="btn_mark_remaining_days" class="group" >商标授权剩余天数<span><input type="checkbox" name="sortByM" id="sortByM" data-ui-type="sort"></span></span></th>
            <th>商家编号</th>
            <#-- <th>商家类型</th> -->
            <th>合同编号</th>
            <th>申报人</th>
            <th class="min-w60">合同开始日期</th>
            <th class="min-w60">合同截止日期</th>
            <th><span id="btn_contract_remaining_days" class="group">合同到期剩余天数<span><input type="checkbox" name="sortByC" id="sortByC" data-ui-type="sort"></span></span></th>
            <th>合同状态</th>
            <th class="min-w50">货品负责人</th>
            <th class="min-w50">最近更新时间</th>
            <th>附件是否上传</th>
            <th class="min-w50">合同类型</th>
            <th class="min-w50">操作</th>
            </tr>
            </thead>
	<tbody>
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
		    <#list pageFinder.data as item >
				<tr>
                	<td class="ft-cl-r" style="text-align: center;">${item['createTime']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['supplier']!'--'}</td>
                	<td class="ft-cl-r" style="text-align: center;"><a href="#" data-attr="{title: '商标', reason:'<#if item.trademarkList??&&item.trademarkList?size!=0><#list item.trademarkList as mark><b>${mark.trademark!''}</b><br /></#list></#if>'}" class="trademark">查看</a></td>
                	<td class="ft-cl-r" style="text-align: center;">${item['markRemainingDays']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['supplierCode']!'--'}</td>
                	<#-- <td class="ft-cl-r" style="text-align: center;">${item['supplierType']!'--'}</td> -->
                	<td class="ft-cl-r" style="text-align: center;">${item['contractNo']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['declarant']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['effectiveDate']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['failureDate']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['contractRemainingDays']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['statusName']!'--'}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['yccontact']!'--'}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['updateTime']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;"> 
                		<#if (item['contractAttachmentCount']>0)&&(item['authorityAttachmentCount']>0)&&(item['trademarkAttachmentCount']>0) >
                			已上传
                			<#else>
                			未上传
                		</#if>
                	</td>
                	<td>
                		<#if (((item.isNewContract)??) && (item.isNewContract=='1')) >
                			新合同
                			<#else>
                			续签合同
                		</#if>
                	</td>
                	<td class="ft-cl-r" style="text-align: center;">  
                	<#if !listKind??>
						<!-- 合同查看 --> 
						<#if ((item.supplierType??) && item.supplierType == '招商供应商') >
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?id=${item['id']!''}">查看</a>
						<#else>
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail.sc?id=${item['id']!''}">查看</a>
						</#if>
					<#else> 
                	<#if (((item.renewFlag)??) && (item.renewFlag=='4')) >
						<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?id=${item['id']!''}">查看</a>
					<#else>
                	<#if item.isOld?? && (item.isOld == '1')>
						<#if listKind?? && listKind=='AUDIT_CONTRACT' && (item.renewFlag == '3')>	
						<!-- 合同审核 业务审核通过可以审核，其他状态查看--> 
						<#if ((item.status??) && item.status == '3') >
							<a href="#" onclick="fApprovalContract('${item['id']!''}');">审核</a>
						<#else>
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?listKind=MANAGE_CONTRACT&id=${item['id']!''}">查看</a>
						</#if>
						<#else>
						<!-- 新商家合同只能查看 -->		
						<#if ((item.supplierType??) && item.supplierType == '招商供应商') >
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?listKind=MANAGE_CONTRACT&id=${item['id']!''}">查看</a>
						<#else>
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail.sc?id=${item['id']!''}">查看</a>
						</#if>
						</#if>
					<#else>
                	<#if listKind?? && listKind=='MANAGE_CONTRACT'>
						<!-- 合同管理，新建，财务审核不通过，过期可编辑  -->	
                	    <#if (((item.status)??) && ((item.status == '1') || (item.status == '6') ||(item.status == '8')))>
                			<#if ( (item.supplierType??) && item.supplierType == '招商供应商') >
								<#if (item.renewFlag == '3')>
									<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?listKind=MANAGE_CONTRACT&id=${item['id']!''}">查看</a>
								<#else>
									<a href="${BasePath}/yitiansystem/merchants/businessorder/to_add_contract_merchant.sc?id=${(item['id'])!''}&supplierId=${(item['supplierId'])!''}">编辑</a>
								</#if>
							<#else>
								<a href="${BasePath}/yitiansystem/merchants/businessorder/to_add_contract.sc?id=${(item['id'])!''}">编辑</a>
							</#if>
						<#else>
                			<#if ((item.supplierType??) && item.supplierType == '招商供应商') >
								<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?listKind=MANAGE_CONTRACT&id=${item['id']!''}">查看</a>
							<#else>
								<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail.sc?id=${item['id']!''}">查看</a>
							</#if>							
						</#if>	
					<#elseif listKind?? && listKind=='AUDIT_CONTRACT'>	
						<!-- 合同审核 业务审核通过可以审核，其他状态查看--> 
						<#if ((item.status??) && item.status == '3') >
							<a href="#" onclick="fApprovalContract('${item['id']!''}');">审核</a>
						<#else>
							<a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?id=${item['id']!''}">查看</a>
						</#if>
					</#if>	
					</#if>	
					</#if>			
					</#if>			
                	</td>
                </tr>	
			</#list>
		<#else>
			 	<tr><td colSpan="12" style="text-align:center">抱歉，没有您要找的数据</td></tr>
		</#if>
	</tbody>
</table>
<div class="bottom clearfix">
	<#if pageFinder ??><#import "../../common4ajax.ftl" as page>
	<@page.queryForm formId="queryForm"/></#if>
</div>
<script>
$(".trademark").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		title : data.title,
		content : '<p class="picDetail">' + data.reason + '</p>',
		id : 'detailBox',
		left : $(this).offset().left - 150,
		top : _top,
		width : 100,
		closable : false
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});


$(function(){
	var orderBy = $("#orderBy").val();
	var sequence = $("#sequence").val();
	$("#btn_mark_remaining_days").click(function(){
		if(orderBy=='mark_remaining_days'&&sequence=='desc'){
			searchSort('mark_remaining_days','asc');
		}else if(orderBy=='mark_remaining_days'&&sequence=='asc'){
			searchSort('mark_remaining_days','desc');
		}else{
			searchSort('mark_remaining_days','desc');
		}
	});
	$("#btn_contract_remaining_days").click(function(){
		if(orderBy=='contract_remaining_days'&&sequence=='desc'){
			searchSort('contract_remaining_days','asc');
		}else if(orderBy=='contract_remaining_days'&&sequence=='asc'){
			searchSort('contract_remaining_days','desc');
		}else{
			searchSort('contract_remaining_days','desc');
		}
	});
	if(sequence==''){
		$("#sortByC").attr("nenabled","Nenabled");
		$("#sortByM").attr("nenabled","Nenabled");
	}
	if(orderBy=='contract_remaining_days'&&sequence=='asc'){
		$("#sortByC").attr("checked",true);
		$("#sortByM").attr("nenabled","Nenabled");
	}else if(orderBy=='contract_remaining_days'&&sequence=='desc'){
	    $("#sortByC").attr("checked",false);
		$("#sortByM").attr("nenabled","Nenabled");
	}
		
	if(orderBy=='mark_remaining_days'&&sequence=='asc'){
		$("#sortByM").attr("checked",true);
		$("#sortByC").attr("nenabled","Nenabled");
	}else if(orderBy=='mark_remaining_days'&&sequence=='desc'){
		$("#sortByM").attr("checked",false);
		$("#sortByC").attr("nenabled","Nenabled");
	}
	
});

//跳转审核合同页面
function fApprovalContract(id){
     location.href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?listKind=AUDIT_CONTRACT&id="+id;
}
</script>