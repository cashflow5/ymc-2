<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商家中心 - 营销中心 - 官方活动报名</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>	
	<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
	<style type="text/css">
	   .webuploader-pick {
		background: url("/yougou/images/button_s.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
		background-position: 100% -50px;
		color:#3366cc;
	    border-radius: 3px;
	    cursor: pointer;
	    display: inline-block;
	    overflow: hidden;
	    padding: 1px;
	    position: relative;
	    text-align: center;
	    float:left;
	    width:85px;
		}
		div.button{
			padding-left: 1px;
		}
		div.button:hover{
			background-position:0 -100px;
		}
		.webuploader-pick-hover {
			background-position:100% -150px;
		}
	</style>
</head>
<body>
<div id="newmain" class="main_bd fr">
    <div class="main_container">
        <div class="normal_box">
            <p class="title site">当前位置：商家中心 &gt; 营销中心 &gt; 官方活动报名 &gt; ${officialActive.activeName!''}</p>
            <div class="tab_panel">
                <div class="notice-box mt15 mb30">
                	<p>活动名称：<strong>${officialActive.activeName!''}</strong></p>  
                    <p>活动类型：<#list activeType?keys as key>
										    <#if key?number == officialActive.activeType>	
												${activeType[key]}				
											</#if>
										</#list></p>
                    <p>报名时间： ${officialActive.signUpStartTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.signUpEndTime?string("yy年MM月dd日 HH:mm:ss")}</p>
                    <p>审核时间： ${officialActive.merchantAuditStartTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.merchantAuditEndTime?string("yy年MM月dd日  HH:mm:ss")}</p>
                    <p>活动时间： ${officialActive.startTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.endTime?string("yy年MM月dd日 HH:mm:ss")}</p>
                    <p>报名商品： 品牌：${brandNames} 品类：${catNames}</p> 
                    <p>商品数量： 报名活动数量最少为${officialActive.minCommodityCount!'0'},最高为${officialActive.maxCommodityCount!'0'}</p>
                    <#if merchantActiveSignup??>
						<#if merchantActiveSignup.status?number == 1>
							<p>报名状态： <strong class="f_yellow">新建</strong></p>
						</#if>
						<#if merchantActiveSignup.status?number == 2>
							<p>报名状态： <strong class="f_yellow">等待审核</strong></p>
						</#if>
						<#if merchantActiveSignup.status?number == 3>
							<p>报名状态： <strong class="f_yellow">审核通过</strong></p>
						</#if>  
						<#if merchantActiveSignup.status?number == 4>
							<p>报名状态： <strong class="f_yellow">审核不通过</strong></p>
							<p>审核意见：${merchantActiveSignup.auditRemark!''}</p>
						</#if>  
						<#if merchantActiveSignup.status?number == 5>
							<p>报名状态： <strong class="f_yellow">报名已结束</strong></p>
						</#if>  
					</#if>
                    
                </div>
                <ul class="tab">
                    <li onclick="location.href='${BasePath}/active/to_signupOfficialActive.sc?id=${officialActive.id}'"><span>活动介绍</span></li>
                    <li onclick="location.href='${BasePath}/active/mySignupOfficialActive.sc?id=${officialActive.id}'" class="curr"><span>活动报名</span></li>
                </ul>
                <div class="tab_content">
                    <div class="title-content-box">
                        <h3>活动规则</h3>
                        <dl class="c-h-2column-left c-h-2column-reg mt25">
                            <dt class="label">是否支持优惠券：<#if officialActive.isSupportCoupons == 0><dd class="c-h-contain">否</dd></#if>
							<#if officialActive.isSupportCoupons == 1><dd class="c-h-contain">是</dd></#if></dt>
                            <dt class="label">支持平台：</dt>
                            <#if officialActive.platform == 'ALL'>
                            <dd class="c-h-contain">网站,手机</dd>
                            </#if>
                            <#if officialActive.platform == 'website'>
                            <dd class="c-h-contain">网站</dd>
                            </#if>
                            <#if officialActive.platform == 'mobile'>
                            <dd class="c-h-contain">手机</dd>
                            </#if>
                            <dt class="label">活动规则：</dt>
                            <#if officialActive.activeType == 10>
                            <dd class="c-h-contain">
                            	<#list officialActive.activeRuleList as item>
                                <p>买${item.minRuleAmount}件，${item.decreaseAmount?string("#.##")}折</p>          
                                </#list>                      
                            </dd>
                            </#if>
							<#if officialActive.activeType == 11>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <p>买${item.minRuleAmount}件，${item.decreaseAmount?string("#.##")}元</p>          
                                </#list> 
                            </dd>
                            </#if>
							<#if officialActive.activeType == 13>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <p>满${item.minRuleAmount?string("#.##")}元，打${item.decreaseAmount?string("#.##")}折</p>          
                                </#list> 
                            </dd>
                            </#if>
							<#if officialActive.activeType == 2>
                            <dd class="c-h-contain">
                               	请导入活动价
                            </dd>
                            </#if>
							<#if officialActive.activeType == 1>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <#if officialActive.loopLimit == 0>
                                <p>每满${item.minRuleAmount?string("#.##")}元，减${item.decreaseAmount?string("#.##")}元</p>    
                                <#else>
                                 <p>满${item.minRuleAmount?string("#.##")}元，减${item.decreaseAmount?string("#.##")}元</p>    
                                </#if>      
                                </#list> 
                            </dd>
                            </#if>
                        </dl>
                    </div>
                    <div class="title-content-box">
                        <h3>添加活动商品</h3>
						<#if merchantActiveSignup.status?number == 1 || merchantActiveSignup.status?number == 4 >    
                        <#if officialActive.isSupportCoupons == 1>
						<#if uniqueCoupon == '1'>
                        <dl class="c-h-2column-left c-h-2column-reg mt25">
                            <dt class="label" style="width:120px">最高可承担卡券金额：</dt>
                            <dd class="c-h-contain">
                                 <input type="text" id="merchantHighestCoupon" class="inputtxt" style="width:105px;" value=""/>元
                            </dd>
                        </dl>
                        </#if>
                        </#if>

                        <dl class="c-h-2column-left c-h-2column-reg mt25">
                            <dt class="label" style="width:120px">导入商品：</dt>
                            <dd class="c-h-contain">
                                <div class="c-h-box">                 
                                    <div class="mt10">
                                    	<div id="thelist" class="uploader-list" style="margin-left:10px;"></div>
                                        <div id="picker" class="button fl">选择文件</div>
                                        <a id="ctlBtn" class="button fl" onclick="" style="vertical-align:middle; margin-left:20px;  color: #3366cc"><span>上传文件</span></a>
				        				
										 &nbsp;&nbsp;&nbsp;&nbsp;请按模板规范上传<a href="javascript:downloadTemplateFile()" class="f_blue">[下载Excel模板]</a>
                                    </div>
                                    <dl class="c-h-2column-left c-h-2column-reg mt20" style="margin-left:10px;">
                                        <div id="result_msg">
                                        </div>
                                        <div id="warm_msg"> 
                                        </div>
                                    </dl>
                                </div>
                            </dd>
                        </dl>
						</#if>
                        <dl class="c-h-2column-left c-h-2column-reg mt25">
                            <dt class="label" style="width:120px">商品列表：</dt>
                            <dd class="c-h-contain">
                                <!--搜索start-->
                                <div class="search_box clearfix" style="padding-top:0;">
                                    <form action="#" id="queryVoform" name="queryVoform" method="post">
                                        <span>
                                    <label>商品编码：</label>
                                    <input type="text" name="comodityNo" class="inputtxt" style="width:105px;" value="" />
                                </span>
                                        <span>
                                    <label style="width:110px;">商品名称：</label>
                                    <input type="text" class="inputtxt" name="comodityName" style="width:105px;" value="" />
                                </span>
                                        <span> 
                                    <a class="button" id="searchBtn" onclick="" style="vertical-align:middle; margin-left:45px;"><span>查询</span></a>
                                        </span>
                                    </form>
                                </div>
                                <#if officialActive.isSupportCoupons == 1>
                                <div>
                                	<div style="color:red;margin-left:18px;">温馨提示：最高可承担卡券金额结算方式以双方签订的协议为准，填写金额需大于等于协议约定金额
                                	5倍的数值，数值越高可活动越有利的促销资源；</div>
                                	<div style="color:red;margin-left:78px;">最高可承担卡券金额为“非必填项”。</div>
                                </div>	
                                </#if>
                                <!--搜索end-->
                                <!--列表start-->
                                <div id="content_list">
			
	   							</div>
                               <div class="mt20"> 
                               		<#if merchantActiveSignup.status?number == 1 || merchantActiveSignup.status?number == 4>
                               		 <a class="button" href="javascript:updateOfficialActiveSignup(1)"><span>提交报名</span></a>
							 		</#if>  
									<#if merchantActiveSignup.status?number == 2>
							 		<a class="button" href="javascript:updateOfficialActiveSignup(2)"><span>撤消报名</span></a>
							 		</#if>
								</div>
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
<#if merchantActiveSignup.status?number == 1 || merchantActiveSignup.status?number == 4>
var $btn = $('#ctlBtn');

var state = 'pending';
var exDialog;
var fileid='';
var serverUrl = "${BasePath}/active/importActiveCommodity.sc?activeId=${officialActive.id}&signupId=${merchantActiveSignup.id}";

var uploaderCSV = WebUploader.create({
    swf: '${BasePath}/webuploader/Uploader.swf',
    server: serverUrl,
    pick: {
    	id: '#picker',
    	multiple:false
    },
    accept: {
        title: 'XLS',
        extensions: '*',
        mimeTypes: 'xls/*'
    },
    fileNumLimit:1,
    fileSingleSizeLimit:10*1024*1024,
    formData:{
       highestCoupon:0
    },
    duplicate:1,   //不去重
    compress:false //压缩
});

uploaderCSV.on( 'fileQueued', function(file) {
    fileid=file.id;
    $("#thelist").html( '<div id="' + file.id + '" class="item">' +
        '<span class="info">' + file.name + '</span>'+
        '&nbsp;&nbsp;&nbsp;&nbsp;<span class="state">等待上传...</span>' +
    '</div>' );
     $('#warm_msg').html("");
     $('#result_msg').html("");
});
// 文件上传过程中创建进度条实时显示。
uploaderCSV.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }

    $li.find('span.state').text('上传中');
    $percent.css( 'width', percentage * 100 + '%' );
});

uploaderCSV.on( 'beforeFileQueued', function( file ) {
    if(''!=fileid){
        uploaderCSV.removeFile(fileid,true);
    }
	var fileType = file.ext;
	if(fileType!="xls"){
		ygdg.dialog.alert("请上传后缀为.xls的数据文件");
		return false;
	}
	return true;
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploaderCSV.on( 'uploadSuccess', function( file,response) {
    exDialog.close();
    $( '#'+file.id ).find('span.state').text('已上传');
    if(typeof response!="object"){    
    	response = eval('('+response+')');
    }
	if(response.resultCode=="200"){
		loadData(1);
	    $('#result_msg').html('导入结果：成功'+(response.allCount-response.errorCount)+'条,失败'+response.errorCount+'条数据'); 
	    $('#warm_msg').html(response.uuid ? '<a class="f_blue" id="linker" href="${BasePath}/commodity/import/error/download.sc?isImport=1&type=xls&uuid=' + response.uuid + '" target="_blank">失败明细下载</a>':'');			
	}else{
		$('#result_msg').html('导入失败，请重试！');
		$('#warm_msg').html(response.uuid ? '<a class="f_blue" id="linker" href="${BasePath}/commodity/import/error/download.sc?isImport=1&type=xls&uuid=' + response.uuid + '" target="_blank">失败明细下载</a>':'');
	}
});

// 文件上传失败，显示上传出错。
uploaderCSV.on( 'uploadError', function( file ) {
    exDialog.close();
    $( '#'+file.id ).find('span.state').text('上传出错');
    ygdg.dialog.alert("上传失败");
});
uploaderCSV.on( 'all', function( type ) {
    if ( type === 'startUpload' ) {
        state = 'uploading';
    } else if ( type === 'stopUpload' ) {
        state = 'paused';
    } else if ( type === 'uploadFinished' ) {
        state = 'done';
    }
    if ( state === 'uploading' ) {
        $btn.find('span').text('暂停上传');
    } else {
        $btn.find('span').text('开始上传');
    }
});
$btn.on( 'click', function() {
    if ( state === 'uploading' ) {
        uploaderCSV.stop();
    } else if(uploaderCSV.getFiles("queued").length>0){
    	<#if officialActive.isSupportCoupons == 1>
    	<#if uniqueCoupon == '1'>
    	var highestCoupon = $('#merchantHighestCoupon').val();
    	uploaderCSV.option('formData',{'highestCoupon':highestCoupon});
    	var pattern = /^[1-9][0-9]*$/;
        var re = new RegExp(pattern);
        if (!(highestCoupon.match(re) != null && highestCoupon%5==0)) {
            ygdg.dialog.alert("最高可承担卡券金额请输入大于零的5的整数倍整数!");
            return;
        }else{
        	var couponDefault = '${couponDefault}';
        	var couponHighest = '${couponHighest}';
        	if(parseInt(highestCoupon) < parseInt(couponDefault) || parseInt(highestCoupon) > parseInt(couponHighest)){
        		ygdg.dialog.alert("请输入["+couponDefault+","+couponHighest+"]之间的5的整数倍整数!");
            	return;
        	}
        }       
    	</#if>
    	</#if>
    	exDialog=ygdg.dialog({
			content:"上传处理中，请稍等片刻<img src='${BasePath}/yougou/images/loading.gif'/>",
			title:'提示',
			cancel:function(){exDialog=null;return true;},
			cancelVal:'取消',
			lock:true
	    });
	    $('#warm_msg').html("");
        uploaderCSV.upload();
    }else{
    	ygdg.dialog.alert("请先选择要上传的Excel文件.");
    }
});
</#if>
$(function(){
  loadData(1);
  $("#searchBtn").click(function(){
  	 loadData(1);
  });
});

//加载数据
var doQueryUrl ="${BasePath}/active/getOfficialActiveCommodityList.sc?signupId=${merchantActiveSignup.id}&isSupportCoupons=${officialActive.isSupportCoupons}&status=${merchantActiveSignup.status}&activeType=${merchantActiveSignup.activeType}";
function loadData(page,pageSize){
	if(page==null){
		page=1;
	}
	if(typeof(pageSize)=='undefined'||pageSize==''||pageSize==null){
		pageSize = $("#pageSize").val();
	}else{
		$("#pageSize").val(pageSize);
	}
		$("#content_list").html('<table cellpadding="0" cellspacing="0" class="list_table">'+
    		'<thead>'+
            '<tr>'+
            '<th width="25">&nbsp;</th>'+
            '<th width="66">商品编码</th>'+
            '<th width="280">商品名称</th>'+
            '<th width="36">款色</th>'+
            '<th width="50">优购价</th>'+
            '<th width="50">活动价</th>'+
            '<th width="36">操作</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>'+
           		'<tr><td colspan="13" style="text-align:center">正在加载.....</td></tr>'+
         	'</tbody>'+
    '</table>');
    
    var searchParams = $("#queryVoform").serialize();
    var url = doQueryUrl+"&page="+page;
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:searchParams,
		url :url,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}
var dialog_obj;
function agreement(){
	if($('#promotion_checkbox').is(':checked')){
		dialog_obj.button({
        	name: '同意',
        	focus: true,
        	disabled:false,
        	callback:function(){
        		commitSignup(1);
        	}
    	},
    	{
        	name: '取消'
    	})
	}else{
		dialog_obj.button({
        	name: '同意',
        	focus: false,
        	disabled:true
    	},
    	{
        	name: '取消'
    	})
	}
}

//提交/撤消报名
function updateOfficialActiveSignup(type){
	var msg = '';
	var flag = true;
	if(type==1){	
	<#if officialActive.isSupportCoupons == 1>
	flag = false;
	dialog_obj=ygdg.dialog({
            title:"促销协议",
            content : '<div class="promotion_box">'+
        				'<h1>促销活动最高可承担卡券金额协议</h1>'+
        				'<div class="promotion_cont">'+
            			'	尊敬的供应商，请您再次确认本次活动报名中最高可承担卡券金额,一经勾'+
						'	选“我已仔细阅读并同意协议”并点击“同意”按键，即意味着商家同意与优购'+
						'	签订本协议并自愿受本协议约束，如优购审核通过后，本协议对商家及优购具有法律效力。'+
        				'</div>'+
        				'<div class="promotion_checkbox_box">'+
            			'<input id="promotion_checkbox" onclick="agreement()" type="checkbox" checked/><label class="promotion_label" for="promotion_checkbox">我已仔细阅读并同意协议</label>'+
        				'</div>'+
    					'</div>',
            max:false,
            min:false,
            width: '450px',
            height: '200px',
            lock:true,
            button:[
               {
                    name: '同意',
                    focus: true,
                    callback:function(){
                    	commitSignup(1);
                    }
                },
                {
                    name: '取消'
                }
            ]
        });
        </#if>
		msg = '确定提交报名吗?';
	}
	if(flag){
		if(type==2){
			msg = "确定撤消报名吗?";
		}
		ygdg.dialog.confirm(msg, function() {
		    commitSignup(type);
		});
	}
}

function commitSignup(type){
	if(type==1){
	    	var tatalCommodity = $('#tatalCommodity').val();
	    	if(!tatalCommodity){
	    		tatalCommodity = 0;
	    	}
	    	var minCommodityCount = '${officialActive.minCommodityCount}';
	    	var maxCommodityCount = '${officialActive.maxCommodityCount}';
	    	if(!minCommodityCount){
	    		minCommodityCount = 0;
	    	}
	    	if(parseInt(tatalCommodity) <= parseInt(minCommodityCount)){
				ygdg.dialog.alert("报名活动商品数量必须高于"+minCommodityCount); 
				return;
			}
			if((parseInt(maxCommodityCount)!=0)  && (parseInt(tatalCommodity) > parseInt(maxCommodityCount))){
				ygdg.dialog.alert("报名活动商品数量最高"+maxCommodityCount); 
				return;
			}
			
	    }
		$.ajax({
			type: 'get',
			url: '/active/updateOfficialActiveSignup.sc?signupId=${merchantActiveSignup.id}&type='+type,
			dataType: 'json',
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data.msg) == 'Success') {
					ygdg.dialog.alert("操作成功！",function(){
					location.href='${BasePath}/active/mySignupOfficialActive.sc?id=${officialActive.id}';
					});
				} else if($.trim(data.msg) == 'Failure'){
					// this.error(jqXHR, textStatus, data);
					ygdg.dialog.alert("操作失败，请重试！");
				} else{
					ygdg.dialog.alert(data.msg);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				ygdg.dialog.alert('操作失败，请重试！');
			}
		});
}

//下载模板文件
function downloadTemplateFile(){
    var isNeedCoupon = '${officialActive.isSupportCoupons!'0'}';
    var isNeedActivePrice = "0";
    <#if officialActive.activeType == 2>
    	isNeedActivePrice = "1";
    </#if>
	<#if uniqueCoupon == '1'>
	isNeedCoupon = '0';
	</#if>
	location.href = '${BasePath}/active/downTemplateFile.sc?needCoupon='+isNeedCoupon+"&needActivePrice="+isNeedActivePrice;
}



</script>
</body>
</html>