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
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">权限分配列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchantsList.sc" name="queryForm" id="queryForm" method="post">
     			<input type="hidden" name="isCanAssign" value="${isCanAssign!''}"/> 
  			  	<div class="wms-top">
                     <label>商家名称：</label>
                     <input type="text" name="supplier" id="supplier" value="<#if merchantsVo??&&merchantsVo.supplier??>${merchantsVo.supplier!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <label>商品品牌：</label>
                     <input type="text" name="brandName" id="brandName" value="<#if merchantsVo??&&merchantsVo.brandName??>${merchantsVo.brandName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <label>商家编号：</label>
                     <input type="text" name="supplierCode" id="supplierCode" value="<#if merchantsVo??&&merchantsVo.supplierCode??>${merchantsVo.supplierCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <label>状态：</label>
                     <select name="isValid" id="isValid">
                        <option value="0">请选择状态</option>
                        <option value="-1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==-1>selected</#if>>停用</option>
                        <option value="1" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==1>selected</#if>>启用</option>
                        <option value="2" <#if merchantsVo??&&merchantsVo.isValid??&&merchantsVo.isValid==2>selected</#if>>新建</option>
                     </select>
                </div>
  			  	<div class="wms-top">
                     <label>合作模式：</label>
                     <select name="isInputYougouWarehouse" id="isInputYougouWarehouse">
                        <option value="">请选择状态</option>
                       	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                       	<option value="${item.ordinal()}" <#if merchantsVo.isInputYougouWarehouse?default(-1) == item.ordinal()>selected="selected"</#if>>${item.description}</option>
                       	</#list>
                     </select>
                     <input type="button" value="搜索" onclick="queryMerchants();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                     &nbsp;&nbsp;&nbsp;&nbsp;
                     <label>根据商家编码刷新商家权限缓存：</label>
                     <input type="text" name="supplierCodeCache" placeholder="商家编码" id="supplierCodeCache" value="<#if merchantsVo??&&merchantsVo.supplierCode??>${merchantsVo.supplierCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <input type="button" value="刷新缓存" onclick="refreshCache();" id="refreshCacheButton"/>&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th width="200px;">商家名称</th>
                        <th>商家编号</th>
                        <th>商家品牌</th>
                        <th>用户名</th>
                        <th width="40px;">状态</th>
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
		                        <td>${item['supplierCode']!""}</td>
		                        <td>
		                        	<#if item.brandVos??&&item.brandVos?size!=0>
		                        		<a href="javascript:;" data-attr="{title: '${(item.supplierCode)!''}', reason:'<#if item.brandVos??&&item.brandVos?size!=0><#list item.brandVos as brand><b>${brand.brandName!''}</b><br /></#list></#if>'}" class="f_blue brand_no">品牌</a>
		                        	</#if>
		                        </td>
		                        <td>${item['loginAccount']!""}</td>
		                        <td><#if item['isValid']??&&item['isValid']==1>启用
			                        <#elseif item['isValid']?? && item['isValid']==2>新建
			                        <#elseif item['isValid']?? && item['isValid']==-1>停用</#if></td>
		                        <td>${item['updateDate']?string("yyyy-MM-dd HH:mm:ss")}</td>
		                        <td>${item.updateUser?default("")}</td>
		                        <td>
	                            	<#if item['loginPassword']??&&item['loginPassword']=="1">
	                            		<a href="#" onclick="assignAuthority('${item['id']!''}')">分配权限</a>
	                            	</#if>
	                            	<a href="#" onclick="assignAuthoritys('${item['id']!''}','${(item.supplierCode)!''}')">分配权限组</a>
		                            <a href="#" onclick="updatePassword('${item['supplierCode']!''}')">修改密码</a>
	                            	<a href="#" onclick="toUpdateHistoryById('${item['supplierCode']!''}')">查看日志</a>
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
//跳转到更新历史记录页面
function toUpdateHistoryById(merchantCode){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewAuthorityOperationLog.sc?merchantCode=" + merchantCode, 900, 700, "查看日志");
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

//跳转到处罚规则页面
function toPunishRule(merchantsCode){
	if(merchantsCode){
	     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_punishRule.sc?merchantsCode="+merchantsCode,1200,700,"编辑处罚规则");
	}
}

//跳转到添修改商品信息页面
function to_updatemerchants(supplierId){
     //openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?flag=2&id="+supplierId,1200,700,"修改商家信息");
     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改商家信息");
}
//跳转到添修改商品信息页面(只能修改品牌和权限)
function to_update_merchantsBankAndCat(supplierId){
     //openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchantsBankAndCat.sc?id="+supplierId,1200,700,"修改商家信息");
     openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc?flag=1&id="+supplierId,800,700,"修改商家信息");
}
//跳转到添加商品信息页面
function addMerchants(){
    location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?flag=2";
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
			   alert("删除成功!");
			   refreshpage();
			}else{
			   alert("删除失败!");
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

function refreshCache(){
	var code = $.trim($('#supplierCodeCache').val());
	if(code!=""){
		$("#refreshCacheButton").attr("disabled",true);
		$("#refreshCacheButton").val("执行中...");
		$.post("${BasePath}/yitiansystem/merchants/businessorder/refreshCache.sc",
				{merchantCode:code},function(text){
			if(text=="1"){
				ygdg.dialog.alert("商家编码为"+$.trim($('#supplierCodeCache').val())+"的主账号刷新缓存成功！");
			}else if("-1"==text){
				ygdg.dialog.alert("商家编码为"+$.trim($('#supplierCodeCache').val())+"的主账号不存在！");
			}else{
				ygdg.dialog.alert("刷新缓存失败！");
			}
			$("#refreshCacheButton").val("刷新缓存");
			$("#refreshCacheButton").attr("disabled",false);
		});
	}else{
		ygdg.dialog.alert("商家编码不能为空！");
	}
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
