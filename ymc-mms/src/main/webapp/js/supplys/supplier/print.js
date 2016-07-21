//打印
function printPage(obj) {
	var body = window.document.body.innerHTML;
	var printArea = window.document.getElementById(obj).innerHTML;
	window.document.body.innerHTML = printArea;
	window.print();
	window.document.body.innerHTML = body;
}