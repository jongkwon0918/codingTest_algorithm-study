package daily.day20261006;

import java.util.*;

/*
3번. 지름길

0km 지점에서 Dkm 지점까지 고속도로를 운전한다.
일반 도로에서는 1km 전진할 때 이동 거리도 1km 늘어난다.
shortcuts[i]={시작 위치, 도착 위치, 지름길 길이}인 일방통행 지름길을 이용할 수 있다.
모든 이동은 앞쪽으로만 가능하며 후진할 수 없다.
D를 지나친 뒤 되돌아올 수 없다.
solution(int d, int[][] shortcuts)는 목적지까지의 최소 운전 거리를 반환한다.

제한사항
- 지름길 개수는 1 이상 12 이하
- 1 <= d <= 10,000
- 시작 위치, 도착 위치, 지름길 길이는 0 이상 10,000 이하
- 시작 위치는 도착 위치보다 작다.
- 지름길의 도착 위치가 d보다 클 수도 있다.

입출력 예시
d=150, shortcuts={{0,50,10},{0,50,20},{50,100,10},{100,151,10},{110,140,90}} -> 70
d=100, shortcuts={{0,101,1},{20,40,30}} -> 100

아래 Solution3의 메서드를 구현하세요.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        try {
        int[][] shortcuts = {
                {0, 50, 10}, {0, 50, 20}, {50, 100, 10},
                {100, 151, 10}, {110, 140, 90}
        };
        System.out.println(solution.solution(150, shortcuts) + " / 기대값: 70");
        System.out.println(solution.solution(100, new int[][]{
                {0, 101, 1}, {20, 40, 30}
        }) + " / 기대값: 100");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution3을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution3 {
    public int solution(int d, int[][] shortcuts) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

