/*철수의 토마토 농장에서는 토마토를 보관하는 큰 창고를 가지고 있습니다. 
토마토는 격자 모양 상자의 칸에 하나씩 넣어서 창고에 보관합니다.
창고에 보관되는 토마토들 중에는 잘 익은 것도 있지만, 아직 익지 않은 토마토들도 있을 수 있습니다. 
보관 후 하루가 지나면, 익은 토마토들의 인접한 곳에 있는 익지 않은 토마토들은 익은 토마토의 영향을 받아 익게 됩니다. 
하나의 토마토의 인접한 곳은 왼쪽, 오른쪽, 앞, 뒤(상, 하, 좌, 우) 네 방향에 있는 토마토를 의미합니다. 
대각선 방향에 있는 토마토들에게는 영향을 주지 못합니다.
철수는 창고에 보관된 토마토들이 며칠이 지나면 다 익게 되는지, 그 최소 일수를 알고 싶어 합니다.
토마토 상자의 상태를 나타내는 2차원 배열 box가 주어질 때, 
모든 토마토가 익을 때까지 걸리는 최소 일수를 return 하도록 solution 함수를 작성해주세요.*/

package bfs;

import java.util.*;

public class BFS_2 {
    public static void main(String[] args) {
    	Solution4 sol = new Solution4();
    	
        int[][] box = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1}
        };
        
        int result4 = sol.solution(box);
        System.out.println("토마토 결과: " + result4);
    }
}

class Solution4 {
    public int solution(int[][] box) {
        int n = box.length;
        int m = box[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        int unripeCount = 0; // 안 익은 토마토 개수
        
        // 1. 창고를 스캔하며 초기 상태 세팅
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (box[i][j] == 1) {
                    // 익은 토마토는 모두 출발점이 되므로 큐에 넣음
                    queue.offer(new int[]{i, j});
                } else if (box[i][j] == 0) {
                    // 나중에 전부 익었는지 확인하기 위해 안 익은 개수 카운트
                    unripeCount++;
                }
            }
        }
        
        // 처음부터 다 익어있었다면 0 반환
        if (unripeCount == 0) return 0;
        
        // 상, 하, 좌, 우 이동 배열
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int days = 0; // 걸린 일수
        
        // 2. BFS 탐색 시작
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                
                // 범위를 벗어나지 않고, 안 익은 토마토(0)라면
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && box[nr][nc] == 0) {
                    // 토마토를 익게 만들고, 며칠 만에 익었는지 누적 값을 기록
                    box[nr][nc] = box[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                    unripeCount--; // 안 익은 토마토 1개 해결!
                    days = box[nr][nc] - 1; // 시작이 1이었으므로 1을 빼야 실제 일수가 됨
                }
            }
        }
        
        // 3. 탐색이 끝났는데도 안 익은 토마토가 남아있다면 -1 반환
        return unripeCount == 0 ? days : -1;
    }
}
