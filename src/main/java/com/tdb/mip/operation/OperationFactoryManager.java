package com.tdb.mip.operation;

import com.tdb.mip.operation.transformation.Transformation;
import com.tdb.mip.operation.tweak.ConfigurationOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public interface OperationFactoryManager {
    Operations build(List<OperationDescription> descriptions);

    @Getter
    @Setter
    class Operations {
        List<Transformation> transformations = new LinkedList<>();
        List<ConfigurationOperation> configurations = new LinkedList<>();
    }
}
