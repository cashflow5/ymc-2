<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<style>
	a.pro-close{background:url("${BasePath}/yougou/images/taobao_pro_open.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	a.pro-open{background:url("${BasePath}/yougou/images/taobao_pro_close.png") 22px 0px no-repeat;display:inline-block;width:40px;text-align:left;}
	.list_table th.line{border-left:1px solid #AEC7E5}
	.list_table th.liner{border-right:1px solid #AEC7E5}
	.list_table tr.line{border-right:1px solid #AEC7E5;border-left:1px solid #AEC7E5}
	.list_table th{text-align:center}
	.list_table td{background:#fff;border-bottom:1px solid #AEC7E5;text-align:center}
	.list_table td table td{border:none;}
	.list_table td table th{background:#F2F2F2;border:1px solid #D7D7D7}
	.list_table td table td table{margin-bottom:10px;margin-top:10px;}
	.list_table td table td table td{border:1px solid #D7D7D7}
	.headstep{background:url('${BasePath}/yougou/images/importtaobaostep-new.jpg') 0px -31px;height:25px;padding-top:6px;color:#ffffff;font-weight:bold;}
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
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script>
	var taobaoYougouItemCatProTemplet = {};
	taobaoYougouItemCatProTemplet.basePath  ="${BasePath}";
	taobaoYougouItemCatProTemplet.catId = "${catId}";
	taobaoYougouItemCatProTemplet.bindId = "${catTemplet.id}";
	taobaoYougouItemCatProTemplet.taobaoCid = "${taobaoCid}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.itemcat.pro.templet.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 查看类目属性模板</p>
			<div class="tab_panel  relative">
				<div class="tab_content"> 
					<div class="cat-info">
						<div class="cat-info-s"><span>优购分类：</span>${catTemplet.yougouCatFullName}</div>
						<div class="cat-info-s"><span>淘宝分类：</span>${catTemplet.taobaoCatFullName}</div>
					</div>
				</div>
				<div>
					<form id="subForm" method="post">
						
					</form>
				</div>
			</div>
		</div>
</div>
</body>
</html>