package hu.cordongroup.filefinderandreporter.service;

import hu.cordongroup.filefinderandreporter.dto.AppArguments;
import hu.cordongroup.filefinderandreporter.dto.OutputCsv;
import hu.cordongroup.filefinderandreporter.exception.CustomRuntimeException;

import java.nio.file.Path;
import java.util.List;

public interface FileService {
    Path createPathFromString(String filePath);

    List<OutputCsv> populateOutputCsvList(List<OutputCsv> outputCsvs, List<Path> driveList, AppArguments appArguments) throws CustomRuntimeException;

    Path createOutputPath(Path inputPath);

    List<Path> createDriveLetters(AppArguments appArguments) throws CustomRuntimeException;

    List<Path> getLocalDriveLetters() throws CustomRuntimeException;
}
