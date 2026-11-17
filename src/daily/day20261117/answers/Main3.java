package daily.day20261117.answers;

import java.util.*;

/*
3. 세 번 이동할 때마다 쉬어 가기

n*n 목초지에서 소가 (0,0)을 출발해 (n-1,n-1)로 이동한다.
상하좌우 인접 칸으로 한 번 이동할 때마다 moveTime초가 걸리고 격자 밖으로 나갈 수 없다.
누적 이동 횟수가3의 배수일 때마다, 방금 도착한 칸의 풀을 반드시 먹는다.
풀을 먹는 데 grass[r][c]초가 추가로 걸린다. 출발 직전에는 풀을 먹지 않는다.
목표에 도착한 이동이3번째,6번째,...라면 목표 칸에서도 풀을 먹은 시간까지 포함한다.
이전에 지난 칸을 다시 방문할 수 있으며, 풀은 먹은 뒤에도 다음 방문 시 조건에 따라 다시 먹는다.
도착까지 걸리는 최소 총 시간을 반환하라. 이동 횟수만 최소화하는 문제가 아니다.

제한사항
- 3 <= grass.length <= 100, grass는 정사각형이다.
- 0 <= moveTime <= 1000000
- 1 <= grass[r][c] <= 100000

입출력 예시
grass가3*3이고 모든 값이1, moveTime=1 -> 5
최소 네 번 이동하고 세 번째 이동에서 풀을 한 번 먹어4+1=5다.
같은 지도에서 moveTime=0 -> 1
Main의 마지막 예제 -> 31
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[][] grass = {{1,1,1},{1,1,1},{1,1,1}};
        System.out.println(solution.solution(grass, 1)); // 5
        System.out.println(solution.solution(grass, 0)); // 1
        System.out.println(solution.solution(new int[][]{
            {30,92,36,10},{38,85,60,16},{41,13,5,68},{20,97,13,80}
        }, 2)); // 31
    }
}

class Solution3 {
    public int solution(int[][] grass, int moveTime) {
        int n = grass.length;
        // 마지막으로 풀을 먹은 뒤 이동 횟수: 0,1,2.
        int[][][] dist = new int[n][n][3];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Arrays.fill(dist[r][c], Integer.MAX_VALUE);
            }
        }
        // {행, 열, 이동 횟수의 나머지, 총 시간}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[3], b[3]));
        dist[0][0][0] = 0;
        pq.offer(new int[]{0, 0, 0, 0});
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int r = current[0];
            int c = current[1];
            int count = current[2];
            int cost = current[3];
            if (cost > dist[r][c][count]) {
                continue;
            }
            if (r == n - 1 && c == n - 1) {
                return cost;
            }
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int nextCount = (count + 1) % 3;
                    int nextCost = cost + moveTime;
                    if (nextCount == 0) {
                        nextCost += grass[nr][nc];
                    }
                    if (nextCost < dist[nr][nc][nextCount]) {
                        dist[nr][nc][nextCount] = nextCost;
                        pq.offer(new int[]{nr, nc, nextCount, nextCost});
                    }
                }
            }
        }
        return -1;
    }
}

