package bfs;/*철수의 토마토 농장에서는 토마토를 보관하는 격자 모양의 상자들을 위아래로 여러 개 쌓아 올린 3차원 창고를 가지고 있습니다.
토마토는 상자의 칸에 하나씩 들어있습니다.
보관 후 하루가 지나면, 잘 익은 토마토들의 인접한 곳(위, 아래, 왼쪽, 오른쪽, 앞, 뒤 총 6방향)에 있는 익지 않은 토마토들은 익은 토마토의 영향을 받아 익게 됩니다.
창고에 보관된 토마토들이 며칠이 지나면 다 익게 되는지, 그 최소 일수를 알고 싶어 합니다.
3차원 창고의 상태를 나타내는 3차원 배열(또는 2차원 리스트의 모음) 형태의 입력이 주어질 때, 
모든 토마토가 익을 때까지 걸리는 최소 일수를 return 하도록 solution 함수를 작성해 주세요.
제한사항
상자의 가로, 세로, 높이 크기는 각각 2 이상 100 이하입니다.
정수 1은 익은 토마토, 정수 0은 아직 익지 않은 토마토, 정수 -1은 토마토가 들어있지 않은 빈칸을 나타냅니다.
만약 처음부터 모든 토마토가 익어있는 상태라면 0을 return 하고, 토마토가 모두 익지는 못하는 상황이면 -1을 return 합니다.*/
import java.util.*;

public class BFS_6 {
	public static void main(String[] args) {
		Solution14 sol = new Solution14();
    	
        int[][][] box = {
			        		{{0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 1}}
        			   };
        
        int result = sol.solution(box);
        System.out.println("모든 토마토가 익을 때까지 걸리는 최소 일수: " + result);

	}
}

class Solution14 {
    public int solution(int[][][] box) {
        int hSize = box.length;
        int nSize = box[0].length;
        int mSize = box[0][0].length;

        Queue<int[]> queue = new LinkedList<>();
        int unripeCount = 0;

        // 1. 3차원 창고 스캔
        for (int h = 0; h < hSize; h++) {
            for (int r = 0; r < nSize; r++) {
                for (int c = 0; c < mSize; c++) {
                    if (box[h][r][c] == 1) {
                        queue.offer(new int[]{h, r, c, 0});
                    } else if (box[h][r][c] == 0) {
                        unripeCount++;
                    }
                }
            }
        }

        if (unripeCount == 0) return 0;

        // 6방향 이동 배열 (위, 아래, 상, 하, 좌, 우)
        int[] dh = {-1, 1, 0, 0, 0, 0};
        int[] dr = {0, 0, -1, 1, 0, 0};
        int[] dc = {0, 0, 0, 0, -1, 1};
        int days = 0;

        // 2. BFS 탐색
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int h = curr[0];
            int r = curr[1];
            int c = curr[2];
            int day = curr[3];
            
            days = Math.max(days, day);
            
            for (int i = 0; i < 6; i++) {
                int nh = h + dh[i];
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nh >= 0 && nh < hSize && nr >= 0 && nr < nSize && nc >= 0 && nc < mSize) {
                    if (box[nh][nr][nc] == 0) {
                        box[nh][nr][nc] = 1;
                        queue.offer(new int[]{nh, nr, nc, day+1});
                        unripeCount--;
                    }
                }
            }
        }

        return unripeCount == 0 ? days : -1;
    }
}