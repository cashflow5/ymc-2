package com.yougou.kaidian.stock.model.vo;

import java.util.ArrayList;
import java.util.List;

public class KeyValueHelperVo {
	private List<KeyValueVo> keyValueList = new ArrayList<KeyValueVo>();

	public KeyValueHelperVo(List<Object[]> list) {
		for (Object[] objs : list) {
			KeyValueVo keyValueVo = new KeyValueVo();
			keyValueVo.setKey(objs[0].toString());
			keyValueVo.setValue(objs[1].toString());
			keyValueList.add(keyValueVo);
		}
	}

	public List<KeyValueVo> getKeyValueList() {
		return keyValueList;
	}

	public void setKeyValueList(List<KeyValueVo> keyValueList) {
		this.keyValueList = keyValueList;
	}
}
