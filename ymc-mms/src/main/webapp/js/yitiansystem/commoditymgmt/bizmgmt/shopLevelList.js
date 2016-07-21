function fn_deleteShopLevel(currentId){
	var result = confirm("确定要删除些级别吗?");
	if(result){
		$.post("d_deleteShopLevel.sc",{"id":currentId},function(data){
			var obj = eval("("+data+")");
			if(obj.flag==1){
				location.href="toShopLevelList.sc";
			}else{
				alert("该等级下有店铺,不能删除!");
			}
		});
	}
}