$(document).ready(function(){
	$('#orderTimeStart').calendar({maxDate:'#orderTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#orderTimeEnd').calendar({minDate:'#orderTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#merchantName_search').suggest({selectId:"merchantName"});
	$('#brandNo_search').suggest({selectId:"brandNo"});
});
