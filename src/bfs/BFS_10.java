// 문제: 나이트의 이동
package bfs;/*체스판 위에 한 나이트가 놓여져 있습니다. 나이트가 이동할 수 있는 방향은 총 8가지입니다.
(체스판의 칸을 좌표 평면처럼 생각할 때, L자 형태로 움직이는 방향들입니다.)
나이트가 현재 있는 칸에서 목표하는 칸까지 이동하기 위해 최소 몇 번 움직여야 하는지 그 최소 이동 횟수를 구하는 solution 함수를 작성해 주세요.
나이트가 한 번에 이동할 수 있는 8가지 방향:
(r - 2, c - 1), (r - 2, c + 1), (r - 1, c - 2), (r - 1, c + 2), (r + 1, c - 2), (r + 1, c + 2), (r + 2, c - 1), (r + 2, c + 1)
제한사항
체스판의 한 변의 길이 $L$은 4 이상 300 이하의 자연수입니다.
체스판의 각 칸은 0부터 $L-1$까지의 인덱스를 가집니다.
시작 위치와 도착 위치는 항상 체스판 내부에 존재합니다.*/
import java.util.*;
public class BFS_10 {
    public static void main(String[] args) {
        Solution21 sol = new Solution21();

        int l = 8;
        int startR = 0;
        int startC = 0;
        int targetR = 7;
        int targetC = 7;

        int result = sol.solution(l, startR, startC, targetR, targetC);

        System.out.println(result);
    }
}


class Solution21 {
    public int solution(int l, int startR, int startC, int targetR, int targetC) {
        if (startR == targetR && startC == targetC) return 0;

        int[][] dist = new int[l][l];
        for (int i = 0; i < l; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        dist[startR][startC] = 0;

        // 나이트가 이동할 수 있는 8가지 방향
        int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            // 목적지에 도달하면 이동 횟수 반환
            if (r == targetR && c == targetC) {
                return dist[r][c];
            }

            for (int i = 0; i < 8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // 체스판 범위를 벗어나지 않고, 아직 방문하지 않은 칸(-1)인 경우
                if (nr >= 0 && nr < l && nc >= 0 && nc < l
                        && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return 0;
    }
}
