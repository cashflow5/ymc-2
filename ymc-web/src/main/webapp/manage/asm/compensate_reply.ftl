<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购-商家中心-赔付工单详情-待商家处理</title>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/icon.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/iconfont.css" />
    <!-- 图片上传样式 -->
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/js/webuploader/webuploader.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/merchans_center.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/business-center.css" />
</head>
<script type="text/javascript">
var basePath = '${BasePath}';
var uploader;
</script>
<body>
        <div id="newmain" class="main_bd fr">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 售后 &gt; 赔付管理</p>
                    <form  name="queryForm" id="queryForm" method="post">
                   				<input type="hidden" id="orderSaleTraceId" name="orderSaleTraceId" value="<#if vo??&&vo.id??>${vo.id!''}</#if>"/>
                            	<input type="hidden" name="orderTraceNo" value="<#if vo??&&vo.orderTraceNo??>${vo.orderTraceNo!''}</#if>"/>
                            	<input type="hidden" name="operateType" value=2 />
                            	<input type="hidden" name="traceStatus" id ="traceStatus" />
                            	<!--周末标志 （不用实时刷新剩余时间） -->
                                <input type="hidden" id="isWeekendFlag" name="isWeekendFlag" value="<#if isWeekendFlag??>${isWeekendFlag!'false'}</#if>" />
                                    
                    <div class="tab_panel relative">
                        <div class="tab_content">
                            <div class="pay-box">
                                <h3 class="pt10">赔付工单详情</h3>
                                <ul class="pay-list clearfix">
                                    <li><span class="stitle">工单号：</span><#if vo??&&vo.orderTraceNo??>${vo.orderTraceNo!''}</#if></li>
                                    <li><span class="stitle">创建时间：</span><#if vo??&&vo.createTime??>${vo.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></li>
                                    <li><span class="stitle">工单状态：</span>
                                    	<#if vo??&&vo.traceStatus??&&vo.traceStatus==3><span class="bold">待处理</span>
                                    	<#elseif vo??&&vo.traceStatus??&&vo.traceStatus==0>不同意（申诉中）  
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==1>同意赔付
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==2>申诉成功
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==3>申诉失败</#if>
                                    </li>
                                    <li><span class="stitle">订单号：</span><#if vo??&&vo.orderSubNo??>${vo.orderSubNo!''}</#if></li>
                                    <li><span class="stitle">订单状态：</span><#if vo??&&vo.orderStatus??>${vo.orderStatus!''}</#if></li>
                                    <li><span class="stitle">赔付方式：</span><#if vo??&&vo.compensateWay??&&vo.compensateWay==1>返现
                                           <#elseif  vo??&&vo.compensateWay??&&vo.compensateWay==2>礼品卡</#if></li>
                                    <li><span class="stitle">等值金额：</span><#if vo??&&vo.compensateAmount??><span class="cred bold">${vo.compensateAmount!0.0}</span>元</#if></li>
                                    <li><span class="stitle">赔付原因：</span><#if vo??&&vo.secondProblemName??>${vo.secondProblemName!''}</#if></li>
                                    <li><span class="stitle">剩余时间：</span><#if isWeekendFlag??&&isWeekendFlag=="false">
	                                   			<input type="hidden" id="deadline" name="deadline" 
	                                       			value="<#if (vo.leftTime)??>${vo.leftTime!-1}<#else>-1</#if>" />
	                                        	<span id="jsTime"></span>
                                        <#else><#if (vo.leftTimeToArray)??><span class="cred">${vo.leftTimeToArray[0]}</span>小时<span class="cred">${vo.leftTimeToArray[1]}</span>分钟<span class="cred">${vo.leftTimeToArray[2]}</span>秒</#if>
                                        </#if>	
                                    </li>
                                    <li class="w958 pb10"><span class="stitle">申请理由：</span><#if vo??&&vo.issueDescription??>${vo.issueDescription!''}</#if></li>
                                    <li class="w958 clearfix"><span class="stitle fl">审核意见：</span>
                                        <div class="text fl">
                                            <textarea class="view fl" maxlength="200" id="processRemark" name="processRemark"></textarea>
                                            
                                            <span class="cred fl ml5 mt30">*需要申诉时必填</span>
                                        </div>
                                    </li>
                                    <input type="hidden" id="filePaths" name="filePaths" />
                                    <li class="w958 clearfix"><span class="stitle fl"></span>
                                        <div class="text mt10 fl clearfix">
                                            <a id="filePickerBatch" class="up-btn up-btn-upload fl mt10" href="javascript:;">上传凭证</a>
                                            <ul class="upload_list fl" id="fileList"></ul>
                                        </div>
                                        <p class="text Gray">最多可上传7张图片，每张图片大小不超过5M，支持bmp\jpeg\jpg\gif\png格式文件。</p>
                                    </li>
                                    
                                    <li class="w958 clearfix mt15"><span class="stitle fl"></span>
                                        <div class="text fl">
                                            <a class="btn-blue-3" href="javascript:approve();">同意赔付</a>
                                            <a class="btn-gray-1" href="javascript:replyForCompensate(this);">我要申诉</a>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>

 <!-- 图片上传插件 -->
<script  type="text/javascript" src="${BasePath}/yougou/js/webuploader/webuploader.js"></script>
<script  type="text/javascript" src="${BasePath}/yougou/js/uploadForCompensate.js"></script>
 <!-- form表单ajax提交用 -->
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.form.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/countDown.js"></script>
<script type="text/javascript">
$(function() {
	var isWeekendFlag = $('#isWeekendFlag').val();
	if(isWeekendFlag=="false"){
		$("input[name='deadline']").each(function(){
			var domTime=$("#jsTime");
			d={timeMillis:$(this).val()};
			domTime.attr('endTime',d.timeMillis);
			domTime.countDown();
		}); 

	}

});

function approve(){
  var id = $('#orderSaleTraceId').val();
  ygdg.dialog.confirm("确定同意赔付吗?",function(){
	   $('#traceStatus').val(2);
	   $.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:$("#queryForm").serialize(),
			url : "${BasePath}/afterSale/reply_for_compensate.sc",
			success : function(data) {
				if(data.resultCode=="200"){
					ygdg.dialog.alert("同意赔付成功",function(){
								    	 document.location.href="${BasePath}/afterSale/compensate_handling_list.sc";
								    });
				}else{
					ygdg.dialog.alert(data.msg);
				}
			},
			error : function(data) {
				ygdg.dialog.alert("ajax调用后台返回失败！");
		    }
	   });
   });
}

function replyForCompensate(curObj){
    $('#traceStatus').val(0);//表示申诉
	var filePaths = $('#fileList').find('input').map(function(){
					  return $(this).val();
					}).get().join(",") ;
	
	if(''!=filePaths){
		$("#filePaths").val(filePaths);
	}
	
	if($.trim($("#processRemark").val())==''){
		ygdg.dialog.alert("请首先填写审核意见再提交申诉！");
		return;
	}
	
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#queryForm").serialize(),
		url : "${BasePath}/afterSale/reply_for_compensate.sc",
		success : function(data) {
			if(data.resultCode=="200"){
				ygdg.dialog.alert("申诉成功",function(){
							    	 document.location.href="${BasePath}/afterSale/compensate_handling_list.sc";
							    });
			}else{
				ygdg.dialog.alert(data.msg);
			}
		},
		error : function(data) {
			ygdg.dialog.alert("ajax调用后台返回失败！");
	    }
   });
	
}
</script>
</body>

</html>
