package com.belle.yitiansystem.merchant.model.entity;

import java.util.Comparator;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
public class DateComparator implements Comparator<SupplierVo> {

	@Override
	public int compare(SupplierVo supplierVo1, SupplierVo supplierVo2) {
		if(null==supplierVo1.getUpdateDate()){
			return -1;
		}
		if( supplierVo1.getUpdateDate().after(supplierVo2.getUpdateDate() ) ){
			return -1;
		}else if ( supplierVo1.getUpdateDate().equals(supplierVo2.getUpdateDate() ) ){
			return 0;
		}else{
			return 1;
		}
	}

}
