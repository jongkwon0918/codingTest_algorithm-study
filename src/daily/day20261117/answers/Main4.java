package daily.day20261117.answers;

import java.util.*;

/*
4. 두 돌로 상대 무리 둘러싸기

직사각형 바둑판에서0은 빈칸,1은 내 돌,2는 상대 돌이다.
서로 다른 빈칸 두 곳에 내 돌을 하나씩 놓는다. 상대방은 그 사이 새 돌을 놓지 않는다.
상대 돌이 상하좌우로 연결되어 있으면 하나의 무리다.
무리에 속한 어떤 돌에도 상하좌우로 인접한 빈칸이 하나도 없으면 그 무리 전체를 잡는다.
대각선 빈칸은 무리를 살리지 못한다. 바둑판 밖도 빈칸으로 보지 않는다.
두 돌을 모두 놓은 최종 판에서 잡히는 상대 돌 수의 최댓값을 반환하라.
잡은 무리의 수가 아니라 무리에 포함된 돌 수를 센다.
이 문제에서는 일반 바둑의 착수 금지나 패 같은 추가 규칙을 적용하지 않는다.

제한사항
- 행과 열은 각각3 이상20 이하이다.
- board의 원소는0,1,2이고 빈칸은 최소 두 개다.
- 초기 판에서는 양쪽 모두 빈틈없이 포위되어 이미 잡힌 무리가 없다.

입출력 예시
{{2,0,0,0},{0,0,0,0},{0,0,0,2}} -> 1
모서리 상대 돌 하나의 두 이웃을 막으면 잡을 수 있다.
{{2,2,2},{2,2,2},{0,2,0}} -> 7
두 빈칸을 모두 막으면 상대 무리 전체7개를 잡는다.
3*3 빈 판 -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new int[][]{
            {2,0,0,0},{0,0,0,0},{0,0,0,2}
        })); // 1
        System.out.println(solution.solution(new int[][]{
            {2,2,2},{2,2,2},{0,2,0}
        })); // 7
        System.out.println(solution.solution(new int[3][3])); // 0
    }
}

class Solution4 {
    int n;
    int m;
    int answer;
    int[][] map;
    List<int[]> blanks;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        n = board.length;
        m = board[0].length;
        answer = 0;
        map = new int[n][m];
        blanks = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            map[r] = board[r].clone();
            for (int c = 0; c < m; c++) {
                if (map[r][c] == 0) {
                    blanks.add(new int[]{r, c});
                }
            }
        }
        dfs(0, 0);
        return answer;
    }

    private void dfs(int start, int count) {
        if (count == 2) {
            answer = Math.max(answer, countCaptured());
            return;
        }
        for (int i = start; i < blanks.size(); i++) {
            int r = blanks.get(i)[0];
            int c = blanks.get(i)[1];
            map[r][c] = 1;
            dfs(i + 1, count + 1);
            map[r][c] = 0;
        }
    }

    private int countCaptured() {
        boolean[][] visited = new boolean[n][m];
        int total = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] == 2 && !visited[r][c]) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{r, c});
                    visited[r][c] = true;
                    int size = 0;
                    boolean hasBlank = false;
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        size++;
                        for (int d = 0; d < 4; d++) {
                            int nr = current[0] + dr[d];
                            int nc = current[1] + dc[d];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                                if (map[nr][nc] == 0) {
                                    hasBlank = true;
                                } else if (map[nr][nc] == 2 && !visited[nr][nc]) {
                                    visited[nr][nc] = true;
                                    queue.offer(new int[]{nr, nc});
                                }
                            }
                        }
                    }
                    if (!hasBlank) {
                        total += size;
                    }
                }
            }
        }
        return total;
    }
}

