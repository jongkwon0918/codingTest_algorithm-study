package daily.day20261016;

import java.lang.reflect.Array;
import java.util.*;

/*
2. 배송에 필요한 최소 비용

1번 헛간에서 n번 헛간까지 택배를 배달한다.
roads[i]={a,b,cost}는 a와 b를 잇는 양방향 길과 그 길에서 필요한 비용이다.
길마다 비용이 다르고, 비용이 0인 길도 있다.
배달 경로에서 지나는 길들의 비용 합이 최소가 되도록 했을 때 그 비용을 반환하라.
같은 두 헛간 사이에 여러 길이 있을 수 있다.

제한사항
- 1 <= n <= 50,000
- 1 <= roads.length <= 50,000
- 1 <= a,b <= n
- 0 <= cost <= 1,000

입출력 예시
n=4, roads={{1,2,0},{2,4,3},{1,3,1},{3,4,1}} -> 2
1->3->4의 비용은 1+1=2이다.
n=3, roads={{1,2,5},{2,3,2},{1,3,20}} -> 7
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(
                4, new int[][]{{1, 2, 0}, {2, 4, 3}, {1, 3, 1}, {3, 4, 1}})); // 2
        System.out.println(solution.solution(
                3, new int[][]{{1, 2, 5}, {2, 3, 2}, {1, 3, 20}})); // 7
    }
}

class Solution2 {
    public int solution(int n, int[][] roads) {
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v, w});
            graph.get(v).add(new int[]{u, w});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1], b[1]));
        pq.offer(new int[]{1,0});

        while (!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }
            if(currStart==n){
                return currCost;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = currCost + next[1];

                if(dist[nextStart]>nextCost){
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost;
                }
            }
        }
        return 0;
    }
}

