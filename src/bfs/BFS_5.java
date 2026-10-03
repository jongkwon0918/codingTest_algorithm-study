/*지구 온난화로 인하여 북극의 빙산이 녹고 있습니다. 이차원 배열 모양의 빙산 정보가 주어집니다.
배열의 각 칸에 들어 있는 값은 융해되지 않은 빙산의 높이입니다. 0은 바닷물이 차 있는 칸을 의미합니다.
빙산의 높이는 바닷물(0)에 접해 있는 칸의 개수만큼 매년 줄어듭니다. 
각 칸에 바닷물이 접해 있는 개수는 상, 하, 좌, 우 네 방향에 0이 있는 개수입니다. 
단, 빙산이 줄어드는 과정은 동시에 일어납니다. 
(한 칸이 녹아서 0이 되었다고 해서, 그 해에 인접한 다른 빙산에 즉시 영향을 주지 않습니다.)
빙산이 두 개 이상의 덩어리로 분리되는 최초의 년(year)을 구하는 함수를 작성해 주세요. 
만약 빙산이 전부 다 녹을 때까지 두 덩어리 이상으로 분리되지 않으면 0을 반환합니다.
제한사항
이차원 배열의 세로와 가로 크기는 각각 3 이상 300 이하입니다.
배열의 각 원소는 0 이상 10 이하의 정수입니다.
처음에 빙산이 들어있는 칸의 개수는 1개 이상 10,000개 이하이며, 배열의 가장 바깥쪽 행과 열에는 모두 0이 들어있습니다.*/

package bfs;
import java.util.*;
public class BFS_5 {

	public static void main(String[] args) {
		Solution13 sol = new Solution13();
    	
        int[][] iceberg = {
			        		{0, 0, 0, 0, 0, 0, 0},
			        		{0, 2, 4, 5, 3, 0, 0},
			        		{0, 3, 0, 2, 5, 2, 0},
			        		{0, 7, 6, 2, 4, 0, 0},
			        		{0, 0, 0, 0, 0, 0, 0}
        			   };
        
        int result = sol.solution(iceberg);
        System.out.println("두 개 이상의 덩어리로 분리되는 최초의 년: " + result);

	}

}

class Solution13 {
    int n, m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] iceberg) {
        n = iceberg.length;
        m = iceberg[0].length;
        int years = 0;

        while (true) {
            // 1. 현재 상태에서 빙산 덩어리 개수 확인
            int chunkCount = countChunks(iceberg);
            
            // 덩어리가 2개 이상이면 걸린 년수 반환
            if (chunkCount >= 2) {
                return years;
            }
            // 빙산이 다 녹았는데도 2개 이상으로 안 쪼개졌다면 0 반환
            if (chunkCount == 0) {
                return 0;
            }

            // 2. 빙산 녹이기 시뮬레이션 실행
            iceberg = meltIce(iceberg);
            years++;
        }
    }

    // 빙산 덩어리 개수를 세는 함수 (BFS)
    private int countChunks(int[][] map) {
        boolean[][] visited = new boolean[n][m];
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] > 0 && !visited[i][j]) {
                    bfs(i, j, map, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private void bfs(int startR, int startC, int[][] map, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        visited[startR][startC] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    if (map[nr][nc] > 0 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }

    // 빙산이 녹는 과정을 동시에 처리하는 함수
    private int[][] meltIce(int[][] map) {
        int[][] tempMap = new int[n][m];
        for (int i = 0; i < n; i++) {
            tempMap[i] = map[i].clone();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] > 0) {
                    int seaCount = 0; // 주변 바닷물(0) 개수
                    
                    for (int d = 0; d < 4; d++) {
                        int nr = i + dr[d];
                        int nc = j + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                            if (map[nr][nc] == 0) {
                                seaCount++;
                            }
                        }
                    }

                    // 주변 바닷물 개수만큼 높이 줄이기 (최소 0 이하로는 내려가지 않음)
                    tempMap[i][j] = Math.max(0, map[i][j] - seaCount);
                }
            }
        }
        return tempMap;
    }
}