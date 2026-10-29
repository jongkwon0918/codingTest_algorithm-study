package daily.day20261025;

import java.util.*;

/*
4. 검을 얻고 제한 시간 안에 도착하기

직사각형 성의 왼쪽 위 (0,0)에서 출발해 오른쪽 아래 (n-1,m-1)까지 가려고 한다.
0은 빈칸,1은 벽,2는 전설의 검이 있는 칸이다.
상하좌우 인접 칸으로 한 칸 이동하는 데1시간이 걸린다.
검을 얻기 전에는 벽을 통과할 수 없고, 검이 있는 칸에 도착하면 즉시 검을 얻는다.
검을 얻은 뒤에는 벽을 제한 없이 통과할 수 있으며 검을 잃지 않는다.
검을 얻지 않고 도착해도 된다.
limit시간 이내에 도착할 수 있으면 최소 시간을 문자열로, 불가능하면 "Fail"을 반환하라.
정확히 limit시간에 도착하는 것도 성공이다.

제한사항
- 행과 열은 각각3 이상100 이하이며 모든 행 길이는 같다.
- 1 <= limit <= 10,000
- 검은 정확히 하나 있고 시작과 도착 칸은0이다.

입출력 예시
castle={{0,0,2},{1,1,1},{0,0,0}}, limit=4 -> "4"
검을 얻은 뒤 아래쪽 벽을 통과할 수 있다.
같은 성에서 limit=3 -> "Fail"
castle={{0,0,0},{1,1,1},{0,2,0}}, limit=100 -> "Fail"
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] castle = {{0,0,2},{1,1,1},{0,0,0}};
        System.out.println(solution.solution(castle, 4)); // 4
        System.out.println(solution.solution(castle, 3)); // Fail
        System.out.println(solution.solution(
                new int[][]{{0,0,0},{1,1,1},{0,2,0}}, 100)); // Fail
    }
}

class Solution4 {
    int n;
    int m;
    boolean[][][] visited;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};

    public String solution(int[][] castle, int limit) {
        n=castle.length;
        m=castle[0].length;
        visited = new boolean[n][m][2];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0,0,1});
        visited[0][0][1] = true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];
            int noWall = curr[3];

            if(r==n-1 && c==m-1){
                if(cost<=limit){
                    return String.valueOf(cost);
                }
                break;
            }

            if(castle[r][c]==2){
                noWall=0;
            }

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc][noWall]) {
                    if(noWall==1){
                        if(castle[nr][nc]!=1){
                            queue.offer(new int[]{nr, nc, cost+1, noWall});
                            visited[nr][nc][noWall] = true;
                        }
                    }else {
                        queue.offer(new int[]{nr, nc, cost+1, noWall});
                        visited[nr][nc][noWall] = true;
                    }
                }
            }
        }

        return "Fail";
    }
}

