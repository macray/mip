package com.tdb.mip;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.config.PipelineConfigChecker;
import com.tdb.mip.operation.Operation;
import com.tdb.mip.operation.OperationBuilder;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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
        List<Class<? extends OperationBuilder>> operationFactories = new ArrayList<>();
        new FastClasspathScanner("com.tdb.mip")
                .matchClassesImplementing(OperationBuilder.class, operationFactories::add)
                .scan();

        System.out.println(operationFactories);
        PipelineConfigChecker.check(pipelineConfig);

        List<Path> sourceFiles = sourceFilesFinder.findFiles();

    }
}
