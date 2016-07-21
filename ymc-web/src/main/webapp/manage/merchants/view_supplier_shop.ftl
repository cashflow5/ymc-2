<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/merchant/merchans_center.css"/>
 
  <script>
	var basePath = '${BasePath}';
  </script>
  <title>优购商城--商家后台</title>
  </head>
<body>
	 <div id="newmain" class="main_bd fr">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 设置 &gt; 店铺信息</p>
                    <div class="tab_panel relative">
                        <ul class="tab">
                            <li onclick="location.href='${BasePath}/merchants/login/to_MerchantsUser.sc'"><span>基本信息</span></li>
                            <li   class="curr"><span>店铺信息</span></li>
                            <li onclick="location.href='${BasePath}/merchants/login/to_view_supplier_contract.sc'"><span>合同信息</span></li>
                        </ul>
                        <div class="tab_content">
                            <div class="info-box">
                                <h3>店铺负责人信息</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(contactsFormVo.chiefContact)?default('')}</li>
                                    <li><span class="stitle">手机：</span>${(contactsFormVo.chiefMobilePhone)?default('')}</li>
                                    <li><span class="stitle">电子邮箱：</span>${(contactsFormVo.chiefEmail)?default('')}</li>
                                    <li><span class="stitle">座机：</span>${(contactsFormVo.chiefTelePhone)?default('')}</li>
                                    <li><span class="stitle">QQ号码：</span>${(contactsFormVo.chiefQQ)?default('')}</li>
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>售后退换货地址</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(rejectedAddress.consigneeName)?default('')}</li>
                                    <li><span class="stitle">收货人电话：</span>${(rejectedAddress.consigneeTell)?default('')}</li>
                                    <li><span class="stitle">收货人地区：</span>${(rejectedAddress.warehouseArea)?default('')}</li>
                                    <li><span class="stitle">邮编号码：</span>${(rejectedAddress.warehousePostcode)?default('')}</li>
                                    <li><span class="stitle">详细地址：</span>${(rejectedAddress.warehouseAdress)?default('')}</li>
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>业务联系人</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(contactsFormVo.businessContact)?default('')}</li>
                                    <li><span class="stitle">手机：</span>${(contactsFormVo.businessMobilePhone)?default('')}</li>
                                    <li><span class="stitle">电子邮箱：</span>${(contactsFormVo.businessEmail)?default('')}</li>
                                    <li><span class="stitle">座机：</span>${(contactsFormVo.businessTelePhone)?default('')}</li>
                                    <li><span class="stitle">QQ号码：</span>${(contactsFormVo.businessQQ)?default('')}</li>
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>售后联系人</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(contactsFormVo.afterSaleContact)?default('')}</li>
                                    <li><span class="stitle">手机：</span>${(contactsFormVo.afterSaleMobilePhone)?default('')}</li>
                                    <li><span class="stitle">电子邮箱：</span>${(contactsFormVo.afterSaleEmail)?default('')}</li>
                                    <li><span class="stitle">座机：</span>${(contactsFormVo.afterSaleTelePhone)?default('')}</li>
                                    <li><span class="stitle">QQ号码：</span>${(contactsFormVo.afterSaleQQ)?default('')}</li>
                                </ul>
                            </div>
                           
                            <div class="info-box">
                                <h3>财务联系人</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(contactsFormVo.financeContact)?default('')}</li>
                                    <li><span class="stitle">手机：</span>${(contactsFormVo.financeMobilePhone)?default('')}</li>
                                    <li><span class="stitle">电子邮箱：</span>${(contactsFormVo.financeEmail)?default('')}</li>
                                    <li><span class="stitle">座机：</span>${(contactsFormVo.financeTelePhone)?default('')}</li>
                                    <li><span class="stitle">QQ号码：</span>${(contactsFormVo.financeQQ)?default('')}</li>
                                </ul>
                            </div>
                            
                             <div class="info-box">
                                <h3>技术联系人</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">姓名：</span>${(contactsFormVo.techContact)?default('')}</li>
                                    <li><span class="stitle">手机：</span>${(contactsFormVo.techMobilePhone)?default('')}</li>
                                    <li><span class="stitle">电子邮箱：</span>${(contactsFormVo.techEmail)?default('')}</li>
                                    <li><span class="stitle">座机：</span>${(contactsFormVo.techTelePhone)?default('')}</li>
                                    <li><span class="stitle">QQ号码：</span>${(contactsFormVo.techQQ)?default('')}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>
