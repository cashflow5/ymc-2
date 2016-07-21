$(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$(".yg-search-input").bind('keypress',function(event){
        if(event.keyCode == "13"){
        	if($("#starttime-2").val()=='' 
        		|| $("#endtime-2").val()==''){
        		$(".commoditySpan a").removeClass("active");
        		$(".commoditySpan a:eq(0)").addClass("active");
        		$("#dateCondition").val(1);
        		setDatePre(1);
        	}
        	searchForm();
        }
    });
	$('#dimensions').on('change', function(){
		loadTable(this.value);
	});
	$('#analysisQuota').on('change','#templateSel',function(){
		loadTemplateIndex(this.value);
	});
    $(".yg-search-input").data('value','请输入商品名称或商品编码');
    $(".yg-search-input").focus(function(){
        if( $(this).val()==$(this).data('value')){
             $(this).val("");
        }
     }).blur(function(event) {
         if($(this).val()==""){
             $(this).val($(this).data('value'));
         }
     });
    setDate(1);
    if(isExportData=="loading"){
    	//正在导出，导出按钮disable，并且提示进度
    	$("#exportData").attr("href","javascript:void(0);");
    	$("#exportData").css("color","#555555");
    	$("#progressBar").removeClass("hide");
    	$("#progressBar span").text(progress);
    	setId = setInterval(function() {
	      	getExportResult();
	      }, 5000);
    }else if(isExportData=="loaded"){
    	//导出结束，导出按钮able，并且提供下载
    	$("#exportData").attr("href","javascript:exportExcel();");
    	 ygdg.dialog.confirm("您上次操作导出报表数据，现已导出完毕，是否下载？",function(){
    		 location.href=basePath+"/report/reportAnalysisDownload.sc?name="+exportResult+"&_t="+new Date().getTime();
    	 });
    }
});


function setDatePre(index){
	$(".commoditySpan a").removeClass("active");
	$(".commoditySpan a:eq("+parseInt(index-1)+")").addClass("active");
	$("#dateCondition").val(index);
	var date = new Date();
	var dateStr = "";
	if(index==2){
		dateStr = addDate(date,-7);
	}else if(index==3){
		dateStr = addDate(date,-30);
	}else{
		dateStr = addDate(date,-1);
	}
	$("#starttime-2").val(dateStr);
	$("#endtime-2").val(addDate(date,-1));
}

function setDate(index){
	setDatePre(index);
	var serchinput = $(".yg-search-input");
	if("1"==$("#dimensions").val()){
    	url = "/report/prodAnalyseList.sc";
    	serchinput.val("请输入商品名称或商品编码").data('value','请输入商品名称或商品编码');	
    }else{
    	url = "/report/catagoryAnalyseList.sc";
    	serchinput.val("请输入分类名称关键词").data('value','请输入分类名称关键词');
    }
    loadData(1,pageSize,url);
}

//根据维度加载表格数据 商品维度，分类维度
function loadTable(dval){
	var serchinput = $(".yg-search-input");
    	// 商品维度， 商品分析
    	if(dval=="1"){
    		serchinput.val("请输入商品名称或商品编码").data('value','请输入商品名称或商品编码');	
    		loadData(curPage,pageSize,"/report/prodAnalyseList.sc");	    		
    		// 分类维度，分类分析	
    	}else {
	    	//输入框提示词改变
    		serchinput.val("请输入分类名称关键词").data('value','请输入分类名称关键词');
    		loadData(curPage,pageSize,"/report/catagoryAnalyseList.sc");    	
    	}
}

//加载分类子表
function loadCataSubTable(tbObj){
		var id = tbObj.id;
		//alert(id);
        var arr=[],trcontent,
        $this = $(tbObj),
        i = $this.find('i'),
        //span_id=$this.attr("id"),
        table_parent = $this.parent().parent('.table-parent'),
        tbody=table_parent.parent('tbody'),
        table_child = table_parent.siblings('.table-child');
        var tdSize = $("#prodAnalyseTable").find("thead th").size();
        //判断是展开还是收缩的,收缩的
        if (!i.hasClass('up')) {
        	tbody.append('<tr class="table-child"><td colspan="'+tdSize+'"><div class="loading_img loadImg"><img src='+basePath+'"/manage/report/images/loading-spinner-blue.gif"/></div></td></tr>');
            i.html('&#xe604;');
            i.addClass('up');
            // 加载品类分析子列表 
		    $.ajax({
				cache : false,
				type : 'POST',
				data:$("form.commodityStyle").serialize()+"&secondCategory="+id,
				dataType : "json",
				url : basePath+"/report/catagoryAnalyseSubList.sc", 
				success : function(data) {
					var child,type,columnIndex;
					 	child=data.child;
	                    columnIndex = data.columnIndex;
	                $(child).each(function(index, el) {//把对应的id的子元素循环出来
	                	 //商品分类
                        type=el.COMMODITY_CATNAME_THREE;
                        trcontent = '<tr class="table-child"><td>'+type+'</td>';
                        $.each(columnIndex,function(i,n){
	                    	trcontent += '<td>';
	                    	if(typeof(el[n.en_name])!='undefined'){
	                    		if(n.digit_decimal!='0'){
	                    			if(n.percent=='1'){
		                    			trcontent +=(el[n.en_name]*100).toFixed(2)+"%";
		                    		}else{
		                    			trcontent +=el[n.en_name].toFixed(2);
		                    		}
	                    		}else{
	                    			trcontent +=el[n.en_name];
	                    		}
	                    	}else{
	                    		if(n.digit_decimal!='0' && n.percent=='1'){
		                    		trcontent +='0%';
	                    		}else{
	                    			trcontent +=0;
	                    		}
	                    	}
	                    	trcontent +='</td>';
	                    });
                    	trcontent +='</tr>';
                    	arr.push(trcontent);
	                });
	                $(".loadImg").parents("tr.table-child").remove();
	                if(i.hasClass('first')){//判断是否是第一次点击
                    	i.removeClass('first');
                        tbody.append(arr.join(""));
                    }else{
                        table_child.show();
                    }
				}
			});
        // 展开的   
        } else {
            i.html('&#xe61a;');
            i.removeClass('up');
            table_child.hide();
        }
}

function exportExcel(){
	//根据时间跨度导出
	$.ajax({
		type: "POST",
		url: basePath+"/report/createReportAnalysis.sc",
		data: $("form.commodityStyle").serialize(),
		dataType: "html",
		success: function(data) {
			if(data=='success'){
				$("#exportData").attr("href","javascript:void(0);");
				$("#exportData").css("color","#555555");
				$("#progressBar").removeClass("hide");
			}else if(data=="loading"){
				  ygdg.dialog.alert("上一次导出数据正在执行，请稍后再试！");
			}
			setId = setInterval(function() {
		      	getExportResult();
		     }, 5000);
   		}
	});
}

var getExportResult = function(){
	$.get(basePath+"/report/getExportResult.sc",function(data){
		if(data.result=="true"){
			$("#exportData").css("color","#0b80ed");
			$("#exportData").attr("href","javascript:exportExcel();");
			$("#progressBar span").text(0);
			$("#progressBar").addClass("hide");
			window.clearInterval(setId);
			location.href=basePath+"/report/reportAnalysisDownload.sc?name="+data.url+"&_t="+new Date().getTime();
		}else{
			//进度条的值变化
			if(data.progress<0){
				$("#exportData").css("color","#0b80ed");
				$("#exportData").attr("href","javascript:exportExcel();");
				$("#progressBar span").text(0);
				$("#progressBar").addClass("hide");
				window.clearInterval(setId);
				ygdg.dialog.alert("导出失败，泪奔呀！");
			}else{
				$("#progressBar span").text(data.progress);
			}
		}
	},"json");
};

function searchForm(){
	if("1"==$("#dimensions").val()){
    	url = "/report/prodAnalyseList.sc";
    }else{
    	url = "/report/catagoryAnalyseList.sc";
    }
    loadData(1,pageSize,url);
}

//新增模板
function addTemplate(){
	var checkedNum = parseInt($("#checkedNum","#analysisQuota").text());
	if(checkedNum<5 || checkedNum>8){
		ygdg.dialog({content:'<span class="Red">请选择5-8项指标<span>!',time:2});
	}else{
		ygdg.dialog.prompt("请输入模板名称",function(name){
			if($.trim(name)!='' && name.length<=18){
				var ids = $("#analysisQuota").find('.quota-general:checked').map(function(){
					return $(this).val();
				}).get().join(",");
				$.post(basePath+"/report/saveTemplate.sc",{name:name,ids:ids},function(data){
					if(data.result=="success"){
						$("#templateSel").append('<option value="'+data.templateId+'" selected="selected">'+name+'</option>').data('ui').refresh();
						ygdg.dialog({content:'<span class="Blue">新增“'+name+'”模板成功<span>!',time:2});
					}else{
						ygdg.dialog({content:'<span class="Red">新增“'+name+'”模板失败<span>!',time:2});
					}
				},"json");
			}else{
				if(name.length>18){
					ygdg.dialog({content:'<span class="Red">请输入18个字以内的模板名称<span>!',time:2});
				}
				$(".aui_content").find("input").focus();
				return false;
			}
		},"");
	}
}

//更新模板
function updateTemplate(){
	//预置模板不能更新、删除
	var checkedNum = parseInt($("#checkedNum","#analysisQuota").text());
	if(checkedNum<5 || checkedNum>8){
		ygdg.dialog({content:'<span class="Red">请选择5-8项指标<span>!',time:2});
	}else{
		var ids = $("#analysisQuota").find('.quota-general:checked').map(function(){
			return $(this).val();
		}).get().join(",");
		var selVal = $("#templateSel").val();
		if(selVal!='0'){
			$.post(basePath+"/report/updateTemplate.sc",{id:selVal,ids:ids},function(text){
				if(text=="success"){
					ygdg.dialog({content:'<span class="Blue">更新模板成功<span>!',time:2});
				}else{
					ygdg.dialog({content:'<span class="Red">更新模板失败<span>!',time:2});
				}
			},"text");
		}else{
			ygdg.dialog({content:'<span class="Red">系统预置模板不能更新<span>!',time:2});
		}
	}
}

//删除模板
function delTemplate(){
	//预置模板不能更新、删除
	var selVal = $("#templateSel").val();
	if(selVal!='0'){
		ygdg.dialog.confirm("确定删除此模板？",function(){
			$.post(basePath+"/report/delTemplate.sc",{id:selVal},function(text){
				if(text=="success"){
					ygdg.dialog({content:'<span class="Blue">删除模板成功<span>!',time:2});
				}else{
					ygdg.dialog({content:'<span class="Red">删除模板失败<span>!',time:2});
				}
			},"text");
		});
	}else{
		ygdg.dialog({content:'<span class="Red">系统预置模板不能删除<span>!',time:2});
	}
}

function submitTemplate(){
	var selVal = $("#templateSel").val();
	$("#analysisQuota").addClass('hide');
	//设置了新模板，当前模板设置为预置模板
	$.post(basePath+"/report/setDefaultTemplate.sc",{id:selVal},function(text){
		if(text=="success"){
			//设置排序列
			$("#sortBy").val($("#analysisQuota").find("input[type='checkbox']:checked:eq(0)").attr("enname"));
			//重新加载
			searchForm();
		}
	},"text");
}

//根据维度显示指标
function getIndex(val){
	if(!($("#analysisQuota").hasClass("hide"))){
		$("#analysisQuota").html('<div class="loading_img"><img src="'+basePath+'/manage/report/images/loading-spinner-blue.gif"/></div>');
		$.post(basePath+"/report/loadAnalysisIndex.sc",{dimension:val},function(html){
			$("#analysisQuota").html(html);
			$('[data-ui-type=dropdown]','#analysisQuota').dropdown();
		},"html");
	}
}

function loadTemplateIndex(val){
	$.post(basePath+"/report/loadTemplateIndex.sc",{id:val},function(json){
		var indexList = json.indexList;
		$(".quota-list","#analysisQuota").find("input[type='checkbox']:checked").prop("checked",false);
		$.each(indexList,function(i,n){
			$("#g-"+n.id).prop("checked",true);
		});
		$("#checkedNum","#analysisQuota").html($(indexList).size());
	},"json");
}


//加载汇总指标数据
function loadTotalIndexData(){	
	var startDate = $("#starttime-1").val();
	var endDate = $("#endtime-1").val();
	if(startDate == "" || endDate == ""){
		ygdg.dialog.alert("请选择起始时间！");
		return false;
	}
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;	
	ajaxLoadTotalIndexData(data);
	//点击查询加载汇总指标数据后，默认加载第一个指标折线图
	var firstIndex = $("#indexBox").children(":first").attr("identify");
	loadEveryDayOrHourData(firstIndex);
	
}

//判断是否新的经营概况指标查询URL,往isNew 隐藏域塞值
function setIsNew(){
	//根据url判断是否走新的汇总查询
	var locationUrl = window.location.toString();
	var index = locationUrl.indexOf("?");
	if(index >=0){
		var params = locationUrl.substring(index+1);
		if(params.length > 0){
			var paramsArr = params.split("&");
			var isNew = "";
			for(var i=0;i<paramsArr.length;i++){
				var val = paramsArr[i].split("=");
				var paramName =val[0];
				var paramValue = val[1];
				if(paramName == "isNew"){
					isNew = paramValue;
					break;
				}
			}
			$("#isNew").val(isNew);
		}
	}
}

//ajax 请求后台加载汇总指标数据
function ajaxLoadTotalIndexData(data){
	setIsNew();
	$.ajax({
		async : true, 
		cache : false,
		type : 'POST',
		dataType : "json",
		data:data,
		url : basePath+"/report/loadTotalIndexData.sc?"+$("#indexForm").serialize(),  
		success : function(data) {				
			//alert(data.visitCount);
			$("#visitCount").html(data.visitCount); // 访次
			$("#pageView").html(data.pageView); // 浏览量
			$("#payedOrderNum").html(data.payedOrderNum); //支付订单数
			$("#payedOrderAmt").html(data.payedOrderAmt); // 支付订单金额
			$("#payedOrderAvgAmt").html(data.payedOrderAvgAmt); // 支付订单均价
			$("#changePercent").html(data.changePercent+"%"); // 转化率
			$("#deliveryPercent").html(data.deliveryPercent+"%"); // 发货率
			$("#newSaleCommodityNum").html(data.newSaleCommodityNum); // 新上架商品数
			
			$("#orderNum").html(data.orderNum); // 收订订单数
			$("#totalAmt").html(data.totalAmt); //收订金额
			$("#deliveryOrderNum").html(data.deliveryOrderNum); // 发货订单数
			$("#deliveryOrderAmt").html(data.deliveryOrderAmt); // 发货金额
			$("#rejectedCommodityNum").html(data.rejectedCommodityNum); // 退货拒收数
			$("#rejectedCommodityPercent").html(data.rejectedCommodityPercent+"%"); // 退货拒收率
			$("#rejectedCommodityAmt").html(data.rejectedCommodityAmt); // 退货拒收额
		},
		error:function(data){
			ygdg.dialog.alert("加载商家经营概况指标异常！");
		}
	});
}


// 加载最近7天汇总指标
function loadSevenDayTotalIndexData(){
	var data = {};
	var date = new Date();
	var dateStr = addDate(date,-7);
	data.startDate = dateStr;
	data.endDate = addDate(date,-1);
	$("#sevenDayTotalIndexBtn").removeClass("active");
	$("#thityDayTotalIndexBtn").removeClass("active");
	$("#sevenDayTotalIndexBtn").addClass("active");
	$("#starttime-1").val(dateStr);
	$("#endtime-1").val(addDate(date,-1));
	loadTotalIndexData();
	//ajaxLoadTotalIndexData(data);
	//点击查询加载汇总指标数据后，默认加载访次的折线图
	//loadEveryDayOrHourData("visitCount");
}

// 加载最近30天汇总指标
function loadThityDayTotalIndexData(){
	var data = {};
	var date = new Date();
	var dateStr = addDate(date,-30);
	data.startDate = dateStr;
	data.endDate = addDate(date,-1);
	$("#sevenDayTotalIndexBtn").removeClass("active");
	$("#thityDayTotalIndexBtn").removeClass("active");
	$("#thityDayTotalIndexBtn").addClass("active");
	$("#starttime-1").val(dateStr);
	$("#endtime-1").val(addDate(date,-1));
	loadTotalIndexData();
	//ajaxLoadTotalIndexData(data);
	//点击查询加载汇总指标数据后，默认加载访次的折线图
	//loadEveryDayOrHourData("visitCount");
}

//计算日期加减 并返回 yyyy-mm-dd 格式日期字符串
function addDate(date,days){  
	var d=new Date(date); 
	d.setDate(d.getDate()+days);  
	var month=d.getMonth()+1; 
	var day = d.getDate(); 
	if(month<10){  
	   month = "0"+month;  
	}  
    if(day<10){
       day = "0"+day;  
    }  
	var val = d.getFullYear()+"-"+month+"-"+day; 
    return val; 
} 

//加载每日或者每小时 商家指标折线图
function loadEveryDayOrHourData(indexName){
	$(".loading_img[name='surveyIndexLoading']").remove();
	$("#visitorsNumber1").empty().before('<div class="loading_img" name="surveyIndexLoading"><img id="" src='+basePath+'"/manage/report/images/loading-spinner-blue.gif"/></div>');
	//加载指定的指标数据,选中第一个指标下拉选项
	if(indexName!="" && indexName!=null && indexName !=undefined){
		//选择指标页签
		$("#indexBox li").each(function(index,obj){
			if($(obj).attr("identify")==indexName){
				$("#indexBox li").removeClass("active");
				$(obj).addClass("active");
			}
		});
		//选择第一个指标下拉选项
		$("#firstIndexName").children().each(function(index,obj){
			if($(obj).val()==indexName){
				$(obj).attr("selected","selected");
				var index = $(obj).index();   		
				//将第二个指标清空，默认请选择 
				$('.yg-dropdown-list li',$('#secondIndexName').parents('.yg-dropdown')).get(0).click();
				// 模拟单击事件，达到页面选择效果
				$('.yg-dropdown-list li',$('#firstIndexName').parents('.yg-dropdown')).get(index).click();
				return;
			}
		});
		
		//将第二个指标清空，默认请选择 
		//$('.yg-dropdown-list li',$('#secondIndexName').parents('.yg-dropdown')).get(0).click();
	}
	var requestdata = {};
	var firstIndexName = $("#firstIndexName").val();
	var secondIndexName = $("#secondIndexName").val();
	requestdata.firstIndexName = firstIndexName;
	requestdata.secondIndexName = secondIndexName;
	requestdata.groupType = "day";
	// 默认查最近7天数据
	var startDate = $("#starttime-1").val();
	var endDate = $("#endtime-1").val();
	requestdata.startDate = startDate;
	requestdata.endDate = endDate;
	setIsNew();
	requestdata.isNew = $("#isNew").val();
	$.ajax({
		async : true, 
		cache : false,
		type : 'POST',
		dataType : "json",
		data:requestdata,
		url : basePath+"/report/loadEveryDayOrHourIndexData.sc",  
		success : function(data) {	
			//debugger;		
			//定义Y轴数组，数据填充数组
			var yAxis = [];
			var series = [];
			//定义首个指标Y轴参数，并赋予默认值
			var firstyAxit = {
				labels:{
					style:{color:"#808080"}
				},
				min: 0,
				title:{
					text:"",
					style:{
						color:"#808080"
					}
				},
				gridLineColor: '#f0f0f0'
			};
			//定义首个指标数据展示对象，并赋予默认值
			var firstSerie = {
				name:"",
				yAxis:0,
				tooltip:{
					valueSuffix:""
				},
				data:data.firstIndex	
			};
			//获取第一个指标中文名称，单位
			var firstChName = getIndexChName(firstIndexName);
			firstyAxit.title.text=firstChName.name;
			firstSerie.name=firstChName.name;		
			firstSerie.tooltip.valueSuffix = firstChName.valueSuffix;		
			//将组装好的数据存入数组
			yAxis.push(firstyAxit);
			series.push(firstSerie);				
			
			//同上，定义第二个指标相关对象，并赋予默认值
			var secondAxit;
			var secondSerie;
			if(secondIndexName!=""){
				secondAxit = {
					labels:{
						style:{color:"#808080"}
					},
					title:{
						text:"",
						style:{
							color: "#808080"
						}
					},
	                opposite: true,
	                min: 0,
	                gridLineColor: '#f0f0f0'
				};
				
				secondSerie = {
					name:"",
					yAxis:1,
					tooltip:{
						valueSuffix:""
					},
					data:data.secondIndex	
				};
				//获取第二个指标中文名称，单位
				var secondChName = getIndexChName(secondIndexName);
				secondAxit.title.text=secondChName.name;
				secondSerie.name=secondChName.name;		
				secondSerie.tooltip.valueSuffix = secondChName.valueSuffix;							
				//将组装好的数据存入数组
				yAxis.push(secondAxit);
				series.push(secondSerie);	
			}
			$(".loading_img[name='surveyIndexLoading']").remove();
			G.Index.bindChartByDate("#visitorsNumber1",yAxis,series);
			
		},
		error:function(e){
			ygdg.dialog.alert("加载商家指标折线图数据异常！");
		}
	});
	

}

//获取指标对应的中文名称,单位
function getIndexChName(indexName){
	var chName={};
	if(indexName=="visitCount"){
		chName.name="访次";
		chName.valueSuffix="次";
		chName.desc = "从访客来到您商品最终关闭了该单品页面，计为1次访问";
	}else if(indexName=="pageView"){
		chName.name="浏览量";
		chName.valueSuffix="次";
		chName.desc = "统计时间段内，访问该商家商品的浏览次数，用以衡量网站用户访问的网页数量";
	}else if(indexName=="payedOrderNum"){
		chName.name="支付订单数";
		chName.valueSuffix="笔";
		chName.desc = "统计时间内，已支付的订单数量";
	}else if(indexName=="payedOrderAmt"){
		chName.name="支付金额";
		chName.valueSuffix="元";
		chName.desc = "统计时间内，已支付的商品金额";
	}else if(indexName=="payedOrderAvgAmt"){
		chName.name="订单均价";
		chName.valueSuffix="元";
		chName.desc = "支付金额/支付订单数";
	}else if(indexName=="changePercent"){
		chName.name="转化率";
		chName.valueSuffix="%";
		chName.desc = "收订订单数/商家所有商品的访次数量";
	}else if(indexName=="deliveryPercent"){
		chName.name="发货率";
		chName.valueSuffix="%";
		chName.desc = "已发货订单件数/已支付订单件数";
	}else if(indexName=="newSaleCommodityNum"){
		chName.name="新上架商品数";
		chName.valueSuffix="件";
		chName.desc = "统计时间内，第一次上架的商品数量";
	}else if(indexName=="payedCommodityNum"){
		chName.name="支付件数";
		chName.valueSuffix="件";
		chName.desc = "统计时间内，已支付商品数量";
	}else if(indexName=="avgCommdotiyAmt"){
		chName.name="商品均价";
		chName.valueSuffix="元";
		chName.desc = "已支付商品金额/已支付商品件数";
	}
	
	else if(indexName=="orderNum"){
		chName.name="收订订单数";
		chName.valueSuffix="笔";
		chName.desc = "统计时间内，下单的订单数量";
	}else if(indexName=="totalAmt"){
		chName.name="收订金额";
		chName.valueSuffix="元";
		chName.desc = "统计时间内，下单的订单金额";
	}else if(indexName=="deliveryOrderNum"){
		chName.name="发货订单数";
		chName.valueSuffix="笔";
		chName.desc = "统计时间内，下单并发货的订单数";
	}else if(indexName=="deliveryOrderAmt"){
		chName.name="发货金额";
		chName.valueSuffix="元";
		chName.desc = "统计时间内，下单并发货的订单金额";
	}else if(indexName=="rejectedCommodityNum"){
		chName.name="退货拒收数";
		chName.valueSuffix="件";
		chName.desc = "统计时间内，退货拒收的商品数量";
	}else if(indexName=="rejectedCommodityAmt"){
		chName.name="退货拒收额";
		chName.valueSuffix="元";
		chName.desc = "统计时间内，退货拒收的商品金额";
	}else if(indexName=="rejectedCommodityPercent"){
		chName.name="退货拒收率";
		chName.valueSuffix="%";
		chName.desc = "退货拒收数/发货数";
	}
	return chName;
}