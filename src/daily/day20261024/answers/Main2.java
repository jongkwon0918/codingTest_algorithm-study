package daily.day20261024.answers;

import java.util.*;

/*
2. 가장 멀리 떨어진 두 육지

직사각형 지도에서 'L'은 육지, 'W'는 바다이다.
상하좌우로 인접한 육지로만 이동할 수 있고 한 칸 이동에 1시간이 걸린다.
육지 두 곳에 보물이 있으며, 두 보물 사이의 최단 이동 시간이 가장 길어지는 곳에 묻혀 있다.
서로 육지로 연결되어 이동할 수 있는 두 칸을 골랐을 때,
두 칸 사이 최단거리들 중 가장 큰 값을 반환하라.
일부러 돌아가는 경로의 길이를 최대화하는 문제가 아니다.

제한사항
- 행과 열의 길이는 각각 1 이상 50 이하이다.
- 모든 행의 길이는 같고 문자는 L 또는 W이다.
- 서로 이동 가능한 육지 두 곳이 있는 지도를 대상으로 한다.

입출력 예시
island={"LLL","LWL","LLL"} -> 4
육지 고리의 반대편 두 칸 사이 최단거리는 4이다. 한 바퀴 도는 길이8을 반환하지 않는다.
island={"LLLLL"} -> 4
island={"LLWLL"} -> 1
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new String[]{"LLL","LWL","LLL"})); // 4
        System.out.println(solution.solution(new String[]{"LLLLL"})); // 4
        System.out.println(solution.solution(new String[]{"LLWLL"})); // 1
    }
}

class Solution2 {
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] island) {
        n = island.length;
        m = island[0].length();
        int answer = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (island[row].charAt(col) == 'L') {
                    answer = Math.max(answer, bfs(row, col, island));
                }
            }
        }
        return answer;
    }

    private int bfs(int startRow, int startCol, String[] island) {
        int[][] dist = new int[n][m];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        dist[startRow][startCol] = 0;
        int farthest = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            farthest = Math.max(farthest, dist[row][col]);

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && island[nr].charAt(nc) == 'L' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return farthest;
    }
}

