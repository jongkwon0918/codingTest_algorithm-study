package daily.day20261006.answers;

import java.util.*;

/*
정답 및 해설: 2번 그림
핵심 풀이: 미방문 1에서 스택을 사용하는 DFS로 그림 하나의 넓이를 센다. 스택에 넣을 때 방문 처리하고, 그림 수와 최대 넓이를 따로 갱신한다. 최대 25만 칸을 다루므로 재귀 깊이 문제를 피하는 반복형 탐색을 사용했다.
시간복잡도: O(n * m)
원본: 백준 1926번 그림
https://www.acmicpc.net/problem/1926
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        try {
        int[][] paper = {
                {1, 1, 0, 1},
                {1, 0, 0, 1},
                {0, 0, 1, 0}
        };
        System.out.println(Arrays.toString(solution.solution(paper)) + " / 기대값: [3, 3]");
        System.out.println(Arrays.toString(solution.solution(new int[][]{
                {0, 0}, {0, 0}
        })) + " / 기대값: [0, 0]");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution2을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution2 {
    public int[] solution(int[][] paper) {
        int n = paper.length;
        int m = paper[0].length;
        boolean[][] visited = new boolean[n][m];
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int count = 0;
        int maxArea = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (paper[r][c] == 0 || visited[r][c]) {
                    continue;
                }
                count++;
                int area = 0;
                Deque<int[]> stack = new ArrayDeque<>();
                stack.push(new int[]{r, c});
                visited[r][c] = true;

                while (!stack.isEmpty()) {
                    int[] current = stack.pop();
                    area++;
                    for (int d = 0; d < 4; d++) {
                        int nr = current[0] + dr[d];
                        int nc = current[1] + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                && paper[nr][nc] == 1 && !visited[nr][nc]) {
                            visited[nr][nc] = true;
                            stack.push(new int[]{nr, nc});
                        }
                    }
                }
                maxArea = Math.max(maxArea, area);
            }
        }
        return new int[]{count, maxArea};
    }
}
