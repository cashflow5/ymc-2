<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-在售商品</title>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/javascript">
var msg="用英文逗号分隔，可查询多个商品";
//提交表单查询
function mySubmit() {
	if($.trim($("#commodityNo_").val())!=msg){
		$("#commodityNo").val($.trim($("#commodityNo_").val()));
	}else{
		$("#commodityNo").val("");
	}
	if($.trim($("#supplierCode_").val())!=msg){
		$("#supplierCode").val($.trim($("#supplierCode_").val()));
	}else{
		$("#supplierCode").val("");
	}
	if($.trim($("#styleNo_").val())!=msg){
		$("#styleNo").val($.trim($("#styleNo_").val()));
	}else{
		$("#styleNo").val("");
	}
	if($.trim($("#thirdPartyCode_").val())!=msg){
		$("#thirdPartyCode").val($.trim($("#thirdPartyCode_").val()));
	}else{
		$("#thirdPartyCode").val("");
	}
 	//先验证
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/commodity/go4SaleCommodityRecycle.sc";
	queryForm.submit();
}
$(function(){
	$("#commodityNo_,#supplierCode_,#styleNo_,#thirdPartyCode_").focusin(function() {
  		if($.trim($(this).val())==msg){
			$(this).val("");
		}
	});
	$("#commodityNo_,#supplierCode_,#styleNo_,#thirdPartyCode_").focusout(function() {
		if($.trim($(this).val())==""){
			$(this).val(msg);
		}
	});
	
	if($.trim($("#commodityNo_").val())==""){
		$("#commodityNo_").val(msg);
	}
	if($.trim($("#supplierCode_").val())==""){
		$("#supplierCode_").val(msg);
	}
	if($.trim($("#styleNo_").val())==""){
		$("#styleNo_").val(msg);
	}
	if($.trim($("#thirdPartyCode_").val())==""){
		$("#thirdPartyCode_").val(msg);
	}
	
	$(".fl p.mb3 a.edit").click(function(){
		$(this).parent().find("a.commodity-title").hide();
		var textarea = $(this).parent().find("textarea");
		textarea.val($(this).parent().find("a.commodity-title").text());
		textarea.show();
		textarea.focus();
		$(this).hide();
		$(this).parent().find("a.save").show();
		$(this).parent().find("a.cancel").show();
	});
	
	$(".fl p.mb3 a.cancel").click(function(){
		$(this).parent().find("a.commodity-title").show();
		$(this).parent().find("textarea").hide();
		$(this).hide();
		$(this).parent().find("a.save").hide();
		$(this).parent().find("a.edit").show();
	});
	
	$(".fl p.mb3 a.save").click(function(){
		$(this).hide();
		var commodityName = $(this).parent().find("textarea").val();
		var commodityNo = $(this).parent().find("input.commodity-no").val();
		saveCommodityName($(this).parent(),commodityName,commodityNo);
	});
	
	/*
	$(".fl p.mb3 textarea").blur(function(){
		$(this).parent().find("a.save").hide();
		$(this).parent().find("a.cancel").hide();
		var commodityName = $(this).parent().find("textarea").val().trim();
		var commodityNo = $(this).parent().find("input.commodity-no").val().trim();
		saveCommodityName($(this).parent(),commodityName,commodityNo);
	});
	*/
	function saveCommodityName(curP,commodityName,commodityNo){
		curP.find("a.edit").hide();
		curP.find("a.save").show();
		curP.find("a.cancel").show();
		if(commodityName==""){
			ygdg.dialog.alert("商品名称不能为空");
			return;
		}
		if(commodityName.length>200){
			ygdg.dialog.alert("商品名称长度不能超过200");
			return;
		}
		if(commodityNo==""){
			ygdg.dialog.alert("商品编码不能为空");
			return;
		}
		curP.find("a.save").hide();
		curP.find("a.cancel").hide();
		curP.find("span").show();
		$.ajax({
    		async : true,
    		cache : false,
    		type : 'POST',
    		dataType : "json",
    		data:{
    			commodityName:commodityName,
    			commodityNo:commodityNo
    		},
    		url : "${BasePath}/commodity/updateCommodityName.sc",
    		success : function(data) {
    			curP.find("span").hide();
    			if(data.resultCode=="200"){
    				curP.find("a.save").hide();
    				curP.find("a.cancel").hide();
    				curP.find("a.edit").show();
    				curP.find("textarea").val(commodityName).hide();
    				curP.find("a.commodity-title").text(commodityName).show();
    			}else{
    				ygdg.dialog.alert(data.msg);
    				curP.find("a.save").show();
    				curP.find("a.cancel").show();
    			}
    		}
		});
	}
});
</script>

<style>
	.fl p.mb3 textarea{display:none;float:left;width:200px;height:auto;border:1px solid #C9C9C9;resize:none;padding:2px;}
	.fl p.mb3 a.commodity-title{display:inline-block;width:220px;float:left;}
	.fl p.mb3 a.edit{margin-left:5px;display:inline-block;width:16px;height:16px;background:url(${BasePath}/yougou/images/icon_edit.png);float:left;}
	.fl p.mb3 a.save{margin-left:5px;display:none;width:16px;height:16px;background:url(${BasePath}/yougou/images/icon_save.png);float:left;}
	.fl p.mb3 a.cancel{margin-left:5px;display:none;margin-top:4px;width:9px;height:9px;background:url(${BasePath}/yougou/images/del-class.gif) no-repeat;float:left;}
	.fl p.mb3 span{margin-left:5px;float:left;display:none}
</style>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 商品 &gt; 商品回收站</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>商品回收站</span></li>
				</ul>
				<div class="tab_content">
				
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" method="post">
							<p>
								<span><label style="width: 100px;">商品名称：</label><input type="text" class="inputtxt" id="commodityName" name="commodityName" value="${commodityQueryVo.commodityName!''}"/></span>
								<span>
									<label style="width: 100px;">商品编码：</label>
									<input type="text" class="inputtxt" id="commodityNo_" name="commodityNo_" value="${commodityQueryVo.commodityNo!''}"/>
									<input type="hidden" class="inputtxt" id="commodityNo" name="commodityNo" value="${commodityQueryVo.commodityNo!''}"/>
								</span>
								<span>
									<label style="width: 100px;">商品款号：</label>
									<input type="text" class="inputtxt" id="styleNo_" name="styleNo_" value="${commodityQueryVo.styleNo!''}" />
									<input type="hidden" class="inputtxt" id="styleNo" name="styleNo" value="${commodityQueryVo.styleNo!''}"/>
								</span>
								
								<span>
									<label style="width: 100px;">货品条码：</label>
									<input type="text" class="inputtxt" id="thirdPartyCode_" name="thirdPartyCode_" value="${commodityQueryVo.thirdPartyCode!''}" />
									<input type="hidden" class="inputtxt" id="thirdPartyCode" name="thirdPartyCode"  value="${commodityQueryVo.thirdPartyCode!''}" />
								</span>
								
							</p>
							<p>
								<span>
									<label style="width:100px;">删除时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="minUpdateDate" name="minUpdateDate" value="${commodityQueryVo.minUpdateDate!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="maxUpdateDate" name="maxUpdateDate" value="${commodityQueryVo.maxUpdateDate!''}"/>
								</span>
								<span style="padding-left:118px;"><a id="mySubmit" class="button" onclick="mySubmit()"><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
				
					<!--列表start-->
					<table class="common_lsttbl mt10">
					      <colgroup>
					      		<col>
						        <col width="310">
	                            <col width="100">
	                            <col>
	                            <col>
	                            <col>
	                            <col>
	                            <col width="110">
                            </colgroup>
                            <thead>
                                <tr>
                                	<th></th>
                                    <th>商品</th>
                                    <th>商品编码</th>
                                    <th>商品款号</th>
                                    <th>价格</th>
                                    <th>可售库存</th>
                                    <th>删除时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="common_lsttbl_cz">
                            	<tr><td height="3" colspan="8"></td></tr>
                            	<tr class="cz_box">
                                	<td colspan="8">
                                    	<div class="cz_bd fl">
                                        	<input type="checkbox" class="checkall ml8" id="checkAll"><label class="ml8" for="checkAll">本页全选</label>
                                            <a href="javascript:;" onclick="javascript:qc.opt.recovery();return false;">恢复到待售</a>
                                            <a href="javascript:;" onclick="javascript:qc.opt.del();return false;">彻底删除</a>
                                        </div>
                                        <div class="cz_page fr">

                                        </div>
                                    </td>
                                </tr>
                            </tbody>
						<tbody class="common_proitm" id="common_proitm">
						<#if pageFinder??&&(pageFinder.data)??>
							<#list pageFinder.data as item>
                            	<tr class="line_gap" id="${item.commodityNo!""}-1">
                                	<td colspan="8" style="border-bottom:1px solid #D4E7FF"></td>
                                </tr>
                                <tr class="proitm_bd" id="${item.commodityNo!""}-3">
                                	<td style="vertical-align:middle;padding-left:5px;" class="td_brdrlf"><input type="checkbox" name="recycel" value="${item.commodityNo!""}"></td>
                                	<td>
                                    	<div class="pro_pic fl">
                                        	<img width="40" height="40" alt="" src="${item.picSmall!""}">
                                        </div>
                                        <div class="txt_inf fl">
                                        	<p class="mb3">
                                        		<input type="hidden" class="commodity-no" value="${item.commodityNo!""}"/>
                                        		${item.commodityName!""}
                                        		<a style="clear:both;display:block"></a>
                                        	</p>
                                        </div>
                                    </td>
                                    <td>${item.commodityNo!""}</td>
                                    <td>${item.styleNo!""}</td>
                                    <td>
                                        <p>优购价：<em class="c3">${item.salePrice!""}</em></p>
                                        <p>市场价：${item.publicPrice!""}</p>
                                    </td>
                                    <td>${item.onSaleQuantity!""}</td>
                                    <td><#if item.updateDate??>${item.updateDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></td>
                                    <td class="td_brdrrt">
                                    	<p><a href="javascript:qc.opt.recovery('${(item.commodityNo)!''}')" >恢复到待售</a></p>
                                    	<p><a href="javascript:qc.opt.del('${(item.commodityNo)!''}')" >彻底删除</a></p>
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
					<!--列表end-->
				
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
				<div class="page_box">
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
<script>
	var basePath = "${BasePath}";
	var qc = {};
	
	/**url相关*/
	/**操作相关*/
	qc.opt = {};
	
	//全选
	$("#checkAll").click(function() {;
		$("#common_proitm").find("input[name='recycel']").attr("checked", this.checked);
	});
		
	/**
	 * 恢复到待售
	 * @param {String} commodityNo 商品编号
	 */
	qc.opt.recovery = function(commodityNo) {
		var ids = [];
		if(commodityNo==null){
			ids = $('input[name="recycel"]').filter(function(){
				return this.checked;
			}).map(function(){
				return this.value;
			}).get();
		}else{
			ids.push(commodityNo);
		}
		if(ids.length==0){
			ygdg.dialog.alert('请先选择需要恢复的商品!');
			return false;
		}
		ygdg.dialog.confirm("确定要恢复商品吗? <br /><br /> <p><span style='color:#AAAAAA'>注：执行该操作商品会恢复到待售商品。</span></p>", function() {
			$.ajax({
				cache : false,
				type: 'post',
				url: '/commodity/recoveryCommodity.sc',
				dataType: 'json',
				data:{
					ids:ids.join(",")
				},
				success: function(data) {
					if(data.resultCode=="200"){
						location.href = '${BasePath}/commodity/go4SaleCommodityRecycle.sc';
					}else{
						ygdg.dialog.alert(data.msg);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('恢复商品失败');
				}
			});
		});
	}
	/**
	 * 删除
	 * @param {String} commodityNo 商品编号
	 */
	qc.opt.del = function(commodityNo) {
		var ids = [];
		if(commodityNo==null){
			ids = $('input[name="recycel"]').filter(function(){
				return this.checked;
			}).map(function(){
				return this.value;
			}).get();
		}else{
			ids.push(commodityNo);
		}
		if(ids.length==0){
			ygdg.dialog.alert('请先选择需要彻底删除的商品!');
			return false;
		}
		ygdg.dialog.confirm("确定要彻底删除商品吗? <br /><br /> <p><span style='color:#AAAAAA'>注：彻底删除的商品不能再找回。</span></p>", function() {
			$.ajax({
				cache : false,
				type: 'post',
				url: '/commodity/delForever.sc',
				dataType: 'json',
				data: {
					ids:ids.join(",")
				},
				success: function(data, textStatus, jqXHR) {
					if(data.resultCode=="200"){
						ygdg.dialog.alert(data.msg,callback);
					}else{
						ygdg.dialog.alert(data.msg);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('删除商品失败');
				}
			});
		});
	}
	$("#minUpdateDate").calendar({maxDate:'#maxUpdateDate'});
	$("#maxUpdateDate").calendar({minDate:'#minUpdateDate'});

    function callback(){
    	location.href = '${BasePath}/commodity/go4SaleCommodityRecycle.sc';
    }
</script>
</html>