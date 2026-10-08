package daily.day20261006.answers;

import java.util.*;

/*
정답 및 해설: 5번 아기 상어
핵심 풀이: 먹이를 하나 먹을 때마다 현재 크기로 이동 가능한 칸의 BFS 거리를 다시 구한다.
도달해서 먹을 수 있는 물고기를 모아 거리→행→열 순으로 정렬하고 첫 번째 물고기를 선택한다.
이후 위치, 누적 시간, 먹은 마릿수, 크기를 갱신한다. 시작 위치의 9는 0으로 지워야 성장한 상어가 9를 물고기로 오인하지 않는다.
시간복잡도: O(N^4 log N): 물고기는 최대 N²-1마리, 각 탐색 및 후보 정렬은 O(N² log N)
원본: 백준 16236번 아기 상어
https://www.acmicpc.net/problem/16236
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        try {
        System.out.println(solution.solution(new int[][]{
                {0, 0, 0}, {0, 0, 0}, {0, 9, 0}
        }) + " / 기대값: 0");
        System.out.println(solution.solution(new int[][]{
                {0, 0, 1}, {0, 0, 0}, {0, 9, 0}
        }) + " / 기대값: 3");
        System.out.println(solution.solution(new int[][]{
                {0, 1, 0}, {1, 9, 1}, {0, 0, 0}
        }) + " / 기대값: 5");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution5을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution5 {
    public int solution(int[][] sea) {
        int n = sea.length;
        int[][] map = new int[n][n];
        int r = 0;
        int c = 0;
        for (int i = 0; i < n; i++) {
            map[i] = sea[i].clone();
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 9) {
                    r = i;
                    c = j;
                    map[i][j] = 0;
                }
            }
        }

        int size = 2;
        int eaten = 0;
        int time = 0;
        while (true) {
            int[] fish = findFish(map, r, c, size);
            if (fish == null) {
                return time;
            }
            r = fish[0];
            c = fish[1];
            time += fish[2];
            map[r][c] = 0;
            eaten++;
            if (eaten == size) {
                size++;
                eaten = 0;
            }
        }
    }

    private int[] findFish(int[][] map, int sr, int sc, int size) {
        int n = map.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // 현재 크기로 통과 가능한 칸들의 거리를 구한다.
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nr = current[0] + dr[d];
                int nc = current[1] + dc[d];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                    continue;
                }
                if (dist[nr][nc] == -1 && map[nr][nc] <= size) {
                    dist[nr][nc] = dist[current[0]][current[1]] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        List<int[]> fishes = new ArrayList<>();
        // 도달할 수 있고 먹을 수 있는 물고기만 모은다.
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (map[r][c] > 0 && map[r][c] < size && dist[r][c] != -1) {
                    fishes.add(new int[]{r, c, dist[r][c]});
                }
            }
        }
        if (fishes.isEmpty()) {
            return null;
        }

        // {행, 열, 거리}: 거리 → 위쪽 → 왼쪽 순으로 정렬한다.
        fishes.sort((a, b) -> {
            if (a[2] != b[2]) {
                return Integer.compare(a[2], b[2]);
            }
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        return fishes.get(0);
    }
}
