G.Index = {
    bindChart: function(id, title, categories, valueSuffix, series) {
        $(id).highcharts({
            title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                categories: categories
            },
            yAxis: {
                title: {
                    text: null
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
               // min: 0
            },
            tooltip: {
                valueSuffix: valueSuffix
            },
            legend: {
                layout: 'horizontal',
                align: 'right',
                verticalAlign: 'top',
                borderWidth: 0
            },
            credits:{
            	enabled:false,
            	href:'http://www.yougou.com',
            	text:'www.yougou.com'
            },
            exporting:{
            	enabled:false
            },
            series: series
        });
    },
    dataTime:function(id,id_val,opt){
        $(id).focus(function(event) {
            var $this=$(this),
                id_str=$this.attr("id"),
                arr=id_str.split('-');
            if($this.hasClass('starttime')){
                WdatePicker($.extend({
                    onpicked:function(){//设置联动开始时间和结束时间联动
                        $dp.$('endtime-'+arr[1]).focus();
                    },
                    //maxDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\')}'//设置开始的最大值不能超过结束时间选择的值
                    maxDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\')||\'%y-%M-{%d-1}\'}',
                	minDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\',{M:-6})&&\'2015-08-13\'}'
                },opt));
            }else{
                WdatePicker($.extend({
                	maxDate:'#F{\'%y-%M-{%d-1}\'||$dp.$D(\'starttime-'+arr[1]+'\',{M:6})}',
                    minDate:'#F{$dp.$D(\'starttime-'+arr[1]+'\')}'
                },opt));               
            }
        });
    },
    tableClick:function(data,tbObj){//字表格展示和收缩，动态添加
        var table_click= $('.table-click');
        table_click.click(function(event) {
            var id,child,type,code,visitors,views,details,conversion,paid,price,evaluation,Collection,
                arr=[],trcontent,
                $this = $(this),
                i = $this.find('i'),
                span_id=$this.attr("id"),
                table_parent = $this.parent().parent('.table-parent'),
                tbody=table_parent.parent('tbody'),
                table_child = table_parent.siblings('.table-child');
            //判断是展开还是收缩的
            if (!i.hasClass('up')) {
                i.html('&#xe604;');
                i.addClass('up');
                $(data).each(function(index, el) {
                    id=this.id,
                    child=this.child;
                    if(id==span_id){//判断点击的id和数据的id是否一致，给不同的id添加相应的类别
                        $(child).each(function(index, el) {//把对应的id的子元素循环出来
                            type=this.type;
                            code=this.code;
                            visitors=this.visitors;
                            views=this.views;
                            details=this.details;
                            conversion=this.conversion;
                            paid=this.paid;
                            price=this.price;
                            evaluation=this.evaluation;
                            Collection=this.Collection;
                            trcontent = '<tr class="table-child"><td>'+type+'</td><td>'+code+'</td><td>'+visitors+'</td><td>'+views+'</td><td>'+details+'</td><td>'+conversion+'</td><td>'+paid+'</td><td>'+price+'</td><td>'+evaluation+'</td><td>'+Collection+'</td></tr>';
                            arr.push(trcontent);
                        });
                        if(i.hasClass('first')){//判断是否是第一次点击
                             tbody.append(arr.join(""));
                        }else{
                            table_child.show();
                        }   
                    }
                });
            } else {
                i.html('&#xe602;');
                i.removeClass('up');
                table_child.hide();
            }
            i.removeClass('first');
        });
    },
    senior:function(){//点击高级功能收缩展开实现
        var senior=$('.yg-senior'),
            index,display,
            yg_query=$('.yg-query'),
            no_btn=$('.no-btn');
        senior.click(function(event) {
           index=senior.index(this);
           display=yg_query.eq(index).css('display');
           if(display=="none"){
            yg_query.eq(index).show();
           }else{
            yg_query.eq(index).hide();
           }
        });
        no_btn.click(function(event) {
            $(this).parent().parent().parent().hide();
        });
    },
    /*search:function(){//搜索框，按钮，聚焦的时候字体消失
        var yg_search=$('.yg-search-input');
        yg_search.focus(function(){
           if( $(this).val()=="请输入关键词"){
                $(this).val("");
           }
        }).blur(function(event) {
            if($(this).val()==""){
                $(this).val("请输入关键词");
            }
        });

    },*/
    search:function(defaultVal){//搜索框，按钮，聚焦的时候字体消失,defaultVal:输入框默认显示提示词
        var yg_search=$('.yg-search-input');
        if(!defaultVal){
        	defaultVal = "请输入关键词";
        }
        return defaultVal;
    },
    quotaClick:function(){//指标弹窗页面click函数
        //data通用指标
       /* var data_general=[
                {name:"收订件数"},{name:"支付件数"},{name:"发货件数"},{name:"收订金额"},{name:"支付金额"},{name:"发货金额"},
                {name:"收订折扣"},{name:"支付折扣"},{name:"发货折扣"},{name:"单品浏览量"},{name:"单品访次"},{name:"单品转换率"},
                {name:"退货拒收数"},{name:"退货拒收额"},{name:"退货拒收率"},{name:"库存"},{name:"发货率"},{name:"新上架商品数"}
            ];*/

        //data商标指标
       /* var data_brand=[
                {name:"一级分类"},{name:"二级分类"},{name:"三级分类"},{name:"年份"},{name:"季节"},{name:"商品状态"},
                {name:"货号"},{name:"款色编码"},{name:"商品编号"},{name:"商品名称"},{name:"市场价"},{name:"优购价"},
                {name:"收订均价"},{name:"支付均价"},{name:"发货均价"},{name:"上架天数"},{name:"收藏人数"},{name:"评论次数"},
                {name:"加车次数"},{name:"持续零收订天数"}
            ];*/

    	//全选功能
        $('.quota-box').on("click",".quota-all-btn",function() {
        	// console.info("hhh");
            var all_checked=$(this).prop("checked"),
                quota_general=$(this).parents("div.quota-box").find('ul.quota-list').find('.quota-general');
            if(all_checked){
                quota_general.prop("checked",true);
            }else{
                quota_general.prop("checked",false);
            }

            //获取选中的长度,设置选中数量
            var quota_siblings=$(this).parents('div.quota-box'),
                length_cheked=quota_siblings.find('.quota-general:checked').length;
                quota_siblings.find('#checkedNum').html(length_cheked);
        });

        //单个checkbox选中，设置选中数量
        $('.quota-box').on("click",".quota-general",function(){
            var quota_box=$(this).parents('.quota-box'),
                legenth_checked=quota_box.find('.quota-general:checked').length;
                quota_box.find('#checkedNum').html(legenth_checked);
        });

       //点击指标显示指标框
       $('.btn-quota').click(function(){
        var quota_index=$('.btn-quota').index(this),
            quota_box=$(this).siblings('.quota-box'),
            quota_general=quota_box.find('.quota-general'),
            quota_brand=quota_box.find('.quota-brand');
         if(quota_box.hasClass('hide')){
            quota_box.removeClass('hide');
         }else{
            quota_box.addClass('hide');
         }
        /* if($(this).hasClass('first')){
            $(this).removeClass('first');
            //通用指标
           // G.Index.quotaAddCont(data_general,quota_general,quota_index);

            //商标指标
           // G.Index.quotaAddCont(data_brand,quota_brand,quota_index);
         }*/
        
       });

       //取消按钮点击，关闭弹窗层
       $('.quota-box').on('click','.quota-cancel',function(){
            $(this).parent().parent().addClass('hide');
       });
    
    },
    bindChartByDate:function(id,yAxis,series){  	
    	$(id).highcharts({
    		title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                type: 'datetime',
                min:null,
                labels: {    
                   formatter: function () {  
                      return Highcharts.dateFormat('%m/%d', this.value);  
                  }  
                },
                tickInterval: null
            },
            yAxis: yAxis,
            tooltip: {  
               xDateFormat: '%Y-%m-%d',		
               shared: true
             },
             credits:{
             	enabled:false,
             	href:'http://www.yougou.com',
             	text:'www.yougou.com'
             },
             exporting:{
             	enabled:false
             },
             legend: {
                 layout: 'horizontal',
                 align: 'right',
                 verticalAlign: 'top',
                 borderWidth: 0
             },
            series:series
      
        });
    	
    }
    
    
    /*quotaAddCont:function(data_quota,ulobject,suffix){//指标弹窗页面，内容添加
        //内容添加
       var arr=[];
       $(data_quota).each(function(index){
            var name=this.name,
                str=ulobject.attr('class'),
                arrs=[];
                arrs=str.split(' ');
                $li='<li><input type="checkbox" class="quota-general" id="g-'+arrs[1]+'-'+index+suffix+'"/><label for="g-'+arrs[1]+'-'+index+suffix+'">'+name+'</label></li>';
            arr.push($li);
       });
       ulobject.append(arr);
    }*/
   
};

