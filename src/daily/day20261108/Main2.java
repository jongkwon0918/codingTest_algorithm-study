package daily.day20261108;

import java.util.*;

/*
2. 울타리 안에서 살아남은 동물

직사각형 마당에서 '.'은 빈칸, '#'은 울타리, 'o'는 양, 'v'는 늑대다.
상하좌우로 울타리를 통과하지 않고 이동 가능한 칸들은 같은 영역이다.
마당 밖으로 탈출할 수 있는 칸은 영역으로 보지 않는다.
초기에 모든 양과 늑대는 마당 내부의 영역에 있으므로 밖으로 이어진 영역에는 동물이 없다.
각 영역에서 양이 늑대보다 많으면 그 영역의 양만 살아남는다.
양이 늑대보다 적거나 같으면 그 영역의 늑대만 살아남는다.
영역마다 독립적으로 판단하여 최종 {살아남은 양 수, 살아남은 늑대 수}를 반환하라.
대각선 연결은 인정하지 않으며, 동물을 다른 영역으로 이동시키지는 않는다.

제한사항
- 3 <= yard.length <= 250
- 3 <= yard[0].length() <= 250, 모든 문자열 길이는 같다.
- 문자는 '.', '#', 'o', 'v' 중 하나다.
- 양과 늑대는 모두 마당 안의 닫힌 영역에 존재한다.

입출력 예시
yard={"#######",
      "#oov#v#",
      "#######"} -> {2,1}
왼쪽은 양2·늑대1이므로 양2마리, 오른쪽은 늑대1마리가 살아남는다.
yard={"#####","#ov.#","#####"} -> {0,1}
같은 수면 늑대가 살아남는다.
yard={"###","#.#","###"} -> {0,0}
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(Arrays.toString(solution.solution(new String[]{
            "#######", "#oov#v#", "#######"
        }))); // [2, 1]
        System.out.println(Arrays.toString(solution.solution(new String[]{
            "#####", "#ov.#", "#####"
        }))); // [0, 1]
        System.out.println(Arrays.toString(solution.solution(new String[]{
            "###", "#.#", "###"
        }))); // [0, 0]
    }
}

class Solution2 {
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int n;
    int m;
    boolean[][] visited;
    char[][] map;
    int sheepCnt;
    int wolfCnt;
    public int[] solution(String[] yard) {
        n = yard.length;
        m = yard[0].length();
        visited = new boolean[n][m];
        sheepCnt=0;
        wolfCnt=0;
        map = new char[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++) {
                map[i][j] = yard[i].charAt(j);
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++) {
                if(map[i][j]!='#' && !visited[i][j]){
                    bfs(i,j);
                }
            }
        }

        return new int[]{sheepCnt, wolfCnt};
    }

    private void bfs(int curri, int currj) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{curri, currj});
        visited[curri][currj] = true;
        List<int[]> wolfList = new ArrayList<>();
        List<int[]> sheepList = new ArrayList<>();

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            if(map[r][c]=='o'){
                sheepList.add(new int[]{r, c});
            }else if(map[r][c]=='v'){
                wolfList.add(new int[]{r, c});
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m && map[nr][nc]!='#' && !visited[nr][nc]){
                    queue.offer(new int[]{nr,nc});
                    visited[nr][nc]=true;
                }

            }
        }

        if(wolfList.size()<sheepList.size()){
            sheepCnt+=sheepList.size();
        }else {
            wolfCnt+=wolfList.size();
        }
    }
}

