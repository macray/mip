package com.tdb.mip.pipeline;

import com.tdb.mip.Platform;
import com.tdb.mip.density.Density;
import com.tdb.mip.operation.Operation;
import com.tdb.mip.writer.ImageWriter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
@Getter
@Setter
@Slf4j
public class Pipeline implements Runnable {
    private List<Density> targetDensities;
    private List<Operation> operations;
    private ImageWriter imageWriter;
    private Platform platform;

    @Override
    @SneakyThrows
    public void run() {
        for(Density density : targetDensities){
            for(Operation operation : operations){
                operation.process(this);
            }
            imageWriter.save(null, null);
        }
    }
}
