//加载一级分类
function loadCatb2cLevel1(){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadCatB2cLevel1.sc",
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	return metaData;
}

//加载等级分类
function loadLevel(selValue, selId) {
	var value = selValue;
	$.ajax( {
		type : "POST",
		url : BASE_PATH+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
		$("#" + selId).append("<option value='0'>选择分类</option>");
		for ( var i = 0; i < data.length; i++) {
			if(data[i].id!=null){
				var option = "<option value='" + data[i].structName + ";"
						+ data[i].id + "'>" + data[i].catName + "</option>";
				$("#" + selId).append(option);
			}
		}
	}
	});
}

//加载所有品牌
function loadBrand(){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadAllBrand.sc",
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	return metaData;
}

//通过品牌ID加载品牌分类
function loadBrandCatB2c(brandID){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadBrandCatb2c.sc",
		data : {
			"brandID" : brandID
		},
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	if(metaData.length<=0){
		alert("未查询到相关记录!");
	}
	return metaData;
}


function loadCommodityByNo_Name(name,no,pageNo){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadCommodityByNo_Name.sc",
		data : {
			"name" : name,
			"no":no,
			"pageNo":pageNo
		},
		dataType : "json",
		success : function(data) {
			metaData = data;
			//metaData = eval("data="+data);
		}
	});
	if(metaData==null||metaData[0]==null||metaData[0]==""){
		alert("未查询到相关记录!");
	}
	return metaData;
}

//加载等级分类
function loadChildLevel(selValue) {
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
		data : {
			"value" : selValue
		},
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	return metaData;
}

/**
 * 获取品牌列表根据品牌分类ID
 * @param serieID 品牌分类id
 * @return
 */
function loadBrandsBy_commodityBrandSerieID(serieID){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadBrandsBy_commodityBrandSerieID.sc",
		data : {
			"serieID" : serieID
		},
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	if(metaData.length<=0){
		alert("未查询到相关记录!");
	}
	return metaData;
}
 
//加载一级分类通过品牌id
function loadBrandRootCatB2c(brandId){
	 var metaData = null;
		$.ajax( {
			type : "POST",
			async : false,
			url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadBrandRootCatB2c.sc",
			data : {
				"brandId" : brandId
			},
			dataType : "json",
			success : function(data) {
				metaData = data;
			}
		});
		if(metaData.length<=0){
			alert("未查询到相关记录!");
		}
		return metaData;
 }

//加载品牌所属的第一级分类列表
function loadTopCatByBrandId(brandId){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadTopCatByBrandId.sc",
		data : {
			"brandId" : brandId
		},
		dataType : "json",
		success : function(data) {
			metaData = data;
		}
	});
	//alert("metaData:"+metaData.length);
	if(metaData.length<=0){
		alert("未查询到相关记录!");
	}
	return metaData;
}


//加载商品对应的货品库存
function loadProductStore(commodityId){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadProductStore.sc",
		data : {
			"commodityId" : commodityId
		},
		success : function(data) {
			metaData = data;
		}
	});
	//alert("metaData:"+metaData.length);
	return metaData;
}

function loadProductPrice(commodityId){
	var metaData = null;
	$.ajax( {
		type : "POST",
		async : false,
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadProductPrice.sc",
		data : {
			"commodityId" : commodityId
		},
		success : function(data) {
			metaData = data;
		}
	});
	//alert("metaData:"+metaData.length);
	return metaData;
}