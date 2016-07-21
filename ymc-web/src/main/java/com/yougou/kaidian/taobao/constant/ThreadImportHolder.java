package com.yougou.kaidian.taobao.constant;

import java.util.List;

import com.yougou.kaidian.taobao.vo.TaobaoCsvItemVO;
/**
 * 多线程导入时threadholder 
 * @author le.sm
 *
 */
public class ThreadImportHolder {
	
	private static final ThreadLocal< List<TaobaoCsvItemVO>> voList = new ThreadLocal< List<TaobaoCsvItemVO>>();
	
	public static  List<TaobaoCsvItemVO> getVoList() {
		return voList.get();
	}
	
	public static  void setVoList(TaobaoCsvItemVO vo) {
		voList.get().add(vo);
	}
	
	public static void clear(){
		voList.remove();
	}
}
