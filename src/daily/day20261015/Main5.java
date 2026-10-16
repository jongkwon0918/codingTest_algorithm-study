package daily.day20261015;

import java.util.*;

/*
5. 일부 바이러스만 활성화하기

n*n 연구소에서 0은 빈칸, 1은 벽, 2는 비활성 바이러스가 있는 칸이다.
2인 칸 중 정확히 m개를 골라 시간0에 활성화한다.
활성 바이러스는 매초 상하좌우로 한 칸씩 퍼지며 벽은 통과할 수 없다.
선택하지 않은 2인 칸에도 퍼질 수 있고, 도달하면 그 바이러스가 활성화된다.
비활성 바이러스 칸을 지나는 데도 이동 1초가 걸린다.
처음 0이었던 모든 빈칸에 바이러스를 퍼뜨리는 최소 시간을 반환하라.
비활성 바이러스 칸을 전부 활성화할 필요는 없다.
어떤 선택으로도 모든 빈칸에 퍼뜨릴 수 없으면 -1이다. 처음부터 빈칸이 없으면 0이다.

제한사항
- 4 <= n <= 50, lab은 n*n 배열이다.
- lab 값은 0,1,2 중 하나이다.
- 2인 칸 수 V는 1 이상 10 이하이다.
- 1 <= m <= V

입출력 예시
lab={{2,0,2,0},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> 1
(0,2)를 활성화하면 두 빈칸에 모두 1초 만에 도달한다.
lab={{0,2,2,2},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> 1
(0,1)을 활성화한다. 먼 비활성 바이러스가 남아 있어도 빈칸을 채우면 끝이다.
lab={{2,1,0,1},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> -1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] lab = {
            {2, 0, 2, 0},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(lab, 1)); // 1
        int[][] inactive = {
            {0, 2, 2, 2},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(inactive, 1)); // 1
        int[][] blocked = {
            {2, 1, 0, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(blocked, 1)); // -1
    }
}

class Solution5 {
    int n;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int answerTime;
    int count;
    List<int[]> list;
    int[] selected;
    boolean[][] visited;
    public int solution(int[][] lab, int m) {
        n = lab.length;
        list = new ArrayList<>();
        selected = new int[m];
        answerTime = Integer.MAX_VALUE;
        count = 0;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++) {
                if(lab[i][j]==0){
                    count++;
                }
                if(lab[i][j]==2){
                    list.add(new int[]{i,j});
                }
            }
        }
        if(count==0){
            return 0;
        }

        dfs(0, 0, lab, m);

        return answerTime==Integer.MAX_VALUE?-1:answerTime;
    }

    private void dfs(int start, int choice, int[][] lab, int m) {
        if (choice == m) {
            Queue<int[]> queue = new LinkedList<>();
            visited = new boolean[n][n];
            for (int index : selected) {
                int[] virus = list.get(index);
                int r = virus[0];
                int c = virus[1];
                queue.offer(new int[]{r, c, 0});
                visited[r][c] = true;
            }
            bfs(queue, lab);
            return;
        }

        for (int i = start; i < list.size(); i++) {
            selected[choice] = i;
            dfs(i + 1, choice + 1, lab, m);
        }
    }

    private void bfs(Queue<int[]> queue, int[][] lab) {
        int remaining = count;
        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int time = curr[2];

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<n && !visited[nr][nc] && lab[nr][nc]!=1){
                    if(lab[nr][nc]==0){
                        remaining--;

                        if(remaining==0){
                            answerTime = Math.min(answerTime, time+1);
                            return;
                        }
                    }
                    queue.offer(new int[]{nr, nc, time+1});
                    visited[nr][nc]=true;
                }
            }
        }
    }
}

