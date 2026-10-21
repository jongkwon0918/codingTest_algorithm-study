package daily.day20261020;

import java.lang.reflect.Array;
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
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph.get(u).add(new int[]{v, w});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start]=0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1],b[1]));
        pq.offer(new int[]{start,0});
        String[] answer = new String[n];
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = currCost+next[1];

                if(dist[nextStart]>nextCost){
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost;
                }
            }
        }

        for(int i=0; i<n; i++){
            answer[i] = (dist[i+1]==Integer.MAX_VALUE?"INF":String.valueOf(dist[i+1]));
        }

        return answer;
    }
}

