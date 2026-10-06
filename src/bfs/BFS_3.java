// 문제: 미로 탐색
//N * M 크기의 직사각형 미로에 갇혀 있습니다.
//미로에는 여러 칸이 존재하며, 각 칸은 이동할 수 있는 칸(1) 또는 벽으로 막혀 이동할 수 없는 칸(0)입니다.
//당신은 현재 (0, 0) 위치에 있고, 미로의 출구는 (N-1, M-1) 위치에 있습니다.
//한 번에 상, 하, 좌, 우로 인접한 한 칸씩 이동할 수 있습니다.
//미로를 탈출하기 위해 지나야 하는 최소 칸의 개수를 구하는 solution 함수를 작성해 주세요. 
//(시작 칸과 마지막 칸도 카운트에 포함합니다.)
//제한사항
//N, M은 각각 2 이상 100 이하의 자연수입니다.
//시작 지점 (0, 0)과 도착 지점 (N-1, M-1)은 항상 이동할 수 있는 칸(1)입니다.
//항상 탈출할 수 있는 경로가 존재합니다.

package bfs;

import java.util.*;

public class BFS_3 {
	 public static void main(String[] args) {
	    	Solution6 sol = new Solution6();
	    	
	        int[][] maps = {
	            {1, 0, 1, 1, 1},
	            {1, 1, 0, 0, 1},
	            {0, 1, 0, 1, 1},
	            {1, 1, 1, 0, 1},
	            {0, 0, 1, 1, 1}
	        };
	        
	        int result = sol.solution(maps);
	        System.out.println("미로 탐색 결과: " + result);
	    }
}



class Solution6 {
    public int solution(int[][] maps) {
        int n = maps.length;
        int m = maps[0].length;

        // 상, 하, 좌, 우 이동을 위한 방향 배열
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // 좌표 [r, c]를 담을 큐 생성 (클래스 대신 int[] 객체 활용)
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        boolean[][] visited = new boolean[n][m];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            // 목적지에 도달하면 해당 칸에 기록된 최단 거리 반환
            if (r == n - 1 && c == m - 1) {
                return maps[r][c];
            }

            // 4방향 탐색
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // 맵 범위를 벗어나지 않고, 이동 가능한 칸(1)인 경우
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && maps[nr][nc] == 1 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    // 다음 칸에 (현재 거리에 + 1)한 값을 적어 누적 거리 기록 및 방문 처리
                    maps[nr][nc] = maps[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return -1; // 도달할 수 없는 경우 (문제 제한사항 상 도달 가능)
    }
}
