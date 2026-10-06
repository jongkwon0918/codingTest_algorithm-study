// 문제: 파티
package dijkstra;/*N개의 숫자로 구분된 각각의 마을에 한 명의 학생이 살고 있습니다.
어느 날 이 N명의 학생들이 X번 마을에 모여서 파티를 벌이기로 했습니다.
마을 사이에는 총 M개의 단방향(방향성이 있는) 도로들이 존재하며, 각 도로마다 지나는 데 소요되는 시간이 있습니다.
학생들은 X번 마을에 모였다가 다시 각자의 원래 마을로 돌아가야 합니다. 모든 학생들은 가장 빠른(최단 시간) 경로로 왕복하려고 합니다.
이 N명의 학생들 중 왕복하는 데 가장 오랜 시간이 걸리는 학생의 소요 시간을 구하는 solution 함수를 작성해 주세요.
제한사항
마을의 개수 N은 1 이상 1,000 이하의 정수입니다.
도로의 개수 M은 1 이상 10,000 이하의 정수입니다.
목적지 마을 번호 X는 1 이상 N 이하의 정수입니다.
도로 정보 roads는 [출발 마을, 도착 마을, 소요 시간] 형태로 이루어진 2차원 배열입니다.
소요 시간은 100 이하의 자연수입니다.모든 학생은 X번 마을로 갈 수 있고, 다시 원래 마을로 돌아올 수 있는 데이터만 주어집니다.*/
import java.util.*;

public class Dijkstra_7 {
    public static void main(String[] args) {
        Solution20 sol = new Solution20();

        int n = 4;
        int x = 2;

        int[][] roads = {
                {1, 2, 4},
                {1, 4, 7},
                {2, 1, 1},
                {2, 3, 5},
                {3, 1, 2},
                {3, 4, 4},
                {4, 2, 3}
        };

        int result = sol.solution(n, x, roads);

        System.out.println(result);
    }
}

class Solution20 {
    public int solution(int n, int x, int[][] roads) {
        List<List<int[]>> comeGraph = new ArrayList<>();
        List<List<int[]>> backGraph = new ArrayList<>();

        for(int i=0; i<=n; i++) {
            comeGraph.add(new ArrayList<>());
            backGraph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            comeGraph.get(u).add(new int[]{v,w});
            backGraph.get(v).add(new int[]{u,w});
        }

        int[] comeDist = Dijkstra(n, x, comeGraph);
        int[] backDist = Dijkstra(n, x, backGraph);

        int maxTime = 0;
        for(int i=1; i<n+1; i++){
            maxTime = Math.max(maxTime, (comeDist[i]+backDist[i]));
        }

        return maxTime;
    }

    private int[] Dijkstra(int n, int start, List<List<int[]>> graph) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
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
                int nextCost = next[1];
                if(dist[nextStart] > nextCost + currCost){
                    dist[nextStart] = nextCost + currCost;
                    pq.offer(new int[]{nextStart, nextCost + currCost});
                }
            }
        }

        return dist;
    }
}