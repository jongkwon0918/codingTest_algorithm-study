package daily.day20261020;

import java.util.*;

/*
4. 세 장애물로 시선 가리기

n*n 복도에서 'T'는 선생님, 'S'는 학생, 'X'는 빈칸이다.
각 선생님은 상하좌우 직선 방향으로 끝까지 학생을 볼 수 있다.
빈칸 세 곳을 골라 장애물 'O'를 정확히 3개 설치한다.
장애물은 그 뒤의 시선을 막으며 학생이나 선생님이 있는 칸에는 설치할 수 없다.
선생님이 다른 선생님을 만났다고 시선이 막히지는 않는다.
모든 학생을 모든 선생님의 시선에서 숨길 수 있으면 "YES", 없으면 "NO"를 반환하라.

제한사항
- 3 <= n <= 6, 모든 문자열 길이는 n이다.
- 선생님은 1 이상 5 이하, 학생은 1 이상 30 이하이다.
- 빈칸은 항상 3개 이상이다.
- 입력 문자는 T,S,X 중 하나이다.

입출력 예시
hallway={"TXS","XXX","XXX"} -> "YES"
(0,1)에 장애물을 두고 나머지 두 개를 다른 빈칸에 둔다.
hallway={"TSX","XXX","XXX"} -> "NO"
선생님과 학생 사이에 장애물을 둘 빈칸이 없다.
hallway={"TXX","XXX","XXS"} -> "YES"
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new String[]{"TXS", "XXX", "XXX"})); // YES
        System.out.println(solution.solution(new String[]{"TSX", "XXX", "XXX"})); // NO
        System.out.println(solution.solution(new String[]{"TXX", "XXX", "XXS"})); // YES
    }
}

class Solution4 {
    public String solution(String[] hallway) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

