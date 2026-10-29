package daily.day20261024;

import java.util.*;

/*
4. 카메라 방향으로 사각지대 줄이기

직사각형 사무실에서 0은 빈칸, 6은 벽, 1~5는 카메라 종류이다.
각 카메라는 자기 위치에서 다음 방향들을 동시에 직선으로 감시한다.
- 1번: 한 방향
- 2번: 서로 반대인 두 방향
- 3번: 서로 직각인 두 방향
- 4번: 네 방향 중 세 방향
- 5번: 상하좌우 네 방향
카메라는 위치를 바꿀 수 없고, 각자 90도 단위로 회전시킬 수 있다.
감시는 벽이나 사무실 끝까지 이어진다. 벽은 통과할 수 없지만 다른 카메라는 통과한다.
여러 카메라가 같은 칸을 감시해도 된다.
모든 카메라의 방향을 정했을 때 아무 카메라도 감시하지 못하는 빈칸 수의 최솟값을 반환하라.
벽과 카메라가 있는 칸은 빈칸 수에 포함하지 않는다.

제한사항
- 행과 열의 길이는 각각 1 이상 8 이하이다.
- 모든 행 길이는 같고 값은 0 이상 6 이하이다.
- 카메라는 최대 8개이다.

입출력 예시
office={{0,0,0},{0,5,0},{0,0,0}} -> 4
가운데 5번은 십자 네 칸을 감시하고 모서리 네 칸은 남는다.
office={{1,0,6,0}} -> 1
벽 뒤의 빈칸은 볼 수 없다.
office={{1,0,1}} -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(
                new int[][]{{0,0,0},{0,5,0},{0,0,0}})); // 4
        System.out.println(solution.solution(new int[][]{{1,0,6,0}})); // 1
        System.out.println(solution.solution(new int[][]{{1,0,1}})); // 0
    }
}

class Solution4 {
    int n;
    int m;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    int minCount;
    int[][] tempMap;
    List<int[]> cameras;
    public int solution(int[][] office) {
        n = office.length;
        m = office[0].length;
        minCount=n*m;
        tempMap = new int[n][m];
        cameras = new ArrayList<>();

        for(int i=0; i<n; i++){
            tempMap[i] = office[i].clone();
            for(int j=0; j<m; j++){
                if(office[i][j]!=0 && office[i][j]!=6){
                    cameras.add(new int[]{i, j, office[i][j]});
                }
            }
        }

        dfs(0);

        return minCount;
    }

    private void dfs(int index) {
        if(index==cameras.size()){
            int count = 0;

            for(int i=0; i<n; i++){
                for(int j=0; j<m; j++){
                    if(tempMap[i][j]==0){
                        count++;
                    }
                }
            }
            minCount = Math.min(minCount, count);
            return;
        }

        int[] camera = cameras.get(index);
        int r = camera[0];
        int c = camera[1];
        int type = camera[2];

        for(int d=0; d<4; d++){
            int[][] backupMap = new int[n][m];
            for (int i = 0; i < n; i++) {
                backupMap[i] = tempMap[i].clone();
            }

            if (type == 2) {
                if (d < 2) {
                    watch(r, c, 0);
                    watch(r, c, 1);
                } else {
                    watch(r, c, 2);
                    watch(r, c, 3);
                }
            }else if (type == 3){
                if (d == 0) {
                    watch(r, c, 0);
                    watch(r, c, 3);
                } else if (d == 1) {
                    watch(r, c, 1);
                    watch(r, c, 3);
                } else if (d == 2) {
                    watch(r, c, 1);
                    watch(r, c, 2);
                } else {
                    watch(r, c, 0);
                    watch(r, c, 2);
                }
            } else if (type == 4) {
                for(int z=0; z<4; z++){
                    if(z!=d){
                        watch(r, c, z);
                    }
                }
            } else if (type == 5) {
                for(int z=0; z<4; z++){
                    watch(r, c, z);
                }
            } else{watch(r, c, d);}

            dfs(index+1);

            tempMap = backupMap;
        }
    }

    private void watch(int r, int c, int d) {
        int nr = r + dr[d];
        int nc = c + dc[d];

        while (nr >= 0 && nr < n && nc >= 0 && nc < m) {
            if (tempMap[nr][nc] == 6) {
                break;
            }

            if (tempMap[nr][nc] == 0) {
                tempMap[nr][nc] = -1;
            }

            nr += dr[d];
            nc += dc[d];
        }
    }
}

