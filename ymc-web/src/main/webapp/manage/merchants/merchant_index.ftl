<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/index.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/carousel.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/security/security.js?${style_v}"></script>
<body id="indexBody">
		<div class="main_bd fr">
    	<div class="curr_position">您当前所在的位置 &gt; <a href="javascript:void(0)">商家中心</a> &gt; 首页</div>
        <div class="clearfix">
        	<div class="index_main_bdc fl">
            	<div class="bsnss_inf clearfix">
                	<div class="hd fl"><img src="${BasePath}/yougou/images/business.jpg" width="80" height="80" /></div>
                    <div class="bd fr">
                    	<ul class="inf_lst clearfix">
                    	    <li style="width:600px"><span>商家名称：</span><#if merchantUsers??>${merchantUsers.supplier!''}</#if></li>
                        	<li><span>登录账号：</span>
                        	<#if merchantUsers??>${merchantUsers.login_name!''}
                        	<#--(<#if emailstatus==-1><a href="javascript:setEmail(0);">邮箱未设置</a>
                        	<#elseif emailstatus==0><span style="color:#EEC900;"><a href="javascript:setEmail(1);">邮箱未激活</a></span>
                        	<#else><span style="color:#228B22;"><a href="javascript:setEmail(2);">邮箱已激活</a></span></#if>)-->
                        	(<#if mobilePhone??><span style="color:#228B22;"><a href="${BasePath }/merchants/security/accountSecurity.sc">手机已绑定</a></span><#else><a href="${BasePath }/merchants/security/accountSecurity.sc">手机未绑定</a></#if>)
                        	</#if>
                        	</li>
                            <li><span>商家编码：</span><label id="merchantCode">${merchantUsers.supplier_code!''}</label></li>
                            <li><span>配送方式：</span><label id="distributionType"></label></li>
                            <li><span>商家状态：</span><#if merchantUsers.status??&&merchantUsers.status==1>启用<#elseif merchantUsers.status??&&merchantUsers.status==0>锁定</#if></li>
                            <li><span>合作模式：</span><label id="hzmodel"></label></li>
                            <li><span>上次登录时间：</span><#if historyLoginTime??>${historyLoginTime!''}</#if><a class="button" id="selectLoginLog" style="margin-left:5px; vertical-align:top;" onclick="selectLoginLog();">
								<span>查看</span>
							</a>
							</li>
                        </ul>
                    </div>
                </div>
                <div class="common_box1 remind mt12">
                	<div class="box1_hd"><h2>店铺提醒</h2></div>
                    <div class="box1_bd">
                    	<div class="deal_now">
                        	<h3>你需要立即处理：</h3>
                            <div class="common_dl1 clearfix">
                            	<h4 class="fl">订单提醒：</h4>
                                <ol class="fl clearfix">
                                    <li><a href="<#if order_authrity_url??>${BasePath}/${order_authrity_url!''}<#else>#</#if>">待发货订单(<em id="waitSends" class="cred">0</em>)</a></li>
                                    <li><a href="<#if order_outstock_url??>${BasePath}/${order_outstock_url!''}<#else>#</#if>">缺货订单(<em id="stockOuts" class="cred">0</em>)</a></li>
                                    <li><a href="<#if a_timeOutOrder??>${BasePath}/order/queryPunishOrderList.sc?punishType=1<#else>#</#if>">超时未发货订单(<em id="timeOutOrders" class="cred">0</em>)</a></li>
                                    <li><a href="<#if a_compensate_count??>${BasePath}/afterSale/compensate_handling_list.sc<#else>#</#if>">赔付工单(<em id="compensateCount" class="cred">0</em>)</a></li>
                                    <li>
                                    	<a href="<#if a_gongdan??>${BasePath}/dialoglist/query.sc?traceStatus=3<#else>#</#if>">待商家处理工单(<em id="dialogCount" class="cred">0</em>)</a>
                                    </li>
                                    <li>
                                    	<a href="<#if a_orderTipsStockUnder??>${BasePath}/wms/supplyStockInput/queryOrderTipsStockUnder.sc<#else>#</#if>">库存少于5(<em id="stockTips" class="cred">0</em>)</a>
                                    </li>
                                    <li id="asmTipLi" title="顾客已申请售后退换货，且商家未质检的售后申请，拒收订单不统计在内。">
										<a href="<#if a_afterSale??>${BasePath}/afterSale/queryAfterSale.sc<#else>#</#if>">待处理售后申请(<em id="asmTips" class="cred">0</em>)</a>
                                    </li>
                                    <li>
										<a href="<#if a_query??>${BasePath}/invoice/query.sc<#else>#</#if>">需开具发票(<em id="invoiceCount" class="cred">0</em>)</a>
                                    </li>
                                </ol>
                            </div>
                        </div>
                        <div class="common_dl1 pro_manage clearfix mt12">
                        	<h4 class="fl"><b>商品管理：</b></h4>
                        	<div class="deal_other clearfix">
	                            <ol class="fl clearfix">
	                            	<li><a href="<#if a_onSaleCommodity??>${BasePath}/commodity/goQueryOnSaleCommodity.sc<#else>#</#if>">在售商品(<em id="onSaleCount" class="cred">0</em>)</a></li>
	                                <li><a href="<#if a_waitSaleCommodity??>${BasePath}/commodity/goWaitSaleCommodity.sc?isAudit=2&status=1<#else>#</#if>">审核通过(<em id="approveCount" class="cred">0</em>)</a></li>
	                                <li><a href="<#if a_waitSaleCommodity??>${BasePath}/commodity/goWaitSaleCommodity.sc?auditStatus=11<#else>#</#if>">待提交审核(<em id="newCount" class="cred">0</em>)</a></li>
	                                <li><a href="<#if a_waitSaleCommodity??>${BasePath}/commodity/goWaitSaleCommodity.sc?auditStatus=12<#else>#</#if>">审核中(<em id="waitAuditCount" class="cred">0</em>)</a></li>
	                                <li><a href="<#if a_waitSaleCommodity??>${BasePath}/commodity/goWaitSaleCommodity.sc?auditStatus=13<#else>#</#if>">审核拒绝(<em id="refusedCount" class="cred">0</em>)</a></li>
	                            </ol>
                            </div>
                        </div> 
                    </div>
                </div>
                <div class="common_box1 mt12">
                	<div class="box1_hd"><h2>最近一周销量趋势</h2></div>
                    <div class="box1_bd">
                    	<div class="sales_trend" id="salesTrend">
                    		<input type="hidden" id="analyzeSellDates" value="${analyzeSellDates}" />
                    		<div id="container"></div>
                        	<!--img src="http://10.0.30.51/ui/business/demo/tbl.jpg" width="568" height="343" alt="" /-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="main_aside fr">
            	<div class="common_box1 tip">
                	<div class="box1_hd">
                    	<h2 class="fl">最新公告</h2>
                    	<a class="fr" href="${BasePath}/notice/queryAll.sc">更多 &gt;&gt;</a>
                    </div>
                    <div class="box1_bd">
                    	<ul class="common_lst1 clearfix" id="noticelist">
                        	<li></li>
                        </ul>
                    </div>
                </div>
                <div class="common_box1 help mt12">
                	<div class="box1_hd"><h2>帮助中心</h2></div>
                    <div class="box1_bd">
                    	<dl class="help_itm pro_itm">
                        	<dt>商品</dt>
                            <dd>
                            	<ul class="clearfix">
                            	   <#if helpMenus_comm??>
		                            	<#list helpMenus_comm as menu>
											<li>
												<a href="${help_url}/help/content/${menu.id}.shtml" target="_blank">${menu.menuName}</a>
											</li>
										</#list>
                            	   <#else>
	                            	    <li><a href="${help_url}/help/help_index.shtml">单品上传</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">批量上传</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">商品管理</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">图片规范</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">库存管理</a></li>
                            	   </#if>
                                </ul>
                            </dd>
                        </dl>
                        <dl class="help_itm">
                        	<dt>订单</dt>
                            <dd>
                            	<ul class="clearfix">
                            	   <#if helpMenus_order??>
		                            	<#list helpMenus_order as menu>
											<li>
												<a href="${help_url}/help/content/${menu.id}.shtml" target="_blank">${menu.menuName}</a>
											</li>
										</#list>
                            	   <#else>
	                                	<li><a href="${help_url}/help/help_index.shtml">校验发货</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">单据打印</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">售后质检</a></li>
                            	   </#if>
                                </ul>
                            </dd>
                        </dl>
                        <dl class="help_itm">
                        	<dt>接口</dt>
                            <dd>
                            	<ul class="clearfix">
                            	    <#if helpMenus_api??>
		                            	<#list helpMenus_api as menu>
											<li>
												<a href="${help_url}/help/content/${menu.id}.shtml" target="_blank">${menu.menuName}</a>
											</li>
										</#list>
                            	   <#else>
	                                    <li><a href="${help_url}/help/help_index.shtml">API接口介绍</a></li>
	                                    <li><a href="${help_url}/help/help_index.shtml">对接流程</a></li>
                            	   </#if>
                                </ul>
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="common_box1 contact mt12">
                	<div class="box1_hd"><h2>联系我们</h2></div>
                    <div class="box1_bd">
                    	<div class="phone_num clearfix">
                        	<div class="fl"><img src="${BasePath}/yougou/images/tel.jpg" width="60" height="44" alt="" /></div>
                            <div class="num_bd fl ml15">
                            	<p>系统支持：0755 - 26027917</p>
                                <p>产品建议：0755 - 21609613</p>
                            </div>
                        </div>
                        <div class="other_type mt15">
                        	<span>邮箱地址：</span>zs@yougou.com<br />
                            <span>QQ交流：</span>2027413897 3249553132
                        </div>
                        <p class="ca mt15">友情提示：请务必在邮件中提供如下信息，以便我们帮您解答：商家编码(或名称)、商品编号、订单编号、页面地址等。</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 
		

</body>
<script type="text/javascript">
	var chart;
	var basePath = "${BasePath}";
	var isSetMerchant = "${isSetMerchant!''}";
	$(document).ready(
			function() {
				var merchantCode = $('#merchantCode').html();
				if (merchantCode != null && merchantCode != '') {
					if(isSetMerchant=='' || isSetMerchant!='true'){
					$.get("${BasePath}/merchants/login/getSupplierDistributionSide.sc?merchantCode=${merchantUsers.supplier_code!''}",function(data){
	    				if (data.length > 1) {
	    					var _arrays = data.split(',');
	    					$('#hzmodel').html(_arrays[0]);
	    					$('#distributionType').html(_arrays[1]);
	    				}
					});
					// 获取订单提醒信息
					$("em.cred").html("<img width='12' style='margin-top:6px' src='${BasePath}/yougou/images/loading16.gif'>");
					$.getJSON("${BasePath}/order/doShopRemind.sc", function(data){
						if(data){
				    		$('#waitSends').html(data.waitSends);
				    		$('#stockOuts').html(data.stockOuts);
				    		$('#timeOutOrders').html(data.timeOutOrders);
						}
					});
					// 获取库存提醒信息
					$.getJSON("${BasePath}/wms/supplyStockInput/stockTips.sc", function(data){
						 if(data){
						 	$('#stockTips').html(data.stockTips);
						 }
					});

					// 获取商品管理数据
					$.get("${BasePath}/commodity/doShopCommodityTips.sc", function(data){
						if(data.length > 1){
				    		var tips = data.split(',');
				    		$('#onSaleCount').html(tips[0]);
				    		$('#approveCount').html(tips[1]);
				    		$('#newCount').html(tips[2]);
				    		$('#waitAuditCount').html(tips[3]);
				    		$('#refusedCount').html(tips[4]);
				    		$('#dialogCount').html(tips[5]);
						}
					});
					// 获取待处理售后申请
					$.getJSON("${BasePath}/afterSale/queryAfterSaleCount.sc", function(json){
						if(typeof(json['asmCount'])!="undefined"){
						    $('#asmTips').html(json.asmCount);
						}else{
							$('#asmTipLi').remove();
						}
					});
					//需开具发票
					$.getJSON("${BasePath}/invoice/queryInvoiceCount.sc", function(json){
						if(typeof(json['invoiceCount'])!="undefined"){
						    $('#invoiceCount').html(json.invoiceCount);
						}else{
							$('#invoiceCount').parent().parent().remove();
						}
					});
					
					// 获取待处理赔付工单数目
					$.getJSON("${BasePath}/afterSale/compensate_count.sc", function(json){
						if(typeof(json['compensate_count'])!="undefined"){
						    $('#compensateCount').html(json.compensate_count);
						}else{
							$('#compensateCount').remove();
						}
					});
					// 获取公告
					$.get("${BasePath}/notice/querytop5.sc", function(data){
					    var innerhtml='';
					    var color='style="color: #FF6600;"';
						if(data.length > 1){
						    for(var i=0;i<data.length;i++){
						     var url='${BasePath}/notice/showdetail.sc?id='+data[i].id;
						   	 innerhtml=innerhtml+'<li><span class="c9 fr">'+data[i].createTime+'</span><a '+(data[i].isRed=="1"?color:"")+' href="'+url+'" >【'+(data[i].noticeType=="1"?"新功能":"重要通知")+'】'+subTitle(data[i].title)+'</a></li>'
						    }
						}else{
						  innerhtml='<li>暂无公告</li>';
						}
						$('#noticelist').html(innerhtml);
					},'json');
					var  orderNums = [], orderMoneys = [];
					// 获取首页销售报表数据
					$.post("${BasePath}/order/analyzeSellInfo.sc", function(data){
					     if (data.result == "true") {
					     	var list = data.list;
							//chart.xAxis.categories = list.date;
					     	chart.series[0].setData(list.orderNums);
					     	chart.series[1].setData(list.orderMoneys);
					     	chart.hideLoading();
					     }
					   }, "json");
					}
				}
				//获取分析的时间段
				var d_riqi = $('#analyzeSellDates').val().split(',');
				/************************************************************/
				/** Highchart **/
				/************************************************************/

				chart = new Highcharts.Chart({
					chart : {
						renderTo : 'container',//要渲染到那个标签
						//type : 'chart_column',//类型 目前 我用到了 line column pie (column line area综合)
						//marginRight : 130,
						//marginBottom : 25,
						height: 285,
						zoomType: 'xy'
					},
					lang : { //这个是修改是上面 打印 下载的提升为汉字，如果在highcharts.js里面修改 简直累死
						exportButtonTitle : '导出',
						printButtonTitle : '打印',
						downloadJPEG : "下载JPEG 图片",
						downloadPDF : "下载PDF文档",
						downloadPNG : "下载PNG 图片",
						downloadSVG : "下载SVG 矢量图"
					},
					credits : {//右下角的文本
						enabled : false
					},
					exporting : {// 是否允许导出 就是右上角的按钮显示不显示
						enabled : false
					},
					title : {
						text : null
					},
					xAxis : {
						//gridLineWidth : 1,
						labels : {
							align : 'center',
							formatter : function() {
								return this.value;
							}
						},
						categories : d_riqi
					},
					plotOptions : {},
					yAxis : [ {
						title : {
							text : '下单量',
							style: {
								color: '#4572A7'
							}
						},
						labels : {
							align : 'right',
							x : 0,
							y : 5,
							formatter : function() {
								return this.value + ' ';
							},
							style: {
								color: '#4572A7'
							}
						},
						min : 0
					}, { // right y axis
						title : {
							text : '下单金额',
							rotation : 270,
							style : {
								color : '#89A54E'
							}
						},
						labels : {
							align : 'left',
							x : 0,
							y : 5,
							formatter : function() {
								return '￥' + this.value;
							},
							style : {
								color : '#89A54E'
							}
						},
						opposite : true,
						min : 0
					} ],
					tooltip : { //这个是鼠标放在上面提升的文字
						formatter : function() {
							var unit = {
			                    '下单量': '单',
			                    '下单金额': '元'
			                } [this.series.name];
							return '<b>' + this.series.name + '</b><br/>'
									+ this.x + ': ' + this.y + ' ' + unit;
						}
					},
					legend : {
						layout : 'vertical',
						align : 'left',
						verticalAlign : 'top',
						x : 120,
						y : 40,
						floating: true,
						shadow: true,
						backgroundColor: '#FFFFFF'
						//borderWidth : 0
					},
					series : [
							{ //这个是绑定数据了。
								name : '下单量',
								color: '#4572A7',
								type: 'column',
								data : orderNums
							},
							{
								name : '下单金额',
								color: '#89A54E',
								type: 'line',
								data : orderMoneys,
								yAxis : 1
							} ]
				});
			});
	  
	  function subTitle(title){
	    if(title.length>14){
	       title=title.substring(0, 12)+"...";
	    }
	  	return title;
	  }
	  
	    // 查询登录日志
	  function selectLoginLog(){
	  	  
	  	  location.href="${BasePath}/merchants/login/queryLoginLog.sc";	  	
	  }
</script>