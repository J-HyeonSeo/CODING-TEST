package com.jhsfully.BOJ._1039;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void solve(int N, int K) {
        //queue는 레벨 탐색을 위한 변수
        Queue<Integer> queue = new LinkedList<>();
        //set은 중복제거용 변수
        Set<Integer> set = new HashSet<>();
        queue.offer(N);

        for (int i = 0; i < K; i++) {

            //새로운 값을 저장하기 위해, set 초기화.
            set.clear();

            while(!queue.isEmpty()) {
                int now = queue.poll();

                //스왑을 편하게 진행하기 위해, char array 형태로 변환.
                char[] s = String.valueOf(now).toCharArray();

                //교환작업 진행.
                for (int j = 0; j < s.length - 1; j++) {
                    for (int k = j + 1; k < s.length; k++) {

                        //스왑 진행
                        char temp = s[j];
                        s[j] = s[k];
                        s[k] = temp;

                        //리딩 제로가 아닌 경우에만, 다음 후보군에 추가.
                        if (s[0] != '0') {
                            set.add(Integer.valueOf(String.valueOf(s)));
                        }

                        //원상 복구
                        s[k] = s[j];
                        s[j] = temp;
                    }
                }
            }

            //K번 교환을 수행하지 못하므로, -1 출력.
            if (set.isEmpty()) {
                System.out.println(-1);
                return;
            }

            //set에서 꺼내와 queue에 추가하기.
            set.forEach(queue::offer);
        }

        //최댓값 추출 후, 정답 출력.
        int answer = set.stream().max(Comparator.comparing(x -> x)).get();
        System.out.println(answer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        solve(N, K);
    }

}
