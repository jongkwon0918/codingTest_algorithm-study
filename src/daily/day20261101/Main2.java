package daily.day20261101;

import java.util.*;

/*
2. 가장 안전한 빈칸의 거리

직사각형 바다에서0은 빈칸, 1은 아기 상어가 있는 칸이다.
어떤 빈칸의 안전 거리는 그 칸에서 가장 가까운 상어까지의 최단 이동 거리이다.
상하좌우와 대각선을 포함한8방향으로 한 칸씩 이동할 수 있고 한 번의 이동 거리는1이다.
모든 빈칸의 안전 거리 중 가장 큰 값을 반환하라.
상어 하나를 정해 가장 멀리 떨어지는 문제가 아니라, 각 빈칸에서 가장 가까운 상어를 기준으로 한다.

제한사항
- 2 <= sea.length <= 50
- 2 <= sea[0].length <= 50, 모든 행의 길이는 같다.
- 각 값은0 또는1이다.
- 빈칸과 상어는 각각 최소 하나 존재한다.

입출력 예시
sea={{1,0,0},{0,0,0},{0,0,0}} -> 2
(2,2)에서 대각선으로 두 번 이동하면(0,0)에 도착한다.
sea={
{1,0,0},
{0,0,0},
{0,0,1}} -> 2
가운데의 안전 거리는1이지만(0,2)와(2,0)의 안전 거리는2다.
sea={{1,0},{0,1}} -> 1
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new int[][]{
            {1,0,0},{0,0,0},{0,0,0}
        })); // 2
        System.out.println(solution.solution(new int[][]{
            {1,0,0},{0,0,0},{0,0,1}
        })); // 2
        System.out.println(solution.solution(new int[][]{
            {1,0},{0,1}
        })); // 1
    }
}

class Solution2 {
    int n;
    int m;
    int[] dr = {-1,1,0,0,-1,1,-1,1};
    int[] dc = {0,0,-1,1,-1,1,1,-1};
    public int solution(int[][] sea) {
        n = sea.length;
        m = sea[0].length;
        for(int i=0; i<n; i++) {
            for (int j = 0; j < m; j++) {
                if(sea[i][j]==1){
                    sea[i][j]=-1;
                }else{
                    sea[i][j]=n*m;
                }
            }
        }
        for(int i=0; i<n; i++) {
            for (int j = 0; j < m; j++) {
                if (sea[i][j] == -1) {
                    sea = dfs(i,j,sea);
                }
            }
        }
        int safeZone = 0;
        for(int i=0; i<n; i++) {
            for (int j = 0; j < m; j++) {
                if(sea[i][j]!=-1){
                    safeZone = Math.max(safeZone, sea[i][j]);
                }
            }
        }

        return safeZone;
    }

    private int[][] dfs(int i, int j, int[][] sea) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i,j,0});
        boolean[][] visited = new boolean[n][m];
        visited[i][j]=true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];

            for(int d=0; d<8; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc] && sea[nr][nc]>cost) {
                    sea[nr][nc] = cost+1;
                    queue.offer(new int[]{nr, nc, cost+1});
                    visited[nr][nc] = true;
                }
            }
        }

        return sea;
    }
}

