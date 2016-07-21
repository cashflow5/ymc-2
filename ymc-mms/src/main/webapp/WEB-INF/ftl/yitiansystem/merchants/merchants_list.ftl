<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css" />
<!-- 排序样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/sortfilter.css" />
<!-- 小图标库的样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />

<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<#if listKind??&&(listKind=='B_APPROVAL'||listKind=='F_APPROVAL'||listKind=='ASSIGN')>
	<#else>
	<div class="toolbar">
		<div class="t-content">
		
			<div class="btn" onclick="addMerchants();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加商家</span>
	        	<span class="btn_r"></span>
        	</div> 
        
        	<!--<div class="btn" onclick="doExport();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">导出商家</span>
	        	<span class="btn_r"></span>
        	</div> --> 
		</div>
	</div>
	</#if>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="${BasePath}/yitiansystem/merchants/manage/to_supplier_List.sc"  class="btn-onselc">商家列表</a></span>
				</li>
				<#if listKind??&&(listKind=='B_APPROVAL'||listKind=='F_APPROVAL'||listKind=='ASSIGN')>
				<#else>
				<li >
				  <span><a href="${BasePath}/yitiansystem/merchants/manage/draft_supplier_List.sc"  class="btn-onselc">草稿列表</a></span>
				</li>
				</#if>
			</ul>
		</div>
 <div class="modify"> 
     <form action='${BasePath}/yitiansystem/merchants/manage/to_supplier_List.sc' name="queryForm" id="queryForm" method="post">
     			<input type="hidden" name="isCanAssign" value="${isCanAssign!''}"/>
     			<input type="hidden" name="listKind" value="${listKind!''}"/> 
     			<input type="hidden" id="sequence" name="sequence" value="${merchantsVo.sequence!''}"/> <!--排序用字段 -->
     			<input type="hidden" id="orderBy" name="orderBy" value="${merchantsVo.orderBy!''}"/> <!--排序用字段 -->
     			
  			  	<div class="wms-top">
  			  	    <p>
                     <label class="first">商家名称：</label>
                     <input  type="text" name="supplier" id="supplier" value="<#if merchantsVo??&&merchantsVo.supplier??>${merchantsVo.supplier!""}</#if>"/>
                     <label>商品品牌：</label>
                     <input  type="text" name="brandName" id="brandName" value="<#if merchantsVo??&&merchantsVo.brandName??>${merchantsVo.brandName!""}</#if>"/>
                     <label>商家编号：</label>
                     <input type="text" name="supplierCode" id="supplierCode" value="<#if merchantsVo??&&merchantsVo.supplierCode??>${merchantsVo.supplierCode!""}</#if>"/>
                     <label>状态：</label>
                     <select name="isValid" id="isValid" style="width:80px;">
                        <option value="">--请选择--</option>
                        <#if listKind??&&listKind=="B_APPROVAL">
                        <option value="3" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==3>selected</#if>>待审核</option>
                        <option value="4" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==4>selected</#if>>业务审核通过</option>
                        <option value="6" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==6>selected</#if>>业务审核不通过</option>
                        <#elseif listKind??&&listKind=="F_APPROVAL">
                        <option value="4" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==4>selected</#if>>业务审核通过</option>
                        <option value="5" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==5>selected</#if>>财务审核通过</option>
                        <option value="7" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==7>selected</#if>>财务审核不通过</option>
                        <option value="1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==1>selected</#if>>启用</option>
                        <option value="-1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==-1>selected</#if>>停用</option>
                        <#else>
                        <option value="2" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==2>selected</#if>>新建</option>
                        <option value="3" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==3>selected</#if>>待审核</option>
                        <option value="4" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==4>selected</#if>>业务审核通过</option>
                        <option value="6" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==6>selected</#if>>业务审核不通过</option>
                        <option value="5" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==5>selected</#if>>财务审核通过</option>
                        <option value="7" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==7>selected</#if>>财务审核不通过</option>
                        <option value="1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==1>selected</#if>>启用</option>
                        <option value="-1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==-1>selected</#if>>停用</option>
                        </#if>
                     </select>
                    </p>
                    <p style="margin-top:5px;">
  			  	     <#-- 
                     <label class="first">商家类型：</label>
                     <select  name="type">
                        <option value="0">--请选择--</option>
                        <#if listKind??&&(listKind=='B_APPROVAL'||listKind=='F_APPROVAL')>
                        <option value="1" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==1>selected</#if>>招商供应商</option>
                        <#else>
                        <option value="1" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==1>selected</#if>>招商供应商</option>
                        <option value="2" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==2>selected</#if>>普通供应商</option>
                        </#if>
                     </select>
                     -->
                     <label class="first">合作模式：</label>
                     <select name="isInputYougouWarehouse" id="isInputYougouWarehouse">
                        <option value="">--请选择--</option>
                       	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                       	<option value="${item.ordinal()}" <#if merchantsVo.isInputYougouWarehouse?default(-1) == item.ordinal()>selected="selected"</#if>>${item.description}</option>
                       	</#list>
                     </select>
                     <label for="">是否需续签：</label>
                	 <select name="isNeedRenew">
                    	<option value="">请选择</option>
                 		<option value="1" <#if merchantsVo??&&merchantsVo.isNeedRenew??&&merchantsVo.isNeedRenew=='1'>selected</#if>>是</option>
                 		<option value="0" <#if merchantsVo??&&merchantsVo.isNeedRenew??&&merchantsVo.isNeedRenew=='0'>selected</#if>>否</option>
                	 </select>
                     <label for="">合同类型：</label>
                	 <select name="isNewContract">
                    	<option value="">请选择</option>
                 		<option value="1" <#if merchantsVo??&&merchantsVo.isNewContract??&&merchantsVo.isNewContract=='1'>selected</#if>>新合同</option>
                 		<option value="0" <#if merchantsVo??&&merchantsVo.isNewContract??&&merchantsVo.isNewContract=='0'>selected</#if>>续签合同</option>
                	 </select>
                	 </p>
               	 	<p style="margin-top:5px;">
                     <label class="first" >更新人：</label>
                     <input type="text" name="updateUser" id="updateUser" value="<#if merchantsVo??&&merchantsVo.updateUser??>${(merchantsVo.updateUser)!""}</#if>"/>
                     <label>货品负责人：</label>
                     <input type="text" name="supplierYgContacts" id="supplierYgContacts" value="<#if merchantsVo??&&merchantsVo.supplierYgContacts??>${(merchantsVo.supplierYgContacts)!""}</#if>"/>
                     <input type="button" value="搜索" onclick="queryMerchants();" class="yt-seach-btn" />
                     <input type="hidden" id="flagForReminds" name="flagForReminds" value="<#if merchantsVo??&&(merchantsVo.flagForReminds)??>${(merchantsVo.flagForReminds)!''}</#if>"/> <!--只查资质提醒的数据用的字段 -->
     				</p>
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th width="200px;">商家名称</th>
                        <th>商家编号</th>
                        <#-- 
                        <th>商家类型</th>
                        -->
                        <th>品牌品类</th>
                        <th width="40px;">状态</th>
                       
                        <th><span id="btn_contract_remaining_days" class="group">合同到期剩余天数<span>
                        <input type="checkbox" name="sortByC" id="sortByC" data-ui-type="sort"></span></span></th>
                        <th><span id="btn_mark_remaining_days" class="group" >商标授权剩余天数<span>
                        <input type="checkbox" name="sortByM" id="sortByM" data-ui-type="sort"></span></span></th>
                       
                        <th>商家资质提醒 
                            <#if merchantsVo??&&(merchantsVo.flagForReminds)??&&(merchantsVo.flagForReminds)=='true'>
                        		   <p id="btn_flag_for_reminds" class="btn_thicker">
                        	<#else>
                        		   <p id="btn_flag_for_reminds" class="btn_normal">
                        	</#if>
                            ${totalRemind!''}</p>
                        
                        </th>
                        <th>是否需续签</th>
                        <th>合同类型</th>
                        <th width="80px;">最后更新时间</th>
                        <th>更新人</th>
                        <th style="text-align:center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['supplier']!""}</td>
		                        <td><a href="#" onclick="reviewMerchants('${item['id']!''}');">${item['supplierCode']!""}</a></td>
		                        <#--
		                        <td>${item['supplierType']!""}</td>
		                        -->
			                    <td>
		                        	<#if item.brandVos??&&item.brandVos?size==1>
		                        		<a href="javascript:;"  class="f_blue"><#list item.brandVos as brand><b>${brand.brandName!''}</b><br /></#list></a>
		                        	<#elseif ((item.brandVos??&&item.brandVos?size!=1) && (item.brandVos??&&item.brandVos?size!=0))> 
	                            		<a href="javascript:;" data-attr="{title: '${(item.supplierCode)!''}', reason:'<#if item.brandVos??&&item.brandVos?size!=0><#list item.brandVos as brand><b>${brand.brandName!''}</b><br /></#list></#if>'}" class="f_blue brand_no">品牌</a>
		                        	</#if>
		                        </td>
		                        <td><#if item['isValid']??&&item['isValid']==1>启用
			                        <#elseif item['isValid']?? && item['isValid']==2>新建
			                        <#elseif item['isValid']?? && item['isValid']==7>财务审核不通过
			                        <#elseif item['isValid']?? && item['isValid']==3>待审核
			                        <#elseif item['isValid']?? && item['isValid']==4>业务审核通过
			                        <#elseif item['isValid']?? && item['isValid']==6>业务审核不通过
			                        <#elseif item['isValid']?? && item['isValid']==5>财务审核通过
			                        <#else>停用
			                        </#if>
			                    </td>
			                    <td><#if item['contractRemainingDays']??&&item['contractRemainingDays']<=90><span class="red"></#if>${item['contractRemainingDays']!""}</span></td>
                                <td><#if item['markRemainingDays']??&&item['markRemainingDays']<=90><span class="red"></#if>${item['markRemainingDays']!""}</span></td>
                                <td>
                                <#if item['expireNum']??&&(item['expireNum']>0) >
                             	 有<span class="red">${item['expireNum']!''}</span>个资质已过期 <a href="javascript:;" onclick="remind_natural('${item['id']!''}')">查看</a>
                                <#else>
	                                <#if item['approachExpireNum']??&&(item['approachExpireNum']>0) >
	                            	  有<span class="red">${item['approachExpireNum']!''}</span>个资质即将过期 <a href="javascript:;" onclick="remind_natural('${item['id']!''}')">查看</a>
	                            	 <#else>
	                            	  无
	                            	 </#if>
                                </#if>
                                
                                </td>
		                        <td>
		                        <span <#if item['contractRemainingDays']??&&item['contractRemainingDays']<=90> class="red"</#if>>
								<#if (((item.isNeedRenew)??) && (item.isNeedRenew=='1')) >
                				是
                				<#elseif (((item.isNeedRenew)??) && (item.isNeedRenew=='0'))> 
                				否
                				<#else>
								--
                				</#if>
								</span>
								</td>
		                        <td>
		                        <#if (((item.isNewContract)??) && (item.isNewContract=='1')) >
                				新合同
                				<#elseif (((item.isNewContract)??) && (item.isNewContract=='0'))>
                				续签合同
                				<#else>
                				--
                				</#if>
								</td>
		                        <td><#if item.updateDate??>${item['updateDate']?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
		                        <td>${item.updateUser?default("")}</td>
		                        <td>
		                      
		                            <#if isCanAssign??&&isCanAssign=="true"><!-- 权限管理列表的操作-->
		                            	<a href="#" onclick="assignAuthority('${item['id']!''}')">分配权限</a>
		                            	<a href="#" onclick="assignAuthoritys('${item['id']!''}','${(item.supplierCode)!''}')">分配权限组</a>
			                            <a href="#" onclick="updatePassword('${item['supplierCode']!''}')">修改密码</a>
		                            	<a href="#" onclick="toUpdateHistoryById('${item['supplierCode']!''}')">查看日志</a>
		                            <#else>
				                              <#if item['supplierType']??&&(item['supplierType']=='招商供应商')><!-- 招商供应商的操作-->
						                            <#if listKind??&&listKind=='B_APPROVAL'><!-- 业务审核商家列表的操作-->
						                            	
						                            	<#if item['isValid']??&&item['isValid']==3>
									                           	<a href="#" onclick="bApprovalMerchants('${item['id']!''}');">审核</a><!-- 业务审核-->
						                            	<#else>
								                              	<a href="#" onclick="reviewMerchants('${item['id']!''}');">查看</a>
						    		                    </#if>
						                            	
						                            <#elseif listKind??&&listKind=='F_APPROVAL'><!-- 财务审核商家列表的操作-->
						                            	
						                            	<#if item['isValid']??&&(item['isValid']==5||item['isValid']==7||item['isValid']==1||item['isValid']==-1)>
								                           	<a href="#" onclick="reviewMerchants('${item['id']!''}');">查看</a>
						    		                    </#if>
						                            	<#if item['isValid']??&&item['isValid']==4>
								                           	<a href="#" onclick="fApprovalMerchants('${item['id']!''}');">审核</a><!-- 财务审核-->
						    		                    </#if>
						    		                    <#if item['isValid']??&&(item['isValid']==-1||item['isValid']==5)>
								                           	<a href="#" onclick="validMerchants('${item['supplierCode']!''}');">启用</a>
						    		                    </#if>
						    		                    <#if item['isValid']??&&item['isValid']==1>
						    		                    	<a href="javascript:void(0);" onclick="to_updatemerchants('${item['supplierType']!''}' , '${item['id']!''}','${item['isNewContract']!''}','F_EDIT');">编辑</a>
								                           	<a href="#" onclick="envalidMerchants('${item['supplierCode']!''}');">停用</a>
						    		                    </#if>
						                            	
						                            <#else><!-- 商家列表的操作-->
						                            	<#if item['isValid']??&&(item['isValid']==1||item['isValid']==-1)>
															<#if item['renewContractStatus']??&&(item['renewContractStatus']=='5')>
															<span style="color:Grey");">已续签</span>
															<#else>
								                           	<a href="#" onclick="renew('${item['id']!''}');">续签</a>
															</#if>
						                         		</#if>
								                        <#if item['isValid']??&&(item['isValid']==-1||item['isValid']==2||item['isValid']==6||item['isValid']==7)>
								                           	<a href="#" onclick="to_updatemerchants('${item['supplierType']!''}' , '${item['id']!''}','${item['isNewContract']!''}');">编辑</a>
						                         		</#if>
						                         		<#if item['isValid']??&&(item['isValid']==3||item['isValid']==4)>
								                           	<a href="#" onclick="recallMerchants('${item['id']!''}');">撤消</a>
						    		                    </#if>
						                         		<#if item['isValid']??&&(item['isValid']==1||item['isValid']==3||item['isValid']==4||item['isValid']==5)>
								                           	<a href="#" onclick="reviewMerchants('${item['id']!''}');">查看</a>
						    		                    </#if>
						                         		<#if item['isValid']??&&item['isValid']==2>
						                           			<a href="#" onclick="deleteMerchants('${item['id']!''}','${item['supplierCode']!''}');">删除</a>
						                         		</#if>
						                         		
						    		                    <a href="#" onclick="update_natural_and_auth('${item['id']!''}','${item['isNewContract']!''}')">更新资质及品牌授权</a>
						    		                    <#-- a href="#" onclick="toCategoryAuth('${item['id']!''}')">品类授权</a-->
					    		                        <a href="#" onclick="toPunishRule('${item['supplierCode']!''}')">编辑处罚规则</a>  
						    		                  </#if>  
					    		             	      
				    		                  </#if>
				    		                    
		                           	</#if>
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
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
var queryUrl = "${BasePath}/yitiansystem/merchants/businessorder/to_merchantsList.sc",
    exportUrl = "${BasePath}/yitiansystem/merchants/businessorder/exportMerchantsList.sc";

/* 排序功能  */
$(function(){
	
	
	var orderBy = $("#orderBy").val();
	var sequence = $("#sequence").val();
	if(sequence==''){
		$("#sortByC").attr("nenabled","Nenabled");
		$("#sortByM").attr("nenabled","Nenabled");
	}
	// 初始化排序的箭头朝向(默认是朝下 降序)
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
	
	// 只查资质提醒的数据
	var flagForReminds = $('#flagForReminds').val();
	$('#btn_flag_for_reminds').click(function(){
		if(flagForReminds=='true' ){
			$('#flagForReminds').val('false');
			queryMerchants();
		}else{
		    $('#flagForReminds').val('true');
			queryMerchants();
		}
	});
	
	
});

function searchSort(orderBy,sequence){
	$('#sequence').val(sequence);
	$('#orderBy').val(orderBy);
	queryMerchants();
}

 //更新资质
var dialog_obj;
function update_natural_and_auth(id,isNewContract){
    if( !isNewContract || isNewContract=='2' ){
	    	ygdg.dialog.alert("该供应商还未创建合同，请先去合同管理录入合同。");
	    }else{
		    dialog_obj=$.dialog.open('${BasePath}/yitiansystem/merchants/manage/to_update_natural_and_auth.sc?id='+id,{
		        title:"更新资质及品类授权信息",
		        max:false,
		        min:false,
		        width: '1200px',
		        height: '800px',
		        lock:true,
		    });
    }
}

//资质提醒
function remind_natural(id){
    dialog_obj=$.dialog.open('${BasePath}/yitiansystem/merchants/manage/view_natural.sc?id='+id,{
        title:"查看商家资质提醒",
        max:false,
        min:false,
        width: '1200px',
        height: '800px',
        lock:true,
    });
}

//续签
function renew(id){
   if(id!=""){
  
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/manage/check_contract_for_supplier.sc?id=" + id, 
		success: function(id){
			if( "500"==id ){
				ygdg.dialog.alert("该供应商还未创建合同，不可以操作续签。");
			}else if("501"==id){
				ygdg.dialog.alert("该供应商的合同不需要续签。");
			}else if("502"==id){
				ygdg.dialog.alert("未找到该供应商，请确定供应商ID是否正确。");
			}else if("503"==id){
				ygdg.dialog.alert("该商家已续签并提交审核，请等待财务审核。");
			}else{
			 	dialog_obj=$.dialog.open('${BasePath}/yitiansystem/merchants/manage/to_renew_supplier_contract.sc?id='+id,{
			        title:"填写续签信息",
			        max:false,
			        min:false,
			        width: '1200px',
			        height: '800px',
			        lock:true,
			    });
			}
		} 
	});  
	
   }
}

//撤销
function recallMerchants(id){
   ygdg.dialog.confirm("确定撤消商家到新建吗?",function(){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/manage/recall_merchant.sc?id=" + id, 
		success: function(result){
			if( "success"==result ){
				ygdg.dialog.alert("撤消商家成功！",function(){
					refreshpage();
				});
			}else{
				ygdg.dialog.alert(result);
			}
		} 
	});  
   });
}
    
//跳转到更新历史记录页面
function toUpdateHistoryById(merchantCode){
   //openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_querySupplierLog.sc?flag=2&supplierId="+id,900,700,"更新历史");
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewMerchantOperationLog.sc?merchantCode=" + merchantCode, 900, 700, "查看日志");
}

//跳转到分配权限页面
function assignAuthority(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/assignAuthority.sc?id="+id,600,700,"分配商家权限");
}

//跳转到分配权限页面
function assignAuthoritys(id,merchantCode){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/distributeRole.sc?id="+id+"&merchantCode="+merchantCode,600,500,"分配商家权限组");
}

//修改密码
function updatePassword(supplierCode){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_updatePassword.sc?supplierCode="+supplierCode,500,300,"修改商家密码");
}

//跳转到查看商品信息页面
function select_merchants(supplierId){
      //openwindow("${BasePath}/yitiansystem/merchants/businessorder/select_merchants.sc?id="+supplierId,1200,700,"查看商家信息");
      openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改商家信息");
}

//跳转到查看商品信息页面
function reviewMerchants(supplierId){
     location.href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_merchant.sc?id="+supplierId;
}

//跳转到查看商品信息页面
function fApprovalMerchants(supplierId){
     location.href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_merchant.sc?listKind=F_APPROVAL&id="+supplierId;
}
//启用商家 --add by lsm 
function validMerchants(merchantCode){
   ygdg.dialog.confirm("确定启用?",function(){
	   if(merchantCode!=""){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/manage/to_startAccout.sc?merchantCode=" + merchantCode, 
			success: function(dt){
				if("success"==dt){
				    ygdg.dialog.alert("启用成功!");
				   refreshpage();
				}else{
				    ygdg.dialog.alert("启用失败!"+dt);
				   refreshpage();
				}
			} 
		});
	   }
  });
}

function envalidMerchants(merchantCode){
 ygdg.dialog.confirm("确定停用?",function(){
 if(merchantCode!=""){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/manage/to_stopAccout.sc?merchantCode=" + merchantCode, 
		success: function(dt){
			if("success"==dt){
			   ygdg.dialog.alert("停用成功!");
			   refreshpage();
			}else{
			    ygdg.dialog.alert("停用失败!"+dt);
			   refreshpage();
			}
		} 
	});
   }
   });
}
//跳转到查看商品信息页面
function bApprovalMerchants(supplierId){
     location.href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_merchant.sc?listKind=B_APPROVAL&id="+supplierId;
     
}

//跳转到处罚规则页面
function toPunishRule(merchantsCode){
	if(merchantsCode){
	     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_punishRule.sc?merchantsCode="+merchantsCode,1200,700,"编辑处罚规则");
	}
}

//跳转到添修改商品信息页面
function to_updatemerchants(supplierType,supplierId,isNewContract,editType){
	 var editTypeStr = "";
	 if('普通供应商'==supplierType){
	  openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改普通商家信息");
	 }else{ // 无合同则提示先录入合同
	    if( !isNewContract || isNewContract=='2' ){
	    	ygdg.dialog.alert("该供应商还未创建合同，请先去合同管理录入合同。");
	    }else{
		    if(editType){
		    	editTypeStr = "&editType="+editType;
			}
	   		location.href="${BasePath}/yitiansystem/merchants/manage/to_update_supplier_merchant.sc?id="+supplierId+editTypeStr;
	    }
	 }
    
}
//跳转到添修改商品信息页面(只能修改品牌和权限)
function to_update_merchantsBankAndCat(supplierId){
     //openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchantsBankAndCat.sc?id="+supplierId,1200,700,"修改商家信息");
     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改商家信息");
}
//跳转到添加商家信息页面
function addMerchants(){
    location.href="${BasePath}/yitiansystem/merchants/manage/to_add_supplier.sc?flag=2";
}
//删除商家信息
function deleteMerchants(id,supplierCode){
 if(id!=""&&supplierCode!=""){
   if(confirm("是否真的删除!")){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/delete_merchants.sc?id=" + id+"&supplierCode="+supplierCode, 
		success: function(dt){
			if("success"==dt){
			   ygdg.dialog.alert("删除成功!");
			   refreshpage();
			}else{
			   ygdg.dialog.alert("删除失败!");
			   refreshpage();
			}
		} 
	});
   }
 }
}
//分配角色
function allotUserRoles(id){
	openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addMerchants_Authority.sc?id="+id,520,650,"分配权限");
}
//根据条件查询招商信息
function queryMerchants(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

function queryCacheMerchants(){
	console.log("queryCacheMerchants~~");
	
}
//跳转到联系人页面
function toSupplierContactSp(id){
	if(id!=""){
	     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_linkmanList.sc?supplierSpId="+id,1200,700,"联系人列表");
	  }
}
//跳转到添加合同页面
function toSupplierContract(id){
     if(id!=""){
	     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_supplierContractList.sc?supplierSpId="+id,1200,700,"合同列表");
	  }
}

//品类授权
function toCategoryAuth(id) {
	if(id!=""){
		openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier_auth.sc?supplierId="+id,700,500,"品类授权");
	}
}

//拒绝原因
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

function doExport(){
	if(!confirm("确定导出！")){
		return;
	}
    document.queryForm.action = exportUrl;
    document.queryForm.submit();
    document.queryForm.action = queryUrl;
}

//跳转到添修改普通供应商的信息页面
function to_updatesupplier(supplierId){
     //openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?flag=2&id="+supplierId,1200,700,"修改商家信息");
     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改商家信息");
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>