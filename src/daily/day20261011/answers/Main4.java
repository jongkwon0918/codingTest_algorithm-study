package daily.day20261011.answers;

import java.util.*;

/*
4. 제한된 점프로 건너가기

직사각형 격자 map에서 0은 평지, 1은 장애물이다.
왼쪽 위 (0,0)에서 오른쪽 아래로 이동한다. 일반 이동은 상하좌우 한 칸이다.
추가로 최대 k번까지 행·열 변화량이 다음 중 하나인 점프를 사용할 수 있다.
(-2,-1),(-2,1),(-1,-2),(-1,2),(1,-2),(1,2),(2,-1),(2,1)
점프 중간에 장애물이 있어도 되지만 착지하는 칸은 평지여야 한다.
일반 이동과 점프 모두 한 번의 동작이다. 격자 밖으로 착지할 수 없다.
최소 동작 수를 반환하라. 도달할 수 없으면 -1이다.
점프를 반드시 k번 사용할 필요는 없다. 시작과 도착이 같으면 0이다.

제한사항
- 행과 열은 각각 1 이상 200 이하이다.
- 0 <= k <= 30
- map의 값은 0 또는 1이며 시작점과 도착점은 항상 0이다.

입출력 예시
map={{0,0,0},{0,0,0},{0,0,0}}, k=1 -> 2
같은 map, k=0 -> 4
map={{0,1,0},{1,1,0},{0,0,0}}, k=1 -> 2
같은 map, k=0 -> -1
map={{0}}, k=3 -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] map = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        System.out.println(solution.solution(map, 1)); // 2
        System.out.println(solution.solution(map, 0)); // 4
        int[][] blocked = {{0, 1, 0}, {1, 1, 0}, {0, 0, 0}};
        System.out.println(solution.solution(blocked, 1)); // 2
        System.out.println(solution.solution(blocked, 0)); // -1
        System.out.println(solution.solution(new int[][]{{0}}, 3)); // 0
    }
}

class Solution4 {
    public int solution(int[][] map, int k) {
        int n = map.length;
        int m = map[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int[] jumpRow = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] jumpCol = {-1, 1, -2, 2, -2, 2, -1, 1};

        boolean[][][] visited = new boolean[n][m][k + 1];
        Queue<int[]> queue = new LinkedList<>();
        // {행, 열, 사용한 점프 수, 동작 수}
        queue.offer(new int[]{0, 0, 0, 0});
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int used = current[2];
            int distance = current[3];

            if (row == n - 1 && col == m - 1) {
                return distance;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr][nc] == 0 && !visited[nr][nc][used]) {
                    visited[nr][nc][used] = true;
                    queue.offer(new int[]{nr, nc, used, distance + 1});
                }
            }

            if (used < k) {
                for (int d = 0; d < 8; d++) {
                    int nr = row + jumpRow[d];
                    int nc = col + jumpCol[d];
                    if (nr >= 0 && nr < n && nc >= 0 && nc < m
                            && map[nr][nc] == 0 && !visited[nr][nc][used + 1]) {
                        visited[nr][nc][used + 1] = true;
                        queue.offer(new int[]{nr, nc, used + 1, distance + 1});
                    }
                }
            }
        }
        return -1;
    }
}

