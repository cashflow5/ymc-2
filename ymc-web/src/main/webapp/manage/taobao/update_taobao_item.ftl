<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/update_taobao_item.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
<style>
	.goods_img_layer img{border:1px solid #E5F3FC;}
	.goods_error_tip{background:url("${BasePath}/yougou/images/error.gif") no-repeat;padding-left:20px}
	.quick-btns{position:fixed;top:160px;width:967px;padding:5px;text-align:right;background:#FFFFFF;z-index:999;}
	.box-shadow{box-shadow:2px 0px 5px #B5B8C8;}
	 #msgdiv{z-index:100}
	 .tab{z-index:10}
</style>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js?${style_v}"></script>
<script>
	var basePath = "${BasePath}";
	var goodsAdd = {};
	goodsAdd.validate = {};
	var updateTaobaoItem = {};
	var image_hander = 0;
	updateTaobaoItem.loadingImgUrl = "${BasePath}/yougou/js/ygdialog/skins/icons/loading.gif";
	updateTaobaoItem.basePath = "${BasePath}";
	updateTaobaoItem.yougouCatNo = "${taobaoItem.yougouCateNo}";
	updateTaobaoItem.props = eval(${props}).props; 
	updateTaobaoItem.sizeInfo = eval(${sizeInfo}).sizeInfo;//尺码明细
	updateTaobaoItem.images = eval(${images}).images;//角度图
	var yougouValidImageRegex = '${YOUGOU_VALID_IMAGE_REGEX!''}';
	
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
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/commodity.validate.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygajaxfileupload.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/jquery.jqFileBtn.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/kindeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.updateitem.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
		<div class="normal_box">
			<div class="quick-btns">
				<a class="button" style="margin-left:4px;" onclick="updateTaobaoItem.submitForm(0)" ><span>&nbsp;&nbsp;保存&nbsp;&nbsp;</span></a>
				<div style="clear"></div>
			</div>
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入  &gt; 商品导入</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"></p>
				<div class="tab_content" style="margin-top:50px;">
				<form id="taobao_item_form" method="post">
						<input type="hidden" id="checkStatus" name="checkStatus" value="0">
						<div class="detail_item_layer" id="goods_brand_layer">
							<label class="detail_item_label">品牌： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<select id="brandNo" name="brandNo" style="width: 129px;">
									<#if lstBrand??>
										<#list lstBrand as item>
											<option value="${(item.brandNo)!""}"  <#if taobaoItem.yougouBrandNo??&&item.brandNo==taobaoItem.yougouBrandNo>selected='selected'</#if> brandId="${(item.id)!''}">${(item.brandName)!""}</option>
										</#list>
									</#if>
								</select>
								<span id="brandNo_error_tip" class="goods_error_tip"></span>
							</div>
							<input type="hidden" name="brandName" id="brandName">
							<div style="height: 1px; clear: both;"></div>
						</div>
						<div class="detail_item_layer" id="goods_cat_layer">
							<label class="detail_item_label">商品分类： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<select name="rootCattegory" class="fl-lf" id="sel1" style="width:120px;">
								    <option value="" selected="selected">一级分类</option>
								</select>
			                    <select name="secondCategory" class="fl-lf" id="sel2" style="width:120px;">
			                    	<option value="" selected="selected">二级分类</option>
			                    </select>
			                    <select name="threeCategory" class="fl-lf" id="sel3" style="width:133px;">
			                    	<option value="" selected="selected">三级分类</option>
			                    </select>
								<span id="category_error_tip" class="goods_error_tip"></span>
								<div id="goods_category_tip">重选分类会造成已填写的信息丢失，请谨慎操作。</div>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						
						<div class="detail_item_layer" id="goods_commodityName_layer">
							<label class="detail_item_label">商品名称： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<input type="text" class="inputtxt" id="goods_commodityName" name="title" value="${taobaoItem.title!''}" maxlength="200" style="width: 400px;"/>
								<span id="commodityName_error_tip" class="goods_error_tip"></span>
								<div id="goods_commodityName_tip">
									商品名称命名范例：品牌名（英/中）+ 年份 + 季节 + 材质 + 性别 + 类别 + 货号<br />
									商家可根据自己的需求增加品牌系列颜色和热点词,系统将自动删除不符合规则的字符。
								</div>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
			
						<div class="detail_item_layer">
							<label class="detail_item_label">商品款号： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<input type="text" class="inputtxt" id="goods_yougouStyleNo" name="yougouStyleNo" maxlength="32" value="${taobaoItem.yougouStyleNo!''}"/>
								<span id="yougouStyleNo_error_tip" class="goods_error_tip"></span>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
			
						<div class="detail_item_layer">
							<label class="detail_item_label">商家款色编码： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<input type="text" class="inputtxt" id="goods_yougouSupplierCode" name="yougouSupplierCode" maxlength="20" value="${taobaoItem.yougouSupplierCode!''}"/>
								<span id="yougouSupplierCode_error_tip" class="goods_error_tip"></span>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
			
						<div class="detail_item_layer">
							<label class="detail_item_label">优购价格： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<input type="text" class="inputtxt" id="goods_yougouPrice" name="yougouPrice" maxlength="10" value="${taobaoItem.yougouPrice!''}"/> 元
								<span id="yougouPrice_error_tip" class="goods_error_tip"></span>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
			
						<div class="detail_item_layer">
							<label class="detail_item_label">淘宝价格： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<input type="text" class="inputtxt" id="goods_price" name="price" maxlength="10" value="${taobaoItem.price!''}"/> 元
								<span id="price_error_tip" class="goods_error_tip"></span>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_layer" id="goods_years_layer">
							<label class="detail_item_label">年份： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								<#if yearArr?? && curYear??>
									<#list yearArr as item>
										<input type="radio" id="goods_years_${(item?string("#"))!''}" value="${(item?string("#"))!''}" name="yougouYears" 
											<#if taobaoItem.yougouYears??>
												<#if item?? && item?string("#") == taobaoItem.yougouYears>
													checked="checked"
												</#if>
											<#else>
												<#if item?? && item == curYear>
													checked="checked"
												</#if>
											</#if>
										/>
										<label for="goods_years_${(item?string("#"))!''}">${(item?string("#"))!''}</label>
										&nbsp;&nbsp;&nbsp;
									</#list>
								</#if>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_splitor"></div>
						
						<div class="detail_item_layer goods_cat_change_show" id="goods_prop_title_layer" style="display: none;">
							<label class="detail_item_label">商品属性：</label>
							<div class="detail_item_content">
								<span id="goods_commodity_properties_tip">(不填或填错商品属性，可能会审核不通过，请认真准确填写。)</span>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						<div class="detail_item_layer goods_cat_change_show" id="goods_prop_layer" style="display: none;">
							<label class="detail_item_label">&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<div class="detail_item_content">
								
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_layer goods_cat_change_show" id="goods_color_layer" style="display: none;">
							<label class="detail_item_label">颜色： <span class="detail_item_star">*</span></label>
							<!--div class="detail_item_content"-->
								<input type="text" class="inputtxt" id="goods_specName" name="yougouSpecName" value="${taobaoItem.yougouSpecName!''}" />
								<span id="specName_error_tip" class="goods_error_tip"></span>
								<span id="goods_commodity_properties_tip"> &nbsp;&nbsp; 例：比如填写 “绿色” 或 “淡红色” 等。</span>
							<!--/div-->
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_layer goods_cat_change_show" id="goods_size_layer" style="display: none;">
							<label class="detail_item_label">尺码： <span class="detail_item_star">*</span></label>
							<div class="detail_item_content">
								
							</div>
							<div id="sizeNo_error_tip" class="goods_error_tip" style="clear: both;margin-left:140px;"></div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_layer" id="goods_size_color_layer" style="display: none;">
							<label class="detail_item_label">&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<div class="detail_item_content">
								<table id="goods_color_size_tbl" class="tbl_gray_style" cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<th style="width: 80px;">颜色</th>
											<th style="width: 120px;">尺码</th>
											<th class="goods_prod_stock_column" style="width: 100px">库存数量 <#-- span class="detail_item_star">*</span --></th>
											<th style="width: 140px;">货品条码  <span class="detail_item_star">*</span></th>
										</tr>
									</thead>
									<tbody id="goods_color_size_tbody">
									</tbody>
								</table>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>
						
						<div class="detail_item_layer">
							<label class="detail_item_label">商品图片：</label>
							<div class="detail_item_content">
									<#--<div style="margin: 0 0 0px 0;padding: 0px 0px 5px 0px;">
										<span style="display: inline-block;position: relative;"><a class="button" style="margin-left: 5px;">
											<span><div id="spanButtonPlaceHolder">控件加载中...</div></span>
										</a>
									</div>
									-->
									<div id="goods_img_file_layer">
										<ul>
										<#list 1..7 as i>
											<li id="goods_img_file_${i}_li" class="goods_img_file_li">
												<div id="goods_img_file_opt_${i}" sortNo="${i}" class="goods_img_file_opt" style="display: none;">
													<span class="goods_img_file_opt_del" sortNo="${i}" onclick="updateTaobaoItem.previewOptDelete_OnClick(this,${i})">删除</span>
												</div>
												<div id="goods_img_layer_${i}" class="goods_img_layer">
													<img src="${BasePath}/yougou/images/unknow_img.png" class="goods_img_image" width="100" height="100" />
												</div>
												<span class="oper_lf"></span><span class="oper_rt"></span>
												<input type="file" id="goods_img_file_${i}" sortNo="${i}" name="commodityImage_${i}" isFirstChange="0" class="detail_jq_file_btn" onchange="updateTaobaoItem.inputFile_OnChange(this,${i})" />
												<input type="hidden" id="img_file_id_${i}" name="imgFileId" value="-1"/>
											</li>
										</#list>
										</ul>
										<input type="hidden" name="preImage" id="imageHideInput">
										
									</div>
								<div id="commodityImage_error_tip" class="goods_error_tip"></div>
								<div id="goods_commodityImage_tip">
									允许上传5-7张商品图片，图片格式为jpg，尺寸800px-1000px的正方形图片，大小不超过500KB。
								</div>
							</div>
							<div style="height: 1px; clear: both;"></div>
						</div>	
						<div class="detail_item_layer" style="margin-top:20px;">
							<label class="detail_item_label">&nbsp;商品描述： </label>
							<div id="image-manage">
									<#import "/manage/commodity/add_update_commodity_image_manage.ftl" as p>
									<@p.add_update_commodity_image_manage/>
							</div>
							<div style="clear:both"></div>
						</div>
						<div class="detail_item_layer">
							<label class="detail_item_label">&nbsp;</label>
							<div class="detail_item_content">
								<textarea id="goods_prodDesc" name="prodDesc" style="width:780px;height:500px;">${taobaoItem.yougouDescription!''}</textarea>
							</div>
						</div>
						<div style="height: 1px; clear: both;"></div>
						<div class="detail_submit_layer">				
							<a class="button" id="commodity_save" style="margin-left:4px;" onclick="updateTaobaoItem.submitForm(0)" ><span>&nbsp;&nbsp;保存&nbsp;&nbsp;</span></a>
							<#--<a class="button" id="commodity_preview" style="margin-left:4px;" onclick="updateTaobaoItem.submitForm(1)" ><span>预览</span></a>-->
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
							<input type="hidden"   name="numIid" value="${taobaoItem.numIid}"/>
							<input type="hidden"   name="extendId" value="${taobaoItem.extendId}"/>
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
			</div>
		</div>
</div>
</body>
</html>