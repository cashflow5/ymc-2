<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-淘宝商品导入</title>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script>
		var taobaoItem = {};
		taobaoItem.basePath = "${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
	<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.item.list.js?${style_v}"></script>
	<style type="text/css">
	   table td.tdtit{text-align: right;}
	   .dobox a{margin-left:2px;}
	   .qr-code{_position:absolute;
	              top:150px;
	              right:200px;
	              overflow-y:auto; 
	              height:450px;
	              display:none; 
	              position:fixed; z-index:99999;
	              padding:0px;
	              width:700px;
	              border:1px solid #CBD8ED;
	              padding:0px 0px 5px 5px;
	              background:#FFFFFF;
	              word-break:break-all;}
       .msgdivtd{border-bottom: 1px solid #CBD8ED;background-color: #F1F8FF;height:24px;padding:1px;color:#ff0000;}
       .webuploader-pick{position: relative;
		display: inline-block;
		cursor: pointer;
		background: none repeat scroll 0% 0% #00B7EE;
		padding: 1px 5px;
		color: #FFF;
		text-align: center;
		border-radius: 3px;
		overflow: hidden;line-height: 20px;margin-top:5px;}
		#downloadError span{color:red;margin-right:0px;}
		.wu-example:after {
		    color: #bbb;
		    content: "请先从淘宝助手导出需要导入优购平台的商品资料(CSV文件)";
		    font-size: 12px;
		    left: 15px;
		    letter-spacing: 1px;
		    position: absolute;
		    text-transform: uppercase;
		    top: 10px;
		}
		.wu-example {
		    padding: 40px 15px 15px;
		    position: relative;
		}
		.uploader-list {
		    overflow: hidden;
		    width: 100%;
		}
		.btn-recycle{position: absolute;top:80px;right: 5px;z-index: 101}
	</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 商品导入</p>
			<div class="tab_panel  relative">
	    		<div id="uploader" class="wu-example">
				    <!--用来存放文件信息-->
				    <div id="thelist" class="uploader-list"></div>
				    <div class="btns">
				        <div id="picker" style="width:90px;float:left;">选择CSV文件</div>
				        <button id="ctlBtn" class="btn btn_gary6" style="float:left;margin-top:4px;">开始上传</button>
				        <div style="color:#ff6600;float:left;margin: 4px 30px;"><span id="warm_msg">&nbsp;&nbsp;注意:单个csv文件不能超过10M,数据比较多时,后台处理较慢,请耐心等待.</span></div>
				        <div style="clear:left;"></div>
				    </div>
				</div>
				<div class="tab_content"> 
					<ul class="tab">
						<li class="curr"><span>未导入到优购</span></li>
						<li onclick="location.href='${BasePath}/taobao/goTaobaoItemList.sc?status=1'"><span>已导入到优购</span></li>
					</ul>
				</div>	
				<div class="btn-recycle">
						<a class="button"><span onclick="document.location.href='${BasePath}/taobao/goRecycle.sc'">商品回收站</span></a>
				</div>
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/taobao/goTaobaoItemList.sc" method="post">
						<table  width="100%">
							<tr style="height:30px;">
								<td class="tdtit">商品名称：</td>
								<td>
									<input type="text" name="title" id="title" value="${(params.title)!'' }" class="inputtxt" />
									<input type="hidden" name="status" value="${status!'' }"/>
								</td>
								<td class="tdtit">商品款号：</td>
								<td>
									<input type="text" id="yougouStyleNo_" name="yougouStyleNo_" value="${(params.yougouStyleNo)!''}" class="inputtxt" />
									<input type="hidden" id="yougouStyleNo" name="yougouStyleNo" />
								</td>
								<td class="tdtit">款色编码：</td>
								<td>
									<input type="text" id="yougouSupplierCode_" name="yougouSupplierCode_" value="${(params.yougouSupplierCode)!''}" class="inputtxt" />
									<input type="hidden" id="yougouSupplierCode" name="yougouSupplierCode" />
								</td>
							</tr>
							<tr style="height:30px;">
							
								<td class="tdtit">货品条码：</td>
								<td>
									<input type="text" id="yougouThirdPartyCode_"  name="yougouThirdPartyCode_" value="${(params.yougouThirdPartyCode)!''}" class="inputtxt" />
									<input type="hidden" id="yougouThirdPartyCode"  name="yougouThirdPartyCode" />
								</td>
								
								<td class="tdtit">所属店铺：</td>
								<td>
									<select name="nickId" id="nick_id" style="width:128px;">
			                        <option value="">全部</option>
			                        <#if taobaoShop??>
										<#list taobaoShop as item>
											<option value="${(item.nid)!""}" <#if params.nid??&&params.nid==(item.nid)!''>selected="selected"</#if> >${(item.nickName)!""}</option>
										</#list>
									</#if>
			                    </select>
								</td>
								<td class="tdtit">验证状态：</td>
								<td>
									<select name="checkStatus" style="width:128px;">
				                        <option value="">全部</option>
				                        <option value="0">校验未通过</option>
				                        <option value="1">校验已通过</option>
				                        <option value="2">后台处理中</option>
			                   	 	</select>
								</td>
								
							</tr>
							<tr style="height:30px;">
							
								<td class="tdtit">下载时间：</td>
								<td>
									<input type="text" style="width:100px;" name="operatedBegin" id="operatedBegin" value="<#if (params.createdBegin)??>${params.createdBegin!'' }</#if>" class="inputtxt" style="width:80px;" /> 至
									<input type="text" style="width:100px;" name="operatedEnd" id="operatedEnd" value="<#if (params.createdEnd)??>${params.createdEnd!'' }</#if>" class="inputtxt" style="width:80px;" />
								</td>
								
								<td class="tdtit">优购分类：</td>
								<td colspan="3">
									<select id="sel1" style="width:82px;">
										<option value="" selected="selected">一级分类</option>
										<#list yougouTree as item>
								    		<option value="${(item.structName)!""}">${(item.name)!""}</option>
								    	</#list>
									</select>
									<select id="sel2" style="width:124px;">
										<option value="" selected="selected">二级分类</option>
									</select>
									<select id="sel3" name="catId" style="width:188px;">
										<option value="" selected="selected">三级分类</option>
									</select>
								</td>
								<td class="tdtit"><a class="button" id="mySubmit"><span onclick="search()">搜索</span></a></td>
								<td></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="content_list"></div>
				</div>
				<div id="msgdiv" class="qr-code">
				   <table style="width:100%;">
				      <tr>
				        <td class="msgdivtd" id="tishi" style="height:25px;">提示</td>
				      </tr>
				      <tr>
				        <td id="showtd">暂无记录!</td>
				      </tr>
				   </table>
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
var $btn = $('#ctlBtn');
var state = 'pending';
var exDialog;
var fileid='';

var uploaderCSV = WebUploader.create({
    swf: '${BasePath}/webuploader/Uploader.swf',
    server: "${BasePath}/taobao/csvImport.sc",
    pick: {
    	id: '#picker',
    	multiple:false
    },
    accept: {
        title: 'CSV',
        extensions: '*',
        mimeTypes: 'csv/*'
    },
    fileNumLimit:2,
    fileSingleSizeLimit:10*1024*1024,
    formData:{
       no:1,
    },
    duplicate:1,   //不去重
    compress:false  //压缩
});

// 在文件开始发送前做些异步操作。
// WebUploader会等待此异步操作完成后，开始发送文件。
//uploaderCSV.timeout(1000);

uploaderCSV.on( 'fileQueued', function(file) {
    fileid=file.id;
    $("#thelist").html( '<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
    '</div>' );
});
// 文件上传过程中创建进度条实时显示。
uploaderCSV.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }

    $li.find('p.state').text('上传中');

    $percent.css( 'width', percentage * 100 + '%' );
});
uploaderCSV.on( 'beforeFileQueued', function( file ) {
    if(''!=fileid){
        uploaderCSV.removeFile(fileid,true);
    }
	var fileType = file.ext;
	if(fileType!="csv"){
		ygdg.dialog.alert("请上传后缀为.csv的数据文件");
		return false;
	}
	return true;
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploaderCSV.on( 'uploadSuccess', function( file,response) {
    exDialog.close();
    $( '#'+file.id ).find('p.state').text('已上传');
    if(typeof response!="object"){    
    	response = eval('('+response+')');
    }
	if(response.resultCode=="200"){
	    if(response.msg.length==0){
	       loadData(1);
	       ygdg.dialog.alert("成功导入"+response.total+"条数据   合计"+response.extendTotal+"条记录");
	    }else{
	        if(response.total!=response.failTotal){
	        	loadData(1);
	        }
	       // $('#warm_msg').before('<a id="csv_down" href="'+response.downUrl+'">下载错误CSV文件</a>');
	        $('#tishi').html("<span style='display:inline-block;float:left;'>成功数据总数:"+response.total+"条数据   合计"+response.extendTotal+"条记录"+"; 失败数据总数:"+response.failTotal+"; 重复数据总数："+response.existTotal+" </span><a href='javascript:hiddenMsgBox();' id='J_QrCodeClose' style='display:inline-block;float:right'>关闭</a>");
			var tablehtml="<table style='width:100%;'>";
			for ( var int = 0; int < response.msg.length; int++) {
				tablehtml=tablehtml+'<tr><td style="padding:2px;"><img src="/yougou/images/error.gif" class="goods_error_image" />'+ response.msg[int].errMsg+'</td></tr>';
			}
			tablehtml=tablehtml+"</table>";
			$('#showtd').show().html(tablehtml);
			$('#msgdiv').show();
	    }
	    $("#thelist").html('');
	}else{
		ygdg.dialog.alert(response.msg);
	}
});

// 文件上传失败，显示上传出错。
uploaderCSV.on( 'uploadError', function( file ) {
    exDialog.close();
    $( '#'+file.id ).find('p.state').text('上传出错');
    ygdg.dialog.alert("上传失败");
});
uploaderCSV.on( 'all', function( type ) {
    if ( type === 'startUpload' ) {
        state = 'uploading';
    } else if ( type === 'stopUpload' ) {
        state = 'paused';
    } else if ( type === 'uploadFinished' ) {
        state = 'done';
    }
    if ( state === 'uploading' ) {
        $btn.text('暂停上传');
    } else {
        $btn.text('开始上传');
    }
});
$btn.on( 'click', function() {
    if ( state === 'uploading' ) {
        uploaderCSV.stop();
    } else if(uploaderCSV.getFiles("queued").length>0){
    	exDialog=ygdg.dialog({
			content:"上传处理中，请稍等片刻<img src='${BasePath}/yougou/images/loading.gif'/>",
			title:'提示',
			cancel:function(){exDialog=null;return true;},
			cancelVal:'取消',
			lock:true
	    });
	    hiddenMsgBox();
	    $('#csv_down').remove();
        uploaderCSV.upload();
    }else{
    	ygdg.dialog.alert("请先选择要上传的CSV文件.");
    }
});
function hiddenMsgBox(){
	$("#msgdiv").hide();
}
setInterval(function() {
    if($("span[type='2']").length>0){
    	loadData($('#pageNo').val());
    }
}, 15000);
</script>
