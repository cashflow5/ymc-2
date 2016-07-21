//跳转到添加品牌权限设置
function addBrand(){
    openwindow(basePath+'/yitiansystem/merchants/businessorder/to_addBank.sc?flag=1',1100,600,'添加品牌');
}
var dialog_obj;
//跳转到添加品牌权限设置
function chooseBrand(index){
	var brankStrs = [];
	var brankNameStrs = [];
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			if(node.lev == -1) { //保存品牌			
				var nodeId = (node.id.split(";"))[0];
				var nodeName = node.name;
				brankStrs[brankStrs.length] = nodeId;
				brankNameStrs[brankNameStrs.length] = nodeName;				
			}
		}
	}
	if(brankStrs.length == 0){
		ygdg.dialog.alert("请先选择品牌!");
		return;
	}
	var htmlContent = '<table id="result_table" cellpadding="0" cellspacing="0" class="list_table" >';
	var temp = $("#brandNo_"+index).val();
	console.log(temp);
	for(var i=0,len=brankStrs.length;i<len;i++){
		if((i+1)%3 == 1){
			htmlContent += '<tr class="even">';
		}
		if(temp == brankStrs[i]){
			htmlContent += '<td><input type="radio" checked="checked" name="brand" value="'+brankStrs[i]+'" value1="'+brankNameStrs[i]+'"/>'+brankNameStrs[i]+'</td>';
		}else{
			htmlContent += '<td><input type="radio" name="brand" value="'+brankStrs[i]+'" value1="'+brankNameStrs[i]+'"/>'+brankNameStrs[i]+'</td>';
		}
		if((i+1)%3 ==0){
			htmlContent += '</tr>';
		}
	}
	htmlContent += '</table>';
    dialog_obj=ygdg.dialog({
            title:"请选择品牌",
            content : '<div style="">'+
            			htmlContent+        				
    					'</div>',
            max:false,
            min:false,
            lock:true,
            button:[
               {
                    name: '确定',
                    focus: true,
                    callback:function(){
                    	if(!$('input[name="brand"]:checked').val()){
                    		ygdg.dialog.alert("请选择品牌！");
                    		   return;
                    	}
                    	chooseBrand2(index,$('input[name="brand"]:checked').val(),$('input[name="brand"]:checked').attr('value1'));
                    }
                } 
            ]
        });
}

function chooseBrand2(index,brandNo,brandName){
	$("#brandNo_"+index).val(brandNo);
	$("#brandName_"+index).html(brandName);
	$("#brand_"+index).html("修改绑定");	
}

function checkBrand(){
	var msg_brand = "";
	var catStrs = [];
	var brankStrs = [];
	var brandNoNameArray = [];
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			if (node.lev == 3) { //取第三级分类
				catStrs[catStrs.length] = node.id;
			}
			if(node.lev == -1) { //保存品牌
				var nodeId = (node.id.split(";"))[0]
				brankStrs[brankStrs.length] = nodeId;
				brandNoNameArray[brandNoNameArray.length] = nodeId +';'+node.name;
			}
		}
	}

	$("#catNameHidden").val(catStrs.join('_'));
	$('#bankNoHidden').val(brankStrs.join(';'));
	$('#bankNoNameHidden').val(brandNoNameArray.join('_'));	

	if($("#catNameHidden").val()=="" ){
 		msg_brand = "请选择授权品牌!<br>";
	}
	
	var msg = "";
	var index = $("#sub-tbody").find("tr.add-row").length;
	if(index<1){
		msg = msg+"至少需要一行商标授权!<br>";
	}  	
	
    //校验商标
    var trademarks = $("input[name='trademark']");
    var $brandNo = $("input[name='brandNo']");
    var $deductionPoint = $("input[name='deductionPoint']");
    var $beAuthorizer = $("input[name='beAuthorizer']");
    var $authorizStartdate = $("input[name='authorizStartdate']");
    var $authorizEnddate = $("input[name='authorizEnddate']");
  
    var brandNoArrays = [];
    var deductionPointArrays = [];
    var brandNoStrings = brankStrs.join(';');
    for(var i=0,_len=trademarks.length;i<_len;i++){
    	if($.trim(trademarks.eq(i).val())==""){
    		msg = msg+"第"+(i+1)+"行商标不能为空！<br>";
    	}
    	// 扣点必须为1-100的整数 !  /^[1-9]\d*$/.test(deductionPointVal) 
    	var deductionPointVal = $.trim($deductionPoint.eq(i).val());
    	if(deductionPointVal=="" || !/^[1-9]\d*$/.test(deductionPointVal)  || deductionPointVal>100 ){
    		msg = msg+"第"+(i+1)+"行扣点不能为空且为1-100的整数！<br>";
    	}
    	var brandNoVal = $.trim($brandNo.eq(i).val());

    	if(!brandNoVal){
    		msg = msg+"第"+(i+1)+"行商标请绑定品牌！<br>";
    	}
    	var brandNoOldVal = $.trim($brandNo.eq(i).attr('oldValue'));
    	if(!brandNoOldVal || brandEditable){
    		if(brandNoStrings.indexOf(brandNoVal) < 0) {
    			msg = msg+"第"+(i+1)+"行商标绑定的品牌没有勾选！<br>";
    		}
    	}
    	brandNoArrays.push(brandNoVal);
    	deductionPointArrays.push(deductionPointVal);
	 
    	var flag = false;
    	// 校验授权
    	for(var row=0; row<6; row++){  
    		var rowIndex = row + i*6;
    		var beAuthorizer = $.trim($beAuthorizer.eq(rowIndex).val());
    		var authoriz_startdate = $.trim($authorizStartdate.eq(rowIndex).val());
    		var theAuthorizEnddate = $.trim($authorizEnddate.eq(rowIndex).val());
    		if(beAuthorizer!='' && authoriz_startdate!='' && theAuthorizEnddate!=''){
    			flag = true;
    			break;
    		}
    	}
    	if(!flag){
    		msg = msg+"第"+(i+1)+"行商标请输入至少一个授权级别的被授权人和授权起止时间<br>";
    	}
    }
    var trs = $("#sub-tbody").find("tr");
    for(var i=0,_len=trs.length;i<_len;i++){
    	var tr = trs.eq(i);
    	var authorizStartdate = $.trim(tr.find("input[name='authorizStartdate']").val());
    	var authorizEnddate = $.trim(tr.find("input[name='authorizEnddate']").val());
    	if(authorizStartdate!=""&&authorizEnddate!=""&&comparisonDate(authorizStartdate,authorizEnddate)){
    		msg = msg+"第"+(i+1)+"行授权结束日期必须大于授权开始日期！<br>";
    	}
    }
    /**
    var hash = {};
    for(var i in brandNoArrays){
    	if(hash[brandNoArrays[i]]){
    		msg = msg+"多个商标绑定了同一个品牌！<br>";
    		break;
    	}
    	hash[brandNoArrays[i]] = true;	
    }*/
    for(var i = 0; i< brandNoArrays.length;i++){
    	for(var j = 0; j< brandNoArrays.length;j++){
    		if(j!=i && brandNoArrays[i] == brandNoArrays[j] && deductionPointArrays[i] != deductionPointArrays[j]){
    			deductionPointArrays[j] = deductionPointArrays[i];
    			msg = msg+"第"+(i+1)+"行与第"+(j+1)+"行商标绑定的同一个品牌，扣点必须一致！<br>";
    		}
    	}
    }
    for(var i in brankStrs){
    	var flag = true;
    	for(var j in brandNoArrays){
    		if(brandNoArrays[j] == brankStrs[i]){
    			flag = false;
    		}
    	}
    	if(flag){
    		msg = msg+"所有勾选的品牌都必须有商标绑定！<br>";
    		break;
    	}
    }

	/**
	var brandNoArrays = [];
    brankStrs.sort();
    $("input[name='brandNo']").each(function(){
	   brandNoArrays.push($(this).val());	 
    });
    brandNoArrays.sort();
    if(brandNoArrays.length != brankStrs.length){
	  msg = msg+"勾选的品牌数量和授权商标数量不一致!<br>";
    }else{
    	for(var i=0,len=brankStrs.length;i<len;i++){
    		if(brandNoArrays[i] != brankStrs[i]){
    			msg = msg+"勾选的品牌和商标绑定的品牌必须一一对应!<br>";
    			break;
    		}
    	}
    }*/
    return [msg_brand,msg];
}

//添加分类权限设置
function addCat(){
	var selectedBrandInfos = $.trim($('#bankNameHidden').val());
	if (selectedBrandInfos.length <= 0) {
		alert('请先添加品牌!');
		return false;
	}
	
	var brandNoIndex = 1;
	var selectedBrandNos = [];
	$.each(selectedBrandInfos.split(';'), function(){
		selectedBrandNos[selectedBrandNos.length] = this.split('_')[brandNoIndex];
	});
	
    openwindow(basePath+'/yitiansystem/merchants/businessorder/to_addCat.sc?flag=1&brandNos=' + selectedBrandNos, 500, 200, '添加分类');
}

var uploader3 = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: basePath+'/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
      id:'#filePicker3',
      multiple:false
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileSingleSizeLimit:100*1024*1024
});
uploader3.on( 'fileQueued', function( file ) {
	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
	   && type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader3.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
	   return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
   $("#filePicker3").hide();
   $("#loading3").show();
});

uploader3.on( 'uploadSuccess', function( file,response) {
	$("#filePicker3").show();
    $("#loading3").hide();
	if(response.resultCode=="200"){
		response.type="3";
		new attachmentItem(response).appendTo("#attachment_3");
	}else{
    	ygdg.dialog.alert(response.msg);
	}
});

var uploader4 = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: basePath+'/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
      id:'#filePicker4',
      multiple:false
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileSingleSizeLimit:100*1024*1024
});
uploader4.on( 'fileQueued', function( file ) {
	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader4.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
	   return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
   $("#filePicker4").hide();
   $("#loading4").show();
});

uploader4.on( 'uploadSuccess', function( file,response) {
	$("#filePicker4").show();
    $("#loading4").hide();
	if(response.resultCode=="200"){
		response.type="4";
		new attachmentItem(response).appendTo("#attachment_4");
	}else{
    	ygdg.dialog.alert(response.msg);
	}
});
if(pageSrc =="natural"){
var uploader10 = WebUploader.create({
	// 选完文件后，是否自动上传。
	auto: true,
	// swf文件路径
	swf: basePath+'/webuploader/Uploader.swf',
	// 文件接收服务端。
	server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
	// 选择文件的按钮。可选。
	// 内部根据当前运行是创建，可能是input元素，也可能是flash.
	pick: {
		id:'#filePicker10',
		multiple:false
	},
	duplicate:1,   //不去重
	compress:false,  //压缩
	fileSingleSizeLimit:100*1024*1024
});
uploader10.on( 'fileQueued', function( file ) {
	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
		uploader4.removeFile(file);
		ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
		return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
	$("#filePicker10").hide();
	$("#loading10").show();
});


uploader10.on( 'uploadSuccess', function( file,response) {
	$("#filePicker10").show();
	$("#loading10").hide();
	if(response.resultCode=="200"){
		response.type="2";
		new attachmentItem(response).appendTo("#attachment_10");
	}else{
		ygdg.dialog.alert(response.msg);
	}
});
}
//type 附件类型 1：合同附件类型 2：资质附件类型 3：授权书附件类型 4:商标注册证附件类型
function attachmentItem(data){
	var type = data.type,fileName = data.fileName,realName = data.realName;
	var item = $("<div class='attachment_item'><input name='contract_attachment' type='hidden' value='"+type+";"+fileName+";"+realName+"'><span class='supplier-query-cont Blue ml5' title='"+fileName+"'>"+fileName+"</span><a href='javascript:void(0);'  class='link-del ml10 Blue'>删除</a></div>");
	return  item;
}
var index_global = 1;
function addSubTable(){
	//var index = $("#sub-tbody").find("tr.add-row").length+1;
	$(Subtable(index_global)).appendTo("#sub-tbody");
	$('#reg_start_'+index_global).calendar({format:'yyyy-MM-dd'});
	$('#reg_end_'+index_global).calendar({format:'yyyy-MM-dd'});
	for(var i=1;i<7;i++){
		$('#authoriz_startdate_'+i+'_'+index_global).calendar({format:'yyyy-MM-dd'});
		$('#authoriz_enddate_'+i+'_'+index_global).calendar({format:'yyyy-MM-dd'});
	}
	index_global++;
}

function Subtable(index){
	var numberArray = ["二","三","四","五","六"];
	var tablestr = '';
       tablestr = tablestr+'<tr class="add-row">'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="trademark" maxlength="100" id="trademark_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="hidden" name="brandNo" maxlength="50" id="brandNo_'+index+'" oldValue=""><p id="brandName_'+index+'"></p><a href="javascript:chooseBrand('+index+');" id="brand_'+index+'">立即绑定</a></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="deductionPoint" maxlength="3" id="deductionPoint_'+index+'"></td>'+  
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="authorizer" maxlength="50" id="authorizer_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="type" maxlength="10" id="type_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="registeredTrademark" maxlength="100" id="registeredTrademark_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" readonly="readonly" name="registeredStartDate" id="reg_start_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" readonly="readonly" name="registeredEndDate" id="reg_end_'+index+'"></td>'+
          '<td>一级授权</td>'+
          '<td>'+
           '<input class="table-text" type="text" name="beAuthorizer" maxlength="50"  id="beAuthorizer_1_'+index+'"></td>'+
          '<td>'+
             '<input class="table-text" type="text" readonly="readonly" name="authorizStartdate" readonly="readonly" id="authoriz_startdate_1_'+index+'"></td>'+
          '<td>'+
            '<input class="table-text" type="text" readonly="readonly" name="authorizEnddate" readonly="readonly" id="authoriz_enddate_1_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r"><a href="javascript:void(0)" class="del-tr">删除</a></td>'+
          '</tr>';
          for(var i=0;i<5;i++){
          	tablestr = tablestr +'<tr>'+
	         '<td>'+numberArray[i]+'级授权</td>'+
	          '<td>'+
	            '<input class="table-text" type="text" name="beAuthorizer" id="beAuthorizer_'+(i+2)+'_'+index+'"></td>'+
	          '<td>'+
	            '<input class="table-text" type="text" readonly="readonly" name="authorizStartdate" readonly="readonly" id="authoriz_startdate_'+(i+2)+'_'+index+'"></td>'+
	          '<td>'+
	            '<input class="table-text" type="text" readonly="readonly" name="authorizEnddate" readonly="readonly" id="authoriz_enddate_'+(i+2)+'_'+index+'"></td>'+
	        '</tr>';
          }
    return tablestr;
}

function clearAttachment(target){      
  	$(target).parent().remove();
}

$(".link-del").live("click",function(){// newly marked on 20151203
//	$(this).parent().remove();
	var $input = $(this).parent().find("input");
	
	if( $input && $input.val().indexOf(";")!=-1 ){
		var originalVal = $input.val();
		$input.val(originalVal+";-1");//加上删除标记 后台处理
	}
	
	$(this).parent().addClass('hidden');
});
  
  $(".del-tr").live("click",function(){
  	var index = $("#sub-tbody").find("tr.add-row").length;
	if(index<=1){
		 ygdg.dialog.alert("至少需要保留一行商标授权!");
		 return;
	}  	

  	 var tr = $(this).parent().parent();
  	 var brandNo = tr.find("input[name='brandNo']").attr("oldValue");
     if(!brandEditable && brandNo){
    	 ygdg.dialog.alert("商标授权已经绑定了品牌，不能删除!");
		 return;
     }
  	 //删除相邻元素
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 //最后删除自己
  	 tr.remove();
  });

$(function(){	  
  if(supplierContractId==""){
    addSubTable(); 
  }else{
    $.ajax({
      async : true,
      cache : false,
      type : 'GET',
      dataType : "json",
      url : basePath+"/yitiansystem/merchants/businessorder/getContractDetailAjax.sc?id="+supplierContractId,
      success : function(data) {
         var trademarkList =data.trademarkList;
         for(var i=0,_len=trademarkList.length;i<_len;i++){
            addSubTable(); 
            var trademark = trademarkList[i];
            $("#trademark_"+(i+1)).val(trademark.trademark);
            $("#brandNo_"+(i+1)).val(trademark.brandNo);
            $("#brandNo_"+(i+1)).attr('oldValue',trademark.brandNo);
            $("#brandName_"+(i+1)).html(trademark.brandName);
            if(!trademark.brandNo){
            	$("#brand_"+(i+1)).html("立即绑定");
            }else{
            	$("#brand_"+(i+1)).html("修改绑定");
            }
            if(!brandEditable && trademark.brandNo){
            	$("#brand_"+(i+1)).html("");
            	if(trademark.deductionPoint){
            		$("#deductionPoint_"+(i+1)).attr('readonly','readOnly');
            	}
            }
            
            $("#deductionPoint_"+(i+1)).val(trademark.deductionPoint == 0? '':trademark.deductionPoint);
            $("#authorizer_"+(i+1)).val(trademark.authorizer);
            $("#type_"+(i+1)).val(trademark.type);
            $("#registeredTrademark_"+(i+1)).val(trademark.registeredTrademark);
            $("#reg_start_"+(i+1)).val(trademark.registeredStartDate);
            $("#reg_end_"+(i+1)).val(trademark.registeredEndDate);
            for(var j=0,_sublen=trademark.trademarkSubList.length;j<_sublen;j++){
                var trademarkSub = trademark.trademarkSubList[j];
                $("#beAuthorizer_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.beAuthorizer);
                $("#authoriz_startdate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizStartdate);
                $("#authoriz_enddate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizEnddate);
            }
         }
      }
    });
  }
  $('#effective').calendar({format:'yyyy-MM-dd'});
  $('#failure').calendar({format:'yyyy-MM-dd'});
});
