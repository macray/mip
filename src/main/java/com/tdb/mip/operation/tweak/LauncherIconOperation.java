package com.tdb.mip.operation.tweak;

import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.pipeline.Pipeline;

import java.util.*;

/**
 * Created by mcy on 27/10/2016.
 */
public class LauncherIconOperation implements ConfigurationOperation {

    @Override
    public void run(Pipeline pipeline) {
        switch (pipeline.getPlatform()) {
            case ANDROID:
                android(pipeline);
                break;

            //case IOS:
            //    ios(pipeline);
            //    break;

            default:
                throw new IllegalStateException(this.getClass().getName() + " dos not support <" + pipeline.getPlatform() + ">");
        }
    }

    private void android(Pipeline pipeline) {
        Set<Density> targetDensities = new HashSet<>(pipeline.getTargetDensities());
        // Add higher density for icon
        Density higherDensity = DensityUtils.findHighestDensity(targetDensities);
        Density higherDensityPlusOne = DensityUtils.closestUpperDensity(AndroidDensity.ALL, higherDensity);
        targetDensities.add(higherDensityPlusOne);

        pipeline.setTargetDensities(new ArrayList<>(targetDensities));

        int iconSizeInPx;
        if (higherDensityPlusOne.equals(AndroidDensity.XXXHDPI)) {
            iconSizeInPx = 192;
        } else if (higherDensityPlusOne.equals(AndroidDensity.XXXHDPI)) {
            iconSizeInPx = 144;
        } else {
            throw new IllegalStateException("Density <" + higherDensityPlusOne + "> not supported");
        }

        //pipeline.getFilters().add(new Resize(iconSizeInPx, iconSizeInPx, pipeline.getPixelRounding()));
    }
}
