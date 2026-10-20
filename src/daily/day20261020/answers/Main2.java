package daily.day20261020.answers;

import java.util.*;

/*
2. 출발점에서 모든 정점까지

정점은 1번부터 n번까지이고 edges[i]={u,v,weight}는 u에서 v로 가는 단방향 간선이다.
start에서 각 정점까지의 최단거리를 번호 순서의 문자열 배열로 반환하라.
answer[i-1]은 i번 정점의 결과이다. 시작점은 "0", 도달할 수 없으면 "INF"이다.
같은 두 정점 사이에 여러 간선이 있을 수 있다.

제한사항
- 1 <= n <= 20,000
- 1 <= edges.length <= 300,000
- 정점 번호와 start는 1 이상 n 이하이다.
- 1 <= weight <= 10

입출력 예시
n=5, edges={{1,2,2},{1,3,3},{2,3,1},{2,4,5},{3,4,1}}, start=1
-> ["0","2","3","4","INF"]
n=3, edges={{1,2,5},{1,2,1}}, start=2 -> ["INF","0","INF"]
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] edges = {{1, 2, 2}, {1, 3, 3}, {2, 3, 1}, {2, 4, 5}, {3, 4, 1}};
        System.out.println(Arrays.toString(solution.solution(5, edges, 1)));
        // [0, 2, 3, 4, INF]
        System.out.println(Arrays.toString(solution.solution(
                3, new int[][]{{1, 2, 5}, {1, 2, 1}}, 2))); // [INF, 0, INF]
    }
}

class Solution2 {
    public String[] solution(int n, int[][] edges, int start) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDistance = current[1];
            if (currentDistance > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextDistance = currentDistance + next[1];
                if (nextDistance < dist[nextNode]) {
                    dist[nextNode] = nextDistance;
                    pq.offer(new int[]{nextNode, nextDistance});
                }
            }
        }

        String[] answer = new String[n];
        for (int i = 1; i <= n; i++) {
            answer[i - 1] = dist[i] == Integer.MAX_VALUE
                    ? "INF" : String.valueOf(dist[i]);
        }
        return answer;
    }
}

