package com.tdb.mip;

import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.service.reader.ImageReader;
import com.tdb.mip.service.reader.SVGImageReader;
import org.junit.Before;
import org.junit.Ignore;
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
//@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SVGImageReaderTest {

    @Mock
    private PipelineConfig config;

    private SVGImageReader imageReader;

    @Before
    public void setUp() {
        //when(config.inkscapePath()).thenReturn(Paths.get("C:/Program Files/Inkscape/inkscape.exe"));
        when(config.inkscapePath()).thenReturn(Paths.get("/Applications/Inkscape.app/Contents/Resources/bin/inkscape"));

        imageReader = new SVGImageReader(config);
    }

    @Test
    public void readSize() {
        ImageReader.Size size = imageReader.readSize(Paths.get("src/test/resources/svg/bear.svg"));
        assertThat(size.getWidth()).isEqualTo(248.662f);
        assertThat(size.getHeight()).isEqualTo(248.662f);
    }

    @Test
    public void readSize2() {
        ImageReader.Size size = imageReader.readSize(Paths.get("src/test/resources/svg/drag.svg"));
        assertThat(size.getWidth()).isEqualTo(512f);
        assertThat(size.getHeight()).isEqualTo(512f);
    }

}