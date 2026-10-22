package daily.day20261022.answers;

import java.util.*;

/*
1. 한 번 이상 이동해서 도달하기

정점 n개의 방향 그래프가 인접 행렬 connections로 주어진다.
connections[i][j]가 1이면 i에서 j로 가는 간선이 있다. 배열 인덱스는 0부터 시작한다.
각 정점 i에서 j까지 간선을 한 개 이상 이용해서 갈 수 있으면 answer[i][j]=1,
갈 수 없으면 0인 n*n 배열을 반환하라.
아무 이동도 하지 않는 것은 경로로 인정하지 않는다.
따라서 자기 자신까지의 값도 항상 1인 것은 아니다. 돌아오는 경로가 있어야 한다.

제한사항
- 1 <= n <= 100, connections는 n*n 배열이다.
- 각 값은 0 또는 1이며 connections[i][i]는 항상 0이다.

입출력 예시
{{0,1,0},{0,0,1},{0,0,0}} -> {{0,1,1},{0,0,1},{0,0,0}}
{{0,1},{1,0}} -> {{1,1},{1,1}}
두 번째 예제는 0→1→0과 1→0→1로 자신에게 돌아올 수 있다.
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(Arrays.deepToString(solution.solution(
                new int[][]{{0,1,0},{0,0,1},{0,0,0}}))); // [[0, 1, 1], [0, 0, 1], [0, 0, 0]]
        System.out.println(Arrays.deepToString(solution.solution(
                new int[][]{{0,1},{1,0}}))); // [[1, 1], [1, 1]]
        System.out.println(Arrays.deepToString(solution.solution(
                new int[][]{{0}}))); // [[0]]
    }
}

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;

    public int[][] solution(int[][] connections) {
        int n = connections.length;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (connections[i][j] == 1) {
                    graph[i].add(j);
                }
            }
        }

        int[][] answer = new int[n][n];
        for (int start = 0; start < n; start++) {
            visited = new boolean[n];
            // 최소 한 개의 간선을 이용해야 하므로 바로 다음 정점부터 방문한다.
            for (int next : graph[start]) {
                if (!visited[next]) {
                    dfs(next);
                }
            }
            for (int i = 0; i < n; i++) {
                if (visited[i]) {
                    answer[start][i] = 1;
                }
            }
        }
        return answer;
    }

    private void dfs(int current) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }
}

