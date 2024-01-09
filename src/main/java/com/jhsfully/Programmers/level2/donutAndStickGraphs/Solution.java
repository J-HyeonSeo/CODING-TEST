package com.jhsfully.Programmers.level2.donutAndStickGraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    public static final int DONUT = 1; //처음으로 다시 방문 하는 정점이 시작과 동일하면 도넛
    public static final int STICK = 2; //더 이상 갈 곳이 없다면, Stick
    public static final int EIGHT = 3; //다음 경로가 2가지 이상 존재하면, 8자임.

    public int findGraph(int start, int now, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
        if (visited.contains(now) && now == start) { // 이미 방문한 정점이었다면!
            return DONUT;
        }
        visited.add(now);
        if (graph.get(now) == null) return STICK;
        if (graph.get(now).size() > 1) return EIGHT;

        for (int node : graph.get(now)) {
            return findGraph(start, node, graph, visited);
        }

        return -1;
    }

    public int[] solution(int[][] edges) {
        int[] answer = new int[4];

        //graph를 형성하자.
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> targetedNodes = new HashSet<>();
        Set<Integer> centerCandidates = new HashSet<>();
        int center = 0;

        for (int[] edge : edges) {
            int s = edge[0];
            int e = edge[1];

            targetedNodes.add(e);

            if (graph.containsKey(s)) {
                centerCandidates.add(s);
            } else {
                graph.put(s, new ArrayList<>());
            }
            graph.get(s).add(e);
        }

        //center 정점 찾기
        for (int node : centerCandidates) { //두 번 이상 경로가 나간 노드 대상으로..
            if (targetedNodes.contains(node)) { //진입 된 노드가 있다면, 8자 노드에 해당
                continue;
            }
            center = node; //두 번 이상 경로나 나갔고, 진입 된 적도 없다면, 중심점임.
            answer[0] = center;
            break;
        }

        //정점 탐색
        Set<Integer> visited = new HashSet<>();
        for (int entryPoint : graph.get(center)) {
            int res = findGraph(entryPoint, entryPoint, graph, visited);
            answer[res]++;
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        //case1
        System.out.println(
            Arrays.toString(solution.solution(new int[][]{{2, 3}, {4, 3}, {1, 1}, {2, 1}})));
    }
}
