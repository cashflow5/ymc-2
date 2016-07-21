
//更新备注
function addRemark(obj,ids){
	alert(obj+"=="+ids);
	$("<input id='remarkId' multiple style='width:50px;height:30px;' onchange='saveRemark(this,"+ids+")'></input>").appendTo($(obj));
}

function saveRemark(obj,id){
	alert(obj.value+"--"+id);
	if(""==obj){
		return;
	}
	$.post(path+"/supply/supplier/PerchaseOrder/u_remark.sc",{val:obj.value,ids:id},function(data){
		if(""==data||undefined==data){
			return;
		}
		var da=eval("("+data+")");
		if(da>0){
			$(obj).parent().html(obj.value);
		}
	})
}
/**
 * 判断结束日期是否大于开始日期
 * @return
 */
function compareDate(){
	var dt1=$("#purchaseDateId").val();
	var dt2=$("#deliveryDateId").val();
	if(dt2==""){
		$("#submitid").get(0).disabled=false;
		return false;
	}
	if(dt2!=""&&dt1==""){
		alert("请填写开始日期");
		$("#submitid").get(0).disabled=true;
		return true;
	}else{
		$("#submitid").get(0).disabled=false;
	}
	dt1=dt1.replace(/-/ig,'')
	dt2=dt2.replace(/-/ig,'')
	if(dt1>=dt2){
		alert("结束日期必须大于开始日期");
		$("#submitid").get(0).disabled=true;
		return true;
	}else{
		$("#submitid").get(0).disabled=false;
	}
}
/**
 * 验证开始日期
 * @return
 */
function startDate(){
	//true 不符合要求
	if(compareDate()){
		return;
	}
	var dt1=$("#purchaseDateId").val();
	var dt2=$("#deliveryDateId").val();
	if(""==dt1&&""!=dt2){
		$("#submitid").get(0).disabled=true;
		return;
	}
	if(""!=dt1){
		$("#submitid").get(0).disabled=false;
	}
}
/**
 * 关闭
 * @param id
 * @return
 */
function depose(id){
	 if(window.confirm('"确认关闭吗?')==false){
			return;
		}		
    	$.ajax({
        type: "POST",
        url: path+"/supply/supplier/PerchaseOrder/u_updatePerchaseStatus.sc",
        data: {"id":id},           
        success: function(data){           
           if(data=="1"){
		 		alert("操作成功!"); 	 
		 		window.location.reload();      		 			 		
           	}else {
           		alert("操作失败");
           	}              
        }
      }); 
	 
//	if(confirm("确认关闭吗?")){
//		$("#pecharseForm").attr("action",path+"/supply/supplier/PerchaseOrder/u_updatePerchaseStatus.sc?val="+id);
//		$("#pecharseForm").submit();
//	}
}
 
 /**
  * 导出excel
  */
 
 function excelSubmit(){
	 $("#pecharseForm").attr("action",path+"/supply/supplier/PerchaseOrder/exportNotExcel.sc");
	 $("#pecharseForm").submit();
	 $("#pecharseForm").attr("action",path+"/supply/supplier/PerchaseOrder/findFulfillOrder.sc");
 }
 