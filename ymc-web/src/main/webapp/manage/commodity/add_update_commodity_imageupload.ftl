<#macro add_update_commodity_imageupload>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${BasePath}/webuploader/style.css?${style_v}"/>
<style>
#uploader .queueList {margin:5px;}
#uploader .placeholder {border: 3px dashed #e6e6e6;
    min-height:325px;padding-top: 158px;text-align: center;
     color: #cccccc;font-size: 18px;position: relative;
}
#uploader .statusBar .btns {top: 5px;}
#uploader .statusBar{height:53px;border-top:0px;}
#cancelBtn,#insertImgBtn{background: none repeat scroll 0% 0% #FFF;border: 1px solid #CFCFCF;color: #565656;padding: 0px 18px;display: inline-block;border-radius: 3px;margin-left: 10px;cursor: pointer;font-size: 14px;float: left;}
#cancelBtn:hover,,#insertImgBtn:hover{background:#EBEFF8;}
#uploader .filelist div.file-panel {height: 30px;}
</style>
<script>
function getSmallImage(imgUrl){
	var yougou_href_url_reg = new RegExp('http://.+/pics/merchantpics/.+', 'i');
	if(yougou_href_url_reg.test(imgUrl)){
		return imgUrl.substring(0,imgUrl.lastIndexOf(".jpg"))+".png"+imgUrl.substring(imgUrl.lastIndexOf(".jpg")+4);
	}else{
		return imgUrl.substring(0,imgUrl.lastIndexOf(".jpg"))+"t"+imgUrl.substring(imgUrl.lastIndexOf(".jpg"));
	}
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/webupload.js?${style_v}"></script>
</head>
<body>
    <div id="uploader">
        <div class="queueList">
            <div id="dndArea" class="placeholder">
                <div id="filePickerBatch"></div>
                <p>或将图片拖到这里，单次最多可选100张</p>
            </div>
            <ul class="filelist" id="filelist" style="display:none;height:485px;overflow:auto;"></ul>
        </div>
        <div class="statusBar" style="display:none;">
            <div class="btns">
              <label style="float:left;display:inline-block;"><input type="checkbox" id="selectAll">&nbsp;全选</label> <a href="javascript:void(0)" id="insertImgBtn">插入图片</a><a href="javascript:void(0)" id="cancelBtn">清空已上传</a> <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
            </div>
        </div>
	</div>
</body>
<script>

$("#filelist").dragsort({dragSelector: "li", dragBetween: true, placeHolderTemplate: "<li class='placeHolder'></li>" });

$(function(){
	//全选
	$("#selectAll").live("click",function(){
		var lis = $(".filelist li");
		if($(this).attr("checked")){
			lis.find("span.check").removeClass("nocheck")
			lis.find("span.check").addClass("checked");
		}else{
			lis.find("span.check").removeClass("checked")
			lis.find("span.check").addClass("nocheck");
		}
	});
	
	 //清空已经上传
    $("#cancelBtn").live('click',function(){
    	var $lis = $(".filelist").find("li.state-complete");
    	$lis.each(function (index, item) {
    		$lis.eq(index).remove();
    		uploader.removeFile($lis.eq(index).attr("id"),true);
    	});
    });
    
    //插入图片
    $("#insertImgBtn").live('click',function(){
		var	urlStrs = "";
		var images = $(".filelist li.state-complete");
		images.each(function(index,item){
			var checkSpan = $(this).find("span.checked");
			if(checkSpan.length>0){
				checkSpan.removeClass("checked");
				checkSpan.addClass("nocheck");
	  			if (urlStrs == '') {
					urlStrs += $(this).find("p.imgWrap img").attr("realimg");
				} else {
					urlStrs += '&&&&&' + $(this).find("p.imgWrap img").attr("realimg");
				}
	  		}
		});
		if(urlStrs==""){
			ygdg.dialog.alert('请先上传到服务器,并选择要插入的图片');
			return;
		}
		onImgSelected.call(this, urlStrs);
    });
});
</script>
</html>
</#macro>