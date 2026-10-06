// 문제: 숨바꼭질
package bfs;/*수빈이는 동생과 숨바꼭질을 하고 있습니다.
수빈이는 현재 점 $N$에 있고, 동생은 점 $K$에 있습니다. 수빈이는 걷거나 순간이동을 할 수 있습니다.
만약 수빈이의 위치가 $X$일 때 걷는다면 1초 후에 $X-1$ 또는 $X+1$로 이동하게 됩니다. 
순간이동을 하는 경우에는 1초 후에 $2 \times X$의 위치로 이동하게 됩니다.수빈이와 동생의 위치가 주어졌을 때, 
수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 함수를 작성해주세요.*/
import java.util.*;

public class BFS_1 {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int n1 = 5;
        int k1 = 17;
        
        int result1 = sol.solution(n1, k1);
        System.out.println("숨바꼭질 결과: " + result1);
    }
}

class Solution {
    public int solution(int n, int k) {
        // 방어 로직: 시작점과 목표점이 같으면 탐색 불필요 (Early Return)
        if (n == k) {
            return 0;
        }

        int MAX = 100000;
        int[] time = new int[MAX + 1];         // 해당 위치까지 도달하는 데 걸린 시간
        boolean[] visited = new boolean[MAX + 1]; // 방문 여부 체크
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(n);
        visited[n] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 동생의 위치에 도달하면 걸린 시간 반환
            if (current == k) {
                return time[current];
            }

            // 3가지 이동 경우의 수
            int[] nextPositions = {current - 1, current + 1, current * 2};

            for (int next : nextPositions) {
                // 유효한 좌표 범위 내에 있고, 아직 방문하지 않은 위치라면 큐에 추가
                if (next >= 0 && next <= MAX && !visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                    time[next] = time[current] + 1;
                }
            }
        }

        return 0;
    }
}