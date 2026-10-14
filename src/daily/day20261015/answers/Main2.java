package daily.day20261015.answers;

import java.util.*;

/*
2. 목표까지의 거리 지도

직사각형 지도에서 0은 갈 수 없는 땅, 1은 갈 수 있는 땅, 2는 목표 지점이다.
상하좌우로 한 칸씩 이동할 수 있다.
각 칸에서 목표 지점까지의 최단거리를 같은 크기의 배열로 반환하라.
원래 0인 칸은 결과도 0, 목표 지점의 결과도 0이다.
원래 1이지만 목표에 도달할 수 없는 칸은 -1이다.

제한사항
- 행과 열은 각각 2 이상 1,000 이하이다.
- 모든 행의 길이는 같으며 값은 0,1,2 중 하나이다.
- 목표 지점 2는 정확히 하나이다.

입출력 예시
map={{2,1,0},{1,1,0},{0,0,1}}
-> {{0,1,0},{1,2,0},{0,0,-1}}
map={{1,2},{1,1}} -> {{1,0},{2,1}}
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] map = {{2, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        System.out.println(Arrays.deepToString(solution.solution(map)));
        // [[0, 1, 0], [1, 2, 0], [0, 0, -1]]
        System.out.println(Arrays.deepToString(solution.solution(
                new int[][]{{1, 2}, {1, 1}}))); // [[1, 0], [2, 1]]
    }
}

class Solution2 {
    public int[][] solution(int[][] map) {
        int n = map.length;
        int m = map[0].length;
        int[][] dist = new int[n][m];
        Queue<int[]> queue = new LinkedList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map[row][col] == 1) {
                    dist[row][col] = -1;
                } else if (map[row][col] == 2) {
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr][nc] != 0 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }
}

