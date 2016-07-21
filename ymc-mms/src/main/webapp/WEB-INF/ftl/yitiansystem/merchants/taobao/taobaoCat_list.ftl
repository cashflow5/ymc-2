<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath }/css/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/common.js"></script>
<script type="text/javascript" src="${BasePath }/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath }/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<style type="text/css">
	.tb_dobox{
		background: none repeat scroll 0 0 #f3f3f3;
	    border-color: #dedee0;
	    border-style: solid;
	    border-width: 1px 0;
	    clear: both;
	    height: 26px;
	    line-height: 30px;
	    margin: 3px 0 8px;
	    padding-right: 10px;
	    padding-top: 4px;
	}
</style>

</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addTaobaoCat();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加淘宝分类</span>
	        	<span class="btn_r"></span>
        	</div> 
        	
        	&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="btn"  align="right">
				&nbsp;&nbsp;
				<span class="btn_txt" >
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="viewLog();">查看日志</a>
			    </span>
				</div>
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">淘宝分类管理</a></span>
				</li>
			</ul>
		</div>
 <div class="modify" id="bodyDiv"> 
     <form action="${BasePath}/yitiansystem/taobao/goTaobaoCat.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>淘宝分类：</label>
                     <input id="categorySet" type="text" value="<#if vo.name??>${vo.name!''}</#if>" 
                     style="width:200px;" readonly onclick="showMenu();return false;"/>
                     <input type="hidden" id="cid" value="<#if vo.cid??>${vo.cid!''}</#if>" name="cid"/>
                     <input type="hidden" name="name" id="name" value="<#if vo.name??>${vo.name!'' }</#if>"/>
                     <div id="menuContent" class="menuContent" style="display: none; position: absolute;">  
			            <ul id="treeDemo" class="ztree" style="margin-top: 0; width: 210px;">  
			            </ul>  
			        </div> 
                     &nbsp;&nbsp;&nbsp;
                     <label>淘宝一级分类编码：</label>
                     <input type="text" name="rootCatCode" id="rootCatCode" value="<#if vo.rootCatCode??>${vo.rootCatCode!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <label>淘宝二级分类编码：</label>
                     <input type="text" name="secondCatCode" id="secondCatCode" value="<#if vo.secondCatCode??>${vo.secondCatCode!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <input type="button" value="搜索" onclick="queryTaobaoCats();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                </div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>&nbsp;</th>
                        <th >淘宝分类</th>
                        <th width="130px">淘宝一级分类编码</th>
                        <th width="130px">淘宝二级分类编码</th>
                        <th width="90px;">创建时间</th>
                        <th width="55px;">操作人</th>
                        <th style="text-align:center;">操作</th>
                        </tr>
                        <tr>
                        	<td colspan="8">
                        	<div class="tb_dobox">
	                        	<input type="checkbox" id="chkb" nameid="" onclick="allChk(this,'ids')"/>
	                        	<label for="chkb">全选</label>
	                        	<!-- &nbsp;&nbsp;<a href="javascript:void(0);" onclick="batchDel();">删除</a> -->
                        	</div>
                        	</td>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    	<#list pageFinder.data as item >
	                        <tr>
	                        	<td><input type="checkbox" name="ids" value="${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}"/></td>
		                        <td id="name_${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}">
		                        <#if item['fName']??>
		                        	${ item['fName']}
		                        </#if>
		                        <#if item['sName']??>
		                        	&nbsp;>&nbsp;${item['sName']}
		                        </#if>
		                        <#if item['tName']??>
		                        	&nbsp;>&nbsp;${item['tName']}
		                        </#if>
		                        <#if item['foName']??>
		                        	&nbsp;>&nbsp;${item['foName']}
		                        </#if>
		                        </td>
		                        <td id="rootCode_${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}">
		                        	${item['fCid']?c}
		                        </td>
		                        <td id="secondCode_${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}">
		                        	${item['sCid']?c}
		                        </td>
		                        <td>
		                        	${item['operated']!"" }
		                        </td>
		                        <td>
		                        	${item['operater']!"" }
		                        </td>
		                        <td>
		                            	<a href="javascript:void(0);" onclick="viewDetail('${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}');">查看</a>&nbsp;
		                            	<a href="javascript:void(0);" onclick="viewDetail('${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}','edit');">修改</a>&nbsp;
		                            	<a href="javascript:void(0);" onclick="exportDetail('${((item.foCid)!(item.tCid!item.sCid!item.fCid))?c}');">导出</a>
		                        </td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">

	function viewLog(){
	    openwindow("${BasePath}/yitiansystem/taobao/log.sc?type=TAOBAO_CAT", 900, 700, "查看日志");
    }
<!--  
var setting = {
    view: {
    	showIcon:false,
        dblClickExpand: true  
    },
    async: {
		enable: true,
		url:"${BasePath}/yitiansystem/taobao/getTaobaoItem.sc",
		autoParam:["cid"],
		dataType:'json',
		dataFilter: filter
	},
    data: {
        simpleData: {  
            enable: true,  
            idKey: "cid",  
            pIdKey: "parent_cid",  
            rootPId: 0  
        }
    },
    callback: {
        onClick: onClick
    }  
};  
$(function(){
    createTree(0);  
});
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
function createTree(id) {  
    var zNodes;  
    $.ajax({
        url: '${BasePath}/yitiansystem/taobao/getTaobaoItem.sc',
        type:'post', 
        data: { "cid": id },  
        dataType: "json",  
        success: function (data) {  
            $.fn.zTree.init($("#treeDemo"), setting, data);  
        },  
        error: function (msg) {  
            alert("--获取淘宝分类异常--");  
        }  
    });  
}
function showMenu(){
	var nameObj = $("#categorySet");  
    var nameOffset = $("#categorySet").offset();  
    $("#menuContent").css({ left: nameOffset.left + "px", top: nameOffset.top + nameObj.outerHeight() + "px" }).slideDown("fast");  

    $("body").bind("mousedown", onBodyDown);  
}
//当点击窗口其他区域时zTree消息框隐藏  
function onBodyDown(event) {  
    if (!(event.target.id == "menuBtn" || 
    	    event.target.id == "menuContent" || 
    	    $(event.target).parents("#menuContent").length > 0)) {  
        hideMenu();  
    }  
}  
function hideMenu() {  
    $("#menuContent").fadeOut("fast");  
    $("body").unbind("mousedown", onBodyDown);  
} 

function onClick(e, treeId, treeNode) {  
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var cateObj = $("#categorySet");  
    var cidObj = $("#cid");
    var nameObj = $("#name");
    cateObj.val(treeNode.name);  
    nameObj.val(treeNode.name);
    cidObj.val(treeNode.cid);
    hideMenu();  
}

function queryTaobaoCats(){
	document.queryForm.submit();
}

function addTaobaoCat(){
	ygdgDialog.prompt('请输入淘宝一级分类代码：',function(value){
		value = $.trim(value);
		if(""!=value){
			parent.ygdg.dialog({
				id:'addTaobaoCat',
				content:'正在淘宝中获取资料，可能需要比较久的时间<img src="${BasePath}/images/loading.gif"/>',
				lock:true,
				title:'提示'
			});
			$.getJSON("${BasePath}/yitiansystem/taobao/addTaobaoCat.sc",{'cids':value},function(json){
				if("200"==json['resultCode']){
					parent.ygdg.dialog.list['addTaobaoCat'].close();
					location.href="${BasePath}/yitiansystem/taobao/goTaobaoCat.sc";
				}else{
					parent.ygdg.dialog.list['addTaobaoCat'].content("获取淘宝分类失败，请稍后再试！");
				}
			});
		}else{
			parent.ygdg.dialog({
				id:'addTaobaoCat',
				content:'淘宝分类代码不能为空！',
				title:'提示'
			});
			return false;
		}
	},"");
}

function batchDel(id){
	var ids = [];
	if(id==null){
		var extendId = $("input[name='ids']:checked");
		for(var i=0,length=extendId.length;i<length;i++){
			ids.push(extendId.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	if(ids.length==0){
		ygdg.dialog.alert("请选择要删除的分类");
		return;
	}
	var str = "";
	ygdg.dialog.confirm("确定批量删除分类吗？", function(){
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : "${BasePath}/yitiansystem/taobao/checkTaobaoCat.sc",
			success : function(json) {
				if("1"==json.result){
					location.href="${BasePath}/yitiansystem/taobao/goTaobaoCat.sc";
				}else if("0"==json.result){
					for(var i=0;i<json.reason.length;i++){
						str += "[分类编码：'"+json.reason[i].cid+"',"+
								"分类名称：'"+json.reason[i].name+"']";
					}
					parent.ygdg.dialog({
						content:str+"存在子节点，不能删除，请先删除末节点！",
						width:'600px',
						lock:true,
						title:'警告'
					});
				}else{
					ygdg.dialog.alert("批量删除淘宝分类失败，服务器发生异常！");
				}
			}
		});
	});
}
function viewDetail(cid,edit){
	$("#tempForm").remove();
	var url = '${BasePath}/yitiansystem/taobao/viewCatDetail.sc';
	if(edit=='edit'){
		url = '${BasePath}/yitiansystem/taobao/updateCatDetail.sc';
	}
	var str = 
	"<form id='tempForm' method='post' action='"+url+"' style='display: none;'>"+
	"<input type='hidden' name='cid' value='"+cid+"'/>"+
	"<input type='hidden' name='name' value='"+$('#name_'+cid).html()+"'/>"+
	"<input type='hidden' name='rootCatCode' value='"+$('#rootCode_'+cid).html()+"'/>"+
	"<input type='hidden' name='secondCatCode' value='"+$('#secondCode_'+cid).html()+"'/></form>";
	$("#bodyDiv").append(str);
	$("#tempForm").submit();
}
function exportDetail(cid){
	$("#tempForm2").remove();
	var str2 = 
		"<form id='tempForm2' method='post' action='${BasePath}/yitiansystem/taobao/exportCatDetail.sc' style='display: none;'>"+
		"<input type='hidden' name='cid' value='"+cid+"'/>"+
		"<input type='hidden' name='name' value='"+$('#name_'+cid).html()+"'/></form>";
	$("#bodyDiv").append(str2);
	$("#tempForm2").submit();
}
//-->
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
