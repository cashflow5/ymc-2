<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<link rel="stylesheet" href="${BasePath}/css/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${BasePath}/js/ztree/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/mms.common.js?${version}"></script>
	<style>
		div.input-style{border:1px solid #C9C9C9;height:25px;width:560px;color:#000000;position:relative;cursor:pointer}
		div.input-text-tree{float:left;width:530px;height:25px;padding-top:5px;padding-left:10px;}
		div.input-icon{float:left;width:20px;cursor:pointer;font-weight:bold;font-size:20px;text-align:center;margin-top:2px;}
		div.tree-con{position:absolute;display:none;border:1px solid #797979;width:100%;top:25px;left:-1px;background:#FFFFFF;z-index:1000;}
		.headstep{background:url('${BasePath}/images/importtaobaostep-new.jpg');height:25px;padding-top:6px;color:#ffffff;font-weight:bold;}
		.info{background:#F2F2F2;padding:10px;margin:10px 0px;font-size:13px;}
	</style>
	<script>
		var taobaoYougouItemCatBind = {};
		taobaoYougouItemCatBind.basePath  ="${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/taobao/yougou.taobao.itemcatbind.js?${version}"></script>
</head>
<body>
<div class="modify continer" >
		<div class="normal_box" style="height:800px;">
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;">
				<div class="headstep">
					<div style="float:left;width:330px;text-align:center;">分类对应设置</div>
					<div style="float:left;width:330px;text-align:center;color:#000000">分类属性对应设置</div>
					<div style="float:left;width:295px;text-align:center;color:#000000">设置成功</div>
				</div>
				<div class="info">
					<div>1. 由于淘宝品牌名称、分类属性、分类属性值与优购不一致，需要手动匹配对应关系。</div>
					<div style="margin-top:10px;">2. 请保证分类及属性对应的准确性，避免误操作导致商品资料匹配异常，从而需要投入更多精力进行后期的修改。</div>
				</div>
			</p>
				<div class="tab_content"> 
					<form id="bindForm" method="post" action="${BasePath}/yitiansystem/taobao/bindTaobaoYougouItemCat.sc">
						<input type="hidden" name="yougouItemId" id="yougouItemId">
						<input type="hidden" name="taobaoCid" id="taobaoCid">
						<input type="hidden" name="taobaoCatFullName" id="taobaoCatFullName">
						<input type="hidden" name="yougouCatFullName" id="yougouCatFullName">
						<table>
							<tr style="height:30px">
								<td>选择优购分类：</td>
								<td>
									<div class="input-style" id="yougouInput">
										<div class="input-text-tree" id="yougouItemName"></div>
										<div class="input-icon" id="yougouIcon">+</div>
										<div class="tree-con" id="yougouTree">
											<ul id="tree_yougou" class="ztree" style="width:548px; overflow:auto;"></ul>
										</div>
									</div>
								</td>
							</tr>
							<tr style="height:30px">
								<td>选择淘宝分类：</td>
								<td>
									<div class="input-style" id="taobaoInput">
										<div class="input-text-tree" id="taobaoItemName"></div>
										<div class="input-icon" id="taobaoIcon">+</div>
										<div class="tree-con"  id="taobaoTree">
											<ul id="tree_taobao" class="ztree" style="width:548px; overflow:auto;"></ul>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td></td>		
								<td>
									<input type="button" value="下一步" onclick="taobaoYougouItemCatBind.nextStep();" class="yt-seach-btn" style="margin-left:0px;" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
</div>
</body>
</html>