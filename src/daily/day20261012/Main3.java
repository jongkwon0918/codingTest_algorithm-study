package daily.day20261012;

import java.util.*;

/*
3. 아이템을 가장 많이 모을 시작점

지역 수 n은 items.length이고 지역 번호는 1부터 n까지이다.
items[i-1]은 i번 지역에 있는 아이템 수이다.
roads[i]={a,b,length}는 길이가 length인 양방향 도로이다.
시작 지역을 자유롭게 하나 선택할 수 있다.
선택한 시작 지역에서 최단거리가 range 이하인 모든 지역의 아이템을 얻는다.
시작 지역의 아이템도 포함하고, 각 지역의 아이템은 한 번만 센다.
얻을 수 있는 아이템 수의 최댓값을 반환하라.
range는 시작 지역에서 각 지역까지의 거리 제한이며,
여러 지역을 방문하는 전체 여행 거리의 합을 제한하는 값이 아니다.

제한사항
- 1 <= n <= 100
- 1 <= range <= 15
- 1 <= items[i] <= 30
- 1 <= roads.length <= 100
- 지역 번호는 1 이상 n 이하, 도로 길이는 1 이상 15 이하이다.
- 모든 지역이 연결되어 있다는 보장은 없다.

입출력 예시
items={5,7,8}, range=2, roads={{1,2,2},{2,3,2}} -> 20
2번에서 각 지역까지 거리가 2 이하이므로 모두 얻는다.
items={5,7,8}, range=1, 같은 roads -> 8
items={2,3,10}, range=2, roads={{1,2,1}} -> 10
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[] items = {5, 7, 8};
        int[][] roads = {{1, 2, 2}, {2, 3, 2}};
        System.out.println(solution.solution(items, 2, roads)); // 20
        System.out.println(solution.solution(items, 1, roads)); // 8
        System.out.println(solution.solution(
                new int[]{2, 3, 10}, 2, new int[][]{{1, 2, 1}})); // 10
    }
}

class Solution3 {
    int n;
    public int solution(int[] items, int range, int[][] roads) {
        n = items.length;
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }

        int maxItem = 0;

        for(int i=1; i<=n; i++){
            maxItem = Math.max(maxItem, dijkstra(items, range, i, graph));
        }

        return maxItem;
    }

    private int dijkstra(int[] items, int range, int start, List<List<int[]>> graph) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = currCost+ next[1];

                if(nextCost<=range && dist[nextStart]>nextCost){
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost;
                }
            }
        }

        int item = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] <= range) {
                item += items[i - 1];
            }
        }
        return item;

    }
}

