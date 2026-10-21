package daily.day20261017;

import java.util.*;

/*
5. 가장 짧은 다리 하나 놓기

n*n 지도에서 1은 육지, 0은 바다이다.
상하좌우로 붙어 있는 육지들은 하나의 섬이다. 대각선만 맞닿으면 다른 섬이다.
바다 칸에 다리를 놓아 서로 다른 섬 두 개를 연결하려 한다.
다리는 상하좌우로 이어져야 하며, 길이는 다리를 놓은 바다 칸 수이다.
어떤 두 섬을 선택해도 되며, 모든 섬을 연결할 필요는 없다.
가장 짧은 다리 하나의 길이를 반환하라.

제한사항
- 1 <= n <= 100, map은 n*n 배열이다.
- 값은 0 또는 1이며 항상 두 개 이상의 섬이 있다.
- 다리는 바다에만 놓고 육지는 길이에 포함하지 않는다.

입출력 예시
map={{1,0,1},{1,0,0},{0,0,0}} -> 1
map={{1,0,0},{0,0,0},{0,0,1}} -> 3
map={{1,0},{0,1}} -> 1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                new int[][]{{1, 0, 1}, {1, 0, 0}, {0, 0, 0}})); // 1
        System.out.println(solution.solution(
                new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 1}})); // 3
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 1
    }
}

class Solution5 {
    int n;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int[][] tempMap;
    boolean[][] visited;
    int minCount;
    public int solution(int[][] map) {
        n = map.length;
        tempMap = new int[n][n];
        visited = new boolean[n][n];

        for(int t=0; t<n; t++){
            Arrays.fill(tempMap[t], 0);
        }

        int num=1;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(!visited[i][j] && map[i][j]==1) {
                    dfs(map, i, j, num);
                    num++;
                }
            }
        }

        boolean[][] mapVisited;
        minCount = Integer.MAX_VALUE;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++) {
                if(tempMap[i][j]!=0){
                    mapVisited = new boolean[n][n];
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{i,j,0});
                    mapVisited[i][j] = true;
                    bfs(mapVisited, queue, tempMap[i][j]);
                }
            }
        }

        return minCount;
    }

    private void bfs(boolean[][] mapVisited, Queue<int[]> queue, int start) {

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int count = curr[2];

            for(int d=0; d<4; d++){
                int nr = r + dr[d];
                int nc = c + dc[d];
                if(nr>=0 && nr<n && nc>=0 && nc<n && !mapVisited[nr][nc]) {
                    if(tempMap[nr][nc]==0){
                        queue.offer(new int[]{nr, nc, count+1});
                        mapVisited[nr][nc]=true;
                    }
                    else if(tempMap[nr][nc]!=start){
                        minCount = Math.min(minCount, count);
                        return;
                    }
                }
            }
        }
    }

    private void dfs(int[][] map, int currI, int currJ, int num) {
        visited[currI][currJ] = true;
        tempMap[currI][currJ] = num;

        for(int d=0; d<4; d++){
            int nr = currI + dr[d];
            int nc = currJ + dc[d];
            if(nr>=0 && nr<n && nc>=0 && nc<n && !visited[nr][nc] && map[nr][nc]==1) {
                dfs(map, nr, nc, num);
            }
        }
    }
}

