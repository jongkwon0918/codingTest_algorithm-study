package daily.day20261006.answers;

import java.util.*;

/*
정답 및 해설: 3번 지름길
핵심 풀이: 0부터 D까지를 정점으로 보고 일반 도로 i→i+1과 지름길을 방향 간선으로 만든다. D를 넘어가는 지름길은 제외한다. 다익스트라로 최소 비용을 구하면 길지만 불리한 지름길도 자연스럽게 선택하지 않는다. 이 문제는 앞쪽으로만 이동하므로 순서대로 거리 배열을 갱신하는 DP로도 풀 수 있다.
시간복잡도: O((D + S) log(D + S)), S는 지름길 수
원본: 백준 1446번 지름길
https://www.acmicpc.net/problem/1446
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        try {
        int[][] shortcuts = {
                {0, 50, 10}, {0, 50, 20}, {50, 100, 10},
                {100, 151, 10}, {110, 140, 90}
        };
        System.out.println(solution.solution(150, shortcuts) + " / 기대값: 70");
        System.out.println(solution.solution(100, new int[][]{
                {0, 101, 1}, {20, 40, 30}
        }) + " / 기대값: 100");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution3을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution3 {
    public int solution(int d, int[][] shortcuts) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= d; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < d; i++) {
            graph.get(i).add(new int[]{i + 1, 1});
        }
        for (int[] shortcut : shortcuts) {
            if (shortcut[1] <= d) {
                graph.get(shortcut[0]).add(new int[]{shortcut[1], shortcut[2]});
            }
        }

        int[] dist = new int[d + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        // {누적 거리, 현재 위치}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[0], b[0])
        );
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int position = current[1];
            if (cost != dist[position]) {
                continue;
            }
            if (position == d) {
                return cost;
            }
            for (int[] next : graph.get(position)) {
                int nextCost = cost + next[1];
                if (nextCost < dist[next[0]]) {
                    dist[next[0]] = nextCost;
                    pq.offer(new int[]{nextCost, next[0]});
                }
            }
        }
        return dist[d];
    }
}

