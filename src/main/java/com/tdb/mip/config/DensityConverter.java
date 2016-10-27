package com.tdb.mip.config;

import com.tdb.mip.density.*;
import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcy on 27/10/2016.
 */
public class DensityConverter implements Converter<Density> {

    @Override
    public Density convert(Method method, String densityName) {
        List<Density> allDensities = new ArrayList<>();
        allDensities.addAll(AndroidDensity.ALL);
        allDensities.addAll(IOSDensity.ALL);
        allDensities.addAll(WindowsDensity.WINDOWS_PHONE);

        return DensityUtils.valueOf(allDensities, densityName);
    }
}
