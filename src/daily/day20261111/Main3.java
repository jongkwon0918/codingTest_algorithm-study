package daily.day20261111;

import java.util.*;

/*
3. 두 가지 불편함을 피하는 산책

숲 지도에서 S는 출발점, F는 도착점, g는 쓰레기, '.'은 빈칸이다.
상하좌우로 이동하고 모든 칸을 통과할 수 있다.
먼저 쓰레기가 있는 칸을 지나는 횟수가 가장 적은 경로를 선택한다.
그 횟수가 같으면 쓰레기와 상하좌우로 인접한 빈칸을 지나는 횟수가 가장 적은 경로를 선택한다.
인접한 쓰레기가 여러 개여도 해당 빈칸에서는 한 번만 센다.
쓰레기 칸 자체는 첫 번째 횟수에만 포함하고 두 번째 횟수에는 포함하지 않는다.
S와 F는 쓰레기 옆이어도 두 번째 횟수에 포함하지 않는다.
두 횟수를 {쓰레기 통과 횟수, 쓰레기 인접 빈칸 통과 횟수}로 반환하라.
거리나 두 횟수의 단순 합을 최소화하는 문제가 아니다.

제한사항
- 세로와 가로는 각각 3 이상 50 이하, 문자열 길이는 같다.
- 문자는 S,F,g,'.' 중 하나이며 S와 F는 각각 하나다.

입출력 예시
{"S..","...","..F"} -> {0,0}
{"SgF","ggg","..."} -> {1,0}
{"S.F","ggg","..."} -> {0,1}
마지막 예제는 가운데 빈칸이 아래 쓰레기에 인접한다.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(Arrays.toString(solution.solution(new String[]{"S..","...","..F"}))); // [0, 0]
        System.out.println(Arrays.toString(solution.solution(new String[]{"SgF","ggg","..."}))); // [1, 0]
        System.out.println(Arrays.toString(solution.solution(new String[]{"S.F","ggg","..."}))); // [0, 1]
    }
}

class Solution3 {
    public int[] solution(String[] forest) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

