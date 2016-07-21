function FileProgress(file, settings) {
	this.fileProgressID = file.id;
	this.fileProgressSettings = settings;
	
	this.opacity = 100;
	this.height = 0;

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	this.fileProgressWrapperFather = document.getElementById(this.fileProgressSettings.progressTarget);
	this.validateMessage = new FileValidation(this.fileProgressSettings.ignoreValidation).validate(file);
	if (!this.fileProgressWrapper) {
		
		this.fileProgressWrapper = document.createElement("tr");
		this.fileProgressWrapper.id = this.fileProgressID;
		
		var progressText = document.createElement("td");
		progressText.innerHTML = file.name + '<br/><span></span>';
		
		var progressSize = document.createElement("td");
		progressSize.innerHTML = SWFUpload.speed.formatBytes(file.size);
		
		var progressCancel = document.createElement("td");
		progressCancel.innerHTML = '<a href="#">移除</a>';

		this.fileProgressWrapper.appendChild(progressText);
		this.fileProgressWrapper.appendChild(progressSize);
		this.fileProgressWrapper.appendChild(progressCancel);
		this.fileProgressWrapperFather.appendChild(this.fileProgressWrapper);
		this.togglePrompt();
	} else {
		this.reset();
	}

	this.height = this.fileProgressWrapper.offsetHeight;
}

FileProgress.prototype.reset = function () {
	$(this.fileProgressWrapper.childNodes[0]).css('color', '#0000FF');
	this.setStatus();
};
FileProgress.prototype.setProgress = function (percentage) {
	$(this.fileProgressWrapper.childNodes[0]).css('color', '#0000FF');
};
FileProgress.prototype.setComplete = function () {
	$(this.fileProgressWrapper.childNodes[0]).css('color', '#008000');
};
FileProgress.prototype.setError = function () {
	$(this.fileProgressWrapper.childNodes[0]).css('color', '#FF0000');
	this.setCancelled(this.fileProgressWrapper.rowIndex * 3000);
};
FileProgress.prototype.setCancelled = function (millisec) {
	var oSelf = this;
	window.setTimeout(function(){
		$(oSelf.fileProgressWrapper).fadeOut().remove();
		oSelf.togglePrompt();
	}, millisec || 3000);
};
FileProgress.prototype.setStatus = function (status) {
	status = this.validateMessage ? this.validateMessage : (status || '');
	$(this.fileProgressWrapper.childNodes[0]).find('span').html(status);
};
//Show/Hide the prompt row
FileProgress.prototype.togglePrompt = function () {
	var rows = $(this.fileProgressWrapperFather).children('tr');
	rows.filter(function(){ return this.id == ''; }).remove();
	if (rows.size() <= 0) {
		$(this.fileProgressWrapperFather).html('<tr><td colspan="3"><font color="red">暂无图片,请点击“添加商品图片”按扭添加。</font></td></tr>');
	}
};
// Show/Hide the cancel button
FileProgress.prototype.toggleCancel = function (show, swfUploadInstance) {
	var fileID = this.fileProgressID;
	$(this.fileProgressWrapper.childNodes[2]).find('a').css('visibility', show ? 'visible' : 'hidden').click(function () {
		if (swfUploadInstance) {
			swfUploadInstance.cancelUpload(fileID);
			return false;
		}
	});
	if (this.validateMessage) {
		if (swfUploadInstance) {
			swfUploadInstance.cancelUpload(fileID);
		}
	}
};












