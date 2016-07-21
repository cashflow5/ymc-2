//确认对话框
function artconfirm(msg, url) {
    art.dialog( {
	content : msg,
	icon : 'confirm',
	window : top,
	yesFn : function() {
	    this.close();

	    if (url != '') {
		location.href = url;
	    }
	},
	noFn : true
    });
}