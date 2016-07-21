<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<style type="text/css">
.goodsTxt {
            width: 28px;
            height: 20px;
            font: 12px/22px Arial,Helvetica,sans-serif;
            text-align: center;
            padding:0px 6px;
            border: 1px solid #C6C6C6;
            margin-top: -22px;
        }
        .ambtn_img{
            width: 15px;
            height: 17px;
            cursor: pointer;
            margin:5px;
        }
</style>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.7.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/filevalidattion.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/handlers.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/commoditymgmt/ajaxfileupload.js"></script>
<title>异常售后审核详情</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="container">

  <div class="list_content">
  			<div class="tab_panel">
				<ul class="tab">
					<li class="curr">
						<span>异常售后申请</span>
					</li>
					<li>
						<span>订单日志</span>
					</li>
				</ul>
			<div class="tab_content"> 

  
    <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/aftersale/api_monitor_config_modify.sc" name="queryForm" id="queryForm" method="post">
              	<div class="add_detail_box">
              	
              	<table class="order-list-tb mt20">
              	  <tfoot>
                    <tr>
                      <td colspan="3" id="info">
                          <span>登记人：${saleApplyBill.createor?default('')}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>登记时间：${saleApplyBill.createTime?string('yyyy-MM-dd HH:mm:ss')}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>优购订单号：${saleApplyBill.orderNo?default('')}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>售后申请单号：${saleApplyBill.applyNo?default('')}</span>
                      </td>
                    </tr>
                  </tfoot>
              	</table>
              	<input type="hidden" name="imgTable" id="imgTable" value="${picCertificate?if_exists}"/>
              	 <div class="panel-body pd10 c5f">
              	       <span>
                       <lable>登记类型：</label>
                       <#if exceptionType??&&exceptionType=='LOST_GOODS'>丢件
                       <#elseif exceptionType??&&exceptionType=='DRAIN_GOODS'>漏发货
                       <#elseif exceptionType??&&exceptionType=='ERROR_GOODS'>错发货
                       <#elseif exceptionType??&&exceptionType=='QUALITY_GOODS'>质量问题投诉
                       <#elseif exceptionType??&&exceptionType=='REJECT_GOODS'>拒收未质检
                       <#else>-</#if>
                       </span>
                 </div>


                   <table cellpadding="0" cellspacing="0" class="order-list-tb mt10" style="width:840px;float:left;text-align:center;">
                		<thead>
                        <tr>
                        <th style="text-align:center;">&nbsp;</th>
                        <th style="text-align:center;">商品名称</th>
                        <th style="text-align:center;">商品规格</th>
                        <th style="text-align:center;">商品款色编码</th>
                        <th style="text-align:center;">购买数量</th>
                        <th style="text-align:center;">可退数量</th>
                        <th style="text-align:center;">质检数量</th>
                        <th style="text-align:center;">登记数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if goodList??>
                        <#list goodList as item >
                		<tr>
                		    <td class="ft-cl-r">&nbsp;<input type="checkbox" no="${item['levelCode']!''}" value="${item['levelCode']!''}" <#if item.flag!='1'>disabled<#elseif saleApplyBill.status!='SALE_APPLY'>checked disabled</#if>/></td>
                		    <td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><a href="${item['url']?default('')}" target="_blank">${item['prodName']?default('')}</a></div></td>
                			<td class="ft-cl-r">${item['commoditySpecificationStr']!''}</td>
                			<td class="ft-cl-r">${item['levelCode']!''}</td>
                			<td class="ft-cl-r">${item['commodityNum']!''}</td>
                			<td class="ft-cl-r">${item['usableNum']!''}</td>
                			<td class="ft-cl-r">${qaNum?default('0')}</td>
                            <td class="ft-cl-r" style="width:120px;"><#if item.flag!='1'>-<#else>${item['applyNum']!''}</#if></td>
                		 </tr>
                	     </#list>
                        <#else>
                        	<tr>
                        	<td colSpan="8">
                        	    抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>                    
                     </table>
                     
                 <div class="blank10"></div>   
			     	 
			    <div class="afterSale_dc">
			        <#if exceptionType??&&exceptionType=='QUALITY_GOODS'><span><lable>是否免单：</label><input type="radio" name="isFreeOrder" value="" disabled <#if isFreeOrder??&&isFreeOrder=='NO'>checked</#if>>不免单&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isFreeOrder" value="${isFreeOrder?default('')}" disabled <#if isFreeOrder??&&isFreeOrder=='YES'>checked</#if>>免单</span><div class="blank10"></div></#if>
                    <span><lable>问题描述：</label>${descException?default('')}</span>
                    <div class="blank10"></div>
                    <#if newOrderNo??>
                    <span><lable>优购订单号：</label>${newOrderNo?default('')}（补）<#if expressCode??>&nbsp;&nbsp;<lable>寄出快递：</label>${logisticsName?default('')}&nbsp;&nbsp;${expressCode?default('')}</#if></span>
                    </#if>
                </div>
     
      <#if saleApplyBill.status??&&saleApplyBill.status=='SALE_APPLY'>
      <div class="afterSale_dc">
                         图片凭证：<input type="file" id="imgMaps" name="imgMaps"/><input type="button" id="uploader" value="立即上传" onclick="changImg();"/>  最多可上传5张，支持GIF、JPG格式，大小不超过5M 
      </div>
	  <div class="afterSale_dc"><span id="viewimg"></span></div>
      <div class="afterSale_dc"><span style="color:#ff0000;font-weight:normal;font-size:12px;">*</span>审核备注：<textarea  style="width:430px;height:90px;" id="checkRemark"></textarea></div>
       <div class="blank10"></div>
      <div >&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;<input type="button" name="" value="审核通过" onclick="save();"/> &nbsp;<input type="button" name="" value="审核拒绝" onclick="refuesApppNo();"/></div>
      <#else>
      <div class="afterSale_dc">审核备注：${auditRemark?default('')}</div>
      </#if>
	  </div>
      </form>
      </div>      	
              	<!--订单日志-->
					<div style="display:none;margin-top:10px;overflow:auto;"  class="modify">
		                <table class="order-list-tb mt10" style="width:1024px;float:left;">
			                <thead>
				                <tr>
				                <th>日志类型</th>
				                <th>时间</th>
				                <th>操作人</th>
				                <th>行为</th>
				                <th>备注</th>
				                <th width="100" class="bdr">操作结果</th>
				                </tr>
			                </thead>
			                <tbody>
				                <#if log ??>
				                	<#list log as item>
						                <tr>
						                <td><#if item.logType ?? && item.logType == 1>操作日志</#if>
						                	<#if item.logType ?? && item.logType == 2>售后日志</#if>
						                	<#if item.logType ?? && item.logType == 3>退款日志</#if>
						                	<#if item.logType ?? && item.logType == 4>正常流转日志</#if>
						                </td>
						                <td><#if item.createTime ??>${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						                <td><#if item.operateUser ??>${item.operateUser}</#if></td>
						                <td><#if item.behavioutDescribe ??>${item.behavioutDescribe}</#if></td>
						                <td><#if item.remark ??>${item.remark!''}</#if></td>
						                <td class="td0">
						                  <#if item.operateResult ?? && item.operateResult == 1>成功<#else>失败</#if>
						                </td>
						                </tr>
				                	</#list>
				                </#if>
			                </tbody>
		                </table>
					</div>
</div>
</div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){	
		$(".tab li").click(function(){
			var index=$(".tab li").index(this);
			$(this).addClass("curr").siblings().removeClass("curr");
			$(".tab_content>div").hide();
			$(".tab_content>div").eq(index).show();
		});
})
function changImg(){
  $.ajaxFileUpload({
            type : "POST",
			url : "${BasePath}/yitiansystem/merchants/aftersale/startUploadCommodityDescribePic.sc",
			secureuri:false,  //是否启用安全提交
			fileElementId:"imgMaps", //表示文件域ID
			dataType:"text",   //数据类型
			success : function(data,status)
			{
			  var dt = data.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('<pre>',"").replace('</pre>','');
			  var json = jQuery.parseJSON(dt);
			  if(json.success==true){
			   //alert(json.reObj);
			   var no_temp=getNo();
			   if(no_temp>5){
			     alert("最大上传图片不得超过5张！");
			     return;
			   }
			    var imgTable = document.getElementById("imgTable").value;
			    var str = '<span id="img_'+no_temp+'"><a href="javascript: void(0);" onclick="openimg(\''+json.reObj+'\');" id="imgUrl_'+no_temp+'">'+json.reObj+'</a> <a href="#" onclick="deleteImg(\'img_'+no_temp+'\',\''+json.reCode+'\');">删除</a></span></br>';
			    $("#viewimg").append(str);
			  }else{
			     alert("上传失败，请重新上传");
			  }
			}
   });
}
var no=0;
function getNo(){
  no=no+1;
  return no;
}

function openimg(url){
   window.open(url,'新开googleWin',"fullscreen=1");
}

function deleteImg(imgno,imgname){
    $.ajax({
			type : "POST",
			url : "${BasePath}/yitiansystem/merchants/aftersale/deleteImg.sc",
			data : {
			  "filename":imgname
			},
			dataType : "json",
			success : function(data) {
			  if(data.success==true)
			  {
			     alert("删除成功");
			     $("#"+imgno).remove();
			  }else{
			     alert("删除失败,请联系管理员");
			  }
			}
		});
}
function save(){
   var reslut = false;
   var applyId="${saleApplyBill.id?default('')}";
   //var exceptionType=$("input[id='registType']:checked").val();
   var picCertificate="";
   //var cancelNum=$("#J_Amount").val();
   var auditRemark=$("#checkRemark").val();
   
   //if(exceptionType==null){
     // $("#registType").focus();
     // alert("请选择登记类型！");
     //return false;
  // }
   if(auditRemark =='' || auditRemark.length==0){
	       $("#checkRemark").focus();
	       alert("请输入审核备注！");
	       return false;
   }
   var no_temp=getNo();
   for (var i=1;i<no_temp;i++){
      if ($("#img_"+i).length > 0) {
       picCertificate=picCertificate+$("#imgUrl_"+i).html()+","
      }
   }
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/aftersale/save.sc",
		  data : {
				    "applyId":applyId,
				    //"exceptionType":exceptionType,
				    "picCertificate":picCertificate,
				    "auditRemark":auditRemark
				    //"cancelNum":cancelNum
		  },
		  dataType : "json",
		  beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
		  },
		  success : function(data) {
		    if(data.success==true){
				 alert("保存成功！");
				 return true;
		    }else{
				 alert("保存失败！");
				 return reslut;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('审核结果保存失败:' + XMLHttpRequest.responseText);
		  }
	});
}
function refuesApppNo(){
	var checkRemark = document.getElementById("checkRemark").value;
	if(checkRemark =='' || checkRemark.length==0){
	       alert("请输入审核备注！");
	       return false;
	}
    var picCertificate="";
    var no_temp=getNo();
    for (var i=1;i<no_temp;i++){
       if ($("#img_"+i).length > 0) {
         picCertificate=picCertificate+$("#imgUrl_"+i).html()+","
       }
    }
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
	$.ajax({
			type : "POST",
			url : "${BasePath}/yitiansystem/merchants/aftersale/refues.sc",
			data : {
			    "applyNo":'${saleApplyBill.applyNo?default('')}',
			    "remark":checkRemark,
			    "picCertificate":picCertificate
			},
			dataType : "json",
		    beforeSend: function(XMLHttpRequest){
			  submitform.attr('state', 'running');
		    },
			success : function(data) {
			  if(data.success==true)
			  {
			     alert("已成功拒绝该申请！");
			     submitform.attr('state', 'waiting');
			     location.reload();
			  }
			  else
			  {
			     alert("拒绝该申请失败,请联系管理员");
			  }
			}
		});
}
function saveConfig(){
   document.queryForm.submit();
}
var dataAMChange={
            min:1,
            max:1000,
            reg:function(x){
                return new RegExp("^[1-9]\\d*$").test(x);
            },
             amount:function(obj, mode) {
                    var x = jQuery(obj).val();
                    if (this.reg(parseInt(x))) {
                        if (mode) {
                            x++;
                        } else {
                            x--;
                        }
                    } else {
                        alert("请输入正确的数量！");
                        jQuery(obj).val(1);
                        jQuery(obj).focus();
                    }
                    return x;
                },
             reduce:function(obj) {
                    var x = this.amount(obj, false);
                    if (parseInt(x) >= this.min) {
                        jQuery(obj).val(x);
                    } else {
                        alert("商品数量最少为" + this.min
                                + "！");
                        jQuery(obj).val(1);
                        jQuery(obj).focus();
                    }
                },
            add:function (obj,max) {
                    var x = this.amount(obj, true);
                    var max = max || this.max;
                    if (parseInt(x) <= parseInt(max)) {
                        jQuery(obj).val(x);
                    } else {
                        alert("您所填写的商品数量超过最大限制！");
                        jQuery(obj).val(max == 0 ? 1 : max);
                        jQuery(obj).focus();
                    }
                },
            modify:function (obj,max) {
                    var x = jQuery(obj).val();
                    var max = max || this.max;
                    if (!this.reg(parseInt(x))) {
                        jQuery(obj).val(1);
                        jQuery(obj).focus();
                        alert("请输入正确的数量！");
                        return;
                    }
                    var intx = parseInt(x);
                    var intmax = parseInt(max);
                    if (intx < this.min) {
                        alert("商品数量最少为" + this.min
                                + "！");
                        jQuery(obj).val(this.min);
                        jQuery(obj).focus();
                    } else if (intx > intmax) {
                        alert("您所填写的商品数量超过最大限制！");
                        jQuery(obj).val(max == 0 ? 1 : max);
                        jQuery(obj).focus();
                    }
                }
};
</script>
