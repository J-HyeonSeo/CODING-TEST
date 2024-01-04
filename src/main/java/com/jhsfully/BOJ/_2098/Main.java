package com.jhsfully.BOJ._2098;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final int INF = 987654321;

    public static int dfs(int N, int[][] weights, int node, int nowVisited, int[][] visited) {

        if (nowVisited == ((1 << N) - 1)) {
            if (weights[node][0] == 0) {
                return INF;
            }
            return weights[node][0];
        }

        if (visited[node][nowVisited] != -1) {
            return visited[node][nowVisited];
        }

        visited[node][nowVisited] = INF;
        for (int i = 0; i < N; i++) {

            //자기 자신이거나, 길이 없거나..
            if (weights[node][i] == 0) {
                continue;
            }

            //이미 방문 하였는가?
            if ((nowVisited & (1 << i)) == (1 << i)) {
                continue;
            }

            int nextVisited = nowVisited | (1 << i);

            //현재노드에, 다음으로 올 수 있는 경우의 수 중에서 최솟값을 저장.
            visited[node][nowVisited] = Math.min(visited[node][nowVisited], weights[node][i] +
                    dfs(N, weights, i, nextVisited, visited));
        }
        return visited[node][nowVisited];
    }

    public static void solve(int N, int[][] weights) {
        int[][] visited = new int[N][1 << N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], -1);
        }

        int answer = dfs(N, weights, 0, 1, visited);

        System.out.println(answer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int[][] weights = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp = scanner.nextInt();
                weights[i][j] = temp;
            }
        }
        solve(N, weights);
    }
}



//이전 방식
//public static void dfs(int N, int[][] weights, int node, int nowVisited, int[][] visited, int dist) {
//
//    if (nowVisited == ((1 << N) - 1)) {
//        if (node == 0) {
//            answer = Math.min(answer, dist);
//        }
//        return;
//    }
//
//    for (int i = 0; i < N; i++) {
//
//        //자기 자신이거나, 길이 없거나..
//        if (weights[node][i] == 0) {
//            continue;
//        }
//
//        //이미 방문 하였는가?
//        if ((nowVisited & (1 << i)) == (1 << i)) {
//            continue;
//        }
//
//        int nextVisited = nowVisited | (1 << i);
//
//        //이미 더 최적의 경로가 존재하는가?
//        if (visited[i][nextVisited] <= dist + weights[node][i]) {
//            continue;
//        }
//
//        visited[i][nextVisited] = dist + weights[node][i];
//        dfs(N, weights, i, nextVisited, visited, dist + weights[node][i]);
//    }
//}
