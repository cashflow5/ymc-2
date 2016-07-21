<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" href="${BasePath}/yougou/css/zTreeStyle.css?${style_v}" type="text/css">

<style>
	a.pro-close{background:url("${BasePath}/yougou/images/taobao_pro_open.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	a.pro-open{background:url("${BasePath}/yougou/images/taobao_pro_close.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	.list_table th.line{border-left:1px solid #AEC7E5}
	.list_table th.liner{border-right:1px solid #AEC7E5}
	.list_table tr.line{border-right:1px solid #AEC7E5;border-left:1px solid #AEC7E5}
	.list_table td table td{border:none;}
	.list_table td table th{background:#F2F2F2;border:1px solid #D7D7D7}
	.list_table td table td table{margin-bottom:10px;margin-top:10px;}
	.list_table td table td table td{border:1px solid #D7D7D7}
	.headstep{background:url('${BasePath}/yougou/images/importtaobaostep-new.jpg') 0px -31px;height:25px;padding-top:6px;color:#ffffff;font-weight:bold;}
	.info{background:#F2F2F2;padding:10px;margin:10px 0px;font-size:13px;}
	.closeIcon{display:block;width:100%;height:20px;}
</style>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script>
	var taobaoYougouItemCatPro = {};
	taobaoYougouItemCatPro.basePath  ="${BasePath}";
	taobaoYougouItemCatPro.catId = "${catId}";
	taobaoYougouItemCatPro.bindId = "${bindId}";
	taobaoYougouItemCatPro.taobaoCid = "${taobaoCid}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.itemcat.pro.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 淘宝商品导入设置</p>
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
		</p>
			<div class="tab_content" id="tab_content"> 
				<form id="subForm" method="post">
				</form>
			</div>
			<div style="margin-top:20px;text-align:center">
				<a class="button" id="nextBtn" style="margin:0px;display:none" onclick="taobaoYougouItemCatPro.save()" style="color:#666666"><span>&nbsp;保&nbsp;存&nbsp;</span></a>
			</div>
			<div id="msgdiv" class="qr-code" style="width:300px;">
				   <table style="width:100%;">
				      <tr>
				        <td class="msgdivtd">提交校验(保存不了看这里)</td>
				      </tr>
				      <tr>
				        <td id="showtd">暂无记录!</td>
				      </tr>
				      <tr>
				        <td style="text-align:right;"><a href="javascript:hiddenMsgBox();" id="J_QrCodeClose">关闭</a></td>
				      </tr>
				   </table>
			</div>
		</div>
	</div>
</div>
</body>
</html>