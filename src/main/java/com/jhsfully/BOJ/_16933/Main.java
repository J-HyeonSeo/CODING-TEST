package com.jhsfully.BOJ._16933;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Pair {
    int x;
    int y;
    int k;
    public Pair(int x, int y, int k) {
        this.x = x;
        this.y = y;
        this.k = k;
    }
}

public class Main {

    public static final int WALL = 1;
    public static final int EMPTY = 0;
    public static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void solve(int N, int M, int K, int[][] board) {

        //낮부터 시작하고, 이동은 누구나 언제나 가능하지만, 벽은 낮에만 부술 수 있습니다.
        int isDay = 1;

        boolean[][][][] visited = new boolean[K+1][2][N][M];

        Queue<Pair> queue = new LinkedList<>();

        queue.offer(new Pair(0, 0, K));
        int dist = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {

                Pair now = queue.poll();

                if (now.x == N - 1 && now.y == M - 1) {
                    System.out.println(dist + 1);
                    return;
                }

                if (visited[now.k][isDay][now.x][now.y]) {
                    continue;
                }

                visited[now.k][isDay][now.x][now.y] = true;
                int nextIsDay = (isDay + 1) % 2;

                for(int[] D : DIRS) {

                    int newX = now.x + D[0];
                    int newY = now.y + D[1];

                    //범위 밖으로 넘어갔을 경우.
                    if (newX < 0 || newX >= N || newY < 0 || newY >= M) {
                        continue;
                    }

                    //이미 벽이 뚫려있는 경우!!
                    if (board[newX][newY] == EMPTY && !visited[now.k][nextIsDay][newX][newY]) {
                        queue.offer(new Pair(newX, newY, now.k));
                    }

                    //벽이 막혀있는 경우!!
                    if (board[newX][newY] == WALL && isDay == 1 && now.k > 0 && !visited[now.k-1][nextIsDay][newX][newY]) {
                        queue.offer(new Pair(newX, newY, now.k - 1));
                    }

                }

                //그냥 머무르는 경우!!
                if (!visited[now.k][nextIsDay][now.x][now.y]) {
                    queue.offer(new Pair(now.x, now.y, now.k));
                }

            }
            dist += 1;
            isDay = (isDay + 1) % 2;
        }

        System.out.println(-1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] numbers = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int N = numbers[0];
        int M = numbers[1];
        int K = numbers[2];

        int[][] board = new int[N][M];

        for (int x = 0; x < N; x++) {
            String row = reader.readLine();
            for (int y = 0; y < M; y++) {
                board[x][y] = Integer.parseInt(String.valueOf(row.charAt(y)));
            }
        }

        solve(N, M, K, board);
    }

}
