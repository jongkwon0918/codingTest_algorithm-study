package daily.day20261020.answers;

import java.util.*;

/*
5. 길을 건너지 않고 만날 수 있을까

n*n 목초지에서 소들은 상하좌우 인접 칸으로 자유롭게 이동할 수 있다.
다만 roads[i]={r1,c1,r2,c2}로 주어진 인접한 두 칸 사이에는 길이 놓여 있다.
그 사이를 직접 이동하려면 길을 건너야 한다. 길은 양방향으로 적용된다.
다른 경로로 돌아서 길을 한 번도 건너지 않고 이동할 수 있다면 만나도 된다.
cows[i]={행,열}은 소 한 마리의 위치이며 모든 소의 위치는 서로 다르다.
길을 하나 이상 건너지 않고서는 만날 수 없는 소의 쌍 개수를 반환하라.
(A,B)와 (B,A)는 같은 쌍이며 자기 자신은 세지 않는다.

제한사항
- 2 <= n <= 100
- 1 <= cows.length <= 100, cows.length <= n*n
- roads의 각 두 칸은 상하좌우로 인접한다.
- roads와 cows의 좌표는 모두 1 이상 n 이하이다.
- 길이 없는 두 인접 칸 사이에는 자유롭게 이동할 수 있다.

입출력 예시
n=3, roads={{2,2,2,3},{3,3,3,2},{3,3,2,3}},
cows={{3,3},{2,2},{2,3}} -> 2
(3,3)의 소가 다른 두 소와 길 없이 만나지 못한다.
n=2, roads={{1,1,1,2}}, cows={{1,1},{1,2}} -> 0
아래쪽 두 칸을 거쳐 돌아갈 수 있다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] roads = {{2, 2, 2, 3}, {3, 3, 3, 2}, {3, 3, 2, 3}};
        int[][] cows = {{3, 3}, {2, 2}, {2, 3}};
        System.out.println(solution.solution(3, roads, cows)); // 2
        System.out.println(solution.solution(
                2, new int[][]{{1, 1, 1, 2}}, new int[][]{{1, 1}, {1, 2}})); // 0
    }
}

class Solution5 {
    int n;
    boolean[][][] blocked;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int n, int[][] roads, int[][] cows) {
        this.n = n;
        blocked = new boolean[n][n][4];
        for (int[] road : roads) {
            int r1 = road[0] - 1;
            int c1 = road[1] - 1;
            int r2 = road[2] - 1;
            int c2 = road[3] - 1;
            for (int d = 0; d < 4; d++) {
                if (r1 + dr[d] == r2 && c1 + dc[d] == c2) {
                    blocked[r1][c1][d] = true;
                }
                if (r2 + dr[d] == r1 && c2 + dc[d] == c1) {
                    blocked[r2][c2][d] = true;
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < cows.length; i++) {
            boolean[][] visited = bfs(cows[i][0] - 1, cows[i][1] - 1);
            for (int j = i + 1; j < cows.length; j++) {
                if (!visited[cows[j][0] - 1][cows[j][1] - 1]) {
                    answer++;
                }
            }
        }
        return answer;
    }

    private boolean[][] bfs(int startRow, int startCol) {
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && !blocked[row][col][d] && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return visited;
    }
}

