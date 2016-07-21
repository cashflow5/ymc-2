	 var maxFileSize = '${maxFileSize}';

$(function(){
 
	// 日期控件格式化
    $('.calendar').each(function(index, el) {
        var number=parseInt(index+1);
       $('#createTimeStart_'+number).calendar({
            format: 'yyyy-MM-dd'
        }); 
        $('#createTimeEnd_'+number).calendar({
            format: 'yyyy-MM-dd'
        });
    });
    
     var uploader2 = WebUploader.create({
         // 选完文件后，是否自动上传。
         auto: true,
         // swf文件路径
         swf: basePath+'/webuploader/Uploader.swf',
         // 文件接收服务端。
         server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
         // 选择文件的按钮。可选。
         // 内部根据当前运行是创建，可能是input元素，也可能是flash.
         pick: {
           id:'#filePicker_2' 
         },
         duplicate:1,   //不去重
         compress:false,  //压缩
         fileSingleSizeLimit:100*1024*1024
     });
     uploader2.on( 'fileQueued', function( file ) {
     	fileQueuedFunction( file,2,uploader2);
     });

     uploader2.on( 'uploadSuccess', function( file,response) {
     	uploadSuccessFunction(file,response,2);
     });

     //////////////////////////////////////////////////
     
     var uploader5 = WebUploader.create({
         // 选完文件后，是否自动上传。
         auto: true,
         // swf文件路径
         swf: basePath+'/webuploader/Uploader.swf',
         // 文件接收服务端。
         server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
         // 选择文件的按钮。可选。
         // 内部根据当前运行是创建，可能是input元素，也可能是flash.
         pick: {
           id:'#filePicker_5' 
         },
         duplicate:1,   //不去重
         compress:false,  //压缩
         fileSingleSizeLimit:100*1024*1024
     });
     uploader5.on( 'fileQueued', function( file ) {
     	fileQueuedFunction( file,5,uploader5);
     });

     uploader5.on( 'uploadSuccess', function( file,response) {
     	uploadSuccessFunction(file,response,5);
     });
     
     //////////////////////////////////////////////////
     
     var uploader6 = WebUploader.create({
         // 选完文件后，是否自动上传。
         auto: true,
         // swf文件路径
         swf: basePath+'/webuploader/Uploader.swf',
         // 文件接收服务端。
         server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
         // 选择文件的按钮。可选。
         // 内部根据当前运行是创建，可能是input元素，也可能是flash.
         pick: {
           id:'#filePicker_6' 
         },
         duplicate:1,   //不去重
         compress:false,  //压缩
         fileSingleSizeLimit:100*1024*1024
     });
     uploader6.on( 'fileQueued', function( file ) {
     	fileQueuedFunction( file,6,uploader6);
     });

     uploader6.on( 'uploadSuccess', function( file,response) {
     	uploadSuccessFunction(file,response,6);
     });
     
     //////////////////////////////////////////////////
     
     var uploader7 = WebUploader.create({
         // 选完文件后，是否自动上传。
         auto: true,
         // swf文件路径
         swf: basePath+'/webuploader/Uploader.swf',
         // 文件接收服务端。
         server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
         // 选择文件的按钮。可选。
         // 内部根据当前运行是创建，可能是input元素，也可能是flash.
         pick: {
           id:'#filePicker_7' 
         },
         duplicate:1,   //不去重
         compress:false,  //压缩
         fileSingleSizeLimit:100*1024*1024
     });
     uploader7.on( 'fileQueued', function( file ) {
     	fileQueuedFunction( file,7,uploader7);
     });

     uploader7.on( 'uploadSuccess', function( file,response) {
     	uploadSuccessFunction(file,response,7);
     });
  
});

function updateNatural(curObj){

	  if( !formValidate() ){
		  return;
	  }
      curObj = $(curObj); 
	  curObj.css('cursor', 'no-drop');// 鼠标变为禁止点击
	  if($(curObj).data('isFirst')){// 根据开关 确定 是否return
		  return;
	  }
	  $(curObj).data('isFirst',true);// 开关  
	  $.ajax({
			async : true,
			cache : false,
			type: "POST",
			dataType : "html",
			data:$('#submitForm').serialize(),
			url : basePath+"/yitiansystem/merchants/manage/update_natural_and_auth.sc",
			success: function(msg){
				curObj.css('cursor', 'default');// 鼠标恢复
				 if (msg == 'success') {
				     	ygdg.dialog.alert("更新资质保存成功.");
				     	refreshpage();
				     	closewindow();
			     } else {
			     		$(curObj).data('isFirst',false);// 开关 不让return
			     		ygdg.dialog.alert('更新资质保存失败.'+msg);
			     }
			},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('更新资质提交失败.');
			}
	   });
	
}

function uploadSuccessFunction( file,response,number) {
	$('#filePicker_'+number).show();
    $('#loading_'+number).hide();
	if(response.resultCode=="200"){
		response.type=number;
		if(2==number){// 种类是2的改为9
			response.type=9;
		}
		new attachmentItem(response).appendTo('#attachment_'+number);
	}else{
    	ygdg.dialog.alert(response.msg);
	}
}

function fileQueuedFunction( file ,number,uploader) {
   	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg,或者打包rar格式上传。");
	   return;
	}
	if(file.size > maxFileSize){
		uploader.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
   $('.error-message').html("");	
   $('#filePicker_'+number).hide();
   $('#loading_'+number).show();
}


//type 附件类型 1：合同附件类型 9：资质附件类型 3：授权书附件类型 4:商标注册证附件类型  5: ,6: ,7: ,8: ......
function attachmentItem(data){// newly marked on 20151203
	var type = data.type,fileName = data.fileName,realName = data.realName;
	var item = $("<div class='attachment_item'><input name='contract_attachment' type='hidden' value='"+type+";"+fileName+";"+realName+"'><span class='supplier-query-cont Blue ml5' title='"+fileName+"'>"+fileName+"</span><a href='javascript:void(0);'  class='link-del ml10 Blue'>删除</a></div>");
	return  item;
}     

//永久勾选的设置
function setEndForever(index){
if(  $('#foreverFor_'+index).attr("checked")=='checked' ){
	  $('#createTimeEnd_'+index).val('2099-12-31');
}else{
	  $('#createTimeEnd_'+index).val('');
}
}

function comparisonDate(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes > lktimes) {
       
        return true;
    }
    else
        return false;

}

function verifyAttachment($attachment){
	if($attachment ){
		var  count = 0;
		$attachment.find("input").each(function(){
			
			var value = $(this).val().trim();
			if( value.indexOf(";-1")<0 || value.lastIndexOf(";-1")+3 < value.length ){
				count++;
			}
		});
		if(count<1){	// 没有附件
			return true; 
		}else{   // 有附件
			return false;
		}
	}else{
		return true;
	}
}

function formValidate(){
	
	
	//先清空历史校验信息
	$(".error-message").html('');
	var errorCount =0;
    var createTimeStart_1 = $('#createTimeStart_1').val();//
	var createTimeEnd_1 = $('#createTimeEnd_1').val();//
	var createTimeStart_2 = $('#createTimeStart_2').val();//
    var createTimeEnd_2 = $('#createTimeEnd_2').val();//
	 
    var dateError1 = '';
    if(createTimeStart_1=="" ){
	     dateError1 = dateError1+"营业执照开始日期不能为空!";
		 $("#createTimeStart_1").focus();
	}
	if(createTimeEnd_1=="" ){
		 dateError1 = dateError1+"营业执照结束日期不能为空!";
		 $("#createTimeEnd_1").focus();
	}
	if( createTimeStart_1!="" && createTimeEnd_1!="" && comparisonDate(createTimeStart_1,createTimeEnd_1) ){
		  dateError1 = dateError1+"营业执照结束日期不能小于营业执照开始日期";
		  $("#createTimeEnd_1").focus();
	}
	if( dateError1!=''){
	  $("#createTimeStartError_1").html(dateError1);
	  errorCount++;
	}
	  
     var dateError2 = '';
     
     if(createTimeStart_2=="" ){
	     dateError2 = dateError2+"组织机构代码证开始日期不能为空!";
		 $("#createTimeStart_2").focus();
	  }
	  if(createTimeEnd_2=="" ){
		 dateError2 = dateError2+"组织机构代码证结束日期不能为空!";
		 $("#createTimeEnd_2").focus();
	  }
	  if( createTimeStart_2!="" && createTimeEnd_2!="" && comparisonDate(createTimeStart_2,createTimeEnd_2) ){
		  dateError2 = dateError2+"组织机构代码证结束日期不能小于组织机构代码证开始日期";
		  $("#createTimeEnd_2").focus();
	  }
	  if( dateError2!=''){
		  $("#createTimeStartError_2").html(dateError2);
		  errorCount++;
	  }
	  
	  // 附件校验
	  
	  if( verifyAttachment($('#attachment_3')) ){
			$("#attachmentError_3").html("请上传授权书附件!");
			errorCount++;
	  }
	  if( verifyAttachment($('#attachment_4')) ){
			$("#attachmentError_4").html("请上传商标注册证附件!");
			errorCount++;
	  }
	  // 附件-营业执照副本 校验
		if(  verifyAttachment($('#attachment_2'))){
			$("#attachmentError_2").html("请上传营业执照副本!");
			errorCount++;
		}
		if(  verifyAttachment($('#attachment_5')) ){
			$("#attachmentError_5").html("请上传组织机构代码证!");
			errorCount++;
		}
		if( verifyAttachment($('#attachment_6')) ){
			$("#attachmentError_6").html("请上传税务登记证!");
			errorCount++;
		}
	 
		var checkResult = checkBrand();
		if(checkResult[0] != ''){
			$("#brandNoError").html(checkResult[0]);
			errorCount++;
		}
		if(checkResult[1] != ''){
			$("#subTable_error").html(checkResult[1]);
			errorCount++;
		}
	  
	  if(errorCount>0){
	      return false;
	  }else{
		  return true;
	  }
}
