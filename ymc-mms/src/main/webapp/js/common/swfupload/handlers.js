/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings);
		progress.setStatus("待上传...");
		progress.toggleCancel(true, this);

	} catch (ex) {
		this.debug(ex);
	}
}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("您添加的文件超过允许的最大限制.\n" + (message === 0 ? "您已经达到上传限制." : "您只能上传" + (message > 1 ? message + "文件." : "1个文件.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings);
		progress.setError();
		progress.toggleCancel(false);
		
		var sem = getSWFUploadErrorMessage(file, errorCode, message);
		progress.setStatus(sem.status);
		this.debug(sem.debug);
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings);
		progress.setStatus("已上传：0%");
		progress.toggleCancel(true, this);
	}
	catch (ex) {
		this.debug(ex);
	}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings);
		progress.setProgress(percent);
		progress.setStatus("上传中：" + percent + "%");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings);
		if (serverData == 'success') {
			progress.setComplete();
			progress.setStatus("已上传.");
			progress.toggleCancel(false);
		} else {
			progress.setError();
			progress.setStatus(serverData);
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings);
		progress.setError();
		progress.toggleCancel(false);
		
		var sem = getSWFUploadErrorMessage(file, errorCode, message);
		progress.setStatus(sem.status);
		this.debug(sem.debug);
	} catch (ex) {
        this.debug(ex);
    }
}

function getSWFUploadErrorMessage(file, errorCode, message) {
	file = file || {};
	message = message || '';
	switch (errorCode) {
	case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
		return { status: ("Upload Error: " + message), debug: ("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
		return { status: ("Upload Failed."), debug: ("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.IO_ERROR:
		return { status: ("Server (IO) Error"), debug: ("Error Code: IO Error, File name: " + file.name + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
		return { status: ("Security Error"), debug: ("Error Code: Security Error, File name: " + file.name + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
		return { status: ("Upload limit exceeded."), debug: ("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
		return { status: ("Failed Validation.  Upload skipped."), debug: ("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
		return { status: ("已取消"), debug: ("Error Code: 已取消") };
	case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
		return { status: ("已停止"), debug: ("Error Code: 已停止") };
	case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
		return { status: ("File is too big."), debug: ("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		return { status: ("Cannot upload Zero Byte files."), debug: ("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		return { status: ("Invalid File Type."), debug: ("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	default:
		return { status: ("Unhandled Error: " + errorCode), debug: ("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message) };
	}
}