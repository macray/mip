package com.tdb.mip.operation;

import com.google.inject.Injector;
import com.tdb.mip.operation.transformation.TransformationFactory;
import com.tdb.mip.operation.tweak.ConfigurationOperationFactory;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcy on 27/10/2016.
 */
@Slf4j
public class ClasspathScanOperationFactoriesProvider implements OperationFactoriesProvider {

    public static final String PACKAGE_NAME = "com.tdb.mip";

    private Injector injector;

    @Inject
    public ClasspathScanOperationFactoriesProvider(Injector injector) {
        this.injector = injector;
    }

    @Override
    public List<TransformationFactory> findTransformationFactories() {
        List<TransformationFactory> factories = new ArrayList<>();
        new FastClasspathScanner(PACKAGE_NAME)
                .matchClassesImplementing(TransformationFactory.class, clazz -> {
                    if (!clazz.isAnonymousClass()) {
                        try {
                            log.info("Found factory <" + clazz.getName() + ">");
                            TransformationFactory factory = injector.getInstance(clazz);
                            factories.add(factory);
                        } catch (Exception e) {
                            log.error("Failed to instantiate factory <" + clazz.getName() + ">");
                        }
                    }
                })
                .scan();

        return factories;
    }

    @Override
    public List<ConfigurationOperationFactory> findConfigurationFactories() {
        List<ConfigurationOperationFactory> factories = new ArrayList<>();
        new FastClasspathScanner(PACKAGE_NAME)
                .matchClassesImplementing(ConfigurationOperationFactory.class, clazz -> {
                    if (!clazz.isAnonymousClass()) {
                        try {
                            log.info("Found factory <" + clazz.getName() + ">");
                            ConfigurationOperationFactory factory = injector.getInstance(clazz);
                            factories.add(factory);
                        } catch (Exception e) {
                            log.error("Failed to instantiate factory <" + clazz.getName() + ">");
                        }
                    }
                })
                .scan();

        return factories;
    }
}
