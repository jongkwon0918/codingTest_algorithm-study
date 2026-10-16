package daily.day20261017.answers;

import java.util.*;

/*
3. 하루 이동 제한 안에서 떡 돌리기

집 번호는 0부터 n-1까지이고 home이 내 집이다.
roads[i]={a,b,length}는 양방향 도로이다.
나머지 모든 이웃집에 떡을 하나씩 배달하려 한다.
떡은 한 번에 하나만 들고 갈 수 있어, 한 집에 배달할 때마다 반드시 내 집으로 돌아온다.
내 집에서의 최단거리가 가까운 이웃집부터 방문한다.
하루 동안 걸은 거리의 합은 dailyLimit 이하이고, 매일 내 집에서 출발해 내 집에서 끝낸다.
모든 집에 배달하는 데 필요한 최소 일수를 반환하라.
도달할 수 없는 집이 있거나 어떤 한 집의 왕복 거리도 하루 제한 안에 들어오지 못하면 -1이다.

제한사항
- 2 <= n <= 1,000
- 1 <= roads.length <= 100,000
- 1 <= dailyLimit <= 10,000,000
- 0 <= home,a,b < n, a != b
- 1 <= length <= 10,000
- 같은 두 집 사이의 도로는 하나이다.

입출력 예시
n=4, roads={{0,1,1},{0,2,2},{0,3,3}}, dailyLimit=8, home=0 -> 2
왕복 거리는 2,4,6이다. 첫날2+4, 둘째날6을 이동한다.
같은 도로, dailyLimit=5, home=0 -> -1
n=3, roads={{0,1,1}}, dailyLimit=10, home=0 -> -1
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[][] roads = {{0, 1, 1}, {0, 2, 2}, {0, 3, 3}};
        System.out.println(solution.solution(4, roads, 8, 0)); // 2
        System.out.println(solution.solution(4, roads, 5, 0)); // -1
        System.out.println(solution.solution(
                3, new int[][]{{0, 1, 1}}, 10, 0)); // -1
    }
}

class Solution3 {
    public int solution(int n, int[][] roads, int dailyLimit, int home) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[home] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{home, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDistance = current[1];
            if (currentDistance > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextDistance = currentDistance + next[1];
                if (nextDistance < dist[nextNode]) {
                    dist[nextNode] = nextDistance;
                    pq.offer(new int[]{nextNode, nextDistance});
                }
            }
        }

        Arrays.sort(dist);
        int days = 1;
        int used = 0;
        // 양수 도로이므로 정렬 후 첫 값0은 내 집의 거리이다.
        for (int i = 1; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE || dist[i] * 2 > dailyLimit) {
                return -1;
            }
            int roundTrip = dist[i] * 2;
            if (used + roundTrip > dailyLimit) {
                days++;
                used = 0;
            }
            used += roundTrip;
        }
        return days;
    }
}

