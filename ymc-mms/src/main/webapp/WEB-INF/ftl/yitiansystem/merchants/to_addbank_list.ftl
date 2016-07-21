<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>优购商城--商家后台</title>
</head>
<body>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加商家品牌</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     		 
  			 <div class="wms-top">
                 <label>商品品牌：</label>
                 <input type="text" name="brandName" id="brandName" value="${brandName!''}"/>&nbsp;&nbsp;&nbsp;
                 <input type="button" id="sreach" value="搜索" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <input type="button" value="确认" onclick="checkBankName();"  align="right" class="yt-seach-btn">
             </div>
     		
             <span>商品品牌:</span> <br/>
             <input type="hidden" name="flag" id="flag" value="<#if flag??>${flag!''}</#if>">
             <table id="sreach_table" cellpadding="0" cellspacing="0" class="list_table" style="margin-top:10px; display: none;">
             	
             </table>
             <table id="result_table" cellpadding="0" cellspacing="0" class="list_table" style="margin-top:10px;">
				<#if brandList??>
					<tr>
						<#list brandList as item>
							<td>
								<input type="checkbox" value="${item.brandNo!''}" title="${item.brandName!''}" name="bank" id="bank_${item.brandNo}"/>
								<label for="bank_${item.brandNo}">${item.brandName!""}</label>&nbsp;&nbsp;
							</td>
							<#if (item_index + 1) % 6 == 0>
								</tr>
								<#if item_has_next>
									<tr>
								</#if>
							</#if>
						</#list>
					</tr>
				</#if>
            </table>
           </div>
           <div style="margin-top:20px;margin-left:520px;">
             <input type="button" value="确认" onclick="checkBankName();"  class="yt-seach-btn">
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
	// 页面载入后加载
	$(document).ready(function(){
  		var _temp = dg.curWin.document.getElementById('bankNoHistory').value;
  		if (_temp) {
  			var brandNos = _temp.split(";");
  			$('input[name=bank]').each(function(){
  				var no = $(this).val();
	  			for (var i = 0; i < brandNos.length; i++) {
	  				if (brandNos[i] == no) {
	  					$(this).attr('checked', 'checked');
	  				}
	  			}
  			});
  		}
  		// 搜索品牌
  		$("#sreach").click(function(){sreachbrand()});
  		
  		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];            
            if(e && e.keyCode==13){ // enter 键
            	sreachbrand(); 
            }
        }; 
	});

	
function sreachbrand(){
	var sreachResult = [];
	var val = $('#brandName').val();
	$('#sreach_table').empty();
	if (val != null && val != '' && val.length != 0) {
		// 通过正则来匹配相关品牌
		var regex = new RegExp($.trim(val), 'ig');
		$('input[name="bank"]').each(function(){
			var no = $(this).val();
			var name = $(this).attr('title');
			var check = $(this).attr('checked');
			if (regex.test(no) || regex.test(name)) {
				sreachResult[sreachResult.length] = { no: no, name: name, check:check};
			}
		});
		var tr_html = '<tr>';
		for (var i = 0; i < sreachResult.length; i++) {
			var node = sreachResult[i];
			var td_html = '<td>';
			td_html += formatString(
					'<input type="checkbox" value="{#no}" {#check} title="{#name}" name="sreach_brand" id="sreach_brand_{#no}"/>' + 
					'<label>{#name}</label>&nbsp;&nbsp;', 
					{
						no: node.no,
						name : node.name,
						check : node.check == true ? 'checked=true' : '' 
					});
			td_html += '</td>';
				
			tr_html += td_html;
			// 判断是否换行
			if (i % 6 == 0) {
				tr_html += '</tr>';
				if (sreachResult.length - 1 > i) {
					tr_html += '<tr>';
				}
			}
		}
		tr_html += '</tr>';
		$('#sreach_table').append(tr_html);
				
		// 当搜索出值时
		if (sreachResult.length > 0) {
			$('#sreach_table').show();
			$('#result_table').hide();
				
			// 给查询结果集绑定点击事件
			$('input[name="sreach_brand"]').each(function(){
				$(this).click(function(){
					var check = $(this).attr('checked');
					var no = $(this).val();
					if (check) {// 被选中
						$('#bank_' + no).attr('checked', 'checked');
					} else {
						$('#bank_' + no).removeAttr('checked');
					}
				});
			});
		} else {
			$('#sreach_table').hide();
			$('#result_table').show();
		}
	} else {
		$('#sreach_table').hide();
		$('#result_table').show();
	}
}

// 选择商品品牌
function checkBankName(){
	var checkedBrands = $('input[name="bank"]:checked');
	if (checkedBrands.size() <= 0) {
		alert('请选择品牌!');
		return false;
	}
	
	// 获取父窗口
	var bankNameHiddenComponent = dg.curWin.document.getElementById('bankNameHidden');
	var brankNoHiddenComponent = dg.curWin.document.getElementById('bankNoHidden');
	
	var brandNames = [];
	var brandNos = [];
	
	// 加载品牌显示框
	var html = "";
	$($("input[name='bank']:checked")).each(
		function() {
			html += formatString(
				'<span class="brand_span_checked" id="brand_checked_result_{#value}">' +
				'   <span class="fl tt">{#name}</span>' + 
				'   <a href="javascript:;" onclick="deleteGoodsCheckBoxProp(\'{#value}\');">' + 
				'   </a>' + 
				'</span>',
				{
					value: $(this).val(),
					name: $(this).attr('title')
				});
			brandNames[brandNames.length] = $(this).attr('title');
			brandNos[brandNos.length] = $(this).val();
		}
	);
	bankNameHiddenComponent.value = '';
	brankNoHiddenComponent.value = '';
	bankNameHiddenComponent.value = brandNames.join(';');
	brankNoHiddenComponent.value = brandNos.join(';');
	$("#bank_span", dg.curWin.document).html(html);
	
	// 触发函数加载分类tree
	$("#supplier_cat_brand_tree_hidden", dg.curWin.document).click();
	
	closewindow();
}



function createMatchRegExp(text) {
	return new RegExp('(' + text + '\\s*;)|(;\\s*' + text + ')|(^' + text + '$)', 'ig');
}

/**
 * 对目标字符串进行格式化
 * 
 * @author huang.wq
 * @param {string}
 *            source 目标字符串
 * @param {Object|string...}
 *            opts 提供相应数据的对象或多个字符串
 * @remark opts参数为“Object”时，替换目标字符串中的{#property name}部分。<br>
 *         opts为“string...”时，替换目标字符串中的{#0}、{#1}...部分。
 * @shortcut format
 * @returns {string} 格式化后的字符串 例： (function(arg0, arg1){ alert(formatString(arg0,
 *          arg1)); })('{#0}-{#1}-{#2}',["2011年","5月","1日"]); (function(arg0,
 *          arg1){ alert(formatString(arg0, arg1));
 *          })('{#year}-{#month}-{#day}', {year: 2011, month: 5, day: 1});
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

</script>

