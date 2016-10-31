package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;


public class VFlipFactory implements TransformationFactory<VFlip> {

    @Override
    public boolean match(OperationDescription description) {
        return "vflip".equals(description.getTextDescription());
    }

    @Override
    public VFlip build(OperationDescription description) {
        return new VFlip();
    }

}
