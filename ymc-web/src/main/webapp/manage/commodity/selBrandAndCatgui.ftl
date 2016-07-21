<!DOCTYPE html>
<html lang="Zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-发布商品</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/commodity.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/goods-selection.css?${style_v}">
</head>
<body>
<!--main_container(Start)-->
<div class="wrap">
<!--normal_box(Start)-->
<div id="content" class="clearfix">
    <div class="cont_search">
		<input type="text" title="请输入商品名称/商品款号/商品编码" placeholder="请输入商品名称/商品款号/商品编码" id="search_text" class="cont_search_text">
		<input type="button" value="查询" id="search_query_btn" class="btn btn_gary1">
	</div>
    <div class="tc">
	    <div class="freqCatg" style="background-color:#fff;">
	    	<span>
	    	<label class="cred">您最近使用的分类：</label>
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
    </div>
    
    <div class="brandAndCatg cate-container">
    	<div class="selBrand cate-cbox-filter rel" id="list0">
    		<ul class="cate-list clearfix">
    			<i class="cate_ico_input">输入名称</i>
    			<input type="text" value="" class="cate-text cate-cbox-filter-text">
    			<ul id="brand_class" class="cate-listbox">
    		   <#list lstBrand as brand>	
    		       <li data-brandNo="${(brand.brandNo)!}" data-brandId="${(brand.id)!}" data-name="${(brand.brandName)!}">
    		       	<span class="cate-listbox-item-text">${(brand.brandName)!}</span><span class="gt">&gt</span>
    		       </li>
    		   </#list>
    		   </ul>
    		</ul>
    	</div>
    	
    	<div class="selFstCatg cate-cbox-filter rel" id="list1" data-level="1">
    		<ul class="cate-list clearfix">
    			<i class="cate_ico_input">输入名称</i>
    			<input type="text" value="" class="cate-text cate-cbox-filter-text">
    		</ul>
    	</div>
    	
    	<div class="selSecCatg cate-cbox-filter rel" id="list2" data-level="2">
    		<ul class="cate-list clearfix">
    			<i class="cate_ico_input">输入名称</i>
    			<input type="text" value="" class="cate-text cate-cbox-filter-text">
    		</ul>
    	</div>
    	
    	<div class="selTrdCatg cate-cbox-filter rel" id="list3" data-level="3">
    		<ul class="cate-list clearfix">
    			<i class="cate_ico_input">输入名称</i>
    			<input type="text" value="" class="cate-text cate-cbox-filter-text">
    		</ul>
    	</div>
    </div>
    
    <div class="bottomTip">
    	<p>
    	     您当前选择的是:<span id="tip0"></span><span id="tip1"></span><span id="tip2"></span><span id="tip3"></span>
    	</p>
    </div>
    
    <div id="nextStep">
    	<button disabled class="btn btn_gary5">下一步，发布商品</button>
    	<span>&nbsp;&nbsp;<a class="help_information" target="_blank" href="http://open.yougou.com/help/content/8a8a8ab340585512014058860dbe002a.shtml">帮助信息》</a></span>
    	<p class="none">查看此分类下已保存<a href="#" id="showTpl">属性模板</a></p>
    	<form id="mainForm" action="/commodity/addCommodityui.sc" method="post">
    		<input type="hidden" name="brandName" id="brandName"/>
    		<input type="hidden" name="brandNo" id="brandNo"/>
    		<input type="hidden" name="brandId" id="brandId"/>
    		<input type="hidden" name="catName" id="catName"/>
    		<input type="hidden" name="catStructName" id="catStructName"/>
    		<input type="hidden" name="catNo" id="catNo"/>
    		<input type="hidden" name="catId" id="catId"/>
    		<input type="hidden" name="secondCatName" id="secondCatName"/>
    		<input type="hidden" name="rootCatName" id="rootCatName"/>
    		<input type="hidden" name="step" value="1"/>
    		<input type="hidden" name="commodityNo" value="${commodityNo!'' }"/>
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
   $("#nextStep>button").removeClass("btn_qing1");
   $("#nextStep>p").hide();
   for(var i = nextDivIndex; i <= 3; i++){// 将后面的div中的list清除，将后面的tip内容清除
      $("#list" + i).find("input").nextAll().remove();
      $("#tip" + i).html("");
   } 
   var ulstr = "<ul id='item_class' class='cate-listbox'>";
   var classstr = "";
   for(var i = 0; i < dataList.length; i++){
	  if(currSelVal && dataList[i].structName == currSelVal){
		  classstr = "sel";
	  }else{
		  classstr = "";
	  }
      ulstr+="<li data-level='"+dataList[i].catLeave+"' data-catNo='"+dataList[i].catNo+"' "+
	  	"data-structname='"+dataList[i].structName+"' data-name='"+dataList[i].catName+"' "+
		  	"data-catId='"+dataList[i].id+"' class='"+classstr+"'>"+
	  		 "<span class='cate-listbox-item-text'>" + dataList[i].catName +"</span><span class='gt'>&gt</span></li>";
	  
   }
   ulstr +="</ul>"; 
   $targetDiv.find("input").after($(ulstr));
}

var clickBrandAndCatg = function(){
      var $this = $(this);
      $this.addClass("sel");
      $this.siblings("li").removeClass();	
      var brandId = $this.attr("data-brandId");
      
      if(brandId){
        $.getJSON("/commodity/queryBrandCat.sc?brandId="+brandId, function(data){
           brandCatgList = data;
           var dataList = $.grep(brandCatgList,function(item){
              return item.catLeave == "1";
           });
           change(dataList, $("#list1"), 1);
           $("#tip0").html($this.attr("data-name")); 
        });
      }else{
      	var level = $this.parents(".cate-cbox-filter").attr("data-level");
      	$("#tip" + level).html("&nbsp;&gt;&nbsp;" + $this.attr("data-name"));
      	if(level == 3){
   	       $("#nextStep>button").removeAttr("disabled");
   	       $("#nextStep>button").addClass("btn_qing1");
   	   	   $("#nextStep>p").show();
           return;
        }
      	var $nextDiv = $this.parents(".cate-cbox-filter").next();  
        if($nextDiv.length > 0){
           var nextLevel = $nextDiv.attr("data-level");
      	   var structName = $this.attr("data-structname");
           var dataList = $.grep(brandCatgList,function(item){
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
         var dataList = $.grep(brandCatgList,function(item){
            return item.catLeave == "1";
         });
         change(dataList, $("#list1"), 1, valArr[1]);
         
         dataList = $.grep(brandCatgList,function(item){
            return item.catLeave == "2" && item.parentId == valArr[1];
         });
         change(dataList, $("#list2"), 2, valArr[2]);
         
         dataList = $.grep(brandCatgList,function(item){
            return item.catLeave == "3" && item.parentId == valArr[2];
         });
         change(dataList, $("#list3"), 3, valArr[3]);
		 $.each(text.split(">"),function(i,n){
			 if(i==0){
				 $("#tip"+i).html($.trim(n));
			 }else{
				 $("#tip"+i).html("&nbsp;&gt;&nbsp;"+$.trim(n));
			 }
		 });
		 $("#nextStep>button").removeAttr("disabled");
		 $("#nextStep>button").addClass("btn_qing1");
 	   	 $("#nextStep>p").show();
     });
};

var submitMainForm = function(){
    $("#brandName").val($("#list0 li.sel").attr("data-name")); // 品牌名称
    $("#brandNo").val($("#list0 li.sel").attr("data-brandNo"));// 品牌编号
    $("#brandId").val($("#list0 li.sel").attr("data-brandId"));// 品牌id
    $("#rootCatName").val($("#list1 li.sel").attr("data-name")); // 一级分类名称
    $("#secondCatName").val($("#list2 li.sel").attr("data-name")); // 二级分类名称
    $("#catName").val($("#list3 li.sel").attr("data-name"));// 三级分类名称
    if($("#list3 li.sel").length>0){
	    $("#catStructName").val($("#list3 li.sel").attr("data-structname"));// 分类结构名
    }else{
    	ygdg.dialog.alert("请选择三级分类！");	
    }
    $("#catNo").val($("#list3 li.sel").attr("data-catNo")); // 分类编号
    $("#catId").val($("#list3 li.sel").attr("data-catId")); // 分类id
    $("#mainForm").submit();
};

var showTpl = function(){
    var cateNo = $("#list3 li.sel").attr("data-catNo");
    if(!cateNo) return false;
	var cateNames = $("#list1 li.sel").attr("data-name") + ">" + $("#list2 li.sel").attr("data-name") + ">" + $("#list3 li.sel").attr("data-name");
	cateNames = encodeURI(encodeURI(cateNames));
	openwindow("/commodity/itemTpl.sc?cateNames="+cateNames+"&catNo="+cateNo+"&from=selcat", 800,500, "选择属性模板",null,'itemTplDialog');
	return false;
};

var initOriginData = function(){
    var brandId = "${(brandId)!}";
    if(!brandId){
       return;
    }
    var catStructName = "${(catStructName)!}";
    $("#selBrdCatg").find("option[value^=" + brandId + "][value$=" + catStructName + "]").attr("selected",true);
    $("#selBrdCatg").reJqSelect(); 
    $("#selBrdCatg").change();
};

$(function(){
	  $(".brandAndCatg li").live("click.lyx", clickBrandAndCatg);
	  
	  $("#selBrdCatg").bind("change.lyx", changeSelBrdCatg);
	
	  $("#nextStep>button").bind("click.lyx", submitMainForm);
	  
	  $("#showTpl").bind("click.lyx", showTpl);
	
	  initOriginData();
	  $(".cate-cbox-filter-text").val("");
	  $('.cate-cbox-filter-text').focus(function(){
			$(this).siblings('.cate_ico_input').html("");
		}).blur(function(){
			if(!$(this).val()){
				$(this).siblings('.cate_ico_input').html("输入名称");
			}
		}).keyup(function(){
			var cate_listbox_item=$(this).siblings().find('.cate-listbox-item-text');
			var input_val=$(this).val();
			if(input_val){
				$(cate_listbox_item).each(function(index){
					var html=cate_listbox_item.eq(index).text(),
						cate_listbox=cate_listbox_item.eq(index);
					if(html.indexOf(input_val)>=0){
						cate_listbox_item.eq(index).parent().show();
					}else{
						cate_listbox_item.eq(index).parent().hide();
					}
					cate_listbox.html(cate_listbox.text().replace(input_val, '<span class="cred">'+ input_val +'</span>'));
				});
			}else{
				$(cate_listbox_item).each(function(index){
					var html=cate_listbox_item.eq(index).text(),
					cate_listbox=cate_listbox_item.eq(index);
					cate_listbox_item.eq(index).parent().show();
					cate_listbox.html(cate_listbox.html().replace('cred',""));
				});
			}
		});
	
	  $('.cate_ico_input').click(function(){
			$(this).html("");
			$(this).siblings('.cate-cbox-filter-text').focus();
		}).blur(function(){
			if(!$(this).siblings('.cate-cbox-filter-text').val()){
				$(this).html("输入名称");
				$(this).siblings('.cate-cbox-filter-text').focus();
			}
		});
	   $("#search_query_btn").click(function(){
		   var keyword = $.trim($("#search_text").val());
		   if(keyword!=''){
			   $.post("/commodity/queryByKeyword.sc",{"keyword":keyword},function(json){
				   		if(json[0].resultcode==1){
				   			var valArr = json[0].selcate.split(";");
						     $("#list0 li").removeClass("sel");
						     $("#list0 li[data-brandno='" + valArr[0] + "']").addClass("sel");
						     $("#tip0").html($("#list0 li.sel").attr("data-name"));
					         brandCatgList = json[0].cate;
					         var dataList = $.grep(brandCatgList,function(item){
					            return item.catLeave == "1";
					         });
					         change(dataList, $("#list1"), 1, valArr[1].split("-")[0]);
					         $("#tip1").html("&nbsp;&gt;&nbsp;"+$("#list1 li.sel").attr("data-name"));
					         dataList = $.grep(brandCatgList,function(item){
					            return item.catLeave == "2" && item.parentId == valArr[1].split("-")[0];
					         });
					         change(dataList, $("#list2"), 2, valArr[1].split("-")[0]+"-"+valArr[1].split("-")[1]);
					         $("#tip2").html("&nbsp;&gt;&nbsp;"+$("#list2 li.sel").attr("data-name"));
					         dataList = $.grep(brandCatgList,function(item){
					            return item.catLeave == "3" && item.parentId == valArr[1].split("-")[0]+"-"+valArr[1].split("-")[1];
					         });
					         change(dataList, $("#list3"), 3, valArr[1]);
							 $("#tip3").html("&nbsp;&gt;&nbsp;"+$("#list3 li.sel").attr("data-name"));
							 $("#nextStep>button").removeAttr("disabled");
							 $("#nextStep>button").addClass("btn_qing1");
					 	   	 $("#nextStep>p").show();
					   	}else{
							ygdg.dialog.alert("抱歉，没有找到与输入内容相关的类目。");	
						}
			   },"json");
			}
		});
});

</script>
</body>
</html>