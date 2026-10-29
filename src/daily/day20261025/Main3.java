package daily.day20261025;

import java.util.*;

/*
3. 방 색을 최소한으로 바꾸기

n*n 방 배열에서 '1'은 흰 방, '0'은 검은 방이다.
흰 방은 자유롭게 통과할 수 있고 검은 방은 원래 통과할 수 없다.
일부 검은 방을 흰 방으로 바꾸어 왼쪽 위에서 오른쪽 아래까지 이동하려 한다.
상하좌우로 이동할 수 있으며, 바꿔야 하는 검은 방 수의 최솟값을 반환하라.
이동 칸 수를 최소로 만드는 문제가 아니다.
출발 방과 도착 방은 흰 방이다.

제한사항
- 1 <= n <= 50
- rooms는 길이n인 문자열n개로 구성된다.
- 문자는0 또는1이다.

입출력 예시
rooms={"11","11"} -> 0
rooms={"10","01"} -> 1
rooms={"111","000","111"} -> 1
가운데 줄의 검은 방 하나만 바꾸면 된다.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(new String[]{"11","11"})); // 0
        System.out.println(solution.solution(new String[]{"10","01"})); // 1
        System.out.println(solution.solution(new String[]{"111","000","111"})); // 1
    }
}

class Solution3 {
    int n;
    int minBlack;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int[][] map;
    public int solution(String[] rooms) {
        n = rooms.length;
        minBlack = n*n;
        map = new int[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                map[i][j] = rooms[i].charAt(j) - '0';
            }
        }
        int[][] dist = new int[n][n];
        for(int i=0; i<n; i++){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[2], b[2]));
        pq.offer(new int[]{0,0,0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int cost = curr[2];

            if(dist[r][c]<cost){
                continue;
            }

            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr>=0 && nr<n && nc>=0 && nc<n){
                    int nextCost = cost;
                    if(map[nr][nc]==0){
                        nextCost+=1;
                    }

                    if(dist[nr][nc]>nextCost){
                        pq.offer(new int[]{nr, nc, nextCost});
                        dist[nr][nc] = nextCost;
                    }
                }
            }
        }

        return dist[n-1][n-1];
    }
}

