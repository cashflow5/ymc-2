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
<title>优购商城--商家后台</title>
</head>
<style type="text/css">
	#select1,#select2{width:158px; margin:-4px;padding:5px;}
	.sel-div{border:1px solid #ccc;width:150px; overflow:hidden;}
	.a-up,.a-down{display: block;border: 1px solid #ccc;text-decoration:none;width:150px; background:#F2F2F2; text-align:center; height:25px; line-height:25px;cursor:pointer; letter-spacing:2px; zoom:1;}
	.a-up{border-bottom:none;}
	.a-down{border-top:none;}
	.a-right,.a-left{display:block; border:1px solid #ccc; width:100px; height:25px; line-height:25px; background:#F2F2F2;margin:15px; cursor:pointer;}
	.a-left{margin-top:20px;}
</style>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加API参数</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
               <form action="${BasePath}/yitiansystem/merchants/businessorder/assign_Merchants_User_Authority.sc" name="querFrom" id="querFrom" method="post" style="padding:0px;margin:0px;">
		      		
		      		<input type="hidden" name="content" id="content">
		      		<div style="padding-left:20px;padding-right:20px;">
		      		<#if apiList??>
				        <table cellpadding="0" cellspacing="0" style="float:center;">
				            <tbody>
				            	<tr>
				            		<td align="center" style="height:20px;">可选API</td>
				            		<td></td>
				            		<td align="center"><span style="color:red;">*</span>已选API</td>
				            	</tr>
				                <tr>
					                <td valign="top">
										<a id="right_up" class="a-up">上移</a>
					               		<div class="sel-div">
					                		<select multiple id="select2" size="30" name="select2">
					                			<#if apiList??>
					                				<#list apiList as item>
					                					<option value="${item['id']!''},${item['apiCode']!''},${item['apiName']!''},${item['apiCategory'].categoryName!''}">${item.apiName!''}</option>
					                				</#list>
					                			</#if>
					                		</select>
					                	</div>
					                	<a id="right_down" class="a-down">下移</a>
					                </td>
					                <td align="center" valign="middle">
					                	<a id="remove" class="a-left">增加API&gt;&gt;</a>
					                	<a id="add1" class="a-right">&lt;&lt; 删除API</a>
					                </td>
					                <td>
					                	<a  id="left_up" class="a-up">上移</a>
					                	<div class="sel-div">
					               			<select  name="allowRoleValues" size="30" multiple id="select1" >
					                			<#if authorityMap??>
					                				<#list authorityMap as item>
					                					<option value="${item['id']!''},${item['apiCode']!''},${item['apiName']!''},${item['apiCategory'].categoryName!''}" selected>${item['apiName']!''}</option>
					                				</#list>
					                			</#if>
					                		</select>
					               	 	</div>
					                	<a  id="left_down" class="a-down">下移</a>
					                </td>
				                </tr>

				                <tr><td style="height:35px;" colspan="2" align="left"><span style="color:red;">*</span>频率上限：
                                   <input name="frequency" id="frequency" value="<#if frequency??>${frequency!''}</#if>">次/
                                   <select  name="frequencyUnit" id="frequencyUnit" >
                                   <option value="1" selected>小时</option>
                                   <option value="2">分钟</option>
                                   <option value="3">秒</option>
                                   </select>
                                   </td>
                                   <td align="left"><span style="color:red;">*</span>日调用次数上限：
                                   <input name="callNum" id="callNum" value="<#if callNum??>${callNum!''}</#if>">次/天</td>
                                </tr>
                                <tr>
                                   <td style="height:35px;" colspan="2" ><span style="color:red;">*</span>是否启用频率控制：
                                   <input type="radio" name="isFrequency" value="0" checked>不开启
                                   <input type="radio" name="isFrequency" value="1">开启
                                   <td align="center"><span style="color:red;">*</span>是否启用日调用次数控制：
                                   <input type="radio" name="isCallNum" value="0" checked>不开启
                                   <input type="radio" name="isCallNum" value="1">开启
                                </tr>
				                <tr>
				                	<td style="height:35px;" colspan="3" align="center">
				                		<input  type="button" onclick="addRole()" class="btn-add-normal" value="保存" />   
				                		<input type="button" onclick="closewindow();" class="btn-add-normal" value="取消" />
				                	</td>
				                </tr>
				            </tbody>
				        </table>
				        <#else>
				          <span style="color:red;">API列表为空,请确认！</span>
				       </#if>
			        </div>
		        </form>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//保存
 function addRole(){  
   var ids=document.getElementById('select1');  
   var result="";
    for (var i=0;i<ids.length;i++) {  
     result+=ids.options[i].value+";";  
    }
    if(result==""){
       alert("请至少选择一个API！");
       return;
    }
    if($('#frequency').val()==""){
       alert("请设定频率上限！");
       return;
    }
    if($('#callNum').val()==""){
       alert("请设定日调用次数上限！");
       return;
    }
    var monitor=$('#frequency').val()+","+$('#frequencyUnit').val()+","+$('#callNum').val()+","+$('input[name=isFrequency]:checked').val()+","+$('input[name=isCallNum]:checked').val();
    parent.window.returnValue=result+monitor;
    window.close();
}

 //角色分配相关JS
$(function(){
       //移到左边
       $('#add1').click(function() {
            $('#select1 option:selecsted').remove().appendTo('#select2');
       });
       //移到右边
       $('#remove').click(function() {
       console.log($('#select2 option:selected'));
            $('#select2 option:selected').remove().appendTo('#select1');
       });
       //双击选项
       $('#select1').dblclick(function(){
           $("option:selected",this).remove().appendTo('#select2');
       });
       //双击选项
       $('#select2').dblclick(function(){
           $("option:selected",this).remove().appendTo('#select1');
       });
       //左边向上按钮
       $('#left_up').click(function(){
          var index = $('#select1 option').index($('#select1 option:selected:first'));
        var $recent = $('#select1 option:eq('+(index-1)+')');
        if(index>0){
             var $options = $('#select1 option:selected').remove();
            setTimeout(function(){
                $recent.before($options );
            },10);
        }
       });
       //左边向下按钮
       $('#left_down').click(function(){
          var index = $('#select1 option').index($('#select1 option:selected:last'));
        var len = $('#select1 option').length-1;
        var $recent = $('#select1 option:eq('+(index+1)+')');
        if(index<len ){
             var $options = $('#select1 option:selected').remove();
            setTimeout(function(){
                $recent.after( $options );
            },10);
           }
       });
       //右边向上按钮
       $('#right_up').click(function(){
          var index = $('#select2 option').index($('#select2 option:selected:first'));
        var $recent = $('#select2 option:eq('+(index-1)+')');
        if(index>0){
             var $options = $('#select2 option:selected').remove();
            setTimeout(function(){
                $recent.before($options );
            },10);
        }
       });
       //右边向下按钮
       $('#right_down').click(function(){
          var index = $('#select2 option').index($('#select2 option:selected:last'));
        var len = $('#select2 option').length-1;
        var $recent = $('#select2 option:eq('+(index+1)+')');
        if(index<len ){
             var $options = $('#select2 option:selected').remove();
            setTimeout(function(){
                $recent.after( $options );
            },10);
           }
       });
});

</script>