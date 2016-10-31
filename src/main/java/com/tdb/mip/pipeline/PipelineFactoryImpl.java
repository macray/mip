package com.tdb.mip.pipeline;

import com.tdb.mip.service.filenamebuilder.AndroidOutputFileNameBuilder;
import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.service.filenameparser.FileNameParser.FileNameInfo;
import com.tdb.mip.service.filenamebuilder.IOSOutFileNameBuilder;
import com.tdb.mip.service.filenamebuilder.OutputFileNameBuilder;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.OperationFactoryManager;
import com.tdb.mip.service.pixelrounding.PixelRoundingHalfDown;
import com.tdb.mip.service.writer.PNGImageWriter;

import javax.inject.Inject;
import java.nio.file.Path;

import static com.tdb.mip.operation.OperationFactoryManager.*;

/**
 * Created by mcy on 26/10/2016.
 */
public class PipelineFactoryImpl implements PipelineFactory {

    private PipelineConfig config;
    private FileNameParser fileNameParser;
    private OperationFactoryManager operationFactoryManager;
    private PixelIndependentPipelineRunnable pixelIndependentPipelineRunnable;
    private PixelDependentPipelineRunnable pixelDependentPipelineRunnable;

    @Inject
    public PipelineFactoryImpl(PipelineConfig config,
                               FileNameParser fileNameParser,
                               OperationFactoryManager operationFactoryManager,
                               PixelIndependentPipelineRunnable pixelIndependentPipelineRunnable,
                               PixelDependentPipelineRunnable pixelDependentPipelineRunnable) {
        this.config = config;
        this.fileNameParser = fileNameParser;
        this.operationFactoryManager = operationFactoryManager;
        this.pixelDependentPipelineRunnable = pixelDependentPipelineRunnable;
        this.pixelIndependentPipelineRunnable = pixelIndependentPipelineRunnable;
    }

    @Override
    public Pipeline build(Path file) {
        String fileName = file.getFileName().toString();
        FileNameInfo info = fileNameParser.parse(fileName);

        Pipeline pipeline = new Pipeline();
        pipeline.setSourceFile(file);
        pipeline.setSourceFileInfo(info);
        pipeline.setPlatform(config.platform());
        pipeline.setImageWriter(new PNGImageWriter());
        pipeline.setTargetDensities(config.targetDensities());
        pipeline.setOutputDir(config.targetDir());
        pipeline.setSourceDensity(config.sourceDensity());
        pipeline.setPixelRounding(new PixelRoundingHalfDown());

        OutputFileNameBuilder outputFileNameBuilder;
        switch (config.platform()) {
            case ANDROID:
                outputFileNameBuilder = new AndroidOutputFileNameBuilder();
                break;

            case IOS:
                outputFileNameBuilder = new IOSOutFileNameBuilder();
                break;

            default:
                throw new IllegalArgumentException("Platform <" + config.platform() + "> not yet supported");
        }
        pipeline.setOutputFileNameBuilder(outputFileNameBuilder);

        if (isPixelIndependentSourceFile(info)) {
            pipeline.setPipelineRunnable(pixelIndependentPipelineRunnable);
        } else {
            pipeline.setPipelineRunnable(pixelDependentPipelineRunnable);
        }

        Operations operations = operationFactoryManager.build(info.getOperationDescriptions());
        pipeline.setOperations(operations);

        return pipeline;
    }

    private boolean isPixelIndependentSourceFile(FileNameInfo info) {
        return "svg".equals(info.getExtension());
    }
}
