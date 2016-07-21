<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/pic.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/ZeroClipboard.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.lazyload.js?${style_v}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygajaxfileupload.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/pic.js"></script>
<ul class="goodsPicsList clearfixed" id="goodsPicsList" style="height:370px;overflow:auto;">
	<#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
		<#list pageFinder.data as item>
			<li title="推拽可以改变顺序">
				<p class="img relative" idIndex="${item.id}" imgSrc="${commodityPreviewDomain!''}${item.picPath}${(item.picName)!''}?${random}')">
				    <a href="javascript:void(0);"  class="f_blue">
						<img class="lazy_loading" id="${item.id}" src="${commodityPreviewDomain!''}${item.picPath}${(item.thumbnaiPicName!item.picName)!''}?${random}"/>
					</a>
					<span class="check nocheck" onmousedown="selectImage(this);" checkType=false></span>
				</p>
				<p>
					<input style="border: none; width: 132px; margin: 0 0 0 -5px;" readonly="readonly" value="<#if item.sourcePicName??&&item.sourcePicName!=''>${item.sourcePicName?replace("\\?[0-9]*", "","ri")}<#else>${item.picName?replace("\\?[0-9]*", "","ri")}</#if>"/>
				</p>
				<p style="margin:0;padding:0;" class="curs">
					<a href="javascript:;" onclick="javascript:deletePic('${item.id}');return false;" class="f_blue curs">删除</a>
					<a href="javascript:;" onclick="javascript:movePic('${item.id}','${item.shopId!''}');return false;" class="f_blue curs">移动</a>
                    <a href="javascript:;" class="f_blue curs" onclick="insertImage('${commodityPreviewDomain!''}${item.picPath}${(item.picName)!''}?${random}',this)">插入图片</a>
				</p>
			</li>
		</#list>
	<#else>
	   <li style="text-align: center;"> 查询不到相关记录!</li>
	</#if>
</ul>
<!--列表end-->
<!--分页start-->
<#if pageFinder?? && pageFinder.data??>
	<div class="page_box">
		<#import "/manage/widget/common4ajax.ftl" as page>
		<@page.queryForm formId="queryForm"/>
	</div>
</#if>
<input type="hidden" id="moveCatalogId" value=""/>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px;height:200px"></ul>
</div>
<!--分页end-->
<script type="text/javascript">
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});

function changeTimeMark(mark){
    location.href="${BasePath}/picture/list.sc?timeMark="+mark;
}
var settingDemo = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClickDemo,
			onDblClick:onDblClickDemo
		}
};
function onDblClickDemo(e, treeId, treeNode){
	hideMenu();
}
function onClickDemo(e, treeId, treeNode) {
    $("#catalog").attr("value", '');
    $("#moveCatalogId").val('');
    
	if (treeNode.level==0){
		alert("请选择子目录...");
		return false;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes(),
	v = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	
    $("#catalog").attr("value", v);
    $("#moveCatalogId").val(treeNode.id);
    hideMenu();
}
var zIndexNum=9999999;
function showMenu() {
	var cityObj = $("#catalog");
	var cityOffset = $("#catalog").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px",zIndex:zIndexNum}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
function moveSelectedPic(){
	var treeObjSrc = $.fn.zTree.getZTreeObj("ztree");
	nodes = treeObjSrc.getSelectedNodes(),
	treeNode = nodes[0];
	if(treeNode){
		var images = $(".goodsPicsList li p.img");
		var ids = [];
		images.each(function(index,item){
			if($(this).find("span.checked").length>0){
	  			ids.push($(this).attr("idIndex"));
	  		}
		});
		if (ids.length <= 0) {
			ygdg.dialog.alert('请先选择需要移动的图片!');
			return ;
		}
		movePic(ids,treeNode.shopId);
	}else{
		ygdg.dialog.alert("请先选择指定目录的图片!");
	}
}
function movePic(picId,nodeid){
      if(nodeid==''){
      	nodeid='0';
      }
      var treeObjTarget = $.fn.zTree.init($("#treeDemo"), settingDemo);
      var treeObjSrc = $.fn.zTree.getZTreeObj("ztree");
      treeObjTarget.copyNode(null,treeObjSrc.getNodeByParam('id',nodeid),'inner',true );
      var dialog=ygdg.dialog({
      title: '移动图片',
      icon: null,
      content: '<span style="display: inline-block;">选择目录:<input id="catalog" type="text" readonly value="" style="padding: 2px;width:120px;" onclick="showMenu(); return false;"/></span>',
       button: [
       {
    		name: '确定',
    		callback: function () {	
    			catalogId = $("#moveCatalogId").val();
    			if(catalogId==""){
    				ygdg.dialog.alert("请选择目录!");
    				return;
    			}
				var params = {'picId':picId+'','catalogId':$("#moveCatalogId").val()};
				$.ajax({
					url: '${BasePath}/picture/movePics.sc',
					async: true,
			 		type: "POST",			
					data: params,
					dataType: "json",
					success: function(data){
						if(typeof(data) != "undefined" && data != null && data['result'] == true){
		                    //ygdg.dialog.alert("图片移动成功!");
		                   loadData(curPage);
						}else{
							ygdg.dialog.alert("图片移动失败,请联系技术支持!");
						}
					},
					error:function(xhr, textStatus, errorThrown){ 
						ygdg.dialog.alert("服务器错误,请稍后再试!");
						return;
					}
				});
    	    },
    	    focus: true
		},
		{
    		name: '关闭'
		}
	   ]
    });
}
//复制到剪切板
function copyPic(text, btn) {
	try {
		var clip = new ZeroClipboard.Client();
		clip.setHandCursor(true);
		clip.addEventListener('mouseOver', function(client) {
			clip.setText(text);
		});
		clip.addEventListener('complete', function(client, text) {
			ygdg.dialog.alert(btn.innerHTML + "成功!");
		});
		clip.glue(btn);
	} catch (e) {
	}
}
//批量删除图片
function deleteSelectedPic() {
	var images = $(".goodsPicsList li p.img");
	var ids = [];
	images.each(function(index,item){
		if($(this).find("span.checked").length>0){
  			ids.push($(this).attr("idIndex"));
  		}
	});
	if (ids.length <= 0) {
		ygdg.dialog.alert('请先选择需要删除的图片!');
		return;
	}
	
	deletePic(ids);
}
//删除图片
function deletePic(ids) {
	ygdg.dialog.confirm('确定删除' + ($.isArray(ids) ? '选择的' : '该') + '图片吗？', function(){
		$.ajax({
			type: 'post',
			url: '${BasePath}/commodity/pics/delete.sc',
			dataType: 'text',
			data: 'ids=' + ids + '&rand=' + Math.random(),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if (data == "success") {
					ygdg.dialog.alert('删除图片成功!');
					 loadData(curPage);
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				ygdg.dialog.alert('删除图片失败!');
			}
		});
	});
}

//全选
$("#checkAll").click(function() {
	var images = $(".goodsPicsList li p.img");
	if($("#checkAll").attr("checked")){
		images.find("span.check").removeClass("nocheck")
		images.find("span.check").addClass("checked");
	}else{
		images.find("span.check").removeClass("checked")
		images.find("span.check").addClass("nocheck");
	}
	
});

//详情
$(".picDetail").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		padding : 0,
		title : data.title,
		content : '<p class="picDetail">图片大小：' + data.size + ' kb<br/>上传时间：' + data.time + '<br/>长宽：'+data.wh+'</p>',
		id : 'detailBox',
		left : $(this).offset().left + 30,
		top : _top,
		closable : false,
		fiexed : true
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});

function selectImage(obj){
	if($(obj).attr("checkType")=="false"){
		$(obj).removeClass("nocheck");
  		$(obj).addClass("checked");
  		$(obj).attr("checkType","true");
  	}else{
  		$(obj).removeClass("checked");
  		$(obj).addClass("nocheck");
  		$(obj).attr("checkType","false");
  	}
}

function insertImage(urlStrs,obj){
	if(urlStrs==null){
		urlStrs = "";
		var images = $(".goodsPicsList li p.img");
		images.each(function(index,item){
			var checkSpan = $(this).find("span.checked");
			if(checkSpan.length>0){
				checkSpan.removeClass("checked");
				checkSpan.addClass("nocheck");
	  			if (urlStrs == '') {
					urlStrs += $(this).attr("imgSrc");
				} else {
					urlStrs += '&&&&&' + $(this).attr("imgSrc");
				}
	  		}
		});
		if(urlStrs==""){
			ygdg.dialog.alert('请选择要插入的图片');
			return;
		}
	}else{
		var pimg = $(obj).parent().parent().find("p.img");
		var checkSpan = pimg.find("span.checked");
		if(checkSpan.length>0){
			checkSpan.remove();
		}
	}
	onImgSelected.call(this, urlStrs);
}


$("#goodsPicsList").dragsort({dragSelector: "li", dragBetween: true, placeHolderTemplate: "<li class='placeHolder'></li>" });


</script>
