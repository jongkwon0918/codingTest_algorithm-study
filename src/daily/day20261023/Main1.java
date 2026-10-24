package daily.day20261023;

import java.util.*;

/*
1. 여섯 자리 숫자 만들기

5*5 숫자판의 각 칸에 0부터 9까지의 숫자가 적혀 있다.
아무 칸에서 출발해 상하좌우 인접 칸으로 정확히 다섯 번 이동한다.
출발 칸을 포함해 지나온 여섯 칸의 숫자를 순서대로 붙여 여섯 자리 문자열을 만든다.
이미 지나온 칸을 다시 방문해도 되며, 000123처럼 0으로 시작해도 된다.
서로 다른 여섯 자리 문자열을 몇 개 만들 수 있는지 반환하라.
같은 문자열을 여러 이동 경로로 만들어도 한 번만 센다.

제한사항
- board는 5*5 배열이며 모든 값은 0 이상 9 이하이다.

입출력 예시
모든 칸이 0인 판 -> 1 (000000만 가능)
모든 칸이 1인 판 -> 1 (111111만 가능)
모든 칸이 1이고 (3,3)만 2인 판 (좌표 0부터) -> 15
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[][] zeros = new int[5][5];
        int[][] ones = new int[5][5];
        for (int row = 0; row < 5; row++) {
            Arrays.fill(ones[row], 1);
        }
        System.out.println(solution.solution(zeros)); // 1
        System.out.println(solution.solution(ones)); // 1
        ones[3][3] = 2;
        System.out.println(solution.solution(ones)); // 15
    }
}

class Solution1 {
    int n;
    List<Integer> list;
    int[] dr = {-1,1,0,0};
    int[] dc = {0,0,-1,1};
    public int solution(int[][] board) {
        n=5;
        list = new ArrayList<>();

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                dfs(i, j, 0, board[i][j], board);
            }
        }

        return list.size();
    }

    private void dfs(int r, int c, int numLength, int num, int[][] board) {
        if(numLength==5){
            if(!list.contains(num)){
                list.add(num);
            }
            return;
        }

        for(int d=0; d<4; d++){
            int nr = r+dr[d];
            int nc = c+dc[d];
            if(nr>=0 && nr<n && nc>=0 && nc<n){
                dfs(nr, nc, numLength+1, num*10+board[nr][nc], board);
            }
        }

    }
}

