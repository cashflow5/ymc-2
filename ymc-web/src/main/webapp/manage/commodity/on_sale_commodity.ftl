<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-在售商品</title>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js?${style_v}"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/commodity-list.css?${style_v}"/>
<script type="text/javascript">
	var basePath = "${BasePath}";
	var commodityNos = "${commodityNos!''}";
</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 商品 &gt; 在售商品</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>出售中的商品</span></li>
					<li class="tab_fr">
						<a class="button" <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>onclick="doExport()"<#else>onclick="alert('没有数据导出！')"</#if> ><span title="根据搜索条件导出">导出</span></a>
					</li>
				</ul>
				<div class="tab_content">
				
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
									<input type="hidden" class="inputtxt" id="supplierCode" name="supplierCode" value="${commodityQueryVo.supplierCode_!''}"/>
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
									<input type="text" class="inputtxt" id="thirdPartyCode_" name="thirdPartyCode_" value="${commodityQueryVo.thirdPartyCode!''}" />
									<input type="hidden" class="inputtxt" id="thirdPartyCode" name="thirdPartyCode" value="${commodityQueryVo.thirdPartyCode!''}"/>
								</span>
							
								<span>
								<label style="width: 90px;">优购价格：</label><input type="text" style="width: 48px;" class="inputtxt" id="minSalePrice" name="minSalePrice" value="${commodityQueryVo.minSalePrice!''}" /> 至
								<input type="text" style="width: 48px;" class="inputtxt" id="maxSalePrice" name="maxSalePrice" value="${commodityQueryVo.maxSalePrice!''}" />
								</span>

                                <span><label style="width: 100px;">商品品牌：</label>
								<select id="brandNo" name="brandNo" style="width:128px;">
									<option value="">请选择</option>
									<#if lstBrand??>
										<#list lstBrand as item>
											<option <#if commodityQueryVo.brandNo??&&item.brandNo??&&commodityQueryVo.brandNo==item.brandNo>selected</#if> value="${(item.brandNo)!""}">${(item.brandName)!""}</option>
										</#list>
									</#if>
								</select>
								</span>
									<input type="hidden" id="memoryRootCat" value="${commodityQueryVo.rootCattegory!''}" />
									<input type="hidden" id="memorySecondCat" value="${commodityQueryVo.secondCategory!''}" />
									<input type="hidden" id="memoryThreeCat" value="${commodityQueryVo.threeCategory!''}" />
								
								
							</p>
							<p>
							
								<span><label style="width:80px;">商品分类：</label>
								    <select id="rootCattegory"  name="rootCattegory">
								    <option value="" selected="selected">一级分类</option>
								    <#if lstCat??>
									    <#list lstCat as item>
									    	<option <#if commodityQueryVo.rootCattegory??&&item.structName??&&commodityQueryVo.rootCattegory==item.structName >selected</#if> value="${(item.structName)!""}">${(item.catName)!""}</option>
									    </#list>
								    </#if>
								    </select>
				                    <select name="secondCategory" class="fl-lf" id="secondCategory">
				                    	<option value="" selected="selected">二级分类</option>
				                    </select>
				                    <select name="threeCategory" class="fl-lf" id="threeCategory">
				                    	<option value="" selected="selected">三级分类</option>
				                    </select>
								</span>
								
								<span>
								<label style="width:80px;">上架时间：</label>
								<input type="text" style="width: 80px;" class="inputtxt" id="minShowDate" name="minShowDate" value="${commodityQueryVo.minShowDate!''}"/> 至
								<input type="text" style="width: 80px;" class="inputtxt" id="maxShowDate" name="maxShowDate" value="${commodityQueryVo.maxShowDate!''}"/>
								</span>
								
								<span><label style="width: 120px;">可售数量大于等于：</label><input type="text" style="width: 35px;" class="inputtxt" id="onSaleQuantiry" onkeyup="value=value.replace(/[^0-9]/g,'')"  name="onSaleQuantiry" value="${commodityQueryVo.onSaleQuantiry!''}" /></span>
								<#--<span><label>可售数量：</label>
								<select id="onSaleQuantiry" name="onSaleQuantiry" style="width:129px;">
									<option <#if commodityQueryVo.onSaleQuantiry??&&commodityQueryVo.onSaleQuantiry=="asc" >selected</#if> value="asc">升序</option>
									<option <#if commodityQueryVo.onSaleQuantiry??&&commodityQueryVo.onSaleQuantiry=="desc" >selected</#if> value="desc">降序</option>
								</select>
								</span>-->
								
								<span style="padding-left:40px;"><a id="mySubmit" class="button" onclick="mySubmit()"><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
				
					<!--列表start-->
					<table class="common_lsttbl mt10">
					      <colgroup>
						        <col width="310"/>
	                            <col width="100"/>
	                            <col/>
	                            <col/>
	                            <col/>
	                            <col/>
	                            <col width="110"/>
	                            <col width="100"/>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>商品</th>
                                    <th>年份</th>
                                    <th>颜色</th>
                                    <th>价格</th>
                                    <th>商品销量</th>
                                    <th>可售数量</th>
                                    <th>上架时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="common_lsttbl_cz">
                            	<tr><td height="3" colspan="8"></td></tr>
                            	<tr class="cz_box">
                                	<td colspan="8">
                                    	<div class="cz_bd fl">
                                        	<input type="checkbox" class="checkall ml8" id="checkAll"><label class="ml8" for="checkAll">本页全选</label>
                                            <a href="javascript:;" onclick="javascript:qc.opt.downBatch_OnClick();return false;">下架</a>
                                        </div>
                                        <div class="cz_page fr">

                                        </div>
                                    </td>
                                </tr>
                            </tbody>
						<tbody class="common_proitm" id="common_proitm">
						<#if pageFinder??&&(pageFinder.data)??>
							<#list pageFinder.data as item>
                            	<tr class="line_gap" id="${item.commodityNo!""}-1">
                                	<td colspan="8"></td>
                                </tr>
                                <tr class="proitm_hd" id="${item.commodityNo!""}-2">
                                	<td colspan="8">
                                    	<input type="checkbox" value="${item.commodityNo!""}" name="onsale" class="checkone ml8">
                                    	<span class="ml5">商品编码：${item.commodityNo!""}</span>
                                    </td>
                                </tr>
                                <tr class="proitm_bd" id="${item.commodityNo!""}-3">
                                	<td class="td_brdrlf">
                                    	<div class="pro_pic fl">
                                        	<img width="40" height="40" alt="" src="${item.picSmall!""}">
                                        </div>
                                        <div class="txt_inf fl">
                                        	<p class="mb3">
                                        		<input type="hidden" class="commodity-no" value="${item.commodityNo!""}"/>
                                        		<textarea>${item.commodityName!""}</textarea>
                                        		<a target="_blank" class="commodity-title" href="${item.prodUrl!''}">${item.commodityName!""}</a>
                                        		<a href="javascript:void(0)" class="edit" title="修改商品名称"></a>
                                        		<a href="javascript:void(0)" class="save" title="保存商品名称"></a>
                                        		<a href="javascript:void(0)" class="cancel" title="取消修改"></a>
                                        		<span><img src="${BasePath}/yougou/images/loading16.gif"></span>
                                        		<a style="clear:both;display:block"></a>
                                        	</p>
                                    		<p>商品款号：<input value="${item.styleNo!""}" readonly="readonly" style="border: none; margin: -3px 0 0 -5px;"></p>
                                            <p>商家款色编码：<input value="${item.supplierCode!""}" readonly="readonly" style="border: none; margin: -3px 0 0 -5px;"></p>
                                        </div>
                                    </td>
                                    <td>${item.years!""}</td>
                                    <td>${item.specName!""}</td>
                                    <td>
                                    	<input type="hidden" class="commodity-no" value="${item.commodityNo!""}">
                                        <p class="price-p">
                                        	<span class="price-tit">优购价：</span>
	                                        <em class="c3">
	                                        	<span class="curprice yougouprice">${item.salePrice!""}</span>
	                                        	<input type="text" value="${item.salePrice!""}" />
                                        		<a href="javascript:void(0)" class="edit4price" title="修改优购价"></a>
                                        		<a href="javascript:void(0)" class="save4price" type="1" title="保存后自动提交审核"></a>
                                        		<a href="javascript:void(0)" class="cancel4price" title="取消修改"></a>
                                        		<span class="loadimg"><img src="${BasePath}/yougou/images/loading16.gif"></span>
	                                        </em>
                                        </p>
                                        <p class="price-p">
                                        	<span class="price-tit">市场价：</span>
                                        	<em class="c3">
	                                        	<span class="curprice markprice">${item.publicPrice!""}</span>
	                                        	<input type="text" value="${item.publicPrice!""}" />
	                                    		<a href="javascript:void(0)" class="edit4price" title="修改市场价"></a>
	                                    		<a href="javascript:void(0)" class="save4price" type="2" title="保存后自动提交审核"></a>
	                                    		<a href="javascript:void(0)" class="cancel4price" title="取消修改"></a>
	                                    		<span class="loadimg"><img src="${BasePath}/yougou/images/loading16.gif"></span>
                                    		</em>
                                        </p>
                                        <p class="price-p">
                                        	<span class="price-tit">成本价：</span>
                                        	${item.costPrice!"无"}
                                        </p>
                                    </td>
                                    <td><strong class="c3 salenum_${item.commodityNo }">0</strong></td>
                                    <td>${item.onSaleQuantity!""}</td>
                                    <td><#if item.showDate??>${item.showDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></td>
                                    <td class="td_brdrrt">
                                    	<p><a href="javascript:qc.opt.down_OnClick('${(item.commodityNo)!''}')" >下架</a></p>
                                        <p><a href="javascript:qc.opt.update_OnClick('${(item.commodityNo)!''}')" >修改</a></p>
                                        <p><a href="javascript:qc.opt.showLog_OnClick('${(item.commodityNo)!''}')" >修改日志</a></p>
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
<script type="text/javascript" src="${BasePath}/yougou/js/manage/commodity/onSaleCommodity.js?${style_v}20160118"></script>
</body>
</html>