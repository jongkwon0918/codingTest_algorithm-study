package daily.day20261022;

import javax.lang.model.type.ArrayType;
import java.util.*;

/*
2. 두 정점이 연결되는 순간의 비용

처음에는 정점 1부터 n까지만 있고 간선은 없다.
edges[i]={a,b,c}는 추가할 수 있는 양방향 간선과 그 가중치 c이다.
간선들을 원하는 순서로 하나씩 추가하다가 s와 t가 연결되는 즉시 멈춘다.
연결되었다는 것은 추가한 간선들을 따라 s에서 t로 갈 수 있다는 뜻이다.
멈춘 시점까지 추가한 모든 간선의 가중치 합을 최소로 만들었을 때 그 값을 반환하라.
s와 t만 연결하면 되며 모든 정점을 연결할 필요는 없다.

제한사항
- 2 <= n <= 5,000, 1 <= edges.length <= 100,000
- 1 <= a,b,s,t <= n, a != b, s != t
- 1 <= c <= 100
- 모든 간선을 추가하면 전체 그래프가 연결된다.

입출력 예시
n=4, edges={{1,2,2},{2,4,2},{1,3,1},{3,4,8}}, s=1,t=4 -> 4
1-2와 2-4를 차례로 추가하면 가중치 합 4로 연결된다.
n=2, edges={{1,2,7}}, s=2,t=1 -> 7
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] edges = {{1,2,2},{2,4,2},{1,3,1},{3,4,8}};
        System.out.println(solution.solution(4, edges, 1, 4)); // 4
        System.out.println(solution.solution(2, new int[][]{{1,2,7}}, 2, 1)); // 7
    }
}

class Solution2 {
    public int solution(int n, int[][] edges, int s, int t) {
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1], b[1]));
        pq.offer(new int[]{s,0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }
            if(currStart==t){
                return currCost;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = currCost + next[1];

                if(dist[nextStart]>nextCost){
                    dist[nextStart] = nextCost;
                    pq.offer(new int[]{nextStart, nextCost});
                }
            }
        }
        return 0;
    }
}

