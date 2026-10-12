package daily.day20261012;

import java.util.*;

/*
5. 물이 차오르기 전에 탈출

직사각형 지도에서 고슴도치가 S에서 출발해 비버의 굴 D로 이동하려 한다.
'.'는 빈칸, '*'는 물, 'X'는 돌이다. S와 D는 각각 하나씩 있다.
고슴도치는 1분마다 상하좌우로 한 칸 이동한다.
물도 매분 상하좌우의 빈칸으로 퍼진다. 돌과 비버의 굴에는 물이 들어가지 못한다.
물은 S가 있던 칸을 포함해 고슴도치가 다닐 수 있는 빈 공간으로 퍼질 수 있다.
고슴도치는 돌이나 물이 있는 칸으로 갈 수 없고,
이번 이동 시점에 물이 차는 칸에도 이동할 수 없다.
굴까지 가는 최소 시간을 문자열로 반환하라. 불가능하면 "KAKTUS"를 반환한다.

제한사항
- 행과 열은 각각 1 이상 50 이하이다.
- 모든 문자열의 길이는 같다.
- 지도 문자는 '.', '*', 'X', 'S', 'D' 중 하나이다.
- S와 D는 각각 정확히 하나이며 물은 여러 곳에 있거나 없을 수 있다.

입출력 예시
board={"D.*","...",".S."} -> "3"
board={"D.*","...","..S"} -> "KAKTUS"
board={"SD"} -> "1"
board={"SXD"} -> "KAKTUS"
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(new String[]{"D.*", "...", ".S."})); // 3
        System.out.println(solution.solution(new String[]{"D.*", "...", "..S"})); // KAKTUS
        System.out.println(solution.solution(new String[]{"SD"})); // 1
        System.out.println(solution.solution(new String[]{"SXD"})); // KAKTUS
    }
}

class Solution5 {
    public String solution(String[] board) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

