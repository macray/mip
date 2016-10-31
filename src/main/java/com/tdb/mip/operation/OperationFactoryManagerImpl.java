package com.tdb.mip.operation;

import com.tdb.mip.exception.AmbiguousOperationDescriptionException;
import com.tdb.mip.exception.OperationDescriptionNotUnderstoodException;
import com.tdb.mip.operation.transformation.Transformation;
import com.tdb.mip.operation.transformation.TransformationFactory;
import com.tdb.mip.operation.tweak.ConfigurationOperation;
import com.tdb.mip.operation.tweak.ConfigurationOperationFactory;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
@Slf4j
public class OperationFactoryManagerImpl implements OperationFactoryManager {

    private List<TransformationFactory> transformationOperationsFactories;
    private List<ConfigurationOperationFactory> configurationOperationsFactories;

    private OperationFactoriesProvider operationFactoriesProvider;

    @Inject
    public OperationFactoryManagerImpl(OperationFactoriesProvider operationFactoriesProvider) {
        this.operationFactoriesProvider = operationFactoriesProvider;

        this.transformationOperationsFactories = operationFactoriesProvider.findTransformationFactories();
        this.configurationOperationsFactories = operationFactoriesProvider.findConfigurationFactories();
        log.info(this.transformationOperationsFactories.size() + " " + TransformationFactory.class.getSimpleName() + " have been registered.");
        log.info(this.configurationOperationsFactories.size() + " " + ConfigurationOperationFactory.class.getSimpleName() + " have been registered.");
    }

    @Override
    public Operations build(List<OperationDescription> descriptions) {
        Operations operations = new Operations();

        for (OperationDescription description : descriptions) {
            buildOperation(operations, description);
        }

        return operations;
    }

    private void buildOperation(Operations operations, OperationDescription description) {
        int factoryFoundCounter = 0;
        for (TransformationFactory factory : transformationOperationsFactories) {
            if (factory.match(description)) {
                factoryFoundCounter++;
                Transformation operation = factory.build(description);
                operations.getTransformations().add(operation);
            }
        }

        for (ConfigurationOperationFactory factory : configurationOperationsFactories) {
            if (factory.match(description)) {
                factoryFoundCounter++;
                ConfigurationOperation operation = factory.build(description);
                operations.getConfigurations().add(operation);
            }
        }

        if (factoryFoundCounter == 0) {
            throw new OperationDescriptionNotUnderstoodException(description);
        }

        if (factoryFoundCounter > 1) {
            throw new AmbiguousOperationDescriptionException(description);
        }
    }
}
