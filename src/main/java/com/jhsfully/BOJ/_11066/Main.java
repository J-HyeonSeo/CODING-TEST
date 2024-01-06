package com.jhsfully.BOJ._11066;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static int solve(int N, int[] files) {
        int[][][] dp = new int[N][N][2];

        for (int step = 0; step < N; step++) {
            for (int start = 0; start < N - step; start++) {
                int end = start + step;

                if (step == 0) {
                    dp[start][end][0] = files[start];
                    dp[start][end][1] = 0;
                } else {
                    dp[start][end][0] = Integer.MAX_VALUE;
                    dp[start][end][1] = Integer.MAX_VALUE;

                    for (int i = start; i < end; i++) {
//                        System.out.println("step => " + step + ", start => " + start + ", end => " + end + ", i => " + i);
                        dp[start][end][1] = Math.min(dp[start][end][1], dp[start][i][1] + dp[i+1][end][1] + dp[start][i][0] + dp[i+1][end][0]);
                        dp[start][end][0] = Math.min(dp[start][end][0], dp[start][i][0] + dp[i+1][end][0]);
                    }
                }
            }
        }

//        System.out.println(Arrays.deepToString(dp));
        return dp[0][N-1][1];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(reader.readLine());
            int[] files = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            System.out.println(solve(N, files));
        }
    }

}
