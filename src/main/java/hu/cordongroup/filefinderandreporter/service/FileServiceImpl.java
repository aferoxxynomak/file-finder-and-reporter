package hu.cordongroup.filefinderandreporter.service;

import hu.cordongroup.filefinderandreporter.dto.AppArguments;
import hu.cordongroup.filefinderandreporter.dto.OutputCsv;
import hu.cordongroup.filefinderandreporter.exception.CustomRuntimeException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private int progress = 0;

    private static String getFileNameWithoutExtension(Path filePath) {
        String fileNameWithExtension = filePath.getFileName().toString();
        int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

        if (lastDotIndex > 0) {
            return fileNameWithExtension.substring(0, lastDotIndex);
        } else {
            return fileNameWithExtension;
        }
    }

    @Override
    public Path createPathFromString(String filePath) {
        return Paths.get(filePath);
    }

    @Override
    public List<OutputCsv> populateOutputCsvList(List<OutputCsv> outputCsvs, List<Path> driveList, AppArguments appArguments) throws CustomRuntimeException {
        System.out.println("Fájlok keresése a fájlrendszerben...");
        try {
            for (Path actualDrive : driveList) {
                System.out.println("Aktuális meghajtó: " + actualDrive);
                iteratePathRecursive(actualDrive, outputCsvs, appArguments);
            }

            System.out.println("Fájlok keresése a fájlrendszerben... Befejezve");
            return outputCsvs;
        } catch (CustomRuntimeException e) {
            throw new CustomRuntimeException(e.getMessage());
        }
    }

    @Override
    public Path createOutputPath(Path inputPath) {
        Path inputDirPath = inputPath.getParent();
        String inputFilename = getFileNameWithoutExtension(inputPath);
        LocalDateTime budapestTime = LocalDateTime.now(ZoneId.of("Europe/Budapest"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String outputFilename = inputFilename + "_" + budapestTime.format(formatter) + ".csv";
        return inputDirPath.resolve(outputFilename);
    }

    @Override
    public List<Path> createDriveLetters(AppArguments appArguments) throws CustomRuntimeException {
        if (appArguments.findOnlyOnCDrive()) {
            return new ArrayList<>(List.of(Path.of("C:\\")));
        }
        return getLocalDriveLetters();
    }

    @Override
    public List<Path> getLocalDriveLetters() throws CustomRuntimeException {
        List<Path> driveList = new ArrayList<>();
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        for (Path root : rootDirectories) {
            try {
                FileStore store = Files.getFileStore(root);
                String type = store.type();
                if (type.equals("NTFS") || type.equals("FAT")) {
                    driveList.add(root);
                }
            } catch (Exception e) {
                throw new CustomRuntimeException("Hiba a helyi meghajtók lekérdezésekor!");
            }
        }
        return driveList;
    }

    private List<OutputCsv> iteratePathRecursive(Path currentPath, List<OutputCsv> outputCsvs, AppArguments appArguments) throws CustomRuntimeException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)) {
            for (Path entry : stream) {
                if (Files.isHidden(entry) && !appArguments.withHiddenFolders()) {
                    continue;
                }
                if (Files.isDirectory(entry)) {
                    if (appArguments.hideProgress()) {
                        showProgress();
                    } else {
                        showActualDirectory(entry);
                    }
                    iteratePathRecursive(entry, outputCsvs, appArguments);
                }
                updateOutputCsvs(outputCsvs, entry);
            }
        } catch (AccessDeniedException e) {
            return outputCsvs;
        } catch (IOException e) {
            throw new CustomRuntimeException("Hiba a fájlok keresése közben!");
        }
        return outputCsvs;
    }

    private void showProgress() {
        this.progress++;
        switch (this.progress / 50) {
            case 0 -> System.out.print("/\r");
            case 1 -> System.out.print("-\r");
            case 2 -> System.out.print("\\\r");
            case 3 -> System.out.print("|\r");
            case 4 -> this.progress = 0;
        }
    }

    private void showActualDirectory(Path path) {
        if (++this.progress >= 10) {
            this.progress = 0;
            System.out.print(path + "\r");
        }
    }

    private void updateOutputCsvs(List<OutputCsv> outputCsvs, Path entry) {
        String actualFilename = getFileNameWithoutExtension(entry);
        int outputCsvListIndex = findOutputCsvListIndexByFilename(outputCsvs, actualFilename);
        if (outputCsvListIndex != -1) {
            OutputCsv updatedCsv = outputCsvs.get(outputCsvListIndex);
            updatedCsv.setFound(true);
            updatedCsv.addPathStringToPathList(entry.toString());
        }
    }

    private int findOutputCsvListIndexByFilename(List<OutputCsv> outputCsvs, String filename) {
        for (int i = 0; i < outputCsvs.size(); i++) {
            if (outputCsvs.get(i).getFilename().equals(filename)) {
                return i;
            }
        }
        return -1;
    }
}
