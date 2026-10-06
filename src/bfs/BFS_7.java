// 문제: 불!
package bfs;/*지훈이는 미로 속에 갇혀있습니다.
미로의 각 칸은 지나갈 수 있는 공간(.), 벽(#), 지훈이의 초기 위치(J), 불의 초기 위치(F)로 이루어져 있습니다.

지훈이는 매 초마다 상하좌우로 인접한 칸으로 이동할 수 있습니다. 
불 역시 매 초마다 상하좌우로 인접한 빈 공간으로 퍼져나갑니다. 
불은 벽(( # ))에는 붙지 않습니다.
지훈이는 미로의 가장자리에 도달하면 미로를 탈출할 수 있습니다. 
불이 도달하기 전에 미로를 탈출할 수 있는 가장 빠른 시간(초)을 구하는 solution 함수를 작성해 주세요.
만약 탈출할 수 없다면 -1을 반환합니다.

제한사항
미로의 행(세로)과 열(가로) 크기는 각각 1 이상 1,000 이하입니다.
미로에 J는 반드시 하나 주어지며, F는 없을 수도 있거나 여러 개 주어질 수 있습니다.
시작하자마자 가장자리에 있는 경우는 1초 만에 탈출할 수 있습니다.*/

import java.util.*;
public class BFS_7 {

	public static void main(String[] args) {
		Solution15 sol = new Solution15();
		String[] maps = {
			    "#####",
			    "#J..F",
			    "#.###",
			    "#....",
			    "#####"
			};

		int result = sol.solution(maps);
		System.out.println("탈출할 수 있는 가장 빠른 시간(초): " + result);

	}

}

class Solution15{
    public int solution(String[] maps) {
        int n = maps.length;
        int m = maps[0].length();
        
        char[][] map = new char[n][m];
        int[][] fireTime = new int[n][m];
        int[][] jihoonTime = new int[n][m];
        
        Queue<int[]> fireQueue = new LinkedList<>();
        Queue<int[]> jihoonQueue = new LinkedList<>();
        
        // 초기화 및 위치 파악
        for (int i = 0; i < n; i++) {
            Arrays.fill(fireTime[i], -1);
            Arrays.fill(jihoonTime[i], -1);
            for (int j = 0; j < m; j++) {
                map[i][j] = maps[i].charAt(j);
                if (map[i][j] == 'F') {
                    fireQueue.offer(new int[]{i, j});
                    fireTime[i][j] = 0;
                } else if (map[i][j] == 'J') {
                    jihoonQueue.offer(new int[]{i, j});
                    jihoonTime[i][j] = 0;
                }
            }
        }
        
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        // 1. 불(F)의 확산 시간 미리 계산 (BFS)
        while (!fireQueue.isEmpty()) {
            int[] curr = fireQueue.poll();
            int r = curr[0];
            int c = curr[1];
            
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    if (map[nr][nc] != '#' && fireTime[nr][nc] == -1) {
                        fireTime[nr][nc] = fireTime[r][c] + 1;
                        fireQueue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        
        // 2. 지훈이(J)의 탈출 경로 탐색 (BFS)
        while (!jihoonQueue.isEmpty()) {
            int[] curr = jihoonQueue.poll();
            int r = curr[0];
            int c = curr[1];
            
            // 미로 가장자리에 도달했다면 탈출 성공! (소요 시간 반환)
            if (r == 0 || r == n - 1 || c == 0 || c == m - 1) {
                return jihoonTime[r][c] + 1;
            }
            
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    // 벽이 아니고 아직 방문하지 않은 곳
                    if (map[nr][nc] != '#' && jihoonTime[nr][nc] == -1) {
                        int nextTime = jihoonTime[r][c] + 1;
                        
                        // 불이 아예 안 오거나(fireTime == -1), 불보다 먼저 도착할 수 있는 경우에만 이동
                        if (fireTime[nr][nc] == -1 || nextTime < fireTime[nr][nc]) {
                            jihoonTime[nr][nc] = nextTime;
                            jihoonQueue.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
        
        return -1; // 탈출 불가능한 경우
    }
}