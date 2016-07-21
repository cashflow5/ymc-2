jQuery.fn.extend({
	dateDisplay : function() {
		$(this).addClass('calendar-pic');
		$(this).datepicker({
		    yearRange: '2010:2015', //取值范围.
		    showOn: 'focus', //输入框和图片按钮都可以使用日历控件。
		    //buttonImage: '/images/calendar.gif', //日历控件的按钮
		    buttonImageOnly: true,
		    showButtonPanel: true
		});
	},

	selectAll : function(obj) {
		$(this).click(function() {
			if ($(this).attr('checked') == true) { 
				  $('#'+obj).find('input[type="checkbox"]').each(function() {
					  $(this).attr('checked', true);
				  });
			} else { 
				$('#'+obj).find('input[type="checkbox"]').each(function() {
					if($(this).attr('disabled')==false) {
						$(this).attr('checked', false);
					}
				 });
			}
		});
	},
	
	display : function(obj) {
		$(this).toggle(function() {
				$('#'+obj).parents('table').css({border:'1px solid #e6e6e6', background: '#eeeeee'});
				$('#'+obj).show();
			}, function() {
				$('#'+obj).parents('table').css({border:'0px solid #e6e6e6', background: '#ffffff'});
				$('#'+obj).hide();
			}
		);
	},
	
	displayOfSearch : function(messageOfShow, messageOfHidden,inputIndex,selectIndex) {
		var more_search = $('.search_list ul li:gt(1):not(:last)');
		
		var showFlag = false;
		$('.search_list ul li input' + inputIndex).each(function() {
			var value = $(this).val();
			if($.trim(value)!=""&&($.trim(value)!="999")) {
				//$('.search_list ul li:gt(1):not(:last)').show();
				showFlag = true;
			}
		});
		$('.search_list ul li select'+ selectIndex).each(function() {
			var value = $(this).val();
			if($.trim(value)!=""&&($.trim(value)!="999")) {
				//$('.search_list ul li:gt(1):not(:last)').show();
				showFlag = true;
			}
		});
		
		if(!showFlag) {
			more_search.hide();
		}
		
		$(this).toggle(function() {
			reinitifH();
			more_search.show();
			$(this).text(messageOfHidden);
		}, function() {
			reinitifH();
			more_search.hide();
			$(this).text(messageOfShow);
		});
	},
	
	 fadeAndWait : function(msg) {
		$(this).click(function() {
			$("body").append("<div class='backgroundDiv'></div>");
			 $(".backgroundDiv").css({"opacity":"0.5"}).fadeIn('normal');
			 var scrollWidth = document.documentElement.clientWidth;
			 var scrollHeight = document.documentElement.clientHeight;
			 $("body").append("<div class='info'><div class='waiting'>" + msg + "</div></div>");
			 var divWidth = $(".info").width();
			 var divHeight = $(".info").height();
			 var divLeft = scrollWidth/2-divWidth/2;
			 var divTop = scrollHeight/2-divHeight/2;
			 if(divTop>280) {
				 divTop = 280;
			 }
			 $(".info").css({"position":"absolute","top":divTop,"left":divLeft}).fadeIn('normal');
		});
	}
});

$.fn.numeral = function() {
    $(this).css("ime-mode", "disabled");
    this.bind("keypress",function() {
        if (event.keyCode == 46) {
            if (this.value.indexOf(".") != -1) {
                return false;
            }
        } else {
            return event.keyCode >= 46 && event.keyCode <= 57;
        }
    });
    this.bind("blur", function() {
        if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
            this.value = this.value.substr(0, this.value.length - 1);
        } else if (isNaN(this.value)) {
            this.value = 0;
        }
    });
    this.bind("paste", function() {
        var s = clipboardData.getData('text');
        if (!/\D/.test(s));
        value = s.replace(/^0*/, '');
        return false;
    });
    this.bind("dragenter", function() {
        return false;
    });
    this.bind("keyup", function() {
    if (/(^0+)/.test(this.value)) {
        this.value = this.value.replace(/^0*/, '');
        } 
    });
};

//function exportExcel(obj) {//整个表格拷贝到EXCEL中  
//	var curTbl = document.getElementById(obj);  
//	var oXL = new ActiveXObject("Excel.Application");  
//	//创建AX对象excel  
//	var oWB = oXL.Workbooks.Add();  
//	//获取workbook对象  
//	var oSheet = oWB.ActiveSheet;  
//	//激活当前sheet  
//	var sel = document.body.createTextRange();  
//	sel.moveToElementText(curTbl);  
//	//把表格中的内容移到TextRange中  
//	sel.select();  
//	//全选TextRange中内容  
//	sel.execCommand("Copy");  
//	//复制TextRange中内容   
//	oSheet.Paste();  
//	//粘贴到活动的EXCEL中        
//	oXL.Visible = true;  
//	//设置excel可见属性  
//} 

function exportExcel(basepath, title, contentId) {
	$('#exportExcelDiv').remove();
	var content = $('#'+contentId).html();
	$('#'+contentId).append('<div id="exportExcelDiv"></div>');
	var formInfors = '<form id="exportExcelForm" action="'+basepath+'/finance/systemmanager/index/exportExecel.sc" method="post">'
	+ '<input id="exportExcelTitle" name="title" type="hidden"><input id="exportExcelContent" name="content" type="hidden">';					
	+ '</form>';
	$('#exportExcelDiv').html(formInfors);
	$('#exportExcelTitle').val(encodeURI(title));
	$('#exportExcelContent').val(content);
	$('#exportExcelForm').submit();
}

function printPage(obj) {
    var body = window.document.body.innerHTML;
    var printArea = window.document.getElementById(obj).innerHTML;
    window.document.body.innerHTML = printArea;
    window.print();
    window.document.body.innerHTML = body;
}


function isNull(val) {
	if (val == null) { return true; }
    if (val.length == 0) return true;
    return false;
}

function isBlank(val) {
	if (val == null) { 
		return true; 
	}
    for (var i=0; i < val.length; i++) {
    	if ((val.charAt(i) != ' ') && (val.charAt(i) != "\t") && (val.charAt(i) != "\n")) {
    		return false; 
    	}
    }
    return true;
}

function isDigit(num) {
    var string="1234567890";
    if (string.indexOf(num) != -1) {
        return true;
    }
    return false;
}

function isInteger(val) {
    for (var i=0; i < val.length; i++) {
    	if (!isDigit(val.charAt(i))) { return false; }
    }
    return true;
}

function isNumeric(val) {
    var dp = false;
    for (var i=0; i < val.length; i++) {
        if (!isDigit(val.charAt(i))) {
            if (val.charAt(i) == '.') {
           	 if (dp == true) { return false; } // already saw a decimal point
            	else { dp = true; }
            }
            else {
                return false;
            }
            if (val.charAt(i) == '-'){
				return false;
            }
        }
    }
    return true;
}

