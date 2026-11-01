package daily.day20261030;

import java.util.*;

/*
5. 블록 그룹을 골라 지우고 회전하기

n*n 격자의 모든 칸에 처음에는 블록이 하나씩 놓여 있다.
-1은 검은 블록, 0은 무지개 블록, 1부터 colors까지는 일반 블록의 색 번호다.
상하좌우로 연결된 블록들로 그룹을 만든다. 그룹에는 일반 블록이 최소 하나 있어야 하고,
일반 블록은 모두 같은 색이어야 한다. 무지개 블록은 몇 개든 포함할 수 있다.
검은 블록은 포함할 수 없고, 그룹의 전체 블록 수는 최소2개여야 한다.
한 색과 무지개를 통해 더 연결되는 블록이 있으면 모두 같은 그룹에 포함한다.
무지개 블록 하나가 서로 다른 색의 후보 그룹에 각각 포함될 수 있다.

각 그룹의 '기준 블록'은 그 그룹의 일반 블록 중 행이 가장 작은 블록이며,
그런 블록이 여러 개면 열이 가장 작은 블록이다. 행·열은0부터 시작한다.
다음 순서로 그룹 하나를 선택한다.
1. 전체 블록 수가 가장 큰 그룹
2. 동률이면 무지개 블록 수가 가장 많은 그룹
3. 그래도 동률이면 기준 블록의 행이 가장 큰 그룹
4. 그래도 동률이면 기준 블록의 열이 가장 큰 그룹
기준 블록 자체는 위쪽·왼쪽에서 정하지만, 그룹끼리 비교할 때는 아래쪽·오른쪽을 우선한다.

선택할 그룹이 있는 동안 아래 과정을 반복한다.
1. 선택한 그룹 하나만 삭제하고, 블록 수가 B이면 B*B점을 얻는다.
2. 중력: 일반·무지개 블록을 아래로 내린다. 검은 블록은 움직이지 않고 다른 블록도 통과할 수 없다.
3. 격자 전체를 반시계 방향으로90도 회전한다. 검은 블록과 빈칸도 함께 회전한다.
4. 다시 중력을 적용한다.
선택 가능한 그룹이 없어지면 종료하고 총점을 반환하라.
삭제 후 빈칸은 구현에서 -2 등 별도 값으로 구분한다. 0은 빈칸이 아니다.

제한사항
- 1 <= board.length = n <= 20, 모든 행의 길이는 n이다.
- 1 <= colors <= 5
- 초기 board의 값은 -1 이상 colors 이하이며 초기 빈칸은 없다.
- 검은 블록은 중력에서는 고정이지만 회전 시에는 위치가 바뀐다.

입출력 예시
colors=1, board={{1,1},{1,1}} -> 16
colors=2, board={{1,0},{2,-1}} -> 4
크기2·무지개1인 두 후보 중 기준 행이 큰2색 그룹을 지운다.
colors=2, board={{1,-1},{-1,2}} -> 0
Main의 마지막5*5예제 -> 77
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(1,
                new int[][]{{1,1},{1,1}})); // 16
        System.out.println(solution.solution(2,
                new int[][]{{1,0},{2,-1}})); // 4
        System.out.println(solution.solution(2,
                new int[][]{{1,-1},{-1,2}})); // 0
        System.out.println(solution.solution(3, new int[][]{
            {2,2,-1,3,1},
            {3,3,2,0,-1},
            {0,0,0,1,2},
            {-1,3,1,3,2},
            {0,3,2,2,1}
        })); // 77
    }
}

class Solution5 {
    int n;
    boolean[][] visited;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    List<List<int[]>> list;
    public int solution(int colors, int[][] board) {
        n = board.length;

        int score = 0;

        while(true) {
            visited = new boolean[n][n];
            list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j] && board[i][j] > 0) {
                        List<int[]> newList = new ArrayList<>();
                        newList = dfs(i, j, board[i][j], newList, board);
                        for (int n = 0; n < newList.size(); n++) {
                            if(board[newList.get(n)[0]][newList.get(n)[1]]==0){
                                visited[newList.get(n)[0]][newList.get(n)[1]] = false;
                            }
                        }
                        list.add(newList);
                    }
                }
            }

            List<List<int[]>> maxSizelist = new ArrayList<>();
            int maxSize = 0;
            for (int i = 0; i < list.size(); i++) {
                maxSize = Math.max(maxSize, list.get(i).size());
            }
            if (maxSize >= 2) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).size() == maxSize) {
                        maxSizelist.add(list.get(i));
                    }
                }
            } else {
                return score;
            }

            if (maxSizelist.size() == 1) {
                score += maxSize*maxSize;
                board = clearAndMoveMap(maxSizelist, board);
            } else{
                int maxRainbowCnt = 0;
                List<List<int[]>> maxRainbowList = new ArrayList<>();

                for (int i = 0; i < maxSizelist.size(); i++) {
                    int rainBowCnt = 0;
                    for (int j = 0; j < maxSizelist.get(i).size(); j++) {
                        int[] coord = maxSizelist.get(i).get(j);
                        int r = coord[0];
                        int c = coord[1];

                        if (board[r][c] == 0) {
                            rainBowCnt++;
                        }
                    }
                    if(maxRainbowCnt<rainBowCnt){
                        maxRainbowList.clear();
                        maxRainbowCnt = rainBowCnt;
                        maxRainbowList.add(maxSizelist.get(i));
                    }else if(maxRainbowCnt==rainBowCnt) {
                        maxRainbowList.add(maxSizelist.get(i));
                    }
                }

                if (maxRainbowList.size() == 1) {
                    score += maxSize*maxSize;
                    board = clearAndMoveMap(maxRainbowList, board);
                } else {
                    int maxRow = 0;
                    List<List<int[]>> maxRowList = new ArrayList<>();

                    for (int i = 0; i < maxRainbowList.size(); i++) {
                        int[] standard = findStandard(maxRainbowList.get(i), board);
                        int row = standard[0];

                        if(maxRow<row){
                            maxRowList.clear();
                            maxRow = row;
                            maxRowList.add(maxRainbowList.get(i));
                        }else if(maxRow==row) {
                            maxRowList.add(maxRainbowList.get(i));
                        }
                    }

                    if (maxRowList.size() == 1) {
                        score += maxSize*maxSize;
                        board = clearAndMoveMap(maxRowList, board);
                    } else {
                        int maxCol = 0;
                        List<List<int[]>> maxCowList = new ArrayList<>();

                        for (int i = 0; i < maxRowList.size(); i++) {
                            int[] standard = findStandard(maxRowList.get(i), board);
                            int col = standard[1];

                            if(maxCol<col){
                                maxCowList.clear();
                                maxCol = col;
                                maxCowList.add(maxRowList.get(i));
                            }else if(maxCol==col) {
                                maxCowList.add(maxRowList.get(i));
                            }
                        }

                        score += maxSize*maxSize;
                        board = clearAndMoveMap(maxCowList, board);
                    }
                }
            }

            board = reverseMap(board);
            board = gravitation(board);

        }
    }

    private int[][] reverseMap(int[][] board) {
        int[][] tempMap = new int[n][n];

        for(int r=0; r<n; r++){
            for(int c=0; c<n; c++){
                tempMap[n-1-c][r] = board[r][c];
            }
        }

        return tempMap;
    }

    private int[] findStandard(List<int[]> findList, int[][] board) {
        int standarRow = n;
        int standarCol = n;

        for(int[] coord : findList){
            int r = coord[0];
            int c = coord[1];

            if(board[r][c]>0){
                if(r<standarRow || (r==standarRow && c<standarCol)){
                    standarRow = r;
                    standarCol = c;
                }
            }
        }
        return new int[]{standarRow, standarCol};
    }


    private int[][] clearAndMoveMap(List<List<int[]>> deleteList, int[][] board) {
        for(int j=0; j<deleteList.get(0).size(); j++){
            board[deleteList.get(0).get(j)[0]][deleteList.get(0).get(j)[1]]=-2;
        }

        board = gravitation(board);

        return board;
    }

    private int[][] gravitation(int[][] board) {
        int[][] tempMap = new int[n][n];
        for(int i=0; i<n; i++){
            tempMap[i] = board[i].clone();
        }
        Queue<int[]> queue = new LinkedList<>();

        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (board[i][j] >= 0) {
                    queue.offer(new int[]{i,j});
                }
            }
        }

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            boolean keepGoing = false;
            int nr = r;
            int nc = c;
            while (!keepGoing) {
                if(nr+dr[1]<n && tempMap[nr+dr[1]][nc]==-2){
                    nr += dr[1];
                }else keepGoing = true;
            }
            tempMap[r][c] = -2;
            tempMap[nr][nc] = board[r][c];
        }

        return tempMap;
    }

    private List<int[]> dfs(int r, int c, int color, List<int[]> newList, int[][] board) {
        visited[r][c] = true;
        newList.add(new int[]{r,c});

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                if(!visited[nr][nc] && (board[nr][nc]==0 || board[nr][nc]==color)){
                    dfs(nr, nc, color, newList, board);
                }
            }
        }
        return newList;
    }
}

