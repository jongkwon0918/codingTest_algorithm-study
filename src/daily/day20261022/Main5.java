package daily.day20261022;

import java.util.*;

/*
5. 내려오는 벽을 피해 탈출하기

8*8 판에서 '.'는 빈칸, '#'는 벽이다.
캐릭터는 왼쪽 아래 (7,0)에서 출발해 오른쪽 위 (0,7)로 탈출하려 한다. 좌표는 0부터 시작한다.
1초마다 캐릭터가 먼저 행동하고, 그다음 모든 벽이 한 칸 아래로 내려온다.
캐릭터는 상하좌우 또는 대각선 인접 칸으로 한 칸 이동하거나 제자리에 머무를 수 있다.
캐릭터는 현재 벽이 있는 칸으로 이동할 수 없으며, 벽이 내려온 뒤 캐릭터와 겹쳐도 실패한다.
맨 아래 행의 벽은 다음 이동 때 판 밖으로 사라진다.
목적지에 도달할 수 있으면 1, 불가능하면 0을 반환하라.
이동 횟수의 최솟값을 구하는 문제는 아니다.

제한사항
- maze는 길이 8의 문자열 8개로 구성된다.
- 문자는 '.' 또는 '#'이다.
- 시작 칸 (7,0)은 항상 빈칸이다.

입출력 예시
벽이 전혀 없는 판 -> 1
맨 아래 행만 '........'이고 그 위 7개 행이 모두 '########'인 판 -> 0
두 번째 예제는 첫 행동 뒤 벽이 맨 아래로 내려와 피할 수 없다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        String[] empty = {
            "........", "........", "........", "........",
            "........", "........", "........", "........"
        };
        String[] trapped = {
            "########", "########", "########", "########",
            "########", "########", "########", "........"
        };
        String[] falling = {
            "........", "........", "........", "........",
            "........", "........", "#.......", "........"
        };
        System.out.println(solution.solution(empty)); // 1
        System.out.println(solution.solution(trapped)); // 0
        System.out.println(solution.solution(falling)); // 1
    }
}

class Solution5 {
    public int solution(String[] maze) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

