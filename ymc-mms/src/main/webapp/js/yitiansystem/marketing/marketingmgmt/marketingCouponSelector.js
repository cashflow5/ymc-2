//品牌品类数据集合
var Brand_Category_Data = new Array();
//品牌容器集合
var Brand_container_array = new Array();
//加载品牌品类
function loadBrandCategory(brandId){
		$("#rootCategory").empty();
		$("#container").html("");
		if(brandId == "0") return;
		var brandTopCatb2cs = loadTopCatByBrandId(brandId);
		$("#rootCategory").append("<option value='0'>请选择大类</option>");
		if(brandTopCatb2cs != null){
			for(var j = 0 ; j < brandTopCatb2cs.length ; j++){
				$("#rootCategory").append("<option value='"+brandTopCatb2cs[j].structName+";"+brandTopCatb2cs[j].id+"'>"+brandTopCatb2cs[j].catName+"</option>");
			}
		}
}

//检测是否存在
function checkExist(brandId,rootId,secondId,thirdId){
	for(var i = 0 ; i < Brand_Category_Data.length ;i++){
		var data = Brand_Category_Data[i];
		var key = brandId+";"+rootId+";"+secondId+";"+thirdId;
		//$("#debug").html(brandId+"-"+rootId+"-"+secondId+"-"+thirdId+"||"+data.key+"<br/>");
		if(data.key == key){
			return i;
		}
	}
	return -1;
}

/**
	检测品牌容器的现实状态
	1:已显示
	0:未显示
   -1:不存在该品牌元素
*/
function checkBrandShowState(brandId){
	for(var i = 0 ; i < Brand_container_array.length ;i++){
		var data = Brand_container_array[i];
		if(data.brandId == brandId){
			if(data.isShow){
				return 1;
			}else{
				Brand_container_array[i].isShow = true;
				//$("#debug").html("data.isShow:"+data.isShow+",Brand_container_array[i].isShow:"+Brand_container_array[i].isShow+"<br/>");
				return 0;
			}
		}
	}
	return -1;
}

//新增元素至品牌容器集合
function addBrandContainerToArray(brandId){
	var result = false;
	for(var i = 0 ; i < Brand_container_array.length ;i++){
		var data = Brand_container_array[i];
		if(data.brandId == brandId){
			result = true;
		}
	}
	if(!result){
		Brand_container_array.push({"brandId":brandId,"isShow":false});
	}
}

//从品牌容器集合删除元素
function removeBrandContainerToArray(brandId){
	for(var j = 0 ; j < Brand_container_array.length ;j++){
		if(Brand_container_array[j].brandId == brandId){
			Brand_container_array = Brand_container_array.del(j);
			$("#brand_"+brandId).remove();
			break;
		}
	}
}

function removeBrandCategoryData(brandId,rootId,secondId,thirdId){
	var index = checkExist(brandId,rootId,secondId,thirdId);
	if(index != -1){
		//删除数据
		Brand_Category_Data = Brand_Category_Data.del(index);
		//判断该品牌下选中的品类数量为0，则删除该品牌容器
		var existCountter = 0;
		for(var i = 0 ; i < Brand_Category_Data.length ;i++){
			var data = Brand_Category_Data[i];
			if(data.brandId == brandId){
				existCountter++;
			}
		}
		if(existCountter <= 0){
			//删除品牌容器集合
			removeBrandContainerToArray(brandId);
		}

	}else{
		alert("未发现元素!");
	}
}

//删除元素
function cancleBrandCategory(brandId,rootId,secondId,thirdId){
	removeBrandCategoryData(brandId,rootId,secondId,thirdId);
	//alert("item_"+createKey(brandId,rootId,secondId,thirdId));
	$("#item_"+brandId+"_"+rootId+"_"+secondId+"_"+thirdId).remove();
}


//临时品牌品类Model
TMP_BrandCategoryModel = function(brandId,rootId,secondId,thirdId,brandName,rootVal,secondVal,thirdVal){
	this.brandId   = brandId   ||"";
	this.rootId    = rootId    ||"";
	this.secondId  = secondId  ||"";
	this.thirdId   = thirdId   ||"";
	this.brandName = brandName ||"";
	this.rootVal   = rootVal   ||"";
	this.secondVal = secondVal ||"";
	this.thirdVal  = thirdVal  ||"";
	this.key = brandId+";"+rootId+";"+secondId+";"+thirdId;
}



//构建含有品牌的容器及元素
function buildHTML(model){
	var contentHtml='';
	contentHtml+='<div class="sort_select clearfix" id="brand_'+model.brandId+'" name="brandItem" brandID="'+model.brandId+'" brandName="'+model.brandName+'">';
	contentHtml+='<p><strong>品牌：'+model.brandName+'</strong></p>';
	contentHtml+='<div id="brand_container_'+model.brandId+'">';
	contentHtml+=buildHTML_(model);
	contentHtml+='</div></div>';
	return contentHtml;
}

//构建品牌下分类元素
function buildHTML_(model){
	var id = model.brandId+'_'+model.rootId+'_'+model.secondId+'_'+model.thirdId;
	var catName = model.rootVal+'_'+model.secondVal+'_'+model.thirdVal;
	var contentHtml='';
	contentHtml+='<span style="width:190px;" id="item_'+id+'" name="catItem" catName="'+catName+'" catID="'+id+'">'+model.rootVal+'>·';
	contentHtml+=model.rootVal+'>'+model.secondVal+'>'+model.thirdVal;
	contentHtml+='&nbsp;&nbsp;<a href=javascript:cancleBrandCategory("'+model.brandId+'","'+model.rootId+'","'+model.secondId+'","'+model.thirdId+'")>';
	contentHtml+='<img src="'+BASE_PATH+'/images/yitiansystem/delete.gif" alt=""></a></span>';
	return contentHtml;
}

//选择品牌品类
function chooseBrandCategory(){
	//获取品牌
	var brandId = $("#brand").children('option:selected').val();
	var brandName = $("#brand").children('option:selected').text();
	//获取第一级分类
	var rootVal = $("#rootCategory").val().split(";")[1];


	if(rootVal == "") return null;
	var secondInputs = getByName("secondCategory");
	for(var i = 0 ; i < secondInputs.length ;i++){
		if(secondInputs[i].checked){
			//alert(getCat_child(secondInputs[i].value).length+"三级分类个数");
			var thirdInputs = getCat_child(secondInputs[i].value);

			for(var j = 0 ;j< thirdInputs.length ;j++){
				if(thirdInputs[j].checked){
					var tmpThirdVal = thirdInputs[j].value.split(";");
					var index = checkExist(brandId,rootVal,secondInputs[i].value,tmpThirdVal[5]);
					//alert(index);
					if(index == -1){
						var model = new TMP_BrandCategoryModel(brandId,rootVal,secondInputs[i].value,tmpThirdVal[5],brandName,tmpThirdVal[0],tmpThirdVal[1],tmpThirdVal[2]);
						//alert(!checkBrandExist(brandId));

						//加入品牌容器集合
						addBrandContainerToArray(brandId);

						//检验品牌容器的显示状态
						var res = checkBrandShowState(brandId);

						if(res == 0){//刚加入没显示
							//构建HTML
							var html = buildHTML(model);
							$("#content_container").append(html);
						}else if(res ==1){//已有元素
							var html = buildHTML_(model);
							$("#brand_container_"+brandId).append(html);
						}else{
							alert("不存在");
						}
						Brand_Category_Data.push({"key":model.key,"value":model,"brandId":brandId});
					}else{
						//alert("重复:"+$("#brand").children('option:selected').text());
					}
				}
			}
		}
	}
}