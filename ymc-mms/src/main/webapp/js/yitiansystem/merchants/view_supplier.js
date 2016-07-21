//业务审核供应商
function bussinessAuditMerchants(id,listKind){
	openwindow(basePath+"/yitiansystem/merchants/manage/to_audit_supplier.sc?id="+id+"&listKind="+listKind,700,300,"业务审核商家");
}

//财务审核供应商
function financeAuditMerchants(id,listKind){
	openwindow(basePath+"/yitiansystem/merchants/manage/to_audit_supplier.sc?id="+id+"&listKind="+listKind,700,300,"财务审核商家");
} 

//业务审核
function businessAudit(supplierId,isPass,listKind){
	if(!$.trim($('#remark').val())){
		ygdg.dialog.alert("请填写审核备注");
		return;
	}
	$.ajax({
	      async : true,
	      cache : false,
	      type : 'GET',
	      dataType : "json",
	      url : basePath+"/yitiansystem/merchants/manage/to_validate_business_audit_supplier.sc?supplierId="+supplierId+"&isPass="+isPass,
	      success : function(data) {
	    	 if(data.result == 'failure'){
	    		 ygdg.dialog.alert(data.msg);
	    		 return;
	    	 }
	    	 if(data.result == 'success'){
	    		 $.ajax({
	    		      async : true,
	    		      cache : false,
	    		      type : 'POST',
	    		      dataType : "json",
	    		      data: $('#queryForm').serialize(),
	    		      url : basePath+"/yitiansystem/merchants/manage/business_audit_supplier.sc?supplierId="+supplierId+"&isPass="+isPass,
	    		      success : function(data) {
	    		    	 if(data.result == 'failure'){
	    		    		 ygdg.dialog.alert(data.msg);
	    		    		 return;
	    		    	 }
	    		    	 if(data.result == 'success'){
	    		    		 ygdg.dialog.alert(data.msg,function(){
	    		    			 refreshpage(basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc?listKind="+listKind);
	    					     closewindow();
	    		    		 });
	    		    	 }
	    		         
	    		      }
	    		    });	
	    	 }
	         
	      }
	    });	
}


//财务审核
function financeAudit(supplierId,isPass,listKind){
	if(!$.trim($('#remark').val())){
		ygdg.dialog.alert("请填写审核备注");
		return;
	}
	$.ajax({
	      async : true,
	      cache : false,
	      type : 'GET',
	      dataType : "json",
	      url : basePath+"/yitiansystem/merchants/manage/to_validate_finance_audit_supplier.sc?supplierId="+supplierId+"&isPass="+isPass,
	      success : function(data) {
	    	 if(data.result == 'failure'){
	    		 ygdg.dialog.alert(data.msg);
	    		 return;
	    	 }
	    	 if(data.result == 'success'){
	    		 $.ajax({
	    		      async : true,
	    		      cache : false,
	    		      type : 'POST',
	    		      dataType : "json",
	    		      data: $('#queryForm').serialize(),
	    		      url : basePath+"/yitiansystem/merchants/manage/finance_audit_supplier.sc?supplierId="+supplierId+"&isPass="+isPass,
	    		      success : function(data) {
	    		    	 if(data.result == 'failure'){
	    		    		 ygdg.dialog.alert(data.msg);
	    		    		 return;
	    		    	 }
	    		    	 if(data.result == 'success'){
	    		    		 ygdg.dialog.alert(data.msg,function(){
	    		    			 refreshpage(basePath+"/yitiansystem/merchants/manage/to_supplier_List.sc?listKind="+listKind);
	    					     closewindow();
	    		    		 });
	    		    	 }
	    		         
	    		      }
	    		    });	
	    	 }
	         
	      }
	    });	
}




//财务审核合同
function toFinanceAuditContract(id,listKind){
	openwindow(basePath+"/yitiansystem/merchants/manage/to_audit_supplier.sc?id="+id+"&listKind="+listKind,700,300,"财务审核合同");
} 

//财务审核合同
function financeAuditContract(contractId,isPass,listKind){
	if(!$.trim($('#remark').val())){
		ygdg.dialog.alert("请填写审核备注");
		return;
	}
	$.ajax({
	      async : true,
	      cache : false,
	      type : 'GET',
	      dataType : "json",
	      url : basePath+"/yitiansystem/merchants/businessorder/to_validate_finance_audit_contract.sc?contractId="+contractId+"&isPass="+isPass,
	      success : function(data) {
	    	 if(data.result == 'failure'){
	    		 ygdg.dialog.alert(data.msg);
	    		 return;
	    	 }
	    	 if(data.result == 'success'){
	    		 $.ajax({
	    		      async : true,
	    		      cache : false,
	    		      type : 'POST',
	    		      dataType : "json",
	    		      data: $('#queryForm').serialize(),
	    		      url : basePath+"/yitiansystem/merchants/businessorder/finance_audit_contract.sc?contractId="+contractId+"&isPass="+isPass,
	    		      success : function(data) {
	    		    	 if(data.result == 'failure'){
	    		    		 ygdg.dialog.alert(data.msg);
	    		    		 return;
	    		    	 }
	    		    	 if(data.result == 'success'){
	    		    		 ygdg.dialog.alert(data.msg,function(){
	    		    			 refreshpage(basePath+"/yitiansystem/merchants/businessorder/to_supplierContractList.sc?listKind="+listKind);
	    					     closewindow();
	    		    		 });
	    		    	 }
	    		         
	    		      }
	    		    });	
	    	 }
	         
	      }
	    });	
}


function requestRefund(contractId,refund,type){
	var confirmMsg = "";
	if(type == "deposit"){
		confirmMsg = "确认{保证金金额"+refund+"元}申请退款吗?";
	}else{
		confirmMsg = "确认{平台使用费金额"+refund+"元}申请退款吗?";
	}
	
	ygdg.dialog.confirm(confirmMsg,function(){
		 $.ajax({
				async : true,
				cache : false,
				type : 'GET',
				dataType : "json",
				url : basePath+"/yitiansystem/merchants/businessorder/request_refund.sc?contractId="+contractId+"&type="+type,
				success : function(data) {
					if(data.result=="success"){
						ygdg.dialog.alert(data.msg,function(){
							document.location.reload();
						});
					}else{
						ygdg.dialog.alert(data.msg);
					}
				}
			});
	 });
}	


