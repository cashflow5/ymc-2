/*
 * 点击向上的图片
 * 树形结构的父与当前节点的id及所在的行号
 */
function turnUp(id){
	var parentId = id.split('_')[0];
	var index = $('#' + id).index();
	var length = $('tr[id^="'+parentId+'_"]').length;
	var parentIndex = $('#' + parentId).index();
	var currIndex = $('#' + id).index();
	if(parentId == 'tbody'){
		var count = 0;
		$('tr[id^="tbody_"]').each(function(){
			if($(this).attr('id') == id){
				index = count;
			}
			count++;
		})
		firstAction(id, 'up', currIndex, index, length);
	}else{
		parentIndex = $('#tbody_' + parentId).index();
		secondAction(id,'up',parentIndex,index,length);
	}
}

/*
 * 点击向下的图片
 * 树形结构的parentId父id，当前行id
 */
function turnDown(id){
	var parentId = id.split('_')[0];
	var index = $('#' + id).index();
	var length = $('tr[id^="'+parentId+'_"]').length;
	var parentIndex = $('#' + parentId).index();
	var currIndex = $('#' + id).index();
	if(parentId == 'tbody'){
		var count = 0;
		$('tr[id^="tbody_"]').each(function(){
			if($(this).attr('id') == id){
				index = count;
			}
			count++;
		})
		firstAction(id, 'down', currIndex, index, length);
	}else{
		parentIndex = $('#tbody_' + parentId).index();
		secondAction(id,'down',parentIndex,index,length);
	}
}

/*
 * 树的第一层：
 * action :down,up 向上或向下移动
 * targetIndex 当前的位置下标
 * index 当前相对于父亲节点的行下标 
 * length当前树的父节点有几个孩子
 */
function firstAction(id, action, currIndex, index, length){
	debugger;
	if(action == 'down'){
		switch(index){
			case length - 1: break;
			case length - 2 : 
				var nextJueryObj =  $('tr[id^="tbody_"]').eq(index + 1);
				$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				nextJueryObj.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
				if(index == 0){
					nextJueryObj.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				}
				
				var nextJueryArr1 = $('tr[id*="'+ nextJueryObj.attr('id').split('_')[1] +'"]');
				for(var i0 = nextJueryArr1.length - 1; i0 >= 0; i0--){
					$('#tbody tr').eq(nextJueryObj.index() + i0).remove();
				}
				for(var i = nextJueryArr1.length - 1; i >= 0; i--){
					nextJueryArr1.eq(i).insertBefore($('#tbody').find('tr').eq(currIndex));
				}
				break;
			default	:
				var nextJuery = $('tr[id^="tbody_"]').eq(index + 1);
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				if(index == 0){
					nextJuery.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				}
				var nextJueryArr2 = $('tr[id*="'+ nextJuery.attr('id').split('_')[1] +'"]');
				for(var j0 = nextJueryArr2.length - 1; j0 >= 0; j0--){
					$('#tbody tr').eq(nextJuery.index() + j0).remove();
				}
				for(var j = nextJueryArr2.length - 1; j >= 0; j--){
					nextJueryArr2.eq(j).insertBefore($('#tbody').find('tr').eq(currIndex));
				}
		}
	}else{
		switch(index){
			case 0 : break;
			case 1 : 
				var lastJueryObj = $('tr[id^="tbody_"]').eq(index - 1);
				//先换图片
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
				lastJueryObj.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				if(index == length - 1){
					lastJueryObj.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				}
				var lastJueryArr1 = $('tr[id*="'+ lastJueryObj.attr('id').split('_')[1] +'"]');
				for(var m0 = lastJueryArr1.length - 1; m0 >= 0; m0--){
					$('#tbody tr').eq(lastJueryArr1.index() + m0).remove();
				}
				for(var m = lastJueryArr1.length - 1; m >= 0; m--){
					lastJueryArr1.eq(m).insertAfter($('#' + id).index());
				}
				break;	
			default : 
				var lastJuery = $('#tbody').find('tr').eq(index - 1);
				if(index == length - 1){
					$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
					lastJuery.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				}
				var lastJueryArr2 = $('tr[id*="'+ lastJuery.attr('id').split('_')[1] +'"]');
				for(var n0 = lastJueryArr2.length - 1; n0 >= 0; n0--){
					$('#tbody tr').eq(lastJueryArr2.index() + n0).remove();
				}
				for(var n = lastJueryArr2.length - 1; n >= 0; n--){
					lastJueryArr2.eq(n).insertAfter($('#' + id).index());
				}
			} 
	}
}
 
function secondAction(id, action,parentIndex, index, length){
	if(action == 'down'){
		switch(index){
			case parentIndex + length : break;
			case parentIndex + length - 1 : 
				var nextJueryObj = $('#tbody').find('tr').eq(index + 1);
				//先换图片
				$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				nextJueryObj.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
				if(index == parentIndex + 1){
					nextJueryObj.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				}
				//后交换两列
				$('#tbody').find('tr').eq(index + 1).remove();
				nextJueryObj.insertBefore($('#' + id));
				break;	
			default : 
				var nextJuery = $('#tbody').find('tr').eq(index + 1);
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				if(index == parentIndex + 1){
					nextJuery.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				}
				$('#tbody').find('tr').eq(index + 1).remove();
				nextJuery.insertBefore($('#' + id));
		}
	}else{
		switch(index){
			case parentIndex + 1 : break;
			case parentIndex + 2 : 
				var lastJueryObj = $('#tbody').find('tr').eq(index - 1);
				//先换图片
				$('#' + id).find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upBlack.jpg');
				$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
				lastJueryObj.find('td').eq(4).find('img').eq(0).attr('src', basePath + '/images/my/upGreen.jpg');
				if(index == parentIndex + length){
					lastJueryObj.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				}
				//后交换两列
				$('#tbody').find('tr').eq(index - 1).remove();
				lastJueryObj.insertAfter($('#' + id));
				break;	
			default : 
				var lastJuery = $('#tbody').find('tr').eq(index - 1);
				if(index == parentIndex + length){
					$('#' + id).find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downGreen.jpg');
					lastJuery.find('td').eq(5).find('img').eq(0).attr('src', basePath + '/images/my/downBlack.jpg');
				}
				$('#tbody').find('tr').eq(index - 1).remove();
				lastJuery.insertAfter($('#' + id));
			} 
	}
}

/*
 * 点击复选框，是否隐藏它的子节点 
 */
function isExpend(id){
	var isChecked = $('#tbody_' + id + ' input[type="checkbox"]').attr('checked');
	if(isChecked){
		$('tr[id^="'+ id +'"]').each(function(){
			$(this).hide();
		})
	}else{
		$('tr[id^="'+ id +'"]').each(function(){
			$(this).show();
		})
	}
}

//初始化基路径与请求路径
var basePath;
var url;
$(function(){
	basePath = $('#basepath').val();
	url = $('#basepath').val() + "/yitiansystem/commoditymgmt/bizmgmt/addshop/";
})