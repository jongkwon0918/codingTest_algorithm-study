// 문제: 특정한 최단 경로
package dijkstra;/*방향성이 없는 양방향 그래프가 있습니다. 1번 정점에서 출발하여 N번 정점으로 최단 거리로 이동하려고 합니다.
단, 임의로 주어진 두 정점 v1과 v2를 반드시 거쳐서 이동해야 합니다.한 번 이동했던 정점이나 간선을 다시 방문할 수 있습니다.
(즉, 이미 방문했던 곳을 또 지나쳐도 됩니다.)
1번 정점에서 출발해 v1과 v2를 반드시 거친 후 N번 정점으로 도달하는 최소 비용(최단 거리)을 구하는 solution 함수를 작성해 주세요.
만약 도달할 수 없다면 -1을 반환해 주세요.
제한사항
정점의 개수 N은 2 이상 800 이하의 자연수입니다.
간선의 개수(roads의 길이)는 0 이상 200,000 이하입니다.
roads의 각 행은 [출발 정점, 도착 정점, 비용]으로 이루어져 있으며, 비용은 1,000 이하의 자연수입니다.
반드시 거쳐야 하는 두 정점 v1과 v2가 주어집니다. (단, v1 != v2, v1 != 1, v2 != N)*/

import java.util.*;

public class Dijkstra_6 {
    public static void main(String[] args) {
        Solution18 sol = new Solution18();
        int n = 4;

        int[][] roads = {
                {1, 2, 3},
                {2, 3, 3},
                {3, 4, 1},
                {1, 3, 5},
                {2, 4, 5}
        };

        int v1 = 2;
        int v2 = 3;

        int answer = sol.solution(n, roads, v1, v2);

        System.out.println(answer);

    }
}


class Solution18 {
    public int solution(int n, int[][] roads, int v1, int v2) {
        List<List<int[]>> graph = new ArrayList<>();

        for (int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }

        int[] distFrom1 = dijkstra(1, n, graph);
        int[] distFromV1 = dijkstra(v1, n, graph);
        int[] distFromV2 = dijkstra(v2, n, graph);

        long pathA = (long) distFrom1[v1] + distFromV1[v2] + distFromV2[n];
        long pathB = (long) distFrom1[v2] + distFromV2[v1] + distFromV1[n];

        long answer = Math.min(pathA, pathB);
        if(answer >= Integer.MAX_VALUE){
            return -1;
        }
        return (int) answer;
    }

    private int[] dijkstra(int i, int n, List<List<int[]>> graph) {

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[i] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1] - b[1]);
        pq.offer(new int[]{i,0});



        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(currCost>dist[currStart]){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = next[1];

                if(dist[nextStart] > nextCost+currCost){
                    dist[nextStart] = nextCost+currCost;
                    pq.offer(new int[]{nextStart, nextCost+currCost});
                }
            }

        }

        return dist;
    }
}
