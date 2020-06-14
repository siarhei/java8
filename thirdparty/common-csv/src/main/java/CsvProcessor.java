import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author sshchahratsou
 */
public class CsvProcessor {
    private static final Path BASE = Paths.get(System.getProperty("user.home"), "Documents/csv");
    private static final Path ORIGINAL = Paths.get(BASE.toString(), "original.csv");
    private static final Path TRANSLATE = Paths.get(BASE.toString(), "translate.csv");
    private static final Path RESULT = Paths.get(BASE.toString(), "result.csv");
    public static final CSVFormat CSV_FORMAT = CSVFormat.RFC4180.withFirstRecordAsHeader();

    public static void main(String[] args) throws Exception {
        Map<List<String>, String> translates = loadTranslates();
        try (Reader fileReader = new FileReader(ORIGINAL.toString());
             Writer fileWriter = new FileWriter(RESULT.toString())) {
            Iterable<CSVRecord> records = CSV_FORMAT.parse(fileReader);
            StreamSupport.stream(records.spliterator(), false)
                    .map(record -> List.of(record.get(0), record.get(1)))
                    .map(translates::get)
                    .forEach(translation -> writeResult(fileWriter, translation));
        }
    }

    private static void writeResult(Writer fileWriter, String value) {
        try {
            CSV_FORMAT.printRecord(fileWriter, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<List<String>, String> loadTranslates() throws Exception {
        try (Reader fileReader = new FileReader(TRANSLATE.toString())) {
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(fileReader);
            return StreamSupport.stream(records.spliterator(), false)
                    .collect(Collectors.toMap(
                            record -> List.of(record.get(0), record.get(1)),
                            record -> record.get(2),
                            (orig, duplicate) -> orig
                    ));
        }
    }
}
