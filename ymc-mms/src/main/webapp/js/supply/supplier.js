function closeAdd() {
  		art.dialog.close();  		
  	}
function closeSupplier() {
		art.dialog.close();
		art.dialog.parent.location.reload();
	}
//提交按钮所在的表单
	function submitForm(formId, url){		
		$("#"+formId).attr("action",url);		//添加分类的hidden到form		
		$("#"+formId).submit();
		alert("修改成功");
		
	}
//选择供应商	
	function selectSupplier(path){
		//window.top.purchaseId=$("#purchaseId").val();
		 showThickBox("选择供应商.",path+"/supply/manage/supplier/toSelectSupplier.sc?TB_iframe=true&height=600&width=950",false);
	}
//采购单选择货品	
	function selectProduct(path){
		window.top.purchaseId=$("#purchaseId").val();
		 showThickBox("选择货品.",path+"/supply/manage/purchase/toSelectCommodity.sc?TB_iframe=true&height=600&width=950",false);
	}
//供应商价格选择货品	
function selectCommodity(path){	
	window.top.supplierId=$("#supplierId").val();	
	 showThickBox("选择货品.",path+"/supply/manage/supplier/toSelectCommodity.sc?TB_iframe=true&height=600&width=950",false);
}

//供应商价格选择商品	
function selectCommodityList(path){	
	window.top.supplierId=$("#supplierId").val();	
	 showThickBox("选择商品.",path+"/supply/manage/supplier/toCommodityList.sc?TB_iframe=true&height=600&width=950",false);	
}

function addContactSave(id){ 
	 var contact=$("#contact").val();
	 var type=$("#type").val();
	 var telePhone=$("#telePhone").val();
	 var mobilePhone=$("#mobilePhone").val();
	 var email=$("#email").val();
	 var fax=$("#fax").val();
	 $.ajax({
      type: "POST",
      data: {"param":id}, 
      url: "saveSupplierContact.sc",        
      success: function(data){         	
		 	if(data=="success"){
		 		alert("保存成功!");
		 	}else{
		 		alert("保存失败!");
		 	}
      }
    }); 
	 window.parent.mbdif.location.reload();
	 window.top.TB_remove(); 
} 

function updateContactSave(id,supplierId){      
	 $.ajax({
      type: "POST",
      data: {"id":id,"supplierId":supplierId}, 
      url: "updateSupplierContact.sc",        
      success: function(data){         	
		 	if(data=="success"){
		 		alert("保存成功!");
		 	}else{
		 		alert("保存失败!");
		 	}
      }
    });
	 window.parent.mbdif.location.reload();
	 window.top.TB_remove();
} 

/**
  * 确认选择供应商(单选)
  * @param inputName
  * @return
  */
 function supplierSelect(supplierId,supplier){	  
	/*var checkId="";
	var checkName="";
	if(inputName.checked){		
		checkId= inputName.supplierTag;
		checkName= inputName.value;
	} 	*/
	art.dialog.parent.document.getElementById('supplier').value = supplier;
	art.dialog.parent.document.getElementById('supplierId').value = supplierId;
	art.dialog.close();
 }
  
  /**
   * 确认选择货品(单选)
   * @param inputName
   * @return
   */
  function productSelect(inputName){	  
 	var commodity="";
 	var product="";
 	var specprop="";
 	var productId="";
 	if(inputName.checked){		
 		commodity= inputName.commodity;
 		product= inputName.product;
 		specprop=inputName.specprop; 		
 		productId=inputName.value; 		
 	}	
  	
  		window.parent.mbdif.document.getElementById("commodityName").value=commodity;
  		window.parent.mbdif.document.getElementById("commodityCode").value=product;
  		window.parent.mbdif.document.getElementById("productId").value=productId;
// 		window.parent.mbdif.document.getElementById("specification").value=specprop;
  		 window.top.TB_remove();
  
  	
  } 
  
   /**
    * 确认选择货品(多选)
    * @param inputName
    * @return
    */
   function batchSelect(){   	
   	var checkObj = $("input[name=productNos]"); 	 
   	var productNos = "";
   	var productNames = "";
   	var supplierPriceSpIds = "";
   	for(var i=0; i<checkObj.length; i++) {
   		if(checkObj[i].checked) {
   			var proNoName = checkObj[i].value;
   	   		var proarray = proNoName.split("~");
   	   		productNos += proarray[0]+"~";
   	   		productNames += proarray[1]+";    ";
   	   		supplierPriceSpIds += proarray[2]+"~";
   		}
   	}
   	if(productNos==""){
   		alert("请选择货品!");
   		return false;
   	}else {
   		var pnos = art.dialog.parent.document.getElementById('productNos').value;
   		art.dialog.parent.document.getElementById('productNos').value = pnos + productNos;
   		var cNames = art.dialog.parent.document.getElementById('commodityName').value;
		art.dialog.parent.document.getElementById('commodityName').value = cNames + productNames;
		var spIds = art.dialog.parent.document.getElementById('supplierPriceSpIds').value;
		art.dialog.parent.document.getElementById('supplierPriceSpIds').value = spIds + supplierPriceSpIds;
		art.dialog.close();
   	}
   }
   
   /**
    * 将货品加入采购单
    * @param ids
    * @return
    */
   function addProdToPur(ids){    
   	 $.ajax({
          type: "POST",
          data: {"ids":ids,"purId":window.top.purchaseId}, 
          url: "c_addProdToPur.sc",        
          success: function(data){         	
   		 	if(data=="success"){
   		 		alert("添加货品成功!");
   		 	}else{
   		 		alert("添加货品失败!");
   		 	}
          }
        });
   }
    
    
    /**
     * 确认选择货品(多选)
     * @param inputName
     * @return
     */
    function batchSelectProducts(inputName){   	
    	var checkObj = $("input[name='"+inputName+"']"); 	 
    	var checkIds = "";
    	
    	checkObj.each(function(index){
    		if(this.checked){
    			if(checkObj.length >= index+1){
    				checkIds += $(this).val() +","; 				
    			}
    		}
    	}); 	
    	if(checkIds==""||checkIds==","){
    		alert("请选择货品!");
    	}
    	if(checkIds!=""){
    		addProductToSupplierPrice(checkIds);
    	}
    }
    
    /**
     * 将货品加入供应商价格
     * @param ids
     * @return
     */
    function addProductToSupplierPrice(ids){      
    	 $.ajax({
           type: "POST",
           data: {"ids":ids,"pId":window.top.supplierId}, 
           url: "c_addProductToSupplierprice.sc",        
           success: function(data){         	
    		 	if(data=="success"){
    		 		alert("添加货品成功!");
    		 	}else{
    		 		alert("添加货品失败!");
    		 	}
           }
         });    	
    } 
    
    function getBack(bathpath){
    	window.parent.mbdif.location.reload();
    	 window.top.TB_remove();
     } 
    
    function getBackSupplier(bathpath){
    	window.parent.location.reload();
    	 window.top.TB_remove();
     } 
    
    
    /**
     * 确认选择货品(多选)
     * @param inputName
     * @return
     */
    function batchSelectCommodity(inputName){    	
    	var checkObj = $("input[name='"+inputName+"']"); 	 
    	var checkIds = "";
    	
    	checkObj.each(function(index){
    		if(this.checked){
    			if(checkObj.length >= index+1){
    				checkIds += $(this).val() +","; 				
    			}
    		}
    	}); 	
    	if(checkIds==""||checkIds==","){
    		alert("请选择商品!");
    	}
    	if(checkIds!=""){
    		addCommodityToSupplierPrice(checkIds);
    	}
    }
    
    /**
     * 将货品加入供应商价格
     * @param ids
     * @return
     */
    function addCommodityToSupplierPrice(ids){    	 
    	 $.ajax({
           type: "POST",
           data: {"ids":ids,"pId":window.top.supplierId}, 
           url: "c_addCommodityToSupplierprice.sc",        
           success: function(data){         	
    		 	if(data=="success"){
    		 		alert("添加商品成功!");
    		 	}else if(data=="fail") {
    		 		alert("此商品已存在,请重新选择!");
    		 	}else{
    		 		alert("添加商品失败!");
    		 	}
           }
         });
    } 

