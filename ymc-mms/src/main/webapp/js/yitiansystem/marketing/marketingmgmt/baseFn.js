/*
基本函数
*/
function getByID(id){
	return document.getElementById(id);
}

function getByName(name){
	return document.getElementsByName(name);
}



Array.prototype.del=function(n) {  //n表示第几项，从0开始算起。
//prototype为对象原型，注意这里为对象增加自定义方法的方法。
  if(n<0)  //如果n<0，则不进行任何操作。
    return this;
  else
    return this.slice(0,n).concat(this.slice(n+1,this.length));
    /**//*
       concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
       　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
      　　　　　　组成的新数组，这中间，刚好少了第n项。
       slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
    */
};
Array.prototype.indexOf = function(val){
	for(var i = 0 ; i < this.length ;i++){
		if(this[i] == val){
			return i;
		}
	}
	return -1;
};



$(document).ready(function(){

	//按品类促销-分类选择
	$("#rootCategory").change(function(){
		var rootCatName = $(this).children('option:selected').text();
		var val = $(this).children('option:selected').val();
		if(val=="0"){return;}
		var vals = val.split(";");
		var childData = loadChildLevel(vals[0]);
		var html = '';
		$("#container").empty();
		if(childData!=null&&childData.length>0){
			html+='<table class="list_table2"><thead>';
			html+='<tr><th width="20%">二级分类</th><th width="80%">三级分类</th></tr></thead>';
			html+='<tbody>';
			for(var i = 0 ; i < childData.length ; i++){
				var child = childData[i];
				if(child==null)continue;
				var children = loadChildLevel(child.structName);

				html+='<tr><td style="text-align:left;">';
				html+='<input type="checkbox" name="secondCategory" id="second_'+child.id+'" value="'+child.id+'" onclick="selectAll(\''+child.id+'\',this)"/>';
				html+='&nbsp;<label for="second_'+child.id+'">'+child.catName+'</label>';
				html+='</td><td style="text-align:left;">';
				if(children.length>0){
					//二级分类输出
					for(var j = 0 ; j <children.length ;j++){
						if(children[j]==null)continue;
						//二级分类输出
						var tempVal=rootCatName+';'+child.catName+';'+children[j].catName+';'+vals[1]+';'+child.id+';'+children[j].id;
						var temId=child.id+'_'+children[j].id;
						html+='<span style="float:left;display:inline;line-height:28px;width:120px;">';
						html+='<input type="checkbox" id="'+temId+'" name="chk_cat" value="'+tempVal+'" onclick="cancleChildCat(\''+child.id+'\',this)"/>';
						html+='&nbsp;<label for="'+temId+'">'+children[j].catName+'</label>';
						html+='</span>';
					}
				}
				html+='</td></tr>';
			}
			html+='</tbody></table>';
	   }else{
	   		alert("未搜索到相应数据");
	   }
	   $("#container").html(html);
   });

});


//选择大分类
function selectAll(rootCatID,inputObj){
	//alert(rootCatID);
	var checkCats = getByName("chk_cat");
	for(var i = 0 ;i < checkCats.length ;i++){
		var val = checkCats[i].value.split(";");
		//alert(val[4]+"--"+rootCatID);
		if(val[4] == rootCatID){
			checkCats[i].checked = inputObj.checked;
		}
	}
}

/**
获取三级分类下面的子分类集合([Array,第二级分类id])
childInputObj:三级分类复选框控件
*/
function getCat_children(childInputObj){
	var val = childInputObj.value.split(";");
	var secondID = val[4];//二级分类id
	return [getCat_child(secondID),secondID];
}

//获取二级分类下的三级分类
function getCat_child(secondID){
	var children = new Array();
	var checkCats = getByName("chk_cat");
	for(var i = 0 ;i < checkCats.length ;i++){
		var vals = checkCats[i].value.split(";");
		if(vals[4] == secondID){
			children.push(checkCats[i]);
		}
	}
	return children;
}

//取消三级分类
function cancleChildCat(childID,childInputObj){
	//获取属于该分类的第一级分类的三级分类集合
	var children = getCat_children(childInputObj);
	//查看所有的三级分类是否都被取消，如果都被取消则取消第一级分类的控件选择
	var childrenInputs = children[0];
	var cancleTimes = 0;
	for(var i = 0 ;i < childrenInputs.length ;i++){
		if(childrenInputs[i].checked){
			cancleTimes++;
		}
	}
	var secondID = children[1];
	if(cancleTimes <= 0){//取消二级选中
		getByID("second_"+secondID).checked = false;
	}else{
		getByID("second_"+secondID).checked = true;
	}
}