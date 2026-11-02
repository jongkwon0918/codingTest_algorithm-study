package daily.day20261102.answers;

import java.util.*;

/*
4. 한 칸을 채워 가장 큰 모양 만들기

0과1로 이루어진 직사각형 배열이 주어진다.
상하좌우로 연결된1의 묶음을 하나의 모양이라고 하고, 포함된 칸 수를 모양의 크기라고 한다.
0인 칸 정확히 하나를1로 바꿀 수 있다.
바꾼 뒤 존재하는 모양 중 가장 큰 크기를 최대화하여 반환하라.
대각선으로 닿는 칸은 연결되지 않는다. 같은 모양에 속한 칸을 여러 번 세면 안 된다.

제한사항
- 2 <= board.length <= 1000
- 2 <= board[0].length <= 1000, 모든 행의 길이는 같다.
- 각 값은0 또는1이며0과1이 각각 최소 하나 존재한다.
- 한 번만 바꾸며, 서로 다른 후보 칸에 대한 변경을 누적하지 않는다.

입출력 예시
board={{1,0},{0,1}} -> 3
board={{1,1,1},{1,0,1},{1,1,1}} -> 9
가운데 칸은 네 방향 모두 같은 모양에 닿지만 기존8칸을 네 번 더하면 안 된다.
board={{1,0,1},{1,0,1}} -> 5
가운데 열의 한 칸을 채우면 두 모양이 합쳐진다.
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new int[][]{
            {1,0},{0,1}
        })); // 3
        System.out.println(solution.solution(new int[][]{
            {1,1,1},{1,0,1},{1,1,1}
        })); // 9
        System.out.println(solution.solution(new int[][]{
            {1,0,1},{1,0,1}
        })); // 5
    }
}

class Solution4 {
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        n = board.length;
        m = board[0].length;
        int[][] group = new int[n][m];
        int[] sizes = new int[n * m + 1];
        int groupCount = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 1 && group[r][c] == 0) {
                    groupCount++;
                    sizes[groupCount] = bfs(r, c, groupCount, board, group);
                }
            }
        }

        int answer = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 0) {
                    int count = 1;
                    int[] seen = new int[4];
                    int seenCount = 0;

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                && group[nr][nc] > 0) {
                            int id = group[nr][nc];
                            boolean duplicate = false;
                            for (int i = 0; i < seenCount; i++) {
                                if (seen[i] == id) {
                                    duplicate = true;
                                }
                            }
                            if (!duplicate) {
                                seen[seenCount] = id;
                                seenCount++;
                                count += sizes[id];
                            }
                        }
                    }
                    answer = Math.max(answer, count);
                }
            }
        }
        return answer;
    }

    private int bfs(int startRow, int startCol, int id,
                    int[][] board, int[][] group) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        group[startRow][startCol] = id;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            count++;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && board[nr][nc] == 1 && group[nr][nc] == 0) {
                    group[nr][nc] = id;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return count;
    }
}

