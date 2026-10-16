package daily.day20261017;

import java.util.*;

/*
5. 가장 짧은 다리 하나 놓기

n*n 지도에서 1은 육지, 0은 바다이다.
상하좌우로 붙어 있는 육지들은 하나의 섬이다. 대각선만 맞닿으면 다른 섬이다.
바다 칸에 다리를 놓아 서로 다른 섬 두 개를 연결하려 한다.
다리는 상하좌우로 이어져야 하며, 길이는 다리를 놓은 바다 칸 수이다.
어떤 두 섬을 선택해도 되며, 모든 섬을 연결할 필요는 없다.
가장 짧은 다리 하나의 길이를 반환하라.

제한사항
- 1 <= n <= 100, map은 n*n 배열이다.
- 값은 0 또는 1이며 항상 두 개 이상의 섬이 있다.
- 다리는 바다에만 놓고 육지는 길이에 포함하지 않는다.

입출력 예시
map={{1,0,1},{1,0,0},{0,0,0}} -> 1
map={{1,0,0},{0,0,0},{0,0,1}} -> 3
map={{1,0},{0,1}} -> 1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(
                new int[][]{{1, 0, 1}, {1, 0, 0}, {0, 0, 0}})); // 1
        System.out.println(solution.solution(
                new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 1}})); // 3
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 1
    }
}

class Solution5 {
    public int solution(int[][] map) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

