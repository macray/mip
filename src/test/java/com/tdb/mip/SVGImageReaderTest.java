package com.tdb.mip;

import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.service.reader.ImageReader;
import com.tdb.mip.service.reader.SVGImageReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by mcy on 31/10/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SVGImageReaderTest {

    @Mock
    private PipelineConfig config;

    private SVGImageReader imageReader;

    @Before
    public void setUp() {
        when(config.inkscapePath()).thenReturn("C:/Program Files/Inkscape/inkscape.exe");
        imageReader = new SVGImageReader(config);
    }

    @Test
    public void readSize() {
        ImageReader.Size size = imageReader.readSize(Paths.get("src/test/resources/svg/bear.svg"));
        assertThat(size.getWidth()).isEqualTo(248.66274f);
        assertThat(size.getHeight()).isEqualTo(149.58707f);
    }

}