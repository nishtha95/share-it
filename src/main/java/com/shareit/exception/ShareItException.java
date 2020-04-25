package com.shareit.exception;

public class ShareItException  extends Exception
{

	private static final long serialVersionUID = -5465086918919378791L;

	public ShareItException() {
		super();
	}
	public ShareItException(String message, Throwable cause) {
        super(message, cause);
        
    }

    public ShareItException(String message) {
        super(message);
        
    }

    public ShareItException(Throwable cause) {
        super(cause);
        
    }

}
