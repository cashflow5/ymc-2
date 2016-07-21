package com.yougou.api.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * HTTP 响应输出流
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceServletOutputStream extends ServletOutputStream {

	private final OutputStream outputStream;
	private final ByteArrayOutputStream cloneStream;

	public ApplicationProgramInterfaceServletOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.cloneStream = new ByteArrayOutputStream(1024);
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
		cloneStream.write(b);
	}

	public byte[] toByteArray() {
		return cloneStream.toByteArray();
	}
}
