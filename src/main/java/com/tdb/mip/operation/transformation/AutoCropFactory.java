package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.pipeline.Pipeline;

public class AutoCropFactory implements TransformationFactory<AutoCrop> {

    @Override
    public boolean match(OperationDescription description) {
        return "autocrop".equals(description.getTextDescription());
    }

    @Override
    public AutoCrop build(OperationDescription description) {
        return new AutoCrop();
    }

}
