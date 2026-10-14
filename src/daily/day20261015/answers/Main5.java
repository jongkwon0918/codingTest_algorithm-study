package daily.day20261015.answers;

import java.util.*;

/*
5. 일부 바이러스만 활성화하기

n*n 연구소에서 0은 빈칸, 1은 벽, 2는 비활성 바이러스가 있는 칸이다.
2인 칸 중 정확히 m개를 골라 시간0에 활성화한다.
활성 바이러스는 매초 상하좌우로 한 칸씩 퍼지며 벽은 통과할 수 없다.
선택하지 않은 2인 칸에도 퍼질 수 있고, 도달하면 그 바이러스가 활성화된다.
비활성 바이러스 칸을 지나는 데도 이동 1초가 걸린다.
처음 0이었던 모든 빈칸에 바이러스를 퍼뜨리는 최소 시간을 반환하라.
비활성 바이러스 칸을 전부 활성화할 필요는 없다.
어떤 선택으로도 모든 빈칸에 퍼뜨릴 수 없으면 -1이다. 처음부터 빈칸이 없으면 0이다.

제한사항
- 4 <= n <= 50, lab은 n*n 배열이다.
- lab 값은 0,1,2 중 하나이다.
- 2인 칸 수 V는 1 이상 10 이하이다.
- 1 <= m <= V

입출력 예시
lab={{2,0,2,0},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> 1
(0,2)를 활성화하면 두 빈칸에 모두 1초 만에 도달한다.
lab={{0,2,2,2},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> 1
(0,1)을 활성화한다. 먼 비활성 바이러스가 남아 있어도 빈칸을 채우면 끝이다.
lab={{2,1,0,1},{1,1,1,1},{1,1,1,1},{1,1,1,1}}, m=1 -> -1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] lab = {
            {2, 0, 2, 0},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(lab, 1)); // 1
        int[][] inactive = {
            {0, 2, 2, 2},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(inactive, 1)); // 1
        int[][] blocked = {
            {2, 1, 0, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        System.out.println(solution.solution(blocked, 1)); // -1
    }
}

class Solution5 {
    int[][] lab;
    int n;
    int m;
    int emptyCount;
    int answer;
    List<int[]> viruses;
    int[] selected;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] lab, int m) {
        this.lab = lab;
        n = lab.length;
        this.m = m;
        emptyCount = 0;
        answer = Integer.MAX_VALUE;
        viruses = new ArrayList<>();
        selected = new int[m];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (lab[row][col] == 0) {
                    emptyCount++;
                } else if (lab[row][col] == 2) {
                    viruses.add(new int[]{row, col});
                }
            }
        }
        if (emptyCount == 0) {
            return 0;
        }
        dfs(0, 0);
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private void dfs(int start, int count) {
        if (count == m) {
            answer = Math.min(answer, bfs());
            return;
        }
        for (int i = start; i < viruses.size(); i++) {
            selected[count] = i;
            dfs(i + 1, count + 1);
        }
    }

    private int bfs() {
        int[][] dist = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int index : selected) {
            int[] virus = viruses.get(index);
            int row = virus[0];
            int col = virus[1];
            dist[row][col] = 0;
            queue.offer(new int[]{row, col});
        }

        int infected = 0;
        int time = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && lab[nr][nc] != 1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                    if (lab[nr][nc] == 0) {
                        infected++;
                        time = Math.max(time, dist[nr][nc]);
                    }
                }
            }
        }
        return infected == emptyCount ? time : Integer.MAX_VALUE;
    }
}

