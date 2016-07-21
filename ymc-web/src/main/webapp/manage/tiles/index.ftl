<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="curr_position">您当前所在的位置 &gt; <a href="">商家后台管理</a> &gt; 首页</div>
    <div class="clearfix">
    	<div class="index_main_bdc fl">
        	<div class="bsnss_inf clearfix">
            	<div class="hd fl"><img src="${BasePath}/yougou/images/business.jpg" width="80" height="80" /></div>
                <div class="bd fr">
                	<ul class="inf_lst clearfix">
                    	<li><span>登录账号：</span><#if merchantUsers??>${merchantUsers.login_name!''}</#if></li>
                        <li><span>商家状态：</span><#if merchantUsers.status??&&merchantUsers.status==1>启用<#elseif merchantUsers.status??&&merchantUsers.status==0>锁定</#if></li>
                        <li><span>商家编码：</span>${merchantUsers.supplier_code!''}</li>
                        <li><span>是否已接入ERP系统：</span>已接入</li>
                        <li><span>商家名称：</span><#if merchantUsers??>${merchantUsers.supplier!''}</#if></li>
                        <li><span>服务期限：</span>2013-07-26至2014-07-26</li>
                        <li><span>合作模式：</span><#if merchantUsers.is_input_yougou_warehouse??>
                        	</#if>
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
                                <li><a href="?" target="_blank">待发货订单(<em class="cred">12</em>)</a></li>
                                <li><a href="?" target="_blank">缺货订单(<em class="cred">12</em>)</a></li>
                                <li><a href="?" target="_blank">超时未发货订单(<em class="cred">12</em>)</a></li>
                            </ol>
                        </div>
						<p>库存提示(少于5)：<a href="?" target="_blank">待发货订单(<em class="cred">12</em>)</a></p>
                    </div>
                    <div class="common_dl1 pro_manage clearfix mt12">
                    	<h4 class="fl">商品管理：</h4>
                        <ol class="fl clearfix">
                        	<li><a href="?" target="_blank">在售商品(<em class="cred">256</em>)</a></li>
                            <li><a href="?" target="_blank">审核通过(<em class="cred">256</em>)</a></li>
                            <li><a href="?" target="_blank">待提交审核(<em class="cred">256</em>)</a></li>
                            <li><a href="?" target="_blank">审核中(<em class="cred">256</em>)</a></li>
                            <li><a href="?" target="_blank">审核拒绝(<em class="cred">256</em>)</a></li>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="common_box1 mt12">
            	<div class="box1_hd"><h2>最近一周销量趋势</h2></div>
                <div class="box1_bd">
                	<div class="sales_trend" id="salesTrend">
                    	<img src="http://10.0.30.51/ui/business/demo/tbl.jpg" width="568" height="343" alt="" />
                    </div>
                </div>
            </div>
        </div>
        <div class="main_aside fr">
        	<div class="common_box1 help">
            	<div class="box1_hd"><h2>帮助中心</h2></div>
                <div class="box1_bd">
                	<ul class="common_lst1 clearfix">
                    	<li><a href="?" target="_blank">发布商品</a></li>
                        <li><a href="?" target="_blank">订单发货</a></li>
                        <li><a href="?" target="_blank">导入库存</a></li>
                        <li><a href="?" target="_blank">商品管理</a></li>
                        <li><a href="?" target="_blank">图片规范</a></li>
                        <li><a href="?" target="_blank">图片上传</a></li>
                        <li><a href="?" target="_blank">售后质检</a></li>
                        <li><a href="?" target="_blank">API对接</a></li>
                    </ul>
                </div>
            </div>
            <div class="common_box1 tip mt12">
            	<div class="box1_hd"><h2>小贴士</h2></div>
                <div class="box1_bd">
                	<ul class="common_lst1 clearfix">
                    	<li>订单被标记缺货后请不要发货，客服将对订单进行取消和退款的处理。</li>
                        <li>发货操作需严格按照先拣货 ，再标缺，最后发货的流程。</li>
                        <li>需确保商品库存准确，如果不准则可在库存修改页面中调整库存。</li>
                        <li>如果商品属于拒收，则需要质检订单包裹中的所有商品。</li>
                    </ul>
                </div>
            </div>
            <div class="common_box1 contact mt12">
            	<div class="box1_hd"><h2>联系方式</h2></div>
                <div class="box1_bd">
                	<p>
                    	技术问题与咨询：<a href=mailto:zs@yougou.com><em class="cred">zs@yougou.com</em></a><br />
                    	产品意见与建议：<a href="${BasePath}/merchants/feeback/list.sc" target="_blank"><em class="cred">意见反馈</em></a><br />
                        QQ交流：<em class="cred">1328558508  2027413897</em><br />
                        API接口地址：<a href="http://open.yougou.com" target="_blank"><em class="cred">open.yougou.com</em></a>
                    </p>
                    <p class="c9">友情提示！请务必在邮件中提供如下信息，以便我们帮您解答：店铺编号、商品编号、订单编号、页面地址等。</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>