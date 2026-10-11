package daily.day20261007;

import java.util.*;

/*
5번. 스타트 택시

N x N 지도에서 0은 이동 가능한 칸, 1은 벽이다.
택시는 상하좌우로 한 칸씩 움직이며 한 칸당 연료 1을 소비한다.
taxi={행,열}, passengers[i]={출발 행,출발 열,도착 행,도착 열}이다.
이 함수에서는 모든 좌표를 0부터 세는 인덱스로 전달한다.

다음 규칙을 반복해 모든 승객을 운송한다.
- 현재 택시에서 최단 이동 거리가 가장 짧은 미운송 승객을 선택한다.
- 거리 동률이면 출발 행이 작은 승객, 그래도 동률이면 출발 열이 작은 승객을 선택한다.
- 그 승객의 출발지로 이동한 뒤 목적지까지 최단거리로 운송한다.
- 성공적으로 도착하면 승객을 태운 뒤 소비한 연료의 2배를 충전한다.
- 승객을 태우러 가는 데 쓴 연료는 충전 대상이 아니다.
- 목적지 도착과 동시에 연료가 정확히 0이 되어도 운송은 성공하고 충전한다.
- 필요한 이동이 불가능하거나 연료가 부족하면 즉시 실패한다.
모든 승객을 운송한 후 남은 연료를 반환하고, 실패하면 -1을 반환하라.
택시와 승객의 출발지가 같으면 승객을 태우러 가는 거리는 0이다.

제한사항
- 2 <= N <= 20인 정사각형 지도
- 1 <= passengers.length <= N*N
- 1 <= fuel <= 500,000
- 택시와 모든 출발·도착 좌표는 지도 내부의 빈칸이다.
- 승객들의 출발지는 서로 다르다. 도착지는 서로 같을 수 있다.
- 각 승객의 출발지와 도착지는 다르다.
- 충전 후 연료는 초기 연료를 초과할 수 있다.

입출력 예시
map={{0,0,0},{0,0,0},{0,0,0}}, taxi={1,1}
passengers={{0,1,0,0},{1,0,2,0}}, fuel=2 -> 2
같은 입력에서 fuel=1 -> -1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        try {
        int[][] map = {
                {0, 0, 0}, {0, 0, 0}, {0, 0, 0}
        };
        int[] taxi = {1, 1};
        int[][] passengers = {
                {0, 1, 0, 0}, {1, 0, 2, 0}
        };
        System.out.println(solution.solution(map, taxi, passengers, 2) + " / 기대값: 2");
        System.out.println(solution.solution(map, taxi, passengers, 1) + " / 기대값: -1");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution5을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution5 {
    public int solution(int[][] map, int[] taxi, int[][] passengers, int fuel) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

