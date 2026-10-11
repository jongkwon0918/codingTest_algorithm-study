package daily.day20261007;

import java.util.*;

/*
4번. 치킨 배달

N x N 도시의 각 칸은 0(빈칸), 1(집), 2(치킨집)이다.
두 칸 (r1,c1), (r2,c2)의 거리는 |r1-r2|+|c1-c2|이다.
집의 치킨 거리는 남겨 둔 치킨집 중 가장 가까운 곳까지의 거리이다.
도시의 치킨 거리는 모든 집의 치킨 거리의 합이다.
치킨집을 최대 m개 남기고 나머지를 폐업할 때 도시의 치킨 거리 최솟값을 반환하라.
거리는 위 공식으로 계산하며 벽이나 이동 경로 조건은 없다.

제한사항
- 2 <= N <= 50인 정사각형 배열
- 1 <= m <= 13
- 집의 수는 1 이상 2*N 이하
- 치킨집 수는 m 이상 13 이하

입출력 예시
city={{1,2,0,0},{0,0,0,0},{0,0,0,0},{0,0,2,1}}, m=1 -> 6
같은 city에서 m=2 -> 2
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        try {
        int[][] city = {
                {1, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 1}
        };
        System.out.println(solution.solution(city, 1) + " / 기대값: 6");
        System.out.println(solution.solution(city, 2) + " / 기대값: 2");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution4을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution4 {
    public int solution(int[][] city, int m) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

