package daily.day20261102;

import java.util.*;

/*
3. 한 도시를 거치거나 피해서 도착하기

1번부터 n번까지의 도시와 단방향 도로가 있다.
roads[i]={u,v,w}는 u에서 v로 이동할 수 있고 거리가 w라는 뜻이다.
출발 도시 x에서 목적 도시 z까지 다음 두 조건의 최소 거리를 각각 반환하라.
answer[0]: 중간 도시 y를 반드시 거쳐서 가는 최소 거리.
answer[1]: 중간 도시 y를 한 번도 거치지 않고 가는 최소 거리.
각 조건을 만족하는 이동이 불가능하면 해당 원소에-1을 넣는다.
도로는 양방향이 아니며, y를 거쳐야 하는 경우에는 구간을 이어 가며 도시를 다시 지날 수 있다.
두 경우는 독립적으로 계산한다. 한쪽이 불가능해도 다른 쪽은 가능할 수 있다.

제한사항
- 원본 표기상 1 <= n <= 100000. 서로 다른 x,y,z가 필요하므로 유효 입력의 n은3 이상이다.
- 1 <= roads.length <= 200000
- 1 <= u,v <= n, u != v, 1 <= w <= 10000
- 같은 (u,v) 방향의 도로는 중복되지 않는다.
- x,y,z는1 이상 n 이하이며 서로 다르다.

입출력 예시
n=4, roads={{1,2,2},{2,4,3},{1,3,1},{3,4,7}}, x=1,y=2,z=4 -> {5,8}
1->2->4는5, 2를 피하는1->3->4는8이다.
n=3, roads={{1,3,4}}, x=1,y=2,z=3 -> {-1,4}
n=3, roads={{1,2,2},{2,3,2}}, x=1,y=2,z=3 -> {4,-1}
n=3, roads={{3,2,1}}, x=1,y=2,z=3 -> {-1,-1}
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(Arrays.toString(solution.solution(4,
                new int[][]{{1,2,2},{2,4,3},{1,3,1},{3,4,7}}, 1,2,4))); // [5, 8]
        System.out.println(Arrays.toString(solution.solution(3,
                new int[][]{{1,3,4}}, 1,2,3))); // [-1, 4]
        System.out.println(Arrays.toString(solution.solution(3,
                new int[][]{{1,2,2},{2,3,2}}, 1,2,3))); // [4, -1]
        System.out.println(Arrays.toString(solution.solution(3,
                new int[][]{{3,2,1}}, 1,2,3))); // [-1, -1]
    }
}

class Solution3 {
    public int[] solution(int n, int[][] roads, int x, int y, int z) {
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v,w});
        }

        int[] distForm = dijkstra(x,z,n,graph,0);
        int[] distFormY = dijkstra(y,z,n,graph,0);
        int[] distFormNotY = dijkstra(x,z,n,graph,y);

        return new int[]{((distForm[y]!=Integer.MAX_VALUE && distFormY[z]!=Integer.MAX_VALUE)? distForm[y]+distFormY[z]:-1), (distFormNotY[z]!=Integer.MAX_VALUE?distFormNotY[z]:-1)};
    }

    private int[] dijkstra(int start, int end, int n, List<List<int[]>> graph, int notY) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1],b[1]));
        pq.offer(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currLength = curr[1];

            if(dist[currStart]<currLength){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextLength = currLength + next[1];

                if(notY!=0 && nextStart==notY){
                    continue;
                }

                if(dist[nextStart]>nextLength){
                    dist[nextStart] = nextLength;
                    pq.offer(new int[]{nextStart, nextLength});
                }
            }
        }
        return dist;
    }
}

