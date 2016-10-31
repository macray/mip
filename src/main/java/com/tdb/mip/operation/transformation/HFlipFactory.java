package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.pipeline.Pipeline;


public class HFlipFactory implements TransformationFactory<HFlip> {

    @Override
    public boolean match(OperationDescription description) {
        return "hflip".equals(description.getTextDescription());
    }

    @Override
    public HFlip build(OperationDescription description) {
        return new HFlip();
    }


}
