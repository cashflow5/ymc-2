 var maxFileSize = '${maxFileSize}';
 // 正则校验
 var pattern_1 = /^[A-Za-z0-9]+$/;//只能输入数字和英文 
 // jquery.validate.js校验对象定义
 var v1;
 
$(function(){

	
		$("#password").keyup(function(event) {
			$("#loginPasswordError").hide();
			$("#loginPasswordPower").css("display","block");
		    PwdStrengthValidate(this);
		});
	

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

    // 保证金和平台使用费是否需要展示的初始化
    if($("[name=isNeedDeposit]:checked").val()=='0'){
		$('#readyToHideDeposit').hide();
		$('#deposit').val(0);
	}else{
		$('#readyToHideDeposit').show();
		//$('#deposit').val('');
	}
    
    if($("[name=isNeedUseFee]:checked").val()=='0'){
		$('#readyToHideUseFee').hide();
		$('#useFee').val(0);
	}else{
		$('#readyToHideUseFee').show();
		//$('#useFee').val('');
	}
    
    var isLastPageFirstLoad = true;
    // 信息填写页面，第二联的下一步按钮效果，js校验
    $('.btn-next.second').click(function(){
    	// jquery.validate.js 校验
    	if ( !v1.form()) {
    		//console.log('jquery.validate.js 校验未通过！');
    		return;
    	}
    	if (!formValidatePart2()) return;
  
    	//售后退换货地址赋值
    	var hidden_province_3 =  $('#province_3').find("option:selected").text();
    	var hidden_city_3 =  $('#city_3').find("option:selected").text();
    	var hidden_area_3 =  $('#area_3').find("option:selected").text();
    	$('#warehouseArea').val(hidden_province_3+"-"+hidden_city_3 + "-"+hidden_area_3 );
    
        if(isLastPageFirstLoad){
        	isLastPageFirstLoad = false;
	        var uploader1 = WebUploader.create({
	            // 选完文件后，是否自动上传。
	            auto: true,
	            // swf文件路径
	            swf: basePath+'/webuploader/Uploader.swf',
	            // 文件接收服务端。
	            server: basePath+"/yitiansystem/merchants/businessorder/attachmentUpload.sc",
	            // 选择文件的按钮。可选。
	            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	            pick: {
	              id:'#filePicker_1' 
	            },
	            duplicate:1,   //不去重
	            compress:false,  //压缩
	            fileSingleSizeLimit:100*1024*1024
	        });
	        uploader1.on( 'fileQueued', function( file ) {
	        	fileQueuedFunction( file,1,uploader1);
	        });
	
	        uploader1.on( 'uploadSuccess', function( file,response) {
	        	uploadSuccessFunction(file,response,1);
	        });
	        
	        //////////////////////////////////////////////////
	        
	        
        }
      	//登陆账号唯一性校验--只对于添加账号的操作(编辑商家的操作也会第一次添加账号) 
    	var supplierId = $('#supplierId').val();
    	var merchantUserId = $('#merchantUserId').val();
    	if(''==supplierId || ''==merchantUserId ){
    		var loginName = $('#loginName').val();
    		//判断用户名是否存在
    		$.ajax({ 
    			type: "post", 
    			url: basePath+"/yitiansystem/merchants/businessorder/exitsLoginAccount.sc", 
    			data: {loginAccount:loginName},
    			contentType: "application/x-www-form-urlencoded; charset=utf-8",
    			success: function(dt){
    				if("sucuess"==dt){//sucuess拼错了 暂时不改！
						$("#loginAccountError").html("登录帐号已经存在,请重新输入!");
						$("#loginName").focus();
    				    return false;
    				} else {
    					 var $this=$('.btn-next.second'),
    			            next_parent=$('.btn-next.second').parent().parent().parent();
    			        next_parent.next().removeClass('hide').siblings().addClass('hide');
    				}
    			}
    		});
    		
        	
    	}else{
    		 var $this=$('.btn-next.second'),
	            next_parent=$('.btn-next.second').parent().parent().parent();
	        next_parent.next().removeClass('hide').siblings().addClass('hide');
    	}
    	
    	
    });

    //上一步按钮
    $('.btn-prev').click(function(){
        var $this=$(this),
            next_parent=$(this).parent().parent().parent();
            next_parent.prev().removeClass('hide').siblings().addClass('hide');
    });
    
 
       // webuploader 批量初始化
      //////////////////////////////////////////////////
       
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
             

      // for 合同创建页面------------------------------------
	
	  var contractId = $("#contractId").val();
		 
	  var _isInputYougouWarehouse = $('#_isInputYougouWarehouse').val();
	  if (_isInputYougouWarehouse != '') {
	  	$("input[name='isInputYougouWarehouse'][value="+_isInputYougouWarehouse+"]").attr("checked",true);
	  }
	  var _setOfBooksCode = $('#_setOfBooksCode').val();
	  if (_setOfBooksCode != '') {
	  	$("#setOfBooksCode").find("option[value="+_setOfBooksCode+"]").attr("selected",true);
	  }
	  
	 
	  
	   
	  
	    // 合同页面-是否需缴纳费用的关联处理
	    $("input[name=isNeedDeposit]").live("click",function(){
	     
	  		if($("[name=isNeedDeposit]:checked").val()=='0'){
	  			$('#readyToHideDeposit').hide();
	  			$('#deposit').val(0);
	  		}else{
	  			$('#readyToHideDeposit').show();
	  			$('#deposit').val('');
	  		}
		  });
		  $("input[name=isNeedUseFee]").live("click",function(){
		  		if($("[name=isNeedUseFee]:checked").val()=='0'){
		  			$('#readyToHideUseFee').hide();
		  			$('#useFee').val(0);
		  		}else{
		  			$('#readyToHideUseFee').show();
		  			$('#useFee').val('');
		  		}
		  });
	  
		  // 页面一的校验
		    v1 = $("#submitForm").validate({
				rules:{
					"supplier":{
						required:true,
						stringCheck:true
					},
					"simpleName":{
						required:true,
						stringCheck:true
					},
					"businessRange":{
						required:true
					},
					"businessLicense":{
						required:true,
						isRightNumOrString:true
					},
					"isUseYougouWms":{
						required:true
					},
					"isUseERP":{
						required:true
					},
					"isNeedDeposit":{
						required:true
					},
					"isNeedUseFee":{
						required:true
					},
					"province_1":{
						required:true
					},
					"city_1":{
						required:true
					},
					"area_1":{
						required:true
					},
					"institutional":{
						required:true,
						isRightForInstitutional:true
					},
					"isNeedRenew":{
						required:true
					},
					"isInvoice":{
						required:true
					},
					"tallageNo":{
						required:true,
						isRightNumOrString:true
					},
					
					"taxRate":{
						required:true,
						lessThen100Num:true
					},
					"bank":{
						required:true,
						stringCheck:true
					},
					"contact":{
						required:true,
						stringCheck:true
					},
					
					"registerDetails":{
						required:true
					},
					"taxplayerType":{
						required:true
					},
					"invoiceAddress":{
						required:true
					},
					"invoicePhone":{
						required:true,
						phone:true
					},
					"clearAccount":{
						required:true,
						integer:true
					},
					"clearBank":{
						required:true,
						stringCheck:true
					},
					"isAddValueInvoice":{
						required:true
					},
					"account":{
						required:true,
						integer:true
					},
					"chiefMobilePhone":{
						required:true,
						mobile:true
					},
					"warehousePostcode":{
						required:true,
						zipCode:true
					},
					"chiefContact":{
						required:true,
						stringCheck:true
				    },
					"chiefTelePhone":{
						phone:true
					},
					"chiefEmail":{
						required:true,
						email:true  
					},
					"businessMobilePhone":{
						mobile:true
					},
					"businessTelePhone":{
						phone:true
					},
					"businessEmail":{
						email:true  
					},
					"afterSaleMobilePhone":{
						mobile:true
					},
					"afterSaleTelePhone":{
						phone:true
					},
					"afterSaleEmail":{
						email:true  
					},
					"financeMobilePhone":{
						mobile:true
					},
					"financeTelePhone":{
						phone:true
					},
					"financeEmail":{
						email:true  
					},
					"techMobilePhone":{
						mobile:true
					},
					"techTelePhone":{
						phone:true
					},
					"techEmail":{
						email:true  
					},
					"mobileCode":{
						required:true,
						mobile:true
					},
					"email":{
						required:true,
						email:true  
					},
					"consigneePhone":{
						required:true,
						phone:true
					},
					"loginName":{
						required:true,
						isRightfulString:true,
						maxlength:20 
					},
					"consigneeName":{
						required:true,
						stringCheck:true
					},
					"maxCouponAmount":{
						required:true,
						positiveInteger:true
					},
					"deposit":{
						required:true,
						integer:true
					},
					"useFee":{
						required:true,
						integer:true
					},
					"bankOwner":{
						required:true,
						stringCheck:true
					},
					"bankAccount":{
						required:true,
						integer:true
					},
					"bankName":{
						required:true,
						stringCheck:true
					},
					"warehouseAdress":{
						required:true
					}
				},
				messages:{
					"warehousePostcode":{
						required:"请输入正确的邮政编码！"
					}
				}
			}); 
		    
	   //地址绑定onchange
	    $('.address-select').on('change', function(){
	       var $this = $(this);
	       var thisName = $this.attr('id');
	       var thisSelectedValue = $this.find('option:selected').text();
	       $('#hidden_'+thisName).val(thisSelectedValue);
	   });	    
		    
});

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

//type 附件类型 1：合同附件类型 9：资质附件类型 3：授权书附件类型 4:商标注册证附件类型  5: ,6: ,7: , ......
function attachmentItem(data){
	var type = data.type,fileName = data.fileName,realName = data.realName;
	var item = $("<div class='attachment_item'><input name='contract_attachment' type='hidden' value='"+type+";"+fileName+";"+realName+"'><span class='supplier-query-cont Blue ml5' title='"+fileName+"'>"+fileName+"</span><a href='javascript:void(0);'  class='link-del ml10 Blue'>删除</a></div>");
	return  item;
}     

// 永久勾选的设置
function setEndForever(index){
  if(  $('#foreverFor_'+index).attr("checked")=='checked' ){
	  $('#createTimeEnd_'+index).val('2099-12-31');
  }else{
	  $('#createTimeEnd_'+index).val('');
  }
}

//重置文本框内容
function resertInput(){
  $(":input[type=text]").val("");
}

var checkPassword = function(password1,password2){
	if(password1.length>=8 && password1.length<=20){
		if(/^\d+$/.test(password1)){
			//全数字
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("密码不能为纯数字！");
			return false;
		}else if(/^[A-Za-z]+$/.test(password1)){
			//全字母
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("密码不能为纯字母！");
			return false;
		}else if(password1!=password2){
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("登录密码与确认密码不相同！");
			return false;
		}else if(/[(\ )]+/.test(password1)){
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("登录密码不能含有空格！");
			return false;
		}
		$('#loginPasswordError').html("");
		$("#loginPasswordPower").css("display","block");
		return true;
	}else{
		$("#loginPasswordPower").hide();
		$('#loginPasswordError').html("请输入8-20位密码！");
		return false;
	}
};

function formValidatePart2(){
	
	//先清空历史校验信息
	$(".error-message").html('');

	var errorCount =0;
	
	var loginAccount = $("#loginName").val();//登录账号
	var loginPassword= $("#password").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	var email = $("#email").val();
	var mobileCode = $("#mobileCode").val();
	var chiefContact = $("#chiefContact").val();
	var chiefMobilePhone = $("#chiefMobilePhone").val();// 手机 校验
	var chiefEmail = $("#chiefEmail").val();
	var consigneeName = $("#consigneeName").val();
	var consigneePhone = $("#consigneePhone").val();
	var province_3  = $('#province_3').val();// 省
	var city_3  = $('#city_3').val();// 市
	var area_3  = $('#area_3').val();// 区
	var warehousePostcode = $("#warehousePostcode").val();
	
	var merchantUserId = $('#merchantUserId').val();
    if(''==merchantUserId ){//新建则校验
		if(loginAccount=="" ){
			$("#loginAccountError").html("登录账号不能为空!");
			$("#loginName").focus();
			errorCount++;
		}
		if(loginPassword=="" ){
			$("#loginPasswordError").html("登录密码不能为空!");
			$("#password").focus();
			$("#loginPasswordPower").hide();
			errorCount++;
		}
		
		if(passwordTwo=="" ){
			$("#passwordTwoError").html("确认密码不能为空!");
			$("#passwordTwo").focus();
			errorCount++;
		}
		if(loginPassword.length<8 || loginPassword.length>20 ){
			$("#loginPasswordError").html("登录密码长度必须为8-20位!").show();
			$("#password").focus();
			$("#loginPasswordPower").hide();
			errorCount++;
		}
		if(loginPassword!=passwordTwo ){
			$("#passwordTwoError").html("登录密码和确认密码不一致!");
			$("#password").focus();
			errorCount++;
		}
	}
	if (email=='' || !verifyEmail(email)) {
		$("#emailError").html("请输入正确的邮箱！");
		$("#email").focus();
		errorCount++;
	} 
	//mobileCode
	if (mobileCode ==''  ) {
		$("#mobileCodeError").html("手机号码不能为空！");
		$("#mobileCode").focus();
		errorCount++;
	} 
	var errorFor_3 = "";
	if(province_3=="" || '-1'== province_3){
		errorFor_3=errorFor_3+"省不能为空!";
		$("#province_3").focus();
	}
	if(city_3 ==""|| '-1'==  city_3){
		errorFor_3=errorFor_3+"市不能为空!";
		$("#city_3").focus();
	}
	if(area_3=="" || '-1'==area_3 ){
		errorFor_3=errorFor_3+"区不能为空!";
		$("#area_3").focus();
	}
	if(""!=errorFor_3){
		$("#provinceError_3").html(errorFor_3);
		errorCount++;
	}
	// chiefContact
	if( chiefContact=="" ){
		$("#chiefContactError").html("店铺联系人 姓名不能为空!");
		$("#chiefContact").focus();
		errorCount++;
	}
	if( chiefMobilePhone==""  ){
		$("#chiefMobilePhoneError").html("请输入正确的手机号码！");
		$("#chiefMobilePhone").focus();
		errorCount++;
	}
	
	if( chiefEmail=="" ){
		$("#chiefEmailError").html("店铺联系人邮箱不能为空!");
		$("#chiefEmail").focus();
		errorCount++;
	}
	if( consigneeName==""  ){
		$("#consigneeNameError").html("退换货收货人 姓名不能为空!");
		$("#consigneeName").focus();
		errorCount++;
	}
	if( consigneePhone==""   ){
		$("#consigneePhoneError").html("请输入正确的电话号码！");
		$("#consigneePhone").focus();
		errorCount++;
	}
	//warehousePostcode
	if( warehousePostcode==""  ){
		$("#warehousePostcodeError").html("退换货收货人 邮编不能为空!");
		$("#warehousePostcode").focus();
		errorCount++;
	}
	if(errorCount>0){
	    return false;
	}else{
		return true;
	}
}

function formValidatePart1() {
	
	//先清空历史校验信息
	$(".error-message").html('');

	var createTimeStart_1 = $('#createTimeStart_1').val();//营业执照有效期起始
	var createTimeEnd_1 = $('#createTimeEnd_1').val();// 营业执照有效期截止
	var province_1  = $('#province_1').val();//注册地址 省
	var city_1  = $('#city_1').val();//注册地址 市
	var area_1  = $('#area_1').val();//注册地址 区
	var createTimeStart_2  = $('#createTimeStart_2').val();//组织机构代码证有效期起始
	var createTimeEnd_2  = $('#createTimeEnd_2').val();//组织机构代码证有效期截止
	var errorCount =0;
	// 附件-营业执照副本 校验
	if( $('#attachment_2').children().length == 0 ){
		$("#attachmentError_2").html("请上传营业执照副本!");
		errorCount++;
	}
	if( $('#attachment_5').children().length == 0 ){
		$("#attachmentError_5").html("请上传组织机构代码证!");
		errorCount++;
	}
	if( $('#attachment_6').children().length == 0 ){
		$("#attachmentError_6").html("请上传税务登记证!");
		errorCount++;
	}

	var dateError1 ="";
	if(createTimeStart_1=="" ){
		dateError1 = dateError1+ "起始日不能为空!";
		 $("#createTimeStart_1").focus();
	}
	if(createTimeEnd_1=="" ){
		dateError1 = dateError1+ "截止日不能为空!";
		 $("#createTimeEnd_1").focus();
	}
	if( createTimeStart_1!="" && createTimeEnd_1!="" && comparisonDate(createTimeStart_1,createTimeEnd_1) ){
		dateError1 = dateError1+"结束日期不能小于开始日期";
		 $("#createTimeEnd_1").focus();
	 }
	if( ""!=dateError1 ){
		 $("#createTimeStartError_1").html(dateError1);
		 errorCount++;
	}
	 
	var errorFor_1 = "";
	if(province_1=="" || '-1'== province_1){
		errorFor_1 = errorFor_1 + "省 不能为空!";
		$("#province_1").focus();
	}
	if(city_1==""|| '-1'==  city_1){
		errorFor_1 = errorFor_1 + "市 不能为空!";
		$("#city_1").focus();
	}
	if(area_1=="" || '-1'==area_1 ){
		errorFor_1 = errorFor_1 + "区 不能为空!";
		$("#area_1").focus();
	}
	if( errorFor_1!="" ){
		$("#provinceError_1").html(errorFor_1);
		errorCount++;
	}

	var dateError2 ="";
	if(createTimeStart_2=="" ){
		dateError2 = dateError2+ "起始日不能为空!";
		 $("#createTimeStart_2").focus();
	}
	if(createTimeEnd_2=="" ){
		dateError2 = dateError2+ "截止日不能为空!";
		 $("#createTimeEnd_2").focus();
	}
	if( createTimeStart_2!="" && createTimeEnd_2!="" && comparisonDate(createTimeStart_2,createTimeEnd_2) ){
		dateError2 = dateError2+"结束日期不能小于开始日期";
		 $("#createTimeEnd_2").focus();
	 }
	if( ""!=dateError2 ){
		 $("#createTimeStartError_2").html(dateError2);
		 errorCount++;
	}

	if(errorCount>0){
	    return false;
	}else{
		return true;
	}
}


//邮箱校验
function verifyEmail(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
}


function changeWms(value) {
	if("1"==value){
		$("input:radio[name=isUseYougouWms][value='1']").attr('checked','true');
	}else{
		$("input:radio[name=isUseYougouWms][value='0']").attr('checked','true');
	}
	if("0"==value){
	    $("#setOfBooksCode option[value='ZT20140903837400']").attr("selected", true); 
	}else{
		$("#setOfBooksCode option[value='']").attr("selected", true); 
	}
}



//添加合同信息
function formValidatePart3(curObj){
   
  //先清空历史校验信息
  $(".error-message").html('');
  
  var errorCount =0;
  var createTimeStart_3 = $('#createTimeStart_3').val();//合同有效期起始
  var createTimeEnd_3 = $('#createTimeEnd_3').val();//合同有效期截止
  //结算方式
  var clearingForm = $("#clearingForm").val();
  //合同ID
  var id=$("#contractId").val();
  //申报人
  var declarant = $.trim($("#declarant").val());
  var setOfBooksCode = $('#setOfBooksCode').find("option:selected").val();//成本套账名称
  if(!setOfBooksCode){
	  $("#setOfBooksCodeError").html("请选择成本套账名称!");
      errorCount++;
  }
//  var maxCouponAmount = $('#maxCouponAmount').val();
  var re = /[^\u4e00-\u9fa5]+$/; 
 
  if(clearingForm == 0){
	 $("#clearingFormError").html("请选择结算方式!");
	 errorCount++;
  }
  
  var contractDateError = '';
  if(createTimeStart_3=="" ){
     contractDateError = contractDateError+"合同开始日期不能为空!";
	 $("#createTimeStart_3").focus();
  }
  if(createTimeEnd_3=="" ){
	 contractDateError = contractDateError+"合同结束日期不能为空!";
	 $("#createTimeEnd_3").focus();
  }
  if( createTimeStart_3!="" && createTimeEnd_3!="" && comparisonDate(createTimeStart_3,createTimeEnd_3) ){
	  contractDateError = contractDateError+"合同结束日期不能小于合同开始日期";
	  $("#createTimeEnd_3").focus();
  }
  if( contractDateError!=''){
	  $("#createTimeStartError_3").html(contractDateError);
	  errorCount++;
  }
  
  if($("[name=isNeedDeposit]:checked").val()=='1'){
	  var deposit = $('#deposit').val();
	  if (deposit=='' || !/^([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*)$/.test(deposit) || deposit>9999999.99 ) {
		  $("#depositError").html("保证金金额不能为空，且只能为大于0且小于9999999.99的小数或者整数");
		  errorCount++;
	  }
  }
  if($("[name=isNeedUseFee]:checked").val()=='1'){
	  var useFee = $('#useFee').val();
	  if (useFee=='' || !/^([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*)$/.test(useFee) || useFee>9999999.99 ) {
		  $("#useFeeError").html("平台使用费金额不能为空，且只能为大于0且小于9999999.99的小数或者整数");
		  errorCount++;
	  }
  }
  if(declarant!=""&&re.test(declarant)){
      $("#declarantError").html("申报人只能填写中文");
	  errorCount++;
  }
   
   //校验合同附件
    if( $('#attachment_1').children().length == 0 ){
		$("#attachmentError_1").html("请上传合同电子版附件!");
		errorCount++;
	}
    if( $('#attachment_3').children().length == 0 ){
		$("#attachmentError_3").html("请上传授权书附件!");
		errorCount++;
	}
    if( $('#attachment_4').children().length == 0 ){
		$("#attachmentError_4").html("请上传商标注册证附件!");
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


function clearAttachment(target){      
  	$(target).parent().remove();
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

function nextButtonfirstFunction(isNew){
	
	var supplier = $("#supplier").val();
	$("#copy_supplier").html(supplier);
	    
}

//密码强度验证函数
function PwdStrengthValidate(obj) {
    var that = $(obj);
    var val = that.val(),
        li = $(".safetyStrength li");
    //特殊字符
    var reg = /[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
    var numberFlag = false;
    var letterFlag = false;
    var otherFlag = false;
    if(/[0-9]+/.test(val)){
    	numberFlag = true;
    }
    if(/[A-Za-z]+/.test(val)){
    	letterFlag = true;
    }
    if(reg.test(val)){
    	otherFlag = true;
    }
    if(val.length >= 6 && val.length<=20){
    	if (numberFlag && letterFlag && otherFlag) {
            li.attr("class", "").eq(2).addClass("pwdTall");
            li.eq(3).addClass('text').html('高');
        }else if ((numberFlag && !letterFlag && otherFlag) || 
    			(numberFlag && letterFlag && !otherFlag) || 
    			(!numberFlag && letterFlag && otherFlag)) {
            li.attr("class", "").eq(1).addClass("pwdMid");
            li.eq(3).addClass('text').html('中');
        }else{
        	li.attr("class", "").eq(0).addClass("pwdLow");
            li.eq(3).addClass('text').html('低');
        }
    }else{
    	li.attr("class", "").eq(0).addClass("pwdLow");
        li.eq(3).addClass('text').html('低');
    }
}


//地址控件被绑定的onchange事件-省 （区别于供应商商家创建页面的逻辑）
function checkTwo(number){
  var province=$('#province_'+number).val();
 
  if(province!=""){
	  	if(province=="-1"){
			$('#city_'+number).empty();
			$("<option value='-1'>请选择城市</option>").appendTo($('#city_'+number));
	  		
	  		$('#area_'+number).empty();
			$("<option value='-1'>请选择区县</option>").appendTo($('#area_'+number));
	  		return;
	  	}
	  	var cityDefault = $('#hidden_city_'+number).val();
	  	    //console.log(cityDefault);
		//根据省信息 加载市信息
		$.ajax({ 
			type: "post", 
			url: basePath+"/yitiansystem/merchants/businessorder/queryChildByLevelAndNo.sc?level=2&no=" + province, 
			success: function(result){
				if(result!=""){
				    $('#city_'+number).empty();
					result = result.replace(/(^\s*)|(\s*$)/g,'');
					var node = eval("("+result+")");
					$("<option value='-1'>请选择城市</option>").appendTo($('#city_'+number));
					for (i=0;i<node.length;i++){
						var area = node[i];
						var no=area.no;
						var name=area.name;
						
						if( cityDefault && cityDefault == name){
							$("<option value='"+no+"' selected>"+name+"</option>").appendTo($('#city_'+number));
						}else{
							$("<option value='"+no+"'>"+name+"</option>").appendTo($('#city_'+number));
						}
						
					}
					
					$('#area_'+number).empty();
					$("<option value='-1'>请选择区县</option>").appendTo($('#area_'+number));
					checkThree(number);
				}
			} 
		});
	   }
}
//地址控件被绑定的onchange事件-市 （区别于供应商商家创建页面的逻辑）
function checkThree(number){
	var city=$('#city_'+number).val();
    if(city!=""){
	  	if(city=="-1"){
	  		$('#area_'+number).empty();
			$("<option value='-1'>请选择区县</option>").appendTo($('#area_'+number));
			return;
	  	}
		var areaDefault = $('#hidden_area_'+number).val();
  	    //console.log(areaDefault);	  	
		//根据省信息 加载市信息
		$.ajax({ 
			type: "post", 
			url: basePath+"/yitiansystem/merchants/businessorder/queryChildByLevelAndNo.sc?level=3&no=" + city, 
			success: function(result){
				if(result!=""){
				    $('#area_'+number).empty();
					result = result.replace(/(^\s*)|(\s*$)/g,'');
					var node = eval("("+result+")");
					$("<option value='-1'>请选择区县</option>").appendTo($('#area_'+number));
					for (i=0;i<node.length;i++){
						var area = node[i];
						var no=area.no;
						var name=area.name;
						if( areaDefault && areaDefault == name){
							$("<option value='"+no+"' selected>"+name+"</option>").appendTo($('#area_'+number));
						}else{
							$("<option value='"+no+"'>"+name+"</option>").appendTo($('#area_'+number));
						}
					}
				}
			} 
		});
	   }
}

// 存草稿到缓存
function saveSegment(curObj){
	
	var supplier = $('#supplier').val();
	//品牌品类保存到隐藏域
	checkBrand();
	
	if(supplier&&$.trim(supplier)!=''){
		//console.log("saveSegment ~");
		
		$.ajax({
			async : true,
			cache : false,
			type: "POST",
			dataType : "html",
			data:$('#submitForm').serialize(),
			url : basePath+"/yitiansystem/merchants/manage/save_supplier_segment.sc",
			success: function(msg){
				$('.SegmentSaveInfo').each(function(){
					$this = $(this);
					$this.html(msg);
					window.parent.$('html,body').scrollTo($('.SegmentSaveInfo').offset().top);
				});
			}
	   });
	}
}
