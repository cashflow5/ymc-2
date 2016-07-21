/**
 * 
 * 文件描述:
 * 		抽取公共方法用于各类活动的增加、修改操作，
 * 		
 * 
 * 下标索引相对较大，防止修改活动初始化造成下标“已存在”现象
 * 
 */

//分页查询出的Commodity数据集合
var pageCommodityDataArray = new Array();

//所有品牌Brand数据集合[A]
var brandCommodityDataArray = new Array();

//存放页面已经存在的Brand数据集合[A]
var extisCommodityDataArray = new Array();

//促销放置的单品商品信息
var Commodity_SingleDataArray = null;

//促销放置的多品促销商品信息
var Commodity_MultiDataArray = null;

//当前栏目的累计数
var item_index = 100;
//当前选择某栏目的添加商品index
var item_add_index = -1;

//当前多级优惠的级别(注意：当选择普通优惠时当前值为0)
var prefereLevel = 0;

//-----------------------------05-14整理
//已选择品牌-缓冲区
var CHOOSE_Brand = new Array();

//所有品牌
var ALL_Cat = new Array();

//选择分类促销-缓冲区
var CHOOSE_Cat = new Array();

//多品促销选择-缓冲区
var CHOOSE_column = new Array();

//-----------------------------05-16整理
//多栏目商品信息-临时
var COLUMN_commodity = new Array();

//-----------------------------05-20整理
//早买早便宜-缓冲区
var CHOOSE_earlyCheap = new Array();


//-----------------------------05-23整理
//已选择三级分类-缓冲区
var CHOOSE_cat = new Array();

//换购分页索引
var hg_search_commodityIndex = 1;

//分页换购
function pageByHG(pageNo){
	$(".search_commodity01").remove();
			
	
	var commodity = buildHgCommodityData(pageNo);
	
	var hg = new HG_commodity();
	
	loadPage_hg(commodity,hg);
}
function loadPage_hg(commodity,hg)
{
		$("#commoditys_container01").html("");
		//进行分类查询
		pageCommodityDataArray = new Array();
		var html = '<tr id="commoditys_container01_tr_00"><th width="10%">选择</th><th width="25%">商品编码</th><th width="35%">商品名称</th><th width="15%">销售价</th><th width="15%">库存</th></tr>';
	
		var tmpCommodity = null;
	 	var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
	 	var productStore;
		if(commodity!=null && commodity.data !=null && commodity.data.length>0){
			//alert("data length:"+commodity.data.length);
			for(var i = 0 ; i <commodity.data.length ;i++){
				commodity_id             = commodity.data[i].id;
				commodity_no             = commodity.data[i].no;
				commodity_commodityName  = commodity.data[i].commodityName;
				commodity_publicPrice    = commodity.data[i].salePrice;
				
				tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
				
				productStore = loadProductStore(commodity_id);
				
				pageCommodityDataArray[i] = {"index":i,"commodity":tmpCommodity,"fn":hg};
				
				html+='<tr class="search_commodity01"><td align="center"><input type="checkbox" name="chk_sure_hg" value="'+i+'" id="chk_sure_hg'+i+'"></td><td  align="center">'+commodity_no+'</td><td  align="center">'+commodity_commodityName+'</td><td align="center">'+commodity_publicPrice+'</td><td align="center">'+productStore+'</td></tr>';
                //html += '<tr onclick="_public_choose_invoke_callBack_commodity('+i+')"><td>'+commodity.data[i].no+'</td><td>'+commodity.data[i].commodityName+'</td><td>￥'+commodity.data[i].salePrice+'</td></tr>';
			}
			//alert(1);
			html+='<tr class="search_commodity01"><td colspan="5" id="page_" align="right"><a href="javascript:pageByHG('+((hg_search_commodityIndex-2)<=0?0:hg_search_commodityIndex-2)+')">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:pageByHG('+hg_search_commodityIndex+')">下一页</a></td></tr>';
		}else{
			
			html+='<tr class="search_commodity01"><td colspan="5" id="page_" align="right"><a href="javascript:pageByHG('+((hg_search_commodityIndex-1)<=0?0:hg_search_commodityIndex-1)+')">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:pageByHG('+1+')">首页</a></td></tr>';
		}
			
		$("#commoditys_container01").append(html);
}
	
//获取、组合换购商品数据
function buildHgCommodityData(pageNo){
		//判断商品编码、商品名称是否有值，如果有值则进行模糊搜索
		var hg_commodity_no   = $("#commodity_no_hg").val();
		var hg_commodity_name = $("#commodity_name_hg").val();
	
		
	 	var commodity = null;
		
		if(hg_commodity_no.length > 0 || hg_commodity_name.length > 0){
			//alert("编号、名称模糊搜索");
			var jsonDataArray = loadCommodityByNo_Name(hg_commodity_name,hg_commodity_no,pageNo);
			if(jsonDataArray==null){
				alert("没有查到相关商品信息!");
				return;
			}
			commodity = new Commodity(jsonDataArray[0],jsonDataArray[1],jsonDataArray[2],jsonDataArray[3]);
		}else{
			/*var selValue = $("#threeCategory01").children('option:selected').val();
			var structName = selValue.split(";")[0];*/
			var structName = "";
			var selValue1 = $("#rootCattegory01").children('option:selected').val();
			var selValue1_struct = selValue1.split(";")[0];
			
			if(selValue1_struct!="0"){
				structName = selValue1_struct;
			}
			
			var selValue2 = $("#secondCategory01").children('option:selected').val();
			var selValue2_struct = selValue2.split(";")[0];
			if(selValue2_struct!="0"){
				structName = selValue2_struct;
			}
			
			var selValue3 = $("#threeCategory01").children('option:selected').val();
			var selValue3_struct = selValue3.split(";")[0];
			if(selValue3_struct!="0"){
				structName = selValue3_struct;
			}
			
			//加载三级分类的商品
			commodity =  _public_loadLevelCommodityList(structName,pageNo);
		}
		if(commodity!=null && commodity.data !=null && commodity.data.length>0){
			hg_search_commodityIndex = pageNo+1;
		}else{
			hg_search_commodityIndex--;
		}
		return commodity;
}



//控制默认初始化显示、隐藏
function initShowOrHide(){
	
	//初始化显示与隐藏
	$("#category").hide();
	$("#brand").hide();
	$("#category_brand").hide();
	
	$("#multi_brand").hide();
	$("#control_vip_rank").hide();

	$(".favorable_normal").show();
	$("#more_rank").hide();
	$(".favorable_btn").hide();
	

	if(OP=="edit"){
		$("#control_vip_rank02").show();
		$("#category").hide();
		$("#brand").hide();
		
		
		//需要在初始化下面，进行第二次赋值
		/*[修改特有js控制]  --------begin--------*/
		if(mb_value){
			if(mb_value=="1"){//普通优惠
				$("#more_bank_inputs2").show();
				$(".favorable_normal").show();
				$("#more_rank").hide();
				$(".favorable_btn").hide();
				
				
				
			}else if(mb_value=="2"){//多级优惠
				//$('#hg_before_form').html("");
				$(".favorable_normal").hide();
				$("#more_rank").show();
				$(".favorable_btn").show();
				$("#more_bank_inputs1 .fullpay_td02").show();
			}
		}
		if(join_member=="0"){
			$("#control_vip_rank01").hide();
			reinitifH();
		}else if(join_member=="1"){
			$("#control_vip_rank01").show();
			reinitifH();
		}
		//alert("tmp.js==>commodityRequire_val="+commodityRequire_val);
		if(commodityRequire_val){
			if(commodityRequire_val == "1"){
				$("#category").show();
			}else if(commodityRequire_val=="2"){
				$("#brand").show();
				$("#control_vip_rank").show();
				fillWithBrand();
				$("#brands_container").show();
			}else if(commodityRequire_val=="4"){
				$("#column_container").show();
				$("#multi_brand").show();
			}else if(commodityRequire_val=="-1"){
				$("#category").hide();
			}else if(commodityRequire_val=="3"){
				$("#category_brand").show();
				/*$("#brand_brands").append("<option value='0'>请选择品牌</option>");
				$("#brand_catb2cs").append("<option value='0'>请选择大类</option>");
				createBrand_allBrand();*/
			}
		}
		/*[修改特有js控制]  --------end--------*/
	}else{
		$("#control_vip_rank01").show();
	}
}

//初始化参与会员范围数据
function initJoinMemberData(){
	if(defaultLevels.length<0) return;
	//初始化默认选中的级别
	for(var i = 0 ; i < defaultLevels.length ; i++){
		document.getElementById("level_ids_"+defaultLevels[i]).checked="checked";
	}
}

//初始化换购-分类-商品选择
function initHg_Cat_Commodity(){
	//第一级分类初始化【用于换购】
	$('#rootCattegory01').change( function() {
		//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "secondCategory01");
		} else {
			$("#secondCategory01").empty();// 清空下来框
			$("#secondCategory01").append("<option value='0'>二级分类</option>");
		}
		$("#threeCategory01").empty();// 清空下来框
		$("#threeCategory01").append("<option value='0'>三级分类</option>");
	});
	
	$('#secondCategory01').change( function() {
	//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "threeCategory01");
		} else {
			$("#threeCategory01").empty();// 清空下来框
			$("#threeCategory01").append("<option value='0'>三级分类</option>");
		}
	});
		/*
		$("#threeCategory01").change(function(){
		});*/
	hg_search_commodityIndex = 1;
	$("#search_commodity01").click(function()
	{
		hg_search_commodityIndex = 1;
		$(".search_commodity01").remove();
		
		var commodity = buildHgCommodityData(hg_search_commodityIndex);
		
		var hg = new HG_commodity();
		
		loadPage_hg(commodity,hg);
				
	});
	/*
	$("#search_commodity01").click(function(){
		$(".search_commodity01").remove();
	
		//判断商品编码、商品名称是否有值，如果有值则进行模糊搜索
		var hg_commodity_no   = $("#commodity_no_hg").val();
		var hg_commodity_name = $("#commodity_name_hg").val();
		
	 	var commodity = null;
		
		if(hg_commodity_no.length > 0 || hg_commodity_name.length > 0){
			//alert("编号、名称模糊搜索");
			var jsonDataArray = loadCommodityByNo_Name(hg_commodity_name,hg_commodity_no,1);
			if(jsonDataArray==null){
				alert("没有查到相关商品信息!");
				return;
			}
			commodity = new Commodity(jsonDataArray[0],jsonDataArray[1],jsonDataArray[2],jsonDataArray[3]);
		}else{
			//alert("分类搜索");
			var selValue = $("#threeCategory01").children('option:selected').val();
			var structName = selValue.split(";")[0];
			//加载三级分类的商品
			commodity =  _public_loadLevelCommodityList(structName,1);
		}
		
		//进行分类查询
		pageCommodityDataArray = new Array();
		var hg = new HG_commodity();
		var html = '';
		var tmpCommodity = null;
	 	var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
		if(commodity!=null && commodity.data !=null && commodity.data.length>0){
			for(var i = 0 ; i <commodity.data.length ;i++){
				commodity_id             = commodity.data[i].id;
				commodity_no             = commodity.data[i].no;
				commodity_commodityName  = commodity.data[i].commodityName;
				commodity_publicPrice    = commodity.data[i].salePrice;
				
				tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
					
				pageCommodityDataArray[i] = {"index":i,"commodity":tmpCommodity,"fn":hg};
				var ran = getRandom();
				html+='<tr class="search_commodity01"><td align="center"><input type="checkbox" name="chk_sure_hg" value="'+i+'" id="chk_sure_hg'+i+'"></td><td  align="center">'+commodity_no+'</td><td  align="center">'+commodity_commodityName+'</td><td align="center">'+commodity_publicPrice+'</td><td align="center"><font id="sr_'+ran+'"><a href=javascript:selectStore("sr_'+ran+'","'+commodity_id+'")>查看</a></font></td></tr>';
			}
			$("#commoditys_container01").append(html);
		}	
				
	});*/
}

//按品类促销-分类选择 Select 控件
function bindPl_CatSelect(){
	//按品类促销-分类选择
	$("#rootCattegory_dp").change(function(){
		var rootCatName = $(this).children('option:selected').text();
		var val = $(this).children('option:selected').val();
		if(val=="0"){return;}
		var vals = val.split(";");
		ALL_Cat = new Array();
		var childData = loadChildLevel(vals[0]);
		var html = '';
		$("#control_vip_ra").empty();
		if(childData!=null&&childData.length>0){
			for(var i = 0 ; i < childData.length ; i++){
				var child = childData[i];
				if(child==null)continue;
				 	html +='<div class="control_vip_ra_tt">'+
	                    	'<div class="blank10"></div>'+
	                    	'<span class="buytime">'+child.catName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
	                        '<span class="operate"><a href=javascript:cat_select(true,"'+child.id+'")>全选</a><a href=javascript:cat_select(false,"'+child.id+'")>反选</a></span>'+
	                        '<div class="blank10"></div></div>';
	                        
				 //加载三级分类
				var children = loadChildLevel(child.structName);
				if(children!=null&&children.length>0){
					html+='<div class="control_vip_ra"><div class="blank10"></div>';
					for(var j = 0 ; j <children.length ;j++){
						if(children[j]==null)continue;
						//所有的第三级分类放入缓冲区
						ALL_Cat.push(children[j].id);
						html +='<input type="checkbox" name="chk_cat" style="margin-right:5px;" value="'+rootCatName+';'+child.catName+';'+children[j].catName+';'+vals[1]+';'+child.id+';'+children[j].id+'"/>'+
	                            	'<span class="buytime">'+children[j].catName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>';
					}
					html+='<div class="blank10"></div></div>';
				}else{
					alert("未搜索到相应数据");
				}
		   }
	   }else{
	   		alert("未搜索到相应数据");
	   }
	   $("#control_vip_ra").html(html);
	});
}

//绑定品牌Select 控件
function bindBrand_Select(){
	//品牌分类-下拉改变
	$("#commodityBrandSerie").change(function(){
		var serieID = $(this).children('option:selected').val();
		var data = loadBrandsBy_commodityBrandSerieID(serieID);
		var html = '<div class="blank10"></div>';
		for(var i = 0 ; i <data.length;i++){
			html+='<input type="checkbox" name="brand" value="'+data[i].brandName+';'+data[i].id+'" id="brand_'+data[i].id+'" style="margin-right:5px;"/>'+
            	'<span class="buytime">'+data[i].brandName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        	if(i!=0&&i%8==0){
           	 html+='<div class="blank5"></div>';
            }
		}
		
		$("#brands_container").html(html);
	});
}
//alert("tmp.js 执行！！！");
$(document).ready(function(){
	
	//绑定添加多栏目按钮事件
	bindAddColumnButton();

	//控制默认初始化显示、隐藏
	initShowOrHide();
	
	//初始化参与会员范围数据
	initJoinMemberData();

	//----[revert.js] begin
	revert_rule();
	preferentialTypeBind();
	joinMemberBind();
	//alert("tmp.js===>joinProductBind()");
	joinProductBind();
	brandCatB2cBind();
	//----[revert.js] end
	
	//alert("商品范围:"+commodityRequire_val);

	//初始化日期
	$("#datepicker_start").dateDisplay('${BasePath}');
	$("#datepicker_end").dateDisplay('${BasePath}');
	//alert(1);

	//初始化换购-分类-商品选择
	initHg_Cat_Commodity();
	
	//绑定品类促销下拉框事件
	bindPl_CatSelect();
	
	//绑定品牌促销下拉框事件
	bindBrand_Select();
	
	//初始化显示修改时的内容
	if(OP=="edit"){
		init_show_edit();
	}
		
});	







//新增品牌
function addBrand(){
  //CHOOSE_Brand = new Array();
	//$("#choose_brand_container").html("");
	var html = '';
	var brands = document.getElementsByName("brand");
	//alert("----------"+brands.length);
	var tmp_countter = 0;
	for(var i= 0 ;i < brands.length ; i++){
		if(brands[i].checked){
			var brandChkVal = brands[i].value.split(";");
		
			if(CHOOSE_Brand.indexOf(brandChkVal[1])==-1){
				if(tmp_countter%2 == 0){
					html+='<ul>';
				}
				//放入选择品牌列表-缓冲区
				CHOOSE_Brand.push(brandChkVal[1]);
				html += '<li id="choose_brand_item_'+brandChkVal[1]+'">'+brandChkVal[0]+'<input type="hidden" name="brandName" value="'+brandChkVal[0]+'"/> <input type="hidden" name="brandId" value="'+brandChkVal[1]+'"/><a href="javascript:delBrand(\''+brandChkVal[1]+'\');"><img alt="" src="'+BASE_PATH+'/images/yitiansystem/delete.gif"></a></li>';
				
				if(tmp_countter!=0 && tmp_countter%2 == 0){
					html+='</ul>';
				}
				tmp_countter++;
			}
		}
	}
	$("#choose_brand_items").append(html);
}

//品牌全选或者反选换购
function pp_select(type){
	var chkBrands = document.getElementsByName("brand");
	for(var i = 0 ; i < chkBrands.length; i++){
		var res = chkBrands[i].checked;
		if(type == 1 ){//全选
				chkBrands[i].checked = "checked";
		}else{
			if(res){
				chkBrands[i].checked = false;
			}else{
				chkBrands[i].checked = "checked";
			}
		}
	}
}


//删除品牌
function delBrand(id){
	$("#choose_brand_item_"+id).remove();
	CHOOSE_Brand = CHOOSE_Brand.del(CHOOSE_Brand.indexOf(id));
}






//新增换购商品
function addHg(){
	Index_HG++;
	$("#add_hg_container").hide();
	var chkSureHG = document.getElementsByName("chk_sure_hg");
	var html = '<span id="hgs_">';
	var form_html = '';
	for(var i = 0 ; i < chkSureHG.length; i++){
		if(chkSureHG[i].checked){
			var ng = getRandom();
			html+='<ul id="hg_item_'+ng+'"><li><input type="text" name="hg_moneyRedemption" id="hgj" style="width:80px;" maxLength="5"></li><li class="product_name"  style="width:300px">'+pageCommodityDataArray[i].commodity.commodityName+'</li><li class="product_name" style="width:200px">'+pageCommodityDataArray[i].commodity.no+'</li><li style="width:80px"><font id="store_'+pageCommodityDataArray[i].commodity.id+'_'+i+'"><a href=javascript:selectStore("store_'+pageCommodityDataArray[i].commodity.id+'_'+Index_HG+'","'+pageCommodityDataArray[i].commodity.id+'")>查看</a></font></li><li>'+pageCommodityDataArray[i].commodity.commodity_publicPrice+'</li><li><a href="javascript:delH('+ng+')">删除</a></li></ul>';
			form_html+='<span id="hg_clist_'+ng+'"><input type="hidden" value="'+pageCommodityDataArray[i].commodity.commodityName+'" name="commodityName"/><input type="hidden" value="'+pageCommodityDataArray[i].commodity.no+'" name="commodityNumber"/><input type="hidden" name="commodityId" value="'+pageCommodityDataArray[i].commodity.id+'" style="width:80px;"></span>';
		}
	}
	
	$("#hg_before_form").append(form_html);
	$("#hg_before").before(html+"</span>");
}

//全选换购商品或者反选换购
function hg_select(type){
	for(var i = 0 ; i < pageCommodityDataArray.length; i++){
		var res = document.getElementById("chk_sure_hg"+i).checked;
		if(type == 1 ){//全选
				document.getElementById("chk_sure_hg"+i).checked = "checked";
		}else{
			if(res){
				document.getElementById("chk_sure_hg"+i).checked = false;
			}else{
				document.getElementById("chk_sure_hg"+i).checked = "checked";
			}
		}
	}
}

//显示换购商品层
function openHg(){
	$("#add_hg_container").show();
}

//关闭换购层，初始化还原
function closeHg(){
	$("#add_hg_container").hide();
}





//新增分类
function addCat(){
	var cats = document.getElementsByName("chk_cat");
	var html = '<ul>';
	var tmp_countter = 0;
	for(var i = 0 ; i <cats.length;i++){
		if(cats[i].checked){
			var vl = cats[i].value.split(";");
			var catID = vl[5];//三级分类id
			var p1ID = vl[4];
			var p2ID = vl[3];
			if(CHOOSE_cat.indexOf(catID)==-1){
				var cat1 = vl[0];
				var cat2 = vl[1];
				var cat3 = vl[2];
			
				CHOOSE_cat.push(catID);
				
				if(tmp_countter%2 == 0){
					html+='<ul>';
				}
				html +='<li id="choose_cat_item_'+catID+'"><input type="hidden" name="categoryName" value="'+cat1+'"/><input type="hidden" name="secondCategoryName" value="'+cat2+'"/><input type="hidden" name="thirdCategoryName" value="'+cat3+'"/><input type="hidden" name="categoriesId" value="'+p1ID+'"/><input type="hidden" name="secondCategoriesId" value="'+p2ID+'"/><input type="hidden" name="thirdCategoriesId" value="'+catID+'"/><span href="#">'+cat1+'</span> &gt; <span href="#">'+cat2+'</span> &gt; <span href="#">'+cat3+'</span><a href=javascript:delCat("'+catID+'")><img alt="" src="'+BASE_PATH+'/images/yitiansystem/delete.gif"></a></li>';
				if(tmp_countter!=0 && tmp_countter%2 == 0){
					html+='</ul>';
				}
				tmp_countter++;
	 		}
		}
	}

	$("#choose_category_item").append(html);
}

//全选或反选品类
function cat_select(isSelect,secondCatID){	
	var catChks = document.getElementsByName("chk_cat");
	for(var i = 0 ; i < catChks.length ; i++){
		if(secondCatID == catChks[i].value.split(";")[4]){
			if(isSelect){
				catChks[i].checked = true;
			}else{
				if(catChks[i].checked){
					catChks[i].checked = false;
				}else{
					catChks[i].checked = true;
				}
			}
		}
	}

}

//删除品类
function delCat(id){
	$("#choose_cat_item_"+id).remove();
	CHOOSE_cat = CHOOSE_cat.del(CHOOSE_cat.indexOf(id));
}




//增加多栏目商品
function addColumnCommodity(index){
	//alert("栏目index:"+index);
	$("#column_item"+index).hide();
	var chkSureColumn = document.getElementsByName("chk_sure_column"+index);
	var columnName = getColumnNameByIndex(index);
	var tp = $("#activeType_").val();
	for(var i = 0 ; i < chkSureColumn.length ; i++){
		if(chkSureColumn[i].checked){
			var cscVal = chkSureColumn[i].value.split(";");
			
			var cid = cscVal[1];
			//alert("所属栏目:"+columnName);
			/*for(var j = 0 ; j < COLUMN_commodity.length;j++){
					alert(COLUMN_commodity[j]);
			}*/
			
			//alert(COLUMN_commodity.length);

			//过滤重复商品
			var activeTp =$("#activeType_").val();
			//alert("activeTp:"+activeTp);
			if((activeTp!="1"&&activeTp!="5")){
				if(pushColumnCommodity(cid)){continue;}
			}
			
			var commodityObj = getColumnCommodityByColumn_Cid(index,cid);
			
			//alert(commodityObj);
			if(commodityObj==null){
				continue;
			}
			
			//alert("显示");
			var column_id="column_list_table_"+index;
			//alert(column_id);
			var otherHTML ='';
			var nm = getRandom();
			if(OP=="edit"){
				if(tp=="2"){//限时抢
					otherHTML = '<td align="center"><input type="text" id="snappingUpPrice" name="snappingUpPrice" value="" maxLength="5"></td>'+
								'<td align="center">'+commodityObj.commodity_publicPrice+'</td>';
				}else if(tp=="3"){//多买多优惠
					otherHTML = '<td align="center"><input type="text" name="lessBuyAmount" size="5" value="" maxLength="5"/></td>'+
					'<td align="center"><input type="text"  name="moreBuyAmount" size="5" value="" maxLength="5"/></td>'+
					'<td align="center"><input type="text" name="commodityDiscount" size="5" value="" maxLength="5"></td>';
				}else if(tp == "4"){//送赠品
					otherHTML = '<td align="center"><input type="text" id="gift_name_'+nm+'" name="presentName" readonly style="width:150px;"  ondblclick=getGift("'+nm+'")><input type="hidden" id="gift_id_'+nm+'" name="presentId"><input type="hidden" id="gift_no_'+nm+'" name="presentNumber" value="" ></td>'+
					'<td align="center"><input type="text" id="presentCount" name="presentCount" size="3" value="" maxLength="5"></td>';
				}
			}
			$("#"+column_id).append('<tr><td><input type="hidden" value="'+commodityObj.commodityName+','+columnName+'" name="oneAndManyCommodityName"/><input type="hidden" value="'+commodityObj.id+'" name="oneAndManyCommodityId"/><input type="hidden" value="'+commodityObj.no+'" name="oneAndManyCommodityNumber"/>'+commodityObj.no+'</td><td>'+commodityObj.commodityName+'</td>'+otherHTML+'<td align="center"><font id="store2_'+commodityObj.id+'_'+index+'_'+nm+'"><a href=javascript:selectStore("store2_'+commodityObj.id+'_'+index+'_'+nm+'","'+commodityObj.id+'")>查看</a></font></td><td align="center"><a  onclick="deletecolumn(this,'+index+')">删除</a></td></tr>');
			$("#"+column_id).parent().show();
			//alert(1111);
		}
	}
	if(tp=="2"){//限时抢
		//修复bug06.02
		//加入动态验证
		validate_addCessorRobCommodity();
	}else if(tp=="3"){//多买多优惠
		//修复bug06.02
		//加入验证
		validate_addMuchMoreCommodity();
	}else if(tp == "4"){//送赠品
		//绑定验证
		validate_addSendGift();
	}else if(tp == "5"){//早买早优惠
		
	}
	
}

//全选换购商品或者反选换购
function column_select(type,columnIndex){
	var obj = document.getElementsByName("chk_sure_column"+columnIndex);
	
	for(var i = 0 ; i < obj.length ; i++){
		var res = obj[i].checked;
		if(type == 1 ){//全选
			obj[i].checked = "checked";
		}else{
			if(res){
				obj[i].checked = false;
			}else{
				obj[i].checked = "checked";
			}
		}
	}
}




var orderAmtSpace = new Array();
//增加多级优惠
function addMoreRank(){
	var str = '';
	var html = '';
	var i = 1;
	
	//获取订单金额范围
	var less = $("#lessConsumpAmount_").val();
	
	var more = $("#moreConsumpAmount_").val();
	
	$("#lessConsumpAmount_").val("");
	$("#moreConsumpAmount_").val("");
	
	var val1 = parseFloat(less);
	
	var val2 = parseFloat(more);
	
	//alert("val1:"+val1);
	//alert("val2:"+val2);
	
	if(val1>=val2){
		alert("订单金额范围区间设定错误!");
		return;
	}
	
	var countter = 0;
	var base = less+'_'+more;
	
	var len = orderAmtSpace.length;
	//alert(len);
	for(var k = 0 ; k < len ; k++){
		var space = orderAmtSpace[k].split("_");
		var x = space[0];
		var y = space[1];
		//alert("x > val2:"+(x > val2));
		//alert("y < val1:"+(y < val1));
		
		if(x > val2 || y <= val1){
			countter++;
		}
	}
	
	//alert(len+"--"+countter);
	if(len!=countter){
		alert("订单金额范围设定错误");
		return;
	}
	
	orderAmtSpace.push(base);
	
	
	html+='<input type="hidden" name="lessConsumpAmount" value="'+less+'"/><input type="hidden" name="moreConsumpAmount" value="'+more+'"/>';
	
	if($("#chk_zj").attr("checked")){
		//直减金额
		var decreaseAmount_val = $("#decreaseAmount_").val();
		$("#decreaseAmount_").val("");
		html+='<input type="hidden" name="decreaseAmount" value="'+decreaseAmount_val+'"/>';
		str += (i++)+'.下单直减'+decreaseAmount_val+'元<br/>';
	}else{
		html+='<input type="hidden" name="decreaseAmount" value=""/>';
	}
	if($("#chk_zk").attr("checked")){
		//折扣数
		var discount_val = $("#discount_").val();
		$("#discount_").val("");
		html+='<input type="hidden"name="discount" value="'+discount_val+'"/>';
		str += (i++)+'.整单打'+discount_val+'折<br/>';
	}else{
		html+='<input type="hidden" name="discount" value=""/>';
	}
	
	if($("#chk_my").attr("checked")){
		//免邮
		html+='<input type="hidden" name="isSupportPostage" value="1"/>';
		str += (i++)+'.免邮<br/>';
	}else{
		html+='<input type="hidden" name="isSupportPostage" value="0"/>';
	}

	
	/*if($("#chk_syhq").attr("checked")){
	
		//优惠券id
			//优惠券id
		var couponsId_val = $("#couponsId_").val();
		var couponsName_val = $("#coupons_Name").val();
		
		$("#couponsId_").val("");
		$("#coupons_Name").val("");
		
		html+='<input type="hidden" name="couponsId" value="'+couponsId_val+'"/>'+
			'<input type="hidden" name="couponsName" value="'+couponsName_val+'"/>';
		str += (i++)+'.送优惠券:'+couponsName_val+'<br/>';
	}else{
		html+='<input type="hidden" name="couponsId" value=""/><input type="hidden" name="couponsName" value=""/>';
	}
	
	if($("#chk_szp").attr("checked")){
		
		//赠品编号(id)
		var giftCommodityNumber_val = $("#giftCommodityNumber_").val();
		//赠品名称
		var giftCommodityName_val = $("#giftCommodityName_").val();
		//赠品id
		var giftCommodityId_val   = $("#giftCommodityId_").val();
		
		$("#giftCommodityNumber_").val("");
		$("#giftCommodityName_").val("");
		$("#giftCommodityId_").val("");
		                 
		html+='<input type="hidden" name="giftCommodityNumber" value="'+giftCommodityNumber_val+'"/>'+
			  '<input type="hidden" name="giftCommodityName" value="'+giftCommodityName_val+'"/>'+
			  '<input type="hidden" name="giftCommodityId" value="'+giftCommodityId_val+'"/>';
		str += (i++)+'.送优赠品,赠品名称:'+giftCommodityName_val+'<br/>';
	}else{
		html+='<input type="hidden" name="giftCommodityNumber" value=""/>'+
			  '<input type="hidden" name="giftCommodityName" value=""/>'+
			  '<input type="hidden" name="giftCommodityId" value=""/>';
	}*/
	
	/*if($("#chk_szp").attr("checked")){
		
		//赠品编号(id)
		var giftCommodityNumber_val = $("#giftCommodityNumber_").val();
		html+='<input type="hidden" name="giftCommodityNumber" value="'+giftCommodityNumber_val+'"/>';
		str += (i++)+'.送优赠品,编码:'+giftCommodityNumber_val+'<br/>';
	}else{
		html+='<input type="hidden" name="giftCommodityNumber" value=""/>';
	}*/
	/*
	if($("#chk_sjf").attr("checked")){
	
		//送积分
		var sendIntegralNumber_val = $("#sendIntegralNumber_").val();
		$("#sendIntegralNumber_").val("");
		html+='<input type="hidden" name="sendIntegralNumber" value="'+sendIntegralNumber_val+'"/>';
		str += (i++)+'.送'+sendIntegralNumber_val+'积分<br/>';
	
	}else{
		html+='<input type="hidden" name="sendIntegralNumber" value=""/>';
	}
	if($("#chk_jfbs").attr("checked")){
		//积分倍数
		var integralMultiples_val = $("#integralMultiples_").val();
		$("#integralMultiples_").val("");
		html+='<input type="hidden" name="integralMultiples" value="'+integralMultiples_val+'"/>';
		str += (i++)+'.送'+integralMultiples_val+'倍积分<br/>';
	}else{
		html+='<input type="hidden" name="integralMultiples" value=""/>';
	}
	if($("#chk_zsz").attr("checked")){
		html+='<input type="hidden" name="isDiscount" value="1"/>';
		str += (i++)+'.支持折上折';
	}else{
		html+='<input type="hidden" name="isDiscount" value="0"/>';
	}
	
	if($("#chk_jqhg").attr("checked")){
		str += (i++)+'.加钱换购商品';
		
		var html2 = '';
		var moneyRedemptions = document.getElementsByName("hg_moneyRedemption");
		for(var k = 0 ; k < moneyRedemptions.length ;k++){
			html2 += '<input type="text" name="moneyRedemption" value="'+moneyRedemptions[k].value+'" style="width:80px;">';
		}
		$('#hg_before_form').append(html2);
		
		html+=$('#hg_before_form').html();
		$('#hg_before_form').html("");
		html += '<input type="hidden" name="hgCounts" value="'+moneyRedemptions.length+'"/>';
	}else{
		html += '<input type="hidden" name="hgCounts" value="0"/>';
	}*/
	$("#hgs_").remove();
	
	$("#more_preferental_form_div").append('<span id="form'+less+'_'+more+'">'+html+'</span>');
	$("#preferentalTab").append('<tr><td>'+less+'元至'+more+'元</td><td style="text-align:left;line-height:20px;">'+str+'</td><td class="td0"><a onclick=deletecolumn2(this,"'+less+'","'+more+'")>删除</a></td></tr>');
	reinitifH();
	
}




//=========================================【抽取出下方的js】



//去除前面的rootCattegory01（换购商品）的重复


/*
 *[局部公共函数说明]
 *
 *函数loadSomeoneLevelSelect() 和 bindDynamic23LevelSelect() 和 bindDynamicSearchCommodityButton()
 *用于加载多栏目活动商品范围的动态新增和修改初始化
 */

//加载某级别下的分类select-option集合[2011-05-23 am 09:52]
function loadSomeoneLevelSelect(index){
	//初始化多栏目下的第一级别分类
	var data = loadCatb2cLevel1();
	if(data != null && data.length >0){
		for ( var i = 0; i < data.length; i++) {
			var option = "<option value='" + data[i].structName + ";" + data[i].id + "'>" + data[i].catName + "</option>";
			$("#rootCattegory0"+index).append(option);
		}
	}
}

//动态绑定2、3级分类select
function bindDynamic23LevelSelect(index){
	$('#rootCattegory0'+index).change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "secondCategory0"+index);
			} else {
				$("#secondCategory0"+index).empty();// 清空下来框
				$("#secondCategory0"+index).append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory0"+index).empty();// 清空下来框
			$("#threeCategory0"+index).append("<option value='0'>三级分类</option>");
		});
				
		$('#secondCategory0'+index).change( function() {
			//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "threeCategory0"+index);
			} else {
				$("#threeCategory0"+index).empty();// 清空下来框
				$("#threeCategory0"+index).append("<option value='0'>三级分类</option>");
			}
		});
}

//动态绑定搜索按钮事件
function bindDynamicSearchCommodityButton(index){
//[添加多级搜索按钮click]
$("#search_commodity0"+index).click(function(){
	search_commodityIndex = 1;
	$(".search_column0"+index).remove();
	
	var commodity = buildCommodityData(search_commodityIndex,"threeCategory0"+index,index);
	var hg = new HG_commodity();
	var containerID = "commoditys_container0_clm"+index;
	var checkID = "chk_sure_column"+index;
	var threeCategoryID = "threeCategory0"+index; 
	_load(commodity,hg,containerID,threeCategoryID,checkID,index);
	
	//$("#commoditys_container0_clm"+columnIndex).append(html);
	
/*$(".search_column0"+index).remove();
	//判断商品编码、商品名称是否有值，如果有值则进行模糊搜索
	var commodity_column_no   = $("#commodity_no_"+index).val();
	var commodity_column_name = $("#commodity_name_"+index).val();

 	var commodity = null;
	
	if(commodity_column_no.length > 0 || commodity_column_name.length > 0){
		//alert("编号、名称模糊搜索");
		var jsonDataArray = loadCommodityByNo_Name(commodity_column_name,commodity_column_no,1);
		commodity = new Commodity(jsonDataArray[0],jsonDataArray[1],jsonDataArray[2],jsonDataArray[3]);
	}else{
		//alert("分类搜索");
		var selValue = $("#threeCategory0"+index).children('option:selected').val();
		//alert(columnIndex+"columnIndex"+$("#secondCategory0'+columnIndex+'").children("option:selected").length);
		var structName = selValue.split(";")[0];
		//加载三级分类的商品
		commodity =  _public_loadLevelCommodityList(structName,1);
	}
	
	
	//alert(commodity.data.length);
	
	
	//进行分类查询
	//pageCommodityDataArray = new Array();
	//alert("index:"+index);
	var hg = new HG_commodity();
	var html =  '<tr id="commoditys_container01_tr_0'+index+'"><th width="10%">选择</th><th width="25%">商品编码</th><th width="35%">商品名称</th>'+
		        '<th width="15%">销售价</th><th width="15%">库存</th></tr>';
	var tmpCommodity = null;
	
 	var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
	if(commodity!=null && commodity.data !=null && commodity.data.length>0){
		//检测是否属于新增栏目，真：则进行新增对应数据结果，否：
		$("#commoditys_container0_clm"+index).empty();
		var arrData = new Array();
		
		for(var i = 0 ; i <commodity.data.length ;i++){
			commodity_id             = commodity.data[i].id;
			commodity_no             = commodity.data[i].no;
			commodity_commodityName  = commodity.data[i].commodityName;
			commodity_publicPrice    = commodity.data[i].salePrice;
			//alert("commodity_id:"+commodity_id);
			tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
			
			arrData.push(tmpCommodity);
			var ran =getRandom();
			html+='<tr class="search_column0'+index+'"><td align="center"><input type="checkbox" name="chk_sure_column'+index+'" value="'+i+';'+commodity_id+'" id="chk_sure_column'+i+'"></td><td  align="center">'+commodity_no+'</td><td  align="center">'+commodity_commodityName+'</td><td align="center">'+commodity_publicPrice+'</td><td align="center"><font id="sr_'+ran+'"><a href=javascript:selectStore("sr_'+ran+'","'+commodity_id+'")>查看</a></font></td></tr>';
		}
		var columnData = getColumnObject(index);
		//alert("columnData:"+columnData.data.length);
		if(columnData==null){
			//alert(index-2);
			//alert("下标为："+(index)+"放入数据量："+arrData.length)
			COLUMN_commodity[index]= {"index":index,"data":arrData};
			//alert("加入:"+arrData.length+"--"+COLUMN_commodity[0].data[0].id+">>");
		}else{//放入数据
			pushDataToColumn(columnIndex,arrData);
		}
		$("#commoditys_container0_clm"+index).append(html);
	}*/
			
});
}

var CHOOSE_columnNames = new Array();

//通过栏目索引获取栏目名称
function getColumnNameByIndex(index){
	for(var i = 0 ; i < CHOOSE_columnNames.length ; i++){
		if(index == CHOOSE_columnNames[i].index){
			//alert("找到了所属栏目。");
			return CHOOSE_columnNames[i].columnName;
		}
	}
	return null;
}

function getIndexByColumnIndex(index){
	for(var i = 0 ; i < CHOOSE_columnNames.length ; i++){
		if(index == CHOOSE_columnNames[i].index){
			return i;
		}
	}
	return -1;
}

//栏目索引（相对较大，防止修改时已存在现象）
var columnIndex = 100;

//绑定添加栏目按钮事件
function bindAddColumnButton(){
	$(".add_column").click(function(){		
		//点击添加栏目按钮，再次进行验证
		if(!v_columnName()){
			alert("请输入栏目名称，格式为1-20个字符!");
			return;
		}
		
		columnIndex++;
		
		var otherHTML = '';
		if(OP=="edit"){
			var tp = $("#activeType_").val();
			if(tp=="2"){//限时抢
				otherHTML = '<td width="15%" align="center"><b>抢购价</b></td><td width="10%" align="center"><b>销售价</b></td>';
			}else if(tp=="3"){
				otherHTML = '</td><td width="15%"><b>最小购买件数</b></td><td width="15%"><b>最大购买件数</b></td><td width="15%"><b>折扣</b></td>';
			}else if(tp == "4"){
				otherHTML = '<td width="15%" align="center"><b>赠品编码</b></td><td width="10%" align="center"><b>数量</b></td>';
			}
		}
		
		var i=$(".column_list").length;	
		var activContent=$(".dtsl_content").val();
		var columnName = $("#column_name").val();
		$("#column_name").val("");
		CHOOSE_columnNames.push({"index":columnIndex,"columnName":columnName});
		$(".add_column_continer").append('<input type="hidden" name="columnName" value="'+columnName+'"/><div class="blank5"></div><div class="column_list" id="column_list_'+i+'"><p class="column"><span>栏目（'+(i+1)+'）</span><span class="activContent">'+columnName+'</span><a class="addcolumn" onclick="showColumnItem('+columnIndex+')">+ 添加商品</a>'+
		
		'<div class="blank10"></div><div class="addprice_buy_area_x addprice_buy_area" style="width:600px;"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_product_table" id="column_list_table_'+columnIndex+'"><tr><td width="20%"><b>商品编码</b> </td><td width="30%"><b>商品名称</b></td>'+otherHTML+'<td width="10%" align="center"><b>库存</b> </td><td width="20%" align="center"><b>操作</b></td></tr></table></div><div class="addmore_product" style="display:none;" id="column_item'+columnIndex+'">'+
		'<div class="product_search_box" style="width:600px;">'+
			'<p>'+
		    	'<span>商品编码: <input type="text" id="commodity_no_'+columnIndex+'"/></span>'+
		        '<span>商品名称: <input type="text" id="commodity_name_'+columnIndex+'"/></span>'+
		    '</p>'+
		    '<div class="blank10"></div>'+
		    '<p>'+
		    	'<span>'+
		    	'<select id="rootCattegory0'+columnIndex+'">'+
					'<option selected value="0" selected="selected">请选择大类</option>'+
				'</select>'+
		        '</span>'+
		        '<span>'+
		        '<select name="seclev" id="secondCategory0'+columnIndex+'">'+
	            	'<option value="0" selected="selected">二级分类</option>'+
	            '</select>'+
		        '</span>'+
		        '<span>'+
	        	'<select name="thirlev" id="threeCategory0'+columnIndex+'">'+
	            	'<option value="0" selected="selected">三级分类</option>'+
	            '</select>'+
		        '</span>'+
		        '<span><input type="button" class="wms-seach-btn" value="搜索" style="border:none;height:24px;line-height:24px;" id="search_commodity0'+columnIndex+'"></span>'+
		    '</p>'+
		    '<div class="blank10"></div>'+
		    '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="commoditys_container0_clm'+columnIndex+'">'+
		      
		    '</table>'+
		    '<div class="blank10"></div>'+
		    '<p class="select_btn">'+
		    	'<span class="buytime"><a href="javascript:column_select(1,'+columnIndex+')">全选</a> <a href="javascript:column_select(2,'+columnIndex+')">反选</a></span>'+
		        '<span><input type="button" style="border:none;height:24px;line-height:24px;" value="确定" class="sapro_fullpay_cotrol_btn"  onclick="addColumnCommodity('+columnIndex+')"><input type="button" style="border:none;height:24px;line-height:24px;" value="关闭" class="sapro_fullpay_cotrol_btn"  onclick="closeColumnItem('+columnIndex+')"></span>'+
		    '</p>'+
		    '<div class="blank5"></div>');
		reinitifH();
		
		//动态加载某级别分类并初始化Select控件中的option选项集合
		loadSomeoneLevelSelect(columnIndex);
		
		//动态绑定二、三级分类
		bindDynamic23LevelSelect(columnIndex);
		
		//动态绑定搜索按钮事件
		bindDynamicSearchCommodityButton(columnIndex);
				
	});
}
	





//==============================[栏目商品集合操作] begin
/**
	获取对应栏目Index的对象，如果不存在返回null
*/
function getColumnObject(columnIndex){
//alert(COLUMN_commodity.length);
 for(var i = 0 ; i <COLUMN_commodity.length;i++){
 	if(COLUMN_commodity[i]!=null&&COLUMN_commodity[i].index==columnIndex){
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
 	if(COLUMN_commodity[i]!=null&&COLUMN_commodity[i].index==columnIndex){
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

	//==============================[栏目商品集合操作] end



	