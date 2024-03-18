package hu.cordongroup.filefinderandreporter.runner;

import hu.cordongroup.filefinderandreporter.dto.AppArguments;
import hu.cordongroup.filefinderandreporter.dto.OutputCsv;
import hu.cordongroup.filefinderandreporter.exception.CustomRuntimeException;
import hu.cordongroup.filefinderandreporter.service.AppArgumentsHandler;
import hu.cordongroup.filefinderandreporter.service.CsvService;
import hu.cordongroup.filefinderandreporter.service.FileService;
import hu.cordongroup.filefinderandreporter.util.Constants;
import hu.cordongroup.filefinderandreporter.util.Util;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class CustomApplicationRunner implements ApplicationRunner {
    private final FileService fileService;
    private final CsvService csvService;
    private final AppArgumentsHandler appArgumentsHandler;

    public CustomApplicationRunner(FileService fileService, CsvService csvService, AppArgumentsHandler appArgumentsHandler) {
        this.fileService = fileService;
        this.csvService = csvService;
        this.appArgumentsHandler = appArgumentsHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(Constants.HELP_HEADER);

        if (args.getNonOptionArgs().isEmpty()) {
            System.out.println(Constants.HELP_TEXT);
        } else {
            try {
                AppArguments appArguments = appArgumentsHandler.handleApplicationArguments(args);
                List<Path> driveList = fileService.createDriveLetters(appArguments);
                Path inputFilePath = fileService.createPathFromString(args.getNonOptionArgs().get(0));
                Path outputPath = fileService.createOutputPath(inputFilePath);
                List<OutputCsv> outputCsvs = csvService.createOutputCsvFromFile(inputFilePath);
                if (outputCsvs.isEmpty()) {
                    throw new CustomRuntimeException("A bemeneti fájl üres vagy nem tartalmaz értelmezhető fájlneveket!");
                }
                List<OutputCsv> finalOutputCsvs = fileService.populateOutputCsvList(outputCsvs, driveList, appArguments);
                csvService.writeOutputCsv(finalOutputCsvs, outputPath);
            } catch (CustomRuntimeException e) {
                Util.printErrorMsg(e.getMessage());
            } catch (Exception e) {
                Util.printErrorMsg("Ismeretlen hiba történt!");
            }
        }
    }
}
