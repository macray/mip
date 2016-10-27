package com.tdb.mip.density;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString(of = {"humanName"})
@Getter
public class Density {

    private String humanName;
    private String suffix;
    private float ratio;

    public Density(String humanName, String suffix, float ratio) {
        this.humanName = humanName;
        this.suffix = suffix;
        this.ratio = ratio;
    }
}
