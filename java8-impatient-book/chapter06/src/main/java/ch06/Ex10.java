package ch06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sshchahratsou
 */
public class Ex10 {
    //Write a program that asks the user for a URL, then reads the web page at that
    //URL, and then displays all the links. Use a CompletableFuture for each stage.
    //Don’t call get. To prevent your program from terminating prematurely, call
    //ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);

    private static final String ADDRESS = "https://kaseya.com/";
    private static final Pattern linkPattern = Pattern.compile("<a[^>]+href=[\\\"']?([\\\"'>]+)[\\\"']?[^>]*>(.+?)<\\/a>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    public static void main(String[] args) {
        readPage(ADDRESS).thenApply(page -> {
            //parse the page
            List<String> links = new ArrayList<>();

            Matcher pageMatcher = linkPattern.matcher(page);
            while (pageMatcher.find()) {
                links.add(pageMatcher.group());
            }

            return links;
        }).handle((links, exception) -> {
            if (exception != null) {
                exception.printStackTrace(System.err);
                return Collections.emptyList();
            } else {
                return links;
            }
        }).thenAccept(links -> links.forEach(System.out::println));


        ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
    }

    private static CompletableFuture<String> readPage(final String address) {
        return CompletableFuture.supplyAsync(() -> {

            URL url = null;
            try {
                url = new URL(address);
                URLConnection urlConnection = url.openConnection();
                urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        Stream<String> lines = in.lines()) {
                    return lines.collect(Collectors.joining());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
