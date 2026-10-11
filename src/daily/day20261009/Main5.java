package daily.day20261009;

import java.util.*;

/*
5. 국경이 열리는 날

n*n개의 나라가 격자에 있고 population[row][col]은 그 나라의 인구수이다.
매일 아래 과정을 반복한다.
1. 상하좌우로 인접한 두 나라의 인구 차이가 l 이상 r 이하이면 국경을 연다.
   이 조건은 그날 인구 이동 전의 인구수로 판단한다.
2. 열린 국경을 통해 서로 이동할 수 있는 나라들은 하나의 연합이다.
3. 연합 내 모든 나라의 인구수를 연합의 총 인구수 / 나라 수로 바꾼다.
   나눗셈의 소수점 이하는 버린다.
4. 모든 국경을 닫고, 다음 날 다시 연합을 결정한다.
더 이상 국경을 열 수 없을 때까지 인구 이동이 발생한 일수를 반환하라.

제한사항
- 1 <= n <= 50, population은 n*n 배열이다.
- 1 <= l <= r <= 100
- 0 <= population[row][col] <= 100
- 인구 이동이 발생하는 일수는 2,000 이하이다.

입출력 예시
population={{50,30},{20,40}}, l=20, r=50 -> 1
population={{50,30},{20,40}}, l=40, r=50 -> 0
population={{50,30},{20,40}}, l=20, r=30 -> 1
population={{10,15,20},{20,30,25},{40,22,10}}, l=5, r=10 -> 2
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(new int[][]{{50, 30}, {20, 40}}, 20, 50)); // 1
        System.out.println(solution.solution(new int[][]{{50, 30}, {20, 40}}, 40, 50)); // 0
        System.out.println(solution.solution(new int[][]{{50, 30}, {20, 40}}, 20, 30)); // 1
        System.out.println(solution.solution(
                new int[][]{{10, 15, 20}, {20, 30, 25}, {40, 22, 10}}, 5, 10)); // 2
    }
}

class Solution5 {
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int n;
    boolean[][] visited;
    public int solution(int[][] population, int l, int r) {
        n = population.length;

        int days = 0;

        while(true){
            visited = new boolean[n][n];
            boolean moved = false;
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    if(!visited[i][j]){
                        if(bfs(population, i, j, l, r)){
                            moved = true;
                        }
                    }
                }
            }
            if(!moved){
                return days;
            }

            days++;
        }
    }

    private boolean bfs(int[][] population, int startRow, int startCol,int l, int r) {
        List<int[]> union = new ArrayList<>();

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        int sum =0;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int row = curr[0];
            int col = curr[1];
            union.add(curr);
            sum+=population[row][col];

            for(int d=0; d<4; d++){
                int nr = row + dr[d];
                int nc = col + dc[d];
                if(nr>=0 && nr<n && nc>=0 && nc<n && !visited[nr][nc]){
                    int check = Math.abs(population[nr][nc]-population[row][col]);
                    if(check>=l && check<=r){
                        queue.offer(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
        }

        if(union.size()==1){
            return false;
        }
        int avg = sum/union.size();
        for(int[] country : union){
            population[country[0]][country[1]] = avg;
        }

        return true;
    }
}

























