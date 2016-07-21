var ordernew = {};

ordernew.printBatch = function(){
	var checkeds = $("#tbody input:checked");
	if(checkeds.length==0){
		ygdg.dialog.alert("请选择需要打印的单据！");
	}else{
		var key = [];
		 $.each(checkeds,function(index,item){
			 key.push(checkeds.eq(index).attr("no"));
		 });
		 ordernew.print(key.join(","));
	}
};
ordernew.printSingle = function(orderNos){
	ordernew.print(orderNos);
};

ordernew.print = function(orderNos){
	ymc_common.loading("show","正在获取打印信息......");
	$.ajax({
		cache: false,
        async: false,
        type: 'POST',
        dataType:'json',
        url: basePath+"/order/toBatchPrintShoppingListTemplateAjax.sc",
        data: {
        	orderNos:orderNos
        },
        success: function(data){
        	ymc_common.loading();
        	if(data.result=="fail"){
        		ygdg.dialog.alert(data.msg);
        	}else{
        		var orderList = data.orderSubList;
             	LODOP=getLodop();
             	if(LODOP==null){
             		return;
             	}
             	//设置打印纸张大小
        		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
        		var strStyle="<style>" +
        				"*{margin:0px auto;}"+
        				"table,td,th{border-collapse: collapse;font-size:10pt;font-family: 微软雅黑;font-weight: normal;text-align:left;}" +
        				"table tr.line td,table tr.line th{font-size:11px;text-align:center;border-bottom:1px dashed #000000;}" +
        				"table tr.line th{padding:3px 0px;}"+
        				"table tr.line th{font-size:10pt;border-bottom:1px solid #000000;font-weight:bold;}" +
        				"table tr.lastline td{border-bottom:1px solid #000000}" +
        				"table tr td.qaP{font-size:11px;} table tr.line2 td{border-bottom:1px solid #000000;} td{color:#666} table tr td.barcode{height:60px;width:230px;}</style>";
        		for(var i=0,length=orderList.length;i<length;i++){
        			LODOP.NewPage();
        			var html =  ordernew.getHtmlByTemplateHead(orderList[i],i+1,data.merchantRejectedAddress);
        			LODOP.ADD_PRINT_TABLE(0,0,"100%","100%",strStyle+html);
        		}
        		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",0);//纵向打印时显示正面朝上
        		LODOP.PREVIEW();//预览
        	}
		}
     });
};

ordernew.getHtmlByTemplateHead = function(data,num,merchantRejectedAddress){
	var userName = data.orderConsigneeInfo.userName==null?"":data.orderConsigneeInfo.userName;
	var oderSubNo = data.orderSubNo ==null?"":data.orderSubNo;
	var createTime = data.createTime==null?"":data.createTime.time;
		createTime = new Date(createTime).format("yyyy-MM-dd hh:mm:ss");
	var totalQuantity  = data.productTotalQuantity==null?"0":data.productTotalQuantity;
	var html = "";
	html = html+'<div style="width:100%;text-align:center;margin:0px auto;"><table style="width:728px;">';
	html = html+'<tr><td colspan="7"><table width="100%"><tr>';
	html = html+'<td style="width:150px;padding-top:20px;" valign="top"><img src="'+basePath+'/yougou/images/printLogo2013.jpg" /></td>';
	html = html+'<td style="width:320px;text-align:right;padding-top:30px;" valign="top"><h1 style="font-size: 30px;font-weight: normal">优购网购物清单</h1></td><td></td></tr>';
	html = html+'<tr class="line2"><td colspan="2" valign="bottom" style="font-size:12px;">亲爱的会员：非常感谢您的购物，优购时尚商城期待您的再次光临！</td>'+
		'<td style="padding-top:0px" valign="top"><div style="padding-left:0px;">批次订单序号：'+num+'</div>';
	html = html+'<div style="margin-top:0px;width:200px;height:60px;"></div>';
	html = html+'</td></tr></table></td></tr>';
	html = html+'<tr><td colspan="7"><table width="100%"><tr><td>客户姓名：'+userName+'</td><td>订单号：'+oderSubNo+'</td><td>订购时间：'+createTime+'</td><td>商品总数：'+totalQuantity+'</td></tr></table></td></tr>';
	html = html+'<tr class="line"><th style="width:62px;">商品编码</th><th style="width:308px;">商品名称</th><th style="width:52px;">规格</th><th style="width:51px;">单价</th><th style="width:41px;">数量</th><th style="width:52px;">小计</th><th style="width:230px;">货品条码</th></tr>';
	LODOP.ADD_PRINT_BARCODE(92,492,250,55,"128A",data.orderSubNo);
	var detailSub = data.orderDetail4subs;
	var commodityNo;
	var prodName;
	var commoditySpecificationStr;
	var levelCode;
	var moreSpace = 0;//每一页页尾多余的部分
		for(var i=0,length = detailSub.length;i<length;i++){
			commodityNo = detailSub[i].commodityNo==null?"":detailSub[i].commodityNo;
			prodName = detailSub[i].prodName==null?"":detailSub[i].prodName;
			commoditySpecificationStr = detailSub[i].commoditySpecificationStr==null?"":detailSub[i].commoditySpecificationStr;
			levelCode = detailSub[i].levelCode==null?"":detailSub[i].levelCode;
			if(i==length-1){
				html = html+"<tr class='line goods lastline'><td style='width:62px;'>"+commodityNo+"</td><td style='width:308px;'><div style='height:30px;overflow:hidden'>"+prodName+"</div></td><td style='width:52px;'>"+commoditySpecificationStr+"</td>" +
				"<td style='width:51px;'>"+detailSub[i].activePrice+"</td><td style='width:41px;'>"+detailSub[i].commodityNum+"</td><td style='width:52px;'>"+detailSub[i].prodTotalAmt+"</td>" +
			"<td class='barcode'></td></tr>";
			}else{
				html = html+"<tr class='line goods'><td style='width:62px;'>"+commodityNo+"</td><td style='width:308px;'><div style='height:30px;overflow:hidden'>"+prodName+"</div></td><td style='width:52px;'>"+commoditySpecificationStr+"</td>" +
				"<td style='width:51px;'>"+detailSub[i].activePrice+"</td><td style='width:41px;'>"+detailSub[i].commodityNum+"</td><td style='width:52px;'>"+detailSub[i].prodTotalAmt+"</td>" +
			"<td class='barcode'></td></tr>";
			}
			if(i<14){//第一页
				LODOP.ADD_PRINT_BARCODE(i*60+210,560,191,42,"128B",levelCode);
			}if(i>=14){  //大于一页的     
				var pageSize = 18;//每页最多能容纳18个商品列
				var curGoods = i-14; //当前商品数
				var leftGoods = curGoods%pageSize;
				if(leftGoods==0&&curGoods<17){     //第二页单独处理，因为第一页末尾留下的空隙跟其他页不一样
					moreSpace = moreSpace+17;
				}
				if(leftGoods==0&&curGoods>17){ //大于第三页的 页面底部留下的空隙单独计算
					moreSpace = moreSpace+12;
				}
				LODOP.ADD_PRINT_BARCODE(i*60+245+moreSpace,560,191,42,"128B",levelCode);
			}
		}
	var orderPayTotalAmont  = data.orderPayTotalAmont=="0"?"0.0":data.orderPayTotalAmont;
	html = html+"<tr><td colspan='7'style='text-align:right'>优惠金额(元)："+data.discountAmount+"&nbsp;&nbsp;&nbsp;&nbsp;实付金额(元)："+orderPayTotalAmont+"</td></tr>";
	html = html+"<tr><td colspan='7'><table width='100%'><tr><td style='font-weight:bold'>&#9733;退换货地址:</td></tr>";
	if(merchantRejectedAddress!=null){
		var warehouseArea = merchantRejectedAddress.warehouseArea==null?"":merchantRejectedAddress.warehouseArea;
		var warehouseAdress = merchantRejectedAddress.warehouseAdress==null?"":merchantRejectedAddress.warehouseAdress;
		var consigneeName = merchantRejectedAddress.consigneeName==null?"":merchantRejectedAddress.consigneeName;
		var warehousePostcode = merchantRejectedAddress.warehousePostcode==null?"":merchantRejectedAddress.warehousePostcode;
		var consigneePhone = merchantRejectedAddress.consigneePhone==null?"":merchantRejectedAddress.consigneePhone;
		html = html+"<tr><td colspan='7' style='font-size:12px'>"+warehouseArea+warehouseAdress+"</td></tr>";
		html = html+"<tr><td colspan='7' style='font-size:12px'>收货人："+consigneeName+"</td></tr>";
		html = html+"<tr><td colspan='7' style='font-size:12px'>邮编："+warehousePostcode+"&nbsp;&nbsp;收货电话："+consigneePhone+"";
		if(merchantRejectedAddress.consigneeTell!=null){
			html = html+"&nbsp;&nbsp;优购客服电话：&nbsp;"+data.hotLine;
		}
		html = html+"</tr></table></td></tr>";
	}
 	var goods =  detailSub.length;// //detailSub.length;
	//计算页数，将所有的内容看成是很多商品行
	var maxGoods = 9;//第一页最多能容纳7个商品列
	if(goods<=maxGoods){
		for(var i=0,length = maxGoods-goods;i<length;i++){
			html = html +"<tr><td colspan='7' style='height:60px'></td></tr>";
		}
	}else if(goods>14){ //多余一页，计算余下的能分多少页
		var pageSize = 18;//每页最多能容纳18个商品列
		var leftGoods = goods-14; //剩余的商品
		var lastPageGoods = leftGoods%pageSize; //最后一页的商品个数//
		var lastPageMaxGoods = 12;//最后一页最多能容纳的商品列
		if(lastPageGoods>0){
			for(var i=0,length = lastPageMaxGoods-lastPageGoods;i<length;i++){
				html = html +"<tr><td colspan='7' style='height:60px'></td></tr>";
			}
		}
	} 
	html = html +"<tr><td colspan='7' style='font-weight:bold;padding:5px 0px;'>&#9733;温馨提醒：</td></tr>";
	if (data.orderSubNo1 == 'YG') {
		// 整点降价或者手机整点降价
		if (data.orderSubNo2 == 'YG-YGSG' || data.orderSubNo2 == 'YG-SJYPSG') {
			html = html
					+ "<tr><td colspan='7'><table width=\"726\">"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">整点降价的商品不接受退换货服务。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">整点降价的商品不享受三包政策。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">整点降价的商品不享受十天补差价。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">&nbsp;</td><td width=\"716\">更多详情请登录www.yougou.com帮助中心了解。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">&nbsp;</td><td width=\"716\">客服热线："
					+ data.hotLine + "</td></tr>" + "</table></td></tr>";
		} else {
			html = html
					+ "<tr><td colspan='7'><table width=\"726\">"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">试穿鞋类商品时请在鞋底加垫干净的纸张，以免弄脏或磨损鞋底；试穿服装等其它商品时，请勿剪去或损坏吊牌并保持商品洁净无异味。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">自签收之日起10日内，需要退换货时，请登录您的会员账户自主申请，并在3日内快递寄回商品。请勿使用平邮、到付、货运方式寄回。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">退换货时，请确保商品未穿着使用，各包装及附件完整，不影响二次销售。鞋类商品寄回退换时请勿直接在原装鞋盒上缠绕胶带。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">为了退换货更加顺畅快捷，请登录优购“帮助中心”了解注意事项，并务必在购物清单上注明退换原因后随货一起寄回。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">优购只承担订单首次退换货不超过15元的费用，并以礼品卡形式返还到您的会员账户。</td></tr>"
					+ "</table></td></tr>";
		}
	} else if (data.orderSubNo1 == 'TB' || data.orderSubNo1 == 'WBPT') {
		if (data.orderSubNo1 == 'WBPT' && data.orderSubNo2 == 'WBPT-YT') {
			html = html
					+ "<tr><td colspan='7'><table width=\"726\">"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">1.</td><td width=\"716\">试穿时请在鞋子下面加垫干净的纸张，以免鞋底弄脏或磨损；试穿其它商品时，请保持洁净。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">2.</td><td width=\"716\">自商品签收15日内，如商品及包装保持出售时原状且配件齐全，我们将为您提供一次免费退换货服务。具体品类的退换货政策以退换货政策细则为准。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">3.</td><td width=\"716\">退换货时，请确保产品未经穿着使用，各包装完整，不接受平邮、到付、货运，不得在原装鞋盒上直接粘绑胶带。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">4.</td><td width=\"716\">为了退换货过程更加顺畅，请及时了解品类的退换货政策细则并将订单纸随货品一并寄回。</td></tr>"
					+ "</table></td></tr>";
		} else if (data.orderSubNo1 == 'TB'
				|| (data.orderSubNo1 == 'WBPT' && data.orderSubNo2 != 'WBPT-YT'
						&& data.orderSubNo2 != 'WBPT-FKVjia' && data.orderSubNo2 != 'WBPT-FKTM')) {
			html = html
					+ "<tr><td colspan='7'><table width=\"726\">"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">试穿鞋类商品时请在鞋底加垫干净的纸张，以免弄脏或磨损鞋底；试穿服装等其它商品时，请勿剪去或损坏吊牌并保持商品洁净无异味。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">自签收之日起7日内，需要退换货时，请登录订单所属购物网站，自主申请退换货。并在3日内快递寄回商品。请勿使用平邮、到付、货运方式寄回。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">退换货时，请确保商品未穿着使用，各包装及附件完整，不影响二次销售。鞋类商品寄回退换时请勿直接在原装鞋盒上缠绕胶带。</td></tr>"
					+ "<tr><td width=\"10\" style=\"vertical-align: top;\">*</td><td width=\"716\">为了退换货更加顺畅快捷，请务必在此张购物清单上注明退换原因后随货一起寄回。</td></tr>"
					+ "</table></td></tr>";
		}
	}
	html = html +"<tr><td colspan='7' style='text-align:center'><img src='"+basePath+"/yougou/images/print_bar.gif'/></td></tr></table></td></tr>";
	html = html+'</table></div>';
	return html;
};