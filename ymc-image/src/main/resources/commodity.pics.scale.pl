#!perl -w
use strict;
use Imager;

@ARGV = glob "@ARGV"; # 处理通配符，脚本可以批量处理图片

@ARGV or die "Runtime errors, Args can not be empty.\n";

# 拉命令行参数
my $sharp = 0; #锐化0.4
my $in;

# 参数下标最大长度
my $last = $#ARGV;

# 校验参数长度
if ($last eq 0) {
	die "Runtime errors, Args length must greater then 1.\n";
}

# 校验加3张小图，引用的图片索引
if ($ARGV[$last] !~ m/^[1-7]$/) {
	die "Runtime errors, Last arg must be 1-7.\n";
}

# 循环下标
my $next = 0;

# 3张小图正则
my $one_s_u_c = qr/_0$ARGV[$last]|-0$ARGV[$last]$/;

# do the work
while ($in = shift) {
	#print "doing $in \n";
	my $img = Imager->new;
	$img->read(file=>$in) or die "Cannot read image $in: ", $img->errstr, "\n";

	# 从输入文件名中产生所有小图文件名，注意这里$in的值变了
	$in =~ s/(_l.jpg|-l.jpg)$//i;
	my $m = $in.'_m.jpg'; # 480x480
	my $one_s = $in.'_s.jpg'; # 160x160 只要角度1，
	my $mb = $in.'_mb.jpg'; # 240x240
	my $ms = $in.'_ms.jpg'; # 160x160
	my $one_u = $in.'_u.jpg'; # 100x100 只要角度1
	my $t = $in.'_t.jpg'; # 60x60
	my $one_c = $in.'_c.jpg'; # 40x40 只要角度1

	my $result;

	# do m 480
	$result = $img->scale(xpixels=>480);
	$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
	$result->write(file=>$m) or die "Cannot write image $m: ", $result->errstr, "\n";

	# do ms 160
	$result = $img->scale(xpixels=>160);
	$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
	$result->write(file=>$ms) or die "Cannot write image $ms: ", $result->errstr, "\n";

	# do mb 240
	$result = $img->scale(xpixels=>240);
	$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
	$result->write(file=>$mb) or die "Cannot write image $mb: ", $result->errstr, "\n";

	# do t 60
	$result = $img->scale(xpixels=>60);
	$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
	#$result->filter(type=>"conv", coef=>[-$sharp-0.2, 2, -$sharp-0.2 ]) or die $result->errstr;
	$result->write(file=>$t) or die "Cannot write image $t: ", $result->errstr, "\n";

	# 如果是角度1，加3张小图
	if ($in =~ m/$one_s_u_c/) {
		# 替换输出文件名称
		$one_s =~ s/0[1-7]_s.jpg$/01_s.jpg/;
		$one_u =~ s/0[1-7]_u.jpg$/01_u.jpg/;
		$one_c =~ s/0[1-7]_c.jpg$/01_c.jpg/;

		# do s 160
		$result = $img->scale(xpixels=>160);
		$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
		$result->write(file=>$one_s) or die "Cannot write image $one_s: ", $result->errstr, "\n";

		# do u 100
		$result = $img->scale(xpixels=>100);
		$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
		$result->write(file=>$one_u) or die "Cannot write image $one_u: ", $result->errstr, "\n";

		# do c 40
		$result = $img->scale(xpixels=>40);
		$result->filter(type=>'unsharpmask', stddev=>1, scale=>$sharp);
		$result->write(file=>$one_c) or die "Cannot write image $one_c: ", $result->errstr, "\n";
	}
	
	# 跳过最后一个参数
	$next = $next + 1;
	if ($next eq $last) {
		last;
	}
}