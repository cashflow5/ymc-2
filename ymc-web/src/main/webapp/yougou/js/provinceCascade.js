$(window).ready(function(){
    //定义默认数据
	var ar = ["请选择省份","请选择城市","请选择区县"];
	//初始化
	$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#city"));
	$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#area"));
	$("#city").reJqSelect();
	$("#area").reJqSelect();
});
//选择省
function checkTwo(){
  var province=$("#province").val();
  if(province!=""){
  	if(province=="-1"){
		$("#city").empty();
		$("<option value='-1'>请选择城市</option>").appendTo($("#city"));
		$("#city").reJqSelect();
  		
  		$("#area").empty();
		$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
		$("#area").reJqSelect();
  		return;
  	}
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		url: "/merchants/login/queryChildByLevelAndNo.sc?level=2&no=" + province, 
		success: function(result){
			if(result!=""){
			    $("#city").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					$("<option value='"+no+"'>"+name+"</option>").appendTo($("#city"));
				}
				$("#city").reJqSelect();
				
				$("#area").empty();
				$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
				$("#area").reJqSelect();
			}
		} 
	});
   }
}
//选择市
function checkThree(){
  var city=$("#city").val();
  if(city!=""){
  	if(city=="-1"){
  		$("#area").empty();
		$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
		$("#area").reJqSelect();
		return;
  	}
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		url: "/merchants/login/queryChildByLevelAndNo.sc?level=3&no=" + city, 
		success: function(result){
			if(result!=""){
			    $("#area").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					$("<option value='"+no+"'>"+name+"</option>").appendTo($("#area"));
				}
				$("#area").reJqSelect();
			}
		} 
	});
   }
}