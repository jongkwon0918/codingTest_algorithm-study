package daily.day20261114.answers;

import java.util.*;

/*
4. 순환 노선까지 몇 정거장

1번부터 n번까지 역과 양방향 구간 n개가 있다. connections[i]={a,b}이다.
전체 노선은 연결되어 있고 고리는 정확히 하나다. 고리에 속하지 않는 역들은 가지처럼 이어져 있다.
고리는 같은 구간을 되돌아가지 않고 여러 역을 거쳐 출발 역으로 돌아올 수 있는 노선이다.
각 역에서 고리에 속한 역까지 이동해야 하는 최소 구간 수를 구하라.
고리에 속한 역의 답은 0이다. 역1번부터 n번 순서로 길이 n의 배열을 반환하라.
구간마다 이동 비용은1이며 자기 자신으로 연결된 구간과 중복 구간은 없다.

제한사항
- 3 <= n <= 3000
- connections.length == n
- 1 <= a,b <= n, a != b
- 모든 역은 서로 이동할 수 있고 고리는 정확히 하나다.

입출력 예시
n=6, connections={{1,2},{2,3},{3,1},{3,4},{4,5},{2,6}} -> {0,0,0,1,2,1}
1-2-3-1이 고리이고 5번은 4번을 거쳐3번까지 두 구간이다.
n=3, connections={{1,2},{2,3},{3,1}} -> {0,0,0}
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.solution(6, new int[][]{
            {1,2},{2,3},{3,1},{3,4},{4,5},{2,6}
        }))); // [0, 0, 0, 1, 2, 1]
        System.out.println(Arrays.toString(solution.solution(3,
                new int[][]{{1,2},{2,3},{3,1}}))); // [0, 0, 0]
    }
}

class Solution4 {
    List<Integer>[] graph;
    boolean[] visited;
    boolean[] cycle;
    int[] parent;
    boolean found;

    public int[] solution(int n, int[][] connections) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : connections) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        visited = new boolean[n + 1];
        cycle = new boolean[n + 1];
        parent = new int[n + 1];
        found = false;
        dfs(1, 0);

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (cycle[i]) {
                dist[i] = 0;
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return Arrays.copyOfRange(dist, 1, n + 1);
    }

    private void dfs(int current, int previous) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (next == previous) {
                continue;
            }
            if (!visited[next]) {
                parent[next] = current;
                dfs(next, current);
                if (found) {
                    return;
                }
            } else {
                // 부모가 아닌 이미 방문한 조상을 만나면 그 사이의 경로가 고리다.
                cycle[next] = true;
                int node = current;
                while (node != next) {
                    cycle[node] = true;
                    node = parent[node];
                }
                found = true;
                return;
            }
        }
    }
}

