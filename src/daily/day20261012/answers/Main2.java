package daily.day20261012.answers;

import java.util.*;

/*
2. 이동한 경로까지 찾기

수직선 위 start에서 target까지 이동한다.
현재 위치가 x일 때 x-1, x+1, 2*x 중 하나로 이동할 수 있고, 각각 1초가 걸린다.
최소 이동 시간과 그 시간으로 이동하는 경로 하나를 반환하라.
answer[0]={최소 시간}, answer[1]={시작 위치, ..., 목표 위치}이다.
최단 경로가 여러 개라면 아무 경로나 반환해도 된다.
시작과 목표가 같으면 시간은 0이고 경로에는 시작 위치만 들어간다.

제한사항
- 0 <= start,target <= 100,000
- 위치는 정수이며 음수 위치로 이동하지 않는다.
- 순간이동도 1초가 걸린다.

입출력 예시
start=5, target=17 -> [[4],[5,4,8,16,17]]
[[4],[5,10,9,18,17]]도 정답이다.
start=3, target=3 -> [[0],[3]]
start=2, target=0 -> [[2],[2,1,0]]
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] answer = solution.solution(5, 17);
        System.out.println("최소 시간: " + answer[0][0]); // 4
        System.out.println("경로: " + Arrays.toString(answer[1])); // 최단 경로 하나
        System.out.println(Arrays.deepToString(solution.solution(3, 3))); // [[0], [3]]
        System.out.println(Arrays.deepToString(solution.solution(2, 0))); // [[2], [2, 1, 0]]
    }
}

class Solution2 {
    public int[][] solution(int start, int target) {
        int limit = 100000;
        int[] dist = new int[limit + 1];
        int[] parent = new int[limit + 1];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        parent[start] = start;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                break;
            }
            int[] nextPositions = {current - 1, current + 1, current * 2};
            for (int next : nextPositions) {
                if (next >= 0 && next <= limit && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    parent[next] = current;
                    queue.offer(next);
                }
            }
        }

        int[] path = new int[dist[target] + 1];
        int current = target;
        for (int i = path.length - 1; i >= 0; i--) {
            path[i] = current;
            current = parent[current];
        }
        return new int[][]{new int[]{dist[target]}, path};
    }
}

