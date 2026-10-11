package daily.day20261009.answers;

import java.util.*;

/*
1. 감염되는 컴퓨터

컴퓨터 n대가 1번부터 n번까지 번호를 가진다.
edges[i] = {u, v}는 두 컴퓨터가 직접 연결되어 있다는 뜻이다.
연결은 양방향이며, 바이러스는 연결을 따라 계속 전파된다.
1번 컴퓨터가 감염되었을 때 추가로 감염되는 컴퓨터 수를 반환하라.
처음 감염된 1번은 반환값에서 제외한다.

제한사항
- 1 <= n <= 100
- edges에는 직접 연결된 컴퓨터 쌍이 주어진다.
- 컴퓨터 번호는 1 이상 n 이하이다.

입출력 예시
n=7, edges={{1,2},{2,3},{1,5},{5,2},{5,6},{4,7}} -> 4
n=1, edges={} -> 0
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[][] edges = {{1, 2}, {2, 3}, {1, 5}, {5, 2}, {5, 6}, {4, 7}};
        System.out.println(solution.solution(7, edges)); // 4
        System.out.println(solution.solution(1, new int[0][2])); // 0
    }
}

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;
    int count;

    public int solution(int n, int[][] edges) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        visited = new boolean[n + 1];
        count = 0;
        dfs(1);
        return count;
    }

    private void dfs(int current) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (!visited[next]) {
                count++;
                dfs(next);
            }
        }
    }
}

