package daily.day20261025.answers;

import java.util.*;

/*
2. 모두에게 가까운 사람

1부터 n까지의 사람들이 있고 friendships[i]={a,b}이면 두 사람이 서로 친구이다.
친구를 한 번 거쳐 이동하는 것을1단계로 본다.
각 사람에 대해 다른 모든 사람까지의 최소 단계 수를 더한 값을 구한다.
이 합이 가장 작은 사람의 번호를 반환하라. 합이 같으면 번호가 작은 사람을 선택한다.
자기 자신까지의 거리는0이고, 모든 사람은 친구 관계를 거쳐 서로 연결되어 있다.

제한사항
- 2 <= n <= 100, 1 <= friendships.length <= 5,000
- 1 <= a,b <= n, a != b
- 같은 친구 관계가 중복되어 주어질 수도 있다.
- 한 사람이 여러 명과 친구일 수 있고 관계는 양방향이다.

입출력 예시
n=4, friendships={{1,2},{2,3},{3,4}} -> 2
사람별 합은6,4,4,6이고 동률인2와3 중2를 선택한다.
n=4, friendships={{1,2},{1,3},{1,4}} -> 1
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(
                4, new int[][]{{1,2},{2,3},{3,4}})); // 2
        System.out.println(solution.solution(
                4, new int[][]{{1,2},{1,3},{1,4}})); // 1
    }
}

class Solution2 {
    public int solution(int n, int[][] friendships) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] friendship : friendships) {
            graph[friendship[0]].add(friendship[1]);
            graph[friendship[1]].add(friendship[0]);
        }

        int answer = 1;
        int minSum = Integer.MAX_VALUE;
        for (int start = 1; start <= n; start++) {
            int sum = bfs(start, n, graph);
            if (sum < minSum) {
                minSum = sum;
                answer = start;
            }
        }
        return answer;
    }

    private int bfs(int start, int n, List<Integer>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += dist[i];
        }
        return sum;
    }
}

