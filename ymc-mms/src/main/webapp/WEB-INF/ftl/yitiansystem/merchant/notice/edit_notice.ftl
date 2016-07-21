<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/kindeditor/kindeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/js/common/kindeditor/zh_CN.js" charset="utf-8"></script>
<title>优购商城--商家后台</title>
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">修改公告</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/notice/update_notice.sc" name="noticeFrom" id="noticeFrom" method="post"> 
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr>
	                        <td style="text-align:left;">
	                         	 <label> <span style="color:red;">&nbsp;*</span>分类：</label>
		                         <select name="noticeType" id="noticeType">
		                           <option value="">请选择类型</option>
		                           <option value="1" <#if mctNotice['noticeType']=="1" >selected="selected"</#if>>新功能</option>
		                           <option value="0" <#if mctNotice['noticeType']=="0" >selected="selected"</#if>>重要通知</option>
		                         </select>
	                        </td>
	                        <td style="text-align:left;">
	                         	 <label> <span style="color:red;">&nbsp;*</span>合作模式：</label>
		                         <select name="merchantType" id="merchantType">
		                           <option value="">请选择合作模式</option>
		                           <option value="1" <#if mctNotice['merchantType']=="1" >selected="selected"</#if>>入优购仓库，优购发货</option>
		                           <option value="2" <#if mctNotice['merchantType']=="2" >selected="selected"</#if>>不入优购仓库，优购发货</option>
		                           <option value="0" <#if mctNotice['merchantType']=="0" >selected="selected"</#if>>不入优购仓库，商家发货</option>
		                         </select>
	                        </td>
	                        <td style="text-align:left;">
	                             <input id="isTop" type="checkbox" value="1" name="isTop" <#if mctNotice['isTop']=="1" >checked="checked"</#if>>
	                         	 <label>置顶</label>
	                         	 <input id="isRed" type="checkbox" value="1" name="isRed" <#if mctNotice['isRed']=="1" >checked="checked"</#if>>
	                         	 <label>高亮</label>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td style="text-align:left;"  colspan=3>
	                         	 <label><span style="color:red;">&nbsp;*</span>标题：</label>
								 <input id="title" type="text" name="title" style="width:400px;" maxlength="40" title='限制40个字符' value="${mctNotice['title']!""}">
								 <input id="id" type="hidden" name="id" value="${mctNotice['id']!""}">
	                        </td>
	                    </tr>
	                    <tr>
	                        <td style="text-align:left;" colspan=3>
	                         	 <label><span style="color:red;">&nbsp;*</span>内容：</label>
	                         	 <textarea id="no_content" name="content" style="width:100%;height:465px;">${mctNotice['content']!""}</textarea>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td style="text-align:center;" colspan=3>
								<input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return updateNotice();">
	                        </td>
	                    </tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
var help= {};	
/** 文本编辑器选项 */
help.options = {
	resizeType: 0,
	allowPreviewEmoticons: false,
	allowImageUpload: true,
	allowFlashUpload: false,
	allowMediaUpload: false,
	allowFileUpload: false,
	uploadJson:'${BasePath}/yitiansystem/notice/uploadNoticePic.sc',
	newlineTag: 'br',
	items: [
		'source', '|', 
		'undo', 'redo', '|', 
		'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 
		'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
		'formatblock', 'fontname', 'fontsize', '|', 
		'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
		'image', 'table', 'hr', 'anchor', 'link', 'unlink'
	]
}; 
/** 文本编辑器 */
help.editor;

/*
 * 初始化商品描述编辑器
 */
KindEditor.ready(function(K) {
	help.editor = K.create('#no_content', help.options);
});
//提交
function updateNotice(){ 
    help.editor.sync();
	var noticeType = $("#noticeType").val();
	var  merchantType= $("#merchantType").val();
	var title = $("#title").val();
	var content = $("#no_content").val();
	if(noticeType=="" ){
		alert('请选择消息分类');
		return false;
	}
	if(merchantType=="" ){
		alert('请选择消息合作模式');
		return false;
	}
   if(title==""){
		alert('标题不能为空');
		return false;
	}
   if(content==""){
		alert('内容不能为空');
		return false;
	}
	document.noticeFrom.submit();
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
