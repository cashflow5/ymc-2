jQuery.validator.addMethod("SpecialSymbols", function(value, element) {var chrnum = /[(\#)(\$)(\%)(\^)(\*)(\+)(\=)(\<)(\>)]+/;return this.optional(element) || !chrnum.test(value); }, "不能包含#,$,%,^,*,(,),+,=,<,>等符号");
jQuery.validator.addMethod("Real", function(value, element) {var chrnum = /^[\w\u4E00-\u9FA5\uF900-\uFA2D]*$/;return this.optional(element) || chrnum.test(value); }, "真实姓名只能包括数字,字母,下划线与中文");
jQuery.validator.addMethod("Tel", function(value, element) {var chrnum = /^(13|14|15|18)[0-9]{9}$/;return this.optional(element) || chrnum.test(value); }, "非法的手机号码");
jQuery.validator.addMethod("Mobile", function(value, element) {var chrnum = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;return this.optional(element) || chrnum.test(value); }, "非法的电话号码");
jQuery.validator.addMethod("QQNum", function(value, element) {var chrnum = /^[1-9][0-9]{4,}/;return this.optional(element) || chrnum.test(value); }, "非法的QQ号码");
$(document).ready(function(){
	var rules = {
			username : {
		 		required: true,
		 		rangelength:[2,20],
		 		Real:[]
		    },
		    loginName : {
			    required: true,
			    rangelength:[2,20],
			    Real:[]
		    },
		    loginPassword:{
		    	required: true,
		    	rangelength:[6,16],
		    	SpecialSymbols:[]
		    },
		    checkloginPassword:{
		    	required: true,
		    	equalTo:"#loginPassword"
		    },
		    telPhone:{
		    	Tel:[]
		    },
		    mobilePhone:{
		    	Mobile:[]
		    },
		    email:{
		    	email:true
		    },
		    qqNum:{
		    	QQNum:[]
		    },
		    msnNum:{
		    	SpecialSymbols:[]
		    }
	 };
	 validator = $("#systemUserForm").validate({
	 	rules: rules,
	 	messages: {
		 	username : {
	 			required: "请输入真实姓名",
	 			rangelength: jQuery.validator.format("请输入一个长度介于{0}和{1}之间的字符串")
	 		},
	 		loginName : {
	 			required: "请输入登录用户名",
	 			rangelength: jQuery.validator.format("请输入一个长度介于{0}和{1}之间的字符串")
	 		},
	 		loginPassword:{
	 			required: "请输入登录密码",
	 			rangelength: jQuery.validator.format("密码长度介于{0}和{1}之间")
	 		},
	 		checkloginPassword:{
	 			required: "请输入确认密码",
	 			equalTo: "密码不一致,请确认后重新输入"
	 		}
	 	},
	 	onkeyup : true,
	 	focusInvalid: true,
	 	errorPlacement: function(error, element) {
	 		var errorHint=$("#"+element.attr("id")+"_tips");
	 		if(error.text()==""){
	 			errorHint.html('&nbsp;').removeClass("errorHint").addClass("successHint");
	 		}else{
	 			errorHint.text(error.text()).removeClass("successHint").addClass("errorHint");
	 		}
	     },
	 	success: function(element) {
	 	},
	 	submitHandler: function(form) {
	 		submitForm(form);
	 	}
	 });
});

$(function(){
	$("input[name='isSupplier']").click(function(){
		if(this.value=="0"){
			$("#supplier").show();
			$("#department").hide();
			$("#organizName").val("");
		}else{ 	
			$("#supplier").hide();
			document.getElementById("supplierCode").selectedIndex=0;
			$("#department").show();
		}
	});
});

/**
 * 提交表单
 */
function submitForm(form){
	if(!validator.form()){
		return;
	}
	form.submit();
}


