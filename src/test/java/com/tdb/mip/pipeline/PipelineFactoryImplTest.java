package com.tdb.mip.pipeline;

import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.service.filenameparser.FileNameParserImpl;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.OperationFactoryManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;

/**
 * Created by mcy on 27/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PipelineFactoryImplTest {
    @Mock
    private PipelineConfig config;
    private FileNameParser fileNameParser;
    @Mock
    private OperationFactoryManager operationFactoryManager;
    @Mock
    private PixelDependentPipelineRunnable pixelDependentPipelineRunnable;
    @Mock
    private PixelIndependentPipelineRunnable pixelIndependentPipelineRunnable;

    private PipelineFactoryImpl service;

    @Before
    public void setUp() throws Exception {
        fileNameParser = new FileNameParserImpl();
        service = new PipelineFactoryImpl(config, fileNameParser, operationFactoryManager, pixelIndependentPipelineRunnable, pixelDependentPipelineRunnable);
    }

    @Test
    public void svg_file_use_independent_pixel_pipeline() throws IOException {
        Pipeline pipeline = service.build(Paths.get("file.svg"));
        assertThat(pipeline.getPipelineRunnable()).isExactlyInstanceOf(PixelIndependentPipelineRunnable.class);
    }

    @Test
    public void png_file_use_dependent_pixel_pipeline() throws IOException {
        Pipeline pipeline = service.build(Paths.get("file.png"));
        assertThat(pipeline.getPipelineRunnable()).isExactlyInstanceOf(PixelDependentPipelineRunnable.class);
    }

}