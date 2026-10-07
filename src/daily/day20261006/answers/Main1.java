package daily.day20261006.answers;

import java.util.*;

/*
정답 및 해설: 1번 연결 요소의 개수
핵심 풀이: 미방문 정점에서 DFS를 시작할 때마다 연결 요소를 하나 늘린다. 인접 리스트는 양방향으로 만든다. 모든 정점을 검사하므로 고립된 정점도 빠뜨리지 않는다.
시간복잡도: O(n + m), m은 간선 수
원본: 백준 11724번 연결 요소의 개수
https://www.acmicpc.net/problem/11724
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        try {
        System.out.println(solution.solution(6, new int[][]{
                {1, 2}, {2, 5}, {5, 1}, {3, 4}, {4, 6}
        }) + " / 기대값: 2");
        System.out.println(solution.solution(4, new int[][]{}) + " / 기대값: 4");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution1을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution1 {
    public int solution(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n + 1];
        int count = 0;
        for (int vertex = 1; vertex <= n; vertex++) {
            if (!visited[vertex]) {
                dfs(vertex, graph, visited);
                count++;
            }
        }
        return count;
    }

    private void dfs(int vertex, List<List<Integer>> graph, boolean[] visited) {
        visited[vertex] = true;
        for (int next : graph.get(vertex)) {
            if (!visited[next]) {
                dfs(next, graph, visited);
            }
        }
    }
}

