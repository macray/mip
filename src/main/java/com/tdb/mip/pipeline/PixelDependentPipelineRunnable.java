package com.tdb.mip.pipeline;

import com.tdb.mip.service.reader.PixelDependentImageReader;
import com.tdb.mip.density.Density;
import com.tdb.mip.operation.tweak.ConfigurationOperation;
import com.tdb.mip.operation.transformation.Transformation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * Created by mcy on 29/10/2016.
 */
@Slf4j
public class PixelDependentPipelineRunnable implements PipelineRunnable {

    private PixelDependentImageReader imageReader;

    @Inject
    public PixelDependentPipelineRunnable(PixelDependentImageReader imageReader) {
        this.imageReader = imageReader;
    }

    @Override
    @SneakyThrows
    public void run(Pipeline pipeline) {
        log.info("Running <" + pipeline.getSourceFile().getFileName() + "> ...");

        // load image in fullSize
        BufferedImage bufferedImage = imageReader.read(pipeline.getSourceFile());

        // run operations
        for (ConfigurationOperation operation : pipeline.getOperations().getConfigurations()) {
            operation.run(pipeline);
        }
        for (Transformation operation : pipeline.getOperations().getTransformations()) {
            bufferedImage = operation.applyTo(bufferedImage);
        }

        // generate image in target densities
        for (Density density : pipeline.getTargetDensities()) {
            // resize to target density
            // TODO: resize
            Path outputFileName = pipeline.getOutputFileNameBuilder().buildOutputFileName(pipeline.getOutputDir(), density, pipeline.getSourceFileInfo());
            pipeline.getImageWriter().save(bufferedImage, outputFileName);
        }
    }
}
