<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>常用快递设置</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/jquery.dragsort-0.5.1.js?${style_v}"></script>
	<style type="text/css">
		.dragsort{width:95%;list-style-type:none;margin:0px;}
		.dragsort li{padding:10px;float:left;width:100px;}
		.dragsort div{width:100px;height:18px;border:solid 1px black;text-align:center;padding-top:1px;}
		.placeHolder div{background-color:white!important;border:dashed 1px gray!important;}
	</style>
</head>
<body>
	<div id="Main">
		<div class="normal_box" style="float:left;width:99%;">
			<div class="form_container">
				<form name="querFrom" id="querFrom" action="${BasePath}/order/fastdelivery/dispatched.sc" method="post" style="padding:0px;margin:0px;">
					<div style="font-size:14px;font-weight:bold;">常用快递（最多6个）</div>
					<ul id="list1" class="dragsort" style="padding:15px 20px; border-bottom: dashed 1px grey;float:left;">
						<#list frequentUseCompanyList![] as company>
							<li>
							<div style="cursor: pointer;" title="${company.logistics_company_name}">
							<span>${(company.logistics_company_name)!''}</span>
							<span style="display: none;" class="code">${(company.logistic_company_code)!'' }</span></div>
							</li>
						</#list>
					</ul>
					<div style="font-size:14px; font-weight:bold;float:left;">选择快递（拖动修改）</div>
					<ul id="list2" class="dragsort" style="padding:15px 20px; float:left;">
						<#list logisticscompanList![] as item>
						  	<li>
						  	<div style="cursor: pointer;" onmousemove="startMove(this);" title="${item.logistics_company_name}">
						  	<span>${(item.logistics_company_name)!''}</span>
						  	<span style="display: none;" class="code">${(item.logistic_company_code)!'' }</span></div>
						  	</li>
						</#list>
				    </ul>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var flag = true;
		var mousedown = false;
		$("#list1, #list2").dragsort({
			dragSelector: "div",
			dragBetween: flag,
			dragEnd: saveOrder,
			placeHolderTemplate: "<li class='placeHolder'><div><span></span></div></li>",
			scrollSpeed: 5
		});
		
		function saveOrder(){
		}

		function startMove(obj){
				var lis = $("#list1 li");
				if(lis.size()>=6){
					var ulId = $(obj.parentNode.parentNode).attr("id");
					if("list2"==ulId){
						$("#list1, #list2").dragsort("destroy");
						$("#list1, #list2").dragsort({
							dragSelector: "div",
							dragBetween: false,
							dragEnd: saveOrder,
							placeHolderTemplate: "<li class='placeHolder'><div></div></li>",
							scrollSpeed: 5
						});
					}else{
						$("#list1, #list2").dragsort("destroy");
						$("#list1, #list2").dragsort({
							dragSelector: "div",
							dragBetween: true,
							dragEnd: saveOrder,
							placeHolderTemplate: "<li class='placeHolder'><div></div></li>",
							scrollSpeed: 5
						});
					}
				}
		}
	</script>
</body>
</html>
