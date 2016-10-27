package com.tdb.mip.pipeline;

import com.tdb.mip.FileNameParser;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.Operation;
import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.operation.OperationsFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by mcy on 27/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PipelineFactoryImplTest {
    @Mock
    private PipelineConfig config;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private FileNameParser fileNameParser;
    @Mock
    private OperationsFactory operationsFactory;

    private PipelineFactoryImpl service;

    @Before
    public void setUp() throws Exception {
        //when(fileNameParser.parse(anyString())).thenReturn(new FileNameParser.FileNameInfo());
        //when(operationsFactory.build(anyListOf(OperationDescription.class))).thenReturn(new ArrayList<>());

        service = new PipelineFactoryImpl(config, fileNameParser, operationsFactory);
    }

    @Test
    public void check_something() throws Exception {
        Pipeline pipeline = service.build(Paths.get("file.svg"));
        assertThat(pipeline.getOperations()).hasSize(0);
        assertThat(pipeline.getPlatform()).isNotNull();
    }

}