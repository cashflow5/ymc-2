package com.yougou.kaidian.commodity.model.vo;

public class ExcelErrorVo extends ErrorVo {
	
	/** 所在行 */
	private int row;
	
	/** 所在列 */
	private int column;
	
	public ExcelErrorVo(String filed, String errMsg) {
		super(filed, errMsg);
	}

	public ExcelErrorVo(String filed, String errMsg, int row, int column) {
		super(filed, errMsg);
		this.column = column;
		this.row = row;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
