package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import com.tdb.mip.pipeline.Pipeline;


public class BlackAndWhiteFactory implements TransformationFactory<BlackAndWhite> {

    @Override
    public boolean match(OperationDescription description) {
        return "bw".equals(description.getTextDescription());
    }

    @Override
    public BlackAndWhite build(OperationDescription description) {
        return new BlackAndWhite();
    }


}
