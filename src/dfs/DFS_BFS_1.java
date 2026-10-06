// 문제: 연구소
package dfs;/*철수의 연구소에서 바이러스가 유출되었습니다. 바이러스는 인접한 상하좌우 빈칸으로 모두 퍼져나갈 수 있습니다.
연구소는 크기가 세로 N, 가로 M인 직사각형 격자 모양이며, 1x1 크기의 정사각형으로 나누어져 있습니다.
격자의 각 칸은 0(빈칸), 1(벽), 2(바이러스) 중 하나로 이루어져 있습니다.
철수는 바이러스의 확산을 막기 위해 새로운 벽을 정확히 3개 세우려고 합니다. 
벽을 3개 세운 뒤, 바이러스가 퍼질 수 없는 곳을 '안전 영역'이라고 합니다.
연구소의 지도를 나타내는 2차원 배열 map이 매개변수로 주어질 때, 
얻을 수 있는 안전 영역의 최대 크기를 return 하도록 solution 함수를 완성해 주세요.*/

import java.util.*;

public class DFS_BFS_1 {
	public static void main(String[] args) {
      
        Solution5 sol = new Solution5();
        
        int[][] map = {
            {2, 0, 0, 0},
            {0, 0, 0, 1},
            {0, 1, 0, 0}
        };
        
        int result5 = sol.solution(map);
        System.out.println("바이러스 연구소 결과: " + result5);
    }
}

class Solution5 {
	int n, m;
    int maxSafeArea = 0;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] map) {
        maxSafeArea = 0;
        n = map.length;
        m = map[0].length;

        // 1단계: 벽을 세울 수 있는 빈 칸 조합을 찾기 위해 DFS 실행
        dfs(0, 0, map);

        return maxSafeArea;
    }

    // DFS로 빈 칸 중 3개를 골라 벽을 세우는 함수 (Combination)
    private void dfs(int depth, int start, int[][] map) {
        if (depth == 3) {
            // 벽 3개가 모두 세워졌다면 바이러스 확산 시뮬레이션 실행
            spreadVirus(map);
            return;
        }

        for (int i = start; i < n * m; i++) {
            int r = i / m;
            int c = i % m;

            if (map[r][c] == 0) {
                map[r][c] = 1; // 벽 세우기
                dfs(depth + 1, i + 1, map);
                map[r][c] = 0; // 백트래킹 (원상복구)
            }
        }
    }

    // BFS를 이용한 바이러스 확산 및 안전 영역 계산
    private void spreadVirus(int[][] map) {
        int[][] tempMap = new int[n][m];
        for (int i = 0; i < n; i++) {
            tempMap[i] = map[i].clone();
        }

        Queue<int[]> queue = new LinkedList<>();
        
        // 초기 바이러스 위치를 큐에 모두 삽입 (Multi-Source BFS)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempMap[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        // BFS 확산
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && tempMap[nr][nc] == 0) {
                    tempMap[nr][nc] = 2;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        // 안전 영역(0) 개수 카운트 및 최댓값 갱신
        int safeArea = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempMap[i][j] == 0) {
                    safeArea++;
                }
            }
        }

        maxSafeArea = Math.max(maxSafeArea, safeArea);
    }
}
