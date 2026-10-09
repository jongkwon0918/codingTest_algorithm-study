// 문제: 안전 영역
package dfs;/*장마철에는 물에 잠기지 않는 안전한 영역을 찾는 것이 중요합니다.
어떤 지역의 높이 정보를 파악하여, 일정 높이 이하의 모든 지역은 물에 잠긴다고 할 때, 
물에 잠기지 않는 안전한 영역의 최대 개수를 구하려 합니다.
안전 영역이란 물에 잠기지 않은 지역(높이가 특정 높이보다 큰 지역)들이 
상, 하, 좌, 우로 인접해 있으며 그 덩어리가 이어진 공간을 의미합니다.
지역의 높이를 담은 2차원 정수 배열 area가 매개변수로 주어질 때, 비가 와서 물에 잠기는 높이가 다를 때 
각 강수 높이에서 계산한 안전 영역 개수 중 최댓값을 return 하도록 solution 함수를 작성해 주세요.
제한사항
지역을 나타내는 2차원 배열 area의 행과 열의 길이는 각각 2 이상 100 이하입니다.
배열 안의 각 원소(지역의 높이)는 1 이상 100 이하의 정수입니다.
아무 지역도 물에 잠기지 않을 수도 있습니다.*/
import java.util.*;

public class DFS_3 {

	public static void main(String[] args) {
		Solution9 sol = new Solution9();
    	
        int[][] area = {
            {6, 8, 2, 6, 2},
            {3, 2, 3, 4, 6},
            {6, 7, 3, 3, 2},
            {7, 2, 5, 3, 6},
            {8, 9, 5, 2, 7}
        };
        
        int result = sol.solution(area);
        System.out.println("안전한 영역: " + result);

	}

}

class Solution9 {
    int n;
    int[][] map;
    boolean[][] visited;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] area) {
        n = area.length;
        map = area;
        int maxHeight = 0;
        
        // 1. 지도에서 가장 높은 건물의 높이 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                maxHeight = Math.max(maxHeight, map[i][j]);
            }
        }
        
        int maxSafeAreas = 1; // 아무 지역도 안 잠기는 경우를 고려해 최소 1로 초기화
        
        // 2. 비가 오는 높이(h)를 1부터 최고 높이 전까지 완전탐색
        for (int h = 1; h < maxHeight; h++) {
            visited = new boolean[n][n];
            int count = 0;
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // 높이가 h보다 크고 아직 방문하지 않은 안전한 땅이라면 BFS 시작
                    if (map[i][j] > h && !visited[i][j]) {
                        bfs(i, j, h);
                        count++;
                    }
                }
            }
            maxSafeAreas = Math.max(maxSafeAreas, count);
        }
        
        return maxSafeAreas;
    }
    
    // BFS를 통해 인접한 안전 영역을 모두 방문 처리하는 함수
    private void bfs(int r, int c, int h) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        visited[r][c] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cr = curr[0];
            int cc = curr[1];
            
            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && map[nr][nc] > h && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }
}
