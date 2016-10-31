package com.tdb.mip.exception;

public class TooManyResizeTransformationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TooManyResizeTransformationException() {
        super("Due to an implementation limitation, only 1 Resize filter is allowed on a same image");
    }
}
