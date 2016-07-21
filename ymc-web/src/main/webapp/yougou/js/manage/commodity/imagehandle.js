/**
 * 上传框变化事件
 * @param {Object} inputFile 上传框对象
 */

/*
goodsAdd.imageUpload.inputFile_OnChange = function(inputFile, number) {
	if(CheckFile(inputFile)){
		//是否为首次变化
		var isFirstChange = parseInt($.trim($(inputFile).attr("isFirstChange")));
		if(!isFirstChange) {
			$(inputFile).attr("isFirstChange", 1);
			$(inputFile).parent().find("span").html("上传新图片");
			//为预览的图片添加操作
			goodsAdd.imageUpload.addOptToPreviewImg(inputFile);		
		}
		var width = 100;
		var height = 100;
		var previewDivId = inputFile.id + "_preview";
		//var msg = 
		//	'<div id="' + previewDivId + '" style="filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);width: ' + width + 'px; height: ' + height + 'px; margin: 0 auto;"></div>';
		var imgLiId = inputFile.id + "_li";
		var layer = $("#" + imgLiId + " .goods_img_layer");
		var img = layer.find("img").eq(0);
		//图片预览
		goodsAdd.imageUpload.imgPreview(inputFile, img, width, height);
		
		var loading = $("<span id='image_loading_'" + number + "' style='position:absolute;left:50%;top:50%;margin-left:-12px;margin-top:-8px;'><img style='position:relative;z-index:2;' src='" + goodsAdd.url.loadingImgUrl + "' width='16' height='16'/><span style='width:30px;height:30px;margin-top:-15px;margin-left:-15px;position:absolute;left:50%;top:50%;z-index:1;background:#ddd;border:1px solid #ccc;-moz-opacity:0.8;opacity: 0.8;' ></span></span>");
		$('#goods_img_layer_' + number).append(loading);
		//异步上传图片、禁用保存按钮
		image_hander++;
		isforbiddenButton('commodity_pic_save', true, null);
		//上传图片
		ajaxFileUpload({
	        id : inputFile.id,
	        url : '/img/upload.sc?no='+number,
	        callback : function(){
                image_hander--;
                var src = this.responseText;
                if(src!=null&&""!=src&&"null"!=src){
	                var obj = eval ("(" + src + ")");
	                console.info(obj)
	                if (obj.files.length>0) {
	                	$("#img_file_id_"+number).val(obj.files[0].id);
	                } else {
	                	$("#img_file_id_"+number).val("-1");
	                	goodsAdd.imageUpload.imgPreviewFail(inputFile, previewDivId, width, height);
	                	ygdg.dialog.alert("图片上传服务器失败,请重新上传！");
	                }
                } else {
                	$("#img_file_id_"+number).val("-1");
                	goodsAdd.imageUpload.imgPreviewFail(inputFile, previewDivId, width, height);
                	ygdg.dialog.alert("图片上传服务器失败,请检查！");
                }
                //图片处理完成、释放保存按钮 (is_execute判断再绑定事件时是否执行函数)
                is_execute = false;
                if (image_hander <= 0) {
                	isforbiddenButton('commodity_pic_save', false, function() {if (is_execute) savePicture();});
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
*/
/**
 * 是否禁用按钮
 * @param buttonId
 * @param isflag ：true禁用按钮  false显示按钮
 * @param fn : isflag=false时可用（按钮点击事件）
 */
function isforbiddenButton(buttonId, isflag, fn) {
	if (isflag) {
		$('#' + buttonId).attr('class', 'button dis');
		$('#' + buttonId).unbind('click');
		$('#' + buttonId).removeAttr('onclick');
	} else {
		$('#' + buttonId).attr('class', 'button');
		$('#' + buttonId).bind('click', fn);
	}
}

/**
 * 是否禁用按钮
 * @param buttonId
 * @param isflag ：true禁用按钮  false显示按钮
 * @param fn : isflag=false时可用（按钮点击事件）
 */
function isforbiddenButtonNew(buttonId, isflag, fn){
	if (isflag) {
		$('#' + buttonId).addClass('btn_gary');
		$('#' + buttonId).unbind('click');
		$('#' + buttonId).removeAttr('onclick');
	} else {
		$('#' + buttonId).removeClass('btn_gary');
		$('#' + buttonId).bind('click', fn);
	}
}

/**
 * 是否禁用悬浮按钮
 * @param buttonId
 * @param isflag ：true禁用按钮  false显示按钮
 * @param fn : isflag=false时可用（按钮点击事件）
 */
function isforbiddenButtonOfQuick(buttonId, isflag, fn){
	if (isflag) {
		$('#' + buttonId).addClass('cgary0');
		$('#' + buttonId).unbind('click');
		$('#' + buttonId).removeAttr('onclick');
	} else {
		$('#' + buttonId).removeClass('cgary0');
		$('#' + buttonId).bind('click', fn);
	}
}


/**
 * 图片校验
 * @param {Object} img 上传框对象
 * @param {Number} width 图片宽度
 * @param {Number} height 图片高度
 */
function CheckFile(img) {
	// 判断图片类型
	if (!/\.(jpg)$/g.test(img.value.toLowerCase())) {
		ygdg.dialog.alert("商品图片类型必须是jpg！");
		return false;
	}
	return true;
}

/**
 * 验证上传的图片(新增时用)
 */
goodsAdd.validate.validateCommodityImg = function() {
	//var $imgInputs = $(".detail_jq_file_btn");
	var $imgInputs = $("input[name='imgFileId']");
	var allNotNullMsg = "请上传图片！";
	var notNullMsg = "请上传第 {#num} 张商品图片";
	//var notJPGMsg = "第 {#num} 张商品图片，请上传.jpg（小写）为后缀的文件";
	var imgInupt = null;
	var curValue = null;
	var imgLength = goodsAdd.CommodityPicIndexer.numberOfWithCategories();
	
	//检测是否全部为空
	var isAllNull = true; 
	for (var i = 0, len = $imgInputs.length; i < len; i++) {
		imgInupt = $imgInputs[i];
		curValue = $.trim(imgInupt.value);
		if(curValue != "-1") {
			isAllNull = false;
			break;
		}
	}
	if(isAllNull) {
		goodsAdd.validate.isEmpty('pm', '', allNotNullMsg);
		return;
	}
	
	for (var i = 0, len = $imgInputs.length; i < len; i++) {
		imgInupt = $imgInputs[i];
		curValue = $.trim(imgInupt.value);
		if (i >= imgLength && curValue == '-1') {
			continue;
		}
		if (curValue == '-1') curValue = '';
		if(goodsAdd.validate.isEmpty('pm', curValue, formatString(notNullMsg, {num: i + 1}))) return;
		//if(goodsAdd.validate.isNotJPG('commodityImage', curValue, formatString(notJPGMsg, {num: i + 1}))) return;
	}
	if($imgInputs.eq(5).val()=="-1"&&$imgInputs.eq(6).val()!="-1"){
		goodsAdd.validate.errorList[goodsAdd.validate.errorList.length] = { errorFiled: "pm", errMsg: "角度图中间不能缺失" };
	}
	return true;
};

/**
 * 验证上传图片(修改时用)
 */
goodsAdd.validate.validateCommodityImgForUpdate = function() {
	//var $imgInputs = $(".detail_jq_file_btn");
	var $imgInputs = $("input[name='imgFileId']");
	var notNullMsg = "请上传第 {#num} 张商品图片";
	//var notJPGMsg = "第 {#num} 张商品图片，请上传.jpg（小写）为后缀的文件";
	var imgInupt = null;
	var curValue = null;
	var imgLength = goodsAdd.CommodityPicIndexer.numberOfWithCategories();
	
	for (var i = 0, len = $imgInputs.length; i < len; i++) {
		imgInupt = $imgInputs[i];
		curValue = $.trim(imgInupt.value);
		if (i >= imgLength && curValue == '-1') {
			continue;
		}
		if (curValue == '-1') curValue = '';
		// 验证为非瞬时状态的图片
		if ($(imgInupt).parent().parent().find('img[transient="false"]').size() != 1) {
			if(goodsAdd.validate.isEmpty('pm', curValue, formatString(notNullMsg, {num: i + 1}))) {
				return false;
			}
		}
		/*if(curValue.length != 0) {
			if(goodsAdd.validate.isNotJPG('commodityImage', curValue, formatString(notJPGMsg, {num: i + 1}))) {
				return false;
			}
		}*/
	}
	if($imgInputs.eq(5).val()=="-1"&&$imgInputs.eq(6).val()!="-1"){
		goodsAdd.validate.errorList[goodsAdd.validate.errorList.length] = { errorFiled: "pm", errMsg: "角度图中间不能缺失" };
	}
	return true;
};

/**
 * 图片预览
 * @param {Object} file 上传框对象
 * @param {String} imgDivId 图片预览层id
 * @param {Number} width 图片宽度
 * @param {Number} height 图片高度
 */
goodsAdd.imageUpload.imgPreview = function(file, img, width, height) {
	if(file["files"] && file["files"][0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.attr("src",evt.target.result);
			img.attr("transient", 'true');
			//layer.html('<img src="' + evt.target.result + '" + width="' + width + '" height="' + height + '" />').attr('transient', 'true');
		};
		reader.readAsDataURL(file.files[0]);
	} else {
		file.select();
		//var preview = img[0];
		if(typeof(document.selection) != "undefined") {
			var path = document.selection.createRange().text;
			//alert(path);
			img.attr("src",path);
			//preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
			//preview.setAttribute('transient', 'true');
		}
	}
};

/**
 * 图片预览
 * @param {Object} file 上传框对象
 * @param {String} imgDivId 图片预览层id
 * @param {Number} width 图片宽度
 * @param {Number} height 图片高度
 */
var goodsAddImgPreview = function(file, number, width, height) {
	if(file["files"] && file["files"][0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			$("#pic_" + number).html("<img src='"+evt.target.result+"' transient='true'></img>");
		};
		reader.readAsDataURL(file.files[0]);
	} else {
		file.select();
		//var preview = img[0];
		if(typeof(document.selection) != "undefined") {
			var path = document.selection.createRange().text;
			//alert(path);
			$("#pic_" + number).html("<img src='"+path+"'></img>");
		}
	}
};

/**
 * 图片预览失败
 * @param {Object} file 上传框对象
 * @param {String} imgDivId 图片预览层id
 * @param {Number} width 图片宽度
 * @param {Number} height 图片高度
 */
goodsAdd.imageUpload.imgPreviewFail = function(file, img, width, height) {
//	var imgDiv = $("#" + imgDivId);
	if(file["files"] && file["files"][0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.attr("src","/yougou/images/unknow_img.png");
			img.attr("transient", 'true');
		};
		reader.readAsDataURL(file.files[0]);
	} else {
		file.select();
		//var preview = document.getElementById(imgDivId);
		if(typeof(document.selection) != "undefined") {
			//var path = document.selection.createRange().text;
			//preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
			//preview.setAttribute('transient', 'true');
			img.attr("src","/yougou/images/unknow_img.png");
		}
	}
};

/**
 * 商品图片索引器(Modifier by: yang.mq)
 */
goodsAdd.CommodityPicIndexer = {
		DEFAULT_NUMBER:5,
		regexps: [ {
			key: /运动_运动鞋_.*/g,
			val: 6
		}],
		numberOfWithCategories: function() {
			var catNames = $('#category1').next().text().trim() + '_' + $('#category2').next().text().trim() + '_' + $('#category3').next().text().trim();
			for (var i = this.regexps.length - 1; i >= 0; i--) {
				if (catNames.match(this.regexps[i].key) != null) {
					return this.regexps[i].val;
				}
			}
			return this.DEFAULT_NUMBER;
		}
};


$(function() {
	//初始化文件上传按钮
	$(".detail_jq_file_btn").jqFileBtn({text: "上传图片", width:"70"});
});

/*******************************************************************************************/
/***********************            上传组件         ****************************************/
/*******************************************************************************************/

var ajaxFileUpload = function(opts){
    return new ajaxFileUpload.prototype.init(opts);
};
ajaxFileUpload.prototype = {
    init:function(opts){
        var set = this.extend({
            url:'/upload',
            id:'fileId',
            callback:function(){}
        },opts || {});
        var _this = this;
        var id = +new Date();
        var form = this.createForm(id),frame = this.createIframe(id,set.url);
        var oldFile = document.getElementById(set.id);
        var newFile = oldFile.cloneNode(true);
        var fileId = 'ajaxFileUploadFile'+id;
        oldFile.setAttribute('id',fileId);
        oldFile.parentNode.insertBefore(newFile,oldFile);
        form.appendChild(oldFile);//注意浏览器安全问题，要将原文件域放到创建的form里提交
        form.setAttribute('target',frame.id);//将form的target设置为iframe,这样提交后返回的内容就在iframe里
        form.setAttribute('action',set.url);
        setTimeout(function(){
            form.submit();
            if(frame.attachEvent){
                frame.attachEvent('onload',function(){_this.uploadCallback(id,set.callback);});
            }else{
                frame.onload = function(){_this.uploadCallback(id,set.callback);};
            }
        },100);
    },
    /*
        创建iframe，ie7和6比较蛋疼，得像下面那样创建，否则会跳转
    */
    createIframe:function(id,url){
        var frameId = 'ajaxFileUploadFrame'+id,iFrame;
        var IE = /msie ((\d+\.)+\d+)/i.test(navigator.userAgent) ? (document.documentMode ||  RegExp['\x241']) : false,
        url = url || 'javascript:false';
        if(IE && IE < 8){
            iFrame = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
            iFrame.src = url;
        }else{
            iFrame = document.createElement('iframe');
            this.attr(iFrame,{
                'id':frameId,
                'name':frameId,
                'src':url
            });
        };
        iFrame.style.cssText = 'position:absolute; top:-9999px; left:-9999px';
        return document.body.appendChild(iFrame);
    },
    /*
        创建form
    */
    createForm:function(id){
        var formId = 'ajaxFileUploadForm'+id;
        var form = document.createElement('form');
        this.attr(form,{
            'action':'',
            'method':'POST',
            'name':formId,
            'id':formId,
            'enctype':'multipart/form-data',
            'encoding':'multipart/form-data'
        });
        form.style.cssText = 'position:absolute; top:-9999px; left:-9999px';
        return document.body.appendChild(form);  
    },
    /*
        获取iframe内容，执行回调函数，并移除生成的iframe和form
    */
    uploadCallback:function(id,callback){
        var frame = document.getElementById('ajaxFileUploadFrame'+id),form = document.getElementById('ajaxFileUploadForm'+id);data = {};
        var db = document.body;
        try{
            if(frame.contentWindow){
                data.responseText = frame.contentWindow.document.body ? frame.contentWindow.document.body.innerHTML : null;
                data.responseXML = frame.contentWindow.document.XMLDocument ? frame.contentWindow.document.XMLDocument : frame.contentWindow.document;
            }else{
                data.responseText = frame.contentDocument.document.body ? frame.contentDocument.document.body.innerHTML : null;
                data.responseXML = frame.contentDocument.document.XMLDocument ? frame.contentDocument.document.XMLDocument : frame.contentDocument.document;
            }
        }catch(e){};
        callback && callback.call(data);
        setTimeout(function(){
            db.removeChild(frame);
            db.removeChild(form);
        },100);
    },
    attr:function(el,attrs){
        for(var prop in attrs) el.setAttribute(prop,attrs[prop]);
        return el;
    },
    extend:function(target,source){
        for(var prop in source) target[prop] = source[prop];
        return target;
    }
};
ajaxFileUpload.prototype.init.prototype = ajaxFileUpload.prototype;