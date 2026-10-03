package dijkstra;/*N * M 크기의 미로가 있습니다. 미로는 빈 방(0)과 벽(1)으로 이루어져 있습니다.
당신은 현재 가장 왼쪽 위인 (0, 0)에 있고, 가장 오른쪽 아래인 (N-1, M-1)로 이동하려 합니다.
상, 하, 좌, 우 인접한 방으로 이동할 수 있지만, 만약 이동하려는 곳이 벽(1)이라면 벽을 부수고 지나가야 합니다. 
벽을 부수면 빈 방(0)으로 바뀌게 됩니다.
목적지 (N-1, M-1)까지 이동하는 동안 부수어야 하는 벽의 최소 개수를 구하는 solution 함수를 작성해 주세요.
제한사항
미로의 세로 크기 N과 가로 크기 M은 1 이상 100 이하의 자연수입니다.
미로의 시작점 (0, 0)과 도착점 (N-1, M-1)은 항상 뚫려 있는 빈 방(0)입니다.*/

import java.util.*;

public class Dijkstra_3 {

	public static void main(String[] args) {
		Solution10 sol = new Solution10();
    	
        int[][] map = {
            {0, 1, 1},
            {1, 1, 0},
            {1, 1, 0}
        };
        
        int result = sol.solution(map);
        System.out.println("부수어야 하는 벽의 최소 개수: " + result);

	}

}

class Solution10 {
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};

    public int solution(int[][] map) {
        int n = map.length;
        int m = map[0].length;

        int[][] dist = new int[n][m];
        for(int i=0; i<n; i++){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[2]-b[2]);
        pq.offer(new int[]{0,0,0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int broken = curr[2];

            if(r==n-1 && c==m-1){
                return broken;
            }
            if(dist[r][c]<broken){
                continue;
            }

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<m && broken+map[nr][nc]<dist[nr][nc]){
                    dist[nr][nc]=broken+map[nr][nc];
                    pq.offer(new int[]{nr, nc, broken+map[nr][nc]});


                }
            }
        }
        return 0;
    }
}