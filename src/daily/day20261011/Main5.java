package daily.day20261011;

import java.util.*;

/*
5. 모두 녹기 직전

사각형 판 map에서 1은 치즈, 0은 빈칸이다. 판 가장자리에는 치즈가 없다.
판 바깥과 상하좌우로 연결된 빈칸에는 외부 공기가 들어 있다.
외부 공기와 한 면 이상 맞닿은 치즈 칸은 한 시간이 지나면 동시에 녹아 0이 된다.
치즈로 둘러싸인 구멍은 외부 공기가 닿지 않으므로, 구멍과 닿았다는 이유로 치즈가 녹지 않는다.
치즈가 녹아 구멍이 외부와 연결되면 다음 시간의 공기 판정에 반영된다.
치즈가 모두 녹는 데 걸리는 시간과, 마지막으로 녹기 직전에 남아 있던 치즈 칸 수를
{시간, 마지막 시간 시작 시의 치즈 칸 수}로 반환하라.

제한사항
- 행과 열의 길이는 양의 정수이며 각각 100 이하이다.
- 모든 행의 길이는 같다. 값은 0 또는 1이다.
- 가장자리 칸은 모두 0이다.
- 처음부터 치즈가 없으면 {0,0}을 반환한다.

입출력 예시
map={{0,0,0},{0,1,0},{0,0,0}} -> [1,1]
map={{0,0,0,0,0},
     {0,1,1,1,0},
     {0,1,1,1,0},
     {0,1,1,1,0},
     {0,0,0,0,0}} -> [2,1]
두 번째 예제에서는 첫 시간에 바깥 치즈 8칸이 녹고, 다음 시간에 가운데 1칸이 녹는다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(Arrays.toString(solution.solution(
                new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}))); // [1, 1]
        int[][] map = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
        System.out.println(Arrays.toString(solution.solution(map))); // [2, 1]
    }
}

class Solution5 {
    int n;
    int m;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    public int[] solution(int[][] map) {
        n = map.length;
        m = map[0].length;
        int time = 0;
        int count = 0;
        while(true){
            int curr = dfs(n,m,map);
            if(curr!=0){
                count = curr;
            }else{
                return new int[]{time, count};
            }
            map = cheeze(map);
            time++;

        }
    }

    private int dfs(int n, int m, int[][] map) {
        int count=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(map[i][j]==1){
                    count++;
                }
            }
        }
        return count;
    }

    private int[][] cheeze(int[][] map) {
        int[][] tempMap = new int[n][m];
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();

        for(int i=0; i<n; i++){
            tempMap[i] = map[i].clone();
        }

        queue.offer(new int[]{0,0});
        visited[0][0] = true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];
                if(nr>=0 && nr<n && nc>=0 && nc<m && !visited[nr][nc]){
                    visited[nr][nc] = true;

                    if (map[nr][nc] == 0) {
                        // 외부 공기라면 계속 탐색
                        queue.offer(new int[]{nr, nc});
                    } else {
                        // 외부 공기와 접한 치즈이므로 녹임
                        tempMap[nr][nc] = 0;
                    }
                }
            }
        }

        return tempMap;
    }
}

