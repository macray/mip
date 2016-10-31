package com.tdb.mip.service.filenameparser;


import com.tdb.mip.operation.OperationDescription;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by mcy on 26/10/2016.
 */
public interface FileNameParser {
    FileNameInfo parse(String fileName);

    @Getter
    @Setter
    class FileNameInfo {
        private String extension;
        private String baseName;
        private List<OperationDescription> operationDescriptions;
    }
}
