$(function(){
		
    //初始化-获取地址控件的初始值和下拉选项
    checkTwo(1);
	checkTwo(3);

    // 信息填写页面，第一联的下一步按钮效果 ，js校验
    $('.btn-next.first').click(function(){
    	// jquery.validate.js 校验
    	if(!v1.form()){
    		return;
    	}
    	
    	if (!formValidatePart1()) return;
    	
    	var supplier = $("#supplier").val();
    	
        if( defaultSupplier != supplier ){
	    	//判断新修改的商家名称是否已经存在
			$.ajax({ 
				type: "post", 
				url: basePath+"/yitiansystem/merchants/businessorder/existMerchantSupplieName.sc",
			    data: {supplieName:supplier},
			    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    success: function(dt){
					if("success1"==dt){
					   $("#supplierError").html("已存在同名的招商供应商，请使用其它名称");
					   $("#supplier").focus();
					   return;
					}else if("success2"==dt){
					   $("#supplierError").html("已存在同名的普通供应商,请使用其它名称");
					   $("#supplier").focus();
					   return;
					}else if("success3"==dt){
						   $("#supplierError").html("已存在同名的韩货供应商,请使用其它名称");
						   $("#supplier").focus();
						   return false;
					}
			}
	     });
	     
		}
  					
        nextButtonfirstFunction(false);
        var $this=$('.btn-next.first'),
        next_parent=$this.parent().parent().parent();
        next_parent.next().removeClass('hide').siblings().addClass('hide');
	
    });
   
});




function saveAllInfo(curObj){
	
	// jquery.validate.js 校验
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
	  var editFrom="",fEdit="";
	  if(editType!='' && editType == 'F_EDIT'){
		  editFrom = "?listKind=F_APPROVAL";
		  fEdit = "?edit="+editType;
   	  }
	  $.ajax({
			async : true,
			cache : false,
			type: "POST",
			dataType : "html",
			data:$('#submitForm').serialize(),
			url : basePath+"/yitiansystem/merchants/manage/update_supplier_merchant.sc"+fEdit,
			success: function(msg){
				 curObj.css('cursor', 'default');// 鼠标恢复
				 if (msg == 'success') {
				     	ygdg.dialog.alert("修改供应商基本信息保存成功.");
				     	location.href=basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc"+editFrom;
				     } else {
				    	 $(curObj).data('isFirst',false);// 开关 不让return
				     	ygdg.dialog.alert('修改供应商基本信息保存失败.'+msg);
				     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('修改供应商基本信息提交保存失败.');
			}
	   });
	  
}


function saveAllInfoAndSubmit(curObj){
	
	// jquery.validate.js 校验
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
			url : basePath+"/yitiansystem/merchants/manage/update_supplier_merchant.sc?isPendingApproval=1",
			success: function(msg){
				curObj.css('cursor', 'default');// 鼠标恢复
				 if (msg == 'success') {
				     	ygdg.dialog.alert("修改供应商基本信息保存并提交审核成功.");
				     	location.href=basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc";
				     } else {
				    	 $(curObj).data('isFirst',false);// 开关 不让return
				     	ygdg.dialog.alert('修改供应商基本信息保存并提交审核失败.'+msg);
				     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('修改供应商基本信息提交保存并审核失败.');
			}
	   });
}

//暂未使用！
function getTotalSubmitJson(){
	// *获取修改的字段 
	var totalSubmitArray = [];
	$("input[change-state=true]").each(function(i,item){	
		
		totalSubmitArray.push(item.name+':"'+item.value+'"');
	});
	
	// *获取隐藏的字段 
	$("input[type=hidden]").each(function(i,item){	
		
		totalSubmitArray.push(item.name+':"'+item.value+'"');
	});
	// 商标授权-全部加入
	$("input[d-type=pleasePush]").each(function(i,item){	
		
		totalSubmitArray.push(item.name+':"'+item.value+'"');
	});
	var data = eval('({'+ totalSubmitArray.join(',') +'})');
	return data;
}

