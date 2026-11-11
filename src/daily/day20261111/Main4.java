package daily.day20261111;

import java.util.*;

/*
4. 세 명이 동시에 지키는 성

n행 m열 격자에 적이 있으며 0은 빈칸, 1은 적이다.
행과 열은 0부터 시작한다. 격자 바로 아래인 n행의 서로 다른 열에 궁수 3명을 배치한다.
각 턴에 궁수마다 거리 distance 이하인 적 중 가장 가까운 적 하나를 공격한다.
거리는 |r1-r2|+|c1-c2|이며 거리가 같다면 열이 가장 작은 적을 선택한다.
공격할 적이 없으면 해당 궁수는 공격하지 않는다.
세 궁수는 동시에 공격하므로 여러 궁수가 같은 적을 선택할 수 있고 그 적은 한 번만 제거된다.
공격받은 적을 모두 제거한 뒤, 남은 적은 아래로 한 칸 이동한다.
n행으로 내려온 적은 성에 도착하여 게임에서 사라지며 제거한 적 수에는 포함하지 않는다.
모든 적이 사라질 때까지 반복한다. 궁수는 움직이지 않는다.
궁수 배치를 잘 선택했을 때 공격으로 제거할 수 있는 적 수의 최댓값을 반환하라.

제한사항
- 3 <= n,m <= 15, 1 <= distance <= 10
- board의 원소는 0 또는 1이다.

입출력 예시
board={{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{1,1,1,1,1},{0,0,0,0,0}},
distance=1 -> 3
같은 board에서 distance=2 -> 5
3*3 빈 격자, distance=1 -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] board = {
            {0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{1,1,1,1,1},{0,0,0,0,0}
        };
        System.out.println(solution.solution(board, 1)); // 3
        System.out.println(solution.solution(board, 2)); // 5
        System.out.println(solution.solution(new int[3][3], 1)); // 0
    }
}

class Solution4 {
    public int solution(int[][] board, int distance) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

