/**
 * 对目标字符串进行格式化
 * @author huang.wq
 * @param {string} source 目标字符串
 * @param {Object|string...} opts 提供相应数据的对象或多个字符串
 * @remark
 * opts参数为“Object”时，替换目标字符串中的{#property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的{#0}、{#1}...部分。	
 * @shortcut format
 * @returns {string} 格式化后的字符串
 * 例：
    (function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#0}-{#1}-{#2}',["2011年","5月","1日"]);
	(function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#year}-{#month}-{#day}', {year: 2011, month: 5, day: 1});   
 */
function formatString(source, opts) {
    source = String(source);
    var data = Array.prototype.slice.call(arguments,1), toString = Object.prototype.toString;
    if(data.length){
	    data = data.length == 1 ? 
	    	/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
	    	(opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) 
	    	: data;
    	return source.replace(/\{#(.+?)\}/g, function (match, key){
	    	var replacer = data[key];
	    	// chrome 下 typeof /a/ == 'function'
	    	if('[object Function]' == toString.call(replacer)){
	    		replacer = replacer(key);
	    	}
	    	return ('undefined' == typeof replacer ? '' : replacer);
    	});
    }
    return source;
};


/**
 * 设置select框的值
 * @param {String} id select框的id
 * @param {String} value 要设置的value
 */
function setSelectValue(id, value) {
	var sel = document.getElementById(id);
	if(!sel) return;
	for (var i = 0, len = sel.options.length; i < len; i++) {        
        if (sel.options[i].value == value) {
			sel.selectedIndex = i;        
            break;        
        }        
    }  
};

/**
 * 创建侦察器
 * @param {String} detectorId 侦察器id，存放到window对象中
 * @param {Function} conditionFun 执行条件方法，返回false就继续侦查
 * @param {Function} targetFun 目标方法
 * @param {Number} interval 侦查时间间隔
 */
function createDetector(detectorId ,conditionFun, targetFun, interval) {
	var timmer = window.setInterval(function() {
		//console.log(detectorId + " execute");
		if(conditionFun()) {
			deleteDetector(detectorId);
			targetFun();
		}
	}, interval);
	window[detectorId] = timmer;
}

/**
 * 删除侦察器
 * @param {String} detectorId 侦察器id
 */
function deleteDetector(detectorId) {
	if(window[detectorId]) {
		window.clearInterval(window[detectorId]);
	}
}

/**
 * 添加隐藏域
 * @param {String} id 隐藏域id
 * @param {String} value 隐藏域的值
 * @param {String} name 隐藏域名字
 * @param {String} appendToSelector 目标容器jquery selector，如#test,.test等
 */
function appendHidden(id, value, name, appendToSelector) {
	var hiddenHtml = '<input id="' + id + '" value="' + value + '" name="' + name + '" type="hidden" style="display: none;"/>';
	$(appendToSelector).append(hiddenHtml);
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};


jQuery.prototype.serializeObject=function(){  
    var obj=new Object();  
    $.each(this.serializeArray(),function(index,param){  
        if(!(param.name in obj)){  
            obj[param.name]=param.value;  
        }  
    });  
    return obj;  
};

/**
 * 获取数组中重复的元素
 * @param {Array} 基本类型的数组
 * @return 返回重复的元素
 */
function getRepeatElements(codeArr) {
	var repeatArr = [];
	if(codeArr == null || typeof(codeArr) == "undefined" ||
			codeArr.length == 0 || codeArr.length == 1) {
		return repeatArr;
	}
	codeArr.sort();
	
	if(codeArr[0] == codeArr[1]) {
		if (!isEmpty(codeArr[0])) 
			repeatArr.push(codeArr[0]);
	}
	for (var i = 1, len = codeArr.length; i < len; i++) {
		if (isEmpty(codeArr[i])) continue;
		if(codeArr[i] == codeArr[i - 1]) {
			var isContain = false;
			for(var j = 0, len1 = repeatArr.length; j < len1; j++) {
				if(repeatArr[j] == codeArr[i]) {
					isContain = true;
					break;
				}
			}
			if(!isContain) {
				repeatArr.push(codeArr[i]);
			}
		}
	}
	return repeatArr;
}

//对属性值的排序
//返回排好序的数组
function sortPropValue(propValueArr){
    if (propValueArr == null) return;
    if (!propValueArr.length) return;
    //对于2015年10月格式或30~40m或20寸格式正则表达式
	var reg = /\d+\D*\d*\D+/;
	var numarr = new Array();
	var strarr = new Array();
	var strNumArr = new Array();
	var n=0,s=0,sn=0,tempValArr = new Array();
    if (propValueArr.length > 1) {
    	for(var i=0;i<propValueArr.length;i++){
    		if(isNaN(propValueArr[i].propValueName)){
    			if(reg.test(propValueArr[i].propValueName)){
    				//匹配数字或者小数
    				tempValArr = propValueArr[i].propValueName.match(/(\d((\.\d+)?))+/g);
    				strNumArr[sn] = {};
    				strNumArr[sn].key = tempValArr;
    				strNumArr[sn].value = propValueArr[i];
    				sn = sn + 1;
    			}else{
    				strarr[s++] = propValueArr[i];
    			}
    		}else{
    			numarr[n++] = propValueArr[i];
    		}
    	}
        //尺码列表排序(字符串)
    	strarr.sort(sortStringComparator);
        //尺码列表排序(数字)
    	numarr.sort(sortNumberComparator);
    	//含字符数字排序
    	strNumArr = sortMapArr(strNumArr);
    	propValueArr = numarr.concat(strNumArr).concat(strarr);
    }
    return propValueArr;
}

//对数组进行排序
//返回value
function sortMapArr(strNumArr){
	strNumArr.sort(sortMapArrComparator);
	var valueArr = new Array();
	for(var m=0;m<strNumArr.length;m++){
		valueArr.push(strNumArr[m].value);
	}
	return valueArr;
}

/**
 * 属性值比较器（字符串）
 * @param {Object} sizeA
 * @param {Object} sizeB
 * @return sizeA 大于 sizeB，则返回大于0的数字
 */
sortStringComparator = function(sizeA, sizeB) {
    return sizeA.propValueName.localeCompare(sizeB.propValueName);
};

/**
 * 属性值比较器（数字）
 * @param {Object} sizeA
 * @param {Object} sizeB
 * @return sizeA 大于 sizeB，则返回大于0的数字
 */
sortNumberComparator = function(sizeA, sizeB) {
    var numA = parseFloat(sizeA.propValueName);
    var numB = parseFloat(sizeB.propValueName);
    return numA - numB;
};

sortMapArrComparator = function(arrsizeA, arrSizeB){
	var result = 0;
	for(var k=0;k<arrsizeA.key.length;k++){
		var numA = parseFloat((arrsizeA.key)[k]);
		var numB = parseFloat((arrSizeB.key)[k]);
		result = numA - numB;
		if(result!=0){
			return result;
		}
		continue;
	}
	return result;
};
