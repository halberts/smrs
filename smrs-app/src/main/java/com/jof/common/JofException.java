package com.jof.common;

public class JofException extends RuntimeException{
    /**
     * 
     */
    private static final long serialVersionUID = 3313298682442016635L;
    private String code;
    private Object source;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JofException(Throwable cause) {
        super(cause);
    }
    
    public JofException(String code,Throwable cause){
        super(cause);
        this.code = code;        
    }

    public JofException(String code,String message,Throwable cause){
        super(message, cause);
        this.code = code;        
    }
    
    public JofException(String code){
        //super(message, cause);
        this.code = code;        
    }

    public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	@Override
    public String toString() {
        return "  code=" + code ;
    }
   
  
    
}
