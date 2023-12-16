package org.main;

import java.util.*;

public class ShortestPath {

    public int findShortestPath(Map<String, Set<String>> graph, String start, String end) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();

        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.getOrDefault(current, Collections.emptySet())) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);

                    if (neighbor.equals(end)) {
                        return distances.get(end);
                    }
                }
            }
        }

        return -1; // Nincs út a két URL között
    }

}