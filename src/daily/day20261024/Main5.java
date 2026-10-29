package daily.day20261024;

import java.util.*;

/*
5. 같은 색을 지우고 아래로 떨어뜨리기

12행6열의 판에 색깔 블록이 놓여 있다. '.'는 빈칸이고 R,G,B,P,Y는 색깔이다.
같은 색 블록이 상하좌우로 4개 이상 연결된 그룹은 그 그룹 전체가 사라진다.
현재 사라질 수 있는 그룹은 색이나 위치와 관계없이 모두 동시에 사라진다.
그다음 남은 블록들은 같은 열에서 아래로 떨어져 빈 공간을 채운다.
낙하 후 다시 4개 이상 연결된 그룹이 생기면 같은 처리를 반복한다.
그룹 여러 개가 동시에 사라져도 연쇄는 한 번만 증가한다.
더 이상 사라질 그룹이 없을 때까지 일어나는 연쇄 횟수를 반환하라.
대각선 연결은 인정하지 않으며 새 블록을 추가하지 않는다.

제한사항
- field는 길이6인 문자열12개이다.
- 문자는 .,R,G,B,P,Y 중 하나이다.
- 초기 판은 이미 아래로 떨어진 상태라 블록 바로 아래에 빈칸이 있는 경우는 없다.

입출력 예시
모든 칸이 '.' -> 0
맨 아래 행만 "RRRR.."이고 나머지는 모두 "......" -> 1
맨 아래 두 행이 "....GG","RRRRGG"이고 나머지는 모두 빈칸 -> 1
빨강4개와 초록4개가 동시에 사라지므로2가 아니라1이다.
아래 Main의 마지막 예제 -> 3
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        String[] empty = new String[12];
        Arrays.fill(empty, "......");
        System.out.println(solution.solution(empty)); // 0

        String[] one = empty.clone();
        one[11] = "RRRR..";
        System.out.println(solution.solution(one)); // 1

        String[] simultaneous = empty.clone();
        simultaneous[10] = "....GG";
        simultaneous[11] = "RRRRGG";
        System.out.println(solution.solution(simultaneous)); // 1

        String[] chain = {
            "......", "......", "......", "......",
            "......", "......", "......", "......",
            ".Y....", ".YG...", "RRYG..", "RRYGG."
        };
        System.out.println(solution.solution(chain)); // 3
    }
}

class Solution5 {
    int n;
    int m;
    boolean[][] visited;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    public int solution(String[] field) {
        n = 12;
        m = 6;
        int time = -1;

        visited = new boolean[n][m];

        char[][] map = new char[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                map[i][j] = field[i].charAt(j);
            }
        }

        boolean next=true;
        while(next){
            time++;
            visited = new boolean[n][m];
            next=false;

            for(int i=0; i<n; i++){
                for(int j=0; j<m; j++) {
                    if(!visited[i][j] && map[i][j]!='.'){
                        boolean over = dfs(i,j, map[i][j], map);
                        if(over){
                            map = changMap(map, i, j);
                            next = true;
                        }
                    }
                }
            }

            moveMap(map);

        }

        return time;
    }

    private void moveMap(char[][] map) {
        for(int c=0; c<m; c++){
            int bottom = n-1;
            for(int r=n-1; r>=0; r--){
                if(map[r][c]!='.'){
                    char color = map[r][c];
                    map[r][c] = '.';
                    map[bottom][c] = color;
                    bottom--;
                }
            }
        }
    }

    private char[][] changMap(char[][] map, int curri, int currj) {
        char[][] tempMap = new char[n][m];
        for(int i=0; i<n; i++){
            tempMap[i] = map[i].clone();
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{curri, currj});
        boolean[][] visited = new boolean[n][m];
        visited[curri][currj] = true;
        tempMap[curri][currj] = '.';

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<m && !visited[nr][nc] && map[curri][currj]==map[nr][nc]){
                    queue.offer(new int[]{nr, nc});
                    visited[nr][nc] = true;
                    tempMap[nr][nc] = '.';
                }
            }
        }

        return tempMap;
    }

    private boolean dfs(int curri, int currj, char color, char[][] map) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{curri, currj});
        visited[curri][currj] = true;
        int count = 1;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<m && !visited[nr][nc] && map[nr][nc]==color){
                    queue.offer(new int[]{nr, nc});
                    visited[nr][nc] = true;
                    count++;

                    if(count>=4){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

