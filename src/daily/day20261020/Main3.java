package daily.day20261020;

import java.util.*;

/*
3. 층을 오가며 출구 찾기

building[층][행]은 해당 층의 한 행을 나타내는 문자열이다.
문자열의 열 위치가 하나의 칸이다. 'S'는 출발점, 'E'는 출구, '.'는 빈칸, '#'는 벽이다.
동서남북과 위층·아래층의 인접 칸으로 이동하며 한 번에 1분이 걸린다.
대각선으로 이동하거나 건물 밖으로 나갈 수 없고 벽은 통과할 수 없다.
탈출 가능하면 "Escaped in x minute(s)." 형식으로 최소 시간을 반환하라.
불가능하면 "Trapped!"를 반환한다.

제한사항
- 층 수·각 층의 행 수·열 수는 각각 1 이상 30 이하이다.
- 모든 층의 크기는 같으며 S와 E는 각각 하나이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
building={{"S."},{"#E"}} -> "Escaped in 2 minute(s)."
0층에서 오른쪽으로 이동하고, 같은 행·열의 1층 출구로 이동한다.
building={{"S#E"}} -> "Trapped!"
building={{"S"},{"E"}} -> "Escaped in 1 minute(s)."
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(new String[][]{{"S."}, {"#E"}}));
        // Escaped in 2 minute(s).
        System.out.println(solution.solution(new String[][]{{"S#E"}})); // Trapped!
        System.out.println(solution.solution(new String[][]{{"S"}, {"E"}}));
        // Escaped in 1 minute(s).
    }
}

class Solution3 {
    public String solution(String[][] building) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

