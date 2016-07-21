<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>数据智慧_收藏夹</title>
	<link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/ygui.css?${style_v}" />
	<link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/detail.css?${style_v}" />
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/WdatePicker.css?${style_v}" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	

</head>
<body>
	<!--header created time: 2015/04/21 by guoran start-->
    <!--公共头部 start-->
    <div class="yg-header">
        <div class="yg-header-content">
            <!-- 头部Logo & banner start-->
            <a href="javascript:;" class="yg-logo"></a>
            <!-- 头部Logo & banner end-->
            <!-- 头部导行 start-->
            <div class="yg-menu-box">
                <div class="yg-menu">
                    <ul class="navList">
                        <li >
                            <a href="${BasePath }/report/gotoReportManagementSurvey.sc">
                                    首页
                                </a>
                        </li>
                        <li >
                            <a href="${BasePath }/report/reportRealTimeStatistics.sc">
                                    实时概况
                                </a>
                        </li>
                	<li >
                            <a href="${BasePath }/report/commodityAnalysisList.sc">
                                经营分析
                            </a>
                        </li>
                        <li  class="select">
                            <a href="${BasePath }/favoriteClassifyController/queryFavoriteClassifyInfo.sc">
                                    收藏夹
                                </a>
                        </li>
                       
                    </ul>
                </div>
            </div>
            <!-- 头部导行 end-->
            <div class="yg-login-info">
                <p class="font-icon mt17 tright">欢迎您，<#if merchantUsers??>${merchantUsers.supplier!''}</#if>：<#if merchantUsers??>${merchantUsers.login_name!''}</#if> <a class="ml15" href="${BasePath}/merchants/login/to_Back.sc" title="登出"><i class="iconfont">&#xe60c;</i></a></p>
            </div>
        </div>
   </div>
   
   
   <!--公共头部 end-->
    <!--header created time: 2015/04/21 by guoran end-->
    <div class="yg-body">
        <div class="yg-box bg-gray">
            <div class="yg-sidebar">
                <div class="yg-sidebar-menu">
                  <h1 class="sidebar-title"><i class="iconfont">&#xe604;</i>收藏夹</h1>
                  <ul class="sub-menu">
                    <#list favoriteClassifyList as item>
                      <li  id="li_${item.id}"  class="sub-menu-item">
                        <a href="javascript:void(0);" onclick="queryMenu('${item.id}');">${(item.classify_name)!''}</a>
                        <input class="add_val hide" type="text" value="${(item.classify_name)!''}"/>
                        <input id="removeId" type="hidden" value="${item.id}"/>
                        <span class="iconfont fr Gray">
                            <i class="save hide">&#xe61f;</i>
                            <i class="editor hide">&#xe620;</i>
                            <i class="del ml5 hide">&#xe629;</i>
                        </span>
                      </li>
                    </#list>
                      <li class="add-sub-menu">
                        <a href="#">+增加归类</a>
                    </li>
                  </ul>
                </div>
            </div>
            <div class="yg-main">
                <div class="box-title pl12">
                    <span>收藏夹> 需做促销商品</span>
                </div>
                <form id="queryForm" name="queryForm" method="post">
                 <input type="hidden"  id="favorite_classify_id"  value="${(favorite_classify_id)!''}"   name="favorite_classify_id"  />
                 <input type="hidden"  id="favorite_category_list"    name="favorite_category_list"  />
                <div class="box-search pl12 no-border-bottom zIndex20">
                   
                     <span class="fl ml5 mt3">收藏时间：</span>
                    <input type="text" id="starttime-1" value="${(starttime)!''}"    name="starttime-1"  class="daterange starttime fl ml10">
                    <input type="text" id="endtime-1" value="${(endtime)!''}"  onChange="javascript:qcFormSubmit()"   name="endtime-1"  class="daterange fl ml10 endtime">
                    <span class="fl ml10 mt3">商品状态：</span>
                    <select class="fl"   onChange="javascript:qcFormSubmit()"  name="statusName" id="statusName"    data-ui-type="dropdown">
                        <option        value="">全部</option>
                        <#list statics['com.yougou.kaidian.commodity.component.CommodityStatus'].getWaitSaleStatusList() as item>
											<option  <#if statusName??&&item.statusName??&&statusName==item.statusName>selected</#if>   >
												${item.statusName?string!''}
											</option>
						</#list>
                    </select>
                    <!-- end 下拉框 -->
                     <!-- start search -->
                    <div class="yg-search fl ">
                        <i class="yg-search-ico"></i>
                        <input type="text"  id="commodityCode"  autocomplete="off"   onChange="javascript:qcFormSubmit()"   value="${(commodityCode)!'请输入商品名称或商品编码'}"   name="commodityCode" class="yg-search-input" />
                    </div>
                    <!-- end search -->
                </div>
                <div class="box-search pl12 no-border-top zIndex10">
                  <label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;品牌： </label>
					<select id="brandNo"  onFocus="javascript:qcFormSubmit()"   name="brandNo" data-ui-type="dropdown">
						<option value="">请选择</option>
						<#if lstBrand??>
							<#list lstBrand as item>
								<option         value="${(item.brandNo)!""}"  brandId="${(item.id)!''}">${(item.brandName)!""}</option>
							</#list>
						</#if>
					</select>
					<label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;分类： </label>
                    <select name="rootCattegory" id="category1" data-ui-type="dropdown" linkage="true">
                        <option value="" selected="selected">一级分类</option>
                      
                    </select>
                    <select name="secondCategory" id="category2" data-ui-type="dropdown" linkage="true">
                        <option value="" selected="selected">二级分类</option>
                    </select>
                    <select name="threeCategory" id="category3" data-ui-type="dropdown"    linkage="true">
                        <option value="" selected="selected">三级分类</option>
                    </select>
							
                    <!-- end 下拉框 -->
                   <div   class="fr mt3" style="*margin-top:0"><a href="javascript:qcFormSubmit()" class="btn-default ml10">搜索</a></div>
                    
                    </form>
                </div>
                <!-- 表格开始 -->
                <table class="yg-table w960">
                    <thead>
                        <tr class="height-70">
                            <th></th>
                            
                            <th>商品图片</th>
                            <th width="240px">商品名称</th>
                            <th>优购价(元)</th>
                            <th>收藏时间</th>
                            <th>所属归类</th>
                            <th>商品状态</th>
                            <th>可售库存</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                      <#if pageFinder??&&(pageFinder.data)??>
						<#list pageFinder.data as item>
                        <tr class="height-20">
                            <td><input  value="${item.id!''}" class="single_checkbox" type="checkbox"/></td>
                            <td>
                            	 <#if item.pic_small??&&item.pic_small!=''>
                                    <img width="40" height="39" alt="" src="${item.pic_small!''}"/>
                                  <#else>
                                    <img width="40" height="39" alt="" src="${BasePath}/yougou/images/nopic.jpg"/>
                                 </#if>
                            </td>
                            <td class="tleft">
                                <p class="ml10">${item.commodity_name!"—"}</p>
                             
                            </td>
                            <td>${item.sale_price!"—"}</td>
                            <td>${item.create_time!"—"}</td>
                            <td class="tleft">
                            ${item.firstClassifyName!"—"}
                             <#if item.first_classify_name??&&item.first_classify_name!=''>
                            	 <i title=" ${item.first_classify_name!'—'}" class="iconfont Blue f13">&#xe61a;
								</i>
                             </#if>
								
                                </p>
                            </td>
                            <td>${item.commodity_status_name!"—"}  </td>
                            <td>${item.on_sale_quantity!"—"}</td>
                            <td>
                            	  <#if favorite_classify_id??&&favorite_classify_id!=''>
	               
	                                <p><a  href="javascript:cancelMerchantClassification('${(item.id!'')}')">取消归类</a></p>
	                             </#if>
                                <p><a href="${BasePath}/report/singleCommodityAnalysis.sc?commodityNo=${(item.no)!'--'}">单品分析</a></p>
                            </td>
                        </tr>
                     </#list>
                   	 <#else>
							<tr>
								<td colspan="12" class="td-no">没有相关数据！</td>
							</tr>
					</#if>
                    </tbody>
                </table>
                
               <!--分页start-->
			<#if pageFinder??&&pageFinder.data??>

				
                <div class="yg-page">
                		<div class="fl">
		                    <input type="checkbox" id="all_checkbox" class="ml10"/><label for="all_checkbox" class="ml5">全选</label>
		                    <#if favorite_classify_id??&&favorite_classify_id!=''>
		                    
				                    <a href="javascript:;" onclick="batchCancelMerchantClassification();"  class="btn-default ml10">批量取消归类</a>
				                    
			                </#if>
		                      <a class="btn-default ml10" href="javascript:void(0);" onclick="exportExcel();">
									<span> 导出</span>
								</a>
								<div class="span" style="visibility: hidden;"   id="progressBar" >
										<div class="span"><img src='${BasePath}/yougou/images/loading.gif'/></div>
										<div class="span" id="progressBarSpan" >
										   0
										</div>
										<div class="span">%</div>
								</div>
						</div>	
						<#import "/manage/report/report_common_page.ftl" as page>
						<@page.queryForm formId="queryForm"/>
				</div>
			</#if>
			<!--分页end-->
			
            </div>
        </div>
    </div>
    <!-- 底部 start-->
    <div class="yg-footer"></div>
   
</body>

	<script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/jquery-1.8.2.min.js"></script>	
    <!-- 滚动条插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/nicescroll.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/highcharts.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/grid-yg.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.common.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.detail.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=blue"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygui.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.sale-goods.js"></script>
	   <!-- 日期插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/WdatePicker.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/category-common.js"></script>
 
    												
   
<script type="text/javascript">
	var basePath = "${BasePath}";
 $(document).ready(function(){
		initfavorite();
      
});
function initfavorite(){

		var favorite_classify_id="${(favorite_classify_id)}";
		$("#li_"+favorite_classify_id).attr("class","selected sub-menu-item");
		
			// 选中品牌下拉项
	var brandNo = "${(brandNo)!''}";	
	$("#brandNo").children().each(function(index,item){
		if($(this).val() == brandNo){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发品牌change事件，初始化一级分类
			$('.yg-dropdown-list li',$('#brandNo').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});
	
	// 选中一级分类
	var rootCattegory = "${(rootCattegory)!''}";
	$("#category1").children().each(function(index,item){
		if($(this).val() == rootCattegory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发change事件，初始化二级分类
			$('.yg-dropdown-list li',$('#category1').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
	
	// 选中二级分类
	var secondCategory = "${(secondCategory)!''}";
	$("#category2").children().each(function(index,item){
		if($(this).val() == secondCategory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发change事件，初始化三级分类
			$('.yg-dropdown-list li',$('#category2').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
	
	// 选中二级分类
	var threeCategory = "${(threeCategory)!''}";
	$("#category3").children().each(function(index,item){
		if($(this).val() == threeCategory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果
			$('.yg-dropdown-list li',$('#category3').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
}
//提交表单查询
 function   qcFormSubmit() {
 	
     var categoryList=$("#brandNo").val()+','+$("#category1").val()+','+$("#category2").val()+','+$("#category3");
     
     $("#favorite_category_list").val(categoryList);
     
	var queryForm = $("#queryForm").attr("action", basePath + "/favoriteClassifyController/queryFavoriteClassifyInfo.sc");
	
 	$("#commodityCode").val($("#commodityCode").val())
	queryForm.submit();
}

//提交表单查询
function   queryMenu(classifyid) {
	
	$("#favorite_classify_id").val(classifyid);
	var queryForm = $("#queryForm").attr("action", basePath + "/favoriteClassifyController/queryFavoriteClassifyInfo.sc");
	queryForm.submit();
}
function  cancelMerchantClassification(merchantClassificationId) {
	
	  var exDialog = ygdg.dialog({
		id:'waitExport',
		content:'确定要取消归类吗？',
		title:'提示',
		button:[{
				name:'确定',
				callback:function(){
					var  favorite_classify_id=$("#favorite_classify_id").val();
					
					if(favorite_classify_id==''){
						ygdg.dialog.alert("请选择归类文件夹");
						return;
					}
					if(merchantClassificationId==''){
						ygdg.dialog.alert("请选择商品");
						return;
					}
					 $.ajax({
							type: 'post',
							url: '/favoriteClassifyController/cancelMerchantClassification.sc',
							dataType: 'html',
							data: 'favoriteId=' +favorite_classify_id+'&commodity_id='+merchantClassificationId ,
							beforeSend: function(jqXHR) {
							},
							success: function(data, textStatus, jqXHR) {
								// inputUpdate.val(data);
								ygdg.dialog.alert("取消归类成功");
								qcFormSubmit();
							},
							complete: function(jqXHR, textStatus) {
							},
							error: function(jqXHR, textStatus, errorThrown) {
								alert('取消归类失败');
							}
					});
				}
			},{
				name:'取消',
				callback:function(){
				}
			}]
	});
		
}
function  batchCancelMerchantClassification() {
		var merchantClassificationId='';
		$('.single_checkbox:checked').each(function(){
			if(merchantClassificationId==''){
				
				merchantClassificationId=$(this).val();
			}else{
				merchantClassificationId=merchantClassificationId+","+$(this).val();
			}
		});
	  var exDialog = ygdg.dialog({
		id:'waitExport',
		content:'确定要取消归类吗？',
		title:'提示',
		button:[{
				name:'确定',
				callback:function(){
					var  favorite_classify_id=$("#favorite_classify_id").val();
					if(favorite_classify_id==''){
				
						ygdg.dialog.alert("请选择归类文件夹");
						return;
					}
					if(merchantClassificationId==''){
						
						ygdg.dialog.alert("请选择商品");
						return;
					}
					 $.ajax({
							type: 'post',
							url: '/favoriteClassifyController/cancelMerchantClassification.sc',
							dataType: 'html',
							data: 'favoriteId=' +favorite_classify_id+'&commodity_id='+merchantClassificationId ,
							beforeSend: function(jqXHR) {
							},
							success: function(data, textStatus, jqXHR) {
								// inputUpdate.val(data);
								ygdg.dialog.alert("取消归类成功");
								qcFormSubmit();
							},
							complete: function(jqXHR, textStatus) {
							},
							error: function(jqXHR, textStatus, errorThrown) {
								alert('取消归类失败');
							}
					});
				}
			},{
				name:'取消',
				callback:function(){
				}
			}]
	});
		
}


//导出商品
function exportExcel(){
	 $("#progressBar").css('visibility','visible');
	//根据时间跨度导出
	$.ajax({
		type: "POST",
		url: basePath+"/favoriteClassifyController/exportFavoriteExcel.sc",
		data: $("#queryForm").serialize(),
		dataType: "html",
		success: function(data) {
			if(data=='success'){
				$("#exportData").attr("href","javascript:void(0);");
				$("#exportData").css("color","#555555");
				//$("#progressBar").removeClass("hide");
				//$("#progressBar").show()
				//$("#progressBar").css('visibility','hidden');
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
	$.get(basePath+"/favoriteClassifyController/getExportResult.sc",function(data){
		if(data.result=="true"){
			$("#exportData").css("color","#0b80ed");
			$("#exportData").attr("href","javascript:exportExcel();");
			$("#progressBar span").text(0);
			//$("#progressBar").addClass("hide");
			 //$("#progressBar").hide();
			 $("#progressBar").css('visibility','hidden');
			window.clearInterval(setId);
			location.href=basePath+"/favoriteClassifyController/favoriteDownload.sc?name="+data.url+"&_t="+new Date().getTime();
		}else{
			//进度条的值变化
			if(data.progress<0){
				$("#exportData").css("color","#0b80ed");
				$("#exportData").attr("href","javascript:exportExcel();");
				$("#progressBarSpan").text(0);
				//$("#progressBar").addClass("hide");
				 //$("#progressBar").hide();
				 $("#progressBar").css('visibility','hidden');
				window.clearInterval(setId);
				ygdg.dialog.alert("导出失败，泪奔呀！");
			}else{
				$("#progressBarSpan").text(data.progress);
				
			}
		}
	},"json");
};



</script>

<style type="text/css">
	
	.span{
		display: inline-block;
		vertical-align: top;
	}
	
	.span *{
		vertical-align: middle;
		line-height:25px;
	}
	
</style>

</html>