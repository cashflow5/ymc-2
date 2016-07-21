<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-淘宝商品导入</title>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script>
	
		var msg="用英文逗号分隔，可查询多个商品";
		var curPage = 1; 
		function loadData(pageNo){
			curPage = pageNo;
			$("#content_list").html('<table class="list_table">'+
				'<tbody id="tbody"><tr><td colspan="10">正在载入......<td></tr></tbody>'+
				'<table>');
				
				
			if($.trim($("#yougouStyleNo_").val())!=msg){
				$("#yougouStyleNo").val($.trim($("#yougouStyleNo_").val()));
			}else{
				$("#yougouStyleNo").val("");
			}	
			if($.trim($("#yougouSupplierCode_").val())!=msg){
				$("#yougouSupplierCode").val($.trim($("#yougouSupplierCode_").val()));
			}else{
				$("#yougouSupplierCode").val("");
			}	
			if($.trim($("#yougouCommodityNo_").val())!=msg){
				$("#yougouCommodityNo").val($.trim($("#yougouCommodityNo_").val()));
			}else{
				$("#yougouCommodityNo").val("");
			}	
			if($.trim($("#yougouThirdPartyCode_").val())!=msg){
				$("#yougouThirdPartyCode").val($.trim($("#yougouThirdPartyCode_").val()));
			}else{
				$("#yougouThirdPartyCode").val("");
			}	
				
				
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "html",
				data:$("#queryVoform").serialize(),
				url : "${BasePath}/taobao/getTaobaoItemList.sc?status=1&page="+pageNo,
				success : function(data) {
					$("#content_list").html(data);
				}
			});
		}
		$(function(){
		
			$("#yougouStyleNo_,#yougouSupplierCode_,#yougouCommodityNo_,#yougouThirdPartyCode_").focusin(function() {
		  		if($.trim($(this).val())==msg){
					$(this).val("");
				}
			});
			$("#yougouStyleNo_,#yougouSupplierCode_,#yougouCommodityNo_,#yougouThirdPartyCode_").focusout(function() {
				if($.trim($(this).val())==""){
					$(this).val(msg);
				}
			});
			
			if($.trim($("#yougouStyleNo_").val())==""){
				$("#yougouStyleNo_").val(msg);
			}
			if($.trim($("#yougouSupplierCode_").val())==""){
				$("#yougouSupplierCode_").val(msg);
			}
			if($.trim($("#yougouCommodityNo_").val())==""){
				$("#yougouCommodityNo_").val(msg);
			}
			if($.trim($("#yougouThirdPartyCode_").val())==""){
				$("#yougouThirdPartyCode_").val(msg);
			}
		
			loadData(curPage);
			$('#importYougouTimeBegin').calendar({format: 'yyyy-MM-dd'});
			$('#importYougouTimeEnd').calendar({format: 'yyyy-MM-dd'});
		});
	</script>
	<style>
		.btn-recycle{position: absolute;top:-2px;right: 5px;z-index: 101}
	</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 商品导入</p>
			<div class="tab_panel  relative">
				<div class="tab_content"> 
					<ul class="tab">
						<li onclick="location.href='${BasePath}/taobao/goTaobaoItemList.sc?status=0'"><span>未导入到优购</span></li>
						<li class="curr"><span>已导入到优购</span></li>
					</ul>
					<div class="btn-recycle">
						<a class="button"><span onclick="document.location.href='${BasePath}/taobao/goRecycle.sc'">商品回收站</span></a>
					</div>
					<div class="search_box">
						<form name="queryVoform" id="queryVoform" action="${BasePath}/taobao/goTaobaoItemList.sc" method="post">
							<p>
								<span>
									<label>商品名称：</label>
									<input type="text" name="title" id="title" value="${(params.title)!'' }" class="inputtxt" />
									<input type="hidden" name="status" value="${status!'' }"/>
								</span>
								
								<span>
									<label>商品款号：</label>
								    <input type="text" id="yougouStyleNo_" name="yougouStyleNo_" class="inputtxt" value="${(params.yougouStyleNo)!''}"/>
								    <input type="hidden" id="yougouStyleNo" name="yougouStyleNo" />
								</span>
								<span>
									<label style="width:90px;">商家款色编码：</label>
								    <input type="text" id="yougouSupplierCode_" name="yougouSupplierCode_" class="inputtxt" value="${(params.yougouSupplierCode)!''}"/>
								    <input type="hidden" id="yougouSupplierCode" name="yougouSupplierCode"  />
								</span>
							
								<span>
									<label>商品编码：</label>
								    <input type="text" id="yougouCommodityNo_" name="yougouCommodityNo_" class="inputtxt" value="${(params.yougouCommodityNo)!''}"/>
								    <input type="hidden" id="yougouCommodityNo" name="yougouCommodityNo" />
								</span>
								</p>
							<p>
							
								<span>
									<label>货品条码：</label>
									<input type="text" id="yougouThirdPartyCode_" name="yougouThirdPartyCode_" class="inputtxt" value="${(params.yougouThirdPartyCode)!''}"/>
									<input type="hidden" id="yougouThirdPartyCode" name="yougouThirdPartyCode"   />
								</span>
								<span>
									<label>所属店铺：</label>
				                   	<select name="nickId" id="nick_id">
				                        <option value="">全部</option>
				                        <#if taobaoShop??>
											<#list taobaoShop as item>
												<option value="${(item.nid)!""}" <#if params.nid??&&params.nid==(item.nid)!''>selected="selected"</#if> >${(item.nickName)!""}</option>
											</#list>
										</#if>
				                    </select>
								</span>
								
								<span style="padding-left:85px;">
									<label>导入时间：</label>
									<input type="text" style="width:88px;" name="importYougouTimeBegin" id="importYougouTimeBegin" value="<#if (params.createdBegin)??>${params.createdBegin!'' }</#if>" class="inputtxt" style="width:80px;" /> 至
									<input type="text" style="width:88px;" name="importYougouTimeEnd" id="importYougouTimeEnd" value="<#if (params.createdEnd)??>${params.createdEnd!'' }</#if>" class="inputtxt" style="width:80px;" />
								</span>
								<span style="padding-left:82px;">
									<a class="button" id="mySubmit">
										<span onclick="loadData(1)">搜索</span>
									</a>
								</span>
							</p>
						</form>
					</div>
					<div id="content_list"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>