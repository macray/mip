package com.tdb.mip.pipeline;

import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.operation.Operation;
import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.writer.ImageWriter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by mcy on 26/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PipelineTest {

    @Mock
    ImageWriter imageWriter;

    private Pipeline pipeline;

    @Before
    public void setUp() throws Exception {
        pipeline = new Pipeline();
        pipeline.setImageWriter(imageWriter);
        pipeline.setTargetDensities(Arrays.asList(AndroidDensity.HDPI, AndroidDensity.MDPI));
    }

    @Test
    public void pipeline_is_dedicated_to_one_platform() {
        assertThat(pipeline).hasFieldOrProperty("platform");
    }

    @Test
    public void pipeline_generate_one_image_per_density() throws IOException {
        pipeline.run();

        int wantedNumberOfInvocations = pipeline.getTargetDensities().size();
        verify(imageWriter, times(wantedNumberOfInvocations)).save(any(), any(File.class));
    }

    @Test
    public void pipeline_execute_all_operations_before_saving_image() {
        Operation op1 = mock(Operation.class);
        Operation op2 = mock(Operation.class);
        Operation op3 = mock(Operation.class);

        pipeline.setOperations(Arrays.asList(op1, op2, op3));
        pipeline.run();

        int wantedNumberOfInvocations = pipeline.getTargetDensities().size();

        verify(op1, times(wantedNumberOfInvocations)).process(pipeline);
        verify(op2, times(wantedNumberOfInvocations)).process(pipeline);
        verify(op3, times(wantedNumberOfInvocations)).process(pipeline);
    }

}