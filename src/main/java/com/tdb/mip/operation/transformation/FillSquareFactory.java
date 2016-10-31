package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.pipeline.Pipeline;

public class FillSquareFactory implements TransformationFactory<FillSquare> {

    @Override
    public boolean match(OperationDescription description) {
        return "fillsquare".equals(description.getTextDescription());
    }

    @Override
    public FillSquare build(OperationDescription description) {
        return new FillSquare();
    }

}
