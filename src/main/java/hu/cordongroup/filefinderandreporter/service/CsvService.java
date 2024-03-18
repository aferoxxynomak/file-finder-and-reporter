package hu.cordongroup.filefinderandreporter.service;

import hu.cordongroup.filefinderandreporter.dto.OutputCsv;
import hu.cordongroup.filefinderandreporter.exception.CustomRuntimeException;

import java.nio.file.Path;
import java.util.List;

public interface CsvService {
    List<OutputCsv> createOutputCsvFromFile(Path filePath) throws CustomRuntimeException;

    void writeOutputCsv(List<OutputCsv> outputCsvs, Path outputPath) throws CustomRuntimeException;
}
