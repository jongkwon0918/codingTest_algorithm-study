package daily.day20261114.answers;

import java.util.*;

/*
5. 냄새를 따라 움직이는 상어들

n*n 격자에 1번부터 m번까지 상어가 한 마리씩 있다. board[r][c]는 상어 번호이고 0은 빈칸이다.
directions[id-1]은 id번 상어의 처음 방향이다. 방향 번호는1=위,2=아래,3=왼쪽,4=오른쪽이다.
priorities[id-1][direction-1]은 그 상어가 해당 방향을 보고 있을 때 검사할 방향4개의 순서다.
각 행의 값은1~4가 한 번씩 등장한다. 예를 들어 {4,1,3,2}는 오른쪽,위,왼쪽,아래 순서다.
방향은 실제로 이동한 방향으로 바뀐다.

시작 시 모든 상어는 자기 칸에 냄새를 남긴다. 냄새는 주인 번호와 남은 시간을 가지며 k초 유지된다.
매초 다음 처리를 반복한다.
1. 모든 상어가 이동 직전의 같은 냄새 지도를 보고 상하좌우 중 다음 칸을 선택한다.
   먼저 범위 안의 냄새 없는 칸을 우선순위대로 찾는다.
   그런 칸이 전혀 없으면 자기 냄새가 있는 칸을 같은 우선순위대로 찾는다.
   다른 상어의 냄새가 있는 칸은 선택하지 않는다.
2. 모든 상어가 동시에 한 칸 이동한다. 같은 칸에 모이면 가장 작은 번호만 살아남는다.
3. 기존 냄새의 남은 시간이1 줄어든다. 0이 되면 사라진다.
4. 살아남은 상어의 도착 칸에 주인 번호와 남은 시간 k를 기록한다.
냄새가1초 남은 칸도 이번 이동 결정 때는 냄새가 있는 칸이다.
도중에 사라진 상어의 예전 냄새는 남은 시간이 끝날 때까지 유지된다.
1번 상어만 남게 되는 최소 시간을 반환하라. 정확히1000초에 성공하면1000,
1000초가 지나도 다른 상어가 있으면 -1이다.

제한사항
- 2 <= n <= 20, 2 <= m <= n*n, 1 <= k <= 1000
- board에는1~m이 각각 정확히 한 번 등장한다.
- directions.length == m, priorities는 m*4*4 배열이다.
- 방향 값은1~4다.
- 처음에는 모든 상어에게 상하좌우 인접한 빈칸이 하나 이상 있다.

입출력 예시
board={{0,0,0},{1,0,2},{0,0,0}}, directions={4,3}, k=2
1번의 모든 방향별 우선순위={4,1,2,3}, 2번은 모두{3,1,2,4} -> 1
두 상어가 중앙(1,1)에 동시에 이동하여1번만 남는다.
Main의 두 번째 예제 -> 26
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][][] priorities = {
            {{4,1,2,3},{4,1,2,3},{4,1,2,3},{4,1,2,3}},
            {{3,1,2,4},{3,1,2,4},{3,1,2,4},{3,1,2,4}}
        };
        System.out.println(solution.solution(new int[][]{
            {0,0,0},{1,0,2},{0,0,0}
        }, new int[]{4,3}, priorities, 2)); // 1

        int[][][] secondPriorities = {
            {{1,2,3,4},{2,3,4,1},{3,4,1,2},{4,1,2,3}},
            {{1,2,3,4},{2,3,4,1},{3,4,1,2},{4,1,2,3}}
        };
        System.out.println(solution.solution(new int[][]{
            {1,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,2}
        }, new int[]{4,3}, secondPriorities, 6)); // 26
    }
}

class Solution5 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board, int[] directions, int[][][] priorities, int k) {
        int n = board.length;
        int m = directions.length;
        // {행, 열, 방향(0~3)}. 행이 -1이면 사라진 상어다.
        int[][] sharks = new int[m + 1][3];
        int[][] smellOwner = new int[n][n];
        int[][] smellTime = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int id = board[r][c];
                if (id > 0) {
                    sharks[id] = new int[]{r, c, directions[id - 1] - 1};
                    smellOwner[r][c] = id;
                    smellTime[r][c] = k;
                }
            }
        }
        int alive = m;
        for (int time = 1; time <= 1000; time++) {
            int[][] nextBoard = new int[n][n];
            // 번호가 작은 상어부터 넣으면 충돌 시 먼저 도착 정보가 기록된 상어가 이긴다.
            // 냄새 배열은 이 반복문 동안 바꾸지 않으므로 이동 결정은 동시에 이루어진다.
            for (int id = 1; id <= m; id++) {
                if (sharks[id][0] == -1) {
                    continue;
                }
                int r = sharks[id][0];
                int c = sharks[id][1];
                int direction = sharks[id][2];
                int nextR = -1;
                int nextC = -1;
                int nextDirection = direction;
                // mode0: 냄새 없는 칸, mode1: 자기 냄새 칸.
                for (int mode = 0; mode < 2 && nextR == -1; mode++) {
                    for (int option : priorities[id - 1][direction]) {
                        int d = option - 1;
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n
                                && ((mode == 0 && smellTime[nr][nc] == 0)
                                || (mode == 1 && smellTime[nr][nc] > 0
                                    && smellOwner[nr][nc] == id))) {
                            nextR = nr;
                            nextC = nc;
                            nextDirection = d;
                            break;
                        }
                    }
                }
                if (nextBoard[nextR][nextC] == 0) {
                    nextBoard[nextR][nextC] = id;
                    sharks[id] = new int[]{nextR, nextC, nextDirection};
                } else {
                    sharks[id][0] = -1;
                    alive--;
                }
            }
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (smellTime[r][c] > 0) {
                        smellTime[r][c]--;
                        if (smellTime[r][c] == 0) {
                            smellOwner[r][c] = 0;
                        }
                    }
                    if (nextBoard[r][c] > 0) {
                        smellOwner[r][c] = nextBoard[r][c];
                        smellTime[r][c] = k;
                    }
                }
            }
            if (alive == 1) {
                return time;
            }
        }
        return -1;
    }
}

