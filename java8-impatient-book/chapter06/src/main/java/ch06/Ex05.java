package ch06;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Ex05 {
    //Write an application in which multiple threads read all words from a collection of files.
    // Use a ConcurrentHashMap<String, Set<File>> to track in which files each word occurs.
    // Use the merge method to update the map.

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");
    private static final String FILE_EXT = "java";
    private static final String UTF_8 = "UTF-8";

    public static void main(String[] args) throws Exception {
        final ConcurrentHashMap<String, Set<File>> collector = new ConcurrentHashMap<>();


        Files.walk(Paths.get(USER_DIR))
                .parallel()
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(FILE_EXT))
                .forEach(file -> {
                    Set<File> files = new HashSet<>();
                    files.add(file);
                    try {
                        Files.lines(file.toPath(), Charset.forName(UTF_8))
                                .flatMap(line -> Stream.of(line.split("[\\P{L}]+")))
                                .map(String::trim)
                                .filter(word -> !word.isEmpty())
                                .forEach(word -> /*collector.merge(
                                        word,
                                        files,
                                        (set1, set2) -> {
                                            Set<File> mergeFiles = new HashSet<>(set1);
                                            mergeFiles.addAll(set2);
                                            return mergeFiles;
                                        }
                                )*/
                                //easier way
                                collector
                                        .computeIfAbsent(word, (key) -> new HashSet<>())
                                        .add(file));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        System.out.println(collector.get("RuntimeException"));
    }
}
