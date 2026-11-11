package daily.day20261111;

import java.util.*;

/*
2. 불길보다 먼저 건물 밖으로

건물 지도에서 '#'은 벽, '.'은 빈칸, '@'는 사람, '*'는 불이다.
사람은 상하좌우로 한 칸 이동하는 데 1초가 걸린다. 지도 바깥으로 한 칸 이동하면 탈출한다.
불도 매초 상하좌우로 퍼지고 벽에는 번지지 않는다. 불은 여러 곳에서 시작할 수 있다.
이미 불이 있거나 같은 시각에 불이 도착하는 목적지로 사람은 이동할 수 없다.
단, 현재 서 있는 칸에 불이 도착하는 순간 다른 안전한 칸으로 떠나는 것은 가능하다.
최소 탈출 시간을 문자열로, 탈출할 수 없으면 "IMPOSSIBLE"을 반환하라.
시작부터 가장자리에 있더라도 바깥으로 나가는 데 1초가 필요하다.
원본의 테스트 케이스 하나를 solution 한 번에 전달한다.

제한사항
- 높이와 너비는 각각 1 이상 1000 이하, 모든 문자열 길이는 같다.
- 문자는 '#', '.', '@', '*' 중 하나이며 '@'는 정확히 하나다.
- 원본은 최대 100개 테스트 케이스를 각각 독립적으로 처리한다.

입출력 예시
{"####","#@..","####"} -> "3"
(1,1)에서 오른쪽 두 칸 이동 후 밖으로 나간다.
{"###","#@#","###"} -> "IMPOSSIBLE"
{"@"} -> "1"
{"#####","#@.*#","##.##"} -> "IMPOSSIBLE"
첫 이동 목적지에 불도 동시에 도착한다.
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new String[]{"####","#@..","####"})); // 3
        System.out.println(solution.solution(new String[]{"###","#@#","###"})); // IMPOSSIBLE
        System.out.println(solution.solution(new String[]{"@"})); // 1
        System.out.println(solution.solution(new String[]{"#####","#@.*#","##.##"})); // IMPOSSIBLE
    }
}

class Solution2 {
    public String solution(String[] building) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

