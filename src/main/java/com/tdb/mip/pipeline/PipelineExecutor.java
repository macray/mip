package com.tdb.mip.pipeline;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by mcy on 29/10/2016.
 */
@Slf4j
public class PipelineExecutor {
    private ExecutorService executorService;

    public PipelineExecutor() {
        executorService = Executors.newFixedThreadPool(4);
    }

    public void execute(Pipeline pipeline) {
        executorService.submit(new RunnableLogException(pipeline));
    }

    public void awaitTermination() {
        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Take too much time. Processing has been aborted", e);
        }
    }

    private class RunnableLogException implements Runnable {

        private Pipeline pipeline;

        public RunnableLogException(Pipeline pipeline) {
            this.pipeline = pipeline;
        }

        @Override
        public void run() {
            try {
                pipeline.getPipelineRunnable().run(pipeline);
            } catch (Exception e) {
                log.error("Error when processing <" + pipeline.getSourceFile() + ">", e);
            }
        }
    }
}
