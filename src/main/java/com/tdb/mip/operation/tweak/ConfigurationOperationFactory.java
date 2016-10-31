package com.tdb.mip.operation.tweak;

import com.tdb.mip.operation.OperationDescription;

/**
 * Created by mcy on 28/10/2016.
 */
public interface ConfigurationOperationFactory<T extends ConfigurationOperation> {
    boolean match(OperationDescription description);

    T build(OperationDescription description);
}
