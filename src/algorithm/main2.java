/*2번. 가장 가까운 출구

0은 이동할 수 있는 칸, 1은 벽이다.

미로에서 (sr, sc)에서 시작하여 가장 가까운 출구까지의 최소 이동 횟수를 구하라.

출구는 미로의 가장자리 칸이다. 시작 칸은 출구에서 제외한다.

출구에 도착할 수 없다면 -1을 반환한다.*/
package algorithm;
import java.util.*;
public class main2 {
    public static void main(String[] args) {

        Solution2 solution = new Solution2();

        // 테스트 입력 직접 작성
        int[][] maze = {
                {0, 1, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 1, 0},
                {0, 0, 0, 0}
        };

        int sr = 1;
        int sc = 1;

        int answer = solution.solution(maze, sr, sc);

        System.out.println(answer);
    }
}


class Solution2 {
    public int solution(int[][] maze, int sr, int sc) {
        int n = maze.length;
        int m = maze[0].length;

        int[][] dist = new int[n][m];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            int r = cur[0];
            int c = cur[1];

            if ((r == 0 || r == n - 1 || c == 0 || c == m - 1)
                    && !(r == sr && c == sc)) {
                return dist[r][c];
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr>=0 && nr<n && nc>=0 && nc<m
                        && maze[nr][nc] != 1 && dist[nr][nc]==-1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }

            }
        }

        return -1;
    }
}
