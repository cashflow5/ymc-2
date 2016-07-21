	<div class="main_container relative">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 商品 &gt; 批量发布商品</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr">
						<span>批量发布商品</span>
					</li>
				</ul>
				<div class="tab_content">
	                <div class="search_box">
	                    <form id="editForm" action="#" method="post" enctype="multipart/form-data">
	                        <div class="clearfixed goodsImportStep">
	                            <h2>第一步：</h2>
	                            <span>
	                            <label style="width:95px;">商品分类：</label>
	                            <select id="firstLevel" style="width:120px;" name="categories">
	                                <option value="">请选择一级分类</option>
	                            </select>
	                            <select id="secondLevel" style="width:120px;" name="categories">
	                                <option value="">请选择二级分类</option>
	                            </select>
	                            <select id="thirdLevel" style="width:120px;" name="categories">
	                                <option value="">请选择三级分类</option>
	                            </select>
	                            </span>
	                            <span style="padding-left:10px;"><a href="javascript:;" onclick="javascript:downloadTemplate();return false;" class="f_blue">下载模板</a></span>
	                            <!-- 
	                            <span style="padding-left:30px;"><a href="javascript:;" onclick="javascript:;return false;" class="f_blue">发布商品规则</a></span>
	                             -->
	                        </div>
	                        <div class="clearfixed goodsImportStep">
	                            <h2>第二步：</h2>
	                            <span>
	                            <label style="width:120px;">商品导入文件：</label>
	                            <span class="fl" style="padding-left:5px;">
	                            	<input id="txtFileName" type="text" size="35" class="inputtxt" readonly="readonly" />
	                            </span>
	                            <span class="fl" style="padding-left:0px;">
		                            <a class="button"><span><div id="spanButtonPlaceHolder">控件加载中...</div></span></a>
	                            </span>
	                            <span class="fl" style="padding-left:50px;">
	                            	<a class="button" href="javascript:;" onclick="javascript:importData(this);return false;"><span id="progress">立即导入</span></a>
	                            </span>
	                            </span>
	                            <div style="clear: both;"></div>
				                <div id="sidebar" style="display: none;" class="grayTipsBox goodsImportTips">
				                    <div id="leftsidebar" style="display: inline; float: left;">
				                    </div>
				                    <div id="rightleftsidebar" style="display: inline; float: right;" class="f_hl">
				                    </div>
				                </div>
	                        </div>
	                    </form>
	                </div>
	                <div class="clearfixed goodsImportStep" style="border-bottom: none; padding-bottom: 0;">
	                    <h2>第三步：<span style="color:#ff6600;">商品图片上传请使用本页面"操作"列"上传图片"按钮,不需要按照款色编码重命名图片,支持描述图拖拽排序。</span></h2>
	                </div>
	                
	                
	                
					<div class="tb_dobox" style="margin-bottom: 2px;">
						<div class="dobox">
							<span class="fl" style="margin-right:5px;">
								<label><input onclick="javascript:selectAllUnauditedCommodity(this);" class="checkedall" type="checkbox" disabled="disabled" /> 全选</label>
							</span>
							<span class="fl">
								<a href="javascript:;" onclick="javascript:if(!$(this).hasClass('dis'))submitAudit();return false;" class="button dis"><span>提交审核</span></a>
							</span>
							<div id="rightleftsidebar" style="display: inline; float: right;" class="f_hl">
								提示：已成功导入的商品资料需要提交审核，并等待优购审核通过后，才能上架销售。
							</div>
						</div>
					</div>
	                <!--列表start-->
	                
	                 <div class="relative">
		                <div class="pos_page">
		                    <div class="page" id="posPage">
		                      
		                    </div>
	               		</div>
		                <div id="commodity_content">
		                    
						</div>
				   </div>			                   
	                <!--列表end-->
	                <!--帮助说明-->
	                <div class="goodsImportHelp">
	                    <strong>批量发布商品帮助说明：</strong><br />
	                    1.第一步：下载模板。
	                    <span class="f_gray">先选择好分类并下载模板，并确保您的计算机已安装“Office2007”及以上版本的Excel软件，如使用“Office2003”请先下载<a href="http://download.microsoft.com/download/6/5/6/6568c67b-822d-4c51-bf3f-c6cabb99ec02/FileFormatConverters.exe" target="_blank">Excel兼容包</a>。</span>
	                    <br />
	                    2.第二步：导入商品资料。
	                    <span class="f_gray">打开下载的模板，按照要求手工录入商品信息并保存。点击“选择”按钮选取编辑好的xlsx文件，然后点击“立即导入”。请确保您的计算机已安装<a href="http://get.adobe.com/cn/flashplayer" target="_blank">Flash</a>。</span>
	                    <br />
	                    3.第三步：导入商品图片和提交审核。
	                    <span class="f_gray">点击商品列表后面的“上传图片”按钮，选择对应的商品图片上传，商品状态变为“已上传”后，点击“提交审核”按钮。</span>
    					<div style="background:#f2f2f2;border:1px solid #ccc;color:	#FFA07A;padding:10px;">
							<p>说明：1.新功能已改成按单款商品上传图片(对图片名称不做限制)，不需要在本地先按照款号+类型+序号重命名角度图和描述图。</p>
							<p>&nbsp&nbsp&nbsp2.需要按照旧方式按款号本地重命名批量上传图片的,请点击展开以下功能进行操作。</p>
							<div style="margin-left:35px;,margin-top:5px;"><a href="javascript:void(0)" class="open" id="open-top">展开批量导入图片</a></div>
		                    <div id="image-tab" style="display:none;margin-top:5px;">
		                      <a class="button" style="margin-left:35px;" href="javascript:;" onclick="javascript:return importPic();"><span>批量导入商品图片</span></a>
		                    </div>
						</div>
	                </div>
	                <!--帮助说明end-->
	            </div>
				</div>
			</div>
		</div>
	</div>
<style>
.open{background: url('/yougou/images/taobao_pro_open.png') 95px 3px no-repeat;display:inline-block;width: 120px;text-align:left;cursor:pointer;}
.close{background: url('/yougou/images/taobao_pro_close.png') 95px 3px no-repeat;display:inline-block;width: 120px;text-align:left;cursor:pointer;}
</style>	
<script type="text/javascript" src="${BasePath}/yougou/js/ZeroClipboard.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/handlers.js"></script>
<script type="text/javascript">

$(function(){
		$("#open-top").live("click",function(){
			if($(this).attr("class")=="open"){
				$(".image-con").show();
				$("#open-top").removeClass("open");
				$("#open-top").addClass("close");
				$("#open-top").text("收起批量导入图片");
				$("#image-tab").show();
				
			}else if($(this).attr("class")=="close"){
				$(".image-con").hide();
				$("#open-top").removeClass("close");
				$("#open-top").addClass("open");
				$("#open-top").text("展开批量导入图片");
				$("#image-tab").hide();
			}
		});
});

var _page=0;
$('#posPage a').live('click',function(){
	var _this=$(this);
	_this.addClass('curr').siblings().removeClass('curr');
	_page=parseInt(_this.text());
	 queryPage(_page);
	var _pg=$('#table_'+_page);
	if(_pg[0]){
		$('html,body').animate({scrollTop:_pg.offset().top},200);
	}
});
		
$(window).scroll(function(){
	if($('#commodity_content').offset().top<$(window).scrollTop()){
		$('.pos_page').css({'position':'fixed'});
	}else{
		$('.pos_page').css({'position':'absolute'});
	}



	$('.list_table').each(function(){
		if($('.list_table')[0]){
			var _this=$(this);
			var _idx=0;
			if(_this.attr('id')){
				_idx=parseInt(_this.attr('id').split('_')[1]);
			}
			setTimeout(function(){
			if($('#table_'+_idx)[0] && $(window).scrollTop()>$('#table_'+_idx).offset().top-10){
				//console.log(_idx);
				$('#posPage a').eq(_idx-1).addClass('curr').siblings().removeClass('curr');
			}
			},600);
		}
	});
});

/**
 *检查是否含有财务千分位分隔符
 *当totalRows>1000时默认格式会加财务千分位逗号
 *例如11,628，在js当做字符串处理
 */
function ck(txt){
  if(txt.indexOf(',')>-1){
     return true;
	}
	 return false;
}

// 加载商品结构
function loadStruct(elementId, ownerId) {
	var element = $('#' + elementId);
	var owner = $('#' + ownerId);
	$.ajax({
		type: 'post',
		url: '${BasePath}/commodity/import/struct.sc',
		dataType: 'json',
		data: 'structName=' + (element.find('option:selected').attr('structName') || '') + '&rand=' + Math.random(),
		beforeSend: function(jqXHR) {
		},
		success: function(data, textStatus, jqXHR) {
			owner.get(0).options.length = 1;
			owner.append($(data).map(function(){ return '<option catNo="' + this.no + '" structName="' + this.structName + '" value="' + this.id + '">' + this.catName + '</option>' }).get().join('')).reJqSelect();
		},
		complete: function(jqXHR, textStatus) {
		},
		error: function(jqXHR, textStatus, errorThrown) {
		}
	});
}
// 加载未审核商品
var page=0;
var firstloadflag=true;
function loadUnauditedCommodity(pageNo) {
	if(page<Number(pageNo)+1){
		var i=1;
        for (i=page+1;i<Number(pageNo)+1;i++){
          	$('#commodity_content').append('<div id="commodity_content_'+i+'" class="clearfixed goodsImportStep"></div>');
        }
        page=pageNo;
	}
	$.ajax({
		type: 'post',
		url: '${BasePath}/commodity/import/list.sc',
		dataType: 'html',
		data: 'rand=' + Math.random() + '&page=' + pageNo,
		beforeSend: function(jqXHR) {
			$('#commodity_content_'+pageNo).html('<table class="list_table" style="width: 100%; margin-top: 0;"><thead><tr><th style="width:3%;"></th><th style="width:10%;">商品名称</th><th style="width:5%;">上传图片状态</th><th style="width:5%;">商品款号</th><th style="width:5%;">款色编码</th><th style="width:5%;">颜色</th><th style="width:5%;">品牌</th><th style="width:5%;">年份</th><th style="width:5%;">市场价格</th><th style="width:5%;">销售价格</th><th style="width:5%;">操作</th></tr></thead><tbody><tr><td colspan="11"><img src="${BasePath}/yougou/js/ygdialog/skins/icons/loading.gif" width="16" height="16" style="vertical-align:middle;"/><font color="#006600">数据载入中...</font></td></tr></tbody></table>');
		},
		success: function(data, textStatus, jqXHR) {
		$('#commodity_content_'+pageNo).replaceWith(data);
		if(firstloadflag){
		//获取页码数和数据总数
		var rowCount=$("#rowCount_1").val();
		var pageSize=$("#pageSize_1").val();
	    /**
	     *如果totalRows>=1000,则去除财务分隔符逗号
	     *否则转换为数字类型
	     */
	    if(ck(rowCount)) {
		  rowCount = split(rowCount);
	    } else {
		  rowCount = Number(rowCount);
	    }
	    //计算页数
	    var totalPage = Math.ceil(rowCount / pageSize);
		//根据页码数输出左侧浮动条
	    for (var i=1;i<Number(totalPage)+1;i++){
	     if(i==1){
	       if(rowCount>pageSize){
		    $('#posPage').append('<a class="curr" href="javascript:;">'+i+'</a>');
		   }else{
		   //如果只有1页就隐藏左侧浮动条
		    $('#posPage').append('<a class="curr" href="javascript:;" style="display:none">'+i+'</a>');
		   }
		 }else{
		  $('#posPage').append('<a href="javascript:;">'+i+'</a>');
		 }
	    }
		firstloadflag=false;
		//$('.pos_page').css({'margin-top':-$('#posPage a').length*20/2});
		}else{
			var _pg=$('#table_'+pageNo);
			if(_pg[0]){
			$('html,body').animate({scrollTop:_pg.offset().top},200);
			}
		}
		},
		complete: function(jqXHR, textStatus) {
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$('#commodity_content_'+pageNo).html('<table class="list_table" style="width: 100%; margin-top: 0;"><thead><tr><th style="width:3%;"></th><th style="width:10%;">商品名称</th><th style="width:5%;">上传图片状态</th><th style="width:5%;">商品款号</th><th style="width:5%;">款色编码</th><th style="width:5%;">颜色</th><th style="width:5%;">品牌</th><th style="width:5%;">年份</th><th style="width:5%;">市场价格</th><th style="width:5%;">销售价格</th><th style="width:5%;">操作</th></tr></thead><tbody><tr><td colspan="11"><font color="#ff0000">数据载入异常：ERROR</font></td></tr></tbody></table>');
		}
	});
	
}
// 全选未审核商品
function selectAllUnauditedCommodity(checkbox) {
	$('.list_table > tbody').find('input[name="commodityNo"]').filter(function(){ return !this.disabled; }).attr('checked', checkbox.checked);
}
// 单个提交审核
function singleSubmitAudit(commodityNo) {
	$('.list_table > tbody').find('input[name="commodityNo"]').attr('checked', false).filter(function(){ return this.value == commodityNo; }).attr('checked', true);
	submitAudit();
}
// 提交审核
function submitAudit() {
	var rowSet = $('.list_table > tbody').find('input[name="commodityNo"]').filter(function(){ return this.checked && !this.disabled; });
	if (rowSet.size() <= 0) {
		alert('请您先选择需要提交审核的商品再进行操作!');
		return false;
	}
	if (window.confirm('您确定将选择的商品提交审核吗?')) {
		$.post("${BasePath}/commodity/checkSensitiveWordByAudit.sc",
				'rand='+Math.random()+'&'+rowSet.map(function(){ return 'commodityNo=' + this.value }).get().join('&'),
			function(json){
				var html = "";
				$.each(json.sensitiveResult,function(i,n){
					html+="款色编码："+n.supplierCode+"&nbsp;检测到敏感词：<span class='cred'>"+n.sensitiveWord+"</span><br/>";
				});
				
				if($.trim(html)!=''){
					$.dialog({
					    id: 'sensitiveDialog',
					    title:'检测到敏感词提醒',
					    content: "<div style='width:450px; height:120px; OVERFLOW: auto;'><p class='cred'><b>检测到敏感词，建议处理后再继续</b></p>"+html+"<div>",
					    width:450,
					    lock: true,
					    resize: false,
					    button: [
					        {
					            name: '取消',
					            callback: function () {
					            	$.post("${BasePath}/commodity/logSensitiveWordByAudit.sc",
                                    	'followOperate=0&rand=' + Math.random() + '&' + rowSet.map(function(){ return 'commodityNo=' + this.value }).get().join('&'));
					            },
					            focus: true
					        },{
					            name: '确认继续',
					            callback: function(){
					            	submitForm(1,rowSet);
						        }
					        }
					    ]
					});
				}else{
					submitForm(0,rowSet);
				}
		},"json");
	}
}

function submitForm(flag,rowSet){
	$.ajax({
		type: 'post',
		url: '${BasePath}/commodity/import/audit.sc',
		dataType: 'json',
		data: (flag==1?'followOperate=1&':'')+'rand=' + Math.random() + '&' + rowSet.map(function(){ return 'commodityNo=' + this.value + '&supplierCode=' + this.getAttribute('code') }).get().join('&'),
		beforeSend: function(jqXHR) {
		},
		success: function(data, textStatus, jqXHR) {
			var spanMessage;
			$.each(data, function(i){
				if ($.trim(this.auditMessage) == 'true') {
					$('#td_' + this.commodityNo).append('<font style="font-size: 12px; color: green;"><br/>提交审核成功</font>').parent().fadeOut(3000, function(){ $(this).remove(); }).find('td:last,td:first').html('');
				} else {
					$('#td_' + this.commodityNo).find('font').remove();
					$('#td_' + this.commodityNo).append('<font style="font-size: 12px; color: red;"><br/>提交审核失败：' + this.auditMessage + '</font>');
				}
			});
		},
		complete: function(jqXHR, textStatus) {
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('提交审核失败');
		}
	});
}

// 删除商品
function deleteUnauditedCommodity(commodityNo, supplierCode) {
	if (confirm('确定删除该商品吗？')) {
		$.ajax({
			type: 'post',
			url: '${BasePath}/commodity/import/delete.sc',
			dataType: 'html',
			data: 'rand=' + Math.random() + '&commodityNo=' + (commodityNo || '') + '&supplierCode=' + (supplierCode || ''),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data) == 'true') {
					$('input[value="' + commodityNo + '"]').parent().parent().fadeOut(1000, function(){ $(this).remove(); });
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('删除商品失败');
			}
		});
	}
}
// 下载导入商品模板
function downloadTemplate() {
	var errors = $('[name="categories"]').filter(function(){ return $(this).val() == '' });
	if (errors.size() > 0) {
		alert(errors.eq(0).children(':first').text());
		return false;
	}
	$('.dynamicValue').remove();
	var selectedOption = $('#thirdLevel').find('option:selected');
	var catNames = ($('#firstLevel').find('option:selected').text() + '_' + $('#secondLevel').find('option:selected').text() + '_' + selectedOption.text());
	$('#editForm').append('<input class="dynamicValue" type="hidden" name="catNames" value="' + catNames + '"/>');
	$('#editForm').append('<input class="dynamicValue" type="hidden" name="catNo" value="' + selectedOption.attr('catNo') + '"/>');
	$('#editForm').append('<input class="dynamicValue" type="hidden" name="structName" value="' + selectedOption.attr('structName') + '"/>');
	$('#editForm').append('<input class="dynamicValue" type="hidden" name="catName" value="' + selectedOption.text() + '"/>');
	$('#editForm').append('<input class="dynamicValue" type="hidden" name="id" value="' + selectedOption.val() + '"/>');
	$('#editForm').attr('action', '${BasePath}/commodity/import/template/download.sc').submit();
}
// 导入商品数据
function importData(button) {
	if ($(button).hasClass('dis')) {
		return false;
	}
	if ($('#txtFileName').val() == '') {
		alert('请选择商品导入文件');
		return false;
	}
	if (!$('#txtFileName').val().match(/\.xlsx$/g)) {
		alert('请确认商品导入文件格式是否为xlsx');
		return false;
	}
	$('#txtFileName').val('');
	swfu.startUpload();
}
// 导入商品图片
function importPic() {
	if ($('.list_table > tbody > tr').size() <= 0) {
		alert('请先导入商品资料');
		return false;
	}
	ygdgDialog.open('${BasePath}/commodity/pics/upload/ready.sc', {
		width: 800,
		height: 600,
		title: '批量导入商品图片',
		close: function(){
			setTimeout(function(){refreshpage();},2000); 
		}
	});
	return false;
}

//单个商品导入图片
function singleImportPic(commodityNo) {
	var url = "${BasePath}/commodity/pics/upload/singleReady.sc?commodityNo="+commodityNo;
	ygdgDialog.open(url, {
		id:"imgDialog",
		width: 845,
		height: 600,
		title: '商品图片上传',
		close: function(){
			refreshpage();
		}
	});
	return false;
}

function closeDialog(){
	ygdg.dialog({id: "imgDialog"}).close();
}



function onImgSelected(img) {
	alert(img.imgUrl);
	alert(img.imgName);
}
var swfu;
var swfu_post_params = { merchantCode: "${(merchantUsers.supplier_code)!''}", 
		merchantName: "${(merchantUsers.supplier)!''}",
		operator:"${(merchantUsers.login_name)!''}" };
$(document).ready(function(){
	//openwindow('${BasePath}/commodity/pics/selector.sc', 800, 600, '选择图片');
	// 默认加载一级分类
	loadStruct('firstLevel', 'firstLevel');
	// 默认加载未审核商品
	loadUnauditedCommodity(1);
	// 一级分类注册联动事件
	$('#firstLevel').change(function(){
		if ($(this).val() != '') {
			loadStruct('firstLevel', 'secondLevel');
		}
	});
	// 二级分类注册联动事件
	$('#secondLevel').change(function(){
		if ($(this).val() != '') {
			loadStruct('secondLevel', 'thirdLevel');
		}
	});
	// 初始化SWFUpload
	swfu = new SWFUpload({
		flash_url : "${BasePath}/swfupload/swfupload.swf",
		upload_url: "${BasePath}/commodity/import.sc",
		file_size_limit : "6 MB",
		file_types : "*.xlsx",
		file_types_description : "All Files",
		file_upload_limit : "0",
		file_queue_limit : "1",
		post_params: swfu_post_params,
		custom_settings : {
			progressTarget : "fsUploadProgress"
		},
		debug: false,
		// Button settings
		button_image_url: "${BasePath}/yougou/images/blank.gif",
		button_width: "27",
		button_height: "25",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '选择',
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		button_text_top_padding: 3,
		button_text_left_padding: 0,
		// The event handler functions are defined in handlers.js
		file_queue_error_handler : function(file, errorCode, message){
			$('#leftsidebar').html('<font color="red">' + getSWFUploadErrorMessage(file, errorCode, message).status + '</font>').parent().show();
		},
		file_dialog_start_handler : function(){
			$('#txtFileName').val('');
			this.cancelUpload();
		},
		file_queued_handler : function(file){
			$('#txtFileName').val(file.name);
		},
		upload_error_handler : function(file, errorCode, message){
			if (SWFUpload.UPLOAD_ERROR.FILE_CANCELLED != errorCode) {
				$('#leftsidebar').html('<font color="red">' + getSWFUploadErrorMessage(file, errorCode, message).debug + '</font>').parent().show();
				$('#progress').html('立即导入').parent().toggleClass('dis');
			}
		},
		upload_start_handler : function(file){
			$('#progress').html('已上传0%').parent().toggleClass('dis');
		},
		upload_progress_handler : function(file, bytesLoaded, bytesTotal){
			$('#progress').html('已上传' + Math.ceil((bytesLoaded / bytesTotal) * 100) + '%');
		},
		upload_success_handler : function(file, serverData){
			try {
				var o = eval('(' + serverData + ')');
				var htmlContent = '';
				if (o.error) {
					htmlContent += ('<font color="red">' + o.error + '</font>');
				} else {
					var html = "";
					$.each(o.sensitiveResult,function(i,n){
						html+="款色编码："+n.supplierCode+"&nbsp;检测到敏感词：<span class='cred'>"+n.sensiveWord+"</span><br/>";
					});
					
					if($.trim(html)!=''){
						$.dialog({
						    id: 'sensitiveDialog',
						    title:'检测到敏感词提醒',
						    content: "<div style='width:450px; height:100px; OVERFLOW: auto;'><p class='cred'><b>检测到敏感词，建议处理后再继续</b></p>"+html+"<div>",
						    width:450,
						    lock: true,
						    resize: false,
						    button: [
						        {
						            name: '立即去修改',
						            callback: function () {
						            	window.location.href = "${BasePath}/commodity/goWaitSaleSensitiveCommodity.sc";
						            },
						            focus: true
						        },
						        {
						            name: '关闭'
						        }
						    ]
						});
					}
					htmlContent += ('成功导入<font id="succeed" color="red">&nbsp;' + o.succeed + '&nbsp;</font>条记录，失败<font id="fail" color="red">&nbsp;' + o.fail + '&nbsp;</font>条。');
					htmlContent += (o.uuid ? '<a class="f_blue" id="linker" href="${BasePath}/commodity/import/error/download.sc?uuid=' + o.uuid + '" target="_blank">失败明细下载</a>' : '');
					page=0;
					$('#commodity_content').empty();
					loadUnauditedCommodity(1);
				}
				$('#leftsidebar').html(htmlContent).parent().show();
				$('#progress').html('立即导入').parent().toggleClass('dis');
			} catch(e) {
				this.uploadError(file, '-1', '文件上传失败');
			}
		}
	});
});
</script>


