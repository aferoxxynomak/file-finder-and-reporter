package hu.cordongroup.filefinderandreporter.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;
import hu.cordongroup.filefinderandreporter.dto.OutputCsv;
import hu.cordongroup.filefinderandreporter.exception.CustomRuntimeException;
import hu.cordongroup.filefinderandreporter.util.Constants;
import hu.cordongroup.filefinderandreporter.util.Util;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public List<OutputCsv> createOutputCsvFromFile(Path filePath) throws CustomRuntimeException {
        System.out.print("Bemeneti fájl beolvasása...");
        List<OutputCsv> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            System.out.print("...");
            try (CSVReader csvReader = new CSVReader(reader)) {
                System.out.print("...");
                String[] oneRow;
                while ((oneRow = csvReader.readNext()) != null) {
                    String firstColumn = oneRow[0].trim().replaceAll("[,;]$", "");
                    if (!firstColumn.isEmpty()) {
                        list.add(new OutputCsv(firstColumn));
                    }
                }
                System.out.println(" Befejezve");
            } catch (IOException | CsvValidationException e) {
                throw new CustomRuntimeException("Hiba a bemeneti fálj feldolgozása közben!");
            }
        } catch (NoSuchFileException e) {
            throw new CustomRuntimeException("A bemeneti fájl nem található: " + filePath);
        } catch (IOException e) {
            throw new CustomRuntimeException("Hiba a bemeneti fájl beolvasásakor! Győzödjön meg róla, hogy létezik-e a fájl vagy nem használja-e másik program.");
        }
        return list;
    }

    @Override
    public void writeOutputCsv(List<OutputCsv> outputCsvs, Path outputPath) throws CustomRuntimeException {
        System.out.print("Kimeneti fájl létrehozása... ");
//        String outputPath = Paths.get("C:", "Users", "zbeny", "Downloads", "output.csv").toString();
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath.toString(), false), Constants.WINDOWS_CHARSET)))
                .withSeparator(Constants.CSV_SEPARATOR).build()) {
            writer.writeNext(Util.getCsvHeader());
            for (OutputCsv outputCsv : outputCsvs) {
                writer.writeNext(new String[]{outputCsv.getFilename(),
                        outputCsv.isFound() ? Constants.CSV_FOUND_YES : Constants.CSV_FOUND_NO,
                        outputCsv.getPathListByString()});
            }
            System.out.println("Kész");
            System.out.println("Megtalálható az alábbi helyen: " + outputPath);
        } catch (FileNotFoundException e) {
            throw new CustomRuntimeException("A kimeneti fájl nem hozható létre. Lehetséges, hogy más alkalmazás használja. Fájl helye: " + outputPath);
        } catch (IOException e) {
            throw new CustomRuntimeException("Hiba a bemeneti fájl létrehozása közben!");
        }
    }

}
