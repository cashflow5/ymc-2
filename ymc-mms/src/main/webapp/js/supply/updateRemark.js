function selectSupplier(perchaseDetialIds,prechaseIds,currentPage){
   window.top.purchaseId=prechaseIds;
   window.top.perchaseDetialId=perchaseDetialIds;
   window.top.currentPages=currentPage;
   showThickBox("修改备注",path+"/supply/supplier/PerchaseOrder/toUpdateRemark.sc?TB_iframe=true&height=350&width=650",false);
}
//初始化修改的编号
$(document).ready(function(){
	$("#prechaseId").val(window.top.purchaseId);
	$("#perchaseDetialId").val(window.top.perchaseDetialId);
	$("#currentPage").val(window.top.currentPages);
});
/**
 * 取消
 * @return
 */
function cancel(){
	//var perchaseId=$("#prechaseId").val();
	//alert(perchaseId);
	window.parent.mbdif.location.reload();
	 window.top.TB_remove();
//	$("#u_purchaseForm").attr("action",path+"/supply/supplier/PerchaseOrder/findDetailById.sc?perchaseId="+perchaseId);
//	window.close();
//	$("#u_purchaseForm").submit();
}

function submits(){
	if(""==$("#remark").val()){
		alert("请填写备注!");
		return;
	}
	 $("#u_purchaseForm").get(0).target="mbdif";
	 $("#u_purchaseForm").submit();
	 closewindow();
	//$("#u_purchaseForm").attr("target","mbdif");
	// alert($("#u_purchaseForm").size());
	
//	private String prechaseId;//采购单id
//	private String perchaseDetialId;//详细id 
//	private String notFulfill;//是否入库
//	private String remark;//备注
//	var prechaseIds=$("#prechaseId").val();
//	var perchaseDetialIds=$("#perchaseDetialId").val();
//	var remarks=$("#remark").val();
//	alert(prechaseIds+"==="+perchaseDetialIds+"==="+remarks);
//	$.post(path+"/supply/supplier/PerchaseOrder/u_remark.sc",{prechaseId:prechaseIds,perchaseDetialId:perchaseDetialIds,remark:remarks},function(data){
//		//alert(data);
//		window.parent.mbdif.location.reload();
//		window.top.TB_remove();
//	});
	//window.parent.mbdif.location.reload();
	//window.top.TB_remove();
	 //alert("修改成功");
	//window.parent.mbdif.location.reload();
	//window.top.TB_remove();
}