package com.tdb.mip.pipeline;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tdb.mip.App;
import com.tdb.mip.AppModule;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by mcy on 30/10/2016.
 */
public class BasicSVGPipelineTest {

    @Test
    public void generate_android_resources() throws IOException {
        Injector injector = Guice.createInjector(new AppModule("src/test/resources/android.test.config.properties"));
        App app = new App();
        injector.injectMembers(app);
        app.run();
    }
}
