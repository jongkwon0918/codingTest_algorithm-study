package daily.day20261016.answers;

import java.util.*;

/*
5. 여러 벽을 부수며 도착하기

직사각형 지도에서 0은 빈칸, 1은 벽이다.
왼쪽 위 (0,0)에서 오른쪽 아래까지 상하좌우로 이동한다.
벽은 전체 경로에서 최대 k개까지 부수고 그 칸으로 이동할 수 있다.
최단 경로의 길이는 지나간 칸 수이며 시작 칸과 도착 칸도 포함한다.
도착할 수 없으면 -1을 반환하라.
시작점과 도착점이 같으면 길이는 1이다.

제한사항
- 행과 열은 각각 1 이상 1,000 이하이다.
- 1 <= k <= 10
- 모든 행의 길이는 같으며 값은 0 또는 1이다.
- 시작점과 도착점은 항상 0이다.

입출력 예시
map={{0,1,1,0}}, k=1 -> -1
같은 map, k=2 -> 4
map={{0,0,0},{1,1,0},{0,0,0}}, k=1 -> 5
map={{0}}, k=1 -> 1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] walls = {{0, 1, 1, 0}};
        System.out.println(solution.solution(walls, 1)); // -1
        System.out.println(solution.solution(walls, 2)); // 4
        System.out.println(solution.solution(
                new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}}, 1)); // 5
        System.out.println(solution.solution(new int[][]{{0}}, 1)); // 1
    }
}

class Solution5 {
    public int solution(int[][] map, int k) {
        int n = map.length;
        int m = map[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        // visited[부순 벽 수][행][열]
        boolean[][][] visited = new boolean[k + 1][n][m];
        Queue<int[]> queue = new LinkedList<>();
        // {행, 열, 부순 벽 수, 시작 칸을 포함한 길이}
        queue.offer(new int[]{0, 0, 0, 1});
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int broken = current[2];
            int distance = current[3];
            if (row == n - 1 && col == m - 1) {
                return distance;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    int nextBroken = broken + map[nr][nc];
                    if (nextBroken <= k && !visited[nextBroken][nr][nc]) {
                        visited[nextBroken][nr][nc] = true;
                        queue.offer(new int[]{nr, nc, nextBroken, distance + 1});
                    }
                }
            }
        }
        return -1;
    }
}

