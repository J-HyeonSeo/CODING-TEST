package com.jhsfully.BOJ._3108;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void dfs(int x, int y, int[][] board) {
        if (x < 0 || x > 2000 || y < 0 || y > 2000) {
            return;
        }
        if (board[x][y] != 1) {
            return;
        }

        board[x][y] = 2;

        for (int[] d : dirs) {
            dfs(x + d[0], y + d[1], board);
        }

    }

    public static void solve(int[][] board) {
        int cnt = 0;

        for (int x = 0; x <= 2000; x++) {
            for (int y = 0; y <= 2000; y++) {
                if (board[x][y] == 1) {
                    dfs(x, y, board);
                    cnt++;
                }
            }
        }

        if (board[1000][1000] == 2) {
            cnt--;
        }

        System.out.println(cnt);
    }

    public static int[][] input() throws IOException {
        int[][] board = new int[2001][2001];

        //reader 객체 선언
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //반복 카운트 획득
        int N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            int[] xy = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            //겹치는 것이 아닌 한 칸을 두고 떨어진 좌표는 떨어진 좌표이므로, 계산하지 않기 위해 2배수를 적용함.
            // -좌표를 제하기 위해서, 500을 더하고 시작함.
            for(int j = 0; j < 4; j++) {
                xy[j] = (xy[j] + 500) * 2;
            }

            int x1 = xy[0];
            int y1 = xy[1];
            int x2 = xy[2];
            int y2 = xy[3];

            //x축 1자로 채우기. (ex = )
            for(int x = x1; x <= x2; x++) {
                board[x][y1] = 1;
                board[x][y2] = 1;
            }

            //y축 일자로 채우기. (ex || )
            for(int y = xy[1]; y <= xy[3]; y++) {
                board[x1][y] = 1;
                board[x2][y] = 1;
            }

        }

        return board;
    }

    public static void main(String[] args) throws IOException {
        int[][] board = input();
        solve(board);
    }
}
