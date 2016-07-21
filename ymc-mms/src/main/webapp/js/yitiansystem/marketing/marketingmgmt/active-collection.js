//换购索引
var Index_HG = 1000;

/**
 * 换购商品对象
 */
function HG_commodity(){}

/**
 * 选择商品回调函数
 * @param commodity Commodity对象
 * @return
 */
HG_commodity.prototype.callable_chooseCommodity = function(commodity){
	this.commodity = commodity;
	$("#commodity_name_01").val(commodity.commodityName);
	$("#choose_commodity_hg_id").val(commodity.id);
	$("#choose_commodity_hg_no").val(commodity.no);
	//alert(commodity.commodityName);
};


/**
 * 按品牌促销对象
 */
function Brand(){}

Brand.prototype.callable_chooseBrand = function(id,brandName){
	if(extisCommodityDataArray.indexOf(id)==-1){
		extisCommodityDataArray.push(id);
		var html ='<span id="brand_'+id+'">'+brandName+'　<a href=javascript:delBrand_WITH_commodity_Brand("brand_'+id+'","'+id+'",'+i+')>删除</a></span>';
		$("#WITH_commodity_brand_container").append(html);
		$("#brand_dialog").css('display','none');
	}else{
		alert("改品牌:"+brandName+",已经被选择过了,请选择其他品牌进行促销!");
	}
};

function Commodity_Single(){}
Commodity_Single.prototype.callable_chooseCommodity = function(commodity){
	this.commodity = commodity;
	
	//$("#commodity_name_01").val(commodity.commodityName);
	//$("#choose_commodity_hg_id").val(commodity.id);
	//$("#choose_commodity_hg_no").val(commodity.no);
	//alert(commodity.commodityName);
};

function queryPromotionProject()
{

	var activeStartDate = $('#activeStartDate').val();
	var activeEndDate = $('#activeEndDate').val();
	
	if((activeStartDate == '' && activeEndDate != '') ||(activeStartDate != '' && activeEndDate == ''))
	{
		alert("请选择活动时间");
		return false;
	}
	else if(activeStartDate > activeEndDate)
	{
		alert("活动开始时间不能大于活动结束时间");
		return false;
	}
	
	$("#queryPromotionProjectForm").submit();
}

function queryGroupActive()
{
	var activeStartDate = $('#activeStartDate').val();
	var activeEndDate = $('#activeEndDate').val();
	
	if((activeStartDate == '' && activeEndDate != '') ||(activeStartDate != '' && activeEndDate == ''))
	{
		alert("请选择活动时间");
		return false;
	}
	else if(activeStartDate > activeEndDate)
	{
		alert("活动开始时间不能大于活动结束时间");
		return false;
	}
	
	$("#queryGroupActiveForm").submit();

}


/**
* 活动处理审核
*/
function activeManage(activeId,activeType,activeState,groupActivitiesRecordId)
{
	
	
	if(groupActivitiesRecordId == '' || groupActivitiesRecordId == null)
	{
		groupActivitiesRecordId = -1;
	}

	var params = "activeId="+activeId+"&activeType="+activeType+"&activeState="+activeState+"&groupActivitiesRecordId="+groupActivitiesRecordId;
	
	
	
	showThickBox("活动处理",BASE_PATH+"/yitiansystem/marketing/marketingmgmt/to_ActiveManage.sc?TB_iframe=true&height=550&width=750",false,params);
}

function deleteActivePromotion(activeId,activeType)
{
	if(confirm("你确定要删除该记录吗?"))
	{
		window.location.href=BASE_PATH+"/yitiansystem/marketing/marketingmgmt/d_PromotionActive.sc?activeId="+activeId+"&activeType="+activeType;
	}
}

function addActivePromotion(activeType)
{
	window.location.href=BASE_PATH+"/yitiansystem/marketing/marketingmgmt/toAddPromotionActive.sc?activeType="+activeType;
}

/**
* 选中所有
*/
function checkAll(selectID , selectIDAll)
{	
	var name = document.getElementsByName(selectID);
	
	for(var i = 0; i < name.length ; i++)
	{
		if(document.getElementById(selectIDAll).checked)
		{
			if(!name[i].disabled)
			{
				name[i].checked = true;
			}
			
		}
		else
		{
			name[i].checked = false;
		}
		
	}
}

function u_groupAddWarterCount()
{
	var isSelected = false;
	var activeId = "";

	for (i = 0; i < document.queryGroupActiveForm.activeID.length;i++)
	{
		if (document.queryGroupActiveForm.activeID[i].checked)
		{
			activeId+=document.queryGroupActiveForm.activeID[i].value+",";
			isSelected = true;
		}
	}


	if(document.queryGroupActiveForm.activeID.checked)
	{
		
		activeId=$('#activeID').val();
		isSelected = true;
	}
	
	if (!isSelected)
	{
		alert('请选择要加水的活动记录');
		return false;
	}
	else
	{
		if(confirm("你确定要改活动进行加水操作吗?"))
		{
			var params = "activeId="+activeId;
			showThickBox("加水处理",BASE_PATH+"/yitiansystem/marketing/marketingmgmt/toGroupAddWarterCount.sc?TB_iframe=true&height=450&width=550",false,params);
		}

	}
	
}


/**
* 进入数据导出页面
* @return
*/
function goExportPromotionActiveTrack(activeId,activeType)
{
	window.location.href=BASE_PATH+"/yitiansystem/marketing/marketingmgmt/goPromotionActivieTrackingExport.sc?activeId="+activeId+"&activeType="+activeType;
}
//创建品牌列表-所有品牌
function createBrand_allBrand(){
	$("#brand_brands").empty();
	$("#brand_brands").append("<option value='0'>请选择品牌</option>");
	//加载所有品牌
	var brands = loadBrand();
	if(brands!=null){
		for(var i = 0 ; i < brands.length ;i++){
			$("#brand_brands").append("<option value='"+brands[i].id+"'>"+brands[i].brandName+"</option>");
		}
	}
		//绑定
	bind_brand_brands();
	$("#category_brand #control_vip_rank").show();
	
}
function bind_brand_brands(){
	$("#brand_brands").change(function(){
	
		$("#choose_brand_catb2cs_container").empty();
	
		var b_val = $(this).val();
		if(b_val=="0")
			return;
		var brandTopCatb2cs = loadTopCatByBrandId(b_val);
		$("#brand_catb2cs").empty();
		$("#brand_catb2cs").append("<option value='0'>请选择大类</option>");
		if(brandTopCatb2cs!=null){
			for(var j = 0 ; j < brandTopCatb2cs.length ; j++){
				$("#brand_catb2cs").append("<option value='"+brandTopCatb2cs[j].structName+";"+brandTopCatb2cs[j].id+"'>"+brandTopCatb2cs[j].catName+"</option>");
			}
		}
		
		$("#brand_catb2cs").change(function(){
			
			var rootCatName = $(this).children('option:selected').text();
			var val = $(this).children('option:selected').val();
			if(val=="0"){return;}
			var vals = val.split(";");
			ALL_Cat2 = new Array();
			var childData = loadChildLevel(vals[0]);
			//alert(childData.length+"child's count:");
			var html = '';
			$("#choose_brand_catb2cs_container").empty();
			for(var i = 0 ; i < childData.length ; i++){
				var child = childData[i];
				//alert(child);
				if(child==null)continue;
				 	html +='<div class="control_vip_ra_tt">'+
                            	'<div class="blank10"></div>'+
                            	'<span class="buytime">'+child.catName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
                                '<span class="operate"><a href=javascript:cat2_select(true,"'+child.id+'")>全选</a><a href=javascript:cat2_select(false,"'+child.id+'")>反选</a></span>'+
                                '<div class="blank10"></div></div>';
                            
                            
				var children = loadChildLevel(child.structName);
				//alert(children.length);
				if(children!=null&&children.length>0){
				
					
					html+='<div class="control_vip_ra"><div class="blank10"></div>';
				
					for(var j = 0 ; j <children.length ;j++){
						if(children[j]==null)continue;
						//所有的第三级分类放入缓冲区
						ALL_Cat2.push(children[j].id);
						html +='<input type="checkbox" name="chk_cat2" style="margin-right:5px;" value="'+rootCatName+';'+child.catName+';'+children[j].catName+';'+vals[1]+';'+child.id+';'+children[j].id+'"/>'+
	                            	'<span class="buytime">'+children[j].catName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>';
	                               
					}
					html+='<div class="blank10"></div></div>';
				}
		   }
		   $("#choose_brand_catb2cs_container").html(html);
		});
	});
}


//活动时间段累计下标
var Index_active_time = 100;
//增加活动时间段
function addActiveTime(){
	var sdate = $("#active_start").val();
	var edate = $("#active_stop").val();
	if(sdate.length<=0||edate.length<=0){
		alert("请选择活动时间段!");
		return false;
	}
	Index_active_time++;
	if(!timeIsExist(sdate,edate)){
		$("#active_start").val("");
		$("#active_stop").val("");
		CHOOOSE_time.push({"index":Index_active_time,"startTime":sdate,"endTime":edate});
		var tmpTime = sdate+" 至  "+edate; 
		$("#activeTime").append('<span id="active_time_'+Index_active_time+'"><input type="hidden" name="activeStopTime" value="'+edate+'"><input type="hidden" name="activeStartTime" value="'+sdate+'"><span>'+tmpTime+'</span><a href="javascript:delActiveTime('+Index_active_time+')">删除</a><br/></span>');
	}else{
		alert("添加活动时间段已存在!");
	}
}


//已选择(增加)活动时间段 
var CHOOOSE_time = new Array();



//删除活动时间段 
function delActiveTime(index){
	for(var i = 0 ; i < CHOOOSE_time.length ; i++){
		if(CHOOOSE_time[i].index == index){
			CHOOOSE_time = CHOOOSE_time.del(i);
			$("#active_time_"+index).remove();
		}
	}

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

//查找库存数量
function selectStore(inputId,commodityId){
	getByID(inputId).innerHTML = loadProductStore(commodityId);
}


//修复多栏目bug06.02
function pushDataToColumn(columnIndex,arrData){
 for(var i = 0 ; i <COLUMN_commodity.length;i++){
 	if(COLUMN_commodity[i]!=null&&COLUMN_commodity[i].index==columnIndex){
 		for(var j = 0 ; j<arrData.length;j++){
 			COLUMN_commodity[i].data.push(arrData[j]);
 		}
 	}
 }
}

//获取随机数字进行组装唯一id值
function getRandom(){
	var dt = new Date();
	return parseInt(Math.random()*100000)+parseInt(Math.random()*100000)+dt.getYear()*dt.getMonth()*dt.getDate();
}



var CHOOSE_Brand_Cat = new Array();
/**
 * Mrvoce
 * 2011.06.07新增品牌类别[功能]
 */
function addBrandCatb2c(){
	//alert("addBrandCatb2c");
	var brand_cat_check_index = 1;
	//1.获取品牌id和名称
	var b_name = $("#brand_brands").children('option:selected').text();
	var b_value= $("#brand_brands").children('option:selected').val();
	if(b_value=="0"){
		//alert("请选择品牌选项!");
		return;
	}
	//2.获取id和名称
	//var root_name = $("brand_catb2cs").children('option:selected').text();
	//var root_value = $("brand_catb2cs").children('option:selected').val();
	var html = '';
	var b_exist = false;
	for(var j = 0 ; j < CHOOSE_Brand_Cat.length;j++){
		if(CHOOSE_Brand_Cat[j][0]==b_value){
			b_exist = true;
			break;
		}
	}
	if(!b_exist){
		html+='<p id="b_catb2c_items_'+b_value+'" name="brandCat_brand">'+b_name+'</p>';
	}
	//3.获取大类、二级分类、三级分类的id和名称
	//4.组装view
	var chk_cat2s = document.getElementsByName("chk_cat2");
	for(var i = 0 ; i < chk_cat2s.length ;i++){
		if(chk_cat2s[i].checked){
			brand_cat_check_index++;
			var name1,name2,name3,value1,value2,value3;
			var val = chk_cat2s[i].value.split(";");
			name1 = val[0];
			name2 = val[1];
			name3 = val[2];
			value1 = val[3];
			value2 = val[4];
			value3 = val[5];
			var exist = false;
			for(var j = 0 ; j < CHOOSE_Brand_Cat.length;j++){
				if(CHOOSE_Brand_Cat[j][0]==b_value && CHOOSE_Brand_Cat[j][1]==value3){
					exist = true;
					break;
				}
			}
			
			if(exist){
				continue;
			}
			
			
			if(brand_cat_check_index%2==0){
				html+='<ul>';
			}
			CHOOSE_Brand_Cat.push([b_value,value3]);
			var formHtml = '<input type="hidden" name="brandName" value="'+b_name+'"/><input type="hidden" name="brandId" value="'+b_value+'"/>'+
				'<input type="hidden" value="'+name1+'" name="categoryName">'+
				'<input type="hidden" value="'+name2+'" name="secondCategoryName">'+
			'<input type="hidden" value="'+name3+'" name="thirdCategoryName">'+
			'<input type="hidden" value="'+value1+'" name="categoriesId">'+
			'<input type="hidden" value="'+value2+'" name="secondCategoriesId">'+
			'<input type="hidden" value="'+value3+'" name="thirdCategoriesId">';
        	html+='<li id="choose_brand_cat_item'+b_value+'_'+value3+'" name="brandCat_cats">'+formHtml+'<span href="#">'+name1+'</span> &gt; <span href="#">'+name2+'</span> &gt; <span href="#">'+name3+'</span><a href=javascript:delBrandCat("'+b_value+'","'+value3+'")><img alt="" src="'+BASE_PATH+'/images/yitiansystem/delete.gif"></a></li>';
        	if(brand_cat_check_index!=2&&brand_cat_check_index%2==0){
				html+='</ul>';
			}
		}
	}
	if(!b_exist){
		html+='<div class="blank10"></div>';
		$("#control_vip_rank_catb2c_brand").append(html);
	}else{
		$('#b_catb2c_items_'+b_value).after(html);
	}
}

function delBrandCat(brandId,thirdCatId){
	$("#choose_brand_cat_item"+brandId+"_"+thirdCatId).remove();
	for(var i = 0 ; i < CHOOSE_Brand_Cat.length;i++){
		if(CHOOSE_Brand_Cat[i][0]==brandId && CHOOSE_Brand_Cat[i][1]==thirdCatId){
			CHOOSE_Brand_Cat = CHOOSE_Brand_Cat.del(i);
			var isHave = false;
			for(var j=0;j<CHOOSE_Brand_Cat.length;j++){
				if(CHOOSE_Brand_Cat[j][0]==brandId){
					isHave = true;
					break;
				}
			}
			if(!isHave){
				$("#b_catb2c_items_"+brandId).remove();
			}
		}
	}
}

//全选或反选品类
function cat2_select(isSelect,secondCatID){	
	var catChks = document.getElementsByName("chk_cat2");
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

/**
* 获取优惠券
*/
function getCoupon()
{
	showThickBox("优惠券",BASE_PATH+"/yitiansystem/marketing/marketingmgmt/getCouponList.sc?TB_iframe=true&height=550&width=750",false);
}

/**
 * 送赠品
 * @return
 */
function getGift(inputIndex){
	 var param = "inputIndex="+inputIndex;
	showThickBox("送赠品",BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadCommodity_Gift.sc?TB_iframe=true&height=550&width=750",false,param);
}

/**
 * 新增是否支持优惠券
 */
function isSupportCoupons_click(obj){
	if(obj.checked)
		$("#input_yhq").val("1");
	else
		$("#input_yhq").val("0");
}
//----------------------------------------------------------------------------
//分页索引
var search_commodityIndex = 1;

//分页换购
function _loadPage(pageNo,containerID,threeCategoryID,checkID,columnIndex){
	if(pageNo<=0){
		alert("当前是首页!");
		return;
	}
	//alert("threeCategoryID:"+threeCategoryID);
	var commodity = buildCommodityData(pageNo,threeCategoryID,columnIndex);
	
	var hg = new HG_commodity();
	
	_load(commodity,hg,containerID,threeCategoryID,checkID,columnIndex);
}
function _load(commodity,hg,containerID,threeCategoryID,checkID,columnIndex){
		$("#"+containerID).html('<tr><th width="10%">选择</th><th width="15%">商品编码</th><th width="35%">商品名称</th><th width="10%">优购价</th><th width="10%">市场价</th><th width="20%">商品分类</th></tr>');
		pageCommodityDataArray = new Array();
		var html = '';
		var tmpCommodity = null;
	 	var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
		if(commodity!=null && commodity.data !=null && commodity.data.length>0){
			
			var arrData = new Array();
			for(var i = 0 ; i <commodity.data.length ;i++){
				commodity_id             = commodity.data[i].id;
				commodity_no             = commodity.data[i].no;
				commodity_commodityName  = commodity.data[i].commodityName;
				commodity_publicPrice    = commodity.data[i].salePrice;
				commodity_salePrice      = commodity.data[i].publicPrice;
				commodity_catName      = commodity.data[i].catName;
				
				tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice,commodity_salePrice,commodity_catName);
					
				arrData.push(tmpCommodity);
				pageCommodityDataArray[i] = {"index":i,"commodity":tmpCommodity,"fn":hg};
				
				html+='<tr class="search_commodity01"><td align="center"><input type="checkbox" name="'+checkID+'" value="'+i+';'+commodity_id+'" id="chk_sure_hg'+i+'"></td><td  align="center">'+commodity_no+'</td><td  align="center">'+commodity_commodityName+'</td>'+
				'<td align="center">'+commodity_publicPrice+'</td><td align="center">'+commodity_salePrice+'</td><td align="center">'+commodity_catName+'</td></tr>';
			}
			
			//检测是否属于新增栏目，真：则进行新增对应数据结果，否：
			var columnData = getColumnObject(columnIndex);
			if(columnData==null){
				COLUMN_commodity[columnIndex-2]= {"index":columnIndex,"data":arrData};
				//alert("加入:"+arrData.length+"--"+COLUMN_commodity[0].data[0].id+">>");
			}else{//放入数据
				pushDataToColumn(columnIndex,arrData);
			}
			html+='<tr class="search_commodity01"><td colspan="5" id="page_" align="right"><a href=javascript:_loadPage('+((search_commodityIndex-2)<=0?0:search_commodityIndex-2)+',"'+containerID+'","'+threeCategoryID+'","'+checkID+'",'+columnIndex+')>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=javascript:_loadPage('+search_commodityIndex+',"'+containerID+'","'+threeCategoryID+'","'+checkID+'",'+columnIndex+')>下一页</a></td><td align="center">当前是第:<b>'+((search_commodityIndex-1)<=0?0:search_commodityIndex-1)+'</b>页</td></tr>';
		}else{
			html+='<tr class="search_commodity01"><td colspan="5" id="page_" align="right"><a href=javascript:_loadPage('+((search_commodityIndex-1)<=0?0:search_commodityIndex-1)+',"'+containerID+'","'+threeCategoryID+'","'+checkID+'",'+columnIndex+')>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=javascript:_loadPage('+1+',"'+containerID+'","'+threeCategoryID+'","'+checkID+'",'+columnIndex+')>首页</a></td><td align="center">当前是第:<b>'+(search_commodityIndex<=0?0:search_commodityIndex)+'</b>页</td></tr>';
		}
			
		$("#"+containerID).append(html);
}
	
//获取、组合商品数据
function buildCommodityData(pageNo,threeCategoryID,columnIndex){
		//alert("threeCategoryID:2"+threeCategoryID);
		//判断商品编码、商品名称是否有值，如果有值则进行模糊搜索
		var hg_commodity_no   = $("#commodity_no_"+columnIndex).val();
		var hg_commodity_name = $("#commodity_name_"+columnIndex).val();
		
	 	var commodity = null;
		
		if(hg_commodity_no.length > 0 || hg_commodity_name.length > 0){
			var jsonDataArray = loadCommodityByNo_Name(hg_commodity_name,hg_commodity_no,pageNo);
			if(jsonDataArray==null){
				alert("没有查到相关商品信息!");
				return;
			}
			commodity = new Commodity(jsonDataArray[0],jsonDataArray[1],jsonDataArray[2],jsonDataArray[3],jsonDataArray[4]);
		}else{
			/*var selValue = $("#"+threeCategoryID).children('option:selected').val();
			var structName = selValue.split(";")[0];*/
			//alert("分类搜索");
			var structName = "";
			var selValue1 = $("#rootCattegory0"+columnIndex).children('option:selected').val();
			var selValue1_struct = selValue1.split(";")[0];
			
			if(selValue1_struct!="0"){
				structName = selValue1_struct;
			}
			
			var selValue2 = $("#secondCategory0"+columnIndex).children('option:selected').val();
			var selValue2_struct = selValue2.split(";")[0];
			if(selValue2_struct!="0"){
				structName = selValue2_struct;
			}
			
			var selValue3 = $("#threeCategory0"+columnIndex).children('option:selected').val();
			var selValue3_struct = selValue3.split(";")[0];
			if(selValue3_struct!="0"){
				structName = selValue3_struct;
			}
			
			//加载三级分类的商品
			commodity =  _public_loadLevelCommodityList(structName,pageNo);
		}
		if(commodity!=null && commodity.data !=null && commodity.data.length>0){
			search_commodityIndex = pageNo+1;
		}else{
			//alert("没有数据");
			search_commodityIndex--;
		}
		return commodity;
}


//加载销售价
function selectMoney(containerId,id){
	$("#"+containerId).html(loadProductPrice(id));
}
 
//删除换购
function delH(id){
	$("#hg_item_"+id).remove();
	$("#hg_clist_"+id).remove();
}


//满额活动-按品牌促销-需求修改-列表所有品牌
function fillWithBrand(){
	var data = loadBrand();
	if(data==null||data.length<=0){
		alert("无品牌信息，请先添加品牌信息!");
		return;
	}
	var html = '<div class="blank10"></div>';
	for(var i = 0 ; i <data.length;i++){
		html+='<input type="checkbox" name="brand" value="'+data[i].brandName+';'+data[i].id+'" id="brand_'+data[i].id+'" style="margin-right:5px;"/>'+
        	'<span class="buytime">'+data[i].brandName+'&nbsp;&nbsp;&nbsp;&nbsp;</span>';
    	if(i!=0&&i%7==0){
       	 	html+='<div class="blank5"></div>';
        }
    	if((i+1)==data.length){
    		html+='<div class="blank5"></div>';
    	}
	}
	
	$("#brands_container").html(html);
	$("#brands_container").show();	
	
}

function hideMoreBank(){
	$("#more_bank_inputs1").hide();
	$("#more_bank_inputs2").hide();
}
