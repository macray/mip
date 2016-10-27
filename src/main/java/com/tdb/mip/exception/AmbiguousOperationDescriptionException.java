package com.tdb.mip.exception;


import com.tdb.mip.operation.Operation;
import com.tdb.mip.operation.OperationDescription;

public class AmbiguousOperationDescriptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AmbiguousOperationDescriptionException(OperationDescription operationDescription) {
        super("At least 2 different operations can understand the following description <" + operationDescription.getRawDescription() + ">");
    }
}
