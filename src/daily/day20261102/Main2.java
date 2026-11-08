package daily.day20261102;

import java.util.*;

/*
2. 벽을 피해 만날 수 있는 사람

직사각형 캠퍼스에서 I는 내 위치, O는 빈 공간, X는 벽, P는 사람이다.
상하좌우로 벽이 아닌 인접 칸으로 이동할 수 있다.
현재 위치에서 만날 수 있는 서로 다른 사람의 수를 문자열로 반환하라.
같은 사람을 여러 번 세지 않고, 사람 칸을 지나서 더 이동할 수도 있다.
만날 수 있는 사람이 하나도 없으면 "TT"를 반환한다.
가장 가까운 사람 한 명까지의 거리를 구하는 문제가 아니다.

제한사항
- 1 <= campus.length <= 600
- 1 <= campus[0].length() <= 600, 모든 문자열 길이는 같다.
- 문자는 I,O,X,P 중 하나이며 I는 정확히 하나이다.

입출력 예시
campus={"IOP","XXO","POP"} -> "3"
오른쪽 위 사람을 지나 아래로 이동하여 나머지 두 사람도 만날 수 있다.
campus={"IXP"} -> "TT"
campus={"I"} -> "TT"
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new String[]{
            "IOP", "XXO", "POP"
        })); // 3
        System.out.println(solution.solution(new String[]{"IXP"})); // TT
        System.out.println(solution.solution(new String[]{"I"})); // TT
    }
}

class Solution2 {
    int n;
    int m;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    char[][] map;
    boolean[][] visited;
    public String solution(String[] campus) {
        n = campus.length;
        m = campus[0].length();
        map = new char[n][m];
        Queue<int[]> queue = new LinkedList<>();
        visited = new boolean[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                map[i][j] = campus[i].charAt(j);
                if(map[i][j]=='I'){
                    queue.offer(new int[]{i,j});
                    visited[i][j] = true;
                }
            }
        }

        int count = 0;

        while (!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            if(map[r][c]=='P'){
                count++;
            }

            for(int d=0; d<4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc]) {
                    if(map[nr][nc]!='X'){
                        queue.offer(new int[]{nr,nc});
                        visited[nr][nc] = true;
                    }
                }
            }
        }

        return count>0 ? String.valueOf(count) :  "TT";
    }
}

