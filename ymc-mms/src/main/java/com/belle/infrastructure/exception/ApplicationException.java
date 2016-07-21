package com.belle.infrastructure.exception;

/**
 * <p>Title: 异常处理类</p>
 * @version 1.0
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public Object obj = null;
	public Exception ex = null;
    public String className = "";
    public ApplicationException() {}

    public ApplicationException(String message,Object obj) {
        super(message);
        this.obj = obj;
    }

    public ApplicationException(Exception ex) {
        super(ex);
        this.ex = ex;
    }
    
    public ApplicationException(Exception ex, String className) {
        super(ex);
        this.ex = ex;
    }

	public Object getObj() {
		return obj;
	}
    
    
}
