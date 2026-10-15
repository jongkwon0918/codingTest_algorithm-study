package daily.day20261015;

import java.util.*;

/*
1. 대각선으로 이어진 섬

직사각형 지도에서 1은 땅, 0은 바다이다.
땅끼리 상하좌우뿐 아니라 대각선으로 붙어 있어도 연결되어 있다.
연결된 땅을 따라 서로 이동할 수 있는 칸들은 하나의 섬이다.
지도의 섬 개수를 반환하라. 지도 밖으로는 이동할 수 없다.

제한사항
- 행과 열은 각각 1 이상 50 이하이다.
- 모든 행의 길이는 같으며 값은 0 또는 1이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
map={{1,0},{0,1}} -> 1
map={{1,0,1},{0,0,0},{1,0,1}} -> 4
map={{0}} -> 0
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 1
        System.out.println(solution.solution(
                new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}})); // 4
        System.out.println(solution.solution(new int[][]{{0}})); // 0
    }
}

class Solution1 {
    boolean[][] visited;
    int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1};
    int[] dc = {0, 0, -1, 1, -1, 1, -1, 1};
    int n;
    int m;
    public int solution(int[][] map) {
        n = map.length;
        m = map[0].length;
        visited = new boolean[n][m];
        int count=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(!visited[i][j] && map[i][j]==1){
                    dfs(map, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] map, int i, int j) {
        visited[i][j] = true;

        for(int d=0; d<8; d++){
            int nr = i+dr[d];
            int nc = j+dc[d];

            if(nr>=0 && nr<n && nc>=0 && nc<m && map[nr][nc]==1 && !visited[nr][nc]){
                dfs(map, nr, nc);
            }
        }
    }
}

