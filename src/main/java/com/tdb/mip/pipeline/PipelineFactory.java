package com.tdb.mip.pipeline;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by mcy on 26/10/2016.
 */
public interface PipelineFactory {
    Pipeline build(Path file);
}
