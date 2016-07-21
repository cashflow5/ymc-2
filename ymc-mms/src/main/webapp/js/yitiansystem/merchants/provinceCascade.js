$(window).ready(function(){
    //定义默认数据
	var ar = ["请选择省份","请选择城市","请选择区县"];
	//初始化
	$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#city"));
	$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#area"));
	checkTwo();
});
//选择省
function checkTwo(){
  var province=$("#province").val();
  if(province!=""){
  	if(province=="-1"){
		$("#city").empty();
		$("<option value='-1'>请选择城市</option>").appendTo($("#city"));
  		
  		$("#area").empty();
		$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
  		return;
  	}
  	var bankCity = $("#bankCity").val();
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		url: basePath+"/yitiansystem/merchants/businessorder/queryChildByLevelAndNo.sc?level=2&no=" + province, 
		success: function(result){
			if(result!=""){
			    $("#city").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				$("<option value='-1'>请选择城市</option>").appendTo($("#city"));
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					if(bankCity && bankCity == name){
						$("<option value='"+no+"' selected>"+name+"</option>").appendTo($("#city"));
					}else{
						$("<option value='"+no+"'>"+name+"</option>").appendTo($("#city"));
					}
				}
				
				$("#area").empty();
				$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
				checkThree();
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
		return;
  	}
  	var bankArea = $("#bankArea").val();
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		url: basePath+"/yitiansystem/merchants/businessorder/queryChildByLevelAndNo.sc?level=3&no=" + city, 
		success: function(result){
			if(result!=""){
			    $("#area").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					if(bankArea && bankArea == name){
						$("<option value='"+no+"' selected>"+name+"</option>").appendTo($("#area"));
					}else{
						$("<option value='"+no+"'>"+name+"</option>").appendTo($("#area"));
					}
				}
			}
		} 
	});
   }
}