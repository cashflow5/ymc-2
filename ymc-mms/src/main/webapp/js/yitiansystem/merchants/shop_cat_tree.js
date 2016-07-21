var setting = {
	check : {
		enable : true,
		chkboxType: { "Y": "p", "N": "ps" }
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		showIcon : false,
		expandSpeed : "fast"
	}
};
/**
 * 删除品类关系
 */
function deleteGoodsCheckBoxProp(brandNo) {
	//删除品牌显示条
	$("#brand_checked_result_" + brandNo).remove();
	//删除Tree节点
	deleteBrandNode(brandNo);

	//调整品牌No取值
	var brandNos = [];

	var _brandList = $("#bankNoHistory").val();
	var _brandNos = _brandList.split(";");
	for ( var i = 0; i < _brandNos.length; i++) {
		if (_brandNos[i] == brandNo)
			continue;
		brandNos[brandNos.length] = _brandNos[i];
	}
	$("#bankNoHistory").val(brandNos.join(';'));
	$("#bankNoHidden").val(brandNos.join(';'));
	if ($("#bankNoHistory").val() == "") {
		$("#supplier_category_brand_tree_tr").hide();
	}
}

function deleteBrandNode(brandNo) {
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getNodes();
	for ( var i = 0, l = nodes.length; i < l; i++) {
		if (nodes[i] == null || nodes[i] == undefined)
			continue;
		if (nodes[i].id == (brandNo + ";0"))
			treeObj.removeNode(nodes[i]);
	}
}

//加载品牌显示框
function showBrandframe(brandNo, brandName) {
	var obj = $('#bank_span');
	var html = obj.html();
	html += formatString(
				'<span class="brand_span_checked" id="brand_checked_result_{#value}">' +
				'   <span class="fl tt">{#name}</span>' + 
				'   <a href="javascript:;" onclick="deleteGoodsCheckBoxProp(\'{#value}\');">' + 
				'   </a>' + 
				'</span>',
				{
					value: brandNo,
					name: brandName
				});
	obj.html(html);
}


/**
 * 对目标字符串进行格式化
 * @author huang.wq
 * @param {string} source 目标字符串
 * @param {Object|string...} opts 提供相应数据的对象或多个字符串
 * @remark
 * opts参数为“Object”时，替换目标字符串中的{#property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的{#0}、{#1}...部分。	
 * @shortcut format
 * @returns {string} 格式化后的字符串
 * 例：
    (function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#0}-{#1}-{#2}',["2011年","5月","1日"]);
	(function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#year}-{#month}-{#day}', {year: 2011, month: 5, day: 1});   
 */
function formatString(source, opts) {
    source = String(source);
    var data = Array.prototype.slice.call(arguments,1), toString = Object.prototype.toString;
    if(data.length){
	    data = data.length == 1 ? 
	    	/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
	    	(opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) 
	    	: data;
    	return source.replace(/\{#(.+?)\}/g, function (match, key){
	    	var replacer = data[key];
	    	// chrome 下 typeof /a/ == 'function'
	    	if('[object Function]' == toString.call(replacer)){
	    		replacer = replacer(key);
	    	}
	    	return ('undefined' == typeof replacer ? '' : replacer);
    	});
    }
    return source;
};

