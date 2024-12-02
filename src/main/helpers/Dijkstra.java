package main.helpers;

import java.util.ArrayList;
public class Dijkstra {

    public Dijkstra() {
    }

    private int minDistance(int[] dist, Boolean[] sptset) {
        int lowest_distance = Integer.MAX_VALUE;
        int lowest_index = -1;
        for (int i = 0; i < sptset.length; i++) {
            if (sptset[i] == false && dist[i] <= lowest_distance) {
                lowest_distance = dist[i];
                lowest_index = i;
            }
        }
        return lowest_index;
    }

    private ArrayList<Object> getShortestPaths(char[][] map, int source) {
        int[] route = new int[map.length];
        Boolean[] sptset = new Boolean[map.length];
        int[] sptDistance = new int[map.length];
        for (int i = 0; i < map.length; i++) {
            sptDistance[i] = Integer.MAX_VALUE;
            sptset[i] = false;
        }
        sptDistance[source] = 0;
        for (int count = 0; count < map.length - 1; count++) {
            int vertex = minDistance(sptDistance, sptset);
            sptset[vertex] = true;
            for (int v = 0; v < map.length; v++) {
                if (!sptset[v] && map[vertex][v] != 0 && sptDistance[vertex] != Integer.MAX_VALUE && sptDistance[vertex] + map[vertex][v] < sptDistance[v]) {
                    sptDistance[v] = sptDistance[vertex] + map[vertex][v];
                    route[vertex] = v;
                }
            }
        }
        ArrayList<Object> list = new ArrayList<>();
        list.add(sptDistance);
        list.add(route);
        return list;
    }

    public String getShortestPath(int begin, int end, char[][] graph) {
        ArrayList<Object> list = getShortestPaths(graph, begin);
        int[] result = (int[]) list.get(0);
        int next = -1;
        String finalRoute = "" + begin;
        while (true) {
            if (next == -1) {
                next = begin;
            }

            next = result[next];
            if (next != 0) {
                finalRoute += "-->" + next;
                if (next == end) {
                    return finalRoute;
                }
            }
        }
    }
}

