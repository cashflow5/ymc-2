<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-淘宝店铺管理</title>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script>
		var basePath = "${BasePath}";
		function updateStatus(id,status){
		  	$.ajax({ 
				type: "post", 
				url: "${BasePath}/taobao/updateTaobaoShopStatus.sc", 
				data:{"id":id,"status":status},
				dataType: "json",
				success: function(dt){
					if(true==dt.success){
					   location.reload(); 
					}else{
					    ygdg.dialog.alert(dt.message);
					}
				} 
		    });
       }
	</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 平台商品导入 &gt; 淘宝店铺管理</p>
			<div class="tab_panel  relative">
				<div class="tab_content"> 
					<ul class="tab">
						<li class="curr">
							<span>淘宝店铺管理</span>
						</li>
					</ul>
					<div style="margin-top: 15px;"><a class="button" id="newShop" onclick="newShopFunc();"><span>添加淘宝店铺</span></a></div>
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th>淘宝店铺名称</th>
								<th>淘宝店铺URL</th>
								<th>店铺主账号</th>
								<th>创建时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr>
										<td style="width:150px;">${item.taobaoShopName!''}</td>
										<td>${item.taobaoShopUrl!''}</td>
										<td style="width:150px;">${item.nickName!''}</td>
										<td style="width:115px;">${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
										<td><#if statics ??><span style="<#switch '${item.status!0}'><#case '0'>color:#EE9A00;<#break><#case '1'>color:#666666;<#break><#case '2'>color:#228B22;<#break><#case '3'>color:#66CC22;<#break><#default>color:#666666;</#switch>">${statics['com.yougou.kaidian.taobao.constant.TaobaoShopStatus'].getStatusName(item.status!0)}</span></#if></td>
										<td style="width:115px;">
											<#if item.status == 0>
												<a href="javascript:updateShopFunc('${(item.id)!''}')">修改</a>
												<a href="javascript:updateStatus('${(item.id)!''}','-1')">删除</a>
												<a href="javascript:updateStatus('${(item.id)!''}','1')">提交审核</a>
											<!--提交审核-->
											<#elseif item.status == 1>
											<!--审核通过-->
											<#elseif item.status == 2>
												<a href="javascript:taobaoAuthorization('${(item.topAppkey)!''}');">授权</a>
												<a href="javascript:updateStatus('${(item.id)!''}','-1')">删除</a>
											<#elseif item.status == 3>
											    <a href="javascript:taobaoAuthorization('${(item.topAppkey)!''}');">授权</a>
												<a href="javascript:updateStatus('${(item.id)!''}','-1')">删除</a>
											</#if>
										</td>
									</tr>
								</#list>
							<#else>
								<tr class="do_tr">
									<td class="td-no" colspan="17">
											没有相关数据
									</td>
								</tr>
							</#if>	
						</tbody>
					</table>
					<!--列表end--> 
					<#if pageFinder??&&pageFinder.data??>
					<!--分页start-->
					<div class="page_box">
						<div class="dobox">
						</div>
						<#if pageFinder ??>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						</#if>
					</div>
					<!--分页end-->
					</#if>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var addShopSubmit=function()
		{
			var iframe = this.iframe.contentWindow;
			iframe.addTaobaoShop();
			return false;
		}
		function newShopFunc() {
 			openwindow('${BasePath}/taobao/goNewTaobaoShop.sc',600,180,'添加淘宝店铺',addShopSubmit);
		}
		function updateShopFunc(id) {
 			openwindow('${BasePath}/taobao/goUpdateTaobaoShop.sc?id='+id,600,180,'修改淘宝店铺',addShopSubmit);
		}
		function taobaoAuthorization(appkey){
			if(appkey==null||appkey==''){
				ygdg.dialog.alert("淘宝对应的appkey为空,请联系管理员!");
			}
			ygdg.dialog.confirm("授权已成功?",function(){location.reload(); },function(){});
			window.open("http://container.open.taobao.com/container?appkey="+appkey);
		}
	</script>
</body>
</html>
