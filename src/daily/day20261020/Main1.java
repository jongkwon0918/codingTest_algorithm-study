package daily.day20261020;

import java.util.*;

/*
1. 방향에 따라 이어지는 판자

직사각형 바닥이 '-'와 '|'로 표시되어 있다.
같은 행에서 좌우로 이어지는 '-'들은 하나의 나무 판자이다.
같은 열에서 위아래로 이어지는 '|'들은 하나의 나무 판자이다.
서로 다른 방향의 문자는 붙어 있어도 같은 판자가 아니다.
필요한 나무 판자의 개수를 반환하라.

제한사항
- 행과 열은 각각 1 이상 50 이하이다.
- 모든 문자열 길이는 같고 문자는 '-' 또는 '|'이다.

입출력 예시
floor={"---","---"} -> 2
floor={"||","||"} -> 2
floor={"-|-","-|-"} -> 5
가운데 세로 판자1개와 양쪽 행별 가로 판자4개이다.
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new String[]{"---", "---"})); // 2
        System.out.println(solution.solution(new String[]{"||", "||"})); // 2
        System.out.println(solution.solution(new String[]{"-|-", "-|-"})); // 5
    }
}

class Solution1 {
    public int solution(String[] floor) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

