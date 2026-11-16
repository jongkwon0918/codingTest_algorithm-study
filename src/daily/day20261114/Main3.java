package daily.day20261114;

import java.lang.reflect.Array;
import java.util.*;

/*
3. 조건을 차례대로 적용하는 약속 장소

1번부터 n번까지 장소와 양방향 도로가 있다. roads[i]={a,b,time}이다.
지헌이는 j에서, 성하는 s에서 동시에 출발해 한 장소에서 만나려고 한다.
각 사람은 해당 장소까지 최단 시간으로 이동한다. 다음 조건을 반드시 순서대로 적용하라.
1. j와 s는 만날 장소 후보에서 제외한다. 두 사람이 모두 도달할 수 있는 장소만 후보가 된다.
2. 후보 전체에서 두 사람의 최단 이동 시간 합이 가장 작은 장소들만 남긴다.
3. 그중 지헌이의 이동 시간이 성하보다 긴 장소를 제외한다. 두 시간이 같으면 가능하다.
4. 남은 곳 중 지헌이의 이동 시간이 가장 작은 장소를 선택한다.
5. 그래도 여러 곳이면 번호가 가장 작은 장소를 선택한다.
선택한 장소 번호를 반환하고, 남은 곳이 없으면 -1을 반환하라.
중요: 3번에서 모두 탈락했다고 시간 합이 더 큰 장소를 다시 고르면 안 된다.

제한사항
- 2 <= n <= 100, 1 <= roads.length <= 1000
- 1 <= a,b,j,s <= n, 1 <= time <= 10000
- 같은 장소 사이 도로가 여러 개여도 처리해야 한다.
- 지헌이와 성하는 서로 만날 수 있는 입력이다. 선택 조건에 맞는 제3의 장소가 있다는 뜻은 아니다.

입출력 예시
n=4, roads={{1,2,2},{2,4,2},{1,3,2},{3,4,2}}, j=1,s=4 -> 2
2,3은 모두 시간 합4, 지헌이 시간2라서 번호가 작은2를 선택한다.
n=4, roads={{1,2,4},{4,2,1},{1,3,3},{4,3,3}}, j=1,s=4 -> -1
2번은 합5지만 지헌이가 늦어 탈락한다. 3번은 합6이므로 다시 후보로 선택할 수 없다.
n=3, roads={{1,2,2},{2,3,2}}, j=1,s=3 -> 2
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(4, new int[][]{
                {1, 2, 2}, {2, 4, 2}, {1, 3, 2}, {3, 4, 2}
        }, 1, 4)); // 2
        System.out.println(solution.solution(4, new int[][]{
                {1, 2, 4}, {4, 2, 1}, {1, 3, 3}, {4, 3, 3}
        }, 1, 4)); // -1
        System.out.println(solution.solution(3, new int[][]{{1, 2, 2}, {2, 3, 2}}, 1, 3)); // 2
    }
}

class Solution3 {
    public int solution(int n, int[][] roads, int j, int s) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int w = road[2];

            graph.get(u).add(new int[]{v, w});
            graph.get(v).add(new int[]{u, w});
        }

        int[] distJ = dijkstra(j, graph, n);
        int[] distS = dijkstra(s, graph, n);

        int minLength = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;
        int answer = -1;

        for (int i = 1; i <= n; i++) {
            if (i != j && i != s && distJ[i] != Integer.MAX_VALUE && distS[i] != Integer.MAX_VALUE) {
                minLength = Math.min(minLength, distJ[i] + distS[i]);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (i != j && i != s && distJ[i] != Integer.MAX_VALUE && distS[i] != Integer.MAX_VALUE && distJ[i] + distS[i] == minLength && distJ[i] <= distS[i]) {

                if (minJ > distJ[i]) {
                    minJ = distJ[i];
                    answer = i;
                }


            }
        }

        return answer;
    }

    private int[] dijkstra(int i, List<List<int[]>> graph, int n) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{i, 0});
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[i] = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if (dist[currStart] < currCost) {
                continue;
            }

            for (int[] next : graph.get(currStart)) {
                int nextStart = next[0];
                int nextCost = next[1] + currCost;

                if (dist[nextStart] > nextCost) {
                    pq.offer(new int[]{nextStart, nextCost});
                    dist[nextStart] = nextCost;
                }
            }
        }

        return dist;
    }
}

