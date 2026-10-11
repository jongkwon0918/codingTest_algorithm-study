package daily.day20261007.answers;

import java.util.*;

/*
정답: 1번 촌수계산
해설과 예제 추적: ../ANSWERS.md
원본 백준 2644번 촌수계산
https://www.acmicpc.net/problem/2644
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        try {
        int[][] relations = {
                {1, 2}, {1, 3}, {2, 7}, {2, 8}, {2, 9}, {4, 5}, {4, 6}
        };
        System.out.println(solution.solution(9, 7, 3, relations) + " / 기대값: 3");
        System.out.println(solution.solution(9, 7, 6, relations) + " / 기대값: -1");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution1을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;

    public int solution(int n, int start, int target, int[][] relations) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] relation : relations) {
            int u = relation[0];
            int v = relation[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        visited = new boolean[n + 1];
        return dfs(start, target, 0);
    }

    private int dfs(int current, int target, int count) {
        visited[current] = true;
        if (current == target) {
            return count;
        }
        for (int next : graph[current]) {
            if (!visited[next]) {
                int answer = dfs(next, target, count + 1);
                if (answer != -1) {
                    return answer;
                }
            }
        }
        return -1;
    }
}

