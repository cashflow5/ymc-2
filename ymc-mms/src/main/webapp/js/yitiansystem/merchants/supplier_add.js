$(function(){
	
	//定义默认数据
	var ar = ["请选择省份","请选择城市","请选择区县"];
   
   //初始化-地址控件
   $('.city').each(function(index, el) {
        var number=parseInt(index+1);
        $("<option value='-1'>"+ar[1]+"</option>").appendTo($('#city_'+number));
   });
   $('.area').each(function(index, el) {
        var number=parseInt(index+1);
        $("<option value='-1'>"+ar[2]+"</option>").appendTo($('#area_'+number));
    });
   
    // 信息填写页面，第一联的下一步按钮效果 ，js校验
    $('.btn-next.first').click(function(){
    
    	// jquery.validate.js 校验
    	if(!v1.form()){
    		return;
    	}
    	// 附件校验
    	if (!formValidatePart1()) return;
    	
    	var supplier = $("#supplier").val();
    	
    	if(''!=supplier && ''!=$.trim(supplier) ){
			//判断商家名称是否已经存在
			$.ajax({ 
				type: "post", 
				url:basePath+"/yitiansystem/merchants/businessorder/existMerchantSupplieName.sc",
				data: {supplieName:supplier},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success: function(dt){
					if("success1"==dt){
					   $("#supplierError").html("已存在同名的招商供应商，请使用其它名称");
					   $("#supplier").focus();
					   return false;
					}else if("success2"==dt){
					   $("#supplierError").html("已存在同名的普通供应商,请使用其它名称");
					   $("#supplier").focus();
					   return false;
					}else if("success3"==dt){
						   $("#supplierError").html("已存在同名的韩货供应商,请使用其它名称");
						   $("#supplier").focus();
						   return false;
					} else {// 同名校验通过，则进入下一联
						
						nextButtonfirstFunction(true);//传参数标志新创建
						var $this=$('.btn-next.first'),
				        next_parent=$this.parent().parent().parent();
						next_parent.next().removeClass('hide').siblings().addClass('hide');
					
					}
				}
			});
    	}else{
    		 $("#supplierError").html("请输入招商供应商名称！");
    		 return; 
    	}
    });
    
});
// 定时保存草稿！
setInterval("saveSegment()",180000);



function saveAllInfo(curObj){
	if(!v1.form()){
		return;
	}
	 if (!formValidatePart3()) return;
	 
	  curObj = $(curObj); 
	  curObj.css('cursor', 'no-drop');// 鼠标变为禁止点击
	  if($(curObj).data('isFirst')){// 根据开关 确定 是否return
		  return;
	  }
	  $(curObj).data('isFirst',true);// 开关  
	 
	  $.ajax({
			async : true,
			cache : false,
			type: "POST",
			dataType : "html",
			data:$('#submitForm').serialize(),
			url : basePath+"/yitiansystem/merchants/manage/add_supplier.sc",
			success: function(msg){
				curObj.css('cursor', 'default');// 鼠标恢复
			     if (msg == 'success') {
			     	ygdg.dialog.alert("供应商基本信息保存成功.");
			     	location.href=basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc";
			     } else {
			        $(curObj).data('isFirst',false);// 开关 不让return
			     	ygdg.dialog.alert('供应商基本信息保存失败.'+msg);
			     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('供应商基本信息提交保存失败.');
			}
	   });
	 
}

function saveAllInfoAndSubmit(curObj){
	if(!v1.form()){
		return;
	}
	 if (!formValidatePart3()) return;
		
	  curObj = $(curObj); 
	  curObj.css('cursor', 'no-drop');// 鼠标变为禁止点击
	  if($(curObj).data('isFirst')){// 根据开关 确定 是否return
		  return;
	  }
	  $(curObj).data('isFirst',true);// 开关  
	 
	  $.ajax({
			async : true,
			cache : false,
			type: "POST",
			dataType : "html",
			data:$('#submitForm').serialize(),
			url : basePath+"/yitiansystem/merchants/manage/add_supplier.sc?isPendingApproval=1",
			success: function(msg){
				 curObj.css('cursor', 'default');// 鼠标恢复
			     if (msg == 'success') {
			     	ygdg.dialog.alert("供应商基本信息保存并且提交审核成功.");
			     	location.href=basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc";
			     } else {
			    	$(curObj).data('isFirst',false);// 开关 不让return
			     	ygdg.dialog.alert('供应商基本信息保存并且提交审核失败.'+msg);
			     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('供应商基本信息提交保存并且审核失败.');
			}
	   });
	 
}