//针对层验证的表单提交
function submitvalidateform() {
    return true;
   }

function submitform(){
	var form = document.forms[0];
	form.target="mbdif";
	form.submit();
	art.dialog.close();
}
function returns(){
	art.dialog.close();
}