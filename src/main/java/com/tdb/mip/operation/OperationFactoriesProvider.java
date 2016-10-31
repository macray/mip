package com.tdb.mip.operation;

import com.tdb.mip.operation.transformation.TransformationFactory;
import com.tdb.mip.operation.tweak.ConfigurationOperationFactory;

import java.util.List;

/**
 * Created by mcy on 27/10/2016.
 */
public interface OperationFactoriesProvider {
    List<TransformationFactory> findTransformationFactories();
    List<ConfigurationOperationFactory> findConfigurationFactories();
}
