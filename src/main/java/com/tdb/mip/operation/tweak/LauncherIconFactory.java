package com.tdb.mip.operation.tweak;

import com.tdb.mip.operation.OperationDescription;

/**
 * Created by mcy on 27/10/2016.
 */
public class LauncherIconFactory implements ConfigurationOperationFactory<LauncherIconOperation> {
    @Override
    public boolean match(OperationDescription description) {
        return description.equals("launcher_icon");
    }

    @Override
    public LauncherIconOperation build(OperationDescription description) {
        return new LauncherIconOperation();
    }
}
