package com.yougou.kaidian.image.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageBean {

	private List<File> scuessFile;
	private List<File> failFile;
	
	public MessageBean() {
		scuessFile=new ArrayList<File>();
		failFile=new ArrayList<File>();
	}
	
	public List<File> getScuessFile() {
		return scuessFile;
	}
	public void setScuessFile(List<File> scuessFile) {
		this.scuessFile = scuessFile;
	}
	public List<File> getFailFile() {
		return failFile;
	}
	public void setFailFile(List<File> failFile) {
		this.failFile = failFile;
	}
}
