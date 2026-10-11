package daily.day20261007.answers;

import java.util.*;

/*
정답: 5번 스타트 택시
해설과 예제 추적: ../ANSWERS.md
원본 백준 19238번 스타트 택시
https://www.acmicpc.net/problem/19238
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        try {
        int[][] map = {
                {0, 0, 0}, {0, 0, 0}, {0, 0, 0}
        };
        int[] taxi = {1, 1};
        int[][] passengers = {
                {0, 1, 0, 0}, {1, 0, 2, 0}
        };
        System.out.println(solution.solution(map, taxi, passengers, 2) + " / 기대값: 2");
        System.out.println(solution.solution(map, taxi, passengers, 1) + " / 기대값: -1");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution5을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution5 {
    public int solution(int[][] map, int[] taxi, int[][] passengers, int fuel) {
        // 출발 행, 출발 열 순서로 먼저 정렬한다.
        Arrays.sort(passengers, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        int r = taxi[0];
        int c = taxi[1];
        boolean[] completed = new boolean[passengers.length];

        for (int count = 0; count < passengers.length; count++) {
            int[][] dist = bfs(map, r, c);
            int selected = -1;
            int minDist = Integer.MAX_VALUE;

            for (int i = 0; i < passengers.length; i++) {
                if (completed[i]) {
                    continue;
                }
                int[] next = passengers[i];
                int nextDist = dist[next[0]][next[1]];
                if (nextDist == -1) {
                    continue;
                }

                if (nextDist < minDist) {
                    selected = i;
                    minDist = nextDist;
                }
            }

            if (selected == -1 || minDist > fuel) {
                return -1;
            }
            fuel -= minDist;

            int[] passenger = passengers[selected];
            dist = bfs(map, passenger[0], passenger[1]);
            int rideDist = dist[passenger[2]][passenger[3]];
            if (rideDist == -1 || rideDist > fuel) {
                return -1;
            }
            fuel -= rideDist;
            fuel += rideDist * 2;
            completed[selected] = true;
            r = passenger[2];
            c = passenger[3];
        }
        return fuel;
    }

    private int[][] bfs(int[][] map, int startR, int startC) {
        int n = map.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        dist[startR][startC] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nextR = current[0] + dx[d];
                int nextC = current[1] + dy[d];
                if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < n
                        && map[nextR][nextC] == 0 && dist[nextR][nextC] == -1) {
                    dist[nextR][nextC] = dist[current[0]][current[1]] + 1;
                    queue.offer(new int[]{nextR, nextC});
                }
            }
        }
        return dist;
    }
}
