package com.tdb.mip;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.tdb.mip.service.filenameparser.FileNameParser;
import com.tdb.mip.service.filenameparser.FileNameParserImpl;
import com.tdb.mip.service.sourcefilefinder.SourceFilesFinder;
import com.tdb.mip.service.sourcefilefinder.SourceFilesFinderImpl;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.ClasspathScanOperationFactoriesProvider;
import com.tdb.mip.operation.OperationFactoriesProvider;
import com.tdb.mip.operation.OperationFactoryManager;
import com.tdb.mip.operation.OperationFactoryManagerImpl;
import com.tdb.mip.pipeline.PipelineFactory;
import com.tdb.mip.pipeline.PipelineFactoryImpl;
import org.aeonbits.owner.ConfigFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppModule extends AbstractModule {
    private Properties properties;

    public AppModule(String configPath) throws IOException {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config <" + configPath + ">");
        }
    }

    @Override
    protected void configure() {
        bind(PipelineConfig.class).toInstance(ConfigFactory.create(PipelineConfig.class, properties));

        bind(SourceFilesFinder.class).to(SourceFilesFinderImpl.class).in(Singleton.class);
        bind(FileNameParser.class).to(FileNameParserImpl.class).in(Singleton.class);
        bind(OperationFactoryManager.class).to(OperationFactoryManagerImpl.class).in(Singleton.class);
        bind(OperationFactoriesProvider.class).to(ClasspathScanOperationFactoriesProvider.class).in(Singleton.class);
        bind(PipelineFactory.class).to(PipelineFactoryImpl.class).in(Singleton.class);
    }
}
