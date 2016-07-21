package com.belle.infrastructure.exception;

/**
 * <p>Title: 前台友好异常处理类</p>
 * @version 1.0
 */
public class MallfriendlyException extends Exception {
	private static final long serialVersionUID = 1L;

	public Exception ex = null;
    public String className = "";
    public MallfriendlyException() {}

    public MallfriendlyException(String message) {
        super(message);
    }

    public MallfriendlyException(Exception ex) {
        super(ex);
        this.ex = ex;
    }

    public MallfriendlyException(Exception ex, String className) {
        super(ex);
        this.ex = ex;
    }
}
