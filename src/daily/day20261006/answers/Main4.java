package daily.day20261006.answers;

import java.util.*;

/*
정답 및 해설: 4번 숨바꼭질 2
핵심 풀이: BFS 거리 배열과 방법 수 배열을 함께 관리한다. 처음 도착하면 거리와 방법 수를 기록하고, 같은 최단거리로 다시 도착하면 방법 수만 더한다. 같은 결과를 내는 서로 다른 연산도 각각 계산한다. 목표를 처음 발견한 즉시 종료하면 같은 거리의 다른 방법을 누락할 수 있다.
시간복잡도: O(L), L=100,001인 탐색 위치 수
원본: 백준 12851번 숨바꼭질 2
https://www.acmicpc.net/problem/12851
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        try {
        System.out.println(Arrays.toString(solution.solution(5, 17)) + " / 기대값: [4, 2]");
        System.out.println(Arrays.toString(solution.solution(1, 2)) + " / 기대값: [1, 2]");
        System.out.println(Arrays.toString(solution.solution(7, 7)) + " / 기대값: [0, 1]");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution4을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution4 {
    public int[] solution(int n, int k) {
        int limit = 100000;
        int[] dist = new int[limit + 1];
        int[] ways = new int[limit + 1];
        Arrays.fill(dist, -1);
        dist[n] = 0;
        ways[n] = 1;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (dist[k] != -1 && dist[current] >= dist[k]) {
                continue;
            }
            int[] nextPositions = {current - 1, current + 1, current * 2};
            for (int next : nextPositions) {
                if (next < 0 || next > limit) {
                    continue;
                }
                int nextTime = dist[current] + 1;
                if (dist[next] == -1) {
                    dist[next] = nextTime;
                    ways[next] = ways[current];
                    queue.offer(next);
                } else if (dist[next] == nextTime) {
                    ways[next] += ways[current];
                }
            }
        }
        return new int[]{dist[k], ways[k]};
    }
}

