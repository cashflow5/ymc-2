$(function(){

	
	 $('#orderCreateTimeStart').calendar({
		 format: 'yyyy-MM-dd HH:mm:ss'
	  });
    
    $('#orderCreateTimeEnd').calendar({
        format: 'yyyy-MM-dd HH:mm:ss'
    });
    $('#invoiceCreateTimeStart').calendar({
        format: 'yyyy-MM-dd HH:mm:ss'
    });
    $('#invoiceCreateTimeEnd').calendar({
        format: 'yyyy-MM-dd HH:mm:ss'
    });
	
    $('#createTimeStart').calendar({
        format: 'yyyy-MM-dd'
    });
    $('#createTimeEnd').calendar({
        format: 'yyyy-MM-dd'
    });

//    $('.timeStart').each(function(index, el) {
//        var number=parseInt(index+1);
//       $('#createTimeStart_'+number).calendar({
//            format: 'yyyy-MM-dd'
//        }); 
//        $('#createTimeEnd_'+number).calendar({
//            format: 'yyyy-MM-dd'
//        });
//    });

    // 信息填写页面，下一步按钮效果
    $('.btn-next').click(function(){
        var $this=$(this),
            next_parent=$(this).parent().parent().parent();
            next_parent.next().removeClass('hide').siblings().addClass('hide');
    })

    //上一步按钮
    $('.btn-prev').click(function(){
        var $this=$(this),
            next_parent=$(this).parent().parent().parent();
            next_parent.prev().removeClass('hide').siblings().addClass('hide');
    })

    
    removeDefaultVal('.supplier-query-text');
    removeDefaultVal('.supplier-query-textarea');
})

//去掉初始值函数
function removeDefaultVal(object){
    $(object).focus(function(event) {
        var $this=$(this),
            vals=$this.val(),
            default_value=$this[0].defaultValue;//获取input的默认值
        if(vals==default_value){
            $this.val("");
            $this.removeClass('default-val');
        }
    }).blur(function(event) {
        var $this=$(this),
            default_value=$this[0].defaultValue;
        if(!$this.val()){
            $this.val(default_value);
            $this.addClass('default-val');
        }
    });
}
