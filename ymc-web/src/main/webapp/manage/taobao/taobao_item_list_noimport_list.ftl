<table class="common_lsttbl mt10">
	<thead>
		<tr>
			<th>商品名称</th>
			<th>颜色</th>
			<th>价格</th>
			<th>下载时间</th>
			<th>验证状态</th>
			<th>操作</th>
		</tr>
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<tr class="do_tr">
				<td colspan="17" style="padding:0;text-align:left;">
					<div class="tb_dobox">
						<div class="dobox" style="margin-left: -5px;position:relative">
							  <label><input class="chkalls" id="checkAllImport" type="checkbox" /><a href="javascript:taobaoItem.importItem()">导入优购</a></label>
							 &nbsp;&nbsp;&nbsp;&nbsp;<label><input class="chkalls" id="checkAllDel" type="checkbox" /><a href="javascript:;" onclick="taobaoItem.del()">删除</a></label> 
							 &nbsp;&nbsp;&nbsp;&nbsp;<label><input class="chkalls" id="checkAllExport" type="checkbox" /><a href="javascript:;" onclick="taobaoItem.exportExcel()">导出修改商品</a></label>
							 &nbsp;&nbsp;&nbsp;&nbsp;<label><input class="chkalls" id="checkAllInitData" type="checkbox" /><a href="javascript:;" onclick="taobaoItem.initData()">根据模板初始化商品</a></label>
							 <div id="filePicker" style="width:110px;display:inline-block;position:absolute;left:410px;top:-3px;">导入修改商品</div>
							 <div id="downloadError" style="display:inline-block;width:350px;position:absolute;left:500px;top:0px;display:none">成功导入<span id="successCount"></span>条，失败<span id="erroCount"></span>条 <a href="" style="color:red;" id="errorLink">下载失败明细</a></div>
							 <div id="downloadErrorInfo" style="display:inline-block;width:350px;position:absolute;left:500px;top:0px;display:none"></div>
						</div>
						<div class="page"> 
							<#if pageFinder ??>
								<#import "/manage/widget/page.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>
						</div>
					</div>
				</td>
			</tr>
		</#if>
	</thead>
	<tbody id="tbody" class="common_proitm">
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<#list pageFinder.data as item>
				<!--全选操作部分-->
				<tr class="line_gap" id="${item.commodity_no!""}-1">
                	<td colspan="7"></td>
                </tr>
                <tr class="proitm_hd" id="${item.commodity_no!""}-2">
                	<td colspan="7">
                    	<input type="checkbox" value="${item.extendId!""}" extendVal="${item.numIid!""}|${item.extendId!""}" checkstatus="${item.checkStatus}" name="extendId" class="checkone ml8">
                    	<span class="ml5">淘宝商品ID：${item.numIid!"—"}</span>
                    	<span class="ml15">淘宝分类：${item.taobaoCatFullName!"—"}</span>
                    </td>
                 </tr>
				<tr class="proitm_bd">
					<td style="width:300px;text-align:left">
						<div class="pro_pic fl">
	                	    <#if item.defaultPic??&&item.defaultPic!=''>
	                	        <img width="40" height="39" alt="" src="${item.defaultPic!''}"/>
	                	    <#else>
	                	        <img width="40" height="39" alt="" src="${BasePath}/yougou/images/nopic.jpg"/>
	                	    </#if>
                        </div>
                         <div class="txt_inf fl" style="width:200px;">
	                        	<p class="mb3" style="color:#3366cc">${item.title!''}</p>
	                            <p>商品款号：${item.yougouStyleNo!"—"}</p>
	                            <p>商家款色编码：${item.yougouSupplierCode!'—'}</p>
                          </div>
					</td>
					<td style="width:150px;">${item.yougouSpecName!''}</td>
					<td style="width:150px;">
						<p>淘宝价:${item.price!"—"}</p>
						<p>优购价:${item.yougouPrice!"—"}</p>
					</td>
					<td style="width:115px;">${item.operated!''}</td>
					<td style="width:150px;">
						<#if item.checkStatus??&&item.checkStatus=="0">
							<span type="0" style="color:red">校验未通过</span>
						<#elseif item.checkStatus??&&item.checkStatus=="1">
							<span type="1" style="color:green">校验已通过</span>
						<#elseif item.checkStatus??&&item.checkStatus=="2">
							<span type="2" style="color:#EAC100;">后台处理中</span>
						</#if>
					</td>
					<td style="width:115px;">
					    <!--
						<a href="${BasePath}/taobao/toUpdateTaobaoItemNew.sc?extendId=${item.extendId}&numIid=${item.numIid}">修改商品新</a><br>
						 -->						
						<#if item.isImportYougou??>
							<#if item.checkStatus??&&item.checkStatus=="0">
								<a href="javascript:taobaoItem.toUpdateTaobaoItem('${item.extendId}','${item.numIid}')">修改商品</a><br>
								导入优购<br>
								导入审核<br>
								<a href="javascript:taobaoItem.del('${(item.extendId)!''}')">删除商品</a>
							<#elseif item.checkStatus??&&item.checkStatus=="1">
								<a href="javascript:taobaoItem.toUpdateTaobaoItem('${item.extendId}','${item.numIid}')">修改商品</a><br>
								<a href="javascript:taobaoItem.importItem('${(item.extendId)!''}','${(item.checkStatus)!''}',0)">导入优购</a><br>
								<a href="javascript:taobaoItem.importItem('${(item.extendId)!''}','${(item.checkStatus)!''}',1)">导入审核</a><br>
								<a href="javascript:taobaoItem.del('${(item.extendId)!''}')">删除商品</a>
							<#elseif item.checkStatus??&&item.checkStatus=="2">
							            修改商品<br>
								导入优购<br>
								导入审核<br>
								删除商品
							</#if>
						</#if>
						<a href="javascript:taobaoItem.showErrorMsg('${(item.extendId)!''}')" style="color:red;display:none" id="${(item.extendId)!''}">查看</a>
					</td>
				</tr>
			</#list>
		<#else>
			<tr class="do_tr">
				<td class="td-no" colspan="17">
						没有相关数据
				</td>
			</tr>
		</#if>	
	</tbody>
</table>
<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
<input type="hidden" id="pageNo" value="${pageFinder.pageNo}">
<!--分页start-->
<div class="page_box">
<div class="dobox">
</div>
<#if pageFinder ??>
	<#import "/manage/widget/common4ajax.ftl" as page>
	<@page.queryForm formId="queryVoform"/>
</#if>
</div>
<!--分页end-->
</#if>
<style>
.webuploader-pick{position: relative;
display: inline-block;
cursor: pointer;
background: none repeat scroll 0% 0% #00B7EE;
padding: 1px 5px;
color: #FFF;
text-align: center;
border-radius: 3px;
overflow: hidden;line-height: 20px;margin-top:5px;}
#downloadError span{color:red;margin-right:0px;}
</style>
<script>

var checkStatus = function(extendId){
    alert(extendId);
	var result = "";
	$.ajax({
		url:'${BasePath}/taobao/toUpdateTaobaoItem.sc',
		async:false,
		data:"extendId="+commodityNo,
		dataType:'json',
		success:function(json){
			if(json){
				if(!json['result']){
					ygdg.dialog.alert('商品已经上架，请刷新页面并到“在售商品”页面下架修改');
				}else{
					//$("#tempForm").attr("action","${BasePath}/commodity/goUpdateCommodity.sc");
					$("#tempForm").attr("action","${BasePath}/extendId/toUpdateTaobaoItemNew.sc");
					$("#tempForm input[name='commodityNo']").val(commodityNo);
					$("#tempForm").submit();
					$("#tempForm input[name='commodityNo']").val("");
					$("#tempForm").removeAttr("action");
					return false;
				}
			}else{
				ygdg.dialog.alert('商品信息有误，请联系优购技术支持！');
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			ygdg.dialog.alert('商品信息有误，请联系优购技术支持！');
		}
	});
};


/*************商品资料导入******************/
var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,

    // swf文件路径
    swf: '${BasePath}/webuploader/Uploader.swf',

    // 文件接收服务端。
    server: "${BasePath}/taobao/exportExcel.sc",

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id:'#filePicker',
    	multiple:false
    },
    // 只允许选择excel。
    accept: {
        title: 'Excel',
        extensions: '*',
        mimeTypes: 'xlsx/*'
    },
    formData:{
       no:1,
    },
    duplicate:1,   //不去重
    //fileSingleSizeLimit:1024*5
    compress:false  //压缩
});

uploader.on( 'beforeFileQueued', function( file ) {
	var fileType = file.ext;
	if(fileType!="xlsx"){
		ygdg.dialog.alert("请上传后缀为.xlsx的Excel文件");
		return false;
	}
	return true;
});

uploader.on( 'fileQueued', function( file ) {
 	$("#downloadError").hide();
	ymc_common.loading("show","正在导入......");
	return true;
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploader.on( 'uploadSuccess', function( file,response) {
	ymc_common.loading();
	if(typeof response!="object"){
		response = eval('('+ response +')');
	}
	if(response.resultCode=="200"){
		taobaoItem.exportResponse = response;
		loadData(1);
	}else{
		ygdg.dialog.alert(response.result);
	}
});

// 文件上传失败，显示上传出错。
uploader.on( 'uploadError', function( file ) {
	ymc_common.loading();
    ygdg.dialog.alert("上传失败");
});
</script>
					