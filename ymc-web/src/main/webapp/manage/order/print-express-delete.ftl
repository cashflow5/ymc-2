<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-单据打印</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<STYLE MEDIA="PRINT">    
		.noprint{display:none;}
		.loading{position:absolute;height:100%;width:100%;}
	</STYLE>
<object id=eprint classid="clsid:CA03A5A8-9890-49BE-BA4A-8C524EB06441" codebase="/yougou/js/eprint.cab#Version=3,0,0,6" viewasext VIEWASTEXT>
</object>
<script language="javascript">
		var print = null;
		var paperTypes = {
			kuaidi:{name:"kuaidi",width:230,height:127}
		};
 
		//打印预览
		function Preview(){
			setParameters();
			if (print.defaultPrinterName.length==0){
				alert("请先安装打印机，再执行此功能！");
				return;
			}
			PrintInit();
			print.Preview();
		}
 
		//直接打印
		function Print(){  
			if (print.defaultPrinterName.length==0){
				alert("请先安装打印机，再执行此功能！");
				return;
			}
			PrintInit();
			setParameters();
      		//print.Print();  //弹出打印对话框(默认值)
			print.Print(true);  //true   不出打印对话框直接打印
		}
		
		function PrintInit(){  //打印机初始化
			print.InitPrint();
			print.companyName = "优购科技";
			print.seriesNo = "8756-1131-1853-1185" ; 
			setParameters();
		}
		
    		var printCall = null;

		function setParameters(){
		    print.SetMarginMeasure(1);  //设置单位  1:毫米(默认值) 2:英寸
		    print.marginTop = 0.2;   //页面上边距
		    print.marginLeft =0.2; //页面左边距
		    print.marginRight = 0.2; //页面右边距
		    print.marginBottom = 0.2; //页面底边距
		    print.header = "";  //页面的页眉信息,设置值可以和下面IE的页眉页脚代码合并设置
        	print.paperSize = "kuaidi"; //纸张格式 
        	print.zoomValue = "100";//打印预览时候的显示缩放比例(打开预览页面按100％的方式显示)
 
		}
	</script>
	<script type="text/javascript">
		//判断快递单号是否含有非法字符（允许数字、字母（大小写均可）、*、_）
		//快递单号含有非法字符返回false、反之为true
		function checkExpressNoValid(expressNo) {
			var regex = new RegExp('[^\\w\*]{1,}', 'gi');
			return !regex.test(expressNo);
		}
		
		function checkExpressNo() {
			var expressId = $('#expressId').val();
			var expressIds = $("#expressIds").val();
			if (expressId != '' && expressId.length != 0) {
				return checkExpressNoValid($.trim(expressId));
			} else if (expressIds != '' && expressIds.length != 0) {
				var array = expressIds.split(",");
				for (var i = 0; i < array.length; i++) {
					if (!checkExpressNoValid($.trim(array[i]))) {
						return false;
					} 
				}
			}
			return true;
		}
		
		//清空空格的方法
		String.prototype.trim = function() {
			return this.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "");
		};
	
		//选择物流公司
		function choosePrintExpressTemplate(logisticsId){
			$("#logisticsId").val(logisticsId);
			$("#querFrom").submit();
		}
		
		//打印
		function printPage(obj, type) {
			//先验证
			if($("#logisticsCode").val()==""){
				ygdg.dialog.alert("请先选择物流公司！");
			}else if($("#expressId").val()==""&&$("#expressIds").val()==""){
				ygdg.dialog.alert("请输入快递单号！");
//			}else if($("#printContent").html().trim()==""){
//				ygdg.dialog.alert("打印内容不允许为空！");
			}else if (!checkExpressNo()) {
				ygdg.dialog.alert("请填写正确的快递单号！");
			}else{
				//在后台将快递单号与订单号建立关联
				var logisticsName=$("#logisticsName").val();
			    logisticsName = encodeURI(logisticsName); 
			   
				if($("input[name=connection]:checked").val()=="1"){
					if($("#expressIds").val().split(",").length!=parseInt($("#orderCount").val())){
						ygdg.dialog.alert("录入的快递单个数与您选择的订单个数不匹配！");
					}else{
						$.post("printExpressTemplate.sc",{expressId:$("#expressId").val(),expressIdCount:$("#expressIdCount").val(),expressIds:$("#expressIds").val(),orderNo:$("#orderNo").val(),logisticsName:logisticsName,logisticsCode:$("#logisticsCode").val()},function(data){
							if(data=="success"){
								var body = window.document.body.innerHTML;
								var printArea = window.document.getElementById(obj).innerHTML;
								window.document.body.innerHTML = printArea;
								setTimeout(function(){
										if(type=="view")
										{
											Preview(); //打印预览
										}
										else
										{
											Print(); //直接打印
										}
										window.document.body.innerHTML = body;
										$('#con2').attr('checked',true);
									},1000);
								
							}else{
								ygdg.dialog.alert(data);
							}
						});
					}
				}else{
					if($("#expressIdCount").val()==""){
						ygdg.dialog.alert("请输入张数！")
					}else{
						if(parseInt($("#expressIdCount").val())!=parseInt($("#orderCount").val())){
							ygdg.dialog.alert("录入的打印张数与您选择的订单个数不匹配！");
						}else{
							//在后台将快递单号与订单号建立关联
							$.get("printExpressTemplate.sc",{expressId:$("#expressId").val(),expressIdCount:$("#expressIdCount").val(),expressIds:$("#expressIds").val(),orderNo:$("#orderNo").val(),logisticsName:logisticsName,logisticsCode:$("#logisticsCode").val()},function(data){
								if(data=="success"){
									var body = window.document.body.innerHTML;
									var printArea = window.document.getElementById(obj).innerHTML;
									window.document.body.innerHTML = printArea;
									setTimeout(function(){
											if(type=="view")
											{
												Preview(); //打印预览
											}
											else
											{
												Print(); //直接打印
											}
											window.document.body.innerHTML = body;
											$('#con1').attr('checked',true);
										},1000);
									
								}else{
									ygdg.dialog.alert(data);
								}
							});
						}
					}
				}
			}
		}
		
	</script>
</head>
 
<body>
<div id="Load" class="noprint" style="position:absolute">加载中，请稍后...</div>
	<div id="Main" class="main_container" style="display:none;margin-top:10px;" >
		<div class="normal_box">
			<!--<p class="title site"></p>-->
			<div class="form_container">
				<form name="querFrom" id="querFrom" action="choosePrintExpressTemplate.sc" method="post" style="padding:0px;margin:0px;">
					<!--订单号-->
					<input type="hidden" name="orderNo" id="orderNo" value="${orderNo!''}"/>
					<input type="hidden" name="orderNos" id="orderNos" value="${orderNos!''}"/>
					<input type="hidden" name="orderCount" id="orderCount" value="${orderCount!''}"/>
					<!--物流公司id-->
					<input type="hidden" name="logisticsId" id="logisticsId" value="${logisticsId!''}"/>
					<input type="hidden" name="logisticsName" id="logisticsName" value=""/>
					<input type="hidden" name="logisticsCode" id="logisticsCode" value=""/>
					
					<div style="font-size:14px;font-weight:bold;">选择物流公司</div>
					<div style="padding:5px; ">
						<#if logisticscompanList??>
						     <#list logisticscompanList as item>
						     	<#if logisticsId??&&(logisticsId==item.id)>
						     		<script language="javascript">
						     			$("#logisticsName").val('${item.logistics_company_name!''}');
						     			$("#logisticsCode").val('${item.logistic_company_code!''}');
						     		</script>
						     	</#if>
						       	<input type="radio" <#if logisticsId??&&(logisticsId==item.id)>checked="checked"</#if> onclick="choosePrintExpressTemplate('${item.id!''}')" name="logisticsCompan" value="${item.id!''}">${item.logistics_company_name!''}
						       	&nbsp;&nbsp;
						     </#list>
					    </#if>
				    </div>
				    <div style="border-bottom:dashed 1px grey;padding:5px;"></div>
				    <div style="font-size:14px;font-weight:bold;padding-top:5px;">录入快递单号</div>
				    <#if orderCount==1 >
				    	快递单号：<input id="expressId" name="expressId" type="text" class="ginput" style="width:150px;"  />
				    	<input type="hidden" id="expressIdCount" name="expressIdCount" value="1"/>
				    <#else>
				    <div style="padding:5px;">
				    	<input type="radio" id="con1" name="connection" checked="checked" onclick="showExpressType(this)" value="0"/>连号&nbsp;&nbsp;&nbsp;&nbsp;
				    	<input type="radio" id="con2" name="connection" onclick="showExpressType(this)" value="1"/>非连号
				    </div>
				    <div id="conId1" style="padding:5px;">
				    	录入首个快递单号：<input id="expressId" name="expressId" type="text" class="ginput" style="width:150px;"  /> (连号)&nbsp; 
				    	<input type="hidden" id="expressIdCount" name="expressIdCount" value="${orderCount!''}"/> 
				    </div>
					<div id="conId2" style="padding:5px;display:none;">
						录入全部快递单号：<textarea id="expressIds" name="expressIds" class="areatxt" cols="45" rows="6" ></textarea> (非连号，多个快递单号用英文逗号隔开)
					</div>
					</#if>
					<div style="padding:5px;">
						[有${orderCount!''}个快递单即将打印，它们是：${orderNos!''}]
					</div>
					<div style="border-bottom:dashed 1px grey;padding:5px;"></div>
				    <div style="font-size:14px;font-weight:bold;padding-top:5px;">快递单预览</div>
					<div style="width:100%; text-align:center;padding-bottom:10px;">
					<span><a class="button" onclick="printPage('printContent','print')"><span>打印</span></a></span>
					<span style="padding-left:10px;"><a class="button" onclick="printPage('printContent','view')"><span>预览</span></a></span>
					</div>
					<!--打印内容-->
					<div id="printContent">
						<#if expressTemplateList??>
							<#list expressTemplateList as item>
								${item!''}
							</#list>
						</#if>
	                </div>
	                <input type="hidden" id="error" value="${error!''}" />
				</form>
			</div>
		</div>
		
		
	</div>
	
<script>
window.onload = function(){
	$("#Load").hide();
	$("#Main").show();
	//错误
	var error = $('#error').val();
	if (error != null && error.length > 0) {
		ygdg.dialog.alert(error);
	}
	
	print = document.getElementById("eprint");
	setParameters();
}
function showExpressType(obj){
	var val = obj.value;
	if(val == 0){
		$("#conId1").show();
		$("#conId2").hide();
	} else {
		$("#conId1").hide();
		$("#conId2").show();
	}
}
</script>
</body>
</html>
