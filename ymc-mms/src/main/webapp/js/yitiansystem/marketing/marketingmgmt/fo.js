/**
 * conf: 1.input 需要验证的文本框 2.vType 类型[1:非空+长度 2:非空+格式 3:可空+格式] (详细定义长度、格式在下方）
 * a:非空+长度 b:非空+格式 c:可空+格式
 * 
 * //----------------------------[长度、格式等其他定义]，如有需要继续添加 长度:vLength 例如:5 格式:format
 * 例如:FoReg.email email:邮箱,point:金额、小数、折扣,count:数量、正整数}
 */
var Fo = function(config){
	this.config = config;
	
	this.setConfig = function(config){
		this.config = config;
	};

	this.validate = function(){
		var inputObj = this.config.input;
		var inputVal = inputObj.value;
		//modify by ZhuangRuibo at 2011-08-09
		var inputLen = "";
		try{
			inputLen = inputVal.trim().length;
		}catch(e){
			
		}
		var result = false;
		
	 if(this.config.vType == 1){//非空+长度

		var vLen = this.config.vLength;// 验证长度(数组)
		
		if(inputLen>0){
			var tmpResult1 = false;
			var tmpResult2 = false;
			if(vLen[0]&&vLen[0]!=null&&vLen[0]<=inputLen){
				tmpResult1 = true;
			}

			if(vLen[1]&&vLen[1]!=null&&vLen[1]>=inputLen){
				tmpResult2 = true;
			}

			if(tmpResult1&&tmpResult2){
				result = true;
			}
		}

		
	}else if(this.config.vType == 2){//非空+格式
		if(inputLen>0 && this.config.format(inputVal)){
			result	= true;
		}

	}else if(this.config.vType == 3){//可空+格式
		if(inputLen <=0){
			result = true;
		}else{
			if(this.config.format(inputVal)){
				result	= true;
			}
		}
	}
	 
	if(!result){FoColor.error(inputObj);}
	else{FoColor.success(inputObj);}
	
	return result;

	};

};

var FoReg = {
	email_reg:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,//email
	email:function(val){
		return FoReg.email_reg.test(val);
	},
	number_reg2:/^[0-9]\d*$/,//正整数(可为0)
	number2:function(val){
		return FoReg.number_reg2.test(val);
	},
	number_reg:/^[1-9]\d*$/,//正整数
	number:function(val){
		return FoReg.number_reg.test(val);
	},
	point_reg:/^(([0-9]+.[0-9]{1,2})|([0-9]*[1-9][0-9]*.[0-9]{1,2})|([0-9]*[0-9]{1,2}))$/,
	//point_reg:/^[1-9]\\d*.\\d*|0.\\d*[1-9]|\d+$/,//浮点数
	point:function(val){
		if(FoReg.point_reg.test(val)){
			if(parseFloat(val)>0){
				return true;
			}else{
				return false;
			}
		}
	},
	discount_reg1:/^([1-9]{1}\.[0-9]{1})$/,
	discount_reg2:/^[1-9]$/,
	point2:function(val){
		//alert((FoReg.discount_reg1.test(val)||FoReg.discount_reg2.test(val)));
		return (FoReg.discount_reg1.test(val)||FoReg.discount_reg2.test(val));
	},
	zipcode_reg:/^\d{6}$/,//邮编
	zipcode:function(val){
		return FoReg.zipcode_reg.test(val);
	},
	activeName_reg:/^[\u4e00-\u9fa5-A-Za-z0-9_/+$/i,//活动名称
	activeName:function(val){
		return FoReg.activeName_reg.test(val);
	},
	couponNumber_reg:/^[A-Za-z0-9]+$/,//优惠券ID
	couponNumber:function(val){
		return FoReg.couponNumber_reg.test(val);
	}
	
};


// Input控件验证-[颜色定义]
var FoColor = {
	success:function(inputObj){
		inputObj.className = 'fo_border_success';
	},
	error  :function(inputObj){
		inputObj.className = 'fo_border_error';
	},
	need   :function(inputObj){
		inputObj.className = 'fo_border_need';
	}
};

var FoToolkit = {
	//创建hidden隐藏域名称为:控件“id+验证方法名称”
	create:function(inputId,validateMethodName,val){
		var isExist = (getByID(inputId+"T"+validateMethodName)==null?false:true);
		if(!isExist){
			if(val&&val!=undefined&&val!=null){
				$("#fo_validate_container").append("<input type='hidden' id='"+inputId+"T"+validateMethodName+"' value='"+val+"'/>");
			}else{
				$("#fo_validate_container").append("<input type='hidden' id='"+inputId+"T"+validateMethodName+"' value='0'/>");
			}
		}
	},
	remove:function(inputId,validateMethodName){
		$("#"+inputId+"T"+validateMethodName).remove();
	},
	doExe:function(state,inputId,validateMethodName){
		if(state) $("#"+inputId+"T"+validateMethodName).val("1");
		else      $("#"+inputId+"T"+validateMethodName).val("0");
		
		return state;
	},
	execute:function(call_fn){
		var result = true;
		if(call_fn()){
			$("#fo_validate_container :input").each(function(i,input){
				if(input.value=="0"){
					alert("活动部分输入错误,如:活动名称、购买限制等必填内容,请确认后再次保存!");
					result=false;
					return false;
				}
			});
		}else{
			result = false;
		}
		//alert("result:"+result);
		return result;
	}
};

// -----------------------------------------[工具方法]

// 获取小数后多少位
function FloatFloor(exp1, exp2){   
    var n1 = Math.round(exp1); // 四舍五入
    var n2 = Math.round(exp2); // 四舍五入
    var rslt = n1/n2; // 除
    rslt = Math.floor(rslt); // 返回小于等于原rslt的最大整数。
    return rslt;   
}

//字符串截取
function subString(str,len){
	var strlen = 0; 
	var s = "";
	for(var i = 0;i < str.length;i++){
		if(str.charCodeAt(i) > 128){strlen += 2;}
		else{strlen++;}
		s += str.charAt(i);
		if(strlen >= len){return s ;}
	}
	return s;
}

//-----------------------------------------[扩展方法]


String.prototype.trim = function(){
	return this.replace(/[' ']/gi,'');
};

function setClassName(id,className){
	try{
		document.getElementById(id).className = className;
	}catch(e){
		alert("未发现id:"+id+"的元素!");
	}
}

function setClassName2(obj,className){
	//alert("obj:"+obj);
	if(obj && obj != null) return;
	obj.className = className;
}

function getByID(id){
	return document.getElementById(id);
}
function getByName(name){
	return document.getElementsByName(name);
}