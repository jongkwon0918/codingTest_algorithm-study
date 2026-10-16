package daily.day20261017.answers;

import java.util.*;

/*
5. 가장 짧은 다리 하나 놓기

n*n 지도에서 1은 육지, 0은 바다이다.
상하좌우로 붙어 있는 육지들은 하나의 섬이다. 대각선만 맞닿으면 다른 섬이다.
바다 칸에 다리를 놓아 서로 다른 섬 두 개를 연결하려 한다.
다리는 상하좌우로 이어져야 하며, 길이는 다리를 놓은 바다 칸 수이다.
어떤 두 섬을 선택해도 되며, 모든 섬을 연결할 필요는 없다.
가장 짧은 다리 하나의 길이를 반환하라.

제한사항
- 1 <= n <= 100, map은 n*n 배열이다.
- 값은 0 또는 1이며 항상 두 개 이상의 섬이 있다.
- 다리는 바다에만 놓고 육지는 길이에 포함하지 않는다.

입출력 예시
map={{1,0,1},{1,0,0},{0,0,0}} -> 1
map={{1,0,0},{0,0,0},{0,0,1}} -> 3
map={{1,0},{0,1}} -> 1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                new int[][]{{1, 0, 1}, {1, 0, 0}, {0, 0, 0}})); // 1
        System.out.println(solution.solution(
                new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 1}})); // 3
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 1
    }
}

class Solution5 {
    int n;
    int[][] map;
    int[][] group;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] map) {
        this.map = map;
        n = map.length;
        group = new int[n][n];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (map[row][col] == 1 && group[row][col] == 0) {
                    count++;
                    label(row, col, count);
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int island = 1; island <= count; island++) {
            answer = Math.min(answer, bfs(island));
        }
        return answer;
    }

    private void label(int startRow, int startCol, int number) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        group[startRow][startCol] = number;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && map[nr][nc] == 1 && group[nr][nc] == 0) {
                    group[nr][nc] = number;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }

    private int bfs(int island) {
        int[][] dist = new int[n][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
            for (int col = 0; col < n; col++) {
                if (group[row][col] == island) {
                    dist[row][col] = 0;
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && dist[nr][nc] == -1) {
                    if (group[nr][nc] != 0) {
                        return dist[row][col];
                    }
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}

