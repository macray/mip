package com.tdb.mip.config;

import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Created by mcy on 27/10/2016.
 */
public class PathConverter implements Converter<Path> {

    @Override
    public Path convert(Method method, String s) {
        return Paths.get(s);
    }
}
