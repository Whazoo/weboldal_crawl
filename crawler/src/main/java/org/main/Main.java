package org.main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Main {

    private Map<String, Set<String>> graph = new HashMap<>();
    private Set<String> visitedUrls = new HashSet<>();

    public void crawl(String url, String targetUrl) {
        crawlUrl(url, targetUrl);
    }

    private void crawlUrl(String currentUrl, String targetUrl) {
        if (!visitedUrls.contains(currentUrl)) {
            visitedUrls.add(currentUrl);

            try {
                Document document = Jsoup.connect(currentUrl).get();
                Elements links = document.select("a[href]");

                for (Element link : links) {
                    String nextUrl = link.absUrl("href");

                    if (nextUrl.startsWith(targetUrl)) {
                        graph.computeIfAbsent(currentUrl, k -> new HashSet<>()).add(nextUrl);
                        System.out.println("Found link: " + nextUrl); // Új sor
                        crawlUrl(nextUrl, targetUrl);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Map<String, Set<String>> getGraph() {
        return graph;
    }

    public static void main(String[] args) {
        Main webCrawler = new Main();
        webCrawler.crawl("https://hu.wikipedia.org/wiki/PlayStation_(konzol)", "https://hu.wikipedia.org/wiki/Nintendo");

        Map<String, Set<String>> graph = webCrawler.getGraph();

        ShortestPath pathFinder = new ShortestPath();
        int shortestPathLength = pathFinder.findShortestPath(graph, "https://hu.wikipedia.org/wiki/PlayStation_(konzol)", "https://hu.wikipedia.org/wiki/Nintendo");

        System.out.println("Legrövidebb Elérési Út Hossza:: " + shortestPathLength);
    }

}