
    <script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 设置模块 &gt; 关联版式设置</p>
                    <form id="layoutForm" action="" method="post" enctype="multipart/form-data">
                    <div class="box-contain">
                        <div class="c-h-row">
                            <label>选择版式：</label>
                            <select name="type" id="layout_type">
                                <option value="1">自定义</option>
                                <option value="0" selected="selected">固定模版</option>
                            </select>
                            <div class="c-h-row-fl tpupdate">
                                <label class="ml10">插入商品：</label>
                                <input id="commodityList" name="commodityList" type="text" class="inputtxt" placeholder="输入商品编码，用“，”隔开" style="width: 300px;" />
                                <a id="importProd" class="button tmpSave" onclick="javascript:;"><span>生成版式</span></a>
                            </div>
                        </div>
                        <div class="c-h-row">
                            <label>选择商品：</label>
                            <select id="commoditySelect" onchange="isDisabled(this.value)" >
                                <option value="1">全部商品</option>
                                <option value="0" selected = "selected">部分商品</option>
                            </select>
                            <div class="c-h-row-fl partgoods" style="display:none;">
                            <input type="text" id="commodityNos" name="commodityNos"  class="inputtxt ml10" style="width:188px;" placeholder="输入商品编码，用“，”隔开" />
                            <a class="button"  id="picker" onclick="$('#commodityFile').click(); " ><span>浏览</span></a>
                            <input type="file" name="settingFile" id="commodityFile" style="display:none" >
                            <a class="ml10" href="javascript:;" onclick="downLoad()">下载Excel模板</a>&nbsp;
                            <span id="fileName"></span>
                            </div>
                            <textarea id="layoutHtml" name="layoutHtml" style="display:none"></textarea>
                        </div>
                        <div class="selectTemp">
                        
                        </div>
                        <div class="mt20 tc" style="line-height:25px; width:800px;">
                            <a class="button" id="saveBtn" onclick="saveSetting()" style="vertical-align: top;"><span>保存</span></a>
                            <a class="ml10" href="javascript:;" onclick="preview()">点击预览</a>
                        </div>
                    </div>
                    </form>
                </div>
            </div>

    <script type="text/javascript" src="${BasePath}/yougou/js/zclip/jquery.zclip.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/kindeditor.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/yguib.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/checkUrl.js"></script>
    <script>
	    $(function() {
	        $('#importProd').click(function() {
	            var tlist = $.trim($('#commodityList').val()).split(','),
	                tmpArr = [];
	          	
	            if(tlist.length!=8){
	            	ygdg.dialog.alert("输入的商品编码必须是8个！多个以英文逗号隔开");
	            	return false;
	            }   
	            
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
	            
	            $.ajax({
		    		async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data:{"commodityList":$('#commodityList').val()},
					url : "${BasePath}/layout_setting/fixedLayoutSetting.sc",  
					success : function(data) {				
						var commList = data.commList;
						var errorMsg = data.errorMsg;
						
						if(commList!=undefined && commList!=null && commList.length>0){
							tmpArr.push('<ul class="relation-goods-list clearfix">');
							for(var i=0;i<commList.length;i++){
								var commodity = commList[i];
								
								tmpArr.push('<li>');
				                tmpArr.push('<div class="goods-box">');
				                tmpArr.push('<a href="'+commodity.pageUrl+'" target="_blank">');
				                tmpArr.push('<img src="'+ commodity.imgUrl +'" class="goods-img" />');
				                tmpArr.push('</a>');				        
								tmpArr.push('<div class="goods-title clearfix"><span class="goods-price">');
								tmpArr.push('<p class="price_sc"> <em class="ygprc15 cmdtyPrice price_no'+ commodity.commodityNo +'" no="'+commodity.commodityNo+'"> ¥<i>'+ commodity.yougouPrice+'</i></em> </p> </span>');
								tmpArr.push('<a target="_blank" href="'+commodity.pageUrl+'" class="goods-buy">立即购买</a></div>');
				                // tmpArr.push('<div class="goods-title clearfix"><span class="goods-price">&yen;'+commodity.yougouPrice+'</span><a target="_blank" href="'+commodity.pageUrl+'" class="goods-buy">立即购买</a></div>');
				                tmpArr.push('</div>');
				                tmpArr.push('</li>');
							}
							 tmpArr.push('</ul>');
							  
	           				 $('.selectTemp').html(tmpArr.join(''));
						}
						
						if(errorMsg!=undefined && errorMsg!=null && errorMsg !=""){
							ygdg.dialog.alert(errorMsg+",无法获取商品信息");
						}
						
						
					},
					error:function(e){
						ygdg.dialog.alert("获取商品信息异常！");
					}    	
			    });
	            
	        });
	        
	         $("#layout_type").change(function(){
		    	var type = $(this).val();
		    	if("1"==type){
		    		window.location.href = "${BasePath}/layout_setting/go_layout_setting.sc?type="+type;
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
	    
	    // 提交保存版式设置
	    function saveSetting(){
	    	var isAll = $("#commoditySelect").val();
	    	var commodityNos = $("#commodityNos").val();
	    	var commodityFile = $("#commodityFile").val();
	    	var layoutHtml = $('.selectTemp').html().trim();

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
	    	if(layoutHtml.trim() == ""){
	    		ygdg.dialog.alert("请生成固定版式内容！");
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
	    	
	    	$("#layoutHtml").val(layoutHtml);
	    	$("#layoutForm").removeAttr("target");
	    	$("#layoutForm").submit();
	    }
	    
	     // 预览版式内容
	    function preview(){
	    	var layoutHtml = $('.selectTemp').html().trim();
	    	//版式内容
	    	if(layoutHtml =="" || layoutHtml ==null){
	    		ygdg.dialog.alert("请生成固定版式内容！");
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
	    	$("#layoutHtml").val($('.selectTemp').html());
			$("#layoutForm").submit();
			$("#layoutForm").removeAttr("target");
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

