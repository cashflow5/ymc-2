<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购-商家中心-赔付管理列表</title>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css" />
</head>

<body>
    <script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
        <div id="newmain" class="main_bd fr">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 售后 &gt; 售后单查询</p>
                    <div class="tab_panel  relative">
                        <div class="tab_content"> 
                            <!--搜索start-->
                            <div class="search_box pt0">
                                <form action="backOrderDetailList.sc" id="queryForm" name="queryForm" method="post">
                                	<input type="hidden" id="handle" name="handle" value="${handle!''}">
                                	<input type="hidden" id="mainId" name="mainId" value="${(backOrderVo.id)!''}">
                                	<input type="hidden" id="backCode" name="backCode" value="${(backOrderVo.backCode)!''}">
                                	<input type="hidden" id="planBackTotalCount" name="planBackTotalCount" value="${(backOrderVo.planBackTotalCount)!'0'}">
                                	<input type="hidden" id="alreadyBackTotalCount" name="alreadyBackTotalCount" value="${(backOrderVo.alreadyBackTotalCount)!'0'}">
                                    <p><span class="ml32">退回单号：${(backOrderVo.backCode)!''}</span><span class="ml40">退货人：${(backOrderVo.backOperator)!''}</span>
                                        <span class="ml40">退货时间：${(backOrderVo.backTime)!''}</span>
                                        <span class="ml40">计划退回总数：${(backOrderVo.planBackTotalCount)!'0'}</span>
                                        <span class="ml40">已退回总数：${(backOrderVo.alreadyBackTotalCount)!'0'}</span>
                                        <span class="ml40">收货状态：
                                        			<#if (backOrderVo.receiveStatus)?? && (backOrderVo.receiveStatus == 0)>
		                                        		待确认收货
		                                        	<#elseif (backOrderVo.receiveStatus)?? && (backOrderVo.receiveStatus == 1)>
		                                        		已确认收货
		                                        	<#elseif (backOrderVo.receiveStatus)?? && (backOrderVo.receiveStatus == 2)>
		                                        		部分确认收货	
		                                        	</#if>
		                                </span></p>
                                    <p>
                                        <span>
                                            <label>订单号：</label>
                                            <input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${(backOrderDetailVo.orderSubNo)!''}" />
                                        </span>
                                        <span>
                                            <label style="width:110px;">货品条码：</label>
                                            <input type="text" name="insideCode" id="insideCode" class="inputtxt" value="${(backOrderDetailVo.insideCode)!''}" />
                                        </span>
                                        <span>
                                            <label style="width:110px;">商品类型：</label>
                                            <select style="width:125px;" name="type">
                                                <option value="" <#if (backOrderDetailVo.type)?? >  <#else> selected="selected" </#if> >请选择</option>
                                                <option value="SDARD_GOODS" <#if (backOrderDetailVo.type)?? && (backOrderDetailVo.type == "SDARD_GOODS")>  selected="selected" </#if>>正品</option>
                                                <option value="UNSDARD_GOODS" <#if (backOrderDetailVo.type)?? && (backOrderDetailVo.type == "UNSDARD_GOODS") > selected="selected" </#if>>残品</option>
                                            </select>
                                        </span>
                                        <span>
                                            <a class="button" id="searchBtn"><span>搜索</span></a>
                                        </span>
                                    </p>
                                </form>
                            </div>
                            <!--搜索end-->

                            <!-- 单据打印导航tab模版引入 -->
                            <ul class="tab">
                                <li onclick="" class="curr"  ><span>计划退回数量</span></li>
                            </ul>
                            <!--列表start-->
                            <#if handle?? && handle == "receive">
	                            <div class="gray-box">
	                                <label for="all_checkbox" class="cblue fl mr5" style="_margin-top:5px;">
	                                	<input type="checkbox" autocomplete="off" id="all_checkbox"  /> 全选
	                                </label>
	                                <a href="javascript:void(0);" class="btn-blue-2 fl mt3" onclick="batchReceiveBackOrder();">批量确认收货</a>
	                                <a href="javascript:void(0);" class="btn-blue-2 fl mt3" style="margin-left:10px;" onclick="allReceiveBackOrder();">全部确认收货</a>
	                            </div>
                            </#if>
                            <table class="list_table">
                                <thead>
                                    <tr>
                                    	<#if handle?? && handle == "receive">
                                       		 <th></th>
                                        </#if>
                                        <th>商品名称</th>
                                        <th>规格</th>
                                        <th>订单号</th>
                                        <th>货品条码</th>
                                        <th>款色编码</th>
                                        <th>商品类型</th>
                                        <th>计划退回数量</th>
                                        <th>实际退回数量</th>
                                        <#if handle?? && handle == "receive">
                                        	<th>操作</th>
                                        </#if>
                                    </tr>
                                </thead>
                                <tbody>             
                                    <#if (pageFinder.data)?? && (pageFinder.data)?size &gt; 0 >
                                		<#list pageFinder.data as detailVo>
                                			<tr>
                                				<#if handle?? && handle == "receive">
			                                        <td>
			                                        	<#if (detailVo.planBackCount)?? && (detailVo.alreadyBackCount)?? && (detailVo.planBackCount &gt; detailVo.alreadyBackCount ) >
			                                        		<input type="checkbox" autocomplete="off" id="${(detailVo.id)!''}" name="detailCheckBox" value="${(detailVo.id)!''}" />
			                                 			<#elseif (detailVo.planBackCount)?? && (detailVo.alreadyBackCount)?? && (detailVo.planBackCount &lt;= detailVo.alreadyBackCount )>       	
			                                 				<input type="checkbox" autocomplete="off" disabled="disabled" id="${(detailVo.id)!''}" name="detailCheckBox" value="${(detailVo.id)!''}" />
			                                        	</#if>		                                  
			                                        </td>		                                        
		                                        </#if>
		                                        <td class="tl">
		                                        	<img src="${(detailVo.commodityPic)!''}"  alt="" class="fl mr5"  />
		                                        	<span>${(detailVo.commodityName)!''}</span>
		                                        </td>
		                                        <td>${(detailVo.specKey)!''}</td>
		                                        <td>${(detailVo.orderSubNo)!''}</td>
		                                        <td>${(detailVo.insideCode)!''}</td>
		                                        <td>${(detailVo.supplierCode)!''}</td>
		                                        <td> <#if (detailVo.type)?? && (detailVo.type == "SDARD_GOODS")> 正品<#elseif (detailVo.type)?? && (detailVo.type == "UNSDARD_GOODS") > 残品 </#if> </td>
		                                        <td>${(detailVo.planBackCount)!'0'}</td>
		                                        <td>${(detailVo.alreadyBackCount)!'0'}</td>
		                                        <#if handle?? && handle == "receive">
			                                        <td>
			                                        	<#if (detailVo.planBackCount)?? && (detailVo.alreadyBackCount)?? && (detailVo.planBackCount &gt; detailVo.alreadyBackCount ) >
			                                        		<a class="confirm-receipt" name="receiveBtn" href="javascript:void(0);" onclick="receiveBackOrder('${(detailVo.id)!''}',${(detailVo.planBackCount)!'0'},${(detailVo.alreadyBackCount)!'0'});">确认收货</a>
			                                 			<#elseif (detailVo.planBackCount)?? && (detailVo.alreadyBackCount)?? && (detailVo.planBackCount &lt;= detailVo.alreadyBackCount )>       	
			                                 				<a class="confirm-receipt Gray" href="javascript:void(0);">确认收货</a>
			                                        	</#if>
			                                        </td>
		                                        </#if>
		                                    </tr>
                                		</#list>
                                	<#else>
                                		 <tr>
	                                        <td <#if handle?? && handle == "receive"> colspan = "10" <#else> colspan = "8" </#if> >暂无数据！</td>
	                                    </tr>	
                                	</#if>
                                	
                                  
                                </tbody>
                            </table>
                            <!--列表end--> 
                          <!--分页start-->
							<#if (pageFinder.data)??>
								<div class="page_box">
									<div class="dobox">
									</div>
										<#import "/manage/widget/common.ftl" as page>
										<@page.queryForm formId="queryForm"/>
								</div>
							</#if>
							<!--分页end-->
                        </div>
                    </div>
                </div>            
            </div>
        </div>
    <script type="text/javascript">
    
    //搜索
	$("#searchBtn").click(function(){
		$("#queryForm").submit();
		
	});
	
	//全选
	$("#all_checkbox").click(function(){
		if($(this).attr("checked") == "checked"){
			$("[name='detailCheckBox']:not(:disabled)").attr("checked","checked");
		}else{
			$("[name='detailCheckBox']:not(:disabled)").removeAttr("checked");
		}
	});
	
	//全部确认收货
	function allReceiveBackOrder(){
		var planBackTotalCount = $("#planBackTotalCount").val();
		var alreadyBackTotalCount = $("#alreadyBackTotalCount").val();		
		if(alreadyBackTotalCount >= planBackTotalCount){
			ygdg.dialog.alert('所有商品都已确认收货，操作终止！');
			return;
		}
		ygdg.dialog({
                title:"批量确认收货",
                content: '确认计划退回数量和当前退回数量一致，需要全部确认收获吗？',
                max:false,
                min:false,
                lock:true,
                icon: 'warning',
                ok: function() {
                	 allHandleReceive();
                },
                cancel:function(){

                }
            });
	}
	
	 //全部异步提交确认收货
    function allHandleReceive(){   	
    	var planBackTotalCount = $("#planBackTotalCount").val();
		var alreadyBackTotalCount = $("#alreadyBackTotalCount").val();
		//总未退回数量=总计划退回数量-总已退回数量
		var noBackTotalCount = planBackTotalCount - alreadyBackTotalCount;  	
    	var requestData= {
	    	"mainId":$("#mainId").val(),
	    	"backCode":$("#backCode").val(),
	    	"noBackTotalCount":noBackTotalCount
    	};
    	 var url = "${BasePath}/backOrder/allHandleReceive.sc";
		 ajaxRequestHandle(url,requestData);
    }
    
    //批量确认收货
    function batchReceiveBackOrder(){
    	 var len = $("[name='detailCheckBox']:checked:not(:disabled)").length;
    	 if(len <= 0 ){
    	 	ygdg.dialog.alert('请选择商品！');
    	 	return ;
    	 }
    	 ygdg.dialog({
                title:"批量确认收货",
                content: '确认计划退回数量和当前退回数量一致吗？',
                max:false,
                min:false,
                lock:true,
                icon: 'warning',
                ok: function() {
                	 batchHandleReceive();
                },
                cancel:function(){

                }
            });
    }
    
    //批量异步提交确认收货
    function batchHandleReceive(){
    	var detailIds="";
    	$("[name='detailCheckBox']:checked:not(:disabled)").each(function(index,obj){
    		detailIds = detailIds + $(obj).val()+",";
    	});
    	var planBackTotalCount = $("#planBackTotalCount").val();
		var alreadyBackTotalCount = $("#alreadyBackTotalCount").val();
		//总未退回数量=总计划退回数量-总已退回数量
		var noBackTotalCount = planBackTotalCount - alreadyBackTotalCount;  	
    	var requestData= {
	    	"detailIds":detailIds,
	    	"mainId":$("#mainId").val(),
	    	"backCode":$("#backCode").val(),
	    	"noBackTotalCount":noBackTotalCount
    	};
    	 var url = "${BasePath}/backOrder/batchHandleReceive.sc";
		 ajaxRequestHandle(url,requestData);
    }
    
    //单个确认收货
    function receiveBackOrder(detailId,planBackCount,alreayBackCount) {
    	//未退回数量=计划退回数量-已退回数量
    	var noBackCount = planBackCount - alreayBackCount;
		var url = "${BasePath}/backOrder/receiveBackOrder.sc?noBackCount="+noBackCount;
		ygdgDialog.open(url, {
			id:"backOrderDialog",
			width: 350,
			height: 150,
			title: '确认退回信息',
			close: function(){
				refreshpage();
			},
			ok: function() {
				//获取弹出层子页面input元素
				var input = document.getElementsByName('OpenbackOrderDialog')[0].contentWindow.document.getElementById("receiveCount");
				var receiveCount = input.value;
				//校验输入的确认收货数量
				if(checkNum(receiveCount) && receiveCount <= noBackCount ){					
					handleReceive(detailId,receiveCount);
				}else{
					ygdg.dialog.alert('请输入不大于未退回数的整数!',3000);
				}
				
            },
            cancel:function(){

            }
		});
	}
	
	//校验是否为大于零的整数
	function checkNum(num){
		var reg = /^[1-9]\d*$/;
		return (reg.test(num));
	}
	
	//异步提交单个确认收货
	function handleReceive(detailId,receiveCount){
		var planBackTotalCount = $("#planBackTotalCount").val();
		var alreadyBackTotalCount = $("#alreadyBackTotalCount").val();
		//总未退回数量=总计划退回数量-总已退回数量
		var noBackTotalCount = planBackTotalCount - alreadyBackTotalCount;
		var requestData = {
			 "detailId":detailId,
			 "receiveCount":receiveCount,
			 "mainId":$("#mainId").val(),
			 "backCode":$("#backCode").val(),
			 "noBackTotalCount":noBackTotalCount
		 };
		 var url = "${BasePath}/backOrder/handleReceive.sc";
		 ajaxRequestHandle(url,requestData);
	}
	
	//异步请求
	function ajaxRequestHandle(url,requestData){
		var dg=null;
		$.ajax({
			url: url,
			type: 'post',
			data: requestData,
			dataType: 'json',
			async: false,
			beforeSend: function(data){
				dg=ygdg.dialog({
					id: 'submitDialog',
					title: '提示', 
					content: '请稍候，正在处理中...', 
					lock: true, 
					closable: false
				});
			},
			success: function(data){								
				if(data.msg=="sucess"){
					ygdg.dialog.alert('操作成功!');
				}else{
					ygdg.dialog.alert('操作失败!');
				}
				dg.close();
				refreshpage();
			},
			error: function(e){
				dg.close();
				ygdg.dialog.alert('操作失败:' +e.responseText);
			}
		});
	
	}
	
	
    </script>
</body>

</html>
