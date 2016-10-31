package com.tdb.mip.operation.transformation;

import com.tdb.mip.pipeline.Pipeline;

public interface FilterFactory<T> {

    public boolean canBuild(FilterDescription filterDescription);

    public T build(FilterDescription filterDescription, Pipeline pipeline);

}
