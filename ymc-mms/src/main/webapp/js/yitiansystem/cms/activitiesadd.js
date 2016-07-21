//分页
// Josn数据
var staticNum = 1;
var tempSaticNum=0;
var basePathURL = "";
var pageSize = 10;// 一页显示多少条
var pageNo = 1;// 页面编号
var data = eval('([])');
var totalCount = data.length;// 记录总数
var pageCount = parseInt((totalCount + pageSize - 1) / pageSize);// 一共多少页
	

	// 初始化第一页
function initPager (pageNo) {
	 window.eval("pageClickCallback('"+tempSaticNum+"','"+pageNo+"')");
}

// 回调函数
pageClickCallback = function(num,pageNo) {
	var inDivId = "#page-T-"+num;
	var tempData = getData(pageNo);
	$(inDivId).html(myrenderpager(pageNo,pageCount,num,"pageClickCallback"));
	var url =  basePathURL+"/yitiansystem/cms/activitiesTopics/queryGoodsByListIds.sc";
	$.post(url,{"ids":tempData},checkGoodsCallBack);
}

	// 获取指定页数数据
function getData(pageNo) {
	var data_temp = [];
	var start = (pageNo - 1) * pageSize;
	for(var i=start; i<(start+pageSize)&&i<=(totalCount-1); i++){
		data_temp.push(data[i]);
	}
	return data_temp.join(",");
}



function checkGoodsCallBack(str){
	var jsonData = eval("("+str+")");
	var html = [];
	html.push("<table width='100%'>");
	html.push("<tr><td>选择</td><td>商品编号</td><td>商品名称</td> </tr>");
	for(var i = 0;i<jsonData.length;i++){
		html.push("<tr><td><input type='radio' onclick=onCheckOneGoods('"+tempSaticNum+"','"+jsonData[i].id+"','"+jsonData[i].no+"','"+jsonData[i].commodityName+"') name = 'a'/></td><td>"+jsonData[i].no+"</td><td>"+jsonData[i].commodityName+"</td> </tr>");
	}
	html.push('</table><a href=javascript:exitChooseGoods("'+tempSaticNum+'")>取消</a>');
	$("#showGoodsDiv-T-"+tempSaticNum).html(html.join(""));
}

Array.prototype.contains=function(obj){
	for(var i=0;i<this.length;i++){
		if(this[i] == obj){
			return i;
		}
	}
	return -1;
}
//去除字符串前后空格
String.prototype.trim=function(){
	return  this.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"");
}

function onCheckOneGoods(num,id,no,goodsName){
	var insertHTML="<tr><td>"+no+"</td><td>"+goodsName+"<input type='hidden' name='commodityId' value='"+id+"'/> <input type='hidden' name='commodityNum' value='"+num+"'/></td>";
	insertHTML=insertHTML+"<td><font class='ft-cl-r'>*</font><input type='text' name='orderSnCommodity' value='1'/></td><td><font class='ft-cl-r'>*</font><select name='displayCommodity'><option value='1'>是</option><option value='0'>否</option></select></td>";
	insertHTML= insertHTML+"<td><input type='button' onclick=removeGoods('commidityList-T-"+num+"',this) value='删除'/></td></tr>";
	if(!checkIsContains(num,id)){
		$("#commidityList-T-"+num).append(insertHTML);
	}
	exitChooseGoods(num);
}

function checkIsContains(num,id){
	var arry = new Array;
	$("#commidityList-T-"+num+" input[name='commodityId']").each(function (index, domEle) {
		arry.push(domEle.value);
	});
	if(arry.contains(id) == -1){
		return false;
	}
	return true;
}

//添加新栏目
function appendDIVToFather(){
	var insertHTML = '<tr id="childrenCloneNode">'
	+'<td>' 
	+'<div class="blank10"></div>' 
	+'<div class="order-cf-box-1">'
	+'<div class="blank10"></div>'
	+'栏目标题：<font class="ft-cl-r">*</font><input type="text" style="width:100px;" name="tagsName"/>&nbsp;&nbsp;&nbsp;&nbsp;'
	+'图片文字：<font class="ft-cl-r">*</font><input type="text" title="最多三个英文字母" style="width:50px;" name="labelTitle" maxLength="3"/>&nbsp;&nbsp;&nbsp;&nbsp;'
	+'更多连接路径：<input type="text"  name="morePath"/>&nbsp;&nbsp;&nbsp;&nbsp;'
	+'排序号：<font class="ft-cl-r">*</font><input style="width:30px;" type="text" name="orderSnLabel"/><input type="hidden" name="labelNum" value="'+staticNum+'"/>&nbsp;&nbsp;&nbsp;&nbsp;'
	+'是否启用：<font class="ft-cl-r">*</font>&nbsp;&nbsp;<select name="displayLabel"><option value="1">是</option><option value="0">否</option></select>&nbsp;&nbsp;&nbsp;&nbsp;'
	+'<input type="button"  value="添加推荐商品" ' 
	+'onclick=appendCommidityGoods("'+staticNum+'") />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
	+'<input type="button" value="删除此栏目" onclick=removeDIVFromFather("fatherCloneNode",this) /><br>'
	+'<div class="blank10"></div>'
	+'<hr>'
	+'<div class="blank10"></div>'
	+'<div id="checkedGoodsDiv-T-'+staticNum+'">'
	+'<table width="100%">'
	+'<tbody id="commidityList-T-'+staticNum+'">'
	+'<tr>'
	+'<td>商品编号</td>'
	+'<td>商品名称</td>'
	+'<td>排序号</td>'
	+'<td>是否显示</td>'
	+'<td>操作</td>'
	+'</tr>'
	+'</tbody>'
	+'</table></div>'
	+'<div id="showGoodsDiv-T-'+staticNum+'" style="display:none;"></div>'
	+'<div class="paginator" style="float:left; width:70%;" id="paginator-T-'+staticNum+'" style="display:none;"><p id="page-T-'+staticNum+'" class="page"></p></div>'
	+'</div>'
	+'</td>'
	+'</tr>';
	$("#fatherCloneNode").append(insertHTML);
	staticNum = staticNum+1;
}

function checkGoodsInfo(){
	var goodsList = $("tbody[id^='commidityList-T-']");
	for(var j=0;j<goodsList.size();j++){
		var tempSnCommodityList = $("#"+goodsList[j].id+" input[name='orderSnCommodity']");
		if(tempSnCommodityList.size()<=0){
			alert("有栏目没有推荐商品,请推荐商品后再提交,或删除此栏目");
			return false;
		}
		for(var k = 0 ;k<tempSnCommodityList.size();k++){
			var tempSn= tempSnCommodityList[k].value;
			tempSn = tempSn.trim();
			if(tempSn == ""){
				alert("有商品排序号为空,请填写后再提交");
				return false;
			}
			if(!/^[1-9]\d*$/.test(tempSn)){
				alert("有商品排序号填写错误,不是数字,请更正后再提交");
				return false;
			}
		}
	}
	return true;
}

function cleanLabels(){
	$("#fatherCloneNode").empty();
	staticNum = 1;
	appendDIVToFather();
}

function removeDIVFromFather(faid,obj){
	document.getElementById(faid).removeChild(obj.parentNode.parentNode.parentNode);
}

function removeGoods(faid,obj){
	document.getElementById(faid).removeChild(obj.parentNode.parentNode);
}

function appendCommidityGoods(tId){
	if(tempSaticNum != 0 && tempSaticNum != tId){
		exitChooseGoods(tempSaticNum);
	}
	tempSaticNum = tId;
	if(staticNum < tempSaticNum) {staticNum = parseInt(tempSaticNum)+1;}
	var activeId = $("#findActivitiesList").val();
	if(activeId == -1){
		alert("请选择活动");
		return;
	}
	var url = basePathURL+"/yitiansystem/cms/activitiesTopics/queryGoods.sc";
	$.post(url,{"promotionActiveId":activeId},queryGoodsBackFunction);
}

// 查找活动
function queryActivities(basePath){
	basePathURL = basePath;
	var activiType = $("#activitiesTypes").val();
	if(activiType == -1){
		alert("请选择活动类型");
		return;
	}
	
	var startTime = $("#activitiesStartTime").val();
	if(startTime == ""){
		alert("请选择活动开始时间");
		return;
	}
	
	var endTime = $("#activitiesEndTime").val();
	if(endTime == ""){
		alert("请选择活动结束时间");
		return;
	}
	var url = basePath+"/yitiansystem/cms/activitiesTopics/queryMarketingList.sc";
	
	$.post(url,{"activeType":activiType,"startTime":startTime,"endTime":endTime},function(str){
		 $("#findActivitiesList").empty();
		 $("#findActivitiesList").append('<option value="-1">--请选择活动--</option>');
		 var jsonStr = eval("("+str+")");
		 for(var i=0;i<jsonStr.length;i++){
			 $("#findActivitiesList").append('<option value="'+jsonStr[i].id+'">'+jsonStr[i].activeName+'</option>');
		 }
	});
}



//查找商品回调函数
function queryGoodsBackFunction(str){
	data = eval("("+str+")");
	totalCount = data.length;// 记录总数
	pageCount = parseInt((totalCount + pageSize - 1) / pageSize);// 一共多少页
	initPager (1);
	$("#checkedGoodsDiv-T-"+tempSaticNum).hide();
	$("#showGoodsDiv-T-"+tempSaticNum).show();
	$("#paginator-T-"+tempSaticNum).show();
}


function exitChooseGoods(str){
	$("#checkedGoodsDiv-T-"+str).show();
	$("#showGoodsDiv-T-"+str).empty();
	$("#page-T-"+str).empty();
	$("#showGoodsDiv-T-"+str).hide();
	$("#paginator-T-"+str).hide();
}

//-------------------------------
function myrenderpager(currentpage, pagecount,num, buttonClickCallback){
	var pagestr = ""; //组装的填充HTML字符串
	var breakpage = 4; 
	var currentposition = 4; 
	var breakspace = 2; 
	var maxspace = 4; 
	var prevnum = currentpage-currentposition;
	var nextnum = currentpage+currentposition;
	if(prevnum<1) prevnum = 1;
	if(nextnum>pagecount) nextnum = pagecount;
	pagestr += (currentpage==1)?'<span class="next">上一页</span>':'<a class="next" href=javascript:'+buttonClickCallback+'("'+num+'",'+(currentpage-1)+')>上一页</a>';
	if(prevnum-breakspace>maxspace){
		for(i=1;i<=breakspace;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
		pagestr += '<span class="break">...</span>';
		for(i=pagecount-breakpage+1;i<prevnum;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
	}else{
		for(i=1;i<prevnum;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
	}
	for(i=prevnum;i<=nextnum;i++){
		pagestr += (currentpage==i)?'<span class="thispage">'+i+'</span>':'<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
	}
	if(pagecount-breakspace-nextnum+1>maxspace){
		for(i=nextnum+1;i<=breakpage;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
		pagestr += '<span class="break">...</span>';
		for(i=pagecount-breakspace+1;i<=pagecount;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
	}else{
		for(i=nextnum+1;i<=pagecount;i++)
			pagestr += '<a href=javascript:'+buttonClickCallback+'("'+num+'",'+i+')>'+i+'</a>';
	}
	pagestr += (currentpage==pagecount)?'<span class="next">下一页</span>':'<a class="next" href=javascript:'+buttonClickCallback+'("'+num+'",'+(parseInt(currentpage)+1)+')>下一页</a>';
	return pagestr;
}
//-------------------------------
//提交表单
function save(){
	var imgHz = [".gif",".jpg",".jpeg",".png"];
	var filePath = $("#uploadFile").val();
	if(filePath==""){
		alert("请选择图片！");
		return false;
	}
	var pre = filePath.substring(filePath.lastIndexOf("."));
	if(imgHz.contains(pre.toLowerCase())==-1){
		alert("请确认您选择的是图片！");
		return false;
	}
	if(checkLabelInfo() && checkBaseInfo() && checkGoodsInfo()){
		$("#formActivity").submit();
	}
}

function checkBaseInfo(){
	var activitiesId = $("#findActivitiesList").val();
	if(activitiesId == -1){
		alert("请选择活动");
		return false;
	}
	var activitiesName = $("#activitiesName").val();
	activitiesName = activitiesName.trim();
	if(activitiesName==""){
		alert("请填写活动页名称,空格不算");
		return false;
	}
	return true;
}

function checkLabelInfo(){
	var i=0;
	//栏目标题
	var labels = $("input[name='tagsName']");
	if(labels.size()<=0){
		alert("没有活动页栏目,请添加至少一个活动页栏目");
		return false;
	}
	for(i=0;i<labels.size();i++){
		var tempVal = labels[i].value;
		tempVal =tempVal.trim();
		if(tempVal == ""){
			alert("有活动页栏目标题为空,请填写后再提交");
			return false;
		}
	}
	//栏目排序号
	var orderSnLabels = $("input[name='orderSnLabel']");
	for(i=0;i<labels.size();i++){
		var tempSnVal = orderSnLabels[i].value;
		tempSnVal =tempSnVal.trim();
		if(tempSnVal == ""){
			alert("有活动页栏目排序号为空,请填写后再提交");
			return false;
		}
		if(!/^[1-9]\d*$/.test(tempSnVal)){
			alert("有活动页栏目排序号填写错误,不是数字,请更正后再提交");
			return false;
		}
	}
	
	//栏目图片文字
	var labelTitle = $("input[name='labelTitle']");
	for(i=0;i<labelTitle.size();i++){
		var tempLabelTitle = labelTitle[i].value;
		tempLabelTitle =tempLabelTitle.trim();
		if(tempLabelTitle == ""){
			alert("有活动页栏目图片文字为空,请填写后再提交");
			return false;
		}
	}
	
	//栏目图片文字
	var morePath = $("input[name='morePath']");
	for(i=0;i<morePath.size();i++){
		var tempmorePath = morePath[i].value;
		tempmorePath =tempmorePath.trim();
		if(tempmorePath == ""){
			alert("有活动页栏目更多连接为空,请填写后再提交");
			return false;
		}
	}
	
	return true;
	
}

function updateSave(){
	if(checkLabelInfo() && checkBaseInfo() && checkGoodsInfo()){
		$("#formActivity").submit();
	}
}

