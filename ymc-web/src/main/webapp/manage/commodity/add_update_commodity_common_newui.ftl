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
    <title>优购-商家中心</title>
    <script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
    <link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/upload.css?${style_v}" />
    <!-- 编辑器插件样式 -->
    <link rel="stylesheet" href="${BasePath}/yougou/js/kindeditor/themes/default/default.css?${style_v}" type="text/css"/>
    <link rel="stylesheet" href="${BasePath}/yougou/js/calendar/lhgcalendar.css?${style_v}" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/business-center.css?${style_v}" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/zTreeStyle.css?${style_v}"/>
    <script type="text/javascript">
		var basePath = "${BasePath}";
		var isSetSizePrice = "${isSizePrice!''}";
		/**商品编号*/
		var requestCommodityNo = "${commodityNo!''}";
		/**是否入优购仓*/
		var isInputYougouWarehouse = "${isInputYougouWarehouse!''}";
		/**是否入优购仓库，入优购仓*/
		var SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN = "${SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN!''}";
		/**是否入优购仓，true:入优购仓*/
		var isInputYougouWarehouseFlag = isInputYougouWarehouse == SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN;
		/**图片预览域名*/
		var commodityPreviewDomain = "${commodityPreviewDomain!''}";
		/**上传的图片数量*/
		var ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT = "${ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT!''}";
		/**1000*1000大图的后缀*/
		var IMG_NAME_SIZE_1000_1000_SUFFIX = "${IMG_NAME_SIZE_1000_1000_SUFFIX!''}";
		/**图片扩展名*/
		var IMG_NAME_EXT_TYPE = "${IMG_NAME_EXT_TYPE!''}";
		/**商品描述图片合法正则表达式  */
		var yougouValidImageRegex = '${YOUGOU_VALID_IMAGE_REGEX!''}';
		goodsAdd = {};
		/**url相关*/
        goodsAdd.url = {};
		/**表单提交url*/
		goodsAdd.url.submitUrl = basePath + "/commodity/${submitUrl}";
		/**模板id，可能选择分类的时候使用了模板了*/
		var templateId = "${templateId!''}";
		var isSensitive = "${isSensitive!''}";
	</script>
</head>

<body>
    <!-- start cont -->
    <div id="content" class="wrap clearfix">
        <!-- start left -->
        <div id="left" class="fl">
            <h1 class="title cgary">商品属性</h1>
            <ul class="goods_name clearfix">
            </ul>
            <div class="check_all clearfix">
                <div class="fl mg10">
                    <input type="checkbox" checked="checked" name="confirm" value="1"/>
                </div>
                <p class="fl">我确认以上商品信息无误，并为我提交的信息负责。</p>
            </div>
        </div>
        <!-- end left -->
        <!-- start right -->
        <div id="right" class="fl">
            <div class="fix_title">
                <span class="cblue"><a onclick="javascript:goodsAdd.saveTemplate()" href="javascript:void(0);">保存属性模板</a></span>
                <span><input type="button" id="commodity_pre_f" onclick="goodsAdd.submit.preview();" class="btn btn_gary1 isSizePrice" value="预览" /></span>
                <span><input type="button" id="commodity_audit_f" onclick="goodsAdd.submit.submitForm(true);" class="btn btn_gary6" value="保存并提交审核" /></span>
                <span><input type="button" id="commodity_save_f" onclick="goodsAdd.submit.submitForm(false);" class="btn btn_gary1" value="保存" /></span>
                <#if commodityNo??>
				<#else>
                <span><label for="product_release">发布相似商品</label></span>
                <span><input type="checkbox" name="product" class="product" id="product_release"/></span>
                </#if>
                <span class="textmore"></span>
            </div>
            <form id="secondForm" action="#" method="post" enctype="multipart/form-data">
            <h1 class="title cgary">商品基本信息</h1>
            <dl class="basic_information basic-pd5 clearfix">
                <dt class="v_title">品牌：</dt>
                <dd id="brandNoDd">${(submitVo.brandName)!}<p class="check-tip cred none" id="brandNo_tip"></p></dd>
            </dl>
            <dl class="basic_information basic-pd5 clearfix" id="catedl">
                <dt class="v_title">分类：</dt>
                <dd class="catedd" id="categoryDd">
                ${(submitVo.rootCatName)!} &gt ${(submitVo.secondCatName)!} &gt ${(submitVo.catName)!}
                <p class="check-tip cred none" id="category_tip"></p></dd>
                <dd><span class="btn btn_gary2 btn-editor" id="reSel">编辑分类</span></dd>
            </dl>
            <dl class="basic_information basic-pd5 clearfix">
                <dt class="v_title">属性模板：</dt>
                <dd><a href="javascript:void(0);" id="showTpl">查看已保存属性模板</a></dd>
            </dl>
            <dl class="basic_information basic-pd5 clearfix">
                <dt class="v_title"><em>*</em>商品名称：</dt>
                <dd>
                    <input type="text" name="commodityName" maxlength="200" class="basic_text" id="commodity_name" />
                    <p class="tip cgary0">命名范例：品牌名(英/中) + 年份 + 季节 + 材质 + 性别 + 类别 + 货号，系统将自动的删除不符合规则的字符串。</p>
                    <p class="check-tip cred none" id="commodity_name_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix">
                <dt class="v_title">商品卖点：</dt>
                <dd>
                    <input type="text" name="sellingPoint" maxlength="25" class="basic_text" id="commodity_selling" />
                   	<p class="tip cgary0">最多支持25个字</p>
                    <p class="check-tip cred none" id="commodity_selling_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>商品款号：</dt>
                <dd>
                    <input type="text" name="styleNo" class="commodity_text" id="commodity_style" />
                    <p class="check-tip cred none" id="commodity_style_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix isSizePrice">
                <dt class="v_title"><em>*</em>款色编码：</dt>
                <dd>
                    <input type="text" name="supplierCode" class="commodity_text" id="commodity_code" />
                    <p class="check-tip cred none" id="commodity_code_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix isSizePrice">
                <dt class="v_title"><em>*</em>优购价：</dt>
                <dd>
                    <input type="text" name="salePriceStr" class="commodity_text" id="commodity_price" />
                    <p class="check-tip cred none" id="commodity_price_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix isSizePrice">
                <dt class="v_title"><em>*</em>市场价：</dt>
                <dd>
                    <input type="text" name="publicPriceStr" class="commodity_text" id="commodity_market_value" />
                    <p class="check-tip cred none" id="commodity_market_value_tip"></p>
                </dd>
            </dl>
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>年份：</dt>
                <dd>
                    <#if yearArr?? && curYear??>
						<#list yearArr as item>
							<input type="radio" id="goods_years_${(item?string("#"))!''}" value="${(item?string("#"))!''}" name="years" 
								<#if item?? && item == curYear>
									checked="checked"
								</#if>
							/>
							<label for="goods_years_${(item?string("#"))!''}">${(item?string("#"))!''}</label>
							&nbsp;&nbsp;&nbsp;
						</#list>
						<p class="check-tip cred none" id="goods_years__tip"></p>
					</#if>
                </dd>
            </dl>
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>颜色分类：<p class="check-tip cred ml13 none" id="specName_tip"></p></dt>
                <dd>
                    <p class="tip cgary0">请尽量选择已有的颜色，如果自定义，也请选择相近的颜色进行自定义，否则会被搜索降权。</p>
                    <div class="sku-list mb15" id="colorDiv">
                    </div>
                </dd>
            </dl>
            <!-- 尺码 -->
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>尺码：<p class="check-tip cred ml13 none" id="goods_sizeNo_tip"></p></dt>
                <dd>
                    <div class="sku-list size-list">
                    </div>
                </dd>
            </dl>
            <table cellpadding="0" cellspacing="0" class="size_tab">
                <thead>
                    <tr class="tab_title">
                        <th>颜色分类</th>
                        <th>尺码</th>
                        <th style="display:none;" class="sizePrice"><em>*</em>优购价（元）</th>
						<th style="display:none;" class="sizePrice"><em>*</em>市场价（元）</th>
                        <th><em>*</em>货品条码</th>
                        <th>库存数量</th>
                        <th>重量(g)</th>
                    </tr>
                </thead>
                <tbody id="goods_color_size_tbody">
                </tbody>
            </table>
            <!-- 商品图片 -->
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>商品图片：<p class="check-tip cred ml13 none" id="pm_tip"></p></dt>
                <dd>
                	<#if ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT??>
                    <div id="itempic">
                        <div class="pic-manager2">
                                <ul class="pm-list clearfix">
                                	<#list 1..ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT as i>
                                		<li class="pm-item">
	                                        <div class="pm-box" id="pic_${i }">描述图</div>
	                                        <div class="pm-itemcont">
	                                            <!--<input type="button" value="上传新图片" class="btn btn_gary2 color_file pm_path" />
	                                            <input type="file" id="goods_img_file_${i}" name="commodityImage_${i}" class="color_path" style="display:none" onchange="goodsAdd.imageUpload.inputFile_OnChange(this,${i})"/> -->
	                                            <input type="file" id="goods_img_file_${i}" sortNo="${i}" name="commodityImage_${i}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this,${i})" />
	                                        </div>
	                                        <span class="oper_lf"></span>
	                                        <span class="oper_lr"></span>
	                                        <#if i gt 5>
	                                        	<span class="oper_dt" num=${i}></span>
	                                        </#if>
	                                        <input type="hidden" value="-1" name="imgFileId" id="img_file_id_${i }"/>
	                                    </li>
                                	</#list>
                                </ul>
                        </div>
                        <div class="pm-msg">
                            <p class="cred">允许上传5-7张商品图片，图片格式为jpg，尺寸800px-1000px的正方形图片，大小不超过500KB。 </p>
                            <p>请不要在商品图片上添加其他信息，如水印、商标、优惠信息等，一经发现将进行降权处理。</p>
                        </div>
                        <div class="imgSpaceBar">
                            <div id="filePicker" class="bulk_upload">批量上传</div>
                            <a href="http://open.yougou.com/help/content/8a8a8ab3401517c8014033bb15e600c2.shtml" target="_blank">查看图片上传规范</a>
                        </div>
                    </div>
                    </#if>
                </dd>
            </dl>
            <dl class="basic_information clearfix">
                <dt class="v_title"><em>*</em>商品描述：</dt>
                <dd>
                    <div class="rel clearfix">
                        <a href="javascript:void(0)" class="close" id="open-top">收起</a>
                        <ul class="des_tab clearfix" id="image-tab">
                            <li num="0" class="curr">
                                <span>上传图片</span>
                            </li>
                            <li num="1">
                                <span>选择图片</span>
                            </li>
                            <span class="fr">图片格式为jpg，图片宽790px，高度10-9999px，单张图片小于1M，图片不允许带水印和外链。</span>
                        </ul>
                    </div>
                    <div class="image-con">
                        <div class="clearfix">
                            <div class="catelog">
                                <div class="picdiv2">
                                    <div id="addCatalog" class="btns">
                                        <span class="btn_l"></span>
                                        <b class="ico_btn add"></b>
                                        <span class="btn_txt">添加目录</span>
                                        <span class="btn_r"></span>
                                    </div>
                                    <div onclick="editCatalog();" class="btns">
                                        <span class="btn_l"></span>
                                        <b class="ico_btn change"></b>
                                        <span class="btn_txt">重命名</span>
                                        <span class="btn_r"></span>
                                    </div>
                                    <div onclick="delCatalog();" class="btns">
                                        <span class="btn_l"></span>
                                        <b class="ico_btn delete"></b>
                                        <span class="btn_txt">删除</span>
                                        <span class="btn_r"></span>
                                    </div>
                                </div>
                                <ul id="ztree" class="ztree"></ul>
                            </div>
                            <div class="image-upload">
                                <div id="uploader">
                                    <div class="queueList">
                                        <div id="dndArea" class="placeholder">
                                            <div id="filePickerBatch"></div>
                                            <p>或将图片拖到这里，单次最多可选100张</p>
                                        </div>
                                        <ul class="filelist" id="filelist" style="display:none;height:485px;overflow:auto;"></ul>
                                    </div>
                                    <div class="statusBar" style="display:none;">
                                        <div class="btns">
                                            <label style="float:left;display:inline-block;">
                                                <input type="checkbox" id="selectAll">&nbsp;全选</label> <a href="javascript:void(0)" id="insertImgBtn">插入图片</a><a href="javascript:void(0)" id="cancelBtn">清空已上传</a>
                                            <div id="filePicker2"></div>
                                            <div class="uploadBtn">开始上传</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="image-select">
                                <div class="timeMarkLabel">
                                    <a num="1" href="javascript:void(0);">今天</a>
                                    <a num="2" class="curr" href="javascript:void(0);">最近一个月</a>
                                    <a num="3" href="javascript:void(0);">最近三个月</a>
                                    <a num="-1" href="javascript:void(0);">全部</a>
                                </div>
                                <div class="uptime">
                                    <span>上传时间：</span>
                                    <input type="text" class="time" id="startTime" />
                                    <span>至</span>
                                    <input type="text" class="time" id="endTime" />
                                    <span>图片名称：</span>
                                    <input type="text" id="innerPicName" name="srcPicName" class="img_name" />
                                    <input type="button" value="搜索" onclick="searchData();" class="btn btn_gary1" id="sousou"/>
                                </div>
                                <div class="function_btn clearfix">
                                    <span>
										<input type="checkbox" class="radio_f" id="all_check_f"/>
										<label for="all_check_f" class="radio_lable_f">全选</label>
									</span>
                                    <span>
										<input type="button" class="del_f btn btn_gary1" value="删除"/>
									</span>
                                    <span>
										<input id="picmove" onclick="moveSelectedPic();" type="button" class="move_f btn btn_gary1" value="移动"/>
									</span>
                                    <span>
										<input id="insertImg" onclick="insertImage();return false;" type="button" class="insert_f btn btn_gary2" value="插入图片"/>
									</span>
                                    <span>
										<input type="radio" checked="checked" value='2' name="orderBy" onclick="loadData('1')" class="radio_f" id="time_f"/>
										<label class="radio_lable_f" for="time_f">时间</label>
									</span>
                                    <span>
										<input name="orderBy" type="radio" value='1' class="radio_f" onclick="loadData('1')" id="name_f"/>
										<label for="name_f" class="radio_lable_f">名称</label>
									</span>
                                </div>
                                <div id="pic-list">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="open-bottom"><a href="javascript:void(0);" class="close" id="open-bottom">收起</a></div>
                    <div class="editor_cont">
                        <textarea id="goods_prodDesc" class="editor_textare" name="prodDesc"></textarea>
                    </div>
                    <p class="check-tip cred ml13 none" id="goods_prodDesc_tip"></p>
                    <#if !(commodityNo??)>
                    <div class="div_alike_goods">
                        <input type="checkbox" name="alike_goods" id="alikegoods" />
                        <label for="alikegoods">发布相似商品</label>
                        <span class="cred">(勾选此项，保存或保存并提交审核成功后，将重新跳转到此页面且刚上传的商品数据保持不变。)</span>
                    </div>
                    </#if>
                </dd>
            </dl>
            <input type="hidden" name="brandNo" value="${(submitVo.brandNo)!}"/>
	    	<input type="hidden" name="catStructName" value="${(submitVo.catStructName)!}"/>
	    	<input type="hidden" name="catNo" value="${(submitVo.catNo)!}"/>
	    	<input type="hidden" name="catName" value="${(submitVo.catName)!}"/>
	    	<input type="hidden" name="brandId" value="${(submitVo.brandId)!}"/>
	    	<input type="hidden" name="brandName" value="${(submitVo.brandName)!}"/>
	    	<div id="goods_selected_properties_layer" style="display: none;">
				<div id="goods_selected_properties_propItemNo"></div>
				<div id="goods_selected_properties_propItemName"></div>
				<div id="goods_selected_properties_propValueNo"></div>
				<div id="goods_selected_properties_propValue"></div>
			</div>
			<div id="goods_selected_sizeNo_layer" style="display: none;"></div>
			<div id="goods_selected_sizeName_layer" style="display: none;"></div>
			<input type="hidden" id="goods_properties_propIdInfo" name="propIdInfo"/>
			<input type="hidden" id="goods_prodIdInfo" name="prodIdInfo"/>
			<input type="hidden" name="specName" id="goods_specName"/>
			<input type="hidden" id="page_source_id" name="pageSourceId" value="${pageSourceId}" />
			<input type="hidden" id="auditStatus" name="auditStatus" value="${auditStatus}" />
			<input type="hidden" name="catId" value="${(submitVo.catId)!}"/>
			<input type="hidden" name="commodityNo" value="${commodityNo!''}"/>
			<input type="hidden" name="commodityId" value="${(commodity.id)!''}"/>
	    </form>
            <dl class="basic_information clearfix">
                <dt class="v_title"><em></em></dt>
                <dd>
                    <div class="btn_set pt30">
                        <button onclick="goodsAdd.submit.submitForm(false);" id="commodity_save"  class="btn btn_qing2">保存</button>
                        <button onclick="goodsAdd.submit.submitForm(true);" id="commodity_audit"  class="btn btn_gary3">保存并提交审核</button>
                        <button onclick="goodsAdd.submit.preview();" id="commodity_pre" class="btn btn_gary4 isSizePrice">预览</button>
                        <a href="javascript:void(0);" onclick="javascript:goodsAdd.saveTemplate();">保存属性模板</a>
                    </div>
                </dd>
            </dl>
     	 </div>
        <!-- end right -->
    </div>
    <form id="firstForm" action="/commodity/addCommodityui.sc" style="display: none;" method="post">
   		<input type="hidden" name="brandNo" id="brandNo" value="${(submitVo.brandNo)!}"/>
   		<input type="hidden" name="catStructName" id="catStructName" value="${(submitVo.catStructName)!}"/>
   		<input type="hidden" name="catno" id="catno" value="${(submitVo.catNo)!}"/>
   		<input type="hidden" name="catId" id="catId" value="${(submitVo.catId)!}"/>
   		<input type="hidden" name="commodityNo" value="${commodityNo!''}"/>
   		<input type="hidden" name="rootCatName" value="${(submitVo.rootCatName)!}"/>
   		<input type="hidden" name="secondCatName" value="${(submitVo.secondCatName)!}"/>
   		<input type="hidden" name="catName" value="${(submitVo.catName)!}"/>
   		<input type="hidden" name="brandName" value="${(submitVo.brandName)!}"/>
   		<input type="hidden" name="brandId" value="${(submitVo.brandId)!}"/>
   	</form>
    <!-- 文件批量上传插件 -->
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/jquery.jqFileBtn.js?${style_v}"></script>
    <!-- form表单ajax提交用 -->
    <script type="text/javascript" src="${BasePath}/yougou/js/jquery.form.js"></script>
    <!-- tree插件 -->
    <script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.core-3.5.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.exedit-3.5.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.excheck-3.5.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.exhide-3.5.min.js?${style_v}"></script>
    <!-- 编辑器插件 -->  
    <script charset="utf-8" type="text/javascript" src="${BasePath}/yougou/js/kindeditor/kindeditor.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/kindeditor/zh_CN.js" charset="utf-8"></script>
    <!-- 日期插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/WdatePicker.js"></script>	
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/addOrUpdateCommodityNewui.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/imagehandle.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/jquery.dragsort-0.5.1.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/uploadbatch.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/upload.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/commodity.validate.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/business-center.js?${style_v}"></script>
    ${importJs}
</body>
</html>
</#macro>