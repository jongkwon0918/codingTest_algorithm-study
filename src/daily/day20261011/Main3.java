package daily.day20261011;

import java.util.*;

/*
3. 감시를 피해 목적지까지

분기점 수 n은 visible.length이며 번호는 0부터 n-1까지이다.
0번에서 출발해 n-1번 목적지로 이동하려 한다.
visible[i]가 0이면 안전한 분기점, 1이면 감시 중인 분기점이다.
감시 중인 분기점으로 이동할 수 없다. 단, 목적지 n-1번은 감시 중이어도 갈 수 있다.
roads[i]={a,b,time}은 양방향 도로와 이동 시간을 나타낸다.
허용된 분기점만 이용해 목적지까지 가는 최소 시간을 반환하라.
이동할 수 없으면 -1을 반환한다. 결과는 int 범위를 넘을 수 있으므로 long이다.

제한사항
- 1 <= n <= 100,000
- 1 <= roads.length <= 300,000
- visible의 값은 0 또는 1이며 visible[0]=0, visible[n-1]=1이다.
- 0 <= a,b < n, a != b
- 1 <= time <= 100,000
- 두 분기점 사이의 도로는 최대 하나이다.

입출력 예시
visible={0,1,0,0,1}
roads={{0,1,1},{1,4,1},{0,2,4},{2,3,3},{3,4,2},{0,4,20}} -> 9
0->1->4는 빠르지만 1번이 감시 중이다. 0->2->3->4로 이동한다.
visible={0,1,1}, roads={{0,1,1},{1,2,1}} -> -1
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[] visible = {0, 1, 0, 0, 1};
        int[][] roads = {{0, 1, 1}, {1, 4, 1}, {0, 2, 4}, {2, 3, 3}, {3, 4, 2}, {0, 4, 20}};
        System.out.println(solution.solution(visible, roads)); // 9
        System.out.println(solution.solution(
                new int[]{0, 1, 1}, new int[][]{{0, 1, 1}, {1, 2, 1}})); // -1
    }
}

class Solution3 {
    public long solution(int[] visible, int[][] roads) {
        int n = visible.length;
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>((a,b)->Long.compare(a[1],b[1]));
        pq.offer(new long[]{0,0});

        while(!pq.isEmpty()){
            long[] curr = pq.poll();
            int currStart = (int) curr[0];
            long currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }
            if(currStart==n-1){
                return currCost;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                long nextCurr = currCost + next[1];

                if((visible[nextStart]==0 || nextStart==n-1) && dist[nextStart]>nextCurr){
                    dist[nextStart] = nextCurr;
                    pq.offer(new long[]{nextStart, nextCurr});
                }
            }
        }

        return -1;
    }
}

