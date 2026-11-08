package daily.day20261108.answers;

import java.util.*;

/*
3. 두 종류 시설에 모두 가까운 집

1번부터 n번까지 정점과 양방향 도로가 있다.
roads[i]={a,b,length}는 길이가 length인 도로다. 같은 두 정점 사이에 여러 도로가 있을 수 있다.
burgers에는 맥도날드 정점들, coffees에는 스타벅스 정점들이 주어진다.
어느 시설도 없는 정점에는 집이 있고, 시설 정점은 집 후보가 아니다.
한 정점에 두 종류의 시설이 함께 있을 수 있다.

집마다 가장 가까운 맥도날드까지의 최단거리와 가장 가까운 스타벅스까지의 최단거리를 구한다.
첫 거리가 burgerLimit 이하이고 둘째 거리가 coffeeLimit 이하인 집만 선택할 수 있다.
가능한 집들 중 두 최단거리 합의 최솟값을 반환하라. 없으면-1을 반환한다.
반환값은 집 번호가 아닌 거리 합이다. 두 시설에 한 경로로 차례대로 들르는 문제가 아니다.
도달할 수 없는 시설 종류가 있으면 그 집은 조건을 만족하지 않는다.

제한사항
- 3 <= n <= 10000, 0 <= roads.length <= 300000
- 1 <= a,b <= n, a != b, 1 <= length < 10000
- 1 <= burgers.length, coffees.length <= n-2
- 시설 번호는1 이상 n 이하이며 각 종류의 목록 안에서는 중복되지 않는다.
- 두 종류의 목록은 서로 겹칠 수 있다. 시설이 아닌 집 정점은 최소 하나 존재한다.
- 1 <= burgerLimit, coffeeLimit <= 100000000

입출력 예시
n=6, roads={{1,3,2},{2,4,1},{3,4,2},{3,5,5},{4,6,3}},
burgers={1,2}, burgerLimit=3, coffees={5,6}, coffeeLimit=4 -> 4
4번 집은 가까운 맥도날드2번까지1, 스타벅스6번까지3이라 합4다.
같은 지도에서 coffeeLimit=2 -> -1
n=3, roads={{1,2,2},{2,3,2}}, burgers={1}, burgerLimit=2,
coffees={1}, coffeeLimit=2 -> 4
1번은 시설이므로 제외하고, 2번 집에서 각 시설까지2씩 합4다.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[][] roads = {{1,3,2},{2,4,1},{3,4,2},{3,5,5},{4,6,3}};
        System.out.println(solution.solution(6, roads,
                new int[]{1,2}, 3, new int[]{5,6}, 4)); // 4
        System.out.println(solution.solution(6, roads,
                new int[]{1,2}, 3, new int[]{5,6}, 2)); // -1
        System.out.println(solution.solution(3,
                new int[][]{{1,2,2},{2,3,2}}, new int[]{1}, 2, new int[]{1}, 2)); // 4
    }
}

class Solution3 {
    public int solution(int n, int[][] roads, int[] burgers, int burgerLimit,
                        int[] coffees, int coffeeLimit) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] burgerDist = dijkstra(burgers, n, graph);
        int[] coffeeDist = dijkstra(coffees, n, graph);
        int answer = Integer.MAX_VALUE;

        for (int current = 1; current <= n; current++) {
            // 도로 길이가 양수이므로 거리0인 정점은 해당 종류의 시설이다.
            if (burgerDist[current] > 0 && burgerDist[current] <= burgerLimit
                    && coffeeDist[current] > 0 && coffeeDist[current] <= coffeeLimit) {
                answer = Math.min(answer, burgerDist[current] + coffeeDist[current]);
            }
        }
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private int[] dijkstra(int[] starts, int n, List<int[]>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));

        for (int start : starts) {
            dist[start] = 0;
            pq.offer(new int[]{start, 0});
        }

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentCost = current[1];
            if (currentCost > dist[currentNode]) {
                continue;
            }

            for (int[] edge : graph[currentNode]) {
                int next = edge[0];
                int nextCost = currentCost + edge[1];
                if (nextCost < dist[next]) {
                    dist[next] = nextCost;
                    pq.offer(new int[]{next, nextCost});
                }
            }
        }
        return dist;
    }
}

