package com.tdb.mip.operation;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by mcy on 26/10/2016.
 */

@ToString
@Getter
public class OperationDescription {
    private String rawDescription;

    public OperationDescription(String rawDescription){
        this.rawDescription = rawDescription;
    }
}
