package daily.day20261007.answers;

import java.util.*;

/*
정답: 3번 해킹
해설과 예제 추적: ../ANSWERS.md
원본 백준 10282번 해킹
https://www.acmicpc.net/problem/10282
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        try {
        System.out.println(Arrays.toString(solution.solution(3, 2, new int[][]{
                {2, 1, 5}, {3, 2, 5}
        })) + " / 기대값: [2, 5]");
        System.out.println(Arrays.toString(solution.solution(3, 1, new int[][]{
                {2, 1, 2}, {3, 1, 8}, {3, 2, 4}
        })) + " / 기대값: [3, 6]");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution3을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution3 {
    public int[] solution(int n, int start, int[][] dependencies) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] dependency : dependencies) {
            int a = dependency[0];
            int b = dependency[1];
            int seconds = dependency[2];
            graph[b].add(new int[]{a, seconds});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        // {컴퓨터 번호, 감염 시각}
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );
        queue.offer(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentNode = current[0];
            int currentDist = current[1];
            if (currentDist > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextDist = currentDist + next[1];
                if (nextDist < dist[nextNode]) {
                    dist[nextNode] = nextDist;
                    queue.offer(new int[]{nextNode, nextDist});
                }
            }
        }

        int count = 0;
        int time = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] != Integer.MAX_VALUE) {
                count++;
                time = Math.max(time, dist[i]);
            }
        }
        return new int[]{count, time};
    }
}

