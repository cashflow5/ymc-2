(function ($) {
    $.fn.validationLanguage = function (call) {};
    $.validationLanguage = {
        newLang: function () {
            $.validationLanguage.allRules = {
                "required": { // Add your regex rules here, you can take telephone as an example
                    "regex": "none",
                    "alertText": "该项为必填项",
                    "alertTextCheckboxMultiple": "请选择一个选项",
                    "alertTextCheckboxe": "* 此选择框为必选"
                },
                "length": {
                    "regex": "none",
                    "alertText": "请输入 ",
                    "alertText2": " 至 ",
                    "alertText3": " 位数字/字符"
                },
                "maxCheckbox": {
                    "regex": "none",
                    "alertText": "复选框超过最大可选数"
                },
                "minCheckbox": {
                    "regex": "none",
                    "alertText": "请至少选择 ",
                    "alertText2": " 项"
                },
                "confirm": {
                    "regex": "none",
                    "alertText": "您的输入有误，请重新输入"
                },
                "telephone": {
                    //"regex":"/^[0-9\-\(\)\ ]+$/",
                    "regex": "/^[0-9\-\+\(\)]*$/",
                    "alertText": "电话号码格式有误"
                },
                "zipcode": {
                    "regex": "/^[0-9]{6}$/",
                    "alertText": "邮政编码填写错误"
                },
				"qq": {
                    "regex": "/^[1-9][0-9]{4,}$/",
                    "alertText": "qq号码填写错误！"
                },
				"msn": {
                    "regex": "/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/",
                    "alertText": "msn填写错误！"
                },
                "mobile": {
                    "regex": "/^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/",
                    "alertText": "手机号码格式有误"
                },
                "email": {
                    "regex": "/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
                    "alertText": "电子邮件地址格式有误"
                },
				"url": {
                    "regex":"/^((http|https|ftp):(\/\/|\\\\)((\w)+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(((\/[\~]*|\\[\~]*)(\w)+)|[.](\w)+)*(((([?](\w)+){1}[=]*))$/",
                    "alertText": "请输入正确的网址"
                },
                "date": {
                    "regex": "/^[0-9]{4}\-\[0-9]{1,2}\-\[0-9]{1,2}$/",
                    "alertText": "时间格式有误，时间格式为：2000-12-20"
                },
                "ipv4": {
                    "regex": "/^((([01]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))[.]){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))$/",
                    "alertText": "无效的 IP 地址"
                },
                "number": {
                    "regex": "/^[0-9\ ]+$/",
                    "alertText": "请输入数字"
                },
                "nospecial": {
                    "regex": "/^[0-9a-zA-Z]+$/",
                    "alertText": "请去掉特殊字符，重新输入"
                },
                "ajaxUser": {
                    "file": "validateLogisticscompanycode.sc",
                    "extraData": "name=eric",
                    "alertTextOk": "* 该用户名可用",
                    "alertTextLoad": "* 验证中，请稍后……",
                    "alertText": "* 该用户名已存在"
                },
                "ajaxName": {
                    "url": "validateLogisticscompanycode.sc",
                    "extraData": "logisticsCompanyCode",
                    "errText": "物流公司编号已存在",
                    "okText": "物流公司编号可用",
                    "alertTextLoad": "验证中，请稍后d……"
                },
                "onlyLetter": {
                    "regex": "/^[a-zA-Z\ \']+$/",
                    "alertText": "请输入英文字符"
                }
            }
        }
    }
})(jQuery);

$(document).ready(function () {
    $.validationLanguage.newLang()
});

(function ($) {

    $.fn.validation = function (settings) {

        if ($.validationLanguage) { // IS THERE A LANGUAGE LOCALISATION ?
            allRules = $.validationLanguage.allRules;
        } else {
            $.validation.debug("Validation engine rules are not loaded check your external file");
        }
        settings = jQuery.extend({
            allrules: allRules,
            validationEventTriggers: "keyup blur",
            inlineValidation: true,
            returnIsValid: false,
            liveEvent: false,
            unbindEngine: true,
            ajaxSubmit: false,
            scroll: true,
            promptPosition: "topRight",
            // OPENNING BOX POSITION, IMPLEMENTED: topLeft, topRight, bottomLeft, centerRight, bottomRight
            success: false,
            beforeSuccess: function () {},
            failure: function () {}
        }, settings);
        $.validation.settings = settings;
        $.validation.ajaxValidArray = new Array(); // ARRAY FOR AJAX: VALIDATION MEMORY 
        if (settings.inlineValidation == true) { // Validating Inline ?
            if (!settings.returnIsValid) { // NEEDED FOR THE SETTING returnIsValid
                allowReturnIsvalid = false;
                if (settings.liveEvent) { // LIVE event, vast performance improvement over BIND
                    $(this).find("[class*=validate][type!=checkbox]").live(settings.validationEventTriggers, function (caller) {
                        _inlinEvent(this);
                    });
                    $(this).find("[class*=validate][type=checkbox]").live("click", function (caller) {
                        _inlinEvent(this);
                    })
                } else {
                    $(this).find("[class*=validate]").not("[type=checkbox]").bind(settings.validationEventTriggers, function (caller) {
                        _inlinEvent(this);
                    });
                    $(this).find("[class*=validate][type=checkbox]").bind("click", function (caller) {
                        _inlinEvent(this);
                    })
                }
                firstvalid = false;
            }

            function _inlinEvent(caller) {
                $.validation.settings = settings;
                if ($.validation.intercept == false || !$.validation.intercept) { // STOP INLINE VALIDATION THIS TIME ONLY
                    $.validation.onSubmitValid = false;
                    $.validation.loadValidation(caller);
                } else {
                    $.validation.intercept = false;
                }
            }
        }
        if (settings.returnIsValid) { // Do validation and return true or false, it bypass everything;
            if ($.validation.submitValidation(this, settings)) {
                return false;
            } else {
                return true;
            }
        }
        $(this).bind("submit", function (caller) { // ON FORM SUBMIT, CONTROL AJAX FUNCTION IF SPECIFIED ON DOCUMENT READY
            $.validation.onSubmitValid = true;
            $.validation.settings = settings;
            if ($.validation.submitValidation(this, settings) == false) {
                if ($.validation.submitForm(this, settings) == true) {
                    return false;
                }
				//try{setTimeout(function(){ygdg.dialog.parent.location.reload()},500);}catch(e){}
            } else {
                settings.failure && settings.failure();
				
                return false;
            }
        })
    };
    $.validation = {
        defaultSetting: function (caller) { // NOT GENERALLY USED, NEEDED FOR THE API, DO NOT TOUCH
            if ($.validationLanguage) {
                allRules = $.validationLanguage.allRules;
            } else {
                $.validation.debug("Validation engine rules are not loaded check your external file");
            }
            settings = {
                allrules: allRules,
                validationEventTriggers: "blur",
                inlineValidation: true,
                returnIsValid: false,
                scroll: true,
                unbindEngine: true,
                ajaxSubmit: false,
                promptPosition: "bottomRight",
                // OPENNING BOX POSITION, IMPLEMENTED: topLeft, topRight, bottomLeft, centerRight, bottomRight
                success: false,
                failure: function () {
                    return;
                }
            }
            $.validation.settings = settings;
        },
        loadValidation: function (caller) { // GET VALIDATIONS TO BE EXECUTED
            if (!$.validation.settings) {
                $.validation.defaultSetting()
            }
            rulesParsing = $(caller).attr('class');
            rulesRegExp = /\[(.*)\]/;
            getRules = rulesRegExp.exec(rulesParsing);
            str = getRules[1];
            pattern = /\[|,|\]/;
            result = str.split(pattern);
            var validateCalll = $.validation.validateCall(caller, result);
            return validateCalll;
        },
        validateCall: function (caller, rules) { // EXECUTE VALIDATION REQUIRED BY THE USER FOR THIS FIELD
            var promptText = ""

            if (!$(caller).attr("name")) {
                $.validation.debug("This field have no ID attribut( name & class displayed): " + $(caller).attr("name") + " " + $(caller).attr("class"))
            }

            caller = caller;
            ajaxValidate = false;
            var callerName = $(caller).attr("name");
            $.validation.isError = false;
            $.validation.showTriangle = true;
            callerType = $(caller).attr("type");

            for (i = 0; i < rules.length; i++) {
				
                switch (rules[i]) {
                case "optional":
                    if (!$(caller).val()) {
                        $.validation.closePrompt(caller);
                        return $.validation.isError;
                    }
                    break;
                case "required":
                    _required(caller, rules);
                    break;
                case "custom":
                    //_customRegex(caller, rules, i);
					_customRegex(caller,rules,i);
                    break;
                case "exemptString":
                    _exemptString(caller, rules, i);
                    break;
                case "ajax":
                    if (!$.validation.onSubmitValid) {
                        _ajax(caller, rules, i);
                    };
                    break;
				case "qq": case "email": case "mobile": case "telephone": case "url": case "ipv4": case "date": case "zipcode": case "number": case "nospecial":
					 _custom(caller,rules,i);
					  break;
                case "length":
                    _length(caller, rules, i);
                    break;
                case "maxCheckbox":
                    _maxCheckbox(caller, rules, i);
                    groupname = $(caller).attr("name");
                    caller = $("input[name='" + groupname + "']");
                    break;
                case "minCheckbox":
                    _minCheckbox(caller, rules, i);
                    groupname = $(caller).attr("name");
                    caller = $("input[name='" + groupname + "']");
                    break;
                case "confirm":
                    _confirm(caller, rules, i);
                    break;
                default:
                    ;
                };
            };
            radioHack();
            if ($.validation.isError == true) {
                linkTofield = $.validation.linkTofield(caller);

                ($("div." + linkTofield).size() == 0) ? $.validation.buildPrompt(caller, promptText, "error") : $.validation.updatePromptText(caller, promptText);
            } else {
                $.validation.closePrompt(caller);
            } /* UNFORTUNATE RADIO AND CHECKBOX GROUP HACKS */
            /* As my validation is looping input with id's we need a hack for my validation to understand to group these inputs */

            function radioHack() {
                if ($("input[name='" + callerName + "']").size() > 1 && (callerType == "radio" || callerType == "checkbox")) { // Hack for radio/checkbox group button, the validation go the first radio/checkbox of the group
                    caller = $("input[name='" + callerName + "'][type!=hidden]:first");
                    $.validation.showTriangle = false;
                }
            } /* VALIDATION FUNCTIONS */

            function _required(caller, rules) { // VALIDATE BLANK FIELD
                callerType = $(caller).attr("type");
                if (callerType == "text" || callerType == "password" || callerType == "textarea") {

                    if (!$(caller).val()) {
                        $.validation.isError = true;
						if($(caller).attr('data-rel'))
						{
                        var rels = $(caller).attr('data-rel').split(',');
						promptText += rels[i] + "<br />";
						}
						else
						{		
							promptText+="该项为必填项！"+ "<br />";
                       			
						}
                      //  $(caller).focus();
                    }
                }
				
               if (callerType == "radio" || callerType == "checkbox") {
                    callerName = $(caller).attr("name");

                    if ($("input[name='" + callerName + "']:checked").size() == 0) {
                        $.validation.isError = true;
                        if ($("input[name='" + callerName + "']").size() == 1) {
                            promptText += $.validation.settings.allrules[rules[i]].alertTextCheckboxe + "<br />";
                        } else {
                            promptText += $.validation.settings.allrules[rules[i]].alertTextCheckboxMultiple + "<br />";
                        }
                    }
                }

                if (callerType == "select-one") { // added by paul@kinetek.net for select boxes, Thank you		
                    if (!$(caller).val() || $(caller).val()<=0 || $(caller).find("option:selected").text()=="请选择") {
                        $.validation.isError = true;
						
						if($(caller).attr('data-rel'))
						{
                        var rels = $(caller).attr('data-rel').split(',');
						promptText += rels[i] + "<br />";
						}
						else
						{		
							 promptText += "请选择！" + "<br />";
                       			
						}
						
                       
                    }
                }
                if (callerType == "select-multiple") { // added by paul@kinetek.net for select boxes, Thank you	
                    if (!$(caller).find("option:selected").val()) {
                        $.validation.isError = true;
						if($(caller).attr('data-rel'))
						{
                        var rels = $(caller).attr('data-rel').split(',');
						promptText += rels[i] + "<br />";
						}
						else
						{		
							 promptText += "请选择！" + "<br />";
                       			
						}
                    }
                }
            }
			
			function _custom(caller,rules,position)
			{
				customRule = rules[position];
                pattern = eval($.validation.settings.allrules[customRule].regex);

                if (!pattern.test($(caller).attr('value'))) {
                    $.validation.isError = true;
					
					if($(caller).attr('data-rel'))
						{
                        var rels = $(caller).attr('data-rel').split(',');
						promptText += rels[i] + "<br />";
						}
						else
						{
                        promptText += $.validation.settings.allrules[rules[i]].alertText + "<br />";
						}
                        //$(caller).focus();
                }
			}
			
			function _customRegex(caller,rules,position)
			{
				var  pattern = /\'|'/;
				var regs=$(caller).attr('class');
				var regex=regs.split(pattern)[1];
                pattern = eval(regex);

                if (!pattern.test($(caller).attr('value'))) {
                    $.validation.isError = true;
					
					if($(caller).attr('data-rel'))
						{
                        var rels = $(caller).attr('data-rel').split(',');
						promptText += rels[i] + "<br />";
						}
						else
						{
                        promptText += "请填写验证提示信息！" + "<br />";
						}
                }
			}

            /*function _customRegex(caller, rules, position) { // VALIDATE REGEX RULES
                customRule = rules[position + 1];
                pattern = eval($.validation.settings.allrules[customRule].regex);

                if (!pattern.test($(caller).attr('value'))) {
                    $.validation.isError = true;
                    promptText += $.validation.settings.allrules[customRule].alertText + "<br />";
                }
            }*/

            function _exemptString(caller, rules, position) { // VALIDATE REGEX RULES
                customString = rules[position + 1];
                if (customString == $(caller).attr('value')) {
                    $.validation.isError = true;
                    promptText += $.validation.settings.allrules['required'].alertText + "<br />";
                }
            }

            function _ajax(caller, rules, position) { // VALIDATE AJAX RULES
                postfile = rules[position + 1];
                fieldValue = $(caller).val();
                ajaxCaller = caller;
                fieldId = $(caller).attr("id");
                ajaxValidate = true;
                ajaxisError = $.validation.isError;
                var loadText = "正在验证，请稍候...";
                var errText = rules[position + 3];
                var okText = "";
                var extra = rules[position + 2]
                var extras = extra.split('&');
                var ajaxData = "";

                for (jj = 0; jj < extras.length; jj++) {
                    ajaxData = ajaxData + "&" + extras[jj] + "=" + fieldValue;
                }
                if (!ajaxisError) {
                    $.ajax({
                        type: "POST",
                        url: postfile,
                        async: true,
                        data: ajaxData,
                        //data: extraData,
                        beforeSend: function () { // BUILD A LOADING PROMPT IF LOAD TEXT EXIST		   			
                            if (loadText != "") {

                                if (!$("div." + fieldId + "formError")[0]) {
                                    return $.validation.buildPrompt(ajaxCaller, loadText, "load");
                                } else {
                                    $.validation.updatePromptText(ajaxCaller, loadText, "load");
                                }
                            }
                        },
                        error: function (data, transport) {
                            $.validation.debug("error in the ajax: " + data.status + " " + transport)
                        },
                        success: function (data) { // GET SUCCESS DATA RETURN JSON
                            //alert(data)
                            if (data == "true") ajaxisError = "false";
                            else ajaxisError = "true";
                            fieldId = ajaxCaller;
                            ajaxErrorLength = $.validation.ajaxValidArray.length;
                            existInarray = false;
                            if (ajaxisError == "false") { // DATA FALSE UPDATE PROMPT WITH ERROR;
                                _checkInArray(false) // Check if ajax validation alreay used on this field
                                if (!existInarray) { // Add ajax error to stop submit		 		
                                    $.validation.ajaxValidArray[ajaxErrorLength] = new Array(2);
                                    $.validation.ajaxValidArray[ajaxErrorLength][0] = fieldId;
                                    $.validation.ajaxValidArray[ajaxErrorLength][1] = false;
                                    existInarray = false;
                                }

                                $.validation.ajaxValid = false;
                                promptText += errText + "<br />";
                                $.validation.updatePromptText(ajaxCaller, promptText, "", true);
                            } else {
                                _checkInArray(true);
                                $.validation.ajaxValid = true;
                                if (okText != "") { // NO OK TEXT MEAN CLOSE PROMPT	 			
                                    $.validation.updatePromptText(ajaxCaller, okText, "pass", true);
                                } else {
                                    ajaxValidate = false;
                                    $.validation.closePrompt(ajaxCaller);
                                }
                            }

                            function _checkInArray(validate) {
                                for (x = 0; x < ajaxErrorLength; x++) {
                                    if ($.validation.ajaxValidArray[x][0] == fieldId) {
                                        $.validation.ajaxValidArray[x][1] = validate;
                                        existInarray = true;

                                    }
                                }
                            }
                        }
                    });
                }
            }

            function _confirm(caller, rules, position) { // VALIDATE FIELD MATCH
                confirmField = rules[position + 1];

                if ($(caller).attr('value') != $("#" + confirmField).attr('value')) {
                    $.validation.isError = true;
                    promptText += $.validation.settings.allrules["confirm"].alertText + "<br />";
                }
            }

            function _length(caller, rules, position) { // VALIDATE LENGTH
                startLength = eval(rules[position + 1]);
                endLength = eval(rules[position + 2]);
                feildLength = $(caller).attr('value').length;

                if (feildLength < startLength || feildLength > endLength) {
                    $.validation.isError = true;

                    if ($(caller).attr('data-rel')) {
                        var rels = $(caller).attr('data-rel').split(',');
                        if ($(caller).attr('data-rel') == "" || $(caller).attr('data-rel') == null) promptText += $.validation.settings.allrules["length"].alertText + startLength + $.validation.settings.allrules["length"].alertText2 + endLength + $.validation.settings.allrules["length"].alertText3 + "<br />"
                        else promptText += rels[i] + "<br />";
                    } else {
                        promptText = $.validation.settings.allrules["length"].alertText + startLength + $.validation.settings.allrules["length"].alertText2 + endLength + $.validation.settings.allrules["length"].alertText3 + "<br />"
                    }
					
					//$(caller).focus();

                }
            }

            function _maxCheckbox(caller, rules, position) { // VALIDATE CHECKBOX NUMBER
                nbCheck = eval(rules[position + 1]);
                groupname = $(caller).attr("name");
                groupSize = $("input[name='" + groupname + "']:checked").size();
                if (groupSize > nbCheck) {
                    $.validation.showTriangle = false;
                    $.validation.isError = true;
                    promptText += $.validation.settings.allrules["maxCheckbox"].alertText + "<br />";
                }
            }

            function _minCheckbox(caller, rules, position) { // VALIDATE CHECKBOX NUMBER
                nbCheck = eval(rules[position + 1]);
                groupname = $(caller).attr("name");
                groupSize = $("input[name='" + groupname + "']:checked").size();
                if (groupSize < nbCheck) {

                    $.validation.isError = true;
                    $.validation.showTriangle = false;
                    promptText += $.validation.settings.allrules["minCheckbox"].alertText + " " + nbCheck + " " + $.validation.settings.allrules["minCheckbox"].alertText2 + "<br />";
                }
            }
            return ($.validation.isError) ? $.validation.isError : false;
        },
        submitForm: function (caller) {
            if ($.validation.settings.ajaxSubmit) {
                if ($.validation.settings.ajaxSubmitExtraData) {
                    extraData = $.validation.settings.ajaxSubmitExtraData;
                } else {
                    extraData = "";
                }
                $.ajax({
                    type: "POST",
                    url: $.validation.settings.ajaxSubmitFile,
                    async: true,
                    data: $(caller).serialize() + "&" + extraData,
                    error: function (data, transport) {
                        $.validation.debug("error in the ajax: " + data.status + " " + transport)
                    },
                    success: function (data) {
                        if (data == "true") { // EVERYTING IS FINE, SHOW SUCCESS MESSAGE
                            $(caller).css("opacity", 1);
                            $(caller).animate({
                                opacity: 0,
                                height: 0
                            }, function () {
                                $(caller).css("display", "none");
                                $(caller).before("<div class='ajaxSubmit'>" + $.validation.settings.ajaxSubmitMessage + "</div>");
                                $.validation.closePrompt(".formError", true);
                                $(".ajaxSubmit").show("slow");
                                if ($.validation.settings.success) { // AJAX SUCCESS, STOP THE LOCATION UPDATE
                                    $.validation.settings.success && $.validation.settings.success();
                                    return false;
                                }
                            })
                        } else { // HOUSTON WE GOT A PROBLEM (SOMETING IS NOT VALIDATING)
                            data = eval("(" + data + ")");
                            if (!data.jsonValidateReturn) {
                                $.validation.debug("you are not going into the success fonction and jsonValidateReturn return nothing");
                            }
                            errorNumber = data.jsonValidateReturn.length
                            for (index = 0; index < errorNumber; index++) {
                                fieldId = data.jsonValidateReturn[index][0];
                                promptError = data.jsonValidateReturn[index][1];
                                type = data.jsonValidateReturn[index][2];
                                $.validation.buildPrompt(fieldId, promptError, type);
                            }
                        }
                    }
                });
                return true;
            }
            // LOOK FOR BEFORE SUCCESS METHOD		
            if (!$.validation.settings.beforeSuccess()) {
                if ($.validation.settings.success) { // AJAX SUCCESS, STOP THE LOCATION UPDATE
                    if ($.validation.settings.unbindEngine) {
                        $(caller).unbind("submit")
                    }
                    $.validation.settings.success && $.validation.settings.success();
                    return true;
                }
            } else {
                return true;
            }
            return false;
        },
        buildPrompt: function (caller, promptText, type, ajaxed) { // ERROR PROMPT CREATION AND DISPLAY WHEN AN ERROR OCCUR
            if (!$.validation.settings) {
                $.validation.defaultSetting()
            }
            deleteItself = "." + $(caller).attr("id") + "formError"

            if ($(deleteItself)[0]) {
                $(deleteItself).stop();
                $(deleteItself).remove();
            }
            var divFormError = document.createElement('div');
            var formErrorContent = document.createElement('div');
            linkTofield = $.validation.linkTofield(caller);
            $(divFormError).addClass("formError")

            if (type == "pass") {
                $(divFormError).addClass("greenPopup")
            }
            if (type == "load") {
                $(divFormError).addClass("blackPopup")
            }
            if (ajaxed) {
                $(divFormError).addClass("ajaxed")
            }

            $(divFormError).addClass(linkTofield);
            $(formErrorContent).addClass("formErrorContent");

            $("body").append(divFormError);
            $(divFormError).append(formErrorContent);

            if ($.validation.showTriangle != false) { // NO TRIANGLE ON MAX CHECKBOX AND RADIO
                var arrow = document.createElement('div');
                $(arrow).addClass("formErrorArrow");
                $(divFormError).append(arrow);
                if ($.validation.settings.promptPosition == "bottomLeft" || $.validation.settings.promptPosition == "bottomRight") {
                    $(arrow).addClass("formErrorArrowBottom");
                    $(arrow).html('<div class="line1"><!-- --></div><div class="line2"><!-- --></div><div class="line3"><!-- --></div><div class="line4"><!-- --></div><div class="line5"><!-- --></div><div class="line6"><!-- --></div><div class="line7"><!-- --></div><div class="line8"><!-- --></div><div class="line9"><!-- --></div><div class="line10"><!-- --></div>');
                }
                if ($.validation.settings.promptPosition == "topLeft" || $.validation.settings.promptPosition == "topRight") {
                    $(divFormError).append(arrow);
                    $(arrow).html('<div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div>');
                }
            }
            $(formErrorContent).html(promptText)

            callerTopPosition = $(caller).offset().top+15;
            callerleftPosition = $(caller).offset().left;
            callerWidth = $(caller).width();
            inputHeight = $(divFormError).height();

            /* POSITIONNING */
            if ($.validation.settings.promptPosition == "topRight") {
                callerleftPosition += callerWidth - 30;
                callerTopPosition += -inputHeight - 10;
            }
            if ($.validation.settings.promptPosition == "topLeft") {
                callerTopPosition += -inputHeight - 10;
            }

            if ($.validation.settings.promptPosition == "centerRight") {
                callerleftPosition += callerWidth + 13;
            }

            if ($.validation.settings.promptPosition == "bottomLeft") {
                callerHeight = $(caller).height();
                callerleftPosition = callerleftPosition;
                callerTopPosition = callerTopPosition + callerHeight + 15;
            }
            if ($.validation.settings.promptPosition == "bottomRight") {
                callerHeight = $(caller).height();
                callerleftPosition += callerWidth - 30;
                callerTopPosition += callerHeight + 15;
            }
            $(divFormError).css({
                top: callerTopPosition,
                left: callerleftPosition,
                opacity: 0
            });
            return $(divFormError).animate({
                "opacity": 1
            }, function () {
                return true;
            });
        },
        updatePromptText: function (caller, promptText, type, ajaxed) { // UPDATE TEXT ERROR IF AN ERROR IS ALREADY DISPLAYED
            linkTofield = $.validation.linkTofield(caller);
            var updateThisPrompt = "." + linkTofield;

            if (type == "pass") {
                $(updateThisPrompt).addClass("greenPopup")
            } else {
                $(updateThisPrompt).removeClass("greenPopup")
            };
            if (type == "load") {
                $(updateThisPrompt).addClass("blackPopup")
            } else {
                $(updateThisPrompt).removeClass("blackPopup")
            };
            if (ajaxed) {
                $(updateThisPrompt).addClass("ajaxed")
            } else {
                $(updateThisPrompt).removeClass("ajaxed")
            };

            $(updateThisPrompt).find(".formErrorContent").html(promptText);
            callerTopPosition = $(caller).offset().top+15;
            inputHeight = $(updateThisPrompt).height();

            if ($.validation.settings.promptPosition == "bottomLeft" || $.validation.settings.promptPosition == "bottomRight") {
                callerHeight = $(caller).height();
                callerTopPosition = callerTopPosition + callerHeight + 15;
            }
            if ($.validation.settings.promptPosition == "centerRight") {
                callerleftPosition += callerWidth + 13;
            }
            if ($.validation.settings.promptPosition == "topLeft" || $.validation.settings.promptPosition == "topRight") {
                callerTopPosition = callerTopPosition - inputHeight - 10;
            }
            $(updateThisPrompt).animate({
                top: callerTopPosition
            });
        },
        linkTofield: function (caller) {
            linkTofield = $(caller).attr("id") + "formError";
            linkTofield = linkTofield.replace(/\[/g, "");
            linkTofield = linkTofield.replace(/\]/g, "");
            return linkTofield;
        },
        closePrompt: function (caller, outside) { // CLOSE PROMPT WHEN ERROR CORRECTED
            if (!$.validation.settings) {
                $.validation.defaultSetting()
            }
            if (outside) {
                $(caller).fadeTo("fast", 0, function () {
                    $(caller).remove();
                });
                return false;
            }
            if (typeof (ajaxValidate) == 'undefined') {
                ajaxValidate = false
            }
            if (!ajaxValidate) {
                linkTofield = $.validation.linkTofield(caller);
                closingPrompt = "." + linkTofield;
                $(closingPrompt).fadeTo("fast", 0, function () {
                    $(closingPrompt).remove();
                });
            }
        },
        debug: function (error) {
            if (!$("#debugMode")[0]) {
                $("body").append("<div id='debugMode'><div class='debugError'><strong>This is a debug mode, you got a problem with your form, it will try to help you, refresh when you think you nailed down the problem</strong></div></div>");
            }
            $(".debugError").append("<div class='debugerror'>" + error + "</div>");
        },
        submitValidation: function (caller) { // FORM SUBMIT VALIDATION LOOPING INLINE VALIDATION
            var stopForm = false;
            $.validation.ajaxValid = true;
            $(caller).find(".formError").remove();
            var toValidateSize = $(caller).find("[class*=validate]").size();

            $(caller).find("[class*=validate]").each(function () {
                linkTofield = $.validation.linkTofield(this);

                if (!$("." + linkTofield).hasClass("ajaxed")) { // DO NOT UPDATE ALREADY AJAXED FIELDS (only happen if no normal errors, don't worry)
                    var validationPass = $.validation.loadValidation(this);
                    return (validationPass) ? stopForm = true : "";
                };
            });
            ajaxErrorLength = $.validation.ajaxValidArray.length; // LOOK IF SOME AJAX IS NOT VALIDATE
            for (x = 0; x < ajaxErrorLength; x++) {
                if ($.validation.ajaxValidArray[x][1] == false) {
                    $.validation.ajaxValid = false;
                }
            }
            if (stopForm || !$.validation.ajaxValid) { // GET IF THERE IS AN ERROR OR NOT FROM THIS VALIDATION FUNCTIONS
                if ($.validation.settings.scroll) {
                    destination = $(".formError:not('.greenPopup'):first").offset().top;
                    $(".formError:not('.greenPopup')").each(function () {
                        testDestination = $(this).offset().top;
                        if (destination > testDestination) {
                            destination = $(this).offset().top;
                        }
                    });
                    $("html:not(:animated),body:not(:animated)").animate({
                        scrollTop: destination
                    }, 20);
                }
                return true;
            } else {
                return false;
            }
        }
    }
})(jQuery);