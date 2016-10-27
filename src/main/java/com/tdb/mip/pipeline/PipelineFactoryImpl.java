package com.tdb.mip.pipeline;

import com.tdb.mip.FileNameParser;
import com.tdb.mip.FileNameParser.FileNameInfo;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.Operation;
import com.tdb.mip.operation.OperationsFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public class PipelineFactoryImpl implements PipelineFactory {

    private PipelineConfig config;
    private FileNameParser fileNameParser;
    private OperationsFactory operationsFactory;

    public PipelineFactoryImpl(PipelineConfig config, FileNameParser fileNameParser, OperationsFactory operationsFactory) {
        this.config = config;
        this.fileNameParser = fileNameParser;
        this.operationsFactory = operationsFactory;
    }

    @Override
    public Pipeline build(Path file) {
        String fileName = file.getFileName().toString();
        FileNameInfo info = fileNameParser.parse(fileName);

        List<Operation> operations = operationsFactory.build(info.getOperationDescriptions());

        Pipeline pipeline = new Pipeline();
        pipeline.setOperations(operations);
        pipeline.setPlatform(config.platform());
        pipeline.setTargetDensities(config.targetDensities());

        return pipeline;
    }
}
