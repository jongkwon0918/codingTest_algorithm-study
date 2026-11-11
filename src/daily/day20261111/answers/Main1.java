package daily.day20261111.answers;

import java.util.*;

/*
1. 가장 큰 덩어리

세로 n, 가로 m인 통로에 음식물이 떨어져 있다.
waste[i]={r,c}는 음식물이 있는 칸이며 좌표는 1부터 시작한다.
상하좌우로 이어진 음식물은 하나의 덩어리이고 대각선은 연결되지 않는다.
가장 큰 덩어리를 이루는 칸 수를 반환하라.

제한사항
- 1 <= n,m <= 100
- 1 <= waste.length <= n*m
- 1 <= r <= n, 1 <= c <= m, 좌표는 중복되지 않는다.

입출력 예시
n=3,m=4,waste={{3,2},{2,2},{3,1},{2,3},{1,1}} -> 4
(3,2),(2,2),(3,1),(2,3)이 하나의 덩어리다.
n=2,m=2,waste={{1,1},{2,2}} -> 1
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(3, 4, new int[][]{
            {3,2},{2,2},{3,1},{2,3},{1,1}
        })); // 4
        System.out.println(solution.solution(2, 2, new int[][]{{1,1},{2,2}})); // 1
    }
}

class Solution1 {
    public int solution(int n, int m, int[][] waste) {
        boolean[][] map = new boolean[n][m];
        boolean[][] visited = new boolean[n][m];
        for (int[] point : waste) {
            map[point[0] - 1][point[1] - 1] = true;
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int answer = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] && !visited[r][c]) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{r, c});
                    visited[r][c] = true;
                    int count = 0;
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        count++;
                        for (int d = 0; d < 4; d++) {
                            int nr = current[0] + dr[d];
                            int nc = current[1] + dc[d];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                    && map[nr][nc] && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                queue.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    answer = Math.max(answer, count);
                }
            }
        }
        return answer;
    }
}

