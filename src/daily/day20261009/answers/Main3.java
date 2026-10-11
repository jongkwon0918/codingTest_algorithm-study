package daily.day20261009.answers;

import java.util.*;

/*
3. 중복 없이 이동하기

각 칸에 영어 대문자가 적힌 직사각형 보드가 있다.
말은 왼쪽 위 (0, 0)에서 출발하며 상하좌우 인접한 칸으로 이동한다.
지금까지 지나온 경로에서 사용한 알파벳은 다시 사용할 수 없다.
시작 칸을 포함해 지날 수 있는 최대 칸 수를 반환하라.
board[r].charAt(c)가 (r, c)의 알파벳이다.

제한사항
- 행과 열의 길이는 각각 1 이상 20 이하이다.
- 모든 문자열 길이는 같고, 각 문자는 A부터 Z까지이다.

입출력 예시
{"CAAB", "ADCB"} -> 3
{"AAAA", "AAAA"} -> 1
{"ABC"} -> 3
서로 다른 칸이라도 같은 알파벳이면 이동할 수 없다.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(new String[]{"CAAB", "ADCB"})); // 3
        System.out.println(solution.solution(new String[]{"AAAA", "AAAA"})); // 1
        System.out.println(solution.solution(new String[]{"ABC"})); // 3
    }
}

class Solution3 {
    boolean[] visited;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int n;
    int m;
    char[][] tempBoard;
    int answer;

    public int solution(String[] board) {
        // 같은 Solution 객체로 다시 호출해도 이전 상태가 남지 않도록 초기화한다.
        visited = new boolean[26];
        answer = 0;

        n = board.length;
        m = board[0].length();
        tempBoard = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tempBoard[i][j] = board[i].charAt(j);
            }
        }

        visited[tempBoard[0][0] - 'A'] = true;
        dfs(0, 0, 1);
        return answer;
    }

    private void dfs(int r, int c, int count) {
        answer = Math.max(answer, count);
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                    && !visited[tempBoard[nr][nc] - 'A']) {
                int alphabet = tempBoard[nr][nc] - 'A';
                visited[alphabet] = true;
                dfs(nr, nc, count + 1);
                visited[alphabet] = false;
            }
        }
    }
}

