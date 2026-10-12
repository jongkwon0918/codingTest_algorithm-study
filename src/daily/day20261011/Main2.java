package daily.day20261011;

import java.util.*;

/*
2. 가장 멀리 숨을 곳

1번부터 n번까지의 헛간이 있고, roads[i]={a,b}는 양방향 길이다.
모든 길을 한 번 지나는 데 드는 거리는 1이다. 모든 헛간은 서로 연결되어 있다.
1번 헛간에서 각 헛간으로 가는 최단거리를 기준으로 가장 먼 헛간을 찾는다.
반환값은 {선택한 헛간 번호, 그 헛간까지의 최단거리, 같은 최단거리를 가진 헛간 수}이다.
가장 먼 헛간이 여러 개라면 번호가 가장 작은 것을 선택한다.

제한사항
- 2 <= n <= 20,000
- 1 <= roads.length <= 50,000
- 1 <= a,b <= n, a != b
- 모든 헛간으로 이동할 수 있다.

입출력 예시
n=6, roads={{3,6},{4,3},{3,2},{1,3},{1,2},{2,4},{5,2}} -> [4,2,3]
1번에서 4,5,6번까지의 최단거리는 모두 2이다.
n=4, roads={{1,2},{2,3},{3,4}} -> [4,3,1]
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] roads = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};
        System.out.println(Arrays.toString(solution.solution(6, roads))); // [4, 2, 3]
        System.out.println(Arrays.toString(solution.solution(
                4, new int[][]{{1, 2}, {2, 3}, {3, 4}}))); // [4, 3, 1]
    }
}

class Solution2 {
    public int[] solution(int n, int[][] roads) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1,0});

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }
            for(int next : graph.get(currStart)){
                int nextCost = currCost+1;
                if(dist[next]>nextCost){
                    dist[next] = nextCost;
                    queue.offer(new int[]{next, nextCost});
                }
            }
        }
        int maxFar = 0;
        for(int i=1; i<=n; i++){
            maxFar = Math.max(maxFar, dist[i]);
        }

        List<Integer>  list = new ArrayList<>();
        int count = 0;
        for(int i=1; i<=n; i++){
            if(dist[i] == maxFar){
                list.add(i);
                count++;
            }
        }

        return new int[]{list.get(0),
                maxFar,
                count};
    }
}

