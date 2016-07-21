<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-淘宝商品csv导入</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
    <link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
    <style type="text/css">
       .qr-code{_position:absolute;top:150px;right:10px;height:auto;display:none;position:fixed;padding:0px;width:300px;border:1px solid #CBD8ED;background:#FFFFFF;}
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
		    content: "淘宝csv数据文件导入";
		    font-size: 12px;
		    font-weight: bold;
		    left: 15px;
		    letter-spacing: 1px;
		    position: absolute;
		    text-transform: uppercase;
		    top: 15px;
		}
		.wu-example {
		    margin: 15px 0;
		    padding: 45px 15px 15px;
		    position: relative;
		}
		.uploader-list {
		    overflow: hidden;
		    width: 100%;
		}
    </style>

</head>

<body>
	<div class="main_container relative">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 淘宝商品csv导入</p>
			<div class="tab_panel" style="margin-top:15px;">
				<div class="tab_content">
						<p>
							<span class="fl icon_info"></span>
							<span class="fl ml5" style="color:#999;">请先从淘宝助手导出需要导入优购平台的商品资料。</span>
						</p>
						<p class="blank20"></p>
						<div id="uploader" class="wu-example">
						    <!--用来存放文件信息-->
						    <div id="thelist" class="uploader-list"></div>
						    <div class="btns">
						        <div id="picker" style="width:80px;float:left;">选择文件</div>
						        <button id="ctlBtn" class="btn btn_gary6" style="float:left;margin-top:4px;">开始上传</button>
						        <div style="clear:left;"></div>
						    </div>
						</div>
						<p class="blank20" style="padding-left:20px;"><label id="message" style="width:680px; display:inline-block;color:#ff0000;"></label></p>
						<div style="background:#f2f2f2;border:1px solid #ccc;color:	#FFA07A;padding:10px;">
							<p>注意事项：1.单个csv文件不能超过10M。</p>
							<p>
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.商品导入成功以后，请到[商品导入]菜单查看。
							</p>
							<p>
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.数据比较多时，后台处理较慢，请耐心等待。
							</p>
						</div>
				</div>
				<div id="msgdiv" class="qr-code">
				   <table style="width:100%;">
				      <tr>
				        <td class="msgdivtd" id="tishi">提示</td>
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
<script type="text/javascript">
var $btn = $('#ctlBtn');
var state = 'pending';
var exDialog;
var fileid='';
var uploader = WebUploader.create({
    // swf文件路径
    swf: '${BasePath}/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: "${BasePath}/taobao/csvImport.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id: '#picker',
    	multiple:false
    },
    // 只允许选择CSV。
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
    //fileSingleSizeLimit:1024*5
    compress:false  //压缩
});
uploader.on( 'fileQueued', function( file ) {
    fileid=file.id;
    $("#thelist").html( '<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
    '</div>' );
});
// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
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
uploader.on( 'beforeFileQueued', function( file ) {
    if(''!=fileid){
        uploader.removeFile(fileid,true);
    }
	var fileType = file.ext;
	if(fileType!="csv"){
		ygdg.dialog.alert("请上传后缀为.csv的数据文件");
		return false;
	}
	return true;
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploader.on( 'uploadSuccess', function( file,response) {
    exDialog.close();
    $( '#'+file.id ).find('p.state').text('已上传');
    console.info(response);
	if(response.resultCode=="200"){
	    if(response.msg.length==0){
	       ygdg.dialog.alert("上传成功!数据总数:"+response.total);
	    }else{
	        $('#tishi').html("数据总数:"+response.total+"  失败总数:"+response.failTotal);
			var tablehtml="<table style='width:100%;'>";
			for ( var int = 0; int < response.msg.length; int++) {
				tablehtml=tablehtml+'<tr><td style="padding:2px;"><img src="/yougou/images/error.gif" class="goods_error_image" />'+ response.msg[int]+'</td></tr>';
			}
			tablehtml=tablehtml+"</table>";
			$('#showtd').show().html(tablehtml);
			$('#msgdiv').show();
			//clear Array
			errorList.length = 0;
	    }
	}else{
		ygdg.dialog.alert(response.msg);
	}
});

// 文件上传失败，显示上传出错。
uploader.on( 'uploadError', function( file ) {
    exDialog.close();
    $( '#'+file.id ).find('p.state').text('上传出错');
    ygdg.dialog.alert("上传失败");
});
uploader.on( 'all', function( type ) {
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
        uploader.stop();
    } else {
    	exDialog=ygdg.dialog({
			content:"上传处理中，请稍等片刻<img src='${BasePath}/yougou/images/loading.gif'/>",
			title:'提示',
			cancel:function(){exDialog=null;return true;},
			cancelVal:'取消',
			lock:true
	    });
        uploader.upload();
    }
});
function hiddenMsgBox(){
	$("#msgdiv").hide();
}
</script>