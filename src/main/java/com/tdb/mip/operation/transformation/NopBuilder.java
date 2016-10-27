package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationBuilder;
import com.tdb.mip.operation.OperationDescription;

/**
 * Created by mcy on 27/10/2016.
 */
public class NopBuilder implements OperationBuilder<NopOperation> {
    @Override
    public boolean match(OperationDescription description) {
        return false;
    }

    @Override
    public NopOperation build(OperationDescription description) {
        return null;
    }
}
