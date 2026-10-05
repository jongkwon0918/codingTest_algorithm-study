/*3번. 역세권 찾기
문제

도시에는 여러 개의 건물이 있고, 건물 사이에는 도로가 연결되어 있다.

도로마다 이동하는 데 필요한 거리가 주어진다.

일부 도로는 공사 중이어서 통과할 수 없다.

두 건물 A, B가 주어졌을 때, A에서 B까지 이동하는 데 필요한 최소 거리를 구한다.

도로를 이용할 수 없는 경우에는 -1을 반환한다.

입력
edges[i] = {출발지, 도착지, 거리}

도로는 양방향으로 연결되어 있다.

예시
edges = {
    {1, 2, 4},
    {1, 3, 2},
    {2, 3, 1},
    {2, 4, 5},
    {3, 4, 8}
}

start = 1
end = 4

최단 경로는

1 → 3 → 2 → 4

이고 거리는

2 + 1 + 5 = 8

따라서 결과:

8*/
package bnkSystem;
import java.util.*;
public class Test3 {
    public static void main(String[] args) {

        int[][] edges = {
                {1, 2, 4},
                {1, 3, 2},
                {2, 3, 1},
                {2, 4, 5},
                {3, 4, 8}
        };

        int start = 1;
        int end = 4;

        Solution3 solution = new Solution3();

        int answer = solution.solution(edges, start, end);

        System.out.println(answer);
    }
}

class Solution3 {

    public int solution(int[][] edges, int start, int end) {

        int n = 0;

        for (int[] edge : edges) {
            n = Math.max(n, Math.max(edge[0], edge[1]));
        }

        ArrayList<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {

            int from = edge[0];
            int to = edge[1];
            int cost = edge[2];

            graph[from].add(new int[]{to, cost});
            graph[to].add(new int[]{from, cost});
        }

        int[] dist = new int[n + 1];

        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );

        dist[start] = 0;

        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {

            int[] current = pq.poll();

            int node = current[0];
            int cost = current[1];

            if (cost > dist[node]) {
                continue;
            }

            if (node == end) {
                return cost;
            }

            for (int[] next : graph[node]) {

                int nextNode = next[0];
                int nextCost = next[1];

                if (dist[nextNode] > cost + nextCost) {

                    dist[nextNode] = cost + nextCost;

                    pq.offer(new int[]{
                            nextNode,
                            dist[nextNode]
                    });
                }
            }
        }

        return -1;
    }
}