/*显示图片*/
 var imgData ;
 function showpic(file,divId){
 	
 	var dFile = document.getElementById(file.id);
 	//var dImg = document.getElementById(img);
 	//var dInfo = document.getElementById('img');
 	if(!dFile.value.match(/.jpg|.gif|.png|.bmp/i)){
 		return;
 	}
 	$("#"+divId).html("");
 	if(dFile.files){
 		var image = $("<img>");
 		imgData = dFile.files[0].getAsDataURL();
 		image.attr("src", imgData);
 		image.attr("id", "imgid");
 		//image.attr("width","300");
 		//image.attr("height","300");
 		//image.attr("onMousemove","test(this)");
 		//dImg.src = dFile.files[0].getAsDataURL();
 		$("#"+divId).append(image);
 		resetH();
 	}
 	else{
 		/*这步骤是用来在ie6,ie7中显示图片的*/
 		//var from=img.indexOf('g')+1;
 		//var to=img.length;
 		//var pic='pic'+img.substring(from,to);
 		var newPreview = document.getElementById(divId);
 		newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dFile.value;
 		//dImg.style.display='none';
 	    newPreview.style.width = "105px";
 	    newPreview.style.height = "105px";
 	    resetH();
 	}
 }
 
 /**
  * 判断一个字符串是否存在于Array中
  * @param arg
  * @return
  */
 function isExist(arr ,arg){
 	var arrStr = arr.join(",").toString();
 	var val = arrStr.indexOf(arg);
 	if(val == -1){
 		return true;
 	}
 	return false;
 }
 
  /**
   * 判断Array是否为空
   * @return
   */
 function isArrayNull(arr){
	 if(arr.length>0){
		 return true;
	 }
	 return false;
 }
 
 /**
  * ajax异步验证时添加样式
  * @param {} spanId span id
  * @param {} cs css
  * @return {}
  */
 function addClass(spanId,cs) {
	    var name = $("#"+spanId);
	    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	    return name.addClass(cs);
}

  // 批量添加子分类
	  function addSubNewCat(){
			//一级分类的选中的值和文本
		  	var rootVal = $('#rootCattegory').children('option:selected').val();
		  	var rootText = $('#rootCattegory').children('option:selected').text();
		  // 二级分类的选中的值和文本
		  	var secondVal = $('#secondCategory').children('option:selected').val();
		  	var secondText = $('#secondCategory').children('option:selected').text();
		  
		  	 // 三级分类的选中的值和文本
		  	var threeVal= $('#threeCategory').children('option:selected').val();
		  	var threeText= $('#threeCategory').children('option:selected').text();
		  // 一级分类的所有的的值和文本
		  	var roots = $('#rootCattegory').children();
		  	// 二级分类所有的值和文本
		  	var seconds = $('#secondCategory').children();
		  	// 三级分类所有的值和文本
		  	var threes = $('#threeCategory').children();

		  	//三级分类的option个数
		  	var len = threes.length;
		  	
		  	var removeF = "removeLiTX(this)";
		  	
		  	// 当三个分类都不选择的时候，批量添加1级分类
		  	if(rootVal == "0"){
		  		addClass("cattip","onerror").html("请选择大分类");
		  	}else if(rootVal != "0"&&secondVal=="0"){
		  		addClass("cattip","onerror").html("请选择二级分类");
		  	}else{
		  	//这种情况就添加所有的一级下的三级的所有列表
		  		if(null!=arr&&arr.length>0){
		  			$.each(threes, function(i, m){
		  				var isNAdd = false ;
		  				if(m.value!='0'){
		  					$.each(arr,function(y,n){
				  				if(m.value==n){
				  					isNAdd = true;
				  				}
				  			})
				  			if(!isNAdd){
			  					var singleli = "<span><li id="+m.value+"><span>" ;
			  					singleli += rootText +"</span>";
			  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
			  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
			  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
			  					singleli += "</span>";
			  					arr.push(m.value);
			  					$("#showCat").append(singleli);
			  				}
		  				}
		  			})
		  		}else if(null!=arr&&arr.length==0){
		  			$.each(threes, function(i, m){
		  				if(m.value!='0'){
		  					var singleli = "<span><li id="+m.value+"><span>" ;
		  					singleli += rootText +"</span>";
		  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
		  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
		  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
		  					singleli += "</span>";
		  					arr.push(m.value);
		  					$("#showCat").append(singleli);
		  				}
		  			})
		  			
		  		}
		  		if(len== 1){
		  			addClass("cattip","onerror").html("该分类下面无子分类");
		  		}else{
		  			addClass("cattip","oncorrect").html("");
		  		}
		  	}
		  	resetH();
	  }
	  
	  //删除分类行
		function  removeLiTX(obj){
			var rowId = $(obj).parent().attr("id");
			$("#"+rowId).remove();
			$.each(arr,function(i,n){
				if(n==rowId){
					arr.splice(i,1);
				}
			});
		}
	  
	 /**
	  * 验证图片格式
	  * @param {} spanId 显示错误信息的id
	  * @param {} fileId file域的id
	  * @return {Boolean}
	  */
	function validatorImage(spanId,fileId){
  		$('#'+spanId).empty();
  		$('#'+spanId).attr("class", "");
  		
  		var imgName = $("#"+fileId).val();
			
		if(imgName != null && imgName != ""){
			imgName = imgName.substring(imgName.lastIndexOf("."),imgName.length);
			var reg = /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/;
			if(reg.test(imgName)){
				return true;
			}
		}
		
  		$('#'+spanId).parent().css("display", "block");
  		$('#'+spanId).append("上传图片格式不正确");
  		$('#'+spanId).attr("class", "onerror");
		return false;
  	}
	  
	  function toSelectAdvPic(type){
		 	var param = "type="+type;
		 	openwindow("../../commoditymgmt/commodityinfo/brand/toBatchPicSelect.sc?type="+type,750,550,"选择品牌图片");
			//showThickBox("选择品牌图片","../../commoditymgmt/commodityinfo/brand/toBatchPicSelect.sc?TB_iframe=true&height=550&width=750",false,param);
	}

	 function initAdvPicUrl(url,type){
		 	if(type=="B"){
		 		$("#imageUrl").attr("value",url);
		 		$("#imageView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageView");
		 	}else if(type=="M"){
		 		$("#imageMUrl").attr("value",url);
		 		$("#imageMView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageMView");
		 	}else if(type=="S"){
		 		$("#imageSUrl").attr("value",url);
		 		$("#imageSView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageSView");
		 	}else if(type=="L"){
		 		$("#imageLUrl").attr("value",url);
		 		$("#imageLView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageLView");
		 	}else if(type=="A"){
		 		$("#imageAUrl").attr("value",url);
		 		$("#imageAView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageAView");
		 	}else if(type=="Sa"){
		 		$("#imageSaUrl").attr("value",url);
		 		$("#imageSaView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageSaView");
		 	}else if(type=="Sm"){
		 		$("#imageSmUrl").attr("value",url);
		 		$("#imageSmView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageSmView");
		 	}else if(type=="Ss"){
		 		$("#imageSsUrl").attr("value",url);
		 		$("#imageSsView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageSsView");
		 	}else if(type=="E")
		 	{	
		 		$("#imageEUrl").attr("value",url);
		 		$("#imageEView").empty();
				$("<img src='"+url+"'  style='width:150px;height:100px;' />").appendTo("#imageEView");
		 	}
			return true;
	}