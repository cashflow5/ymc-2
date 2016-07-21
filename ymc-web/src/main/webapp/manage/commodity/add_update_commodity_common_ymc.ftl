<#-- 新增 或 修改 商品公共页面 -->
<#-- pageTitle: 页面标题 -->
<#-- submitUrl: 表单提交url -->
<#-- importJs: 额外导入的js -->
<#macro add_update_commodity_common_new
	pageTitle=""
	submitUrl=""
	importJs=""
	 >

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-${pageTitle}</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodityNew.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/commodity.css?${style_v}"/>
<style>
	.quick-btns{position:fixed;top:160px;width:967px;padding:5px;text-align:right;background:#FFFFFF;z-index:999}
	.box-shadow{box-shadow:2px 0px 5px #B5B8C8;}
	#msgdiv{z-index:100}
	.tab{z-index:10}
	.goods_prop_content{width:808px;}
	.moreLayer{display:none;}
	.innerTitle{margin-top:15px;margin-bottom:15px;text-indent:106px;}
</style>
<script type="text/javascript">
var basePath = "${BasePath}";

/**是否入优购仓库，入优购仓*/
var SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN = "${SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN!''}";
/**是否入优购仓库，不入优购仓*/
var SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN = "${SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN!''}";
/**上传的图片数量*/
var ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT = "${ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT!''}";
/**1000*1000大图的后缀*/
var IMG_NAME_SIZE_1000_1000_SUFFIX = "${IMG_NAME_SIZE_1000_1000_SUFFIX!''}";
/**图片扩展名*/
var IMG_NAME_EXT_TYPE = "${IMG_NAME_EXT_TYPE!''}";
/**商品状态,审核拒绝*/
var COMMODITY_STATUS_REFUSE = "${COMMODITY_STATUS_REFUSE!''}";
/**商品状态,新建*/
var COMMODITY_STATUS_DRAFT = "${COMMODITY_STATUS_DRAFT!''}";
/**查询商品的页面tabId，未提交审核*/
var QUERY_COMMODITY_PAGE_TAB_ID_DRAFT = "${QUERY_COMMODITY_PAGE_TAB_ID_DRAFT!''}";
/**查询商品的页面tabId，待审核*/
var QUERY_COMMODITY_PAGE_TAB_ID_PENDING = "${QUERY_COMMODITY_PAGE_TAB_ID_PENDING!''}";
/**查询商品的页面tabId，审核通过*/
var QUERY_COMMODITY_PAGE_TAB_ID_PASS = "${QUERY_COMMODITY_PAGE_TAB_ID_PASS!''}";
/**查询商品的页面tabId，审核拒绝*/
var QUERY_COMMODITY_PAGE_TAB_ID_REFUSE = "${QUERY_COMMODITY_PAGE_TAB_ID_REFUSE!''}";
/**查询商品的页面tabId，全部商品*/
var QUERY_COMMODITY_PAGE_TAB_ID_ALL = "${QUERY_COMMODITY_PAGE_TAB_ID_ALL!''}";

/**商品编号*/
var requestCommodityNo = "${commodityNo!''}";
/**是否入优购仓*/
var isInputYougouWarehouse = "${isInputYougouWarehouse!''}";
/**是否入优购仓，true:入优购仓*/
var isInputYougouWarehouseFlag = isInputYougouWarehouse == SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN;
/**图片预览域名*/
var commodityPreviewDomain = "${commodityPreviewDomain!''}";
/**来自于哪个列表tab点击进来的*/
var fromTabId = "${fromTabId!''}";

/**商品描述图片合法正则表达式  */
var yougouValidImageRegex = '${YOUGOU_VALID_IMAGE_REGEX!''}';

$(function(){
            $(".oper_lf").live("click",function(){
                var my_img = $(this).parent().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().children(".goods_img_layer").children("div").children("img"),my_src = my_img.attr("src"),
                    pre_img = $(this).parent().prev().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().prev().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().prev().children(".goods_img_layer").children("div").children("img"),pre_src = pre_img.attr("src");
                my_img.attr("src",pre_src);
                pre_img.attr("src",my_src);
                
                if(pre_src){
	                var srcInput=$(this).parent().children("input[type='hidden']").val();
	                var preInput=$(this).parent().prev().children("input[type='hidden']").val();
	                if(srcInput=='0'){
	                	srcInput=my_src.replace("_ms.jpg","_l.jpg");
	                }
	            	if(preInput=='0'){
	                	preInput=pre_src.replace("_ms.jpg","_l.jpg");
	                }
	                $(this).parent().children("input[type='hidden']").val(preInput);
	                $(this).parent().prev().children("input[type='hidden']").val(srcInput);
                }
            });
            $(".oper_rt").live("click",function(){
                var my_img = $(this).parent().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().children(".goods_img_layer").children("div").children("img"),my_src = my_img.attr("src"),
                    next_img = $(this).parent().next().children(".goods_img_layer").children(".goods_img_image").length >0 ? $(this).parent().next().children(".goods_img_layer").children(".goods_img_image") : $(this).parent().next().children(".goods_img_layer").children("div").children("img"),next_src = next_img.attr("src");
                my_img.attr("src",next_src);
                next_img.attr("src",my_src);
                
                if(next_src){
	                var srcInput=$(this).parent().children("input[type='hidden']").val();
	                var nextInput=$(this).parent().next().children("input[type='hidden']").val();
	                if(srcInput=='0'){
	                	srcInput=my_src.replace("_ms.jpg","_l.jpg");
	                }
	            	if(nextInput=='0'){
	                	nextInput=next_src.replace("_ms.jpg","_l.jpg");
	                }
	                $(this).parent().children("input[type='hidden']").val(nextInput);
	                $(this).parent().next().children("input[type='hidden']").val(srcInput);
                }
            });
            //删除图片
           $(".oper_dt").live("click",function(){
           		var li = $(this).parent();
           		if($(this).attr("num")=="6"&&li.find("input[type='hidden']").val()!='-1'&&$(".goods_img_layer").eq(6).parent().find("input[type='hidden']").val()!='-1'){
           				ygdg.dialog.alert("请先删除第7张图");
           				return;
           		}
                li.find(".goods_img_layer img").attr("src","/yougou/images/unknow_img.png");
                li.find("input[name='imgFileId']").val("-1");
            });
           
             $(".goods_img_file_li").live({
            	 	mouseenter:
                     function(){
	            	 	$(this).addClass("curr");
		             },
             		mouseleave:
             		function(){
		                 $(this).removeClass("curr");
		            }
             });
            
            $("#publish_alike_goods_quick").click(function(){
            	if($(this).attr("checked")){
            		$("#publish_alike_goods").attr("checked",true);
            	}else{
            		$("#publish_alike_goods").attr("checked",false);
            	}
            	
            });
});
        
        


function scollPostion(){//滚动条位置
	   var t, l, w, h;
	   if (document.documentElement && document.documentElement.scrollTop) {
	       t = document.documentElement.scrollTop;
	       l = document.documentElement.scrollLeft;
	       w = document.documentElement.scrollWidth;
	       h = document.documentElement.scrollHeight;
	   } else if (document.body) {
	       t = document.body.scrollTop;
	       l = document.body.scrollLeft;
	       w = document.body.scrollWidth;
	       h = document.body.scrollHeight;
	   }
	   return { top: t, left: l, width: w, height: h };
	}
	
$(window).bind("scroll",function(){
   var scollPos =  scollPostion();
    if(scollPos.top<=180){
   	 $(".quick-btns").css({"top":160-scollPos.top+"px","border":"0px"});
   	 $(".quick-btns").removeClass("box-shadow");
    }else{
   	 $(".quick-btns").css({"top":"0px"});
   	 $(".quick-btns").addClass("box-shadow");
   	 $(".quick-btns").css({"border-bottom":"1px solid #B5B8C8"});
    }
});

</script>
</head>

<body>
<!--main_container(Start)-->
<div class="main_container">
<!--normal_box(Start)-->
<div class="normal_box">
	<div class="quick-btns">
		<a href="javascript:goodsAdd.saveTemplate()" style="margin-left:8px;margin-right:10px;float:right;margin-top:3px;">保存属性模板</a>
		<a class="button" style="margin-left:8px;float:right;" id="commodity_pre_f" onclick="goodsAdd.submit.preview()" ><span>预览</span></a>
		<a class="button" style="margin-left:8px;float:right;" id="commodity_audit_f" onclick="goodsAdd.submit.submitForm(true)" ><span>保存并提交审核</span></a>
		<a class="button"  style="margin-left:8px;float:right;" id="commodity_save_f" onclick="goodsAdd.submit.submitForm(false)" ><span>保存</span></a>
		<#if commodityNo??>
			<#else>
			<div style="display:inline-block;float:right;margin-top:3px"><label><input type='checkbox' id='publish_alike_goods_quick' /> 发布相似商品</label></div>
		</#if>
		<div style="clear"></div>
	</div>
	<p class="title site">当前位置：商家中心 &gt; 商品 &gt; ${pageTitle}</p>
	<!--tab_panel(Start)-->
	<div class="tab_panel" id="query_goods_tab_panel">
		 
		<!--tab_content(Start)-->
		<div class="tab_content" style="margin-top:50px;">
		    
		    <div style="margin-top:20px;margin-left:45px;">
                <ul class="publishSetp">
    	            <li>
    	   	                                商品分类选择
    	   	            <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/otherStep.png"></p>    
    	            </li>
    	            <li>
    	   	                                商品资料填写
    	   	            <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/currStep.png"></p>     
    	            </li>
    	            <li>
    	   	                                 商品提交成功
    	   	            <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/otherStep.png"></p>     
    	            </li>
                </ul>
            </div>
		    
		    <div class="selectedBrdCatg">
    	 		<p class="selectedBrd"><span class="redStar">*</span>&nbsp;品牌：${(submitVo.brandName)!}</p>
    	 		<p class="selectedCatg"><span class="redStar">*</span>&nbsp;您所使用的分类：${(submitVo.rootCatName)!} &gt ${(submitVo.secondCatName)!} &gt ${(submitVo.catName)!}<span style="diplay:inline-block;margin-left:20px;"><a href="#" id="reSel">重选分类</a></span></p>
    		</div>
		
			<form id="add_or_update_commodity_form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="brandNo" id="brandNo" value="${(submitVo.brandNo)!}"/>
			<input type="hidden" name="brandId" id="brandId" value="${(submitVo.brandId)!}"/>
			<input type="hidden" name="catId" id="catId" value="${(submitVo.catId)!}"/>
            <input type="hidden" name="catNo" id="catNo" value="${(submitVo.catNo)!}"/>
            <input type="hidden" name="brandName" id="brandName" value="${(submitVo.brandName)!}"/>
    		<input type="hidden" name="catName" id="catName" value="${(submitVo.catName)!}"/>
    		<input type="hidden" name="catStructName" id="catStructName" value="${(submitVo.catStructName)!}"/>
    		<input type="hidden" name="secondCatName" id="secondCatName" value="${(submitVo.secondCatName)!}"/>
    		<input type="hidden" name="rootCatName" id="rootCatName" value="${(submitVo.rootCatName)!}"/>
			
			<div class="layerTitle">
    	 		 <p><span class="left">基本资料基本填写</span><span class="right">必填项必须按照规则填写，以免无法提交或审核不通过。</span></p>
    		</div> 			
			<div class="detail_item_layer" id="goods_commodityName_layer">
				<label class="detail_item_label"><span class="detail_item_star">*</span>&nbsp;商品名称：</label>
				<div class="detail_item_content">
					<input type="text" class="inputtxt" id="goods_commodityName" name="commodityName" value="" placeHolder="可输入30个字"  maxlength="200" style="width: 400px;"/>
					<span id="commodityName_error_tip" class="goods_error_tip"></span>
					<div id="goods_commodityName_tip">
						示例：品牌（英/中）+ 年份 + 季节 + 材质 + 性别 + 类别 + 货号等，不允许使用引号、回车，不允许以“\”结尾。
					</div>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
		  <div class="detail_item_layer">
				<label class="detail_item_label"><span class="detail_item_star">&nbsp;</span>&nbsp;商品卖点：</label>
				<div class="detail_item_content">
					<input type="text" class="inputtxt" id="goods_sellingPoint" name="sellingPoint" maxlength="25" placeHolder="可输入25字" style="width: 400px;" value=""/>
					<span id="sellingPoint_error_tip" class="goods_error_tip"></span>
					<div id="goods_commodityName_tip">
						您可以在这里写入商品特性、销售卖点、优惠活动等描述文字，填写后将展示在单品页。
					</div>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>

			<div class="detail_item_layer">
				<label class="detail_item_label"><span class="detail_item_star">*</span>&nbsp;商品款号：</label>
				<div class="detail_item_content">
					<input type="text" class="inputtxt" id="goods_styleNo" name="styleNo" maxlength="32" value=""/>
					<span id="styleNo_error_tip" class="goods_error_tip"></span>
					<div id="goods_commodityName_tip">
						商家商品款式的编码，不允许填写中文或空格，建议使用英文、数字、下划线、“-”、“/”等。
					</div>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
 
			<div class="detail_item_layer" id="goods_years_layer">
				<label class="detail_item_label"><span class="detail_item_star">*</span>上市年份： </label>
				<div class="detail_item_content">
					<#--<#if yearArr?? && curYear??>
						<#list yearArr as item>
							<input type="radio" id="goods_years_${(item?string("#"))!''}" value="${(item?string("#"))!''}" name="years" 
								<#if item?? && item == curYear>
									checked="checked"
								</#if>
							/>
							<label for="goods_years_${(item?string("#"))!''}">${(item?string("#"))!''}</label>
							&nbsp;&nbsp;&nbsp;
						</#list>
					</#if>-->
					<select style="width:122px;float:right;" name="years" id="years">
						<#if yearArr?? && curYear??>
							<#list yearArr as item>
								<option value="${(item?string("#"))!''}" 
								<#if item?? && item == curYear>
										selected="selected"</#if>
									>${(item?string("#"))!''}</option>
							</#list>
						</#if>
					</select>
					<div id="goods_commodityName_tip">
						请勿在商品发布后随意更改上市年份。
					</div>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
			
			<div class="layerTitle">
    	 		 <p><span class="left">商品属性填写</span><span class="right">认真填写商品属性有助于顾客更好的搜索到您的商品，必填项必须勾选，否则无法提交审核。</span></p>
    		</div> 
			
			<div class="detail_item_layer goods_cat_change_show" id="goods_prop_title_layer" style="display: none;">
				<label class="detail_item_label">商品属性：</label>
				<div class="detail_item_content">
					<span id="goods_commodity_properties_tip" style="float:left;">(不填或填错商品属性，可能会审核不通过，请认真准确填写。)</span>
					<a href="javascript:goodsAdd.showTemplate()" style="margin-left:8px;margin-top:3px;disaplay:inline-block;float:right;">查看已保存属性模板</a>		
					<a class="button" id="commodity_pre" style="float:right" onclick="goodsAdd.saveTemplate()" ><span>保存属性模板</span></a>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>

			<#--属性内容-->
			<div class="detail_item_layer goods_cat_change_show" id="goods_prop_layer" style="display: block;">
				<div class="detail_item_content proplist" style="width:100%">
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
			
			<div class="layerTitle">
    	 		 <p><span class="left">销售属性填写</span><span class="right">不同的商品分类对应不同的尺码表，如没有所需的尺码可联系优购运营添加。</span></p>
    		</div>

            <div class="detail_item_layer goods_cat_change_show" id="goods_color_layer_label">
            	<label class="detail_item_label"><span class="detail_item_star">*</span>颜色：</label>
                <span>请尽量选择已有的颜色，如果自定义商品名称也请选择相近的颜色进行自定义。</span>
                <span id="specName_error_tip" class="goods_error_tip"></span>
            </div>			
             
			<div class="detail_item_layer goods_cat_change_show" id="goods_color_layer">
				<label class="detail_item_label"><span class="detail_item_star">&nbsp;</span></label>
				<div class="goods_color_layer_con">
					<div style="display:none">
						<input type="text" class="inputtxt" id="goods_specName" name="specName" value="" />
						<span id="goods_commodity_properties_tip"> &nbsp;&nbsp; 例：比如填写 “绿色” 或 “淡红色” 等。</span>
					</div>
					<div id="goods_color_layer_list">
					</div>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
			
            <div class="detail_item_layer goods_cat_change_show" id="goods_size_layer_label">
            	<label class="detail_item_label"> <span class="detail_item_star">*</span>&nbsp;尺码：</label>
                <span>勾选尺码后需要填写对应的货品条码，库存在发布商品时可不填，需要在上架前补充。</span>
                <span id="sizeNo_error_tip" class="goods_error_tip"></span>
            </div> 

			<div class="detail_item_layer goods_cat_change_show" id="goods_size_layer">
				<label class="detail_item_label"> <span class="detail_item_star">&nbsp</span></label>
				<div class="detail_item_content">
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
			
			<div class="detail_item_layer" id="goods_size_color_layer" style="display: none;">
				<label class="detail_item_label">&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<div class="detail_item_content">
					<table id="goods_color_size_tbl" class="tbl_gray_style" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th style="width: 100px;">颜色</th>
								<th style="width: 110px;">尺码</th>
								<th style="width: 100px;"><span class="detail_item_star">*</span>市场价</th>
								<th style="width: 100px;">
								<span class="detail_item_star fl" style="margin-left: 20px;">*</span>
								<span class="fl">优购价</span>
								<span id="salePriceTips" class="fl icon_tips" style="margin-top: 8px;"></span></th>
								<th style="width: 120px;">
								<span class="detail_item_star fl" style="margin-left: 20px;">*</span>
								<span class="fl">货品条码</span><span id="thirdCodeTips" class="fl icon_tips" style="margin-top: 8px;"></span></th>
								<th class="goods_prod_stock_column" style="width: 100px; display: none;">库存数量 <#-- span class="detail_item_star">*</span --></th>
								<th style="width: 100px;">重量(g)</th>
								<th style="width: 80px;">操作</th>
							</tr>
						</thead>
						<tbody id="goods_color_size_tbody">
						</tbody>
					</table>
				</div>
				<div style="height: 1px; clear: both;"></div>
			</div>
			
			<div class="layerTitle"> 
    	 		 <p><span class="left">商品图片上传</span><span class="right">不同的颜色需要上传不同的一组商品图片，至少5张商品图，至少1张描述图。</span></p>
    		</div>
			<div class="detail_item_layer">
				<label class="detail_item_label"><span class="detail_item_star">*</span>&nbsp;商品图片： </label>
				<div class="detail_item_content">
						<div style="margin-bottom:3px;">
							<div style="margin-top:3px;">
  								<p><b>温馨提醒：</b>每个款色的商品均需至少上传5张，上传7张最佳，按照优购规则上传商品主图。
  								<a href="http://open.yougou.com/help/content/8a8a8ab3401517c8014033bb15e600c2.shtml" target="_blank">查看图片上传规范</a>
  								</p>
								<p>&nbsp;</p>
								<p><b>图片要求：</b>图片格式为jpg，单张图片小于500kb，图片尺寸为800*800至1000*1000的<b>正方形</b>图片。</p>
								<p>&nbsp;</p>
							</div>
							<div style="clear:both;"></div>
						</div>
				</div>
			</div>
			
			<div class="detail_item_layer color_item_layer">
				<label class="detail_item_label colorlabel color_">&nbsp;</label>
				<div class="detail_item_content color_" style="width: 820px;">
					<div class="fl colorlabel colorDiv">
						<span class="colorname">&nbsp;</span>
					</div>
					<#if ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT??>
						<div id="goods_img_file_layer">
							<ul>
							<#list 1..ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT as i>
								<li id="goods_img_file_${i}_li" class="goods_img_file_li">
									<div id="goods_img_layer_${i}" class="goods_img_layer">
										<img src="${BasePath}/yougou/images/unknow_img.png" class="goods_img_image" width="90" height="90" />
									</div>
									<span class="oper_lf"></span><span class="oper_rt"></span>
									<#if i gt 5>
										<span class="oper_dt" num=${i}></span>
									</#if>
									<input type="file" id="goods_img_file_${i}" sortNo="${i}" name="commodityImage_${i}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this,${i})" />
									<input type="hidden" id="img_file_id_${i}" name="imgFileId" value="-1"/>
								</li>
							</#list>
							</ul>
						</div>
					</#if>
					<div>
						<div id="filePicker_color1" style="width:80px;float:left;margin-top: 30px;">批量传图</div>
						<div style="clear:both;"></div>
					</div>
					<div id="commodityImage_error_tip" class="goods_error_tip"></div>
				</div>
				<div class="color_" style="height: 1px; clear: both;"></div>
				<#-- 
				<label class="detail_item_label colorlabel">黄色：</label>
				<div class="detail_item_content">
					<#if ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT??>
						<div id="goods_img_file_layer">
							<ul>
							<#list 1..ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT as i>
								<li id="goods_img_file_${i}_li" class="goods_img_file_li">
									<div id="goods_img_layer_${i}" class="goods_img_layer">
										<img src="${BasePath}/yougou/images/unknow_img.png" class="goods_img_image" width="90" height="90" />
									</div>
									<span class="oper_lf"></span><span class="oper_rt"></span>
									<#if i gt 5>
										<span class="oper_dt" num=${i}></span>
									</#if>
									<input type="file" id="goods_img_file_${i}" sortNo="${i}" name="commodityImage_${i}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this,${i},'')" />
									<input type="hidden" id="img_file_id_${i}" name="imgFileId" value="-1"/>
								</li>
							</#list>
							</ul>
						</div>
					</#if>
					<div>
						<div id="filePicker_color2" style="width:80px;float:left;margin-top: 30px;">批量上传</div>
						<div style="clear:both;"></div>
					</div>
					<div id="commodityImage_error_tip" class="goods_error_tip"></div>
				</div>
				-->
			</div>
			
			<div class="detail_item_layer" style="margin-top:20px;">
				<label class="detail_item_label"><span class="detail_item_star">*</span>&nbsp;商品描述： </label>
				<div id="image-manage">
						<#import "/manage/commodity/add_update_commodity_image_manage.ftl" as p>
						<@p.add_update_commodity_image_manage/>
				</div>
				<div style="clear:both"></div>
			</div>
			<div class="detail_item_layer">
				<label class="detail_item_label">&nbsp;</label>
				<div class="detail_item_content">
					<#--
					<div id="goods_prodDesc_img_btn" class="clearfix">
						<a class="button fl" style="margin-left:0px;" onclick="goodsAdd.prodSpec.imgBtn_OnClick()" ><span>选择商品描述图片</span></a>
						<span id="prodDesc_error_tip" class="goods_error_tip fl"></span>
					</div>
					-->
					<textarea id="goods_prodDesc" name="prodDesc" style="width:780px;height:500px;"></textarea>
				</div>
			</div>
			<div style="height: 1px; clear: both;"></div>
			<#if commodityNo??>
				<#else>
					<div style='margin: 5px 0 -15px 0; margin-left: 135px;' id="div_alike_goods">
						<input type='checkbox' id='publish_alike_goods' /> 发布相似商品  <span id="goods_commodity_properties_tip" style="color:red">  (勾选此项，保存或保存并提交审核成功后，将重新跳转到此页面且刚上传的商品数据保持不变。)</span>
					</div>
			</#if>
			<div class="detail_submit_layer">				
				<a class="button" id="commodity_save" style="margin-left:4px;float:left" onclick="goodsAdd.submit.submitForm(false)" ><span>保存</span></a>
				<a class="button" id="commodity_audit" style="float:left" onclick="goodsAdd.submit.submitForm(true)" ><span>保存并提交审核</span></a>
				<a class="button" id="commodity_pre" style="float:left" onclick="goodsAdd.submit.preview()" ><span>预览</span></a>
				<a href="javascript:goodsAdd.saveTemplate()" style="margin-left:8px;margin-top:3px;disaplay:inline-block;float:left;">保存属性模板</a>		
				<input type="hidden" id="goods_rootCatName" name="rootCatName" value="" style="display: none;" />
				<input type="hidden" id="goods_brandName" name="brandName" value="" style="display: none;" />
				<input type="hidden" id="goods_brandId" name="brandId" value="" style="display: none;" />
				
				<div id="goods_selected_properties_layer" style="display: none;">
					<div id="goods_selected_properties_propItemNo"></div>
					<div id="goods_selected_properties_propItemName"></div>
					<div id="goods_selected_properties_propValueNo"></div>
					<div id="goods_selected_properties_propValue"></div>
				</div>				
				
				<input type="hidden" id="goods_properties_propIdInfo" name="propIdInfo" value="" style="display: none;" />
				<!--input type="hidden" id="goods_specName" name="specName" value="" style="display: none;" /-->
				
				<div id="goods_selected_sizeNo_layer" style="display: none;"></div>
				<div id="goods_selected_sizeName_layer" style="display: none;"></div>
				
				<input type="hidden" id="goods_prodIdInfo" name="prodIdInfo" value="" style="display: none;" />

				<input type="hidden" id="goods_commodityId" name="commodityId" value="" style="display: none;" />
				<input type="hidden" id="goods_commodityNo" name="commodityNo" value="" style="display: none;" />
				
				<input type="hidden" id="page_source_id" name="pageSourceId" value="${pageSourceId}" />
				<input type="hidden" id="auditStatus" name="auditStatus" value="${auditStatus}" />
			</div>
			<div style="height: 1px; clear: both;"></div>
			</form>
			<div id="msgdiv" class="qr-code">
			   <table style="width:100%;">
			      <tr>
			        <td class="msgdivtd">提交校验(保存不了看这里)</td>
			      </tr>
			      <tr>
			        <td id="showtd">暂无记录!</td>
			      </tr>
			      <tr>
			        <td style="text-align:right;"><a href="javascript:hiddenMsgBox();" id="J_QrCodeClose">关闭</a></td>
			      </tr>
			   </table>
			</div>
		</div>
		<!--tab_content(End)-->
	</div>
	<!--tab_panel(End)-->
</div>
<!--normal_box(End)-->
</div>
<!--main_container(End)-->
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.form.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/kindeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">
var image_hander = 0;
/** 是否执行 */
var is_execute = true;

var goodsAdd = {};
goodsAdd.color = {};
/**url相关*/
goodsAdd.url = {};
/**表单提交url*/
goodsAdd.url.submitUrl = basePath + "/commodity/${submitUrl}";

/**分类相关*/
goodsAdd.cat = {};

/**品牌相关*/
goodsAdd.brand = {};

/**商品属性相关*/
goodsAdd.properties = {};

/**颜色相关*/
goodsAdd.color = {};

/**尺寸相关*/
goodsAdd.size = {};

/**货品信息相关*/
goodsAdd.prodInfo = {};

/**图片相关*/
goodsAdd.imageUpload = {};

/**商品描述相关*/
goodsAdd.prodSpec = {};

/**表单提交相关*/
goodsAdd.submit = {};

/**验证相关*/
goodsAdd.validate = {};

/**隐藏 发布相似商品的块 */
function publishAlikeGoodsHide() {
	var pageSourceId = $("#page_source_id").val();
	if (pageSourceId == 1) {
		$("#publish_alike_goods").attr('checked', false);
		$("#div_alike_goods").hide();
	}
}
/**隐藏  */
function hiddenMsgBox() {
	$('#msgdiv').hide();
}


$(document).ready(function(){
	publishAlikeGoodsHide();
	goodsAdd.color.setColors();
	//绑定品牌onchange事件
	
});

$.extend(WebUploader.Uploader.options, {
    auto: true,
    swf: '${BasePath}/webuploader/Uploader.swf',
    server: '${BasePath}/img/upload.sc',
 	// 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'jpg,png',
        mimeTypes: 'image/*'
    },
    formData:{
       no:1,
    },
    //不去重
    duplicate:1,   
    //fileSingleSizeLimit:1024*5
    //压缩
    compress:false
});

var uploader = WebUploader.create({
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#filePicker_color1'
	});

uploader.on('beforeFileQueued',function(file){
	//上传之前判断是否选择颜色
	var checkedColor = $("div[class='color_con']").find("input[name='colorSelect']:checked");
	if(checkedColor.length>0){
		return true;
	}
	ygdg.dialog.alert("请选择商品颜色！");
	return false;
});

uploader.on( 'filesQueued', function( files ) {
	var images = $(".color_ .goods_img_layer");
	if(files.length>7){
		for(var i=0,length=files.length;i<length;i++){ 
			// 创建缩略图
			var file = files[i];
			uploader.removeFile( file ); //将文件移除，否则会继续上传
		}
		ygdg.dialog.alert("最多只能选择7张图片");
		return;
	}
	//初始化所有图片
	//给上传图片加上ID
	for(var i=0,length=files.length;i<length;i++){ 
		var file = files[i];
		var img = images.find("img").eq(i);
		img.attr("src","/yougou/images/unknow_img.png");
		img.addClass(file.id);
		img.parent().addClass("fileSelect");
		img.parent().siblings("input[type='hidden']").val("-1");
	}
});

uploader.on( 'uploadProgress', function( file, percentage ) {
    var $parent = $("."+file.id).eq(0).parent();
    var $percent = $( '#'+file.id +'_percent');
    if(!$percent.length ){
         $percent = $('<div class="percent" id="'+file.id+'_percent"></div>').appendTo($parent);
    }
    $percent.css( 'width', percentage * 100 + '%' ); 
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploader.on( 'uploadSuccess', function( file,response) {
	if(response.success==true){
		var re=/.jpg$/;
		$("."+file.id).eq(0).parent().siblings("input[type='hidden']").val(response.message);
		$("."+file.id).eq(0).attr("src",response.message.replace(re,".png"));
		$( '#'+file.id +'_percent').remove();
	}else{
    	if (response.message == '1') ygdg.dialog.alert(file.name+',request请求参数no为空值,请检查!');
    	else if (response.message == '2') ygdg.dialog.alert(file.name+',获取登录会话信息失败,请尝试重新登录操作!');
    	else if (response.message == '3') ygdg.dialog.alert(file.name+',商品图片大小超过了 500 KB');
    	else if (response.message == '4') ygdg.dialog.alert(file.name+',商品图片分辨率不符合 ( 800-1000px) * (800-1000px)的规格');
    	else if (response.message == '5') ygdg.dialog.alert(file.name+',图片校验异常');
    	else if (response.message == '6') ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
    	else if (response.message == '7') ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
    	else ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如再有问题请联系管理员！");
    	$("."+file.id).eq(0).attr("src","/yougou/images/unknow_img.png");
    	$("."+file.id).eq(0).parent().siblings("input[type='hidden']").val("-1");
    	$( '#'+file.id +'_percent').remove();
	}
});

// 文件上传失败，显示上传出错。
uploader.on( 'uploadError', function( file ) {
    ygdg.dialog.alert("上传失败");
    $( '#'+file.id +'_percent').remove();
});

var dg,codeDg;
$('#salePriceTips').live('mouseover',function(){
	var _html='<div style="line-height:20px;width:200px;">优购前台销售价格，优购价不能高于市场价，必须为正整数，必填。</div>';
	var _this=$(this);
	var _top=_this.offset().top- $(document).scrollTop()+30;
	var _left=_this.offset().left-150;
	dg=ygdgDialog({
		id:'test',
		padding : 0,
		title:'优购价',
		top:_top,
		left:_left,
		closable:false,
		content:_html
	});
}).live('mouseout',function(){
 	dg.close();
});

$('#thirdCodeTips').live('mouseover',function(){
	var _html='<div style="line-height:20px;width:200px;">商品sku编码，商品尺码的唯一表示，必填。</div>';
	var _this=$(this);
	var _top=_this.offset().top- $(document).scrollTop()+30;
	var _left=_this.offset().left-150;
	codeDg=ygdgDialog({
		id:'test',
		padding : 0,
		title:'货品条码',
		top:_top,
		left:_left,
		closable:false,
		content:_html
	});
}).live('mouseout',function(){
	codeDg.close();
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/jquery.jqFileBtn.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/imagehandle.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/commodity.validate.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/addOrUpdateCommodityYmc.js?${style_v}"></script>
${importJs}
</body>
</html>

</#macro>