
    <script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js?${style_v}"></script>
    

            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 设置模块 &gt; 关联版式设置</p>
                    <form id="layoutForm" action="" method="post" enctype="multipart/form-data">
                    <div class="box-contain">
                        <div class="c-h-row">
                            <label>选择版式：</label>
                            <select name="type" id="layout_type" name="type">
                                <option value="1" selected="selected">自定义</option>
                                <option value="0">固定模版</option>
                            </select>
                            <select style="margin-left:10px;" onchange="getTemplate(this.value)" id="templateSelect" name="id">
                            	<option value="" >请选择</option>
                                <#if tempList??>
	                                <#list tempList as temp>
	                                	<option value="${temp.id!''}">${temp.layoutName!''}</option>
	                                </#list>
                                </#if>
                            </select>
                            <div class="c-h-row-fl opbutton">
                                <a class="button addTmp" onclick="javascript:;"><span>添加版式</span></a>
                                <a class="button updateTmp" onclick="javascript:;"><span>修改版式</span></a>
                                <a class="button" onclick="optLayoutTemplate('delete')"><span>删除版式</span></a></div>
                            <div class="c-h-row-fl tpupdate" style="display:none;">
                                <label class="ml10">选择版式：</label>
                                <input type="text" id="layoutName" class="inputtxt" />
                                <a class="button tmpSave" onclick="optLayoutTemplate();"><span>保存</span></a>
                                <a class="button tmpCancel" onclick="javascript:;"><span>取消</span></a>
                                <span class="ml10">最长8个汉字！</span>
                            </div>
                        </div>
                        <div class="c-h-row">
                            <label>选择商品：</label>
                            <select id="commoditySelect" onchange="isDisabled(this.value)">
                                <option value="1">全部商品</option>
                                <option value="0" selected="selected">部分商品</option>
                            </select>
                            <div class="c-h-row-fl partgoods" style="display:none;">
	                            <input type="text" id="commodityNos" name="commodityNos" class="inputtxt ml10" style="width:188px;" placeholder="输入商品编码，用“，”隔开" />
								<a class="button" id="picker" onclick="$('#commodityFile').click(); "><span>浏览</span></a>
	                            <input type="file" name="settingFile" id="commodityFile" style="display:none" >
	                            <a class="ml10" href="javascript:;" onclick="downLoad()">下载Excel模板</a>&nbsp;
	                            <span id="fileName"></span>
                            </div>
                        </div>
                        <div class="c-h-row f_gray">
                           	 自定义版式：宽度为790px,高度不限,不允许有外链接图片
                        </div>
                        <div>
                            <textarea id="customerTemplate" cols="30" rows="10"></textarea>
                            <textarea id="layoutHtml" name="layoutHtml" style="display:none"></textarea>
                        </div>
                        <div class="mt20 tc" style="line-height:25px; width:800px;">
                            <a class="button" id="saveBtn" onclick="saveSetting()" style="vertical-align: top;"><span>保存</span></a>
                            <a class="ml10" href="javascript:;" onclick="preview();">点击预览</a>
                        </div>
                    </div>
                    </form>
                </div>
            </div>

    <script type="text/javascript" src="${BasePath}/yougou/js/zclip/jquery.zclip.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/kindeditor.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/yguib.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/checkUrl.js?${style_v}"></script>
    <script>
    	// 版式操作标记，默认新增
    	var tempOptFlag = "add";
    	// 文本编辑器对象
    	var editor;
    	 KindEditor.ready(function(K) {
	            editor = K.create('#customerTemplate', {
	                width: '800px',
	                height: '410px',
	                resizeType: 0,
	                allowPreviewEmoticons: false,
	                allowImageUpload: false,
	                allowFlashUpload: false,
	                allowMediaUpload: false,
	                allowFileUpload: false,
	                newlineTag: 'br',
	                htmlTags: {
	                    font: ['color', 'size', 'face', '.background-color'],
	                    span: ['style'],
	                    div: ['class', 'align', 'style'],
	                    table: ['class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'style'],
	                    'td,th': ['class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor', 'style'],
	                    a: ['class', 'href', 'target', 'name', 'style'],
	                    embed: ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality',
	                        'style', 'align', 'allowscriptaccess', '/'
	                    ],
	                    img: ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'style', '/', 'usemap'],
	                    hr: ['class', '/'],
	                    br: ['/'],
	                    'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6': ['align', 'style'],
	                    'tbody,tr,strong,b,sub,sup,em,i,u,strike': [],
	                    'map': ['id', 'name'],
	                    'area': ['alt', 'coords', 'href', 'nohref', 'shape', 'target']
	                },
	                items: [
	                    'source', '|',
	                    'undo', 'redo', '|',
	                    'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|',
	                    'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
	                    'formatblock', 'fontname', 'fontsize', '|',
	                    'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
	                    'image','flash', 'media', 'insertfile',
	                    'table', 'hr', 'anchor', 'link', 'unlink'
	                ]
	            });
	        });
	        
	    $(function() {
	    	
	       
			
			// 点击新增，修改 版式按钮
	        $('.addTmp, .updateTmp').on('click', function() {
	            $('.tpupdate').show();
	            $('.opbutton').hide();
	            if($(this).hasClass("updateTmp")){
	            	tempOptFlag = "update";
	            }else{
	            	tempOptFlag = "add";
	            }
	        });
	        
	        //点击保存版式按钮
	        /*$('.tmpSave').on('click', function() {
	        	var layoutName = $("#layoutName").val();
	        	if(layoutName.gblen()<=16 && layoutName.gblen()>0){
		            $('.tpupdate').hide();
		            $('.opbutton').show();
	            }
	        });*/
	        
	        // 点击取消，清除隐藏新增相关按钮
	        $('.tmpCancel').on('click', function() {
	        	$("#layoutName").val("");
	        	
	            $('.tpupdate').hide();
	            $('.opbutton').show();

	        });
	        
	        // 版式类型change事件
	        $("#layout_type").change(function(){
		    	var type = $(this).val();
		    	if("0"==type){
		    		window.location.href = "${BasePath}/layout_setting/go_layout_setting.sc?type=0";
		    	}
	    
	   		});
	   		
	   		// 商品编码输入框change事件
	   		$("#commodityNos").change(function(){
	   			// 输入了商品编码不能再上传excel文件
	   			if($(this).val()!=null && $(this).val()!=""){
	   				$("#commodityFile").attr("disabled",true);
	   				$("#picker").removeAttr("onclick");
	   			}else{
	   				$("#commodityFile").attr("disabled",false);
	   				$("#picker").attr("onclick","$('#commodityFile').click();");
	   			}
	   			
	   			var checked = checkCommodityNo($(this).val());
	   			if(!checked){
	   				$(this).focus();
	   				return false;
	   			}
	   			
	   		});
	   		
	   		
	   		// 商品编码excel上传change事件
	   		$("#commodityFile").change(function(){
	   			var fileName = $(this).val();
	   			var ext = fileName.substring(fileName.lastIndexOf(".")+1);
	   			if(ext!="xlsx"){
	   				ygdg.dialog.alert("请导入下载的07版Excel模板文件，后缀为xlsx!");
	   				return false;
	   			}
	   			// 输入了商品编码不能再上传excel文件
	   			if($(this).val()!=null && $(this).val()!=""){
	   				$("#commodityNos").attr("disabled",true);
	   			}else{
	   				$("#commodityNos").attr("disabled",false);
	   			}
	   			$("#fileName").html($(this).val());
	   		});
	   		
	   		//是否禁用商品编码输入框及文件导入
	   		isDisabled($("#commoditySelect").val());
	   		
	   		// 是否下载导入失败的商品编码Excel 文件
	   		var errorCommodityNos = "${errorCommodityNos!''}";
	   		if(errorCommodityNos!=""){
	   			var timer = window.setTimeout(function(){
	   				var url = "${BasePath}/layout_setting/downLoadErrorCommodityNos.sc";
		   			//模拟Form表单提交下载Excel		    		
		    		var form = $("<form>");  
			        form.attr('style', 'display:none');  
			        form.attr('target', '');  
			        form.attr('method', 'post');  
			        form.attr('action', url);  
			  
			        var input1 = $('<textarea>');  
			        input1.attr('type', 'hidden');  
			        input1.attr('name', 'errorCommodityNos');  
			        input1.attr('value', errorCommodityNos);  
			  
			        $('body').append(form);  
			        form.append(input1);  			          
			        form.submit();  
			        form.remove();  
		    		window.clearTimeout(timer);  			
	   			},2000);	   				   			
	   		}
	   		
	    });
	    
	    // 当选择所有商品时，商品编码输入框及上传文件按钮禁用
	    function isDisabled(commodityType){
	    	if(commodityType=="1"){
	    		$("#fileName").html("");
	    		$("#commodityNos").val("");
	    		$('.partgoods').hide();
	    		$("#commodityNos,#commodityFile").attr("disabled",true);
	    		
	    	}else{
	    		$('.partgoods').show();
	    		$("#commodityNos,#commodityFile").attr("disabled",false);
	    	}
	    }
	    
	    //下载指定商品excel 模板
	    function downLoad(){
	    	var url = "${BasePath}/layout_setting/downLoadLayoutSettingExcel.sc";
	    	//模拟Form表单提交下载Excel
	    	$('<form action="' + url + '" method="post">' + '</form>').appendTo('body').submit().remove();
	    	
	    }
	    
	    // 自定义版式操作
	    function optLayoutTemplate(isDelete){
	   	    var requestdata = {};
	   	    //新增或者修改
	    	if(isDelete == null){
		    	var layoutName = $("#layoutName").val();
		    	if(layoutName == ""){
		    		ygdg.dialog.alert("请输入版式名称");
		    		return false;
		    	}
		    	
		    	if(layoutName.gblen()>16){
		    		ygdg.dialog.alert("版式名称长度不能超过8个汉字！");
		    		return false;
		    	}
		    	
		    	requestdata.type = $("#layout_type").val();
		    	requestdata.layoutName = layoutName;
		    	// 默认新增url
		    	var url = "${BasePath}/layout_setting/addLayoutTemplate.sc";
		    	if(tempOptFlag == "update"){
		    		var id = $("#templateSelect").val();
		    		if(id=="" || id==null){
		    			ygdg.dialog.alert("请选择版式！");
		    			return false;
	    			}
		    		url = "${BasePath}/layout_setting/updateLayoutTemplate.sc";
		    		requestdata.id = id;
		    	}
		    	
		    	ajaxOptTemplate(url,requestdata);
		    // 删除	
	    	}else if(isDelete == "delete"){
	    		var id = $("#templateSelect").val();
	    		if(id=="" || id==null){
		    			ygdg.dialog.alert("请选择版式！");
		    			return false;
		    	}
		    	
	    		ygdg.dialog.confirm("删除版式后，前期的版式设置将会失效，确认删除吗？",function(){	    	
	    			requestdata.id = id;
		    		url = "${BasePath}/layout_setting/deleteLayoutTemplate.sc";
	    			ajaxOptTemplate(url,requestdata);
	    			//删除模板后清除编辑器中内容
	    			editor.html("");
	    		});	    			    		
	
	    	}
	    	
	    
	    }
	    
	    // ajax 访问后台操作版式
	    function ajaxOptTemplate(url,requestdata){
	    	$.ajax({
	    		async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				data:requestdata,
				url : url,  
				success : function(data) {
					if(data.msg == "success"){						
						$("#layoutName").val("");
						refeshTemplate();
						ygdg.dialog.alert("操作成功");
						 $('.tmpCancel').click();
					}else{
						ygdg.dialog.alert("操作异常！");
					}
				},
				error:function(e){
					ygdg.dialog.alert("操作异常！");
				}    	
	    	});
	    
	    }
	    
	    // 扩展String 对象，获取字符长度，英文字母长度为1，中文长度为2
	    String.prototype.gblen = function() {    
		    var len = 0;    
		    for (var i=0; i<this.length; i++) {    
		        if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94) {    
		             len += 2;    
		         } else {    
		             len ++;    
		         }    
		     }    
		    return len;    
		} 
		
		//刷新页面自定义版式下拉选项
		function refeshTemplate(){
			var tempVal = $("#templateSelect").val();
			$.ajax({
	    		async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				url : "${BasePath}/layout_setting/queryLayoutTemplate.sc",  
				success : function(data) {
					var tempList = data.tempList;
					if(tempList.length>0){
						var optStr = "<option value=''>请选择</option>";
						for(var i=0;i<tempList.length;i++){
							var temp = tempList[i]
							
							if(temp.id==tempVal){
								optStr += "<option selected='seleclted' value='"+temp.id+"' >"+temp.layoutName+"</option>";
							}else{
								optStr += "<option value='"+temp.id+"' >"+temp.layoutName+"</option>";
							}
						}
						$("#templateSelect").html("");
						$("#templateSelect").html(optStr);
						//重新渲染下拉选择
						$("#templateSelect").reJqSelect();
					}
				},
				error:function(e){
					ygdg.dialog.alert("刷新版式选项异常！");
				}    	
	    	});
		}
		
		// 选择自定义版式后加载该版式html 至编辑器内
		function getTemplate(templateId){
		
			//if(confirm("当前页面内容尚未保存，确定切换吗？")){
			
				if(templateId!=""){
					$.ajax({
			    		async : false,
						cache : false,
						type : 'POST',
						dataType : "json",
						data:{"id":templateId},
						url : "${BasePath}/layout_setting/queryLayoutTemplateById.sc",  
						success : function(data) {
							var template = data.temp;
							if(template !=null && template !=undefined && template !=""){
								editor.html(template.layoutHtml);
								//$("#customerTemplate").html(template.layoutHtml);
							}else{
								editor.html("");
							}
							
						},
						error:function(e){
							ygdg.dialog.alert("获取选择版式内容异常！");
						}    	
			    	});
		    	}	    	
	    	//}
			
		}
		
		// 提交保存版式设置
	    function saveSetting(){
	    	var isAll = $("#commoditySelect").val();
	    	var commodityNos = $("#commodityNos").val();
	    	var commodityFile = $("#commodityFile").val();
	    	var layoutHtml = editor.html().trim();
	    	//alert($("#commodityNos").val()+"=="+$("#commodityFile").val());
	    	
	    	if($("#templateSelect").val() == ""){
	    		ygdg.dialog.alert("请选择版式！");
		    	return false;
	    	}
	    	
	    	// 选择部分商品
	    	if(isAll == "0"){
		    	if(commodityNos == "" && commodityFile == ""){
		    		ygdg.dialog.alert("请输入商品编码,或者上传Excel 文件！");
		    		return false;
		    	}
	    	}
	    	
	    	// 校验商品编码
	    	if(commodityNos !=""){
	    		var tlist = $.trim($('#commodityNos').val()).split(',');
	    		var flag = "Y";
	            $(tlist).each(function(i, item) {
	               	var commodityCode = $.trim(item);
	               	if(commodityCode==""){               	
	               		flag = "N";
	               		return false;
	               	}
	            });
	            if(flag=="N"){
	            	ygdg.dialog.alert("商品编码不能为空字符串");
	            	return false;
	            }	            
	    		var checked = checkCommodityNo(commodityNos);
	   			if(!checked){
	   				$("#commodityNos").focus();
	   				return false;
	   			}
	    	}
	    
	    	//版式内容
	    	if(layoutHtml ==""){
	    		ygdg.dialog.alert("请输入自定义版式内容！");
		    	return false;
	    	}
	    	
	    	//版式内容是否包含外链接
	    	if(!checkLayoutHtml(layoutHtml)){
	    		ygdg.dialog.alert("版式内容含有外链接！");
		    	return false;
	    	}
	    	//所有商品
	    	if(isAll == "1"){
	    		$("#layoutForm").attr("action","${BasePath}/layout_setting/layoutSettingForAllCommoditys.sc");
	    	// 部分商品 	
	    	}else if(commodityNos!=""){
	    		$("#layoutForm").attr("action","${BasePath}/layout_setting/layoutSettingForSomeCommoditys.sc");
	    	// 文件上传	
	    	}else if(commodityFile!=""){
	    		$("#layoutForm").attr("action","${BasePath}/layout_setting/importLayoutSettingExcel.sc");
	    	}
	    	
			if(layoutHtml.indexOf("width:790px;overflow:hidden;") < 0 ){				
				$("#layoutHtml").val('<div style="width:790px;overflow:hidden;">'+layoutHtml+'</div>');
			}else{
				$("#layoutHtml").val(layoutHtml);
			}
	    	$("#layoutForm").removeAttr("target");
	    	$("#layoutForm").submit();
	    }
	    
	    // 校验编辑器内是否有外链接
	    function checkLayoutHtml(layoutHtml){
	    	var checkFlag = true;
	    	if(layoutHtml!=""){
		    	$(layoutHtml).find("a").each(function(index,obj){
		    		
		    		var url = $(obj).attr("href");
		    		var isChecked = checkYGUrl(url);
		    		//alert(url+"----"+isChecked);
		    		if(!isChecked){
		    			checkFlag = false;
		    			return false;
		    		}	        
		    	});
	    	}
	    	return checkFlag;
	    }
	    
	    // 预览版式内容
	    function preview(){
	    	var layoutHtml = editor.html().trim();
	    	if(layoutHtml.indexOf("width:790px;overflow:hidden;") < 0 ){
				layoutHtml = '<div style="width:790px; overflow:hidden;">'+layoutHtml+'</div>';
			}
	    	//版式内容
	    	if(layoutHtml==""){
	    		ygdg.dialog.alert("请输入自定义版式内容！");
		    	return false;
	    	}
	    	
	    	//版式内容是否包含外链接
	    	if(!checkLayoutHtml(layoutHtml)){
	    		ygdg.dialog.alert("版式内容含有外链接！");
		    	return false;
	    	}
	    	var url = "${BasePath}/layout_setting/previewLayoutSetting.sc";
	    	$("#layoutForm").attr("target","_blank");
	    	$("#layoutForm").attr("action",url);
	    	$("#layoutHtml").val(layoutHtml);
			$("#layoutForm").submit();
			$("#layoutForm").removeAttr("target");
	    }
	    
	    //校验输入的商品编码是否存在且为当前商家的商品
	    function checkCommodityNo(commodityNos){
	    	var checked = true;
	    	$.ajax({
		    		async : false,
					cache : true,
					type : 'POST',
					dataType : "json",
					data:{"commodityNos":commodityNos},
					url : "${BasePath}/layout_setting/checkCommodityNo.sc",  
					success : function(data) {
						var errorMsg = data.errorMsg;
						if(errorMsg != null && errorMsg != "" && errorMsg != undefined){
							ygdg.dialog.alert("商品编码:"+errorMsg+" 不存在或者不是你的商品，请填写正确的商品编码！");
							checked = false;
						}
						return checked;
					},
					error:function(e){
						ygdg.dialog.alert("校验输入的商品编码异常！");
					}    	
		    	});
		    return checked;	
		    	
	    }
	    
	
    </script>

