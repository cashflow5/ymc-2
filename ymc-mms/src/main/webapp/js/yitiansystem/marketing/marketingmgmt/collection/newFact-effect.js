//全选或反选
function column_select(flag){
	var chks = document.getElementsByName("chk_sure_column2");
	
	if(flag==1){//全选
		for(var i = 0 ; i<chks.length;i++){
			chks[i].checked = true;
		}
	}else{
		for(var i = 0 ; i<chks.length;i++){
			chks[i].checked = !chks[i].checked;
		}
	}
}

//是否支持折上折
function selectIsDistinct(obj){
	if(obj.checked){
		$("#input_zsz").val("1");
	}else{
		$("#input_zsz").val("0");
	}
}

function cancleSearchFilter(){
	var ts = $("tr[name='trs_']");
	for(var i = 0 ; i < ts.length ;i++){
		ts[i].style.display="";
	}
}

//搜索已选择的商品列表中相关的商品，并标红处理
function searchCommodity_Filter(){
	cancleSearchFilter();
	//有分类直接查询分类
	var threeCategory03Val = $("#threeCategory03").children('option:selected').text();
	var commodityNo = $("#filter_commodity_no").val();
	var ts = $("tr[name='trs_']");
	var isFinded = false;
	var isLook = false;
	for(var i = 0 ; i < ts.length ;i++){
		var val = ts[i].getAttribute("value");
		var vals = val.split(";");
		var no = vals[0];
		var cat = vals[1];
		
		if(threeCategory03Val != "三级分类" && threeCategory03Val == cat){
			isFinded = true;
			isLook = true;
		}
		if(commodityNo != "" && no == commodityNo){
			isFinded = true;
			isLook = true;
		}
		if(!isLook){
			ts[i].style.display="none";
		}
		isLook = false;
	}
	if(!isFinded){
		alert("没找到相应数据!");
	}
}