//子分类html
var newSecondHtml;
//父分类html
var newFirstHtml;
//父分类无数据时显示
var headFirstHtml;
//子分类无数据时显示
var headSecondHtml;
//新增的分类选项值
var newAssrotOption;
//动态显示与隐藏div的时间
var speed = 500;

function initHtml(){
	newSecondHtml = "<ul class='child-ul' name='newAdd' role=''>"
		+ "<li class='li-1'><img src='"+ basepath +"/images/yitiansystem/class-join.gif' /><input name='textfield' type='text' style='width:155px;'/></li>"
		+ "<li class='li-2'><a href='#' class='edit-pic-a'><img src='"+ basepath +"/images/yitiansystem/edit-pic-1.gif'/> 添加图片</a></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-3'><a href='#'><img src='"+ basepath +"/images/yitiansystem/up-2.gif' /></a>&nbsp;&nbsp;<a href='#'><img src='"+ basepath +"/images/yitiansystem/down-1.gif'/></a>&nbsp;&nbsp;<a href='#'  class='del-child-a'><img src='"+ basepath +"/images/yitiansystem/del-class.gif' /></a></li>"
		+ "<li class='li-4'></li></ul>";
	
	headSecondHtml = "<ul class='child-ul' name='newAdd' role=''>"
		+ "<li class='li-1'><img src='"+ basepath +"/images/yitiansystem/class-join.gif' /><input name='textfield' type='text' style='width:155px;' /></li>"
		+ "<li class='li-2'><a href='#' class='edit-pic-a'><img src='"+ basepath +"/images/yitiansystem/edit-pic-1.gif'/> 添加图片</a></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-3'><a href='#'><img src='"+ basepath +"/images/yitiansystem/up-1.gif' /></a>&nbsp;&nbsp;<a href='#'><img src='"+ basepath +"/images/yitiansystem/down-1.gif' /></a>&nbsp;&nbsp;<a href='#'  class='del-child-a'><img src='"+ basepath +"/images/yitiansystem/del-class.gif' /></a></li>"
		+ "<li class='li-4'></li></ul>";
	
	newFirstHtml = "<tr class='tr'><td colspan='7' class='td0 td3'><ul class='store-td-ul' name='newAdd' role=''>"
		+ "<li class='li-1'><input name='textfield'  type='text' size='220' /></li>"
		+ "<li class='li-2'><a href='#' class='edit-pic-a'><img src='"+ basepath +"/images/yitiansystem/edit-pic-1.gif'/> 添加图片</a></li>"
		+ "<li class='li-2'><a href='#' onclick='addAssort(this,2);'><img src='"+ basepath +"/images/yitiansystem/add-small-class.gif' /> 添加子分类</a></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'><input type='checkbox' name='checkbox' onclick='showChildAssort(this)'/></li>"
		+ "<li class='li-3'><a href='#'><img src='"+ basepath +"/images/yitiansystem/up-2.gif' /></a>&nbsp;&nbsp;<a href='#'><img src='"+ basepath +"/images/yitiansystem/down-1.gif' /></a>&nbsp;&nbsp;<a href='#' class='del-farther-a'><img src='"+ basepath +"/images/yitiansystem/del-class.gif' /></a></li>"
		+ "<li class='li-4'><select name='selectAssort' onchange='moveAssortOption(this)'><option value='-1'>-请选择-</option>"+ newAssrotOption +"</select></li>"
		+ "</ul></td></tr>";
	
	headFirstHtml = "<tr class='tr'><td colspan='7' class='td0 td3'><ul class='store-td-ul' name='newAdd' role=''>"
		+ "<li class='li-1'><input name='textfield' type='text' size='22' /></li>"
		+ "<li class='li-2'><a href='#' class='edit-pic-a'><img src='"+ basepath +"/images/yitiansystem/edit-pic-1.gif'/> 添加图片</a></li>"
		+ "<li class='li-2'><a href='#' onclick='addAssort(this,2);'><img src='"+ basepath +"/images/yitiansystem/add-small-class.gif' /> 添加子分类</a></li>"
		+ "<li class='li-2'></li>"
		+ "<li class='li-2'><input type='checkbox' name='checkbox' onclick='showChildAssort(this)'/></li>"
		+ "<li class='li-3'><a href='#'><img src='"+ basepath +"/images/yitiansystem/up-1.gif' /></a>&nbsp;&nbsp;<a href='#'><img src='"+ basepath +"/images/yitiansystem/down-1.gif' /></a>&nbsp;&nbsp;<a href='#' class='del-farther-a'><img src='"+ basepath +"/images/yitiansystem/del-class.gif' /></a></li>"
		+ "<li class='li-4'><select name='selectAssort' onchange='moveAssortOption(this)'><option value='-1'>-请选择-</option>"+ newAssrotOption +"</select></li>"
		+ "</ul></td></tr>";
	
	newAssrotOption = '';
}

//添加父子分类
function addAssort(e,level){
	var count = 0; 
	$('td ul:first-child').each(function(){
		count++; 
		var assortName = $(this).find('input').val();
		if($.trim(assortName) != ''){
			newAssrotOption += '<option value='+ count +'>'+ assortName +'</option>';	
		}
	})
	initHtml();
	
	//相对于当前分类的最后一个（tr或者ul）下标
	var currIndex = $('#tbody tr').length -1;
	if(level == 1){
		if(currIndex < 0){
			$('#tbody').append(headFirstHtml);
		}else{
			$('#tbody').find('tr').eq(currIndex).find('img[src$=down-1.gif]').eq(0).attr('src',basepath +"/images/yitiansystem/down-2.gif");
			$('#tbody').append(newFirstHtml);
		}
	}else{
		currIndex = $(e).parents('td').find('ul').length - 1;
		//如果是收缩状态则展开它
		var isChecked = $(e).parents('ul').find('input[name="checkbox"]').attr('checked');
		if(isChecked){
			$(e).parents('ul').find('input[name="checkbox"]').attr('checked',false);
			$(e).parents('td').find('ul:gt(0)').each(function(){
				$(this).show();
			})	
		}
		if(currIndex <= 0){
			$(e).parents('td').append(headSecondHtml);
		}else{
			$(e).parents('td').find('ul').eq(currIndex).find('img[src$=down-1.gif]').eq(0).attr('src',basepath +"/images/yitiansystem/down-2.gif");
			$(e).parents('td').append(newSecondHtml);
		}
	}
}

//初始化下拉框的分类列表
function initSelectAssort() {
	$('select[name="selectAssort"]').each(function(){
		$(this).empty();
		$(this).append('<option value="-1">-请选择-</option>');
		var currSelect = $(this);
		var currAssortName = $(this).parents('ul').find('input').val();
		var count = 0;
		$('td ul:first-child').each(function(){
			var assortName = $(this).find('input').val();
			if($.trim(assortName) != '' && assortName == currAssortName){
				currSelect.append('<optgroup label="'+ assortName +'" style="color:gray"></optgroup>');	
			}else if($.trim(assortName) != ''){
				currSelect.append('<option value='+ count +'>'+ assortName +'</option>');	
			}
			count ++;
		})
	})
}

//子分类的展开与收缩
function showChildAssort(e){
	var isChecked = $(e).attr('checked');
	if(isChecked){
		$(e).parents('td').find('ul:gt(0)').each(function(){
			$(this).hide();
		})
	}else{
		$(e).parents('td').find('ul:gt(0)').each(function(){
			$(this).show();
		})
	}
}

//选择分类的值改变事件
function moveAssortOption(e){
	//当前tr的总行数数
	var tr_length = $(e).parents('tbody').find('tr').length;
	//当前所在的tr位于tbody的下标（从0开始）
	
	var tr_indexOf_tbody = $(e).parents('tbody').find('tr').index($(e).parents('tr'));
	//要移动的tr位置下标（从0开始）
	var target_tr_body = parseInt($(e).val());
	//清除被选中的下拉值，选中默认值
	$(e).val('-1');
	
	if(target_tr_body == tr_indexOf_tbody){
		return;//IE下禁选无效
	}else if(target_tr_body == tr_indexOf_tbody + 1){
		doDownOrUpAction('down',e,tr_length,tr_indexOf_tbody + 1,'first');
		//重新初始化下拉值
		initSelectAssort();
	}else if(target_tr_body == tr_indexOf_tbody - 1){
		return;//此情况不需要移动 
		//doDownOrUpAction('up',e,tr_length,tr_indexOf_tbody,'first');
	}else{
		moveDownAction(e, tr_indexOf_tbody, target_tr_body, tr_length);
	}
}

//页面初始化事件
var basepath = '';//基路径
$(document).ready(function(){
	basepath = $('#basepath').val();
	inputBlur();
	picDownAndUpBindClick();
	picDeleteBindClick();
	initSelectAssort();
	initExtendAssort();
	inputPicUrl();
});

//初始化展开子分类的展开与收缩
function initExtendAssort(){
	$('input[name="checkbox"]:checked').each(function(){
		$(this).parents('td').find('ul:gt(0)').each(function(){
			$(this).hide();
		})
	})
}

//给父层节点的input添加失焦事件
function inputBlur(){
	$('td ul:first-child').find('input').live('blur' ,function(){
		if($.trim($(this).val()) != ''){
			initSelectAssort();
		}
	})
}

//当前编辑或添加图片的所在ul行的jquery对象
var currEditPicUl = null;
//给分类添加图片路径
function inputPicUrl(){
	$(".edit-pic-a").live('click', function(event){  
		event.stopPropagation();  
		var offset = $(event.target).offset();  
		$(".store-edit-pic").css({top:offset.top+$(event.target).height()+"px",left:offset.left});  
		var val = $(event.target).parents('ul').attr('role') == '' ? 'http://' : $(event.target).parents('ul').attr('role');
		$(".store-edit-pic input[name=picUrl]").val(val);
		$(".store-edit-pic").show(speed); 
		currEditPicUl = $(this).parents('ul');
	}); 
	$('.store-edit-pic').click(function(event){
		event.stopPropagation();
	});
	//若有红色提示样式，则去掉该样式
	$(parent.document).click(function(event){
		if($(".store-edit-pic input").hasClass('store-input-err')){
			$(".store-edit-pic input").removeClass('store-input-err');
		}
		$(".store-edit-pic").hide(speed);
	}); 
}

//图片设置：如果设置成功则把增加图片改为编辑图片，并改变图片的样式
function checkPicUrl(e){
	var picUrl = $(e).prev().val();
	var patt = /^http:\/\/[a-zA-Z0-9]+\.[a-zA-Z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
	if($.trim(picUrl) != '' && $.trim(picUrl) != 'http://'){
		if(patt.test(picUrl)){
			currEditPicUl.attr('role',$.trim(picUrl));
			currEditPicUl.find('a[class=edit-pic-a]').html('<img src="'+ basepath +'/images/yitiansystem/edit-pic-2.gif"/>&nbsp;&nbsp;编辑图片');
			$(e).prev().val('');
			$(".store-edit-pic").hide(speed);
		}else{
			$(e).prev().addClass('store-input-err');
		}
	}else{
		if(currEditPicUl.attr('role') != ''){
			currEditPicUl.find('a[class=edit-pic-a]').html('<img src="'+ basepath +'/images/yitiansystem/edit-pic-1.gif"/>&nbsp;&nbsp;添加图片');
			currEditPicUl.attr('role','');
		}
		$(".store-edit-pic").hide(speed);
	}
}

//添加图片向上向下移动的点击事件 
function picDownAndUpBindClick(){
	$('img[src$=-2.gif]').live('click' ,function(){
		
		//当前图片所在的ul相对于td的下标(若为0，则为第一层节点，否则为第二层节点)从1开始
		var ul_indexOf_td = $(this).parents('td').find('ul').index($(this).parents('ul'));
		//当前父的孩子个数
		var ul_length = $(this).parents('td').find('ul').length - 1;
		//第一层相对下标,从1开始
		var tr_indexOf_tbody = $(this).parents('tbody').find('tr').index($(this).parents('tr')) + 1; 
		//相对于tbody,tr的总数	
		var tr_length = $(this).parents('tbody').find('tr').length;
		
		//点击向上或向下的图片
		var action = 'down';
		if($(this).attr('src').indexOf(action) < 0){
			action = 'up';
		}
		if(ul_indexOf_td == 0){
			doDownOrUpAction(action,this,tr_length,tr_indexOf_tbody,'first');
			//重新初始化下拉值
			initSelectAssort();
		}else if(ul_indexOf_td > 0){
			doDownOrUpAction(action,this,ul_length,ul_indexOf_td,'second');
		}
	})
}

//添加图片的删除事件
function picDeleteBindClick(){
	
	$('img[src$=del-class.gif]').live('click' ,function(){
		
		var ulName = $(this).parents('ul').attr('name');
		var ul_length = $(this).parents('td').find('ul').length;
		var tr_length = $(this).parents('tbody').find('tr').length;
		var ul_indexOf_td = $(this).parents('td').find('ul').index($(this).parents('ul'));
		var tr_indexOf_tbody = $(this).parents('tbody').find('tr').index($(this).parents('tr')) + 1;
		
		if('newAdd' == ulName){
			if(ul_length == 1){
				doDeleteAction(this, tr_length, tr_indexOf_tbody, 'first');
			}else if(ul_indexOf_td == 0){
				alert('该分类下有子分类，不能直接删除');
			}else{
				doDeleteAction(this, ul_length, ul_indexOf_td, 'second');
			}
		}else{
			var typeId = $(this).parents('ul').attr('id');
			if(ul_length == 1){
				if(hasGoodsByTypeId(typeId)){
					var result = confirm("该分类下有商品，如果删除该分类，商品会移除该分类，确认删除吗？");
					if(result){
						if(deleteGoodsByTypeId(typeId)){
							doDeleteAction(this, tr_length, tr_indexOf_tbody, 'first');
						}else{
							alert('删除失败');
						}
					}
				}else{
					doDeleteAction(this, tr_length, tr_indexOf_tbody, 'first');
				}
			}else if(ul_indexOf_td == 0){
				alert('该分类下有子分类，不能直接删除');
			}else{
				if(hasGoodsByTypeId(typeId)){
					var result = confirm("该分类下有商品，如果删除该分类，商品会移除该分类，确认删除吗？");
					if(result){
						if(deleteGoodsByTypeId(typeId)){
							doDeleteAction(this, ul_length, ul_indexOf_td, 'second');
						}else{
							alert('删除失败');
						}
					}
				}else{
					doDeleteAction(this, ul_length, ul_indexOf_td, 'second');
				}
			}
		}
	})
}

/**
 * 树的上下移动
 * @param action    移动的方向：向上（up） ，向下（down）
 * @param e         事件源（即当前删除图片）
 * @param length    当前层次的兄弟总个数
 * @param index     当前层次的下标（从1开始）
 * @param level     要移动的层次，第一层first或者第二层second
 * @return
 */
function doDownOrUpAction(action, e, length, index, level){
	var currJuery = $(e).parents('ul');
	if(level == 'first'){
		currJuery = $(e).parents('tr');
	}
	if(action == 'down'){
		var nextJuery = $(e).parents('td').find('ul').eq(index+1);	
		if(level == 'first'){
			nextJuery = $(e).parents('tbody').find('tr').eq(index);		
		}
		switch(index){
			case 1 : 
				//先交换图片
				currJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-2.gif');
				nextJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
				if(index == length - 1){
					currJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
					nextJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-2.gif');
				}	
				break;
			case length - 1: 
				currJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
				nextJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-2.gif');
				break;	
			default : break;
		}
		var nextJuery_clone = nextJuery.clone();
		$(nextJuery_clone).insertBefore(currJuery);
		nextJuery.remove();
	}else{
		var lastJuery = $(e).parents('td').find('ul').eq(index - 1);
		if(level == 'first'){
			lastJuery = $(e).parents('tbody').find('tr').eq(index - 2);
		}
		switch(index){
		case length : 
			currJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-2.gif');
			lastJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
			if(index == 2){
				currJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
				lastJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-2.gif');
			}
			break;
		case 2 : 
			currJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
			lastJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-2.gif');
			break;	
		default : 
			break;
		} 
		var lastJuery_clone = lastJuery.clone();
		$(lastJuery_clone).insertAfter(currJuery);
		lastJuery.remove();
	}
}

/**
 * 树的层次移动(移动到某一大分类下)
 * @param e             事件源（即当前下拉框）
 * @param fromIndex     当前层次的下标(从0开始)
 * @param toIndex       目的地的下标
 * @param sumIndex      当前层的兄弟总数
 * @return
 */
function moveDownAction(e, fromIndex, toIndex, sumIndex){
	var currJuery = $(e).parents('tr');
	var toJuery = $(e).parents('tbody').find('tr').eq(toIndex);		
	switch(toIndex){
		case sumIndex - 1: 
			currJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
			toJuery.find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-2.gif');
			if(fromIndex == 0){
				currJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-2.gif');
				$(e).parents('tbody').find('tr').eq(fromIndex + 1).find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
			}
			break;
		default : 
			if(fromIndex == sumIndex - 1){
				$(e).parents('tbody').find('tr').eq(fromIndex).find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-2.gif');
				$(e).parents('tbody').find('tr').eq(fromIndex - 1).find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
			}else if(fromIndex == 0){
				currJuery.find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-2.gif');
				$(e).parents('tbody').find('tr').eq(fromIndex + 1).find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
			}
			break;
	}
	var currJuery_clone = currJuery.clone();
	$(currJuery_clone).insertAfter(toJuery);
	currJuery.remove();
	initSelectAssort();
}

/**
 * 树的删除
 * @param e          事件源（即当前删除图片）
 * @param length     当前层次的兄弟总个数
 * @param index      当前层次的下标（从1开始）
 * @param level      要删除的层次，第一层first或者第二层second
 * @return
 */
function doDeleteAction(e, length, index, level){
	if(level == 'first'){
		switch(index){
			case 1 :
				$('#tbody tr:eq('+ index +')').find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
				break;
			case length :
				$('#tbody tr:eq('+ (index - 2) +')').find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
				break;
			default : break;	
		} 
		$(e).parents('tr').remove();
		//重新初始化下拉值
		initSelectAssort();
	}else{
		switch(index){
			case 1 :
				$(e).parents('tr').find('ul:eq('+ (index + 1) +')').find('img[src*=up]').eq(0).attr('src',basepath + '/images/yitiansystem/up-1.gif');
				break;
			case length - 1:
				$(e).parents('tr').find('ul:eq('+ (index - 1) +')').find('img[src*=down]').eq(0).attr('src',basepath + '/images/yitiansystem/down-1.gif');
				break;
			default : break;	
		}
		$(e).parents('ul').remove();
	}
}

//查找某个分类下是否有商品
function hasGoodsByTypeId(typeId){
	var result = false;
	var shopId = $('#shopId').val();
	$.ajax({
        type: "POST",
        async:false,
        url: basepath+"/yitiansystem/commoditymgmt/bizmgmt/shopcommoditytype/hasGoodsByTypeId.sc",
        data: {
			"shopId":shopId,
			"typeId":typeId
		},
        dataType:"json", 
        success: function(data){
			if(data[0].result != 0){
				result = true;
			}
        }
    });
	return result;
}

//删除某个分类下的商品
function deleteGoodsByTypeId(typeId){
	var result = false;
	var shopId = $('#shopId').val();
	$.ajax({
		type: "POST",
		async:false,
		url: basepath+"/yitiansystem/commoditymgmt/bizmgmt/shopcommoditytype/deleteGoodsByTypeId.sc",
		data: {
			"shopId":$("#shopId").val(),
			"typeId":typeId
		},
		dataType:"json", 
		success: function(data){
			result = true;
		}
	});
	return result;
}

//获取要保存的数据
function getData(){
	var typeId = '';//分类id
	var isLeaf = '';//是否为叶子节点(1为叶子节点)
	var typeName = '';//分类名称
	var structName = '';//结构字符串
	var currentLevelSum = '';//当前所在的层次总数
	var expandFlag = '';//是否展开
	var picUrl = '';//分类图片地址
	$('input[name="textfield"]').each(function(){
		typeId += $(this).parents('ul').attr('id') + '`';
		picUrl += $(this).parents('ul').attr('role') + '`';
		typeName += $(this).val() + '`';
		//当前图片所在的ul相对于td的下标(若为0，则为第一层节点，否则为第二层节点)从1开始
		var ul_indexOf_td = $(this).parents('td').find('ul').index($(this).parents('ul')) + 10;
		var ul_length = $(this).parents('td').find('ul').length - 1;
		//第一层相对下标,从1开始
		var tr_indexOf_tbody = $(this).parents('tbody').find('tr').index($(this).parents('tr')) + 1 + 10; 
		if(ul_indexOf_td == 10){
			expandFlag += $(this).parents('ul input[type="checkbox"]').attr('checked')?1:0; 
			structName += tr_indexOf_tbody + '`';
			currentLevelSum += $('#tbody tr').length + '`';
			if(ul_length == 0){
				isLeaf += '1`';
			}else{
				isLeaf += '0`';
			}
		}else{
			structName += tr_indexOf_tbody + '-' + ul_indexOf_td + '`';
			currentLevelSum += ul_length + '`';
			isLeaf += '1`';
		}
		expandFlag += '`';
	})
	$('#CURDTypeForm').append('<input type="hidden" name="isLeaf" value="'+ isLeaf +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="typeId" value="'+ typeId +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="typeName" value="'+ typeName +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="structName" value="'+ structName +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="currentLevelSum" value="'+ currentLevelSum +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="expandFlag" value="'+ expandFlag +'end">');
	$('#CURDTypeForm').append('<input type="hidden" name="picUrl" value="'+ picUrl +'end">');
	$('#CURDTypeForm').submit();
}

//验证分类名称是否为空，当前层次的名称是否唯一
function verifyNull(){
	var isNotNull = true;
	var trLength = $('tbody tr').length;
	//父节点的分类名称
	var firstTypeNameArray = new Array();
	//子节点的分类名称
	var secondTypeNameArray = new Array();
	if(trLength == 0){
		alert('请添加分类');
		isNotNull = false;
	}
	$('input[name="textfield"]').each(function(){
		if($.trim($(this).val()) == ''){
			isNotNull = false;
			if(!$(this).hasClass('store-input-err')){
				$(this).removeClass('input-text');
				$(this).addClass('store-input-err');
			}
		}else{
			if($(this).hasClass('store-input-err')){
				$(this).removeClass('store-input-err');
			}
		}
		var ul_indexOf_td = $(this).parents('td').find('ul').index($(this).parents('ul'));
		var tr_indexOf_tbody = $(this).parents('tbody').find('tr').index($(this).parents('tr'));
		
		if(ul_indexOf_td == 0){
			firstTypeNameArray.push($.trim($(this).val()));
		}else if(ul_indexOf_td == 1){
			secondTypeNameArray[tr_indexOf_tbody] = new Array();
			secondTypeNameArray[tr_indexOf_tbody].push($.trim($(this).val()));
		}else{
			secondTypeNameArray[tr_indexOf_tbody].push($.trim($(this).val()));
		}
	})
	if(isNotNull && arrayToSet(firstTypeNameArray).length != trLength){
		alert("父分类名称不能重复");
		isNotNull = false;
	}
	if(isNotNull){
		for(var i = 0; i < trLength; i++){
			if(secondTypeNameArray[i]){
				var l1 = secondTypeNameArray[i].length;
				if(arrayToSet(secondTypeNameArray[i]).length != secondTypeNameArray[i].length){
					isNotNull = false;
					alert("同一分类下的分类名称不能重复");
					break;
				}
				var newArray = firstTypeNameArray.concat(secondTypeNameArray[i]);
				if(arrayToSet(newArray).length != l1 + trLength){
					isNotNull = false;
					alert("子分类不能与父分类名称重复");
					break;
				}
			}
		}
	}
//	if(isNotNull && arrayToSet(typeNameArray).length != inputLength){
//		isNotNull = false;
//		alert('分类名称不能相同');
//	}
	return isNotNull;
}

//去除重复
function arrayToSet(array)
 {
   var a = {};
    for(var i=0; i<array.length; i++)
   {
     if(typeof a[array[i]] == "undefined")
       a[array[i]] = 1;
   }
    array.length = 0;
   for(var i in a)
	   array[array.length] = i;
   return array;
} 

function CURD_type(){
	if(verifyNull()){
		getData();
	}
}