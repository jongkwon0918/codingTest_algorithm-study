package daily.day20261025;

import java.util.*;

/*
5. 이동하는 상어를 순서대로 잡기

rows*cols 격자에 상어들이 있다. sharks[i]={행,열,속력,방향,크기}이다.
행과 열은1부터 시작하고 방향은 위1,아래2,오른쪽3,왼쪽4이다.
낚시꾼은1번 열 왼쪽 바깥에서 시작해 다음 순서를 반복한다.
1. 오른쪽으로 한 열 이동한다.
2. 현재 열에서 행 번호가 가장 작은 상어 한 마리를 잡아 제거한다. 없으면 잡지 않는다.
3. 살아 있는 모든 상어가 자기 속력만큼 동시에 이동한다.
상어는 현재 방향으로 한 칸씩 움직이며 다음 칸이 격자 밖이면 방향을 반대로 바꾼 뒤 이동한다.
경계에 부딪혀도 속력은 줄어들지 않는다.
모든 상어의 이동이 끝난 뒤 같은 칸에 여러 마리가 있으면 가장 큰 상어만 살아남는다.
상어들이 이동 도중 스쳐 지나가는 것은 충돌로 처리하지 않는다.
낚시꾼이 마지막 열까지 처리하고 잡은 상어들의 크기 합을 반환하라.

제한사항
- 2 <= rows,cols <= 100, 0 <= sharks.length <= rows*cols
- 행은1 이상 rows 이하, 열은1 이상 cols 이하이다.
- 0 <= 속력 <= 1,000, 1 <= 방향 <= 4, 1 <= 크기 <= 10,000
- 초기 위치와 크기는 각각 상어끼리 중복되지 않는다.

입출력 예시
rows=2,cols=3,sharks={{2,1,0,1,5},{1,3,0,2,9}} -> 14
1번 열에서5,3번 열에서9를 잡는다.
rows=2,cols=3,sharks={} -> 0
rows=3,cols=3,sharks={{1,2,1,2,4},{3,2,1,1,9}} -> 9
첫 열에서 잡는 상어는 없고,두 상어가(2,2)에서 만나9만 남아 둘째 열에서 잡힌다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                2, 3, new int[][]{{2,1,0,1,5},{1,3,0,2,9}})); // 14
        System.out.println(solution.solution(2, 3, new int[0][5])); // 0
        System.out.println(solution.solution(
                3, 3, new int[][]{{1,2,1,2,4},{3,2,1,1,9}})); // 9
        System.out.println(solution.solution(4, 6, new int[][]{
            {4,1,3,3,8}, {1,3,5,2,9}, {2,4,8,4,1}, {4,5,0,1,4},
            {3,3,1,2,7}, {1,5,8,4,3}, {3,6,2,1,2}, {2,2,2,3,5}
        })); // 22
    }
}

class Solution5 {
    int n;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, 1, -1};

    public int solution(int rows, int cols, int[][] sharks) {
        Queue<int[]> queue = new LinkedList<>();
        n = sharks.length;
        int answer = 0;
        int fishhook = 1;
        int[][][] map = new int[rows+1][cols+1][];

        for (int i = 0; i < n; i++) {
            map[sharks[i][0]][sharks[i][1]] = new int[]{sharks[i][2],sharks[i][3],sharks[i][4]};
        }

        while(fishhook<=cols){
            for (int i=1; i<=rows; i++) {
                if (map[i][fishhook] != null) {
                    answer += map[i][fishhook][2];
                    map[i][fishhook] = null;
                    break;
                }
            }

            int[][][] nextMap = new int[rows+1][cols+1][];

            for(int i=1; i<=rows; i++){
                for(int j=1; j<=cols; j++){
                    if(map[i][j]!=null){
                        queue.offer(new int[]{i,j,map[i][j][0], map[i][j][1], map[i][j][2]});
                    }
                }
            }

            while(!queue.isEmpty()){
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int speed = curr[2];
                int arrow = curr[3];
                int size = curr[4];

                int sharkSpeed = speed;

                int nr=r;
                int nc=c;

                while(sharkSpeed>0){
                    nr = r+dr[arrow-1];
                    nc = c+dc[arrow-1];

                    if(nr<1 || nr>rows || nc<1 || nc>cols){
                        if(arrow==1 || arrow==3){
                            arrow +=1;
                        } else if (arrow==2 || arrow==4) {
                            arrow -=1;
                        }

                        nr = r+dr[arrow-1];
                        nc = c+dc[arrow-1];
                    }
                    r=nr;
                    c=nc;

                    sharkSpeed--;
                }

                if(nextMap[nr][nc]==null || nextMap[nr][nc][2]<size){
                    nextMap[nr][nc] = new int[]{speed, arrow, size};
                }
            }
            map = nextMap;
            fishhook++;
        }
        return answer;
    }
}

