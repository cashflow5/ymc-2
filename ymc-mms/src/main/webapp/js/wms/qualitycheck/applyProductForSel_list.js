function selApplyProduct(realApplyNo, realProductNo) {
	if(!window.confirm('您确认无误，并继续执行此操作吗？')){
		return;
	}	
	dg.curWin.document.getElementById("realApplyNo").value = realApplyNo;
	dg.curWin.document.getElementById("realProductNo").value = realProductNo;
	$("#doExpSaveBtn").attr('disabled','disabled');
	dg.curWin.doSaveCheckItem();
	closewindow();
}
