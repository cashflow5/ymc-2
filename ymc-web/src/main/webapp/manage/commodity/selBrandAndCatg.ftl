<!DOCTYPE html>
<html lang="Zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-发布商品</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/commodity.css?${style_v}"/>
</head>
<body>
<!--main_container(Start)-->
<div class="main_container">
<!--normal_box(Start)-->
<div class="normal_box">
    <p class="title site">当前位置：商家中心 &gt; 商品模块 &gt; 发布商品</p>
    <div style="margin-top:20px;margin-left:45px;">
        <ul class="publishSetp">
    	   <li>
    	   	      商品分类选择
    	   	  <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/currStep.png"></p>    
    	   </li>
    	   <li>
    	   	      商品资料填写
    	   	  <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/otherStep.png"></p>     
    	   </li>
    	   <li>
    	   	      商品提交成功
    	   	  <p><img id="u192_line" class="img " src="/yougou/images/commodityPublish/otherStep.png"></p>     
    	   </li>
        </ul>
    </div>
    
    <div class="freqCatg">
    	<span >
    	<label>经常使用的品牌和一、二、三级分类：</label>
    	<select style="width:300px;float:right;" id="selBrdCatg">
    		<#if recentBrdCatg?? && (recentBrdCatg?size > 0)>
    		    <option>请选择</option>
    			<#list recentBrdCatg as  item>
    			   <option value="${(item[1])!}">${(item[0])!}</option>
    			</#list>
    		<#else>	
    		<option>暂无</option>
    		</#if>
    	</select>
    	</span>
    </div>
    
    <div class="brandAndCatg">
    	<div class="selBrand" id="list0">
    		<ul>
    		   <#list lstBrand as brand>	
    		       <li data-brandNo="${(brand.brandNo)!}" data-brandId="${(brand.id)!}" data-name="${(brand.brandName)!}">${(brand.brandName)!}<span>&gt</span></li>
    		   </#list>
    		</ul>
    	</div>
    	
    	<div class="selFstCatg" id="list1" data-level="1">
    	</div>
    	
    	<div class="selSecCatg" id="list2" data-level="2">
    	</div>
    	
    	<div class="selTrdCatg" id="list3" data-level="3">
    	</div>
    </div>
    
    <div class="bottomTip">
    	<p>
    	     您当前选择的是:<span id="tip0"></span><span id="tip1"></span><span id="tip2"></span><span id="tip3"></span>
    	</p>
    </div>
    
    <div id="nextStep">
    	<button disabled>下一步，填写商品属性</button><span style="visibility:hidden">&nbsp;&nbsp;<a href="#" id="showTpl">使用商品属性模板发布 &gt&gt</a></span>
    	<form id="mainForm" action="/commodity/publishCommodity.sc?step=1" method="post">
    		<input type="hidden" name="brandName" id="brandName"/>
    		<input type="hidden" name="brandNo" id="brandNo"/>
    		<input type="hidden" name="brandId" id="brandId"/>
    		 
    		<input type="hidden" name="catName" id="catName"/>
    		<input type="hidden" name="catStructName" id="catStructName"/>
    		<input type="hidden" name="catNo" id="catNo"/>
    		<input type="hidden" name="catId" id="catId"/>
    		<input type="hidden" name="secondCatName" id="secondCatName"/>
    		<input type="hidden" name="rootCatName" id="rootCatName"/>
    		 
    	</form>
    </div>
    
</div>
<!--normal_box(End)-->

</div>
<!--main_container(End)-->
<script>

var brandCatgList = {};

/*设置品牌和分类选项*/
function change(dataList, $targetDiv, nextDivIndex, currSelVal){
   $("#nextStep>button").attr("disabled", true); 
   $("#nextStep>span").css("visibility","hidden");
 
   for(var i = nextDivIndex; i <= 3; i++){// 将后面的div中的list清除，将后面的tip内容清除
      $("#list" + i).html("");
      $("#tip" + i).html("");
   } 
    
   for(var i = 0; i < dataList.length; i++){
   	  var $li = $("<li>" + dataList[i].catName +"<span>&gt</span></li>");
   	  $li.attr("data-level", dataList[i].catLeave);
   	  $li.attr("data-catNo", dataList[i].catNo);
   	  $li.attr("data-structname", dataList[i].structName);
   	  $li.attr("data-name", dataList[i].catName);
   	  $li.attr("data-catId", dataList[i].id);
   	  if(currSelVal && dataList[i].structName == currSelVal){
   	     $li.addClass("sel");
   	  }
      $targetDiv.append($li); 
   }
    
}

var clickBrandAndCatg = function(){
      var $this = $(this);
      $this.addClass("sel");
      $this.siblings().removeClass();	
      var brandId = $this.attr("data-brandId");
      
      if(brandId){
        $.getJSON("/commodity/queryBrandCat.sc?brandId="+brandId, function(data){
           brandCatgList = data;
           var dataList = brandCatgList.filter(function(item){
              return item.catLeave == "1";
           });
           change(dataList, $("#list1"), 1);
           $("#tip0").html($this.attr("data-name")); 
        });
      }else{
      	var level = $this.parent().attr("data-level");
      	$("#tip" + level).html("&nbsp;&gt;&nbsp;" + $this.attr("data-name"));
      	if(level == 3){
   	       $("#nextStep>button").removeProp("disabled");
   	       $("#nextStep>span").css("visibility","visible");
           return;
        }
      	var $nextDiv = $this.parent().next();  
        if($nextDiv.length > 0){
           var nextLevel = $nextDiv.attr("data-level");
      	   var structName = $this.attr("data-structname");
           var dataList = brandCatgList.filter(function(item){
              return item.catLeave == nextLevel && item.parentId == structName;
           });
           change(dataList, $nextDiv, nextLevel);
        }  
      } 
};

var changeSelBrdCatg = function(obj){
     var $this = $(this);
	  
     var val = $this.val();
     var $selectedOpt = $this.find("option:selected");
     var text = $selectedOpt.text();
     if($selectedOpt.index() == 0){
         return;
     }

     var valArr = val.split(",");
     $("#list0 li").removeClass("sel");
     $("#list0 li[data-brandId='" + valArr[0] + "']").addClass("sel");
      
     $.getJSON("/commodity/queryBrandCat.sc?brandId=" + valArr[0], function(data){
         brandCatgList = data;
         var dataList = brandCatgList.filter(function(item){
            return item.catLeave == "1";
         });
         change(dataList, $("#list1"), 1, valArr[1]);
         
         dataList = brandCatgList.filter(function(item){
            return item.catLeave == "2" && item.parentId == valArr[1];
         });
         change(dataList, $("#list2"), 2, valArr[2]);
         
         dataList = brandCatgList.filter(function(item){
            return item.catLeave == "3" && item.parentId == valArr[2];
         });
         change(dataList, $("#list3"), 3, valArr[3]);
		 
		 $("#tip0").html(text);
		 $("#nextStep>button").removeProp("disabled");
   	     $("#nextStep>span").css("visibility","visible"); 
     });
};

var submitMainForm = function(){
    $("#brandName").val($("#list0 li.sel").attr("data-name")); // 品牌名称
    $("#brandNo").val($("#list0 li.sel").attr("data-brandNo"));// 品牌编号
    $("#brandId").val($("#list0 li.sel").attr("data-brandId"));// 品牌id
    
    $("#rootCatName").val($("#list1 li.sel").attr("data-name")); // 一级分类名称
    $("#secondCatName").val($("#list2 li.sel").attr("data-name")); // 二级分类名称
     
    $("#catName").val($("#list3 li.sel").attr("data-name"));// 三级分类名称
    $("#catStructName").val($("#list3 li.sel").attr("data-structname"));// 分类结构名
    $("#catNo").val($("#list3 li.sel").attr("data-catNo")); // 分类编号
    $("#catId").val($("#list3 li.sel").attr("data-catId")); // 分类id
     
    $("#mainForm").submit();
};

var showTpl = function(){
    var cateNo = $("#list3 li.sel").attr("data-catNo");
    if(!cateNo) return false;
	var cateNames = $("#list1 li.sel").attr("data-name") + ">" + $("#list2 li.sel").attr("data-name") + ">" + $("#list3 li.sel").attr("data-name");
	cateNames = encodeURI(encodeURI(cateNames));
	openwindow("/commodity/itemTpl.sc?cateNames="+cateNames+"&catNo="+cateNo, 800,500, "选择属性模板");
	return false;
};

var initOriginData = function(){
    var brandNo = "${(brandNo)!}";
    if(!brandNo){
       return;
    }
    var catStructName = "${(catStructName)!}";
    
    $("#selBrdCatg").find("option[value=^" + brandNo + "][value$=" + catStructName + "]").attr("selected",true);
    $("#selBrdCatg").reJqSelect(); 
    $("#selBrdCatg").change();
};

$(function(){
  $(".brandAndCatg li").live("click.lyx", clickBrandAndCatg);
  
  $("#selBrdCatg").bind("change.lyx", changeSelBrdCatg);

  $("#nextStep>button").bind("click.lyx", submitMainForm); 	  	
  
  $("#showTpl").bind("click.lyx", showTpl);
  
  initOriginData();
  
});

</script>
</body>
</html>