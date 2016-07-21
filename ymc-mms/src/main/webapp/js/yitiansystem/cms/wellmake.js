function selectBrand(path){	
	 var tagIds = $("#tagsId").val();
	 window.top.tagId = tagIds;
	 showThickBox("添加优品制造品牌",path+"/yitiansystem/cms/wellmakesystem/toAddwellMakeBrad.sc?TB_iframe=true&height=300&width=500",false);
}