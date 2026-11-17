package daily.day20261117.answers;

import java.util.*;

/*
2. 여러 음식 중 가장 가까운 곳

직사각형 지도에서 '0'은 빈칸, '1'은 장애물, '2'는 시작점이다.
'3','4','5'는 서로 다른 음식이 있는 칸이다.
상하좌우 인접한 칸으로 이동하고 한 칸 이동 비용은1이다. 장애물을 통과할 수 없다.
세 음식 중 하나라도 도착하면 된다. 음식 종류의 우선순위는 없으며 모두 먹을 필요도 없다.
가장 가까운 음식까지의 최소 이동 횟수를 반환하고 아무 음식에도 갈 수 없으면 -1을 반환하라.
원본의 성공 여부 TAK/NIE와 거리를, 이 형식에서는 거리 또는 -1로 반환한다.

제한사항
- 행과 열의 길이는 각각1~3000이며 모든 문자열 길이는 같다.
- 전체 칸 수는4 이상9000000 이하이다.
- 문자는 '0'~'5'이며 '2','3','4','5'는 각각 한 번 등장한다.

입출력 예시
{"20003","11100","40005"} -> 4
시작점에서 오른쪽으로 네 칸 이동해3에 도착한다.
{"211","111","345"} -> -1
{"2345"} -> 1
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new String[]{"20003","11100","40005"})); // 4
        System.out.println(solution.solution(new String[]{"211","111","345"})); // -1
        System.out.println(solution.solution(new String[]{"2345"})); // 1
    }
}

class Solution2 {
    public int solution(String[] map) {
        int n = map.length;
        int m = map[0].length();
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r].charAt(c) == '2') {
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c, 0});
                }
            }
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int distance = current[2];
            char value = map[r].charAt(c);
            if (value == '3' || value == '4' || value == '5') {
                return distance;
            }
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr].charAt(nc) != '1' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, distance + 1});
                }
            }
        }
        return -1;
    }
}

