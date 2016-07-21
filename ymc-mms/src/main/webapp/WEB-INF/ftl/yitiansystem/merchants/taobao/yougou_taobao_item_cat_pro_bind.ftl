<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<style>
	a.pro-close{background:url("${BasePath}/images/taobao_pro_open.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	a.pro-open{background:url("${BasePath}/images/taobao_pro_close.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	.list_table{background:#E2E2E2}
	.list_table th.line{border-left:1px solid #AEC7E5}
	.list_table th.liner{border-right:1px solid #AEC7E5}
	.list_table tr.line{border-right:1px solid #AEC7E5;border-left:1px solid #AEC7E5}
	.list_table th{text-align:center}
	.list_table td{background:#fff;border-bottom:1px solid #AEC7E5;text-align:center}
	.list_table td table td{border:none;}
	.list_table td table th{background:#F2F2F2;border:1px solid #D7D7D7}
	.list_table td table td table{margin-bottom:10px;margin-top:10px;}
	.list_table td table td table td{border:1px solid #D7D7D7}
	.headstep{background:url('${BasePath}/images/importtaobaostep-new.jpg') 0px -31px;height:25px;padding-top:6px;color:#ffffff;font-weight:bold;}
	.info{background:#F2F2F2;padding:10px;margin:10px 0px;font-size:13px;}
	.closeIcon{display:block;width:100%;height:20px;}
	.cat-info{margin-top:10px;}
	.cat-info-s{margin-top:10px;}
	.cat-info-s span{font-weight: bold;}
	.cat-info-red{color:red;}
	table.list_table tr.on td{background:#fff}
	.qr-code{_position:absolute;top:150px;right:10px;height:auto;display:none;position:fixed;padding:0px;width:150px;border:1px solid #CBD8ED;background:#FFFFFF;}
	.msgdivtd{border-bottom: 1px solid #CBD8ED;background-color: #F1F8FF;height:24px;padding:1px;color:#ff0000;}
	.qr-code table table td{height:30px;}
	.qr-code table td a{color:blue;}
</style>
<script>
	var taobaoYougouItemCatPro = {};
	taobaoYougouItemCatPro.basePath  ="${BasePath}";
	taobaoYougouItemCatPro.catId = "${catId}";
	taobaoYougouItemCatPro.bindId = "${catTemplet.id}";
	taobaoYougouItemCatPro.taobaoCid = "${taobaoCid}";
</script>
<script type="text/javascript" src="${BasePath}/js/mms.common.js?${version}"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/taobao/yougou.taobao.itemcat.pro.js?${version}"></script>
</head>
<body>
<div class="modify continer">
	<div class="normal_box">
		<div class="tab_panel  relative">
		<p style="position:absolute;top:0;right:0;">
			<div class="headstep">
				<div style="float:left;width:330px;text-align:center;color:#000000">分类对应设置</div>
				<div style="float:left;width:330px;text-align:center;">分类属性对应设置</div>
				<div style="float:left;width:295px;text-align:center;color:#000000">设置成功</div>
			</div>
			<div class="info">
				<div>1. 由于淘宝品牌名称、分类属性、分类属性值与优购不一致，需要手动匹配对应关系。</div>
				<div style="margin-top:10px;">2. 请保证分类及属性对应的准确性，避免误操作导致商品资料匹配异常，从而需要投入更多精力进行后期的修改。</div>
			</div>
			<div class="cat-info">
				<div class="cat-info-s"><span>优购分类：</span>${catTemplet.yougouCatFullName}</div>
				<div class="cat-info-s"><span>淘宝分类：</span>${catTemplet.taobaoCatFullName}</div>
				<div class="cat-info-s"><span>属性匹配：</span><span class="cat-info-red">带*为优购必填属性，必填项属性和属性值全部匹配成功，选填项可根据实际需要进行匹配</span></div>
			</div>
		</p>
			<div class="tab_content" id="tab_content" style="margin-top:20px;"> 
				<form id="subForm" method="post">
				</form>
			</div>
			<div style="margin-top:20px;text-align:center">
				<input type="button" value="&nbsp;保&nbsp;存&nbsp;" onclick="taobaoYougouItemCatPro.save();" class="yt-seach-btn" style="margin-left:0px;" />
			</div>
		</div>
	</div>
</div>
</body>
</html>