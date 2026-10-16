package daily.day20261017.answers;

import java.util.*;

/*
2. 여섯 방향으로 움직이는 말

n*n 보드에 놓인 말을 start={행,열}에서 target={행,열}로 이동한다.
한 번의 이동에서 행·열 변화량은 다음 여섯 가지 중 하나이다.
(-2,-1),(-2,1),(0,-2),(0,2),(2,-1),(2,1)
다른 방향으로는 이동할 수 없고 보드 밖으로 나갈 수 없다.
최소 이동 횟수를 반환하라. 도달할 수 없으면 -1이다.
시작과 목표가 같으면 0이다. 장애물은 없다.

제한사항
- 5 <= n <= 200
- 좌표의 행과 열은 0 이상 n 미만이다.
- start와 target의 길이는 각각 2이다.

입출력 예시
n=5, start={0,0}, target={2,1} -> 1
n=5, start={0,0}, target={4,2} -> 2
n=5, start={0,0}, target={1,0} -> -1
n=5, start={2,2}, target={2,2} -> 0
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{2, 1})); // 1
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{4, 2})); // 2
        System.out.println(solution.solution(5, new int[]{0, 0}, new int[]{1, 0})); // -1
        System.out.println(solution.solution(5, new int[]{2, 2}, new int[]{2, 2})); // 0
    }
}

class Solution2 {
    public int solution(int n, int[] start, int[] target) {
        int[] dr = {-2, -2, 0, 0, 2, 2};
        int[] dc = {-1, 1, -2, 2, -1, 1};
        int[][] dist = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        dist[start[0]][start[1]] = 0;
        queue.offer(new int[]{start[0], start[1]});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (row == target[0] && col == target[1]) {
                return dist[row][col];
            }
            for (int d = 0; d < 6; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }
}

