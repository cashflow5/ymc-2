<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-商品回收站</title>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script>
		var taobaoItem = {};
		taobaoItem.basePath = "${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.recycle.list.js?${style_v}"></script>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 商品回收站</p>
			<div class="tab_panel  relative">
				<div class="tab_content"> 
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/taobao/goRecycle.sc" method="post">
						<p>
							<span>
								<label>商品名称：</label>
								<input type="text" name="title" id="title" value="${(params.title)!'' }" class="inputtxt" />
								<input type="hidden" name="status" value="${status!'' }"/>
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
						</p>
						<p>
							<span>
									<label>货品条码：</label>
									<input type="text" id="yougouThirdPartyCode_" name="yougouThirdPartyCode_" class="inputtxt" value="${(params.yougouThirdPartyCode)!''}"/>
									<input type="hidden" id="yougouThirdPartyCode" name="yougouThirdPartyCode"   />
							</span>
							<span>
								<label>下载时间：</label>
								<input type="text" style="width:119px;" name="operatedBegin" id="operatedBegin" value="<#if (params.createdBegin)??>${params.createdBegin!'' }</#if>" class="inputtxt" style="width:80px;" /> 至
								<input type="text" style="width:119px;" name="operatedEnd" id="operatedEnd" value="<#if (params.createdEnd)??>${params.createdEnd!'' }</#if>" class="inputtxt" style="width:80px;" />
							</span>
							<span style="padding-left:80px;">
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
</body>
</html>
<script>
$(document).ready(function(){
	$('#operatedBegin').calendar({format: 'yyyy-MM-dd'});
	$('#operatedEnd').calendar({format: 'yyyy-MM-dd'});
}); 
</script>