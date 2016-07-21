var imageManageUpload = {};
var curPage = 1;
$(function() {
        //left的,编辑的hover
        $(".goods_class").live("mouseover",function(){
        	var siblingselect=$(this).siblings().children("select");
            if(!siblingselect.attr('show')){
                $(this).addClass('hover');
            }
        }).live("mouseout",function(){
        	$(this).removeClass('hover');
        });
        
        $("#product_release").click(function(){
            	if($(this).attr("checked")){
            		$("#alikegoods").attr("checked",true);
            	}else{
            		$("#alikegoods").attr("checked",false);
            	}
            });
        
        
        $("#alikegoods").click(function(){
        	if($(this).attr("checked")){
        		$("#product_release").attr("checked",true);
        	}else{
        		$("#product_release").attr("checked",false);
        	}
        });

        //编辑
        $('.edit').live('click', function() {
            $(this).siblings('.goods_span_val').html("");
            $(this).parent(".goods_class").siblings("div").show();
            $(this).parent(".goods_class").siblings("div").children("select").attr("show",1);
        });

        //闭合式
        $('.btn_yes').live('click', function(event) {
            var arr = [],val,str,
                _checked_val = $(this).parents('.shutting').find($('input[class="shut"]:checked'));
            $.each(_checked_val, function(index) {
                val = _checked_val.eq(index).val();
                val = val.split(";")[1];
                arr.push(val);
            });
            str = arr.join("<br/>");
            if (!str) {
                $(this).parents('.shutting').removeClass('hover');
                $(this).parents('.shutting').find('.shutting_click').addClass('show').removeClass('hide');
                $(this).parents('.shutting').find('.shutting_edit').removeClass('hover');
                $(this).parents('.shutting').children('.title').children("p").removeClass("none");
            }else{
            	$(this).parents('.shutting').children('.title').children("p").addClass("none");
            }
            $(this).parents('.shutting').find('.shutting_span_val').html(str);
            $(this).parents('.shutting').find('.shutting_val').removeClass('show');
            /* Act on the event */
        });
        function disabledOther(){
        	$('.shutting').removeClass('hover');
            $('.shutting_val').removeClass('show').siblings('.shutting_edit').removeClass('hover');
            $('.btn_yes').click();
        }
        //点击选择
        $('.shutting_click').live('click', function(event) {
        	disabledOther();
            $(this).parent().addClass('hover');
            $(this).siblings('.shutting_val').addClass('show');
            $(this).addClass('hide').removeClass('show');
        });
        
        $(".shutting").live("mouseover",function(){
        	if ($(this).find('.shutting_click').hasClass('hide')) {
                $(this).addClass('hover');
                $(this).find('.shutting_edit').addClass('hover');
            }
        }).live("mouseout",function(){
        	if ($(this).find('.shutting_click').hasClass('hide')) {
                //$(this).removeClass('hover');
                $(this).find('.shutting_edit').removeClass('hover');
            if ($(this).find('.shutting_val').hasClass('show')) {
                $(this).addClass('hover');
            } else {
                $(this).removeClass('hover');
            }
        }});
        
        //点击编辑
        $('.shutting_edit').live('click', function(event) {
        	disabledOther();
            $(this).siblings('.shutting_val').addClass('show');
            $(this).siblings('.shutting_span_val').html("");
        });

        //main的js开始

        //屏幕滚动固定main的title
        $(window).scroll(function(event) {
            var offsettop = parseInt($('.fix_title').offset().top),
                scrolltop = parseInt($(window).scrollTop()),
                opposite = scrolltop - offsettop;
            if (scrolltop >= 132) {
                $('.fix_title').addClass('fixed');
            } else {
                $('.fix_title').removeClass('fixed');
            }
        });


        //颜色库
        /*input text*/
        $('.color_text').live("click",function() {
        	$(this).siblings('.color_radio').click();
            return false;      
        });
        
        //tab
        $('.tab_title').show();

        //file文件
        $('.color_file').click(function(event) {
            $(this).siblings('.color_path').click();
            return false;
        });

        //hover事件显示隐藏左右移动图标
        $('.pm-item').hover(function() {
        	 $(this).addClass('hover');
        }, function() {
        	$(this).removeClass('hover');
        });
        
        //右移动图片
        $('.pic-manager2 .oper_lr').live('click', function() {
        	var my_img = $(this).parent().children(".pm-box").html(),
                next_img = $(this).parent().next().children(".pm-box").html();
                if(next_img){
                	$(this).parent().children(".pm-box").html(next_img);
                	$(this).parent().next().children(".pm-box").html(my_img);
	                var srcInput=$(this).parent().children("input[type='hidden']").val();
	                var nextInput=$(this).parent().next().children("input[type='hidden']").val();
	                if(srcInput=='0'){
	                	if(my_img.indexOf("img")!=-1){
	                		my_img = $(this).parent().next().children(".pm-box").find("img").attr("src");
	                	}
	                	srcInput=my_img.replace("_ms.jpg","_l.jpg");
	                }
	            	if(nextInput=='0'){
	            		if(next_img.indexOf("img")!=-1){
	            			next_img = $(this).parent().children(".pm-box").find("img").attr("src");
	            		}
	                	nextInput=next_img.replace("_ms.jpg","_l.jpg");
	                }
	                $(this).parent().children("input[type='hidden']").val(nextInput);
	                $(this).parent().next().children("input[type='hidden']").val(srcInput);
	                setPmboxText();
                }
            });
            //左移动图片
        $('.pic-manager2 .oper_lf').live('click', function() {
                var my_img = $(this).parent().children(".pm-box").html(),
                        pre_img = $(this).parent().prev().children(".pm-box").html();
                    if(pre_img){
                    	$(this).parent().children(".pm-box").html(pre_img);
                    	$(this).parent().prev().children(".pm-box").html(my_img);
    	                var srcInput=$(this).parent().children("input[type='hidden']").val();
    	                var preInput=$(this).parent().prev().children("input[type='hidden']").val();
    	                if(srcInput=='0'){
    	                	if(my_img.indexOf("img")!=-1){
    	                		my_img = $(this).parent().prev().children(".pm-box").find("img").attr("src");
    	                	}
    	                	srcInput=my_img.replace("_ms.jpg","_l.jpg");
    	                }
    	            	if(preInput=='0'){
    	            		if(pre_img.indexOf("img")!=-1){
    	            			pre_img = $(this).parent().children(".pm-box").find("img").attr("src");
    	            		}
    	                	preInput=pre_img.replace("_ms.jpg","_l.jpg");
    	                }
    	                $(this).parent().children("input[type='hidden']").val(preInput);
    	                $(this).parent().prev().children("input[type='hidden']").val(srcInput);
    	                setPmboxText();
                    }
            });
            //upfile
        
        //删除图片
        $(".pic-manager2 .oper_dt").live("click",function(){
        		var li = $(this).parent();
        		if($(this).attr("num")=="6"&&
        				li.find("input[type='hidden']").val()!='-1'&&
        				li.next(".pm-item").find("input[type='hidden']").val()!='-1'){
        				ygdg.dialog.alert("请先删除第7张图");
        				return;
        		}
             li.find(".pm-box").html("描述图");
             li.find("input[name='imgFileId']").val("-1");
         });

        //点击删除
        $('.color_del').click(function() {
                $(this).hide();
                $(this).siblings('a').find('.color_img').hide();
                $(this).siblings('.color_redel').show();
            });
            //恢复删除
        $('.color_redel').click(function(event) {
            $(this).hide();
            $(this).siblings('a').find('.color_img').show();
            $(this).siblings('.color_del').show();

        });
        
        // create by guoran 20150428
        // 颜色选择
        $('.color_radio').live('click', function() {
            var $this = $(this),
                color = $this.val(),
                selects = [],
                selectsLength = 0,
                sizes = [],
                size_body = $('.size_tab tbody'),
                _text = $this.siblings('.color_text').val();
            	$(this).parent().addClass('checked').siblings().removeClass('checked');//选中可以修改名称
            $('tr', size_body).each(function(i, item) {
                selects.push($(item).find('[data-id]').data('id') + '');
            });
            selectsLength = selects.length;
            $('.size-list :checked').each(function(i, item) {
                var strcontent = '<tr id="goods_product_tr_'+item.value+'">';
                if (selectsLength < 1 && i == 0) {
                    strcontent += '<td rowspan="1">' + _text + '</td>';
                }
                strcontent += '<td data-id="' + item.value + '">' + 
                			$(item).attr("sizename") + '</td><td class="sizePrice" style="display: none;">'+
                			'<input name="salePriceBySize" id="salePriceStr_'+item.value+'" sizeValue="'+item.value+'" maxlength="10" type="text" class="salePriceStr_txt"></td>'+
                			'<td class="sizePrice" style="display: none;"><input name="publicPriceBySize" id="publicPriceStr_'+item.value+'" sizeValue="'+item.value+'" maxlength="10" type="text" class="publicPriceStr_txt"></td>'+
                			'<td><input id="sku_' + item.value + '" name="thirdPartyCode" class="goods_thirdPartyCode_txt" sizeValue="'+item.value+'" type="text" /></td><td>'+
                			'<input name="stock" class="goods_stock_txt" sizeValue="'+item.value+'" id="stock_' + item.value + 
                			'" type="text" /></td><td><input name="weightStr" sizeValue="'+item.value+'" class="goods_weightStr_txt" id="weight_' + 
                			item.value + '" type="text" /></td></tr>';
                if ($.inArray(item.value, selects) < 0) {
                    sizes.push(strcontent);
                }
            });
            size_body.append($(sizes.join('')));
            $('tr:first', size_body).find('td:first').attr('rowspan', $('tr', size_body).size()).text(_text);
            $('td:first', size_body).data('color', color);
            if(isSetSizePrice){
            	$(".sizePrice").show();
            }else{
            	$(".sizePrice").hide();
            }
            showtable(size_body);
            $("#specName_tip").addClass("none");
            $("#goods_specName").val($this.siblings(".color_text").val());
        });
        
        $('.size-list input[name="goods_sizeNo"]').live('click', function() {
            var $this = $(this),
                size_body = $('.size_tab tbody'),
                tr = $('tr:first', size_body),
                //rowcount = parseInt($('td:first', tr).attr('rowspan') ? $('td:first', tr).attr('rowspan') : 0),
                rowcount=$('tr', size_body).length,
                strcontent = '<tr id="goods_product_tr_'+$this.val()+'">',
                color = $('.color_radio:checked');
            if ($this.prop('checked')) {
            	//按尺码设置价格修改尺码的点击事件
            	if(requestCommodityNo && isSetSizePrice){
            		if($("#goods_color_size_tbody").children("tr").length>0){
            			//保存删除前的设置
            			var idstr = $("#goods_color_size_tbody").children("tr:eq(0)").attr("id");
            			var preSizeNo = idstr.substr(idstr.lastIndexOf("_")+1);
            			//var pre = $("#goods_sizeNo_display_" + preSizeNo);
            			var saleVal = $("#salePriceStr_"+preSizeNo).val();
            			var publicVal = $("#publicPriceStr_"+preSizeNo).val();
            			var stockVal = $("#stock_"+preSizeNo).val();
            			var weightVal = $("#weight_"+preSizeNo).val();
            			//删除一行货品信息，除货品条码，其余赋值。
            			deleteTableTr(preSizeNo,size_body,rowcount,color,tr);
            			addTableTr($this,size_body,rowcount,color,tr,strcontent);
            			//goodsAdd.prodInfo.removeTableLine(preSizeNo, pre[0], colorName);
            			//goodsAdd.prodInfo.addTableLine(sizeValue, sizeName, colorName);
            			$("#salePriceStr_"+$this.val()).val(saleVal);
            			$("#publicPriceStr_"+$this.val()).val(publicVal);
            			$("#stock_"+$this.val()).val(stockVal);
            			$("#weight_"+$this.val()).val(weightVal);
            			if($this.val()==goodsAdd.commodity.originSize[0].sizeNo){
            				$("#sku_"+$this.val()).val(goodsAdd.commodity.originSize[0].thirdPartyInsideCode);
            			}
            		}else{
            			addTableTr($this,size_body,rowcount,color,tr,strcontent);
            		}
            	}else{
            		/*if (color.size() > 0 && $('[data-id="' + $this.val() + '"]', size_body).size() < 1) {
                        if ($('tr', size_body).size() < 1) {
                            strcontent += '<td rowspan="1">' + color.siblings('.color_text').val() + '</td>';
                        }
                        strcontent += '<td data-id="' + $this.val() + '">' + $this.attr("sizename") + 
                        '</td><td class="sizePrice" style="display: none;"><input name="salePriceBySize" id="salePriceStr_'+$this.val()+'" sizeValue="'+$this.val()+'" maxlength="10" type="text" class="salePriceStr_txt"></td>'+
                        '<td class="sizePrice" style="display: none;"><input name="publicPriceBySize" id="publicPriceStr_'+$this.val()+'" sizeValue="'+$this.val()+'" maxlength="10" type="text" class="publicPriceStr_txt"></td>'+
                        '<td><input name="thirdPartyCode" class="goods_thirdPartyCode_txt" sizeValue="'+$this.val()+'" id="sku_' + $this.val() + '" type="text" />'+
                        '</td><td><input name="stock" class="goods_stock_txt" sizeValue="'+$this.val()+'" id="stock_' + $this.val() + '" type="text" /></td>'+
                        '<td><input name="weightStr" class="goods_weightStr_txt" id="weight_' + $this.val() + '" sizeValue="'+$this.val()+'" type="text" /></td></tr>';
                        size_body.append($(strcontent));
                        $('td:first', tr).attr('rowspan', rowcount + 1);
                    }*/
            		addTableTr($this,size_body,rowcount,color,tr,strcontent);
            	}
            	//尺码编号隐藏域Html
            	var sizeNoHiddenHtml = formatString(
            		'<input type="hidden" id="goods_sizeNo_{#sizeValue}" name="sizeNo" value="{#sizeValue}"/>', 
            		{
            			sizeValue: $this.val()
            		});
            	$("#goods_selected_sizeNo_layer").append(sizeNoHiddenHtml);
                //尺码名称隐藏域Html
            	var sizeNameHiddenHtml = formatString(
            		'<input type="hidden" id="goods_sizeName_{#sizeValue}" name="sizeName" value="{#sizeName}"/>', 
            		{
            			sizeName: $this.attr("sizename"),
            			sizeValue: $this.val()
            		});
            	
            	$("#goods_selected_sizeName_layer").append(sizeNameHiddenHtml);
            } else {
            	//尺码点击事件 预处理
            	if(!goodsAdd.size.pre_size_OnClick($this, $this.val(), $this.attr("sizename"), color.siblings('.color_text').val())) return;
            	deleteTableTr($this.val(),size_body,rowcount,color,tr);
            	
            	
                var currtr = $('td[data-id="' + $this.val() + '"]', size_body).closest('tr'),
                    currindex = currtr.index();
                currtr.remove();
                if (currindex == 0) {
                    $('tr:first', size_body).prepend('<td rowspan="' + (rowcount - 1) + '">' + color.siblings('.color_text').val() + '</td>');
                } else {
                    $('td:first', tr).attr('rowspan', rowcount - 1);
                }
                $("#goods_sizeName_"+$this.val()).remove();
                $("#goods_sizeNo_"+$this.val()).remove();
            }
            $('td:first', size_body).data('color', color.val());
            if(isSetSizePrice){
            	$(".sizePrice").show();
            }else{
            	$(".sizePrice").hide();
            }
            showtable(size_body);
            $("#sizeNo_tip").addClass("none");
        });

        function showtable(cont) {
            if ($('tr', cont).size() > 0) {
                $('.size_tab').show();
            }
            if ($('tr', cont).size() < 1) {
                $('.size_tab').hide();
            }
        }
        
        //图片描叙
        //表格切换
        $('.des_tab').find('li').click(function() {
            $(this).addClass('curr').siblings().removeClass('curr');
        });

        //tree
        getCatalogTree();

        $("#addCatalog").live("click", {
            isParent: true
        }, addCatalog);
        
        $("#reSel").bind("click.lyx", function(){
        	ygdg.dialog.confirm("更换分类或品牌后，部分商品信息将丢失。",function(){
        		$("#firstForm").submit();
        	});
    	});
        $("#showTpl").bind("click.lyx", showTpl);
        
        $(".timeMarkLabel a").live("click",function(){
    		$(".timeMarkLabel a").removeClass("curr");
    		$(this).addClass("curr");
    		$("#innerPicName").val("");
    		loadData(1);
    	});
    	$("#all_check_f").attr("disabled",false);
    	$("#all_check_f").attr("checked",false);
    	
        $('.color_text').live('change', function() {
            var $this = $(this),
                rd = $this.siblings('.color_radio').prop('checked');
            if (rd) {
                $('.size_tab tbody td:first').html($(this).val());
                $('#goods_specName').val($(this).val());
            }
        });
    });

function setPmboxText() {
    $('.pm-item').find('.pm-box').each(function(i, item) {
        if (!$('img', item)[0]) {
            if ($(item).hasClass('main')) {
                $(item).html('主图');
            } else {
                $(item).html('描述图');
            }
        }
    });
}

function deleteTableTr(sizeValue,size_body,rowcount,color,tr){
	var currtr = $('td[data-id="' + sizeValue + '"]', size_body).closest('tr'),
    currindex = currtr.index();
	currtr.remove();
	if (currindex == 0) {
	    $('tr:first', size_body).prepend('<td rowspan="' + (rowcount - 1) + '">' + color.siblings('.color_text').val() + '</td>');
	} else {
	    $('td:first', tr).attr('rowspan', rowcount - 1);
	}
	$("#goods_sizeName_"+sizeValue).remove();
	$("#goods_sizeNo_"+sizeValue).remove();
}

function addTableTr($this,size_body,rowcount,color,tr,strcontent){
	
	if ($('tr', size_body).size() < 1) {
        strcontent += '<td rowspan="1">' + color.siblings('.color_text').val() + '</td>';
    }
    strcontent += '<td data-id="' + $this.val() + '">' + $this.attr("sizename") + 
    '<input name="size_'+$this.val()+'" value="'+$this.val()+'" type="text">'+
    '</td><td class="sizePrice" style="display: none;"><input name="salePriceBySize" id="salePriceStr_'+$this.val()+'" sizeValue="'+$this.val()+'" maxlength="10" type="text" class="salePriceStr_txt"></td>'+
    '<td class="sizePrice" style="display: none;"><input name="publicPriceBySize" id="publicPriceStr_'+$this.val()+'" sizeValue="'+$this.val()+'" maxlength="10" type="text" class="publicPriceStr_txt"></td>'+
    '<td><input name="goods_thirdPartyCode_'+$this.val()+'" class="goods_thirdPartyCode_txt" sizeValue="'+$this.val()+'" id="sku_' + $this.val() + '" type="text" />'+
    '</td><td><input name="stock_'+$this.val()+'" class="goods_stock_txt" sizeValue="'+$this.val()+'" id="goods_stock_' + $this.val() + '" type="text" /></td>'+
    '<td><input name="weightStr" class="goods_weightStr_txt" id="weight_' + $this.val() + '" sizeValue="'+$this.val()+'" type="text" /></td></tr>';
    size_body.append($(strcontent));
    $('td:first', tr).attr('rowspan', rowcount + 1);
}

//添加目录
function timestamp() {
    var timestamp = Date.parse(new Date());
    return timestamp + parseInt(Math.random() * (1000));
}
var newCount = 1;
function addCatalog(e) {
    var zTree = $.fn.zTree.getZTreeObj("ztree");
    var isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        if (treeNode.level < 2) {
            var shopId = treeNode.shopId;
            treeNode = zTree.addNodes(treeNode, {
                id: timestamp(),
                pId: treeNode.id,
                isParent: false,
                name: "目录" + (newCount++),
                shopId: shopId
            });
        } else {
            ygdg.dialog.alert("目录不能超过三级!", {
                shift: false
            });
        }
    } else {
        ygdg.dialog.alert("请先选定父目录!", {
            shift: false
        });
    }
    if (treeNode) {
        editOrSave = 1;
        zTree.editName(treeNode[0]);
    }
}
var setting = {
    data: {
        keep: {
            parent: true
        },
        simpleData: {
            enable: true
        }
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    view: {
        selectedMulti: false
    }
    ,
    callback: {
    	onRename:onRename,
    	beforeRemove: beforeRemove,
    	beforeRename: beforeRename,
    	onRemove: onRemove,
    	onClick:treeOnClick
    }
};

function onRemove(e, treeId, treeNode) {
	var params = {'id':treeNode.id};
	$.ajax({
		url: basePath+'/picture/delPicCatalog.sc',
		async: true,
 		type: "POST",			
		data: params,
		dataType: "json",
		success: function(data){
			if(typeof(data) != "undefined" && data != null && data['result'] == true){
                ygdg.dialog.alert("目录删除成功!");
                treeOnClick(e, treeId, treeNode.getParentNode(),null);
			}else{
				ygdg.dialog.alert("目录删除失败,请联系技术支持!");
			}
		},
		error:function(xhr, textStatus, errorThrown){ 
			ygdg.dialog.alert("服务器错误,请稍后再试!");
			return;
		}
	});
}

var editOrSave=0;
var oldname;
function beforeRename(treeId, treeNode, newName) {
	newName = newName.trim();
    oldname=treeNode.name;
	if (newName.length == 0) {
		ygdg.dialog.alert("目录名称不能为空!");
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		zTree.cancelEditName(treeNode.name);
		return false;
	}
	return true;
}

function beforeRemove(treeId, treeNode) {
	return confirm("确认删除 目录 -- " + treeNode.name + " 吗?");
}

function onRename(e,treeId, treeNode,isCancel) {
	if(editOrSave==1){
		//添加
		var params = {'id':treeNode.id,'catalogName':treeNode.name.trim(),'parentId':treeNode.pId,'shopId':treeNode.shopId};
		$.ajax({
			url: basePath+'/picture/savePicCatalog.sc',
			async: true,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data['result'] == true){
                    ygdg.dialog.alert("目录添加成功!");
				}else{
					ygdg.dialog.alert("目录添加失败,请联系技术支持!");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				ygdg.dialog.alert("服务器错误,请稍后再试!");
				return;
			}
		});
	}else if(editOrSave==2&&oldname!=treeNode.name){
		//更新
		var params = {'id':treeNode.id,'catalogName':treeNode.name.trim()};
		$.ajax({
			url: basePath+'/picture/updatePicCatalog.sc',
			async: true,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data['result'] == true){
                    ygdg.dialog.alert("目录修改成功!");
				}else{
					ygdg.dialog.alert("目录修改失败,请联系技术支持!");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				ygdg.dialog.alert("服务器错误,请稍后再试!");
				return;
			}
		});
	}
	editOrSave=0;
}

function treeOnClick(e, treeId, treeNode,clickFlag) {
	if($("#image-tab").find("li").eq(0).attr("class")=="curr"){
	    imageManageUpload.catalogId = treeNode.id;
		return;
	}else{
		imageManageUpload.catalogId = treeNode.id;
		loadData(1);
	}
    var form=$('#queryForm');

    var shopId = $('<input type="hidden" name="shopId" />');
    shopId.attr('value', treeNode.shopId);

    form.append(shopId);
    var catalogId = $('<input type="hidden" name="catalogId" />');
    catalogId.attr('value', treeNode.id);
    form.append(catalogId);
    
    var treelevel = $('<input type="hidden" name="treelevel" />');
    treelevel.attr('value', treeNode.level);
    form.append(treelevel);
	form.submit();
}

var getCatalogTree = function() {
    $.ajax({
        async: true,
        cache: false,
        type: 'POST',
        dataType: "json",
        data: {
            nickId: $("#nickId").val()
        },
        url: basePath+"/picture/loadPicCatalog.sc",
        success: function(data) {
            if (data.resultCode == "200") {
                var zNodes = [];
                var treeModes = data.treeModes;
                for (var i = 0, _len = treeModes.length; i < _len; i++) {
                    var node = {
                        id: treeModes[i].id,
                        pId: treeModes[i].parentId,
                        name: treeModes[i].catalogName,
                        shopId: treeModes[i].shopId,
                        open: true
                    };
                    zNodes.push(node);
                }
                var treeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
                var node = treeObj.getNodeByParam("id", "0");
                treeObj.selectNode(node, false);
                imageManageUpload.catalogId = "0";
            } else {
            	ygdg.dialog.alert("加载分类异常!");
            }
        }
    });
    //商品描叙
    //选项卡
    $('#image-tab').find('li').click(function(event) {
        var num = $(this).attr("num");
        $(this).addClass('curr').siblings().removeClass('curr');
        if (num == 0) {
            $('.image-upload').show();
            $('.image-select').hide();
        } else {
            $('.image-upload').hide();
            $('.image-select').show();
            loadData(1);
        }
    });
    
    //收起、展开
    $('#open-bottom').click(function(event) {
        $('.image-con').hide();
        $('#image-tab').hide();
        $('#open-top').addClass('open').removeClass('close').html("展开");
        $(this).parent().hide();
    });
    $('#open-top').click(function() {
        var open_top_class = $(this).attr("class"),
            image_con = $('.image-con'),
            image_tab = $('#image-tab'),
            open_bottom = $('.open-bottom');
        if (open_top_class == 'open') {
            image_con.show();
            image_tab.show();
            open_bottom.show();
            $(this).addClass('close').removeClass('open').html("收起");

        } else {
            image_con.hide();
            image_tab.hide();
            open_bottom.hide();
            $(this).addClass('open').removeClass('close').html("展开");
        }
    });
};

//全选
$("#all_check_f").click(function() {
	var images = $(".goodsPicsList li p.img");
	if($("#all_check_f").attr("checked")){
		images.find("span.check").removeClass("nocheck")
		images.find("span.check").addClass("checked");
	}else{
		images.find("span.check").removeClass("checked")
		images.find("span.check").addClass("nocheck");
	}
	
});

    //批量删除删除
$('.del_f').click(function() {
	var images = $(".goodsPicsList li p.img");
	var ids = [];
	images.each(function(index,item){
		if($(this).find("span.checked").length>0){
  			ids.push($(this).attr("idIndex"));
  		}
	});
	if (ids.length <= 0) {
		ygdg.dialog.alert('请先选择需要删除的图片!');
		return;
	}
	deletePic(ids);
    });

//删除图片
function deletePic(ids) {
	ygdg.dialog.confirm('确定删除' + ($.isArray(ids) ? '选择的' : '该') + '图片吗？', function(){
		$.ajax({
			type: 'post',
			url: '/commodity/pics/delete.sc',
			dataType: 'text',
			data: 'ids=' + ids + '&rand=' + Math.random(),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if (data == "success") {
					ygdg.dialog.alert('删除图片成功!');
					 loadData(curPage);
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				ygdg.dialog.alert('删除图片失败!');
			}
		});
	});
}

function loadData(pageNo){
	curPage = pageNo;
	var timeMark  = getTimeMark();
	var orderBy = getOrderBy();
	var data = {};
	data.page  = pageNo;;
	data.page = pageNo;
	data.timeMark = timeMark==""?"-1":timeMark;
	data.createdStart = $("#startTime").val();
	data.createdEnd = $("#endTime").val();
	data.srcPicName = $("#innerPicName").val().trim();
	data.catalogId = imageManageUpload.catalogId;
	if(orderBy!=null){
		data.orderBy =orderBy;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:data,
		url : basePath+"/picture/loadPiclist.sc",
		success : function(data) {
			$("#pic-list").html(data);
		}
	});
};

function getTimeMark(){
	var timeMark= "";
	var active = $("div.timeMarkLabel a.curr");
	if(active.length>0){
		timeMark = active.attr("num");
	}
	return timeMark;
}

function getOrderBy(){
	var orderBy=  $("input[name='orderBy']:checked").val();
	return orderBy;
}

function searchData(){
	$(".timeMarkLabel a").removeClass("curr");
	$(".timeMarkLabel a").eq(3).addClass("curr");
	loadData(1);
}

function editCatalog() {
        var zTree = $.fn.zTree.getZTreeObj("ztree"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0];
        if (nodes.length == 0) {
            ygdg.dialog.alert("请先选择要编辑的目录!", {
                shift: false //去掉动画
            });
            return;
        }
        editOrSave = 2;
        zTree.editName(treeNode);
    }
    //删除目录
function delCatalog() {
    var zTree = $.fn.zTree.getZTreeObj("ztree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        ygdg.dialog.alert("请先选择一个目录!", {
            shift: false //去掉动画
        });
        return;
    }
    if (treeNode.level == 0 && (treeNode.id == '0' || treeNode.id == treeNode.shopId)) {
        ygdg.dialog.alert("不允许删除受保护跟目录!", {
            shift: false //去掉动画
        });
    } else {
        zTree.removeNode(treeNode, true);
    }
}
$("#starttime").calendar({
    maxDate: '#endtime'
});
$("#endtime").calendar({
    minDate: '#starttime'
});

function insertImage(urlStrs, obj) {
    if (urlStrs == null) {
        urlStrs = "";
        var images = $(".upimgs li .img");
        images.each(function(index, item) {
            var checkSpan = $(this).find("span.checked");
            if (checkSpan.length > 0) {
                checkSpan.removeClass("checked");
                checkSpan.addClass("nocheck");
                if (urlStrs == '') {
                    urlStrs += $(this).attr("imgSrc");
                } else {
                    urlStrs += '&&&&&' + $(this).attr("imgSrc");
                }
            }
        });
        if (urlStrs == "") {
            ygdg.dialog.alert('请选择要插入的图片');
            return;
        }
    } else {
        var pimg = $(obj).parent().parent().find("p.img");
        var checkSpan = pimg.find("span.checked");
        if (checkSpan.length > 0) {
            checkSpan.remove();
        }
    }
    onImgSelected.call(this, urlStrs);
}

/*$(window).on('mousedown', function(e){
	if(e.target.id == 'ztree2' || $(e.target).parents('#ztree2')[0]){
		debugger;
		if($('#'+e.target.id+'[treenode_a]')[0] || $(e.target).parents('#'+e.target.id+'[treenode_a]')[0]){
			$('#ztree2').hide();
		} 
	}else{
		$('#ztree2').hide();
	}
})*/


$('[name=setting]').live('click', function(e) {
    if (e.target.id == 'rdenactmen') {
        if ($(this).prop('checked')) {
            $('#enactmen').attr('disabled', false);
        } else {
            $('#enactmen').attr('disabled', true);
        }
    } else {
        $('#enactmen').attr('disabled', true);
    }
});
