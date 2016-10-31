package com.tdb.mip.operation.transformation;

import com.tdb.mip.operation.OperationDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://code.google.com/p/filthy-rich-clients/source/browse/trunk/filthyRichClients-16/src16.StaticEffects.Blur/org/progx/artemis/image/ColorTintFilter.java?r=2
public class TintFactory implements TransformationFactory<Tint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TintFactory.class);

    private static final Pattern PATTERN_TINT = Pattern.compile("tint#{0,1}([0-9a-fA-F]{6})");
    private static final Pattern PATTERN_TINT_MIX_VALUE = Pattern.compile("tint#{0,1}([0-9a-fA-F]{6}),([0-9]+[,\\.]{0,1}[0-9]*)");


    private Color extractTint(Matcher matcher) {
        String colorHexa = "#" + matcher.group(1);
        LOGGER.info("tint " + colorHexa);
        return Color.decode(colorHexa);
    }

    @Override
    public boolean match(OperationDescription description) {
        String config = description.getTextDescription();
        return PATTERN_TINT.matcher(config).matches() || PATTERN_TINT_MIX_VALUE.matcher(config).matches();
    }

    @Override
    public Tint build(OperationDescription description) {
        Matcher matcher = PATTERN_TINT_MIX_VALUE.matcher(description.getTextDescription());

        boolean withMixValue = matcher.matches();
        if (!withMixValue) {
            matcher = PATTERN_TINT.matcher(description.getTextDescription());
            matcher.matches();
        }

        Color tint = extractTint(matcher);
        float mixValue = 1f;
        if (withMixValue) {
            mixValue = Float.parseFloat(matcher.group(2));
        }

        tint = new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 0);
        return new Tint(tint, mixValue);
    }
}
