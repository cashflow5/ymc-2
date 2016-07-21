<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-待售商品</title>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?${style_v}"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/commodity-list.css?${style_v}"/>
<script type="text/javascript">
	var basePath = "${BasePath}";
	var commodityNos = "${commodityNos!''}";
</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 商品 &gt; 待售商品</p>
			<div class="tab_panel">
				<ul class="tab">
					<li onclick="javascript:location.href='${BasePath}/commodity/goWaitSaleCommodity.sc'"><span>待售商品</span></li>
					<li class="curr" onclick="javascript:location.href='${BasePath}/commodity/goWaitSaleSensitiveCommodity.sc'"><span>含敏感词</span></li>
					<li class="tab_fr" style="right:200px;" title="商品回收站">
						<a class="button" href="go4SaleCommodityRecycle.sc"><span>商品回收站</span></a>
					</li>
					<li class="tab_fr" style="right:100px;" title="可根据搜索条件导出">
						<a class="button" href="javascript:void(0);" onclick="doExport();"><span> 导出</span></a>
						<div class="span" style="visibility: hidden;"   id="progressBar" >
								<div class="span"><img src='${BasePath}/yougou/images/loading.gif'/></div>
								<div class="span" id="progressBarSpan" >
								   0
								</div>
								<div class="span">%</div>
							</div>
					</li>
					<li class="tab_fr">
						<div id="importpicker" class="button">导入修改资料</div>
					</li>
				</ul>
				<div class="tab_content" id="tab_content">
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" method="post">
							<p>
								<span><label style="width: 80px;">商品名称：</label><input type="text" class="inputtxt" id="commodityName" name="commodityName" value="${commodityQueryVo.commodityName!''}"/></span>
								<span>
									<label style="width: 90px;">商品编码：</label>
									<input type="text" class="inputtxt" id="commodityNo_" name="commodityNo_" value="${commodityQueryVo.commodityNo!''}"/>
									<input type="hidden" class="inputtxt" id="commodityNo" name="commodityNo" value="${commodityQueryVo.commodityNo!''}"/>
								</span>
								<span>
									<label style="width: 100px;">商家款色编码：</label>
									<input type="text" class="inputtxt" id="supplierCode_" name="supplierCode_" value="${commodityQueryVo.supplierCode!''}"/>
									<input type="hidden" class="inputtxt" id="supplierCode" name="supplierCode" value="${commodityQueryVo.supplierCode!''}"/>
								</span>
								<span>
									<label style="width: 90px;">商品款号：</label>
									<input type="text" class="inputtxt" id="styleNo_" name="styleNo_" value="${commodityQueryVo.styleNo!''}" />
									<input type="hidden" class="inputtxt" id="styleNo" name="styleNo" value="${commodityQueryVo.styleNo!''}"/>
								</span>
							</p>
							<p>
							
								<span>
								<label style="width: 80px;">货品条码：</label>
								<input type="text" class="inputtxt" id="thirdPartyCode_" name="thirdPartyCode_" value="${commodityQueryVo.thirdPartyCode!''}"/>
								<input type="hidden" class="inputtxt" id="thirdPartyCode" name="thirdPartyCode" value="${commodityQueryVo.thirdPartyCode!''}"/>
								</span>
								
								<span>
								<label style="width: 90px;">优购价格：</label><input type="text" style="width: 48px;" class="inputtxt" id="minSalePrice" name="minSalePrice" value="${commodityQueryVo.minSalePrice!''}" /> 至
								<input type="text" style="width: 48px;" class="inputtxt" id="maxSalePrice" name="maxSalePrice" value="${commodityQueryVo.maxSalePrice!''}" />
								</span>
								<span><label style="width: 100px;">商品品牌：</label>
								<select id="brandNo" name="brandNo" style="width:128px;">
									<option value="">请选择</option>
									<#list lstBrand as item>
										<option <#if commodityQueryVo.brandNo??&&item.brandNo??&&commodityQueryVo.brandNo==item.brandNo>selected</#if> value="${(item.brandNo)!""}">${(item.brandName)!""}</option>
									</#list>
								</select>
								</span>
								<span id="query_goods_is_audit_opt" class="query_goods_condition_item">
									<label style="width: 90px;">商品状态：</label>
									<select id="auditStatus" name="auditStatus" style="width:128px;">
										<option value="">请选择</option>
										<#list statics['com.yougou.kaidian.commodity.component.CommodityStatus'].getWaitSaleStatus() as item>
											<option <#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == (item.index)?string>selected</#if> value="${item.index}">
												${item.statusName?string!''}
											</option>
										</#list>
									</select>
								</span>
								
							</p>
							<p>
								
								<span><label style="width: 80px;">下架类型：</label>
								<select id="status" name="status" style="width:128px;">
									<option value="0">请选择</option>
									<option <#if commodityQueryVo.status??&&commodityQueryVo.status==1 >selected</#if> value="1">从未上架商品</option>
									<option <#if commodityQueryVo.status??&&commodityQueryVo.status==2 >selected</#if> value="2">人工下架商品</option>
								</select>
								</span>
								
								<span><label style="width: 90px;">商品分类：</label>
									<input type="hidden" id="memoryRootCat" value="${commodityQueryVo.rootCattegory!''}" />
									<input type="hidden" id="memorySecondCat" value="${commodityQueryVo.secondCategory!''}" />
									<input type="hidden" id="memoryThreeCat" value="${commodityQueryVo.threeCategory!''}" />
								    <select id="rootCattegory"  name="rootCattegory">
								    <option value="" selected="selected">一级分类</option>
								    <#list lstCat as item>
								    	<option <#if commodityQueryVo.rootCattegory??&&item.structName??&&commodityQueryVo.rootCattegory==item.structName >selected</#if> value="${(item.structName)!""}">${(item.catName)!""}</option>
								    </#list>
								    </select>
				                    <select name="secondCategory" class="fl-lf" id="secondCategory">
				                    	<option value="" selected="selected">二级分类</option>
				                    </select>
				                    <select name="threeCategory" class="fl-lf" id="threeCategory">
				                    	<option value="" selected="selected">三级分类</option>
				                    </select>
								</span>
								

								<span style="padding-left:62px;"><a id="mySubmit" class="button" onclick="javascript:qc.formSubmit('/commodity/goWaitSaleSensitiveCommodity.sc');"><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
				    <table class="common_lsttbl mt10">
                        	<colgroup><col width="340">
                            <col>
                            <col width="90">
                            <col>
                            <col width="70">
                            <col width="80">
                            </colgroup><thead>
                                <tr>
                                    <th>商品</th>
                                    <th>价格</th>
                                    <th>创建时间</th>
                                    <th>含敏感词</th>
                                    <th>商品状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="common_lsttbl_cz">
                            	<tr><td height="3" colspan="8"></td></tr>
                            	<tr class="cz_box">
                                	<td colspan="8">
                                	<style>
                                	.common_lsttbl_cz .cz_bd_1 a{margin-left:8px;}
                                	</style>
                                    	<div class="cz_bd cz_bd_1 fl">
                                    	          &nbsp; 全选
                                    	    <input type="checkbox" class="checkall ml8" id="checkAll_delete">
                                            <a href="javascript:;" onclick="javascript:qc.opt.deleteBatch_OnClick('/commodity/goWaitSaleSensitiveCommodity.sc');return false;">删除</a>
                                            
                                            <input type="checkbox" class="checkall ml8" id="checkAll_submitAudit">
                                            <a href="javascript:;" onclick="javascript:qc.opt.submitAuditBatch_OnClick('/commodity/goWaitSaleSensitiveCommodity.sc');return false;">提交审核</a>
                                        </div>
                                        <div class="cz_page fr">

                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                            <tbody class="common_proitm" id="common_proitm">
                            <#if pageFinder??&&(pageFinder.data)??>
							<#list pageFinder.data as item>
                            	<tr class="line_gap" id="${item.commodity_no!""}-1">
                                	<td colspan="8"></td>
                                </tr>
                                <tr class="proitm_hd" id="${item.commodity_no!""}-2">
                                	<td colspan="8">
                                    	<input type="checkbox" value="${item.commodity_no!""}_${item.supplier_code!""}" name="waitsale" class="checkone ml8">
                                    	<span class="ml5">商品编码：${item.commodity_no!"—"}</span>
                                    	<span class="ml15">年份：${item.years!"—"}</span>
                                    	<span class="ml15">颜色：${item.spec_name!"—"}</span>
                                    </td>
                                </tr>
                                <tr class="proitm_bd" id="${item.commodity_no!""}-3">
                                	<td class="td_brdrlf">
                                    	<div class="pro_pic fl">
                                    	    <#if item.pic_small??&&item.pic_small!=''>
                                    	        <img width="40" height="39" alt="" src="${item.pic_small!''}"/>
                                    	    <#else>
                                    	        <img width="40" height="39" alt="" src="${BasePath}/yougou/images/nopic.jpg"/>
                                    	    </#if>
                                        </div>
                                        <div class="txt_inf fl">
                                        	<p class="mb3"><a target="_blank" href="${BasePath}/commodity/previewDetail.sc?commodityNo=${(item.commodity_no)!''}">${item.commodity_name!"—"}</a></p>
                                            <p>商品款号：<input value="${item.style_no!"—"}" readonly="readonly" style="border: none; margin: -3px 0 0 -5px;"></p>
                                            <p>商家款色编码：<input value="${item.supplier_code!"—"}" readonly="readonly" style="border: none; margin: -3px 0 0 -5px;"></p>
                                        </div>
                                    </td>
                                    <td>
                                            <p>优购价：${item.sale_price!"—"}</p>
                                            <p>市场价：${item.public_price!"—"}</p>
                                    </td>
                                    <td>${item.create_date!"—"}</td>
                                    <td title="${(item.sensitive_word)!'' }">
                                    <#assign arr=(item.sensitive_word)?split(",")>
                                    <#if ((arr)?size<=3)>
                                    	${(item.sensitive_word)!'' }
                                    <#else>
                                    	<#list arr as x>${x }<#if (x_index<2)>,<#else>...<#break></#if></#list>
                                    </#if>
                                    </td>
                                    <td><span style="<#if item.jmsFinish == 1>color:#666666;<#else><#switch '${item.commodity_status!-1}'><#case '11'>color:#EE9A00;<#break><#case '5'>color:#228B22;<#break><#case '13'><#case '1'>color:#EE4000;<#break><#case '12'><#case '4'>color:#9BCD9B;<#break><#default>color:#666666;</#switch></#if>">${(statics['com.yougou.kaidian.commodity.component.CommodityStatus'].getStatusName(item.commodity_status!-1))}</span></td>
                                    <td class="td_brdrrt">
                                        <input type="hidden" id="vb-${(item.commodity_no)!''}_${item.supplier_code!""}" value="<#if item.commodity_status == 11||item.commodity_status == 1||item.commodity_status == 13>true</#if>"/>
                                        <input type="hidden" id="sb-${(item.commodity_no)!''}_${item.supplier_code!""}" value="<#if item.commodity_status == 11||item.commodity_status ==1>true</#if>"/>
										<p><a href="javascript:void(0);" id="n_${(item.commodity_no)!''}" onclick="checkStatus('${(item.commodity_no)!''}',1);">修改</a></p>
										<!--新建-->
										<#if item.commodity_status == 11>
											<p><a href="javascript:qc.opt.delete_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >删除</a></p>
											<p><a href="javascript:qc.opt.submitAudit_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >提交审核</a></p>
										<!--提交审核-->
										<#elseif item.commodity_status == 12>
										<!--审核通过-->
										<#elseif item.commodity_status == 5>
										<!--审核拒绝-->
										<#elseif item.commodity_status ==13>
											<p><a href="javascript:qc.opt.delete_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >删除</a></p>
											<p><a href="javascript:;" data-attr="{title: '${(item.commodity_no)!''}', reason:'${item.refuse_reason!"—"}'}" class="f_blue refuse_reason">拒绝原因</a></p>
										<#elseif item.commodity_status == 1>
											<p><a href="javascript:qc.opt.delete_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >删除</a></p>
											<p><a href="javascript:qc.opt.submitAudit_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >提交审核</a></p>
										</#if>
                                    </td>
                                </tr>
                        </#list>
						<#else>
							<tr>
								<td colspan="12" class="td-no">没有相关数据！</td>
							</tr>
						</#if>
                            </tbody>                
                     </table>
					<!--列表end-->
				<form target="_blank" style="display: none;" id="tempForm" method="post">
					<input name="commodityNo" type="hidden"/>
					<input name="isSensitive" type="hidden"/>
				</form>
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
				<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryForm"/>
				</div>
			</#if>
			<!--分页end-->
			</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/waitSaleCommodity.js?${style_v}"></script>
</body>
</html>
