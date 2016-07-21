			image_hander = 0;
			/** 是否执行 */
            is_execute = true,
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

            /**商品描述相关*/
            goodsAdd.prodSpec = {};

            /**表单提交相关*/
            goodsAdd.submit = {};
    		/**验证相关*/
    		goodsAdd.validate = {};
    		/**图片相关*/
            goodsAdd.imageUpload = {};
            /**获取商品属性、颜色、尺码的url*/
			goodsAdd.url.getCommodityPropertitiesUrl = basePath + "/commodity/loadPropertitiesByCatId.sc?catId=";
            /**查询新建商品url*/
            goodsAdd.url.goQueryDraftCommodityUrl = basePath + "/commodity/goQueryDraftCommodity.sc";
            /**查询待审核商品url*/
            goodsAdd.url.goQueryPendingCommodityUrl = basePath + "/commodity/goQueryPendingCommodity.sc";
            /**查询审核拒绝商品url*/
            goodsAdd.url.goQueryRefuseCommodity = basePath + "/commodity/goQueryRefuseCommodity.sc";
            /**查询全部商品url*/
            goodsAdd.url.goQueryAllCommodityUrl = basePath + "/commodity/goQueryAllCommodity.sc";
            /**待售全部商品url*/
            goodsAdd.url.goForSaleCommodity = basePath + "/commodity/goWaitSaleCommodity.sc";
            /**提交成功图片url*/
            goodsAdd.url.successImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";
            /**loading图片 url*/
            goodsAdd.url.loadingImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/loading.gif";
            /**商品描述图片选择器url*/
            goodsAdd.url.descImgSelectorUrl = basePath + '/commodity/pics/selector.sc';

            goodsAdd.url.queryBrandCatUrl = basePath + "/commodity/queryBrandCat.sc";

            /*
             * 页面初始化
             */
            $(function() {
            	ymc_common.loading("show", "正在加载信息...");
                $('#commodity_name').blur(function() {
                    var value = $(this).val();
                    if (!isEmpty(value)) {
                        $('#commodity_name_tip').addClass("none");
                    }
                });
                $('#commodity_style').blur(function() {
                    var value = $(this).val();
                    if (!isEmpty(value)) {
                        $('#commodity_style_tip').addClass("none");
                    }
                });
                $('#commodity_code').blur(function() {
                    var value = $(this).val();
                    if (!isEmpty(value)) {
                        $('#commodity_code_tip').addClass("none");
                    }
                });
                $('#commodity_price').blur(function() {
                    var value = $(this).val();
                    if (!isEmpty(value)) {
                        $('#commodity_price_tip').addClass("none");
                    }
                });
                $('#commodity_market_value').blur(function() {
                    var value = $(this).val();
                    if (!isEmpty(value)) {
                        $('#commodity_market_value_tip').addClass("none");
                    }
                });
                if(isSetSizePrice){
                	$(".isSizePrice").hide();
                	$(".sizePrice").show();
                }else{
                	$(".isSizePrice").show();
                	$(".sizePrice").hide();
                }
                goodsAdd.color.setColors();
                //加载商品属性
                loadPropFun($("#catId").val());
            });
            
            var loadPropFun = function(catId) {
            	catId = $.trim(catId);
            	if(catId.length == 0) {
            		return;
            	}
            	//清空货品信息
            	$("#goods_color_size_tbody").html("");
            	if(catId != null) {
            		$.getJSON(goodsAdd.url.getCommodityPropertitiesUrl + catId,function(data){
            			if(data == null || typeof(data) != "object" || data.isSuccess == null ||
        						typeof(data.isSuccess) == "undefined") {
        					return;
        				}
        				if(data.isSuccess != "true") {
        					ygdg.dialog.alert(data.errorMsg);
        					return;
        				}
        				goodsAdd.datasheetIndex = data.sheetIndex;
        				//设置主图
        				goodsAdd.cat.setSheetIndex(data.sheetIndex);
        				//加载商品属性
        				goodsAdd.properties.load(data.propList);
        				//加载尺码、颜色列表
        				goodsAdd.size.load(data.sizeList);
            		});
            	}
            };
            
            goodsAdd.color.setColors = function(){
            	var colors = [
            	      {"className":"army_green","colorName":"军绿色"}, 
            	      {"className":"sky_blue","colorName":"天蓝色"},
            	      {"className":"chocolate","colorName":"巧克力色"}, 
            	      {"className":"orange","colorName":"桔色"},
            	      {"className":"light_grey","colorName":"浅灰色"}, 
            	      {"className":"light_green","colorName":"浅绿色"},
            	      {"className":"light_yellow","colorName":"浅黄色"}, 
            	      {"className":"dark_khaki","colorName":"深卡其布色"},
            	      {"className":"dark_grey","colorName":"深灰色"}, 
            	      {"className":"deep_purple","colorName":"深紫色"},
            	      {"className":"navy_blue","colorName":"深蓝色"}, 
            	      {"className":"white","colorName":"白色"},
            	      {"className":"pink","colorName":"粉红色"}, 
            	      {"className":"violet","colorName":"紫罗兰"},
            	      {"className":"purple","colorName":"紫色"}, 
            	      {"className":"red","colorName":"红色"},
            	      {"className":"green","colorName":"绿色"}, 
            	      {"className":"flower","colorName":"花色"},
            	      {"className":"blue","colorName":"蓝色"}, 
            	      {"className":"brown","colorName":"褐色"},
            	      {"className":"clarity","colorName":"透明"},
            	      {"className":"wine_red","colorName":"酒红色"}, 
            	      {"className":"yellow","colorName":"黄色"},
            	      {"className":"black","colorName":"黑色"}
            	];
            	var colorUL = '<ul class="clearfix">';
            	for(var i=0,_len=colors.length;i<_len;i++){
            		colorUL+='<li><input type="radio" name="color_radio" class="color_radio" id="c-'+(i+1)+'" value="'+colors[i].className+'" />'+
            				 '<label class="color_bg '+colors[i].className+'" for="c-'+(i+1)+'"></label>'+
            				 '<input type="text" class="color_text" id="color-'+(i+1)+'" value="'+colors[i].colorName+'" /></li>';
            	}
            	colorUL+="</ul>";
            	$("#colorDiv").html(colorUL);
            	
            };
            
            
            /**
             * 上传框变化事件
             * @param {Object} inputFile 上传框对象
             */
            goodsAdd.imageUpload.inputFile_OnChange = function(inputFile, number) {
                if (CheckFile(inputFile)) {
                    var width = 88;
                    var height = 88;
                    //图片预览
            		var browserInfo = getBrowserInfo()+"";
                	if(browserInfo.lastIndexOf("msie")==-1){
                		goodsAddImgPreview(inputFile, number, width, height);
                	}
                    var loading = '<span id="image_loading_' + number + '" class="image_load">'+
                    			  '<img style="position:relative;z-index:2;width: 16px;height: 16px;"  src="' + goodsAdd.url.loadingImgUrl + '" width="16px" height="16px" />'+
                    			  '<span class="picSpan"></span></span>';
                    $('#pic_' + number).append(loading);
                    //异步上传图片、禁用保存按钮
                    image_hander++;
                    isforbiddenButtonNew('commodity_save', true, null);
                    isforbiddenButtonNew('commodity_audit', true, null);
                    isforbiddenButtonNew('commodity_pre', true, null);
                    isforbiddenButtonOfQuick('commodity_save_f',true,null);
                    isforbiddenButtonOfQuick('commodity_audit_f',true,null);
                    isforbiddenButtonOfQuick('commodity_pre_f',true,null);
                    var htmlstr = $("#pic_"+number).html();
                    //上传图片
                    ajaxFileUpload({
                        id: inputFile.id,
                        url: '/img/upload.sc?no=' + number,
                        callback: function() {
                            image_hander--;
                            var src = this.responseText;
                            if (src != null && "" != src && "null" != src) {
                                src = src.replace(/<pre>/ig, "");
                                src = src.replace(/<\/pre>/ig, "");
                                var obj = eval("(" + src + ")");
//                                alert(obj);
//                                alert("##############->"+obj.success);
                                if (obj.success == true) {
                                    $("#img_file_id_" + number).val(obj.message);
                                    if (browserInfo.lastIndexOf("msie") != -1) {
                                    	$("#pic_" + number).html("<img src='"+getThumbnail(obj.message)+"'></img>");
                                    }
                                    $("#pm_tip").addClass("none");
                                } else {
                                    $("#img_file_id_" + number).val("-1");
                                    $("#img_file_id_" + number).siblings("div.pm-box").html(htmlstr);
                                    if (obj.message == '1') ygdg.dialog.alert('request请求参数no为空值,请检查!');
                                    else if (obj.message == '2') ygdg.dialog.alert('获取登录会话信息失败,请尝试重新登录操作!');
                                    else if (obj.message == '3') ygdg.dialog.alert('商品图片大小超过了 500 KB');
                                    else if (obj.message == '4') ygdg.dialog.alert('商品图片分辨率不符合  800-1000px * 800-1000px的规格');
                                    else if (obj.message == '5') ygdg.dialog.alert('图片校验异常');
                                    else if (obj.message == '6') ygdg.dialog.alert('上传图片失败, 请重新再试！');
                                    else if (obj.message == '7') ygdg.dialog.alert('上传图片失败,获取不到图像对象，请重试!');
                                    else ygdg.dialog.alert("图片上传失败，请重新上传！");
                                }
                            } else {
                                $("#img_file_id_" + number).val("-1");
                                $("#img_file_id_" + number).siblings("div[class='pm-box']").html(htmlstr);
                                ygdg.dialog.alert("图片上传，服务器返回数据格式异常,请联系管理员！");
                            }
                            //图片处理完成、释放保存按钮 (is_execute判断再绑定事件时是否执行函数)
                            is_execute = false;
                            if (image_hander <= 0) {
                                isforbiddenButtonNew('commodity_save', false, function() {
                                    if (is_execute) goodsAdd.submit.submitForm(false);
                                });
                                isforbiddenButtonNew('commodity_audit', false, function() {
                                    if (is_execute) goodsAdd.submit.submitForm(true);
                                });
                                isforbiddenButtonNew('commodity_pre', false, function() {
                                    if (is_execute) goodsAdd.submit.preview();
                                });
                                isforbiddenButtonOfQuick('commodity_save_f',false,function(){
                                	if (is_execute) goodsAdd.submit.submitForm(false);
                                });
                                isforbiddenButtonOfQuick('commodity_audit_f',false,function(){
                                	if (is_execute) goodsAdd.submit.submitForm(true);
                                });
                                isforbiddenButtonOfQuick('commodity_pre_f', false, function() {
                                    if (is_execute) goodsAdd.submit.preview();
                                });
                            }
                            is_execute = true;
                            //移除loading
                            $('#image_loading_' + number).remove();
                            $("#img_file_id_" + number).parent("li").removeClass("hover");
                        }
                    });
                }
                //error_tip hide
                $('#pm_tip').addClass("none");
            };
            
            
            function getSmallImage(imgUrl) {
                var yougou_href_url_reg = new RegExp('http://.+/pics/merchantpics/.+', 'i');
                if (yougou_href_url_reg.test(imgUrl)) {
                    return imgUrl.substring(0, imgUrl.lastIndexOf(".jpg")) + ".png" + imgUrl.substring(imgUrl.lastIndexOf(".jpg") + 4);
                } else {
                    return imgUrl.substring(0, imgUrl.lastIndexOf(".jpg")) + imgUrl.substring(imgUrl.lastIndexOf(".jpg"));
                }
            }

            goodsAdd.cat.setSheetIndex = function(sheetIndex) {
                    $("#pic_"+sheetIndex).text("主图");
                    $("#pic_"+sheetIndex).addClass("main");
                    $("#pic_"+sheetIndex).attr("title","主图");
                };
                /**
                 * 清除商品属性、颜色、尺码
                 */
            goodsAdd.cat.clearProperties = function() {
                $("#goods_prop_layer .detail_item_content").html("");
                $("#goods_color_layer .detail_item_content").html("");
                $("#goods_size_layer .detail_item_content").html("");

                $("#goods_selected_sizeNo_layer").html("");
                $("#goods_selected_sizeName_layer").html("");
            };

            /**
             * 加载商品属性
             * @param {Array} propList 商品属性列表
             */
            goodsAdd.properties.load = function(propList) {
            	//alert("loading.........");
                if (!(propList)) return;
                var html = '';
                var propObj = null; //单个属性
                var propValues = null; //属性值数组
                var propValueObj = null; //单个属性值
                for (var i = 0, len = propList.length; i < len; i++) {
                    propObj = propList[i];
                    propValues = propObj.propValues;
                    valueType = propObj.valueType;
                    if (!Boolean(propValues) || !Boolean(propValues.length)) continue;
                    //对含有数字的属性值进行排序
            		propValues = sortPropValue(propValues).reverse();
                    if (valueType == 1) {
                    	//多选
                        var checkboxhtml = '<div class="shutting_val">';
                        for (var j = 0, len2 = propValues.length; j < len2; j++) {
                            propValueObj = propValues[j];
                            checkboxhtml += formatString(
                                '<div class="shut_checkbox clearfix">' +
                                '	<div class="shut_title"><input type="checkbox" onclick="goodsAdd.properties.confirm(\'{#propItemNo}\', \'{#propItemName}\');" value="{#value};{#name}" id="shut{#propItemNo}_{#value}" class="shut" name="goods_prop_checkbox_{#propItemNo}"></div>' +
                                '	<div class="shut_cont" title="{#name}"><label for="shut{#propItemNo}_{#value}">{#name}</label></div></div>',
                                {
                                    value: propValueObj.propValueNo,
                                    name: propValueObj.propValueName,
                                    propItemNo: propObj.propItemNo,
                                    propItemName: propObj.propItemName,
                                });
                        }
                        checkboxhtml += '<div class="shut_checkbox clearfix"><div class="shut_title">'+
        								'<input type="button" value="确定" id="shut_yes" class="btn btn_qing2 btn_yes" name="btn">'+
        								'</div></div></div>';
                        
                        html += formatString(
                            '<li><div id="shutting-{#index}" class="clearfix shutting fl">' +
                            '	<div class="title"><em>{#must}</em>{#propItemName}：{#tips}</div><div class="shutting_span_val"></div>' +
                            '   <div class="shutting_click show">点击选择</div>' +
                            '   <div class="shutting_edit">编辑</div>' +
                            '	<input type="hidden" id="goods_prop_valuetype_{#propItemNo}"    '+
                            'propItemNo="{#propItemNo}" propItemName="{#propItemName}" prodMustInput="{#clsMustInput}" value="1" />'+
                            '	{#checkboxs}</div>'+
                            '</li>', {
                            	index:(i+1),
                                propItemNo: propObj.propItemNo,
                                propItemName: propObj.propItemName,
                                must: propObj.isShowMall == 0 ? "*" : "",
                                clsMustInput: propObj.isShowMall == 0 ? "true" : "false",
                                checkboxs: checkboxhtml,
                                tips: propObj.isShowMall == 0 ?formatString('<p class="check-tip cred ml10" id="{#propItemNo}_tip"></p>',{propItemNo:propObj.propItemNo}):''
                            });
                    } else {
                    	
                     
                    	var optionsHtml = '<select onchange="goodsAdd.properties.selectChange(this);" propitemno="'+propObj.propItemNo+'" '+
                    					  'name="goods_prop_select_'+propObj.propItemNo+'" propitemname="'+propObj.propItemName+'" show="1" style="margin: 0 5px 5px 25px; width: 168px;float:none ;"'+
                    					  ' id="'+propObj.propItemNo+'">';
                    	optionsHtml +='<option value="">--请选择--</option>';
                        //当前属性选项
                        for (var j = 0, len2 = propValues.length; j < len2; j++) {
                            propValueObj = propValues[j];
                            if (propObj.propItemNo == "1Iph6" && goodsAdd.anomalyColors[propValueObj.propValueName] != null) { //过滤特殊色系
                                continue;
                            }
                            optionsHtml += formatString(
                                '<option value="{#propValueNo}" propvaluename="{#propValueName}">{#propValueName}</option>', {
                                    propValueNo: propValueObj.propValueNo,
                                    propValueName: propValueObj.propValueName
                                });
                        }
                        optionsHtml+="</select>";
                    
                        html += formatString(
                            '<li><div class="goods_class clearfix fl">' +
                            '	<span class="title"><em>{#must}</em>{#propItemName}：{#tips}</span>' +
                            '	<span class="goods_span_val"></span>' +
                            '   <input type="hidden" id="goods_prop_valuetype_{#propItemNo}" name="goods_prop_valuetype_{#propItemNo}"  value="0" propItemNo="{#propItemNo}" propItemName="{#propItemName}"/>' +
                            '   <span class="fr edit">编辑</span></div>' +
                            '	{#options}'+
                            '</div></li>', {
                                propItemNo: propObj.propItemNo,
                                propItemName: propObj.propItemName,
                                must: propObj.isShowMall == 0 ? "*" : "",
                                clsMustInput: propObj.isShowMall == 0 ? "true" : "false",
                                options: optionsHtml,
                                tips: propObj.isShowMall == 0 ?formatString('<p class="check-tip cred ml10" id="{#propItemNo}_tip"></p>',{propItemNo:propObj.propItemNo}):''
                            });
                    }
                }
                $("#content .goods_name").html(html);
                //渲染下拉框
                var $propSelects = $("#content .goods_name select");
                var propSelect = null;
                for (var i = 0, len = $propSelects.length; i < len; i++) {
                    propSelect = $propSelects[i];
                    $(propSelect).jqSelect();
                    $(propSelect).addClass("goods_properties_select");
                }
            };

            /**
             * 清空商品属性信息
             */
            goodsAdd.properties.clear = function() {
                $("#goods_selected_properties_propItemNo").html("");
                $("#goods_selected_properties_propItemName").html("");
                $("#goods_selected_properties_propValueNo").html("");
                $("#goods_selected_properties_propValue").html("");
            };

            /**
             * 
             * 加载尺码列表
             * @param {Array} sizeList 尺码列表
             */
            goodsAdd.size.load = function(sizeList) {
            	sizeList = sortPropValue(sizeList);
                var html = '<ul class="clearfix">';
                for (var i = 0, len = sizeList.length; i < len; i++) {
                    html += formatString(
                    		
                        '<li title="{#name}">' +
                        '	<input type="{#type}" name="goods_sizeNo" id="goods_sizeNo_display_{#value}" sizename="{#name}" value="{#value}" /> ' +
                        '	<label for="goods_sizeNo_display_{#value}">{#name}</label>' +
                        '	</li>', {
                            type: isSetSizePrice && requestCommodityNo ? 'radio' : 'checkbox',
                            value: sizeList[i].propValueNo,
                            name: sizeList[i].propValueName
                        });
                }
                html += '</ul>';
                $("#right .size-list").html(html);
            };

            /**
             * 尺码比较器（字符串）
             * @param {Object} sizeA
             * @param {Object} sizeB
             * @return sizeA 大于 sizeB，则返回大于0的数字
             */
            goodsAdd.size.sizeStringComparator = function(sizeA, sizeB) {
                return sizeA.propValueName.localeCompare(sizeB.propValueName);
            };

            /**
             * 尺码比较器（数字）
             * @param {Object} sizeA
             * @param {Object} sizeB
             * @return sizeA 大于 sizeB，则返回大于0的数字
             */
            goodsAdd.size.sizeNumberComparator = function(sizeA, sizeB) {
                var numA = parseFloat(sizeA.propValueName);
                var numB = parseFloat(sizeB.propValueName);
                return numA - numB;
            };

            function getBrowserInfo() {
                var agent = navigator.userAgent.toLowerCase();
                var regStr_ie = /msie [\d.]+;/gi;
                var regStr_ff = /firefox\/[\d.]+/gi
                var regStr_chrome = /chrome\/[\d.]+/gi;
                var regStr_saf = /safari\/[\d.]+/gi;
                //IE
                if (agent.indexOf("msie") > 0) {
                    return agent.match(regStr_ie);
                }

                //firefox
                if (agent.indexOf("firefox") > 0) {
                    return agent.match(regStr_ff);
                }

                //Chrome
                if (agent.indexOf("chrome") > 0) {
                    return agent.match(regStr_chrome);
                }

                //Safari
                if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
                    return agent.match(regStr_saf);
                }
            }

            function getThumbnail(src) {
                var index = src.lastIndexOf(".");
                var srcName = src;
                if (index != -1) {
                    srcName = src.substring(0, index + 1) + "png";
                }
                return srcName;
            }

            /**商品描述编辑器选项*/
            goodsAdd.prodSpec.options = {
                resizeType: 0,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                allowFlashUpload: false,
                allowMediaUpload: false,
                allowFileUpload: false,
                newlineTag: 'br',
                htmlTags: {
                    font: ['color', 'size', 'face', '.background-color'],
                    span: ['style'],
                    div: ['class', 'align', 'style'],
                    table: ['class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'style'],
                    'td,th': ['class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor', 'style'],
                    a: ['class', 'href', 'target', 'name', 'style'],
                    embed: ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality',
                        'style', 'align', 'allowscriptaccess', '/'
                    ],
                    img: ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'style', '/', 'usemap'],
                    hr: ['class', '/'],
                    br: ['/'],
                    'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6': ['align', 'style'],
                    'tbody,tr,strong,b,sub,sup,em,i,u,strike': [],
                    'map': ['id', 'name'],
                    'area': ['alt', 'coords', 'href', 'nohref', 'shape', 'target']
                },
                items: [
                    'source', '|',
                    'undo', 'redo', '|',
                    'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
                    'formatblock', 'fontname', 'fontsize', '|',
                    'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
                    'table', 'hr', 'anchor', 'link', 'unlink'
                ],
                afterBlur:function(){
                	if(this.isEmpty()){
                		$("#goods_prodDesc_tip").removeClass("none").html("请填写描述！");
                	}else{
                		$("#goods_prodDesc_tip").addClass("none").html("");
                	}
                }
            };
            /**商品描述编辑器*/
            goodsAdd.prodSpec.editor;
            //默认isflag:true显示预览
            var isflag = true;

            /*
             * 初始化商品描述编辑器
             */
            KindEditor.ready(function(K) {
                goodsAdd.prodSpec.editor = K.create('#goods_prodDesc', goodsAdd.prodSpec.options);
                //设置工具栏source按钮点击事件
                /*	goodsAdd.prodSpec.editor.clickToolbar('source', function() {
                		var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
                		var imgObj = null;
                		var src = null;
                		var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
                		for (var i = 0, len = imgs.length; i < len; i++) {
                			imgObj = imgs[i];
                			src = imgObj.src;
                			if (!yougou_img_url_reg.test(src)) {
                				if (isflag) { //切换到源码
                					//ygdg.dialog.alert(isflag.toString());
                				} else { //切换预览(显示非法链接图标)
                					imgObj.src = basePath + "/yougou/images/outer_link.png";
                				}
                			}
                		}
                		isflag = !isflag;
                	});*/
            });

            /**
             * 校验商品描述中是否存在外链
             */
            goodsAdd.prodSpec.checkOuterLink = function() {
                var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
                var imgObj = null;
                var src = null;
                var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
                var isCheck = true;
                /*	for (var i = 0, len = imgs.length; i < len; i++) {
                		imgObj = imgs[i];
                		src = imgObj.src;
                		if (!yougou_img_url_reg.test(src)) {
                			if (isflag) { //如果为预览(显示非法链接图标)
                				imgObj.src = basePath + "/yougou/images/outer_link.png";
                				isCheck = false;
                			}
                		}
                	}*/
                //校验area
                var areaHrefs = $("area", $(".ke-edit-iframe")[0].contentWindow.document.body);
                var href = null;
                var yougou_href_url_reg = new RegExp('http://.+\\.yougou.com/.+', 'i');
                var isCheck = true;
                for (var j = 0, len = areaHrefs.length; j < len; j++) {
                    href = areaHrefs[j].href;
                    if (!yougou_href_url_reg.test(href)) {
                        isCheck = false;
                    }
                }
                return isCheck;
            };

            /**
             * 选择商品描述图片按钮 点击事件
             */
            goodsAdd.prodSpec.imgBtn_OnClick = function() {
                openwindow(goodsAdd.url.descImgSelectorUrl, 820, 560, '选择描述图片');
                //error_tip hide
                $('#goods_prodDesc_tip').addClass("none");
            };

            /**
             * 图片选择器 关闭事件
             * @param {String} imgUrl 图片地址
             */
            goodsAdd.prodSpec.imgSelector_OnClose = function(imgUrls) {
                closewindow();
                //update by guoran 20150430
                //layer.closeAll();
                if (imgUrls == null || imgUrls == '') return;
                var msg = '<br/>';
                var urls = imgUrls.split('&&&&&');
                $.each(urls, function(n, url) {
                    msg += formatString('<img src="{#imgUrl}" />', {
                        imgUrl: url
                    });
                    msg += '<br/>';
                });
                goodsAdd.prodSpec.editor.html(goodsAdd.prodSpec.editor.html() + msg);
                $("#goods_prodDesc_tip").addClass("none");
                //goodsAdd.prodSpec.checkOuterLink();
            };


            /**
             * 图片选择器 关闭事件
             * @param {String} imgUrl 图片地址
             */
            function onImgSelected(imgUrl) {
                goodsAdd.prodSpec.imgSelector_OnClose(imgUrl);
            }

                /**
                 * 提交表单
                 * @param {Boolean} isSaveSubmit 是否为保存并提交
                 */
            goodsAdd.submit.submitForm = function(isSaveSubmit) {
            	
            	//alert("submit");
                //是否校验通过
                var isCheck = true;
                var confirmLen = $("input[name='confirm']:checked").length;
                if(confirmLen>0){
                	//同步商品描述编辑器内容
                    goodsAdd.prodSpec.editor.sync();
                    //提交表单前处理
                	if(!goodsAdd.submit.preSubmit()) return;
                    //验证表单
                    if (!goodsAdd.validate.validateForm()) {
                        isCheck = false;
                    };
                    if (isCheck) {
                        goods_submit(isSaveSubmit);
                    }
                }else{
                	ygdg.dialog.alert("请先勾选左边属性列表下方的确认复选框！");
                }
            };

            function goods_submit(isSaveSubmit) {
            	//alert("2");
                if (isSetSizePrice) {
                    if (requestCommodityNo) {
                        $("#commodity_code").val(goodsAdd.commodity.supplierCode);
                    }
                }
                ygdg.dialog({
                    id: "submitDialog",
                    title: '操作提示',
                    content: '<img src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /> 正在提交,请稍候...',
                    lock: true,
                    closable: false
                });

                var $myForm = $("#secondForm");
                var actionUrl = goodsAdd.url.submitUrl;
                //标记是否为保存并提交审核
                if (isSaveSubmit) {
                    if (goodsAdd.url.submitUrl.indexOf("?") == -1) {
                        actionUrl = actionUrl + "?isSaveSubmit=1";
                    } else {
                        actionUrl = actionUrl + "&isSaveSubmit=1";
                    }
                }
                
                $myForm.attr("action", actionUrl);
                $("p[id$='_tip']").addClass("none");
                $myForm.ajaxSubmit({
                    cache: false,
                    dataType: "json",
                    success: goodsAdd.submit.success,
                    error: function(XmlHttpRequest, textStatus, errorThrown) {
                        if ('parsererror' != textStatus) {
                            alert("网络异常，请刷新后重试!");
                        }
                        ygdg.dialog({
                            id: "submitDialog"
                        }).close();
                    }
                });
            }

            /**
             * 商品资料提交响应
             * @param {Object} data
             */
            goodsAdd.submit.success = function(data) {
                ygdg.dialog({
                    id: "submitDialog"
                }).close();
                var errMsg = "商品资料提交失败";
                if (data == null || typeof(data) != "object" ||
                    data.isSuccess == null || typeof(data.isSuccess) == "undefined") {
                    ygdg.dialog.alert(errMsg);
                    return;
                }
                errMsg = data.errorMsg || errMsg;
                //商品资料提交响应(后置处理)
                goodsAdd.submit.successPost(data, errMsg);
            };

            /**
             * 预览
             */
            goodsAdd.submit.preview = function() {
                //同步商品描述编辑器内容
                goodsAdd.prodSpec.editor.sync();
                //提交表单前处理
            	if(!goodsAdd.submit.preSubmit()) return;
                //验证表单
                if (!goodsAdd.validate.validateForm()) return;

                var myForm = document.getElementById("secondForm");
                var actionUrl = "";
                if (goodsAdd.url.submitUrl.indexOf("?") == -1) {
                    actionUrl = goodsAdd.url.submitUrl + "?isPreview=1";
                } else {
                    actionUrl = goodsAdd.url.submitUrl + "&isPreview=1";
                }
                myForm.action = actionUrl;
                myForm.target = "_blank";
                myForm.submit();
                myForm.target = "";
            };

            /**待调用的验证函数列表*/
            goodsAdd.validate.validateFunList = [];

            /** 错误列表 */
            goodsAdd.validate.errorList = new Array();

            /**
             * 验证表单
             * @param {Array} formData 表单域数组
             * @param {Object} jqForm jq form对象
             * @param {Object} options 选项
             */
            goodsAdd.validate.validateForm = function(formData, jqForm, options) {
                var validateFunList = goodsAdd.validate.validateFunList;
                for (var i = 0, len = validateFunList.length; i < len; i++) {
                    eval('goodsAdd.validate[validateFunList[i]]()');
                }
                var errorList = goodsAdd.validate.errorList;
                
//                for(var i =0;i<errorList.length;i++){
//                	
//                	alert("errorList:"+errorList[i].errMsg);
//                }
                if (errorList.length == 0) return true;

                goodsAdd.validate.batchShowErrorList();
                return false;
            };

            /**
             * 一次性展示错误列表
             */
            goodsAdd.validate.batchShowErrorList = function() {
                var errorList = goodsAdd.validate.errorList;
                var error = null;
                var regexp = new RegExp('^(stock_|sku_|weight_|salePriceStr_|publicPriceStr_)');
                for (var int = 0; int < errorList.length; int++) {
                    error = errorList[int];
                    if (goodsAdd.validate.startWith(error.errorFiled, regexp)) {
                        var filed = error.errorFiled.split("_");
                        //定位 goods_filed[1]_filed[0] error block [filed[1]_filed[0]_error_tip]			
                        var obj = $("#"+filed[0] + '_' + filed[1]);
                        obj.addClass("error");
                        obj.attr('title', error.errMsg);
                        obj.blur(function() { //绑定焦点事件
                            var value = $(this).val();
                            if (!isEmpty(value)) {
                                $(this).removeClass('error');
                            }
                        });
                        //针对优购价大于市场价时，修改市场价，优购价依然报红修改
                        if (filed[0] == 'salePriceStr') {
                            obj.parent("td").next("td").children("input").blur(function() {
                                var value = $(this).val();
                                if (!isEmpty(value)) {
                                    $(this).parent("td").prev("td").children("input").removeClass('error');
                                }
                            });
                        }
                        //obj.focus();
                    } else {
                        var error_html = error.errMsg;
                        $('#' + error.errorFiled + '_tip').removeClass("none").html(error_html);
                    }
                }
                if(errorList.length>0){
                	$("#"+errorList[0].errorFiled).focus();
                }
                errorList.length = 0;
            };
            
            
            /**
             * 一次性弹出错误列表，focus
             */
            goodsAdd.validate.batchShowError = function() {
                var errorList = goodsAdd.validate.errorList;
                var error = null;
                var error_html = "";
                var regexp = new RegExp('^(stock_|sku_|weight_)');
                for (var int = 0; int < errorList.length; int++) {
                	error = errorList[int];
                	error_html += error.errMsg+"<br/>";
                	if (goodsAdd.validate.startWith(error.errorFiled, regexp)) {
                		var obj = $("#"+error.errorFiled);
                        obj.addClass("error");
                        obj.attr('title', error.errMsg);
                        obj.blur(function() { //绑定焦点事件
                            var value = $(this).val();
                            if (!isEmpty(value)) {
                                $(this).removeClass('error');
                            }
                        });
                	}else{
                         $('#' + error.errorFiled + '_tip').removeClass("none").html(error.errMsg);
                	}
                }
                ygdg.dialog({
                	title:'错误提示',
                	content:error_html,
                	id:'errortip',
                	icon:'error'
                });
                errorList.length = 0;
            };
            

            /**
             * 验证品牌 
             */
            goodsAdd.validate.validateBrandNo = function() {
                var notNullMsg = '请选择品牌！';
                var $thisObj = $('#secondForm input[name="brandNo"]');
                var thisValue = $.trim($thisObj.val());
                goodsAdd.validate.isEmpty('brandNo', thisValue, notNullMsg);
            };

            /**
             * 验证分类 
             */
            goodsAdd.validate.validateCattegory = function() {
                var notNullMsg = "请选择分类！";
                var $thisObj = $("#secondForm input[name='catStructName']");
                var thisValue = $.trim($thisObj.val());
                goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
            };

            /**
             * 验证商品名称
             */
            goodsAdd.validate.validateCommodityName = function() {
                var notNullMsg = "请填写正确的商品名称！";
                var $thisObj = $("#commodity_name");
                var thisValue = $.trim($thisObj.val());
                goodsAdd.validate.isEmpty('commodity_name', thisValue, notNullMsg);
            };

            /**
             * 验证商品款号
             */
            goodsAdd.validate.validateStyleNo = function() {
                var notNullMsg = "请填写商品款号！";
                var containsChinese = "商品款号不能包含中文";
                var $thisObj = $("#commodity_style");
                var thisValue = $.trim($thisObj.val());
                if (goodsAdd.validate.isEmpty('commodity_style', thisValue, notNullMsg)) return;
                if (!goodsAdd.validate.containsChinese('commodity_style', thisValue, containsChinese)) return;
            };

            /**
             * 验证商家款色编码
             */
            goodsAdd.validate.validateSupplierCode = function() {
                if (!(isSetSizePrice)) {
                    var notNullMsg = "请输入款色编码！";
                    var containsChinese = "商家款色编码只能是数字、字母、横线(-)、下划线、斜杠(/)";
                    var $thisObj = $("#commodity_code");
                    var thisValue = $.trim($thisObj.val());
                    if (goodsAdd.validate.isEmpty('commodity_code', thisValue, notNullMsg)) return;
                    if (goodsAdd.validate.containsChinese('commodity_code', thisValue, containsChinese)) return;
                    if (goodsAdd.validate.allowInput('commodity_code', thisValue, containsChinese)) return;
                }
            };
            /**
             * 验证商家款色编码
             */
            goodsAdd.validate.validateSupplierCode4Update = function() {
                if (!(isSetSizePrice)) {
                    var notNullMsg = "请输入款色编码";
                    var containsChinese = "商家款色编码只能是数字、字母、横线(-)、下划线、斜杠(/)";
                    var $thisObj = $("#commodity_code");
                    var thisValue = $.trim($thisObj.val());
                    if (goodsAdd.validate.isEmpty('commodity_code', thisValue, notNullMsg)) return;
                    //if (goodsAdd.validate.allowInputContainsChinese('supplierCode', thisValue, containsChinese)) return;
                    //if(goodsAdd.validate.containsChinese('supplierCode', thisValue, containsChinese)) return;
                    if(goodsAdd.validate.containsChinese('commodity_code', thisValue, containsChinese)) return;
            		if(goodsAdd.validate.allowInput('commodity_code', thisValue, containsChinese)) return;
                }
            };
            /**
             * 验证优购价格
             */
            goodsAdd.validate.validateSalePrice = function() {
                //true, set price by size
                if (isSetSizePrice) {
                    var notNullMsg = "请输入第 {#num} 行优购价格";
                    var notNumMsg = "第 {#num} 行优购价格请输入数字";
                    var pintZero = "第 {#num} 行优购价小数点后必须为0";
                    var lessThanZeroMsg = "第 {#num} 行优购价格请输入大于0的数字";
                    var salePriceGtPublicPrice = "第 {#num} 行优购价格必须小于第 {#num} 行市场价";
                    var $salePriceTxts = $(".salePriceStr_txt");
                    var salePrice = null;
                    for (var i = 0, len = $salePriceTxts.length; i < len; i++) {
                        salePrice = $salePriceTxts[i];
                        var thisValue = $.trim(salePrice.value);
                        var sizeValue = $(salePrice).attr('sizeValue');
                        var publicPriceValue = $.trim($(".publicPriceStr_txt")[i].value);
                        if (goodsAdd.validate.isEmpty("salePriceStr_"+sizeValue, thisValue, formatString(notNullMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isNotNum("salePriceStr_"+sizeValue, thisValue, formatString(notNumMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isNumberPointZero("salePriceStr_"+sizeValue, thisValue, formatString(pintZero, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isLessThanZero("salePriceStr_"+sizeValue, thisValue, formatString(lessThanZeroMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isFormerGtLatter("salePriceStr_"+sizeValue, thisValue, publicPriceValue, formatString(salePriceGtPublicPrice, {
                                num: (i + 1)
                            }))) continue;
                    }
                    return true;
                } else {
                    var notNullMsg = "请输入优购价格！";
                    var notNumMsg = "优购价格请输入数字";
                    var pintZero = "优购价小数点后必须为0";
                    var lessThanZeroMsg = "优购价格请输入大于0的数字";
                    var $thisObj = $("#commodity_price");
                    var thisValue = $.trim($thisObj.val());
                    if (goodsAdd.validate.isEmpty('commodity_price', thisValue, notNullMsg)) return;
                    if (goodsAdd.validate.isNotNum('commodity_price', thisValue, notNumMsg)) return;
                    if (goodsAdd.validate.isNumberPointZero('commodity_price', thisValue, pintZero)) return;
                    if (goodsAdd.validate.isLessThanZero('commodity_price', thisValue, lessThanZeroMsg)) return;
                }
            };

            /**
             * 验证市场价格
             */
            goodsAdd.validate.validatePublicPrice = function() {
                //true, set price by size
                if (isSetSizePrice) {
                    var notNullMsg = "请输入第 {#num} 行市场价格";
                    var notNumMsg = "第 {#num} 行市场价格请输入数字";
                    var pintZero = "第 {#num} 行市场价小数点后必须为0";
                    var lessThanZeroMsg = "第 {#num} 行市场价格请输入大于0的数字";
                    var $publicPriceTxts = $(".publicPriceStr_txt");
                    var publicPrice = null;
                    for (var i = 0, len = $publicPriceTxts.length; i < len; i++) {
                        publicPrice = $publicPriceTxts[i];
                        var thisValue = $.trim(publicPrice.value);
                        var sizeValue = $(publicPrice).attr('sizeValue');
                        if (goodsAdd.validate.isEmpty('publicPriceStr_'+sizeValue, thisValue, formatString(notNullMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isNotNum('publicPriceStr_'+sizeValue, thisValue, formatString(notNumMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isNumberPointZero('publicPriceStr_'+sizeValue, thisValue, formatString(pintZero, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isLessThanZero('publicPriceStr_'+sizeValue, thisValue, formatString(lessThanZeroMsg, {
                                num: (i + 1)
                            }))) continue;
                    }
                    return true;
                } else {
                    var notNullMsg = "请输入市场价格！";
                    var notNumMsg = "市场价格请输入数字";
                    var pintZero = "市场价小数点后必须为0";
                    var lessThanZeroMsg = "市场价格请输入大于0的数字";
                    var $thisObj = $("#commodity_market_value");
                    var thisValue = $.trim($thisObj.val());
                    if (goodsAdd.validate.isEmpty('commodity_market_value', thisValue, notNullMsg)) return;
                    if (goodsAdd.validate.isNotNum('commodity_market_value', thisValue, notNumMsg)) return;
                    if (goodsAdd.validate.isNumberPointZero('commodity_market_value', thisValue, pintZero)) return;
                    if (goodsAdd.validate.isLessThanZero('commodity_market_value', thisValue, lessThanZeroMsg)) return;
                }
            };


            /**
             * 验证扩展属性是否必选 add by zhuang.rb at 2012-12-11
             */
            goodsAdd.validate.validateGoodsProp = function() {
                $(".goods_name li").each(function() {
                    var $Obj = $(this).find("input[type='hidden']");
                    var valueType = $Obj.val();
                    var isMust = $(this).find(".title em").text().length > 0 ? true : false;
                    if (isMust) {
                        if (valueType == '1') {
                            var notNullMsg = '请选择' + $Obj.attr("propitemname");
                            var checkedInput = $(this).find("input:checked");
                            var thisValue = checkedInput.length > 0 ? checkedInput.eq(0).val() : "";
                            goodsAdd.validate.isEmpty($Obj.attr('propItemNo'), thisValue, notNullMsg);
                        }else{
                        	var $thisObj = $(this).find("select option:checked");
            				var notNullMsg = '请选择' + $Obj.attr("propitemname");
            				var thisValue = $.trim($thisObj.val());
            				goodsAdd.validate.isEmpty($Obj.attr('propitemno'), thisValue, notNullMsg);
                        }
                    }
                });
            };

            /**
             * 验证颜色  
             */
            goodsAdd.validate.validateSpecNo = function() {
            	var Msg = '请选择颜色！';
            	var errMsg = '商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"';
            	var colorObj = $("#colorDiv input[name='color_radio']:checked");
            	if (colorObj.length == 0) {
            		goodsAdd.validate.isEmpty('specName', '', Msg);
            		return;
            	}
            	var regexp = new RegExp('^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$');
            	var thisValue = colorObj.siblings(".color_text").val();
            	goodsAdd.validate.RegExp('specName', thisValue, regexp, errMsg);
            };

            /**
             * 验证尺码 
             */
            goodsAdd.validate.validateSizeNo = function() {
                var errMsg = "请选择尺码！";
                if ($(".size-list input[name='goods_sizeNo']:checked").length == 0) {
                    goodsAdd.validate.isEmpty('goods_sizeNo', '', errMsg);
                }
            };

            /**
             * 验证库存数量
             * modify by huang.tao 2013-05-24
             * 去掉库存限制(库存不输入时默认为0)
             */
            goodsAdd.validate.validteStock = function() {
            	 
                //如果不入优购仓
               // if (!isInputYougouWarehouseFlag) {
                    var notNumMsg = "第 {#num} 行库存数量请输入数字";
                    var lessThanZeroMsg = "第 {#num} 行库存数量请输入的数字必须大于等于0";
                    var notIntMsg = "第 {#num} 行库存数量请输入的数字必须整数";
                    var $stocks = $(".goods_stock_txt");
                    var stock = null;
                    for (var i = 0, len = $stocks.length; i < len; i++) {
                        stock = $stocks[i];
                        var thisValue = $.trim(stock.value);
                        var sizeValue = $(stock).attr('sizeValue');
                        if (thisValue.length != 0) {
                            if (goodsAdd.validate.isNotNum('stock_'+sizeValue, thisValue, formatString(notNumMsg, {
                                    num: (i + 1)
                                }))) continue;
                            if (goodsAdd.validate.isThanZero('stock_'+sizeValue, thisValue, formatString(lessThanZeroMsg, {
                                    num: (i + 1)
                                }))) continue;
                            if (goodsAdd.validate.isNotInt('stock_'+sizeValue, thisValue, formatString(notIntMsg, {
                                    num: (i + 1)
                                }))) continue;
                        }
                    }
               // }
                return true;
            };

            /**
             * 验证货品条码
             */
            goodsAdd.validate.validateThirdPartyCode = function() {
                var emptyMsg = "请输入 第 {#num} 行的货品条码";
                var containsChinese = "第 {#num} 行的货品条码只能包含数字、字母、横线(-)、下划线、斜杠(/)、点(.)";
                var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
                var thirdPartyCode = null;
                for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
                    thirdPartyCode = $thirdPartyCodes[i];
                    var thisValue = $.trim(thirdPartyCode.value);
                    var sizeValue = $(thirdPartyCode).attr('sizeValue');
                    if (goodsAdd.validate.isEmpty("sku_"+sizeValue, thisValue, formatString(emptyMsg, {
                            num: (i + 1)
                        }))) continue;
                    if (goodsAdd.validate.allowInputAndDot("sku_"+sizeValue, thisValue, formatString(containsChinese, {
                            num: (i + 1)
                        }))) continue;
                }
                //校验货品条码是否重复
                goodsAdd.validate.validateThirdPartyCodeRepeat();
            };

            /**
             * 校验货品条码是否重复
             */
            goodsAdd.validate.validateThirdPartyCodeRepeat = function() {
                var isRepeat = false;
                var codeArr = [];
                var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
                if ($thirdPartyCodes.length == 0 || $thirdPartyCodes.length == 1) {
                    return isRepeat;
                }
                var thirdPartyCode = null;
                var sizeValue = null;
                for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
                    thirdPartyCode = $thirdPartyCodes[i];
                    var thisValue = $.trim(thirdPartyCode.value);
                    codeArr.push(thisValue);
                }
                //获取重复的元素
                var repeatArr = getRepeatElements(codeArr);
                if (repeatArr.length > 0) {
                    var msg = "重复的货品条码： ";
                    for (var i = 0, len = repeatArr.length; i < len; i++) {
                        for (var int = 0; int < $thirdPartyCodes.length; int++) {
                            thirdPartyCode = $thirdPartyCodes[int];
                            var thisValue = $.trim(thirdPartyCode.value);
                            sizeValue = $(thirdPartyCode).attr('sizeValue');
                            if (thisValue == repeatArr[i]) {
                                goodsAdd.validate.isEmpty("sku_"+sizeValue, '', msg + repeatArr[i]);
                            }
                        }
                    }

                    isRepeat = true;
                }
                return isRepeat;
            };

            /**
             * 验证重量
             */
            goodsAdd.validate.validateWeight = function() {
                var notNumMsg = "第 {#num} 行重量请输入数字";
                var lessThanZeroMsg = "第 {#num} 行重量请输入的数字必须大于0";
                var notIntMsg = "第 {#num} 行重量请输入的数字必须整数";
                var $weightTxts = $(".goods_weightStr_txt");
                var weight = null;
                for (var i = 0, len = $weightTxts.length; i < len; i++) {
                    weight = $weightTxts[i];
                    var thisValue = $.trim(weight.value);
                    var sizeValue = $(weight).attr('sizeValue');
                    if (thisValue.length != 0) {
                        if (goodsAdd.validate.isNotNum('weight_'+sizeValue, thisValue, formatString(notNumMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isLessThanZero('weight_'+sizeValue, thisValue, formatString(lessThanZeroMsg, {
                                num: (i + 1)
                            }))) continue;
                        if (goodsAdd.validate.isNotInt('weight_'+sizeValue, thisValue, formatString(notIntMsg, {
                                num: (i + 1)
                            }))) continue;
                    }
                }
                return true;
            };


            /**
             * 验证商品描述 
             */
            goodsAdd.validate.validateProdDesc = function() {
                var notNullMsg = "请填写描述！";
                var maxLen = 30000;
                var lenLargerMsg = "商品描述长度不能超过 " + maxLen;
                var $thisObj = $("#goods_prodDesc");
                var thisValue = $.trim($thisObj.val());
                //判断是否为空格和换行符
                var notBlankLineFeedStr = $.trim($thisObj.val().replace(/&nbsp;/g, '').replace(/<br \/>/ig, ''));
                if (goodsAdd.validate.isEmpty('goods_prodDesc', notBlankLineFeedStr, notNullMsg)) return false;
                if (goodsAdd.validate.isLenLarger('goods_prodDesc', thisValue, maxLen, lenLargerMsg)) return false;
                if (!isflag) { //编辑框在代码域时同步数据
                    goodsAdd.prodSpec.editor.clickToolbar('source');
                }
                $("#goods_prodDesc_tip").addClass("none");
                return true;
            };

                //特殊色系
            goodsAdd.anomalyColors = {
                    "藏蓝": "藏蓝",
                    "粉色": "粉色",
                    "褐色": "褐色",
                    "黑蓝": "黑蓝",
                    "橘红": "橘红",
                    "军绿": "军绿",
                    "咖色": "咖色",
                    "卡其色": "卡其色",
                    "玫红": "玫红",
                    "浅蓝": "浅蓝",
                    "浅绿": "浅绿",
                    "色系": "色系",
                    "深红": "深红",
                    "烟灰": "烟灰"
                };
                /*
                 * 保存模板
                 */
            goodsAdd.saveTemplate = function() {
                //校验品牌
                var notNullMsg = '请选择品牌！';
                var $thisObj = $("#secondForm input[name='brandNo']");
                var thisValue = $.trim($thisObj.val());
                if (thisValue == null || typeof(thisValue) == "undefined" || thisValue.length == 0) {
                    ygdg.dialog.alert(notNullMsg);
                    return;
                }

                notNullMsg = "请选择分类！";
                $thisObj = $("#secondForm input[name='catStructName']");
                thisValue = $.trim($thisObj.val());
                if (thisValue == null || typeof(thisValue) == "undefined" || thisValue.length == 0) {
                    ygdg.dialog.alert(notNullMsg);
                    return;
                }

                notNullMsg = "请填写正确的商品名称！";
                $thisObj = $("#commodity_name");
                thisValue = $.trim($thisObj.val());
                if (thisValue == null || typeof(thisValue) == "undefined" || thisValue.length == 0) {
                    ygdg.dialog.alert(notNullMsg);
                    return;
                }
                var propMsg = "";
                $(".goods_name li").each(function() {
                    var $Obj = $(this).find("input[type='hidden']");
                    var valueType = $Obj.val();
                    var isMust = $(this).find(".title em").text().length > 0 ? true : false;
                    if (isMust) {
                        if (valueType == '1') {
                            var notNullMsg = '请选择' + $Obj.attr("propitemname");
                            var checkedInput = $(this).find("input:checked");
                            var thisValue = checkedInput.length > 0 ? checkedInput.eq(0).val() : "";
                            if (thisValue == null || typeof(thisValue) == "undefined" || thisValue.length == 0) {
                                propMsg = propMsg + notNullMsg + "<br>";
                            }
                        }else{
                        	var $thisObj = $(this).find("select option:checked");
            				var notNullMsg = '请选择' + $Obj.attr("propitemname");
            				var thisValue = $.trim($thisObj.val());
            				if(thisValue == null || typeof(thisValue) == "undefined" || thisValue.length == 0) {
            					propMsg = propMsg+notNullMsg+"<br>";
            				}
                        }
                    }
                });

                if (!propMsg == "") {
                    ygdg.dialog.alert(propMsg);
                    return;
                }
                ymc_common.loading("show", "正在保存模板......");
                $.ajax({
                    async: true,
                    cache: false,
                    type: 'POST',
                    dataType: "json",
                    data: $('#secondForm').serialize(),
                    url: basePath + "/commodity/saveTemplate.sc",
                    success: function(data) {
                        ymc_common.loading();
                        if (data.resultCode == "200") {
                            ygdg.dialog.alert("分类属性模板保存成功!<br><a href='javascript:goodsAdd.showTemplate(\"closeWin\")'>查看已保存属性模板</a>");
                        } else {
                            ygdg.dialog.alert(data.msg);
                        }
                    }
                });
            };

            /**
             * 查看属性模板
             */
            goodsAdd.showTemplate = function(type) {
            	showTpl();
            };
            
            var showTpl = function(){
                var catNo = $("#catno").val();
                if(!catNo) return false;
            	var cateNames = $("#catedl").children(".catedd").text();
            	cateNames = encodeURI(encodeURI(cateNames));
            	openwindow("/commodity/itemTpl.sc?cateNames="+cateNames+"&catNo="+catNo+"&from=newui", 800,500, "选择属性模板");
            	return false;
            };

            function closeDialog() {
                //关闭提示框
                var list = ygdgDialog.list,
                    config;
                for (var i in list) {
                    if (list[i]) {
                        config = list[i].config;
                        list[i].close();
                        delete list[i];
                    };
                };
            }
            
            
            /**
             * 确定属性多选值
             * @param propItemNo 属性No
             * @param propItemName 属性名称
             */
            goodsAdd.properties.confirm = function(propItemNo, propItemName) {
            	var propValueNo = '';
            	var propValueName = '';
            	$($("input[name='goods_prop_checkbox_"+propItemNo+"']:checked")).each(
            		function() {
            			var a = this.value.split(";");
            			if (a.length == 2) {
            				propValueNo += a[0] + ";";	propValueName += a[1] + ";";
            			}
            		}
            	);
            	if (propValueNo.length > 0) {
            		if ($("#goods_properties_propItemNo_" + propItemNo).length>0) {
            			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
            			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
            			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo.substring(0, propValueNo.length - 1));
            			$("#goods_properties_propValueName_" + propItemNo).val(propValueName.substring(0, propValueName.length - 1));
            		} else {
            			//首次
            			//添加隐藏域
            			appendHidden("goods_properties_propItemNo_" + propItemNo,
            				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
            			appendHidden("goods_properties_propItemName_" + propItemNo,
            				propItemName, "propItemName", "#goods_selected_properties_propItemName");
            			appendHidden("goods_properties_propValueNo_" + propItemNo,
            				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
            			appendHidden("goods_properties_propValueName_" + propItemNo,
            				propValueName, "propValueName", "#goods_selected_properties_propValue");
            		}
            	} else {
            		$("#goods_properties_propItemNo_" + propItemNo).remove();
            		$("#goods_properties_propItemName_" + propItemNo).remove();
            		$("#goods_properties_propValueNo_" + propItemNo).remove();
            		$("#goods_properties_propValueName_" + propItemNo).remove();
            	}
            };
            

            function useTemplate(id) {
//            	alert("hhhhhhhhhhh");
                ymc_common.loading("show", "正在加载模板...");
                $.ajax({
                    async: true,
                    cache: false,
                    type: 'POST',
                    dataType: "json",
                    url: basePath + "/commodity/useTemplate.sc?id=" + id,
                    success: function(data) {
                        if (data.resultCode == "200") {
                            closeDialog();
                            //初始化所有属性
                            $("#left .shutting_val").find(":checkbox").attr("checked", false);
                            $("#left .shutting_span_val").html("");
                            //$("#left select").reJqSelect();
                            goodsAdd.setAllSelectDefaultValue();
                            $("#goods_selected_properties_propItemNo").empty();
                            $("#goods_selected_properties_propItemName").empty();
                            $("#goods_selected_properties_propValueNo").empty();
                            $("#goods_selected_properties_propValue").empty();
                            var list = data.list;
                            for (var i = 0, _len = list.length; i < _len; i++) {
                                var item = list[i];
                                var propVals = item.propValueNos;
                                for (var j = 0, _sublen = propVals.length; j < _sublen; j++) {
                                    var checkBox = $("#shut" + item.propItemNo + "_" + propVals[j]);
                                    var select = $("#"+item.propItemNo+" option[value='"+propVals[j]+"']");
                                    if (checkBox.length > 0) { //存在多选
                                        if (!checkBox.attr("checked")) {
                                            checkBox.attr("checked", true);
                                            $("#goods_prop_valuetype_"+item.propItemNo).siblings(".shutting_span_val").append(item.propValueName.split(";")[j]+"<br/>");
                                            $("#goods_prop_valuetype_"+item.propItemNo).siblings(".shutting_click").removeClass("show").addClass("hide");
                                            goodsAdd.properties.confirm(item.propItemNo, item.propItemName);
                                        }
                                    }else if(select.length > 0) {//存在单选
                                        goodsAdd.setSelectValue(item.propItemNo,propVals[j]);
                                        $("#"+item.propItemNo).reJqSelect();
                                        goodsAdd.properties.selectChange($("#"+item.propItemNo));
                                    }
                                }
                            }
                            //使用模板为设置的属性值设为点击选择
                            goodsAdd.setNoChekckValue();
                            ymc_common.loading();
                        } else {
                            ygdg.dialog.alert(data.msg);
                        }
                    }
                });
            }
            
            /**
             * 设置下拉框的值
             * @param {String} id 下拉框id
             * @param {String} value 要选中的名称
             */
            goodsAdd.setSelectValue = function(id , value) {
            	var $options = $("#" + id + ">option");
            	var option = null;
            	for (var i = 0, len = $options.length; i < len; i++) {
            		option = $options[i];
            		var optionVal = option.value;
            		if(optionVal != null && optionVal.length >= 1) {
            			if(optionVal == (value)) {
            				document.getElementById(id).selectedIndex = i;
            			}
            		}
            	}
            };
            /**
             * 设置所有下拉框色
             */
            goodsAdd.setAllSelectDefaultValue = function(){
            	var selectList = $("#left select");
            	$.each(selectList,function(i,n){
            		selectList.get(i).selectedIndex = 0;
            		$(n).reJqSelect();
            	});
            };
            
            /**
             * 使用模板未设置的属性值设置为点击选择
             */
            goodsAdd.setNoChekckValue = function(){
//            	alert("333333333333333333333");
            	var valueTypeObjs = $("input[id^='goods_prop_valuetype_'][value='1']");
            	$.each(valueTypeObjs,function(i,n){
            		var htmlStr =$(n).siblings(".shutting_span_val").html();
            		if(!(htmlStr)){
            			$(n).siblings(".shutting_click").addClass('show').removeClass('hide');
            			$(n).siblings('.shutting_edit').removeClass('hover');
            			$(n).siblings('.title').children("p").removeClass("none");
            		}
            	});
            };
            
            /**
             * 选择下拉选框修改事件，用于属性值选择触发
             */
            goodsAdd.properties.selectChange = function(self) {
            	var $self = $(self);
            	var selfValue = $.trim($self.val());
            	var propItemNo = $.trim($self.attr("propitemno"));  //属性编号
            	var propItemName = $.trim($self.attr("propitemname"));  //属性名称
            	var $option = $self.find("option:selected");
            	if(selfValue.length != 0) {  //该属性项有值
            		var propValueNo = selfValue;  //属性值编号
            		var propValueName = $.trim($option.attr("propValueName"));  //属性值名称
            		if($("#goods_properties_propItemNo_" + propItemNo).length > 0) {  //不是首次选择
            			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
            			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
            			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo);
            			$("#goods_properties_propValueName_" + propItemNo).val(propValueName);
            		} else {  //首次选择
            			//添加隐藏域
            			appendHidden("goods_properties_propItemNo_" + propItemNo,
            				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
            			appendHidden("goods_properties_propItemName_" + propItemNo,
            				propItemName, "propItemName", "#goods_selected_properties_propItemName");
            			appendHidden("goods_properties_propValueNo_" + propItemNo,
            				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
            			appendHidden("goods_properties_propValueName_" + propItemNo,
            				propValueName, "propValueName", "#goods_selected_properties_propValue");
            		}
            	} else {  //选择了“请选择”
            		$("#goods_properties_propItemNo_" + propItemNo).remove();
            		$("#goods_properties_propItemName_" + propItemNo).remove();
            		$("#goods_properties_propValueNo_" + propItemNo).remove();
            		$("#goods_properties_propValueName_" + propItemNo).remove();
            	}
            	//error_tip hide
            	$('#' + propItemNo + '_tip').addClass("none");
            };
