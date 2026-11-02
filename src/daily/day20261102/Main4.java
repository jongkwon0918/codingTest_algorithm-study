package daily.day20261102;

import java.util.*;

/*
4. 한 칸을 채워 가장 큰 모양 만들기

0과1로 이루어진 직사각형 배열이 주어진다.
상하좌우로 연결된1의 묶음을 하나의 모양이라고 하고, 포함된 칸 수를 모양의 크기라고 한다.
0인 칸 정확히 하나를1로 바꿀 수 있다.
바꾼 뒤 존재하는 모양 중 가장 큰 크기를 최대화하여 반환하라.
대각선으로 닿는 칸은 연결되지 않는다. 같은 모양에 속한 칸을 여러 번 세면 안 된다.

제한사항
- 2 <= board.length <= 1000
- 2 <= board[0].length <= 1000, 모든 행의 길이는 같다.
- 각 값은0 또는1이며0과1이 각각 최소 하나 존재한다.
- 한 번만 바꾸며, 서로 다른 후보 칸에 대한 변경을 누적하지 않는다.

입출력 예시
board={{1,0},{0,1}} -> 3
board={{1,1,1},{1,0,1},{1,1,1}} -> 9
가운데 칸은 네 방향 모두 같은 모양에 닿지만 기존8칸을 네 번 더하면 안 된다.
board={{1,0,1},{1,0,1}} -> 5
가운데 열의 한 칸을 채우면 두 모양이 합쳐진다.
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new int[][]{
            {1,0},{0,1}
        })); // 3
        System.out.println(solution.solution(new int[][]{
            {1,1,1},{1,0,1},{1,1,1}
        })); // 9
        System.out.println(solution.solution(new int[][]{
            {1,0,1},{1,0,1}
        })); // 5
    }
}

class Solution4 {
    public int solution(int[][] board) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

