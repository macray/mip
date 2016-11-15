package com.tdb.mip.config;

import com.tdb.mip.operation.OperationDescription;
import org.aeonbits.owner.Converter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by mcy on 15/11/2016.
 */
public class OperationDescriptionConverter implements Converter<OperationDescription> {
    @Override
    public OperationDescription convert(Method method, String s) {
        if (StringUtils.isNotBlank(s)) {
            return new OperationDescription(s);
        }
        return null;
    }
}
