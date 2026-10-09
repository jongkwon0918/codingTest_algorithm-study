package daily.day20261006;

import java.util.*;

/*
5번. 아기 상어

N x N 공간에 아기 상어 한 마리와 물고기들이 있다.
0은 빈칸, ,1~6은 물고기 크기 9는 상어의 시작 위치이다.
상어의 초기 크기는 2이며 상하좌우로 한 칸 이동하는 데 1초가 걸린다.
자신보다 큰 물고기가 있는 칸은 지나갈 수 없다.
같은 크기의 물고기는 지나갈 수 있지만 먹지 못한다.
자신보다 작은 물고기만 먹을 수 있으며 먹는 데 별도의 시간은 걸리지 않는다.

먹을 수 있는 물고기 중 이동 거리가 가장 짧은 것을 선택한다.
거리가 같으면 가장 위의 물고기, 그래도 같으면 가장 왼쪽 물고기를 선택한다.
현재 크기만큼의 마릿수를 먹으면 크기가 1 커지고, 성장에 필요한 먹은 수는 0으로 초기화한다.
더 이상 도달해서 먹을 물고기가 없을 때 종료한다.
solution(int[][] sea)는 종료할 때까지 걸린 총 시간을 반환한다.

제한사항
- 2 <= N <= 20인 정사각형 배열
- 배열 원소는 0, 1, 2, 3, 4, 5, 6, 9
- 9는 정확히 하나 존재한다.

입출력 예시
sea={{0,0,0},{0,0,0},{0,9,0}} -> 0
sea={{0,0,1},{0,0,0},{0,9,0}} -> 3
sea={{0,1,0},{1,9,1},{0,0,0}} -> 5

아래 Solution5의 메서드를 구현하세요.
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
    int n;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};

    public int solution(int[][] sea) {
        n = sea.length;
        int r = 0;
        int c = 0;
        int[][] map = new int[n][n];

        for(int i=0; i<n; i++){
            map[i] = sea[i].clone();
            for(int j=0; j<n; j++){
                if(map[i][j]==9){
                    r = i;
                    c = j;
                    map[i][j] = 0;
                }
            }
        }

        int size = 2;
        int eaten = 0;
        int time = 0;

        while(true){
            int[] fish = findFish(map, r, c, size);
            if(fish == null){
                return time;
            }
            r = fish[0];
            c = fish[1];
            time += fish[2];
            map[r][c] = 0;
            eaten++;
            if(eaten == size){
                size++;
                eaten = 0;
            }
        }
    }

    private int[] findFish(int[][] map, int r, int c, int size) {
        int[][] dist = new int[n][n];
        for(int[] row : dist){
            Arrays.fill(row, -1);
        }
        dist[r][c] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});

        while(!queue.isEmpty()){
            int[] curr = queue.poll();

            for(int d=0; d<4; d++){
                int nr = curr[0]+dr[d];
                int nc = curr[1]+dc[d];

                if (nr>=0 && nr<n && nc>=0 && nc<n
                        && map[nr][nc]<=size && dist[nr][nc]==-1) {
                    dist[nr][nc] = dist[curr[0]][curr[1]]+1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        List<int[]> fishes = new ArrayList<>();
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]>0 && map[i][j]<size && dist[i][j] != -1){
                    fishes.add(new int[]{i, j, dist[i][j]});
                }
            }
        }

        if(fishes.isEmpty()){
            return null;
        }

        fishes.sort((a,b)-> {
            if(a[2]!=b[2]){
                return Integer.compare(a[2],b[2]);
            }
            if(a[0]!=b[0]){
                return Integer.compare(a[0], b[0]);
            }else{
                return Integer.compare(a[1], b[1]);
            }
        });
        return fishes.get(0);
    }
}
