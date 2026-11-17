package daily.day20261117.answers;

import java.util.*;

/*
5. 복제되는 물고기와 세 걸음의 선택

4*4 격자에 물고기들과 상어 한 마리가 있다.
fishes[i]={행,열,방향}이고 shark={행,열}이다. 입력 좌표는 원본처럼1~4를 사용한다.
한 칸에 여러 물고기가 있어도 되고 시작할 때 물고기와 상어가 같은 칸에 있어도 된다.
물고기 방향은1=왼쪽,2=왼쪽위,3=위,4=오른쪽위,5=오른쪽,6=오른쪽아래,7=아래,8=왼쪽아래다.
아래 연습을 turns번 반복하고 남은 전체 물고기 수를 반환하라.

한 번의 연습 순서
1. 현재 모든 물고기의 위치와 방향을 복제용으로 저장한다. 실제 복제는5단계에서 일어난다.
2. 모든 물고기가 한 칸씩 동시에 이동한다. 격자 밖, 상어 칸, 냄새가 있는 칸으로는 이동할 수 없다.
   현재 방향부터 검사하고 불가능하면 반시계45도씩 회전하여 가능한 첫 방향으로 이동한다.
   8방향 모두 불가능하면 원래 위치와 원래 방향으로 남는다. 다른 물고기는 이동을 막지 않는다.
3. 물고기 이동 후 상어가 상하좌우로 연속해서 정확히3번 한 칸 이동한다.
   한 번이라도 격자 밖으로 나가는 경로는 불가능하며, 같은 칸의 재방문은 가능하다.
   지나간 칸의 모든 물고기를 먹고, 실제로 먹은 칸에 냄새를 남긴다.
   먹는 물고기가 가장 많은 경로를 선택한다. 같은 칸의 물고기는 재방문해도 한 번만 센다.
   동률이면 방향을 위=1,왼쪽=2,아래=3,오른쪽=4로 바꾼 세 자리 수가 작은 경로를 선택한다.
   예: 위-아래-왼쪽=132는 아래-오른쪽-아래=343보다 우선한다.
4. 두 번 전 연습에서 생긴 냄새가 사라진다. 이번에 생긴 냄새는 다음 두 번의 물고기 이동을 막는다.
5. 1단계에 저장한 위치와 방향 그대로 물고기를 추가한다. 기존 물고기를 덮어쓰지 않는다.
   복제로 생기는 물고기는 상어 칸이나 냄새 칸에도 나타날 수 있다.
상어는3단계에서 끝난 위치에서 다음 연습을 시작한다.

제한사항
- 1 <= fishes.length <= 10, 1 <= turns <= 100
- 행,열은1~4, 물고기 방향은1~8이다.
- 진행 중 전체 물고기 수는 항상1000000 이하인 입력이다.

입출력 예시
fishes={{1,1,1}}, shark={4,4}, turns=1 -> 2
이동한 물고기는(2,1)에 있고 상어는3걸음으로 닿지 못한다. 복제로 원래(1,1)에 한 마리가 추가된다.
Main의 원본 예제 변환: turns=1 -> 9, turns=2 -> 13
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(new int[][]{{1,1,1}}, new int[]{4,4}, 1)); // 2
        int[][] fishes = {{4,3,5},{1,3,5},{2,4,2},{2,1,6},{3,4,4}};
        System.out.println(solution.solution(fishes, new int[]{4,2}, 1)); // 9
        System.out.println(solution.solution(fishes, new int[]{4,2}, 2)); // 13
    }
}

class Solution5 {
    // 물고기: 왼쪽부터 시계방향. 반시계 회전은 인덱스를 하나 줄인다.
    int[] fishDr = {0, -1, -1, -1, 0, 1, 1, 1};
    int[] fishDc = {-1, -1, 0, 1, 1, 1, 0, -1};
    // 상어 경로의 사전순: 위, 왼쪽, 아래, 오른쪽.
    int[] sharkDr = {-1, 0, 1, 0};
    int[] sharkDc = {0, -1, 0, 1};
    int[][][] fishCount;
    int[][] smell;
    int sharkR;
    int sharkC;
    int maxEat;
    int[] choice;
    int[] bestPath;

    public int solution(int[][] fishes, int[] shark, int turns) {
        fishCount = new int[4][4][8];
        smell = new int[4][4];
        sharkR = shark[0] - 1;
        sharkC = shark[1] - 1;
        choice = new int[3];
        for (int[] fish : fishes) {
            fishCount[fish[0] - 1][fish[1] - 1][fish[2] - 1]++;
        }
        for (int turn = 0; turn < turns; turn++) {
            // 1. 복제할 위치와 방향별 마릿수 보관.
            int[][][] backup = new int[4][4][8];
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    backup[r][c] = fishCount[r][c].clone();
                }
            }
            // 2. 모든 물고기 이동.
            moveFish();
            // 3. 상어의 세 걸음 중 가장 많이 먹는 경로 선택.
            maxEat = -1;
            bestPath = new int[3];
            dfs(sharkR, sharkC, 0, 0, new boolean[4][4]);
            for (int d : bestPath) {
                sharkR += sharkDr[d];
                sharkC += sharkDc[d];
                int count = countAt(sharkR, sharkC);
                if (count > 0) {
                    Arrays.fill(fishCount[sharkR][sharkC], 0);
                    // 바로 다음 감소에서2가 되어 다음 두 번의 이동 동안 남는다.
                    smell[sharkR][sharkC] = 3;
                }
            }
            // 4. 냄새 감소. 물고기가 이동할 때는 감소 전 냄새를 사용했다.
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (smell[r][c] > 0) {
                        smell[r][c]--;
                    }
                }
            }
            // 5. 원래 위치와 방향으로 복제 물고기 추가.
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    for (int d = 0; d < 8; d++) {
                        fishCount[r][c][d] += backup[r][c][d];
                    }
                }
            }
        }
        int answer = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                answer += countAt(r, c);
            }
        }
        return answer;
    }

    private void moveFish() {
        int[][][] next = new int[4][4][8];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                for (int d = 0; d < 8; d++) {
                    int count = fishCount[r][c][d];
                    if (count == 0) {
                        continue;
                    }
                    boolean moved = false;
                    for (int rotation = 0; rotation < 8; rotation++) {
                        int nextDirection = (d - rotation + 8) % 8;
                        int nr = r + fishDr[nextDirection];
                        int nc = c + fishDc[nextDirection];
                        if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4
                                && !(nr == sharkR && nc == sharkC) && smell[nr][nc] == 0) {
                            next[nr][nc][nextDirection] += count;
                            moved = true;
                            break;
                        }
                    }
                    if (!moved) {
                        next[r][c][d] += count;
                    }
                }
            }
        }
        fishCount = next;
    }

    private void dfs(int r, int c, int depth, int eaten, boolean[][] visited) {
        if (depth == 3) {
            // 동률에서는 먼저 찾은 사전순 경로를 유지한다.
            if (eaten > maxEat) {
                maxEat = eaten;
                bestPath = choice.clone();
            }
            return;
        }
        for (int d = 0; d < 4; d++) {
            int nr = r + sharkDr[d];
            int nc = c + sharkDc[d];
            if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
                boolean alreadyVisited = visited[nr][nc];
                int nextEaten = eaten;
                if (!alreadyVisited) {
                    nextEaten += countAt(nr, nc);
                    visited[nr][nc] = true;
                }
                choice[depth] = d;
                dfs(nr, nc, depth + 1, nextEaten, visited);
                // 재방문은 허용하되 같은 칸의 물고기는 한 번만 센다.
                if (!alreadyVisited) {
                    visited[nr][nc] = false;
                }
            }
        }
    }

    private int countAt(int r, int c) {
        int count = 0;
        for (int d = 0; d < 8; d++) {
            count += fishCount[r][c][d];
        }
        return count;
    }
}

