/*3번. 가장 가까운 탈출구

미로가 주어진다.

0 : 이동할 수 있는 칸
-1 : 이동할 수 없는 칸
1, 2, 3 ... : 탈출구

탈출구 번호는 1부터 순서대로 주어진다.

여러 개의 시작 위치가 주어질 때, 각 시작 위치에서 가장 가까운 탈출구의 번호를 구한다.

조건은 다음과 같다.

시작 위치에서 탈출구까지의 거리가 가장 짧은 탈출구를 선택한다.
가장 짧은 거리가 같은 탈출구가 여러 개라면 아직 선택되지 않은 탈출구를 우선한다.
거리와 사용 여부까지 동일한 경우 탈출구 번호가 작은 것을 선택한다.
각 시작 위치의 결과를 배열로 반환한다.

상하좌우로만 이동할 수 있다.*/

package bankware;

import java.util.*;
public class Test3 {
    public static void main(String[] args) {

        int[][] maze = {
                {0, 0, 0, -1, 1},
                {-1, 0, 0, -1, 0},
                {0, 0, -1, 0, 2},
                {0, -1, 0, 0, 0},
                {3, 0, 0, 0, 0}
        };

        int[][] starts = {
                {0, 0},
                {4, 4},
                {2, 0}
        };

        Solution2 solution = new Solution2();

        int[] answer = solution.solution(maze, starts);

        System.out.println(Arrays.toString(answer));
    }
}

class Solution2 {

    int n;
    int m;

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int[] solution(int[][] maze, int[][] starts) {

        n = maze.length;
        m = maze[0].length;

        int[] answer = new int[starts.length];

        int maxExit = 0;
        for (int[] row : maze) {
            for (int cell : row) {
                maxExit = Math.max(maxExit, cell);
            }
        }
        boolean[] used = new boolean[maxExit + 1];

        for (int i = 0; i < starts.length; i++) {

            int sr = starts[i][0];
            int sc = starts[i][1];

            answer[i] = findExit(maze, sr, sc, used);

            if (answer[i] != -1) {
                used[answer[i]] = true;
            }
        }

        return answer;
    }

    private int findExit(int[][] maze, int sr, int sc, boolean[] used) {

        int[][] dist = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;

        int minDist = Integer.MAX_VALUE;
        int answer = -1;

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];

            if (dist[r][c] > minDist) {
                continue;
            }

            if (maze[r][c] > 0) {

                int exit = maze[r][c];

                if (dist[r][c] < minDist) {

                    minDist = dist[r][c];
                    answer = exit;

                } else if (dist[r][c] == minDist) {

                    if ((used[answer] && !used[exit])
                            || (used[answer] == used[exit] && exit < answer)) {
                        answer = exit;
                    }
                }
            }

            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && maze[nr][nc] != -1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return answer;
    }
}
