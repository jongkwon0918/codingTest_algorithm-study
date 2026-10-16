package daily.day20261017;

import java.util.*;

/*
1. 적힌 수만큼 점프하기

정사각형 보드의 왼쪽 위에서 출발해 오른쪽 아래로 가려 한다.
현재 칸에 적힌 수만큼 정확히 오른쪽 또는 아래로 점프할 수 있다.
적힌 수보다 짧게 또는 길게 이동하거나, 위·왼쪽으로 이동할 수 없다.
보드 밖으로 나가는 이동은 실패이다.
오른쪽 아래에 도달할 수 있으면 "HaruHaru", 없으면 "Hing"를 반환하라.
목표 칸에는 -1이 적혀 있다. 다른 칸에는 0이 적혀 있을 수도 있다.

제한사항
- 보드는 n*n이고 2 <= n <= 3이다.
- 목표 이외의 칸 값은 0 이상 100 이하이다.
- 목표는 오른쪽 아래 한 칸이다.

입출력 예시
board={{1,1},{1,-1}} -> "HaruHaru"
board={{2,1},{1,-1}} -> "Hing"
board={{0,1},{1,-1}} -> "Hing"
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new int[][]{{1, 1}, {1, -1}})); // HaruHaru
        System.out.println(solution.solution(new int[][]{{2, 1}, {1, -1}})); // Hing
        System.out.println(solution.solution(new int[][]{{0, 1}, {1, -1}})); // Hing
    }
}

class Solution1 {
    public String solution(int[][] board) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

