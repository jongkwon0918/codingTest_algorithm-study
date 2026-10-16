package daily.day20261016;

import java.util.*;

/*
3. 사다리와 뱀을 거쳐 도착하기

1번부터 100번까지의 칸이 있고 1번에서 시작한다.
주사위를 한 번 굴릴 때 1~6 중 원하는 수를 골라 그만큼 앞으로 이동한다.
100번을 넘는 이동은 할 수 없다.
도착한 칸이 사다리 시작점이면 사다리 끝으로, 뱀 시작점이면 뱀 끝으로 반드시 이동한다.
사다리·뱀을 타는 데에는 주사위를 추가로 굴리지 않는다.
ladders[i]={시작,끝}, snakes[i]={시작,끝}이다.
100번에 도착하기 위한 최소 주사위 횟수를 반환하라.

제한사항
- 1 <= ladders.length <= 15, 1 <= snakes.length <= 15
- 사다리는 시작<끝, 뱀은 시작>끝이다.
- 1번과 100번에는 사다리나 뱀이 없다.
- 각 칸에는 최대 하나의 사다리 또는 뱀이 있고 두 종류가 동시에 있지 않는다.
- 항상 100번에 도착할 수 있는 입력이 주어진다.

입출력 예시
ladders={{2,98}}, snakes={{99,3}} -> 2
1->2에서 사다리를 타고98, 다음 주사위2로100에 도착한다.
ladders={{2,90}}, snakes={{91,3}} -> 3
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(
                new int[][]{{2, 98}}, new int[][]{{99, 3}})); // 2
        System.out.println(solution.solution(
                new int[][]{{2, 90}}, new int[][]{{91, 3}})); // 3
    }
}

class Solution3 {
    public int solution(int[][] ladders, int[][] snakes) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

