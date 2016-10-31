package com.tdb.mip.exception;

import com.tdb.mip.operation.OperationDescription;

public class OperationDescriptionNotUnderstoodException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationDescriptionNotUnderstoodException(OperationDescription operationDescription) {
        super("The operation description '" + operationDescription.getTextDescription() + "' is not understood by any registered OperationFactory");
    }
}
