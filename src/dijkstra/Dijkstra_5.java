package dijkstra;/*[The Legend of Zelda] 게임의 주인공 링크는 지하동굴을 탈출해야 합니다.
지하동굴은 N * N 크기의 2차원 격자로 나타낼 수 있으며, 
각 칸에는 도둑루피가 있어서 그 칸을 지나갈 때 해당 칸에 적힌 금액만큼 루피를 잃게 됩니다.
링크는 현재 가장 왼쪽 위인 (0, 0)에 있고, 동굴의 출구는 가장 오른쪽 아래인 (N-1, N-1)에 있습니다. 
링크는 상, 하, 좌, 우 인접한 칸으로만 이동할 수 있습니다.
(0, 0)에서 출발하여 (N-1, N-1)까지 이동하는 동안 잃게 되는 루피의 최소 금액을 구하는 solution 함수를 작성해 주세요. 
(시작 칸과 도착 칸의 루피도 포함하여 합산합니다.)
제한사항
동굴의 크기 N은 2 이상 125 이하의 자연수입니다.
격자의 각 칸에 들어있는 루피 금액은 0 이상 9 이하의 정수입니다.*/
import java.util.*;
public class Dijkstra_5 {

	public static void main(String[] args) {
		Solution17 sol = new Solution17();
		int[][] map = {
							{5, 5, 4}, 
							{3, 9, 1}, 
							{3, 2, 7}
					  };

		int result = sol.solution(map);
		System.out.println("루피의 최소 금액: " + result);

	}

}

class Solution17 {
    public int solution(int[][] cave) {
        int n = cave.length;
        int[][] dist = new int[n][n];
        for(int i=0; i<n; i++){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        dist[0][0] = cave[0][0];

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2]-b[2]);
        pq.offer(new int[]{0,0,cave[0][0]});

        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,-1,1};
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int currCost = curr[2];

            if(r==n-1 && c==n-1){
                return currCost;
            }

            if(dist[r][c]<currCost){
                continue;
            }

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<n && dist[nr][nc]>cave[nr][nc]+currCost){
                    dist[nr][nc] = cave[nr][nc]+currCost;
                    pq.offer(new int[]{nr, nc, cave[nr][nc]+currCost});
                }
            }
        }

        return 0;
    }
}