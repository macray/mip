package com.tdb.mip.config;

import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by mcy on 27/10/2016.
 */
public class PatternConverter implements Converter<Pattern> {

    @Override
    public Pattern convert(Method method, String s) {
        return Pattern.compile(s);
    }
}
