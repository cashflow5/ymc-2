function deleteajax(id){
	 $.ajax({
		type:"post",
		url:"d_commodityProduct.sc",
		data:{
			"id":id
		},
		dataType:"json",
		error:function(){alert('操作失败');},
		success:function(data){//
			if(data.result){
				var id=data.id;
				$("#"+id).remove();
				$(".ytweb-table tbody tr").each(function(){
					var tdindex=$(this).index();
					var tds=$(this).find("td");
						tds.find("input").each(function(){
						var inputname=$(this).attr("alt");
						$(this).attr("name","ps["+tdindex+"]."+inputname);
						});
					
				});
			}
			
			var productRows = $("input[deleteRowFlag='rowdeleteflag']").size();
			if(productRows == 0){
				$("#saveButton").hide();
			}
		}
	});
}



function toaddcommodityproduct(){
	var commodityid=$("#commodityId").val();
	var catb2cid=$("#catb2cID").val();
	var catb2ctree=$("#catb2cTree").val();
//	var form=$("#toaddcommodity");
//	form.submit();
	
	
	var param = "commodityId="+commodityid+"&catb2cID="+catb2cid+"&catb2cTree="+catb2ctree;
	showThickBox("货品添加","../../commoditymgmt/commodityinfo/productlist/toaddCommodityPtoduct.sc?TB_iframe=true&height=550&width=850",false,param);
}

