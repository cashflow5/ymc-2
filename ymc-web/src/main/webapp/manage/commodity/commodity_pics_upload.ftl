
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-批量发布商品-上传图片</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" href="${BasePath}/yougou/css/jquery-css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css?${style_v}"/>
<link rel="stylesheet" href="${BasePath}/webuploader/style.css?${style_v}"/>
<style>
.webuploader-pick{padding:3px 5px;margin-left:5px}
#uploader .filelist li{cursor:move}
#uploader .filelist li img{width:auto;max-width:110px;}
</style>
</head>
<body>
<!--main_container(Start)-->
<div class="main_container" style="width:775px;">
<!--normal_box(Start)-->
<div class="normal_box">
	<input type="hidden" name="commodityNo" id="commodityNo" value="${commodity.commodityNo }" />
	 <div class="detail_item_layer">
	 		<ul class="tab">
	 		    <p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span style="color:#ff6600;" class="fl ml5">注意：本页面功能不需要提前重命名商品图片！&nbsp;</span></p>
				<li class="curr">
					<span>上传角度图</span>
				</li>
			</ul>
			<div>
				<div id="filePicker" style="float:left;width:100px;">批量上传</div>
				<div style="float:right;display:inline-block;margin-top:3px;"><span class="fl icon_info"></span><span style="color:#ff6600;" class="fl ml5">允许上传5-7张商品图片，图片格式为jpg，尺寸800px-1000px的正方形图片，大小不超过500KB。</span></div>
				<div style="clear:both"></div>
			</div>
			<div class="detail_item_content">
					<div id="goods_img_file_layer">
						<ul>
						<#list 1..7 as i>
							<li id="goods_img_file_${i}_li" class="goods_img_file_li">
									<div id="goods_img_layer_${i}" class="goods_img_layer">
										<img src="${BasePath}/yougou/images/unknow_img.png" class="goods_img_image" width="100" height="100" />
									</div>
									<span class="oper_lf"></span><span class="oper_rt"></span>
									<input type="file" id="goods_img_file_${i}" sortNo="${i}" name="commodityImage_${i}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this,${i})" />
									<input type="hidden" id="img_file_id_${i}" name="imgFileId" value="-1"/>
							</li>
						</#list>
						</ul>
					</div>
					<div id="commodityImage_error_tip" class="goods_error_tip"></div>
			</div>
			<div style="height: 1px; clear: both;"></div>
			
			<ul class="tab">
			    <p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span style="color:#ff6600;" class="fl ml5">需上传至少1张宽度790px、高至少10px，格式为jpg（小写），大小不超过1MB，图片不允许带外链和水印。</span></p>
				<li class="curr">
					<span>上传描述图</span>
				</li>
			</ul>
			<div id="wrapper">
		        <div id="container">
		            <!--头部，相册选择和格式选择-->
		            <div id="uploader">
		                <div class="queueList">
		                    <div id="dndArea" class="placeholder">
		                        <div id="filePickerBatch"></div>
		                        <p>或将图片拖到这里，单次最多可选100张</p>
		                    </div>
		                    <ul class="filelist" id="filelist"></ul>
		                </div>
		                <div class="statusBar" style="display:none;">
		                    <div class="btns">
		                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
		                    </div>
		                </div>
		            </div>
		        </div>
			</div>
		</div>
	</div>
	<div style="height: 30px; clear: both;margin-bottom:30px;margin-top:10px;margin-left:330px;">
		<a class="button" id="commodity_pic_save" onclick="savePicture();" ><span>保存图片</span></a>
	</div>
</div>

<script type="text/javascript">
var basePath = "${BasePath}";
var image_hander = 0;
/** 是否执行 */
var is_execute = true;
/**loading图片 url*/
loadingImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/loading.gif";

var basePath = "${BasePath}";

var goodsAdd = {};
/**url相关*/
goodsAdd.url = {};
/**表单提交url*/
goodsAdd.url.submitUrl = basePath + "/commodity/";

goodsAdd.url.getCommodityByNo = basePath + "/commodity/getCommodityByNo.sc?commodityNo=${commodityNo}"; 

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

goodsAdd.commodity = {};

goodsAdd.commodity.imgFile = {};

goodsAdd.url.queryBrandCatUrl = basePath + "/commodity/queryBrandCat.sc";

goodsAdd.add_or_update_commodity_image_count = "${ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT}";

goodsAdd.commodityPreviewDomain = "${commodityPreviewDomain}";

goodsAdd.imageUpload = {};

goodsAdd.url.loadingImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/loading.gif";

var IMG_NAME_SIZE_1000_1000_SUFFIX = "${IMG_NAME_SIZE_1000_1000_SUFFIX!''}";
/**图片扩展名*/
var IMG_NAME_EXT_TYPE = "${IMG_NAME_EXT_TYPE!''}";

goodsAdd.commodityNo="${commodityNo}";

goodsAdd.latestNumber = "${numbers}";

</script>

<script src="${BasePath}/yougou/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.js"></script>
<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/imagehandle.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/jquery.jqFileBtn.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/webuploader/upload.js?${style_v}"></script>
<script src="${BasePath}/yougou/js/manage/commodity/commodity.pic.upload.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.dragsort-0.5.1.js?${style_v}"></script>
<script>
$("#filelist").dragsort({dragSelector: "li", dragBetween: true, placeHolderTemplate: "<li class='placeHolder'></li>" });
</script>
</body>
</html>

