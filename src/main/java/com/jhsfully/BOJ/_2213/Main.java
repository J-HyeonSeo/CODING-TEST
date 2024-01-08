package com.jhsfully.BOJ._2213;

import java.io.*;
import java.util.*;

public class Main {

    public static void solve(int now, int[][] dp, int[] weights, List<List<Integer>> graph, boolean[] visited) {
        dp[now][1] = weights[now];

        //BASE
        if (graph.get(now).size() == 1 && visited[graph.get(now).get(0)]) {
            return;
        }

        for (int node : graph.get(now)) {
            if (node == now || visited[node]) continue;
            visited[node] = true;
            solve(node, dp, weights, graph, visited);
            dp[now][0] += Math.max(dp[node][1], dp[node][0]);
            dp[now][1] += dp[node][0];
        }
    }

    public static void findRoute(int now, boolean selected, int[][] dp, List<List<Integer>> graph, boolean[] visited, Set<Integer> route) {
        visited[now] = true;
        boolean nextSelected = false;

        if (dp[now][1] > dp[now][0] && !selected) {
            route.add(now);
            nextSelected = true;
        }

        for (int node : graph.get(now)) {
            if (visited[node]) continue;
            findRoute(node, nextSelected, dp, graph, visited, route);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());
        int[] weights = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < N-1; i++) {
            int[] edge = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            graph.get(edge[0]-1).add(edge[1]-1);
            graph.get(edge[1]-1).add(edge[0]-1);
        }
        boolean[] visited = new boolean[N];
        visited[0] = true;
        int[][] dp = new int[N][2];
        solve(0, dp, weights, graph, visited);
        System.out.println(Math.max(dp[0][0], dp[0][1])); //합산 출력!

        Set<Integer> routeSet = new HashSet<>();
        visited = new boolean[N];
        findRoute(0, false, dp, graph, visited, routeSet);
        List<Integer> route = new ArrayList<>(routeSet);
        route.sort((x, y) -> x - y);
        for (int i = 0; i < route.size(); i++) {
            System.out.print(route.get(i)+1);
            if (i < route.size()) {
                System.out.print(" ");
            }
        }
    }
}
