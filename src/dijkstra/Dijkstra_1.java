package dijkstra;/*N개의 도시가 있습니다. 그리고 한 도시에서 출발하여 다른 도시에 도착하는 버스 노선들이 있습니다.
우리는 도시 start에서 출발하여 도시 end까지 가려고 합니다.
도시의 개수 N, 버스 노선 정보가 담긴 2차원 배열 fares,
출발 도시 start, 도착 도시 end가 매개변수로 주어집니다.
fares의 각 행은 [출발 도시, 도착 도시, 버스 비용]으로 이루어져 있습니다.
start 도시에서 end 도시까지 가는 데 드는 최소 버스 비용을 구하여 반환하는 solution 함수를 완성해 주세요.
제한사항
N은 1 이상 1,000 이하의 정수입니다.버스의 개수(fares의 길이)는 1 이상 100,000 이하입니다.
버스 비용은 0 이상 100,000 이하의 정수입니다.
항상 출발 도시에서 도착 도시로 갈 수 있는 경로가 존재한다고 가정합니다.*/

import java.sql.Array;
import java.util.*;

public class Dijkstra_1 {
    public static void main(String[] args) {
        Solution2 sol = new Solution2();

        int n2 = 5;
        int[][] fares = {
            {1, 2, 2}, {1, 3, 3}, {1, 4, 1}, {1, 5, 10},
            {2, 4, 2}, {3, 4, 1}, {3, 5, 1}, {4, 5, 3}
        };
        int start = 1;
        int end = 5;

        int result2 = sol.solution(n2, fares, start, end);
        System.out.println("도시 최소 이동 비용 결과: " + result2);
    }
}

class Solution2 {
    public int solution(int n, int[][] fares, int start, int end) {
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<end+1; i++){
            graph.add(new ArrayList<>());
        }

        for(int[] fare : fares){
            int u = fare[0];
            int w = fare[1];
            int v = fare[2];

            graph.get(u).add(new int[]{w,v});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1] - b[1]);
        pq.offer(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(currStart == end){
                return currCost;
            }

            if(currCost > dist[currStart]){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = next[1] + currCost;

                if(nextCost < dist[nextStart]) {
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost ;
                }
            }
        }


        return dist[end];
    }
}