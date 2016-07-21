<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript">

  
//来源绑定店铺
function sourceClickEventBind(sourceObject,sourceName){
	var sourceSellerArr = document.getElementsByName("seller_name_"+sourceName);
	for(var i = 0 ; i<sourceSellerArr.length;i++){
		sourceSellerArr[i].checked=sourceObject.checked;
		var sourceSellerSubArr = document.getElementsByName("sellerSub_name_"+sourceSellerArr[i].value);
		for(var j = 0; j<sourceSellerSubArr.length;j++){
		    sourceSellerSubArr[j].checked=sourceObject.checked;
		}
	}
}

//一级店铺绑定来源和下属二级店铺
function sellerClickEventBind(sellerObj,sourceName,sellerName){
	if(sellerObj.checked){
		$("#source_"+sourceName).attr("checked",true);
	}else{		
		var cancelChecked = false;
        var sourceSellerArr = document.getElementsByName("seller_name_"+sourceName);
		for(var i = 0 ; i < sourceSellerArr.length ;i++){
			if(sourceSellerArr[i].checked){
				cancelChecked = true;
			}
		}
		
		if(!cancelChecked){
			$("#source_"+sourceName).attr("checked",false);
		}
		}
		var sourceSellerSubArr = document.getElementsByName("sellerSub_name_"+sellerName);
		for(var j = 0 ; j<sourceSellerSubArr.length;j++){
		    sourceSellerSubArr[j].checked=sellerObj.checked;
	}
	
}

//二级店铺绑定一级店铺和来源
function sellerSubClickEventBind(sellerObj,sourceName,sellerName){
    if(sellerObj.checked){
        $("#seller_"+sellerName).attr("checked",true);
		$("#source_"+sourceName).attr("checked",true);
	}else{		
		var cancelChecked = false;
        var sourceSellerSubArr = document.getElementsByName("sellerSub_name_"+sellerName);
		for(var i = 0 ; i < sourceSellerSubArr.length ;i++){
			if(sourceSellerSubArr[i].checked){
				cancelChecked = true;
			}
		}
		
		if(!cancelChecked){
			$("#seller_"+sellerName).attr("checked",false);
			var subCancelChecked = false;
			var sourceSellerArr = document.getElementsByName("seller_name_"+sourceName);
			for(var i = 0 ; i < sourceSellerArr.length ;i++){
			if(sourceSellerArr[i].checked){
				subCancelChecked = true;
			    }
		    }
		    if(!subCancelChecked){
			$("#source_"+sourceName).attr("checked",false);
		    }
		}		
	}
}

//分类绑定品牌
function catClickEventBind(catObject,catName){
	var catBrandArr = document.getElementsByName("brand_catName_"+catName);
	for(var i = 0 ; i < catBrandArr.length ;i++){
		catBrandArr[i].checked = catObject.checked;
	}
}

//品牌绑定分类
function brandClickEventBind(barndObj,catName){
	if(barndObj.checked){
		$("#cat_"+catName).attr("checked",true);
	}else{
		var cancelChecked = false;
		var catBrandArr = document.getElementsByName("brand_catName_"+catName);
		for(var i = 0 ; i < catBrandArr.length ;i++){
			if(catBrandArr[i].checked){
				cancelChecked = true;
			}
		}

		if(!cancelChecked){
			$("#cat_"+catName).attr("checked",false);
		}
	}
}

function formSubmit(){
	var categorysArr = document.getElementsByName("categorys");
	for(var j = 0 ; j < categorysArr.length ;j++){
			if(categorysArr[j].checked){
				var data = '';
		        var catBrandArr = document.getElementsByName("brand_catName_"+categorysArr[j].value);
               
		        for(var i = 0 ; i < catBrandArr.length ;i++){
			       if(catBrandArr[i].checked){
				    data += catBrandArr[i].value+',';
			       }
		        }
		      $("#cat_brand_val_"+categorysArr[j].value).val(data);
			}
    }
	
	var sourcesArr = document.getElementsByName("sources");
	for(var k = 0 ; k < sourcesArr.length ;k++){
		if(sourcesArr[k].checked){
			var sellerdata = '';
			var sourceSellerArr = document.getElementsByName("seller_name_"+sourcesArr[k].value);
		        for(var l = 0 ; l < sourceSellerArr.length ;l++){
			       if(sourceSellerArr[l].checked){
			       var sourceSellerSubArr = document.getElementsByName("sellerSub_name_"+sourceSellerArr[l].value);
			       for(var j = 0; j < sourceSellerSubArr.length ;j++){
			           if(sourceSellerSubArr[j].checked){
			               sellerdata += sourceSellerSubArr[j].value+',';
			           }
			       }
				    
			       }
		        }
		      $("#source_sellers_val_"+sourcesArr[k].value).val(sellerdata);
		}
    }
	$("#form1").submit();  
	
	refreshpage("to_groupDate.sc");
	closewindow();
		
}


</script>
</head>

<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content"> </div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>权限设置</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
			<form name="form1" id="form1" action="addPermission.sc" method="post">
				<h3>小组信息</h3>
				<tr>
					<th width="100">组名:</th>
					<th><input type="text" name="groupName" id="groupName" maxLength="200" /></th>
				</tr>
				<h3>订单操作权限</h3>
				<table width="100%" class="list_table2">
					<tbody>

						<tr>
							<th width="8%">一级订单来源</th>
							<th width="20%">二级订单来源</th>
							<th width="72%">三级订单来源</th>
						</tr>
						
						<#list sourceMap?keys as sourceKey>
						
						<#assign sellerMap=sourceMap[sourceKey] />
						
						<tr>
							<td>
								<label>
									<input type="checkbox"  id="source_${sourceKey?split("|")[1]}" name="sources" onclick="sourceClickEventBind(this,'${sourceKey?split("|")[1]}');" value="${sourceKey?split("|")[1]!""}"/>
									${sourceKey?split("|")[0]}
									<input type="hidden" name="sellers" value="" id="source_sellers_val_${sourceKey?split("|")[1]}">
								</label>
							</td>
							<td colspan=2 style="padding:0px;border-style:none;">
								<table width="100%" class="list_table2" style="margin:0px;" border="0" frame="void">
									<#list sellerMap?keys as sellerKey>
										<tr style="margin:0px;">
											<td width="21.74%" style="margin:0px;">
												<label>
													<input type="checkbox"  id="seller_${sellerKey?split("|")[1]}" name="seller_name_${sourceKey?split("|")[1]}" onclick="sellerClickEventBind(this,'${sourceKey?split("|")[1]}','${sellerKey?split("|")[1]}')" value="${sellerKey?split("|")[1]!""}"/>
													${sellerKey?split("|")[0]}
												</label>
											</td>
											<td  style="margin:0px;">
												<#assign sellerSubList=sellerMap[sellerKey] />
												<#list sellerSubList as sellerSub>
													<span><label>
													<input type="checkbox" name="sellerSub_name_${sellerKey?split("|")[1]}" onclick="sellerSubClickEventBind(this,'${sourceKey?split("|")[1]}','${sellerKey?split("|")[1]}')" value="${sellerSub.no!""}"/>
													${sellerSub.name}
													</label>
													</span>
											    </#list>
											</td>
										</tr>
									</#list>
								</table>
							</td>
						</tr>				
                        
						</#list>
						
						
					</tbody>
				</table>
				<div class="blank10"></div>
				<h3>商品操作权限</h3>
				<table width="100%" id="commodity" class="list_table2">
					<tbody>
					
						<tr>
							<th width="100">商品分类</th>
							<th>商品品牌</th>
						</tr>







						<#list catAndBrandMap?keys as testKey> 
						<tr>
							<td>
								<label>
									<input type="checkbox"  id="cat_${testKey!""}" name="categorys" onclick="catClickEventBind(this,'${testKey!""}');" value="${testKey!""}"/>
									${testKey!""}
									<input type="hidden" name="brands" value="" id="cat_brand_val_${testKey!""}">
								</label>
							</td>
							<#assign item=catAndBrandMap[testKey] />
							<td>
								<#list item as brand >
									<label>
									<input type="checkbox"  name="brand_catName_${testKey!""}" onclick="brandClickEventBind(this,'${testKey!""}')" value="${brand.brandNo!""}"/>
									${brand.brandName}
									</label>
								</#list>
								
							</td>
						</tr>				

						 
						</#list>

						
						<tr>
							<td valign="top">&nbsp;</td>
							<td valign="bottom"><span  id="remarktip"></span></td>
						</tr>
					</tbody>
				</table>
				<div class="blank10"></div>
				<h3>处理小组说明</h3>
				<p>
					<textarea id="remark" name="remark" cols="60" rows="4"></textarea>
				</p>
				<p >
					<input type="button" value="保存" class="btn-save" onclick="formSubmit()"/>
					<input type="button" value="返回" class="btn-back" onclick="window.closewindow();" />
				</p>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
</body>
</html>
