package com.tdb.mip.pipeline;

import com.tdb.mip.service.reader.ImageReader;
import com.tdb.mip.service.reader.SVGImageReader;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.exception.TooManyResizeTransformationException;
import com.tdb.mip.operation.tweak.ConfigurationOperation;
import com.tdb.mip.operation.transformation.Transformation;
import com.tdb.mip.operation.transformation.Resize;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by mcy on 28/10/2016.
 */
@Slf4j
public class PixelIndependentPipelineRunnable implements PipelineRunnable {

    private ImageReader imageReader;

    @Inject
    public PixelIndependentPipelineRunnable(SVGImageReader imageReader) {
        this.imageReader = imageReader;
        Validate.isTrue(this.imageReader.supportResizing(), "Reader must support resizing");
    }

    @Override
    @SneakyThrows
    public void run(Pipeline pipeline) {
        log.info("Running <" + pipeline.getSourceFile().getFileName() + "> ...");

        // need to run configurationOperations
        for (ConfigurationOperation configurationOperation : pipeline.getOperations().getConfigurations()) {
            configurationOperation.run(pipeline);
        }

        // because can change targetDensities
        for (Density density : pipeline.getTargetDensities()) {
            // compute working size
            int workingHeight = 0;
            int workingWidth = 0;

            int finalSizeHeight;
            int finalSizeWidth;

            // TODO: working size

            ImageReader.Size size = imageReader.readSize(pipeline.getSourceFile());

            Resize resize = removeResizeTransformationFromPipeline(pipeline);
            float densityRatio = DensityUtils.getRatio(pipeline.getSourceDensity(), density);
            if (resize != null) {
                resize.updateTargetWidthOrHeightIfNeeded(size.getWidth(), size.getHeight());
                finalSizeHeight = pipeline.getPixelRounding().round((float) resize.getH() * densityRatio);
                finalSizeWidth = pipeline.getPixelRounding().round((float) resize.getW() * densityRatio);
            } else {
                finalSizeHeight = pipeline.getPixelRounding().round(size.getHeight() * densityRatio);
                finalSizeWidth = pipeline.getPixelRounding().round(size.getWidth() * densityRatio);
            }

            // load image in final size
            imageReader.setOutputSize(finalSizeWidth, finalSizeHeight);
            BufferedImage bufferedImage = imageReader.read(pipeline.getSourceFile());

            // run operations
            bufferedImage = applyTransformations(pipeline, bufferedImage);

            // generate image in target density
            Path output = pipeline.getOutputFileNameBuilder().buildOutputFileName(pipeline.getOutputDir(), density, pipeline.getSourceFileInfo());
            pipeline.getImageWriter().save(bufferedImage, output);
        }
    }

    private BufferedImage applyTransformations(Pipeline pipeline, BufferedImage image) {
        for (Transformation transformation : pipeline.getOperations().getTransformations()) {
            image = transformation.applyTo(image);
        }
        return image;
    }

    private Resize removeResizeTransformationFromPipeline(Pipeline pipeline) {
        Iterator<Transformation> iterator = pipeline.getOperations().getTransformations().iterator();
        Resize resize = null;
        while (iterator.hasNext()) {
            Transformation transformation = iterator.next();
            if (transformation instanceof Resize) {
                boolean alreadyFound = resize != null;
                if (alreadyFound) {
                    throw new TooManyResizeTransformationException();
                }
                resize = (Resize) transformation;
                iterator.remove();
            }
        }

        return resize;
    }
}