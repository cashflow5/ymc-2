//全局变量
define(function(require,exports,module){
	var Global={};
	
	var parentStoreId = null;
	var parentPageId = null;
	try{
		parentStoreId = parent.$("#storeId").val();
	}catch(e){};
 
	try{
		parentPageId = parent.$("#pageId").val();
	}catch(e){};
	
	Global.base={
		storeId:$("#storeId").val() || parentStoreId,
		baseRoot:'/fss',
		currentPageId:$("#pageId").val() || parentPageId,
		imgSelectorCallBack:null,
		imgWareHouseUrl:"/fss/gallery/mdlGallery/"+$("#storeId").val()+ "/"+$("#mdlCd").val() + ".sc",
		imgUploaderSWF:"/fss/assets/js/libs/uploadify/uploadify.swf",
		imgUploaderCancelPng:"/fss/assets/js/uploadify/cancel.png",
		imgUploadUrl:"/fss/gallery/upload/"+$("#storeId").val()+".sc?jsessionid="+$("#jsessionid").val()+"&mdlCd="+$("#mdlCd").val()+ "&YGKD_SID="+$("#YGKD_SID").val() 
	};
	Global.module={
		currMdl:null,
		lytInsertFlag:0,
		currLyt:null,
		currActBtn:null,
		currMdlWidth:0
	};
	module.exports=Global;
});