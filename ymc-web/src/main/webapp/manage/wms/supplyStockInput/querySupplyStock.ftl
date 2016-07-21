<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-库存查询</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/javascript">
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/wms/supplyStockInput/querySupplyGenStock.sc";
	queryForm.submit();
}
//正品库存查询
function supplyGenStock(){
   location.href="${BasePath}/wms/supplyStockInput/querySupplyGenStock.sc?flat=1" ;
}
</script>
<style>
a.icon_cancel{margin-left:5px;}
.icon_cancel{width:9px;height:9px;display:inline-block;background:url(${BasePath}/yougou/images/del-class.gif) no-repeat;margin-top:4px;}

</style>
</head>
<body>
	<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 库存 &gt; 库存查询</p>
					<div class="tab_panel">
					<ul class="tab">
				    	<li onclick="supplyGenStock();" class="curr"><span>库存查询</span></li>
				    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	<span class="lazyLoad cred hide"></span>
				    	<#if queryVO.warehouseCode??&&!safeStockQuantity??>
				    	<li class="tab_fr">
				    	     <input type="button" id="setSafeStock" onclick="setSafeStockQuantity();" class="btn btn_gary6" value="设置安全库存" />
				    	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	</li>
				    	</#if>
						<li class="tab_fr">
							<input type="button" onclick="doExport();" class="btn btn_gary1" value="导出库存" />
						</li>
				    </ul>
				<div class="tab_content">
				<!--搜索start-->
				<div class="search_box">
				    <form name="queryForm" id="queryForm" method="post">
				    <input type="hidden" id="productList" name="productList" value="<#if queryVO??>${queryVO.productList?if_exists}</#if>" />
					<p>
					<span><label>商品名称：</label><input type="text" class="inputtxt"  name="goodsName" id="goodsName" value="<#if queryVO??>${queryVO.goodsName?if_exists}</#if>" /></span>
					<span><label>商品编码：</label><input type="text"  class="inputtxt"  name="commodityCode" id="commodityCode" value="<#if queryVO??>${queryVO.commodityCode?if_exists}</#if>"/></span>
					<span><label>货品编码：</label><input type="text"  class="inputtxt"  name="productNo" id="productNo" value="<#if queryVO??>${queryVO.productNo?if_exists}</#if>"/></span>
					<span><label style="width:110px;">商家款色编码：</label><input type="text" class="inputtxt"  name="supplierCode" id="supplierCode" value="<#if queryVO??>${queryVO.supplierCode?if_exists}</#if>" /></span>
					</p>
					<p>
					<span><label>商品款号：</label><input type="text" class="inputtxt"  name="styleNo" id="styleNo" value="<#if queryVO??>${queryVO.styleNo?if_exists}</#if>" /></span>
					<span><label>货品条码：</label><input type="text" class="inputtxt"  name="insideCode" id="insideCode" value="<#if queryVO??>${queryVO.insideCode?if_exists}</#if>" /></span>
					<span><label>商品年份：</label>
					<select id="year" name="year" style="width:130px;">
					 <option value="">--全部年份--</option>
						<#if years??> <#list years as item> <#if queryVO??> <#if item.key == queryVO.year?if_exists>
							<option value="${item.key?if_exists}" selected> <#else>
							<option value="${item.key?if_exists}"> </#if>
							${item.value?if_exists} </option>
							<#else>
							<option value="${item.key?if_exists}">${item.value?if_exists}
							</#if>
							</#list>
						</#if>
					</select>
					</span>
					<span><label style="width:105px;">销售状态：</label>
					<select id="commodityStatus" name="commodityStatus" style="width:128px;">
						<option value="">请选择</option>
						<option <#if queryVO.commodityStatus??><#if queryVO.commodityStatus=="2">selected</#if> </#if>value="2">在售商品</option>
						<option <#if queryVO.commodityStatus??><#if queryVO.commodityStatus=="1">selected</#if> </#if>value="1">不可售商品</option>
					</select>
					</span>
					</p>
					<p>
					<span><label>商品品牌：</label>
					<select id="brandNo" name="brandNo" style="width:126px;">
							<option value="">--全部品牌--</option>
							<#if lstBrand??> <#list lstBrand as item> <#if queryVO??> <#if item.brandNo == queryVO.brandNo?if_exists>
							<option value="${item.brandNo?if_exists}" selected> <#else>
							<option value="${item.brandNo?if_exists}"> </#if>
							${item.brandName?if_exists} </option>
							<#else>
							<option value="${item.brandNo?if_exists}">${item.brandName?if_exists}
							</#if>
							</#list>
							</#if>
					</select>
					</span>
					<span>
						<label>库存类型：</label>
						<select id="selectType" name="selectType" style="width:126px;">
							<option <#if queryVO.selectType??><#if queryVO.selectType==1>selected</#if> </#if>value=1>正品</option>o
							<option <#if queryVO.selectType??><#if queryVO.selectType==2>selected</#if> </#if>value=2>残品</option>
						</select>
					</span>
					<span><label>商品分类：</label>
						<input type="hidden" id="memoryRootCat" value="${queryVO.category1No!''}" />
						<input type="hidden" id="memorySecondCat" value="${queryVO.category2No!''}" />
						<input type="hidden" id="memoryThreeCat" value="${queryVO.category3No!''}" />
					    <select id="rootCattegory"  name="category1No">
					    	<option value="" selected="selected">一级分类</option>
					    	<#list lstCat as item>
					    	<option value="${(item.structName)!""}">${(item.catName)!""}</option>
					    	</#list>
					    </select>
	                    <select name="category2No" class="fl-lf" id="secondCategory">
	                    	<option value="" selected="selected">二级分类</option>
	                    </select>
	                    <select name="category3No" class="fl-lf" id="threeCategory">
	                    	<option value="" selected="selected">三级分类</option>
	                    </select>
					</span>
					</p>
					<p>
					<span><label  style="width: 105px;">库存大于等于：</label><input type="text" style="width: 35px;" class="inputtxt" id="stock" onkeyup="value=value.replace(/[^0-9]/g,'')"  name="stock" value="${queryVO.stock!''}" /></span>
					<span style="padding-left:695px;"><a class="button" id="mySubmit" onclick="doQuery()" ><span>搜索</span></a></span> 
					</p>
				</form>
				</div>
				<!--搜索end-->
						
				<!--列表start-->
				<#if queryVO.warehouseCode??&&safeStockQuantity??><p><span class="fl icon_info"></span>
				<span class="fl ml5" style="color:#999;">您已设置安全库存为${safeStockQuantity?default("0")}，则可售库存会相应减少${safeStockQuantity?default("0")}个&nbsp;&nbsp;
				<a href="javascript:setSafeStockQuantity();" id="setSafeStockHref" style="TEXT-DECORATION:underline;">查看安全库存设置</a></span></p>&nbsp;&nbsp;&nbsp;
				<#if isYougouAdmin?? && isYougouAdmin==1><a href="${BasePath}/wms/supplyStockInput/getSafeStock.sc" target="_blank">查看商品实际安全库存</a></#if><br/></#if>
				<table cellpadding="0" cellspacing="0" class="list_table">
					<thead>
						<tr>
						<th style="width:10px;"></th>
						<th style="width:300px;">商品名称</th>
						<th>货品编码</th>
						<th style="text-align:left;padding-left:10px;">商家货品条码</th>
						<th>规格</th>
						<th style="line-height: 30px;"><span class="fl">总库存数 </span>
						<span id="tips" class="fl ml3 mt5 icon_tips"></span></th>
						<th style="line-height: 30px;"><span class="fl">预占库存 </span>
						<span id="preTips" class="fl ml3 mt5 icon_tips"></span></th>
						<th style="line-height: 30px;"><span class="fl">可售库存 </span>
						<span id="saleTips" class="fl ml3 mt5 icon_tips"></span></th>
						<th>成本价</th>
					    </tr>
				    </thead>
				    <tbody>
				       <#if pageFinder?? && (pageFinder.data)?? > 
				       <#list pageFinder.data as item >
				      <tr>
				      <td style="padding:2px">
      		                <#if item.picSmall??&&item.picSmall!=''>
                    	        <img src="${item.picSmall!""}" alt="" width="35" height="35" />
                    	    <#else>
                    	        <img width="35" height="35" alt="" src="${BasePath}/yougou/images/nopic.jpg"/>
                    	    </#if>
				      </td>
					  <td style="text-align:left;">
					  	<div style="width:300px;float:left;word-break:break-all;">
							<a href="${item.prodUrl!''}" target="_blank">${item.goodsName!""}</a>
						</div>
					  </td>
					  <td>${item.productNo?default("")}</td>
					  <td style="text-align:left;padding-left:10px;">${item.thirdPartyCode?default("")}</td>
					  <td>${item.specification?default("")}</td>
					  <td id="${item.productNo?default("")}">
					  ${item.quantity?default(0)}
					  <#if queryVO.warehouseCode??>
					  	<a href="javascript:editsSupplyStock('${item.productNo?default('')}','${item.quantity?default(0)}');" class="icon_edit fr" title="点击图标可修改商品库存"></a>
					  	<!--<a href="javascript:editsSupplyStock('${item.productNo?default('')}','${item.quantity?default(0)}');" class="icon_cancel fr" title="点击图标可取消书写"></a>-->
					  </#if>
					  </td>
					  <td id="${item.productNo?default("")}_preQuantity">${item.preQuantity?default(0)}</td>
					  <td id="${item.productNo?default("")}_saleQuantity">${item.saleQuantity?default(0)}</td>
					  <td id="${item.productNo?default("")}_costPrice">${item.costPrice?default(0)}</td>
				      </tr>
				</#list> 
				<#else>
				<tr>
					<td class="td-no" colspan="11">没有相关记录！</td>
				</tr>
				</#if>
				</tbody>
				
			</table>
			<!--列表end-->
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
					<div class="page_box">
						<div class="dobox">
						</div>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryForm"/>
					</div>
			</#if>		
			<!--分页end-->
			</div>
		</div>
	</div>
	 </div>
</body>
</html>
<script type="text/javascript">
var setSafeStockResult = "${setSafeStockResult!''}";
var isSetSafeStock = "${isSetSafeStock!''}";
var setId = "";
$(function(){
	if(isSetSafeStock=="loading"){
		 $(".lazyLoad").html('<img src="${BasePath}/yougou/js/ygdialog/skins/icons/loading.gif"/>批量更新库存中,请稍候...&nbsp;&nbsp;&nbsp;');
         $(".lazyLoad").removeClass("hide");
         //按钮、链接变色
         $("#setSafeStock").attr("onclick","javascript:void(0);");
         $("#setSafeStock").addClass("cgary0");
         $("#setSafeStockHref").attr("href","#");
         $("#setSafeStockHref").removeAttr("style");
         setId = setInterval(function() {
	      	getSetSafeStockResult();
	      }, 5000);
	}else if(isSetSafeStock=="loaded"){
		ygdg.dialog({
			id:'setSafeStock',
			content:setSafeStockResult=="true"?'库存更新成功！':'设置安全库存成功，但是批量更新库存失败，请联系优购支持人员！',
			title:'设置安全库存',
			icon:setSafeStockResult=="true"?'face-smile':'face-sad',
			time:2
		});
		$("#setSafeStock").attr("onclick","setSafeStockQuantity();");
		$("#setSafeStock").removeClass("cgary0");
        $("#setSafeStockHref").attr("href","javascript:setSafeStockQuantity();");
        $("#setSafeStockHref").css("TEXT-DECORATION","underline");
	}
});

var doExportUrl="${BasePath}/wms/supplyStockInput/doGenStockExport.sc";
var doQueryUrl="${BasePath}/wms/supplyStockInput/querySupplyGenStock.sc";
function doExport() {
	if (!confirm("确定导出！")) {
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportUrl;
	queryForm.submit();
	// -_-!!
	queryForm.action = doQueryUrl;
}

var basePath = "${BasePath}";

//注册分类下拉事件
setGoodsCattegory();

var dg;
$('#tips').live('mouseover',function(){
	var _html='<div style="line-height:20px;width:200px;">供应商在优购平台销售的实时商品总库存数量，包含下单预占的库存。</div>';
	var _this=$(this);
	var _top=_this.offset().top- $(document).scrollTop()+30;
	var _left=_this.offset().left-100;
	dg=ygdgDialog({
		id:'tipsDiglog',
		padding : 0,
		title:'总库存数',
		top:_top,
		left:_left,
		closable:false,
		content:_html
	});
}).live('mouseout',function(){
 	dg.close();
});
$('#preTips').live('mouseover',function(){
	var _html='<div style="line-height:20px;width:200px;">顾客下单后预占相应的库存数量，发货后预占库存释放，总库存也会相应减少。</div>';
	var _this=$(this);
	var _top=_this.offset().top- $(document).scrollTop()+30;
	var _left=_this.offset().left-100;
	dg=ygdgDialog({
		id:'tipsDiglog',
		padding : 0,
		title:'预占库存',
		top:_top,
		left:_left,
		closable:false,
		content:_html
	});
}).live('mouseout',function(){
 	dg.close();
});
$('#saleTips').live('mouseover',function(){
	var _html='<div style="line-height:20px;width:200px;">可售库存为顾客在网站前台实际可购买的库存总数，库存数量-预占库存=可售库存。</div>';
	var _this=$(this);
	var _top=_this.offset().top- $(document).scrollTop()+30;
	var _left=_this.offset().left-100;
	dg=ygdgDialog({
		id:'tipsDiglog',
		padding : 0,
		title:'可售库存',
		top:_top,
		left:_left,
		closable:false,
		content:_html
	});
}).live('mouseout',function(){
 	dg.close();
});

//记忆分类
var memoryRootCat = $('#memoryRootCat').val();
var memorySecondCat = $('#memorySecondCat').val().split(';');
var memoryThreeCat = $('#memoryThreeCat').val().split(';');
if (memoryRootCat != '0' && memoryRootCat != '') {
	$("select:[id='rootCattegory'] option").each(function() {
		var val = $(this).val();
		if (memoryRootCat == val) {
			$(this).attr('selected', 'selected');
			getGoodsCattegory(val, "secondCategory");
		}
	});
}
if (memorySecondCat.length > 1) {
	setSecondCategory(memorySecondCat[0]);
}
if (memoryThreeCat.length > 1) {
	setThreeCategory(memoryThreeCat[0]);
}

function setSecondCategory(cat2) {
	/**二级分类的条件*/
	function cat2Condition() {
		return $("#secondCategory>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat2TargetFun() {
		setCatSelectValue("secondCategory", cat2);
		$("#secondCategory").reJqSelect();
		$("#secondCategory").change();
	}
	createDetector("secondcat2Detector", cat2Condition, cat2TargetFun, 200);
};

function setThreeCategory(cat3) {
	/**二级分类的条件*/
	function cat3Condition() {
		return $("#threeCategory>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat3TargetFun() {
		setCatSelectValue("threeCategory", cat3);
		$("#threeCategory").reJqSelect();
		$("#threeCategory").change();
	}
	createDetector("threecat3Detector", cat3Condition, cat3TargetFun, 200);
};

/**
 * 设置分类下拉框的值
 * @param {String} id 下拉框id
 * @param {String} curStructName 要选中的分类结构名称
 */
setCatSelectValue = function(id , curStructName) {
	var $options = $("#" + id + ">option");
	var option = null;
	for (var i = 0, len = $options.length; i < len; i++) {
		option = $options[i];
		var catInfo = option.value.split(";");
		if(catInfo != null && catInfo.length >= 1) {
			var structName = catInfo[0];
			if(structName == (curStructName)) {
				document.getElementById(id).selectedIndex = i;
			}
		}
	}
};

/**
 * 修改库存
 * @param {String} productNo 货品编码
 * @param {String} thirdPartyCode 商家货品条码
 * @param {String} quantity 商家货品库存数量
 */
function editsSupplyStock(productNo, quantity) {
var _input="<input type='text' style='width:30px;' class='ginput' id='"+productNo+"_input' name='"+productNo+"_input' onkeyup=\"value=value.replace(/[^0-9]/g,\'\')\" value='"+quantity+"'/>"
           +"&nbsp;&nbsp;<a href=\"javascript:cancelSupplyStock('"+productNo+"','"+quantity+"');\" class='icon_cancel fr' title='点击图标可取消书写'></a>"
           +"<a href=\"javascript:saveSupplyStock('"+productNo+"','"+quantity+"');\" class='icon_save fr' title='点击图标可保存商品库存'></a>";
$("#"+productNo).html(_input);
$("#"+productNo+"_input").focus();
};

function cancelSupplyStock(productNo,quantity_Old){
	ygdg.dialog.confirm("是否确认取消刚才输入的库存量？",function(){
		var _show=quantity_Old+" <a href=\"javascript:editsSupplyStock('"+productNo+"','"+quantity_Old+"');\" class='icon_edit fr' title='点击图标可修改商品库存'></a>";
		$("#"+productNo).html(_show);
	},function(){return;});
}
/**
 * 修改后保存库存
 * @param {String} productNo 货品编码
 * @param {String} thirdPartyCode 商家货品条码
 * @param {String} quantity 商家货品库存数量
 */
function saveSupplyStock(productNo,quantity_Old) {
var quantity=$('#'+productNo+'_input').val();
var saleQuantity = "${safeStockQuantity!'0'}";
if(quantity==quantity_Old){
   ygdg.dialog.alert("库存值没有改变！");
}else if(quantity==''){
   ygdg.dialog.alert("库存值不能为空！");
   return;
}else if(parseInt(quantity)<parseInt($('#'+productNo+'_preQuantity').text())+parseInt(saleQuantity)){
	ygdg.dialog.alert("库存值不能小于（预占库存+安全库存）！");
	return;
}else{
    var safeStockQuantity = 0;
	$.ajax({
		url: '${BasePath}/wms/supplyStockInput/exactUpdateInventory.sc',
		type: 'post',
		dataType: 'json',
		async: false, 
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data: 'commodityCode=' + productNo + '&quantity=' + quantity + '&rand=' + Math.random(),
		success: function(data, textStatus) {
			if (data && data.result == true) {
			    safeStockQuantity=data.safeStockQuantity;
				ygdg.dialog.alert("更新库存成功！");
			} else {
				ygdg.dialog.alert(data);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert("更新库存失败！");
		}
	});
	$("#"+productNo+"_saleQuantity").html(quantity-$('#'+productNo+'_preQuantity').text()-safeStockQuantity);
	$("#"+productNo+"_qtyCost").html(Math.round(quantity*$('#'+productNo+'_costPrice').text()*100)/100);
}
var _show=quantity+" <a href=\"javascript:editsSupplyStock('"+productNo+"','"+quantity+"');\" class='icon_edit fr' title='点击图标可修改商品库存'></a>";
$("#"+productNo).html(_show);
};


/**
 * 设置安全库存
 */
function setSafeStockQuantity(){
		      var dialog=ygdg.dialog({
	          title: '设置安全库存',
	          width: 460,
              height: 160,
	          content: '<span><label>当商品库存小于等于</label><input type="text" style="width:30px;height:14px;text-align:right;"  id="safeStockQuantity" name="safeStockQuantity" onkeyup="value=value.replace(/[^0-9]/g,\'\')" value="${safeStockQuantity?default("0")}" /> 时，自动将该货品库存清0下架。</span><br/><br/><span><label style="display:inline-block;color:#C7C7C7;">库存不足被下架的商品将邮件通知商家，如未收到邮件请联系优购货品人员。</label></span>',
	           button: [
	           {
            		name: '保存',
            		callback: function () {
            		var safeStockQuantity=$("#safeStockQuantity").val();
                    if(verifyQuantity(safeStockQuantity)){
                      saveQuantity(safeStockQuantity);
                    }else{
                       this.shake && this.shake();
                       $("#safeStockQuantity").select();
                       $("#safeStockQuantity").focus();
                       return false;
                    }	
            	    },
            	    focus: true
        		},
        		{
            		name: '关闭'
        		}
    		   ]
		    });
}
function verifyQuantity(quantity){
	var flag = quantity>=0?true:false;
	return flag;
}
function saveQuantity(quantity){
	$.ajax({
		  type: 'post',
		  url: '${BasePath}/wms/supplyStockInput/updateSafeStockQuantity.sc?param='+new Date().getTime(),
		  data: 'quantity='+quantity,
		  success: function(data, textStatus) {
			  if(data=="success"){
				  $(".lazyLoad").html('<img src="${BasePath}/yougou/js/ygdialog/skins/icons/loading.gif"/>批量更新库存中,请稍候...&nbsp;&nbsp;&nbsp;');
		          $(".lazyLoad").removeClass("hide");
			  }else if(data=="loading"){
				  ygdg.dialog.alert("上一次设置安全库存正在执行，请稍后刷新再试！");
			  }
			  $("#setSafeStock").attr("onclick","javascript:void(0);");
			  $("#setSafeStock").addClass("cgary0");
	          $("#setSafeStockHref").attr("href","#");
	          $("#setSafeStockHref").removeAttr("style");
	          setId = setInterval(function() {
		      	getSetSafeStockResult();
		      }, 5000);
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
		    ygdg.dialog.alert(XMLHttpRequest.responseText);
		  }
	});
}

var getSetSafeStockResult = function(){
	$.get(basePath+"/wms/supplyStockInput/getSetSafeStockResult.sc",function(text){
		if(text=="true"){
			window.clearInterval(setId);
			doQuery();
		}
	});
};
</script>
