package com.tdb.mip.operation;

import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public interface OperationsFactory {
    List<Operation> build(List<OperationDescription> descriptions);
}
