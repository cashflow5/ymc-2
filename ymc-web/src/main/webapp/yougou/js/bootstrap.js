(function() {
    var scripts = document.getElementsByTagName('script'),
        localhostTests = [
            /^localhost$/,
            /\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(:\d{1,5})?\b/ // IP v4
        ],
        host = window.location.hostname,
        isDevelopment = false,
        queryString = window.location.search,
        test, path, i, ln, scriptSrc, match;

    for (i = 0, ln = scripts.length; i < ln; i++) {
        scriptSrc = scripts[i].src;

        match = scriptSrc.match(/bootstrap\.js$/);

        if (match) {
            path = scriptSrc.substring(0, scriptSrc.length - match[0].length);
            break;
        }
    }

    if (queryString.match('(\\?|&)debug') !== null) {
        isDevelopment = true;
    }
    else if (queryString.match('(\\?|&)nodebug') !== null) {
        isDevelopment = false;
    }

    if (isDevelopment === null) {
        for (i = 0, ln = localhostTests.length; i < ln; i++) {
            test = localhostTests[i];

            if (host.search(test) !== -1) {
                isDevelopment = true;
                break;
            }
        }
    }

    if (isDevelopment === null && window.location.protocol === 'file:') {
        isDevelopment = true;
    }
	isDevelopment=false;
    document.write('<script type="text/javascript" src="' + path + 'jquery-1.7.2.min.js"></script>');
    document.write('<script type="text/javascript" src="' + path + 'common' + ((isDevelopment) ? '.min' : '') + '.js"></script>');
    document.write('<script type="text/javascript" src="' + path + 'ygui' + ((isDevelopment) ? '.min' : '') + '.js?version=20141218"></script>');
	document.write('<script type="text/javascript" src="' + path + 'calendar/lhgcalendar' + ((isDevelopment) ? '.min' : '') + '.js"></script>');
	document.write('<script type="text/javascript" src="' + path + 'validator/formValidator' + ((isDevelopment) ? '.min' : '') + '.js"></script>');
	document.write('<script type="text/javascript" src="' + path + 'ajaxfileupload.js"></script>');
	document.write('<script type="text/javascript" src="' + path + 'ygdialog/lhgdialog' + ((isDevelopment) ? '.min' : '') + '.js?s=blue"></script>');
})();

