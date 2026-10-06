// 문제: 적록색약
/*적록색약은 빨간색과 초록색의 차이를 거의 느끼지 못합니다. 따라서, 
적록색약인 사람이 보는 그림은 적록색약이 아닌 사람이 보는 그림과는 다를 수 있습니다.
크기가 N * N인 그리드가 매개변수 picture로 주어집니다. 
그림은 'R'(빨강), 'G'(초록), 'B'(파랑) 중 하나의 색상이 칸마다 적혀 있습니다.
상하좌우로 인접한 칸에 적힌 색상이 같으면 같은 구역으로 인정합니다.
이때, 1) 적록색약이 아닌 사람이 보는 구역의 개수와 2) 적록색약인 사람이 보는 구역의 개수를 구해서 
배열 [정상인 구역 수, 적록색약 구역 수] 형태로 반환하는 solution 함수를 작성해 주세요.
(적록색약인 사람은 'R'과 'G'를 같은 색상으로 취급하여 하나의 구역으로 묶습니다.)
제한사항
그리드의 크기 N은 1 이상 100 이하의 자연수입니다.
picture의 각 원소는 길이 N의 문자열이며, 'R', 'G', 'B'로만 이루어져 있습니다.*/

package bfs;

import java.util.*;

public class BFS_4 {
	public static void main(String[] args) {
		Solution11 sol = new Solution11();
    	
        String[] picture = {"RRRBB", "GGBBB", "BBBRR", "BBRRR", "RRRRR"};
        
        int[] result = sol.solution(picture);
        System.out.println("정상인 구역 수, 적록색약 구역 수: " + result[0] + ", "+ result[1]);

	}
}

class Solution11 {
    int n;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int[] solution(String[] picture) {
        n = picture.length;
        char[][] map = new char[n][n];
        char[][] blindMap = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char c = picture[i].charAt(j);
                map[i][j] = c;
                // 색약인 버전은 G를 R로 치환하여 미리 준비
                blindMap[i][j] = (c == 'G') ? 'R' : c;
            }
        }

        // 1. 정상인 구역 수 구하기
        int normalCount = countRegions(map);
        
        // 2. 적록색약 구역 수 구하기
        int blindCount = countRegions(blindMap);

        return new int[]{normalCount, blindCount};
    }

    // 공통 BFS 탐색 메서드 (전달받은 지도를 기준으로 덩어리 개수 반환)
    private int countRegions(char[][] targetMap) {
        boolean[][] visited = new boolean[n][n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j]) {
                    bfs(i, j, targetMap, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private void bfs(int startR, int startC, char[][] targetMap, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        visited[startR][startC] = true;
        char color = targetMap[startR][startC];

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    // 방문하지 않았고, 현재 색상과 동일한 글자라면 계속 확장
                    if (!visited[nr][nc] && targetMap[nr][nc] == color) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }
}

