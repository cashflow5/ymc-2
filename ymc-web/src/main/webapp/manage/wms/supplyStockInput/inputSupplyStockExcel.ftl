<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家中心--库存导入</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<style>
#loading{ 
	width:150px; 
	height:22px; 
	border:1px solid #ccc;
	background:#fff;
	border-radius:5px;
} 
#loading div{ 
	width:0px; 
	height:22px; 
	background:#32CD32;
	color:#fff; 
	text-align:center; 
	font-family:Tahoma; 
	font-size:12px; 
	line-height:22px; 
} 
</style>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script language="javascript">

function loacdExport(){
	 location.href = "${BasePath}/wms/supplyStockInput/loacdExport.sc";
}
	
function ajaxUploadFile(path){
		var excelFile=document.getElementById("excelFile").value;
		if(excelFile==""){
			ygdg.dialog.alert("请选择导入文件！");
			return ;
		}
		var subName = excelFile.substring(excelFile.length - 3,excelFile.length);
			
		if (subName != "xls") {
			ygdg.dialog.alert("请选择xls文件！");
			return;
		}
		
		ygdg.dialog.confirm("确定导入库存？", function(){
			//清空文件域
			setTimeout(function(){
				$(".ginput").val("");
				var file = $("#excelFile");   
				file.after(file.clone().val(""));   
				file.remove();  
			},100);
			
			//禁用按钮
			disableButton('mySubmit');
			
			//显示进度条
			$('#progress_bar').show();
			doProgress(true);
			$.ajaxFileUpload({
				url:path+'/wms/supplyStockInput/excelSupplyStockInput.sc',
				secureuri:false,
				fileElementId:'excelFile',
				dataType: 'json',
				success: function(resultMsg, textStatus) {
					SetProgress(100);
					var result=resultMsg.success;
				   	if (result) {
					  ygdg.dialog.alert("导入成功！",function(){location.reload();});
					} else {
						if (resultMsg.msg != "") {
						   ygdg.dialog.alert(resultMsg.msg);
						}else{
							ygdg.dialog.alert("导入失败");
						}
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					SetProgress(100);
					ygdg.dialog.alert("导入excel数据有误，导入失败");
				}
			});
		});
	}	
	
	//禁用导入按钮
	function disableButton(id) {
		//样式置灰色
		$('#' + id).attr('class', 'button dis');
		//移除点击事件
		$('#' + id).removeAttr("onclick");
	}
	
	//释放导入按钮
	function freeButton(id) {
		//恢复样式
		$('#' + id).attr('class', 'button');
		//移除点击事件
		$('#' + id).attr("onclick", "return ajaxUploadFile('${BasePath}');");
	}
</script>
</head>
<body>
	
	
	<div class="main_container">
		
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 库存 &gt; 库存导入</p>
					<div class="tab_panel">
						<ul class="tab">
							<li class="curr"><span>库存导入</span></li>
						</ul>
					   
					   	<div class="stock_in">
							<h3>库存导入步骤：</h3>
							<p>
							1、请下载文件模板，并使用它准备您的库存数据库。<a href="${BasePath}/wms/supplyStockInput/exportTemplate.sc"  target="_blank" >下载库存导入模板(空)</a>
								<a href="javascript:;" onclick="loacdExport()" >下载库存导入模板(现有商品)</a>
							<br />
							2、将包含您库存数据的工作表保存为Excel文件，然后在此处将其上传：<br />
							 <span class="fl" style="padding-left:20px;">请选择要上传的文件：</span>
							
							 <form id="input" method="POST" action="" enctype="multipart/form-data" style="padding:0;margin:0" >
		                     <span class="fl" style="padding-left:5px;"><input  class="inputtxt"  id="excelFile" name="excelFile" type="file" size="55"/></span>
		                     <span class="fl" style="margin-left:50px;"><a class="button" id="mySubmit" onclick="return ajaxUploadFile('${BasePath}');"><span>立即导入</span></a></span>
		                     <div id="progress_bar" class="fl none" style="margin-left:10px;"> 
								<div id="loading"><div></div></div> 
							</div> 
	                         </form><br/>
							3、如果上传成功，请检查库存导入是否正确，如果处理报告出现任何错误，请您修改您的库存文件并重新上传。<br/>
							4、上传文件大小不能超过2M。
							</p>
						</div>
							<span id="uploadMsg"></span>
					</div>
					
			</div>
	 
	 </div>
	</div>
<script type="text/javascript">
$('#excelFile').jqfile();
setInterval(function(){
$(".ginput").val($("#excelFile").val());
},300);

var progress_id = "loading"; 
function SetProgress(progress) { 
	if (progress) { 
		$("#" + progress_id + " > div").css("width", String(progress) + "%"); //控制#loading div宽度 
		$("#" + progress_id + " > div").html(String(progress) + "%"); //显示百分比 
	} 
	if (progress == 100) {
		setTimeout(function() { $('#progress_bar').hide(); freeButton('mySubmit');}, 1000);
	}
} 

var i = 0;
var ij = 0;
function doProgress(isfirst) {
	var isflag = isfirst ? "true" : "false";
	i = isfirst ? 0 : i;
	$.ajax({
		type: "POST",
		url: '${BasePath}/wms/supplyStockInput/queryStockImportProgressBar.sc',
		data: {isflag:isflag},
		dataType : 'json',
		async:false,
		success: function(data){
			if (data.result == "true") {
				ij = data.progress;
			}
		}
	});
	if (ij < 100 || i == 0) { 
		setTimeout("doProgress(false)", 50);
		i++; 
		if (i == 0) ij = 0;
		SetProgress(ij);
	}
	if (ij >= 100) { 
		i++;
		SetProgress(ij);
		ij++;
		return;
	}
}
</script>
</body>
</html>
