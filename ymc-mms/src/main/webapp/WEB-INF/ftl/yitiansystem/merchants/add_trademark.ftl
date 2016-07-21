<#macro trademark pageSrc="" >
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/cat_tree.js?lyx20151008"></script>

<script>
var basePath = '${BasePath}';  
var maxFileSize = '${maxFileSize}';
var pageSrc = "${pageSrc}";
var supplierContractId = "";
<#if supplierContract??>
supplierContractId = "${supplierContract.id!''}";
</#if>
var brandEditable = true;
<#if supplier?? && supplier.isValid?? && (supplier.isValid==5 || supplier.isValid==1 || supplier.isValid==-1)>
brandEditable = false;
</#if>
<#if renew_contract?? && renew_contract == '1'>
brandEditable = true;
supplierContractId =  "${contractIdForTrademark!''}";
</#if>

$(document).ready(function(){
	//加载品牌显示框
	var brandStrs = $('#bankNameHidden').val();
	var brandNos = [];
	if (brandStrs != null && brandStrs != '' && brandStrs.length > 0) {
		var str = brandStrs.split('_');
		for(var i = 0; i < str.length; i++) {
			var array = str[i].split(';');
			if (array[0] == '') continue;
			showBrandframe(array[0], array[1]);
			brandNos[brandNos.length] = array[0];
		}
	}
	$('#bankNoHistory').val(brandNos.join(';'));
	$('#bankNoHidden').val(brandNos.join(';'));
	
	var zNodes = [];
	
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			var dispaly = false;
			var isEnabled = '${item.isEnabled!''}';
			if (isEnabled == 1) {
				dispaly = true;
			}
			zNodes[zNodes.length] = {
				id: '${item.structName!""}', 
				pId: '${item.parentId!''}', 
				name: '${item.catName!''}', 
				lev: '${item.catLeave!''}',
				checked: dispaly
			};
		</#list>
	</#if>		
	
	$.fn.zTree.init($("#ztree"), setting, zNodes);
	if (zNodes.length > 0)
	$("#supplier_category_brand_tree_tr").show();
});
</script>

<h1 class="supplier-title">添加品牌品类</h1>
<div class="form-box">
    <div class="g-layout-column-left ml50 clearfix">
        <div class="g-layout-left tright">
        	<span class="Red">*</span>请选择品牌品类：
        </div>
        <div class="g-layout-main">
        	<div class="fl">
        		<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
            	<input type="hidden" name="bankNameHidden" id="bankNameHidden" value="<#if brandStrs??>${brandStrs!''}</#if>" />
            	<input type="hidden" name="bankNoHidden" id="bankNoHidden"  />
            	<input type="hidden" name="bankNoNameHidden" id="bankNoNameHidden"  />
            	<input type="hidden" name="catNameHidden" id="catNameHidden" />
                <span id="bank_span"></span>
                <img src="${BasePath}/images/finance/adduser.gif" onclick="addBrand();" style=" margin-left: 5px;"/>
                &nbsp;&nbsp;<span style="color:red;" id="bankNameError"></span>
        	</div>
        
        	<span class="Gray ml5" style="line-height:30px;padding-left:0">如果没有您需要的品牌品类，请先在大拇指商品系统创建新的品牌品类</span>
        </div>
        <div class="g-layout-main">
       	<#-- 分类树结构展示 -->
		<div class="content_wrap"><ul id="ztree" class="ztree"></ul></div>
		<input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" />
		</div>
    </div>
		<p class='error-message' id="brandNoError"/>
</div>
<h1 class="supplier-title">授权资质</h1>
<div class="form-box">
    <div class="supplier-query-wrap clearfix">
        <div class="supplier-query-left">
            <label for="" class="supplier-query-title"><span class="Red">*</span>授权书：</label>
            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker3">上传文件</a>
			<div id="loading3" class="loading"><img src="${BasePath}/images/loading.gif"></div>
			<div id="attachment_3" class="inline-block">
            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
	              <#list supplierContract.attachmentList as item >
	                <#if item['attachmentType']=='3'>
	                  <div class="attachment_item">
	                    <input name="contract_attachment" value="3;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
	                    <span class="supplier-query-cont Blue ml5">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
	                  </div>
	                </#if>
	              </#list>
	          </#if>
			</div>
			<p class='error-message' id="attachmentError_3"/>
        </div>
        <div class="supplier-query-right">
            <label for="" class="supplier-query-title"><span class="Red">*</span>商标注册证：</label>
            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker4">上传文件</a>
			<div id="loading4" class="loading"><img src="${BasePath}/images/loading.gif"></div>
			<div id="attachment_4" class="inline-block">
            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
              <#list supplierContract.attachmentList as item >
                <#if item['attachmentType']=='4'>
                  <div class="attachment_item">
                    <input name="contract_attachment" value="4;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
                  </div>
                </#if>
              </#list>
            </#if>
			</div>
			<p class='error-message' id="attachmentError_4"/>
        </div>
    </div>
    <div class="supplier-query-wrap clearfix">
        <div class="supplier-query-left">
            <label for="" class="supplier-query-title"><span class="Red">*</span>商标授权：</label>
            <a class="yg-btn-gray-2" onclick="addSubTable()">添加</a>
        </div>
        <div class="supplier-query-right">
        	<#if pageSrc == 'natural'>
        	<label for="" class="supplier-query-title">补充协议：</label>
            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker10">上传文件</a>
			<div id="loading10" class="loading"><img src="${BasePath}/images/loading.gif"></div>
			<div id="attachment_10" class="inline-block">
            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
              <#list supplierContract.attachmentList as item >
                <#if item['attachmentType']=='2'>
                  <div class="attachment_item">
                    <input name="contract_attachment" value="2;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
                  </div>
                </#if>
              </#list>
            </#if>
			</div>
			</#if>
        </div>
    </div>
</div>
<div id="content_list">
    <table class="list_table" cellpadding="0" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th><span class="Red">*</span>商标</th>
                <th><span class="Red">*</span>绑定品牌</th>
                <th><span class="Red">*</span>扣点</th>
                <th>商标专利授权人</th>
                <th>类别</th>
                <th>注册商标号</th>
                <th>注册开始日期</th>
                <th>注册截止日期</th>
                <th>授权级别</th>
                <th><span class="Red">*</span>被授权人</th>
                <th><span class="Red">*</span>授权期起</th>
                <th><span class="Red">*</span>授权期止</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="sub-tbody">
		</tbody>                        
    </table>  
    <p class='form-box error-message' id="subTable_error"/>                  
</div>               
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/add_trademark.js?v=20160106"></script>
</#macro>