package daily.day20261023.answers;

import java.util.*;

/*
5. 위치와 방향을 함께 맞추기

직사각형 공장에서 0은 로봇이 갈 수 있는 칸, 1은 갈 수 없는 칸이다.
로봇은 위치와 바라보는 방향을 가진다. 한 번의 명령으로 다음 중 하나를 수행한다.
- 현재 방향으로 1칸, 2칸 또는 3칸 전진한다. 이동 중 지나가는 모든 칸이 통과 가능해야 한다.
- 제자리에서 왼쪽으로 90도 또는 오른쪽으로 90도 회전한다.
180도 회전은 한 명령이 아니며, 뒤로 걷는 명령도 없다.
start와 target은 각각 {행,열,방향}이다.
행과 열은 원본처럼 1부터 시작하고, 방향은 동=1, 서=2, 남=3, 북=4이다.
목표 위치에 도착하는 것뿐 아니라 목표 방향까지 맞추는 최소 명령 횟수를 반환하라.

제한사항
- 행과 열의 길이는 각각 1 이상 100 이하이다.
- 모든 행 길이는 같고 각 값은 0 또는 1이다.
- 출발 칸과 도착 칸은 0이며 항상 목표까지 이동할 수 있다.

입출력 예시
factory={{0,0,0,0}}, start={1,1,1},target={1,4,1} -> 1
동쪽으로 세 칸 전진하는 명령 한 번이면 된다.
factory={{0}}, start={1,1,1},target={1,1,2} -> 2
같은 칸에서 동→남→서 또는 동→북→서로 두 번 회전해야 한다.
factory가 3*3 모두 0, start={3,1,1},target={1,3,4} -> 3
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                new int[][]{{0,0,0,0}}, new int[]{1,1,1}, new int[]{1,4,1})); // 1
        System.out.println(solution.solution(
                new int[][]{{0}}, new int[]{1,1,1}, new int[]{1,1,2})); // 2
        System.out.println(solution.solution(
                new int[3][3], new int[]{3,1,1}, new int[]{1,3,4})); // 3
    }
}

class Solution5 {
    int[] dr = {0, 0, 0, 1, -1};
    int[] dc = {0, 1, -1, 0, 0};

    public int solution(int[][] factory, int[] start, int[] target) {
        int n = factory.length;
        int m = factory[0].length;
        int[][][] dist = new int[n][m][5];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                Arrays.fill(dist[row][col], -1);
            }
        }

        int startRow = start[0] - 1;
        int startCol = start[1] - 1;
        int targetRow = target[0] - 1;
        int targetCol = target[1] - 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol, start[2]});
        dist[startRow][startCol][start[2]] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int direction = current[2];
            int count = dist[row][col][direction];
            if (row == targetRow && col == targetCol && direction == target[2]) {
                return count;
            }

            // 동/서에서 회전하면 남/북, 남/북에서 회전하면 동/서이다.
            int firstDirection = direction <= 2 ? 3 : 1;
            for (int nextDirection = firstDirection;
                    nextDirection < firstDirection + 2; nextDirection++) {
                if (dist[row][col][nextDirection] == -1) {
                    dist[row][col][nextDirection] = count + 1;
                    queue.offer(new int[]{row, col, nextDirection});
                }
            }

            for (int step = 1; step <= 3; step++) {
                int nr = row + dr[direction] * step;
                int nc = col + dc[direction] * step;
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && factory[nr][nc] == 0) {
                    if (dist[nr][nc][direction] == -1) {
                        dist[nr][nc][direction] = count + 1;
                        queue.offer(new int[]{nr, nc, direction});
                    }
                } else {
                    // 장애물이나 경계는 뛰어넘을 수 없다.
                    break;
                }
            }
        }
        return -1; // 원본의 도달 가능 조건에서는 실행되지 않는다.
    }
}

