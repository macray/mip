package com.tdb.mip;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.tdb.mip.config.PipelineConfig;
import com.tdb.mip.operation.OperationsFactory;
import com.tdb.mip.operation.OperationsFactoryImpl;
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
        bind(OperationsFactory.class).to(OperationsFactoryImpl.class).in(Singleton.class);
        bind(FileNameParser.class).to(FileNameParserImpl.class).in(Singleton.class);
    }
}
