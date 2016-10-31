package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;

/**
 * Created by mcy on 28/10/2016.
 */
public interface TransformationFactory<T extends Transformation> {
    boolean match(OperationDescription description);

    T build(OperationDescription description);
}
