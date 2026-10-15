package daily.day20261015;

import java.util.*;

/*
3. 가까운 면접장까지 가장 먼 도시

도시 번호는 1부터 n까지이며 centers에는 면접장이 있는 도시 번호들이 들어 있다.
roads[i]={u,v,cost}는 u에서 v로만 갈 수 있는 단방향 도로이다.
각 도시의 지원자는 여러 면접장 중 가장 가까운 면접장으로 이동한다.
도시별로 가장 가까운 면접장까지의 최단거리를 구했을 때,
그 거리가 가장 큰 도시 번호와 거리를 {도시 번호, 거리}로 반환하라.
거리가 같은 도시가 여러 개라면 가장 작은 도시 번호를 선택한다.
면접장이 있는 도시의 거리는 0이다. 각 도시에서 적어도 한 면접장에 도달할 수 있다.
누적 거리가 int 범위를 넘을 수 있으므로 반환형은 long[]이다.

제한사항
- 2 <= n <= 100,000
- 1 <= roads.length <= 500,000
- 1 <= centers.length <= n, 면접장 도시 번호는 중복되지 않는다.
- 1 <= u,v <= n, 1 <= cost <= 100,000
- 같은 두 도시 사이에 여러 도로가 있을 수 있다.

입출력 예시
n=5, roads={{1,2,2},{2,4,3},{1,5,10},{3,4,6},{3,5,1}}, centers={4,5}
-> [1,5]
도시별 가장 가까운 면접장까지의 거리는 {5,3,1,0,0}이다.
n=3, roads={{1,3,5},{2,3,5}}, centers={3} -> [1,5]
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[][] roads = {{1, 2, 2}, {2, 4, 3}, {1, 5, 10}, {3, 4, 6}, {3, 5, 1}};
        System.out.println(Arrays.toString(solution.solution(5, roads, new int[]{4, 5})));
        // [1, 5]
        System.out.println(Arrays.toString(solution.solution(
                3, new int[][]{{1, 3, 5}, {2, 3, 5}}, new int[]{3}))); // [1, 5]
    }
}

class Solution3 {
    public long[] solution(int n, int[][] roads, int[] centers) {
        List<int[]>[] graph = new ArrayList[n+1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph[v].add(new int[]{u, w});
        }

        long[] dist = new long[n+1];
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<long[]> pq = new PriorityQueue<>((a,b)->Long.compare(a[1], b[1]));

        for (int center : centers) {
            dist[center] = 0;
            pq.offer(new long[]{center, 0});
        }

        while(!pq.isEmpty()){
            long[] curr = pq.poll();
            int currStart = (int)curr[0];
            long currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }

            for(int[] next : graph[currStart]){
                int nextStart = next[0];
                long nextCost = currCost + next[1];

                if(dist[nextStart]>nextCost){
                    pq.offer(new long[]{nextStart, nextCost});
                    dist[nextStart]=nextCost;
                }
            }
        }

        int answerCity = 1;
        long maxCost = dist[1];

        for (int city = 2; city <= n; city++) {
            if (dist[city] > maxCost) {
                maxCost = dist[city];
                answerCity = city;
            }
        }

        return new long[]{answerCity, maxCost};
    }
}
