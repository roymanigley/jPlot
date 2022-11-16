package ch.bytecrowd.jplot.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;

public final class CsvUtils {
    private CsvUtils() {
    }

    public static LinkedHashMap<String, LinkedHashMap<Object, Number>> readDataFromCSV(String pathToCsv) {
        if (Files.notExists(Paths.get(pathToCsv))) {
            throw new IllegalArgumentException("File " + pathToCsv + " not found");
        }
        try (
                final var reader = new FileReader(pathToCsv);
                final var csvReader = new CSVReader(reader);
        ) {
            final var data = new LinkedHashMap<String, LinkedHashMap<Object, Number>>();
            final var lineIterator = csvReader.iterator();
            final var xValues = Arrays.stream(lineIterator.next())
                    .skip(1)
                    .toList();
            while (lineIterator.hasNext()) {
                final var xyValues = new LinkedHashMap<Object, Number>();
                final var dataLine = lineIterator.next();
                final var label = dataLine[0];
                final var  yValues = Arrays.stream(dataLine)
                        .skip(1)
                        .flatMap(String::lines)
                        .map(Double::parseDouble)
                        .toList();

                for (int i = 0; i < xValues.size(); i++) {
                    final Number yValue;
                    if (yValues.size() <= i) {
                        yValue = 0;
                    } else {
                        yValue = yValues.get(i);
                    }
                    xyValues.put(xValues.get(i), yValue);
                }
                data.put(label, xyValues);
            }
            return data;
        } catch (IOException ex) {
            throw new RuntimeException("could not read from CSV file: " + pathToCsv);
        }
    }
}
