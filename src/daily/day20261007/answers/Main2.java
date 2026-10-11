package daily.day20261007.answers;

import java.util.*;

/*
정답: 2번 스타트링크
해설과 예제 추적: ../ANSWERS.md
원본 백준 5014번 스타트링크
https://www.acmicpc.net/problem/5014
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        try {
        System.out.println(solution.solution(10, 1, 10, 2, 1) + " / 기대값: 6");
        System.out.println(solution.solution(10, 1, 10, 2, 0) + " / 기대값: -1");
        System.out.println(solution.solution(10, 5, 5, 0, 0) + " / 기대값: 0");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution2을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution2 {
    public int solution(int f, int start, int target, int up, int down) {
        int[] dist = new int[f + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                return dist[current];
            }
            int[] nextFloors = {current + up, current - down};
            for (int next : nextFloors) {
                if (next >= 1 && next <= f && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return -1;
    }
}

