
function FileValidation(ignoreValidation) {
	this.fileIgnoreValidation = ignoreValidation;
	
	this.KB = 1024;
	this.fileSizes = [ this.KB * 10, this.KB * 60, this.KB * 10, this.KB * 10, this.KB * 500, this.KB * 1024, this.KB * 25, this.KB * 10, this.KB * 10 ];
	this.fileNameRegexps = [ '_01_s\.(jpg)$', '_0[1-7]_m\.(jpg)$', '_0[1-7]_t\.(jpg)$', '_01_c\.(jpg)$', '_0[1-7]_l\.(jpg)$', '_(0[1-9]|[1-9][0-9])_b\.(jpg)$', '_0[1-7]_mb\.(jpg)$', '_0[1-7]_ms\.(jpg)$', '_01_u\.(jpg)$' ];
	this.filePixelRegexps = [ '^160\\*160$', '^480\\*480$', '^60\\*60$', '^40\\*40$', '^([89][0-9][0-9]|1000)\\*([89][0-9][0-9]|1000)$', '^7([4-8][0-9]|90)\\*[1-9]([0-9]|[0-9][0-9]|[0-9][0-9][0-9])$', '^240\\*240$', '^160\\*160$', '^100\\*100$' ];
	this.filePixelMessage = [ '160*160', '480*480', '60*60', '40*40', '800-1000*800-1000', '790*10-9999', '240*240', '160*160', '100*100' ];
	this.startIndex = 4;
	this.endIndex = 6;
}

// Validate file size
/*-------------------------------------------------------------------------------------
用途			|解释							|规格			|大小		|图片名称
---------------------------------------------------------------------------------------
列表页		|列表页图片（1个角度）			|160X160		|10k		|商品编码_01_s
---------------------------------------------------------------------------------------
单品页		|商品详细页左边大图（7个角度）	|480X480		|60k		|商品编码_01_m
			|商品缩略小图（7个角度）			|60X60			|10k		|商品编码_01_t
			|商品颜色选择小图（1个角度）		|40X40			|10k		|商品编码_01_c
			|放大镜 大图（7个角度）			|1000X1000		|500k		|商品编码_01_l
			|商品描述图						|740X100+		|200k		|商品编码_01_b
---------------------------------------------------------------------------------------
其他	手机 	|手机版小图（7个角度）			|240X240		|25k		|商品编码_01_mb
			|手机版小图（7个角度）			|160X160		|10k		|商品编码_01_ms
			|后台程序（1个角度）				|100X100		|10k		|商品编码_01_u
-------------------------------------------------------------------------------------*/
FileValidation.prototype.validate = function (file) {
	var defaultMessage = this.fileIgnoreValidation != 'true' ? null : '文件名称格式错误';
	for (var i = this.startIndex; defaultMessage != null && i < this.endIndex; i++) {
		if (file.name.match(new RegExp(this.fileNameRegexps[i], 'gi')) != null) {
			if (file.name.replace(/[^\x00-\xff]/g,"**").length>32) {
				return '文件名称不能超过32个字符(注:1个汉字占两个字符)：' + file.name;
			}
			if (file.size > (this.fileSizes[i] + this.KB)) {
				return '文件大小不能超过：' + SWFUpload.speed.formatBytes(this.fileSizes[i]);
			}
			if (file.loaded && (file.width + '*' + file.height).match(new RegExp(this.filePixelRegexps[i], 'gi')) == null) {
				return '文件像素必须为：' + this.filePixelMessage[i];
			}
			return null;
		}
	}
	return defaultMessage;
};



