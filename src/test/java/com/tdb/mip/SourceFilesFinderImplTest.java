package com.tdb.mip;

import com.tdb.mip.config.PipelineConfig;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by mcy on 27/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceFilesFinderImplTest {

    @Mock
    private PipelineConfig config;
    private SourceFilesFinderImpl service;

    @Before
    public void setUp() throws Exception {
        when(config.allowedExtensions()).thenReturn(Arrays.asList("svg", "png", "jpg"));
        when(config.ignoredFilesPatterns()).thenReturn(Arrays.asList(Pattern.compile("\\._.*")));
        when(config.recursiveLoad()).thenReturn(true);

        service = new SourceFilesFinderImpl(config);
    }

    @Test
    public void should_load_every_images_recursively() throws Exception {
        Path sourceDir = Paths.get("src/test/resources/sourcefilefinder");
        when(config.sourceDir()).thenReturn(sourceDir);

        List<Path> files = service.findFiles();
        assertThat(files).hasSize(3);
        assertThat(files).contains(sourceDir.resolve("empty.jpg"));
        assertThat(files).contains(sourceDir.resolve("empty.png"));
        assertThat(files).contains(sourceDir.resolve("folder1/folder2/empty.svg"));
    }

}