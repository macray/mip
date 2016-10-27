package com.tdb.mip.operation;

import com.tdb.mip.exception.AmbiguousOperationDescriptionException;
import com.tdb.mip.exception.OperationDescriptionNotUnderstoodException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public class OperationsFactoryImplTest {
    private OperationsFactory operationsFactory;

    @Before
    public void setUp() throws Exception {
        operationsFactory = new OperationsFactoryImpl();
    }

    @Test
    public void build() throws Exception {
        List<Operation> operations = operationsFactory.build(Arrays.asList(
                new OperationDescription("icon"),
                new OperationDescription("w90"),
                new OperationDescription("blabla")
        ));

        Assertions.assertThat(operations).hasSize(3);
    }

    @Test(expected = OperationDescriptionNotUnderstoodException.class)
    public void when_operation_is_unknown_throw_exception() throws Exception {
        operationsFactory.build(Arrays.asList(
                new OperationDescription("unknown")
        ));
    }

    @Test(expected = AmbiguousOperationDescriptionException.class)
    public void when_operation_match_more_than_1_operation() throws Exception {
        operationsFactory.build(Arrays.asList(
                new OperationDescription("matchall")
        ));
    }

}