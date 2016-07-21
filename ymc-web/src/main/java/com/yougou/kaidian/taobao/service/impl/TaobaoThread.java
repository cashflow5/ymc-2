package com.yougou.kaidian.taobao.service.impl;


public class TaobaoThread {
	
	private int index;
	
	private String[] csvArray;
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String[] getCsvArray() {
		return csvArray;
	}

	public void setCsvArray(String[] csvArray) {
		this.csvArray = csvArray;
	}

	public TaobaoThread(int index, String[] csvArray) {
		super();
		this.index = index;
		this.csvArray = csvArray;
	}

}