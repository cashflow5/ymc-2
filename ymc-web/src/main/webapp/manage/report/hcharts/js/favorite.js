(function($) {
//�ղ�Ч������
YGUI.collect=function (){
    var attr, length, display,
        operate = $('.operate'),
        collect = $('.collect'),
        ul = $('.collect-list'),
        li = $('.collect-item'),
        collect_btn = $('.collect-btn'),
        collect_click = $('.collect-click'),
        checkbox = li.find('input[type="checkbox"]'),
        data = [{
            'name': '����������Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '��������Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': 'ת���ʽϵ���Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '���ϴ���Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '�ع۲���Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '������Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '�貹����Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '13��ר����Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '13����ҳ��Ʒ',
            'value':'bbbbbbbbbbbbbbbb'
        }];
    ul.on('mouseover mouseout', '.collect-item', function(event) { //�����������hover�¼�����ӱ���������ʾ
        if (event.type == "mouseover") {
            $(this).addClass('hover').siblings().removeClass('hover');
        } else {
            $(this).removeClass('hover');
        }
    });
    collect.click(function(event) {
        var arr = [],collect_list,
            collect_index = collect.index(this);
        $(data).each(function(index, el) {
            var name = this.name,
                li_new = '<li class="collect-item"><label for="d-' + collect_index + '-' + index + '"><input type="checkbox"class="checbox" name="destination" id="d-' + collect_index + '-' + index + '" value="'+this.value+'">' + this.name + '</label></li>';
            arr.push(li_new);
        });
        display = $(this).find('.collect-list').css('display');
        collect_list = $(this).find('.collect-list');
        if ($(this).find('.collect-click').hasClass('first')) { //�ж��Ƿ��ǵ�һ�ε��
            collect_list.html(arr.join(""));
            $(this).find('.collect-click').find('i.up').html('&#xe60d;');
            collect_list.show();
        } else {
            if (display == "none") {
                collect_list.show();
                $(this).find('.collect-click').find('i.up').html('&#xe60d;');
            } else {
                collect_list.hide();
                $(this).find('.collect-click').find('i.up').html('&#xe610;');
            }
        }
        $(this).find('.collect-click').removeClass('first');
    });
    collect.hover(function() {
        $(this).parent().addClass('hover'); //���class hover���ie7��index������
    }, function() {
        $(this).find('.collect-list').hide();
        $(this).find('i.up').html('&#xe610;');
        $(this).parent().removeClass('hover');
    });
    ul.on("click", 'input[type="checkbox"]', function() { //��input���click�¼�
        attr = $(this).prop('checked'),
            length = $(this).parent().parent().parent().find('input[type="checkbox"]:checked').length;
        if (length > 0) {
            $(this).parent().parent().parent().parent().siblings('.star').find('i').addClass('Blue');
        } else {
            $(this).parent().parent().parent().parent().siblings('.star').find('i').removeClass('Blue');
        }
        if (attr) {
            $(this).parent().parent().addClass('checked').siblings().removeClass('checked');
        } else {
            $(this).parent().parent().removeClass('checked');
        }
    });
    
    // ����������ʽ���
    ul.niceScroll({
        cursorcolor: "#5893ed",
        cursoropacitymax: 1,
        touchbehavior: false,
        cursorwidth: "5px",
        cursorborder: "0",
        cursorborderradius: "5px"
    });
}
    YGUI.collect();
})(jQuery);
