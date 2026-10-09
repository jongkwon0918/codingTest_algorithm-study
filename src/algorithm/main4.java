/*4번. 섬의 개수

1은 땅이고 0은 바다이다.

상하좌우로 연결된 1들은 하나의 섬이다.

섬의 개수를 반환하라.*/
package algorithm;
import java.util.*;
public class main4 {
    public static void main(String[] args) {

        Solution4 solution = new Solution4();

        int[][] map = {
                 {1,1,0,0,0},
                 {1,1,0,1,0},
                 {0,0,0,1,1},
                 {0,0,0,0,0}
            };


        int answer = solution.solution(map);

        System.out.println(answer);
    }
}
class Solution4 {

    int n;
    int m;
    int[][] map;

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] map) {
        this.map = map;
        n = map.length;
        m = map[0].length;

        int answer = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {

                if (map[r][c] == 1) {
                    answer++;
                    dfs(r, c);
                }
            }
        }

        return answer;
    }

    void dfs(int r, int c) {
        map[r][c] = 0;

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr>=0 && nr<n && nc>=0 && nc<m
                    && map[nr][nc] == 1) {
                dfs(nr, nc);
            }
        }
    }
}
