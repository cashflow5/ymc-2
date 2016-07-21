goodsAdd.commodity.imgFile.imgPathList = [];
$(function() {
	//初始化图片信息
	goodsAdd.commodity.initInfo();
});

/**
 * 验证表单
 * @param {Array} formData 表单域数组
 * @param {Object} jqForm jq form对象
 * @param {Object} options 选项
 */
goodsAdd.validate.validateForm = function(formData, jqForm, options) {
	var validateFunList = goodsAdd.validate.validateFunList;
	for (var i = 0, len = validateFunList.length; i < len; i++) {
		eval('goodsAdd.validate[validateFunList[i]]()');
	}
	
	var errorList = goodsAdd.validate.errorList;
	if (errorList.length == 0) return true;
	
	goodsAdd.validate.batchShowErrorList();
	
	return false;
};

/**
 * 一次性展示错误列表
 */
goodsAdd.validate.batchShowErrorList = function() {
	var errorList = goodsAdd.validate.errorList;
	var error = null;
	var regexp = new RegExp('(_stock|_thirdPartyCode|_weightStr)$');
	//processing error List
	for ( var int = 0; int < errorList.length; int++) {
		error = errorList[int];
		if (goodsAdd.validate.endsWith(error.errorFiled, regexp)) {
			var filed = error.errorFiled.split("_");
			//定位 goods_filed[1]_filed[0] error block [filed[1]_filed[0]_error_tip]			
			var obj = $('#goods_' + filed[1] + '_' + filed[0]);
			obj.addClass("error");
			obj.attr('title', error.errMsg);
			obj.blur(function() {//绑定焦点事件
				var value = $(this).val();
				if(!isEmpty(value)) {
					$(this).removeClass('error');
				}
			});
			//obj.focus();
		} else {
			var error_html = '<img src="/yougou/images/error.gif" class="goods_error_image" />' + error.errMsg;
			$('#' + error.errorFiled + '_error_tip').show().html(error_html);
			if (goodsAdd.validate.endsWith(error.errorFiled, new RegExp('(supplierCode|styleNo|commodityName|salePrice|publicPrice|specName|prodDesc)'))) {
				$('#goods_' + error.errorFiled).focus();
			} else if (error.errorFiled == 'brandNo') {
				$('#brandNo').focus();
			} else if (error.errorFiled == 'category') {
				$('#category1').focus();
			}
		}
	}
	//clear Array
	errorList.length = 0;
};

function savePicture() {
	//角度图
	var images =  $("input[name='imgFileId']");
	var imgDesc = $(".desc-image");
	var imgFiles = []; //角度图
	var imgDescs = []; //描述图
	for(var i=0,length=images.length;i<length;i++){
		if(images.eq(i).val()!="-1"){
			imgFiles.push(images.eq(i).val());
		}
	}
	for(var i=0,length=imgDesc.length;i<length;i++){
		if(imgDesc.eq(i).attr("uploadSuccess")){
			imgDescs.push(imgDesc.eq(i).attr("realImg"));
		}
	}
	if(imgFiles.length==0){
		ygdg.dialog.alert("角度图不能为空");
		return;
	}
	if(imgDescs.length==0){
		ygdg.dialog.alert("描述图不能为空");
		return;
	}
	var latestNumber = goodsAdd.latestNumber;
	if(imgFiles.length<latestNumber){
		ygdg.dialog.confirm("角度图少于所需张数，你确定要提交吗？", function() {
			savePictureDo(imgFiles,imgDescs);
		});
	}else{
		savePictureDo(imgFiles,imgDescs);
	}
}

function savePictureDo(imgFiles,imgDescs){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			"imgFiles":imgFiles.join(","),
			"descImages":imgDescs.join(","),
			"commodityNo":goodsAdd.commodityNo
		},
		url : basePath+"/commodity/uploadImage4Commodity.sc",// 请求的action路径
		success : function(data) {
			if(data.result=="success"){
				ygdg.dialog.alert("保存成功");
				parent.closeDialog();
			}else{
				ygdg.dialog.alert("保存失败");
			}
		}
	});
}

/**
 * 为预览的图片添加操作
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.addOptToPreviewImg = function(inputFile) {
	$(inputFile).parent().parent().bind("mouseover",
			function() {
				goodsAdd.imageUpload.previewImg_OnMouseOver(inputFile);
			});
	$(inputFile).parent().parent().bind("mouseout",
			function() {
				goodsAdd.imageUpload.previewImg_OnMouseOut(inputFile);
			});
};

/**
 * 预览图片 mouseover 事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.previewImg_OnMouseOver = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).show();
};

/**
 * 预览图片 mouseout 事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.previewImg_OnMouseOut = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).hide();
};

/**
 * 删除操作 点击事件
 * @param {Object} self 被点击的操作对象
 */
goodsAdd.imageUpload.previewOptDelete_OnClick = function(self, number) {
	$(self).parent().parent().unbind("mouseover");
	$(self).parent().parent().unbind("mouseout");
	$(self).parent().hide();
	
	var sortNo = $.trim($(self).attr("sortNo"));
	var src = goodsAdd.commodity.imgFile.imgPathList[sortNo - 1];
	var new_upload = false;
	if (src == null || src == 'undefined' || src.length == 0) {
		src = basePath + '/yougou/images/unknow_img.png';
		new_upload = true;
	} 
	//
	//把预览区替换成原图片
	var msg = formatString(
		'<img src="{#src}" class="goods_img_image" width="100" height="100" />',
		{
			src: src
		});
	$(self).parent().parent().find(".goods_img_layer").html(msg);
	
	//原上传input
	var originInputFile = $("#goods_img_file_" + sortNo);
	var inputId = $.trim(originInputFile.attr("id"));
	var inputName = $.trim(originInputFile.attr("name"));
	var newInputFileHtml = formatString(
		'<input type="file" id="{#inputId}" sortNo="{#sortNo}" name="{#inputName}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this, {#number})" />',
		{
			inputId: inputId,
			inputName: inputName,
			sortNo: sortNo,
			number: number
		});
	//移除原上传input
	$(originInputFile).parent().remove();
	$("#img_file_id_"+number).val(new_upload ? "-1" : "0");
	//加入新的上传input
	$(self).parent().parent().append(newInputFileHtml);
	//渲染上传框
	$("#" + inputId).jqFileBtn({text: "上传图片"});
};
//描述图数组
var descImageArry = [];
var uploaderSingle = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,

    // swf文件路径
    swf: basePath+'/webuploader/Uploader.swf',

    // 文件接收服务端。
    server: basePath+"/img/upload.sc",

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker',

    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'jpg,png',
        mimeTypes: 'image/*'
    },
    formData:{
       no:1,
    },
    duplicate:1,   //不去重
    //fileSingleSizeLimit:1024*5
    compress:false  //压缩
});

uploaderSingle.on( 'filesQueued', function( files ) {
	var images = $(".goods_img_layer");
	if(files.length>7){
		for(var i=0,length=files.length;i<length;i++){ 
			// 创建缩略图
			var file = files[i];
			uploader.removeFile( file ); //将文件移除，否则会继续上传
		}
		ygdg.dialog.alert("最多只能选择7张图片");
		return;
	}
	//初始化所有图片
	//var img = images.find("img");
	//给上传图片加上ID
	//images.find("img").attr("src","/yougou/images/unknow_img.png");
	//$("input[name='imgFileId']").val("-1");
	for(var i=0,length=files.length;i<length;i++){ 
	    //img[i].attr("src","/yougou/images/unknow_img.png");
		var file = files[i];
		var img = images.find("img").eq(i);
		//console.info(img);
		img.addClass(file.id);
		img.parent().addClass("fileSelect");
	}
});

uploaderSingle.on( 'uploadProgress', function( file, percentage ) {
    var $parent = $("."+file.id).eq(0).parent();
    var $percent = $( '#'+file.id +'_percent');
    if(!$percent.length ){
         $percent = $('<div class="percent" id="'+file.id+'_percent"></div>').appendTo($parent);
    }
    $percent.css( 'width', percentage * 100 + '%' ); 
    
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploaderSingle.on( 'uploadSuccess', function( file,response) {
	if(response.success==true){
		var re=/.jpg$/;
		$("."+file.id).eq(0).parent().siblings("input[type='hidden']").val(response.message);
		var imgUrl = response.message.replace(re,".png");
		$("."+file.id).eq(0).attr("src",imgUrl);
		$( '#'+file.id +'_percent').remove();
	}else{
    	if (response.message == '1') ygdg.dialog.alert(file.name+',request请求参数no为空值,请检查!');
    	else if (response.message == '2') ygdg.dialog.alert(file.name+',获取登录会话信息失败,请尝试重新登录操作!');
    	else if (response.message == '3') ygdg.dialog.alert(file.name+',商品图片大小超过了 500 KB');
    	else if (response.message == '4') ygdg.dialog.alert(file.name+',商品图片分辨率不符合  800-1000px * 800-1000px的规格');
    	else if (response.message == '5') ygdg.dialog.alert(file.name+',图片校验异常');
    	else if (response.message == '6') ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
    	else if (response.message == '7') ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
    	else ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如再有问题请联系管理员！");
    	$("."+file.id).eq(0).attr("src","/yougou/images/unknow_img.png");
    	$("."+file.id).eq(0).parent().siblings("input[type='hidden']").val("-1");
    	$( '#'+file.id +'_percent').remove();
	}
});

// 文件上传失败，显示上传出错。
uploaderSingle.on( 'uploadError', function( file ) {
    ygdg.dialog.alert("上传失败");
    $( '#'+file.id +'_percent').remove();
});

//初始化信息

goodsAdd.commodity.initInfo = function() {
	$.ajax({
		url: goodsAdd.url.getCommodityByNo,
		cache: false,
		type: "GET",
		dataType: "json",
		success: function(data) {
			if(data == null || typeof(data) != "object" || data.isSuccess == null ||
					typeof(data.isSuccess) == "undefined") {
				return;
			}
			if(data.isSuccess != "true") {
				ygdg.dialog.alert(data.errorMsg);
				return;
			}
			if(!data.commodity) return;
			var commodity = data.commodity;
			//图片处理消息缓存
			var imageMessage = data.imageCache;
			//填充角度图
			goodsAdd.commodity.imgFile.setImg(commodity.pictures, imageMessage);
			goodsAdd.commodity.imgFile.setDescImg(commodity.commodityDesc);
		},
		error: function() {
			alert("网络异常，请刷新后重试!");
			ygdg.dialog({id: "loadCommodityDialog"}).close();
		}
	});
};
//初始化图片
goodsAdd.commodity.imgFile.setImg = function(pictures, imageMessage) {
	//图片缓存
	var imgFileIds = [];
	if (null != imageMessage) {
		imgFileIds = imageMessage.imgFileId;
	}
	var d = new Date();
	//图片域名
	var picDomain = goodsAdd.commodityPreviewDomain;
	//img标签集合
	var $imgs = $("#goods_img_file_layer .goods_img_image");
	var pic = null;
	var imgObj = null;
	for (var i = 0, len = goodsAdd.add_or_update_commodity_image_count; i < len; i++) {
		imgObj = $imgs[i];
		var imgPattern = "_" + goodsAdd.commodity.imgFile.formatNum(i + 1) + 
			IMG_NAME_SIZE_1000_1000_SUFFIX + IMG_NAME_EXT_TYPE;
		
		var imgPattern1 = "_" + goodsAdd.commodity.imgFile.formatNum(i + 1) + "_l";
		
		for (var j = 0, len1 = pictures.length; j < len1; j++) {
			pic = pictures[j];
			if(pic.picName.indexOf(imgPattern) != -1) {
				$('#img_file_id_' + (i + 1)).val('0');//设置0为已经有图片
				var imgUrl = (picDomain + pic.picPath + pic.picName).replace('_l.jpg','_ms.jpg')+"?flag="+d.getTime();
				//添加到图片路径数组中并标记状态为非瞬时
				goodsAdd.commodity.imgFile.imgPathList[i] = imgUrl;
				$(imgObj).attr("src", imgUrl).attr('transient', 'false');//显示小图
			}
		}
		
		//先尝试从缓存取图片
		if(imgFileIds!=null){
			for (var k = 0, length = imgFileIds.length; k < length; k++) {
				var imgId = imgFileIds[k];
				if (imgId == '0' || imgId == '-1') continue;
				if (imgId.indexOf(imgPattern1) != -1) {
					$('#img_file_id_' + (i + 1)).val(imgId);//设置临时图片Id
					var imgUrl = picDomain + imageMessage.ftpRelativePath + imgId + ".jpg";
					$(imgObj).attr("src", imgUrl).attr('transient', 'false');//显示小图;
				}
			}
		}
		
	}
};

/**
 * 上传框变化事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.inputFile_OnChange = function(inputFile,number) {
	if(CheckFile(inputFile)){
		//是否为首次变化
		var isFirstChange = parseInt($.trim($(inputFile).attr("isFirstChange")));
		var browserInfo = getBrowserInfo()+"";
		if(!isFirstChange) {
			$(inputFile).attr("isFirstChange", 1);
			$(inputFile).parent().find("span").html("上传新图片");
			//为预览的图片添加操作
			if(browserInfo.lastIndexOf("msie")==-1){
				goodsAdd.imageUpload.addOptToPreviewImg(inputFile);		
			}
		}
		
		var width = 100;
		var height = 100;
		var previewDivId = inputFile.id + "_preview";
		var imgLiId = inputFile.id + "_li";
		var layer = $("#" + imgLiId + " .goods_img_layer");
		var img = layer.find("img").eq(0);
		//图片预览
		if(browserInfo.lastIndexOf("msie")==-1){
			goodsAdd.imageUpload.imgPreview(inputFile, img, width, height);
		}
		var loading = '<span id="image_loading_' + number + '" style="position:absolute;left:50%;top:50%;margin-left:-12px;margin-top:-8px;"><img style="position:relative;z-index:2;"  src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /><span style="width:30px;height:30px;margin-top:-15px;margin-left:-15px;position:absolute;left:50%;top:50%;z-index:1;background:#ddd;border:1px solid #ccc;-moz-opacity:0.8;opacity: 0.8;" ></span></span>';
		$('#goods_img_layer_' + number).append(loading);
		//异步上传图片、禁用保存按钮
		image_hander++;
		isforbiddenButton('commodity_save', true, null);
		isforbiddenButton('commodity_audit', true, null);
		//上传图片
		ajaxFileUpload({
	        id:inputFile.id,
	        url:'/img/upload.sc?no='+number,
	        callback:function(){
	                image_hander--;
	                var src = this.responseText;
	                if(src!=null&&""!=src&&"null"!=src){
	                	src=src.replace(/<pre>/ig,"");
						src=src.replace(/<\/pre>/ig,"");
		                var obj = eval ("(" + src + ")");
		                if(obj.success==true){
		                	$("#img_file_id_"+number).val(obj.message);
		                	var browserInfo = getBrowserInfo()+"";
		                	if(browserInfo.lastIndexOf("msie")!=-1){
		                	 	$("#goods_img_layer_"+number).find("img").attr("src",getThumbnail(obj.message));
		                	}
		                }else{
		                	$("#img_file_id_"+number).val("-1");
		                	$("#img_file_id_"+number).siblings("div[class='goods_img_layer']").children("img").attr("src","/yougou/images/unknow_img.png");
		                	goodsAdd.imageUpload.imgPreviewFail(inputFile, previewDivId, width, height);
		                	if (obj.message == '1') ygdg.dialog.alert('request请求参数no为空值,请检查!');
		                	else if (obj.message == '2') ygdg.dialog.alert('获取登录会话信息失败,请尝试重新登录操作!');
		                	else if (obj.message == '3') ygdg.dialog.alert('商品图片大小超过了 500 KB');
		                	else if (obj.message == '4') ygdg.dialog.alert('商品图片分辨率不符合  800-1000px * 800-1000px的规格');
		                	else if (obj.message == '5') ygdg.dialog.alert('图片校验异常');
		                	else if (obj.message == '6') ygdg.dialog.alert('上传图片失败, 请重新再试！');
		                	else if (obj.message == '7') ygdg.dialog.alert('上传图片失败,获取不到图像对象，请重试!');
		                	else ygdg.dialog.alert("图片上传失败，请重新上传！");
		                }
	                }else{
	                	$("#img_file_id_"+number).val("-1");
	                	goodsAdd.imageUpload.imgPreviewFail(inputFile, img, width, height);
	                	ygdg.dialog.alert("图片上传，服务器返回数据格式异常,请联系管理员！");
	                }
	                //图片处理完成、释放保存按钮 (is_execute判断再绑定事件时是否执行函数)
	                is_execute = false;
	                if (image_hander <= 0) {
	                	isforbiddenButton('commodity_save', false, function() {if (is_execute) goodsAdd.submit.submitForm(false);});
	                	isforbiddenButton('commodity_audit', false, function() {if (is_execute) goodsAdd.submit.submitForm(true);});
	                }
	                is_execute = true;
	                //移除loading
	                $('#image_loading_' + number).remove();
	        }
	    }); 
	}  
	//error_tip hide
	$('#commodityImage_error_tip').hide();
};

function getBrowserInfo(){
	var agent = navigator.userAgent.toLowerCase() ;
	var regStr_ie = /msie [\d.]+;/gi ;
	var regStr_ff = /firefox\/[\d.]+/gi
	var regStr_chrome = /chrome\/[\d.]+/gi ;
	var regStr_saf = /safari\/[\d.]+/gi ;
	//IE
	if(agent.indexOf("msie") > 0){
		return agent.match(regStr_ie) ;
	}
	
	//firefox
	if(agent.indexOf("firefox") > 0){
		return agent.match(regStr_ff) ;
	}
	
	//Chrome
	if(agent.indexOf("chrome") > 0){
		return agent.match(regStr_chrome) ;
	}
	
	//Safari
	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0){
		return agent.match(regStr_saf) ;
	}
}

function getThumbnail(src){
	var index = src.lastIndexOf(".");
	var srcName = src;
	if(index!=-1){
		srcName = src.substring(0,index+1)+"png";
	}
	return srcName;
}

goodsAdd.commodity.imgFile.formatNum = function(desc){
	if(desc==null){
		return;
	}
	var imageArray = getimgsrc(desc);
	
}

goodsAdd.commodity.imgFile.formatNum = function(index) {
	var result = "";
	if(index < 10) {
		result = "0" + index;
	} else {
		result = String(index);
	}
	return result;
};
//初始化描述图
goodsAdd.commodity.imgFile.setDescImg = function(commodityDesc){
	if(commodityDesc==null){
		return;
	}else{
		 var descImageArray = getimgsrc(commodityDesc),
		 $wrap = $('#uploader'),
		 $placeHolder = $wrap.find('.placeholder' ),
		 $queue = $(".filelist").eq(0),
		 $statusBar = $wrap.find( '.statusBar' );
	     if(descImageArray.length>0){
	    	 $placeHolder.addClass( 'element-invisible' );
			 $statusBar.removeClass( 'element-invisible' );
			 $statusBar.show();
			 for(var i=0,length=descImageArray.length;i<length;i++){
				 var imgUrl = descImageArray[i];
				 var smallImg = getSmallImage(imgUrl);
				 new Item(smallImg,i,imgUrl).appendTo($queue);
			 }
	     }
        uploader.refresh();
	}
	
}

function getSmallImage(imgUrl){
	var yougou_href_url_reg = new RegExp('http://.+/pics/merchantpics/.+', 'i');
	if(yougou_href_url_reg.test(imgUrl)){
		return imgUrl.substring(0,imgUrl.lastIndexOf(".jpg"))+".png"+imgUrl.substring(imgUrl.lastIndexOf(".jpg")+4);
	}else{
		return imgUrl.substring(0,imgUrl.lastIndexOf(".jpg"))+"t"+imgUrl.substring(imgUrl.lastIndexOf(".jpg"));
	}
}

function Item(imgurl,num,realImg){
	var $li = $( '<li num="'+num+'">' +
            '<p class="title"></p>' +
            '<p class="imgWrap"><img src="'+imgurl+'" realImg="'+realImg+'" class="desc-image" uploadsuccess="true"></p>'+
            '<span class="success"></span>' +
            '</li>' ),
        $btns = $('<div class="file-panel">' +
            '<span class="cancel" title="删除">删除</span></div>').appendTo( $li ),
         $wrap = $li.find( 'p.imgWrap');
            $li.on( 'mouseenter', function() {
            	var uploadType = $(this).find(".success");
            	if(uploadType.length>0){
            		$btns.find(".oper_lf").show();
            		$btns.find(".oper_rt").show();
            	}else{
            		$btns.find(".oper_lf").hide();
            		$btns.find(".oper_rt").hide();
            	}
            	//找到最后一个已经上传的图片
            	var lis = $(".filelist").find("li");
            	for(var i=0,_len=lis.length;i<_len;i++){
            		var uploadType = lis.eq(i).find(".success");
            		if(uploadType.length<=0){//找到第一个没上传的图片
            			if(i-1>=0){
            				lis.eq(i-1).find(".file-panel").eq(0).find(".oper_rt").hide();
            			}
            			break;
            		}
            	}
                $btns.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index();
                var num = parseInt($li.attr("num"));
	            switch ( index ) {
	                case 0:
	                	$li.remove();
	                	if($(".filelist").find("li").length==0){
	                		 var $wraptemp = $('#uploader'),
	                		 $placeHolderTemp = $wraptemp.find('.placeholder' ),
	                		 $statusBarTemp = $wraptemp.find( '.statusBar' );
	                		 $placeHolderTemp.removeClass( 'element-invisible' );
	                		 $statusBarTemp.addClass( 'element-invisible' );
	                		 $statusBarTemp.hide();
	                		 uploader.refresh();
	                	}
	                    return;
	                case 1:  //左移
	                    if(num==0){
	                    	return;
	                    }
	                    var curImg = $wrap.find("img").eq(0);
	                    var curImgSrc = curImg.attr("src");
	                    var curRealImgSrc = curImg.attr("realImg");
	                    var preImg = $(".filelist").find("li").eq(num-1).find( 'p.imgWrap').find("img").eq(0);
	                    curImg.attr("src",preImg.attr("src"));
	                    curImg.attr("realImg",preImg.attr("realImg"));
	                    preImg.attr("src",curImgSrc);
	                    preImg.attr("realImg",curRealImgSrc);
	                    break;
	                case 2:  //右移
	                	if(num==($(".filelist").find("li").length-1)){
	                    	return;
	                    }
	                    var curImg = $wrap.find("img").eq(0);
	                    var curImgSrc = curImg.attr("src");
	                    var curRealImgSrc = curImg.attr("realImg");
	                    var nextImg = $(".filelist").find("li").eq(num+1).find( 'p.imgWrap').find("img").eq(0);
	                    curImg.attr("src",nextImg.attr("src"));
	                    curImg.attr("realImg",nextImg.attr("realImg"));
	                    nextImg.attr("src",curImgSrc);
	                    nextImg.attr("realImg",curRealImgSrc);
	                    break;
	            }
            });
     return $li;
}

function getimgsrc(htmlstr){
	var reg=/<img.+?src=('|")?([^'"]+)('|")?(?:\s+|>)/gim;
	var arr = []; 
	while(tem=reg.exec(htmlstr)){ 
		arr.push(tem[2]); 
	}
	return arr;
}
