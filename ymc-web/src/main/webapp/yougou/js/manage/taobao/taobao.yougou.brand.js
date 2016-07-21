taobaoYougouBrand.search = function(){
	$("#queryVoform").submit();
}

/**
 * 新增品牌绑定
 */
taobaoYougouBrand.save = function(id){
	var url = taobaoYougouBrand.basePath+"/taobao/toSaveYougouTaobaoBrand.sc";
	if(id!=null){
		url = url+"?id="+id;
	}
	ygdgDialog.open(url, {
		id:"imgDialog",
		width:630,
		title: '新建品牌设置',
		close: function(){
			refreshpage();
		}
	});
}
/**
 * 品牌绑定
 */
taobaoYougouBrand.bind = function(){
	if($("input[name='id']").length==0){
		return;
	}
	var ids = $("input[name='id']");
	var yougouBrandNoSelects = $("select[name='yougouBrandNos']");
	var params = [];
	for(var i=0,length=ids.length;i<length;i++){
		var select = yougouBrandNoSelects.eq(i);
		var brandNo = select.find("option:selected").val();
		var brandName = select.find("option:selected").text();
		params.push(ids.eq(i).val()+"|"+brandNo+"|"+brandName);
	}
	ymc_common.loading("show","正在保存设置......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			dataStr:params.join(",")
		},
		url : taobaoYougouBrand.basePath+ "/taobao/saveYougouTaobaoBrand.sc",
		success : function(data) {
			ymc_common.loading();
			if(data.resultCode == "200"){
				ygdg.dialog.alert("绑定成功");
				closewindow();
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}

/**
 * 删除
 */
taobaoYougouBrand.del = function(id){
	var ids = [];
	if(id==null){
		var bindIds = $("input[name='bindId']:checked");
		for(var i=0,length=bindIds.length;i<length;i++){
			ids.push(bindIds.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要删除的选项");
		return;
	}
	ygdg.dialog.confirm("确定要删除吗？", function(){
		//ymc_common.loading("show","正在删除......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : taobaoYougouBrand.basePath+ "/taobao/delYougouTaobaoBrand.sc",
			success : function(data) {
				//ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert("删除成功");
					document.location.reload();
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
	
};


