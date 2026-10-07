package daily.day20261006;

import java.util.*;

/*
5번. 아기 상어

N x N 공간에 아기 상어 한 마리와 물고기들이 있다.
0은 빈칸, 1~6은 물고기 크기, 9는 상어의 시작 위치이다.
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
    public int solution(int[][] sea) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

