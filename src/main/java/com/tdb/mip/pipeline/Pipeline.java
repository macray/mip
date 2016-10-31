package com.tdb.mip.pipeline;

import com.tdb.mip.service.filenamebuilder.OutputFileNameBuilder;
import com.tdb.mip.model.Platform;
import com.tdb.mip.density.Density;
import com.tdb.mip.service.pixelrounding.PixelRounding;
import com.tdb.mip.service.writer.ImageWriter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;

import static com.tdb.mip.service.filenameparser.FileNameParser.*;
import static com.tdb.mip.operation.OperationFactoryManager.*;

/**
 * Created by mcy on 26/10/2016.
 */
@Getter
@Setter
@NoArgsConstructor
public class Pipeline {
    // add working size
    private List<Density> targetDensities;
    private Operations operations;
    private ImageWriter imageWriter;
    private Platform platform;
    private Path sourceFile;
    private FileNameInfo sourceFileInfo;
    private PipelineRunnable pipelineRunnable;
    private OutputFileNameBuilder outputFileNameBuilder;
    private Path outputDir;
    private Density sourceDensity;
    private PixelRounding pixelRounding;
}
