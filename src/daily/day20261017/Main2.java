package daily.day20261017;

import java.util.*;

/*
2. 여섯 방향으로 움직이는 말

n*n 보드에 놓인 말을 start={행,열}에서 target={행,열}로 이동한다.
한 번의 이동에서 행·열 변화량은 다음 여섯 가지 중 하나이다.
(-2,-1),(-2,1),(0,-2),(0,2),(2,-1),(2,1)
다른 방향으로는 이동할 수 없고 보드 밖으로 나갈 수 없다.
최소 이동 횟수를 반환하라. 도달할 수 없으면 -1이다.
시작과 목표가 같으면 0이다. 장애물은 없다.

제한사항
- 5 <= n <= 200
- 좌표의 행과 열은 0 이상 n 미만이다.
- start와 target의 길이는 각각 2이다.

입출력 예시
n=5, start={0,0}, target={2,1} -> 1
n=5, start={0,0}, target={4,2} -> 2
n=5, start={0,0}, target={1,0} -> -1
n=5, start={2,2}, target={2,2} -> 0
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{2, 1})); // 1
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{4, 2})); // 2
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{1, 0})); // -1
        System.out.println(solution.solution(5, new int[]{2, 2}, new int[]{2, 2})); // 0
    }
}

class Solution2 {
    int[] dr = {-2,-2,0,0,2,2};
    int[] dc = {-1,1,-2,2,-1,1};
    boolean[][] visited;
    public int solution(int n, int[] start, int[] target) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1], 0});
        visited = new boolean[n][n];
        visited[start[0]][start[1]] = true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];

            if(r==target[0] && c==target[1]){
                return cost;
            }

            for(int d=0; d<6; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<n && !visited[nr][nc]){
                    queue.offer(new int[]{nr, nc, cost+1});
                    visited[nr][nc] = true;
                }
            }
        }

        return -1;
    }
}

