package daily.day20261022;

import java.util.*;

/*
5. 내려오는 벽을 피해 탈출하기

8*8 판에서 '.'는 빈칸, '#'는 벽이다.
캐릭터는 왼쪽 아래 (7,0)에서 출발해 오른쪽 위 (0,7)로 탈출하려 한다. 좌표는 0부터 시작한다.
1초마다 캐릭터가 먼저 행동하고, 그다음 모든 벽이 한 칸 아래로 내려온다.
캐릭터는 상하좌우 또는 대각선 인접 칸으로 한 칸 이동하거나 제자리에 머무를 수 있다.
캐릭터는 현재 벽이 있는 칸으로 이동할 수 없으며, 벽이 내려온 뒤 캐릭터와 겹쳐도 실패한다.
맨 아래 행의 벽은 다음 이동 때 판 밖으로 사라진다.
목적지에 도달할 수 있으면 1, 불가능하면 0을 반환하라.
이동 횟수의 최솟값을 구하는 문제는 아니다.

제한사항
- maze는 길이 8의 문자열 8개로 구성된다.
- 문자는 '.' 또는 '#'이다.
- 시작 칸 (7,0)은 항상 빈칸이다.

입출력 예시
벽이 전혀 없는 판 -> 1
맨 아래 행만 '........'이고 그 위 7개 행이 모두 '########'인 판 -> 0
두 번째 예제는 첫 행동 뒤 벽이 맨 아래로 내려와 피할 수 없다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        String[] empty = {
            "........", "........", "........", "........",
            "........", "........", "........", "........"
        };
        String[] trapped = {
            "########", "########", "########", "########",
            "########", "########", "########", "........"
        };
        String[] falling = {
            "........", "........", "........", "........",
            "........", "........", "#.......", "........"
        };
        System.out.println(solution.solution(empty)); // 1
        System.out.println(solution.solution(trapped)); // 0
        System.out.println(solution.solution(falling)); // 1
    }
}

class Solution5 {
    int n;
    char[][] map;
    int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1, 0};
    int[] dc = {0, 0, -1, 1, -1, 1, -1, 1, 0};

    public int solution(String[] maze) {
        n = 8;
        map = new char[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                map[i][j]=maze[i].charAt(j);
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{7,0});



        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean[][] visited = new boolean[n][n];

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];

                if(map[r][c]=='#'){
                    continue;
                }

                if (r == 0 && c == 7) {
                    return 1;
                }

                for (int d = 0; d < 9; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr >= 0 && nr < n && nc >= 0 && nc < n && map[nr][nc] != '#' && !visited[nr][nc]) {
                        queue.offer(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
            map = moveMap();
        }

        return 0;
    }

    private char[][] moveMap() {
        char[][] tempMap = new char[n][n];

        for(int i=0; i<n; i++) {
            Arrays.fill(tempMap[i], '.');
        }
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]=='#'){
                    tempMap[i+1][j]='#';
                }
            }
        }

        return tempMap;
    }
}

