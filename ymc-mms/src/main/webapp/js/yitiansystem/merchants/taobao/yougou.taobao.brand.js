var curPage = 1;
var curPageSize = 20;
$(function(){
	
	loadData(1,20);
	//全选
	$("#selectAll").live("click",function(){
		var isCheckAll = true;
		if($(this).attr("checked")){
			isCheckAll = true;
		}else{
			isCheckAll = false;
		}
		$("input[name='bindId']").attr("checked",isCheckAll);
	});
	
});

function loadData(pageNo,pageSize){
	if(pageNo==null||pageNo==""){
		pageNo=1;
	}
	curPageSize = pageSize;
	curPage = pageNo;
	$("#content_list").html('<div class="list-loading">正在载入......</div>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : "getYougouTaobaoBindList.sc?page="+pageNo+"&pageSize="+pageSize,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}

function search(){
	loadData(curPage,curPageSize);
}

function deleteItem(id){
	var idsArray = [];
	if(id==null){
		var catIds = $("input[name='bindId']:checked");
		if(catIds.length==0){
			ygdg.dialog.alert("请选择要删除的品牌绑定");
			return;
		}
		for(var i=0,_len=catIds.length;i<_len;i++){
			idsArray.push(catIds.eq(i).val());
		}
		
	}else{
		idsArray.push(id);
	}
	
	 ygdg.dialog.confirm("确定要删除品牌绑定吗?",function(){
		 $.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data:{
					ids:idsArray.join(",")
				},
				url : "deleteYougouTaobaoBrand.sc",
				success : function(data) {
					if(data.resultCode=="200"){
						ygdg.dialog.alert("删除成功!");
						loadData(curPage,curPageSize);
					}else{
						ygdg.dialog.alert(data.msg);
					}
				}
			});
	 });
}

