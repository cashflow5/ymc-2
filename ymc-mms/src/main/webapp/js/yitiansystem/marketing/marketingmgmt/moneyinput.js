(function($) {
    $.fn.extend({
        money_mode: function(options) {
            var defaults = {
                decimal_length: 2,//小数点位数
                format: "#,###.00",//格式化类型
                locale: "us"
            };

            var options = $.extend(defaults, options);
            var len = parseInt(options['decimal_length']);

            return this.each(function() {
                var input = $(this);
                input.css("ime-mode", "disabled");
                var decimal_point = false;
                input.bind("keypress", function() {
                    if (event.keyCode == 46) {
                        if (input.val().indexOf(".") != -1 || (input.val().lastIndexOf(".") == (input.val().length - 1))) {
                            return false;
                        }
                    } else {
                        if (decimal_point)
                            return false;
                        else
                            return event.keyCode >= 46 && event.keyCode <= 57;
                    }
                });

                input.bind("blur", function() {
                	// begin失去焦点后字符串格式化
                	var formatData = formatCodes(options.locale.toLowerCase());
                	var valid = formatData.valid;
		            var dec = formatData.dec;
		            var group = formatData.group;
		            var neg = formatData.neg;
                    if (input.val() != null && input.val() != '') {
                        var text = new String(jQuery(input).text());
                        if (jQuery(input).is(":input"))
                            text = new String(jQuery(input).val());
                        var isValid = true;
                        for (var i = 0; i < text.length; i++) {
                            if (valid.indexOf(text.charAt(i)) == -1)
                                isValid = false;
                        }
                        if (isValid) {
                            var number = new Number(text.replace(group, '').replace(dec, ".").replace(neg, "-"));
                            var returnString = "";
                            var decimalValue = number % 1;
                            if (options.format.indexOf(".") > -1) {
                                var decimalPortion = dec;
                                var decimalFormat = options.format.substring(options.format.lastIndexOf(".") + 1);
                                var decimalString = new String(decimalValue.toFixed(decimalFormat.length));
                                decimalString = decimalString.substring(decimalString.lastIndexOf(".") + 1);
                                for (var i = 0; i < decimalFormat.length; i++) {
                                    if (decimalFormat.charAt(i) == '#' && decimalString.charAt(i) != '0') {
                                        decimalPortion += decimalString.charAt(i);
                                        break;
                                    }
                                    else if (decimalFormat.charAt(i) == "0") {
                                        decimalPortion += decimalString.charAt(i);
                                    }
                                }
                                returnString += decimalPortion
                            }
                            else
                                number = Math.round(number);
                            var ones = Math.floor(number);
                            var onePortion = "";
                            if (ones == 0) {
                                onePortion = "0";
                            }
                            else {
                                // find how many digits are in the group
                                var onesFormat = "";
                                if (options.format.indexOf(".") == -1)
                                    onesFormat = options.format;
                                else
                                    onesFormat = options.format.substring(0, options.format.indexOf("."));
                                var oneText = new String(ones);
                                var groupLength = 9999;
                                if (onesFormat.lastIndexOf(",") != -1)
                                    groupLength = onesFormat.length - onesFormat.lastIndexOf(",") - 1;
                                var groupCount = 0;
                                for (var i = oneText.length - 1; i > -1; i--) {
                                    onePortion = oneText.charAt(i) + onePortion;
                                    groupCount++;
                                    if (groupCount == groupLength && i != 0) {
                                        onePortion = group + onePortion;
                                        groupCount = 0;
                                    }
                                }
                            }
                            returnString = onePortion + returnString;
                            if (number < 0)
                                returnString += neg;
                            if (jQuery(this).is(":input"))
                                jQuery(this).val(returnString);
                            else
                                jQuery(this).text(returnString);
                        }
                    }
                    // end 失去焦点后字符串格式化
                });

                input.bind("paste", function() {
                    var s = clipboardData.getData('text');
                    if (!/\D/.test(s));
                    value = s.replace(/^0*/, '');
                    return false;
                });

                input.bind("dragenter", function() {
                    return false;
                });

                input.bind("keyup", function() {
                    if (input.val() != null && input.val() != '') {
                        if (input.val().indexOf(".") == -1) {
                            return true;
                        }
                        else {
                            var decimalIndex = input.val().indexOf('.');
                            var decimalPart = input.val().substring(decimalIndex + 1, input.val().length);
                            if (decimalPart.length == len) {
                                decimal_point = true;
                                return false;
                            }
                            else
                                return true;
                        }
                    }
                    else {
                        decimal_point = false;
                        return false;
                    }

                });
            });
        }
    });
    function formatCodes(locale) {
        // default values
        var valid = "1234567890.,-";
        var dec = ".";
        var group = ",";
        var neg = "-";
        if (locale == "us" || locale == "cn" || locale == "tw") {
            valid = "1234567890.,-";
            dec = ".";
            group = ",";
        }
        return new FormatData(valid, dec, group, neg);
    };

    function FormatData(valid, dec, group, neg) {
        this.valid = valid;
        this.dec = dec;
        this.group = group;
        this.neg = neg;
    };
})(jQuery);