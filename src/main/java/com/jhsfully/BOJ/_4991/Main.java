package com.jhsfully.BOJ._4991;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Pair {
    int startX;
    int startY;
    int x;
    int y;
    int group;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Pair(int x, int y, int group) {
        this.x = x;
        this.y = y;
        this.group = group;
    }
    public Pair(int x, int y, int group, int startX, int startY) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair other = (Pair) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "(x => " + this.x + ", y => " + this.y + ")";
    }
}

public class Main {

    public static final char FUR = 'x';
    public static final char DUST = '*';
    public static final char START = 'o';
    public static final int INF = Integer.MAX_VALUE;
    public static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
    public static int findShortestRoutes(int[] groupArr, int prevGroup, int nowDist, int idx, int N, Map<Integer, Map<Integer, Integer>> dist) {
        if (idx == N) {
//            System.out.println("endPointReturn => " + nowDist);
            return nowDist;
        }

        int minDist = INF;

        for (int i = idx; i < N; i++) {
            swap(groupArr, idx, i);
            int nextGroup = groupArr[idx];
            if (dist.containsKey(prevGroup) && dist.get(prevGroup).containsKey(nextGroup)) {
                int nextDist = dist.get(prevGroup).get(nextGroup);
                minDist = Math.min(minDist, findShortestRoutes(groupArr, nextGroup, nowDist + nextDist, idx + 1, N, dist));
            }
            swap(groupArr, idx, i);
        }

        return minDist;
    }

    public static void solve(int N, int M, char[][] board) {
        //group -> Map<group, dist>
        Map<Integer, Map<Integer, Integer>> dist = new HashMap<>();
        Map<Pair, Integer> groupMapper = new HashMap<>();

        Queue<Pair> queue = new LinkedList<>();

        int startGroup = 0;

        //FIND DUSTS..
        int group = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == DUST || board[i][j] == START) {
                    queue.offer(new Pair(i, j, group, i, j));
                    groupMapper.put(new Pair(i, j), group);
                    if(board[i][j] == START) {
                        startGroup = group;
                    }
                    group++;
                }
            }
        }

        boolean[][][] visited = new boolean[group][N][M];

        //FIND SHORTEST TARGET
        int nowDist = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Pair now = queue.poll();

                if (visited[now.group][now.x][now.y]) {
                    continue;
                }

                visited[now.group][now.x][now.y] = true;

                for (int[] D : DIRS) {
                    int x = now.x + D[0];
                    int y = now.y + D[1];

                    if (x < 0 || x >= N || y < 0 || y >= M) {
                        continue;
                    }

                    if (visited[now.group][x][y]) {
                        continue;
                    }

                    if (board[x][y] == FUR) {
                        continue;
                    }

                    if (board[x][y] == DUST) {
                        int targetGroup = groupMapper.get(new Pair(x, y));
                        if (!dist.containsKey(now.group)) {
                            dist.put(now.group, new HashMap<>());
                        }
                        dist.get(now.group).put(targetGroup, nowDist + 1);
//                        System.out.println("group : "+now.group+", startX : " + now.startX + ", startY : " + now.startY + ", x : " + x + ", y : " + y + ", dist => " + (nowDist + 1));
//                        visited[now.group][x][y] = true;
                    }
                    queue.offer(new Pair(x, y, now.group));
                }
            }
            nowDist++;
        }

        //IT's SHOW TIME!
        int[] groupArr = new int[group-1];
        int tempIdx = 0;
        int tempValue = 0;
        while(tempIdx < group-1) {
            if (tempValue == startGroup) {
                tempValue++;
                continue;
            }
            groupArr[tempIdx] = tempValue;
            tempIdx++;
            tempValue++;
        }

//        System.out.println(Arrays.toString(groupArr));
//        System.out.println(group-1);
//        System.out.println(dist);
//        System.out.println(groupMapper);

        int answer = findShortestRoutes(groupArr, startGroup, 0, 0, group-1, dist);
        System.out.println(answer < INF ? answer : -1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int[] size = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            if (size[0] == 0) break;
            int N = size[1];
            int M = size[0];
            char[][] board = new char[N][M];
            for (int i = 0; i < N; i++) {
                board[i] = reader.readLine().toCharArray();
            }
            solve(N, M, board);
        }
    }
}
