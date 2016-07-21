package com.belle.yitiansystem.taobao.model.vo;  

public class TaobaoItemCatPropVo {
	private boolean must;
	private String pname;
	private long pid;
	
	public boolean getMust() {
		return must;
	}
	public void setMust(boolean must) {
		this.must = must;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	/** 
	 * 重写equals方法 
	 * @see java.lang.Object#equals(java.lang.Object) 
	 */
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(!(obj instanceof TaobaoItemCatPropVo))
			return false;
		final TaobaoItemCatPropVo vo = (TaobaoItemCatPropVo)obj;
		if(getPid()==vo.getPid())
			return true;
		return false;
	}
	
	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see java.lang.Object#hashCode() 
	 */
	@Override
	public int hashCode() {
		return new Long(getPid()).intValue();
	}
}
