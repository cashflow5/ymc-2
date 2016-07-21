var flag = true;
function validateform() {
    return flag;
}
var config = {
    form : "warehouse",
    submit : validateform,
    fields : [ {
	name : 'warehouseAcreage',
	allownull : false,
	//regExp : /^\d+(\.\d*)?$/,
	regExp :  /^([0-9]{1,9}|0)(\.\d{2})?$/,
	defaultMsg : '',
	focusMsg : '请输入仓库面积',
	errorMsg : '仓库面积输入错误,必须是数字且小位数点前位数最大9位。(如：999.01) ',
	rightMsg : '仓库面积输入合法',
	msgTip : 'warehouseAcreagetip'
    }, {
	name : 'warehouseAddress',
	allownull : false,
	regExp : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,50}$/,
	defaultMsg : '',
	focusMsg : '请输入仓库地址',
	errorMsg : '仓库地址只能有2-50个汉字、数字、字母、下划线，不能以下划线开头和结尾',
	rightMsg : '仓库地址输入合法',
	msgTip : 'warehouseAddresstip'
    }, {
	name : 'contact',
	allownull : false,
	regExp : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/,
	defaultMsg : '',
	focusMsg : '请输入联系人名称',
	errorMsg : '联系人名称只能有2-30个汉字、数字、字母、下划线，不能以下划线开头和结尾',
	rightMsg : '联系人名称输入合法',
	msgTip : 'contacttip'
    }, {
	name : 'telPhone',
	allownull : false,
	regExp : /^(\d{3}-?\d{7,8}|\d{4}-?\d{7,8}|\d{7,8})$/,
	defaultMsg : '',
	focusMsg : '请输入电话号码',
	errorMsg : '电话号码输入错误 例：0755-88888888',
	rightMsg : '电话号码输入合法',
	msgTip : 'telPhonetip'
    }, {
	name : 'mobilePhone',
	allownull : false,
	regExp : /^1[3|4|5|8][0-9]\d{8}$/,
	defaultMsg : '',
	focusMsg : '请输入手机号码',
	errorMsg : '手机号码输入错误 例：13700000000',
	rightMsg : '手机号码输入合法',
	msgTip : 'machinePhonetip'
    }, {
	name : 'email',
	allownull : false,
	regExp : /^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/,
	defaultMsg : '',
	focusMsg : '请输入邮件地址',
	errorMsg : '邮件地址输入错误 例：ss@163.com',
	rightMsg : '邮件地址输入合法',
	msgTip : 'emailtip'
    },{
    name : 'inventoryRatio',
    allownull : false,
    regExp :/^([0][.]\d{2}|[1])$/,
    defaultMsg : '请输入库存比例',
    focusMsg : '请输入库存比例',
    errorMsg : '库存比例输入错误(0.00-0.99或者1)',
    rightMsg : '库存比例输入合法',
    msgTip : 'inventoryRatiotip'
    }]
}
Tool.onReady( function() {
    var f = new Fw(config);
    f.register();
});

function deldivCkaccno(){
    var isoutwarehouse = $("#selIsoutwarehouse").val();
    if(selIsoutwarehouse == 1){
        var name = $("#ckaccnotip");
        name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    }
}

function addCkaccnoClass(cs) {
    var name = $("#ckaccnotip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}

function addNameClass(cs) {
    var name = $("#warehouseNametip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}
//验证名称是否被占用
function validatorWarehouseName() {
	
    var warehouseName = $("#textfield2").val();
    var warehouseCode = $("#warehouseCode").val();
    var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
    if (!regex.exec(warehouseName)) {
	addNameClass("onerror").html("仓库名称只能由2-30个字符组成");
	msgTip : 'warehouseNametip'
	flag = false;
	return false;
    }
    $.ajax( {
	type : "POST",
	url : "validateWarehouseName.sc",
	data : {
	    "warehouseName" : warehouseName,
	    "warehouseCode" : warehouseCode
	},
	success : function(msg) {
	    if ("true" == msg) {
		addNameClass("onerror").html("仓库名称已经存在");
		flag = false;
	    }
	    if ("false" == msg) {
		addNameClass("oncorrect").html("仓库名称可以使用");
		flag = true;
	    }
	}
    });
}
//验证仓库标识是否被占用
function validatorCkaccno() {
    var ckaccno = $("#textfield3").val();
    var warehouseCode = $("#warehouseCode").val();
    var regex = /^[A-Z]{1}$/;
    if (!regex.exec(ckaccno)) {
    addCkaccnoClass("onerror").html("仓库标识只能是大写[A-Z]1个字符");
    flag = false;
    return false;
    }
    
    $.ajax( {
    type : "POST",
    url : "valickaccno.sc",
    data : {
        "ckaccno" : ckaccno,
        "warehouseCode" : warehouseCode
    },
    success : function(msg) {
        if ("true" == msg) {
        addCkaccnoClass("onerror").html("仓库标识已被占用");
        flag = false;
        }
        if ("false" == msg) {
        addCkaccnoClass("oncorrect").html("仓库标识可以使用");
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
//验证描述内容是否超长
function checkRemark()
{
  	var remarkText=document.getElementById("remark").value;
	if (remarkText.length>250) {
	  	addRemarktClass("onerror").html("备注内容只能输入250个字符");
        flag = false;
	}
	else{
		addRemarktClass("oncorrect").html("备注内容输入正确");
		 flag =true;
		}		
			
 /*   if (remarkText.length>250) {
	   alert("备注内容共["+remarkText.length+"]个字符只能输入250个字符");
	$("#submitbutton").attr("disabled","disabled");
	}
	else{
		$("#submitbutton").attr("disabled","");
		}*/
}