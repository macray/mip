package com.tdb.mip.operation;

import com.tdb.mip.exception.AmbiguousOperationDescriptionException;
import com.tdb.mip.exception.OperationDescriptionNotUnderstoodException;
import com.tdb.mip.operation.transformation.Transformation;
import com.tdb.mip.operation.transformation.TransformationFactory;
import com.tdb.mip.operation.tweak.ConfigurationOperation;
import com.tdb.mip.operation.tweak.ConfigurationOperationFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static com.tdb.mip.operation.OperationFactoryManager.*;

/**
 * Created by mcy on 26/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class OperationFactoryManagerImplTest {

    private OperationFactoriesProvider provider;

    private OperationFactoryManager operationFactoryManager;

    @Before
    public void setUp() throws Exception {
        provider = new OperationFactoriesProvider() {
            @Override
            public List<TransformationFactory> findTransformationFactories() {
                return Arrays.asList(new TransformationFactory[]{new TransformationFactory() {
                    @Override
                    public boolean match(OperationDescription description) {
                        return description.getTextDescription().equals("matchall") || description.getTextDescription().contains("t1");
                    }

                    @Override
                    public Transformation build(OperationDescription description) {
                        return new Transformation() {
                            @Override
                            public BufferedImage applyTo(BufferedImage image) {
                                return null;
                            }

                            @Override
                            public Transformation copy() {
                                return null;
                            }
                        };
                    }
                }, new TransformationFactory() {
                    @Override
                    public boolean match(OperationDescription description) {
                        return description.getTextDescription().equals("matchall") || description.getTextDescription().contains("t2");
                    }

                    @Override
                    public Transformation build(OperationDescription description) {
                        return new Transformation() {
                            @Override
                            public BufferedImage applyTo(BufferedImage image) {
                                return null;
                            }

                            @Override
                            public Transformation copy() {
                                return null;
                            }
                        };
                    }
                }});
            }

            @Override
            public List<ConfigurationOperationFactory> findConfigurationFactories() {
                return Arrays.asList(
                        new ConfigurationOperationFactory() {
                            @Override
                            public boolean match(OperationDescription description) {
                                return description.getTextDescription().equals("matchall") || description.getTextDescription().contains("c1");
                            }

                            @Override
                            public ConfigurationOperation build(OperationDescription description) {
                                return pipeline1 -> {
                                };
                            }
                        }
                );
            }
        };
        operationFactoryManager = new OperationFactoryManagerImpl(provider);
    }

    @Test
    public void build() throws Exception {
        Operations ops = operationFactoryManager.build(Arrays.asList(
                new OperationDescription("t1"),
                new OperationDescription("c1"),
                new OperationDescription("t2")
        ));

        Assertions.assertThat(ops.transformations).hasSize(2);
        Assertions.assertThat(ops.configurations).hasSize(1);
    }

    @Test(expected = OperationDescriptionNotUnderstoodException.class)
    public void should_throw_exception_when_description_does_not_match_any_factory() throws Exception {
        operationFactoryManager.build(Arrays.asList(
                new OperationDescription("unknown")
        ));
    }

    @Test(expected = AmbiguousOperationDescriptionException.class)
    public void should_throw_exception_when_description_match_more_than_1_operation() throws Exception {
        operationFactoryManager.build(Arrays.asList(
                new OperationDescription("matchall")
        ));
    }

}