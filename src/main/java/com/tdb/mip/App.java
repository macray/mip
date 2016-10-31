package com.tdb.mip;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.config.PipelineConfigChecker;
import com.tdb.mip.pipeline.Pipeline;
import com.tdb.mip.pipeline.PipelineExecutor;
import com.tdb.mip.pipeline.PipelineFactory;
import com.tdb.mip.service.sourcefilefinder.SourceFilesFinder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
@Slf4j
public class App {

    @Inject
    PipelineConfig pipelineConfig;

    @Inject
    SourceFilesFinder sourceFilesFinder;

    @Inject
    PipelineFactory pipelineFactory;

    public static void main(String[] args) throws IOException {
        log.info("Mobile Image Pipeline starting ...");

        String[] defaultConfig = {null};
        String[] configFiles = args.length > 0 ? args : defaultConfig;

        for (String configFile : configFiles) {
            log.info("Will use config file <" + configFile + ">");
            Validate.notBlank(configFile, "Configuration file path must be defined");

            Injector injector = Guice.createInjector(new AppModule(configFile));
            App app = injector.getInstance(App.class);
            app.run();
        }
    }

    public void run() {
        PipelineConfigChecker.check(pipelineConfig);

        List<Path> sourceFiles = sourceFilesFinder.findFiles();
        List<Pipeline> pipelines = createPipelines(sourceFiles);
        runAndWaitForCompletion(pipelines);
    }

    private List<Pipeline> createPipelines(List<Path> sourceFiles) {
        List<Pipeline> pipelines = new LinkedList<>();
        for (Path file : sourceFiles) {
            Pipeline pipeline = pipelineFactory.build(file);
            pipelines.add(pipeline);
        }
        return pipelines;
    }

    private void runAndWaitForCompletion(List<Pipeline> pipelines) {
        PipelineExecutor executor = new PipelineExecutor();
        pipelines.forEach(executor::execute);
        executor.awaitTermination();
    }
}
