//初始化商品选择器
function initlizateCommodityChooser(){
	//初始化多栏目下的第一级别分类
	var data = loadCatb2cLevel1();
	if(data != null && data.length >0){
		for ( var i = 0; i < data.length; i++) {
			var option = "<option value='" + data[i].structName + ";" + data[i].id + "'>" + data[i].catName + "</option>";
			$("#rootCattegory02").append(option);
			$("#rootCattegory03").append(option);
		}
	}
	
	$('#rootCattegory02').change( function() {
	//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "secondCategory02");
		} else {
			$("#secondCategory02").empty();// 清空下来框
			$("#secondCategory02").append("<option value='0'>二级分类</option>");
		}
		$("#threeCategory02").empty();// 清空下来框
		$("#threeCategory02").append("<option value='0'>三级分类</option>");
	});
			
	$('#secondCategory02').change( function() {
	//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "threeCategory02");
		} else {
			$("#threeCategory02").empty();// 清空下来框
			$("#threeCategory02").append("<option value='0'>三级分类</option>");
		}
	});
	
	
	$('#rootCattegory03').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "secondCategory03");
			} else {
				$("#secondCategory03").empty();// 清空下来框
				$("#secondCategory03").append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory03").empty();// 清空下来框
			$("#threeCategory03").append("<option value='0'>三级分类</option>");
		});
				
		$('#secondCategory03').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "threeCategory03");
			} else {
				$("#threeCategory03").empty();// 清空下来框
				$("#threeCategory03").append("<option value='0'>三级分类</option>");
			}
		});
		
	//添加过滤搜索按钮click
		$("#filterSearchBtn").click(function(){
			searchCommodity_Filter();
		});
		
	
	//[添加多级搜索按钮click]
	$("#search_commodity02").click(function(){
		$(".search_column02").remove();
		var commodity = buildCommodityData(1,"threeCategory02",2);
		var hg = new HG_commodity();
		var containerID = "commoditys_container0_clm2";
		var checkID = "chk_sure_column2";
		var threeCategoryID = "threeCategory02"; 
		_load(commodity,hg,containerID,threeCategoryID,checkID,2);
	});
}


/**
获取对应栏目Index的对象，如果不存在返回null
*/
function getColumnObject(columnIndex){
 for(var i = 0 ; i < COLUMN_commodity.length;i++){
 	if(COLUMN_commodity[i].index==columnIndex){
 		return COLUMN_commodity[i];
 	}
 }
 return null;
}





/**
	获取对应栏目商品的对象，如果不存在返回null
*/
function getColumnCommodityByColumn_Cid(columnIndex,commodityId){
for(var i = 0 ; i <COLUMN_commodity.length;i++){
 	if(COLUMN_commodity[i].index==columnIndex){
 		var data = COLUMN_commodity[i].data;
 		for(var j = 0 ; j < data.length;j++){
 			if(data[j].id==commodityId){
 				return data[j];
 			}
 		}
 	}
 }
 return null;
}

//增加多栏目商品
function addColumnCommodity(index){
	cancleSearchFilter();
	//$("#column_item"+index).hide();
	var chkSureColumn = document.getElementsByName("chk_sure_column2");
	
	for(var i = 0 ; i < chkSureColumn.length ; i++){
		if(chkSureColumn[i].checked){
			var cscVal = chkSureColumn[i].value.split(";");
			var cid = cscVal[1];
			
			//已经存在，不能重复添加
			if(choose_commodityArray.indexOf(cid)!=-1){
				continue;
			}
			
			var commodityObj = getColumnCommodityByColumn_Cid(2,cid);
			if(commodityObj==null){continue;}
			
			choose_commodityArray.push(cid);
			
			var column_id="column_list_table_"+index;
			
			var nm = getRandom();
			$("#"+column_id).append('<tr name="trs_" value="'+commodityObj.no+';'+commodityObj.commodity_catName+'"><td><input type="hidden" value="'+commodityObj.commodityName+',1" name="oneAndManyCommodityName"/><input type="hidden" value="'+commodityObj.id+'" name="oneAndManyCommodityId"/><input type="hidden" value="'+commodityObj.no+'" name="oneAndManyCommodityNumber"/>'+commodityObj.no+'</td><td>'+commodityObj.commodityName+'</td><td>'+commodityObj.commodity_catName+'</td><td>'+commodityObj.commodity_publicPrice+'</td><td>'+commodityObj.commodity_Price2+'</td><td align="center"><font id="store2_'+commodityObj.id+'_'+index+'_'+nm+'"><a href=javascript:selectStore("store2_'+commodityObj.id+'_'+index+'_'+nm+'","'+commodityObj.id+'")>查看</a></font></td><td align="center"><a  onclick="deletecolumn(this,\''+commodityObj.id+'\')">删除</a></td></tr>');
		}
	}
	
	$("#active_commodity_count").html(choose_commodityArray.length);
	
}

//删除行
function deletecolumn(obj,cid){
	choose_commodityArray = choose_commodityArray.del(choose_commodityArray.indexOf(cid));
	obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
	$("#active_commodity_count").html(choose_commodityArray.length);
}

//活动添加时间段是否存在
function timeIsExist(s,e){
	for(var i = 0 ; i < CHOOOSE_time.length ; i++){
		if(CHOOOSE_time[i].startTime == s && CHOOOSE_time[i].endTime == e){
			return true;
		}
	}
	return false;
}

