<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="${BasePath }/css/finance/finance.css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/print.js"></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>打印合同</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<div id="printpage">
<table  border="0" align="center">
  <tr>
    <td colspan="2" align="center"> <span class="STYLE1">成品购货合同</span></td>
    <td align="right">合同编号：</td>
    <td align="left"><#if purchase??&&purchase.purchaseCode??>${purchase.purchaseCode}</#if></td>
  </tr>
  <tr>
    <td>合同号：</td>
    <td ><#if purchase??&&purchase.purchaseCode??>${purchase.purchaseCode}</#if></td>
    <td>最后交换日：</td>
    <td><#if purchase??&&purchase.deliveryDate??>${purchase.deliveryDate}</#if></td>
  </tr>
  <tr>
    <td>购货方：</td>
    <td >(以下称甲方)百丽鞋业(宜天)有限公司</td>
    <td >鉴定地点：</td>
    <td >&nbsp;</td>
  </tr>
  <tr>
    <td >供货方：</td>
    <td><#if purchase??&&purchase.supplier??&&purchase.supplier.supplier??>${purchase.supplier.supplier}</#if></td>
    <td >签约日：</td>
    <td >
      <label>
        <input type="text" class="input_text" name="textfield" />
      </label>
    </td>
  </tr>
  <tr>
    <td colspan="4" align="left">1,甲，乙双方同意按下述条款和条件又由乙方为甲方加工下列商品(以下简称货号)</td>
  </tr>
</table>
<table border="1" align="center">
  <tr>
    <td align="center">序号</td>
    <td  align="center">商品编号</td>
    <td  align="center">厂编号</td>
    <td  align="center">名称</td>
    <#list sizes as size >
    <td width="24" align="center">${size}</td>
    </#list>
    <td  align="center">合计</td>
    <td  align="center">单价</td>
    <td align="center">金额</td>
  </tr>
<#if purchase??&&purchase.commodities??>				 
<#list purchase.commodities as c >
  <tr>
    <td align="center">${c_index + 1 }</td>
    <td align="center"><#if c??&&c.commodity??&&c.commodity.no??>${c.commodity.no}</#if></td>
    <td align="center"><#if purchase.supplier??&&purchase.supplier.supplierCode??>${purchase.supplier.supplierCode}</#if></td>
    <td align="center"><#if c??&&c.commodity??&&c.commodity.commodityName??>${c.commodity.commodityName}</#if></td>
    <#list sizes as size >
    	<td id="${size}" align="center">
    		 <#list c.details as detail>
    		 	<#if detail??&&detail.purchaseQuantity??&&detail.size==size>${detail.purchaseQuantity}</#if>
    		 </#list>
    	</td>
    </#list>
    <td align="center"><#if c??&&c.total??>${c.total}</#if></td>
    <td align="center"><#if c??&&c.purchasePrice??>${c.purchasePrice}</#if></td>
    <td align="center"><#if c??&&c.totalPrice??>${c.totalPrice}</#if></td>
  </tr>
</#list>	
</#if>
</table>
<table border="0" align="center">
  <tr>
    <td >合计</td>
    <td><#if purchase??&&purchase.amount??>${purchase.amount}</#if>件</td>
    <td><#if purchase??&&purchase.totalPrice??>${purchase.totalPrice}</#if>￥</td>
  </tr>	
  <tr>
    <td colspan="4">总价：<#if purchase??&&purchase.chineseTotalPrice??>${purchase.chineseTotalPrice}</#if></td></td>
  </tr>
  <tr>
    <td colspan="4"><p>2.2：首单的具体产品明细见附件-&lt;&lt;厂商订货(生产单)&gt;&gt;所示。<br />
      第三条 产品模型，包款得最低数量：<br />
      3.1：有关包楦，包款的最低数量：<br />
      （1） 对乙方同一楦型货品甲方首次订量超过 
      <input name="textfield222" class="input_text" type="text" size="12" />
      双（含
      <input name="textfield322" class="input_text" type="text" size="12" /> 
      双）时，该楦型即被甲方永久独家使用 <br />
      （2）	对乙方单款鞋甲方首次订量超过
      <input name="textfield323" class="input_text" type="text" size="12" /> 
      双（含
      <input name="textfield324" class="input_text" type="text" size="12" /> 
      双）时，该鞋款即被甲方永久独家使用（包款）。<br />
      3.2乙方承诺甲方包楦、包款的权利 <br />
        （1）	乙方承诺所供甲方鞋之楦型和款式均属自有知识产权产品，或已取得知识产权拥有方允许乙方向甲方提供委托定<br />
        牌加工生产之产品。<br />
        （2）	对甲方已包楦或包款的鞋，乙方只能用于甲方的订、补货生产。未经甲方授权，乙方不能再用甲方已包楦型和已<br />
        包款给第三方或自己生产货品。 <br />
        （3）	如乙方违反此条款，乙方须按因此给甲方造成的实际损失赔偿甲方。对该赔偿，甲方有权直接从应付乙方货款中<br />
        扣除。 <br />
        第四条 产品标识与包装 <br />
        4.1定牌产品标识与包装使用甲方授权其使用的&quot;TATA&quot;注册商标，以及法律许可甲方使用的其他标记、文字、符号。 <br />
        4.2乙方未经同意不能将甲方授权其使用的&quot;TATA&quot;注册商标，及法律许可甲方使用的其他标记、文字、符号等 <br />
        产品标识与包装提供给第三方，包括与此相关的容易混淆的任何标记、商标、铭牌或公司名称。 <br />
        4.3定牌产品仅允许甲方或甲方授权的企业销售，乙方未经甲方书面授权不得销售。如果甲方发现有与乙方合作的第三 <br />
        方有对甲方商标、标记的侵权行为，应立即通知乙方停止为其生产。当侵权实施已经发生，乙方未获准甲方同意不能 <br />
        擅自处理侵权产品。 <br />
        4.4乙方须根据甲方要求，在产品内外包装标明&quot;TATA&quot;品牌。鞋垫商标：使用(    )黑底白字布标，(    ) 白底黑字 <br />
        布标，(    ) 烫印或丝印。 <br />
        4.5鞋内标示：标示的内容包括货号的第四位到第九位、字符、国标码、鞋型等内容，标示的位置在鞋口内侧或其他适 <br />
        当位置（以甲方确认的确认版为准）。 <br />
        4.6由甲方提供内鞋盒(                                         )、三包卡、合格证等物料用于货品包装。外箱则由乙方按甲方明细包装的要求提供。 <br />
        4.7包装方式：按附件一厂商订货（生产单）上的城市或城市批发仓装箱，其中女鞋类的单鞋、中后空鞋、凉鞋拖鞋 <br />
        以及低靴每箱装16对，中靴每箱装16对，高靴每箱装12对。男鞋类单鞋每箱装15对，特殊规格鞋按实际情况装箱。 <br />
        配码则按订单明细表上的配码分装。外箱上及送货单均需注明品牌、城市或城市批发仓、订单号、货号、配码、数量、 <br />
        配码。外箱必须用三坑瓦楞纸箱。 <br />
        第五条 生产确认、质监及验收 <br />
        5.1乙方按首批订单货号产品做确认版（需每款色各一对）。确认版由甲方确认后乙方才能开始按确认版标准正式投产。 <br />
        5.2甲乙双方共同遵照国家相关行业标准的要求及甲方对货品的其它合理要求。其中： <br />
        （1）皮鞋按QB/T 1002-2005行业标准执行。对感官质量按该标准中4.1.5优等品项目要求生产，物理机械性能按该标准中4.2各检测项目优等品要求生产。 <br />
        （2）皮凉鞋按QB/T2307-97行业标准执行。对感官质量按该标准中4.3优等品项目要求生产，物理机械性能按该标准中4.6中各检测项目优等品要求生产。 <br />
        （3）产品中有关皮革、皮毛的有害物质限量按GB20400-2006标准执行。 <br />
        5.3甲方有权对正在生产的订补单货品进行检查。如发现问题应及时按甲方质监员的要求处理，甲方并保留拒绝接收这 <br />
        些货品的权利。 <br />
        5.4对乙方已交付甲方的有质量缺陷产品在两个销售季节内（春季鞋为当年春秋两季、秋季鞋为当年秋季和来年春季两 <br />
        季，冬夏鞋为当年冬夏和来年冬夏两季）， 甲方可以根据具体情况向乙方提出退货、返修或索赔。退货、返修引起的 <br />
        运杂费、维修费由乙方负责。该期限从甲方在仓库或任何甲方指定的交货地点收到货的日期算起。 <br />
        5.5. 乙方生产定牌产品在设计、规格、替代原材料及部件等方面如有变更，事前需征得甲方的书面同意。如果甲方提 <br />
        出任何变更要求，需乙方书面同意。 <br />
        5.6乙方应进行严格的产品出厂前的检验测试，以确保产品品质与合同条款和质量标准规定的内容相符。  甲方可以通 <br />
        过其质监代表共同参与对出厂产品的质量控制。同时有权利不定期检查与定牌产品相关的检测设备、生产过程及材料 <br />
        零部件质量控制。 <br />
        第六条  产品发货、运输及交货期 <br />
        6.1乙方必须按本合同附件一《厂商订货（生产单）》的甲方分仓订货要求发货；乙方在出货当日或次日须填写完整 <br />
        出货明细表（格式见附件二）并电邮（ E_mail ）至甲方跟单人员。乙方如有违反， 甲方将视具体工作延误状况对 <br />
        乙方作相应处罚。 <br />
        6.2投料顺序：由乙方参照本合同附件一《厂商订货（生产单）》明细表上的分仓城市，其中春夏季采取由南方到 <br />
        北方的投料顺序安排生产；秋冬季采取由北方到南方的投料顺序安排生产。生产安排计划表（格式见附件三）交 <br />
        甲方确认后实施。 <br />
        6.3交货方式：由乙方负责将全部货品送往甲方指定的货运商发往各城市。产品运输由甲方指定的货运商负责，甲方 <br />
        承担运费。乙方把货物交给甲方指定的货运商之前出现的风险责任由乙方负责，之后出现的风险责任由甲方负责， <br />
        但乙方有义务配合甲方对出现货运风险的事做处理。 <br />
        6.4交货日期：按甲方确认的生产安排计划表或《厂商订货（生产单）》的交货时间要求出货。如乙方迟交货品，则 <br />
        按迟交货品价值每日收取千分之三的罚金。由于皮料等原因所造成的延误须经甲方书面出具改单确认函确认。 <br />
        6.5除经甲方改单确认函确认的调整之外，任何货品的交付如果超过交货期10天以上，甲方有权取消未交货品订单， <br />
        并不承担任何责任。 <br />
        第七条  付款及发票的开据 <br />
        7.1依据甲乙双方对货品出货期的共同约定，上月26日至当月25日为当月出货期，对帐依据为甲方当月实收乙方货品和 <br />
        合理在途货品的数量及金额， 同时扣减当月由甲方退给乙方的有质量问题的产品数量金额以及因乙方原因由甲方对乙方处 <br />
        罚的金额。具体对帐程序为:乙方在每月26日填写当月对帐明细表（格式见附件四）传递给甲方跟单人员，双方在收到 <br />
        出货明细表后的2个工作日内完成对帐。 <br />
        7.2经双方对帐无误后，乙方须在对帐后的次月15日前开据与对帐结果相符合的一般纳税人增值税专用发票（17%税率） <br />
        送至甲方。甲方在收到乙方合格的增值税发票的当月月底前安排付款。如乙方不能及时向甲方开具发票，影响到甲方 <br />
        正常经营核算的，甲方将不予结算乙方货款并有权停止本合同（包括补单）中乙方仍未交付甲方的货品。 <br />
        7.3为保障合法的经营秩序，甲乙双方对发票开据共同约定如下，如发生以下任何一种情况，甲方有权拒付出货货品款 <br />
        项，并且乙方将承担由此而造成的全部法律责任以及因此给甲方造成的经济损失： <br />
        （1）乙方开据的一般纳税人增值税发票有误： <br />
        a，发票及收款单位名称与本合同签署单位名称不一致； <br />
        b，除上述单位名称不一致以外的其他任何错误。 <br />
        （2）不能开据一般纳税人增值税发票或提供的一般纳税人增值发票不符合国家法规规定。 <br />
        第八条  商标许可事宜 <br />
        8.1为便于乙方开展本合同约定的委托定牌生产活动，甲方将就本合同条款2.1项及甲方的补单所列明的产品规格、数量 <br />
        对乙方做出商标授权使用许可。商标授权使用许可的期限将与本合同期限一致，最长不超过六个月。 <br />
        8.2 乙方确认甲方商标授权许可生产的单位名称详见附件三中的生产厂家。 <br />
        第九条   乙方就经营资格向甲方做如下承诺和保证： <br />
        9.1乙方为独立核算，自负盈亏的实体，其与第三方的债仅、债务与甲方无关。乙方本身具备足够资金及能力，按照本 <br />
        合同定牌生产甲方订单产品。 <br />
        9.2乙方及其股东、董事不是甲方所属公司机构的现任职员及其直系亲属。 <br />
        9.3乙方的营业执照、税务登记证、增值税一般纳税人资格证书、法定代表人身份证明文件、签署人身份证及授权委托 <br />
        书（正本）等有关证件复印本已提供给甲方。 <br />
        9.4乙方承诺经营活动中始终遵守国家法律法规的规定，本合同所涉及的加工、生产业务均为乙方在营业地址所属地区 <br />
        已获准经营的项目，且自行承担经营产生的一切费用。 <br />
        9.5 乙方应按照甲方的生产、工艺及委托定牌加工管理要求及时提供甲方需要的相关单据、报表和报告。 <br />
        9.6 乙方承诺只从事合法及符合道德的实务，没有向甲方任何人支付任何贿款或其他利诱。如有甲方任何人员向乙方 <br />
        提出回扣、索贿等不合理的要求，乙方应及时将这一情况向甲方反馈。投诉电话：0755－82877388。 <br />
        9.7 乙方承诺理解并遵守本合同的所有条款约定。 <br />
        第十条  法律适用及争议解决 <br />
        10.1合同双方应严格按本合同条款履行合同，任何一方不得违反本合同规定，否则将视为违约。 <br />
        10.2本合同适用中华人民共和国法律，本合同下的任何纠纷应依照中华人民共和国法律解决。 <br />
        10.3 因本合同的解释与履行产生的任何纠纷，双方应首先努力通过友好协商解决。如经友好协商不能解决，任何一 <br />
        方均可将争议提交中国国际经济贸易仲裁委员会华南分会仲裁，按该机构届时有效的仲裁规则在深圳进行仲裁。 <br />
        仲裁裁决是终局的，对双方均有约束力。 <br />
        10.4 甲乙双方约定的合同签署地为深圳市，本合同有效期自签订之日起六个月内有效。 <br />
        10.5因乙方违约对甲方造成的损失，甲方有权根据合同条款约定的进行索偿，其时间不受合同有效期的限制。 <br />
        第十一条  附则 <br />
        11.1本合同的条款、附件共同组成一个完整的协议，在此之前的口头约定、书面资料不构成协议的内容因而无效。 <br />
    11.2本合同壹式贰份，甲、乙双方各执壹份，经双方受权代表签字、盖公章后生效。</p></td>
  </tr>
  <tr>
    <td colspan="4">（以下为签署页）</td>
  </tr>
  <tr>
    <td  align="right">甲方：</td>
    <td >&nbsp;</td>
    <td  align="right">乙方：</td>
    <td ><input name="textfield3242" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">法定代表人：</td>
    <td>&nbsp;</td>
    <td align="right">法定代表人：</td>
    <td><input name="textfield32422" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">委托代理人：</td>
    <td>&nbsp;</td>
    <td align="right">委托代理人：</td>
    <td><input name="textfield32423" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">地址：</td>
    <td>&nbsp;</td>
    <td align="right">地址：</td>
    <td><input name="textfield32424" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">电话：</td>
    <td>&nbsp;</td>
    <td align="right">电话：</td>
    <td><input name="textfield32425" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">传真：</td>
    <td>&nbsp;</td>
    <td align="right">传真：</td>
    <td><input name="textfield32426" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">邮编：</td>
    <td>&nbsp;</td>
    <td align="right">邮编：</td>
    <td><input name="textfield32427" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">开户银行：</td>
    <td>&nbsp;</td>
    <td align="right">开户银行：</td>
    <td><input name="textfield32428" class="input_text" type="text" /></td>
  </tr>
  
  <tr>
    <td align="right">帐号：</td>
    <td>&nbsp;</td>
    <td align="right">帐号：</td>
    <td><input name="textfield32429" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">税号：</td>
    <td>&nbsp;</td>
    <td align="right">税号：</td>
    <td><input name="textfield324210" class="input_text" type="text" /></td>
  </tr>
  <tr>
    <td align="right">日期：</td>
    <td>&nbsp;</td>
    <td align="right">日期：</td>
    <td><input name="textfield324211" class="input_text" type="text" /></td>
  </tr>
</table>
</div>
<a  class="btn-sh" href="#" onclick="printPage('printpage')">打&nbsp;印</a>
<p>&nbsp;</p>
</body>
</html>
