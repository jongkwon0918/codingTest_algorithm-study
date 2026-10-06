// 문제: 벽 부수고 이동하기
package bfs;/*N * M 행렬로 표현되는 미로가 있습니다.
미로에서 0은 이동할 수 있는 칸을 나타내고, 1은 이동할 수 없는 벽이 있는 칸을 나타냅니다.
당신은 (0, 0)에서 출발하여 (N-1, M-1)의 위치까지 이동하려 합니다. 
이때 상, 하, 좌, 우로 인접한 칸으로 이동할 수 있습니다.
만약 이동하려는 칸이 벽(1)이고, 아직 벽을 단 한 번도 부순 적이 없다면, 
그 벽을 부수고 이동하는 것이 가능합니다. 시작점에서 목적지까지 이동할 때 지나야 하는 최소 칸의 개수를 구하는 solution 함수를 작성해 주세요.
(시작 칸과 도착 칸을 모두 포함하여 카운트합니다. 만약 경로가 없다면 -1을 반환합니다.)
제한사항
미로의 세로 크기 N과 가로 크기 M은 각각 1 이상 1,000 이하의 자연수입니다.
시작점 (0, 0)과 도착점 (N-1, M-1)은 항상 0(빈 방)입니다.*/

import java.util.*;
public class BFS_8 {

	public static void main(String[] args) {
		Solution16 sol = new Solution16();
		int[][] map = {
							{0, 1, 0, 0}, 
							{0, 1, 0, 1}, 
							{0, 0, 0, 1}, 
							{1, 1, 0, 0}
					  };

		int result = sol.solution(map);
		System.out.println("목적지까지 이동할 때 지나야 하는 최소 칸의 개수: " + result);

	}

}

class Solution16 {
    public int solution(int[][] map) {
        int n = map.length;
        int m = map[0].length;
        
        // 목적지가 시작점과 같은 경우 예외 처리
        if (n == 1 && m == 1) return 1;
        
        // 3차원 방문 및 거리 배열 [행][열][벽 부수기 여부(0 또는 1)]
        int[][][] dist = new int[n][m][2];
        
        Queue<int[]> queue = new LinkedList<>();
        // [r, c, broken(0: 안 부섬, 1: 부수고 옴)]
        queue.offer(new int[]{0, 0, 0});
        dist[0][0][0] = 1; // 시작 칸 포함해서 거리 1로 시작
        
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int broken = curr[2];
            
            // 목적지 도달 시 최단 거리 반환
            if (r == n - 1 && c == m - 1) {
                return dist[r][c][broken];
            }
            
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    // 1. 다음 칸이 빈 방(0)이고, 아직 해당 상태로 방문하지 않은 경우
                    if (map[nr][nc] == 0 && dist[nr][nc][broken] == 0) {
                        dist[nr][nc][broken] = dist[r][c][broken] + 1;
                        queue.offer(new int[]{nr, nc, broken});
                    }
                    
                    // 2. 다음 칸이 벽(1)이고, 내가 아직 벽을 부순 적이 없다면(broken == 0)
                    if (map[nr][nc] == 1 && broken == 0 && dist[nr][nc][1] == 0) {
                        dist[nr][nc][1] = dist[r][c][0] + 1;
                        queue.offer(new int[]{nr, nc, 1}); // 부순 상태(1)로 변경하여 큐에 삽입
                    }
                }
            }
        }
        
        return -1; // 도달할 수 없는 경우
    }
}
