/**
 * storageUtils.js
 * @author dsy
 * 
 */
if(!window.d || !d.init) {
	window.d = {};
	d.toString = valueOf = function() {return "d";};
(function() {
	var cur = this;
	this.DOM = {  
	    ID : ID,  
	    N : function(_) {return document.getElementsByName(_);},  
	    T : function(_) {return document.getElementsByTagName(_)},
	    create : function() { return function() { this.initialize.apply(this,arguments);}},  
		extend : function(destination, source){for (var properties in source) destination[properties] = source[properties]; },
	    bind : function(obj,fun) { return function() {fun.apply(obj,arguments);}},  
	    each : function(list,fun) {for(var i = 0,len = list.length; i < len; i++) fun(list[i],i)}
	}
	this.StringBuffer = StringBuffer;
	this.subForm = {
		NF : function(formName) {
			var submitForm = document.createElement("form");
			submitForm.name = formName;
		    document.body.appendChild(submitForm);
		    submitForm.method = "POST";
		    return submitForm;
		},
		CFE : function(inputForm, inputObj,inputName) {
			var newElement = null;
			var objValue = null;
			if(!inputObj.selectedindex) objValue = typeof(inputObj.value) == "undefined" ? inputObj : inputObj.value;
			else objValue = inputObj.options[inputObj.selectedindex].value
			//alert("objValue : "+objValue);
			//alert("inputName.id : "+inputObj.id);
			//alert("inputName : "+typeof(inputName));
			inputName = typeof(inputName) == "undefined"&&!(typeof(inputName) == "string") ? inputObj.id : inputName;
			//alert("inputName : "+inputName);
			newElement = document.createElement("input");
			newElement.setAttribute("id",inputName);
		    newElement.setAttribute("name",inputName);
		    newElement.setAttribute("type","hidden");
		    newElement.setAttribute("value",objValue);
		    inputForm.appendChild(newElement);
		}	
	};
	this.tabUtils = function(options) {
		this.init(options);
	};
	this.tabUtils.prototype = {
		init: function(init) {
			options = init || {};
			this.id = options.id; //table id作为_LISTCOUNT前缀对象
			this.trClassName = options.className || "";
			this.dataValues = options.dataValues || [];
			this.index = options.initIndex || true;
		},
		insertRow : function() {
			var cur = this;
			var targetTable = ID(this.id);
			var objRow = targetTable.insertRow(targetTable.rows.length);
			objRow.className = this.trClassName;
			var objCell = null;
			for(var i = 0; i <cur.dataValues.length;i++) {
				objCell = objRow.insertCell(i);
				objCell.innerHTML = genInput("text",cur.id,cur.dataValues[i]);//货品编码
			}
			objCell = objRow.insertCell(cur.dataValues.length);
			objCell.className = "td0";
			objCell.innerHTML = "<a id=\"deleteFund\" href=\"javascript:void(null)\" onclick='deleteRow(this)'>删除</a>";
		},
		 //重新构建索引
		initIndex :function initIndex(){
			var table = ID(this.id);
			for(var i=1;i<table.rows.length;i++){
				table.rows[i].id = i;
		    	var rowCells=table.rows[i].cells
		    	var indexId=i-1;	
		    	for(var j=0;j<rowCells.length;j++){
		    		//重新构建索引
		    		if(rowCells[j].childNodes[0]!=null){
			    		var rowTr = rowCells[j].childNodes[0],tempInput,tempEx;
						//此处为兼容FF
						if(rowTr.nodeType == 3) {
							//此table只有第一个td作隐藏ID操作
							var input = rowCells[j].getElementsByTagName("input");
							for(var o = 0; o < input.length; o++) {
								var tempEx = input[o];
								var id = tempEx.id+indexId;tempEx.id=id;tempEx.name=id;
							}
						} else {
							tempInput = rowCells[j].childNodes;
							for(var o = 0; o < tempInput.length; o++) {
								var tempEx = tempInput[o];
								if(!tempEx.tagName) continue;
								if(tempEx.tagName.toLowerCase() == "input") {
									//alert(tempEx.outerHTML);
									var id = tempEx.id+indexId;tempEx.id=id;
									tempEx.name=id;
								}
							}
						}
			    	}
					
		    	}
		    }
			ID(this.id+"_LISTCOUNT").value = ((table.rows.length - 1) == 0) ? "" :(table.rows.length - 1);//此处要减掉标题 行
		},
		submitForm : function(formName,actionName,method) {
				if(this.index) {
					this.initIndex();
					this.index = false;
				}
				//alert("做了初始化");
				formName.action = actionName;
				formName.method = method;
				formName.submit();
		}
	};
	this.init = true;
}).call(d)	
}




























































/*************************************************************************/
function ID() {
	if (arguments.length == 0) return false;
    var Elements = new Array();
    for (var i = 0; i < arguments.length; i++) {
        var Element = arguments[i];
        if (typeof(Element) == 'string')  var checkObj = document.getElementsByName(Element);
        var objLength = checkObj.length;
        if (objLength > 1) {
            for (var j = 0; j < objLength; j++) Elements.push(checkObj[j]);
            return Elements;
        }  else  Element = document.getElementById(Element);
        if (arguments.length == 1)   return Element;
        Elements.push(Element);
    }
    return Elements;
}
function StringBuffer() {
	this._aStr = [];
}
StringBuffer.prototype.add = function (str) {
	this._aStr.push(str);
	return this;
};
StringBuffer.prototype.toString = function () {
	return this._aStr.join("");
};

function deleteRow(trObj){
    var rowObj = trObj.parentNode.parentNode;
    var targetTable = rowObj.parentNode;
    targetTable.removeChild(rowObj);
    //alert(targetTable.outerHTML);//FF下没有 outerHTML
    //var trIndex = rowObj.rowIndex;
    //targetTable.deleteRow(trIndex);//到这里出错,没反应了
}

/*************************************************************************/
/*By dsy @Date 20110501 进行代码重构*/
/**
 * @desc: 生成指定类型的Input输入框架
 * @param type:类型；
 * @param id : input id
 * @param tmname : {
 * 		idname:idname, 列名称
 * 	  	style:style,　列风格用于控制高，宽等属性
 * 	  	className:className,
 * 	  	eventAction:eventmethod}　用于单个input控件事件的
 * @param value : 控制值
 */
function genInput(type,id,tmname,value) {
	value = typeof(value) == 'undefined' ? "" : value;
	var idname = typeof tmname.idname == "undefined" ? "" : tmname.idname;
	var styleInput = typeof tmname.style == "undefined" ? "" : tmname.style; 
	var className = typeof tmname.className == "undefined" ? "" : tmname.className; 
	var tempStr = new StringBuffer();
	tempStr.add("<input type='")
	       .add(type)
	       .add("' ")
	       .add("style='")
		   .add(styleInput)
		   .add("' ")
	       .add("class='")
		   .add(className)
		   .add("' ")
		   .add("id='")
		   .add(id)
		   .add("_")
		   .add(idname)
		   .add("' ")
		   .add("name='")
		   .add(id)
		   .add("_")
		   .add(idname)
		   .add("' ")
		   .add("value='")
		   .add(value)
		   .add("' ")
		   .add("/>");
	 return tempStr.toString();
}

Array.prototype.remove=function(dx){
	  if(isNaN(dx)||dx>this.length){return false;}
	  for(var i=0,n=0;i<this.length;i++)   if(this[i]!=this[dx])   this[n++]=this[i]
	  this.length-=1
	}
/*************************************************************************/

