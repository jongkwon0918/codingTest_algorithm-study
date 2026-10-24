package daily.day20261023;

import java.util.*;

/*
5. 위치와 방향을 함께 맞추기

직사각형 공장에서 0은 로봇이 갈 수 있는 칸, 1은 갈 수 없는 칸이다.
로봇은 위치와 바라보는 방향을 가진다. 한 번의 명령으로 다음 중 하나를 수행한다.
- 현재 방향으로 1칸, 2칸 또는 3칸 전진한다. 이동 중 지나가는 모든 칸이 통과 가능해야 한다.
- 제자리에서 왼쪽으로 90도 또는 오른쪽으로 90도 회전한다.
180도 회전은 한 명령이 아니며, 뒤로 걷는 명령도 없다.
start와 target은 각각 {행,열,방향}이다.
행과 열은 원본처럼 1부터 시작하고, 방향은 동=1, 서=2, 남=3, 북=4이다.
목표 위치에 도착하는 것뿐 아니라 목표 방향까지 맞추는 최소 명령 횟수를 반환하라.

제한사항
- 행과 열의 길이는 각각 1 이상 100 이하이다.
- 모든 행 길이는 같고 각 값은 0 또는 1이다.
- 출발 칸과 도착 칸은 0이며 항상 목표까지 이동할 수 있다.

입출력 예시
factory={{0,0,0,0}}, start={1,1,1},target={1,4,1} -> 1
동쪽으로 세 칸 전진하는 명령 한 번이면 된다.
factory={{0}}, start={1,1,1},target={1,1,2} -> 2
같은 칸에서 동→남→서 또는 동→북→서로 두 번 회전해야 한다.
factory가 3*3 모두 0, start={3,1,1},target={1,3,4} -> 3
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                new int[][]{{0,0,0,0}}, new int[]{1,1,1}, new int[]{1,4,1})); // 1
        System.out.println(solution.solution(
                new int[][]{{0}}, new int[]{1,1,1}, new int[]{1,1,2})); // 2
        System.out.println(solution.solution(
                new int[3][3], new int[]{3,1,1}, new int[]{1,3,4})); // 3
    }
}

class Solution5 {
    int n;
    int m;
    int[] dr = {0,0,1,-1};
    int[] dc = {1,-1,0,0};
    boolean[][][] visited;
    public int solution(int[][] factory, int[] start, int[] target) {
        n = factory.length;
        m = factory[0].length;
        visited = new boolean[n][m][5];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0]-1, start[1]-1, start[2]-1, 0});
        visited[start[0]-1][start[1]-1][start[2]-1] = true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int arrow = curr[2];
            int count = curr[3];

            if(r==target[0]-1 && c==target[1]-1 && arrow==target[2]-1){
                return count;
            }

            for(int i=1; i<4; i++){
                int nr = r + dr[arrow]*i;
                int nc = c + dc[arrow]*i;

                if(nr>=0 && nr<n && nc>=0 && nc<m &&  factory[nr][nc] == 0){
                    if(!visited[nr][nc][arrow]){
                        visited[nr][nc][arrow] = true;
                        queue.offer(new int[]{nr, nc, arrow, count + 1});
                    }
                } else{
                    break;
                }
            }

            int[] nextArrows = arrow < 2 ? new int[]{2, 3} : new int[]{0, 1};

            for (int nextArrow : nextArrows) {
                if (!visited[r][c][nextArrow]) {
                    visited[r][c][nextArrow] = true;
                    queue.offer(new int[]{r, c, nextArrow, count + 1});
                }
            }

        }

        return 0;
    }
}

