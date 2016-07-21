var isIE = (document.all) ? true : false;

var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	var args = Array.prototype.slice.call(arguments).slice(2);
	return function() {
		return fun.apply(object, args);
	}
}

var BindAsEventListener = function(object, fun) {
	return function(event) {
		return fun.call(object, Event(event));
	}
}

function Event(e){
	var oEvent = isIE ? window.event : e;
	if (isIE) {
		oEvent.pageX = oEvent.clientX + document.documentElement.scrollLeft;
		oEvent.pageY = oEvent.clientY + document.documentElement.scrollTop;
		oEvent.preventDefault = function () { this.returnValue = false; };
		oEvent.detail = oEvent.wheelDelta / (-40);
		oEvent.stopPropagation = function(){ this.cancelBubble = true; }; 
	}
	return oEvent;
}

var CurrentStyle = function(element){
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
}

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};


//����������
var Slider = Class.create();
Slider.prototype = {
  //�������󣬻���
  initialize: function(container, bar, options) {
	this.Bar = $$(bar);
	this.Container = $$(container);
	this._timer = null;//�Զ����ƵĶ�ʱ��
	this._ondrag = false;//���ie��click����
	//�Ƿ���Сֵ�����ֵ���м�ֵ
	this._IsMin = this._IsMax = this._IsMid = false;
	//ʵ����һ���ϷŶ��󣬲��޶���Χ
	this._drag = new Drag(this.Bar, { Limit: true, mxContainer: this.Container,
		onStart: Bind(this, this.DragStart), onStop: Bind(this, this.DragStop), onMove: Bind(this, this.Move)
	});
	
	this.SetOptions(options);
	
	this.WheelSpeed = Math.max(0, this.options.WheelSpeed);
	this.KeySpeed = Math.max(0, this.options.KeySpeed);
	
	this.MinValue = this.options.MinValue;
	this.MaxValue = this.options.MaxValue;
	
	this.RunTime = Math.max(1, this.options.RunTime);
	this.RunStep = Math.max(1, this.options.RunStep);
	
	this.Ease = !!this.options.Ease;
	this.EaseStep = Math.max(1, this.options.EaseStep);
	
	this.onMin = this.options.onMin;
	this.onMax = this.options.onMax;
	this.onMid = this.options.onMid;
	
	this.onDragStart = this.options.onDragStart;
	this.onDragStop = this.options.onDragStop;
	
	this.onMove = this.options.onMove;
	
	this._horizontal = !!this.options.Horizontal;//һ�㲻�����޸�
	
	//�����Ϸŷ���
	this._drag[this._horizontal ? "LockY" : "LockX"] = true;
	
	//�������
	addEventHandler(this.Container, "click", BindAsEventListener(this, function(e){ this._ondrag || this.ClickCtrl(e);}));
	//ȡ��ð�ݣ���ֹ��Container��click��ͻ
	addEventHandler(this.Bar, "click", BindAsEventListener(this, function(e){ e.stopPropagation(); }));
	
	//���������ֿ���
	this.WheelBind(this.Container);
	//���÷��������
	this.KeyBind(this.Container);
	//������ȡ����
	var oFocus = isIE ? (this.KeyBind(this.Bar), this.Bar) : this.Container;
	addEventHandler(this.Bar, "mousedown", function(){ oFocus.focus(); });
	//ie��겶���ff��ȡ��Ĭ�϶��������ܻ�ý��㣬����Ҫ�ֶ���ȡ
	//���ie��focus���õ�Container����ô�ڳ��ֹ�����ʱie��focus���ܻᵼ���Զ�����
  },
  //����Ĭ������
  SetOptions: function(options) {
	this.options = {//Ĭ��ֵ
		MinValue:	0,//��Сֵ
		MaxValue:	100,//���ֵ
		WheelSpeed: 5,//�������ٶ�,Խ��Խ��(0��ȡ�������ֿ���)
		KeySpeed: 	50,//����������ٶ�,Խ��Խ��(0��ȡ�����������)
		Horizontal:	true,//�Ƿ�ˮƽ����
		RunTime:	20,//�Զ����Ƶ���ʱʱ��,Խ��Խ��
		RunStep:	2,//�Զ�����ÿ�λ����İٷֱ�
		Ease:		false,//�Ƿ񻺶�
		EaseStep:	5,//�����ȼ�,Խ��Խ��
		onMin:		function(){},//��Сֵʱִ��
		onMax:		function(){},//���ֵʱִ��
		onMid:		function(){},//�м�ֵʱִ��
		onDragStart:function(){},//�϶���ʼʱִ��
		onDragStop:	function(){},//�϶�����ʱִ��
		onMove:		function(){}//����ʱִ��
	};
	Extend(this.options, options || {});
  },
  //��ʼ�ϷŻ���
  DragStart: function() {
  	this.Stop();
	this.onDragStart();
	this._ondrag = true;
  },
  //�����ϷŻ���
  DragStop: function() {
  	this.onDragStop();
	setTimeout(Bind(this, function(){ this._ondrag = false; }), 10);
  },
  //������
  Move: function() {
  	this.onMove();
	
	var percent = this.GetPercent();
	//��Сֵ�ж�
	if(percent > 0){
		this._IsMin = false;
	}else{
		if(!this._IsMin){ this.onMin(); this._IsMin = true; }
	}
	//���ֵ�ж�
	if(percent < 1){
		this._IsMax = false;
	}else{
		if(!this._IsMax){ this.onMax(); this._IsMax = true; }
	}
	//�м�ֵ�ж�
	if(percent > 0 && percent < 1){
		if(!this._IsMid){ this.onMid(); this._IsMid = true; }
	}else{
		this._IsMid = false;
	}
  },
  //���������
  ClickCtrl: function(e) {
	var o = this.Container, iLeft = o.offsetLeft, iTop = o.offsetTop;
	while (o.offsetParent) { o = o.offsetParent; iLeft += o.offsetLeft; iTop += o.offsetTop; }
	//�����й�������Ҫ��pageX��pageY
	this.EasePos(e.pageX - iLeft - this.Bar.offsetWidth / 2, e.pageY - iTop - this.Bar.offsetHeight / 2);
  },
  //�����ֿ���
  WheelCtrl: function(e) {
	var i = this.WheelSpeed * e.detail;
	this.SetPos(this.Bar.offsetLeft + i, this.Bar.offsetTop + i);
	//��ֹ��������������
	e.preventDefault();
  },
  //��������
  WheelBind: function(o) {
  	//�����ֿ���
	addEventHandler(o, isIE ? "mousewheel" : "DOMMouseScroll", BindAsEventListener(this, this.WheelCtrl));
  },
  //���������
  KeyCtrl: function(e) {
	if(this.KeySpeed){
		var iLeft = this.Bar.offsetLeft, iWidth = (this.Container.clientWidth - this.Bar.offsetWidth) / this.KeySpeed
			, iTop = this.Bar.offsetTop, iHeight = (this.Container.clientHeight - this.Bar.offsetHeight) / this.KeySpeed;
		//���ݰ�������ֵ
		switch (e.keyCode) {
			case 37 ://��
				iLeft -= iWidth; break;
			case 38 ://��
				iTop -= iHeight; break;
			case 39 ://��
				iLeft += iWidth; break;
			case 40 ://��
				iTop += iHeight; break;
			default :
				return;//���Ƿ��򰴼�����
		}
		this.SetPos(iLeft, iTop);
		//��ֹ��������������
		e.preventDefault();
	}
  },
  //�󶨷����
  KeyBind: function(o) {
	addEventHandler(o, "keydown", BindAsEventListener(this, this.KeyCtrl));
	//����tabIndexʹ���ö�����֧��focus
	o.tabIndex = -1;
	//ȡ��focusʱ���ֵ����߿�
	isIE || (o.style.outline = "none");
  },
  //��ȡ��ǰֵ
  GetValue: function() {
	//���������Сֵ�ͻ����ٷֱ�ȡֵ
	return this.MinValue + this.GetPercent() * (this.MaxValue - this.MinValue);
  },
  //����ֵλ��
  SetValue: function(value) {
	//���������Сֵ�Ͳ���ֵ���û���λ��
	this.SetPercent((value- this.MinValue)/(this.MaxValue - this.MinValue));
  },
  //��ȡ�ٷֱ�
  GetPercent: function() {
	//���ݻ���������ȡ�ٷֱ�
	return this._horizontal ? this.Bar.offsetLeft / (this.Container.clientWidth - this.Bar.offsetWidth)
		: this.Bar.offsetTop / (this.Container.clientHeight - this.Bar.offsetHeight)
  },
  //���ðٷֱ�λ��
  SetPercent: function(value) {
	//���ݰٷֱ����û���λ��
	this.EasePos((this.Container.clientWidth - this.Bar.offsetWidth) * value, (this.Container.clientHeight - this.Bar.offsetHeight) * value);
  },
  //�Զ�����(�Ƿ����)
  Run: function(bIncrease) {
	this.Stop();
	//����һ��bIncrease
	bIncrease = !!bIncrease;
	//�����Ƿ����������ֵ
	var percent = this.GetPercent() + (bIncrease ? 1 : -1) * this.RunStep / 100;
	this.SetPos((this.Container.clientWidth - this.Bar.offsetWidth) * percent, (this.Container.clientHeight - this.Bar.offsetHeight) * percent);
	//���û�е�����ֵ�ͼ�������
	if(!(bIncrease ? this._IsMax : this._IsMin)){
		this._timer = setTimeout(Bind(this, this.Run, bIncrease), this.RunTime);
	}
  },
  //ֹͣ����
  Stop: function() {
	clearTimeout(this._timer);
  },
  //��������
  EasePos: function(iLeftT, iTopT) {
	this.Stop();
	//���������������������ѭ��
	iLeftT = Math.round(iLeftT); iTopT = Math.round(iTopT);
	//���û�����û���
	if(!this.Ease){ this.SetPos(iLeftT, iTopT); return; }
	//��ȡ��������
	var iLeftN = this.Bar.offsetLeft, iLeftS = this.GetStep(iLeftT, iLeftN)
	, iTopN = this.Bar.offsetTop, iTopS = this.GetStep(iTopT, iTopN);
	//���������ֵ
	if(this._horizontal ? iLeftS : iTopS){
		//����λ��
		this.SetPos(iLeftN + iLeftS, iTopN + iTopS);
		//���û�е�����ֵ���������
		if(this._IsMid){ this._timer = setTimeout(Bind(this, this.EasePos, iLeftT, iTopT), this.RunTime); }
	}
  },
  //��ȡ����
  GetStep: function(iTarget, iNow) {
    var iStep = (iTarget - iNow) / this.EaseStep;
    if (iStep == 0) return 0;
    if (Math.abs(iStep) < 1) return (iStep > 0 ? 1 : -1);
    return iStep;
  },
  //���û���λ��
  SetPos: function(iLeft, iTop) {
	this.Stop();
	this._drag.SetPos(iLeft, iTop);
  }
};