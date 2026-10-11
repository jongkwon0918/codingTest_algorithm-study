package daily.day20261011;

import java.util.*;

/*
1. 밭을 돌보는 지렁이

직사각형 밭에서 field[row][col]이 1이면 배추가 있고, 0이면 없다.
상하좌우로 붙어 있는 배추들은 하나의 묶음이다. 대각선은 연결되지 않는다.
배추흰지렁이 한 마리는 연결된 배추 묶음 전체를 보호할 수 있다.
모든 배추를 보호하기 위해 필요한 최소 지렁이 수를 반환하라.
행은 위에서 아래, 열은 왼쪽에서 오른쪽이며 인덱스는 0부터 시작한다.

제한사항
- 1 <= field.length <= 50
- 1 <= field[0].length <= 50, 모든 행의 길이는 같다.
- 값은 0 또는 1이며 배추 수는 1 이상 2,500 이하이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
field={{1,1,0,0},{0,1,0,1},{1,0,0,1}} -> 3
field={{1,0},{0,1}} -> 2
field={{1}} -> 1
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(
                new int[][]{{1, 1, 0, 0}, {0, 1, 0, 1}, {1, 0, 0, 1}})); // 3
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 2
        System.out.println(solution.solution(new int[][]{{1}})); // 1
    }
}

class Solution1 {
    public int solution(int[][] field) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

