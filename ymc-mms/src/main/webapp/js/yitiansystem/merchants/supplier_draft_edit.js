$(function(){
		
    //初始化-获取地址控件的初始值和下拉选项
    checkTwo(1);
	checkTwo(3);

    // 信息填写页面，第一联的下一步按钮效果 ，js校验
    $('.btn-next.first').click(function(){
    
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
    
	$.ajax({
	        async : true,
	        cache : false,
	        type : 'GET',
	        dataType : "json",
	        url : basePath+"/yitiansystem/merchants/manage/get_trademark_detail_ajax.sc?redisKey="+$('#redisKey').val(),
	        success : function(data) {
	           var trademarkList =data.trademarkList;
	           console.log(trademarkList);
	           var _len = trademarkList.length;
	           if( !data||!trademarkList || _len<1 ){
	        	   addSubTable(); 
	           }else{
		           for(var i=0;i<_len;i++){
		        	  i==0?i:addSubTable(); 
		              var trademark = trademarkList[i];
		              $("#trademark_"+(i+1)).val(trademark.trademark);
		              $("#brandNo_"+(i+1)).val(trademark.brandNo);
		              $("#brandName_"+(i+1)).html(trademark.brandName);
		              if(!trademark.brandName){
		              	$("#brand_"+(i+1)).html("立即绑定");
		              }else{
		              	$("#brand_"+(i+1)).html("修改绑定");
		              }
		              $("#deductionPoint_"+(i+1)).val(trademark.deductionPoint == 0? '':trademark.deductionPoint);
		              $("#authorizer_"+(i+1)).val(trademark.authorizer);
		              $("#type_"+(i+1)).val(trademark.type);
		              $("#registeredTrademark_"+(i+1)).val(trademark.registeredTrademark);
		              $("#reg_start_"+(i+1)).val(trademark.registeredStartDate);
		              $("#reg_end_"+(i+1)).val(trademark.registeredEndDate);
		              for(var j=0,_sublen=trademark.trademarkSubList.length;j<_sublen;j++){
		                  var trademarkSub = trademark.trademarkSubList[j];
		                  $("#beAuthorizer_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.beAuthorizer);
		                  $("#authoriz_startdate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizStartdate);
		                  $("#authoriz_enddate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizEnddate);
		              }
		           }
	           }
	        }
	 });
});
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
				curObj.css('cursor', 'default');
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
			     if (msg == 'success') {
			     	ygdg.dialog.alert("供应商基本信息保存并且提交审核成功.");
			     	location.href=basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc";
			     } else {
			     	ygdg.dialog.alert('供应商基本信息保存并且提交审核失败.'+msg);
			     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('供应商基本信息提交保存并且审核失败.');
			}
	   });
	 
}
