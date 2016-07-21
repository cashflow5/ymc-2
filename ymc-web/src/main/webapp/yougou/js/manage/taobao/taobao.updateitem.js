updateTaobaoItem.properties = {};
updateTaobaoItem.size = {};
updateTaobaoItem.prodInfo = {};
updateTaobaoItem.prodSpec = {};
updateTaobaoItem.imgPathList = [];
updateTaobaoItem.prodSpec.options = {
		resizeType: 0,
		allowPreviewEmoticons: false,
		allowImageUpload: false,
		allowFlashUpload: false,
		allowMediaUpload: false,
		allowFileUpload: false,
		newlineTag: 'br',
		htmlTags:{
	        font : ['color', 'size', 'face', '.background-color'],
	        span : ['style'],
	        div : ['class', 'align', 'style'],
	        table: ['class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'style'],
	        'td,th': ['class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor', 'style'],
	        a : ['class', 'href', 'target', 'name', 'style'],
	        embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality',
	        'style', 'align', 'allowscriptaccess', '/'],
	        img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'style', '/','usemap'],
	        hr : ['class', '/'],
	        br : ['/'],
	        'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : ['align', 'style'],
	        'tbody,tr,strong,b,sub,sup,em,i,u,strike' : [],
			'map': ['id','name'],
			'area':['alt','coords','href','nohref','shape','target']
		},
		items: [
			'source', '|', 
			'undo', 'redo', '|', 
			'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 
			'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
			'formatblock', 'fontname', 'fontsize', '|', 
			'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
			'table', 'hr', 'anchor', 'link', 'unlink'
		]
	}; 
	/**商品描述编辑器*/
updateTaobaoItem.prodSpec.editor;
var isflag = true;
KindEditor.ready(function(K) {
	updateTaobaoItem.prodSpec.editor = K.create('#goods_prodDesc', updateTaobaoItem.prodSpec.options);
});
$(function(){
	//方法加载顺序不要调整  先绑定三级分类change事件 然后初始化当前商品对应的分类，在绑定 品牌 一级分类 二级分类 change事件
	updateTaobaoItem.queryBrandCat($("#brandNo").find("option:selected").attr("brandId"));
	updateTaobaoItem.initCat();
	$("#brandNo").change(function() {
		updateTaobaoItem.queryBrandCat($(this).find("option:selected").attr("brandId"));
		updateTaobaoItem.reinitializeOption("0","sel1");
	});
	$("#sel1").change(function() {
		updateTaobaoItem.reinitializeOption($(this).find("option:selected").attr("structname"),"sel2");
	});
	$("#sel2").change(function() {
		updateTaobaoItem.reinitializeOption($(this).find("option:selected").attr("structname"),"sel3");
	});
	//初始化文件上传按钮
	$(".detail_jq_file_btn").jqFileBtn({text: "上传图片"});
	
	//设置角度图
	updateTaobaoItem.setImagesInfo();
	
	$(".oper_lf").live("click",function(){
        var my_img = $(this).parent().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().children(".goods_img_layer").children("div").children("img"),my_src = my_img.attr("src"),
            pre_img = $(this).parent().prev().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().prev().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().prev().children(".goods_img_layer").children("div").children("img"),pre_src = pre_img.attr("src");
        my_img.attr("src",pre_src);
        pre_img.attr("src",my_src);
        
        if(pre_src){
            var srcInput=$(this).parent().children("input[type='hidden']").val();
            var preInput=$(this).parent().prev().children("input[type='hidden']").val();
            if(srcInput=='0'){
            	srcInput=my_src.replace("_ms.jpg","_l.jpg");
            }
        	if(preInput=='0'){
            	preInput=pre_src.replace("_ms.jpg","_l.jpg");
            }
            $(this).parent().children("input[type='hidden']").val(preInput);
            $(this).parent().prev().children("input[type='hidden']").val(srcInput);
        }
    });
    $(".oper_rt").live("click",function(){
        var my_img = $(this).parent().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().children(".goods_img_layer").children("div").children("img"),my_src = my_img.attr("src"),
            next_img = $(this).parent().next().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().next().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().next().children(".goods_img_layer").children("div").children("img"),next_src = next_img.attr("src");
        my_img.attr("src",next_src);
        next_img.attr("src",my_src);
        
        if(next_src){
            var srcInput=$(this).parent().children("input[type='hidden']").val();
            var nextInput=$(this).parent().next().children("input[type='hidden']").val();
            if(srcInput=='0'){
            	srcInput=my_src.replace("_ms.jpg","_l.jpg");
            }
        	if(nextInput=='0'){
            	nextInput=next_src.replace("_ms.jpg","_l.jpg");
            }
            $(this).parent().children("input[type='hidden']").val(nextInput);
            $(this).parent().next().children("input[type='hidden']").val(srcInput);
        }
    });
     $(".goods_img_file_li").hover(function(){
            $(this).addClass("curr");
        },function(){
            $(this).removeClass("curr");
        }
    )
    //输入框，离开焦点，清除错误提示
	$("input[type='text']").live("blur",function(){
		if($(this).val()!=""){
			$(this).removeClass("error");
			$(this).parent().find("span.goods_error_tip").hide();
		}
	})
	$(".goods_stock_txt").live("blur",function(){
		$(this).removeClass("error");
	})
	
	
	$("#sel3").change(function() {
		if($(this).val()!=""){
			updateTaobaoItem.loadProps($("#sel3").val());
		}
	});
	//加载商品信息
	updateTaobaoItem.loadProps($("#sel3").val(),true);
});

//设置角度图
updateTaobaoItem.setImagesInfo = function(){
		pictures = updateTaobaoItem.images;
		//img标签集合
		var $imgs = $("#goods_img_file_layer .goods_img_image");
		var pic = null;
		var imgObj = null;
		for (var i = 0, len = pictures.length; i < len; i++) {
			imgObj = $imgs.eq(i);
			pic = pictures[i];
			if(pic.yougouUrl!=""&&pic.yougouUrlThumbnail!=""){
				imgObj.attr("src",pic.yougouUrlThumbnail);
				$('#img_file_id_' + (i + 1)).val(pictures[i].yougouUrl);//设置0为已经有图片
				updateTaobaoItem.imgPathList[i] = pic.yougouUrl;
			}
		}
}

//初始化分类
updateTaobaoItem.initCat = function(){
	
	//通过三级分类逆推一级二级分类
	
	//三级分类parentId;
	var parentId_3;
	for(var i=0,length=updateTaobaoItem.catList.length;i<length;i++){
		var tree = updateTaobaoItem.catList[i];
		//console.info(tree.catNo);
		if(updateTaobaoItem.yougouCatNo==tree.catNo){
			parentId_3 = tree.parentId;
			break;
		}
	}
	//遍历三级分类
	var parentId_2;
	for(var i=0,length=updateTaobaoItem.catList.length;i<length;i++){
		var tree = updateTaobaoItem.catList[i];
		if(parentId_3==tree.parentId){
			if(updateTaobaoItem.yougouCatNo==tree.catNo){
				$("<option value='"+tree.structName+";"+tree.id+";"+tree.catNo+";"+tree.catName+"' selected='selected' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel3"));
			}else{
				$("<option value='"+tree.structName+";"+tree.id+";"+tree.catNo+";"+tree.catName+"' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel3"));
			}
		}
		if(parentId_3==tree.structName){
			parentId_2 = tree.parentId;
		}
	}
	$("#sel3").change().reJqSelect();
	
	//遍历二级分类
	for(var i=0,length=updateTaobaoItem.catList.length;i<length;i++){
		var tree = updateTaobaoItem.catList[i];
		if(parentId_2==tree.parentId){
			if(parentId_3==tree.structName){
				$("<option value='"+tree.catNo+"' selected='selected' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel2"));
			}else{
				$("<option value='"+tree.catNo+"' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel2"));
			}
		}
	}
	$("#sel2").change().reJqSelect();
	
	for(var i=0,length=updateTaobaoItem.catList.length;i<length;i++){
		var tree = updateTaobaoItem.catList[i];
		if(tree.parentId=="0"){
			if(parentId_2==tree.structName){
				$("<option value='"+tree.catNo+"' selected='selected' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel1"));
			}else{
				$("<option value='"+tree.catNo+"' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#sel1"));
			}
		}
	}
	$("#sel1").change().reJqSelect();
}

/**
 * 根据品牌获取分类
 */
updateTaobaoItem.queryBrandCat = function(brandId){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			brandId:brandId
		},
		url : updateTaobaoItem.basePath+"/taobao/queryBrandCat.sc",
		success : function(data) {
			updateTaobaoItem.catList = data;
		}
	});
}

updateTaobaoItem.reinitializeOption = function(pid,nodeId){
	var tempOption =$("#"+nodeId).children().eq(0); 
	$("#"+nodeId).children().remove();
	tempOption.appendTo($("#"+nodeId));
	for(var i=0,length=updateTaobaoItem.catList.length;i<length;i++){
		var tree = updateTaobaoItem.catList[i];
		 if(tree.parentId==pid){
			 if(nodeId=="sel3"){
				 $("<option value='"+tree.structName+";"+tree.id+";"+tree.catNo+";"+tree.catName+"' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#"+nodeId)); 
			 }else{
				 $("<option value='"+tree.catNo+"' structname='"+tree.structName+"'>"+tree.catName+"</option>").appendTo($("#"+nodeId));
			 }
			
	 	 }
	}
	$("#"+nodeId).change().reJqSelect();
}

updateTaobaoItem.loadProps = function(catNoFull,isInit){
	var catNo = catNoFull.split(";")[2];
	//显示商品属性、颜色、尺码
	$(".goods_cat_change_show").show();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			catNo:catNo
		},
		url : updateTaobaoItem.basePath+"/taobao/getCommodityPropertitiesByCatNo.sc",
		success : function(data) {
			if(data == null || typeof(data) != "object" || data.isSuccess == null ||
					typeof(data.isSuccess) == "undefined") {
				return;
			}
			if(data.isSuccess != "true") {
				ygdg.dialog.alert(data.errorMsg);
				return;
			}
			updateTaobaoItem.latestPics = data.latestPics;
			//加载商品属性
			updateTaobaoItem.properties.load(data.propList);
			
			//加载尺码列表
			updateTaobaoItem.size.load(data.sizeList);
			
			//属性变化事件
			$(".goods_properties_select").change(function() {
				updateTaobaoItem.properties.select_OnChange(this);
			});
			
			//颜色输入框鼠标事件
			$("#goods_specName").blur(function() {			
				$("#goods_product_color_td").html($("#goods_specName").val());
			});
			//尺码的点击事件
			$(".goods_sizeNo_checkboxes").click(function() {
				updateTaobaoItem.size.size_OnClick($(this));					
			});
			
			//初始化尺码信息
			//$("#goods_color_size_tbl").hide();
			$("#goods_color_size_tbody").empty();
			if(isInit){
				updateTaobaoItem.setProperties();
				updateTaobaoItem.setSizeInfo();
			}
		}
	});
}


/**
 * 加载商品属性
 * @param {Array} propList 商品属性列表
 */
updateTaobaoItem.properties.load = function(propList) {
	if(propList == null) return;
	if(!propList.length) return;
	var html = '';
	var propObj = null;	//单个属性
	var propValues = null;  //属性值数组
	var propValueObj = null;  //单个属性值
	var optionsHtml = null;  //select框中的选项html
	for (var i = 0, len = propList.length; i < len; i++) {
		propObj = propList[i];
		propValues = propObj.propValues;
		valueType = propObj.valueType;
		if(!Boolean(propValues) || !Boolean(propValues.length)) continue;
		//应产品的要求在发布商家商品时不显示如下字段 add by zhuang.rb at 2012-12-11
		if(propObj.propItemName=="所在区域" || propObj.propItemName=="名厂直销品牌" || propObj.propItemName=="名厂直销分类" || propObj.propItemName=="货品来源") continue;
		
		if (valueType == 1) {//多选
			var checkboxhtml = '<ul class="clearfix">';
			for (var j = 0, len2 = propValues.length; j < len2; j++) {
				propValueObj = propValues[j];
				checkboxhtml += formatString(
					'<li>' + 
					'	<input type="checkbox" id="goods_prop_display_{#propItemNo}_{#value}" name="goods_prop_checkbox_{#propItemNo}" value="{#value};{#name}" /> ' +
					'	<label for="goods_prop_display_{#value}">' +
					'		{#name}' + 
					'	</label>' +
					'</li>', 
					{
						value: propValueObj.propValueNo,
						name: propValueObj.propValueName,
						propItemNo: propObj.propItemNo
					});
			}
			checkboxhtml += '</ul>';
			html += formatString(
				'<div class="goods_prop_select_layer clearfix">' +
				'	<label>{#propItemName} ：{#propIsShowMall}</label>' + 
				'   <div class="fl" id="goods_prop_button_div_{#propItemNo}">' +
				'      <span id="goods_prop_checked_result_{#propItemNo}"></span>' +
				'      <span>' +
				'            <a id="checkbox_button_{#propItemNo}" onclick="updateTaobaoItem.properties.showGoodsCheckBoxProp(\'{#propItemNo}\');" class="button" style="margin-left:0;">' +
				'               <span id="goods_prop_checked_button_{#propItemNo}">+选择</span>' +
				'            </a>' +
				'      </span>' +
				'   </div>' +
				'   <div class="goods_prop_content" id="goods_prop_hidden_{#propItemNo}">' +
				'       {#checkboxs}' + 
				'   	<p class="clearfix" style="width:200px;margin:0 auto;margin-top:10px;">' +
				'          <a onclick="updateTaobaoItem.properties.confirm(\'{#propItemNo}\', \'{#propItemName}\')"  class="button fl" style="margin-left:0;"><span>确定</span></a>'+
				'          <a style="float:left;padding-left:100px;" href="javascript:;" onclick="updateTaobaoItem.properties.cancel(\'{#propItemNo}\');">取消</a>' + 
				'       </p>' +
				'   </div>' +
				'   <input type="hidden" id="goods_prop_valuetype_{#propItemNo}" propItemNo="{#propItemNo}" propItemName="{#propItemName}" prodMustInput="{#clsMustInput}" value="1" />' +
				'   <span id="{#propItemNo}_error_tip" class="goods_error_tip"></span>' +
				'</div>', 
				{
					propItemNo: propObj.propItemNo,
					propItemName: propObj.propItemName,
					propIsShowMall: propObj.isShowMall==0?"<span class='detail_item_star'>*</span>":"",
					clsMustInput: propObj.isShowMall==0?"true":"false",
					checkboxs: checkboxhtml
				});
		} else {
			//当前属性选项
			optionsHtml = '<option value="" selected="selected">请选择</option>';
			for (var j = 0, len2 = propValues.length; j < len2; j++) {
				propValueObj = propValues[j];
				optionsHtml += formatString(
					'<option value="{#propValueNo};{#propValueName}" realvalue="{#propValueNo}" propValueName="{#propValueName}">{#propValueName}</option>',
					{
						propValueNo: propValueObj.propValueNo,
						propValueName: propValueObj.propValueName
					});
			}
			html += formatString(
				'<div class="goods_prop_select_layer clearfix">' +
				'	<label>{#propItemName} ：{#propIsShowMall}</label>' +
				'	<select id="goods_prop_select_{#propItemNo}" name="goods_prop_select_{#propItemNo}" propItemNo="{#propItemNo}" propItemName="{#propItemName}" prodMustInput="{#clsMustInput}" >{#options}</select>' +
				'   <input type="hidden" id="goods_prop_valuetype_{#propItemNo}" value="0" />' + 
				'   <span id="{#propItemNo}_error_tip" class="goods_error_tip"></span>' +
				'</div>', 
				{
					propItemNo: propObj.propItemNo,
					propItemName: propObj.propItemName,
					//用来标记或者判断商品扩展属性是否必选 add by zhuang.rb at 2012-12-11
					propIsShowMall: propObj.isShowMall==0?"<span class='detail_item_star'>*</span>":"",
					clsMustInput: propObj.isShowMall==0?"true":"false",
					options: optionsHtml
				});
		}
	}
	$("#goods_prop_layer .detail_item_content").html(html);
	//渲染下拉框
	var $propSelects = $("#goods_prop_layer .detail_item_content select");
	var propSelect = null;
	for (var i = 0, len = $propSelects.length; i < len; i++) {
		propSelect = $propSelects[i];
		$(propSelect).jqSelect();
		$(propSelect).addClass("goods_properties_select");
	}
};


/**
 * 加载尺码列表
 * @param {Array} sizeList 尺码列表
 */
updateTaobaoItem.size.load = function(sizeList) {
	if(sizeList == null) return;
	if(!sizeList.length) return;
	if(sizeList.length > 1) {
		if(isNaN(sizeList[0].propValueName)) {
			//尺码列表排序(字符串)
			sizeList.sort(updateTaobaoItem.size.sizeStringComparator);
		} else {
			//尺码列表排序(数字)
			sizeList.sort(updateTaobaoItem.size.sizeNumberComparator);
		}
	}
	var html = '<ul>';
	var sizeObj = null;
	for (var i = 0, len = sizeList.length; i < len; i++) {
		sizeObj = sizeList[i];
		html += formatString(
			'<li>' +
			'	<input type="checkbox" id="goods_sizeNo_display_{#value}" class="goods_sizeNo_checkboxes" name="goods_size_list" value="{#value}" /> ' +
			'	<label for="goods_sizeNo_display_{#value}">' +
			'		{#name}' + 
			'	</label>' +
			'</li>', 
			{
				value: sizeObj.propValueNo,
				name: sizeObj.propValueName
			});
	}
	html += '</ul>';
	$("#goods_size_layer .detail_item_content").html(html);
};


/**
 * 属性选择框变化事件
 * @param {Object} self 当前点击的属性选择框
 */
updateTaobaoItem.properties.select_OnChange = function(self) {
	var $self = $(self);
	var selfValue = $.trim($self.val());
	var propItemNo = $.trim($self.attr("propItemNo"));  //属性编号
	var propItemName = $.trim($self.attr("propItemName"));  //属性名称
	if(selfValue.length != 0) {  //该属性项有值
		var propValueNo = selfValue;  //属性值编号
		var propValueName = $.trim($self.find("option:selected").attr("propValueName"));  //属性值名称
		if($("#goods_properties_propItemNo_" + propItemNo).length > 0) {  //不是首次选择
			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo);
			$("#goods_properties_propValueName_" + propItemNo).val(propValueName);
		} else {  //首次选择
			//添加隐藏域
			appendHidden("goods_properties_propItemNo_" + propItemNo,
				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
			appendHidden("goods_properties_propItemName_" + propItemNo,
				propItemName, "propItemName", "#goods_selected_properties_propItemName");
			appendHidden("goods_properties_propValueNo_" + propItemNo,
				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
			appendHidden("goods_properties_propValueName_" + propItemNo,
				propValueName, "propValueName", "#goods_selected_properties_propValue");
		}
	} else {  //选择了“请选择”
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
	}
	
	//error_tip hide
	$('#' + propItemNo + '_error_tip').hide();
};

/**
 * 尺码的点击事件
 * @param {Object} self 当前点击的尺码
 */
updateTaobaoItem.size.size_OnClick = function(self) {
	$("#goods_size_color_layer").show();
	//当前选中的尺码值
	var sizeValue = $.trim($(self).val());
	//当前选中的尺码名称
	var sizeName = $.trim($(self).parent().find("label").text());
	//颜色名称
	var colorName = $("#goods_specName").val();
	
	//尺码点击事件 预处理
	//if(!updateTaobaoItem.size.pre_size_OnClick(self, sizeValue, sizeName, colorName)) return;
	if(self.attr("checked")=="checked") {
		//新增一行货品信息
		updateTaobaoItem.prodInfo.addTableLine(sizeValue, sizeName, colorName);
	} else {
		//删除一行货品信息
		updateTaobaoItem.prodInfo.removeTableLine(sizeValue, self, colorName);
	}
	
	//error_tip hide
	$('#sizeNo_error_tip').hide();
};



/** 展开多选属性值 */
updateTaobaoItem.properties.showGoodsCheckBoxProp = function(propItemNo){
	$("#goods_prop_hidden_"+propItemNo).show();
	$("#goods_prop_button_div_"+propItemNo).hide();
	//error_tip hide
	$('#' + propItemNo + '_error_tip').hide();
};


/**
 * 取消本次选择操作/保留上次操作
 * @param propItemNo 属性No
 */
updateTaobaoItem.properties.cancel = function(propItemNo) {
	$("#goods_prop_hidden_"+propItemNo).hide();
	$("#goods_prop_button_div_"+propItemNo).show();
	$($("input[name='goods_prop_checkbox_"+propItemNo+"']:checked")).each(function() {
		$(this).attr('checked', false);
	});
	//还原上次保留的操作
	var _propObj = $("#goods_properties_propValueNo_" + propItemNo);
	if (_propObj == null || typeof(_propObj) == "undefined") return false;
	var _propValueNos_temp = $("#goods_properties_propValueNo_" + propItemNo).val();
	if (_propValueNos_temp == null || typeof(_propValueNos_temp) == "undefined" || _propValueNos_temp.length == 0) return false;
	var _propValueNos = _propValueNos_temp.split(";");
	var _propValueNo = "";
	for (var i = 0, len = _propValueNos.length; i < len; i++) {
		_propValueNo = _propValueNos[i];
		if (_propValueNo != null && _propValueNo.length != 0) {
			$("#goods_prop_display_" + propItemNo + "_"+_propValueNo).attr('checked', true);
		}
	}
	
};


/**
 * 确定属性多选值
 * @param propItemNo 属性No
 * @param propItemName 属性名称
 */
updateTaobaoItem.properties.confirm = function(propItemNo, propItemName) {
	//重置选择结果框
	$("#goods_prop_checked_result_"+propItemNo).empty();
	var html = '';
	var propValueNo = '';
	var propValueName = '';
	$($("input[name='goods_prop_checkbox_"+propItemNo+"']:checked")).each(
		function() {
			var a = this.value.split(";");
			if (a.length == 2) {
				propValueNo += a[0] + ";";	propValueName += a[1] + ";";
				html += formatString(
						'<span class="goods_prop_span_checked" id="goods_prop_checked_result_{#propItemNo}_{#value}">' +
						'<span class="fl tt">{#name}</span><a href="javascript:;" ' +  
						'       onclick="updateTaobaoItem.properties.deleteGoodsCheckBoxProp(\'{#propItemNo}\', \'{#value}\', \'{#name}\');">' + 
						'	    ' +
						'   </a>' + 
						'</span>', 
						{
							value: a[0],
							name: a[1],
							propItemNo: propItemNo
						});
			}
		}
	);
	$("#goods_prop_checked_result_"+propItemNo).html(html);
	if (propValueNo.length > 0) {
		if ($("#goods_properties_propItemNo_" + propItemNo).length>0) {
			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo.substring(0, propValueNo.length - 1));
			$("#goods_properties_propValueName_" + propItemNo).val(propValueName.substring(0, propValueName.length - 1));
		} else {
			//添加隐藏域
			appendHidden("goods_properties_propItemNo_" + propItemNo,
				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
			appendHidden("goods_properties_propItemName_" + propItemNo,
				propItemName, "propItemName", "#goods_selected_properties_propItemName");
			appendHidden("goods_properties_propValueNo_" + propItemNo,
				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
			appendHidden("goods_properties_propValueName_" + propItemNo,
				propValueName, "propValueName", "#goods_selected_properties_propValue");
		}
		$("#goods_prop_checked_button_"+propItemNo).html("继续添加");
	} else {
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
		$("#goods_prop_checked_button_"+propItemNo).html("+选择");
	}
	
	$("#goods_prop_hidden_"+propItemNo).hide();
	$("#goods_prop_button_div_"+propItemNo).show();
};

/**
 * 删除某属性的多选值项
 * @param propItemNo 被删除的属性项No
 * @param propValueNo 被取消的属性项值No
 * @param propValueName 被取消的属性项值Name
 */
updateTaobaoItem.properties.deleteGoodsCheckBoxProp = function(propItemNo, propValueNo, propValueName) {
	$("#goods_prop_checked_result_"+propItemNo+"_"+propValueNo).remove();
	$("#goods_prop_display_" + propItemNo + "_" + propValueNo).attr('checked', false);
	
	var _propValueNo = $("#goods_properties_propValueNo_" + propItemNo).val();
	var _propValueName = $("#goods_properties_propValueName_" + propItemNo).val();
	var _Nos = "";
	var _Names = "";
	if (_propValueNo.search(propValueNo + ";") == -1) {
		_Nos = _propValueNo.replace(propValueNo, "");
		_Names = _propValueName.replace(propValueName, "");	
	} else {
		_Nos = _propValueNo.replace(propValueNo + ";", "");
		_Names = _propValueName.replace(propValueName + ";", "");	
	}
	$("#goods_properties_propValueNo_" + propItemNo).val(_Nos);
	$("#goods_properties_propValueName_" + propItemNo).val(_Names);
	if (_Nos.length == 0) {
		$("#goods_prop_checked_button_"+propItemNo).html("+选择");
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
	}
};

updateTaobaoItem.size.pre_size_OnClick = function(self, sizeValue, sizeName, colorName) {
	var productNo = $.trim($("#goods_product_tr_" + sizeValue).attr("productNo"));
	if(productNo != null && typeof(productNo) != "undefined" && 
			productNo.length != 0 && !self.checked) {
		var sellStatus=$("#goods_sizeNo_display_" + sizeValue).attr("sellStatus");
		if(sellStatus=='2'){
			ygdg.dialog.alert("该尺码对应的货品已经有销售数据,不允许删除!");
			$("#goods_sizeNo_display_" + sizeValue).attr("checked", "true");
			return false;
		}
		ygdg.dialog.confirm('确定要删除尺码为 "' + sizeName + '" 这个货品吗？', function(){
			//删除一行货品信息
			updateTaobaoItem.removeTableLine(sizeValue, self, colorName);
		}, 
		function(){
			$("#goods_sizeNo_display_" + sizeValue).attr("checked", "true");
		});
		return false;
	}
	return true;
};


/**
 * 新增一行货品信息
 * @param {String} sizeValue 当前选中的尺码值
 * @param {String} sizeName 当前选中的尺码名称
 * @param {String} colorName 当前选中的颜色名称
 */
updateTaobaoItem.prodInfo.addTableLine = function(sizeValue, sizeName, colorName) {
	var trCount = $("#goods_color_size_tbody>tr").length;
	//是否为第一行数据
	var isFirstLine = trCount == 0;
	if(!isFirstLine) {
		$("#goods_product_color_td").attr("rowspan", trCount + 1);
	}
	//货品资料Html
	var productInfoHtml = formatString(
		'<tr id="goods_product_tr_{#sizeValue}">' +
		'	{#colorTd}' +
		'	<td>{#sizeName}<input type="hidden" name="size_{#sizeValue}" value={#sizeValue}></td>' +
		'	<td class="goods_prod_stock_column"><input type="text" sizeValue="{#sizeValue}" id="goods_stock_{#sizeValue}" name="stock_{#sizeValue}" class="inputtxt goods_stock_txt" value="" style="width: 80px;" maxlength="6" /></td>' +
		'	<td><input type="text" sizeValue="{#sizeValue}" id="goods_thirdPartyCode_{#sizeValue}" name="goods_thirdPartyCode_{#sizeValue}" class="inputtxt goods_thirdPartyCode_txt" value="" style="width: 120px;" maxlength="32" /></td>' +
		'</tr>',
		{
			colorTd: isFirstLine ? formatString('<td id="goods_product_color_td">{#colorName}</td>', {colorName: colorName} ) : "",
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_color_size_tbody").append(productInfoHtml);

	
	//尺码编号隐藏域Html
	var sizeNoHiddenHtml = formatString(
		'<input type="hidden" id="goods_sizeNo_{#sizeValue}" name="sizeNo" value="{#sizeValue}" style="display: none;" />', 
		{
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_selected_sizeNo_layer").append(sizeNoHiddenHtml);
	
	//尺码名称隐藏域Html
	var sizeNameHiddenHtml = formatString(
		'<input type="hidden" id="goods_sizeName_{#sizeValue}" name="sizeName" value="{#sizeName}" style="display: none;" />', 
		{
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_selected_sizeName_layer").append(sizeNameHiddenHtml);
};

/**
 * 删除一行货品信息
 * @param {String} sizeValue 当前选中的尺码值
 * @param {Object} self 当前点击的尺码
 * @param {String} colorName 当前选中的颜色名称
 */
updateTaobaoItem.prodInfo.removeTableLine = function(sizeValue, self, colorName) {
	var lineNum = $("#goods_product_tr_" + sizeValue).index();
	var rowCount = $("#goods_color_size_tbody>tr").length;
	
	var colorTdHtml = '';
	//如果是第一行
	if(lineNum == 0) {
		colorTdHtml = formatString(
		'<td rowspan="{#rowCount}" id="goods_product_color_td">{#colorName}</td>',
		{
			rowCount: rowCount - 1,
			colorName: colorName
		});
	} else {  //如果不是第一行
		$("#goods_product_color_td").attr("rowspan", rowCount - 1);
	}
	$("#goods_product_tr_" + sizeValue).remove();
	$("#goods_sizeNo_" + sizeValue).remove();
	$("#goods_sizeName_" + sizeValue).remove();
	
	$("#goods_color_size_tbody>tr:eq(0)").prepend(colorTdHtml);
};

updateTaobaoItem.setProperties = function(){
	var props = updateTaobaoItem.props;
	for (var i = 0, len = props.length; i < len; i++) {
		prop =  props[i];
		var propItemNo = prop.yougouPropItemNo;
		var propValueNo = prop.yougouPropValueNo;
		var propValueName = prop.yougouPropValueName;
		//属性是否多选
		var valueType = $("#goods_prop_valuetype_"+propItemNo).val();
		//console.info(i);
		if (valueType == '0') {
			 //$("#goods_prop_select_" + propItemNo).val(propValueNo+";"+propValueName);
			 $("#goods_prop_select_" + propItemNo).find("option[realvalue='"+propValueNo+"']").attr("selected",true);
			// setSelectValue("#goods_prop_select_" + propItemNo, propValueNo);
			  //$("#goods_prop_select_" + propItemNo).find("option[value='"+propValueNo+"']").attr("selected",true);
			//$("#goods_prop_select_" + propItemNo).val(propValueNo);
			//alert($("#goods_prop_select_" + propItemNo).val());
			$("#goods_prop_select_" + propItemNo).reJqSelect();
			$("#goods_prop_select_" + propItemNo).change();
		} else if (valueType == '1') {
			//属性多选
			$("#goods_prop_display_" + propItemNo + "_" + propValueNo).attr('checked', true);
			var propItemName = $("#goods_prop_display_" + propItemNo + "_" + propValueNo).val().split(";")[1];
			updateTaobaoItem.properties.confirm(propItemNo, propItemName);
			//加入属性id和商品id
			/*var _valueTypeObj = $("#goods_prop_valuetype_" + propItemNo);
		//	var _propItemId = _valueTypeObj.attr("propItemId");
			var _propValueNo = _valueTypeObj.attr("propValueNo");
			if (_propItemId == null || _propItemId.length == 0) {
				//_valueTypeObj.attr("propItemId", propItemId);
				_valueTypeObj.attr("propValueNo", propValueNo);
			} else {
				//_valueTypeObj.attr("propItemId", _propItemId + ";" + propItemId);
				_valueTypeObj.attr("propValueNo", _propValueNo + ";" + propValueNo);
			}*/
		}
	}
}
updateTaobaoItem.setSizeInfo = function(){
 
	var sizeInfos = updateTaobaoItem.sizeInfo;
	//alert("sizeInfos.length:"+sizeInfos.length);
	for (var i = 0, len = sizeInfos.length; i < len; i++) {
		sizeInfo = sizeInfos[i];
		$("#goods_sizeNo_display_" + sizeInfo.yougouPropValueNo).attr("checked", "true");
		//$("#goods_sizeNo_display_" + sizeInfo.yougouPropValueNo).attr("sellStatus", sizeInfo.sellStatus);
		//触发尺码的点击事件
		//console.info(sizeInfo.yougouPropValueNo);
		//console.info($("#goods_sizeNo_display_" + sizeInfo.yougouPropValueNo).attr("checked"))
		updateTaobaoItem.size.size_OnClick($("#goods_sizeNo_display_" + sizeInfo.yougouPropValueNo));
		//alert("sizeInfo.yougouPropValueNo:"+sizeInfo.yougouPropValueNo);
		//设置库存
		$("#goods_stock_" + sizeInfo.yougouPropValueNo).val(sizeInfo.stock);
		//设置货品条码
		$("#goods_thirdPartyCode_" + sizeInfo.yougouPropValueNo).val(sizeInfo.barcode);
		
		//prodNoStr += prod.productNo + ",";
		//prodNoSizeNoMap[prod.productNo] = prod.sizeNo;
	}
}


updateTaobaoItem.inputFile_OnChange = function(inputFile,number) {
	if(CheckFile(inputFile)){
		//是否为首次变化
		var isFirstChange = parseInt($.trim($(inputFile).attr("isFirstChange")));
		var browserInfo = getBrowserInfo()+"";
		if(!isFirstChange) {
			$(inputFile).attr("isFirstChange", 1);
			$(inputFile).parent().find("span").html("上传新图片");
			//为预览的图片添加操作
			if(browserInfo.lastIndexOf("msie")==-1){
				updateTaobaoItem.addOptToPreviewImg(inputFile);		
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
			updateTaobaoItem.imgPreview(inputFile, img, width, height);
		}
		var loading = '<span id="image_loading_' + number + '" style="position:absolute;left:50%;top:50%;margin-left:-12px;margin-top:-8px;"><img style="position:relative;z-index:2;"  src="' + updateTaobaoItem.loadingImgUrl + '" width="16" height="16" /><span style="width:30px;height:30px;margin-top:-15px;margin-left:-15px;position:absolute;left:50%;top:50%;z-index:1;background:#ddd;border:1px solid #ccc;-moz-opacity:0.8;opacity: 0.8;" ></span></span>';
		$('#goods_img_layer_' + number).append(loading);
		//异步上传图片、禁用保存按钮
		image_hander++;
		//isforbiddenButton('commodity_save', true, null);
		//isforbiddenButton('commodity_audit', true, null);
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
						var obj = eval("(" + src + ")");
			            //obj = eval ("(" + obj + ")");
		                if(obj.success==true){
		                	$("#img_file_id_"+number).val(obj.message);
		                	if(browserInfo.lastIndexOf("msie")!=-1){
		                	 	$("#goods_img_layer_"+number).find("img").attr("src",getThumbnail(obj.message));
		                	}
		                }else{
		                	$("#img_file_id_"+number).val("-1");
		                	$("#img_file_id_"+number).siblings("div[class='goods_img_layer']").children("img").attr("src","/yougou/images/unknow_img.png");
		                	updateTaobaoItem.imgPreviewFail(inputFile, previewDivId, width, height);
		                	
		                	//alert("-------------->"+obj.message);
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
	                	updateTaobaoItem.imgPreviewFail(inputFile, img, width, height);
	                	ygdg.dialog.alert("图片上传，服务器返回数据格式异常,请联系管理员！");
	                }
	                //图片处理完成、释放保存按钮 (is_execute判断再绑定事件时是否执行函数)
	                is_execute = false;
	                if (image_hander <= 0) {
	                	//isforbiddenButton('commodity_save', false, function() {if (is_execute) goodsAdd.submit.submitForm(false);});
	                	//isforbiddenButton('commodity_audit', false, function() {if (is_execute) goodsAdd.submit.submitForm(true);});
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

updateTaobaoItem.imgPreview = function(file, img, width, height) {
	if(file["files"] && file["files"][0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.attr("src",evt.target.result);
			img.attr("transient", 'true')
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

updateTaobaoItem.imgPreviewFail = function(file, img, width, height) {
//	var imgDiv = $("#" + imgDivId);
	if(file["files"] && file["files"][0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.attr("src","/yougou/images/unknow_img.png");
			img.attr("transient", 'true')
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
function CheckFile(img) {
	// 判断图片类型
	if (!/\.(jpg)$/g.test(img.value.toLowerCase())) {
		ygdg.dialog.alert("商品图片类型必须是jpg！");
		return false;
	}
	return true;
}

updateTaobaoItem.addOptToPreviewImg = function(inputFile) {
	$(inputFile).parent().parent().bind("mouseover",
			function() {
				updateTaobaoItem.previewImg_OnMouseOver(inputFile);
			});
	$(inputFile).parent().parent().bind("mouseout",
			function() {
				updateTaobaoItem.previewImg_OnMouseOut(inputFile);
			});
};

updateTaobaoItem.previewImg_OnMouseOver = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).show();
};

/**
 * 预览图片 mouseout 事件
 * @param {Object} inputFile 上传框对象
 */
updateTaobaoItem.previewImg_OnMouseOut = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).hide();
};

/**
 * 删除操作 点击事件
 * @param {Object} self 被点击的操作对象
 */
updateTaobaoItem.previewOptDelete_OnClick = function(self, number) {
	$(self).parent().parent().unbind("mouseover");
	$(self).parent().parent().unbind("mouseout");
	$(self).parent().hide();
	
	var sortNo = $.trim($(self).attr("sortNo"));
	var src = updateTaobaoItem.imgPathList[sortNo - 1];
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
		'<input type="file" id="{#inputId}" sortNo="{#sortNo}" name="{#inputName}" isFirstChange="0" class="detail_jq_file_btn" onchange="updateTaobaoItem.inputFile_OnChange(this, {#number})" />',
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

updateTaobaoItem.submitForm = function(isPreview){
	$("#msgdiv").hide();
	if(!updateTaobaoItem.checkForm()){
		return;
	}
	
	var images =  $("input[name='imgFileId']");
	var imgFiles = []; //角度图
	for(var i=0,length=images.length;i<length;i++){
		if(images.eq(i).val()!="-1"){
			imgFiles.push(images.eq(i).val());
		}
	}
	$("#imageHideInput").val(imgFiles.join(","));
	ymc_common.loading("show","正在保存......");
	$("#brandName").val($("#brandNo").find("option:selected").text());
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#taobao_item_form").serialize(),
		url : updateTaobaoItem.basePath+"/taobao/updateTaobaoItem.sc?isPreview="+isPreview,
		success : function(data) {
			ymc_common.loading();
			if(data.resultCode=="200"){
				if(data.errorList!=null){
					updateTaobaoItem.showErrorTips(data.errorList);
				}else{
					ygdg.dialog.alert("修改成功!");
					document.location.href="goTaobaoItemList.sc";
				}
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}

updateTaobaoItem.showErrorTips = function(errorList){
	if(errorList!=null){
		var error;
		var $obj;
		var $barcodeObj;
		$("#showtd").empty();
		var table=$("<table width='100%'></table>").appendTo($("#showtd"));
		for(var i=0,len =errorList.length;i<len;i++ ){
			error = errorList[i];
			$obj = $("#"+error.errorFiled+"_error_tip");
			$barcodeObj= $("#"+error.errorFiled);
			if($obj[0]!=null){
				$obj.text(error.errMsg);
				$obj.show();
			}
			if($barcodeObj[0]!=null){
				$barcodeObj.addClass("error");
			}
			$("<tr><td style='padding:2px'><img src='/yougou/images/error.gif' class='goods_error_image'>"+error.errMsg+"</td></tr>").appendTo(table);
		}
		$("#msgdiv").show();
	}
}
goodsAdd.validate.errorList = [];
goodsAdd.validate.validateFunList = ["validateBrandNo", "validateRootCattegory", "validateSecondCategory",
                         			"validateThreeCategory", "validateCommodityName", "validateStyleNo",
                         			"validateSupplierCode", "validateSalePrice", "validatePublicPrice","validateYougouPrice", "validateGoodsProp",
                         			"validateSpecNo", "validateSizeNo", "validteStock", "validateThirdPartyCode",
                         			"validateProdDescLength"];//
//校验数据的合法性
updateTaobaoItem.checkForm = function(){
	goodsAdd.validate.errorList = [];
	var validateFunList = goodsAdd.validate.validateFunList;
	for (var i = 0, len = validateFunList.length; i < len; i++) {
		eval('goodsAdd.validate[validateFunList[i]]()');
	}
	var errorList = goodsAdd.validate.errorList;
	
	//校验角度图 描述图
	if(goodsAdd.validate.validateImage()&&goodsAdd.validate.validateProdDesc){
		$("#checkStatus").val("1");
	}else{
		$("#checkStatus").val("0");
	}
	if(errorList.length==0){
		return true;
	}else{
		updateTaobaoItem.showErrorTips(errorList);
	}
	
}


/**
 * 校验图片
 */
goodsAdd.validate.validateImage = function() {
	var notNullMsg = "角度图必须必须是"+updateTaobaoItem.latestPics+"张";
	var images =  $("input[name='imgFileId']");
	var imgFiles = []; //角度图
	for(var i=0,length=images.length;i<length;i++){
		if(images.eq(i).val()!="-1"){
			imgFiles.push(images.eq(i).val());
		}
	}
	var latestNumber = updateTaobaoItem.latestPics;
	if(imgFiles.length<latestNumber){
		//goodsAdd.validate.errorList.push({ errorFiled: "commodityImage", errMsg: notNullMsg });
		return false;
	}else{
		return true;
	}
};

/**
 * 验证品牌 
 */
goodsAdd.validate.validateBrandNo = function() {
	var notNullMsg = '请选择品牌';
	var $thisObj = $('#brandNo');
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('brandNo', thisValue, notNullMsg);
};

/**
 * 验证一级分类 
 */
goodsAdd.validate.validateRootCattegory = function() {
	var notNullMsg = "请选择一级分类";
	var $thisObj = $("#sel1");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证二级分类 
 */
goodsAdd.validate.validateSecondCategory = function() {
	var notNullMsg = "请选择二级分类";
	var $thisObj = $("#sel2");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证三级分类 
 */
goodsAdd.validate.validateThreeCategory = function() {
	var notNullMsg = "请选择三级分类";
	var $thisObj = $("#sel3");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证商品名称
 */
goodsAdd.validate.validateCommodityName = function() {
	var notNullMsg = "请填写正确的商品名称";
	var $thisObj = $("#goods_commodityName");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('commodityName', thisValue, notNullMsg);
};

/**
 * 验证商品款号
 */
goodsAdd.validate.validateStyleNo = function() {
	var notNullMsg = "请填写商品款号";
	var containsChinese = "商品款号不能包含中文";
	var $thisObj = $("#goods_yougouStyleNo");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('yougouStyleNo', thisValue, notNullMsg))return;
	//if(!goodsAdd.validate.containsChinese('yougouStyleNo', thisValue, containsChinese)) return;
};

/**
 * 验证商家款色编码
 */
goodsAdd.validate.validateSupplierCode = function() {
	var notNullMsg = "请输入商家款色编码";
	var containsChinese = "商家款色编码不能包含中文";
	var $thisObj = $("#goods_yougouSupplierCode");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('yougouSupplierCode', thisValue, notNullMsg))return;
	//if(goodsAdd.validate.containsChinese('yougouSupplierCode', thisValue, containsChinese)) return;
};

/**
 * 验证优购价格
 */
goodsAdd.validate.validateSalePrice = function() {
	var notNullMsg = "请输入优购价格";
	var notNumMsg = "优购价格请输入数字";
	var lessThanZeroMsg = "优购价格请输入大于0的数字";
	var $thisObj = $("#goods_yougouPrice");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('yougouPrice', thisValue, notNullMsg)) return;
	if(goodsAdd.validate.isNotNum('yougouPrice', thisValue, notNumMsg)) return;
	if(goodsAdd.validate.isLessThanZero('yougouPrice', thisValue, lessThanZeroMsg)) return;
};

/**
 * 验证市场价格
 */
goodsAdd.validate.validatePublicPrice = function() {
	var notNullMsg = "请输入淘宝价格";
	var notNumMsg = "淘宝价格请输入数字";
	var lessThanZeroMsg = "淘宝价格请输入大于0的数字";
	var $thisObj = $("#goods_price");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('price', thisValue, notNullMsg)) return;
	if(goodsAdd.validate.isNotNum('price', thisValue, notNumMsg)) return;
	if(goodsAdd.validate.isLessThanZero('price', thisValue, lessThanZeroMsg)) return;
};

/**
 * 优购价必须小于市场价
 */
goodsAdd.validate.validateYougouPrice = function() {
	var notNullMsg = "优购价不能大于淘宝价";
	var yougouPrice = $("#goods_yougouPrice").val();
	var taobaoPrice = $("#goods_price").val();
	if(parseFloat(yougouPrice)>parseFloat(taobaoPrice)){
		goodsAdd.validate.errorList.push({ errorFiled: "yougouPrice", errMsg: notNullMsg });
	}
};

/**
 * 验证扩展属性是否必选 add by zhuang.rb at 2012-12-11
 */
goodsAdd.validate.validateGoodsProp = function() {
	$(".goods_prop_select_layer").each(function(){
		var $Obj = $(this).find("input[type='hidden']");
		var valueType = $Obj.val();
		if (valueType == '0') {
			var $thisObj = $(this).find("select");
			if($thisObj.attr("prodMustInput") == "true"){
				var notNullMsg = '请选择' + $thisObj.attr("propitemname");
				var thisValue = $.trim($thisObj.val());
				goodsAdd.validate.isEmpty($thisObj.attr('propItemNo'), thisValue, notNullMsg);
			}
		} else if (valueType == '1') {
			if ($Obj.attr("prodMustInput") == "true") {
				var notNullMsg = '请选择' + $Obj.attr("propitemname");
				var propItemNo = $Obj.attr("propItemNo");
				var thisValue = $("#goods_properties_propValueNo_"+propItemNo);
				goodsAdd.validate.isEmpty($Obj.attr('propItemNo'), thisValue, notNullMsg);
			}
		}
	});
};

/**
 * 验证颜色  
*/
goodsAdd.validate.validateSpecNo = function(){
	var Msg = '请输入商品颜色';
	var errMsg = '商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"';
	var thisValue = $("#goods_specName").val();
	if(goodsAdd.validate.isEmpty('specName', thisValue, Msg)) return;
	
	var regexp = new RegExp('^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$');
	goodsAdd.validate.RegExp('specName', thisValue, regexp, errMsg);
};

/**
 * 验证尺码 
 */
goodsAdd.validate.validateSizeNo = function() {
	var errMsg = "请选择尺码";
	if($(".goods_sizeNo_checkboxes:checked").length == 0) {
		goodsAdd.validate.isEmpty('sizeNo', '', errMsg);
	}
};

/**
 * 验证库存数量
 * modify by huang.tao 2013-05-24
 * 去掉库存限制(库存不输入时默认为0)
 */
goodsAdd.validate.validteStock = function() {
	var notIntMsg = "第 {#num} 行库存数量请输入的数字必须整数";
	var $stocks = $(".goods_stock_txt");
	var stock = null;
	for (var i = 0, len = $stocks.length; i < len; i++) {
		stock = $stocks[i];
		var thisValue = $.trim(stock.value);
		var sizeValue = $(stock).attr('sizeValue');
		if(thisValue.length != 0) {
			if(goodsAdd.validate.isNotNum("goods_stock_"+sizeValue , thisValue, formatString(notIntMsg, {num: (i + 1)}))) continue;				
		}
	}
};

/**
 * 验证货品条码
 */
goodsAdd.validate.validateThirdPartyCode = function() {
	var emptyMsg = "请输入 第 {#num} 行的货品条码";
	var containsChinese = "第 {#num} 行的货品条码不能包含中文";
	var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
	var thirdPartyCode = null;
	for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
		thirdPartyCode = $thirdPartyCodes[i];
		var thisValue = $.trim(thirdPartyCode.value);
		var sizeValue = $(thirdPartyCode).attr('sizeValue');
		if(goodsAdd.validate.isEmpty("goods_thirdPartyCode_"+sizeValue, thisValue, formatString(emptyMsg, {num: (i + 1)}))) continue;
		if(goodsAdd.validate.containsChinese("goods_thirdPartyCode_"+sizeValue, thisValue, formatString(containsChinese, {num: (i + 1)}))) continue;
	}
	//校验货品条码是否重复
	goodsAdd.validate.validateThirdPartyCodeRepeat();
};

/**
 * 校验货品条码是否重复
 */
goodsAdd.validate.validateThirdPartyCodeRepeat = function() {
	var isRepeat = false;
	var codeArr = [];
	var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
	if($thirdPartyCodes.length == 0 || $thirdPartyCodes.length == 1) {
		return isRepeat;
	}
	var thirdPartyCode = null;
	var sizeValue = null;
	for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
		thirdPartyCode = $thirdPartyCodes[i];
		var thisValue = $.trim(thirdPartyCode.value);
		codeArr.push(thisValue);
	}
	//获取重复的元素
	var repeatArr = getRepeatElements(codeArr);
	if(repeatArr.length > 0) {
		var msg = "重复的货品条码： ";
		for (var i = 0, len = repeatArr.length; i < len; i++) {
			for ( var int = 0; int < $thirdPartyCodes.length; int++) {
				thirdPartyCode = $thirdPartyCodes[int];
				var thisValue = $.trim(thirdPartyCode.value);
				sizeValue = $(thirdPartyCode).attr('sizeValue');
				if (thisValue == repeatArr[i]) {
					goodsAdd.validate.isEmpty("goods_thirdPartyCode_"+sizeValue, '', msg + repeatArr[i]);					
				}
			}
		}
		
		isRepeat = true;
	}
	return isRepeat;
};

/**
 * 获取数组中重复的元素
 * @param {Array} 基本类型的数组
 * @return 返回重复的元素
 */
function getRepeatElements(codeArr) {
	var repeatArr = [];
	if(codeArr == null || typeof(codeArr) == "undefined" ||
			codeArr.length == 0 || codeArr.length == 1) {
		return repeatArr;
	}
	codeArr.sort();
	
	if(codeArr[0] == codeArr[1]) {
		if (!isEmpty(codeArr[0])) 
			repeatArr.push(codeArr[0]);
	}
	for (var i = 1, len = codeArr.length; i < len; i++) {
		if (isEmpty(codeArr[i])) continue;
		if(codeArr[i] == codeArr[i - 1]) {
			var isContain = false;
			for(var j = 0, len1 = repeatArr.length; j < len1; j++) {
				if(repeatArr[j] == codeArr[i]) {
					isContain = true;
					break;
				}
			}
			if(!isContain) {
				repeatArr.push(codeArr[i]);
			}
		}
	}
	return repeatArr;
}

/**
 * 验证商品描述 
 */
goodsAdd.validate.validateProdDesc = function() {
	var $thisObj = $("#goods_prodDesc");
	$thisObj.val(updateTaobaoItem.prodSpec.editor.html());
	var thisValue = $.trim($thisObj.val());
	//判断是否为空格和换行符
	var notBlankLineFeedStr = $.trim($thisObj.val().replace(/&nbsp;/g, '').replace(/<br \/>/ig, ''));
	if(notBlankLineFeedStr == null || typeof(notBlankLineFeedStr) == "undefined" || notBlankLineFeedStr.length == 0) {
		return false;
	}else{
		$("#prodDesc_error_tip").hide();
		return true;
	}
};

goodsAdd.validate.validateProdDescLength = function(){
	var notNullMsg = "请输入商品描述";
	var maxLen = 30000;
	var lenLargerMsg = "商品描述长度不能超过 " + maxLen;
	var $thisObj = $("#goods_prodDesc");
	$thisObj.val(updateTaobaoItem.prodSpec.editor.html());
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isLenLarger('prodDesc', thisValue, maxLen,lenLargerMsg )) {
		return false
	}else{
		return true;
	};
}
/**
 * 校验商品描述中是否存在外链
 */
updateTaobaoItem.prodSpec.checkOuterLink = function() {
	var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var imgObj = null;
	var src = null;
	var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
	var isCheck = true;
/*	for (var i = 0, len = imgs.length; i < len; i++) {
		imgObj = imgs[i];
		src = imgObj.src;
		if (!yougou_img_url_reg.test(src)) {
			if (isflag) { //如果为预览(显示非法链接图标)
				imgObj.src = basePath + "/yougou/images/outer_link.png";
				isCheck = false;
			}
		}
	}*/
	//校验area
	var areaHrefs=$("area", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var href = null;
	var yougou_href_url_reg = new RegExp('http://.+\\.yougou.com/.+', 'i');
	var isCheck = true;
	for (var j = 0, len = areaHrefs.length; j < len; j++) {
		href = areaHrefs[j].href;
		if (!yougou_href_url_reg.test(href)) {
				isCheck = false;
		}
	}
	return isCheck;
};
function hiddenMsgBox(){
	$("#msgdiv").hide();
}
goodsAdd.url={};
goodsAdd.url.descImgSelectorUrl=updateTaobaoItem.basePath + '/commodity/pics/selector.sc';
goodsAdd.prodSpec={};
goodsAdd.prodSpec.imgBtn_OnClick = function() {
	openwindow(goodsAdd.url.descImgSelectorUrl, 820, 560, '选择描述图片');
	
	//error_tip hide
	$('#prodDesc_error_tip').hide();
};

/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
goodsAdd.prodSpec.imgSelector_OnClose = function(imgUrls) {
	closewindow();
	if(imgUrls == null || imgUrls == '') return;
	var msg = '<br/>';
	var urls = imgUrls.split('&&&&&');
	$.each(urls, function(n, url){
		msg += formatString('<img src="{#imgUrl}" />',
		{
			imgUrl : url
		});
		msg += '<br/>';
	});
	updateTaobaoItem.prodSpec.editor.html(updateTaobaoItem.prodSpec.editor.html() + msg);
	goodsAdd.prodSpec.checkOuterLink();
};

/**
 * 校验商品描述中是否存在外链
 */
goodsAdd.prodSpec.checkOuterLink = function() {
	var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var imgObj = null;
	var src = null;
	var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
	var isCheck = true;
/*	for (var i = 0, len = imgs.length; i < len; i++) {
		imgObj = imgs[i];
		src = imgObj.src;
		if (!yougou_img_url_reg.test(src)) {
			if (isflag) { //如果为预览(显示非法链接图标)
				imgObj.src = basePath + "/yougou/images/outer_link.png";
				isCheck = false;
			}
		}
	}*/
	//校验area
	var areaHrefs=$("area", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var href = null;
	var yougou_href_url_reg = new RegExp('http://.+\\.yougou.com/.+', 'i');
	var isCheck = true;
	for (var j = 0, len = areaHrefs.length; j < len; j++) {
		href = areaHrefs[j].href;
		if (!yougou_href_url_reg.test(href)) {
				isCheck = false;
		}
	}
	return isCheck;
};

/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
function onImgSelected(imgUrl) {
	goodsAdd.prodSpec.imgSelector_OnClose(imgUrl);
}
