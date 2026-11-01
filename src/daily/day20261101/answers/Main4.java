package daily.day20261101.answers;

import java.util.*;

/*
4. 열쇠를 모아 문을 지나 탈출하기

직사각형 미로에서 시작점0부터 출구1 중 어느 하나까지 가는 최소 이동 횟수를 반환하라.
상하좌우로 한 칸 이동할 때마다 횟수가1 늘어난다.
문자의 의미는 다음과 같다.
'.': 빈칸, '#': 벽, '0': 시작점, '1': 출구
'a'~'f': 해당 종류의 열쇠, 'A'~'F': 같은 알파벳의 열쇠가 있어야 통과할 수 있는 문
열쇠 칸으로 들어가면 즉시 얻고, 얻은 열쇠는 사라지지 않으며 여러 번 사용할 수 있다.
문이나 열쇠가 같은 종류로 여러 개 있을 수 있고, 문에 대응하는 열쇠가 아예 없을 수도 있다.
이미 지나간 칸도 다시 지나갈 수 있다. 탈출할 수 없으면-1을 반환하라.
이동한 경로의 열쇠만 사용할 수 있으며, 다른 경로에서 얻은 열쇠를 공유하지 않는다.

제한사항
- 1 <= maze.length <= 50
- 1 <= maze[0].length() <= 50, 모든 문자열 길이는 같다.
- 위에 설명한 문자만 등장한다.
- 시작점0은 정확히 하나, 출구1은 최소 하나 존재한다.

입출력 예시
maze={"0aA1"} -> 3
열쇠a를 얻고 문A를 지나 출구로 간다.
maze={"0A1"} -> -1
maze={"a0A1"} -> 4
왼쪽으로 가서 열쇠a를 얻은 뒤 시작 칸으로 돌아와 문A를 통과한다.
maze={"01"} -> 1
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new String[]{"0aA1"})); // 3
        System.out.println(solution.solution(new String[]{"0A1"})); // -1
        System.out.println(solution.solution(new String[]{"a0A1"})); // 4
        System.out.println(solution.solution(new String[]{"01"})); // 1
    }
}

class Solution4 {
    public int solution(String[] maze) {
        int n = maze.length;
        int m = maze[0].length();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][][] visited = new boolean[n][m][64];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (maze[r].charAt(c) == '0') {
                    // {행, 열, 보유 열쇠 상태, 이동 횟수}
                    queue.offer(new int[]{r, c, 0, 0});
                    visited[r][c][0] = true;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int keys = current[2];
            int count = current[3];

            if (maze[r].charAt(c) == '1') {
                return count;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && maze[nr].charAt(nc) != '#') {
                    char cell = maze[nr].charAt(nc);
                    int nextKeys = keys;
                    boolean canMove = true;

                    if (cell >= 'A' && cell <= 'F') {
                        int door = 1 << (cell - 'A');
                        canMove = (keys & door) != 0;
                    }

                    if (cell >= 'a' && cell <= 'f') {
                        nextKeys = keys | (1 << (cell - 'a'));
                    }

                    if (canMove && !visited[nr][nc][nextKeys]) {
                        visited[nr][nc][nextKeys] = true;
                        queue.offer(new int[]{nr, nc, nextKeys, count + 1});
                    }
                }
            }
        }
        return -1;
    }
}

