<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/merchants_cat.js"></script>
<title>优购商城--商家后台</title>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加商家分类</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form name="queryForm" id="queryForm" method="post"> 
     <input type="hidden" name="flag" id="flag" value="<#if flag??>${flag!''}</#if>">
          <table>
                <tbody>
                		<tr>
                			<th>
                				商品品牌：
                			</th>
                			<td>
                				<select name="toplev" id="brand">
                					<option value="" selected="selected">请选择</option>
                					<#if lstBrand??>
										<#list lstBrand as item>
											<option value="${(item.brandNo)!""}" brandId="${(item.id)!''}">${(item.brandName)!""}</option>
										</#list>
									</#if>
                				</select>
                			</td>
                		</tr>
						<tr>
							<th>
								商品分类：
							</th>
							<td>
								<select name="toplev" class="fl-lf" id="category1">
			                    	<option value="" selected="selected">一级分类</option>
			                    </select>
			                    <select name="seclev" class="fl-lf" id="category2">
			                    	<option value="" selected="selected">二级分类</option>
			                    </select>
			                    <select name="thirlev" class="fl-lf" id="category3">
			                    	<option value="" selected="selected">三级分类</option>
			                    </select>
							</td>
						</tr>
					</tbody>
					</table>
				</form>
               <div style="margin-top:20px;margin-left:200px;">
                 <input type="button" value="确认" id="confirmbtn" class="yt-seach-btn">
               </div>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
function initCatForBrand(brandNo, selId, categories) {
	if (brandNo == null || brandNo == '' || brandNo.length == 0) {
		$("#" + selId).get(0).options.length = 1;
		$("#" + selId).change();
		return false;
	}
	$.ajax( {
		type : "POST",
		url : "${BasePath}/yitiansystem/merchants/businessorder/queryBrandCat.sc",
		async : false,
		data : {"brandNo" : brandNo},
		dataType : "json",
		success : function(data) {
			if (data == null) {
				$("#" + selId).get(0).options.length = 1;
				$("#" + selId).change();
				return;
			}
			
			for ( var i = 0; i < data.length; i++) {
				categories[categories.length] = { label: data[i].catName, 
						value: data[i].structName + ";" + data[i].id, 
						level: data[i].catLeave, 
						self: data[i].structName, 
						owner: data[i].parentId };
			}
			
			reinitializeOption('0', 1, '#' + selId, categories);
		}
	});
}
function reinitializeOption(id, level, selector, categories) {
	var optionText = $(categories).filter(function(){
		return (this.level == level) && (this.owner == id); 
	}).map(function(){
		return '<option id="' + this.self + '" value="' + this.value + '">' + this.label + '</option>';
	}).get().join('');
	
	$(selector).get(0).options.length = 1;
	$(selector).append(optionText).change();
}

$(document).ready(function(){
var categories = [];
	$("#brand").change(function() {
		// 清空分类数组
		categories = [];
		// 初始化分类信息（新）
		initCatForBrand(this.value, 'category1', categories);
	}).change();
	
	function createMatchRegExp(text) {
		return new RegExp('(' + text + '\\s*;)|(;\\s*' + text + ')|(^' + text + '$)', 'ig');
	}
	
	function createMatchRegExp2(text) {
		return new RegExp('(' + text + '-?[0-9-]*;[a-zA-Z0-9]{32}_)|(_' + text + '-?[0-9-]*;[a-zA-Z0-9]{32})|(^' + text + '-?[0-9-]*;[a-zA-Z0-9]{32}$)', 'ig');
	}
	
	$('#category1').change(function(){
		reinitializeOption($(this).find(':selected').attr('id'), 2, '#category2', categories);
	});
	
	$('#category2').change(function(){
		reinitializeOption($(this).find(':selected').attr('id'), 3, '#category3', categories);
	});
	
	$('#confirmbtn').click(function(){
		var option1 = $('#category1').find(':selected');
		var option2 = $('#category2').find(':selected');
		var option3 = $('#category3').find(':selected');
		var brandName = $('#brand').find(':selected').html();
		var brandNo = $("#brand").find(':selected').val();
		
		var selectedCategories = [];
		var lowLevelValue = '';
		if ($.trim(option1.val()) != '') {
			selectedCategories[selectedCategories.length] = option1.html();
			lowLevelValue = option1.val();
		}
		if ($.trim(option2.val()) != '') {
			selectedCategories[selectedCategories.length] = option2.html();
			lowLevelValue = option2.val();
		}
		if ($.trim(option3.val()) != '') {
			selectedCategories[selectedCategories.length] = option3.html();
			lowLevelValue = option3.val();
		}
		if (selectedCategories.length == 0) {
			alert('请选择一级分类!');
			return false;
		}
		
		//获取父文本框显示控件
		var catNameComponent = dg.curWin.document.getElementById('catName');
		var catListComponent = dg.curWin.document.getElementById('catList');
		//获取父文本框隐藏的控件
		var catNameHiddenComponent = dg.curWin.document.getElementById('catNameHidden');
		
		var selectedCategoriesNames = $.trim(catNameComponent.innerHTML);
		var selectedCategoriesInfos = $.trim(catNameHiddenComponent.value);
		var contents = selectedCategories.join('-');
		
		if (selectedCategories.length == 1) {
			contents = $(categories).filter(function(){
				return (this.owner == option1.attr('id'));
			}).map(function(){
				var tmpObj = this;
				var tmpText = (contents + '-' + this.label);
				return $(categories).filter(function(){
					return (this.owner == tmpObj.self) && (selectedCategoriesNames.match(createMatchRegExp(brandName + '\\|' + tmpText + '-' + this.label)) == null);
				}).map(function(){
					return (brandName + '|' + tmpText + '-' + this.label);
				}).get().join(';')
			}).get().join(';');
		} else if (selectedCategories.length == 2) {
			contents = $(categories).filter(function(){
				return (this.owner == option2.attr('id')) && (selectedCategoriesNames.match(createMatchRegExp(brandName + '\\|' + contents + '-' + this.label)) == null); 
			}).map(function(){
				return (brandName + '|' + contents + '-' + this.label);
			}).get().join(';');
		} else if (selectedCategories.length == 3) {
			contents = selectedCategoriesNames.match(createMatchRegExp(brandName + '\\|' +contents)) != null ? '' : brandName + '|' + contents; 
		}
		
		if (contents.length > 0) {
			catNameComponent.innerHTML = selectedCategoriesNames + (selectedCategoriesNames.length == 0 ? '' : ';') + contents;
			selectedCategoriesInfos = selectedCategoriesInfos.replace(createMatchRegExp2(brandName + '\\|' + lowLevelValue.substring(0, lowLevelValue.indexOf(';'))), '');
			catNameHiddenComponent.value = selectedCategoriesInfos + (selectedCategoriesInfos.length == 0 ? '' : '_') + brandNo + ';' + lowLevelValue;
			if($("#flag").val() == 2) {
				catListComponent.value = catNameComponent.innerHTML;
			}
		}
		
		closewindow();
	});
});
</script>