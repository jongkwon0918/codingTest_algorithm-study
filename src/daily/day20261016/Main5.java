package daily.day20261016;

import java.util.*;

/*
5. 여러 벽을 부수며 도착하기

직사각형 지도에서 0은 빈칸, 1은 벽이다.
왼쪽 위 (0,0)에서 오른쪽 아래까지 상하좌우로 이동한다.
벽은 전체 경로에서 최대 k개까지 부수고 그 칸으로 이동할 수 있다.
최단 경로의 길이는 지나간 칸 수이며 시작 칸과 도착 칸도 포함한다.
도착할 수 없으면 -1을 반환하라.
시작점과 도착점이 같으면 길이는 1이다.

제한사항
- 행과 열은 각각 1 이상 1,000 이하이다.
- 1 <= k <= 10
- 모든 행의 길이는 같으며 값은 0 또는 1이다.
- 시작점과 도착점은 항상 0이다.

입출력 예시
map={{0,1,1,0}}, k=1 -> -1
같은 map, k=2 -> 4
map={{0,0,0},{1,1,0},{0,0,0}}, k=1 -> 5
map={{0}}, k=1 -> 1
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] walls = {{0, 1, 1, 0}};
        System.out.println(solution.solution(walls, 1)); // -1
        System.out.println(solution.solution(walls, 2)); // 4
        System.out.println(solution.solution(
                new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}}, 1)); // 5
        System.out.println(solution.solution(new int[][]{{0}}, 1)); // 1
    }
}

class Solution5 {
    public int solution(int[][] map, int k) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

