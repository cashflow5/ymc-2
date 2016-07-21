$(function() {

	
	var categories;
	
	    //品牌下拉框onchange事件
		$("#brandNo").change(function() {			
			var brandName = $(this).find("option:selected").text();
			var brandId = $.trim($(this).find("option:selected").attr("brandId"));
		
			// 初始化分类信息（新）
			categories = [];
			initCatForBrand(brandId, 'category1', categories);
	
		});	
		
		$("#category1").change(function() {
			reinitializeOption($(this).find(':selected').attr('id'), 2, '#category2', categories);
		});
		
		$("#category2").change(function() {
			reinitializeOption($(this).find(':selected').attr('id'), 3, '#category3', categories);
		});
});	
function initCatForBrand(brandId, selId, categories) {
	if (brandId == null || brandId == '' || brandId.length == 0) {
		$("#" + selId).get(0).options.length = 1;
		$("#" + selId).data('ui').refresh();
		return false;
	}
	$.ajax( {
		type : "POST",
		url : basePath + "/commodity/queryBrandCat.sc",
		async : false,
		data : {
			"brandId" : brandId
		},
		dataType : "json",
		success : function(data) {
			if (data == null) {
				$("#" + selId).get(0).options.length = 1;
				return;
			}
			
			for ( var i = 0; i < data.length; i++) {
				categories[categories.length] = { label: data[i].catName, 
						//value: data[i].structName + ";" + data[i].id + ";" + data[i].catNo + ";" + data[i].catName, 
						value:data[i].structName,
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
	$(selector).append(optionText);
	$(selector).data('ui').refresh();
}