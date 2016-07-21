var curPage = 1; 
function loadData(pageNo,showError){
	commondity.setCatStructname();
	curPage = pageNo;
	$("#content_list").html('<table class="list_table"><thead>'+
		'<tr><th width="5%"></th><th width="40%">商品名称</th><th width="10%">款色编码</th><th width="8%">优购价</th><th width="15%">品牌</th><th width="7%">状态</th><th width="20%">所属分类</th></tr>'+
		'<tbody id="tbody"><tr><td colspan="10">正在载入......<td></tr></tbody>'+
		'<table>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : global.base.baseRoot+"/mctfss/commodity/getList.sc?page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}
//设置分类信息
commondity.setCatStructname = function(){
	if($("#cate3").val()!=""){
		$("#catStructname").val($("#cate3").val());
		return;
	}
	if($("#cate3").val()==""&&$("#cate2").val()!=""){
		$("#catStructname").val($("#cate2").val());
		return;
	}
	if($("#cate3").val()==""&&$("#cate2").val()==""){
		$("#catStructname").val($("#cate1").val());
		return;
	}
}
commondity.search = function(){
	loadData(curPage);
}
$(function(){
	loadData(curPage);
	 commondity.loadCate(1,"");
	 $("#selectStoreId").change(function(){
		 commondity.loadBrand($(this).val());
	 });
	 
	$("#brandId").change(function(){
		 commondity.loadCate(1,"");
	});
	$("#cate1").change(function(){
		 commondity.loadCate(2,$("#cate1").find("option:selected").attr("cateId"));
	});
	$("#cate2").change(function(){
		 commondity.loadCate(3,$("#cate2").find("option:selected").attr("cateId"));
	});
})
commondity.loadBrand = function(storeId){
	$("#brandId").children().remove();
	if(storeId!=""){
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : global.base.baseRoot+"/mctfss/commodity/selBrands.sc?storeId="+storeId,
			success : function(data) {
				var brands = data.data;
				for(var i=0,_len=brands.length;i<_len;i++){
					$("<option value='"+brands[i].brandNo+"' brandid='"+brands[i].id+"'>"+brands[i].brandName+"</option>").appendTo("#brandId");
				}
				$("#brandId").change().reJqSelect();
			}
		});
	}
}
commondity.loadCate = function(cateLevel,cateId){
	var storeId = $("#selectStoreId").val();
	var brandId  = $("#brandId").find("option:selected").attr("brandid");
	if(cateLevel!=1&&(cateId==""||cateId==null)){
		return;
	}
	if(cateLevel==1){
		$("#cate1").children().remove();
		$("<option value=''>一级分类</option>").appendTo("#cate1");
		$("#cate1").change().reJqSelect();
		
		$("#cate2").children().remove();
		$("<option value=''>二级分类</option>").appendTo("#cate2");
		$("#cate2").change().reJqSelect();
		
		$("#cate3").children().remove();
		$("<option value=''>三级分类</option>").appendTo("#cate3");
		$("#cate3").change().reJqSelect();
	}else if(cateLevel==2){
		$("#cate2").children().remove();
		$("<option value=''>二级分类</option>").appendTo("#cate2");
		$("#cate2").change().reJqSelect();
		
		$("#cate3").children().remove();
		$("<option value=''>三级分类</option>").appendTo("#cate3");
		$("#cate3").change().reJqSelect();
	}else if(cateLevel==3){
		$("#cate3").children().remove();
		$("<option value=''>三级分类</option>").appendTo("#cate3");
		$("#cate3").change().reJqSelect();
	}
	
	if(storeId!=""&&brandId!=""){
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				storeId:storeId,
				brandId:brandId,
				cateLevel:"ctgL"+cateLevel,
				cateId:cateId
			},
			url : global.base.baseRoot+"/mctfss/commodity/selCate.sc",
			success : function(data) {
				var cates = data.cateList;
				for(var i=0,_len=cates.length;i<_len;i++){
					$("<option value='"+cates[i].structName+"' cateId='"+cates[i].id+"'>"+cates[i].catName+"</option>").appendTo("#cate"+cateLevel);
				}
				$("#cate"+cateLevel).change().reJqSelect();
			}
		});
	}
}

/*commondity.init = function() {
    $.ajaxSetup({
        timeout: 45000,
        cache: false,
    });
    
    var structName = $("#structName").val();
    var brandId = $("#brandId").val();
    
    // 初始化一级分类下拉框
    if (brandId != "" && structName == "") {
        $.getJSON(global.base.baseRoot + "/comodityCate/selCategory/" + global.base.storeId + "/" + brandId + "/ctgL1/" + brandId + ".sc",
        function(jsonData) {
            if (jsonData.scode && jsonData.scode == 0) {
            	var $brandClass = $("select[name='brandId']");
                var $nextObj = $brandClass.next(".ipt_box");
                if ($nextObj.length > 0) {
                    $nextObj.find("option").remove();
                    $nextObj.append("<option value=''>一级分类</option>");
                    if ( !! jsonData.data) {
                        $.each(jsonData.data,
                        function(i, item) {
                            $nextObj.append("<option data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                            var $next_nextAll = $nextObj.nextAll("select");
                            if ($next_nextAll.length[0]) {
                                $next_nextAll.find("option[index!=0]").remove();
                            }
                        });
                    }
                }
            }
        });
    }
    
    // 品牌下拉框联动一级分类
    $("#brandId").live("change",
    function() {
        var _this = $(this);
        var brandId = $.trim(_this.val());
        $.getJSON(global.base.baseRoot + "/comodityCate/selCategory/" + global.base.storeId + "/" + brandId + "/ctgL1/" + brandId + ".sc",
        function(jsonData) {
            if (jsonData.scode && jsonData.scode == 0) {
            	var $brandClass = $("select[name='brandId']");
                var $nextObj = $brandClass.next(".ipt_box");
                if ($nextObj.length > 0) {
                    $nextObj.find("option").remove();
                    $nextObj.append("<option value=''>一级分类</option>");
                    if ( !! jsonData.data) {
                        $.each(jsonData.data,
                        function(i, item) {
                            $nextObj.append("<option data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                            var $next_nextAll = $nextObj.nextAll("select");
                            if ($next_nextAll.length[0]) {
                                $next_nextAll.find("option[index!=0]").remove();
                            }
                        });
                    }
                }
                $("#structName").val("");
                $nextObj.siblings("select[name='secClass']").find("option").remove();
                $nextObj.siblings("select[name='secClass']").append("<option value=''>二级分类</option>");
                $nextObj.siblings("select[name='trdClass']").find("option").remove();
                $nextObj.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
            }
        });
    });

    // 分类下拉框联动
    $(".cate").live("change",
    function() {
        var _this = $(this);
        var brandId = "";
        if (_this.attr("name") == 'brandId') {
            brandId = $.trim(_this.val());
        } else {
            brandId = _this.siblings("select[name='brandId']").val();
        }

        var cateLevel = _this.attr("data-cateLev");

        if (cateLevel == '') { // 第三级分类
            return;
        }
        var cateId = $.trim($(this).find("option:selected").attr("data-cateId"));
        if (cateId.length == 0) { // 未选择任何选项
            //_this.nextAll("select").find("option[index!=0]").remove();
            _this.nextAll("select").find("option").remove();
            if(cateLevel=='ctgL1') {
            	_this.siblings("select[name='fstClass']").append("<option value=''>一级分类</option>");
            	_this.siblings("select[name='secClass']").append("<option value=''>二级分类</option>");
	  			_this.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
            } else if(cateLevel=='ctgL2') {
	  			_this.siblings("select[name='secClass']").append("<option value=''>二级分类</option>");
	  			_this.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
	  		} else if (cateLevel=='ctgL3') {
	  			_this.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
	  		}
            return;
        }
        $.getJSON(global.base.baseRoot + "/comodityCate/selCategory/" + global.base.storeId + "/" + brandId + "/" + cateLevel + "/" + cateId + ".sc",
        function(jsonData) {
            if (jsonData.scode && jsonData.scode == 0) {
                var $nextObj = _this.next(".ipt_box");
                if ($nextObj.length > 0) {
                    //$nextObj.find("option[index!=0]").remove();
                    $nextObj.find("option").remove();
                    if (cateLevel=='ctgL1') {
                    	$nextObj.append("<option value=''>一级分类</option>");
                    } else if(cateLevel=='ctgL2') {
						$nextObj.append("<option value=''>二级分类</option>");
					} else if (cateLevel=='ctgL3') {
						$nextObj.append("<option value=''>三级分类</option>");
					} 
                    if ( !! jsonData.data) {
                        $.each(jsonData.data,
                        function(i, item) {
                            $nextObj.append("<option data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                            var $next_nextAll = $nextObj.nextAll("select");
                            if ($next_nextAll.length > 0) {
                                //$next_nextAll.find("option[index!=0]").remove();
                                $next_nextAll.find("option").remove();
                                if (cateLevel=='ctgL1') {
                                	_this.siblings("select[name='secClass']").append("<option value=''>二级分类</option>");
                		  			_this.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
                                } else if(cateLevel=='ctgL2') {
                                	_this.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
                                }
                                //$next_nextAll.append("<option value=''>三级分类</option>");
                            }
                        });
                    }
                }
            }
        });
    });
    
    // 店铺名称修改后，级联品牌和一级分类
    $("#selectStoreId").live("change",
    function() {
        var _this = $(this);
        var storeId = $.trim(_this.val());
        $("#storeId").val(storeId);
        global.base.storeId = storeId;
        $.getJSON(global.base.baseRoot + "/fss/selBrands.sc?storeId=" + storeId,
        function(jsonData) {
            if (jsonData.scode && jsonData.scode == 0) {
            	if ( !! jsonData.data) {
            		//初始品牌
            		var $brandClass = $("select[name='brandId']");
            		$brandClass.children().remove();
            		$.each(jsonData.data,
                    function(i, item) {
                        if(i == 0){
                        	$brandClass.append("<option value='" + item.id + "' selected>" + item.brandName + "</option>");
                        }else{
                        	$brandClass.append("<option value='" + item.id + "'>" + item.brandName + "</option>");
                        }
                    });
            		$brandClass.change().reJqSelect();
            		
            		//初始化一级分类
            		var $nextObj = $brandClass.next(".ipt_box");
                    if ($nextObj.length > 0) {
                    	$nextObj.find("option").remove();
                        $nextObj.append("<option value=''>一级分类</option>");
                        if ( !! jsonData.data[1]) {
                            $.each(jsonData.data[1],
                            function(i, item) {
                                $nextObj.append("<option data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                                var $next_nextAll = $nextObj.nextAll("select");
                                if ($next_nextAll.length[0]) {
                                    $next_nextAll.find("option[index!=0]").remove();
                                }
                            });
                            $("#structName").val("");
                        }
                        $nextObj.siblings("select[name='secClass']").find("option").remove();
                        $nextObj.siblings("select[name='secClass']").append("<option value=''>二级分类</option>");
                        $nextObj.siblings("select[name='trdClass']").find("option").remove();
                        $nextObj.siblings("select[name='trdClass']").append("<option value=''>三级分类</option>");
                    }
            	}
            }
        });
    });

    // 设置 structName   :not([name='brandId'])
    $(".cate").live("change.lenney",
    function() {
        var $this = $(this);
        var structNameObj = $this.siblings("input[name='structName']");
        if ($this.attr("name") == 'brandId') {
            structNameObj.val("");
        } else {
            var thisVal = $this.val();
            if ($this.val().length == 0) { // 未选择具体分类
                if ($this.prev().length > 0 && $this.prev().attr("name") != 'brandId') {
                    structNameObj.val($this.prev().val());
                } else {
                    structNameObj.val("");
                }
            } else {
                structNameObj.val(thisVal);
            }
        }

    });

    // 初始化三级分类 
    var initCateFun = function(structNameObj) {
        var _this_structNameObj = structNameObj;
        var brandId = $("#brandId").val();
        var structNameVal = $.trim(structNameObj.val());
        if (structNameVal.length == 0) {
            return;
        }
        $.getJSON(global.base.baseRoot + "/comodityCate/initCate/" + global.base.storeId + "/" + brandId + "/" + structNameVal + ".sc",
        function(jsonData) {
            if (jsonData.scode && jsonData.scode == 0) {
                var $fstSel = _this_structNameObj.siblings("select[name='fstClass']");
                var $secSel = _this_structNameObj.siblings("select[name='secClass']");
                var $trdSel = _this_structNameObj.siblings("select[name='trdClass']");

                if ( !! jsonData.data.cateMap) {
                    if ( !! jsonData.data.cateMap.ctgL1) {
                        $.each(jsonData.data.cateMap.ctgL1,
                        function(i, item) {
                            $fstSel.append("<option selected='selected' data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                        });
                        if ( !! jsonData.data.cate_1) {
                            $fstSel.val(jsonData.data.cate_1.structName);
                        } else {
                            $fstSel.val('');
                        }
                    }
                    if ( !! jsonData.data.cateMap.ctgL2) {
                        $.each(jsonData.data.cateMap.ctgL2,
                        function(i, item) {
                            $secSel.append("<option selected='selected' data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                        });
                        if ( !! jsonData.data.cate_2) {
                            $secSel.val(jsonData.data.cate_2.structName);
                        } else {
                            $secSel.val('');
                        }
                    }
                    if ( !! jsonData.data.cateMap.ctgL3) {
                        $.each(jsonData.data.cateMap.ctgL3,
                        function(i, item) {
                            $trdSel.append("<option selected='selected' data-cateId='" + item.id + "' value='" + item.structName + "'>" + item.catName + "</option>");
                        });
                        if ( !! jsonData.data.cate_3) {
                            $trdSel.val(jsonData.data.cate_3.structName);
                        } else {
                            $trdSel.val('');
                        }
                    }
                }
            }
        });
    };

    // 进入页面时，初始化三级分类
    $("input[name='structName']").each(function(i, item) {
        initCateFun($(this));
    });

};*/
