package com.tdb.mip.operation;

import com.tdb.mip.FileNameParser;
import com.tdb.mip.FileNameParserImpl;

import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public class OperationsFactoryImpl implements OperationsFactory {

    @Override
    public List<Operation> build(List<OperationDescription> descriptions) {
        for (OperationDescription description : descriptions) {
            //
        }

        return null;
    }
}
