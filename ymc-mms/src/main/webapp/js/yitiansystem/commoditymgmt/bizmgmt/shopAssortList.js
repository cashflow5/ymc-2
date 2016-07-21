function d_shopAssort(id){
	var result = confirm("确定要删除店铺分类吗?");
	if(result){
		$.post("d_shopAssort.sc",{"id":id},function(data){
			var obj = eval("("+data+")");
			if(obj.flag==1){
				location.href="toShopAssortList.sc";
			}else{
				alert("该分类下有店铺,不能删除!");
			}
		});
	}
}