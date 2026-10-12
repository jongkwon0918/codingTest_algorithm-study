package daily.day20261011;

import java.util.*;

/*
4. 제한된 점프로 건너가기

직사각형 격자 map에서 0은 평지, 1은 장애물이다.
왼쪽 위 (0,0)에서 오른쪽 아래로 이동한다. 일반 이동은 상하좌우 한 칸이다.
추가로 최대 k번까지 행·열 변화량이 다음 중 하나인 점프를 사용할 수 있다.
(-2,-1),(-2,1),(-1,-2),(-1,2),(1,-2),(1,2),(2,-1),(2,1)
점프 중간에 장애물이 있어도 되지만 착지하는 칸은 평지여야 한다.
일반 이동과 점프 모두 한 번의 동작이다. 격자 밖으로 착지할 수 없다.
최소 동작 수를 반환하라. 도달할 수 없으면 -1이다.
점프를 반드시 k번 사용할 필요는 없다. 시작과 도착이 같으면 0이다.

제한사항
- 행과 열은 각각 1 이상 200 이하이다.
- 0 <= k <= 30
- map의 값은 0 또는 1이며 시작점과 도착점은 항상 0이다.

입출력 예시
map={{0,0,0},{0,0,0},{0,0,0}}, k=1 -> 2
같은 map, k=0 -> 4
map={{0,1,0},{1,1,0},{0,0,0}}, k=1 -> 2
같은 map, k=0 -> -1
map={{0}}, k=3 -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] map = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        System.out.println(solution.solution(map, 1)); // 2
        System.out.println(solution.solution(map, 0)); // 4
        int[][] blocked = {{0, 1, 0}, {1, 1, 0}, {0, 0, 0}};
        System.out.println(solution.solution(blocked, 1)); // 2
        System.out.println(solution.solution(blocked, 0)); // -1
        System.out.println(solution.solution(new int[][]{{0}}, 3)); // 0
    }
}

class Solution4 {
    int n;
    int m;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int[] jr = {-2,-2,-1,-1,1,1,2,2};
    int[] jc = {-1,1,-2,2,-2,2,-1,1};
    public int solution(int[][] map, int k) {
        n = map.length;
        m = map[0].length;

        if(n-1 == 0 && m-1 == 0) return 0;

        int[][][] dist = new int[n][m][k+1];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++) {
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
            }
        }
        dist[0][0][k] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0,0,k});

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];
            int restK = curr[3];

            if(r==n-1 && c==m-1){
                return cost;
            }

            if(restK>0){
                for(int j=0; j<8; j++){
                    int nr = r+jr[j];
                    int nc = c+jc[j];

                    if(nr>=0 && nr<n && nc>=0 && nc<m){
                        if(dist[nr][nc][restK-1]>cost+1 && map[nr][nc]==0){
                            dist[nr][nc][restK-1] = cost+1;
                            queue.offer(new int[]{nr, nc, cost+1, restK-1});
                        }
                    }
                }
            }

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<m){
                    if(dist[nr][nc][restK]>cost+1 && map[nr][nc]==0){
                        dist[nr][nc][restK] = cost+1;
                        queue.offer(new int[]{nr, nc, cost+1, restK});
                    }
                }
            }
        }

        return -1;
    }
}

