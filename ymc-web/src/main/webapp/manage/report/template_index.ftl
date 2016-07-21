<h1 class="quota-title">
	<input type="checkbox" id="general" class="quota-all-btn"><label
		for="general">选择指标</label><span class="normal">(请选择5-8项)</span>
</h1>
<div class="quota-content">
	<ul class="quota-list quota-brand">
        <#if analysisIndexList?? && analysisIndexList?size &gt; 0>
			<#list analysisIndexList as item >
				  <li title="${item.label}<#if (item.dimension)?? &&(item.dimension!='0') && (item.dimension!=dimension)>（该指标仅针对<#if dimension=='1'>分类<#else>商品</#if>维度）</#if>"><input type="checkbox" ${((item.dimension!='0') && (item.dimension!=dimension))?string('disabled','') } class="quota-general" enname="${(item.enName)!'' }" id="g-${item.id}" 
				  <#list templateIndexList as checkedItem>
				  	<#if checkedItem.id == item.id>
				  		checked="checked" 
				  		<#break>
				  	</#if>
				  </#list>
				  value="${item.id}"/><label for="g-${item.id}">
				  <#if ((item.label)?length<=5)>
				  	${item.label}
				  <#else>
				    ${(item.label)[0..4]}...
				  </#if>
				  </label></li>
			</#list>
		</#if>	
    </ul>
</div>
<div class="quota-drop-box">
	<select id="templateSel" data-ui-type="dropdown">
		<option value="0">预置模板</option>
		<#if templateList?? && templateList?size &gt;0 > 
			<#list templateList as item>
			<option value="${item.template_id}"<#if
				((item.is_last_use)??&&(item.is_last_use=='Y'))>selected='selected'</#if>>${item.template_name}</option>
			</#list> 
		</#if>
	</select> 
	<a href="javascript:updateTemplate();" class="btn-default">更新</a> <a
		href="javascript:delTemplate();" class="btn-default">删除</a> <a
		href="javascript:addTemplate();" class="btn-default">新增模板</a>
</div>
<div class="quota-btn-box">
	<span class="Gray checked-item">已选择<span id="checkedNum"><#if templateIndexList??>${templateIndexList?size }<#else>0</#if></span>项
	</span> <a href="javascript:submitTemplate();" class="btn-default active">确定</a>
	<a href="javascript:void(0);" class="btn-default ml5 quota-cancel">取消</a>
</div>