var flag = true;
function validateform() {

	if($("#expressNo").val()==""){
		 addCodeClass("onerror").html("请输入物流公司编号！");
            flag = false;
		}
	if($("#expressName").val()==""){
		 addNameClass("onerror").html("请输入物流公司名称！");
            flag = false;
		}
    return flag;
}


var config = {
    form : "myform",
    submit : validateform,
    fields : [ /*{
	name : 'expressName',
	allownull : false,
	regExp : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/,
	defaultMsg : '请输入快递公司名称',
	focusMsg : '请输入快递公司名称',
	errorMsg : '快递公司名称只能有2-30个汉字、数字、字母、下划线，不能以下划线开头和结尾',
	rightMsg : '快递公司名称输入合法',
	msgTip : 'expressNametip'
    },*/
	
	{
	name : 'cost',
	allownull : false,
	//regExp : /^\d+(\.\d*)?$/,
	regExp :  /^([0-9]{1,9}|0)(\.\d{2})?$/,
	defaultMsg : '',
	focusMsg : '请输入到付默认费用',
	errorMsg : '到付默认费用输入错误,必须是数字且小位数点前位数最大9位。(如：999.01) ',
	rightMsg : '到付默认费用输入合法',
	msgTip : 'costtip'
    },
	{
	name : 'no',
	allownull : false,
	//regExp : /^\d+(\.\d*)?$/,
		regExp : /^\+?[1-9][0-9]{0,3}$/,
	defaultMsg : '',
	focusMsg : '请输入顺序号',
	errorMsg : '顺序号输入错误,必须是数字且小位数点前位数最大4位。',
	rightMsg : '顺序号输入合法',
	msgTip : 'notip'
    }]
}

Tool.onReady( function() {
    var f = new Fw(config);
    f.register();
});



$(document).ready( function() {
//  $("#expressNametip").addClass("onshow").html("请输入快递公司名称");
 //   $("#expressNotip").addClass("onshow").html("请输入快递公司编号");
});

function addNameClass(cs) {
    var name = $("#expressNametip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}
function addCodeClass(cs) {
    var name = $("#expressNotip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}


//验证编号是否被占用
function checkExpressCode() {
    var expressName = $("#expressName").val();
    var expressNo = $("#expressNo").val();
	var id=$("#id").val();
    var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
    if (!regex.exec(expressNo)) {
	addNameClass("onerror").html("快递公司编号只能由2-30个字符组成");
	msgTip : 'expressNotip'
	flag = false;
	return false;
    }
    $.ajax( {
	type : "POST",
	url : "checkExpressCode.sc",
	data : {
	    "expressNo" : expressNo,"expressName":"","id":""
	},
	success : function(msg) {
		//alert(msg);
	    if ("true" == msg) {
		addCodeClass("onerror").html("快递公司编号已经存在");
		flag = false;
	    }
	    if ("false" == msg) {
		addCodeClass("oncorrect").html("快递公司编号可以使用");
		flag = true;
	    }
	}
    });
}
//验证名称是否被占用
function checkExpressname() {
    var expressName = $("#expressName").val();
    var expressNo = $("#expressNo").val();
	var id=$("#id").val();
    var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
    if (!regex.exec(expressName)) {
	addNameClass("onerror").html("快递公司名称只能由2-30个字符组成");
	msgTip : 'expressNotip'
	flag = false;
	return false;
    }
    $.ajax( {
	type : "POST",
	url : "checkExpressCode.sc",
	data : {
	    "expressNo":"","expressName":expressName,"id":id
	},
	success : function(msg) {
	//	alert(msg);
	    if ("true" == msg) {
		addNameClass("onerror").html("快递公司名称已经存在");
		flag = false;
	    }
	    if ("false" == msg) {
		addNameClass("oncorrect").html("快递公司名称可以使用");
		flag = true;
	    }
	}
    });
}

function addRemarktClass(cs) {
    var name = $("#remarktip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}


