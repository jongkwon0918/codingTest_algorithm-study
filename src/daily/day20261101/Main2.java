package daily.day20261101;

import java.util.*;

/*
2. 가장 안전한 빈칸의 거리

직사각형 바다에서0은 빈칸, 1은 아기 상어가 있는 칸이다.
어떤 빈칸의 안전 거리는 그 칸에서 가장 가까운 상어까지의 최단 이동 거리이다.
상하좌우와 대각선을 포함한8방향으로 한 칸씩 이동할 수 있고 한 번의 이동 거리는1이다.
모든 빈칸의 안전 거리 중 가장 큰 값을 반환하라.
상어 하나를 정해 가장 멀리 떨어지는 문제가 아니라, 각 빈칸에서 가장 가까운 상어를 기준으로 한다.

제한사항
- 2 <= sea.length <= 50
- 2 <= sea[0].length <= 50, 모든 행의 길이는 같다.
- 각 값은0 또는1이다.
- 빈칸과 상어는 각각 최소 하나 존재한다.

입출력 예시
sea={{1,0,0},{0,0,0},{0,0,0}} -> 2
(2,2)에서 대각선으로 두 번 이동하면(0,0)에 도착한다.
sea={{1,0,0},{0,0,0},{0,0,1}} -> 2
가운데의 안전 거리는1이지만(0,2)와(2,0)의 안전 거리는2다.
sea={{1,0},{0,1}} -> 1
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new int[][]{
            {1,0,0},{0,0,0},{0,0,0}
        })); // 2
        System.out.println(solution.solution(new int[][]{
            {1,0,0},{0,0,0},{0,0,1}
        })); // 2
        System.out.println(solution.solution(new int[][]{
            {1,0},{0,1}
        })); // 1
    }
}

class Solution2 {
    public int solution(int[][] sea) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

