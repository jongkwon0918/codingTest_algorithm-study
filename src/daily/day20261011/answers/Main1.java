package daily.day20261011.answers;

import java.util.*;

/*
1. 밭을 돌보는 지렁이

직사각형 밭에서 field[row][col]이 1이면 배추가 있고, 0이면 없다.
상하좌우로 붙어 있는 배추들은 하나의 묶음이다. 대각선은 연결되지 않는다.
배추흰지렁이 한 마리는 연결된 배추 묶음 전체를 보호할 수 있다.
모든 배추를 보호하기 위해 필요한 최소 지렁이 수를 반환하라.
행은 위에서 아래, 열은 왼쪽에서 오른쪽이며 인덱스는 0부터 시작한다.

제한사항
- 1 <= field.length <= 50
- 1 <= field[0].length <= 50, 모든 행의 길이는 같다.
- 값은 0 또는 1이며 배추 수는 1 이상 2,500 이하이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
field={{1,1,0,0},{0,1,0,1},{1,0,0,1}} -> 3
field={{1,0},{0,1}} -> 2
field={{1}} -> 1
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(
                new int[][]{{1, 1, 0, 0}, {0, 1, 0, 1}, {1, 0, 0, 1}})); // 3
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 2
        System.out.println(solution.solution(new int[][]{{1}})); // 1
    }
}

class Solution1 {
    int[][] field;
    boolean[][] visited;
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] field) {
        this.field = field;
        n = field.length;
        m = field[0].length;
        visited = new boolean[n][m];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (field[row][col] == 1 && !visited[row][col]) {
                    dfs(row, col);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int row, int col) {
        visited[row][col] = true;

        for (int d = 0; d < 4; d++) {
            int nr = row + dr[d];
            int nc = col + dc[d];
            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                    && field[nr][nc] == 1 && !visited[nr][nc]) {
                dfs(nr, nc);
            }
        }
    }
}

