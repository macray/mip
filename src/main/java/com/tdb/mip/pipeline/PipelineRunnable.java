package com.tdb.mip.pipeline;

import com.tdb.mip.pipeline.Pipeline;

import java.nio.channels.Pipe;

/**
 * Created by mcy on 28/10/2016.
 */
public interface PipelineRunnable {
    void run(Pipeline pipeline);
}
