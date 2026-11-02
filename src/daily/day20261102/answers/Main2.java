package daily.day20261102.answers;

import java.util.*;

/*
2. 벽을 피해 만날 수 있는 사람

직사각형 캠퍼스에서 I는 내 위치, O는 빈 공간, X는 벽, P는 사람이다.
상하좌우로 벽이 아닌 인접 칸으로 이동할 수 있다.
현재 위치에서 만날 수 있는 서로 다른 사람의 수를 문자열로 반환하라.
같은 사람을 여러 번 세지 않고, 사람 칸을 지나서 더 이동할 수도 있다.
만날 수 있는 사람이 하나도 없으면 "TT"를 반환한다.
가장 가까운 사람 한 명까지의 거리를 구하는 문제가 아니다.

제한사항
- 1 <= campus.length <= 600
- 1 <= campus[0].length() <= 600, 모든 문자열 길이는 같다.
- 문자는 I,O,X,P 중 하나이며 I는 정확히 하나이다.

입출력 예시
campus={"IOP","XXO","POP"} -> "3"
오른쪽 위 사람을 지나 아래로 이동하여 나머지 두 사람도 만날 수 있다.
campus={"IXP"} -> "TT"
campus={"I"} -> "TT"
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new String[]{
            "IOP", "XXO", "POP"
        })); // 3
        System.out.println(solution.solution(new String[]{"IXP"})); // TT
        System.out.println(solution.solution(new String[]{"I"})); // TT
    }
}

class Solution2 {
    public String solution(String[] campus) {
        int n = campus.length;
        int m = campus[0].length();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (campus[r].charAt(c) == 'I') {
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            if (campus[r].charAt(c) == 'P') {
                count++;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && campus[nr].charAt(nc) != 'X' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return count == 0 ? "TT" : String.valueOf(count);
    }
}

