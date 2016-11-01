package com.tdb.mip.operation.tweak;

import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.operation.transformation.Resize;
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

            case IOS:
                ios(pipeline);
                break;

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
        
        pipeline.getOperations().getTransformations().add(new Resize(iconSizeInPx, iconSizeInPx, pipeline.getPixelRounding()));
    }

    private void ios(Pipeline pipeline) {
        List<Density> targetDensities = new ArrayList<>();
        targetDensities.add(new Density("icon-120", "120", 0.1171875f));
        targetDensities.add(new Density("icon-180", "180", 0.17578125f));
        targetDensities.add(new Density("icon-76", "76", 0.07421875f));
        targetDensities.add(new Density("icon-152", "152", 0.1484375f));
        targetDensities.add(new Density("icon-167", "167", 0.1630859375f));
        targetDensities.add(new Density("icon-40", "40", 0.0390625f));
        targetDensities.add(new Density("icon-80", "80", 0.078125f));

        // the ratio has been computed based on 1024x1024 resolution
        pipeline.getOperations().getTransformations().add(new Resize(1024, 1024, pipeline.getPixelRounding()));
        pipeline.setSourceDensity(new Density("1", "", 1f));
    }
}
