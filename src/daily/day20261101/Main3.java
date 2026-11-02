package daily.day20261101;

import java.lang.reflect.Array;
import java.util.*;

/*
3. 가장 가까운 친구로부터 멀리 살기

1번부터 n번까지의 땅 중 집을 구하려고 한다.
friends에는 세 친구가 사는 땅 번호가 주어진다. 같은 땅에 사는 친구도 있을 수 있다.
roads[i]={a,b,length}는 a와 b를 양방향으로 연결하는 길이 length의 도로이다.
한 후보 땅에서 세 친구 각각에게 가는 최단 도로 거리를 구한 뒤,
그중 가장 작은 값을 그 땅의 평가 거리로 정한다.
이 평가 거리가 가장 큰 땅의 번호를 반환하라. 동률이면 가장 작은 땅 번호를 반환한다.
친구까지의 거리 합이나, 가장 먼 친구까지의 거리를 최대화하는 문제가 아니다.

예를 들어 X에서 친구들까지 거리가3,5,4이면 평가 거리는3이다.
Y에서 거리가5,7,2이면 평가 거리는2이므로 X가 더 좋은 후보이다.

제한사항
- 1 <= n <= 100000
- friends.length == 3, 각 번호는1 이상 n 이하이다.
- n-1 <= roads.length <= 500000
- 도로 양 끝의 번호는1 이상 n 이하, 길이는1 이상10000 이하이다.

입출력 예시
n=6, friends={1,2,3},
roads={{1,4,2},{2,4,2},{3,4,2},{4,5,3},{4,6,3}} -> 5
4번의 평가 거리는2, 5번과6번은5다. 동률이므로5번을 선택한다.
n=4, friends={1,1,1}, roads={{1,2,1},{2,3,1},{3,4,1}} -> 4
n=1, friends={1,1,1}, roads={} -> 1
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(6, new int[]{1,2,3},
                new int[][]{{1,4,2},{2,4,2},{3,4,2},{4,5,3},{4,6,3}})); // 5
        System.out.println(solution.solution(4, new int[]{1,1,1},
                new int[][]{{1,2,1},{2,3,1},{3,4,1}})); // 4
        System.out.println(solution.solution(1, new int[]{1,1,1},
                new int[0][3])); // 1
    }
}

class Solution3 {
    public int solution(int n, int[] friends, int[][] roads) {
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

        int[] distA = dijkstra(friends[0], n, graph);
        int[] distB = dijkstra(friends[1], n, graph);
        int[] distC = dijkstra(friends[2], n, graph);

        int answerMaxCost = -1;
        int answerMaxIdx = 1;
        for(int i=1; i<=n; i++){
            int minCost = Math.min(distA[i], Math.min(distB[i], distC[i]));
            if(answerMaxCost<minCost){
                answerMaxCost = minCost;
                answerMaxIdx = i;
            }
        }

        return answerMaxIdx;
    }

    private int[] dijkstra(int start, int n, List<List<int[]>> graph) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});
        dist[start]=0;

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(dist[currStart]<currCost){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = next[1] + currCost;

                if(dist[nextStart]>nextCost){
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost;
                }
            }
        }
        return dist;
    }
}

