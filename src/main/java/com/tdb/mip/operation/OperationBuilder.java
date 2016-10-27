package com.tdb.mip.operation;

/**
 * Created by mcy on 27/10/2016.
 */
public interface OperationBuilder<T extends Operation> {
    boolean match(OperationDescription description);

    T build(OperationDescription description);
}
